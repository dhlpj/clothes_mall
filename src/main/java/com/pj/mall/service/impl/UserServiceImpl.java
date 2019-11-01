package com.pj.mall.service.impl;

import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.mapper.UserMapper;
import com.pj.mall.pojo.Admin;
import com.pj.mall.pojo.User;
import com.pj.mall.service.UserService;
import com.pj.mall.util.CodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Jack
 * @create 2019-04-13 19:07
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        //查询用户
        User uesr = new User();
        uesr.setUsername(username);
        User record = userMapper.selectOne(uesr);
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
    public void checkData(String data, Integer type) {
        User user = new User();
        //判断数据类型
        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                throw new MallException(ExceptionEnum.INVALID_USER_DATA_TYPE);
        }
        int count = userMapper.selectCount(user);
        if(count!=0){//表示已经存在该类型的数据
            if(type==1){
                throw new MallException(ExceptionEnum.USERNAME_ALREADY_EXIST);
            }else{
                throw new MallException(ExceptionEnum.PHONE_ALREADY_EXIST);
            }
        }
    }

    @Transactional
    @Override
    public void register(User user) {
        //生成盐值
        String salt = CodeUtil.generateSalt();
        user.setSalt(salt);
        user.setPassword(CodeUtil.md5Hex(user.getPassword(),salt));
        user.setCreateTime(new Date());
        userMapper.insert(user);
    }

    @Override
    public void changePwd(Long userId, String oldPassword, String newPassword) {
        //先查询
        User user = userMapper.selectByPrimaryKey(userId);
        if(user==null){
            throw new MallException(ExceptionEnum.USER_NOT_FOUND);
        }
        String salt = user.getSalt();
        //比较加盐后的密码
        if(!StringUtils.equals(CodeUtil.md5Hex(oldPassword, salt),user.getPassword())){
            throw new MallException(ExceptionEnum.PASSWORD_ERROR);
        }
        salt = CodeUtil.generateSalt();
        //设置新的盐和密码
        user.setSalt(salt);
        user.setPassword(CodeUtil.md5Hex(newPassword,salt));
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User queryUserById(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user==null){
            throw new MallException(ExceptionEnum.USER_NOT_FOUND);
        }
        return user;
    }
}
