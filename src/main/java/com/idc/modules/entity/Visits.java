package com.idc.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
@TableName("hz_visits")
public class Visits extends Model<Visits> {

    private static final long serialVersionUID = 1L;

    /**
     * 浏览ID
     */
    @TableId(value = "visitsId", type = IdType.AUTO)
    private Long visitsId;

    /**
     * 设备分类名称
     */
    @TableField("categoryName")
    private String categoryName;

    /**
     * 设备ID
     */
    @TableField("equipmentId")
    private Long equipmentId;

    /**
     * 设备序列号
     */
    @TableField("equipmentNo")
    private String equipmentNo;

    /**
     *  用户ID
     **/
    @TableField("userId")
    private Long userId;


    /**
     * 用户昵称
     */
    @TableField("nickName")
    private String nickName;

    /**
     * 性别:0.未知 1.男 2.女
     */
    private Integer gender;

    /**
     * 客户端类型【1.微信 2.支付宝】
     */
    @TableField("clientType")
    private Integer clientType;

    /**
     * 门店名称
     */
    @TableField("shopName")
    private String shopName;

    /**
     * 门店地址
     */
    @TableField("shopAddress")
    private String shopAddress;

    /**
     * 负责人ID
     */
    @TableField("managerId")
    private String managerId;

    /**
     * 负责人名称
     */
    @TableField("managerName")
    private String managerName;

    /**
     * 负责人手机
     */
    @TableField("managerPhone")
    private String managerPhone;

    /**
     * 创建时间
     */
    private Date ctime;


    @Override
    protected Serializable pkVal() {
        return this.visitsId;
    }

}
