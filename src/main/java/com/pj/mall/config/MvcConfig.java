package com.pj.mall.config;

import com.pj.mall.interceptor.admin.AdminLoginInterceptor;
import com.pj.mall.interceptor.portal.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jack
 * @create 2019-04-04 11:43
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminLoginInterceptor()).
                addPathPatterns("/admin/**").excludePathPatterns("/admin/login.html","/admin/login");
        registry.addInterceptor(new UserInterceptor()).
                addPathPatterns("/portal/cart/**","/portal/order/**","/portal/alipay/toAlipayPage/**","/portal/user/**").
                excludePathPatterns("/portal/user/login","/portal/user/register","/portal/user/check/**");
    }

    //配置静态资源的映射策略
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").
                addResourceLocations("classpath:/static/");
    }*/
}
