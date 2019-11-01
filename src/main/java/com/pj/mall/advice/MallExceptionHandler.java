package com.pj.mall.advice;

import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.pj.mall.common.PageResult;
import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Jack
 * @create 2019-03-11 23:08
 * 通用的异常处理
 */
@Slf4j
@RestControllerAdvice//底层采用aop实现
public class MallExceptionHandler {
    @ExceptionHandler(MallException.class)//异常通知
    public PageResult handleMallException(MallException exception){
        log.error("捕获异常：",exception);
        return new PageResult(exception.getExceptionEnum());
    }

    /**
     * 验证码异常
     * @param kaptchaException
     * @return
     */
    @ExceptionHandler(value = KaptchaException.class)
    public PageResult kaptchaExceptionHandler(KaptchaException kaptchaException) {
        log.error("捕获异常：",kaptchaException);
        if (kaptchaException instanceof KaptchaIncorrectException) {
            return new PageResult(ExceptionEnum.CAPTCHA_ERROR);
        } else if (kaptchaException instanceof KaptchaNotFoundException) {
            return new PageResult(ExceptionEnum.CAPTCHA_NOT_FOUND);
        } else if (kaptchaException instanceof KaptchaTimeoutException) {
            return new PageResult(ExceptionEnum.CAPTCHA_TIMEOUT);//内部未生成验证码
        } else {
            return new PageResult(ExceptionEnum.CAPTCHA_RENDER_ERROR);
        }

    }

    @ExceptionHandler(Exception.class)//其他异常
    public PageResult handleException(Exception exception){
        log.error("捕获异常：",exception);
        return new PageResult(ExceptionEnum.SERVER_INTERNAL_EXCEPTION);
    }

}
