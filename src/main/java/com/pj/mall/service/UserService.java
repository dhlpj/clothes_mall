package com.pj.mall.service;

import com.pj.mall.pojo.User;

/**
 * @author Jack
 * @create 2019-04-13 19:06
 */
public interface UserService {
    /**
     * 校验用户名及密码
     * @param username
     * @param password
     * @return
     */
    User queryUserByUsernameAndPassword(String username, String password);

    /**
     * 校验数据
     * @param data 需要检验的数据
     * @param type 1-用户名    2-手机号
     * @return 不可用就抛出对应的异常
     */
    void checkData(String data, Integer type);

    /**
     * 用户注册
     * @param user
     */
    void register(User user);

    /**
     * 修改密码
     * @param userId
     * @param oldPassword
     * @param newPassword
     */
    void changePwd(Long userId,String oldPassword, String newPassword);

    /**
     * 根据userId查询user
     * @param userId
     * @return
     */
    User queryUserById(Long userId);
}
