package com.idc.modules.entity.excle;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Auther Dyaln
 * @Date 2020/12/10
 */
@Data
public class BrandInforListExcle {
    private int id;
    @Excel(name="申报企业类型",width = 20D)
    private String cname;
    @Excel(name="申报企业",width = 20D)
    private String brandname;
    @Excel(name="公司性质",width = 20D)
    private String qyxzname;
    @Excel(name="品牌名称",width = 20D)
    private String chanpmc;
    @Excel(name="法人代表",width = 20D)
    private String pname;
    @Excel(name="申报状态",width = 20D)
    private String complete;
    @Excel(name="申报时间",width = 20D)
    private String addtime;

}
