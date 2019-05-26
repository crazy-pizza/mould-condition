package com.fuwei.mapper;

import com.fuwei.bean.Record;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {


    void delete(Record record);

    void insert(Record record);

    List<Record> query(Record record);
}
