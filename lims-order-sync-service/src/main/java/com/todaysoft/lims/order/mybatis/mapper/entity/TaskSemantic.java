package com.todaysoft.lims.order.mybatis.mapper.entity;


public class TaskSemantic {

    public static final String DNA_EXTRACT = "DNA-EXT";//DNA提取

    public static final String DNA_QC = "DNA-QC";//DNA质检

    public static final String CDNA_EXTRACT = "CDNA-EXT";//CDNA提取

    public static final String CDNA_QC = "CDNA-QC";//CDNA质检

    public static final String LIBRARY_CRE = "LIBRARY-CRE";//文库创建

    public static final String LIBRARY_QC = "LIBRARY-QC";//文库质检

    public static final String LIBRARY_CAP = "LIBRARY-CAP";//文库捕获

    public static final String RQT = "RQT";//相对定量

    public static final String POOLING = "POOLING";//混样

    public static final String QT = "QT";//绝对定量

    public static final String NGS_SEQ = "NGS-SEQ";//上机实验

    public static final String BIOLOGY_ANALY = "BIOLOGY-ANALY";//生信分析

    public static final String RNA_ZL = "RNA_ZL";//RNA转录

    public static final String TECHNICAL_ANALY = "TECHNICAL-ANALY";//技术分析

    //sanger 验证节点
    public static final String PRIMER_DESIGN = "PRIMER-DESIGN";//报告邮寄

    public static final String PCR_ONE = "PCR-ONE";//报告邮寄

    public static final String PCR_TWO = "PCR-TWO";//报告邮寄

    public static final String DATA_ANALYSIS = "DATA-ANALYSIS";//报告邮寄

    //sanger检测

    public static final String SANGER_PCR_ONE = "SANGER-PCR-ONE";//报告邮寄

    public static final String SANGER_PCR_TWO = "SANGER-PCR-TWO";//报告邮寄

    public static final String SANGER_DATA_ANALYSIS = "SANGER-DATA-ANALYSIS";//报告邮寄

    public static final String SANGER_DATA_REPORT = "SANGER-DATA-REPORT";

    //实验取消
    public static final String EXPERIMENT_CANCER = "EXPERIMENT-CANCER";

    //反向测序
    public static final String SANGER_PCR_TWO_REVERSE = "SANGER-PCR-TWO-REVERSE";

    //PCR-NGS 验证节点
    public static final String PCR_NGS_PRIMER_DESIGN = "PCR-NGS-PRIMER-DESIGN";//PCRNGS引物设计

    public static final String PCR_NGS = "PCR-NGS";//PCRNGS引物设计

    public static final String PCR_NGS_DATA_ANALYSIS = "PCR-NGS-DATA-ANALYSIS";//PCRNGS数据分析

    public static final String PCR_NGS_EXTEND = "PCR-NGS-EXTEND";//PCRNGS扩展任务

    public static final String REPORT_COMBINE = "REPORT-COMBINE";//报告整合

    public static final String RESAMPLING = "RESAMPLING";

    public static final String MLPA_DATA_ANALYSIS = "MLPA-DATA-ANALYSIS";

    public static final String MLPA = "MLPA";

    public static final String QPCR = "QPCR";

    public static final String CAP_NGS = "CAP-NGS";

    public static final String LONG_PCR = "Long-PCR";

    public static final String LONG_CRE = "Long-CRE";

    public static final String LONG_QC = "Long-QC";

    public static final String MULTI_PCR = "MULTI-PCR";

    public static final String MULTIPCR_QC = "MULTIPCR-QC";

    public static final String FLUO_TEST = "FLUO-TEST";

    public static final String FLUO_ANALYSE = "FLUO-ANALYSE";

    public static final String DT = "DT";

    public static final String DT_DATA_ANALYSIS = "DT-DATA-ANALYSIS";//PCRNGS数据分析

    public static final String PCRNGS_METHOD_NAME = "PCR-NGS验证";

    public static final String SANGER_METHOD_NAME = "Sanger检测";

    public static final String MLPA_METHOD_NAME = "MLPA验证";

    public static final String QPCR_METHOD_NAME = "QPCR验证";

}
