package com.idc.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Dylan
 * @since 2020-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SmsLogs extends Model<SmsLogs> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发送手机号
     */
    private String phoneNum;

    /**
     * 发送内容
     */
    private String phoneCode;

    /**
     * 请求ip
     */
    private String reqIp;

    /**
     * 发送时间
     */
    private Date sendTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
