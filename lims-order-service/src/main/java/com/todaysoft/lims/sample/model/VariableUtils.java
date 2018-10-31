package com.todaysoft.lims.sample.model;

/**
 * Created by HSHY-032 on 2016/8/16.
 */
public class VariableUtils {

	//产物类型
    public static final String TYPE_SAMPLE = "1";//产物类型为样本
    public static final String TYPE_PRODUCT = "2";//产物类型为实验产物
    
    //样本变更类型
    public static final String TYPE_IN = "1";//样本变更类型，产出
    public static final String TYPE_OUT = "2";//样本变更类型，投入
    
    //样本变更事件
    public static final Integer CHANGE_RECEIVE = 0;//样本变更事件为样本接收
    
    //是否原始样本
    public static final String ORIGINAL_N = "0";
    public static final String ORIGINAL_Y = "1";
    
    //存储类型
    public static final String STORAGE_TEMP = "1";//临存
    public static final String STORAGE_LONG = "2";//长期存储
    
    //是否是实验样本
    public static final Integer SAMPLETYPE_TEST = 1;//实验样本
    public static final Integer SAMPLETYPE_ORIGINAL = 2;//原始样本样本
    
    //常用单位
}
