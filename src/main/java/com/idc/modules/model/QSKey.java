package com.idc.modules.model;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * @auther: jijl
 * @Date: Create in 2020/10/10
 * @Description: String 类型的主键
 **/
@Getter
@Setter
public class QSKey {

    @NotBlank(message = "主键编号不能为空")
    private String key;

}
