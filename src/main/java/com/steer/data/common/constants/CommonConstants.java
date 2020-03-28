package com.steer.data.common.constants;

/**
 * Created by ace on 2017/8/29.
 */
public class CommonConstants {

    public static final String REDIS_ENABLED="redis_enabled";//redis是否存活
    public static final String ENABLED = "enabled";//存活
    public static final String DISABLED = "disabled";//不存活，关闭。


    public static final String FAIL = "500";
    public static final String SUCCESS = "200";
    //补偿次数
    public static final Integer RETRY_COUNT = 3;
    //第一次补偿时长ms  10s
    public static final Integer RETRY_FIRST = 10000;
    //第二次补偿时长ms  30s
    public static final Integer RETRY_SECOND = 30000;
    //第三次补偿时长ms  180s
    public static final Integer RETRY_THIRD = 180000;

    public final static String RESOURCE_TYPE_MENU = "menu";
    public final static String RESOURCE_TYPE_BTN = "button";
    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 40101;
    public static final Integer EX_USER_PASS_INVALID_CODE = 40001;
    // 客户端token异常
    public static final Integer EX_CLIENT_INVALID_CODE = 40301;
    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40331;
    public static final Integer EX_OTHER_CODE = 500;
    public static final String CONTEXT_KEY_USER_ID = "currentUserId";
    public static final String CONTEXT_KEY_USERNAME = "currentUserName";
    public static final String CONTEXT_KEY_USER_NAME = "currentUser";
    public static final String CONTEXT_KEY_USER_TOKEN = "currentUserToken";
    public static final String JWT_KEY_USER_ID = "userId";
    public static final String JWT_KEY_NAME = "name";
}
