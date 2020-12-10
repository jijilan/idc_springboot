package com.idc.common.result;

import com.idc.common.enums.ResultEnum;
import com.idc.common.utils.DateUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther Dyaln
 * @Date 2020/12/10
 */
@Data
public class ResultData implements Serializable {
    private String msg;
    private Object data;

    public static ResultData ok(String msg,Object data) {
        return new ResultData(msg,data);
    }
    private ResultData( String msg,Object obj) {
        this.msg = msg;
        this.data=obj;
    }
}
