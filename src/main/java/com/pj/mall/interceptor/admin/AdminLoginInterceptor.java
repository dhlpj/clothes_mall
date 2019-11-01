package com.pj.mall.interceptor.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Jack
 * @create 2019-04-06 19:30
 */
@Slf4j
public class AdminLoginInterceptor implements HandlerInterceptor {
    //HandlerAdapter执行之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        StringBuffer requestURL = request.getRequestURL();
        String requestURI = request.getRequestURI();
        log.info("拦截的URL:"+requestURL.toString());
        log.info("requestURI:"+requestURI);
        Object adminUsername = session.getAttribute("adminUsername");
        if(adminUsername==null){
            response.sendRedirect("/admin/login.html");
            return false;//拦截
        }
        //放行
        return true;
    }

    //HandlerAdapter执行之后，返回modelAndView之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    //返回modelAndView之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
