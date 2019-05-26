package com.fuwei.controller;

import com.fuwei.bean.User;
import com.fuwei.common.ResultCode;
import com.fuwei.common.ResultUtils;
import com.fuwei.exception.BusinessException;
import com.fuwei.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author YuanChong
 * @create 2019-05-25 22:35
 * @desc
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登陆
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public Object login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(user.getUsername())) {
            throw new BusinessException(ResultCode.USERNAME_LOST);
        }
        if(StringUtils.isEmpty(user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_LOST);
        }
        User dbUser = userService.login(user);
        String accessToken = UUID.randomUUID().toString();
        request.getSession().setAttribute(accessToken, dbUser);
        return ResultUtils.success(accessToken);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public Object register(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new BusinessException(ResultCode.USERNAME_LOST);
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_LOST);
        }
        if (StringUtils.isEmpty(user.getRealname())) {
            throw new BusinessException(ResultCode.REALNAME_LOST);
        }
        userService.register(user);
        return ResultUtils.success(null);
    }


}
