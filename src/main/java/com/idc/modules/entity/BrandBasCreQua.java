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
 * 证明材料3（企业信用）、4（产品质量）、6（建筑面积）、7（管理体系认证）、8(市场占有率)
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandBasCreQua extends Model<BrandBasCreQua> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * brand_basic_infor id,品牌商id
     */
    @JsonProperty("品牌商id")
    private Integer brandId;

    /**
     * 3.企业信用-是否收到行政处罚: 0 否,1 是
     */
    @JsonProperty("是否收到行政处罚")
    private Integer xyxzcf;

    /**
     * 3.企业信用-是否收到行政处罚图片
     */
    @JsonProperty("是否收到行政处罚图片")
    private String xyxzcfImg;

    /**
     * 3.企业信用-是否被列入经营异常名录:0 否,1 是
     */
    @JsonProperty("是否被列入经营异常名录")
    private Integer xyjyycml;

    /**
     * 3.企业信用-是否被列入经营异常名录图片
     */
    @JsonProperty("是否被列入经营异常名录图片")
    private String xyjyycmlImg;

    /**
     * 3.企业信用-是否被列入严重失信企业名单:0否,1是
     */
    @JsonProperty("是否被列入严重失信企业名单")
    private Integer xyyzsxqymd;

    /**
     * 3.企业信用-是否被列入严重失信企业名单图片
     */
    @JsonProperty("是否被列入严重失信企业名单图片")
    private String xyyzsxqymdImg;

    /**
     * 4.产品质量-品牌产品合格证明
     */
    @JsonProperty("品牌产品合格证明")
    private String zlhgzm;

    /**
     * 4.产品质量-检测报告
     */
    @JsonProperty("检测报告")
    private String zljcbg;

    /**
     * 4.产品质量-质量承诺书
     */
    @JsonProperty("质量承诺书")
    private String zjzlcns;

    /**
     * 6.生产基地-总建筑面积
     */
    @JsonProperty("总建筑面积")
    private BigDecimal jdzjzmj;

    /**
     * 6.生产基地-佐证材料
     */
    @JsonProperty("佐证材料")
    private String jdzzcl;

    /**
     * 7.管理体系-管理体系认证复印件
     */
    @JsonProperty("管理体系认证复印件")
    private String txrzfyj;

    /**
     * 7.管理体系-开始运行时间
     */
    @JsonProperty("开始运行时间")
    private String txksyxsj;

    /**
     * 7.管理体系-运行时长
     */
    @JsonProperty("管理体系-运行时长")
    private Integer txyxsc;

    /**
     * 7.管理体系-佐证材料
     */
    @JsonProperty("管理体系-佐证材料")
    private String txzzcl;

    /**
     * 8.市场占有率-市场占有率
     */
    @JsonProperty("市场占有率")
    private BigDecimal sczyl;

    /**
     * 8.市场占有率-佐证材料
     */
    @JsonProperty("市场占有率-佐证材料")
    private String sczzcl;

    /**
     * 添加时间
     */
    private Date addTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
