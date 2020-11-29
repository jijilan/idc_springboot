package com.idc.modules.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 证明材料15.产品业绩情况
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandBasPerformance extends Model<BrandBasPerformance> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * brand_basic_infor id,品牌商id
     */
    private Integer brandId;

    /**
     * 项目名称
     */
    @JsonProperty("项目名称")
    private String xmmc;

    /**
     * 合同签订日期
     */
    @JsonProperty("合同签订日期")
    private String htqdrq;

    /**
     * 合同金额
     */
    @JsonProperty("合同金额")
    private BigDecimal htprice;

    /**
     * 业绩证明材料
     */
    @JsonProperty("业绩证明材料")
    private String yjzmcl;

    /**
     * 新增时间
     */
    private Date addTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
