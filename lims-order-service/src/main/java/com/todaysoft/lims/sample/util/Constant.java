package com.todaysoft.lims.sample.util;

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
    
    //1 临床遗传 2、临床肿瘤  3健康筛查  4:科技服务
    
    public static final String ORDER_CLINICAL_INHIENT = "1";
    
    public static final String ORDER_CLINICAL_TUMOURS = "2";
    
    public static final String ORDER_HEIGHT_TYPE = "3";
    
    public static final String ORDER_RESEARCH_TYPE = "4";
    
    public static final int SAMPLE_RECEIVING_SUCCESS = 1; //成功
    
    public static final int SAMPLE_RECEIVING_FAIL = 0;//异常
    
    public static final int SAMPLE_RECEIVING = 1; //收样
    
    public static final int SAMPLE_RECEIVING_TRANS = 2;//转存
    
    public static final int SAMPLE_RECEIVING_INBOUND = 3;//入库 
    
    public static final int SAMPLE_RECEIVING_CANCEL = 5; //已取消
    
    public static final int SAMPLE_RECEIVE_LSTSIN_STATUS = 1; //1-入库 2-出库
    
    public static final int SAMPLE_RECEIVE_LSTSOUT_STATUS = 1; //1-入库 2-出库
    
    public static final int SAMPLE_RECEIVE_TEMPIN = 1; //临存
    
    public static final int SAMPLE_RECEIVE_LONGIN = 2; //长期
    
    public static final String SAMPLE_RECEIVE_TEMPINSTRING = "1"; //临存
    
    public static final String SAMPLE_RECEIVE_LONGINSTRIGN = "2"; //长期
    
    public static final int LOCATIONS_USED = 2; //已使用
    
    public static final int LOCATION_UNUSE = 1; //未被使用
    
    public static final int SHEDULE_STATUS_DEFAULT = 0;//不可启动
    
    public static final int SHEDULE_STATUS_ABLE = 1;//可启动
    
    public static final int RECEIVED_SAMPLE_DEFAULT = 0;//0-未入库1-已入库
    
    public static final int RECEIVED_SAMPLE_ABLE = 1;//0-未入库1-已入库
    
    public static final int SHEDULE_STATUS_UNABLE = 2;//启动完不可再启动
    
    public static final int ORDER_APP_SOURCE = 1;//APP端
    
    public static final int ORDER_BACKEND_SOURCE = 2;//业务后台
    
    public static final int QC_STATUS_FAIL = 0;//APP端
    
    public static final int QC_STATUS_OK = 1;//业务后台
    
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
    
    public static final int ORDER_PRODUCT_REFUND = 3; //已退款
    
    /**
     * 样本状态
     */
    public static final int SAMPLE_RECEIVED_STATUS = 2; //已接收
    
    public static final int SAMPLE_BACKED_STATUS = 4; //已返样
    
    public static final int SAMPLE_REDO_STATUS = 5; //已取消
    
    /**
     * 延迟申请状态
     */
    public static final int ORDER_DELAY_DEFAULT = 0; //待审核
    
    public static final int ORDER_DELAY_SUCCESS = 1; //审核通过
    
    public static final int ORDER_DELAY_FAIL = 2; //审核未通过
    
    /**
     * 订单支付方式
     */
    public static final String ORDER_PAYTYPE_POS = "3"; //pos机
    
    public static final String ORDER_PAYTYPE_TRANS = "4"; //转账
    
    public static final int ORDER_COUPON_USED = 1; //优惠券状态 ---使用过
    
    /**
     * 订单退款申请状态
     */
    public static final int ORDER_REFUND_SUCCESS = 1; //通过审核
    
    /**
     * 付款类型
     */
    public static final String ORDER_PAYMENT_TYPE_DEFAULT = "1"; //付款
    
    public static final String ORDER_PAYMENT_TYPE_REPLENISH = "2"; //补款
    
    public static final String ORDER_PAYMENT_TYPE_REFUND = "3"; //退款
    
    /**
     * 合同订单  ---检测确认状态
     */
    public static final int ORDER_SCHEDULE_PAYMENT_STATUS = 1; //已确认
    
    public static final int ORDER_SCHEDULE_UNPAYMENT_STATUS = 0; //待确认
    
    /**
     * 合同发票类型
     */
    public static final String CONTRACT_INVOICE_METHOD_FOCUS = "1"; //集中开票    0--一单一票
    
    /**
     * 合同结算方式
     */
    public static final String CONTRACT_SETTLE_METHOD_ONETH = "1"; //一次性付款
    
    public static final String CONTRACT_SETTLE_METHOD_BATCH = "2"; //分批付款
    
    public static final String CONTRACT_SETTLE_METHOD_MONTH = "3"; //月结
    
    public static final String CONTRACT_SETTLE_METHOD_ONEORDER = "4"; //一单一结
    
    /**
     * 附属样本用途
     */
    public static final int ORDER_SUBSAMPLE_TEMP = 4; //待确认
    
    /**
     * 合同状态 0-草稿 1-待确认 2-已确认  3--已结项
     */
    public static String CONTRACT_STATUS_CONFIRM = "2"; //已确认
    
    /**
     * 产品状态，0：待送测；1：待数据分析；2：待验证；3：待出报告；4：待审核报告；5：待寄送报告；6：已完成；
     */
    public static int PRODUCT_STATUS_WAITINGCHECK = 0;
    
    /**
     * 合同是否已经对账
     */
    
    public static int CONTRACT_UNRECONCILIATION = 0; //未对账
    
    public static int CONTRACT_RECONCILIATION = 1; //已对账
}
