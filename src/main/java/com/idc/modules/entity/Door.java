package com.idc.modules.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
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
@Accessors(chain = true)
@TableName("hz_door")
public class Door extends Model<Door> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    @TableId(value = "doorId", type = IdType.AUTO)
    private Long doorId;

    /**
     * 设备序列号
     */
    @Excel(name = "设备编号")
    @TableField("equipmentNo")
    private String equipmentNo;

    /**
     * 二维码地址
     */
    @TableField("qrcodeUrl")
    private String qrcodeUrl;

    /**
     * 门店ID
     */
    @Excel(name = "门店编号")
    @TableField("shopId")
    private Long shopId;

    /**
     * 设备状态:【1.正常 2.故障 3.离线 】
     */
    private Integer status;

    /**
     * 模式【0.无人管理模式 1.有人管理模式】
     */
    private Integer mode;

    /**
     * 照明灯开关状态 0：关 1：开  2：未知
     */
    @TableField("lightStatus")
    private Integer lightStatus;

    /**
     * 紫外线灯开关状态 0：关 1：开  2：未知
     */
    @TableField("uvStatus")
    private Integer uvStatus;

    /**
     * 红外栅栏状态，8路红外栅栏信号检测，作为判断是否有人在无人区。0：无人 1：有人  2：未知
     */
    @TableField("infraredStatus")
    private String infraredStatus;

    /**
     * 红外栅栏防拆状态 0：正常 1：拆除  2：未知
     */
    @TableField("infraredTamperStatus")
    private Integer infraredTamperStatus;

    /**
     * 是否启用:[1.启用 2.禁用]
     */
    @TableField("isFlag")
    private Integer isFlag;

    /**
     * 创建时间
     */
    private Date ctime;


    @Override
    protected Serializable pkVal() {
        return this.doorId;
    }

}
