package com.idc.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Dylan
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandDictionary extends Model<BrandDictionary> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级编码
     */
    private String pCode;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 排序字段
     */
    private Integer bySort;

    /**
     * 当前时间
     */
    private Date addTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
