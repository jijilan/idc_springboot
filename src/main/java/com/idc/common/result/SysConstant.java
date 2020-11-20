package com.idc.common.result;

/**
 * @Author: jijl
 * @Description: 系统公共常量
 * @Date: 2020/7/2 16:54
 **/
public class SysConstant {

    public static final String PROJECT_NAME = "HZNOBODYSHOP_";

    public static final String DOOR_EQUIPMENT_NO = "DOOR";

    public static final String GASHAPON_EQUIPMENT_NO = "GASHAPON";

    /**
     * 权限前缀
     */
    public static final String AUTHORITY = PROJECT_NAME + "authority_";

    public static final String TOKEN = "Authorization";

    public static final String USER = "user";

    public static final String USER_ID = "userId";

    public static final String USER_NO = "userNo";

    public static final String MANAGER = "manager";

    public static final String MANAGER_ID = "managerId";

    public static final String OPEN_ID = "openId";

    /**
     * 用户登录过期时间
     */
    public static final long USER_AUTH_TIMEOUT = 604800000;

    /**
     * 管理员授权过期时间
     */
    public static final long ADMIN_AUTH_TIMEOUT = 259200000;


    public static final String EQUIPMENT_DOOR = "0";

    public static final String EQUIPMENT_GASHAPON = "1";

    public static class Table {
        public static final String MANAGER_ID_TOP = "MAN";
        public static final String SHOP_ID_TOP = "SHO";
        public static final String ROLE_ID_TOP = "ROL";
        public static final String MENU_ID_TOP = "MEN";
        public static final String USER_ID_TOP = "USR";
        public static final String ORDER_CASH_ID_TOP = "OCA";
        public static final String ORDER_CONSUME_ID_TOP = "OCO";
        public static final String HOTEL_ID_TOP = "HOT";
        public static final String FINANCE_ID_TOP = "FIN";
        public static final String ORDER_OUTTRADE_NO_TOP = "1001";
    }

    public static class Redis {
        public final static String MESSAGE1 = "hznobodyshop_message1";
        public final static String MESSAGE2 = "hznobodyshop_message2";
    }
}
