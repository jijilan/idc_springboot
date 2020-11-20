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

    IS_DEL_N(1, "未删除"),
    IS_DEL_Y(2, "已删除"),

    LOG_SYSTEM(1, "业务日志"),
    LOG_ERROR(2, "异常日志"),

    MANAGER_TYPE_ADMIN(1, "超级管理员"),
    MANAGER_TYPE_GENERAL(2, "普通管理员"),

    MANAGER_LEVLE_0(0, "平台"),
    MANAGER_LEVLE_1(1, "平台直营"),
    MANAGER_LEVLE_2(2, "一级代理(总)"),
    MANAGER_LEVLE_3(3, "二级代理(区)"),
    MANAGER_LEVLE_4(4, "门店代理"),
    SHOP_TYPE_0(0, "直营"),
    SHOP_TYPE_1(1, "门店代理"),


    //一级代理(总)
    SYS_ROLE_ID_1(1, "ROL266812021980176"),
    //二级代理(区)
    SYS_ROLE_ID_2(2, "ROL266812021432444"),
    //门店代理
    SYS_ROLE_ID_3(3, "ROL266812021333333"),
    //默认密码
    DEFAULT_PASSWORD(0, "123456"),

    PARTNER_STAUTS_AUDIT(1, "审核中"),
    PARTNER_STAUTS_ADOPT(2, "已通过"),
    PARTNER_STAUTS_REJECT(3, "已驳回"),

    PAY_STATUS_N(1, "未支付"),
    PAY_STATUS_Y(2, "已支付"),

    ORDER_STATUS_COMPLETE(1, "已完成"),
    ORDER_STATUS_REFUND(2, "已退款"),

    PAY_CHANNEL_WX(1, "微信"),
    PAY_CHANNEL_ALI(2, "支付宝"),


    SHOP_CHARGE_RULE_CATEGORYID(1, "扭蛋机"),
    SHOP_CHARGE_RULE_RULEKEY(1, "按次"),


    EQUEMENT_TYPE_0(0, "门禁"),
    EQUEMENT_TYPE_1(1, "扭蛋机"),

    FINANCE_TYPE_PROFIT(1, "收益"),
    FINANCE_TYPE_EXPENDITURE(2, "支出"),
    FINANCE_TYPE_CASH(3, "提现"),
    FINANCE_TYPE_RETURN_CASH(4, "提现返还"),

    DOOR_LIGHTSTATUS_UVSTATUS_INFRAREDSTATUS_INFRAREDTAMPERSTATUS_0(0, "关/无人/正常"),
    DOOR_LIGHTSTATUS_UVSTATUS_INFRAREDSTATUS_INFRAREDTAMPERSTATUS_1(1, "开/有人/拆除"),
    DOOR_LIGHTSTATUS_UVSTATUS_INFRAREDSTATUS_INFRAREDTAMPERSTATUS_2(2, "未知"),

    SHOP_PATTERN_1(1, "白天/有人模式"),
    SHOP_PATTERN_2(2, "夜间/无人模式"),
    SHOP_PATTERN_3(3, "禁入模式"),

    DOOR_MODE_0(0, "无人管理模式"),
    DOOR_MODE_1(1, "有人管理模式"),

    DOOR_LIGHT_STATUS_0(0, "关"),
    DOOR_LIGHT_STATUS_1(1, "开"),


    EQUIPMENT_STATUS_NORMAL(1, "正常"),
    EQUIPMENT_STATUS_ERROR(2, "故障"),
    EQUIPMENT_STATUS_FAU(3, "离线"),

    WX_SP(1, "WX_SP"),

    GASHAPON_BINDINGNUM(6, "扭蛋机规格6个");


    private int code;

    private String name;

    DictionaryEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
