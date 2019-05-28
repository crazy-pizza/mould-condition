package com.fuwei.service;

import com.fuwei.bean.Condition;
import com.fuwei.bean.User;
import com.fuwei.common.ResultCode;
import com.fuwei.exception.BusinessException;
import com.fuwei.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YuanChong
 * @create 2019-05-26 00:10
 * @desc
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public User login(User user) {
        //加密
        String encryptPassword = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(encryptPassword);
        User dbUser = userMapper.query(user);
        if(dbUser == null) {
            throw new BusinessException(ResultCode.LOGIN_FAIL);
        }
        return dbUser;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized void register(User user) {
        User query = new User();
        query.setUsername(user.getUsername());
        User dbUser = userMapper.query(query);
        if(dbUser != null) {
            throw new BusinessException(ResultCode.USERNAME_EXIST);
        }
        //加密
        String encryptPassword = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(encryptPassword);
        userMapper.insert(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(User user) {
        //加密 默认密码123456
        String encryptPassword = DigestUtils.md5Hex("123456");
        user.setPassword(encryptPassword);
        userMapper.resetPassword(user);

    }


    public List<User> queryUserList(User user) {
        return userMapper.queryList(user);
    }
}
