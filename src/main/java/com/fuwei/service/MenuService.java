package com.fuwei.service;

import com.fuwei.bean.Menu;
import com.fuwei.bean.Record;
import com.fuwei.common.ResultCode;
import com.fuwei.exception.BusinessException;
import com.fuwei.mapper.MenuMapper;
import com.fuwei.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author YuanChong
 * @create 2019-05-26 03:01
 * @desc
 */
@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RecordMapper recordMapper;



    @Transactional(rollbackFor = Exception.class)
    public synchronized void addMenu(Menu menu) {
        Menu params = new Menu();
        params.setParentID(menu.getParentID());
        params.setMenuName(menu.getMenuName());
        List<Menu> list = menuMapper.query(params);
        if(list.size() > 0) {
            throw new BusinessException(ResultCode.MENU_REPE);
        }
        menuMapper.insert(menu);
    }

    /**
     * 删除菜单
     * @param menu
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Menu menu) {
        //查菜单被哪些数据所用
        Record record = new Record();
        record.setMenuID(menu.getMenuID());
        List<Record> query = recordMapper.query(record);
        if(query.size() > 0) {
            throw new BusinessException(ResultCode.DELETE_MENU_WRONG);
        }
        //删菜单
        Menu params = new Menu();
        params.setParentID(menu.getMenuID());
        List<Menu> list = menuMapper.query(params);
        if(list.size() > 0) {
            throw new BusinessException("018","菜单下有子菜单，不能删除");
        }
        menuMapper.delete(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized void updateMenu(Menu menu) {
        Menu params = new Menu();
        params.setParentID(menu.getParentID());
        params.setMenuName(menu.getMenuName());
        //排除掉自身ID做名称校验
        params.setExcludeID(menu.getMenuID());
        List<Menu> list = menuMapper.query(params);
        if(list.size() > 0) {
            throw new BusinessException(ResultCode.MENU_REPE);
        }
        menuMapper.update(menu);
    }

    /**
     * 查询菜单 结果以树层级展示
     * @param menu
     * @return
     */
    public List<Menu> queryMenu(Menu menu) {
        List<Menu> menuList = menuMapper.query(menu);
        //按照父菜单去分组查询菜单 结果以树层级展示
        Map<Long, List<Menu>> parentIDMap = menuList.stream().collect(Collectors.groupingBy(Menu::getParentID));
        //parentID=0的代表一级菜单
        List<Menu> oneLevelMenuList = parentIDMap.get(0L);
        if(oneLevelMenuList != null) {
            for (Menu oneMenu : oneLevelMenuList) {
                //获取当前遍历的一级菜单下的所有二级菜单
                List<Menu> twoLevelMenuList = parentIDMap.get(oneMenu.getMenuID());
                if(twoLevelMenuList != null) {
                    for (Menu twoMenu : twoLevelMenuList) {
                        //获取当前遍历的二级菜单下的所有三级菜单
                        List<Menu> threeLevelMenuList = parentIDMap.get(twoMenu.getMenuID());
                        //设置二级菜单的子菜单
                        twoMenu.setChildList(threeLevelMenuList);

                    }
                }
                //设置一级菜单的子菜单
                oneMenu.setChildList(twoLevelMenuList);
            }
        }
        return oneLevelMenuList;
    }

    /**
     * 查询菜单 结果以树层级展示 递归做法(推荐)
     * @param menu
     * @return
     */
    public List<Menu> queryMenuV2(Menu menu) {
        List<Menu> menuList = menuMapper.query(menu);
        //按照父菜单去分组查询菜单 结果以树层级展示
        Map<Long, List<Menu>> parentIDMap = menuList.stream().collect(Collectors.groupingBy(Menu::getParentID));
        return generyTree(parentIDMap,0L);
    }

    /**
     * 递归生成树结构
     * @param parentIDMap
     * @param parentID 当前所属父分类
     * @return
     */
    public List<Menu> generyTree(Map<Long, List<Menu>> parentIDMap, Long parentID) {
        List<Menu> menuList = parentIDMap.get(parentID);
        if(menuList != null) {
            for (Menu menu : menuList) {
                List<Menu> childList = generyTree(parentIDMap, menu.getMenuID());
                menu.setChildList(childList);
            }
        }
        return menuList;
    }

    /**
     * 启用禁用菜单
     * @param menu
     */
    public void activeMenu(Menu menu) {
        List<Long> menuIDList = new ArrayList<>();
        queryAllChild(menu.getMenuID(),menuIDList);
        String menuIDs = menuIDList.stream().map(String::valueOf).collect(Collectors.joining(","));
        Menu params = new Menu();
        params.setMenuIDs(menuIDs);
        params.setAction(menu.getAction());
        menuMapper.update(params);
    }


    void queryAllChild(Long parentID, List<Long> result) {
        result.add(parentID);
        Menu params = new Menu();
        params.setParentID(parentID);
        //拿到子菜单
        List<Menu> menuList = menuMapper.query(params);
        if(menuList.size() > 0) {
            for(Menu menu:menuList) {
                queryAllChild(menu.getMenuID(),result);
            }
        }
    }


}
