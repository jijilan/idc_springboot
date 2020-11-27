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
 * 证明材料9：拟入库品牌产品相关的专利证书提供专利复印件
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandBasPatent extends Model<BrandBasPatent> {

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
     * 专利名称
     */
    private String zhlmc;

    /**
     * 专利类型 字典表 p_code=C01
     */
    private String zhllx;

    /**
     * 专利图片
     */
    private String zhlImg;

    /**
     * 添加时间
     */
    private Date addTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
