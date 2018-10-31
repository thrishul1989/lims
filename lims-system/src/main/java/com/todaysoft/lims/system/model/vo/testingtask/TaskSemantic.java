package com.todaysoft.lims.system.model.vo.testingtask;

/**
 * Created by HSHY-032 on 2016/8/16.
 */
public class TaskSemantic
{
    public static final String BIOLOGY_ANNOTATION = "BIOLOGY-ANNOTATION";//生信注释

    public static final String TECHNICAL_ANALYSIS = "TECHNICAL-ANALYSIS";//新技术分析

    public static final String DNA_EXTRACT = "DNA-EXT";//DNA提取
    
    public static final String DNA_QC = "DNA-QC";//DNA质检
    
    public static final String CDNA_EXTRACT = "CDNA-EXT";//CDNA提取
    
    public static final String CDNA_QC = "CDNA-QC";//CDNA质检
    
    public static final String LIBRARY_CRE = "LIBRARY-CRE";//文库创建
    
    public static final String LIBRARY_QC = "LIBRARY-QC";//文库质检
    
    public static final String LIBRARY_CAP = "LIBRARY-CAP";//文库捕获
    
    public static final String POOLING = "POOLING";//相对定量&混样
    
    public static final String QT = "QT";//绝对定量
    
    public static final String ON_TEST = "ON_TEST";//上机实验
    
    public static final String BIOINFORMATICS_ANALYZE = "BIOINFORMATICS_ANALYZE";//生信分析
    
    public static final String RNA_ZL = "RNA_ZL";//RNA转录
    
    public static final String ANALYSIS = "TEC-ANALYS";//技术分析
    
    public static final String REPORT_CREATE = "REPORT-CREATE";//报告生成
    
    public static final String REPORT_CHECK = "REPORT-CHECK";//报告审核
    
    public static final String REPORT_MAIL = "REPORT-MAIL";//报告邮寄
    
    //sanger 验证节点
    public static final String PRIMER_DESIGN = "PRIMER-DESIGN";//引物设计
    
    public static final String PCR_ONE = "PCR-ONE";//一次PCR
    
    public static final String PCR_TWO = "PCR-TWO";//二次PCR
    
    public static final String DATA_ANALYSIS = "DATA-ANALYSIS";//数据分析

    public static final String DATA_DIVISION = "DATA-DIVISION";//数据拆分
    
    //MLPA验证
    public static final String MLPA_TEST = "MLPA-TEST";//mlpa实验
    
    public static final String MLPA_DATA = "MLPA-DATA";//mlpa数据分析
    
    public static final String TESTING_RESULT_SUCCESS = "1";//实验任务成功
    
    public static final String TESTING_RESULT_FAIL_REDO = "2";//实验任务失败可重做
    
    public static final String TESTING_RESULT_FAIL_NOREDO = "3";//实验任务失败不可重做
    
}
