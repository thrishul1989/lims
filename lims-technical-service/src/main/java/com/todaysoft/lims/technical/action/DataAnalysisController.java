package com.todaysoft.lims.technical.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.todaysoft.lims.technical.entity.CapCnvData;
import com.todaysoft.lims.technical.facade.DataAnalysisFacade;
import com.todaysoft.lims.technical.model.BiologyAnalysisSearchSheet;
import com.todaysoft.lims.technical.model.TechnicalAnalysisAssignableTaskSearcher;
import com.todaysoft.lims.technical.model.TechnicalAnalysisSheetModel;
import com.todaysoft.lims.technical.model.TestingSheetModel;
import com.todaysoft.lims.technical.model.TestingSheetRequest;
import com.todaysoft.lims.technical.model.TestingTaskModel;
import com.todaysoft.lims.technical.model.request.CancelCollectionCnvAnalysisResult;
import com.todaysoft.lims.technical.model.request.CapCnvDrawPicRequest;
import com.todaysoft.lims.technical.model.request.CapCnvRequest;
import com.todaysoft.lims.technical.model.request.CapCnvResultData;
import com.todaysoft.lims.technical.model.request.CollectionCapcnvPicRequest;
import com.todaysoft.lims.technical.model.request.CompareSampleReanalysisRequest;
import com.todaysoft.lims.technical.model.request.CompareSampleRequest;
import com.todaysoft.lims.technical.model.request.PhenoTypePointsRequest;
import com.todaysoft.lims.technical.model.request.SubmitCnvVerifyRequest;
import com.todaysoft.lims.technical.model.request.TechnicalReportRequest;
import com.todaysoft.lims.technical.model.request.UploadEvidenceRequest;
import com.todaysoft.lims.technical.model.response.CnvAnalysisDataModel;
import com.todaysoft.lims.technical.model.response.CnvAnalysisFamilyDataModel;
import com.todaysoft.lims.technical.model.response.CollectionCnvAnalysisPicResultList;
import com.todaysoft.lims.technical.model.response.DrawPictureModel;
import com.todaysoft.lims.technical.model.response.ListResponse;
import com.todaysoft.lims.technical.model.response.MutationStatisticsModel;
import com.todaysoft.lims.technical.model.response.UploadEvidenceRespModel;
import com.todaysoft.lims.technical.model.searcher.MutationStatisticsSearcher;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotate;
import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisResult;
import com.todaysoft.lims.technical.mybatis.entity.CompareSample;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.technical.service.IPhenotypeService;
import com.todaysoft.lims.technical.utils.Pagination;
import com.todaysoft.lims.technical.utils.RequestHeaders;

@RestController
@RequestMapping("/technicalanaly")
public class DataAnalysisController
{
    @Autowired
    private DataAnalysisFacade facade;
    
    @Autowired
    private MongoTemplate template;
    
    @Autowired
    private IPhenotypeService phenotypeService;
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping("/getcapcnvlist")
    public CnvAnalysisDataModel getItemList(@RequestBody CapCnvRequest request)
    {
        CnvAnalysisDataModel model = facade.getCapCnvDataModel(request);
        return model;
    }
    
    @RequestMapping("/ifFamilyAnalysis/{analysisSampleId}")
    public Boolean ifFamilyAnalysis(@PathVariable String analysisSampleId)
    {
        return facade.ifFamilyAnalysis(analysisSampleId);
    }
    
    @RequestMapping("/getAssginableTasks/{pageNo}/{pageSize}")
    public Pagination<TechnicalAnalysisTask> getAssginableTasks(@RequestBody TechnicalAnalysisAssignableTaskSearcher request, @PathVariable int pageNo, @PathVariable int pageSize, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        PageHelper.startPage(pageNo, pageSize);
        
        return facade.getAssginableTasks(request, token);
    }
    
    @RequestMapping("/claimTechnicalAnalysis/{familyGroupId}")
    public Integer claimTechnicalAnalysis(@PathVariable String familyGroupId, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        
        return facade.claimTechnicalAnalysis(familyGroupId, token);
    }
    
    /**
     * 发送打分接口，创建打分任务
     * @param request
     * @return
     */
    @RequestMapping("/phenoType/phenoTypePoints")
    private Integer phenoTypePoints(@RequestBody PhenoTypePointsRequest request)
    {
        return phenotypeService.phenoTypePoints(request);
    }
    
    @RequestMapping("/uploadEvidence")
    public UploadEvidenceRespModel uploadEvidence(@RequestBody UploadEvidenceRequest request)
    {
        return facade.uploadEvidence(request);
    }
    
    @RequestMapping(value = "/getQualitycontrolDataByaAnalysisId")
    public List<MutationStatisticsModel> getQualitycontrolDataByaAnalysisId(@RequestBody MutationStatisticsSearcher searcher)
    {
        return facade.getQualitycontrolDataByaAnalysisId(searcher);
    }
    
    @RequestMapping(value = "/searchTechnicalAnalysisTask")
    public List<TechnicalAnalysisTask> searchTechnicalAnalysisTask(@RequestBody TechnicalAnalysisTask searcher)
    {
        List<TechnicalAnalysisTask> task = facade.searchTechnicalAnalysisTask(searcher);
        return task;
    }
    
    @RequestMapping(value = "/getEvidenceByMongoId/{mongoId}")
    public String getEvidenceByMongoId(@PathVariable String mongoId)
    {
        return facade.getEvidenceByMongoId(mongoId);
    }
    
    @RequestMapping(value = "/getMutationDetail/{objectId}")
    public String getMutationDetail(@PathVariable String objectId)
    {
        return facade.getMutationDetail(objectId);
    }
    
    @RequestMapping("/getCapCnvPicture")
    public DrawPictureModel getCapCnvPicture(@RequestBody CapCnvDrawPicRequest request) throws IllegalAccessException, InvocationTargetException
    {
        DrawPictureModel model = facade.getCapCnvPicture(request);
        return model;
    }
    
    @RequestMapping("/cnvAnalysisDataList")
    private ListResponse<CapCnvData> cnvAnalysisDataList(@RequestBody CapCnvRequest request)
    {
        List<CapCnvData> records = facade.getCapCnvList(request);
        return new ListResponse<>(records);
    }
    
    @RequestMapping("/cnvAnalysisResultList")
    private ListResponse<CnvAnalysisResult> cnvAnalysisResultList(@RequestBody CapCnvRequest request)
    {
        return facade.cnvAnalysisResultList(request);
    }
    
    @RequestMapping("/saveAnalysisCapCnvResult")
    @ResponseBody
    private void cnvAnalysisResultList(@RequestBody CapCnvResultData request)
    {
        facade.saveAnalysisCapCnvResult(request);
    }
    
    @RequestMapping("/getCompareSampleList")
    @ResponseBody
    private List<CompareSample> getCompareSampleList(@RequestBody CompareSampleRequest request) throws IllegalAccessException, InvocationTargetException
    {
        return facade.getCompareSampleList(request);
    }
    
    @RequestMapping("/compareSampleReanalysis")
    public void compareSampleReanalysis(@RequestBody CompareSampleReanalysisRequest request)
    {
        facade.compareSampleReanalysis(request);
    }
    
    @RequestMapping("/submitCnvVerify")
    @ResponseBody
    private void submitCnvVerify(@RequestBody SubmitCnvVerifyRequest request)
    {
        facade.submitCnvVerify(request);
    }
    
    @RequestMapping(value = "/cancelSubmitCnvVerify")
    public void cancelSubmitCnvVerify(@RequestBody CancelCollectionCnvAnalysisResult request)
    {
        facade.cancelSubmitCnvVerify(request);
    }
    
    @RequestMapping("/getFamilyCapCnvList")
    public CnvAnalysisFamilyDataModel getFamilyCapCnvList(@RequestBody CapCnvRequest request)
    {
        CnvAnalysisFamilyDataModel model = facade.getFamilyCapCnvDataModel(request);
        return model;
    }
    
    @RequestMapping("/cnvFamilyAnalysisDataList")
    private List<Map> cnvFamilyAnalysisDataList(@RequestBody CapCnvRequest request)
    {
        List<Map> records = facade.getFamilyCapCnvList(request);
        return records;
    }
    
    @RequestMapping("/getAnnotateByfamily/{mongoId}")
    public List<BioInfoAnnotate> getAnnotateByfamily(@PathVariable String mongoId)
    {
        return facade.getAnnotateByfamily(mongoId);
    }
    
    @RequestMapping("/collectionCapcnvPic")
    public void collectionCapcnvPic(@RequestBody CollectionCapcnvPicRequest request)
    {
        facade.collectionCapcnvPic(request);
    }
    
    @RequestMapping("/collectionCapcnvData/{analysisSampleId}")
    public List<CollectionCnvAnalysisPicResultList> collectionCapcnvData(@PathVariable String analysisSampleId)
    {
        return facade.collectionCapcnvData(analysisSampleId);
    }
    
    @RequestMapping("/getTaskByFamilyId/{familyAnalysisId}")
    public List<TechnicalAnalysisTask> getTaskByFamilyId(@PathVariable String familyAnalysisId)
    {
        return facade.getTaskByFamilyId(familyAnalysisId);
    }
    
    @RequestMapping("/technicalReportOperate")
    public void technicalReportOperate(@RequestBody TechnicalReportRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        facade.technicalReportOperate(request, token);
    }
    
    @RequestMapping(value = "/reTodoTechnicalAnalysis/{id}", method = RequestMethod.POST)
    public void reTodoTechnicalAnalysis(@PathVariable String id, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        facade.reTodoTechnicalAnalysis(id, token);
    }
    
    @RequestMapping(value = "/completeSheetPaging/{pageNo}/{pageSize}")
    public PageInfo<TestingSheetModel> completeSheetPaging(@RequestBody TestingSheetRequest request, @PathVariable int pageNo, @PathVariable int pageSize)
    {
        PageHelper.startPage(pageNo, 10);
        
        List<BiologyAnalysisSearchSheet> list = facade.completeSheetPaging(request);
        PageInfo<BiologyAnalysisSearchSheet> pagination = new PageInfo<BiologyAnalysisSearchSheet>(list);
        PageInfo<TestingSheetModel> resList = new PageInfo<>();
        List<TestingSheetModel> modelList = new ArrayList<>();
        for (BiologyAnalysisSearchSheet sheet : pagination.getList())
        {
            TestingSheetModel model = new TestingSheetModel();
            model.setId(sheet.getId());
            model.setTaskName(BiologyAnalysisSearchSheet.BIOLOGY_ANALYSIS_NAME);
            model.setCode(sheet.getCode());
            model.setAssignerName(sheet.getTesterName());
            model.setAssignTime(sheet.getCreateTime());
            model.setTesterName(sheet.getTesterName());
            model.setSubmitTime(sheet.getSubmitTime());
            model.setSemantic(BiologyAnalysisSearchSheet.BIOLOGY_ANALYSIS_SEMANTIC);
            modelList.add(model);
        }
        resList.setTotal(pagination.getTotal());
        resList.setList(modelList);
        resList.setPageSize(pageSize);
        resList.setPageNum(pageNo);
        return resList;
    }
    
    @RequestMapping(value = "/getTaskById/{testingTaskId}", method = RequestMethod.GET)
    public TestingTaskModel getTaskById(@PathVariable String testingTaskId)
    {
        return facade.getTaskById(testingTaskId);
    }
    
    @RequestMapping(value = "/getSheetListByTaskId/{taskId}", method = RequestMethod.GET)
    public List<TechnicalAnalysisSheetModel> getSheetListByTaskId(@PathVariable String taskId)
    {
        return facade.getSheetListByTaskId(taskId);
    }
    
    @RequestMapping(value = "/checkTechnicalAnalysisIsFinished/{analysisSampleId}", method = RequestMethod.GET)
    public Boolean checkTechnicalAnalysisIsFinished(@PathVariable String analysisSampleId)
    {
        return facade.checkTechnicalAnalysisIsFinished(analysisSampleId);
    }
    
    @RequestMapping(value = "/checkTechnicalAnalysisHasHpoTask/{analysisSampleId}", method = RequestMethod.GET)
    public Boolean checkTechnicalAnalysisHasHpoTask(@PathVariable String analysisSampleId)
    {
        return facade.checkTechnicalAnalysisHasHpoTask(analysisSampleId);
    }
    
    @RequestMapping(value = "/getFamilyId/{id}", method = RequestMethod.GET)
    public String getFamilyId(@PathVariable String id)
    {
        return facade.getFamily(id);
    }
    
    @RequestMapping(value = "/getTestingSheet/{id}", method = RequestMethod.GET)
    public TechnicalAnalysisSheetModel getTestingSheet(@PathVariable String id)
    {
        return facade.getTestingSheet(id);
    }
    
    @RequestMapping(value = "/getTask/{familyGroupId}", method = RequestMethod.GET)
    public TechnicalAnalysisTask getTask(@PathVariable String familyGroupId)
    {
        return facade.getTask(familyGroupId);
    }
    
    @RequestMapping("/searchErrorTechnicalTask/{familyAnalysisId}")
    public List<TechnicalAnalysisTask> searchErrorTechnicalTask(@PathVariable String familyAnalysisId)
    {
        return facade.searchErrorTechnicalTask(familyAnalysisId);
    }
}
