package com.fuwei.service;

import com.fuwei.bean.User;
import com.fuwei.common.ResultCode;
import com.fuwei.exception.BusinessException;
import com.fuwei.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
