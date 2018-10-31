package com.todaysoft.lims.system.util;

public class OrderConstant
{
    /**
     * 订单付款状态：0-待付款 1-付款待确认 2-已付款
     */
    public static final Integer ORDER_PAYMENT_UNPAID = 0;
    
    public static final Integer ORDER_PAYMENT_CONFIRMING = 1;
    
    public static final Integer ORDER_PAYMENT_PAID = 2;
    
    /**
     * 订单结算类型
     */
    
    public static final Integer ORDER_SETTLEMENT_SINGLE = 1; //一单一结
    
    public static final Integer ORDER_SETTLEMENT_CENTRAL = 2; //集中
    
    /***
     *  订单状态 ：0-暂存 1-待检测下放 2-检测中 3-暂停 4-已取消 5-已完成
     */
    public static final Integer ORDER_TESTING_UNSTARTED = 0;
    
    public static final Integer ORDER_TESTING_DELEGATED = 1;
    
    public static final Integer ORDER_TESTING_PROCESSING = 2;
    
    public static final Integer ORDER_TESTING_PAUSED = 3;
    
    public static final Integer ORDER_TESTING_CANCELED = 4;
    
    public static final Integer ORDER_TESTING_FINISHED = 5;
}
