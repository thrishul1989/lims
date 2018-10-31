package com.todaysoft.lims.biology.adapter.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.biology.adapter.ITestingAdapter;
import com.todaysoft.lims.biology.model.PcrNgsDataModel;
import com.todaysoft.lims.biology.model.PoolingDivisionSample;
import com.todaysoft.lims.biology.model.ProductMethodModel;
import com.todaysoft.lims.biology.model.request.BiologyReAnnotationRequest;
import com.todaysoft.lims.biology.model.request.ProductQueryRequest;

@Service
public class TestingAdapter implements ITestingAdapter
{
    
    @Autowired
    private RestTemplate template;
    
    @Override
    public List<PoolingDivisionSample> getSamplesBySquencingCode(String squencgingCode)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/getPoolingDivisionSample/{code}");
        ResponseEntity<List<PoolingDivisionSample>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<PoolingDivisionSample>>()
            {
            }, squencgingCode);
        return exchange.getBody();
    }
    
    @Override
    public String getTaskCodeBySemantic(String semantic)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/getTaskCodeBySemantic/{semantic}");
        return template.getForObject(url, String.class, Collections.singletonMap("semantic", semantic));
    }
    
    @Override
    public ProductMethodModel getProductAndMethod(String sampleCode, String productCode, String methodName)
    {
        //        ProductMethodRequest request = new ProductMethodRequest();
        //        request.setProductCode(productCode);
        //        request.setMethodName(methodName);
        String url = GatewayService.getServiceUrl("/bpm/testing/getProductAndMethod/{sampleCode}/{productCode}/{methodName}");
        ResponseEntity<ProductMethodModel> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ProductMethodModel>()
        {
        }, sampleCode, productCode, methodName);
        return exchange.getBody();
    }
    
    @Override
    public ProductMethodModel getProductNameByCode(String productCode)
    {
        ProductQueryRequest request = new ProductQueryRequest();
        request.setProductCode(productCode);
        String url = GatewayService.getServiceUrl("/bpm/testing/getProductNameByCode");
        ResponseEntity<ProductMethodModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProductQueryRequest>(request), new ParameterizedTypeReference<ProductMethodModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void goPcrNgsDataAnalysis(String taskId, String squencingCode)
    {
        PcrNgsDataModel request = new PcrNgsDataModel();
        request.setTaskId(taskId);
        request.setSquencingCode(squencingCode);
        String url = GatewayService.getServiceUrl("/bpm/testing/biology-analy/goPcrNgsDataAnalysis");
        template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public void taskFailReport(BiologyReAnnotationRequest request)
    {
        PcrNgsDataModel requestModel = new PcrNgsDataModel();
        requestModel.setTaskId(request.getTaskId());
        requestModel.setRemark(request.getRemark());
        requestModel.setTaskRefer("BIOLOGY-ANNOTATION");
        String url = GatewayService.getServiceUrl("/bpm/testing/biology-analy/taskFailReport");
        template.postForObject(url, requestModel, Integer.class);
    }
}
