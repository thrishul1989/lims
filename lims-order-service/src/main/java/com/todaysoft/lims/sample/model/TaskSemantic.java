package com.todaysoft.lims.sample.model;

/**
 * Created by HSHY-032 on 2016/8/16.
 */
public class TaskSemantic {

    public static final String DNA_EXTRACT="DNA_EXTRACT";//DNA提取
    public static final String RNA_EXTRACT="RNA_EXTRACT";//RNA提取
    public static final String DNA_QC="DNA_QC";//DNA质检
    public static final String RNA_QC="RNA_QC";//RNA质检
    public static final String WK_CREATE="WK_CREATE";//文库创建
    
    public static final String WK_QC="WK_QC";//文库质检
    public static final String LIBRARY_CATCH="LIBRARY_CATCH";//文库捕获
    public static final String ON_TEST="ON_TEST";//上机实验
    public static final String BIOINFORMATICS_ANALYZE="BIOINFORMATICS_ANALYZE";//生信分析
    public static final String RNA_ZL="RNA_ZL";//RNA转录
    
    public static final String POOLING = "POOLING";//混样
    public static final String XDDL="XDDL";//相对定量&混样
    public static final String JDDL="JDDL";//绝对定量
    public static final String ANALYSIS="ANALYSIS";//技术分析
    public static final String REPORT_CREATE="REPORT_CREATE";//报告生成
    public static final String REPORT_CHECK="REPORT_CHECK";//报告审核
    public static final String REPORT_MAIL="REPORT_MAIL";//报告邮寄

    public static final Integer TESTING_RESULT_SUCCESS=1;//实验任务成功
    public static final Integer TESTING_RESULT_FAIL_REDO=2;//实验任务失败可重做
    public static final Integer TESTING_RESULT_FAIL_NOREDO=3;//实验任务失败不可重做

    public static final Integer TESTING_TASK_WAITASSIGN=0;//待下达
    public static final Integer TESTING_TASK_WAITTEST=1;//待实验
    public static final Integer TESTING_TASK_COMPLETE=2;//已完成


    public static final Integer TESTING_TASK_END_SUCC=0;//完成
    public static final Integer TESTING_TASK_END_REDO=1;//退回可重做
    public static final Integer TESTING_TASK_END_UNREDO=2;//退回不可重做
    
}
