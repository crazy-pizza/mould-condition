package com.fuwei.bean;

import lombok.Data;

import java.util.List;

/**
 * @author YuanChong
 * @create 2019-05-25 22:51
 * @desc
 */
@Data
public class Condition {

    private Long conditionID;
    private String mouldNum;
    private String robotKind;
    private String machineNum;
    private Long date;
    private Long time;
    private String remark;
    private Long userID;
    private Integer isAdmin;

    private Integer pageNum;
    private Integer pageSize;

    private List<Record> recordList;
}
