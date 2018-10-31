package com.todaysoft.lims.testing.base.utils;

public class OrderConstants
{
    public final static String SHEDULE_PAUSE = "pause";//暂停流程
    
    public final static String SHEDULE_CANCEL = "cancel";//取消流程
    
    public final static String SHEDULE_FINISH = "finish";//完成
    
    public final static String SHEDULE_RESTART = "restart";//继续流程
    
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
     * 产品状态
     */
    public static final Integer ORDER_PRODUCT_FINISH = 6;
    
    public static final Integer ORDER_PRODUCT_NOTSEND_EMAIL = 7;
    
    public static final Integer ORDER_PRODUCT_CANCEL = 8;
    
    public static final Integer ORDER_RESEARCH_PRODUCT_FINISH = 1;
    
    public static final Integer ORDER_RESEARCH_PRODUCT_CANCEL = 2;
    
    /**
     * 样本用途
     */
    public static final Integer ORDER_SAMPLE_PROPOSE_VERTY = 1;
    
    /**
     * 主样本
     */
    public static final String ORDER_SAMPLE_PRIMARY = "1";
    
    public static final Integer ORDER_SCHEDULE_FINISHED = 3; //已结束
    
    public static final Integer ORDER_TECHNICAL_NEW_SCHEDULE_FINISHED = 5; //复核完成
    
    public static final String ORDER_VERTIFY_SAMPLE_TEMP_FINISHED = "已临时完成";
    
}
