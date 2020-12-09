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
 * 证明材料 1(基本信息证明）、2（近三年营收情况）、5（履约评价情况）
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandBasRevPerf extends Model<BrandBasRevPerf> {

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
     * 1.营业执照
     */
    @JsonProperty("营业执照")
    private String yingyezz;

    /**
     * 1.营业执照副本
     */
    @JsonProperty("营业执照副本")
    private String yingyezzfb;

    /**
     * 1.注册资金实缴证明
     */
    @JsonProperty("注册资金实缴证明")
    private String zhuczjsjzm;

    /**
     * 1.组织机构代码
     */
    @JsonProperty("组织机构代码")
    private String zuzjgdm;

    /**
     * 1.税务登记证
     */
    @JsonProperty("税务登记证")
    private String shuiwdjz;

    /**
     * 1.代理商授权证明材料
     */
    @JsonProperty("代理商授权证明材料")
    private String dailssqzmcl;

    /**
     * 1.企业成立年限
     */
    @JsonProperty("企业成立年限")
    private int chenglnx;
    /**
     * 2.营业收入(上一年)
     */
    @JsonProperty("营业收入(上一年)")
    private BigDecimal yingysrOne;

    /**
     * 2.营业财务报表(上一年)
     */
    @JsonProperty("营业财务报表(上一年)")
    private String yingycwbbOne;

    /**
     * 2.营业收入(上二年)
     */
    @JsonProperty("营业收入(上二年)")
    private BigDecimal yingysrTwo;

    /**
     * 2.营业财务报表(上二年)
     */
    @JsonProperty("营业财务报表(上二年)")
    private String yingycwbbTwo;

    /**
     * 2.营业收入(上三年)
     */
    @JsonProperty("营业收入(上三年)")
    private BigDecimal yingysrThree;

    /**
     * 2.营业财务报表(上三年)
     */
    @JsonProperty("营业财务报表(上三年)")
    private String yingycwbbThree;

    /**
     * 5.履约评价情况佐证材料(上一年)
     */
    @JsonProperty("履约评价情况佐证材料(上一年)")
    private String lvyqkOne;

    /**
     * 5.履约评价情况佐证材料(上二年)
     */
    @JsonProperty("履约评价情况佐证材料(上二年)")
    private String lvyqkTwo;

    /**
     * 5.履约评价情况佐证材料(上三年)
     */
    @JsonProperty("履约评价情况佐证材料(上三年)")
    private String lvyqkThree;

    /**
     * 添加时间
     */
    private Date addTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
