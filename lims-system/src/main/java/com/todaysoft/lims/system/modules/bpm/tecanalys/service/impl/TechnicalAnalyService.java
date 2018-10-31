package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.persist.PageInfo;
import com.todaysoft.lims.system.model.request.PhenoTypePointsRequest;
import com.todaysoft.lims.system.model.transation.VariableModel;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.TestingTaskRequest;
import com.todaysoft.lims.system.model.vo.testingtask.TaskSemantic;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BioInfoAnnotate;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheetSearcher;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.INGSCompleteService;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.model.TestTask;
import com.todaysoft.lims.system.modules.bpm.service.ITestingTaskSheetService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.TechnicalAnalysisAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ClaimTemplateColumnForDoctor;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.DataColAndMutationDataModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.MutationFilterForm;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.MutationStatisticsModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.MutationStatisticsSearcher;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.SiteplotData;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignArgs;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseRecord;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseResult;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyRecord;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySheetReceivedSample;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySheetSamplesModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitForm;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitRecord;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitRecordArgs;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitVerifyRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyVerifySample;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisSampleModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisSheetModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisTaskRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.UploadEvidenceRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.UploadEvidenceRespModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ZipFileUploadModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CancelCollectionCnvAnalysisResult;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CapCnvData;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CapCnvResultData;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CnvAnalysisDataModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CnvAnalysisFamilyDataModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CollectionCnvAnalysisPicResultList;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.base.Pager;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CapCnvDrawPicRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CapCnvRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CollectionCapcnvPicRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CompareSampleReanalysisRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CompareSampleRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.DoctorIfColletionRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.GroupNameDetailsRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.MutationCollectionRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.MutationDataRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.SubmitCnvVerifyRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.SubmitTechnicalTaskRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.SvRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalAddVerifyRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalAnalysisAddVerify;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalCollectionRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalReportRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.response.CompareSample;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.response.DrawPictureModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.response.ListResponse;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.response.PagerResponse;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.ITechnicalAnalyService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.DataCodeColumnValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.ITechnicalAnalyRecordValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.LocusDuplicateValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.LocusTypeColumnValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.MLPAVerifyValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.PCRNGSVerifyValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.QPCRVerifyValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.ReferenceLocusValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.ReportLocusValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.SampleColumnValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.SangerVerifyValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.TechnicalAnalyRecordValidateContext;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.VerifyColumnValidator;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator.VerifyMethodColumnValidator;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.service.IDataTemplateService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.service.security.AccountDetails;
import com.todaysoft.lims.system.util.ConfigManage;
import com.todaysoft.lims.system.util.DateUtil;
import com.todaysoft.lims.system.util.DateUtils;
import com.todaysoft.lims.system.util.excel.ExcelUtil;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;

@Service
public class TechnicalAnalyService extends RestService implements ITechnicalAnalyService, ITestingTaskSheetService
{
    
    @Autowired
    private INGSCompleteService ngsService;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IDataTemplateService dataTemplateService;
    
    @Autowired
    private IMlpaDataDataService mlpaDataDataService;
    
    @Autowired
    private IUserService userService;
    
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TechnicalAnalyService.class);
    
    @Override
    public List<TechnicalAnalyTask> getAssignableList(TechnicalAnalyAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/technical-analy/tasks");
        ResponseEntity<List<TechnicalAnalyTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TechnicalAnalyAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<TechnicalAnalyTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<TechnicalAnalyTask> getAssignableListDetail(TechnicalAnalyAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/technical-analy/tasks_detail");
        ResponseEntity<List<TechnicalAnalyTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TechnicalAnalyAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<TechnicalAnalyTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public TechnicalAnalyAssignModel getAssignModel(TechnicalAnalyAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/technical-analy/tasks/assigning");
        return template.postForObject(url, args, TechnicalAnalyAssignModel.class);
    }
    
    @Override
    public void assign(TechnicalAnalyAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/technical-analy/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public TechnicalAnalySubmitModel getSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/technical-analy/sheets/{id}");
        return template.getForObject(url, TechnicalAnalySubmitModel.class, id);
    }
    
    @Override
    public TechnicalAnalySubmitModel getSheetExportModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/technical-analy/sheets/{id}/export");
        return template.getForObject(url, TechnicalAnalySubmitModel.class, id);
    }
    
    @Override
    public void verify(TechnicalAnalySubmitVerifyRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/technical-analy/sheets/verify");
        template.postForLocation(url, request);
    }
    
    @Override
    public TechnicalAnalyParseResult parse(String id, MultipartFile zipFile, Integer isReTechnical)
    {
        TechnicalAnalyParseRecord parseRecord;
        // 根据任务单id 查询出来是哪套模板 然后取出来模板的名称跟关键字的key值 页面显示的标题就是模板里面配置的
        List<String> columns = Lists.newArrayList();
        DataTemplate dataTemplate = dataTemplateService.getDataTemplateMapBySheetId(id);
        if (null == dataTemplate)
        {
            logger.error("技术分析上传未找到数据模板,sheetId" + id);
        }
        dataTemplate.getColumnList().stream().forEach(x -> columns.add(x.getColumnName()));
        
        ZipFileUploadModel zipModel = mlpaDataDataService.unZipFiles(zipFile, TestingMethod.TECHNICAL);
        
        TechnicalAnalyRecordResolver resolver = new TechnicalAnalyRecordResolver(zipModel, dataTemplate);
        List<TechnicalAnalyRecord> records = resolver.resolve();
        // 关闭压缩所文件
        try
        {
            zipModel.getZipFile().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        TechnicalAnalySubmitModel sheet = getSubmitModel(id);
        List<String> dataCode = Lists.newArrayList();
        
        Map<String, Boolean> map = Maps.newHashMap();// 任务已经提交过验证的的给校验不合格
                                                     // 页面上虽然校验合格
                                                     // isReTechnical=1技术分析提交
                                                     // 2补提
        List<String> dataCodeAndSample = getBySheet(sheet, map, dataCode);
        
        TechnicalAnalySheetSamplesModel model = getSamplesModel(id);
        List<TechnicalAnalySheetReceivedSample> samples = (null == model || null == model.getSamples()) ? Collections.emptyList() : model.getSamples();
        
        Set<String> sheetReceivedSamples = new HashSet<String>();
        Set<String> matchesSamples = new HashSet<String>();
        Map<String, TechnicalAnalySheetReceivedSample> sheetReceivedSamplesMap = new HashMap<String, TechnicalAnalySheetReceivedSample>();
        
        for (TechnicalAnalySheetReceivedSample sample : samples)
        {
            sheetReceivedSamples.add(sample.getSampleCode());
            sheetReceivedSamplesMap.put(sample.getSampleCode(), sample);
        }
        
        List<TechnicalAnalyParseRecord> total = new ArrayList<TechnicalAnalyParseRecord>();
        List<TechnicalAnalyParseRecord> valids = new ArrayList<TechnicalAnalyParseRecord>();
        List<TechnicalAnalyParseRecord> invalids = new ArrayList<TechnicalAnalyParseRecord>();
        List<TechnicalAnalyRecord> validsReocrd = new ArrayList<TechnicalAnalyRecord>();
        
        if (!CollectionUtils.isEmpty(records))
        {
            TechnicalAnalyRecordValidateContext context = new TechnicalAnalyRecordValidateContext(sheetReceivedSamples, dataCodeAndSample, map);
            // 校验历史重复记录,如果当前流程被取消，则不校验历史提交记录
            // TechnicalAnalySheet completeSheet =
            // ngsService.getTechnicalAnalySheet(id);
            // if (null != completeSheet &&
            // Collections3.isNotEmpty(completeSheet.getRecords()))
            // {
            // for (TechnicalAnalySubmitRecord comleteRecord :
            // completeSheet.getRecords())
            // {
            //
            // parseRecord = new TechnicalAnalyParseRecord();
            // parseRecord.setTemporaryId(UUID.randomUUID().toString().replaceAll("-",
            // ""));
            // parseRecord.setData(comleteRecord.getRecord());
            // parseRecord.setValid(true);
            // context.setTarget(parseRecord);
            // context.valid();
            // }
            // }
            
            List<ITechnicalAnalyRecordValidator> validators = new ArrayList<ITechnicalAnalyRecordValidator>();
            validators.add(new SampleColumnValidator());
            validators.add(new LocusTypeColumnValidator());
            validators.add(new VerifyColumnValidator());
            validators.add(new VerifyMethodColumnValidator());
            validators.add(new SangerVerifyValidator());
            validators.add(new PCRNGSVerifyValidator());
            validators.add(new QPCRVerifyValidator());
            validators.add(new MLPAVerifyValidator());
            validators.add(new ReferenceLocusValidator());
            validators.add(new ReportLocusValidator());
            validators.add(new LocusDuplicateValidator());
            validators.add(new DataCodeColumnValidator());
            
            for (TechnicalAnalyRecord record : records)
            {
                parseRecord = new TechnicalAnalyParseRecord();
                parseRecord.setTemporaryId(UUID.randomUUID().toString().replaceAll("-", ""));
                parseRecord.setData(record);
                parseRecord.setValid(true);
                context.setTarget(parseRecord);
                
                for (ITechnicalAnalyRecordValidator validator : validators)
                {
                    validator.validate(context);
                    
                    if (!parseRecord.isValid())
                    {
                        break;
                    }
                }
                
                if (parseRecord.isValid())
                {
                    valids.add(parseRecord);
                    matchesSamples.add(parseRecord.getData().getDataCode());
                    context.valid();
                    validsReocrd.add(record);
                }
                else
                {
                    invalids.add(parseRecord);
                }
                
                total.add(parseRecord);
                
                //
                record.setValid(parseRecord.isValid());
                record.setTemporaryId(parseRecord.getTemporaryId());
                record.setMessage(parseRecord.getMessage());
                //
            }
        }
        
        // 验证记录设置样本
        for (TechnicalAnalyRecord valid : validsReocrd)
        {
            if (!valid.isVerify())
            {
                continue;
            }
            
            valid.setRelatedSample(sheetReceivedSamplesMap.get(valid.getSample()));
        }
        // 验证图片命名规则
        for (TestingDataPic pic : zipModel.getPicList())
        {
            boolean valid = true;
            String message = "有效";
            String picNameDataCode = pic.getDataCode();
            if (!dataCode.contains(picNameDataCode))
            {
                valid = false;
                message = "无效-图片名称数据编号有误";
            }
            pic.setValid(valid);
            pic.setMessage(message);
        }
        records.sort(Comparator.comparing(TechnicalAnalyRecord::isValid).reversed());
        DefaultTableModel modelMap = new DefaultTableModel(columns, records);
        TechnicalAnalyParseResult result = new TechnicalAnalyParseResult();
        result.setRecords(total);
        result.setValids(valids);
        result.setInvalids(invalids);
        result.setModelMap(modelMap);
        result.setPicList(zipModel.getPicList());
        result.setLocalFilePath(zipModel.getLocalFilePath());
        result.setUploadDir(zipModel.getUploadDir());
        result.setPicParentPath(zipModel.getPicParentPath());
        result.setMatchesSamples(matchesSamples);
        return result;
    }
    
    private TechnicalAnalySheetSamplesModel getSamplesModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/technical-analy/sheets/{id}/samples");
        return template.getForObject(url, TechnicalAnalySheetSamplesModel.class, id);
    }
    
    @Override
    public void submit(TechnicalAnalySubmitForm data, TechnicalAnalyParseResult parseResult)
    {
        try
        {
            TechnicalAnalySubmitRequest request = getSubmitRequest(data, parseResult);
            String url = GatewayService.getServiceUrl("/bpm/testing/technical-analy/sheets/submit");
            System.out.println(JSON.toJSONString(request));
            template.postForLocation(url, request);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private TechnicalAnalySubmitRequest getSubmitRequest(TechnicalAnalySubmitForm data, TechnicalAnalyParseResult parseResult)
    {
        TechnicalAnalySubmitRequest request = new TechnicalAnalySubmitRequest();
        request.setId(data.getId());
        request.setSubmitType(data.getSubmitType());
        if (!StringUtils.isEmpty(data.getTaskIds()))
        {
            request.setTaskList(Arrays.asList(data.getTaskIds().split(",")));
        }
        
        // 处理界面数据
        List<TechnicalAnalyVerifySample> verifySamples;
        
        if (Collections3.isNotEmpty(data.getRecords()))
        {
            for (TechnicalAnalySubmitRecordArgs record : new ArrayList<TechnicalAnalySubmitRecordArgs>(data.getRecords()))
            {
                if (StringUtils.isEmpty(record.getTemporaryId()))
                {
                    data.getRecords().remove(record);
                    continue;
                }
                
                verifySamples = record.getVerifySamples();
                
                if (!CollectionUtils.isEmpty(verifySamples))
                {
                    for (TechnicalAnalyVerifySample sample : new ArrayList<TechnicalAnalyVerifySample>(verifySamples))
                    {
                        if (StringUtils.isEmpty(sample.getId()))
                        {
                            verifySamples.remove(sample);
                        }
                    }
                }
            }
        }
        
        Map<String, TechnicalAnalyRecord> context = new HashMap<String, TechnicalAnalyRecord>();
        
        if (null != parseResult)
        {
            if (!CollectionUtils.isEmpty(parseResult.getValids()))
            {
                for (TechnicalAnalyParseRecord record : parseResult.getValids())
                {
                    logger.error("~~~技术分析提交~~" + record.getTemporaryId() + "~~~message~~" + record.getMessage());
                    context.put(record.getTemporaryId(), record.getData());
                }
            }
            
            // 组装保存图片的数据
            DataAnalysisParseModel parseModel = new DataAnalysisParseModel();
            parseModel.setLocalFilePath(parseResult.getLocalFilePath());
            parseModel.setUploadDir(parseResult.getUploadDir());
            parseModel.setPicList(parseResult.getPicList());
            List<TestingDataPic> list = mlpaDataDataService.uploadFileAndWrapData(parseModel, request.getId());
            request.setPicList(list);
        }
        
        TechnicalAnalySubmitRecord record;
        List<TechnicalAnalySubmitRecord> records = new ArrayList<TechnicalAnalySubmitRecord>();
        
        for (TechnicalAnalySubmitRecordArgs recordArgs : data.getRecords())
        {
            record = new TechnicalAnalySubmitRecord();
            record.setVerify(recordArgs.isVerify());
            record.setVerifyMethod(recordArgs.getVerifyMethod());
            record.setVerifySamples(recordArgs.getVerifySamples());
            record.setRecord(context.get(recordArgs.getTemporaryId()));
            records.add(record);
        }
        
        request.setRecords(records);
        return request;
    }
    
    public List<String> getBySheet(TechnicalAnalySubmitModel sheet, Map<String, Boolean> map, List<String> dataCodeList)
    {
        List<String> result = Lists.newArrayList();
        if (null != sheet)
        {
            List<TechnicalAnalyTask> tasks = sheet.getTasks();
            if (Collections3.isNotEmpty(tasks))
            {
                for (TechnicalAnalyTask task : tasks)
                {
                    if (!dataCodeList.contains(task.getDataCode()))
                    {
                        dataCodeList.add(task.getDataCode());
                    }
                    boolean isSubmitted = false;
                    if (!StringUtils.isEmpty(sheet.getSubmiterId()))
                    {
                        isSubmitted = true;// 说明是补提验证，这个时候不验证数据提交的是已提交验证的任务
                    }
                    String combineStr = task.getDataCode() + "_" + task.getReceivedSampleCode();
                    if (!result.contains(combineStr))
                    {
                        result.add(combineStr);
                    }
                    if (task.getQualifiedType() == 2)
                    {
                        isSubmitted = true;
                    }
                    if (!map.containsKey(combineStr))
                    {
                        map.put(combineStr, isSubmitted);
                    }
                }
                
            }
        }
        return result;
    }
    
    @Override
    public List<TechnicalAnalyTask> getListBySequencingCode(TechnicalAnalyAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/technical-analy/exportSummary");
        ResponseEntity<List<TechnicalAnalyTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TechnicalAnalyAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<TechnicalAnalyTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public String downloadBySequencingCode(String sequencingCode, List<TechnicalAnalyTask> tasks)
    {
        InputStream inputStream = TechnicalAnalyService.class.getResourceAsStream("/taskTemplate/TEC_ANALYSIS_SUMMARY.xlsx");
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
        String time = testSheetService.getFormatTime("yyyy-MM-dd", new Date());
        File file = new File(sequencingCode + "_" + time + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (TechnicalAnalyTask task : tasks)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                String sendDate = "";
                String reportDate = "";
                if (null != task.getSubmitTime())
                {
                    sendDate = dateFormater.format(task.getSubmitTime());
                }
                if (null != task.getReportDate())
                {
                    reportDate = dateFormater.format(task.getReportDate());
                }
                data.put(1, task.getSequencingCode());
                data.put(2, task.getProductCode());
                data.put(3, task.getMarketingCenter());
                data.put(4, task.getDataCode());
                data.put(5, task.getReceivedSampleName());
                if ("0".equals(task.getSex()))
                {
                    data.put(6, "男");
                }
                else if ("1".equals(task.getSex()))
                {
                    data.put(6, "女");
                }
                else
                {
                    data.put(6, task.getSex());
                }
                data.put(7, task.getCaseNum());
                data.put(8, task.getProductName());
                data.put(9, sendDate);
                data.put(10, task.getReceivedSampleCode());
                data.put(11, task.getAge());
                data.put(12, task.getCustomerCompanyName());
                data.put(13, task.getMethodName());
                data.put(14, reportDate);
                data.put(15, task.getProbeName());
                data.put(16, task.getClinicalDiagnosis());
                data.put(17, task.getPhenotype());
                data.put(18, task.getFocusGenes());
                data.put(19, task.getCaseSummary());
                data.put(20, task.getFamilyHistorySummary());
                data.put(21, task.getSampleTypeName());
                data.put(22, task.getCustomerName());
                data.put(23, task.getBusinessLeader());
                data.put(24, task.getTechnicalPrincipal());
                data.put(25, task.getOrderCode());
                data.put(26, task.getContractType());
                data.put(27, task.getRemark());
                data.put(28, task.getCheckedCount());
                datalist.add(data);
            }
            
            String[] heads =
                new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1", "O1", "P1", "Q1", "R1", "S1", "T1", "U1",
                    "V1", "W1", "X1", "Y1", "Z1", "[1","\\1"}; // 必须为列表头部所有位置集合，
                                                         // 输出
                                                         // 数据单元格样式和头部单元格样式保持一致
            
            excel.writeDateList(path, heads, datalist, wsheet);
            
            // 写到输出流并移除资源
            excel.writeAndClose(path, os);
            os.flush();
            
        }
        catch (FileNotFoundException e)
        {
            
            e.printStackTrace();
        }
        catch (IOException e)
        {
            
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
    
    @Override
    public void generateData()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/tecanalys/generateData");
        template.getForObject(url, String.class);
    }
    
    public static ZipFileUploadModel unZipFiles(MultipartFile zipFileUpload, Map<String, String> picNameMap)
    {
        ZipFileUploadModel model = new ZipFileUploadModel();
        File xlsFile = null;
        int xlsFileCount = 0;
        File filePath = new File(ConfigManage.getkey("uploadPath"));
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        Date date = new Date();
        String dateStr = dateFormater.format(date);
        String descFileNames = filePath.getPath();
        String mulu = "";
        String fileName = filePath + "\\" + dateStr + zipFileUpload.getOriginalFilename();// zip
                                                                                          // file
        File fileZipLocal = new File(fileName);
        if (!descFileNames.endsWith(File.separator))
        {
            descFileNames = descFileNames + File.separator;
        }
        try
        {
            InputStream in = zipFileUpload.getInputStream();
            FileUtils.inputstreamToFile(in, fileZipLocal);
            // 根据ZIP文件创建ZipFile对象
            ZipFile zipFile = new ZipFile(fileName, "gbk");
            ZipEntry entry = null;
            String entryName = null;
            String descFileDir = null;
            byte[] buf = new byte[4096];
            int readByte = 0;
            // 获取ZIP文件里所有的entry
            @SuppressWarnings("rawtypes")
            Enumeration enums = zipFile.getEntries();
            // 遍历所有entry
            while (enums.hasMoreElements())
            {
                String dataCodeStr = "";
                boolean xlsFileBool = false;
                entry = (ZipEntry)enums.nextElement();
                // 获得entry的名字
                entryName = entry.getName();
                String fileResult = "";
                String sufffixname = "";
                descFileDir = descFileNames + entryName;
                if (entry.isDirectory())
                {
                    mulu = descFileDir;
                    // 如果entry是一个目录，则创建目录
                    new File(descFileDir).mkdirs();
                    continue;
                }
                else
                {
                    String arr[] = entryName.split("/");
                    if (1 < arr.length)
                    {
                        fileResult = dateStr + "_" + arr[1];
                        dataCodeStr = arr[1];
                    }
                    else
                    {
                        fileResult = dateStr + "_" + entryName;
                        dataCodeStr = entryName;
                    }
                    // 取文件后缀名
                    
                    int fileIndex = entryName.lastIndexOf(".");
                    if (fileIndex != -1)
                    {
                        sufffixname = entryName.substring(entryName.lastIndexOf(".") + 1);
                    }
                    if ("xlsx".equalsIgnoreCase(sufffixname) || "xls".equalsIgnoreCase(sufffixname))
                    {
                        xlsFileCount++;
                        xlsFileBool = true;
                    }
                    else if ("jpg".equalsIgnoreCase(sufffixname) || "png".equalsIgnoreCase(sufffixname) || "gif".equalsIgnoreCase(sufffixname)
                        || "jpeg".equalsIgnoreCase(sufffixname) || "bmp".equalsIgnoreCase(sufffixname))
                    {
                        String dataCode = dataCodeStr.split("#")[0];
                        if (picNameMap.containsKey(dataCode))
                        {
                            picNameMap.put(dataCode, picNameMap.get(dataCode) + "," + fileResult);
                        }
                        else
                        {
                            picNameMap.put(dataCode, fileResult);
                        }
                    }
                    // 如果entry是一个文件，则创建父目录
                    new File(descFileDir).getParentFile().mkdirs();
                }
                
                if (!xlsFileBool)
                {
                    File filePic = new File(descFileDir);
                    // 打开文件输出流
                    OutputStream os = new FileOutputStream(filePic);
                    // 从ZipFile对象中打开entry的输入流
                    InputStream is = zipFile.getInputStream(entry);
                    while ((readByte = is.read(buf)) != -1)
                    {
                        os.write(buf, 0, readByte);
                    }
                    os.close();
                    is.close();
                    FileUtils.uploadQiniu(filePic, fileResult);
                    filePic.delete();
                }
                else
                {
                    xlsFile = new File(descFileDir);
                    OutputStream os = new FileOutputStream(xlsFile);
                    // 从ZipFile对象中打开entry的输入流
                    InputStream is = zipFile.getInputStream(entry);
                    while ((readByte = is.read(buf)) != -1)
                    {
                        os.write(buf, 0, readByte);
                    }
                    os.close();
                    is.close();
                }
            }
            if (1 < xlsFileCount)
            {
                throw new IllegalStateException();
            }
            model.setFile(xlsFile);
            model.setZipFile(zipFile);
            model.setUploadDir(mulu);
            model.setLocalZipFile(fileZipLocal);
            return model;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    private static boolean deleteDir(File dir)
    {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++)
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
    public List<TestingDataPic> wrap(Map<String, String> picNameMap)
    {
        List<TestingDataPic> list = Lists.newArrayList();
        if (null != picNameMap)
        {
            TestingDataPic model;
            for (String key : picNameMap.keySet())
            {
                model = new TestingDataPic();
                model.setDataCode(key);
                model.setPicUrl(picNameMap.get(key));
                list.add(model);
            }
        }
        return list;
    }
    
    @Override
    public CnvAnalysisDataModel getRecords(CapCnvRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getcapcnvlist");
        ResponseEntity<CnvAnalysisDataModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CapCnvRequest>(request), new ParameterizedTypeReference<CnvAnalysisDataModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<TechnicalAnalysisTask> getTechnicalAnalysisAssignableList(TechnicalAnalysisAssignableTaskSearcher searcher, int pageNo)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getAssginableTasks/{pageNo}/{pageSize}");
        ResponseEntity<Pagination<TechnicalAnalysisTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TechnicalAnalysisAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<TechnicalAnalysisTask>>()
                {
                },
                pageNo,
                20);
        return exchange.getBody();
    }
    
    @Override
    public Integer claimTechnicalAnalysis(String familyGroupId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTechnicalAnalysis/{familyGroupId}");
        return template.postForObject(url, new TechnicalAnalysisAssignableTaskSearcher(), Integer.class, familyGroupId);
    }
    
    @Override
    public List<String> queryRecentFilterList(MutationFilterForm request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/queryRecentFilterList");
        return template.postForObject(url, request, List.class);
    }
    
    @Override
    public List<ClaimTemplateColumnForDoctor> getClaimTemplateColumnList()
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/getClaimTempleColumnForDoctor");
        ResponseEntity<List<ClaimTemplateColumnForDoctor>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TechnicalAnalysisAssignableTaskSearcher>(new TechnicalAnalysisAssignableTaskSearcher()),
                new ParameterizedTypeReference<List<ClaimTemplateColumnForDoctor>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public boolean ifFamilyAnalysis(String analysisSampleId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/ifFamilyAnalysis/{analysisSampleId}");
        return template.getForObject(url, Boolean.class, Collections.singletonMap("analysisSampleId", analysisSampleId));
    }
    
    @Override
    public DrawPictureModel getCapCnvPicture(String ids, String sampleCode, String sampleAnalysisId)
    {
        CapCnvDrawPicRequest request = new CapCnvDrawPicRequest();
        request.setIds(ids);
        request.setSampleCode(sampleCode);
        request.setSampleAnalysisId(sampleAnalysisId);
        String url = GatewayService.getServiceUrl("/technicalanaly/getCapCnvPicture");
        ResponseEntity<DrawPictureModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CapCnvDrawPicRequest>(request), new ParameterizedTypeReference<DrawPictureModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String downloadFilterData(CapCnvRequest request)
    {
        ExcelUtil excel = new ExcelUtil();
        
        String fileName = "cnv_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
        File tempFile = new File(fileName);
        List<String> heads = Arrays.asList("染色体位置", "基因", "区域", "拷贝数", "P值");
        List<List<String>> records = Lists.newArrayList();//行数据
        int pageNo = 0;
        int pageSize = 1000;
        List<CapCnvData> pageCapCnvDataList = null;
        while (true)
        {
            if (pageNo != 0)
            {
                heads = null;
            }
            CapCnvRequest pageCapCnvRequest = new CapCnvRequest();
            BeanUtils.copyProperties(request, pageCapCnvRequest);
            pageCapCnvRequest.setPageNo(pageNo);
            pageCapCnvRequest.setPageSize(pageSize);
            pageNo++;
            pageCapCnvDataList = getcapCnvDataListByRequest(pageCapCnvRequest);
            if (pageCapCnvDataList == null || pageCapCnvDataList.size() <= 0)
            {
                break;
            }
            if (Collections3.isNotEmpty(pageCapCnvDataList))
            {
                for (CapCnvData data : pageCapCnvDataList)
                {
                    List<String> record = Lists.newArrayList();
                    record.add(StringUtils.isEmpty(data.getChrLocation()) ? "" : data.getChrLocation());
                    record.add(StringUtils.isEmpty(data.getGene()) ? "" : data.getGene());
                    record.add(StringUtils.isEmpty(data.getArea()) ? "" : data.getArea());
                    record.add(null == data.getCopyNumber() ? "" : data.getCopyNumber().toString());
                    record.add(null == data.getpValue() ? "" : data.getpValue().toString());
                    records.add(record);
                }
            }
            try
            {
                logger.info(tempFile.getPath());
                excel.exportObjects2Excel(records, heads, "cnv", false, tempFile.getPath());//true-2007xlsx,false-2003xls 导出xlsx有问题
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
            }
        }
        
        return tempFile.getAbsolutePath();
    }
    
    public List<CapCnvData> getcapCnvDataListByRequest(CapCnvRequest request)
    {
        
        String url = GatewayService.getServiceUrl("/technicalanaly/cnvAnalysisDataList");
        ResponseEntity<ListResponse<CapCnvData>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CapCnvRequest>(request), new ParameterizedTypeReference<ListResponse<CapCnvData>>()
            {
            });
        return exchange.getBody().getData();
    }
    
    @Override
    public void downloadData(HttpServletResponse response, String path)
    {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        File file = null;
        try
        {
            // path是指欲下载的文件的路径。
            file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            // 清空response
            response.reset();
            // 设置response的Header
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(buffer);
            outputStream.flush();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
                outputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if (null != file)
        {
            file.delete();
        }
    }
    
    @Override
    public void saveAnalysisCapCnvResult(CapCnvResultData capCnvResultData)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/saveAnalysisCapCnvResult");
        template.postForLocation(url, capCnvResultData);
    }
    
    @Override
    public List<CompareSample> getCompareSample(CompareSampleRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getCompareSampleList");
        ResponseEntity<List<CompareSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CompareSampleRequest>(request), new ParameterizedTypeReference<List<CompareSample>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void compareSampleReanalysis(CompareSampleReanalysisRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/compareSampleReanalysis");
        template.postForLocation(url, request);
    }
    
    @Override
    public void saveFilterCreate(MutationFilterForm request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/saveMutationFilter");
        template.postForObject(url, request, List.class);
        
    }
    
    @Override
    public DataColAndMutationDataModel queryMutationData(MutationDataRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/queryMutationDataByFilter");
        return template.postForObject(url, request, DataColAndMutationDataModel.class);
    }
    
    @Override
    public boolean ifColletion(DoctorIfColletionRequest req)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/ifColletion");
        return template.postForObject(url, req, boolean.class);
    }
    
    @Override
    public List<TechnicalAnalysisSampleModel> getFamilySampleByFamilyId(String familyId)
    {
        
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/getFamilySampleByFamilyId/{familyId}");
        ResponseEntity<List<TechnicalAnalysisSampleModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TechnicalAnalysisSampleModel>(new TechnicalAnalysisSampleModel()),
                new ParameterizedTypeReference<List<TechnicalAnalysisSampleModel>>()
                {
                },
                familyId);
        return exchange.getBody();
        
    }
    
    @Override
    public Integer updatePhenoTypePoints(PhenoTypePointsRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/phenoType/phenoTypePoints");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public List<MutationStatisticsModel> getQualitycontrolDataByaAnalysisId(MutationStatisticsSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getQualitycontrolDataByaAnalysisId");
        return template.postForObject(url, searcher, List.class);
    }
    
    @Override
    public String downloadFilterData(MutationDataRequest req, String name, String fileType) throws Exception
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/queryMutationDataByFilterForDownload");
        DataColAndMutationDataModel model = template.postForObject(url, req, DataColAndMutationDataModel.class);
        List<List<String>> list = warp(model);
        Workbook wb = null;
        String time = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
        File file = new File(name + "_" + time + "." + fileType);
        Sheet sheet = null;
        // 创建工作文档对象
        if (!file.exists())
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();
                
            }
            else if (fileType.equals("xlsx"))
            {
                
                wb = new XSSFWorkbook();
            }
            // 创建sheet对象
            sheet = wb.createSheet("sheet1");
            OutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        else
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();
            }
            else if (fileType.equals("xlsx"))
            {
                wb = new XSSFWorkbook();
            }
        }
        // 创建sheet对象
        if (sheet == null)
        {
            sheet = wb.createSheet("sheet1");
        }
        
        // -----------内容设置----------------
        CellStyle style3 = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style3.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style3.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style3.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font3 = wb.createFont();
        font3.setFontName("宋体");
        // font3.setFontHeight((short)240);
        style3.setFont(font3);
        
        Row row = null;
        Cell cell = null;
        List<String> oneHeadList = model.getColList();// 一级列头(不重复)
        List<String> oneHeadForTwoList = Lists.newArrayList();// 一级列头(根据二级标题重复生成一级标题)
        String[] headnum0 = new String[oneHeadList.size()];// 对应excel中的行和列，下标从0开始{"开始行,结束行,开始列,结束列"}
        List<String> twoHeadList = Lists.newArrayList();// 二级列头
        int kk = 0;
        int ssize = 0;
        for (int k = 0; k < oneHeadList.size(); k++)
        {
            oneHeadForTwoList.add(oneHeadList.get(k));
            if (null != model.getSenondColName())
            {
                List<String> subList = model.getSenondColName().get(oneHeadList.get(k));
                if (Collections3.isNotEmpty(subList))
                {
                    ssize = subList.size();
                    kk = k;
                    twoHeadList = subList;
                    for (int i = 0; i < subList.size() - 1; i++)
                    {
                        oneHeadForTwoList.add(oneHeadList.get(k));
                    }
                    headnum0[k] = "0,0," + k + "," + (k + subList.size() - 1);
                }
                else
                {
                    if (k > kk && ssize != 0)
                    {
                        headnum0[k] = "0,1," + (k + ssize - 1) + "," + (k + ssize - 1);
                    }
                    else
                    {
                        headnum0[k] = "0,1," + k + "," + k;
                    }
                }
            }
            else
            {
                headnum0[k] = "0,1," + k + "," + k;
            }
        }
        String[] headnum1 = new String[twoHeadList.size()];
        for (int i = 0; i < twoHeadList.size(); i++)
        {
            headnum1[i] = "1,1," + (kk + i) + "," + (kk + i);
        }
        
        row = sheet.createRow(0); // 创建第1行
        for (int j = 0; j < oneHeadForTwoList.size(); j++)
        {
            cell = row.createCell(j);
            cell.setCellValue(oneHeadForTwoList.get(j));
            cell.setCellStyle(style3); // 样式，居中
            sheet.setColumnWidth(j, 20 * 256);
        }
        row.setHeight((short)450);
        // 动态合并单元格
        for (int j = 0; j < headnum0.length; j++)
        {
            String[] temp = headnum0[j].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        row = sheet.createRow(1); // 创建第2行
        for (int j = 0; j < oneHeadForTwoList.size(); j++)
        {
            cell = row.createCell(j);
            cell.setCellStyle(style3); // 样式，居中
            if (j > kk - 1 && j < kk + twoHeadList.size())
            {
                for (int i = 0; i < twoHeadList.size(); i++)
                {
                    cell = row.createCell(i + kk);
                    cell.setCellValue(twoHeadList.get(i));// 给excel中第二行的18、19、20、21列赋值
                    cell.setCellStyle(style3);// 设置excel中第二行的18、19、20、21列的边框
                }
            }
        }
        // 动态合并单元格
        for (int i = 0; i < headnum1.length; i++)
        {
            String[] temp = headnum1[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        
        ExcelUtil excel = new ExcelUtil();
        String path2 = file.getPath();
        excel.exportObjects2ModuleExcel(list, 2, wb, "sheet1", true, path2);
        wb.write(new FileOutputStream(path2));
        return file.getAbsolutePath();
    }
    
    private List<List<String>> warp(DataColAndMutationDataModel model)
    {
        List<List<String>> list = Lists.newArrayList();
        if (null != model)
        {
            List<String> colList = model.getColList();
            List<Map<String, String>> valueList = model.getDataValue();
            if (Collections3.isNotEmpty(valueList))
            {
                for (Map<String, String> map : valueList)
                {
                    List<String> vl = Lists.newArrayList();
                    for (String col : colList)
                    {
                        if ("Evidence".equals(col))
                        {
                            if (null != model.getSenondColName())
                            {
                                List<String> subList = model.getSenondColName().get(col);
                                if (null != subList)
                                {
                                    for (String s : subList)
                                    {
                                        vl.add(map.get(s));
                                    }
                                }
                            }
                        }
                        else
                        {
                            vl.add(map.get(col));
                        }
                    }
                    list.add(vl);
                }
            }
        }
        return list;
    }
    
    @Override
    public List<TechnicalAnalysisTask> searchTechnicalAnalysisTask(TechnicalAnalysisTaskRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/searchTechnicalAnalysisTask");
        ResponseEntity<List<TechnicalAnalysisTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TechnicalAnalysisTaskRequest>(request),
                new ParameterizedTypeReference<List<TechnicalAnalysisTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public String getEvidenceByMongoId(String mongoId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getEvidenceByMongoId/{mongoId}");
        return template.getForObject(url, String.class, Collections.singletonMap("mongoId", mongoId));
    }
    
    @Override
    public UploadEvidenceRespModel uploadEvidence(UploadEvidenceRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/uploadEvidence");
        return template.postForObject(url, request, UploadEvidenceRespModel.class);
    }
    
    @Override
    public Map<String, String> getGroupDetails(GroupNameDetailsRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/getGroupDetails");
        return template.postForObject(url, request, Map.class);
    }
    
    @Override
    public String getMutationDetail(String objectId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getMutationDetail/{objectId}");
        return template.getForObject(url, String.class, Collections.singletonMap("objectId", objectId));
    }
    
    @Override
    public void submitCnvVerify(SubmitCnvVerifyRequest submitCnvVerifyRequest)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/submitCnvVerify");
        template.postForLocation(url, submitCnvVerifyRequest);
    }
    
    @Override
    public void cancelSubmitCnvVerify(CancelCollectionCnvAnalysisResult request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/cancelSubmitCnvVerify");
        template.postForObject(url, request, void.class);
    }
    
    @Override
    public Integer collection(TechnicalCollectionRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/collection");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public void saveAddVerify(TechnicalAddVerifyRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/saveAddVerify");
        template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public List<TechnicalAnalysisAddVerify> getAddVerifyDataByFamilyId(String analysisFamilyId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/getAddVerifyDataByFamilyId/{familyId}");
        ResponseEntity<List<TechnicalAnalysisAddVerify>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TechnicalAnalysisAddVerify>(new TechnicalAnalysisAddVerify()),
                new ParameterizedTypeReference<List<TechnicalAnalysisAddVerify>>()
                {
                },
                analysisFamilyId);
        return exchange.getBody();
    }
    
    @Override
    public void submitTechnicalTask(String familyGroupId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/submitTechnicalTask/{familyGroupId}");
        template.postForObject(url, new GroupNameDetailsRequest(), Integer.class, familyGroupId);
    }
    
    @Override
    public CnvAnalysisFamilyDataModel getFamilyCapcnvRecords(CapCnvRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getFamilyCapCnvList");
        ResponseEntity<CnvAnalysisFamilyDataModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CapCnvRequest>(request), new ParameterizedTypeReference<CnvAnalysisFamilyDataModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void startAnalsyisSchedule(VariableModel model)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/startAnalsyisSchedule");
        template.postForObject(url, model, Integer.class);
        
    }
    
    @Override
    public Integer cancelMutationCollection(MutationCollectionRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/cancelMutationCollection");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public VariableModel submitRecheckTechnicalTask(SubmitTechnicalTaskRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/submitRecheckTechnicalTask");
        return template.postForObject(url, request, VariableModel.class);
    }
    
    @Override
    public Integer myCollectionCount(String analysisSampleId, String technicalTaskId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/myCollectionCount/{analysisSampleId}/{userId}/{technicalTaskId}");
        return template.postForObject(url, new GroupNameDetailsRequest(), Integer.class, analysisSampleId, account.getUserId(), technicalTaskId);
    }
    
    @Override
    public String downloadFamilyFilterData(CapCnvRequest request)
    {
        List<Map> capCnvDataList = getFamilycapCnvDataListByRequest(request);
        String fileName = "cnv_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
        File tempFile = new File(fileName);
        List<String> heads = new ArrayList<String>();
        heads.add("染色体位置");
        heads.add("基因");
        heads.add("区域");
        
        List<List<String>> records = Lists.newArrayList();// 行数据
        if (Collections3.isNotEmpty(capCnvDataList))
        {
            Map<String, Object> map = capCnvDataList.get(0);
            for (String key : map.keySet())
            {
                if (key.contains("CopyRatio"))
                {
                    String head = key.replaceAll("CopyRatio", "拷贝数").replaceAll("()", "");
                    heads.add(head);
                }
                if (key.contains("P-value"))
                {
                    String head = key.replaceAll("P-value", "P值").replaceAll("()", "");
                    heads.add(head);
                }
            }
            for (Map<String, Object> data : capCnvDataList)
            {
                List<String> record = Lists.newArrayList();
                for (String key : data.keySet())
                {
                    if (!key.equals("_id") && !key.equals("analysisSampleId"))
                    {
                        record.add(data.get(key).toString());
                    }
                    
                }
                records.add(record);
            }
        }
        ExcelUtil excel = new ExcelUtil();
        try
        {
            excel.exportObjects2Excel(records, heads, "cnv", false, tempFile.getPath());// true-2007xlsx,false-2003xls
                                                                                        // 导出xlsx有问题
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return tempFile.getAbsolutePath();
        
    }
    
    private List<Map> getFamilycapCnvDataListByRequest(CapCnvRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/cnvFamilyAnalysisDataList");
        ResponseEntity<List<Map>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CapCnvRequest>(request), new ParameterizedTypeReference<List<Map>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<String> technicalanalySvdata(SvRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/sv/pager");
        PagerResponse<String> response = template.postForObject(url, request, PagerResponse.class);
        Pager<String> pager = response.getData();
        
        Pagination<String> pagination = new Pagination<String>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(pager.getRecords());
        return pagination;
    }
    
    @Override
    public void collectionCapcnvPic(CollectionCapcnvPicRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/collectionCapcnvPic");
        template.postForLocation(url, request);
    }
    
    @Override
    public List<BioInfoAnnotate> getAnnotateByfamily(String mongoId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getAnnotateByfamily/{mongoId}");
        
        ResponseEntity<List<BioInfoAnnotate>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<BioInfoAnnotate>(new BioInfoAnnotate()),
                new ParameterizedTypeReference<List<BioInfoAnnotate>>()
                {
                },
                mongoId);
        return exchange.getBody();
        
    }
    
    @Override
    public List<TechnicalAnalysisTask> getTaskByFamilyId(String familyAnalysisId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getTaskByFamilyId/{familyAnalysisId}");
        ResponseEntity<List<TechnicalAnalysisTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TechnicalAnalysisTask>(new TechnicalAnalysisTask()),
                new ParameterizedTypeReference<List<TechnicalAnalysisTask>>()
                {
                },
                familyAnalysisId);
        return exchange.getBody();
    }
    
    @Override
    public Pagination<SiteplotData> SiteplotDataSearch(SvRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/sv/regioncoutPager");
        PagerResponse<SiteplotData> response = template.postForObject(url, request, PagerResponse.class);
        Pager<SiteplotData> pager = response.getData();
        
        Pagination<SiteplotData> pagination = new Pagination<SiteplotData>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(pager.getRecords());
        return pagination;
        
    }
    
    @Override
    public DataColAndMutationDataModel searchMutationListByAnalsysiSampleId(String analysisSampleId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/searchMutationListByAnalsysiSampleId/{analysisSampleId}");
        ResponseEntity<DataColAndMutationDataModel> res = template.getForEntity(url, DataColAndMutationDataModel.class, analysisSampleId);
        return res.getBody();
    }
    
    @Override
    public List<CollectionCnvAnalysisPicResultList> collectionCapcnvData(String analysisSampleId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/collectionCapcnvData/{analysisSampleId}");
        ResponseEntity<List<CollectionCnvAnalysisPicResultList>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<CollectionCnvAnalysisPicResultList>(new CollectionCnvAnalysisPicResultList()),
                new ParameterizedTypeReference<List<CollectionCnvAnalysisPicResultList>>()
                {
                },
                analysisSampleId);
        return exchange.getBody();
        
    }
    
    @Override
    public void technicalReportOperate(TechnicalReportRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/technicalReportOperate");
        template.postForLocation(url, request);
    }
    
    @Override
    public com.todaysoft.lims.persist.Pagination<TestingSheet> completeSheetpaging(TestingSheetSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/completeSheetPaging/{pageNo}/{pageSize}");
        com.todaysoft.lims.persist.Pagination<TestingSheet> pagination = new com.todaysoft.lims.persist.Pagination<>();
        ResponseEntity<PageInfo<TestingSheet>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingSheetSearcher>(searcher), new ParameterizedTypeReference<PageInfo<TestingSheet>>()
            {
            }, searcher.getPageNo(), searcher.getPageSize());
        PageInfo<TestingSheet> page = exchange.getBody();
        pagination.setRecords(page.getList());
        pagination.setTotalCount((int)page.getTotal());
        pagination.setPageSize(searcher.getPageSize());
        pagination.setPageNo(searcher.getPageNo());
        return pagination;
    }
    
    @Override
    public TestingTaskRequest getTTRById(String testingTaskId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getTaskById/{testingTaskId}");
        return template.getForObject(url, TestingTaskRequest.class, testingTaskId);
    }
    
    @Override
    public TestingSheet getTestingSheetByTaskId(String taskId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getSheetListByTaskId/{taskId}");
        ResponseEntity<List<TechnicalAnalysisSheetModel>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TechnicalAnalysisSheetModel>>()
            {
            }, taskId);
        List<TechnicalAnalysisSheetModel> records = exchange.getBody();
        if (Collections3.isNotEmpty(records))
        {
            TechnicalAnalysisSheetModel sheet = records.get(0);
            TestingSheet testingSheet = new TestingSheet();
            testingSheet.setId(sheet.getId());
            testingSheet.setAssignerId(sheet.getTesterId());
            testingSheet.setAssignerName(sheet.getTesterName());
            testingSheet.setAssignTime(sheet.getCreateTime());
            testingSheet.setCode(sheet.getCode());
            testingSheet.setTaskName("新技术分析");
            testingSheet.setTesterId(sheet.getTesterId());
            testingSheet.setTesterName(sheet.getTesterName());
            testingSheet.setSemantic(TaskSemantic.TECHNICAL_ANALYSIS);
            testingSheet.setSubmitTime(sheet.getSubmitTime());
            return testingSheet;
        }
        return null;
    }
    
    @Override
    public List<TestTask> getTestingTaskSheet()
    {
        return null;
    }
    
    @Override
    public void deleteVerifyDataById(String id)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/deleteVerifyDataById/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @Override
    public Boolean checkTechnicalAnalysisIsFinished(String analysisSampleId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/checkTechnicalAnalysisIsFinished/{analysisSampleId}");
        return template.getForObject(url, Boolean.class, analysisSampleId);
    }
    
    @Override
    public Boolean checkTechnicalAnalysisHasHpoTask(String analysisSampleId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/checkTechnicalAnalysisHasHpoTask/{analysisSampleId}");
        return template.getForObject(url, Boolean.class, analysisSampleId);
    }
    
    @Override
    public String getFamilyId(String id)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getFamilyId/{id}");
        return template.getForObject(url, String.class, id);
    }
    
    @Override
    public VariableModel getMod(String familyGroupId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/getMod/{familyGroupId}");
        return template.getForObject(url, VariableModel.class, familyGroupId);
    }
    
    @Override
    public TestingSheet getTestingSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getTestingSheet/{id}");
        TechnicalAnalysisSheetModel technicalAnalysisSheetModel = template.getForObject(url, TechnicalAnalysisSheetModel.class, id);
        TestingSheet testingSheet = new TestingSheet();
        testingSheet.setId(technicalAnalysisSheetModel.getId());
        testingSheet.setAssignerId(technicalAnalysisSheetModel.getTesterId());
        testingSheet.setAssignerName(technicalAnalysisSheetModel.getTesterName());
        testingSheet.setAssignTime(technicalAnalysisSheetModel.getCreateTime());
        testingSheet.setCode(technicalAnalysisSheetModel.getCode());
        testingSheet.setTaskName("新技术分析");
        testingSheet.setTesterId(technicalAnalysisSheetModel.getTesterId());
        testingSheet.setTesterName(technicalAnalysisSheetModel.getTesterName());
        testingSheet.setSemantic(TaskSemantic.TECHNICAL_ANALYSIS);
        testingSheet.setSubmitTime(technicalAnalysisSheetModel.getSubmitTime());
        return testingSheet;
        
    }
    
    @Override
    public TechnicalAnalyTask getTask(String familyGroupId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/getTask/{familyGroupId}");
        return template.getForObject(url, TechnicalAnalyTask.class, familyGroupId);
    }
    
    @Override
    public void saveAddVerifyNew(TechnicalAddVerifyRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/saveAddVerifyNew");
        template.postForObject(url, request, VariableModel.class);
    }
    
    @Override
    public List<TechnicalAnalysisTask> searchErrorTechnicalTask(String familyGroupId)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/searchErrorTechnicalTask/{familyAnalysisId}");
        ResponseEntity<List<TechnicalAnalysisTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TechnicalAnalysisTask>(new TechnicalAnalysisTask()),
                new ParameterizedTypeReference<List<TechnicalAnalysisTask>>()
                {
                },
                familyGroupId);
        return exchange.getBody();
        
    }
}
