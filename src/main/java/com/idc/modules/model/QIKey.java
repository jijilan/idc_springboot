package com.idc.modules.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @auther: jijl
 * @Date: Create in 2020/10/21
 * @Description: Integer 类型的主键
 **/
@Getter
@Setter
public class QIKey {

    @NotNull(message = "主键编号不能为空")
    private Integer key;

}
