package com.todaysoft.lims.testing.completeTasks.service.imp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.DataTemplate;
import com.todaysoft.lims.testing.base.entity.DataTemplateColumn;
import com.todaysoft.lims.testing.base.entity.MlpaVerifyRecord;
import com.todaysoft.lims.testing.base.entity.QpcrVerifyRecord;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.SequencingRecord;
import com.todaysoft.lims.testing.base.entity.TestingCaptureGroup;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.entity.TestingVerifyRecord;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.biologyanaly.service.IBiologyAnalyService;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAAttributes;
import com.todaysoft.lims.testing.cdnaqc.service.ICDNAQcService;
import com.todaysoft.lims.testing.completeTasks.model.ngs.BiologyAnalySheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.CDNAQcSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.CDNAQcTask;
import com.todaysoft.lims.testing.completeTasks.model.ngs.DNAQcSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.DNAQcTask;
import com.todaysoft.lims.testing.completeTasks.model.ngs.LibraryCaptureSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.LibraryCaptureSheetGroup;
import com.todaysoft.lims.testing.completeTasks.model.ngs.LibraryQcSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.LibraryQcTask;
import com.todaysoft.lims.testing.completeTasks.model.ngs.QtSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.RQTSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.RQTTask;
import com.todaysoft.lims.testing.completeTasks.model.ngs.SequencingSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.TechnicalAnalySheet;
import com.todaysoft.lims.testing.completeTasks.service.INGSCompleteService;
import com.todaysoft.lims.testing.dnaqc.model.DNAAttributes;
import com.todaysoft.lims.testing.dnaqc.service.IDNAQcService;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSampleAttributes;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSheetGroupModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSheetModel;
import com.todaysoft.lims.testing.libcap.service.ILibraryCaptureService;
import com.todaysoft.lims.testing.libcre.model.LibraryAttributes;
import com.todaysoft.lims.testing.libqc.model.LibraryQcSubmitModel;
import com.todaysoft.lims.testing.libqc.service.ILibraryQcService;
import com.todaysoft.lims.testing.ontesting.model.SequencingSubmitModel;
import com.todaysoft.lims.testing.ontesting.service.ISequencingService;
import com.todaysoft.lims.testing.qt.model.QtAssignReference;
import com.todaysoft.lims.testing.qt.model.QtSubmitModel;
import com.todaysoft.lims.testing.qt.model.QtSubmitSampleArgs;
import com.todaysoft.lims.testing.qt.service.IQtService;
import com.todaysoft.lims.testing.report.service.IDataTemplateService;
import com.todaysoft.lims.testing.rqt.model.RQTSheetModel;
import com.todaysoft.lims.testing.rqt.model.RQTTaskResultDetails;
import com.todaysoft.lims.testing.rqt.service.IRQTService;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyRecord;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitRecord;
import com.todaysoft.lims.testing.technicalanaly.service.ITechnicalAnalyService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class NGSCompleteService implements INGSCompleteService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private BSMAdapter bsmadapter;
    
    @Autowired
    private IDNAQcService dnaQcService;
    
    @Autowired
    private ICDNAQcService cdnaQcService;
    
    @Autowired
    private ILibraryQcService libQcService;
    
    @Autowired
    private ILibraryCaptureService libCpaService;
    
    @Autowired
    private IRQTService rqtService;
    
    @Autowired
    private IQtService qtService;
    
    @Autowired
    private ISequencingService sequencingService;
    
    @Autowired
    private ITechnicalAnalyService tecAnalyService;
    
    @Autowired
    private IBiologyAnalyService bioAnalyService;
    
    @Autowired
    private IDataTemplateService dataTemplateService;
    
    @Override
    public DNAQcSheet getDNAQcSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        com.todaysoft.lims.testing.dnaqc.model.DNAQcSheet assignSheet = dnaQcService.getTestingSheet(id);
        DNAQcSheet sheet = new DNAQcSheet();
        BeanUtils.copyProperties(assignSheet, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        List<DNAQcTask> tasks = Lists.newArrayList();
        TestingTask testingTask;
        TestingTaskResult result;
        DNAAttributes attributes;
        for (com.todaysoft.lims.testing.dnaqc.model.DNAQcTask assignTask : assignSheet.getTasks())
        {
            DNAQcTask task = new DNAQcTask();
            BeanUtils.copyProperties(assignTask, task);
            
            result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
            if (null != result)
            {
                if (TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
                {
                    task.setQualified(true);
                }
                else
                {
                    task.setUnqualifiedRemark(result.getRemark());
                    if (StringUtils.isNotEmpty(result.getStrategy()))
                    {
                        task.setUnqualifiedStrategy(result.getStrategy());
                    }
                    else
                    {
                        task.setUnqualifiedStrategy(result.getResult());
                    }
                }
            }
            
            testingTask = baseDaoSupport.get(TestingTask.class, task.getId());
            attributes = JSON.parseObject(testingTask.getInputSample().getAttributes(), DNAAttributes.class);
            if (null != attributes)
            {
                task.setConcn(attributes.getConcn());
                task.setVolume(attributes.getVolume());
                task.setRatio2623(attributes.getRatio2623());
                task.setRatio2628(attributes.getRatio2628());
                task.setQualityLevel(attributes.getQualityLevel());
            }
            
            tasks.add(task);
        }
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    public CDNAQcSheet getCDNAQcSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        com.todaysoft.lims.testing.cdnaqc.model.CDNAQcSheet assignSheet = cdnaQcService.getTestingSheet(id);
        CDNAQcSheet sheet = new CDNAQcSheet();
        BeanUtils.copyProperties(assignSheet, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        List<CDNAQcTask> tasks = Lists.newArrayList();
        TestingTask testingTask;
        TestingTaskResult result;
        CDNAAttributes attributes;
        for (com.todaysoft.lims.testing.cdnaqc.model.CDNAQcTask assignTask : assignSheet.getTasks())
        {
            CDNAQcTask task = new CDNAQcTask();
            BeanUtils.copyProperties(assignTask, task);
            
            result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
            if (null != result)
            {
                if (TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
                {
                    task.setQualified(true);
                }
                else
                {
                    task.setUnqualifiedRemark(result.getRemark());
                    if (StringUtils.isNotEmpty(result.getStrategy()))
                    {
                        task.setUnqualifiedStrategy(result.getStrategy());
                    }
                    else
                    {
                        task.setUnqualifiedStrategy(result.getResult());
                    }
                }
            }
            
            testingTask = baseDaoSupport.get(TestingTask.class, task.getId());
            attributes = JSON.parseObject(testingTask.getInputSample().getAttributes(), CDNAAttributes.class);
            if (null != attributes)
            {
                task.setConcn(attributes.getConcn());
                task.setVolume(attributes.getVolume());
                task.setRatio2623(attributes.getRatio2623());
                task.setRatio2628(attributes.getRatio2628());
                task.setQualityLevel(attributes.getQualityLevel());
            }
            
            tasks.add(task);
        }
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    public LibraryQcSheet getLibraryQcSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        LibraryQcSubmitModel assignSheet = libQcService.getSheet(id);
        LibraryQcSheet sheet = new LibraryQcSheet();
        BeanUtils.copyProperties(assignSheet, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        List<LibraryQcTask> tasks = Lists.newArrayList();
        TestingTask testingTask;
        TestingTaskResult result;
        LibraryAttributes attributes;
        for (com.todaysoft.lims.testing.libqc.model.LibraryQcTask assignTask : assignSheet.getTasks())
        {
            LibraryQcTask task = new LibraryQcTask();
            BeanUtils.copyProperties(assignTask, task);
            
            result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
            if (null != result)
            {
                if (TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
                {
                    task.setQualified(true);
                }
                else
                {
                    task.setUnqualifiedRemark(result.getRemark());
                    if (StringUtils.isNotEmpty(result.getStrategy()))
                    {
                        task.setUnqualifiedStrategy(result.getStrategy());
                    }
                    else
                    {
                        task.setUnqualifiedStrategy(result.getResult());
                    }
                }
            }
            
            testingTask = baseDaoSupport.get(TestingTask.class, task.getId());
            attributes = JSON.parseObject(testingTask.getInputSample().getAttributes(), LibraryAttributes.class);
            if (null != attributes)
            {
                task.setConcn(attributes.getConcn());
                task.setVolume(attributes.getVolume());
                task.setRatio2623(attributes.getRatio2623());
                task.setRatio2628(attributes.getRatio2628());
                task.setFragmentLengthStart(attributes.getFragmentLengthStart());
                task.setFragmentLengthEnd(attributes.getFragmentLengthEnd());
                task.setQualityLevel(attributes.getQualityLevel());
            }
            
            tasks.add(task);
        }
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    public LibraryCaptureSheet getLibCapSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        LibraryCaptureSheetModel sheetModel = libCpaService.getTestingSheet(id);
        
        LibraryCaptureSheet sheet = new LibraryCaptureSheet();
        
        BeanUtils.copyProperties(sheetModel, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<LibraryCaptureSheetGroup> groupList = Lists.newArrayList();
        TestingSample testingSample;
        TestingCaptureGroup result;
        LibraryCaptureSampleAttributes attributes;
        for (LibraryCaptureSheetGroupModel gorupModel : sheetModel.getGroups())
        {
            LibraryCaptureSheetGroup group = new LibraryCaptureSheetGroup();
            BeanUtils.copyProperties(gorupModel, group);
            
            result = baseDaoSupport.get(TestingCaptureGroup.class, group.getId());
            if (TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
            {
                group.setQualified(true);
            }
            else
            {
                group.setUnqualifiedRemark(result.getRemark());
                group.setUnqualifiedStrategy(result.getResult());
            }
            
            NamedQueryer query =
                NamedQueryer.builder()
                    .hql("FROM TestingSample ts WHERE ts.sampleCode = :sampleCode")
                    .names(Lists.newArrayList("sampleCode"))
                    .values(Lists.newArrayList(result.getTestingCode()))
                    .build();
            List<TestingSample> samples = baseDaoSupport.find(query, TestingSample.class);
            testingSample = Collections3.getFirst(samples);
            if (null != testingSample)
            {
                attributes = JSON.parseObject(testingSample.getAttributes(), LibraryCaptureSampleAttributes.class);
                group.setConcn(attributes.getConcn());
            }
            
            groupList.add(group);
        }
        sheet.setGroups(groupList);
        return sheet;
    }
    
    @Override
    public RQTSheet getRQTSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        RQTSheetModel sheetModel = rqtService.getTestingSheet(id);
        
        RQTSheet sheet = new RQTSheet();
        
        BeanUtils.copyProperties(sheetModel, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<RQTTask> tasks = Lists.newArrayList();
        TestingTaskResult result;
        RQTTaskResultDetails attributes;
        for (com.todaysoft.lims.testing.rqt.model.RQTTask assignTask : sheetModel.getTasks())
        {
            RQTTask task = new RQTTask();
            BeanUtils.copyProperties(assignTask, task);
            
            result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
            if (null != result)
            {
                attributes = JSON.parseObject(result.getDetails(), RQTTaskResultDetails.class);
                task.setCtv(attributes.getCtv());
            }
            
            tasks.add(task);
        }
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    public QtSheet getQtSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        QtSubmitModel qtModel = qtService.getSubmitModel(id);
        QtSheet sheet = new QtSheet();
        BeanUtils.copyProperties(qtModel, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        NamedQueryer query =
            NamedQueryer.builder()
                .hql("FROM SequencingRecord sr WHERE sr.qtSheetId = :qtSheetId")
                .names(Lists.newArrayList("qtSheetId"))
                .values(Lists.newArrayList(id))
                .build();
        List<SequencingRecord> primaryRecords = baseDaoSupport.find(query, SequencingRecord.class);
        if (Collections3.isNotEmpty(primaryRecords))
        {
            SequencingRecord primaryRecord = Collections3.getFirst(primaryRecords);
            QtSubmitSampleArgs primarySample = new QtSubmitSampleArgs();
            primarySample.setConcn(primaryRecord.getQtSequencingConcn());
            primarySample.setLibrarySizeOf2100(primaryRecord.getQt2100Value());
            primarySample.setLibrarySizeOfQbit(primaryRecord.getQtQbitValue());
            primarySample.setLibrarySizeOfQPCR(primaryRecord.getQtQPCRValue());
            primarySample.setFragmentLength(primaryRecord.getQtFragmentLength());
            if (null != primaryRecord.getQtQPCRValue())
            {
                primarySample.setLibraryValueOfQPCR(primaryRecord.getQtQPCRValue()
                    .multiply(new BigDecimal(452))
                    .divide(new BigDecimal(primaryRecord.getQtFragmentLength()), 2, RoundingMode.CEILING));
            }
            primarySample.setSequencingTime(primaryRecord.getSequencingTime());
            primarySample.setCluster(primaryRecord.getSequencingCluster());
            sheet.setPrimarySample(primarySample);
        }
        
        Iterator it = sheet.getReferences().iterator();
        while (it.hasNext())
        {
            QtAssignReference ref = (QtAssignReference)it.next();
            if (ref.getSampleCode().equals(sheet.getSampleCode()))
            {
                it.remove();
            }
        }
        
        return sheet;
    }
    
    @Override
    public SequencingSheet getSequencingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        SequencingSubmitModel sheetModel = sequencingService.getSubmitModel(id);
        
        SequencingSheet sheet = new SequencingSheet();
        
        BeanUtils.copyProperties(sheetModel, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        String hql = "FROM SequencingRecord r WHERE r.sequencingSheetId = :sequencingSheetId";
        List<SequencingRecord> records =
            baseDaoSupport.findByNamedParam(SequencingRecord.class, hql, new String[] {"sequencingSheetId"}, new Object[] {sheet.getId()});
        
        if (CollectionUtils.isEmpty(records) || records.size() > 1)
        {
        	return sheet;
        }
        SequencingRecord record = records.get(0);
        sheet.setSequencingCluster(record.getSequencingCluster());
        sheet.setSequencingEffectiveRate(record.getSequencingEffectiveRate());
        sheet.setSequencingEffectiveSize(record.getSequencingEffectiveSize());
        sheet.setSequencingQ30(record.getSequencingQ30());
        
        return sheet;
    }
    
    @Override
    public TechnicalAnalySheet getTechnicalAnalySheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        TechnicalAnalySubmitModel sheetModel = tecAnalyService.getSubmitModel(id);
        
        TechnicalAnalySheet sheet = new TechnicalAnalySheet();
        
        BeanUtils.copyProperties(sheetModel, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<String> columnNames = Lists.newArrayList();
        DataTemplate dataTemplate = dataTemplateService.getDataTemplateMapBySheetId(id);
        //dataTemplate.getColumnList().stream().forEach(x -> columnNames.add(x.getColumnName()));
        if (null != dataTemplate)
        {
            for (DataTemplateColumn dtc : dataTemplate.getColumnList())
            {
                columnNames.add(dtc.getColumnName());
            }
        }
        sheet.setColumnNames(columnNames);
        
        List<List<String>> valuesLists = Lists.newArrayList();
        
        TestingTask testingTask;
        TechnicalAnalySubmitRecord submitRecord;
        TechnicalAnalyRecord record;
        TestingTechnicalAnalyRecord saveRecord;
        
        NamedQueryer query =
            NamedQueryer.builder()
                .hql("FROM TestingTechnicalAnalyRecord ttar WHERE ttar.sheet.id = :sheetId")
                .names(Lists.newArrayList("sheetId"))
                .values(Lists.newArrayList(entity.getId()))
                .build();
        List<TestingTechnicalAnalyRecord> subRecordList = baseDaoSupport.find(query, TestingTechnicalAnalyRecord.class);
        
        List<TechnicalAnalySubmitRecord> records = Lists.newArrayList();
        if (Collections3.isNotEmpty(subRecordList))
        {
            for (TestingTechnicalAnalyRecord ttar : subRecordList)
            {
                
                List<String> columnValues = Lists.newArrayList();
                
                String combineCode = "";
                String sample = "";
                NamedQueryer queryV =
                    NamedQueryer.builder()
                        .hql("FROM TestingVerifyRecord tvr WHERE tvr.analyRecord.id = :analyRecordId")
                        .names(Lists.newArrayList("analyRecordId"))
                        .values(Lists.newArrayList(ttar.getId()))
                        .build();
                List<TestingVerifyRecord> verifyRecords = baseDaoSupport.find(queryV, TestingVerifyRecord.class);
                if (Collections3.isNotEmpty(verifyRecords))
                {
                    for (TestingVerifyRecord r : verifyRecords)
                    {
                        if (StringUtils.isNotEmpty(sample))
                        {
                            sample = sample + "," + r.getInputSampleFamilyRelation();
                        }
                        else
                        {
                            sample = null == r.getInputSampleFamilyRelation() ? "" : r.getInputSampleFamilyRelation();
                        }
                        if ("本人".equals(r.getInputSampleFamilyRelation()))//获取本人合并编号
                        {
                            combineCode = getCombineCodeByVerifyId(r.getId());
                        }
                    }
                    
                    columnValues.add(sample);
                }
                else
                {
                    columnValues.add("");
                }
                record = new TechnicalAnalyRecord();
                BeanUtils.copyProperties(ttar, record);
                record.setRefSample(sample);
                record.setCombineCode(combineCode);
                TechnicalAnalySubmitRecord subRecord = new TechnicalAnalySubmitRecord();
                subRecord.setRecord(record);
                
                JSONObject temp = JSONObject.parseObject(ttar.getOtherFields());
                if (null != temp)
                {
                    for (String name : columnNames)
                    {
                        columnValues.add(null == temp.getString(name) ? "" : temp.getString(name));
                    }
                }
                valuesLists.add(columnValues);
                
                records.add(subRecord);
            }
        }
        
        sheet.setColumnValues(valuesLists);
        sheet.setRecords(records);
        return sheet;
    }
    
    private String getCombineCodeByVerifyId(String id)
    {
        
        List<SangerVerifyRecord> sangerverifyrecords =
            baseDaoSupport.find(SangerVerifyRecord.class, "from SangerVerifyRecord s where s.verifyRecord.id='" + id + "'");
        if (Collections3.isNotEmpty(sangerverifyrecords))
        {
            
            return Collections3.getFirst(sangerverifyrecords).getCode();
        }
        List<MlpaVerifyRecord> mlpaverifyrecords = baseDaoSupport.find(MlpaVerifyRecord.class, "from MlpaVerifyRecord s where s.verifyRecord.id='" + id + "'");
        if (Collections3.isNotEmpty(mlpaverifyrecords))
        {
            
            return Collections3.getFirst(mlpaverifyrecords).getCombineCode();
        }
        List<QpcrVerifyRecord> qpcrverifyrecords = baseDaoSupport.find(QpcrVerifyRecord.class, "from QpcrVerifyRecord s where s.verifyRecord.id='" + id + "'");
        if (Collections3.isNotEmpty(qpcrverifyrecords))
        {
            
            return Collections3.getFirst(qpcrverifyrecords).getCombineCode();
        }
        return null;
        
    }
    
    @Override
    public BiologyAnalySheet getBiologyAnalySheet(String id)
    {
        TestingSheet testingSheet = baseDaoSupport.get(TestingSheet.class, id);
        BiologyAnalySheet sheet = new BiologyAnalySheet();
        BeanUtils.copyProperties(testingSheet, sheet);
        sheet.setSampleCode(testingSheet.getCode());
        sheet.setCreateTime(testingSheet.getCreateTime());
        return sheet;
    }
    
}
