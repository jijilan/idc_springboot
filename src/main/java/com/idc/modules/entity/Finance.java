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
 *
 * </p>
 *
 * @author jijl
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("hz_finance")
public class Finance extends Model<Finance> {

    private static final long serialVersionUID = 1L;

    /**
     * 财务ID
     */
    @TableId(value = "financeId", type = IdType.AUTO)
    private Long financeId;

    /**
     * 财务业务标识
     */
    @TableField("financeNo")
    private String financeNo;

    /**
     * 订单ID
     */
    @TableField("orderId")
    private Long orderId;

    /**
     * 用户ID
     */
    @TableField("userId")
    private Long userId;

    /**
     * 代理商ID
     */
    @TableField("managerId")
    private String managerId;

    /**
     * 财务类型【1.收益(代理商收益) 2.支出(代理退款或用户下单) 3.提现 4.提现退还】
     */
    private Integer type;

    /**
     * 支付渠道: 1.微信 2.支付宝
     */
    @TableField("sourceChannel")
    private Integer sourceChannel;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 收益金额
     */
    @TableField("revenueAmount")
    private BigDecimal revenueAmount;

    /**
     * 支出金额
     */
    @TableField("expensesAmount")
    private BigDecimal expensesAmount;

    /**
     * 有效性:1.有效 2.无效
     */
    @TableField("isFlag")
    private Integer isFlag;

    /**
     * 创建时间
     */
    private Date ctime;


    @Override
    protected Serializable pkVal() {
        return this.financeId;
    }

}
