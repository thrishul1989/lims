package com.todaysoft.lims.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.vo.TemporaryData;
import com.todaysoft.lims.system.model.vo.TestingSheet;
import com.todaysoft.lims.system.model.vo.TestingSheetJddlModel;
import com.todaysoft.lims.system.model.vo.TestingSheetTaskCatch;
import com.todaysoft.lims.system.model.vo.testingtask.TaskSemantic;
import com.todaysoft.lims.system.model.vo.testingtask.TestingSheetCreateRequest;
import com.todaysoft.lims.system.model.vo.testingtask.TestingSheetTask;
import com.todaysoft.lims.system.model.vo.testingtask.TestingTaskAssign;
import com.todaysoft.lims.system.model.vo.testingtask.TestingTaskCreateRequest;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;
import com.todaysoft.lims.system.service.ITestingTaskService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.adapter.request.testingtask.TestingTaskDetailRequest;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingTaskService extends RestService implements ITestingTaskService
{
    
    @Autowired
    private IUserService userService;
    
    @Override
    public TestingTaskAssign paging(TestingTaskDetailRequest searcher)
    {
        
        TestingTaskDetailRequest request = new TestingTaskDetailRequest();
        BeanUtils.copyProperties(searcher, request);
        
        String url = GatewayService.getServiceUrl("/testingTask/paging");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public Integer create(TestingTaskCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testingTask/create");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public Integer createRNA(TestingTaskCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testingTask/createRNA");
        return template.postForObject(url, request, Integer.class);
    }
    
    /**
     * 创建DNA任务 —————— new
     */
    
    @Override
    public Integer create(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testingTask/create");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public Boolean createDNAQC(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignDnaQt");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean createLibrary(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignWkCreate");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean createWKQC(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignWkQt");
        return template.postForObject(url, request, Boolean.class);
    }
    
    /**
     * 文库捕获下达任务
     */
    @Override
    public Boolean createLibraryCatch(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignWKCatch");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean createXddl(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignXddl");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean createJddl(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignAbsoluteQ");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean createOnTest(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignOntest");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean createBioInfo(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignBioInfo");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean createTecAnalysis(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignTecAnalysis");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean createReportCreate(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignReportCreate");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean createReportCheck(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignReportCheck");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean createReportMail(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/assignReportMail");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public TestingTaskAssign pagingDnaQc(TestingTaskDetailRequest searcher)
    {
        
        TestingTaskDetailRequest request = new TestingTaskDetailRequest();
        BeanUtils.copyProperties(searcher, request);
        
        String url = GatewayService.getServiceUrl("/testingTask/pagingDnaQc");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingLibraryCreate(TestingTaskDetailRequest searcher)
    {
        
        TestingTaskDetailRequest request = new TestingTaskDetailRequest();
        BeanUtils.copyProperties(searcher, request);
        
        String url = GatewayService.getServiceUrl("/testingTask/pagingLibraryCreation");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingWKQC(TestingTaskDetailRequest searcher)
    {
        
        TestingTaskDetailRequest request = new TestingTaskDetailRequest();
        BeanUtils.copyProperties(searcher, request);
        
        String url = GatewayService.getServiceUrl("/testingTask/pagingWKQC");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingLibraryCatch(TestingTaskDetailRequest searcher)
    {
        
        TestingTaskDetailRequest request = new TestingTaskDetailRequest();
        BeanUtils.copyProperties(searcher, request);
        
        String url = GatewayService.getServiceUrl("/testingTask/pagingLibraryCatch");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingXddl(TestingTaskDetailRequest searcher)
    {
        TestingTaskDetailRequest request = new TestingTaskDetailRequest();
        BeanUtils.copyProperties(searcher, request);
        
        String url = GatewayService.getServiceUrl("/testingTask/pagingXddl");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingJddl(TestingTaskDetailRequest searcher)
    {
        TestingTaskDetailRequest request = new TestingTaskDetailRequest();
        BeanUtils.copyProperties(searcher, request);
        
        String url = GatewayService.getServiceUrl("/testingTask/pagingJddl");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingOnTest(TestingTaskDetailRequest searcher)
    {
        TestingTaskDetailRequest request = new TestingTaskDetailRequest();
        BeanUtils.copyProperties(searcher, request);
        
        String url = GatewayService.getServiceUrl("/testingTask/pagingOnTest");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingBioInfo(TestingTaskDetailRequest searcher)
    {
        TestingTaskDetailRequest request = new TestingTaskDetailRequest();
        BeanUtils.copyProperties(searcher, request);
        
        String url = GatewayService.getServiceUrl("/testingTask/pagingBioInfo");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(request), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingTecAnalysis(TestingTaskDetailRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/testingTask/pagingTecAnalysis");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(searcher), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingReportCreate(TestingTaskDetailRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/testingTask/pagingReportCreate");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(searcher), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingReportCheck(TestingTaskDetailRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/testingTask/pagingReportCreate");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(searcher), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskAssign pagingReportMail(TestingTaskDetailRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/testingTask/pagingReportCreate");
        ResponseEntity<TestingTaskAssign> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingTaskDetailRequest>(searcher), new ParameterizedTypeReference<TestingTaskAssign>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String getSampleLocation(String containerType)
    {
        String url = GatewayService.getServiceUrl("/testingTask/getSampleLocation/{containerType}");
        return template.getForObject(url, String.class, Collections.singletonMap("containerType", containerType));
    }
    
    @Override
    public List<TestingSheetJddlModel> getJddlList()
    {
        TestingTaskDetailRequest request = new TestingTaskDetailRequest();
        request.setSemantic(TaskSemantic.LIBRARY_CAP);
        String url = GatewayService.getServiceUrl("/testingSheet/getJddlList");
        ResponseEntity<List<TestingSheetJddlModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TestingTaskDetailRequest>(request),
                new ParameterizedTypeReference<List<TestingSheetJddlModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public TestingSheetCreateRequest doSome(TestingSheetCreateRequest request)
    {
        String pa = null;
        if (StringUtils.isNotEmpty(request.getParam()))
        {
            
            pa = request.getParam();
        }
        else
        {
            pa = request.getDataForm();
        }
        /*
         * request.setInputQuantity(tSheet.getInputQuantity());
         * request.setOutputQuantity(tSheet.getOutputQuantity());
         */
        if (StringUtils.isNotEmpty(request.getContent()))
        {
            TestingSheet tSheet = JSON.parseObject(request.getContent(), TestingSheet.class);
            TestingSheetTask tsT = JSON.parseObject(request.getContent(), TestingSheetTask.class);
            request.setSendDate(tsT.getSendDate());
            request.setSendName(tsT.getSendName());
            request.setTestingName(tsT.getTestingName());
            request.setMethod(tsT.getMethod());
        }
        
        List<TestingSheetTask> list = JSON.parseArray(pa + "", TestingSheetTask.class);
        List<TestingSheetTask> samlllist = new ArrayList<TestingSheetTask>();
        List<String> semanticList = Lists.newArrayList(TaskSemantic.ANALYSIS, TaskSemantic.REPORT_CREATE, TaskSemantic.REPORT_CHECK, TaskSemantic.REPORT_MAIL);
        if (semanticList.contains(request.getSemantic()))
        {
            for (TestingSheetTask t : list)
            {
                samlllist.add(t);
            }
        }
        else
        {
            for (TestingSheetTask t : list)
            {
                if (StringUtils.isNotEmpty(t.getTestingCode()))
                {
                    samlllist.add(t);
                }
            }
        }
        request.setSheetTaskList(samlllist);
        
        List<TestingSheetCreateRequest> sheet = JSON.parseArray(pa + "", TestingSheetCreateRequest.class);
        if (Collections3.isNotEmpty(sheet))
        {
            if (StringUtils.isNotEmpty(sheet.get(0).getWkbhCode()))
            {
                request.setWkbhCode(sheet.get(0).getWkbhCode());
            }
        }
        
        List<TestingSheetTaskCatch> catchlist = JSON.parseArray(pa + "", TestingSheetTaskCatch.class);
        request.setSheetTaskCatchList(catchlist);
        
        List<TemporaryData> datalist = JSON.parseArray(pa + "", TemporaryData.class);
        request.setTemporaryData(datalist);
        return request;
    }
    
    @Override
    public boolean validateCatchCode(TestingSheetCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/testing/validateCatchCode");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public TestingTask get(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingTask/getTestingTask/{id}");
        return template.getForObject(url, TestingTask.class, id);
    }
    
    @Override
    public TestingMethod getTestingMethodBySemantic(String semantic)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingTask/getTestingMethod/{semantic}");
        return template.getForObject(url, TestingMethod.class, semantic);
    }
}
