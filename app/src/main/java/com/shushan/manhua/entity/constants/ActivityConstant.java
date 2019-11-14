package com.shushan.homework101.entity.constants;

/**
 * Activity 广播之类的
 */
public class ActivityConstant {
    /**
     * 跳转到首页
     */
    public static final String SWITCH_TO_HOME_PAGE = "switch_to_home_page";
    /**
     * 跳转到老师
     */
    public static final String SWITCH_TO_TEACHER_PAGE = "switch_to_teacher_page";
    /**
     * 支付成功 更新数据
     */
    public static final String PAY_SUCCESS = "pay_success";
//    /**
//     * 体验券支付成功
//     */
//    public static final String EXPERIENCE_PAY_SUCCESS = "experience_pay_success";
    /**
     * 更新年级
     * 更新我的年级刷新我的老师列表数据
     */
    public static final String UPDATE_PERSONAL_GRADE = "update_personal_grade";
    /**
     * 更新个人资料更新我的页面(除了年级信息)
     * 不用更新首页和老师页面
     */
    public static final String UPDATE_PERSONAL_INFO = "update_personal_info";
    /**
     * 领取辅导券后更新我的老师页面
     */
    public static final String UPDATE_MINE_TEACHER_LIST = "update_mine_teacher_list";
    /**
     * 小灰条提示通知消息
     */
    public static final String SHOW_NOTIFICATION_MESSAGE = "show_notification_message";
    /**
     * 友盟推送消息
     * 更新未读消息
     * 更新我的
     */
    public static final String UM_PUSH_MESSAGE = "um_push_message";
    /**
     * 有融云消息未读
     */
    public static final String RC_UNREAD_MSG = "rc_unread_msg";
    /**
     * 有融云消息已读
     */
    public static final String RC_READ_MSG = "rc_read_msg";

}
