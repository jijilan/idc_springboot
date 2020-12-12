package com.idc.modules.entity.excle;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Auther Dyaln
 * @Date 2020/12/12
 */
@Data
public class BrandProductInforListExcle {
    @Excel(name="申报企业名称",width = 20D)
    private String brandName;
    @Excel(name="规格参数(仅供参考)",width = 30D)
    private String shuomsl;
    @Excel(name="品牌",width = 20D)
    private String pinpai;
    @Excel(name="序列型号",width = 20D)
    private String xuliexh;
    @Excel(name="是否原厂",width = 20D)
    private String isOriginal;
    @Excel(name="规格参数",width = 20D)
    private String guigecanshu;
    @Excel(name="偏离情况",width = 20D)
    private String pianliqk;
    @Excel(name="单价/元",width = 20D)
    private String price;
    @Excel(name="折扣率",width = 20D)
    private String discount;
    @Excel(name="备注",width = 20D)
    private String remark;

}
