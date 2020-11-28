package com.idc.modules.entity;

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
 * 品牌 制造商、代理商 简介表
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandSummary extends Model<BrandSummary> {

    private static final long serialVersionUID = 1L;
    public BrandSummary(){
        addTime=new Date();
    }
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty("id")
    private Integer id;

    /**
     * 品牌基础id
     */
    @JsonProperty("品牌基础id")
    private Integer brandId;

    /**
     * 品牌制造商简介
     */
    @JsonProperty("品牌制造商简介")
    private String overview;

    /**
     * 主营产品
     */
    @JsonProperty("主营产品")
    private String zhuycp;

    /**
     * 其他信息
     */
    @JsonProperty("其他信息")
    private String qitaxinxi;

    /**
     * 其他附件
     */
    @JsonProperty("其他附件")
    private String qitafujian;

    /**
     * 添加时间
     */
    @JsonProperty("添加时间")
    private Date addTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
