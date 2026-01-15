package com.example.courses.music.util;

/**
 * 全局常量类
 */
public class Constant {
    /**
     * 接口调用太频繁了
     */
    public static final int ERROR_TOO_FAST = 50;
    public static final String ERROR_TOO_FAST_MESSAGE = "接口调用太频繁了，请稍后再试";

    /**
     * 参数签名错误
     */
    public static final int ERROR_PARAMS_SIGN = 70;
    public static final String ERROR_PARAMS_SIGN_MESSAGE = "参数签名错误";

    /**
     * 参数签名错误
     */
    public static final int ERROR_PARAMS_DECRYPT = 70;
    public static final String ERROR_PARAMS_DECRYPT_MESSAGE = "参数解密错误";


    /**
     * 没有参数签名
     */
    public static final int ERROR_PARAMS_NOT_SIGN = 80;
    public static final String ERROR_PARAMS_NOT_SIGN_MESSAGE = "没有参数签名";

    /**
     * 保存数据失败错误
     */
    public static final int ERROR_SAVE_DATA = 1540;
    public static final String ERROR_SAVE_DATA_MESSAGE = "保存数据失败";

    /**
     * 用户名或密码错误
     */
    public static final int ERROR_USERNAME_OR_PASSWORD = 1030;
    public static final String ERROR_USERNAME_OR_PASSWORD_MESSAGE = "用户名或密码错误";


    /**
     * 邮箱已经验证，请勿重复操作！
     */
    public static final int ERROR_EMAIL_ALREADY_CONFIRM = 1080;
    public static final String ERROR_EMAIL_ALREADY_CONFIRM_MESSAGE = "邮箱已经验证，请勿重复操作";


    /**
     * 取消关注失败
     */
    public static final int ERROR_CANCEL_FOLLOW = 5020;
    public static final String ERROR_CANCEL_FOLLOW_MESSAGE = "取消关注失败";

    /**
     * 订单状态错误
     */
    public static final int ERROR_ORDER_STATUS = 1140;
    public static final String ERROR_ORDER_STATUS_MESSAGE = "订单状态错误！";

    /**
     * 支付渠道错误
     */
    public static final int ERROR_ORDER_PAY_CHANNEL = 1150;
    public static final String ERROR_ORDER_PAY_CHANNEL_MESSAGE = "支付渠道错误！";

    /**
     * 支付来源错误
     */
    public static final int ERROR_ORDER_PAY_ORIGIN = 1160;
    public static final String ERROR_ORDER_PAY_ORIGIN_MESSAGE = "支付来源错误！";


    /**
     * 未知错误
     */
    public static final int ERROR_UNKNOWN = 20;

    public static final String ERROR_UNKNOWN_MESSAGE = "服务端未知错误";

    /**
     * 参数错误
     */
    public static final int ERROR_ARGUMENT = 30;

    public static final String ERROR_ARGUMENT_MESSAGE = "请求参数错误";

    /**
     * 资源不存在错误
     */
    public static final int ERROR_NOT_FOUND = 404;

    public static final String ERROR_NOT_FOUND_MESSAGE = "资源不存在";

    /**
     * 数据状态错误
     */
    public static final int ERROR_DATA_STATUS = 50;
    public static final String ERROR_DATA_STATUS_MESSAGE = "数据状态错误，请调用正确的接口，或者确保有权限操作";

    /**
     * 第三方服务错误
     */
    public static final int ERROR_THIRD_PARTY_SERVICE = 60;
    public static final String ERROR_THIRD_PARTY_SERVICE_MESSAGE = "第三方服务错误，详细的请查看扩展错误信息";

    /**
     * 用户不存在
     */
    public static final int ERROR_USER_NOT_FOUND = 1540;

    public static final String ERROR_USER_NOT_FOUND_MESSAGE = "用户不存在";

    /**
     * 数据已经存在错误
     */
    public static final int ERROR_DATA_EXIST = 1550;
    public static final String ERROR_DATA_EXIST_MESSAGE = "数据已经存";

    /**
     * 账号被禁用
     */
    public static final int ERROR_DISABLE = 1560;
    public static final String ERROR_DISABLE_MESSAGE = "账户已被禁用";

    /**
     * 手机号已经存在
     */
    public static final int ERROR_EXIST_PHONE = 1570;
    public static final String ERROR_EXIST_PHONE_MESSAGE = "手机号已经存在";

    /**
     * 邮箱已经存在
     */
    public static final int ERROR_EXIST_EMAIL = 1580;
    public static final String ERROR_EXIST_EMAIL_MESSAGE = "邮箱已经存在";

    /**
     * 加盐格式化字符串
     */
    public static final String FORMAT_SALT = "wt5j1URZ1H6RDtt%suWg7x2E0Mr5Xwzm";

    public static final int ERROR_UNAUTHORIZED = 401;
    public static final String ERROR_UNAUTHORIZED_MESSAGE = "请登陆";


    public static final int ERROR_FORBIDDEN = 403;
    public static final String ERROR_FORBIDDEN_MESSAGE = "没有权限";

    public static final int ERROR_METHOD = 405;
    public static final String ERROR_METHOD_MESSAGE = "请求方式错误";

    public static final int ERROR_DATABASE = 410;
    public static final String ERROR_DATABASE_MESSAGE = "服务端数据库错误";

    public static final int ERROR_DATABASE_FOREIGN_KEY = 420;
    public static final String ERROR_DATABASE_FOREIGN_KEY_MESSAGE = "服务端数据不符合数据库完整性约束";

    public static final int ERROR_NO_DATA = 430;
    public static final String ERROR_NO_DATA_MESSAGE = "没有数据";

    public static final int ERROR_CREATE_CHAT_TOKEN = 440;
    public static final String ERROR_CREATE_CHAT_TOKEN_MESSAGE = "服务端获取聊天Token失败，请联系课程客服解决后再试";

    public static final int ERROR_HAVE_RECEIVED_COUPON = 7000;
    public static final String ERROR_HAVE_RECEIVED_COUPON_MESSAGE = "已经领取了";

    /**
     * 优惠券不存在
     * <p>
     * 例如：客户端恶意传优惠券id；或者选择的时候还有效，提交订单时，失效了
     */
    public static final int ERROR_COUPON_NOT_EXIST = 7010;
    public static final String ERROR_COUPON_NOT_EXIST_MESSAGE = "优惠券不存在";

    /**
     * 优惠券不可用
     * <p>
     * 例如：不满足订单使用条件
     */
    public static final int ERROR_COUPON_NOT_AVAILABLE = 7020;
    public static final String ERROR_COUPON_NOT_AVAILABLE_MESSAGE = "优惠券不可用";

    public static final int ERROR_INIT_LIVE = 8000;
    public static final String ERROR_INIT_LIVE_MESSAGE = "服务端初始化直播SDK失败";

    public static final int ERROR_LIVE_TOKEN = 8010;
    public static final String ERROR_LIVE_TOKEN_MESSAGE = "服务端获取直播Token失败";

    public static final int ERROR_LIVE_CREATE_ROOM = 8020;
    public static final String ERROR_LIVE_CREATE_ROOM_MESSAGE = "服务端创建直播间失败";

    public static final int ERROR_LIVE_ALREADY_ROOM = 8030;
    public static final String ERROR_LIVE_ALREADY_ROOM_MESSAGE = "直播间已经存在，不能再次创建";

    public static final int ERROR_LIVE_DELETE_ROOM = 8040;
    public static final String ERROR_LIVE_DELETE_ROOM_MESSAGE = "服务端删除直播间失败";

    /**
     * JSON序列化错误
     */
    public static final int ERROR_TO_JSON = 10000;
    public static final String ERROR_TO_JSON_MESSAGE = "JSON序列化错误";

    /**
     * JSON返序列化错误
     */
    public static final int ERROR_PARSE_JSON = 10010;
    public static final String ERROR_PARSE_JSON_MESSAGE = "JSON返序列化错误";


    /**
     * JSON返序列化错误
     */
    public static final int ERROR_CODE_COUNT_LIMIT = 11000;
    public static final String ERROR_CODE_COUNT_LIMIT_MESSAGE = "每个帐号每天只能发送5条";

    /**
     * 验证码错误
     */
    public static final int ERROR_CODE = 1070;
    public static final String ERROR_CODE_MESSAGE = "验证码错误";

    /**
     * 更改和密码和现在的密码一样
     */
    public static final int ERROR_USE_LAST_PASSWORD = 1100;
    public static final String ERROR_USE_LAST_PASSWORD_MESSAGE = "不能更改为上次的密码";

    /**
     * 验证码不能为空
     */
    public static final int ERROR_CODE_EMPTY = 1110;
    public static final String ERROR_CODE_EMPTY_MESSAGE = "验证码不能为空";

    /**
     * 已经收藏了
     */
    public static final int ERROR_ALREADY_COLLECT = 1520;
    public static final String ERROR_ALREADY_COLLECT_MESSAGE = "已经收藏了";

    /**
     * 搜索失败
     */
    public static final int ERROR_SEARCH = 2020;
    public static final String ERROR_SEARCH_MESSAGE = "搜索失败";

    /**
     * 更新搜索数据库失败
     */
    public static final int ERROR_UPDATE_SEARCH = 2030;
    public static final String ERROR_UPDATE_SEARCH_MESSAGE = "更新搜索索引失败";

    /**
     * 搜索失败
     */
    public static final int ERROR_DELETE_SEARCH = 2040;
    public static final String ERROR_DELETE_SEARCH_MESSAGE = "删除搜索索引失败";

    /**
     * 批量操作搜索索引失败
     */
    public static final int ERROR_BULK_SEARCH = 2040;
    public static final String ERROR_BULK_SEARCH_MESSAGE = "批量操作搜索索引失败";

    /**
     * 文件上传错误
     */
    public static final int ERROR_UPLOAD = 2050;
    public static final String ERROR_UPLOAD_MESSAGE = "文件上传错误";

    /**
     * 逗号分隔符
     */
    public static final String SEPARATOR_COMMA = ",";

    //region 订单状态
    /**
     * 待支付
     */
    public static final int WAIT_PAY = 0;

    /**
     * 订单关闭
     */
    public static final int CLOSE = 10;

    /**
     * 待发货
     */
    public static final int WAIT_SHIPPED = 500;

    /**
     * 待收货
     */
    public static final int WAIT_RECEIVED = 510;

    /**
     * 待评价
     */
    public static final int WAIT_COMMENT = 520;

    /**
     * 完成
     */
    public static final int COMPLETE = 530;
    //endregion

    //支付渠道
    /**
     * 没有支付渠道
     * 这种情况是，管理员手动
     * 将一个订单改为支付状态
     */
    public static final int PAY_CHANNEL_DEFAULT = 0;

    /**
     * 支付宝
     */
    public static final int PAY_CHANNEL_ALIPAY = 10;

    /**
     * 微信
     */
    public static final int PAY_CHANNEL_WECHAT = 20;

    /**
     * 余额
     */
    public static final int PAY_CHANNEL_BALANCE = 30;

    /**
     * 轮播图
     */
    public static final int STYLE_AD = 510;

    /**
     * 购买的课程
     */
    public static final int STYLE_BOUGHT = 520;

    /**
     * 热门课程
     */
    public static final int STYLE_HOT = 530;

    /**
     * 热门线下课程
     */
    public static final int STYLE_HOT_OFFLINE = 540;

    /**
     * 线下课程
     */
    public static final int STYLE_OFFLINE = 550;

    /**
     * 我创建的课程
     */
    public static final int STYLE_MY_CREATE = 560;

    /**
     * 0
     */
    public static final int VALUE0 = 0;

    /**
     * 10
     */
    public static final int VALUE10 = 10;

    /**
     * 20
     */
    public static final int VALUE20 = 20;

    /**
     * 30
     */
    public static final int VALUE30 = 30;

    /**
     * 最热排序
     */
    public static final int ORDER_HOT = 10;

    /**
     * 数据库操作成功，返回1
     */
    public static final int RESULT_OK = 1;

    /**
     * 排序方式
     */
    public static final String DESC = "desc";
    public static final String ASC = "asc";

    /**
     * 默认直播间id
     * <p>
     * 以后开通的直播间id，在这个基础上加1
     */
    public static final long DEFAULT_ROOM_ID = 1228;

    /**
     * 滑动验证码
     */
    public static final Long CAPTCHA_TYPE = 9L;

    /**
     * 表示我的云音乐
     */
    public static final Long CAPTCHA_BUSINESS_ID = 10L;

    /**
     * 表示注册位置
     */
    public static final Long CAPTCHA_LOGIN_SCENE_ID = 110L;

    /**
     * QQ
     */
    public static final int PLATFORM_QQ = 20;

    /**
     * 微信
     */
    public static final int PLATFORM_WECHAT = 30;

    /**
     * 支付宝支付签名验证失败返回
     */
    public static final String SIGN_VERIFY_FAILED = "verify failed";

    /**
     * 支付宝，回调后服务器处理完毕后
     * 返回该字符串
     * 不然支付宝会以一定的频率继续回调接口
     */
    public static final String PAY_SUCCESS = "success";

    /**
     * 生成微信支付信息错误
     */
    public static final int ERROR_GENERATE_WECHAT_PAY = 1180;
    public static final String ERROR_GENERATE_WECHAT_PAY_MESSAGE = "生成微信支付信息错误！";

    /**
     * 生成微信支付信息错误
     */
    public static final int ERROR_WECHAT_PAY_SIGN = 1190;
    public static final String ERROR_WECHAT_PAY_SIGN_MESSAGE = "生成微信支付签名错误！";

    /**
     * 微信回调 成功响应
     */
    public static final String WECHAT_PAY_SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";

    /**
     * 微信回调响应
     */
    public static final String WECHAT_PAY_RESPONSE = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[%s]]></return_msg></xml>";

    //region 平台
    /**
     * android
     */
    public static final int ANDROID = 0;

    /**
     * ios
     */
    public static final int IOS = 10;

    /**
     * web
     */
    public static final int WEB = 20;

    /**
     * wap
     */
    public static final int WAP = 30;
    //endregion

    /**
     * 证码缓存key
     */
    public static final String KEY_CODE = "codes/texts/%s";

    /**
     * 是否存在缓存key
     * <p>
     * 目前主要用来实现限制验证码发送频率
     * 当然也可以用它来实现接口请求频率
     */
    public static final String KEY_EXIST = "exists/codes/%s";

    //region 推送事件类型
    /**
     * 客户端退出事件
     */
    public static final int PUSH_STYLE_LOGOUT = 0;
    public static final String KEY_RULES = "rules";

    //endregion

    /**
     * 管理员角色名称
     */
    public static final String ROLE_ADMIN = "admin";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";


    /**
     * 请求/响应签名key
     */
    public static final String HEADER_SIGN = "Sign";


    /**
     * 默认编码
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 加盐格式化字符串
     */
    public static final String SALT_START = "wt5j1URZ1H6RDtt";

    /**
     * 加盐格式化字符串
     */
    public static final String SALT_END = "uWg7x2E0Mr5Xwzm";
}