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
 * 系统参数表
 * </p>
 *
 * @author jijl
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("hz_param_sys")
public class ParamSys extends Model<ParamSys> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统参数id
     */
    @TableId(value = "sysParamId", type = IdType.AUTO)
    private Long sysParamId;

    /**
     * 参数-文本类型
     */
    @TableField("paramSecond")
    private String paramSecond;

    /**
     * 参数类型（1:轮播图广告 2.新手教程 3.用户协议 4.关于我们 5.客服电话 6.使用流程 7.加盟广告 8.项目LOGO 9.项目名称 10.加盟电话 11.紫外线灯时间段设置）
     */
    @TableField("sysType")
    private Integer sysType;

    /**
     * 描述
     */
    @TableField("sysDesc")
    private String sysDesc;

    /**
     * 是否可用（1:是  2:否）
     */
    @TableField("isFlag")
    private Integer isFlag;

    /**
     * 创建时间
     */
    private Date ctime;


    @Override
    protected Serializable pkVal() {
        return this.sysParamId;
    }

}
