package com.idc.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 证明材料10.拟入库品牌产品获奖情况
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandBasAwarded extends Model<BrandBasAwarded> {

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
     * 获奖项目
     */
    @JsonProperty("获奖项目")
    private String huojxm;

    /**
     * 获奖产品
     */
    @JsonProperty("获奖产品")
    private String huojcp;

    /**
     * 获奖级别 字典表 p_code=C02
     */
    @JsonProperty("获奖级别")
    private String huojjb;

    /**
     * 获奖证明图片
     */
    @JsonProperty("获奖证明图片")
    private String huojzmImg;

    /**
     * 添加时间
     */
    private Date addTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
