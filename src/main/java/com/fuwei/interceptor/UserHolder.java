package com.fuwei.interceptor;

import com.fuwei.bean.User;

/**
 * @author YuanChong
 * @create 2019-05-25 23:22
 * @desc
 */
public class UserHolder {
    private static ThreadLocal<User> currentUser = new ThreadLocal();


    public static User getUser() {
        return currentUser.get();
    }

    public static void setUser(User user) {
        currentUser.set(user);
    }

    public static void clear() {
        currentUser.remove();
    }


}
