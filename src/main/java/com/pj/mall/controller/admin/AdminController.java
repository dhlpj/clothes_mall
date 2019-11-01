package com.pj.mall.controller.admin;

import com.baomidou.kaptcha.Kaptcha;
import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.Admin;
import com.pj.mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Jack
 * @create 2019-04-02 10:27
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private Kaptcha kaptcha;
    @Autowired
    private AdminService adminService;

    /**
     * 登录
     * @param username
     * @param password
     * @param verifyCode
     * @param session
     * @return
     */
    @PostMapping("/login")
    public PageResult<Void> login(@RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  @RequestParam("verifyCode") String verifyCode,
                                  HttpSession session){
        //校验验证码
        kaptcha.validate(verifyCode);//default timeout 900 seconds
        //验证用户名及密码
        Admin admin = adminService.queryAdminByUsernameAndPassword(username,password);
        session.setAttribute("id",admin.getId());
        session.setAttribute("adminUsername",admin.getUsername());
        return new PageResult<>();
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @DeleteMapping("/logout")
    public PageResult<Void> logout(HttpSession session){
        //从session中移除用户名
        session.removeAttribute("id");
        session.removeAttribute("adminUsername");
        return new PageResult<>();
    }

    @PostMapping("/validatePassword")
    public PageResult<Void> validatePassword(@RequestParam("oldPassword") String oldPassword,
                                             HttpSession session){
        //获得id
        Long id = (Long)session.getAttribute("id");
        adminService.validatePassword(id,oldPassword);
        return new PageResult<>();
    }

    @PutMapping("/changePwd")
    public PageResult<Void> changePwd(@RequestParam("newPassword") String newPassword,
                                      HttpSession session){
        //获得id
        Long id = (Long)session.getAttribute("id");
        adminService.changePwd(id,newPassword);
        //移除用户名
        session.removeAttribute("id");
        session.removeAttribute("adminUsername");
        return new PageResult<>();
    }
}
