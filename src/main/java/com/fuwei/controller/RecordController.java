package com.fuwei.controller;

import com.fuwei.bean.Record;
import com.fuwei.bean.User;
import com.fuwei.common.ResultCode;
import com.fuwei.common.ResultUtils;
import com.fuwei.component.UserResolver;
import com.fuwei.exception.BusinessException;
import com.fuwei.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YuanChong
 * @create 2019-05-26 12:28
 * @desc
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;


    @RequestMapping("/addRecord")
    public Object addRecord(@RequestBody Record record, @UserResolver User user) {
        if(record.getConditionID() == null || record.getConditionID() == 0L) {
            throw new BusinessException(ResultCode.CONDITIONID_LOST);
        }
        if(record.getMenuID() == null || record.getMenuID() == 0L) {
            throw new BusinessException(ResultCode.MENUID_LOST);
        }
        if(record.getData() == null) {
            throw new BusinessException(ResultCode.RECORD_DATA_LIMIT);
        }
        record.setUserID(user.getUserID());
        record.setIsAdmin(user.getIsAdmin());
        recordService.addRecord(record);
        return ResultUtils.success();
    }




}
