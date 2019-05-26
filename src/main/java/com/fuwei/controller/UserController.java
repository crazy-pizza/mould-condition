package com.fuwei.controller;

import com.fuwei.bean.Condition;
import com.fuwei.bean.User;
import com.fuwei.common.ResultCode;
import com.fuwei.common.ResultUtils;
import com.fuwei.component.UserResolver;
import com.fuwei.exception.BusinessException;
import com.fuwei.service.UserService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/out/login")
    public Object login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(user.getUsername())) {
            throw new BusinessException(ResultCode.USERNAME_LOST);
        }
        if(StringUtils.isEmpty(user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_LOST);
        }
        User dbUser = userService.login(user);
        //生成token
        String accessToken = UUID.randomUUID().toString();
        dbUser.setAccessToken(accessToken);
        request.getSession().setAttribute(accessToken, dbUser);
        return ResultUtils.success(accessToken);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("/out/register")
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
        return ResultUtils.success();
    }

    /**
     * 登出
     * @param
     * @return
     */
    @RequestMapping("/logoff")
    public Object logoff(@UserResolver User user, HttpServletRequest request) {
        if(StringUtils.isNotEmpty(user.getAccessToken())) {
            request.getSession().removeAttribute(user.getAccessToken());
        }
        return ResultUtils.success();
    }

    /**
     * 重置密码
     * @param user 要重置的用户
     * @param loginUser 目前登陆的用户
     * @return
     */
    @RequestMapping("/resetPassword")
    public Object resetPassword(@RequestBody User user, @UserResolver User loginUser) {
        if(user.getUserID() == null || user.getUserID() == 0L) {
            throw new BusinessException(ResultCode.USERID_LOST);
        }
        if(loginUser.getIsAdmin() != 2) {
            throw new BusinessException(ResultCode.NOT_ADMIN);
        }
        userService.resetPassword(user);
        return ResultUtils.success();
    }

    /**
     * 分页查询用户列表
     * @param user
     * @return
     */
    @RequestMapping("/queryUserList")
    public Object queryUserList(@RequestBody User user) {
        if(user.getPageNum() == null || user.getPageNum() == 0L) {
            throw new BusinessException(ResultCode.PAGENO_LIMIT);
        }
        if(user.getPageSize() == null || user.getPageSize() == 0L) {
            throw new BusinessException(ResultCode.PAGESIZE_LIMIT);
        }
        PageInfo<Condition> pageInfo = userService.queryUser(user);
        return ResultUtils.pageSuccess(pageInfo);
    }

    /**
     * 查询当前登陆的用户信息
     * @return
     */
    @RequestMapping("/loginUser")
    public Object isAdmin(@UserResolver User user) {
        return ResultUtils.success(user);
    }


}
