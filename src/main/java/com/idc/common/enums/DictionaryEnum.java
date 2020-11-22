package com.idc.common.enums;

import lombok.Getter;

/**
 * @auther: jijl
 * @Date: Create in 2020/11/15
 * @Description: 数据字段枚举
 **/
@Getter
public enum DictionaryEnum {

    JURISDICTION_MODULAR(1, "模块"),
    JURISDICTION_MENU(2, "菜单"),
    JURISDICTION_BUTTON(3, "按钮"),

    ISFLAG_Y(1, "有效/正常"),
    ISFLAG_N(2, "无效/禁用"),

    USER_ISFLAG_Y(0, "有效/正常"),
    USER_ISFLAG_N(1, "无效/禁用"),


    IS_DEL_N(1, "未删除"),
    IS_DEL_Y(2, "已删除"),

    LOG_SYSTEM(1, "业务日志"),
    LOG_ERROR(2, "异常日志"),

    MANAGER_TYPE_ADMIN(1, "超级管理员"),
    MANAGER_TYPE_GENERAL(2, "普通管理员"),



    //默认密码
    DEFAULT_PASSWORD(0, "123456");




    private int code;

    private String name;

    DictionaryEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
