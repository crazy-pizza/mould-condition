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
    private Integer isAdmin;

}
