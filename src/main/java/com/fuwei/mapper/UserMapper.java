package com.fuwei.mapper;

import com.fuwei.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuanChong
 * @create 2019-05-26 00:26
 * @desc
 */
@Mapper
public interface UserMapper {

    User query(User user);

    void insert(User user);

    void resetPassword(User user);

    List<User> queryList(User user);
}
