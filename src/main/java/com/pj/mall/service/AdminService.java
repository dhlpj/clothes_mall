package com.pj.mall.service;

import com.pj.mall.pojo.Admin;

/**
 * @author Jack
 * @create 2019-04-02 11:21
 */
public interface AdminService {

    /**
     * 根据用户名及密码查找管理员
     * @param username
     * @param password
     * @return
     */
    Admin queryAdminByUsernameAndPassword(String username, String password);

    /**
     * 验证用户输入的密码是否正确
     * @param id
     * @param oldPassword
     */
    void validatePassword(Long id, String oldPassword);

    /**
     * 修改用户的密码
     * @param id
     * @param newPassword
     */
    void changePwd(Long id, String newPassword);
}
