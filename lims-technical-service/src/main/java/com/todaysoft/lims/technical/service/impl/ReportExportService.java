package com.todaysoft.lims.technical.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.todaysoft.lims.technical.model.MutationSourceUpdateModel;
import com.todaysoft.lims.technical.model.request.ReportExportCnvAnalysisPicResultListRequest;
import com.todaysoft.lims.technical.model.request.ReportExportMutationInfoRequest;
import com.todaysoft.lims.technical.model.request.UpdateMutationSourceRequest;
import com.todaysoft.lims.technical.model.response.CnvAnalysisPicResultList;
import com.todaysoft.lims.technical.model.response.ReportExportCnvAnalysisPicResultList;
import com.todaysoft.lims.technical.mybatis.CnvAnalysisPicMapper;
import com.todaysoft.lims.technical.mybatis.CnvAnalysisResultMapper;
import com.todaysoft.lims.technical.mybatis.ReportExportCapcnvResultMapper;
import com.todaysoft.lims.technical.mybatis.ReportExportCnvAnalysisPicMapper;
import com.todaysoft.lims.technical.mybatis.ReportExportMutationInfoMapper;
import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisPic;
import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisResult;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportCapcnvResult;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportCnvAnalysisPic;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportMutationInfo;
import com.todaysoft.lims.technical.service.IClaimTemplateService;
import com.todaysoft.lims.technical.service.IReportExportService;
import com.todaysoft.lims.technical.utils.StringUtils;

@Service
public class ReportExportService implements IReportExportService
{
    @Autowired
    private ReportExportMutationInfoMapper reportExportMutationInfoMapper;
    
    @Autowired
    private ReportExportCapcnvResultMapper reportExportCapcnvResultMapper;
    
    @Autowired
    private ReportExportCnvAnalysisPicMapper reportExportCnvAnalysisPicMapper;
    
    @Autowired
    private CnvAnalysisResultMapper cnvAnalysisResultMapper;
    
    @Autowired
    private CnvAnalysisPicMapper cnvAnalysisPicMapper;
    
    @Autowired
    private IClaimTemplateService claimTemplateService;
    
    @Autowired
    private MongoTemplate template;
    
    private static Logger logger = LoggerFactory.getLogger(ReportExportService.class);
    
    @Override
    @Transactional
    public Map<String, List<ReportExportMutationInfo>> getReportExportMutationInfo(ReportExportMutationInfoRequest request)
    {
        Assert.notNull(request.getTaskId(), "任务id不能空");
        List<ReportExportMutationInfo> reportExportMutationInfos = reportExportMutationInfoMapper.selectByTaskId(request.getTaskId());
        for (ReportExportMutationInfo info : reportExportMutationInfos)
        {
            reportExportMutationInfoMapper.deleteByPrimaryKey(info.getId());
        }
        List<ReportExportMutationInfo> reportExportMutationInfoList = claimTemplateService.searchReportExportMutationInfoListByAnalsysiSampleId(request);
        if (reportExportMutationInfoList != null && reportExportMutationInfoList.size() > 0)
        {
            for (ReportExportMutationInfo reportExportMutationInfo : reportExportMutationInfoList)
            {
                reportExportMutationInfo.setTaskId(request.getTaskId());
                reportExportMutationInfoMapper.insert(reportExportMutationInfo);
            }
        }
        Map<String, List<ReportExportMutationInfo>> clinicalJudgmentToReportExportMutationInfoList = groupByClinicalJudgment(reportExportMutationInfoList);
        return clinicalJudgmentToReportExportMutationInfoList;
    }
    
    @Override
    public List<ReportExportMutationInfo> searchReportExportMutationInfoListByObjectIds(List<String> objectIds)
    {
        return claimTemplateService.searchReportExportMutationInfoListByObjectIds(objectIds);
    }
    
    private Map<String, List<ReportExportMutationInfo>> groupByClinicalJudgment(List<ReportExportMutationInfo> reportExportMutationInfos)
    {
        return reportExportMutationInfos.stream().collect(Collectors.groupingBy(ReportExportMutationInfo::getClinicalJudgment));
    }
    
    @Override
    @Transactional
    public Map<String, List<ReportExportCnvAnalysisPicResultList>> getReportExportCnvAnalysisPicResultList(ReportExportCnvAnalysisPicResultListRequest request)
    {
        Assert.notNull(request.getTaskId(), "任务id不能空");
        List<ReportExportCapcnvResult> reportExportCapcnvResults = reportExportCapcnvResultMapper.selectByTaskId(request.getTaskId());
        for (ReportExportCapcnvResult reportExportCapcnvResult : reportExportCapcnvResults)
        {
            reportExportCapcnvResultMapper.deleteByPrimaryKey(reportExportCapcnvResult.getId());
        }
        List<ReportExportCnvAnalysisPic> reportExportCnvAnalysisPics = reportExportCnvAnalysisPicMapper.selectByTaskId(request.getTaskId());
        for (ReportExportCnvAnalysisPic reportExportCnvAnalysisPic : reportExportCnvAnalysisPics)
        {
            reportExportCnvAnalysisPicMapper.deleteByPrimaryKey(reportExportCnvAnalysisPic.getId());
        }
        
        List<ReportExportCnvAnalysisPicResultList> reportExportCapcnvResultList = searchReportExportCapcnvResultByPicIdAndResultId(request);
        if (reportExportCapcnvResultList != null && reportExportCapcnvResultList.size() > 0)
        {
            //保存ReportExportCnvAnalysisPic,ReportExportCapcnvResult
            for (ReportExportCnvAnalysisPicResultList reportExportCnvAnalysisPicResultList : reportExportCapcnvResultList)
            {
                CnvAnalysisPic cnvAnalysisPic = reportExportCnvAnalysisPicResultList.getCnvAnalysisPic();
                if (cnvAnalysisPic != null)
                {
                    ReportExportCnvAnalysisPic reportExportCnvAnalysisPic = new ReportExportCnvAnalysisPic();
                    reportExportCnvAnalysisPic.setCnvAnalysisPicId(cnvAnalysisPic.getId());
                    reportExportCnvAnalysisPic.setTaskId(request.getTaskId());
                    reportExportCnvAnalysisPicMapper.insert(reportExportCnvAnalysisPic);
                    List<ReportExportCapcnvResult> reportExportCapcnvResultss = reportExportCnvAnalysisPicResultList.getReportExportCapcnvResultList();
                    if (reportExportCapcnvResultss != null && reportExportCapcnvResultss.size() > 0)
                    {
                        for (ReportExportCapcnvResult reportExportCapcnvResult : reportExportCapcnvResultss)
                        {
                            reportExportCapcnvResult.setTaskId(request.getTaskId());
                            reportExportCapcnvResultMapper.insert(reportExportCapcnvResult);
                        }
                    }
                }
            }
        }
        //分组
        return groupingByClinicalJudgment(reportExportCapcnvResultList);
    }
    
    private Map<String, List<ReportExportCnvAnalysisPicResultList>> groupingByClinicalJudgment(List<ReportExportCnvAnalysisPicResultList> reportExportCnvAnalysisPicResultLists)
    {
        HashMap<String, List<ReportExportCnvAnalysisPicResultList>> clinicalJudgmentToReportExportCnvAnalysisPicResultLists =
            new HashMap<String, List<ReportExportCnvAnalysisPicResultList>>();
        if (reportExportCnvAnalysisPicResultLists != null && reportExportCnvAnalysisPicResultLists.size() > 0)
        {
            for (ReportExportCnvAnalysisPicResultList reportExportCnvAnalysisPicResultList : reportExportCnvAnalysisPicResultLists)
            {
                if (null != reportExportCnvAnalysisPicResultList.getCnvAnalysisPic()
                    && null != reportExportCnvAnalysisPicResultList.getCnvAnalysisPic().getClinicalJudgment())
                {
                    List<ReportExportCnvAnalysisPicResultList> groupingReportExportCnvAnalysisPicResultLists =
                        clinicalJudgmentToReportExportCnvAnalysisPicResultLists.get(reportExportCnvAnalysisPicResultList.getCnvAnalysisPic()
                            .getClinicalJudgment());
                    if (groupingReportExportCnvAnalysisPicResultLists == null)
                    {
                        groupingReportExportCnvAnalysisPicResultLists = new ArrayList<ReportExportCnvAnalysisPicResultList>();
                        clinicalJudgmentToReportExportCnvAnalysisPicResultLists.put(reportExportCnvAnalysisPicResultList.getCnvAnalysisPic()
                            .getClinicalJudgment(), groupingReportExportCnvAnalysisPicResultLists);
                    }
                    groupingReportExportCnvAnalysisPicResultLists.add(reportExportCnvAnalysisPicResultList);
                }
            }
        }
        return clinicalJudgmentToReportExportCnvAnalysisPicResultLists;
    }
    
    public List<ReportExportCnvAnalysisPicResultList> searchReportExportCapcnvResultByPicIdAndResultId(ReportExportCnvAnalysisPicResultListRequest request)
    {
        if (request.getCnvAnalysisPicIds() != null && request.getCnvAnalysisPicIds().size() > 0)
        {
            List<CnvAnalysisPicResultList> cnvAnalysisPicResultLists = new ArrayList<CnvAnalysisPicResultList>();
            for (String cnvAnalysisPicId : request.getCnvAnalysisPicIds())
            {
                CnvAnalysisPic cnvAnalysisPic = cnvAnalysisPicMapper.selectByPrimaryKey(cnvAnalysisPicId);
                CnvAnalysisPicResultList cnvAnalysisPicResultList = new CnvAnalysisPicResultList();
                cnvAnalysisPicResultLists.add(cnvAnalysisPicResultList);
                cnvAnalysisPicResultList.setCnvAnalysisPic(cnvAnalysisPic);
                if (cnvAnalysisPic != null && request.getCnvAnalysisResultIds() != null && request.getCnvAnalysisResultIds().size() > 0)
                {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("ids", request.getCnvAnalysisResultIds());
                    params.put("picId", cnvAnalysisPicId);
                    List<CnvAnalysisResult> cnvResultList = cnvAnalysisResultMapper.getByIdsAndPicId(params);
                    cnvAnalysisPicResultList.setCnvAnalysisResultList(cnvResultList);
                }
            }
            
            //转换CnvAnalysisResult为ReportExportCapcnvResult
            if (cnvAnalysisPicResultLists.size() > 0)
            {
                List<ReportExportCnvAnalysisPicResultList> reportExportCnvAnalysisPicResultLists = new ArrayList<ReportExportCnvAnalysisPicResultList>();
                for (CnvAnalysisPicResultList cnvAnalysisPicResultList : cnvAnalysisPicResultLists)
                {
                    ReportExportCnvAnalysisPicResultList reportExportCnvAnalysisPicResultList = new ReportExportCnvAnalysisPicResultList();
                    reportExportCnvAnalysisPicResultLists.add(reportExportCnvAnalysisPicResultList);
                    reportExportCnvAnalysisPicResultList.setCnvAnalysisPic(cnvAnalysisPicResultList.getCnvAnalysisPic());
                    List<CnvAnalysisResult> cnvAnalysisResults = cnvAnalysisPicResultList.getCnvAnalysisResultList();
                    if (cnvAnalysisResults != null && cnvAnalysisResults.size() > 0)
                    {
                        List<ReportExportCapcnvResult> reportExportCapcnvResults = new ArrayList<ReportExportCapcnvResult>();
                        reportExportCnvAnalysisPicResultList.setReportExportCapcnvResultList(reportExportCapcnvResults);
                        for (CnvAnalysisResult cnvAnalysisResult : cnvAnalysisResults)
                        {
                            ReportExportCapcnvResult reportExportCapcnvResult =
                                convertCnvAnalysisResultToReportExportCapcnvResult(request.getTaskId(), cnvAnalysisResult);
                            reportExportCapcnvResults.add(reportExportCapcnvResult);
                        }
                    }
                }
                return reportExportCnvAnalysisPicResultLists;
            }
        }
        return null;
    }
    
    private ReportExportCapcnvResult convertCnvAnalysisResultToReportExportCapcnvResult(String taskId, CnvAnalysisResult cnvAnalysisResult)
    {
        ReportExportCapcnvResult reportExportCapcnvResult = new ReportExportCapcnvResult();
        reportExportCapcnvResult.setCnvAnalysisPicId(cnvAnalysisResult.getCnvAnalysisPicId());
        reportExportCapcnvResult.setExon(cnvAnalysisResult.getExon());
        reportExportCapcnvResult.setGene(cnvAnalysisResult.getGene());
        reportExportCapcnvResult.setInheritSource(cnvAnalysisResult.getInheritSource());
        reportExportCapcnvResult.setMissingArea(cnvAnalysisResult.getMissingArea());
        reportExportCapcnvResult.setRelatedDisease(cnvAnalysisResult.getRelatedDisease());
        reportExportCapcnvResult.setTaskId(taskId);
        reportExportCapcnvResult.setPathogenicLevel(cnvAnalysisResult.getPathogenicLevel());
        reportExportCapcnvResult.setMutationComment("区域内OMIM收录的致病基因分析见上表。");
        reportExportCapcnvResult.setAreaSize("KB");
        reportExportCapcnvResult.setMissingType(cnvAnalysisResult.getClinicalJudgment());
        return reportExportCapcnvResult;
    }
    
    @Override
    public void updateMutationSource(UpdateMutationSourceRequest mutationSourceRequest)
    {
        try
        {
            if (StringUtils.isNotEmpty(mutationSourceRequest) && null != mutationSourceRequest.getMutationSourceUpdateModels())
            {
                for (MutationSourceUpdateModel msum : mutationSourceRequest.getMutationSourceUpdateModels())
                {
                    if (StringUtils.isNotBlank(msum.getId()))
                    {
                        DBCollection mutationCollection = template.getCollection("analysis-mutation");
                        BasicDBObject query = new BasicDBObject("_id", new ObjectId(msum.getId()));
                        BasicDBObject update = new BasicDBObject("$set", new BasicDBObject("遗传来源.name", msum.getMutationSource()));
                        // BasicDBObject update =new BasicDBObject("$set",new BasicDBObject("遗传来源",new BasicDBObject("name",msum.getMutationSource())));
                        mutationCollection.update(query, update);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        
    }
    
}
