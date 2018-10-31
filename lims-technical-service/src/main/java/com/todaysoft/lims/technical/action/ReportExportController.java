package com.todaysoft.lims.technical.action;

import com.todaysoft.lims.technical.model.request.ReportExportCnvAnalysisPicResultListRequest;
import com.todaysoft.lims.technical.model.request.ReportExportMutationExplainRequest;
import com.todaysoft.lims.technical.model.request.ReportExportMutationInfoRequest;
import com.todaysoft.lims.technical.model.request.UpdateMutationSourceRequest;
import com.todaysoft.lims.technical.model.response.ReportExportCnvAnalysisPicResultList;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportMutationInfo;
import com.todaysoft.lims.technical.service.IReportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/technicalanaly/reportexport")
public class ReportExportController
{
    @Autowired
    private IReportExportService service;
    
    @RequestMapping("/getReportExportMutationInfo")
    public Map<String, List<ReportExportMutationInfo>> getReportExportMutationInfo(@RequestBody ReportExportMutationInfoRequest request)
    {
       return service.getReportExportMutationInfo(request);
    }
    
    @RequestMapping("/getReportExportCnvAnalysisPicResultList")
    public Map<String, List<ReportExportCnvAnalysisPicResultList>> getReportExportCnvAnalysisPicResultList(@RequestBody ReportExportCnvAnalysisPicResultListRequest request)
    {
       return service.getReportExportCnvAnalysisPicResultList(request);
    }
    
    @RequestMapping("/getReportExportMutationExplainInfo")
    @ResponseBody
    public List<ReportExportMutationInfo> getReportExportMutationExplainInfo(@RequestBody ReportExportMutationExplainRequest request)
    {
       Assert.notNull(request.getObjectIds(), "对象id不能为空");
       return service.searchReportExportMutationInfoListByObjectIds(request.getObjectIds());
    }
    @RequestMapping(value = "/updateMutationSource",method = RequestMethod.POST)
    public void  updateMutationSource(@RequestBody UpdateMutationSourceRequest mutationSourceRequest)
    {
        service.updateMutationSource(mutationSourceRequest);
    }

}
