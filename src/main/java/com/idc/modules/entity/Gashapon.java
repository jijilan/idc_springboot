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
@TableName("hz_gashapon")
public class Gashapon extends Model<Gashapon> {

    private static final long serialVersionUID = 1L;

    /**
     * 扭蛋机ID
     */
    @TableId(value = "gashaponId", type = IdType.AUTO)
    private Long gashaponId;

    /**
     * 设备编号
     */
    @TableField("equipmentNo")
    private String equipmentNo;

    /**
     * 二维码地址【6个二维码,多个以逗号分隔】
     */
    @TableField("qrcodeUrl")
    private String qrcodeUrl;

    /**
     * 门店编号
     */
    @TableField("shopId")
    private Long shopId;

    /**
     * 绑定数量【默认=6】
     */
    @TableField("bindingNum")
    private Integer bindingNum;

    /**
     * 设备状态:【1.正常 2.故障 3.离线 】
     */
    private Integer status;

    /**
     * 状态集合:[0,1,0,0,0,0]【0.表示繁忙 1.表示空闲】
     */
    @TableField("useStatusArray")
    private String useStatusArray;

    /**
     * 是否启用:[1.启用 2.禁用]
     */
    @TableField("isFlag")
    private Integer isFlag;

    /**
     * 创建时间
     */
    private Date ctime;


    @Override
    protected Serializable pkVal() {
        return this.gashaponId;
    }

}
