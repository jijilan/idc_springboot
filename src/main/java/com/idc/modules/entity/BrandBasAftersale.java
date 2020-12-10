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
 * 证明材料11（售后服务机构地理位置）,12（售后响应时间）,13（免费维保期）,16（售后方案）,17（申办材料真实性承诺书）
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandBasAftersale extends Model<BrandBasAftersale> {

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
     * 11.售后服务机构地理位置-地理位置
     */
    @JsonProperty("售后服务机构地理位置-地理位置")
    private String dlwz;

    /**
     * 11.售后服务机构地理位置-地理位置佐证材料
     */
    @JsonProperty("售后服务机构地理位置-地理位置佐证材料")
    private String dlwzzzcl;

    /**
     * 12.售后响应时间-售后响应时间
     */
    @JsonProperty("售后响应时间")
    private Integer shxysj;

    /**
     * 12.售后响应时间-承诺书
     */
    @JsonProperty("承诺书")
    private String shxysjCns;

    /**
     * 13.免费维保期-免费维保期
     */
    @JsonProperty("免费维保期")
    private String mfwbq;

    /**
     * 13.免费维保期-佐证材料
     */
    @JsonProperty("佐证材料")
    private String mfwbqZzcl;

    /**
     * 16.售后方案
     */
    @JsonProperty("售后方案")
    private String shfa;

    /**
     * 17.申报材料真实性承诺书
     */
    @JsonProperty("申报材料真实性承诺书")
    private String sbclzsx;

    /**
     * 新增时间
     */
    private Date addTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
