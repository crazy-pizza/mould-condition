package com.fuwei.exception;

import com.fuwei.common.ResultCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

	private String code;
    private String msg;

    public BusinessException(ResultCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

}
