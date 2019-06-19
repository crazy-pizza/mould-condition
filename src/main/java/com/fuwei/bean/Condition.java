package com.fuwei.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

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
    private String date;
    private String time;
    private String remark;
    private Long userID;
    private Integer isAdmin;

    private Integer pageNum;
    private Integer pageSize;

    private List<Record> recordList;

    private String realname;
}
