package com.idc.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author jijl
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("hz_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableId(value = "userId", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户业务编号
     */
    @TableField("userNo")
    private String userNo;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @TableField("nickName")
    private String nickName;

    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像不能为空")
    @TableField("userPicture")
    private String userPicture;

    /**
     * 性别:0.未知 1.男 2.女
     */
    @NotNull(message = "性别不能为空")
    private Integer gender;

    /**
     * 微信用户标识
     */
    @TableField("wxOpenId")
    private String wxOpenId;

    /**
     * 支付宝用户标识
     */
    @TableField("aliOpenId")
    private String aliOpenId;

    /**
     * 用户状态【1.启用 2.禁用】
     */
    @TableField("isFlag")
    private Integer isFlag;

    /**
     * 创建时间
     */
    private Date ctime;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
