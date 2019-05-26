package com.fuwei.bean;

import lombok.Data;

/**
 * @author YuanChong
 * @create 2019-05-25 22:57
 * @desc
 */
@Data
public class Record {

    private Long recordID;
    private Long conditionID;
    private Long menuID;
    private String data;

    private Long userID;
    private Integer isAdmin;

    private String conditionIDs;
}
