package com.fuwei.interceptor;

import com.alibaba.fastjson.JSON;
import com.fuwei.bean.User;
import com.fuwei.common.ResultCode;
import com.fuwei.common.ResultUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author YuanChong
 * @create 2019-05-25 23:08
 * @desc 登陆拦截器 token认证
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if(request.getCookies() != null) {
            Optional<Cookie> accessToken = Arrays.stream(request.getCookies()).filter(cookie -> Objects.equals(cookie.getName(), "accessToken")).findFirst();
            if(accessToken.isPresent()) {
                User user = (User)request.getSession().getAttribute(accessToken.get().getValue());
                if(user != null) {
                    UserHolder.setUser(user);
                    return true;
                }
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String error = JSON.toJSONString(ResultUtils.error(ResultCode.LOGIN_LOST));
        PrintWriter writer = response.getWriter();
        writer.append(error);
        writer.flush();
        writer.close();
        return false;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        UserHolder.clear();
    }
}
