package com.idc.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.idc.common.annotation.valid.Phone;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 合伙人申请表
 * </p>
 *
 * @author jijl
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("hz_partner")
public class Partner extends Model<Partner> {

    private static final long serialVersionUID = 1L;

    /**
     * 合伙人ID
     */
    @TableId(value = "partnerId", type = IdType.AUTO)
    private Long partnerId;

    /**
     * 用户编号
     */
    @TableField("userId")
    private Long userId;

    /**
     * 合伙人姓名
     */
    @NotBlank(message = "姓名不能为空")
    @TableField("partnerName")
    private String partnerName;

    /**
     * 合伙人手机
     */
    @Phone
    @TableField("partnerPhone")
    private String partnerPhone;

    /**
     * 合伙人地址
     */
    @NotBlank(message = "地址不能为空")
    @TableField("partnerAddress")
    private String partnerAddress;

    /**
     * 备注
     */
    @TableField("partnerDesc")
    private String partnerDesc;

    /**
     * 处理状态:1.审核中 2.已处理 3.已驳回
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 处理意见
     */
    private String opinions;

    /**
     * 处理时间
     */
    private Date utime;


    @Override
    protected Serializable pkVal() {
        return this.partnerId;
    }

}
