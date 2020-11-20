package com.idc.modules.entity.sub;


import lombok.Data;

import java.util.List;

/**
 * @auther: jijl
 * @Date: Create in 2019/2/22
 * @Description:
 **/
@Data
public class SysManagerSub {

    private String managerId;

    private Integer managerLevel;

    private String superId;

    private List<SysManagerSub> sysManagerSubList;
}
