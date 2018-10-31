package com.todaysoft.lims.biology.adapter;


import com.todaysoft.lims.biology.model.PoolingDivisionSample;
import com.todaysoft.lims.biology.model.ProductMethodModel;
import com.todaysoft.lims.biology.model.request.BiologyReAnnotationRequest;

import java.util.List;

public interface ITestingAdapter {

    List<PoolingDivisionSample> getSamplesBySquencingCode(String squencgingCode);

    String getTaskCodeBySemantic(String semantic);

    ProductMethodModel getProductAndMethod(String sampleCode,String productCode, String methodName);

    void goPcrNgsDataAnalysis(String taskId,String squencingCode);

    void taskFailReport(BiologyReAnnotationRequest request);

    ProductMethodModel getProductNameByCode(String productCode);
}
