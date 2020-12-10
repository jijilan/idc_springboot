package com.idc.modules.entity.excle;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Auther Dyaln
 * @Date 2020/12/10
 */
@Data
public class BrandCountExcle {
    BrandCountExcle(){
        title="申报品牌数量";
    }
    @Excel(name="-----",width = 20D)
    private String title;
    @Excel(name="品牌制造商(家)",width = 20D)
    private String macNum;
    @Excel(name="品牌代理商(家)",width = 20D)
    private String agentNum;
    @Excel(name="合计",width = 20D)
    private String num;
}
