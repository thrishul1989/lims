package com.todaysoft.lims.gateway.service.adapter;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.Task;
import com.todaysoft.lims.gateway.model.TestingSchedule;
import com.todaysoft.lims.gateway.model.TodoModel;
import com.todaysoft.lims.gateway.model.request.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class TestingSheduleAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-sampleReceive-service";
    
    @Autowired
    private RestTemplate template;
    
    @HystrixCommand(fallbackMethod = "fallback")
    public TestingSchedule get(Integer id){
    	String url = getServiceUrl("/testing/{id}");
    	return template.getForObject(url, TestingSchedule.class, Collections.singletonMap("id", id));
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void start(TestingScheduleStartRequest request){
        String url = getServiceUrl("/testing/start");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void assign(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assign");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submit(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submit");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void assignDnaQt(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignDnaQt");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitDnaQt(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitDnaQt");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void assignWkCreate(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignWkCreate");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitWk(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitWkCreate");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void assignWkQt(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignWkQt");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitWkQt(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitWkQt");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void assignWKCatch(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignWKCatch");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitWKCatch(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitWKCatch");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void assignXddl(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignXddl");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitXddl(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitXddl");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void assignAbsoluteQ(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignAbsoluteQ");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitAbsoluteQ(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitAbsoluteQ");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void assignOntest(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignOntest");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitOntest(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitOntest");
        template.postForObject(url, request, Boolean.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void assignBioInfo(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignBioInfo");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitBioInfo(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitBioInfo");
        template.postForObject(url, request, Boolean.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void assignTecAnalysis(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignTecAnalysis");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitTecAnalysis(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitTecAnalysis");
        template.postForObject(url, request, Boolean.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void assignReportCreate(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignReportCreate");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitReportCreate(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitReportCreate");
        template.postForObject(url, request, Boolean.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void assignReportCheck(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignReportCheck");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitReportCheck(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitReportCheck");
        template.postForObject(url, request, Boolean.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void assignReportMail(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/assignReportMail");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void submitReportMail(TestingSheetSubmitRequest request){
        String url = getServiceUrl("/testing/submitReportMail");
        template.postForObject(url, request, Boolean.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public List<TodoModel> todo(TestingNodeTodoRequest request)
    {
        String url = getServiceUrl("/testing/todo");
        ResponseEntity<List<TodoModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingNodeTodoRequest>(request), new ParameterizedTypeReference<List<TodoModel>>()
            {
            });
        return exchange.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public List<TodoModel> executeTodo(TestingNodeTodoRequest request)
    {
        String url = getServiceUrl("/testing/executeTodo");
        ResponseEntity<List<TodoModel>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<TestingNodeTodoRequest>(request), new ParameterizedTypeReference<List<TodoModel>>()
                {
                });
        return exchange.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Pagination<TodoModel> processJoin(TestingNodeTodoRequest request)
    {
        String url = getServiceUrl("/testing/processJoin");
        ResponseEntity<Pagination<TodoModel>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<TestingNodeTodoRequest>(request), new ParameterizedTypeReference<Pagination<TodoModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public TestingSheetSubmitRequest dataProcess(TestingSheetSubmitRequest request){
    	String url = getServiceUrl("/testing/xddl/dataProcess");
    	return template.postForObject(url, request, TestingSheetSubmitRequest.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public boolean validateCatchCode(TestingSheetCreateRequest request){
        String url = getServiceUrl("/testing/validateCatchCode");
      return   template.postForObject(url, request, boolean.class);
    }
    
    

    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
}
