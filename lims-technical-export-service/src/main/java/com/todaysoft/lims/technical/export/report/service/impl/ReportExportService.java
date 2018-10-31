package com.todaysoft.lims.technical.export.report.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.technical.export.TechnicalExportServiceApplication;
import com.todaysoft.lims.technical.export.mybatis.mapper.CnvAnalysisPicMapper;
import com.todaysoft.lims.technical.export.mybatis.mapper.ReportExportCapcnvResultMapper;
import com.todaysoft.lims.technical.export.mybatis.mapper.ReportExportCnvAnalysisPicMapper;
import com.todaysoft.lims.technical.export.mybatis.mapper.ReportExportDetectionResultMapper;
import com.todaysoft.lims.technical.export.mybatis.mapper.ReportExportMethodColumnConfigMapper;
import com.todaysoft.lims.technical.export.mybatis.mapper.ReportExportMutationInfoMapper;
import com.todaysoft.lims.technical.export.mybatis.mapper.ReportExportSampleBaseInfoMapper;
import com.todaysoft.lims.technical.export.mybatis.model.CnvAnalysisPic;
import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerReportPicture;
import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerReportSample;
import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerResultInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportCapcnvResult;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportCnvAnalysisPic;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResult;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResultPicture;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMethodColumnConfig;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMutationInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportSampleBaseInfo;
import com.todaysoft.lims.technical.export.report.dto.ReportExportCnvAnalysisPicResultList;
import com.todaysoft.lims.technical.export.report.dto.ReportExportData;
import com.todaysoft.lims.technical.export.report.model.request.ReportExportDetectionResultPictureRequest;
import com.todaysoft.lims.technical.export.report.model.request.ReportExportMutationExplainRequest;
import com.todaysoft.lims.technical.export.report.model.request.ReportExportUpdateRequest;
import com.todaysoft.lims.technical.export.report.service.IReportExportService;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ReportExportService implements IReportExportService
{
    @Autowired
    private ReportExportSampleBaseInfoMapper reportExportSampleBaseInfoMapper;
    @Autowired
    private ReportExportCapcnvResultMapper reportExportCapcnvResultMapper;
    @Autowired
    private ReportExportCnvAnalysisPicMapper reportExportCnvAnalysisPicMapper;
    @Autowired
    private ReportExportMutationInfoMapper reportExportMutationInfoMapper;
    @Autowired
    private ReportExportDetectionResultMapper reportExportDetectionResultMapper;
    @Autowired
    private CnvAnalysisPicMapper cnvAnalysisPicMapper;
    @Autowired
    private ReportExportMethodColumnConfigMapper reportExportMethodColumnConfigMapper;
    @Autowired
    private GenericAnalysisReportGenerator genericAnalysisReportGenerator;
    
    @Autowired
    private RestTemplate template;
    private static Logger log = LoggerFactory.getLogger(ReportExportService.class);
    @Override
    public ReportExportSampleBaseInfo getReportExportSampleBaseInfoByTaskId(String taskId)
    {
        List<ReportExportSampleBaseInfo> reportExportSampleBaseInfos= reportExportSampleBaseInfoMapper.selectByTaskId(taskId);
        if(reportExportSampleBaseInfos!=null&&reportExportSampleBaseInfos.size()>0) {
            return reportExportSampleBaseInfos.get(0);
        }
        return null;
    }

    @Override
    public Map<String, List<ReportExportMutationInfo>> getReportExportMutationInfo(String taskId)
    {
        List<ReportExportMutationInfo> reportExportMutationInfos= reportExportMutationInfoMapper.selectByTaskId(taskId);
        List<String> objectIds=new ArrayList<String>();
        for(ReportExportMutationInfo reportExportMutationInfo:reportExportMutationInfos) {
            if(StringUtils.isNotBlank(reportExportMutationInfo.getObjectId())) {
                objectIds.add(reportExportMutationInfo.getObjectId());
            }
        }
        if(!objectIds.isEmpty()) {
            ReportExportMutationExplainRequest request=new ReportExportMutationExplainRequest();
            request.setObjectIds(objectIds);
            List<ReportExportMutationInfo> reportExportMutationExplains = getReportExportMutationExplainInfo(request);
            Map<String, ReportExportMutationInfo> objectIdToReportExportMutationInfo=new HashMap<String, ReportExportMutationInfo>();
            for(ReportExportMutationInfo reportExportMutationExplain:reportExportMutationExplains) {
                objectIdToReportExportMutationInfo.put(reportExportMutationExplain.getObjectId(), reportExportMutationExplain);
            }
            for(ReportExportMutationInfo reportExportMutationInfo:reportExportMutationInfos) {
                if(objectIdToReportExportMutationInfo.get(reportExportMutationInfo.getObjectId())!=null) {
                    reportExportMutationInfo.setReportExportMutationInfoExplain(objectIdToReportExportMutationInfo.get(reportExportMutationInfo.getObjectId()).getReportExportMutationInfoExplain());
                }
            }
        }

        //分组
        Map<String, List<ReportExportMutationInfo>> clinicalJudgmentToReportExportMutationInfoList = groupByClinicalJudgment(reportExportMutationInfos);
        return clinicalJudgmentToReportExportMutationInfoList;
    }
    
    private Map<String, List<ReportExportMutationInfo>> groupByClinicalJudgment(List<ReportExportMutationInfo> reportExportMutationInfos)
    {
        return reportExportMutationInfos.stream().collect(Collectors.groupingBy(ReportExportMutationInfo::getClinicalJudgment));
    }

    @Override
    public Map<String, List<ReportExportCnvAnalysisPicResultList>> getReportExportCnvAnalysisPicResultList(String taskId)
    {
        List<ReportExportCnvAnalysisPic> reportExportCnvAnalysisPics = reportExportCnvAnalysisPicMapper.selectByTaskId(taskId);
        List<ReportExportCapcnvResult> reportExportCapcnvResults = reportExportCapcnvResultMapper.selectByTaskId(taskId);
        Map<String, List<ReportExportCapcnvResult>> cnvAnalysisPicIdToReportExportCapcnvResults = reportExportCapcnvResults.stream().collect(Collectors.groupingBy(ReportExportCapcnvResult::getCnvAnalysisPicId));
        List<ReportExportCnvAnalysisPicResultList> reportExportCnvAnalysisPicResultLists=new ArrayList<ReportExportCnvAnalysisPicResultList>();
        for(ReportExportCnvAnalysisPic reportExportCnvAnalysisPic:reportExportCnvAnalysisPics) {
            ReportExportCnvAnalysisPicResultList  reportExportCnvAnalysisPicResultList=new ReportExportCnvAnalysisPicResultList();
            reportExportCnvAnalysisPicResultLists.add(reportExportCnvAnalysisPicResultList);
            CnvAnalysisPic cnvAnalysisPic=cnvAnalysisPicMapper.selectByPrimaryKey(reportExportCnvAnalysisPic.getCnvAnalysisPicId());
            reportExportCnvAnalysisPicResultList.setCnvAnalysisPic(cnvAnalysisPic);
            reportExportCnvAnalysisPicResultList.setReportExportCapcnvResultList(cnvAnalysisPicIdToReportExportCapcnvResults.get(reportExportCnvAnalysisPic.getCnvAnalysisPicId()));
        }
        //分组
        return groupingByClinicalJudgment(reportExportCnvAnalysisPicResultLists);
    }
    
    private Map<String, List<ReportExportCnvAnalysisPicResultList>> groupingByClinicalJudgment(List<ReportExportCnvAnalysisPicResultList> reportExportCnvAnalysisPicResultLists){
        HashMap<String,List<ReportExportCnvAnalysisPicResultList>> clinicalJudgmentToReportExportCnvAnalysisPicResultLists=new HashMap<String,List<ReportExportCnvAnalysisPicResultList>>();
        for(ReportExportCnvAnalysisPicResultList reportExportCnvAnalysisPicResultList:reportExportCnvAnalysisPicResultLists) {
           if(null!=reportExportCnvAnalysisPicResultList.getCnvAnalysisPic()&&null!=reportExportCnvAnalysisPicResultList.getCnvAnalysisPic().getClinicalJudgment()) {
               List<ReportExportCnvAnalysisPicResultList> groupingReportExportCnvAnalysisPicResultLists=clinicalJudgmentToReportExportCnvAnalysisPicResultLists.get(reportExportCnvAnalysisPicResultList.getCnvAnalysisPic().getClinicalJudgment());
               if(groupingReportExportCnvAnalysisPicResultLists==null) {
                   groupingReportExportCnvAnalysisPicResultLists=new ArrayList<ReportExportCnvAnalysisPicResultList>();
                   clinicalJudgmentToReportExportCnvAnalysisPicResultLists.put(reportExportCnvAnalysisPicResultList.getCnvAnalysisPic().getClinicalJudgment(),groupingReportExportCnvAnalysisPicResultLists);
               }
               groupingReportExportCnvAnalysisPicResultLists.add(reportExportCnvAnalysisPicResultList);
           }
        }
        return clinicalJudgmentToReportExportCnvAnalysisPicResultLists;
    }

    @Override
    public void updateReportExport(ReportExportUpdateRequest request)
    {
        ReportExportSampleBaseInfo reportExportSampleBaseInfo= request.getReportExportSampleBaseInfo();
        reportExportSampleBaseInfoMapper.updateByPrimaryKey(reportExportSampleBaseInfo);
        List<ReportExportCapcnvResult> reportExportCapcnvResults  =request.getReportExportCapcnvResults();
        
        //报告中解读部分的值填写到capcnv列表中一起修改
        if(request.getReportExportCapcnvResultExplains()!=null&&request.getReportExportCapcnvResultExplains().size()>0) {
            Map<String, List<ReportExportCapcnvResult>> idToReportExportCapcnvResults=request.getReportExportCapcnvResultExplains().stream().collect(Collectors.groupingBy(ReportExportCapcnvResult::getId));
            if(reportExportCapcnvResults!=null&&reportExportCapcnvResults.size()>0) {
                for(ReportExportCapcnvResult reportExportCapcnvResult:reportExportCapcnvResults) {
                    if(idToReportExportCapcnvResults.get(reportExportCapcnvResult.getId())!=null&&idToReportExportCapcnvResults.get(reportExportCapcnvResult.getId()).size()>0) {
                        reportExportCapcnvResult.setAreaSize(idToReportExportCapcnvResults.get(reportExportCapcnvResult.getId()).get(0).getAreaSize());
                        reportExportCapcnvResult.setMissingType(idToReportExportCapcnvResults.get(reportExportCapcnvResult.getId()).get(0).getMissingType());
                        reportExportCapcnvResult.setPathogenicLevel(idToReportExportCapcnvResults.get(reportExportCapcnvResult.getId()).get(0).getPathogenicLevel());
                        reportExportCapcnvResult.setMutationComment(idToReportExportCapcnvResults.get(reportExportCapcnvResult.getId()).get(0).getMutationComment());
                    }
                }
            }  
        }
        if(reportExportCapcnvResults!=null&&reportExportCapcnvResults.size()>0) {
            for(ReportExportCapcnvResult reportExportCapcnvResult:reportExportCapcnvResults) {
                reportExportCapcnvResultMapper.updateByPrimaryKey(reportExportCapcnvResult);
            }
        }
        List<ReportExportMutationInfo> reportExportMutationInfos=request.getReportExportMutationInfos();
        if(reportExportMutationInfos!=null&&reportExportMutationInfos.size()>0) {
            for(ReportExportMutationInfo reportExportMutationInfo:reportExportMutationInfos) {
                reportExportMutationInfoMapper.updateByPrimaryKey(reportExportMutationInfo);
            }
        }
        
        List<ReportExportDetectionResult> reportExportDetectionResults= request.getMlpaDetectionResults();
        if(reportExportDetectionResults!=null&&reportExportDetectionResults.size()>0) {
            for(ReportExportDetectionResult reportExportDetectionResult:reportExportDetectionResults) {
                reportExportDetectionResult.setDetails(JSON.toJSONString(reportExportDetectionResult.getResult()));
                reportExportDetectionResultMapper.updateByPrimaryKey(reportExportDetectionResult);
            }
        }
        List<ReportExportDetectionResult> dtmutationDetectionResults= request.getDtmutationDetectionResults();
        if(dtmutationDetectionResults!=null&&dtmutationDetectionResults.size()>0) {
            for(ReportExportDetectionResult reportExportDetectionResult:dtmutationDetectionResults) {
                reportExportDetectionResult.setDetails(JSON.toJSONString(reportExportDetectionResult.getResult()));
                reportExportDetectionResultMapper.updateByPrimaryKey(reportExportDetectionResult);
            }
        }
    }
    
    

    @Override
    public List<ReportExportDetectionResult> getReportExportDetectionResult(ReportExportDetectionResult reportExportDetectionResult)
    {
        if(StringUtils.isBlank(reportExportDetectionResult.getClinicalJudgment())||StringUtils.isBlank(reportExportDetectionResult.getTaskId())||StringUtils.isBlank(reportExportDetectionResult.getMethodName())) {
            Assert.notNull(reportExportDetectionResult, "3个参数不能为空");
        }
        List<ReportExportDetectionResult> reportExportMutationInfos = reportExportDetectionResultMapper.selectByTaskIdAndClinicalJudgmentAndMethodName(reportExportDetectionResult);
        return reportExportMutationInfos;
    }
    
    @Override
    public String generateReport(String taskId)
    {
        String fileName= checkTemplateFile();
        if(fileName==null) {
            log.error("模板文件不存在");
            return null;
        }
        ReportExportSampleBaseInfo reportExportSampleBaseInfo= getReportExportSampleBaseInfoByTaskId(taskId);
        Map<String, List<ReportExportMutationInfo>> clinicalJudgmentToReportExportMutationInfoList= getReportExportMutationInfo(taskId);
        Map<String, List<ReportExportCnvAnalysisPicResultList>> clinicalJudgmentToReportExportCnvAnalysisPicResultLists= getReportExportCnvAnalysisPicResultList(taskId);
        List<ReportExportMethodColumnConfig> mlpaDetectionResultColumns= getReportExportMethodColumnConfig(ReportExportMethodColumnConfig.METHOD_MLPA);
        List<ReportExportMethodColumnConfig> dtDetectionResultMutationColumns= getReportExportMethodColumnConfig(ReportExportMethodColumnConfig.METHOD_DTMUTATION);
        ReportExportData reportExportData=new ReportExportData();
        reportExportData.setReportExportSampleBaseInfo(reportExportSampleBaseInfo);
        reportExportData.setClinicalJudgmentToReportExportMutationInfoList(clinicalJudgmentToReportExportMutationInfoList);
        
        reportExportData.setClinicalJudgmentToReportExportCnvAnalysisPicResultLists(clinicalJudgmentToReportExportCnvAnalysisPicResultLists);
        reportExportData.setMlpaDetectionResultColumns(mlpaDetectionResultColumns);
        reportExportData.setDtDetectionResultColumns(dtDetectionResultMutationColumns);
        ReportExportDetectionResult reportExportDetectionResult=new ReportExportDetectionResult();
        reportExportDetectionResult.setMethodName(ReportExportMethodColumnConfig.METHOD_MLPA);
        reportExportDetectionResult.setClinicalJudgment(ReportExportDetectionResult.CLINICALJUDGMENT_CLINICALPHENOTYPEHIGHCORRELATION);
        reportExportDetectionResult.setTaskId(taskId);
        List<ReportExportDetectionResult> mlpaHighCorrelationReportExportDetectionResults = getReportExportDetectionResult(reportExportDetectionResult);
        reportExportData.setMlpaHighCorrelationReportExportDetectionResults(mlpaHighCorrelationReportExportDetectionResults);
        reportExportDetectionResult.setClinicalJudgment(ReportExportDetectionResult.CLINICALJUDGMENT_CLINICALPHENOTYPECORRELATION);
        List<ReportExportDetectionResult> mlpaCorrelationReportExportDetectionResults = getReportExportDetectionResult(reportExportDetectionResult);
        reportExportData.setMlpaCorrelationReportExportDetectionResults(mlpaCorrelationReportExportDetectionResults);
        reportExportDetectionResult.setMethodName(ReportExportMethodColumnConfig.METHOD_DTMUTATION);
        reportExportDetectionResult.setClinicalJudgment(ReportExportDetectionResult.CLINICALJUDGMENT_CLINICALPHENOTYPEHIGHCORRELATION);
        List<ReportExportDetectionResult> dtHighCorrelationReportExportDetectionResults=getReportExportDetectionResult(reportExportDetectionResult);
        reportExportData.setDtHighCorrelationReportExportDetectionResults(dtHighCorrelationReportExportDetectionResults);
        reportExportDetectionResult.setClinicalJudgment(ReportExportDetectionResult.CLINICALJUDGMENT_CLINICALPHENOTYPECORRELATION);
        List<ReportExportDetectionResult> dtCorrelationReportExportDetectionResults=getReportExportDetectionResult(reportExportDetectionResult);
        reportExportData.setDtCorrelationReportExportDetectionResults(dtCorrelationReportExportDetectionResults);
        List<MutationFamilySangerResultInfo> mutationFamilySangerResultInfos = getMutationFamilySangerResultInfoByTaskId(taskId);
        reportExportData.setMutationFamilySangerResultInfos(mutationFamilySangerResultInfos);
        ReportExportDetectionResultPictureRequest reportExportDetectionResultPictureRequest=new ReportExportDetectionResultPictureRequest();
        reportExportDetectionResultPictureRequest.setMethod(ReportExportMethodColumnConfig.METHOD_MLPA);
        reportExportDetectionResultPictureRequest.setTaskId(taskId);
        List<ReportExportDetectionResultPicture> mlpaReportExportDetectionResultPictures= getReportExportDetectionResultPictureByTaskIdAndMethod(reportExportDetectionResultPictureRequest);
        reportExportDetectionResultPictureRequest.setMethod(ReportExportMethodColumnConfig.METHOD_DTMUTATION);
        List<ReportExportDetectionResultPicture> dtReportExportDetectionResultPictures= getReportExportDetectionResultPictureByTaskIdAndMethod(reportExportDetectionResultPictureRequest);
        reportExportData.setMlpaReportExportDetectionResultPictures(mlpaReportExportDetectionResultPictures);
        reportExportData.setDtReportExportDetectionResultPictures(dtReportExportDetectionResultPictures);
        return genericAnalysisReportGenerator.generateReport(fileName,reportExportData);
    }
    
    private String checkTemplateFile()
    {
        InputStream inputStream=null;
        try {
            inputStream =TechnicalExportServiceApplication.class.getResourceAsStream("/reportdir/reportexport-template28.doc");
            File targetFile = new File(System.getProperty("user.home")+"\\reportdir\\template63.doc");
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, targetFile);
            return "template63.doc";
        }catch (Exception e) {
            log.error(e.getMessage(),e);
        }finally {
            try
            {
                if (null != inputStream)
                {
                    inputStream.close();
                }
            }
            catch (IOException e)
            {
                log.error(e.getMessage(),e);
            }
        }
        return null;
    }

    private List<ReportExportMutationInfo> getReportExportMutationExplainInfo(ReportExportMutationExplainRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/reportexport/getReportExportMutationExplainInfo");
        ResponseEntity<List<ReportExportMutationInfo>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ReportExportMutationExplainRequest>(request),
                new ParameterizedTypeReference<List<ReportExportMutationInfo>>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public List<ReportExportMethodColumnConfig> getReportExportMethodColumnConfig(String method)
    {
        Assert.notNull(method, "参数不能为空");
        List<ReportExportMethodColumnConfig> columnCofigs=reportExportMethodColumnConfigMapper.selectByMethod(method);
        return columnCofigs;
    }

    @Override
    public List<MutationFamilySangerResultInfo> getMutationFamilySangerResultInfoByTaskId(String taskId)
    {
        if(StringUtils.isNotBlank(taskId)) {
            List<MutationFamilySangerResultInfo> mutationFamilySangerResultInfos= reportExportMutationInfoMapper.selectMutationFamilySangerResultInfoByTaskId(taskId);
            for(MutationFamilySangerResultInfo mutationFamilySangerResultInfo:mutationFamilySangerResultInfos) {
                if(StringUtils.isBlank(mutationFamilySangerResultInfo.getObjectId())) continue;
                List<MutationFamilySangerReportSample> mutationFamilySangerReportSamples= reportExportMutationInfoMapper.selectMutationFamilySangerReportSampleByMutationObjectId(mutationFamilySangerResultInfo.getObjectId());
                mutationFamilySangerResultInfo.setMutationFamilySangerReportSamples(mutationFamilySangerReportSamples);
                for(MutationFamilySangerReportSample mutationFamilySangerReportSample:mutationFamilySangerReportSamples ) {
                    if(StringUtils.isNotBlank(mutationFamilySangerReportSample.getCombineCode())) {
                        List<MutationFamilySangerReportPicture> pictures= reportExportMutationInfoMapper.selectMutationFamilySangerPictureByCombineCode(mutationFamilySangerReportSample.getCombineCode());
                        mutationFamilySangerReportSample.setPictures(pictures);
                    }
                }
            } 
            return mutationFamilySangerResultInfos;
        }
        return null;
    }

    @Override
    public List<ReportExportDetectionResultPicture> getReportExportDetectionResultPictureByTaskIdAndMethod(ReportExportDetectionResultPictureRequest reportExportDetectionResultPictureRequest)
    {
        ReportExportDetectionResult reportExportDetectionResult=new ReportExportDetectionResult();
        reportExportDetectionResult.setMethodName(reportExportDetectionResultPictureRequest.getMethod());
        reportExportDetectionResult.setTaskId(reportExportDetectionResultPictureRequest.getTaskId());
        List<ReportExportDetectionResult> reportExportDetectionResults = reportExportDetectionResultMapper.selectByTaskIdAndClinicalJudgmentAndMethodName(reportExportDetectionResult);
        List<ReportExportDetectionResultPicture> reportExportDetectionResultPictures=new ArrayList<ReportExportDetectionResultPicture>();
        if(reportExportDetectionResults!=null&&reportExportDetectionResults.size()>0) {
            Set<String> taskResultIds=new HashSet<>();
            for(ReportExportDetectionResult detectionResult:reportExportDetectionResults) {
                Map<String,String> object = (Map<String,String>)JSON.parse(detectionResult.getDetails());
                if(object!=null&&StringUtils.isNoneBlank(object.get("taskResultId"))) {
                    String testingTaskId= object.get("taskResultId");
                    if(!taskResultIds.contains(testingTaskId)) {
                        taskResultIds.add(testingTaskId);
                        List<ReportExportDetectionResultPicture> detectionResultPictures=reportExportDetectionResultMapper.selectDetectionResultPictureByTestingTaskId(testingTaskId);
                        reportExportDetectionResultPictures.addAll(detectionResultPictures);
                    }
                }
            } 
        }
        return reportExportDetectionResultPictures;
    }

}
