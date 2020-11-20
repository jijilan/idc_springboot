package com.idc.common.exception;

import com.idc.common.enums.ResultEnum;
import lombok.Getter;

/**
 * @Author: jijl
 * @Description: 自定义异常类
 * @Date: 2020/7/2 16:48
 **/
@Getter
public class MyException extends RuntimeException {

    private ResultEnum resultEnum;

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }

    public MyException(ResultEnum resultEnum, String message) {
        super(resultEnum.getMsg() + message);
        this.resultEnum = resultEnum;
    }
}
