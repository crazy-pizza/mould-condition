package com.fuwei.mapper;

import com.fuwei.bean.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuanChong
 * @create 2019-05-26 03:09
 * @desc
 */
@Mapper
public interface MenuMapper {


    List<Menu> query(Menu menu);

    void insert(Menu menu);

    void delete(Menu menu);

    void update(Menu menu);
}
