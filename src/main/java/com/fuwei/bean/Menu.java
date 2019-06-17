package com.fuwei.bean;

import lombok.Data;

import java.util.List;

/**
 * @author YuanChong
 * @create 2019-05-25 22:49
 * @desc
 */
@Data
public class Menu {

    private Long menuID;
    private String menuName;
    private Long parentID;
    private Integer menuLevel;
    private Integer action;
    private Long excludeID;
    private List<Menu> childList;
    private String menuIDs;
}
