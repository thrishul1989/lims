package com.todaysoft.lims.system.util;

public class Constant
{
    public static final String STARTED = "2"; //明细已启动
    
    public static final String UNSTARTED = "1";//明细未启动
    
    public static final String CONTAINERLOCATION_USED = "2";
    
    public static final String SAMPLE_DETAIL_TEMPSAVE = "0";
    
    public static final String SAMPLE_DETAIL_SAVED = "1";
    
    public static final String Longterm_Storage_Location = "longtermstoragelocation";//长期存储位置
    
    public static final String Temporary_Storage_Location = "temporarystoragelocation";//临期存储位置
    
    public static final String ORDER_UNCHARGE = "0";
    
    public static final String ORDER_CHARGED = "1";
    
    public static final String ORDER_UNPAY = "0";
    
    public static final String ORDER_PAYED = "1";
    
    public static final String CUMSTER_ENABLE = "0";
    
    public static final String CUMSTER_DISABLE = "1";
    
    public static final Integer PROBE_ENABLE = 0;
    
    public static final Integer PROBE_DISENABLE = 1;
    
    //1 临床遗传 2、临床肿瘤  3健康筛查  4:科技服务
    
    public static final int ORDER_CLINICAL_INHIENT = 1;
    
    public static final int ORDER_CLINICAL_TUMOURS = 2;
    
    public static final int ORDER_HEIGHT_TYPE = 3;
    
    public static final int ORDER_RESEARCH_TYPE = 4;
    
    //减免申请--审批结果
    public static final int ORDER_REDUCE_TEMP = 0;
    
    public static final int ORDER_REDUCE_SUC = 1;
    
    public static final int ORDER_REDUCE_FAIL = 2;
    
    //样本收样状态
    public static final int ORDER_SAMPLE_WAITSEND = 0;
    
    public static final int ORDER_SAMPLE_SENDING = 1;
    
    public static final int ORDER_SAMPLE_RECEIVED = 2;
    
    public static final int ORDER_SAMPLE_ERROR = 3;
    
    public static final int ORDER_SAMPLE_RETURN = 4;
    
    /**
     * 订单状态
     */
    
    public static final int ORDER_DEFAULT_UNSTATE = 0; //暂存
    
    public static final int ORDER_DEFAULT_STATE = 1; //待付款 111
    
    public static final int ORDER_RESEARCH_STATE = 6; //待检测确认
    
    public static final int ORDER_STATUS_RECEIVED_SAMPLE = 2; //付款待确认111
    
    public static final int ORDER_ALREADY_COMPLETED = 5; //已完成 111
    
    public static final int ORDER_ALREADY_PAYED = 4; //已付款 111
    
    public static final int ORDER_DELAY_PAYED = 3; //延迟付款111
    
    public static final int ORDER_DETECTIONING = 7; //检测中
    
    public static final int ORDER_REFUND = 8; //已退款 
    
    public static final int ORDER_REDO = 9; //已取消
    
    /**
     * 订单支付方式
     */
    public static final String ORDER_PAYTYPE_POS = "3"; //pos机
    
    public static final String ORDER_PAYTYPE_TRANS = "4"; //转账
    
    /**
     * 报告名称前缀
     */
    public static final String REPORT_NAME_PREFIX = "product_report_";
}
