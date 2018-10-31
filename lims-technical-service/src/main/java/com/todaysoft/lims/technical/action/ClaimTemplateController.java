package com.todaysoft.lims.technical.action;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.technical.model.DataColAndMutationDataModel;
import com.todaysoft.lims.technical.model.TechnicalAnalysisAddVerify;
import com.todaysoft.lims.technical.model.TechnicalAnalysisSampleModel;
import com.todaysoft.lims.technical.model.VariableModel;
import com.todaysoft.lims.technical.model.request.ClaimTemplateRequest;
import com.todaysoft.lims.technical.model.request.DoctorIfColletionRequest;
import com.todaysoft.lims.technical.model.request.FilterItemsRequest;
import com.todaysoft.lims.technical.model.request.GroupNameDetailsRequest;
import com.todaysoft.lims.technical.model.request.MutationCollectionRequest;
import com.todaysoft.lims.technical.model.request.MutationDataRequest;
import com.todaysoft.lims.technical.model.request.MutationFilterFormRequest;
import com.todaysoft.lims.technical.model.request.SubmitTechnicalTaskRequest;
import com.todaysoft.lims.technical.model.request.TechnicalAddVerifyRequest;
import com.todaysoft.lims.technical.model.request.TechnicalCollectionRequest;
import com.todaysoft.lims.technical.model.response.ClaimTemplateColumnForDoctor;
import com.todaysoft.lims.technical.model.response.ClaimTemplateModel;
import com.todaysoft.lims.technical.mybatis.entity.ClaimTemplate;
import com.todaysoft.lims.technical.mybatis.entity.FilterItems;
import com.todaysoft.lims.technical.service.IClaimTemplateService;
import com.todaysoft.lims.technical.service.IMessageSendService;
import com.todaysoft.lims.technical.service.ITestingScheduleService;
import com.todaysoft.lims.technical.utils.Collections3;
import com.todaysoft.lims.technical.utils.Pagination;
import com.todaysoft.lims.technical.utils.RequestHeaders;
import com.todaysoft.lims.technical.utils.StringUtils;

@RestController
@RequestMapping("/technicalanaly/claimTemplate")
public class ClaimTemplateController
{
    @Autowired
    private IClaimTemplateService service;
    
    @Autowired
    private IMessageSendService messageSendService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    private static final Logger logger = LoggerFactory.getLogger(ClaimTemplateController.class);
    
    @RequestMapping("/paging")
    public Pagination<ClaimTemplate> pager(@RequestBody ClaimTemplateRequest request)
    {
        return service.pager(request);
        
    }
    
    @RequestMapping("/getItemList")
    public List<FilterItems> getItemList(@RequestBody FilterItemsRequest request)
    {
        FilterItems record = new FilterItems();
        BeanUtils.copyProperties(request, record);
        return service.getItemList(record);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody ClaimTemplateRequest request)
    {
        return service.validate(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody ClaimTemplateRequest request)
    {
        service.create(request);
    }
    
    @RequestMapping("/saveItem")
    public void saveItem(@RequestBody FilterItemsRequest request)
    {
        
        service.saveItem(request);
    }
    
    @RequestMapping("/getByIdForView")
    public ClaimTemplateModel getByIdForView(@RequestBody ClaimTemplateRequest request)
    {
        return service.getByIdForView(request.getId());
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody ClaimTemplateRequest request)
    {
        service.delete(request);
    }
    
    @RequestMapping("/setPriFlag")
    public void setPriFlag(@RequestBody ClaimTemplateRequest request)
    {
        service.setPriFlag(request);
    }
    
    @RequestMapping("/getById")
    public ClaimTemplateModel getById(@RequestBody ClaimTemplateRequest request)
    {
        return service.getById(request.getId());
    }
    
    @RequestMapping("/update")
    public void update(@RequestBody ClaimTemplateRequest request)
    {
        service.update(request);
    }
    
    //获取分析要求列表
    @RequestMapping(value = "/claimTemplateList", method = RequestMethod.GET)
    public List<ClaimTemplate> claimTemplateList()
    {
        return service.claimTemplateList();
    }
    
    @RequestMapping(value = "/queryRecentFilterList")
    public List<String> queryRecentFilterList(@RequestBody MutationFilterFormRequest request)
    {
        return service.queryRecentFilterList(request);
    }
    
    @RequestMapping("/getClaimTempleColumnForDoctor")
    public List<ClaimTemplateColumnForDoctor> getClaimTempleColumnForDoctor()
    {
        return service.getClaimTempleColumnForDoctor();
    }
    
    @RequestMapping(value = "/saveMutationFilter", method = RequestMethod.POST)
    public void saveMutationFilter(@RequestBody MutationFilterFormRequest request)
    {
        service.saveMutationFilter(request);
    }
    
    @RequestMapping(value = "/queryMutationDataByFilter")
    public DataColAndMutationDataModel queryMutationListByFilter(@RequestBody MutationDataRequest request)
    {
        return service.queryMutationDataByFilter(request);
    }
    
    @RequestMapping(value = "/ifColletion", method = RequestMethod.POST)
    public boolean ifColletion(@RequestBody DoctorIfColletionRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        return service.ifColletion(request, token);
    }
    
    @RequestMapping(value = "/getFamilySampleByFamilyId/{familyId}", method = RequestMethod.POST)
    public List<TechnicalAnalysisSampleModel> getFamilySampleByFamilyId(@PathVariable String familyId)
    {
        return service.getFamilySampleByFamilyId(familyId);
    }
    
    @RequestMapping(value = "/queryMutationDataByFilterForDownload", method = RequestMethod.POST)
    public DataColAndMutationDataModel queryMutationDataByFilterForDownload(@RequestBody MutationDataRequest request)
    {
        return service.queryMutationDataByFilterForDownload(request);
        
    }
    
    @RequestMapping(value = "/getGroupDetails")
    public Map<String, String> getGroupDetails(@RequestBody GroupNameDetailsRequest request)
    {
        return service.getGroupDetails(request.getGroupName(), request.getId());
        
    }
    
    @RequestMapping(value = "/collection", method = RequestMethod.POST)
    public Integer collection(@RequestBody TechnicalCollectionRequest request)
    {
        return service.collection(request);
        
    }
    
    @RequestMapping(value = "/cancelMutationCollection", method = RequestMethod.POST)
    public Integer cancelMutationCollection(@RequestBody MutationCollectionRequest request)
    {
        return service.cancelMutationCollection(request);
        
    }
    
    @RequestMapping(value = "/submitTechnicalTask/{familyGroupId}", method = RequestMethod.POST)
    public void submitTechnicalTask(@PathVariable String familyGroupId)
    {
        service.submitTechnicalTask(familyGroupId);
        
    }
    
    /**
     * 复核确认 step1
     * @param request
     */
    @RequestMapping(value = "/submitRecheckTechnicalTask", method = RequestMethod.POST)
    @Transactional(rollbackFor = {Exception.class})
    public VariableModel submitRecheckTechnicalTask(@RequestBody SubmitTechnicalTaskRequest request)
    {
        service.submitRecheckTechnicalTask(request);
        VariableModel model = service.startAnalsyisSchedule(request.getFamilyGroupId());
        return model;
    }
    
    /**
     * 复核确认 step2
     * @param request
     */
    @RequestMapping(value = "/startAnalsyisSchedule", method = RequestMethod.POST)
    public void startAnalsyisSchedule(@RequestBody VariableModel model)
    {
        if (Collections3.isNotEmpty(model.getTasks()))
        {
            testingScheduleService.updateRedundantTask(model.getTasks());
        }
        if (StringUtils.isNotEmpty(model.getScheduleIds()))
        {
            messageSendService.sendReportMessage(model);
        }
    }
    
    @RequestMapping("/getMod/{familyGroupId}")
    public VariableModel getMod(@PathVariable String familyGroupId)
    {
        VariableModel model = service.getMod(familyGroupId);
        if (Collections3.isNotEmpty(model.getTasks()))
        {
            testingScheduleService.updateRedundantTask(model.getTasks());
        }
        return model;
    }
    
    @RequestMapping("/myCollectionCount/{analysisSampleId}/{userId}/{technicalTaskId}")
    public Integer myCollectionCount(@PathVariable String analysisSampleId, @PathVariable String userId, @PathVariable String technicalTaskId)
    {
        return service.myCollectionCount(analysisSampleId, userId, technicalTaskId);
    }
    
    @RequestMapping("/searchMutationListByAnalsysiSampleId/{analysisSampleId}")
    public DataColAndMutationDataModel searchMutationListByAnalsysiSampleId(@PathVariable String analysisSampleId)
    {
        return service.searchMutationListByAnalsysiSampleId(analysisSampleId);
    }
    
    @RequestMapping(value = "/saveAddVerify", method = RequestMethod.POST)
    public void saveAddVerify(@RequestBody TechnicalAddVerifyRequest request)
    {
        service.saveAddVerify(request);
    }
    
    @RequestMapping(value = "/getAddVerifyDataByFamilyId/{familyId}", method = RequestMethod.POST)
    public List<TechnicalAnalysisAddVerify> getAddVerifyDataByFamilyId(@PathVariable String familyId)
    {
        return service.getAddVerifyDataByFamilyId(familyId);
    }
    
    @RequestMapping(value = "/deleteVerifyDataById/{id}", method = RequestMethod.DELETE)
    public void deleteVerifyDataById(@PathVariable String id)
    {
        service.deleteVerifyDataById(id);
    }
    
    @RequestMapping(value = "/saveAddVerifyNew", method = RequestMethod.POST)
    public void saveAddVerifyNew(@RequestBody TechnicalAddVerifyRequest request)
    {
        VariableModel model = service.saveAddVerifyNew(request);
        
        if (Collections3.isNotEmpty(model.getTasks()))
        {
            testingScheduleService.updateRedundantTask(model.getTasks());
        }
        if (StringUtils.isNotEmpty(model.getScheduleIds()))
        {
            messageSendService.sendReportMessage(model);
        }
    }
}
