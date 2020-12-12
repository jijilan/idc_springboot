package com.idc.modules.entity.excle;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Auther Dyaln
 * @Date 2020/12/12
 */
@Data
public class BrandSummaryInforExcel {
    @Excel(name="申报企业名称",width = 20D)
    private String brandName;
    @Excel(name="品牌制造商成立年限",width = 20D)
    private String chenglnx;
    @Excel(name="注册实缴资金",width = 20D)
    private String zhucesjzijin;
    @Excel(name="营业收入",width = 20D)
    private String yingysr;
    @Excel(name="生产基地建筑面积",width = 20D)
    private String jdzjzmj;
    @Excel(name="质量管理体系认证",width = 20D)
    private String txyxsc;
    @Excel(name="品牌市场占有率",width = 20D)
    private String sczyl;
    @Excel(name="拟入库品牌产品相关专利证书#发明专利",width = 20D)
    private String zlfamnum;
    @Excel(name="拟入库品牌产品相关专利证书#新型应用",width = 20D)
    private String zlxyynum;
    @Excel(name="拟入库品牌产品相关专利证书#外观",width = 20D)
    private String zlxxnum;
    @Excel(name="拟入库品牌产品相关专利证书#合计",width = 20D)
    private String fmnum;
    @Excel(name="拟入库品牌产品获奖情况#国家级",width = 20D)
    private String hjgjnum;
    @Excel(name="拟入库品牌产品获奖情况#省部级",width = 20D)
    private String gjsjnum;
    @Excel(name="拟入库品牌产品获奖情况#市级",width = 20D)
    private String gjshijnum;
    @Excel(name="拟入库品牌产品获奖情况#合计",width = 20D)
    private String hjnum;
    @Excel(name="售后服务机构地理位置",width = 20D)
    private String dlwz;
    @Excel(name="售后响应时间",width = 20D)
    private String shxysj;
    @Excel(name="免费维保期",width = 20D)
    private String mfwbq;
}
