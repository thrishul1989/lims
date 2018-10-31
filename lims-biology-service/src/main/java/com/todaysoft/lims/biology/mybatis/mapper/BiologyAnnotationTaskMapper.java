package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.*;
import com.todaysoft.lims.biology.model.request.BiologyAnnotatioListRequest;
import com.todaysoft.lims.biology.model.request.BiologyFamilyAnnotatioListRequest;

import java.util.List;

public interface BiologyAnnotationTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyAnnotationTask record);

    List<BiologyAnnotationTask> getTaskSearchList(BiologyAnnotatioListRequest request);

    int insertSelective(BiologyAnnotationTask record);

    BiologyAnnotationTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyAnnotationTask record);

    int updateByPrimaryKey(BiologyAnnotationTask record);

    OrderProductInfoModel getOrderInfoBySampleCode(String sampleCode);

    void createTaskList(List<BiologyAnnotationTask> taskList);

    List<OrderSampleInfoModel> getFamilySampleByCode(String orderCode);

    OrderSampleInfoModel getPrimarySampleSexByCode(String sampleCode);

    OrderSampleInfoModel getSubSampleSexByCode(String sampleCode);

    OrderSampleInfoModel getPhenotypeByOrderCode(String orderCode);

    List<OrderSampleInfoModel> getOrderSamplesByOrderCode(String orderCode);

    BiologyAnnotationTask getTaskByDataCode(String dataCode);

    ScheduleSampleModel getScheduleByTestingSample(String orderCode, String productCode, String methodSemantic, String sampleCode);

    ScheduleSampleModel getScheduleByReceivedSample(String orderCode, String productCode, String methodSemantic, String sampleCode);

    List<PhenotypeModel> getPhenotypeModelByPrimaySample(String sampleCode);

    OrderSampleInfoModel getSubSampleRelation(String sampleCode);

    BiologyAnnotationTask getTaskById(String id);
}