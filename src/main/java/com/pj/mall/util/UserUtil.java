package com.pj.mall.util;

import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;

import javax.servlet.http.HttpSession;

/**
 * @author Jack
 * @create 2019-04-17 13:12
 */
public class UserUtil {

    /**
     * 获取登录用户的userId
     * @param session
     * @return
     */
    public static Long getUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId==null){
            throw new MallException(ExceptionEnum.UNAUTHORIZED);
        }
        return Long.parseLong(userId.toString());
    }
}
