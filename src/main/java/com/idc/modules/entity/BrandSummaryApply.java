package com.idc.modules.entity;

import java.math.BigDecimal;
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
 * 品牌简介-所申报品牌产品应用情况
 * </p>
 *
 * @author Dylan
 * @since 2020-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandSummaryApply extends Model<BrandSummaryApply> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 简介id
     */
    private Integer sumaryId;

    /**
     * 项目名称
     */
    private String xmmc;

    /**
     * 使用时间
     */
    private String sysj;

    /**
     * 产品序列型号
     */
    private String cpxlxh;

    /**
     * 合同金额
     */
    private BigDecimal htprice;

    /**
     * 新增时间
     */
    private Date addTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
