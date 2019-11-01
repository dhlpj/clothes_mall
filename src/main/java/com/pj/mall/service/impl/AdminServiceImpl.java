package com.pj.mall.service.impl;

import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.mapper.AdminMapper;
import com.pj.mall.pojo.Admin;
import com.pj.mall.service.AdminService;
import com.pj.mall.util.CodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jack
 * @create 2019-04-02 11:21
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin queryAdminByUsernameAndPassword(String username, String password) {
        //查询管理员
        Admin admin = new Admin();
        admin.setUsername(username);
        Admin record = adminMapper.selectOne(admin);
        if(record==null){
            throw new MallException(ExceptionEnum.INVALID_USERNAME_OR_PASSWORD);
        }
        String salt = record.getSalt();
        //比较加盐后的密码
        if(!StringUtils.equals(CodeUtil.md5Hex(password, salt),record.getPassword())){
            throw new MallException(ExceptionEnum.INVALID_USERNAME_OR_PASSWORD);
        }
        return record;
    }

    @Override
    public void validatePassword(Long id, String oldPassword) {
        //查询管理员
        Admin admin = adminMapper.selectByPrimaryKey(id);
        if(admin==null){
            throw new MallException(ExceptionEnum.ADMIN_NOT_FOUND);
        }
        String salt = admin.getSalt();
        //比较加盐后的密码
        if(!StringUtils.equals(CodeUtil.md5Hex(oldPassword, salt),admin.getPassword())){
            throw new MallException(ExceptionEnum.PASSWORD_ERROR);
        }
    }

    @Transactional
    @Override
    public void changePwd(Long id, String newPassword) {
        //先查询
        Admin admin = adminMapper.selectByPrimaryKey(id);
        if(admin==null){
            throw new MallException(ExceptionEnum.ADMIN_NOT_FOUND);
        }
        String salt = CodeUtil.generateSalt();
        //设置新的盐和密码
        admin.setSalt(salt);
        admin.setPassword(CodeUtil.md5Hex(newPassword,salt));
        adminMapper.updateByPrimaryKey(admin);
    }
}
