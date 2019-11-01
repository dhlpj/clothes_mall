package com.pj.mall.common;

import com.pj.mall.exception.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 后台管理返回的数据格式
 * @author Jack
 * @create 2019-03-11 22:57
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    private Integer code;//200为成功，400用户输入问题，404未找到，500服务器出错
    private String msg;
    private Long count;
    private T data;

    /**
     * 封装异常返回
     * @param exceptionEnum
     */
    public PageResult(ExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
        count = 0l;
        data = null;
    }

    /**
     * 无返回数据时
     */
    public PageResult(){
        this.code = 200;
        this.msg = "ok";
        this.count = 0l;
        this.data = null;
    }

    /**
     * 有返回数据时,且为集合
     * @param total
     * @param data
     */
    public PageResult(Long total,T data){
        this.code = 200;
        this.msg = "ok";
        this.count = total;
        this.data = data;
    }

    /**
     * 有返回数据时,且为单个数据
     * @param data
     */
    public PageResult(T data){
        this.code = 200;
        this.msg = "ok";
        this.count = 1l;
        this.data = data;
    }
}
