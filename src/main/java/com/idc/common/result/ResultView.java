package com.idc.common.result;

import com.idc.common.enums.ResultEnum;
import com.idc.common.utils.DateUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: jijl
 * @Description: restFul自定义响应类
 * @Date: 2020/7/2 16:54
 **/
@Data
public class ResultView implements Serializable {

    private Integer code;

    private String msg;

    private Object data;

    private String time;


    public static ResultView ok() {
        return new ResultView();
    }

    public static ResultView ok(Object data) {
        return new ResultView(data);
    }
    public static ResultView ok(String msg,Object data) {
        return new ResultView(200,msg,data);
    }
    public static ResultView error(ResultEnum resultEnum) {
        return new ResultView(resultEnum.getCode(), resultEnum.getMsg());
    }

    public static ResultView error(ResultEnum resultEnum, String errMsg) {
        return new ResultView(resultEnum.getCode(), errMsg);
    }

    public static ResultView error(Integer code, String errMsg) {
        return new ResultView(code, errMsg);
    }
    public static ResultView ok(Integer code, String msg,Object data) {
        return new ResultView(code,msg,data);
    }

    public static ResultView error(String errMsg) {
        return new ResultView(errMsg);
    }

    private ResultView() {
        this.code = ResultEnum.CODE_200.getCode();
        this.msg = ResultEnum.CODE_200.getMsg();
        this.time = DateUtils.getCurrentDateTime();
    }

    private ResultView(Object data) {
        this.data = data;
        this.code = ResultEnum.CODE_200.getCode();
        this.msg = ResultEnum.CODE_200.getMsg();
        this.time = DateUtils.getCurrentDateTime();
    }

    private ResultView(Integer code, String msg,Object obj) {
        this.code = code;
        this.msg = msg;
        this.data=obj;
        this.time = DateUtils.getCurrentDateTime();
    }
    private ResultView(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.time = DateUtils.getCurrentDateTime();
    }
    private ResultView(String errMsg) {
        this.code = ResultEnum.CODE_2.getCode();
        this.msg = ResultEnum.CODE_2.getMsg() + errMsg;
        this.time = DateUtils.getCurrentDateTime();
    }
}
