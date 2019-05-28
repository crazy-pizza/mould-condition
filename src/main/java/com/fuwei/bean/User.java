package com.fuwei.bean;

import lombok.Data;

/**
 * @author YuanChong
 * @create 2019-05-25 22:46
 * @desc
 */
@Data
public class User {

    private String realname;
    private String password;
    private String username;
    private Long userID;
    private Integer isAdmin; //1-普通用户 2-admin用户

    private Integer pageNum;
    private Integer pageSize;
    private String searchKey;
    private String oldPassword;
}
