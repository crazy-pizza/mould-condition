package com.fuwei.service;

import com.fuwei.bean.Condition;
import com.fuwei.bean.Record;
import com.fuwei.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author YuanChong
 * @create 2019-05-26 12:28
 * @desc
 */
@Service
public class RecordService {


    @Autowired
    private ConditionService conditionService;

    @Autowired
    private RecordMapper recordMapper;


    @Transactional(rollbackFor = Exception.class)
    public synchronized void addRecord(Record record) {
        Condition condition = new Condition();
        condition.setUserID(record.getUserID());
        condition.setIsAdmin(record.getIsAdmin());
        //先校验权限
        conditionService.validatePermission(condition);
        //删除老记录
        recordMapper.delete(record);
        //添加新记录
        recordMapper.insert(record);

    }
}
