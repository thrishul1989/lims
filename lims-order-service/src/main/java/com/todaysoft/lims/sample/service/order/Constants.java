package com.todaysoft.lims.sample.service.order;

public class Constants
{
    public static final Integer ORDER_DRAFT_NO = 0;
    
    public static final Integer ORDER_DRAFT_YES = 1;
    
    /**
     * 订单付款状态：0-待付款 1-付款待确认 2-已付款
     */
    public static final Integer ORDER_PAYMENT_UNPAID = 0;
    
    public static final Integer ORDER_PAYMENT_CONFIRMING = 1;
    
    public static final Integer ORDER_PAYMENT_PAID = 2;
    
    /**
     * 全款标志
     */
    
    public static final Integer ORDER_FULL_PAID_NO = 0;
    
    public static final Integer ORDER_FULL_PAID_YES = 1;
    
    /**
     * 延迟付款
     */
    public static final Integer ORDER_PAYMENT_DELAY_UNAPPLIED = 0;
    
    public static final Integer ORDER_PAYMENT_DELAY_PROCESSING = 1;
    
    public static final Integer ORDER_PAYMENT_DELAY_AGREED = 2;
    
    public static final Integer ORDER_PAYMENT_DELAY_DISAGREED = 3;
    
    /***
     *  订单状态 ：0-暂存 1-待检测下放 2-检测中 3-暂停 4-已取消 5-已完成
     */
    public static final Integer ORDER_TESTING_UNSTARTED = 0;
    
    public static final Integer ORDER_TESTING_DELEGATED = 1;
    
    public static final Integer ORDER_TESTING_PROCESSING = 2;
    
    public static final Integer ORDER_TESTING_PAUSED = 3;
    
    public static final Integer ORDER_TESTING_CANCELED = 4;
    
    public static final Integer ORDER_TESTING_FINISHED = 5;
    
    /**
     * 订单结算类型
     */
    
    public static final Integer ORDER_SETTLEMENT_SINGLE = 1; //一单一结
    
    public static final Integer ORDER_SETTLEMENT_CENTRAL = 2; //集中
    
    /*
     * 付费检测状态 0：未确认  1：已确认
     * */
    public static final Integer SCHEDULE_PAYMENT_STATUS_UNCONFIRMED = 0;
    
    public static final Integer SCHEDULE_PAYMENT_STATUS_CONFIRMED = 1;
    
    public static final Integer ORDER_TESTING_START_NO = 0;
    
    public static final Integer ORDER_PAYMENT_REQUIRMENT_UNMATCHED = 0;
    
    public static final Integer ORDER_PAYMENT_REQUIRMENT_MATCHED = 1;
    
    public static final Integer ORDER_SAMPLE_REQUIRMENT_UNMATCHED = 0;
    
    public static final Integer ORDER_SAMPLE_REQUIRMENT_MATCHED = 1;
    
    /**
     * 订单状态
     * 0-暂存
     * 1-待付款
     * 2-待确认付款
     * 3-延迟付款
     * 4-已付款
     * 5-完成
     * 6-待检测确认订单
     * 7-检测中订单
     * 9-已取消
     * */
    public static final Integer ORDER_TEMPORARY = 0;
    
    public static final Integer ORDER_PAY_PENDING = 1;
    
    public static final Integer ORDER_PAY_TO_BE_CONFIRMED = 2;
    
    public static final Integer ORDER_PAY_DELAY = 3;
    
    public static final Integer ORDER_PAYED = 4;
    
    public static final Integer ORDER_FINISHED = 5;
    
    public static final Integer ORDER_UNTESTED = 6;
    
    public static final Integer ORDER_TESTING = 7;
    
    public static final Integer ORDER_CANCEL = 9;
    
    /**
     * 结算方式：一单一结
     */
    public static final String SETTLEMENT_SINGLE = "4";
    
    /**
     * 订单支付方式：未支付
     */
    public static final String ORDER_PAYMENT_TYPE_NONE = "0";
    
    /**
     * 合同订单启动方式：自动启动
     */
    public static final Integer CONTRACT_START_AUTO = 1;
    
    /**
     * 产品状态
     */
    public static final Integer ORDER_PRODUCT_FINISH = 6;
    
    public static final Integer ORDER_PRODUCT_NOTSEND_EMAIL = 7;
    
    public static final Integer ORDER_PRODUCT_CANCEL = 8;
    
    public static final Integer ORDER_RESEARCH_PRODUCT_FINISH = 1;
    
    public static final Integer ORDER_RESEARCH_PRODUCT_CANCEL = 2;
}
