package com.todaysoft.lims.biology.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.todaysoft.lims.biology.model.*;
import com.todaysoft.lims.biology.model.api.annotationcallback.BiologyReAnalysisDataResponse;
import com.todaysoft.lims.biology.model.request.*;
import com.todaysoft.lims.biology.model.response.FamilyAnnotatioResponse;
import com.todaysoft.lims.biology.mybatis.mapper.entity.BioInfoAnnotate;
import com.todaysoft.lims.biology.service.IBiologyAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/biology/annotation")
public class BiologyAnnotatioController
{
    @Autowired
    private IBiologyAnnotationService service;

    @RequestMapping(value = "/taskPaging")
    public PageInfo<BiologyAnnotationTask> searchBiologyTaskList(@RequestBody BiologyAnnotatioListRequest request)
    {
        return service.getTaskPaging(request);
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
            model.setTaskName(BiologyDivisionSheet.BIOLOGY_ANNOTATION_NAME);
            model.setCode(sheet.getCode());
            model.setAssignerName(sheet.getTesterName());
            model.setAssignTime(sheet.getCreateTime());
            model.setTesterName(sheet.getTesterName());
            model.setSubmitTime(sheet.getSubmitTime());
            model.setSemantic(BiologyDivisionSheet.BIOLOGY_ANNOTATION_SEMANTIC);
            modelList.add(model);
        }
        resList.setTotal(pagination.getTotal());
        resList.setList(modelList);
        resList.setPageSize(pageSize);
        resList.setPageNum(pageNo);
        return resList;
    }

    @RequestMapping(value = "/reStartAnnotation")
    public void reStartAnnotation(@RequestBody BiologyReAnnotationRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.reStartAnnotation(request,token);
    }

    @RequestMapping(value = "/resultCallBack", method = RequestMethod.POST)
    public void resultCallBack(@RequestBody BiologyReAnalysisDataResponse result)
    {
        service.resultCallBack(null,result);
    }

    @RequestMapping(value = "/familyTaskPaging")
    public PageInfo<BiologyAnnotationFamilyTask> searchFamilyTaskPaging(@RequestBody BiologyFamilyAnnotatioListRequest request)
    {
        return service.getFamilyTaskPaging(request);
    }

    @RequestMapping(value = "/familyTaskInfo/{id}")
    public List<BiologyAnnotationTask> getFamilyTaskInfo(@PathVariable String id)
    {
        return service.getFamilyTaskInfo(id);
    }

    @RequestMapping(value = "/startFamilyAnnotation", method = RequestMethod.POST)
    public void resultCallBack(@RequestBody BiologyFamilyStartAnalyRequest request,@RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.startFamilyAnnotation(request,token);
    }

    @RequestMapping(value = "/family/resultCallBack", method = RequestMethod.POST)
    public void familyResultCallBack(@RequestBody FamilyAnnotatioResponse result)
    {
        service.familyResultCallBack(null,result);
    }

    @RequestMapping(value = "/reTodoBiologyAnnotation", method = RequestMethod.POST)
    public void resultCallBack(@RequestBody(required = false) BiologyAnnotationTask request,@RequestHeader(value=RequestHeaders.MEMBER_TOKEN,required = false) String token)
    {
        service.reTodoBiologyAnnotation(request,token);
    }

    @RequestMapping(value = "/getBiologyAnnotationSheet/{sheetId}")
    public BiologyAnnotationSheetViewModel getBiologyAnnotationSheet(@PathVariable String sheetId)
    {
        return service.getBiologyAnnotationSheet(sheetId);
    }

    @RequestMapping(value = "/updateTaskState", method = RequestMethod.POST)
    public void updateTaskState(@RequestBody BiologyFamilyStartAnalyRequest request)
    {
        service.updateTaskState(request);
    }

    @RequestMapping(value = "/updateFamilyTaskState", method = RequestMethod.POST)
    public void updateFamilyTaskState(@RequestBody BiologyFamilyStartAnalyRequest request)
    {
        service.updateFamilyTaskState(request);
    }

    @RequestMapping(value = "/reAnalysisAnnotaionFile", method = RequestMethod.POST)
    public void reAnalysisAnnotaionFile(@RequestBody BiologyFamilyStartAnalyRequest request)
    {
        service.reAnalysisAnnotaionFile(request);
    }

    @RequestMapping(value = "/reFamilyAnalysisAnnotaionFile", method = RequestMethod.POST)
    public void reFamilyAnalysisAnnotaionFile(@RequestBody BiologyFamilyStartAnalyRequest request)
    {
        service.reFamilyAnalysisAnnotaionFile(request);
    }

    @RequestMapping(value = "/getTaskById/{testingTaskId}", method = RequestMethod.GET)
    public BiologyAnnotationTask getTaskById(@PathVariable String testingTaskId) {
        return service.getTaskById(testingTaskId);
    }
    @RequestMapping(value = "/getSheetListByTaskId/{taskId}")
    public List<BiologyAnnotationSheet> getSheetListByTaskId(@PathVariable String taskId)
    {
        return service.getSheetListByTaskId(taskId);
    }

    @RequestMapping(value = "/getByAnalaysisSampleId/{analysisSample}")
    public BioInfoAnnotate getByAnalaysisSampleId(@PathVariable String analysisSample)
    {
        return service.getByAnalaysisSampleId(analysisSample);
    }

    @RequestMapping(value = "/getFamilyRelationAnnotate/{analysisSample}")
    public BiologyFamilyRelationAnnotate getFamilyRelationAnnotate(@PathVariable String analysisSample)
    {
        return service.getFamilyRelationAnnotate(analysisSample);
    }
    @RequestMapping(value = "/getAnnotationFailOperateByTask/{taskId}")
    public BiologyAnnotationFailOperate getAnnotationFailOperateByTask(@PathVariable String taskId)
    {
        return service.getAnnotationFailOperateByTask(taskId);
    }
}
