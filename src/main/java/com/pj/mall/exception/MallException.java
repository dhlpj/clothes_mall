package com.pj.mall.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jack
 * @create 2019-03-11 23:10
 */
@Getter
@AllArgsConstructor
public class MallException extends RuntimeException{
    private ExceptionEnum exceptionEnum;
}
