package com.fuwei.mapper;

import com.fuwei.bean.Condition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConditionMapper {


    void insert(Condition condition);

    List<Condition> query(Condition condition);

    void update(Condition condition);

    void delete(Condition condition);
}
