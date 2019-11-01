package com.pj.mall.interceptor.portal;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Jack
 * @create 2019-04-13 9:54
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object username = session.getAttribute("username");
        if(username==null){
            log.info("未登录，拦截！");
            String redirectURL = request.getParameter("redirectURL");
            if(StringUtils.isNotBlank(redirectURL)){
                response.sendRedirect("/portal/login.html"+"?redirectURL="+redirectURL);//跳转到商品详情页面
            }else{
                response.sendRedirect("/portal/login.html");//跳转到请求的其他页面
            }
            //拦截
            return false;
        }else{
            //放行
            log.info("已登录，放行！");
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
