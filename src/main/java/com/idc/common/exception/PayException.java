package com.idc.common.exception;


import com.idc.common.enums.ResultEnum;
import lombok.Getter;

/**
 * @Author: jijl
 * @Description: 支付异常
 * @Date: 2020/7/2 16:48
 **/
@Getter
public class PayException extends RuntimeException {

    private ResultEnum resultEnum;

    public PayException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }

    public PayException(ResultEnum resultEnum, String message) {
        super(message);
        this.resultEnum = resultEnum;
    }

    public PayException(String message) {
        super(message);
        this.resultEnum = ResultEnum.CODE_2;
    }
}
