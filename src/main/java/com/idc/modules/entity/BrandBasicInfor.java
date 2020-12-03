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
 * 品牌 基础信息表(代理商、品牌商)
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandBasicInfor extends Model<BrandBasicInfor> {

    private static final long serialVersionUID = 1L;
    public BrandBasicInfor(){
        addTime=new Date();
        updateTime=new Date();
    }
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty("id")
    private Integer id;

    /**
     * 1 品牌制造商,2 品牌代理商
     */
    @JsonProperty("品牌商类型")
    private Integer cType;

    /**
     * 品牌(代理商&制造商)名称
     */
    @JsonProperty("品牌(代理商&制造商)名称")
    private String brandName;

    /**
     * 注册资金(万元)
     */
    @JsonProperty("注册资金(万元)")
    private String zhucezijin;

    /**
     * 注册实缴资金(万元)
     */
    @JsonProperty("注册实缴资金(万元)")
    private String zhucesjzijin;

    /**
     * 公司性质-brand_dictionary p_code=A
     */
    @JsonProperty("公司性质)")
    private String gongsixz;

    /**
     * 品牌代理商地址&品牌制造商注册地址
     */
    @JsonProperty("注册地址)")
    private String pinpaidz;

    /**
     * 代理产品&品牌制造商生产地址
     */
    @JsonProperty("生产地址)")
    private String chanpscdz;

    /**
     * 主营产品
     */
    @JsonProperty("主营产品)")
    private String zhuycp;

    /**
     * 材料类别数据
     */
    @JsonProperty("材料类别数据)")
    private String cailliaolb;

    /**
     * 材料类别备用字段
     */
    @JsonProperty("材料类别备用字段)")
    private String cailiaolbJson;

    /**
     * 代理产品&产品品牌名称
     */
    @JsonProperty("产品品牌名称)")
    private String chanpmc;

    /**
     * 品牌logo 图片地址
     */
    @JsonProperty("品牌logo)")
    private String pinplogo;

    /**
     * 公司传值号码
     */
    @JsonProperty("公司传值号码")
    private String chuanzhm;

    /**
     * 代理品牌的品牌制造商名称(品牌代理商才有)
     */
    @JsonProperty("代理品牌的品牌制造商名称")
    private String dailppzzsmc;

    /**
     * 品牌代理&制造商营业执照(0 无,1 有)
     */
    @JsonProperty("是否营业执照")
    private Integer pinpyyzz;

    /**
     * 品牌代理&制造商组织机构代码证(0 无,1 有)
     */
    @JsonProperty("是否组织机构代码证")
    private Integer pinpzzjgdmz;

    /**
     * 品牌代理&制造商税务登记证(0 无,1 有)
     */
    @JsonProperty("是否税务登记证")
    private Integer pinpswdjz;

    /**
     * 品牌代理&制造商三证合一营业执照(0 无,1 有)
     */
    @JsonProperty("是否三证合一营业执照")
    private Integer pinpsanzyyzz;

    /**
     * 品牌代理&制造商三年完税证明(0 无,1 有)
     */
    @JsonProperty("是否三年完税证明")
    private Integer pinpsannwszm;

    @JsonProperty("申报材料真实性承诺书")
    private String chengns;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
