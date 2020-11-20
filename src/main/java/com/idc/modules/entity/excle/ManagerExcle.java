package com.idc.modules.entity.excle;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.idc.modules.entity.SysManager;
import lombok.Data;


@Data
public class ManagerExcle extends SysManager {
    @Excel(name = "总营业额", orderNum = "7", width = 25)
    private String num;
    private String sums1;
    private String sums2;
}
