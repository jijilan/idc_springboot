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
@TableName("hz_shop_charge_rule")
public class ShopChargeRule extends Model<ShopChargeRule> {

    private static final long serialVersionUID = 1L;

    /**
     * 规则ID
     */
    @TableId(value = "ruleId", type = IdType.AUTO)
    private Long ruleId;

    /**
     * 酒店ID
     */
    @TableField("shopId")
    private Long shopId;

    /**
     * 设备分类ID
     */
    @TableField("categoryId")
    private Long categoryId;

    /**
     * 规则键【1.按次】
     */
    @TableField("ruleKey")
    private Integer ruleKey;

    /**
     * 规则值
     */
    @TableField("ruleValue")
    private Long ruleValue;

    /**
     * 规则费用
     */
    @TableField("rulePrice")
    private BigDecimal rulePrice;

    /**
     * 时间
     */
    private Date ctime;


    @Override
    protected Serializable pkVal() {
        return this.ruleId;
    }

}
