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
 * 品牌 内部人员信息表(法人代表、销售负责人、销售业务人员、其他业务人员)
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandPerson extends Model<BrandPerson> {

    private static final long serialVersionUID = 1L;
    public BrandPerson(){
        addTime=new Date();
    }
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 企业基础信息id
     */
    @JsonProperty("企业基础信息id")
    private Integer brandId;

    /**
     * 人员类型 1 法人代表,2 销售负责人,3 销售业务人员,4 其他业务人员
     */
    @JsonProperty("人员类型 1 法人代表,2 销售负责人,3 销售业务人员,4 其他业务人员")
    private Integer pType;

    /**
     * 用户名称
     */
    @JsonProperty("用户名称")
    private String pName;

    /**
     * 岗位名称
     */
    @JsonProperty("岗位名称")
    private String pPost;

    /**
     * 身份证号码
     */
    @JsonProperty("身份证号码")
    private String pIdcard;

    /**
     * 社保号码
     */
    @JsonProperty("社保号码")
    private String pSocialCode;

    /**
     * 手机号
     */
    @JsonProperty("手机号")
    private String pPhoneNum;

    /**
     * 邮箱
     */
    @JsonProperty("邮箱")
    private String pMailBox;

    /**
     * 添加时间
     */
    private Date addTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
