package com.todaysoft.lims.biology.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.todaysoft.lims.biology.model.BiologyDivisionFastqData;
import com.todaysoft.lims.biology.model.BiologyDivisionMonitor;
import com.todaysoft.lims.biology.model.BiologyDivisionSearchSheet;
import com.todaysoft.lims.biology.model.BiologyDivisionSheet;
import com.todaysoft.lims.biology.model.BiologyDivisionSheetViewModel;
import com.todaysoft.lims.biology.model.BiologyDivisionStartModel;
import com.todaysoft.lims.biology.model.BiologyDivisionTask;
import com.todaysoft.lims.biology.model.TestingSheetModel;
import com.todaysoft.lims.biology.model.TestingSheetRequest;
import com.todaysoft.lims.biology.model.api.divisioncallback.BiologyDivisionCallBackModel;
import com.todaysoft.lims.biology.model.request.BioDivisionListRequest;
import com.todaysoft.lims.biology.model.request.CallBackSampleModel;
import com.todaysoft.lims.biology.model.request.MaintainDivisionDataRequest;
import com.todaysoft.lims.biology.mybatis.mapper.entity.BioInfoAnnotate;
import com.todaysoft.lims.biology.service.IBiologyAnnotationService;
import com.todaysoft.lims.biology.service.IBiologyService;
import com.todaysoft.lims.utils.StringUtils;

@RestController
@RequestMapping("/biology/division")
public class BiologyController
{
    @Autowired
    private IBiologyService service;
    
    @Autowired
    private IBiologyAnnotationService annotationService;
    
    @RequestMapping(value = "/taskPaging")
    public PageInfo<BiologyDivisionTask> searchBiologyTaskList(@RequestBody BioDivisionListRequest request)
    {
        return service.getTaskPaging(request);
    }
    
    @RequestMapping(value = "/getTaskOperateInfo/{id}")
    public BiologyDivisionStartModel getTaskOperateInfo(@PathVariable String id)
    {
        return service.getTaskOperateInfoById(id);
    }
    
    //根据上机号查询相关样本
    @RequestMapping(value = "/getSampleListBySquencingCode/{code}")
    public BiologyDivisionStartModel getSampleListBySquencingCode(@PathVariable String code)
    {
        return service.getSampleListBySquencingCode(code);
    }
    
    @RequestMapping(value = "/startBiologyDivision", method = RequestMethod.POST)
    public void submit(@RequestBody BiologyDivisionStartModel request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.startBiologyDivision(request, token);
    }
    
    @RequestMapping(value = "/resultCallBack", method = RequestMethod.POST)
    public void resultCallBack(@RequestBody BiologyDivisionCallBackModel result)
    {
        String taskReturnId = service.resultCallBack(result);
        
        if (StringUtils.isNotEmpty(taskReturnId))
        {
            // 生成生信注释任务
            annotationService.createAnnotationTask(taskReturnId);
        }
    }
    
    @RequestMapping(value = "/getSheetListByTaskId/{taskId}")
    public List<BiologyDivisionSheet> getSheetListByTaskId(@PathVariable String taskId)
    {
        return service.getSheetListByTaskId(taskId);
    }
    
    @RequestMapping(value = "/getTaskInfoById/{taskId}")
    public BiologyDivisionTask getTaskInfoById(@PathVariable String taskId)
    {
        return service.getTaskInfoById(taskId);
    }
    
    @RequestMapping(value = "/getBiologyDivisionFailRecords/{taskId}")
    public List<BiologyDivisionMonitor> getBiologyDivisionFailRecords(@PathVariable String taskId)
    {
        return service.getBiologyDivisionFailRecords(taskId);
    }
    
    @RequestMapping(value = "/synchronizeDivisionData")
    public List<BiologyDivisionFastqData> synchronizeDivisionData(@RequestBody MaintainDivisionDataRequest request)
    {
        return service.synchronizeDivisionData(request);
    }
    
    @RequestMapping(value = "/synchronizeBiologyInfoAnnotateData")
    public List<BioInfoAnnotate> synchronizeBiologyInfoAnnotateData(@RequestBody MaintainDivisionDataRequest request)
    {
        return service.synchronizeBiologyInfoAnnotateData(request);
    }
    
    @RequestMapping(value = "/updateLimsBiologyAnnotateStatus", method = RequestMethod.POST)
    public void updateLimsBiologyAnnotateStatus(@RequestBody CallBackSampleModel request)
    {
        service.updateLimsBiologyAnnotateStatus(request);
    }
    
    @RequestMapping(value = "/completeSheetPaging/{pageNo}/{pageSize}")
    public PageInfo<TestingSheetModel> completeSheetPaging(@RequestBody TestingSheetRequest request, @PathVariable int pageNo, @PathVariable int pageSize)
    {
        PageHelper.startPage(pageNo, 10);
        
        List<BiologyDivisionSearchSheet> list = service.completeSheetPaging(request);
        PageInfo<BiologyDivisionSearchSheet> pagination = new PageInfo<BiologyDivisionSearchSheet>(list);
        PageInfo<TestingSheetModel> resList = new PageInfo<>();
        List<TestingSheetModel> modelList = new ArrayList<>();
        for (BiologyDivisionSearchSheet sheet : pagination.getList())
        {
            TestingSheetModel model = new TestingSheetModel();
            model.setId(sheet.getId());
            model.setTaskName(BiologyDivisionSheet.BIOLOGY_DIVISION_NAME);
            model.setCode(sheet.getCode());
            model.setAssignerName(sheet.getTesterName());
            model.setAssignTime(sheet.getCreateTime());
            model.setTesterName(sheet.getTesterName());
            model.setSubmitTime(sheet.getSubmitTime());
            model.setSemantic(BiologyDivisionSheet.BIOLOGY_DIVISION_SEMANTIC);
            modelList.add(model);
        }
        resList.setTotal(pagination.getTotal());
        resList.setList(modelList);
        resList.setPageSize(pageSize);
        resList.setPageNum(pageNo);
        return resList;
    }

    @RequestMapping(value = "/getBiologyDivisionSheet/{sheetId}")
    public BiologyDivisionSheetViewModel getBiologyDivisionSheet(@PathVariable String sheetId)
    {
        return service.getBiologyDivisionSheet(sheetId);
    }

    @RequestMapping(value = "/getDataById/{id}")
    public BiologyDivisionFastqData getDataById(@PathVariable String id)
    {
        return service.getDataById(id);
    }

}
