package com.idc.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author jijl
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("hz_shop")
public class Shop extends Model<Shop> {

    private static final long serialVersionUID = 1L;

    /**
     * 门店ID
     */
    @TableId(value = "shopId", type = IdType.AUTO)
    private Long shopId;

    /**
     * 门店编号
     */
    @TableField("shopNo")
    private String shopNo;

    /**
     * 门店名称
     */
    @TableField("shopName")
    private String shopName;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 管理员编号
     */
    @TableField("managerId")
    private String managerId;

    /**
     * 平台分成
     */
    @TableField("platformProportion")
    private Integer platformProportion;

    /**
     * 总代理(一级)分成
     */
    @TableField("oneProportion")
    private Integer oneProportion;

    /**
     * 区域(二级)代理分成
     */
    @TableField("twoProportion")
    private Integer twoProportion;

    /**
     * 门店分成
     */
    @TableField("shopProportion")
    private Integer shopProportion;

    /**
     * 模式:【1.白天模式 2.夜间模式 3.禁用模式】
     */
    private Integer pattern;

    /**
     * 状态:1.启用 2.禁用
     */
    @TableField("isFlag")
    private Integer isFlag;

    /**
     * 创建时间
     */
    private Date ctime;


    @Override
    protected Serializable pkVal() {
        return this.shopId;
    }

}
