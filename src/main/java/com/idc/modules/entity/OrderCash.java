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
 * 提现表
 * </p>
 *
 * @author jijl
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("hz_order_cash")
public class OrderCash extends Model<OrderCash> {

    private static final long serialVersionUID = 1L;

    /**
     * 提现订单号
     */
    @TableId(value = "orderId", type = IdType.AUTO)
    private Long orderId;

    /**
     * 提现业务单号
     */
    @TableField("orderNo")
    private String orderNo;

    /**
     * 支付流水号
     */
    @TableField("outTradeNo")
    private String outTradeNo;

    /**
     * 代理商编号
     */
    @TableField("managerId")
    private String managerId;

    /**
     * 开户人/公司名称
     */
    @TableField("realName")
    private String realName;

    /**
     * 银行卡号/对公账号
     */
    @TableField("bankCard")
    private String bankCard;

    /**
     * 账户类型【1.私人卡号 2.对公账户】
     */
    @TableField("accountType")
    private Integer accountType;

    /**
     * 所属银行
     */
    @TableField("bankName")
    private String bankName;

    /**
     * 身份证号码
     */
    @TableField("idCard")
    private String idCard;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 提现金额
     */
    private BigDecimal amount;

    /**
     * 审核状态1.审核中 2.通过 3.驳回
     */
    private Integer status;

    /**
     * 驳回理由
     */
    @TableField("reasonContext")
    private String reasonContext;

    /**
     * 处理时间
     */
    private Date utime;

    /**
     * 申请时间
     */
    private Date ctime;


    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
