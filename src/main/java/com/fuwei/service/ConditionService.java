package com.fuwei.service;

import com.fuwei.bean.Condition;
import com.fuwei.bean.Record;
import com.fuwei.common.ResultCode;
import com.fuwei.exception.BusinessException;
import com.fuwei.mapper.ConditionMapper;
import com.fuwei.mapper.RecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author YuanChong
 * @create 2019-05-26 11:06
 * @desc
 */
@Service
public class ConditionService {

    @Autowired
    private ConditionMapper conditionMapper;

    @Autowired
    private RecordMapper recordMapper;


    @Transactional(rollbackFor = Exception.class)
    public void addCondition(Condition condition) {
        conditionMapper.insert(condition);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCondition(Condition condition) {
        validatePermission(condition);
        conditionMapper.update(condition);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteCondition(Condition condition) {
        validatePermission(condition);
        conditionMapper.delete(condition);
        //删除关联的所有数据
        Record record = new Record();
        record.setConditionID(condition.getConditionID());
        recordMapper.delete(record);
    }

    /**
     * 权限校验
     * @param condition
     */
    public void validatePermission(Condition condition) {
        Condition params = new Condition();
        params.setConditionID(condition.getConditionID());
        List<Condition> list = conditionMapper.query(params);
        if(list.size() == 0) {
            throw new BusinessException(ResultCode.CONDITION_LOST);
        }
        Long userID = list.get(0).getUserID();
        //校验记录创建人与当前用户是不是同一个 admin具有全部权限 如果没有权限 抛出异常
        if(!condition.getUserID().equals(userID) && condition.getIsAdmin() != 2) {
            throw new BusinessException(ResultCode.PERMISSION_LIMIT);
        }
    }

    public PageInfo<Condition> queryCondition(Condition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<Condition> list = conditionMapper.query(condition);
        //所有查到的条件记录的主键ID集合
        String conditionIDs = list.stream().map(Condition::getConditionID).map(String::valueOf).collect(Collectors.joining(","));
        if(StringUtils.isNotEmpty(conditionIDs)) {
            Record record = new Record();
            record.setConditionIDs(conditionIDs);
            //查询每条记录下的具体菜单数据
            List<Record> recordList = recordMapper.query(record);
            //按照conditionID分组
            Map<Long, List<Record>> recordMap = recordList.stream().collect(Collectors.groupingBy(Record::getConditionID));
            for(Condition con : list) {
                List<Record> records = recordMap.get(con.getConditionID());
                con.setRecordList(records);

            }
        }
        PageInfo<Condition> pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
