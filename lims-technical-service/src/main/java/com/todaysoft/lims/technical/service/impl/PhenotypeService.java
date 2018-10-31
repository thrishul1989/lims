package com.todaysoft.lims.technical.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.todaysoft.lims.technical.config.ConfigManage;
import com.todaysoft.lims.technical.model.request.PhenoTypePointsRequest;
import com.todaysoft.lims.technical.model.response.PhenoTypePointRespModel;
import com.todaysoft.lims.technical.mybatis.BioInfoAnnotateHpoMonitorMapper;
import com.todaysoft.lims.technical.mybatis.BioInfoAnnotateMapper;
import com.todaysoft.lims.technical.mybatis.BiologyDivisionFastqDataMapper;
import com.todaysoft.lims.technical.mybatis.BiologyFamilyAnalysisSampleRelationMapper;
import com.todaysoft.lims.technical.mybatis.BiologyFamilyRelationAnnotateMapper;
import com.todaysoft.lims.technical.mybatis.OrderSampleDetailMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalysisTaskMapper;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotate;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotateHpoMonitor;
import com.todaysoft.lims.technical.mybatis.entity.BiologyDivisionFastqData;
import com.todaysoft.lims.technical.mybatis.entity.BiologyFamilyAnalysisSampleRelation;
import com.todaysoft.lims.technical.mybatis.entity.BiologyFamilyRelationAnnotate;
import com.todaysoft.lims.technical.mybatis.entity.OrderSampleDetail;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.technical.service.IPhenotypeService;
import com.todaysoft.lims.technical.utils.Collections3;
import com.todaysoft.lims.technical.utils.HttpRequestAPI;
import com.todaysoft.lims.technical.utils.JsonUtils;

@Service
public class PhenotypeService implements IPhenotypeService
{
    
    @Autowired
    private BioInfoAnnotateMapper bioInfoAnnotateMapper;
    
    @Autowired
    private BioInfoAnnotateHpoMonitorMapper bioInfoAnnotateHpoMonitorMapper;
    
    @Autowired
    private BiologyDivisionFastqDataMapper divisionDataMapper;
    
    @Autowired
    private ConfigManage configManage;
    
    @Autowired
    private TechnicalAnalysisTaskMapper technicalAnalysisTaskMapper;
    
    @Autowired
    private BiologyFamilyAnalysisSampleRelationMapper biologyFamilyAnalysisSampleRelationMapper;
    
    @Autowired
    private BiologyFamilyRelationAnnotateMapper biologyFamilyRelationAnnotateMapper;
    
    @Autowired
    private OrderSampleDetailMapper orderSampleDetailMapper;
    
    private static Logger log = LoggerFactory.getLogger(PhenotypeService.class);
    
    @Override
    public Integer phenoTypePoints(PhenoTypePointsRequest request)
    {
        TechnicalAnalysisTask searcher = new TechnicalAnalysisTask();
        searcher.setFamilyGroupId(request.getFamilyGroupId());
        searcher.setReceivedSampleSamplingBtype(1); //主样本
        List<TechnicalAnalysisTask> tasks = technicalAnalysisTaskMapper.selectBySearcher(searcher); //获取任务
        if (Collections3.isNotEmpty(tasks))
        {
            List<TechnicalAnalysisTask> task = tasks.stream().filter(o -> o.getReceivedSampleSamplingBtype().equals(1)).collect(Collectors.toList()); //过滤主样本
            task = tasks.stream().filter(o -> o.getStatus() != 7).collect(Collectors.toList()); //
            TechnicalAnalysisTask t = Collections3.getFirst(task);
            Integer b = t.getIfFamilyTask();
            
            BioInfoAnnotate bioInfoAnnotate = bioInfoAnnotateMapper.getAnnotateBySampleCode(t.getTestingAnalyDataCode()); //主数据编号，查询主样本注释
            if (null == bioInfoAnnotate)
            {// 未注释完成
                log.warn("根据主样本数据编号:" + t.getTestingAnalyDataCode() + "查询注释数据为空");
                return 2;
            }
            if (b != null && b.equals(0))//如果单例样本
            {
                
                if ("5".equals(bioInfoAnnotate.getAnnotateStatus()))
                {
                    if (null != bioInfoAnnotate)
                    {
                        Map<String, Object> httpRequest = new HashMap<>();
                        httpRequest.put("ifSingleSample", "1");
                        httpRequest.put("dataCode", t.getTestingAnalyDataCode());
                        httpRequest.put("vcfFilePath", bioInfoAnnotate.getVcfDmdexon());
                        httpRequest.put("sex", request.getExamineeSex());
                        httpRequest.put("hpoCode", Joiner.on(",").join(request.getHpoCode()));
                        httpRequest.put("family", Collections.EMPTY_LIST);
                        return phenoTypePointsHttp(bioInfoAnnotate, null, JsonUtils.toJson(httpRequest), request.getFamilyGroupId());
                    }
                }
                else if ("7".equals(bioInfoAnnotate.getAnnotateStatus()))
                {
                    log.info("技术分析数据编号为：" + t.getTestingAnalyDataCode() + "已经在打分中");
                    return 0;
                }
            }
            else
            { //家系
                BiologyDivisionFastqData divisionDate = divisionDataMapper.selectByDataCode(bioInfoAnnotate.getDataCode());
                List<BiologyFamilyAnalysisSampleRelation> familys = biologyFamilyAnalysisSampleRelationMapper.getRelationBySampleId(divisionDate.getId());
                if (Collections3.isNotEmpty(familys))
                {
                    BiologyFamilyRelationAnnotate ganalysisfamilyAnnotate = biologyFamilyRelationAnnotateMapper.selectByFamilyId(request.getFamilyGroupId());
                    if (null == ganalysisfamilyAnnotate)
                    {// 未注释完成
                        log.warn("##家系分析##技术分析主样本数据编号" + bioInfoAnnotate.getDataCode() + ",查询关联家系注释数据ID为："
                            + Collections3.getFirst(familys).getFamilyAnalysisId() + "的对象为空");
                        return 2;
                    }
                    else
                    {
                        if (5 == ganalysisfamilyAnnotate.getStatus())
                        {
                            Map<String, Object> httpRequest = new HashMap<>();
                            httpRequest.put("ifSingleSample", "0");
                            httpRequest.put("dataCode", t.getTestingAnalyDataCode());
                            httpRequest.put("vcfFilePath", bioInfoAnnotate.getVcfDmdexon());
                            httpRequest.put("sex", request.getExamineeSex());
                            httpRequest.put("hpoCode", Joiner.on(",").join(request.getHpoCode()));
                            
                            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                            // 查询家系样本
                            for (BiologyFamilyAnalysisSampleRelation relation : familys)
                            {
                                if (!relation.getSampleId().equals(divisionDate.getId()))
                                {
                                    BiologyDivisionFastqData relationDivision = divisionDataMapper.selectByPrimaryKey(relation.getSampleId());
                                    //根据样本id查询附属样本
                                    OrderSampleDetail familySample = orderSampleDetailMapper.selectBySampleCode(relationDivision.getSampleCode());
                                    BioInfoAnnotate familyAnnotate = bioInfoAnnotateMapper.getAnnotateBySampleCode(relationDivision.getDataCode());
                                    Map<String, String> familyMap = new HashMap<>();
                                    familyMap.put("dataCode", relationDivision.getDataCode());
                                    familyMap.put("vcfFilePath", familyAnnotate.getVcfDmdexon());
                                    familyMap.put("relation", null != familySample ? familySample.getFamilyRelation() : "");
                                    familyMap.put("symptom", familySample.getOwnerPhenotype() + "");
                                    list.add(familyMap);
                                }
                                
                            }
                            
                            httpRequest.put("family", list);
                            log.info("发送打分接口参数:" + JsonUtils.toJson(httpRequest));
                            return phenoTypePointsHttp(null, ganalysisfamilyAnnotate, JsonUtils.toJson(httpRequest), request.getFamilyGroupId());
                            
                        }
                        else if (7 == ganalysisfamilyAnnotate.getStatus())
                        {
                            
                            log.info("正在打分中");
                            return 0;
                        }
                    }
                }
                else
                {
                    log.info("无家系注释数据");
                }
            }
            
        }
        
        return 0;
        
    }
    
    public Integer phenoTypePointsHttp(BioInfoAnnotate bioInfoAnnotate, BiologyFamilyRelationAnnotate familyAnnotate, String httpRequest, String familyGroupId)
    {
        
        try
        {
            log.info("发送打分接口参数" + httpRequest);
            PhenoTypePointRespModel res = HttpRequestAPI.httpPost(HttpRequestAPI.PHENOTYPE_POINT, httpRequest, PhenoTypePointRespModel.class);
            if (null != res && "0000".equals(res.getStatusCode()))
            {
                
                BioInfoAnnotateHpoMonitor monitor = new BioInfoAnnotateHpoMonitor();
                monitor.setAnnotateId(null == bioInfoAnnotate ? familyAnnotate.getGanalysisFamilyId() : bioInfoAnnotate.getId());
                monitor.setSampleTestId(familyGroupId);
                monitor.setMonitorTaskId(res.getData().get("taskId").toString());
                monitor.setCreateTime(new Date());
                monitor.setStatus(false);
                bioInfoAnnotateHpoMonitorMapper.insertSelective(monitor);
                
                // 更改状态打分中
                if (null != bioInfoAnnotate)
                {
                    bioInfoAnnotate.setAnnotateStatus("7");
                    bioInfoAnnotateMapper.update(bioInfoAnnotate);
                    // 同步更改样本表的状态
                    
                }
                else
                {
                    familyAnnotate.setStatus(7);
                    biologyFamilyRelationAnnotateMapper.updateBySelective(familyAnnotate);
                }
                return 1;
            }
        }
        catch (Exception e)
        {
            return 3;
        }
        return 3;
        
    }
    
    @Override
    public List<BioInfoAnnotateHpoMonitor> getMonitoringList()
    {
        return bioInfoAnnotateHpoMonitorMapper.getMonitoringList();
    }
    
}
