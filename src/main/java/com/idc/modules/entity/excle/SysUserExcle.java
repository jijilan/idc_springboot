package com.idc.modules.entity.excle;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Auther Dyaln
 * @Date 2020/12/14
 */
@Data
public class SysUserExcle {
    SysUserExcle(){
        zhanghlx="注册账号";
    }
    private int id;
    @Excel(name="账号类型",width = 20D)
    private String zhanghlx;

    @Excel(name="注册企业类型",width = 20D)
    private String typeName;

    @Excel(name="企业名",width = 20D)
    private String brandName;

    @Excel(name="账号",width = 20D)
    private String userName;

    @Excel(name="注册时间",width = 20D)
    private String createTime;

    @Excel(name="账号状态",width = 20D)
    private String useactive;
}
