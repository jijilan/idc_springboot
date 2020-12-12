package com.idc.modules.entity.excle;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Auther Dyaln
 * @Date 2020/12/11
 */
@Data
public class EnterpriseInforExcle {
    @Excel(name="申报企业名称",width = 20D)
    private String brandName;
    @Excel(name="申报品牌名称",width = 20D)
    private String chanpmc;
    @Excel(name="注册地址",width = 20D)
    private String pinpaidz;
    @Excel(name="公司性质",width = 20D)
    private String qiyxz;
    @Excel(name="注册资金(万元)",width = 20D)
    private String zhucezijin;
    @Excel(name="注册实缴资金(万元)",width = 20D)
    private String zhucesjzijin;
    @Excel(name="企业类型",width = 20D)
    private String typeName;
    @Excel(name="法人代表",width = 20D)
    private String farName;
    @Excel(name="法人手机号码",width = 20D)
    private String farPhoneNum;
    @Excel(name="销售负责人",width = 20D)
    private String xiaosName;
    @Excel(name="销售负责人手机号码",width = 20D)
    private String xiaosPhoneNum;
    @Excel(name="制造工厂地址",width = 20D)
    private String chanpscdz;
}
