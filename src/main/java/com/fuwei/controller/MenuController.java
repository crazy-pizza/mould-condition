package com.fuwei.controller;

import com.fuwei.bean.Menu;
import com.fuwei.bean.User;
import com.fuwei.common.ResultCode;
import com.fuwei.common.ResultUtils;
import com.fuwei.component.UserResolver;
import com.fuwei.exception.BusinessException;
import com.fuwei.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YuanChong
 * @create 2019-05-26 02:54
 * @desc
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 添加菜单 非管理员用户不能操作
     * @param menu
     * @param user
     * @return
     */
    @RequestMapping("/addMenu")
    public Object addMenu(@RequestBody Menu menu, @UserResolver User user) {
        if(user.getIsAdmin() != 2) {
            throw new BusinessException(ResultCode.NOT_ADMIN);
        }
        if(StringUtils.isEmpty(menu.getMenuName())) {
            throw new BusinessException(ResultCode.MENU_NAME_LOST);
        }
        if(menu.getMenuLevel() == null || menu.getMenuLevel() == 0) {
            throw new BusinessException(ResultCode.MENU_LEVEL_LOST);
        }
        menuService.addMenu(menu);
        return ResultUtils.success();
    }


    /**
     * 修改菜单 非管理员用户不能操作
     * @param menu
     * @param user
     * @return
     */
    @RequestMapping("/updateMenu")
    public Object updateMenu(@RequestBody Menu menu, @UserResolver User user) {
        if(user.getIsAdmin() != 2) {
            throw new BusinessException(ResultCode.NOT_ADMIN);
        }
        if(StringUtils.isEmpty(menu.getMenuName())) {
            throw new BusinessException(ResultCode.MENU_NAME_LOST);
        }
        menuService.updateMenu(menu);
        return ResultUtils.success();
    }


    /**
     * 启用禁用菜单 非管理员用户不能操作
     * @param menu
     * @param user
     * @return
     */
    @RequestMapping("/activeMenu")
    public Object activeMenu(@RequestBody Menu menu, @UserResolver User user) {
        if(user.getIsAdmin() != 2) {
            throw new BusinessException(ResultCode.NOT_ADMIN);
        }
        if(menu.getAction() == null || menu.getAction() == 0) {
            throw new BusinessException("018","状态必传");
        }
        menuService.activeMenu(menu);
        return ResultUtils.success();
    }

    /**
     * 删除菜单 非管理员用户不能操作
     * @param menu
     * @param user
     * @return
     */
    @RequestMapping("/deleteMenu")
    public Object deleteMenu(@RequestBody Menu menu, @UserResolver User user) {
        if(user.getIsAdmin() != 2) {
            throw new BusinessException(ResultCode.NOT_ADMIN);
        }
        if(menu.getMenuID() == null || menu.getMenuID() == 0L) {
            throw new BusinessException(ResultCode.MENUID_LOST);
        }
        menuService.deleteMenu(menu);
        return ResultUtils.success();
    }


    /**
     * 查询菜单 以树形层级展示
     * @param menu
     * @param user
     * @return
     */
    @RequestMapping("/queryMenu")
    public Object queryMenu(@RequestBody Menu menu, @UserResolver User user) {
        List<Menu> list = menuService.queryMenu(menu);
        return ResultUtils.success(list);
    }


}
