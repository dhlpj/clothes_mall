package com.pj.mall.controller.portal;

import com.pj.mall.config.AlipayProperties;
import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.service.AlipayService;
import com.pj.mall.util.PayHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Jack
 * @create 2019-04-18 22:41
 */
@Slf4j
@Controller
@RequestMapping("/portal/alipay")
public class AlipayController {
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private PayHelper payHelper;
    @Autowired
    private AlipayProperties alipayProperties;

    /**
     * 跳转到支付宝页面
     * @param orderId
     * @param response
     */
    @GetMapping("/toAlipayPage/{orderId}")
    public void createOrder(@PathVariable("orderId") Long orderId,HttpServletResponse response){
        String content = alipayService.toAlipayPage(orderId);
        printMsgToResponse(response, content);
    }

    /**
     * 同步通知
     * @param params
     * @return
     */
    //@GetMapping(value = "/return",produces = "application/x-www-form-urlencoded;charset=UTF-8")添加字符编码有时不能跳转页面
    @GetMapping(value = "/return")
    public String payReturn(@RequestParam Map<String,String> params){
        //验签
        boolean signVerified = payHelper.checkSignature(params);
        if(signVerified){
            return "/portal/ok";
        }else{
            return "/portal/error";
        }
    }

    /**
     * 异步通知
     * @param params
     * @param response
     */
    @PostMapping(value = "/notify", produces = "application/x-www-form-urlencoded;charset=UTF-8")
    public void payNotify(@RequestParam Map<String,String> params,HttpServletResponse response){
        String result = alipayService.handleNotification(params);
        printMsgToResponse(response,result);
    }

    private void printMsgToResponse(HttpServletResponse response, String content) {
        try {
            response.setContentType("text/html;charset=" + alipayProperties.getCharset());
            response.getWriter().write(content);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("跳转到支付宝页面失败",e);
            throw new MallException(ExceptionEnum.JUMP_TO_ALIPAY_ERROR);
        }
    }
}
