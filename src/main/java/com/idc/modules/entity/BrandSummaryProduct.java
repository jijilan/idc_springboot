package com.idc.modules.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品牌 代理商、制造商 简介--相关产品
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandSummaryProduct extends Model<BrandSummaryProduct> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * brand_dictionary 编码 code
     */
    private String dicCode;

    /**
     * 品牌
     */
    private String pinpai;

    /**
     * 序列型号
     */
    private String xuliexh;

    /**
     * 是否原厂:0 否,1 是
     */
    private Integer isOriginal;

    /**
     * 规格参数
     */
    private String guigecanshu;

    /**
     * 偏离情况
     */
    private String pianliqk;

    /**
     * 单价/元
     */
    private BigDecimal price;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 备注
     */
    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
