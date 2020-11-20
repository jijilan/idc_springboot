package com.idc.modules.entity;

import java.math.BigDecimal;

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
@TableName("hz_order_consume")
public class OrderConsume extends Model<OrderConsume> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "orderId", type = IdType.AUTO)
    private Long orderId;

    /**
     * 订单业务编号
     */
    @TableField("orderNo")
    private String orderNo;

    /**
     * 订单支付流水号
     */
    @TableField("outTradeNo")
    private String outTradeNo;

    /**
     * 订单状态【1.已完成 2.已退款】
     */
    @TableField("orderStatus")
    private Integer orderStatus;

    /**
     * 支付状态【1未支付 2已支付】
     */
    @TableField("payStatus")
    private Integer payStatus;

    /**
     * 用户编号
     */
    @TableField("userId")
    private Long userId;

    /**
     * 设备ID
     */
    @TableField("gashaponId")
    private Long gashaponId;

    /**
     * 设备序号
     */
    @TableField("deviceNum")
    private String deviceNum;

    /**
     * 门店名称
     */
    @TableField("shopName")
    private String shopName;

    /**
     * 门店地址
     */
    @TableField("shopAddress")
    private String shopAddress;

    /**
     * 套餐规则
     */
    @TableField("chargeRulesJson")
    private String chargeRulesJson;

    /**
     * 支付渠道【1.微信小程序 2.支付宝小程序】
     */
    @TableField("payChannel")
    private Integer payChannel;

    /**
     * 支付金额
     */
    @TableField("payAmount")
    private BigDecimal payAmount;

    /**
     * 支付时间
     */
    @TableField("payTime")
    private Date payTime;

    /**
     * 结束时间
     */
    @TableField("endTime")
    private Date endTime;

    /**
     * 退款时间
     */
    @TableField("returnTime")
    private Date returnTime;

    /**
     * 创建时间
     */
    private Date ctime;


    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
