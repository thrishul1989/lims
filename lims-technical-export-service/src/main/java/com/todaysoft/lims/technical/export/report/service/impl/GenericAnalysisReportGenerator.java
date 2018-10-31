package com.todaysoft.lims.technical.export.report.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.jacob.com.Dispatch;
import com.todaysoft.lims.technical.export.mybatis.mapper.TestingReportUploadRecordMapper;
import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerReportPicture;
import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerReportSample;
import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerResultInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportCapcnvResult;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResult;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResultPicture;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDiseaseInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportEvidenceInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMethodColumnConfig;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMutationInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportSampleBaseInfo;
import com.todaysoft.lims.technical.export.mybatis.model.TestingReportUploadRecord;
import com.todaysoft.lims.technical.export.report.dto.MergeCell;
import com.todaysoft.lims.technical.export.report.dto.ReportExportCnvAnalysisPicResultList;
import com.todaysoft.lims.technical.export.report.dto.ReportExportData;
import com.todaysoft.lims.technical.export.report.model.request.ReportRequest;
import com.todaysoft.lims.technical.export.report.model.request.ReportUploadRecordRequest;
import com.todaysoft.lims.technical.export.utils.JacobWordDocument;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.IdGen;
import com.todaysoft.lims.utils.StringUtils;

import static com.todaysoft.lims.technical.export.utils.BarcodeUtil.generateFile;

@Service
public class GenericAnalysisReportGenerator
{
    private static Logger log = LoggerFactory.getLogger(GenericAnalysisReportGenerator.class);
    
    @Autowired
    private TestingReportUploadRecordMapper testingReportUploadRecordMapper;
    
    @Autowired
    private RestTemplate template;
    
    @Transactional
    public synchronized String  generateReport(String tamplateFileName,ReportExportData reportExportData) {
            JacobWordDocument jacobHelper=null;
        try {
            log.info("开始生成报告，任务id："+reportExportData.getReportExportSampleBaseInfo().getTaskId());
           // log.info("路径："+GenericAnalysisReportGenerator.class.getResource("/reportdir/reportexport-template5.doc").getPath().substring(1));
           // jacobHelper=new JacobWordDocument(GenericAnalysisReportGenerator.class.getClass().getResource("/reportdir/reportexport-template5.doc").getPath().substring(1));
            jacobHelper=new JacobWordDocument(System.getProperty("user.home")+"\\reportdir\\"+tamplateFileName);

            //根据样本编号生成页眉条形码
            String sampleCode =reportExportData.getReportExportSampleBaseInfo().getSampleCode();
            String barCodePicPath = System.getProperty("user.home")+"\\reportdir\\"+sampleCode+".png";
            generateFile(sampleCode, barCodePicPath);
            jacobHelper.setWaterMark(barCodePicPath);
            insertReportExportSampleBaseInfo(reportExportData.getReportExportSampleBaseInfo(), jacobHelper);
            List<ReportExportCnvAnalysisPicResultList> reportExportCnvAnalysisPicResultLists = reportExportData.getClinicalJudgmentToReportExportCnvAnalysisPicResultLists().get("临床表型高度相关");

            insertReportExportCnvAnalysisPicResultMissingType("highCorrelationMissingType",jacobHelper,reportExportCnvAnalysisPicResultLists);
            insertReportExportCnvAnalysisPicResultLists("clinicalPhenotypeHighCorrelation",jacobHelper, reportExportCnvAnalysisPicResultLists);
            insertReportExportCnvAnalysisExplain("cnvHighCorrelationExplain", jacobHelper, reportExportCnvAnalysisPicResultLists);
            
            reportExportCnvAnalysisPicResultLists =reportExportData.getClinicalJudgmentToReportExportCnvAnalysisPicResultLists().get("临床表型相关");
            insertReportExportCnvAnalysisPicResultMissingType("correlationMissingType",jacobHelper,reportExportCnvAnalysisPicResultLists);
            insertReportExportCnvAnalysisPicResultLists("clinicalPhenotypeCorrelation",jacobHelper, reportExportCnvAnalysisPicResultLists);
            insertReportExportCnvAnalysisExplain("cnvCorrelationExplain", jacobHelper, reportExportCnvAnalysisPicResultLists);

            
            List<ReportExportMutationInfo> reportExportMutationInfos= reportExportData.getClinicalJudgmentToReportExportMutationInfoList().get("临床表型高度相关");
            insertReportExportMutationInfos("muClinicalPhenotypeHighCorrelation",jacobHelper, reportExportMutationInfos);
            insertReportExportMutationInfoRefers("muHighCorrelationRefers",jacobHelper, reportExportMutationInfos);
            insertReportExportMutationInfoDiseasePhenotypes("muHighCorrelationDiseasePhenotype",jacobHelper, reportExportMutationInfos);
            insertReportExportMutationInfoEvidences("muHighCorrelationEvidences",jacobHelper, reportExportMutationInfos);
            insertReportExportMutationInfoDiseaseInfos("muHighCorrelationDiseaseInfos", jacobHelper, reportExportMutationInfos);
            reportExportMutationInfos= reportExportData.getClinicalJudgmentToReportExportMutationInfoList().get("杂合携带");
            insertReportExportMutationInfos("mutationInfoHeterozygousCarrier",jacobHelper, reportExportMutationInfos);
            insertReportExportMutationInfoRefers("mutationInfoHeterozygousRefers",jacobHelper, reportExportMutationInfos);
            reportExportMutationInfos= reportExportData.getClinicalJudgmentToReportExportMutationInfoList().get("可疑变异");
            insertReportExportMutationInfos("mutationInfoSuspiciousVariation",jacobHelper, reportExportMutationInfos);
            insertReportExportMutationInfoRefers("mutationInfoSuspiciousRefers",jacobHelper, reportExportMutationInfos);
            insertReportExportMutationInfoDiseasePhenotypes("mutationInfoSuspiciousDiseasePhenotype",jacobHelper, reportExportMutationInfos);
            insertReportExportMutationInfoEvidences("mutationInfoSuspiciousEvidences",jacobHelper, reportExportMutationInfos);
            insertReportExportMutationInfoDiseaseInfos("mutationInfoSuspiciousDiseaseInfos", jacobHelper, reportExportMutationInfos);
            
            insertReportExportCnvAnalysisPicResultLocusType("mlpaHighCorrelationLocusType", jacobHelper, reportExportData.getMlpaHighCorrelationReportExportDetectionResults());
            insertReportExportDetectionResults("mlpaHighCorrelationDetectionResults", jacobHelper, reportExportData.getMlpaDetectionResultColumns(), reportExportData.getMlpaHighCorrelationReportExportDetectionResults());
            insertReportExportCnvAnalysisPicResultLocusType("mlpaCorrelationLocusType", jacobHelper, reportExportData.getMlpaCorrelationReportExportDetectionResults());
            insertReportExportDetectionResults("mlpaCorrelationDetectionResults", jacobHelper, reportExportData.getMlpaDetectionResultColumns(), reportExportData.getMlpaCorrelationReportExportDetectionResults());

            insertReportExportDetectionResults("dtHighCorrelationDetectionResults", jacobHelper, reportExportData.getDtDetectionResultColumns(), reportExportData.getDtHighCorrelationReportExportDetectionResults());
            insertReportExportDetectionResults("dtCorrelationDetectionResults", jacobHelper, reportExportData.getDtDetectionResultColumns(), reportExportData.getDtCorrelationReportExportDetectionResults());
            
            insertMutationFamilySangerResultInfos("mutationFamilySangerResultInfos",jacobHelper,reportExportData.getMutationFamilySangerResultInfos());
            insertReportExportDetectionResultPictures("mlpaReportExportDetectionResultPictures",jacobHelper,reportExportData.getMlpaReportExportDetectionResultPictures());
            insertReportExportDetectionResultPictures("dtReportExportDetectionResultPictures",jacobHelper,reportExportData.getDtReportExportDetectionResultPictures());


            String fileName = "REPORTEXPORT_" + reportExportData.getReportExportSampleBaseInfo().getTaskId()+"_"+IdGen.uuid()+".doc";
            jacobHelper.saveAs(System.getProperty("user.home")+"\\reportdir\\"+fileName);
            jacobHelper.closeDocumentNotSave();
            jacobHelper.destory();  
            log.info("结束生成报告，任务id："+reportExportData.getReportExportSampleBaseInfo().getTaskId());
            log.info("开始上传报告到qiniu，任务id："+reportExportData.getReportExportSampleBaseInfo().getTaskId());
            File file=new File(System.getProperty("user.home")+"\\reportdir\\"+fileName);
            FileUtils.uploadQiniu(file,fileName);
            log.info("结束上传报告qiniu，任务id："+reportExportData.getReportExportSampleBaseInfo().getTaskId());

            //上传记录表
            ReportUploadRecordRequest recordRequest=new ReportUploadRecordRequest();
            recordRequest.setFileName(fileName);
            recordRequest.setReportId(reportExportData.getReportExportSampleBaseInfo().getTaskId());
            recordRequest.setUploadType(ReportUploadRecordRequest.UPLOADTYPE_AUTO);
            insertTestingReportUploadRecord(recordRequest);
            
            TestingReportUploadRecord record=new TestingReportUploadRecord();
            record.setReportId(reportExportData.getReportExportSampleBaseInfo().getTaskId());
            record.setUploadType(TestingReportUploadRecord.UPLOADTYPE_MANUAL);
            int count=testingReportUploadRecordMapper.selectByReportIdAndUploadType(record);
            if(count<=0) {
                //更新订单产品表
                ReportRequest request = new ReportRequest(reportExportData.getReportExportSampleBaseInfo().getTaskId(), fileName);
                updataOrderProductForFile(request);
                //更新报告表
                request.setFileName(fileName);
                updataReportForFile(request);
            }
            return fileName;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if(jacobHelper!=null) {
                jacobHelper.destory();  
            }
        }
        return null;
    }


    private void insertReportExportCnvAnalysisPicResultMissingType(String markerName,JacobWordDocument jacobHelper,List<ReportExportCnvAnalysisPicResultList> reportExportCnvAnalysisPicResultLists)
    {
        String missingType=getMissingTypeCnvResult(reportExportCnvAnalysisPicResultLists);
        jacobHelper.insertMarkerEndText(markerName, missingType);
    }

    private String getMissingTypeCnvResult(List<ReportExportCnvAnalysisPicResultList> reportExportCnvAnalysisPicResultLists) {
        if(reportExportCnvAnalysisPicResultLists!=null&&reportExportCnvAnalysisPicResultLists.size()>0) {
            Set<String> cnvResult=new HashSet<String>();
            for(ReportExportCnvAnalysisPicResultList reportExportCnvAnalysisPicResultList:reportExportCnvAnalysisPicResultLists) {
                List<ReportExportCapcnvResult> reportExportCapcnvResults = reportExportCnvAnalysisPicResultList.getReportExportCapcnvResultList();
                if(reportExportCapcnvResults!=null&&reportExportCapcnvResults.size()>0) {
                    for(ReportExportCapcnvResult reportExportCapcnvResult:reportExportCapcnvResults) {
                        cnvResult.add(reportExportCapcnvResult.getMissingType());
                    }
                }
            }
            return StringUtils.join(cnvResult,"/");
        }
        return "";
    }
    
    private void insertReportExportCnvAnalysisPicResultLocusType(String markerName,JacobWordDocument jacobHelper,List<ReportExportDetectionResult> reportExportDetectionResults)
    {
        String locusType= getLocusTypeMlpaDetectionResult(reportExportDetectionResults);
        jacobHelper.insertMarkerText(markerName, locusType);
    }
    
    private String getLocusTypeMlpaDetectionResult(List<ReportExportDetectionResult> reportExportDetectionResults) {
        if(reportExportDetectionResults!=null&&reportExportDetectionResults.size()>0) {
            Set<String> locusTypes=new HashSet<String>();
            for(ReportExportDetectionResult reportExportDetectionResult:reportExportDetectionResults) {
                Map<String,String> object = (Map<String,String>)JSON.parse(reportExportDetectionResult.getDetails());
                if(object!=null&&object.get("locusType")!=null) {
                    locusTypes.add(object.get("locusType"));   
                }
            }
            return StringUtils.join(locusTypes,"/");
        }
        return "";
    }
    
    
    private void insertReportExportMutationInfoRefers(String markerName,JacobWordDocument jacobHelper, List<ReportExportMutationInfo> reportExportMutationInfos)
    {
        List<String> lines=new ArrayList<String>();
        if(reportExportMutationInfos!=null&&reportExportMutationInfos.size()>0) {
            for(ReportExportMutationInfo reportExportMutationInfo: reportExportMutationInfos) {
                if(reportExportMutationInfo.getReportExportMutationInfoExplain()!=null) {
                    String disease= reportExportMutationInfo.getReportExportMutationInfoExplain().getTagPublication();
                    if(StringUtils.isNotBlank(disease)) {
                        lines.add("已报道过与"+disease+"相关；"+reportExportMutationInfo.getReportExportMutationInfoExplain().getTagReportDisease());
                    }
                }
            }
        }
        StringBuffer sb=new StringBuffer();
        for(String line: lines) {
            sb.append(line).append("\n");
        }
        jacobHelper.insertMarkerText(markerName, sb.toString());
    }
    
    private void insertReportExportMutationInfoDiseasePhenotypes(String markerName,JacobWordDocument jacobHelper, List<ReportExportMutationInfo> reportExportMutationInfos)
    {
        List<String> lines=new ArrayList<String>();
        if(reportExportMutationInfos!=null&&reportExportMutationInfos.size()>0) {
            for(ReportExportMutationInfo reportExportMutationInfo: reportExportMutationInfos) {
                if(reportExportMutationInfo.getReportExportMutationInfoExplain()!=null) {
                    List<ReportExportDiseaseInfo> diseases= reportExportMutationInfo.getReportExportMutationInfoExplain().getDiseaseInfos();
                    if(diseases!=null&&diseases.size()>0) {
                        for(ReportExportDiseaseInfo diseaseInfo:diseases) {
                            lines.add(diseaseInfo.getDisease()+"的临床表型："+diseaseInfo.getDiseasePhenotype());
                        }
                    }
                }
            }
        }
        StringBuffer sb=new StringBuffer();
        for(String line: lines) {
            sb.append(line).append("\n");
        }
        jacobHelper.insertMarkerText(markerName, sb.toString());
    }
    
    private void insertReportExportMutationInfoEvidences(String markerName,JacobWordDocument jacobHelper, List<ReportExportMutationInfo> reportExportMutationInfos)
    {
        List<String> lines=new ArrayList<String>();
        if(reportExportMutationInfos!=null&&reportExportMutationInfos.size()>0) {
            for(ReportExportMutationInfo reportExportMutationInfo: reportExportMutationInfos) {
                if(reportExportMutationInfo.getReportExportMutationInfoExplain()!=null) {
                    lines.add("该样本分析到"+reportExportMutationInfo.getReportExportMutationInfoExplain().getGeneSymbol()+"基因有1个"+reportExportMutationInfo.getReportExportMutationInfoExplain().getGeneType()+"，根据ACMG指南，该变异判定为"+reportExportMutationInfo.getReportExportMutationInfoExplain().getPathogenicAnalysis());
                    List<ReportExportEvidenceInfo> evidenceInfos= reportExportMutationInfo.getReportExportMutationInfoExplain().getEvidenceInfos();
                    if(evidenceInfos!=null&&evidenceInfos.size()>0) {
                        lines.add("ACMG证据如下：");
                        for(ReportExportEvidenceInfo evidenceInfo:evidenceInfos) {
                            lines.add(evidenceInfo.getName()+":"+evidenceInfo.getDecription());
                        }
                    }
                }
            }
        }
        StringBuffer sb=new StringBuffer();
        for(String line: lines) {
            sb.append(line).append("\n");
        }
        jacobHelper.insertMarkerText(markerName, sb.toString());
    }
    
    private void insertReportExportMutationInfoDiseaseInfos(String markerName,JacobWordDocument jacobHelper, List<ReportExportMutationInfo> reportExportMutationInfos)
    {
        if(reportExportMutationInfos!=null&&reportExportMutationInfos.size()>0) {
            Dispatch diseaseInfosTable= jacobHelper.insertMarkerTable(markerName, 1, 5);
            jacobHelper.insertCellText(diseaseInfosTable, 1, 1, "基因");
            jacobHelper.insertCellText(diseaseInfosTable, 1, 2, "遗传方式");
            jacobHelper.insertCellText(diseaseInfosTable, 1, 3, "疾病");
            jacobHelper.insertCellText(diseaseInfosTable, 1, 4, "疾病简介");
            jacobHelper.insertCellText(diseaseInfosTable, 1, 5, "疾病表型");
            List<MergeCell> mergeCells=new ArrayList<MergeCell>();
            int rowIndex = 2;
            for(ReportExportMutationInfo reportExportMutationInfo: reportExportMutationInfos) {
                if(reportExportMutationInfo.getReportExportMutationInfoExplain()!=null) {
                    List<ReportExportDiseaseInfo> diseaseInfos= reportExportMutationInfo.getReportExportMutationInfoExplain().getDiseaseInfos();
                    if(diseaseInfos!=null&&diseaseInfos.size()>0) {
                        for (int i = 0; i < diseaseInfos.size(); i++)
                        {
                            MergeCell mergeCell=new MergeCell();
                            jacobHelper.insertRow(diseaseInfosTable);
                            if(i==0) {
                                mergeCell.setStart(rowIndex);
                                jacobHelper.insertCellText(diseaseInfosTable, rowIndex, 1, reportExportMutationInfo.getReportExportMutationInfoExplain().getGeneSymbol());
                            }else {
                                jacobHelper.insertCellText(diseaseInfosTable, rowIndex, 1, "");
                            }
                            jacobHelper.insertCellText(diseaseInfosTable, rowIndex, 2, diseaseInfos.get(i).getInhert());
                            jacobHelper.insertCellText(diseaseInfosTable, rowIndex, 3, diseaseInfos.get(i).getDisease());
                            jacobHelper.insertCellText(diseaseInfosTable, rowIndex, 4, diseaseInfos.get(i).getDiseaseInformation());
                            jacobHelper.insertCellText(diseaseInfosTable, rowIndex, 5, diseaseInfos.get(i).getDiseasePhenotype());
                            
                            if(i==(diseaseInfos.size()-1)) {
                                mergeCell.setEnd(rowIndex);  
                            }
                            rowIndex++;
                            
                            if(mergeCell.getStart()!=0&&mergeCell.getEnd()!=0&&mergeCell.getStart()!=mergeCell.getEnd()) {
                                mergeCells.add(mergeCell);
                            }
                        }
                    }
                }
            }
            for(MergeCell mergeCell:mergeCells) {
                jacobHelper.mergeCell(diseaseInfosTable, mergeCell.getStart(), 1, mergeCell.getEnd(), 1);
            }
            
            jacobHelper.setTableStyle(diseaseInfosTable);
        }
    }
    
    private void insertReportExportMutationInfos(String markerName,JacobWordDocument jacobHelper, List<ReportExportMutationInfo> reportExportMutationInfos)
    {
        if(reportExportMutationInfos!=null&&reportExportMutationInfos.size()>0) {
            Dispatch cnvResultTable= jacobHelper.insertMarkerTable(markerName, 1, 12);
            jacobHelper.insertCellText(cnvResultTable, 1, 1, "基因");
            jacobHelper.insertCellText(cnvResultTable, 1, 2, "染色体位置");
            jacobHelper.insertCellText(cnvResultTable, 1, 3, "转录本外显子");
            jacobHelper.insertCellText(cnvResultTable, 1, 4, "核苷酸");
            jacobHelper.insertCellText(cnvResultTable, 1, 5, "氨基酸");
            jacobHelper.insertCellText(cnvResultTable, 1, 6, "纯合/杂合");
            jacobHelper.insertCellText(cnvResultTable, 1, 7, "正常人频率");
            jacobHelper.insertCellText(cnvResultTable, 1, 8, "蛋白预测");
            jacobHelper.insertCellText(cnvResultTable, 1, 9, "致病性分析");
            jacobHelper.insertCellText(cnvResultTable, 1, 10, "遗传方式");
            jacobHelper.insertCellText(cnvResultTable, 1, 11, "疾病/表型");
            jacobHelper.insertCellText(cnvResultTable, 1, 12, "变异来源");  
            
            int rowIndex = 2;
            for (ReportExportMutationInfo reportExportMutationInfo:reportExportMutationInfos)
            {
                jacobHelper.insertRow(cnvResultTable);
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 1, reportExportMutationInfo.getGeneSymbol());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 2, reportExportMutationInfo.getChromosomalLocation());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 3, reportExportMutationInfo.getExon());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 4, reportExportMutationInfo.getNucleotideChanges());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 5, reportExportMutationInfo.getAminoAcidChanges());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 6, reportExportMutationInfo.getGeneType());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 7, reportExportMutationInfo.getHighestMafName());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 8, reportExportMutationInfo.getEffect());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 9, reportExportMutationInfo.getPathogenicAnalysis());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 10, reportExportMutationInfo.getInhert());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 11, reportExportMutationInfo.getDiseasePhenotype());
                jacobHelper.insertCellText(cnvResultTable, rowIndex, 12, reportExportMutationInfo.getInhertSourceName());  
                rowIndex++;
            }
            jacobHelper.setTableStyle(cnvResultTable);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void insertReportExportDetectionResults(String markerName,JacobWordDocument jacobHelper,List<ReportExportMethodColumnConfig> reportExportMethodColumnConfigs, List<ReportExportDetectionResult> reportExportDetectionResults)
    {
        Assert.notNull(reportExportMethodColumnConfigs,"列名不能为空");
        if(reportExportDetectionResults!=null&&reportExportDetectionResults.size()>0) {
            Dispatch reportExportDetectionResultTable= jacobHelper.insertMarkerTable(markerName, 1, reportExportMethodColumnConfigs.size());
            for (int i = 1; i <= reportExportMethodColumnConfigs.size(); i++)
            {
                jacobHelper.insertCellText(reportExportDetectionResultTable, 1, i, reportExportMethodColumnConfigs.get(i-1).getShowName());
            }
            int rowIndex = 2;
            for (ReportExportDetectionResult reportExportDetectionResult:reportExportDetectionResults)
            {
                jacobHelper.insertRow(reportExportDetectionResultTable);
                Map<String,String> object = (Map<String,String>)JSON.parse(reportExportDetectionResult.getDetails());
                if(object!=null) {
                    int i=1;
                    for(ReportExportMethodColumnConfig reportExportMethodColumnConfig:reportExportMethodColumnConfigs) {
                        jacobHelper.insertCellText(reportExportDetectionResultTable, rowIndex,i,object.get(reportExportMethodColumnConfig.getColumnName()));
                        i++;
                    }
                    rowIndex++;
                }
            }
            jacobHelper.setTableStyle(reportExportDetectionResultTable);
        }
    }

    private void insertReportExportCnvAnalysisExplain(String markerName,JacobWordDocument jacobHelper, List<ReportExportCnvAnalysisPicResultList> reportExportCnvAnalysisPicResultLists)
    {
        List<String> lines=new ArrayList<String>();
        if(reportExportCnvAnalysisPicResultLists!=null&&reportExportCnvAnalysisPicResultLists.size()>0)
        for(ReportExportCnvAnalysisPicResultList reportExportCnvAnalysisPicResultList : reportExportCnvAnalysisPicResultLists) {
            if(reportExportCnvAnalysisPicResultList.getReportExportCapcnvResultList()!=null&&reportExportCnvAnalysisPicResultList.getReportExportCapcnvResultList().size()>0) {
                for (ReportExportCapcnvResult reportExportCapcnvResult:reportExportCnvAnalysisPicResultList.getReportExportCapcnvResultList())
                {
                    lines.add("该样本分析到"+reportExportCapcnvResult.getMissingArea()+"区域发生"+reportExportCapcnvResult.getAreaSize()+reportExportCapcnvResult.getMissingType()+"，为"+reportExportCapcnvResult.getPathogenicLevel()+"。");
                    lines.add(reportExportCapcnvResult.getMutationComment());
                }
            }
        }
        
        StringBuffer sb=new StringBuffer();
        for(String line: lines) {
            sb.append(line).append("\n");
        }
        jacobHelper.insertMarkerText(markerName, sb.toString());
    }
    
    private void insertReportExportCnvAnalysisPicResultLists(String markerName,JacobWordDocument jacobHelper, List<ReportExportCnvAnalysisPicResultList> reportExportCnvAnalysisPicResultLists)
    {
        if(reportExportCnvAnalysisPicResultLists!=null&&reportExportCnvAnalysisPicResultLists.size()>0)
        for(ReportExportCnvAnalysisPicResultList reportExportCnvAnalysisPicResultList : reportExportCnvAnalysisPicResultLists) {
            if(reportExportCnvAnalysisPicResultList.getCnvAnalysisPic()!=null) {
                jacobHelper.insertMarkerPicture(markerName, reportExportCnvAnalysisPicResultList.getCnvAnalysisPic().getImageUrl());
                jacobHelper.insertMarkerText(markerName, "该实验结果图如下：");
            }
            if(reportExportCnvAnalysisPicResultList.getReportExportCapcnvResultList()!=null&&reportExportCnvAnalysisPicResultList.getReportExportCapcnvResultList().size()>0) {
                Dispatch cnvResultTable= jacobHelper.insertMarkerTable(markerName, 1, 5);
                jacobHelper.insertCellText(cnvResultTable, 1, 1, "基因");
                jacobHelper.insertCellText(cnvResultTable, 1, 2, "缺失/重复区域");
                jacobHelper.insertCellText(cnvResultTable, 1, 3, "外显子");
                jacobHelper.insertCellText(cnvResultTable, 1, 4, "遗传来源");
                jacobHelper.insertCellText(cnvResultTable, 1, 5, "疾病/表型");
                int rowIndex = 2;
                for (ReportExportCapcnvResult reportExportCapcnvResult:reportExportCnvAnalysisPicResultList.getReportExportCapcnvResultList())
                {
                    jacobHelper.insertRow(cnvResultTable);
                    jacobHelper.insertCellText(cnvResultTable, rowIndex, 1,reportExportCapcnvResult.getGene());
                    jacobHelper.insertCellText(cnvResultTable, rowIndex, 2, reportExportCapcnvResult.getMissingArea());
                    jacobHelper.insertCellText(cnvResultTable, rowIndex, 3, reportExportCapcnvResult.getExon());
                    jacobHelper.insertCellText(cnvResultTable, rowIndex, 4, reportExportCapcnvResult.getInheritSource());
                    jacobHelper.insertCellText(cnvResultTable, rowIndex, 5, reportExportCapcnvResult.getRelatedDisease());
                    rowIndex++;
                }
                jacobHelper.setTableStyle(cnvResultTable);
            }
        }
    }

    private void insertReportExportSampleBaseInfo(ReportExportSampleBaseInfo reportExportSampleBaseInfo, JacobWordDocument jacobHelper) throws IllegalAccessException
    {
        jacobHelper.insertMarkerText("reportHead",reportExportSampleBaseInfo.getOrderCode()+"_"+reportExportSampleBaseInfo.getSampleCode()+"_"+reportExportSampleBaseInfo.getExamineeName());
        Field[] fields = ReportExportSampleBaseInfo.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if(!"id".equals(fields[i].getName())&&fields[i].get(reportExportSampleBaseInfo)!=null) {
                jacobHelper.insertMarkerText(fields[i].getName(),(String)fields[i].get(reportExportSampleBaseInfo));
            }
        }
    }
    
    private void insertMutationFamilySangerResultInfos(String markerName, JacobWordDocument jacobHelper, List<MutationFamilySangerResultInfo> mutationFamilySangerResultInfos)
    {
       if(mutationFamilySangerResultInfos!=null&&mutationFamilySangerResultInfos.size()>0) {
           for(MutationFamilySangerResultInfo mutationFamilySangerResultInfo:mutationFamilySangerResultInfos) {
               Dispatch sangerResultTable= jacobHelper.insertMarkerTable(markerName, 1, 6);
               jacobHelper.insertCellText(sangerResultTable, 1, 1, "分析样本");
               jacobHelper.insertCellText(sangerResultTable, 1, 2, "分析结果");
               jacobHelper.insertCellText(sangerResultTable, 1, 3, mutationFamilySangerResultInfo.getGeneSymbol());
               jacobHelper.insertCellText(sangerResultTable, 1, 4, mutationFamilySangerResultInfo.getChromosomalLocation());
               jacobHelper.insertCellText(sangerResultTable, 1, 5, mutationFamilySangerResultInfo.getNucleotideChanges());
               jacobHelper.insertCellText(sangerResultTable, 1, 6, mutationFamilySangerResultInfo.getAminoAcidChanges());
               if(mutationFamilySangerResultInfo.getMutationFamilySangerReportSamples()!=null&&mutationFamilySangerResultInfo.getMutationFamilySangerReportSamples().size()>0) {
                   int rowIndex = 2;
                   for(MutationFamilySangerReportSample mutationFamilySangerReportSample:mutationFamilySangerResultInfo.getMutationFamilySangerReportSamples()) {
                       jacobHelper.insertRow(sangerResultTable);
                       jacobHelper.insertCellText(sangerResultTable, rowIndex, 1,mutationFamilySangerReportSample.getSampleName()+"\n"+mutationFamilySangerReportSample.getSampleCode());
                       jacobHelper.insertCellText(sangerResultTable, rowIndex, 2,mutationFamilySangerReportSample.getCombineType());
                       List<String> picUrls=new ArrayList<String>();
                       if(mutationFamilySangerReportSample.getPictures()!=null&&mutationFamilySangerReportSample.getPictures().size()>0) {
                          for(MutationFamilySangerReportPicture mutationFamilySangerReportPicture:mutationFamilySangerReportSample.getPictures()) {
                              String picUrl= getImagePath(mutationFamilySangerReportPicture.getPicUrl());
                              if(StringUtils.isNotBlank(picUrl)) {
                                   picUrls.add(picUrl);
                              }
                          }
                       }
                       jacobHelper.insertCellText(sangerResultTable, rowIndex, 3,"");
                       for(String picUrl:picUrls) {
                           jacobHelper.insertCellPicture(sangerResultTable, rowIndex, 4, picUrl);
                       }
                       jacobHelper.insertCellText(sangerResultTable, rowIndex, 5,"");
                       jacobHelper.insertCellText(sangerResultTable, rowIndex, 6,"");
                       rowIndex++;
                   }
                   rowIndex =2;
                   for(MutationFamilySangerReportSample mutationFamilySangerReportSample:mutationFamilySangerResultInfo.getMutationFamilySangerReportSamples()) {
                        jacobHelper.mergeCell(sangerResultTable, rowIndex, 3, rowIndex, 6);
                        rowIndex++;
                   }
               }
               jacobHelper.setTableStyle(sangerResultTable);
           }
       }
    }
    
    private void insertReportExportDetectionResultPictures(String markerName, JacobWordDocument jacobHelper, List<ReportExportDetectionResultPicture> mlpaReportExportDetectionResultPictures)
    {
        if(mlpaReportExportDetectionResultPictures!=null&&mlpaReportExportDetectionResultPictures.size()>0) {
            int length=mlpaReportExportDetectionResultPictures.size();
            for(int i=length-1;i>=0;i--) {
                ReportExportDetectionResultPicture reportExportDetectionResultPicture = mlpaReportExportDetectionResultPictures.get(i);
                String picUrl= getImagePath(reportExportDetectionResultPicture.getPicUrl());
                if(picUrl!=null) {
                    jacobHelper.insertMarkerText(markerName, "");
                    jacobHelper.insertMarkerPicture(markerName, picUrl);
                }
                else {
                    jacobHelper.insertMarkerText(markerName, "");
                }
            }
        }
    }
    
    private synchronized String getImagePath(String imageUrl) {
        InputStream inputStream=null;
        try {
            String path=FileUtils.getDownloadUrl(imageUrl);
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(3 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            File targetFile = new File(System.getProperty("user.home")+"\\reportdir\\"+imageUrl);
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, targetFile);
            if(targetFile.exists())  return System.getProperty("user.home")+"\\reportdir\\"+imageUrl;
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
    
    private void updataOrderProductForFile(ReportRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/updataOrderProductForFile");
        template.postForObject(url, request, String.class);
    }
    
    private void updataReportForFile(ReportRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/updataReportForFile");
        template.postForObject(url, request, String.class);
    }
    
    private void insertTestingReportUploadRecord(ReportUploadRecordRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/insertTestingReportUploadRecord");
        template.postForObject(url, request, String.class);
    }
}
