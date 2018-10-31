package com.todaysoft.lims.technical.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.todaysoft.lims.technical.adapter.bpm.TestingAdapter;
import com.todaysoft.lims.technical.model.*;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aliyun.openservices.ons.api.Producer;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.todaysoft.lims.technical.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.technical.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.technical.adapter.smm.SMMAdapter;
import com.todaysoft.lims.technical.config.Configs;
import com.todaysoft.lims.technical.facade.DataAnalysisFacade;
import com.todaysoft.lims.technical.model.request.CheckPrimerForDesignRequest;
import com.todaysoft.lims.technical.model.request.ClaimTemplateColumnModel;
import com.todaysoft.lims.technical.model.request.ClaimTemplateRequest;
import com.todaysoft.lims.technical.model.request.DoctorIfColletionRequest;
import com.todaysoft.lims.technical.model.request.FilterItemsRequest;
import com.todaysoft.lims.technical.model.request.MutationCollectionRequest;
import com.todaysoft.lims.technical.model.request.MutationDataRequest;
import com.todaysoft.lims.technical.model.request.MutationFilterFormRequest;
import com.todaysoft.lims.technical.model.request.ReportExportMutationInfoRequest;
import com.todaysoft.lims.technical.model.request.SubmitTechnicalTaskRequest;
import com.todaysoft.lims.technical.model.request.TechnicalAddVerifyRequest;
import com.todaysoft.lims.technical.model.request.TechnicalAnalySubmitRequest;
import com.todaysoft.lims.technical.model.request.TechnicalCollection;
import com.todaysoft.lims.technical.model.request.TechnicalCollectionRequest;
import com.todaysoft.lims.technical.model.response.ClaimTemplateColumnForDoctor;
import com.todaysoft.lims.technical.model.response.ClaimTemplateModel;
import com.todaysoft.lims.technical.model.response.DataTemplateModel;
import com.todaysoft.lims.technical.model.response.Disease;
import com.todaysoft.lims.technical.model.response.Gene;
import com.todaysoft.lims.technical.model.response.Phenotype;
import com.todaysoft.lims.technical.model.searcher.MutationDataSearcher;
import com.todaysoft.lims.technical.mybatis.BiologyDivisionFastqDataMapper;
import com.todaysoft.lims.technical.mybatis.BiologyFamilyAnalysisMapper;
import com.todaysoft.lims.technical.mybatis.BiologyFamilyAnalysisSampleRelationMapper;
import com.todaysoft.lims.technical.mybatis.ClaimTemplateColumnMapper;
import com.todaysoft.lims.technical.mybatis.ClaimTemplateMapper;
import com.todaysoft.lims.technical.mybatis.CnvAnalysisPicMapper;
import com.todaysoft.lims.technical.mybatis.ContractMapper;
import com.todaysoft.lims.technical.mybatis.CustomerMapper;
import com.todaysoft.lims.technical.mybatis.FilterItemsMapper;
import com.todaysoft.lims.technical.mybatis.MarketingCenterMapper;
import com.todaysoft.lims.technical.mybatis.MlpaVerifyRecordMapper;
import com.todaysoft.lims.technical.mybatis.OrderMapper;
import com.todaysoft.lims.technical.mybatis.OrderSampleDetailMapper;
import com.todaysoft.lims.technical.mybatis.ProductMapper;
import com.todaysoft.lims.technical.mybatis.ProductPrincipalMapper;
import com.todaysoft.lims.technical.mybatis.QpcrVerifyRecordMapper;
import com.todaysoft.lims.technical.mybatis.ReceivedSampleMapper;
import com.todaysoft.lims.technical.mybatis.SangerVerifyRecordMapper;
import com.todaysoft.lims.technical.mybatis.SchedulePlanTaskMapper;
import com.todaysoft.lims.technical.mybatis.SequenceMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalyRecordMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalyVerifyMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalysisAddVerifyMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalysisTaskMapper;
import com.todaysoft.lims.technical.mybatis.TestingMethodMapper;
import com.todaysoft.lims.technical.mybatis.TestingReportSampleMapper;
import com.todaysoft.lims.technical.mybatis.TestingSampleMapper;
import com.todaysoft.lims.technical.mybatis.TestingScheduleActiveMapper;
import com.todaysoft.lims.technical.mybatis.TestingScheduleHistoryMapper;
import com.todaysoft.lims.technical.mybatis.TestingScheduleMapper;
import com.todaysoft.lims.technical.mybatis.TestingTaskMapper;
import com.todaysoft.lims.technical.mybatis.TestingTaskRunVariableMapper;
import com.todaysoft.lims.technical.mybatis.UserMapper;
import com.todaysoft.lims.technical.mybatis.entity.ClaimTemplate;
import com.todaysoft.lims.technical.mybatis.entity.ClaimTemplateColumn;
import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisPic;
import com.todaysoft.lims.technical.mybatis.entity.DataTemplateColumn;
import com.todaysoft.lims.technical.mybatis.entity.FilterItems;
import com.todaysoft.lims.technical.mybatis.entity.MlpaVerifyRecord;
import com.todaysoft.lims.technical.mybatis.entity.Order;
import com.todaysoft.lims.technical.mybatis.entity.OrderSampleDetail;
import com.todaysoft.lims.technical.mybatis.entity.Primer;
import com.todaysoft.lims.technical.mybatis.entity.Product;
import com.todaysoft.lims.technical.mybatis.entity.QpcrVerifyRecord;
import com.todaysoft.lims.technical.mybatis.entity.ReceivedSample;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportDiseaseInfo;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportEvidenceInfo;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportMutationInfo;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportMutationInfoExplain;
import com.todaysoft.lims.technical.mybatis.entity.SangerVerifyRecord;
import com.todaysoft.lims.technical.mybatis.entity.SchedulePlanTask;
import com.todaysoft.lims.technical.mybatis.entity.Sequence;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyRecord;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyRecordWithBLOBs;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyVerify;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.technical.mybatis.entity.TestingMethod;
import com.todaysoft.lims.technical.mybatis.entity.TestingReportSample;
import com.todaysoft.lims.technical.mybatis.entity.TestingSample;
import com.todaysoft.lims.technical.mybatis.entity.TestingSchedule;
import com.todaysoft.lims.technical.mybatis.entity.TestingScheduleActive;
import com.todaysoft.lims.technical.mybatis.entity.TestingScheduleHistory;
import com.todaysoft.lims.technical.mybatis.entity.TestingTask;
import com.todaysoft.lims.technical.mybatis.entity.TestingTaskRunVariable;
import com.todaysoft.lims.technical.service.IClaimTemplateService;
import com.todaysoft.lims.technical.service.IDataTemplateService;
import com.todaysoft.lims.technical.service.IFilterItemsService;
import com.todaysoft.lims.technical.service.IMessageSendService;
import com.todaysoft.lims.technical.service.ITestingScheduleService;
import com.todaysoft.lims.technical.utils.Collections3;
import com.todaysoft.lims.technical.utils.IdGen;
import com.todaysoft.lims.technical.utils.JsonUtils;
import com.todaysoft.lims.technical.utils.Pager;
import com.todaysoft.lims.technical.utils.Pagination;
import com.todaysoft.lims.technical.utils.StringUtils;

@Service
public class ClaimTemplateService implements IClaimTemplateService
{
    @Autowired
    private ClaimTemplateMapper claimTemplatemapper;
    
    @Autowired
    private ClaimTemplateColumnMapper columnMapper;
    
    @Autowired
    private BSMAdapter bsmAdapter;
    
    @Autowired
    private FilterItemsMapper filterItemsMapper;
    
    @Autowired
    private IDataTemplateService dataTemplateService;
    
    @Autowired
    private IFilterItemsService filterItemsService;
    
    @Autowired
    private MongoTemplate template;
    
    @Autowired
    private TechnicalAnalysisTaskMapper technicalAnalysisTaskMapper;
    
    @Autowired
    private BiologyDivisionFastqDataMapper biologyDivisionFastqDataMapper;
    
    @Autowired
    private TechnicalAnalyRecordMapper technicalAnalyRecordMapper;
    
    @Autowired
    private TechnicalAnalysisAddVerifyMapper addVerifyMapper;
    
    @Autowired
    private BiologyFamilyAnalysisMapper biologyFamilyAnalysisMapper;
    
    @Autowired
    private BiologyFamilyAnalysisSampleRelationMapper biologyFamilyAnalysisSampleRelationMapper;
    
    private static final Logger logger = LoggerFactory.getLogger(ClaimTemplateService.class);
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private OrderSampleDetailMapper orderSampleDetailMapper;
    
    public final static String MUTATION_COLLECTION = "mutation";
    
    public final static String CNV_PIC = "cnvPic";
    
    public final static String CNV_RESULT = "cnvResult";

    @Autowired
    private TestingAdapter testingAdapter;
    
    @Autowired
    private BCMAdapter bcmAdapter;
    
    private Map<String, List<DataTemplateColumn>> dataTemplateColumnMap = new HashMap<String, List<DataTemplateColumn>>();
    
    @Autowired
    private TestingSampleMapper testingSampleMapper;
    
    @Autowired
    private TechnicalAnalyVerifyMapper technicalAnalyVerifyMapper;
    
    @Autowired
    private TestingScheduleActiveMapper testingScheduleActiveMapper;
    
    @Autowired
    private TestingScheduleMapper testingScheduleMapper;
    
    @Autowired
    private TestingReportSampleMapper testingReportSampleMapper;
    
    @Autowired
    private SequenceMapper sequenceMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private TestingMethodMapper testingMethodMapper;
    
    @Autowired
    private TestingTaskMapper testingTaskMapper;
    
    @Autowired
    private ReceivedSampleMapper receivedSampleMapper;
    
    @Autowired
    private SangerVerifyRecordMapper sangerVerifyRecordMapper;
    
    @Autowired
    private TestingTaskRunVariableMapper testingTaskRunVariableMapper;
    
    @Autowired
    private TestingScheduleHistoryMapper testingScheduleHistoryMapper;
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private QpcrVerifyRecordMapper qpcrVerifyRecordMapper;
    
    @Autowired
    private MlpaVerifyRecordMapper mlpaVerifyRecordMapper;
    
    @Autowired
    private CnvAnalysisPicMapper cnvAnalysisPicMapper;
    
    @Autowired
    private DataAnalysisFacade dataAnalysisFacade;
    
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;
    
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private ContractMapper contractMapper;
    
    @Autowired
    private CustomerMapper customerMapper;
    
    @Autowired
    private MarketingCenterMapper marketingCenterMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ProductPrincipalMapper productPrincipalMapper;
    
    @Autowired
    private TestingSampleMapper testingSampleMappr;
    
    @Autowired
    private SchedulePlanTaskMapper schedulePlanTaskMapper;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private IMessageSendService messageSendService;
    
    @Override
    public Pagination<ClaimTemplate> pager(ClaimTemplateRequest request)
    {
        Pagination<ClaimTemplate> result = new Pagination<ClaimTemplate>();
        result.setTotalCount(count(request));
        result.setRecords(search(request));
        result.setPageNo(request.getPageNo());
        result.setPageSize(request.getPageSize());
        return result;
    }
    
    private int count(ClaimTemplateRequest request)
    {
        return claimTemplatemapper.count(request);
    }
    
    private List<ClaimTemplate> search(ClaimTemplateRequest request)
    {
        return claimTemplatemapper.search(request);
    }
    
    @Override
    public List<FilterItems> getItemList(FilterItems record)
    {
        List<FilterItems> list = new ArrayList<>();
        if (record.getSemantic() == null || record.getSemantic() == "")
        {
            return list;
        }
        return filterItemsMapper.select(record);
    }
    
    @Override
    public boolean validate(ClaimTemplateRequest request)
    {
        ClaimTemplate dt = claimTemplatemapper.selectByName(request.getName());
        if (null != dt)
        {
            if (StringUtils.isEmpty(request.getId()))
            {
                return false;
            }
            else
            {
                if (!dt.getId().equals(request.getId()))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    @Transactional
    public void create(ClaimTemplateRequest request)
    {
        ClaimTemplate record = new ClaimTemplate();
        BeanUtils.copyProperties(request, record);
        record.setId(IdGen.uuid());
        record.setCreateTime(new Date());
        record.setPriFlag(false);
        claimTemplatemapper.insert(record);
        List<ClaimTemplateColumnModel> modelList = request.getColumnList();
        if (null != modelList && modelList.size() > 0)
        {
            for (ClaimTemplateColumnModel model : modelList)
            {
                ClaimTemplateColumn column = new ClaimTemplateColumn();
                BeanUtils.copyProperties(model, column);
                column.setId(IdGen.uuid());
                column.setTemplateId(record.getId());
                column.setDataColumnId(model.getDataTemplateColumnId());
                if (null != model.getRadioValue() && "" != model.getRadioValue())
                {
                    column.setDefaultValue(model.getRadioValue());
                }
                if (null != model.getRangeValue1() && "" != model.getRangeValue1() && null != model.getRangeValue2() && "" != model.getRangeValue2())
                {
                    column.setDefaultValue(model.getRangeValue1() + "-" + model.getRangeValue2());
                }
                else if (null != model.getRangeValue1() && "" != model.getRangeValue1())
                {
                    column.setDefaultValue(model.getRangeValue1() + "-");
                }
                else if (null != model.getRangeValue2() && "" != model.getRangeValue2())
                {
                    column.setDefaultValue("-" + model.getRangeValue2());
                }
                columnMapper.insert(column);
            }
        }
        
    }
    
    @Override
    public ClaimTemplate get(String id)
    {
        return claimTemplatemapper.selectByPrimaryKey(id);
    }
    
    @Override
    public List<ClaimTemplateColumn> getColumn(String templateId)
    {
        return columnMapper.selectByTemplateId(templateId);
    }
    
    /*
     * 
     * 
     * @Override public List<ClaimTemplate> selectForPriFlag() { return
     * mapper.selectForPriFlag(); }
     */
    
    @Override
    @Transactional
    public void saveItem(FilterItemsRequest request)
    {
        FilterItems record = new FilterItems();
        BeanUtils.copyProperties(request, record);
        record.setId(IdGen.uuid());
        record.setDelFlag(false);
        filterItemsMapper.insert(record);
    }
    
    @Override
    public ClaimTemplateModel getByIdForView(String id)
    {
        ClaimTemplateModel model = new ClaimTemplateModel();
        List<ClaimTemplateColumnModel> colModellist = new ArrayList<ClaimTemplateColumnModel>();
        ClaimTemplate ct = get(id);
        BeanUtils.copyProperties(ct, model);
        DataTemplateModel dt = dataTemplateService.getById(ct.getTemplateId());
        if (null != dt)
        {
            model.setTemplateId(dt.getName());
        }
        else
        {
            model.setTemplateId("");
        }
        List<ClaimTemplateColumn> list = getColumn(id);
        if (null != list && list.size() > 0)
        {
            for (ClaimTemplateColumn col : list)
            {
                ClaimTemplateColumnModel colModel = new ClaimTemplateColumnModel();
                DataTemplateColumn dataCol = dataTemplateService.getColumnById(col.getDataColumnId());
                if (null != dataCol)
                {
                    colModel.setDataColumnId(dataCol.getColumnName());
                    if ("1".equals(dataCol.getType()) || "2".equals(dataCol.getType()))
                    {
                        if (StringUtils.isNotEmpty(col.getFilterName()))
                        {
                            String s = "";
                            String[] strArr = col.getFilterName().split(",");
                            for (int i = 0; i < strArr.length; i++)
                            {
                                FilterItems fi = filterItemsService.get(strArr[i]);
                                if (fi == null)
                                {
                                    System.err.println("未找到对应filter item ：" + strArr[i]);
                                    continue;
                                }
                                if (i == 0)
                                {
                                    s = fi.getName();
                                }
                                else
                                {
                                    s += "," + fi.getName();
                                }
                            }
                            colModel.setFilterName(s);
                        }
                    }
                    else
                    {
                        colModel.setFilterName(col.getFilterName());
                    }
                    if ("1".equals(dataCol.getType()))
                    {
                        if (StringUtils.isNotEmpty(col.getDefaultValue()))
                        {
                            String s = "";
                            String[] strArr = col.getDefaultValue().split(",");
                            for (int i = 0; i < strArr.length; i++)
                            {
                                FilterItems fi = filterItemsService.get(strArr[i]);
                                if (i == 0)
                                {
                                    s = fi.getName();
                                }
                                else
                                {
                                    s += "," + fi.getName();
                                }
                            }
                            colModel.setDefaultValue(s);
                        }
                    }
                    else
                    {
                        colModel.setDefaultValue(col.getDefaultValue());
                    }
                    colModel.setColumnIndex(col.getColumnIndex());
                }
                colModellist.add(colModel);
            }
        }
        model.setColumnList(colModellist);
        return model;
    }
    
    @Override
    @Transactional
    public void delete(ClaimTemplateRequest request)
    {
        ClaimTemplate record = new ClaimTemplate();
        record.setDelFlag(true);
        record.setId(request.getId());
        claimTemplatemapper.update(record);
    }
    
    @Override
    @Transactional
    public void setPriFlag(ClaimTemplateRequest request)
    {
        ClaimTemplate record = new ClaimTemplate();
        claimTemplatemapper.updatePriFlagFalse();
        record.setId(request.getId());
        record.setPriFlag(true);
        claimTemplatemapper.update(record);
    }
    
    @Override
    public ClaimTemplateModel getById(String id)
    {
        ClaimTemplateModel model = new ClaimTemplateModel();
        ClaimTemplate ct = get(id);
        List<ClaimTemplateColumn> list = getColumn(id);
        List<ClaimTemplateColumnModel> colModellist = new ArrayList<ClaimTemplateColumnModel>();
        BeanUtils.copyProperties(ct, model);
        if (null != list && list.size() > 0)
        {
            for (ClaimTemplateColumn col : list)
            {
                ClaimTemplateColumnModel colModel = new ClaimTemplateColumnModel();
                BeanUtils.copyProperties(col, colModel, "defaultValue");
                DataTemplateColumn dataCol = dataTemplateService.getColumnById(col.getDataColumnId());
                
                if (null != dataCol)
                {
                    colModel.setType(dataCol.getType());
                    colModel.setSemantic(dataCol.getSemantic());
                    if ("1".equals(dataCol.getType()))
                    {
                        // colModel.setDefaultValue(col.getDefaultValue());
                        if (StringUtils.isNotEmpty(col.getDefaultValue()))
                        {
                            List<FilterItems> fiList = new ArrayList<FilterItems>();
                            String[] strArr = col.getDefaultValue().split(",");
                            for (String s : strArr)
                            {
                                FilterItems fi = filterItemsService.get(s);
                                fiList.add(fi);
                            }
                            colModel.setDefaultValue(JsonUtils.toJson(fiList));
                        }
                    }
                    if ("2".equals(dataCol.getType()))
                    {
                        colModel.setRadioValue(col.getDefaultValue());
                        FilterItems request = new FilterItems();
                        request.setSemantic(dataCol.getSemantic());
                        colModel.setFilterItemsList(getItemList(request));
                    }
                    if ("3".equals(dataCol.getType()))
                    {
                        if (StringUtils.isNotEmpty(col.getDefaultValue()))
                        {
                            String[] strArr = col.getDefaultValue().split("-");
                            colModel.setRangeValue1(strArr[0]);
                            if (strArr.length > 1)
                            {
                                colModel.setRangeValue2(strArr[1]);
                            }
                            
                        }
                    }
                }
                if (StringUtils.isNotEmpty(col.getFilterName()))
                {
                    List<FilterItems> fiList = new ArrayList<FilterItems>();
                    String[] strArr = col.getFilterName().split(",");
                    for (String s : strArr)
                    {
                        FilterItems fi = filterItemsService.get(s);
                        fiList.add(fi);
                    }
                    colModel.setFilterName(JsonUtils.toJson(fiList));
                }
                
                colModellist.add(colModel);
            }
        }
        model.setColumnList(colModellist);
        return model;
    }
    
    @Override
    @Transactional
    public void update(ClaimTemplateRequest request)
    {
        ClaimTemplate record = new ClaimTemplate();
        BeanUtils.copyProperties(request, record);
        claimTemplatemapper.update(record);
        columnMapper.deleteByTemplateId(request.getId());
        List<ClaimTemplateColumnModel> modelList = request.getColumnList();
        if (null != modelList && modelList.size() > 0)
        {
            for (ClaimTemplateColumnModel model : modelList)
            {
                ClaimTemplateColumn column = new ClaimTemplateColumn();
                BeanUtils.copyProperties(model, column);
                column.setId(IdGen.uuid());
                column.setTemplateId(record.getId());
                column.setDataColumnId(model.getDataTemplateColumnId());
                if (null != model.getRadioValue() && "" != model.getRadioValue())
                {
                    column.setDefaultValue(model.getRadioValue());
                }
                if (null != model.getRangeValue1() && "" != model.getRangeValue1() && null != model.getRangeValue2() && "" != model.getRangeValue2())
                {
                    column.setDefaultValue(model.getRangeValue1() + "-" + model.getRangeValue2());
                }
                else if (null != model.getRangeValue1() && "" != model.getRangeValue1())
                {
                    column.setDefaultValue(model.getRangeValue1() + "-");
                }
                else if (null != model.getRangeValue2() && "" != model.getRangeValue2())
                {
                    column.setDefaultValue("-" + model.getRangeValue2());
                }
                columnMapper.insert(column);
            }
        }
    }
    
    @Override
    public List<ClaimTemplate> claimTemplateList()
    {
        List<ClaimTemplate> list = claimTemplatemapper.claimTemplateList();
        return list;
    }
    
    @Override
    public List<String> queryRecentFilterList(MutationFilterFormRequest request)
    {
        DBCollection collection = template.getCollection("mutation-filter");
        List<String> list = new ArrayList<String>();
        BasicDBObject object = new BasicDBObject("doctorId", request.getDoctorId());
        BasicDBObject object2 = new BasicDBObject("createTime", -1);
        Iterator<DBObject> iterator = collection.find(object).limit(5).sort(object2).iterator();
        // Iterator<DBObject> iterator = collection.find().iterator();
        while (iterator.hasNext())
        {
            DBObject obj = iterator.next();
            System.out.println(obj);
            // String s = JsonUtils.toJson(iterator.next());
            list.add(obj.toString());
        }
        return list;
    }
    
    @Override
    public List<ClaimTemplateColumnForDoctor> getClaimTempleColumnForDoctor()
    {
        List<ClaimTemplateColumnForDoctor> modelList = new ArrayList<ClaimTemplateColumnForDoctor>();
        List<ClaimTemplate> claimList = claimTemplatemapper.selectForPriFlag();
        if (null != claimList && claimList.size() > 0)
        {
            ClaimTemplate ct = claimList.get(0);
            List<ClaimTemplateColumn> columnList = columnMapper.selectByTemplateId(ct.getId());
            if (null != columnList && columnList.size() > 0)
            {
                for (ClaimTemplateColumn col : columnList)
                {
                    ClaimTemplateColumnForDoctor model = new ClaimTemplateColumnForDoctor();
                    DataTemplateColumn dataCol = dataTemplateService.getColumnById(col.getDataColumnId());
                    if (null != dataCol)
                    {
                        model.setType(dataCol.getType());
                        model.setSemantic(dataCol.getSemantic());
                        model.setColumnName(dataCol.getColumnName());
                        if ("1".equals(dataCol.getType()))
                        {
                            if (StringUtils.isNotEmpty(col.getDefaultValue()))
                            {
                                List<FilterItems> fiList = new ArrayList<FilterItems>();
                                String[] strArr = col.getDefaultValue().split(",");
                                for (String s : strArr)
                                {
                                    FilterItems fi = filterItemsService.get(s);
                                    if (null != fi)
                                    {
                                        fiList.add(fi);
                                    }
                                }
                                model.setChooseDefaultValueList(fiList);
                            }
                        }
                        if ("2".equals(dataCol.getType()))
                        {
                            model.setRadioValue(col.getDefaultValue());
                        }
                        if ("3".equals(dataCol.getType()))
                        {
                            if (StringUtils.isNotEmpty(col.getDefaultValue()))
                            {
                                String[] strArr = col.getDefaultValue().split("-");
                                model.setRangeValue1(strArr[0]);
                                model.setRangeValue2(strArr[1]);
                            }
                        }
                        if (StringUtils.isNotEmpty(col.getFilterName()))
                        {
                            List<FilterItems> fiList = new ArrayList<FilterItems>();
                            String[] strArr = col.getFilterName().split(",");
                            for (String s : strArr)
                            {
                                FilterItems fi = filterItemsService.get(s);
                                if (null != fi)
                                {
                                    fiList.add(fi);
                                }
                            }
                            model.setChooseFilterItemsList(fiList);
                        }
                    }
                    modelList.add(model);
                }
            }
        }
        return modelList;
    }
    
    @Override
    public void saveMutationFilter(MutationFilterFormRequest request)
    {
        DBCollection collection = template.getCollection("mutation-filter");
        BasicDBObject object = JsonUtils.fromJson(request.getJsonStr(), BasicDBObject.class);
        object.put("doctorName", request.getDoctorName());
        object.put("doctorId", request.getDoctorId());
        object.put("createTime", new Date());
        collection.save(object);
        
    }
    
    @Override
    public DataColAndMutationDataModel queryMutationDataByFilter(MutationDataRequest request)
    {
        
        request.setAnalysisSampleId(request.getAnalysisFamilyId());
        DataColAndMutationDataModel model = new DataColAndMutationDataModel();
        MutationFilterToWarpDataModel tempModel = warpQueryFilter(request, model);
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 50 : request.getPageSize();
        MutationDataSearcher searcher = new MutationDataSearcher();
        searcher.setSearchCond(tempModel.getSearchCond());
        PagerQueryer<DBObject> queryer = new PagerQueryer<DBObject>(this);
        Pager<DBObject> pager = queryer.query(searcher, pageNo, pageSize);
        warpMutationData(request, model, tempModel, pager.getRecords(), pager.getTotalCount());
        model.setPageNo(pager.getPageNo());
        model.setPageSize(pager.getPageSize());
        model.setTotalCount(pager.getTotalCount());
        return model;
    }
    
    private void warpMutationData(MutationDataRequest request, DataColAndMutationDataModel model, MutationFilterToWarpDataModel tempModel, List<DBObject> dbList, int totalCount)
    {
        List<Map<String, String>> dataValueList = new ArrayList<>();
        Map<String, List<String>> secondColMap = new LinkedHashMap<>();
        int matSumNumber = totalCount;// 突变数目
        int geneNumber = 0;// 基因数目
        List<String> geneNumberList = new ArrayList<>();// 基因不重复数量
        if (null != dbList && dbList.size() > 0)
        {
            for (DBObject obj2 : dbList)
            {
                if (null != model.getColList() && model.getColList().size() > 0)
                {
                    Map<String, String> map = new LinkedHashMap<>();
                    for (String col : model.getColList())
                    {
                        List<DataTemplateColumn> subList = null;
                        if (null == dataTemplateColumnMap.get(col + tempModel.getTemplateId()))
                        {
                            subList = dataTemplateService.getColumnByGroupName(col, tempModel.getTemplateId());
                            dataTemplateColumnMap.put(col + tempModel.getTemplateId(), subList);
                        }
                        else
                        {
                            subList = dataTemplateColumnMap.get(col + tempModel.getTemplateId());
                        }
                        
                        if (null != subList && subList.size() > 0)
                        {
                            if (subList.size() == 1)
                            {
                                if ("Gene_Symbol".equals(subList.get(0).getSemantic()))
                                {
                                    String s = null == obj2.get(subList.get(0).getSemantic()) ? "" : obj2.get(subList.get(0).getSemantic()).toString();
                                    if (!geneNumberList.contains(s))
                                    {
                                        geneNumberList.add(s);
                                    }
                                    if (null != tempModel.getGeneSymbolList())
                                    {
                                        for (String str : tempModel.getGeneSymbolList())
                                        {
                                            s = s.replace(str, "<span style='color: red;'>" + str + "</span>");
                                        }
                                    }
                                    map.put(subList.get(0).getGroupName(), s);
                                }
                                else if ("Inhert".equals(subList.get(0).getSemantic()))
                                {
                                    DBObject DiseaseTotal = (DBObject)obj2.get("DiseaseTotal");
                                    if (null != DiseaseTotal)
                                    {
                                        String inhert =
                                            null == DiseaseTotal.get(subList.get(0).getSemantic()) ? "" : DiseaseTotal.get(subList.get(0).getSemantic())
                                                .toString();
                                        if (StringUtils.isNotEmpty(inhert))
                                        {
                                            String[] inhertArray = inhert.split("\\,");
                                            
                                            for (int i = 0; i <= inhertArray.length - 1; i++)
                                            {
                                                inhertArray[i] = i + 1 + "." + inhertArray[i];
                                                
                                            }
                                            String inhertShow = Joiner.on(" | ").join(inhertArray);
                                            map.put(subList.get(0).getColumnName(), inhertShow);
                                        }
                                        
                                    }
                                    
                                }
                                else if ("DiseasePhenotype".equals(subList.get(0).getSemantic()))
                                {
                                    DBObject DiseaseTotal = (DBObject)obj2.get("DiseaseTotal");
                                    if (null != DiseaseTotal)
                                    {
                                        String s =
                                            null == DiseaseTotal.get(subList.get(0).getSemantic()) ? "" : DiseaseTotal.get(subList.get(0).getSemantic())
                                                .toString();
                                        if (StringUtils.isNotEmpty(s))
                                        {
                                            String[] inhertArray = s.split("\\,");
                                            
                                            for (int i = 0; i <= inhertArray.length - 1; i++)
                                            {
                                                inhertArray[i] = i + 1 + "." + inhertArray[i];
                                                
                                            }
                                            String inhertShow = Joiner.on(" | ").join(inhertArray);
                                            map.put(subList.get(0).getColumnName(), inhertShow);
                                        }
                                        
                                    }
                                    
                                }
                                else if ("DiseaseInformation".equals(subList.get(0).getSemantic()))
                                {
                                    DBObject DiseaseTotal = (DBObject)obj2.get("DiseaseTotal");
                                    if (null != DiseaseTotal)
                                    {
                                        String s =
                                            null == DiseaseTotal.get(subList.get(0).getSemantic()) ? "" : DiseaseTotal.get(subList.get(0).getSemantic())
                                                .toString();
                                        if (StringUtils.isNotEmpty(s))
                                        {
                                            String[] inhertArray = s.split("\\,");
                                            
                                            for (int i = 0; i <= inhertArray.length - 1; i++)
                                            {
                                                inhertArray[i] = i + 1 + "." + inhertArray[i];
                                                
                                            }
                                            String inhertShow = Joiner.on(" | ").join(inhertArray);
                                            map.put(subList.get(0).getColumnName(), inhertShow);
                                        }
                                        
                                    }
                                    
                                }
                                else if ("遗传来源".equals(subList.get(0).getSemantic()))
                                {
                                    if (null != obj2.get(subList.get(0).getSemantic()))
                                    {
                                        Map m = (Map)obj2.get(subList.get(0).getSemantic());
                                        map.put(subList.get(0).getGroupName(), null == m ? "" : m.get("name").toString());
                                    }
                                    else
                                    {
                                        map.put(subList.get(0).getGroupName(), "");
                                    }
                                    
                                }
                                else if ("Mygeno_InterACMG".equals(subList.get(0).getSemantic()))
                                {
                                    String s = "-";
                                    if (null != obj2.get(subList.get(0).getSemantic()))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get(subList.get(0).getSemantic());
                                        
                                        if (null != m.get("name"))
                                        {
                                            s = m.get("name").toString();
                                        }
                                    }
                                    map.put(subList.get(0).getGroupName(), s);
                                }
                                else if ("之父基因型".equals(subList.get(0).getSemantic()) || "之母基因型".equals(subList.get(0).getSemantic())
                                    || "之母测序深度".equals(subList.get(0).getSemantic()) || "之父测序深度".equals(subList.get(0).getSemantic()))
                                {
                                    String s = "-";
                                    if (null != obj2.get("遗传来源"))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get("遗传来源");
                                        
                                        if (null != m.get(subList.get(0).getSemantic()))
                                        {
                                            s = m.get(subList.get(0).getSemantic()).toString();
                                        }
                                    }
                                    map.put(subList.get(0).getGroupName(), s);
                                }
                                else
                                {
                                    map.put(subList.get(0).getGroupName(),
                                        null == obj2.get(subList.get(0).getSemantic()) ? "" : obj2.get(subList.get(0).getSemantic()).toString());
                                    // 杂合携带默认加入收藏
                                    if ("标签".equals(subList.get(0).getSemantic()))
                                    {
                                        if (null != obj2.get(subList.get(0).getSemantic()))
                                        {
                                            if ("杂合携带".equals(obj2.get(subList.get(0).getSemantic()).toString()))
                                            {
                                                
                                            }
                                        }
                                    }
                                }
                            } // if (subList.size() == 1)
                            else
                            {
                                if ("关联表型".equals(col))
                                {
                                    DBObject DiseaseTotal = (DBObject)obj2.get("DiseaseTotal");
                                    if (null != DiseaseTotal)
                                    {
                                        String disease = null == DiseaseTotal.get("Disease") ? "" : DiseaseTotal.get("Disease").toString();
                                        String omim = null == DiseaseTotal.get("OMIM") ? "" : DiseaseTotal.get("OMIM").toString();
                                        if (StringUtils.isNotEmpty(disease))
                                        {
                                            String[] diseaseArray = disease.split("\\,");
                                            
                                            String[] omimArray = omim.split("\\,");
                                            List<String> list = new ArrayList<>();
                                            
                                            for (int i = 0; i <= diseaseArray.length - 1; i++)
                                            {
                                                if ((omimArray.length - 1) >= i)
                                                {
                                                    list.add(i + 1 + "." + diseaseArray[i] + "(" + omimArray[i] + ")");
                                                }
                                                
                                            }
                                            String Show = Joiner.on(" | ").join(list);
                                            map.put(col, Show);
                                        }
                                        
                                    }
                                    
                                }
                                if ("变异信息".equals(col))
                                {
                                    if (null != obj2.get("AA_change"))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get("AA_change");
                                        if (null != m)
                                        {
                                            map.put(col, null == m.get("name") ? "" : m.get("name").toString().split(",")[0]);
                                        }
                                    }
                                }
                                if ("疾病与基因关联度打分".equals(col))
                                {
                                    if (null != obj2.get("Total_Score"))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get("Total_Score");
                                        if (null != m)
                                        {
                                            if (null == m.get("Total_Score"))
                                            {
                                                map.put(col, "-");
                                            }
                                            else
                                            {
                                                map.put(col, null == m.get("Total_Score") ? "" : m.get("Total_Score").toString());
                                            }
                                            
                                        }
                                    }
                                }
                                if ("人群频率".equals(col))
                                {
                                    if (null != obj2.get("Highest-MAF"))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get("Highest-MAF");
                                        if (null != m)
                                        {
                                            map.put(col, null == m.get("name") ? "" : m.get("name").toString());
                                        }
                                    }
                                }
                                if ("蛋白功能预测".equals(col))
                                {
                                    if (null != obj2.get("REVEL_score"))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get("REVEL_score");
                                        if (null != m)
                                        {
                                            map.put(col, null == m.get("name") ? "" : m.get("name").toString());
                                        }
                                    }
                                }
                                if ("Evidence".equals(col))
                                {
                                    List<String> sl = new ArrayList<>();
                                    for (DataTemplateColumn dtc : subList)
                                    {
                                        if ("Tag".equals(dtc.getSemantic()))
                                        {
                                            if (null != obj2.get(dtc.getSemantic()))
                                            {
                                                Map<String, Object> m = (Map<String, Object>)obj2.get("Tag");
                                                if (null != m)
                                                {
                                                    map.put(dtc.getColumnName(), null == m.get("name") ? "" : m.get("name").toString());
                                                }
                                            }
                                        }
                                        else
                                        {
                                            map.put(dtc.getColumnName(), null == obj2.get(dtc.getSemantic()) ? "" : obj2.get(dtc.getSemantic()).toString());
                                        }
                                        sl.add(dtc.getColumnName());
                                    }
                                    secondColMap.put(col, sl);
                                }
                            }
                        }
                    }
                    
                    map.put("_id", obj2.get("_id").toString());
                    map.put("analysisSampleId", obj2.get("analysisSampleId").toString());
                    dataValueList.add(map);
                }
            }
        }
        else
        {
            if (null != model.getColList() && model.getColList().size() > 0)
            {
                for (String col : model.getColList())
                {
                    if ("Evidence".equals(col))
                    {
                        List<DataTemplateColumn> subList = dataTemplateService.getColumnByGroupName(col, tempModel.getTemplateId());
                        List<String> sl = new ArrayList<>();
                        for (DataTemplateColumn dtc : subList)
                        {
                            sl.add(dtc.getColumnName());
                        }
                        secondColMap.put(col, sl);
                    }
                }
            }
        }
        model.setDataValue(dataValueList);
        model.setSenondColName(secondColMap);
        if (null != geneNumberList)
        {
            geneNumber = geneNumberList.size();
        }
        model.setMatSumNumber(matSumNumber);
        model.setGeneNumber(geneNumber);
        
    }
    
    private MutationFilterToWarpDataModel warpQueryFilter(MutationDataRequest request, DataColAndMutationDataModel model)
    {
        Boolean isFamily = dataAnalysisFacade.ifFamilyAnalysis(request.getAnalysisSampleId());
        List<DataTemplateColumn> dataColList = new ArrayList<>();
        // List<Map<String, String>> dataValueList = new ArrayList<>();
        // Map<String, List<String>> secondColMap = new HashMap<>();
        String templateId = "";
        List<ClaimTemplate> claimList = this.selectForPriFlag();
        if (null != claimList && claimList.size() > 0)
        {
            ClaimTemplate ct = claimList.get(0);
            dataColList = dataTemplateService.getColumn(ct.getTemplateId());
            templateId = ct.getTemplateId();
        }
        List<String> groupNameList = new ArrayList<String>();
        List<String> removceGroup = Lists.newArrayList("HGMD", "之父测序深度", "之母测序深度", "之父基因型", "之母基因型");
        if (null != dataColList)
        {
            for (DataTemplateColumn col : dataColList)
            {
                if (!groupNameList.contains(col.getGroupName()))
                {
                    groupNameList.add(col.getGroupName());
                }
            }
            if (!isFamily)
            { //非家系
                groupNameList.removeAll(removceGroup);
            }
        }
        model.setColList(groupNameList);
        DBCollection collection = template.getCollection("mutation-filter");
        BasicDBObject object = new BasicDBObject("doctorId", request.getDoctorId());
        BasicDBObject object2 = new BasicDBObject("createTime", -1);
        Iterator<DBObject> iterator = collection.find(object).limit(1).sort(object2).iterator();
        DBObject obj = null;
        while (iterator.hasNext())
        {
            obj = iterator.next();
        }
        // 统一替换查询条件，外面会多套一层
        if (obj.containsField("Tag"))
        {
            obj.put("Tag.name", obj.get("Tag"));
            obj.removeField("Tag");
        }
        if (obj.containsField("SIFT_Predict"))
        {
            obj.put("REVEL_score.SIFT_Predict", obj.get("SIFT_Predict"));
            obj.removeField("SIFT_Predict");
        }
        if (obj.containsField("PolyPhen_2_Predict"))
        {
            obj.put("REVEL_score.PolyPhen_2_Predict", obj.get("PolyPhen_2_Predict"));
            obj.removeField("SIFT_Predict");
        }
        if (obj.containsField("MutationTaster_Predict"))
        {
            obj.put("REVEL_score.MutationTaster_Predict", obj.get("MutationTaster_Predict"));
            obj.removeField("MutationTaster_Predict");
        }
        if (obj.containsField("GERP++_Predict"))
        {
            obj.put("REVEL_score.GERP++_Predict", obj.get("GERP++_Predict"));
            obj.removeField("GERP++_Predict");
        }
        if (obj.containsField("MCAP_pred"))
        {
            obj.put("REVEL_score.MCAP_pred", obj.get("MCAP_pred"));
            obj.removeField("MCAP_pred");
        }
        if (obj.containsField("SPIDEXMin") || obj.containsField("SPIDEXMax"))
        {
            obj.put("REVEL_score.SPIDEX", "");
            
        }
        
        /*
         * int matSumNumber = 0;// 突变数目 int geneNumber = 0;// 基因数目 List<String>
         * geneNumberList = new ArrayList<>();// 基因不重复数量
         */
        BasicDBList condList = new BasicDBList();
        BasicDBObject cond2 = new BasicDBObject();
        String flag = "$and";
        List<String> diseaseNameList = new ArrayList<>();
        List<String> geneSymbolList = new ArrayList<>();
        List<String> phenotypeList = new ArrayList<>();
        if (null != obj)
        {
            // 高亮显示
            if (null != obj.get("diseases"))
            {
                String[] s = obj.get("diseases").toString().split(",");
                if (s.length > 0)
                {
                    for (int i = 0; i < s.length; i++)
                    {
                        Disease d = bcmAdapter.getDiseaseById(s[i]);
                        if (null != d)
                        {
                            diseaseNameList.add(d.getName());
                        }
                    }
                }
            }
            
            if (null != obj.get("genes"))
            {
                String[] s = obj.get("genes").toString().split(",");
                if (s.length > 0)
                {
                    for (int i = 0; i < s.length; i++)
                    {
                        Gene g = bcmAdapter.getGeneById(s[i]);
                        if (null != g)
                        {
                            geneSymbolList.add(g.getSymbol());
                        }
                    }
                }
            }
            
            if (null != obj.get("phenotypes"))
            {
                String[] s = obj.get("phenotypes").toString().split(",");
                if (s.length > 0)
                {
                    for (int i = 0; i < s.length; i++)
                    {
                        Phenotype p = bcmAdapter.getPhenotypeById(s[i]);
                        if (null != p)
                        {
                            phenotypeList.add(p.getName());
                        }
                    }
                }
            }
            model.setPhenotypeList(phenotypeList);
            
            // 筛选记录，防止最后重复筛选
            List<String> ignoreKeys = new ArrayList<>();
            List<String> ke = Arrays.asList("highest-MAF2Min", "highest-MAF2Max", "Tag.name");
            ignoreKeys.addAll(ke);
            
            for (String key : obj.keySet())
            {
                if (!"_id".equals(key) && !"doctorName".equals(key) && !"doctorId".equals(key) && !"createTime".equals(key) && !"SIFT_Predict".equals(key)
                    && !"PolyPhen_2_Predict".equals(key) && !"MutationTaster_Predict".equals(key) && !"GERP++_Predict".equals(key) && !"MCAP_pred".equals(key)
                    && !"SPIDEXMin".equals(key) && !"SPIDEXMax".equals(key) && !"MutInDatabase".equals(key) && !"diseases".equals(key) && !"genes".equals(key)
                    && !"phenotypes".equals(key))
                {
                    
                    System.out.println("key= " + key + " and value= " + obj.get(key));
                    if ("proteinFunction".equals(key))// 蛋白功能预测
                    {
                        if (null != obj.get(key))
                        {
                            if ("满足全部".equals(obj.get(key)))
                            {
                                joinAndOr(obj, "REVEL_score.SIFT_Predict", condList, "and");
                                joinAndOr(obj, "REVEL_score.PolyPhen_2_Predict", condList, "and");
                                joinAndOr(obj, "REVEL_score.MutationTaster_Predict", condList, "and");
                                joinAndOr(obj, "REVEL_score.GERP++_Predict", condList, "and");
                                joinAndOr(obj, "REVEL_score.MCAP_pred", condList, "and");
                                joinAndOr(obj, "REVEL_score.SPIDEX", condList, "and");
                                
                            }
                            else
                            {
                                BasicDBList orObject = new BasicDBList();
                                joinAndOr(obj, "REVEL_score.SIFT_Predict", orObject, "or");
                                joinAndOr(obj, "REVEL_score.PolyPhen_2_Predict", orObject, "or");
                                joinAndOr(obj, "REVEL_score.MutationTaster_Predict", orObject, "or");
                                joinAndOr(obj, "REVEL_score.GERP++_Predict", orObject, "or");
                                joinAndOr(obj, "REVEL_score.MCAP_pred", orObject, "or");
                                joinAndOr(obj, "REVEL_score.SPIDEX", orObject, "or");
                                if (orObject.size() != 0)
                                {
                                    BasicDBObject o = new BasicDBObject();
                                    o.put("$or", orObject);
                                    condList.add(o);
                                }
                            }
                        }
                        List<String> kes =
                            Arrays.asList("REVEL_score.SIFT_Predict",
                                "REVEL_score.PolyPhen_2_Predict",
                                "REVEL_score.MutationTaster_Predict",
                                "REVEL_score.GERP++_Predict",
                                "REVEL_score.MCAP_pred",
                                "REVEL_score.SPIDEX");
                        ignoreKeys.addAll(kes);
                    }
                    else if ("evidence".equals(key))
                    {
                        if ("交集".equals(obj.get(key)))
                        {
                            flag = "$and";
                            
                        }
                        else
                        {
                            flag = "$or";
                            
                        }
                        BasicDBList orObject = new BasicDBList();
                        evidenceJoinAndOr(obj, cond2);
                        
                        if (orObject.size() != 0)
                        {
                            BasicDBObject o = new BasicDBObject();
                            o.put("$or", orObject);
                            condList.add(o);
                        }
                        
                    }
                    else if ("遗传来源".equals(key))
                    {
                        if (obj.get(key) instanceof List)
                        {
                            BasicDBList subList = new BasicDBList();
                            List<String> sl = (List<String>)obj.get(key);
                            for (int i = 0; i < sl.size(); i++)
                            {
                                if ("空值".equals(obj.get(key)))
                                {
                                    
                                    BasicDBObject query = new BasicDBObject();
                                    query.put(key, new BasicDBObject("$exists", false));
                                    subList.add(query);
                                }
                                else
                                {
                                    BasicDBObject condObj = new BasicDBObject(key + ".name", sl.get(i));
                                    subList.add(condObj);
                                }
                                
                            }
                            BasicDBObject searchSub = new BasicDBObject();
                            searchSub.put("$or", subList);
                            condList.add(searchSub);
                        }
                        else
                        {
                            if ("空值".equals(obj.get(key)))
                            {
                                
                                BasicDBObject query = new BasicDBObject();
                                query.put(key, new BasicDBObject("$exists", false));
                                condList.add(query);
                            }
                            else
                            {
                                BasicDBObject condObj = new BasicDBObject(key + ".name", obj.get(key));
                                condList.add(condObj);
                            }
                            
                        }
                    }
                    else
                    {
                        
                        if (!ignoreKeys.contains(key))
                        {
                            if (obj.get(key) instanceof List)
                            {
                                BasicDBList subList = new BasicDBList();
                                List<String> sl = (List<String>)obj.get(key);
                                for (int i = 0; i < sl.size(); i++)
                                {
                                    BasicDBObject condObj = new BasicDBObject(key, sl.get(i));
                                    subList.add(condObj);
                                }
                                BasicDBObject searchSub = new BasicDBObject();
                                searchSub.put("$or", subList);
                                condList.add(searchSub);
                            }
                            else if ((key.indexOf("Min") > 0 || key.indexOf("Max") > 0) && key.indexOf("MAF2") < 0)
                            {
                                BasicDBList subList = new BasicDBList();
                                String keyStr = key.substring(0, key.length() - 3);
                                
                                BasicDBObject searchSub = new BasicDBObject();
                                
                                // 大于小于
                                BasicDBObject bigObj = new BasicDBObject();
                                BasicDBList bigList = new BasicDBList();
                                if (key.indexOf("Min") > 0)
                                {
                                    if ("highest-MAF".equals(keyStr))
                                    {
                                        keyStr = "name";
                                    }
                                    BasicDBObject minObj = new BasicDBObject("Highest-MAF." + keyStr, new BasicDBObject("$gte", obj.get(key)));
                                    bigList.add(minObj);
                                }
                                if (key.indexOf("Max") > 0)
                                {
                                    if ("highest-MAF".equals(keyStr))
                                    {
                                        keyStr = "name";
                                    }
                                    BasicDBObject mxnObj = new BasicDBObject("Highest-MAF." + keyStr, new BasicDBObject("$lte", obj.get(key)));
                                    bigList.add(mxnObj);
                                    
                                    if (null == obj.get(keyStr + "Min"))
                                    {
                                        if ("highest-MAF".equals(keyStr))
                                        {
                                            keyStr = "name";
                                        }
                                        // 是否存在
                                        BasicDBObject existSub = new BasicDBObject("Highest-MAF." + keyStr, "-");
                                        subList.add(existSub);
                                    }
                                }
                                bigObj.put("$and", bigList);
                                
                                subList.add(bigObj);
                                
                                BasicDBObject bbObj = new BasicDBObject();
                                bbObj.put("$or", subList);
                                condList.add(bbObj);
                            }
                            else
                            {
                                
                                if ("MutInNormal".equals(key))
                                {
                                    if (obj.get(key).equals("Y"))
                                    {
                                        
                                        BasicDBObject query = new BasicDBObject();
                                        query.put("MutInNormal", new BasicDBObject("$exists", true));
                                        condList.add(query);
                                    }
                                    else
                                    {
                                        BasicDBObject query = new BasicDBObject();
                                        query.put("MutInNormal", new BasicDBObject("$exists", false));
                                        condList.add(query);
                                    }
                                    
                                }
                                else
                                {
                                    if ("空值".equals(obj.get(key)))
                                    {
                                        BasicDBObject query = new BasicDBObject();
                                        query.put(key, new BasicDBObject("$exists", false));
                                        condList.add(query);
                                    }
                                    else
                                    {
                                        BasicDBObject condObj = new BasicDBObject(key, obj.get(key));
                                        condList.add(condObj);
                                    }
                                    
                                }
                                
                            }
                            
                        }
                        
                    }
                }
            }
        }
        BasicDBObject analysisSampleIdObj = null;
        analysisSampleIdObj = new BasicDBObject("analysisSampleId", request.getAnalysisSampleId());
        if (condList.size() != 0)
        {
            condList.add(analysisSampleIdObj);
        }
        
        BasicDBObject searchCond = new BasicDBObject();
        if (condList.size() != 0)
        {
            searchCond.put("$and", condList);
        }
        
        BasicDBList totalList = new BasicDBList();
        if (searchCond.size() != 0)
        {
            totalList.add(searchCond);
        }
        BasicDBObject qu = new BasicDBObject();
        if (cond2.size() != 0)
        {
            // 翻入sampleid
            BasicDBObject q = new BasicDBObject();
            q.put("analysisSampleId", request.getAnalysisSampleId());
            BasicDBList talList = new BasicDBList();
            talList.add(cond2);
            talList.add(q);
            qu.put("$and", talList);
            totalList.add(qu);
        }
        BasicDBObject totalSearchCond = new BasicDBObject();
        if (totalList.size() != 0)
        {
            totalSearchCond.put(flag, totalList);
        }
        if (totalSearchCond.size() == 0)
        {
            totalSearchCond.put("analysisSampleId", request.getAnalysisSampleId());
        }
        if (totalList.size() != 0)
        {
            totalSearchCond.put(flag, totalList);
        }
        
        MutationFilterToWarpDataModel tempModel = new MutationFilterToWarpDataModel();
        tempModel.setTemplateId(templateId);
        tempModel.setDiseaseNameList(diseaseNameList);
        tempModel.setGeneSymbolList(geneSymbolList);
        tempModel.setPhenotypeList(phenotypeList);
        tempModel.setSearchCond(totalSearchCond);
        return tempModel;
    }
    
    private void evidenceJoinAndOr(DBObject obj, BasicDBObject cond2)
    {
        BasicDBList hgmdAndhighList = new BasicDBList();
        if (obj.get("Tag.name") instanceof List)
        {
            BasicDBList subList = new BasicDBList();
            List<String> sl = (List<String>)obj.get("Tag.name");
            for (int i = 0; i < sl.size(); i++)
            {
                if ("空值".equals(sl.get(i)))
                {
                    BasicDBObject query = new BasicDBObject();
                    query.put("Tag.name", new BasicDBObject("$exists", false));
                    subList.add(query);
                }
                else
                {
                    BasicDBObject condObj = new BasicDBObject("Tag.name", sl.get(i));
                    subList.add(condObj);
                }
                
            }
            BasicDBObject searchSub = new BasicDBObject();
            searchSub.put("$or", subList);
            hgmdAndhighList.add(searchSub);
        }
        else if (null != obj.get("Tag.name"))
        {
            if ("空值".equals(obj.get("Tag.name").toString()))
            {
                BasicDBObject query = new BasicDBObject();
                query.put("Tag.name", new BasicDBObject("$exists", false));
                hgmdAndhighList.add(query);
            }
            else
            {
                BasicDBObject query = new BasicDBObject("Tag.name", obj.get("Tag.name").toString());
                hgmdAndhighList.add(query);
            }
        }
        BasicDBList subList = new BasicDBList();
        BasicDBObject searchSub = new BasicDBObject();
        BasicDBObject mxnObj;
        BasicDBObject minObj;
        if (null != obj.get("highest-MAF2Max") || null != obj.get("highest-MAF2Min"))
        {
            if (null != obj.get("highest-MAF2Max"))
            {
                
                mxnObj = new BasicDBObject("Highest-MAF.name", new BasicDBObject("$lte", obj.get("highest-MAF2Max")));
                subList.add(mxnObj);
                
            }
            if (null != obj.get("highest-MAF2Min"))
            {
                minObj = new BasicDBObject("Highest-MAF.name", new BasicDBObject("$gte", obj.get("highest-MAF2Min")));
                subList.add(minObj);
                searchSub.put("$and", subList);
            }
            if (null == obj.get("highest-MAF2Min"))
            {
                minObj = new BasicDBObject("Highest-MAF.name", "-");
                subList.add(minObj);
                searchSub.put("$or", subList);
            }
        }
        
        if (searchSub.size() != 0)
        {
            hgmdAndhighList.add(searchSub);
        }
        
        BasicDBObject searchSub2 = new BasicDBObject();
        if (hgmdAndhighList.size() != 0)
        {
            searchSub2.put("$and", hgmdAndhighList);
        }
        
        BasicDBList orList = new BasicDBList();
        if (searchSub2.size() != 0)
        {
            orList.add(searchSub2);
        }
        
        if (null != obj.get("MutInDatabase"))
        {
            BasicDBObject query1 = new BasicDBObject();
            if (obj.get("MutInDatabase").equals("Y"))
            {
                
                query1.put("MutInDatabase", new BasicDBObject("$exists", true));
            }
            else if (obj.get("MutInDatabase").equals("N"))
            {
                query1.put("MutInDatabase", new BasicDBObject("$exists", false));
            }
            if (query1.size() != 0)
            {
                orList.add(query1);
            }
            
        }
        if (orList.size() != 0)
        {
            cond2.put("$or", orList);
        }
        
    }
    
    @Override
    public List<ClaimTemplate> selectForPriFlag()
    {
        return claimTemplatemapper.selectForPriFlag();
    }
    
    private void joinAndOr(DBObject obj, String key, BasicDBList condList, String type)
    {
        if ("and".equals(type))
        {
            if (null != obj.get(key))
            {
                if (obj.get(key) instanceof List)
                {
                    BasicDBList subList = new BasicDBList();
                    List<String> sl = (List<String>)obj.get(key);
                    for (int i = 0; i < sl.size(); i++)
                    {
                        if ("空值".equals(sl.get(i)))
                        {
                            BasicDBObject query = new BasicDBObject();
                            query.put(key, new BasicDBObject("$exists", false));
                            subList.add(query);
                        }
                        else
                        {
                            BasicDBObject condObj = new BasicDBObject(key, sl.get(i));
                            subList.add(condObj);
                        }
                        
                    }
                    BasicDBObject searchSub = new BasicDBObject();
                    searchSub.put("$or", subList);
                    condList.add(searchSub);
                }
                else if ("REVEL_score.SPIDEX".equals(key))
                {
                    BasicDBObject searchSub = new BasicDBObject();
                    BasicDBList subList = new BasicDBList();
                    BasicDBObject mxnObj;
                    BasicDBObject minObj;
                    if (null != obj.get("SPIDEXMax"))
                    {
                        
                        mxnObj = new BasicDBObject(key, new BasicDBObject("$lte", Double.parseDouble(obj.get("SPIDEXMax").toString())));
                        subList.add(mxnObj);
                        
                    }
                    if (null != obj.get("SPIDEXMin"))
                    {
                        minObj = new BasicDBObject(key, new BasicDBObject("$gte", Double.parseDouble(obj.get("SPIDEXMin").toString())));
                        subList.add(minObj);
                        searchSub.put("$and", subList);
                    }
                    if (null == obj.get("SPIDEXMin"))
                    {
                        minObj = new BasicDBObject(key, new BasicDBObject("$exists", true));
                        subList.add(minObj);
                        searchSub.put("$or", subList);
                    }
                    condList.add(searchSub);
                    
                }
                else if ("MutInNormal".equals(key))
                {
                    BasicDBObject query1 = new BasicDBObject();
                    if (obj.get(key).equals("Y"))
                    {
                        
                        query1.put(key, new BasicDBObject("$exists", true));
                    }
                    else if (obj.get(key).equals("N"))
                    {
                        query1.put(key, new BasicDBObject("$exists", false));
                    }
                    
                    condList.add(query1);
                    
                }
                else
                {
                    if ("空值".equals(obj.get(key)))
                    {
                        BasicDBObject query = new BasicDBObject();
                        query.put(key, new BasicDBObject("$exists", false));
                        condList.add(query);
                    }
                    else
                    {
                        BasicDBObject condObj = new BasicDBObject(key, obj.get(key));
                        condList.add(condObj);
                    }
                    
                }
                
            }
        }
        else
        {
            if (null != obj.get(key))
            {
                BasicDBList orList = new BasicDBList();
                if (obj.get(key) instanceof List)
                {
                    BasicDBList subList = new BasicDBList();
                    List<String> sl = (List<String>)obj.get(key);
                    for (int i = 0; i < sl.size(); i++)
                    {
                        if ("空值".equals(sl.get(i)))
                        {
                            BasicDBObject query = new BasicDBObject();
                            query.put(key, new BasicDBObject("$exists", false));
                            subList.add(query);
                        }
                        else
                        {
                            BasicDBObject condObj = new BasicDBObject(key, sl.get(i));
                            subList.add(condObj);
                        }
                    }
                    BasicDBObject searchSub = new BasicDBObject();
                    searchSub.put("$or", subList);
                    orList.add(searchSub);
                }
                else if ("REVEL_score.SPIDEX".equals(key))
                {
                    BasicDBObject minObj;
                    BasicDBObject mxnObj;
                    BasicDBList subList = new BasicDBList();
                    if (null != obj.get("SPIDEXMax"))
                    {
                        
                        mxnObj = new BasicDBObject(key, new BasicDBObject("$lte", obj.get("SPIDEXMax")));
                        subList.add(mxnObj);
                        
                    }
                    if (null == obj.get("SPIDEXMin"))
                    {
                        minObj = new BasicDBObject(key, new BasicDBObject("$exists", true));
                        subList.add(minObj);
                        BasicDBObject searchSub = new BasicDBObject();
                        searchSub.put("$or", subList);
                        orList.add(searchSub);
                    }
                    if (null != obj.get("SPIDEXMin"))
                    {
                        minObj = new BasicDBObject(key, new BasicDBObject("$gte", obj.get("SPIDEXMin")));
                        subList.add(minObj);
                        BasicDBObject searchSub = new BasicDBObject();
                        searchSub.put("$and", subList);
                        orList.add(searchSub);
                    }
                }
                else if ("MutInDatabase".equals(key) || "MutInNormal".equals(key))
                {
                    BasicDBObject query1 = new BasicDBObject();
                    if (obj.get(key).equals("Y"))
                    {
                        query1.put(key, new BasicDBObject("$exists", true));
                    }
                    else if (obj.get(key).equals("N"))
                    {
                        query1.put(key, new BasicDBObject("$exists", false));
                    }
                    
                    orList.add(query1);
                    
                }
                else
                {
                    
                    if ("空值".equals(obj.get(key)))
                    {
                        BasicDBObject query = new BasicDBObject();
                        query.put(key, new BasicDBObject("$exists", false));
                        condList.add(query);
                    }
                    else
                    {
                        BasicDBObject condObj = new BasicDBObject(key, obj.get(key));
                        condList.add(condObj);
                    }
                    
                }
                
                BasicDBObject searchSubor = new BasicDBObject();
                if (orList.size() != 0)
                {
                    searchSubor.put("$or", orList);
                }
                if (searchSubor.size() != 0)
                {
                    condList.add(searchSubor);
                }
                
            }
            
        }
    }
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof MutationDataSearcher))
        {
            throw new IllegalArgumentException();
        }
        MutationDataSearcher search = (MutationDataSearcher)searcher;
        DBCollection collection = template.getCollection("analysis-mutation");
        int a = collection.find(search.getSearchCond()).count();
        return a;
    }
    
    @Override
    public List<DBObject> query(Object searcher, int offset, int limit)
    {
        List<DBObject> list = new ArrayList<DBObject>();
        if (!(searcher instanceof MutationDataSearcher))
        {
            throw new IllegalArgumentException();
        }
        MutationDataSearcher search = (MutationDataSearcher)searcher;
        DBCollection collection = template.getCollection("analysis-mutation");
        BasicDBObject paSortObj = new BasicDBObject("pa_sort", 1);
        Iterator<DBObject> iterator = collection.find(search.getSearchCond()).sort(paSortObj).skip(offset).limit(limit).iterator();
        while (iterator.hasNext())
        {
            DBObject obj = iterator.next();
            list.add(obj);
        }
        return list;
    }
    
    @Override
    public boolean ifColletion(DoctorIfColletionRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        if (this.MUTATION_COLLECTION.equals(request.getTag()))
        {
            // 突变收藏
            List<TechnicalAnalyRecordWithBLOBs> recordList = technicalAnalyRecordMapper.getByObjectId(request.getId());
            if (Collections3.isEmpty(recordList))
            {
                return false;
            }
            logger.info("mutation data is collectioned!  id:" + request.getId());
            return true;
        }
        else if (this.CNV_RESULT.equals(request.getTag()))
        {
            List<TechnicalAnalyRecordWithBLOBs> resultList = technicalAnalyRecordMapper.getByCnvResultId(request.getId());
            if (Collections3.isEmpty(resultList))
            {
                return false;
            }
            
            return true;
        }
        else if (this.CNV_PIC.equals(request.getTag()))
        {
            CnvAnalysisPic cnvAnalysisPic = cnvAnalysisPicMapper.selectByPrimaryKey(request.getId());
            if (cnvAnalysisPic != null && cnvAnalysisPic.getIsCollection() != null && 1 == cnvAnalysisPic.getIsCollection())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        return false;
        
    }
    
    @Override
    public List<TechnicalAnalysisSampleModel> getFamilySampleByFamilyId(String familyId)
    {
        List<TechnicalAnalysisSampleModel> respList = new ArrayList<>();
        TechnicalAnalysisTask searcher = new TechnicalAnalysisTask();
        searcher.setFamilyGroupId(familyId);
        List<TechnicalAnalysisTask> tasks = technicalAnalysisTaskMapper.selectBySearcher(searcher);
        if (Collections3.isNotEmpty(tasks) && tasks.size() == 1)
        {
            // 单利样本,带出该订单下所有验证样本和本人
            String orderId = Collections3.getFirst(tasks).getOrderId();
            List<OrderSampleDetail> details = orderSampleDetailMapper.selectByOrderId(orderId);
            for (OrderSampleDetail sampleDetail : details)
            {
                if ((null != sampleDetail.getPurpose() && 1 == sampleDetail.getPurpose())
                    || sampleDetail.getSampleCode().equals(Collections3.getFirst(tasks).getReceivedSampleCode()))
                {
                    TechnicalAnalysisSampleModel model = new TechnicalAnalysisSampleModel();
                    model.setSampleCode(sampleDetail.getSampleCode());
                    model.setSamplePurpose(sampleDetail.getPurpose());
                    model.setFamilyRelation(sampleDetail.getFamilyRelation());
                    // 查询检测样本id
                    TestingSample testingSample = testingSampleMapper.selectByPrimarySampleCode(sampleDetail.getSampleCode());
                    if (null != testingSample)
                    {
                        model.setTestingSampleId(testingSample.getId());
                    }
                    if (sampleDetail.getSampleCode().equals(Collections3.getFirst(tasks).getReceivedSampleCode()))
                    {
                        model.setIfSelf(1);
                    }
                    respList.add(model);
                    
                }
            }
            
        }
        else if (Collections3.isNotEmpty(tasks) && tasks.size() > 1)
        {
            // 家系样本,带出该订单下所有检测验证样本
            String orderId = Collections3.getFirst(tasks).getOrderId();
            List<OrderSampleDetail> details = orderSampleDetailMapper.selectByOrderId(orderId);
            List<OrderSampleDetail> reSampleList = new ArrayList<>();
            List<OrderSampleDetail> errorSampleList = new ArrayList<>();
            for (OrderSampleDetail detail : details)
            {
                if (StringUtils.isNotEmpty(detail.getSampleErrorStatus()) && detail.getSampleErrorStatus() == 1) //此样本处理为重新送样
                {
                    if (StringUtils.isNotEmpty(detail.getSampleErrorNewNo()))
                    {
                        ReceivedSample receivedSample = receivedSampleMapper.selectBySampleCode(detail.getSampleErrorNewNo());
                        if (receivedSample!=null)
                        {
                            OrderSampleDetail re = orderSampleDetailMapper.selectBySampleCode(detail.getSampleErrorNewNo().toUpperCase());
                            errorSampleList.add(detail);
                            reSampleList.add(re);
                        }
                    }
                }
            }

            //在所有样本中去除异常的样本
            if (Collections3.isNotEmpty(errorSampleList))
            {
                for (int i = 0; i <= details.size() - 1; i++)
                {
                    for (int j = 0; j <= errorSampleList.size() - 1; j++)
                    {
                        if (details.get(i).getSampleCode().equals(errorSampleList.get(j).getSampleCode()))
                        {
                            details.remove(i);
                            i--;
                            break;
                        }
                    }

                }
            }
            //在所有样本中去除重新送样的样本
            if (Collections3.isNotEmpty(reSampleList))
            {
                for (int i = 0; i <= details.size() - 1; i++)
                {
                    for (int j = 0; j <= reSampleList.size() - 1; j++)
                    {
                        if (details.get(i).getSampleCode().equals(reSampleList.get(j).getSampleCode()))
                        {
                            details.remove(i);
                            i--;
                            break;
                        }
                    }

                }
            }
            int i =0;
            for (TechnicalAnalysisTask t: tasks ) //只有这条家系对应的任务要考虑重新送样的问题，如果是检测跟对照一起走家系，如果是验证就不会有重新送样的问题了
            {
                AbnormalSolveRecord record = testingAdapter.getReSampleRecord(t.getId());//这条任务关联流程重新送样的记录
                if (record !=null)
                {
                    for (OrderSampleDetail err: errorSampleList )
                    {
                        if (err.getSampleCode().equals(t.getReceivedSampleCode()))//如果这条任务存在重新送样，则只展示重新送样后的样本
                        {
                            OrderSampleDetail reSample = orderSampleDetailMapper.selectBySampleCode(err.getSampleErrorNewNo());
                            details.add(reSample);//重新送样的样本放入
                        }
                    }
                    i++;
                }
            }

            if (i == 0)//说明家系的几个任务关联的流程都没有重新送样
            {
                for (OrderSampleDetail error:errorSampleList)
                {
                    details.add(error);
                }
            }

            for (OrderSampleDetail sampleDetail : details)
            {
                
                if (null == sampleDetail.getPurpose() || 2 == sampleDetail.getPurpose() || 3 == sampleDetail.getPurpose() || 1 == sampleDetail.getPurpose())
                {
                    TechnicalAnalysisSampleModel model = new TechnicalAnalysisSampleModel();
                    model.setSampleCode(sampleDetail.getSampleCode());
                    model.setSamplePurpose(sampleDetail.getPurpose());
                    model.setFamilyRelation(sampleDetail.getFamilyRelation());
                    // 查询检测样本id
                    TestingSample testingSample = testingSampleMapper.selectByPrimarySampleCode(sampleDetail.getSampleCode());
                    if (null != testingSample)
                    {
                        model.setTestingSampleId(testingSample.getId());
                    }
                    // 本人指主样本
                    if (1 == sampleDetail.getSampleBtype())
                    {
                        model.setIfSelf(1);
                        model.setFamilyRelation("1");
                    }
                    respList.add(model);
                }
            }

            Comparator<TechnicalAnalysisSampleModel> comparator = new Comparator<TechnicalAnalysisSampleModel>()
            { //将样本根据家系关系排序
                public int compare(TechnicalAnalysisSampleModel o1, TechnicalAnalysisSampleModel o2)
                {
                    return  Integer.valueOf(o2.getFamilyRelation()).intValue() - Integer.valueOf(o1.getFamilyRelation()).intValue();
                }
            };
            Collections.sort(respList,comparator.reversed());
        }
        return respList;
        
    }
    
    @Override
    public DataColAndMutationDataModel queryMutationDataByFilterForDownload(MutationDataRequest request)
    {
        DataColAndMutationDataModel model = new DataColAndMutationDataModel();
        MutationFilterToWarpDataModel tempModel = warpQueryFilter(request, model);
        int pageNo = 1;
        int pageSize = 200;
        MutationDataSearcher searcher = new MutationDataSearcher();
        searcher.setSearchCond(tempModel.getSearchCond());
        PagerQueryer<DBObject> queryer = new PagerQueryer<DBObject>(this);
        Pager<DBObject> pager;
        List<DBObject> allList = Lists.newArrayList();
        do
        {
            
            pager = queryer.query(searcher, pageNo++, pageSize);
            
            if (!CollectionUtils.isEmpty(pager.getRecords()))
            {
                allList.addAll(pager.getRecords());
            }
            
        } while (!pager.isLastPage());
        
        warpMutationDataForDownload(model, tempModel, allList);
        
        return model;
    }
    
    // 下载使用
    private void warpMutationDataForDownload(DataColAndMutationDataModel model, MutationFilterToWarpDataModel tempModel, List<DBObject> dbList)
    {
        List<Map<String, String>> dataValueList = new ArrayList<>();
        Map<String, List<String>> secondColMap = new HashMap<>();
        if (null != dbList && dbList.size() > 0)
        {
            for (DBObject obj2 : dbList)
            {
                if (null != model.getColList() && model.getColList().size() > 0)
                {
                    Map<String, String> map = new HashMap<>();
                    for (String col : model.getColList())
                    {
                        List<DataTemplateColumn> subList = dataTemplateService.getColumnByGroupName(col, tempModel.getTemplateId());
                        if (null != subList && subList.size() > 0)
                        {
                            if (subList.size() == 1)
                            {
                                if ("Gene_Symbol".equals(subList.get(0).getSemantic()))
                                {
                                    String s = null == obj2.get(subList.get(0).getSemantic()) ? "" : obj2.get(subList.get(0).getSemantic()).toString();
                                    /*
                                     * if (null !=
                                     * tempModel.getGeneSymbolList()) { for
                                     * (String str :
                                     * tempModel.getGeneSymbolList()) { s =
                                     * s.replace(str,
                                     * "<span style='color: red;'>" + str +
                                     * "</span>"); } }
                                     */
                                    map.put(subList.get(0).getGroupName(), s);
                                }
                                else if ("遗传来源".equals(subList.get(0).getSemantic()))
                                {
                                    if (null != obj2.get(subList.get(0).getSemantic()))
                                    {
                                        Map m = (Map)obj2.get(subList.get(0).getSemantic());
                                        map.put(subList.get(0).getGroupName(), null == m ? "" : m.get("name").toString());
                                    }
                                    else
                                    {
                                        map.put(subList.get(0).getGroupName(), "");
                                    }
                                    
                                }
                                else if ("Mygeno_InterACMG".equals(subList.get(0).getSemantic()))
                                {
                                    String s = "-";
                                    if (null != obj2.get(subList.get(0).getSemantic()))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get(subList.get(0).getSemantic());
                                        
                                        if (null != m.get("name"))
                                        {
                                            s = m.get("name").toString();
                                        }
                                        map.put(subList.get(0).getGroupName(), s);
                                    }
                                }
                                else
                                {
                                    map.put(subList.get(0).getGroupName(),
                                        null == obj2.get(subList.get(0).getSemantic()) ? "" : obj2.get(subList.get(0).getSemantic()).toString());
                                    
                                }
                            }
                            else
                            {
                                if (col.equals("关联疾病|遗传方式"))
                                {
                                    String[] ds = new String[] {""};
                                    String[] is = new String[] {""};
                                    if (null == obj2.get("Disease"))
                                    {
                                        ds = new String[] {""};
                                    }
                                    else
                                    {
                                        if (obj2.get("Disease").toString().contains("|"))
                                        {
                                            ds = obj2.get("Disease").toString().split("\\|");
                                        }
                                        else if (obj2.get("Disease").toString().contains(","))
                                        {
                                            ds = obj2.get("Disease").toString().split("\\,");
                                        }
                                        else
                                        {
                                            ds = new String[] {obj2.get("Disease").toString()};
                                        }
                                    }
                                    
                                    if (null == obj2.get("Inhert"))
                                    {
                                        is = new String[] {""};
                                    }
                                    else
                                    {
                                        if (obj2.get("Inhert").toString().contains("|"))
                                        {
                                            is = obj2.get("Inhert").toString().split("\\|");
                                        }
                                        else if (obj2.get("Inhert").toString().contains(","))
                                        {
                                            is = obj2.get("Inhert").toString().split("\\,");
                                        }
                                        else
                                        {
                                            is = new String[] {obj2.get("Inhert").toString()};
                                        }
                                    }
                                    
                                    StringBuffer s = new StringBuffer();
                                    ;
                                    if (ds.length != 0 || is.length != 0)
                                    {
                                        for (int i = 0; i < (0 == ds.length ? is.length : ds.length); i++)
                                        {
                                            if (i == 0)
                                            {
                                                
                                                if (i <= ds.length - 1)
                                                {
                                                    s.append(ds[i]);
                                                }
                                                s.append(" | ");
                                                if (i <= is.length - 1)
                                                {
                                                    s.append(is[i]);
                                                }
                                                
                                            }
                                            else
                                            {
                                                s.append("<br/>");
                                                if (i <= ds.length - 1)
                                                {
                                                    s.append(ds[i]);
                                                }
                                                s.append(" | ");
                                                if (i <= is.length - 1)
                                                {
                                                    s.append(is[i]);
                                                }
                                                
                                            }
                                        }
                                    }
                                    
                                    if (null != tempModel.getDiseaseNameList())
                                    {
                                        for (String str : tempModel.getDiseaseNameList())
                                        {
                                            s = new StringBuffer(s.toString().replace(str, "<span style='color: red;'>" + str + "</span>"));
                                        }
                                    }
                                    map.put(col, s.toString());
                                }
                                if ("变异信息".equals(col))
                                {
                                    if (null != obj2.get("AA_change"))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get("AA_change");
                                        if (null != m)
                                        {
                                            map.put(col, null == m.get("name") ? "" : m.get("name").toString().split(",")[0]);
                                        }
                                    }
                                }
                                if ("疾病与基因关联度打分".equals(col))
                                {
                                    if (null != obj2.get("Total_Score"))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get("Total_Score");
                                        if (null != m)
                                        {
                                            map.put(col, null == m.get("Total_Score") ? "" : m.get("Total_Score").toString());
                                        }
                                    }
                                }
                                if ("人群频率".equals(col))
                                {
                                    if (null != obj2.get("Highest-MAF"))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get("Highest-MAF");
                                        if (null != m)
                                        {
                                            map.put(col, null == m.get("name") ? "" : m.get("name").toString());
                                        }
                                    }
                                }
                                if ("蛋白功能预测".equals(col))
                                {
                                    if (null != obj2.get("REVEL_score"))
                                    {
                                        Map<String, Object> m = (Map<String, Object>)obj2.get("REVEL_score");
                                        if (null != m)
                                        {
                                            map.put(col, null == m.get("name") ? "" : m.get("name").toString());
                                        }
                                    }
                                }
                                if ("Evidence".equals(col))
                                {
                                    List<String> sl = new ArrayList<>();
                                    for (DataTemplateColumn dtc : subList)
                                    {
                                        if ("Tag".equals(dtc.getSemantic()))
                                        {
                                            if (null != obj2.get(dtc.getSemantic()))
                                            {
                                                Map<String, Object> m = (Map<String, Object>)obj2.get("Tag");
                                                if (null != m)
                                                {
                                                    map.put(dtc.getColumnName(), null == m.get("name") ? "" : m.get("name").toString());
                                                }
                                            }
                                        }
                                        else
                                        {
                                            map.put(dtc.getColumnName(), null == obj2.get(dtc.getSemantic()) ? "" : obj2.get(dtc.getSemantic()).toString());
                                        }
                                        sl.add(dtc.getColumnName());
                                    }
                                    secondColMap.put(col, sl);
                                }
                            }
                        }
                    }
                    
                    map.put("_id", obj2.get("_id").toString());
                    map.put("analysisSampleId", obj2.get("analysisSampleId").toString());
                    dataValueList.add(map);
                }
            }
        }
        else
        {
            if (null != model.getColList() && model.getColList().size() > 0)
            {
                for (String col : model.getColList())
                {
                    if ("Evidence".equals(col))
                    {
                        List<DataTemplateColumn> subList = dataTemplateService.getColumnByGroupName(col, tempModel.getTemplateId());
                        List<String> sl = new ArrayList<>();
                        for (DataTemplateColumn dtc : subList)
                        {
                            sl.add(dtc.getColumnName());
                        }
                        secondColMap.put(col, sl);
                    }
                }
            }
        }
        model.setDataValue(dataValueList);
        model.setSenondColName(secondColMap);
        
    }
    
    @Override
    public Map<String, String> getGroupDetails(String groupName, String id)
    {
        String templateId = "";
        Map<String, String> map = new LinkedHashMap<>();
        List<ClaimTemplate> claimList = this.selectForPriFlag();
        if (null != claimList && claimList.size() > 0)
        {
            ClaimTemplate ct = claimList.get(0);
            templateId = ct.getTemplateId();
        }
        DBCollection collection = template.getCollection("analysis-mutation");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", new ObjectId(id));
        DBObject dbObj = collection.findOne(queryCondition);
        
        if (null != templateId && !"".equals(templateId))
        {
            List<DataTemplateColumn> subList = dataTemplateService.getColumnByGroupName(groupName, templateId);
            if (null != subList)
            {
                for (DataTemplateColumn col : subList)
                {
                    if (null != dbObj)
                    {
                        if ("变异信息".equals(groupName))
                        {
                            Map<String, Object> m = (Map<String, Object>)dbObj.get("AA_change");
                            if (null != m)
                            {
                                map.put(col.getColumnName(), null == m.get(col.getSemantic()) ? "" : m.get(col.getSemantic()).toString());
                            }
                        }
                        else if ("疾病与基因关联度打分".equals(groupName))
                        {
                            Map<String, Object> m = (Map<String, Object>)dbObj.get("Total_Score");
                            if (null != m)
                            {
                                map.put(col.getColumnName(), null == m.get(col.getSemantic()) ? "" : m.get(col.getSemantic()).toString());
                            }
                        }
                        else if ("人群频率".equals(groupName))
                        {
                            Map<String, Object> m = (Map<String, Object>)dbObj.get("Highest-MAF");
                            if (null != m)
                            {
                                if ("highest-MAF".equals(col.getColumnName()))
                                {
                                    map.put(col.getColumnName(), null == m.get("name") ? "" : m.get("name").toString());
                                }
                                else
                                {
                                    map.put(col.getColumnName(), null == m.get(col.getSemantic()) ? "" : m.get(col.getSemantic()).toString());
                                }
                                
                            }
                        }
                        else if ("蛋白功能预测".equals(groupName))
                        {
                            Map<String, Object> m = (Map<String, Object>)dbObj.get("REVEL_score");
                            if (null != m)
                            {
                                map.put(col.getColumnName(), null == m.get(col.getSemantic()) ? "" : m.get(col.getSemantic()).toString());
                            }
                        }
                        else if ("HGMD".equals(groupName))
                        {
                            Map<String, Object> m = (Map<String, Object>)dbObj.get("Tag");
                            if (null != m)
                            {
                                if ("hgvs".equals(col.getSemantic()) || "Descr".equals(col.getSemantic()))
                                {
                                    String hgvs = null == m.get("hgvs") ? "" : m.get("hgvs").toString();
                                    String descr = null == m.get("Descr") ? "" : m.get("Descr").toString();
                                    map.put("报道突变", hgvs);
                                }
                                else
                                {
                                    map.put(col.getColumnName(), null == m.get(col.getSemantic()) ? "" : m.get(col.getSemantic()).toString());
                                }
                            }
                        }
                        else
                        {
                            map.put(col.getColumnName(), null == dbObj.get(col.getSemantic()) ? "" : dbObj.get(col.getSemantic()).toString());
                        }
                    }
                }
            }
        }
        return map;
    }
    
    @Override
    public Integer collection(TechnicalCollectionRequest request)
    {
        for (TechnicalCollection collection : request.getList())
        {
            TechnicalAnalyRecordWithBLOBs record = new TechnicalAnalyRecordWithBLOBs();
            BeanUtils.copyProperties(collection.getRecord(), record);
            List<TechnicalAnalysisTask> tasks = technicalAnalysisTaskMapper.getTaskByFamilyId(collection.getRecord().getTechnicalFamilyGroupId());
            if (Collections3.isNotEmpty(tasks) && tasks.size() == 1)
            {
                record.setTechnicalTaskId(Collections3.getFirst(tasks).getId());
            }
            if ("1".equals(record.getVerify()))
            {
                record.setVerify("验证");
                record.setLocusType("验证位点");
            }
            else
            {
                record.setVerify("不验证");
            }
            // 插入验证流程必填字段
            DBCollection dbCollection = template.getCollection("analysis-mutation");
            BasicDBObject queryCondition = new BasicDBObject();
            
            queryCondition.put("_id", new ObjectId(record.getMutationObjectId()));
            
            DBObject dbObj = dbCollection.findOne(queryCondition);
            if (null != dbObj)
            {
                record.setDisease(null == dbObj.get("Disease") ? "" : dbObj.get("Disease").toString());
                record.setInhert(null == dbObj.get("Inhert") ? "" : dbObj.get("Inhert").toString());
                record.setGeneSymbol(null == dbObj.get("Gene_Symbol") ? "" : dbObj.get("Gene_Symbol").toString());
                record.setChromosomeLocation(null == dbObj.get("ID") ? "" : dbObj.get("ID").toString());
                record.setEndLocus(null == dbObj.get("End") ? "" : dbObj.get("End").toString());
                if (null != dbObj.get("ID"))
                {
                    String[] chromosomeList = dbObj.get("ID").toString().split("-");
                    String chromosome = chromosomeList[0];
                    record.setChromosome(chromosome);
                    if (chromosomeList.length > 1)
                    {
                        String[] locationList = chromosomeList[1].split("\\s+");
                        record.setBeginLocus(locationList[0]);
                        
                    }
                }
                record.setNucleotideChanges(null == dbObj.get("AA_change") ? "" : (null == ((DBObject)(dbObj.get("AA_change"))).get("Nucleotide_Changes")) ? ""
                    : ((DBObject)(dbObj.get("AA_change"))).get("Nucleotide_Changes").toString());
                record.setAminoacidChanges(null == dbObj.get("AA_change") ? "" : (null == ((DBObject)(dbObj.get("AA_change"))).get("Amino_Acid_Changes")) ? ""
                    : ((DBObject)(dbObj.get("AA_change"))).get("Amino_Acid_Changes").toString());
                record.setGeneType(null == dbObj.get("Gene_Type") ? "" : dbObj.get("Gene_Type").toString());
                record.setExon(null == dbObj.get("AA_change") ? "" : (null == ((DBObject)(dbObj.get("AA_change"))).get("Exon")) ? ""
                    : ((DBObject)(dbObj.get("AA_change"))).get("Exon").toString());
                record.setMutationSource(null == dbObj.get("遗传来源") ? "" : (null == ((DBObject)(dbObj.get("遗传来源"))).get("name")) ? ""
                    : ((DBObject)(dbObj.get("遗传来源"))).get("name").toString());
                
                String dataCode = dbObj.get("Sample").toString();
                if (StringUtils.isNotEmpty(dataCode))
                {
                    record.setDataCode(dataCode);
                    String sampleCode = dataCode.split("_")[0];
                    record.setSample(sampleCode);
                    
                }
            }
            technicalAnalyRecordMapper.insertSelective(record);
            for (TechnicalAnalyVerifySample sample : collection.getSamples())
            {
                TechnicalAnalyVerify verify = new TechnicalAnalyVerify();
                verify.setInputSampleId(sample.getId());
                verify.setRecordId(record.getId());
                verify.setSampleFamilyRelation(sample.getFamilyRelation());
                technicalAnalyVerifyMapper.insertSelective(verify);
            }
        }
        return 1;
    }
    
    @Override
    public void saveAddVerify(TechnicalAddVerifyRequest request)
    {
        //删掉已有的 保存现在的
        String familyId = request.getTechnicalFamilyGroupId();
        addVerifyMapper.deleteByFamilyGroupId(familyId);
        if (Collections3.isNotEmpty(request.getTextsForList()))
        {
            for (TechnicalAnalysisAddVerify verify : request.getTextsForList())
            {
                verify.setId(IdGen.uuid());
                verify.setTechnicalAnalysisId(familyId);
                addVerifyMapper.insert(verify);
            }
        }
    }
    
    @Override
    public VariableModel saveAddVerifyNew(TechnicalAddVerifyRequest request)
    {
        String familyId = request.getTechnicalFamilyGroupId();
        List<TechnicalAnalysisAddVerify> results = new ArrayList<>();
        if (Collections3.isNotEmpty(request.getTextsForList()))
        {
            for (TechnicalAnalysisAddVerify verify : request.getTextsForList())
            {
                verify.setId(IdGen.uuid());
                verify.setTechnicalAnalysisId(familyId);
                addVerifyMapper.insert(verify);
                results.add(verify);
            }
        }
        
        TechnicalAnalySubmitRequest request1 = new TechnicalAnalySubmitRequest();
        request1.setFamilyGroupId(familyId);
        
        List<TechnicalAnalysisTask> technicalTasks = technicalAnalysisTaskMapper.getTaskByFamilyId(familyId); //根据groupid 查询非异常技术分析任务
        VariableModel model = new VariableModel();
        List<TestingTask> tasks = Lists.newArrayList();
        List<TechnicalAnalyRecord> records = new ArrayList<>();
        for (TechnicalAnalysisAddVerify verify : results)
        {
            
            TechnicalAnalyRecordWithBLOBs record = new TechnicalAnalyRecordWithBLOBs();
            //补提的都做验证
            if (Collections3.isNotEmpty(technicalTasks) && technicalTasks.size() == 1)
            {
                record.setTechnicalTaskId(Collections3.getFirst(technicalTasks).getId());
            }
            record.setTechnicalFamilyGroupId(familyId);
            record.setVerify("验证");
            record.setLocusType("验证位点");
            record.setVerifyMethod(verify.getVerifyMethod());
            record.setClinicalJudgment(verify.getClinicalJudgment());
            record.setGeneSymbol(verify.getGene());
            record.setChromosome(verify.getChrom());
            record.setBeginLocus(verify.getStartLocation());
            record.setEndLocus(verify.getEndLocation());
            // 拼装CHROMOSOME_LOCATION chr-location1 location2
            record.setChromosomeLocation(verify.getChrom() + "-" + verify.getStartLocation() + " " + verify.getEndLocation());
            record.setNucleotideChanges(verify.getNucleotide());
            record.setAminoacidChanges(verify.getAminoAcid());
            record.setSample(verify.getSampleCode());//去主样本的
            record.setDataCode(verify.getDataCode());//取主样本的
            record.setSourceRef("3");
            record.setGeneType(verify.getPure());
            technicalAnalyRecordMapper.insertSelective(record);
            records.add(record);
            if (StringUtils.isNotEmpty(verify.getFamilyRelation()))
            {
                String relations[] = StringUtils.split(verify.getFamilyRelation(), ",");
                for (String sampleRelation : relations)
                {
                    String relation = sampleRelation.split("_")[0];
                    String sampleId = sampleRelation.split("_")[1];
                    TechnicalAnalyVerify verifyRelation = new TechnicalAnalyVerify();
                    verifyRelation.setInputSampleId(sampleId);
                    verifyRelation.setRecordId(record.getId());
                    verifyRelation.setSampleFamilyRelation(relation);
                    technicalAnalyVerifyMapper.insertSelective(verifyRelation);
                }
            }
        }
        
        for (TechnicalAnalyRecord record : records)
        {
            TechnicalAnalySubmitRecord submitRecord = new TechnicalAnalySubmitRecord();
            submitRecord.setRecord(record);
            List<TechnicalAnalyVerify> verifys = technicalAnalyVerifyMapper.selectByRecord(record.getId());
            List<TechnicalAnalyVerifySample> verifySamples = new ArrayList<>();
            for (TechnicalAnalyVerify verify : verifys)
            {
                TechnicalAnalyVerifySample verifySample = new TechnicalAnalyVerifySample();
                verifySample.setFamilyRelation(verify.getSampleFamilyRelation());
                verifySample.setId(verify.getInputSampleId());
                verifySample.setRecordId(record.getId());
                verifySamples.add(verifySample);
            }
            submitRecord.setVerifySamples(verifySamples);
            submitRecord.setVerifyMethod(record.getVerifyMethod());
            if ("验证".equals(record.getVerify()))
            {
                submitRecord.setVerify(true);
            }
            else
            {
                submitRecord.setVerify(false);
            }
            
            request1.getRecords().add(submitRecord);
            
        }
        
        submit(request1, model, tasks);
        return model;
        
    }
    
    @Override
    public List<TechnicalAnalysisAddVerify> getAddVerifyDataByFamilyId(String familyId)
    {
        // 查询 技术分析的 补提记录
        List<TechnicalAnalysisAddVerify> results = addVerifyMapper.getAddVerifyDataByFamilyId(familyId);
        return results;
    }
    
    @Override
    public void submitTechnicalTask(String familyGroupId)
    {
        technicalAnalysisTaskMapper.submitTechnicalTaskBy(familyGroupId, "4");
    }
    
    @Override
    @Transactional
    public VariableModel startAnalsyisSchedule(String analysisFamilyId)
    {
        //复核通过之后给结束时间赋值，复核已通过
        TechnicalAnalysisTask searcher = new TechnicalAnalysisTask();
        searcher.setFamilyGroupId(analysisFamilyId);
        List<TechnicalAnalysisTask> analysisTasks = technicalAnalysisTaskMapper.selectBySearcher(searcher);
        if (Collections3.isNotEmpty(analysisTasks))
        {
            for (TechnicalAnalysisTask task : analysisTasks)
            {
                task.setEndTime(new Date());
                technicalAnalysisTaskMapper.updateByPrimaryKeySelective(task);
            }
            
        }
        
        // 保存补提的记录
        saveAddVerifyToAnalyRecord(analysisFamilyId);
        
        TechnicalAnalySubmitRequest request = new TechnicalAnalySubmitRequest();
        request.setFamilyGroupId(analysisFamilyId);
        VariableModel model = new VariableModel();
        List<TestingTask> tasks = Lists.newArrayList();
        List<TechnicalAnalyRecord> records = technicalAnalyRecordMapper.selectByFamilyId(analysisFamilyId);
        Map<String, TechnicalAnalyRecord> map = new HashMap<>();
        records.forEach(x -> {
            String key = x.getVerifyMethod() + x.getSample() + x.getChromosome() + x.getBeginLocus();
            map.put(key, x);
        });

        records = new ArrayList<>(map.values());
        for (TechnicalAnalyRecord record : records)
        {
            TechnicalAnalySubmitRecord submitRecord = new TechnicalAnalySubmitRecord();
            submitRecord.setRecord(record);
            List<TechnicalAnalyVerify> verifys = technicalAnalyVerifyMapper.selectByRecord(record.getId());
            List<TechnicalAnalyVerifySample> verifySamples = new ArrayList<>();
            for (TechnicalAnalyVerify verify : verifys)
            {
                TechnicalAnalyVerifySample verifySample = new TechnicalAnalyVerifySample();
                verifySample.setFamilyRelation(verify.getSampleFamilyRelation());
                verifySample.setId(verify.getInputSampleId());
                verifySample.setRecordId(record.getId());
                verifySamples.add(verifySample);
            }
            submitRecord.setVerifySamples(verifySamples);
            submitRecord.setVerifyMethod(record.getVerifyMethod());
            if ("验证".equals(record.getVerify()))
            {
                submitRecord.setVerify(true);
            }
            else
            {
                submitRecord.setVerify(false);
            }
            
            request.getRecords().add(submitRecord);
            
        }
        
        submit(request, model, tasks);
        
        //项目监控发送消息
        messageSendService.sendProgramMonitorNewBiologyMessage(analysisTasks);
        return model;
    }
    
    @Override
    public VariableModel getMod(String analysisFamilyId)
    {
        saveAddVerifyToAnalyRecord(analysisFamilyId);
        TechnicalAnalySubmitRequest request = new TechnicalAnalySubmitRequest();
        request.setFamilyGroupId(analysisFamilyId);
        VariableModel model = new VariableModel();
        List<TestingTask> tasks = Lists.newArrayList();
        List<TechnicalAnalyRecord> records = technicalAnalyRecordMapper.selectByFamilyId(analysisFamilyId);
        for (TechnicalAnalyRecord record : records)
        {
            TechnicalAnalySubmitRecord submitRecord = new TechnicalAnalySubmitRecord();
            submitRecord.setRecord(record);
            List<TechnicalAnalyVerify> verifys = technicalAnalyVerifyMapper.selectByRecord(record.getId());
            List<TechnicalAnalyVerifySample> verifySamples = new ArrayList<>();
            for (TechnicalAnalyVerify verify : verifys)
            {
                TechnicalAnalyVerifySample verifySample = new TechnicalAnalyVerifySample();
                verifySample.setFamilyRelation(verify.getSampleFamilyRelation());
                verifySample.setId(verify.getInputSampleId());
                verifySample.setRecordId(record.getId());
                verifySamples.add(verifySample);
            }
            submitRecord.setVerifySamples(verifySamples);
            submitRecord.setVerifyMethod(record.getVerifyMethod());
            if ("验证".equals(record.getVerify()))
            {
                submitRecord.setVerify(true);
            }
            else
            {
                submitRecord.setVerify(false);
            }
            
            request.getRecords().add(submitRecord);
            
        }
        
        submit(request, model, tasks);
        return model;
    }
    
    private void saveAddVerifyToAnalyRecord(String analysisFamilyId)
    {
        
        List<TechnicalAnalysisTask> task = technicalAnalysisTaskMapper.getTaskByFamilyId(analysisFamilyId);
        
        List<TechnicalAnalysisAddVerify> results = addVerifyMapper.getAddVerifyDataByFamilyId(analysisFamilyId);
        // 家系id
        for (TechnicalAnalysisAddVerify verify : results)
        {
            TechnicalAnalyRecordWithBLOBs record = new TechnicalAnalyRecordWithBLOBs();
            //补提的都做验证
            record.setTechnicalFamilyGroupId(analysisFamilyId);
            record.setVerify("验证");
            record.setLocusType("验证位点");
            record.setVerifyMethod(verify.getVerifyMethod());
            record.setClinicalJudgment(verify.getClinicalJudgment());
            record.setGeneSymbol(verify.getGene());
            record.setChromosome(verify.getChrom());
            record.setBeginLocus(verify.getStartLocation());
            record.setEndLocus(verify.getEndLocation());
            // 拼装CHROMOSOME_LOCATION chr-location1 location2
            record.setChromosomeLocation(verify.getChrom() + "-" + verify.getStartLocation() + " " + verify.getEndLocation());
            record.setNucleotideChanges(verify.getNucleotide());
            record.setAminoacidChanges(verify.getAminoAcid());
            record.setSample(verify.getSampleCode());//去主样本的
            record.setDataCode(verify.getDataCode());//取主样本的
            record.setSourceRef("3");
            record.setGeneType(verify.getPure());
            if (task != null && task.size() == 1)
            {
                record.setTechnicalTaskId(Collections3.getFirst(task).getId());//只有单列需要，家系暂无异常处理
            }
            technicalAnalyRecordMapper.insertSelective(record);
            if (StringUtils.isNotEmpty(verify.getFamilyRelation()))
            {
                String relations[] = StringUtils.split(verify.getFamilyRelation(), ",");
                for (String sampleRelation : relations)
                {
                    String relation = sampleRelation.split("_")[0];
                    String sampleId = sampleRelation.split("_")[1];
                    TechnicalAnalyVerify verifyRelation = new TechnicalAnalyVerify();
                    verifyRelation.setInputSampleId(sampleId);
                    verifyRelation.setRecordId(record.getId());
                    verifyRelation.setSampleFamilyRelation(relation);
                    technicalAnalyVerifyMapper.insertSelective(verifyRelation);
                }
            }
        }
    }
    
    @Transactional
    public void submit(TechnicalAnalySubmitRequest request, VariableModel model, List<TestingTask> tasks)
    {
        
        TechnicalAnalySubmitContext context = generateSubmitContext(request, model);
        
        // 1、保存技术分析结果数据
        doSaveTechnicalAnalyRecord(context);
        
        // 3、更新检测流程
        doUpdateTestingSchedules(context);
        
        // 6、下达验证流程
        doAssignVerifyProcess(context, tasks);
        
        // 更新冗余字段
        //updataTaskFileds(tasks);
        //testingScheduleService.updateRedundantTask(tasks);
        model.setTasks(tasks);
    }
    
    private Date setTaskPlanFinishDate(TestingTask task, TestingSchedule testingSchedule)
    {
        SchedulePlanTask searcher = new SchedulePlanTask();
        searcher.setOrderId(testingSchedule.getOrderId());
        searcher.setProductId(testingSchedule.getProductId());
        searcher.setTestingMethodId(testingSchedule.getMethodId());
        searcher.setSampleId(testingSchedule.getSampleId());
        searcher.setTaskSemantic(task.getSemantic());
        searcher.setVerifyId(testingSchedule.getVerifyKey());
        List<SchedulePlanTask> planTasks = schedulePlanTaskMapper.selectBySearcher(searcher);
        if (Collections3.isNotEmpty(planTasks))
        {
            Date date = planTasks.get(0).getPlannedFinishDate();
            return date;
        }
        return null;
        
    }
    
    private TechnicalAnalySubmitContext generateSubmitContext(TechnicalAnalySubmitRequest request, VariableModel model)
    {
        
        TechnicalAnalySubmitContext context = new TechnicalAnalySubmitContext();
        context.setContextForSubmitResult(request);
        String familyGroupId = request.getFamilyGroupId();
        TechnicalAnalysisTask searcher = new TechnicalAnalysisTask();
        searcher.setFamilyGroupId(familyGroupId);
        
        List<TechnicalAnalysisTask> technicalTasks = technicalAnalysisTaskMapper.selectBySearcher(searcher);
        
        TestingTask task;
        TestingSchedule schedule;
        
        List<String> scheduleIds = Lists.newArrayList();
        if (Collections3.isNotEmpty(technicalTasks))
        {
            
            for (TechnicalAnalysisTask technicalAnalysisTask : technicalTasks)
            {
                
                context.setContextForTestingTask(technicalAnalysisTask);
                TestingScheduleActive search = new TestingScheduleActive();
                search.setTaskId(technicalAnalysisTask.getId());
                
                // 查不到就查历史表 因为有可能是历史任务那边的补提
                String scheduleId = "";
                List<TestingScheduleActive> actives = testingScheduleActiveMapper.selectBySearcher(search);
                if (!CollectionUtils.isEmpty(actives))
                {
                    scheduleId = Collections3.getFirst(actives).getScheduleId();
                }
                else
                {
                    List<TestingScheduleHistory> histories = testingScheduleHistoryMapper.getByTaskId(technicalAnalysisTask.getId());
                    if (!CollectionUtils.isEmpty(histories))
                    {
                        scheduleId = Collections3.getFirst(histories).getScheduleId();
                    }
                    else
                    {
                        logger.error("technicalAnalysisTask scheduleId is null.technicalAnalysisTaskId is:" + technicalAnalysisTask.getId());
                        throw new IllegalStateException();
                    }
                }
                
                schedule = testingScheduleMapper.selectByPrimaryKey(scheduleId);
                context.setContextForTestingSchedule(technicalAnalysisTask, schedule);
                if (!scheduleIds.contains(schedule.getId()))
                {
                    scheduleIds.add(schedule.getId());
                }
            }
        }
        
        String ids = StringUtils.join(scheduleIds, ",");
        model.setScheduleIds(ids);
        return context;
    }
    
    private void doSaveTechnicalAnalyRecord(TechnicalAnalySubmitContext context)
    {
        List<TechnicalAnalySubmitRecord> records = context.getTechnicalAnalyRecords();
        
        for (TechnicalAnalySubmitRecord record : records)
        {
            
            if (record.isVerify() && !CollectionUtils.isEmpty(record.getVerifySamples()))
            {
                for (TechnicalAnalyVerifySample sample : record.getVerifySamples())
                {
                    TechnicalAnalyVerify searcher = new TechnicalAnalyVerify();
                    searcher.setInputSampleId(sample.getId());
                    searcher.setRecordId(sample.getRecordId());
                    TechnicalAnalyVerify verify = technicalAnalyVerifyMapper.selectBySearcher(searcher);
                    context.setContextForCreateTestingVerifyRecord(record, sample, verify);
                }
            }
        }
    }
    
    private void doUpdateTestingSchedules(TechnicalAnalySubmitContext context)
    {
        Set<TechnicalAnalySubmitScheduleContext> schedules = context.getSchedules();
        
        TestingSchedule schedule;
        List<TestingScheduleActive> actives;
        Date timestamp = new Date();
        List<String> ids = Lists.newArrayList();
        
        for (TechnicalAnalySubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            schedule.setActiveTask("已完成");
            schedule.setEndTime(timestamp);
            schedule.setEndType(TestingSchedule.END_SUCCESS);
            testingScheduleMapper.updateByPrimaryKeySelective(schedule);
            ids.add(schedule.getId());
            
            TestingScheduleActive search = new TestingScheduleActive();
            search.setScheduleId(schedule.getId());
            
            actives = testingScheduleActiveMapper.selectBySearcher(search);
            
            if (!CollectionUtils.isEmpty(actives))
            {
                for (TestingScheduleActive active : actives)
                {
                    testingScheduleActiveMapper.deleteByPrimaryKey(active.getId());
                }
            }
            
            //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
            List<TestingReportSample> testingReportSample = testingReportSampleMapper.selectByScheduleId(schedule.getId());
            if (Collections3.isNotEmpty(testingReportSample))
            {
                for (TestingReportSample s : testingReportSample)
                {
                    testingReportSampleMapper.deleteByPrimaryKey(s.getId());
                }
            }
        }
    }
    
    private void doAssignVerifyProcess(TechnicalAnalySubmitContext context, List<TestingTask> tasks)
    {
        
        Set<String> set = new HashSet<>();
        List<TechnicalAnalySubmitRecord> records = context.getTechnicalAnalyRecords();
        
        TechnicalAnalyVerify verify;
        
        String batchNo = getSangerVerifyCode();
        
        String dataCode = "";
        
        for (TechnicalAnalySubmitRecord record : records)
        {
            dataCode = record.getRecord().getDataCode();
            
            if (!record.isVerify() || CollectionUtils.isEmpty(record.getVerifySamples()))
            {
                continue;
            }
            
            for (TechnicalAnalyVerifySample sample : record.getVerifySamples())
            {
                verify = context.getTestingVerifyRecord(record, sample);
                
                if (verify == null)
                {
                    throw new IllegalStateException();
                }
                
                TechnicalAnalyRecord rec = technicalAnalyRecordMapper.selectRecordByPrimaryKey(verify.getRecordId());
                if (context.isAssigned(verify, rec))
                {
                    continue;
                }
                
                assignVerifyProcess(verify, context, batchNo, dataCode, tasks);
                
            }
        }
    }
    
    private String getSangerVerifyCode()
    {
        String todayStr = com.todaysoft.lims.technical.util.DateUtil.getDate("MMdd");
        String combineCode = "";
        int index = 1;
        Sequence sequence = getSequenceListByNameAndDate("technicalCombineBatch");
        if (null != sequence)
        {
            index = sequence.getCurrentValue().intValue();
        }
        combineCode = "S" + todayStr + String.format("%02d", index);
        return combineCode;
    }
    
    private Sequence getSequenceListByNameAndDate(String string)
    {
        Sequence sequence;
        String todayStr = com.todaysoft.lims.technical.util.DateUtil.getDate("yyyyMMdd");
        sequence = sequenceMapper.selectByName(string);
        if (null == sequence)
        {
            return null;
        }
        else
        {
            if (null == sequence.getBatchDate())
            {
                sequence.setBatchDate(new Date());
                sequence.setCurrentValue(1L);
            }
            else
            {
                if (!todayStr.equals(com.todaysoft.lims.technical.util.DateUtil.formatDate(sequence.getBatchDate(), "yyyyMMdd")))
                {
                    sequence.setBatchDate(new Date());
                    sequence.setCurrentValue(1L);
                }
                else
                {
                    sequence.setCurrentValue(sequence.getCurrentValue() + sequence.getIncrement());
                }
            }
        }
        sequenceMapper.updateByPrimaryKeySelective(sequence);
        return sequence;
    }
    
    private void assignVerifyProcess(TechnicalAnalyVerify verify, TechnicalAnalySubmitContext context, String batchNo, String dataCode, List<TestingTask> taskList)
    {
        List<String> schduleIds = new ArrayList<>();
        Set<String> orderIds = new HashSet<>();
        TechnicalAnalyRecord technicalRecord = technicalAnalyRecordMapper.selectRecordByPrimaryKey(verify.getRecordId());
        //
        TestingSchedule relatedSchedule = context.getTestingSchedule(technicalRecord.getDataCode());
        
        Product product = null;
        
        if (StringUtils.isNotEmpty(dataCode))
        {
            product = getProductByDataCode(dataCode);
        }
        
        if (null == relatedSchedule)
        {
            throw new IllegalStateException();
        }
        
        String method = technicalRecord.getVerifyMethod();
        
        TestingMethod methodSearcher = new TestingMethod();
        methodSearcher.setType(2);
        methodSearcher.setSemantic(method.toUpperCase());
        methodSearcher.setEnabled(true);
        List<TestingMethod> methods = testingMethodMapper.selectBySearcher(methodSearcher);
        
        if (CollectionUtils.isEmpty(methods))
        {
            return;
        }
        
        TestingMethod testingMethod = methods.get(0);
        
        //判断验证样本是否已经存在DNA，存在则设置样本未DNA，不需要再走提取节点（因为存在补提的情况，验证样本已经做过DNA）
        String sampleTypeId = testingMethod.getInputSampleType();
        TestingSample sample = testingSampleMapper.selectByPrimaryKey(verify.getInputSampleId());
        if (!sample.getSampleTypeId().equals(sampleTypeId))
        {
            List<TestingSample> dnaSampls = testingSampleMapper.selectByParentSample(verify.getInputSampleId());
            
            for (TestingSample dnaSample : dnaSampls)
            {
                List<TestingTask> taskss = getRelatedTasks(dnaSample.getId(), TaskSemantic.DNA_QC);
                
                if (!CollectionUtils.isEmpty(taskss))
                {
                    for (TestingTask task : taskss)
                    {
                        if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                        {
                            verify.setInputSampleId(dnaSample.getId());
                            break;
                        }
                    }
                }
            }
            
        }
        
        //------------------------------------------//
        
        if (TestingMethod.SANGER.equals(testingMethod.getSemantic()))
        {
            SangerVerifyRecord sangerVerifyRecord = new SangerVerifyRecord();
            sangerVerifyRecord.setVerifyRecord(verify.getId());
            sangerVerifyRecord.setCombineCode(getCombineCode(batchNo, verify));
            
            String inputSampleTypeId = testingMethod.getInputSampleType();
            TestingSample inputSample = testingSampleMapper.selectByPrimaryKey(verify.getInputSampleId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (StringUtils.isNotEmpty(inputSample.getParentSampleId()))
                {
                    sangerVerifyRecord.setDnaSampleId(verify.getInputSampleId());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                sangerVerifyRecord.setDnaSampleId(verify.getInputSampleId());
                                break;
                            }
                        }
                    }
                }
            }
            
            // 获取已设计引物
            Primer primer = getSangerVerifyPrimer(verify);
            if (null != primer)
            {
                sangerVerifyRecord.setPrimerId(primer.getId());
            }
            
            sangerVerifyRecordMapper.insertSelective(sangerVerifyRecord);
            
            // 创建流程
            
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (StringUtils.isEmpty(inputSample.getParentSampleId()))
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                TestingSample parentSample = testingSampleMapper.selectByPrimaryKey(inputSample.getParentSampleId());
                sampleId = parentSample.getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolve(sampleId, inputSampleTypeId);
            
            if (!CollectionUtils.isEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule = new TestingSchedule();
            //如果当前流程被暂停或者取消，相应验证流程也将暂停取消
            schedule.setProccessState(relatedSchedule.getProccessState());
            schedule.setOrderId(relatedSchedule.getOrderId());
            if (null != product)
            {
                schedule.setProductId(product.getId());
            }
            else
            {
                schedule.setProductId(relatedSchedule.getProductId());
            }
            schedule.setMethodId(testingMethod.getId());
            schedule.setSampleId(inputSample.getId());
            schedule.setScheduleNodes(process.toString());
            schedule.setStartTime(new Date());
            schedule.setVerifyKey(sangerVerifyRecord.getId());
            schedule.setVerifyTarget(relatedSchedule.getId());
            testingScheduleMapper.insertSelective(schedule);
            schduleIds.add(schedule.getId());
            orderIds.add(schedule.getOrderId());
            context.setContextForCreateVerifySchedule(verify, technicalRecord);
            
            // 引物合成/设计任务
            if (StringUtils.isEmpty(sangerVerifyRecord.getPrimerId()))
            {
                TestingTask primerTask = context.getPrimerTask(technicalRecord);
                
                TestingTask existPrimerTask = getTestingTaskByChromAndLocation1(technicalRecord.getChromosome(), technicalRecord.getBeginLocus(), "Sanger");
                
                if (null == primerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmAdapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    //引物库没匹配到引物的 还要判断下是否已经存在了相同的引物设计合成任务 用染色体 跟位置1去查询
                    if (null != existPrimerTask)
                    {
                        primerTask = existPrimerTask;
                    }
                    else
                    {
                        primerTask = new TestingTask();
                        primerTask.setName(sangerPrimerPrepareTaskConfig.getName());
                        primerTask.setSemantic(sangerPrimerPrepareTaskConfig.getSemantic());
                        primerTask.setInputSampleId(inputSample.getId());
                        primerTask.setStartTime(new Date());
                        primerTask.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                        primerTask.setResubmit(false);
                        primerTask.setResubmitCount(0);
                        
                        testingTaskMapper.insertSelective(primerTask);
                        
                        PrimerDesignTaskVariables pv = new PrimerDesignTaskVariables();
                        pv.setGene(technicalRecord.getGeneSymbol());
                        pv.setCodingExon(technicalRecord.getExon());
                        pv.setChromosomeLocation(technicalRecord.getChromosomeLocation());
                        pv.setChromosomeNumber(technicalRecord.getChromosome());
                        pv.setBeginLocus(technicalRecord.getBeginLocus());
                        pv.setEndLocus(technicalRecord.getEndLocus());
                        
                        TestingTaskRunVariable variable = new TestingTaskRunVariable();
                        variable.setTaskId(primerTask.getId());
                        variable.setText(JsonUtils.asJson(pv));
                        testingTaskRunVariableMapper.insertSelective(variable);
                        
                    }
                    context.setContextForCreateSangerPrimerPrepareTask(technicalRecord, primerTask);
                }
                
                TestingScheduleActive active = new TestingScheduleActive();
                active.setScheduleId(schedule.getId());
                active.setTaskId(primerTask.getId());
                testingScheduleActiveMapper.insertSelective(active);
                
                if (isNotExist(taskList, primerTask))
                {
                    taskList.add(primerTask);
                }
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(primerTask.getId());
                history.setTaskTimestamp(new Date());
                testingScheduleHistoryMapper.insertSelective(history);
                
                schedule.setActiveTask(primerTask.getName());
                testingScheduleMapper.updateByPrimaryKeySelective(schedule);
                
                //设置加急
                Order order = orderMapper.selectByPrimaryKey(schedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        primerTask.setIfUrgent(order.getIfUrgent());
                        primerTask.setUrgentName(order.getUrgentName());
                        primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        testingTaskMapper.updateByPrimaryKey(primerTask);
                    }
                }
                
            }
            else
            {
                //如果引物库有这个引物而且待下达或者待实验也有这个任务 此时优先选择这个任务
                TestingTask primerTask = context.getPrimerTask(technicalRecord);
                TestingTask existPrimerTask = null;
                if (null == primerTask)
                {
                    existPrimerTask = getTestingTaskByChromAndLocation1(technicalRecord.getChromosome(), technicalRecord.getBeginLocus(), "Sanger");
                    primerTask = existPrimerTask;
                }
                else
                {
                    existPrimerTask = primerTask;
                }
                if (null != existPrimerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmAdapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setScheduleId(schedule.getId());
                    active.setTaskId(primerTask.getId());
                    testingScheduleActiveMapper.insertSelective(active);
                    
                    if (isNotExist(taskList, primerTask))
                    {
                        taskList.add(primerTask);
                    }
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(primerTask.getId());
                    history.setTaskTimestamp(new Date());
                    testingScheduleHistoryMapper.insertSelective(history);
                    
                    schedule.setActiveTask(primerTask.getName());
                    testingScheduleMapper.updateByPrimaryKeySelective(schedule);
                    
                    context.setContextForCreateSangerPrimerPrepareTask(technicalRecord, primerTask);
                    
                    //引物设置为空，因为要重新做这个任务
                    sangerVerifyRecord.setPrimerId(null);
                    sangerVerifyRecordMapper.updateByPrimaryKeySelective(sangerVerifyRecord);
                    
                    Order order = orderMapper.selectByPrimaryKey(schedule.getOrderId());
                    if (null != order.getIfUrgent())
                    {
                        if (1 == order.getIfUrgent())
                        {
                            primerTask.setIfUrgent(order.getIfUrgent());
                            primerTask.setUrgentName(order.getUrgentName());
                            primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                            testingTaskMapper.updateByPrimaryKey(primerTask);
                        }
                    }
                }
            }
            
            //创建任务
            String testingNode = "";
            if (StringUtils.isEmpty(sangerVerifyRecord.getDnaSampleId()))
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            if (!TaskSemantic.PCR_ONE.equals(testingNode) || StringUtils.isNotEmpty(sangerVerifyRecord.getPrimerId()))
            {
                TaskConfig config = context.getTaskConfig(testingNode);
                
                if (null == config)
                {
                    config = bcmAdapter.getTaskConfigBySemantic(testingNode);
                    context.setContextForTaskConfig(testingNode, config);
                }
                
                TestingTask task = context.getSampleTask(inputSample, config);
                
                if (null == task)
                {
                    task = existDNATask(inputSample, config);
                    if (null == task)
                    {
                        task = new TestingTask();
                        task.setName(config.getName());
                        task.setSemantic(config.getSemantic());
                        task.setInputSampleId(inputSample.getId());
                        task.setStartTime(new Date());
                        task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                        task.setResubmit(false);
                        task.setResubmitCount(0);
                        
                        testingTaskMapper.insertSelective(task);
                        
                        TestingTaskRunVariable variable = new TestingTaskRunVariable();
                        variable.setTaskId(task.getId());
                        
                        testingTaskRunVariableMapper.insertSelective(variable);
                        context.setContextForCreateSampleTask(task);
                        
                    }
                    
                }
                
                TestingScheduleActive active = new TestingScheduleActive();
                active.setScheduleId(schedule.getId());
                active.setTaskId(task.getId());
                testingScheduleActiveMapper.insertSelective(active);
                
                //存储冗余字段
                if (isNotExist(taskList, task))
                {
                    taskList.add(task);
                }
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(task.getId());
                history.setTaskTimestamp(new Date());
                testingScheduleHistoryMapper.insertSelective(history);
                
                Order order = orderMapper.selectByPrimaryKey(schedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        task.setIfUrgent(order.getIfUrgent());
                        task.setUrgentName(order.getUrgentName());
                        task.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        testingTaskMapper.updateByPrimaryKey(task);
                    }
                }
                
                String activeTask = schedule.getActiveTask();
                
                if (StringUtils.isNotEmpty(activeTask))
                {
                    activeTask = activeTask + "|" + task.getName();
                }
                else
                {
                    activeTask = task.getName();
                }
                schedule.setActiveTask(activeTask);
                testingScheduleMapper.updateByPrimaryKeySelective(schedule);
                
            }
        }
        
        //QPCR验证
        else if (TestingMethod.QPCR.equals(testingMethod.getSemantic()))
        {
            QpcrVerifyRecord qpcrVerifyRecord = new QpcrVerifyRecord();
            qpcrVerifyRecord.setVerifyRecord(verify.getId());
            
            qpcrVerifyRecord.setCombineCode(getCombineCode(batchNo, verify));
            
            String inputSampleTypeId = testingMethod.getInputSampleType();
            TestingSample inputSample = testingSampleMapper.selectByPrimaryKey(verify.getInputSampleId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (StringUtils.isNotEmpty(inputSample.getParentSampleId()))
                {
                    qpcrVerifyRecord.setDnaSampleId(verify.getInputSampleId());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                qpcrVerifyRecord.setDnaSampleId(verify.getInputSampleId());
                                break;
                            }
                        }
                    }
                }
            }
            
            qpcrVerifyRecordMapper.insertSelective(qpcrVerifyRecord);
            
            // 创建流程
            
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (StringUtils.isEmpty(inputSample.getParentSampleId()))
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                TestingSample parentSample = testingSampleMapper.selectByPrimaryKey(inputSample.getParentSampleId());
                sampleId = parentSample.getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolve(sampleId, inputSampleTypeId);
            
            if (!CollectionUtils.isEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule = new TestingSchedule();
            //如果当前流程被暂停或者取消，相应验证流程也将暂停取消
            schedule.setProccessState(relatedSchedule.getProccessState());
            schedule.setOrderId(relatedSchedule.getOrderId());
            if (null != product)
            {
                schedule.setProductId(product.getId());
            }
            else
            {
                schedule.setProductId(relatedSchedule.getProductId());
            }
            schedule.setMethodId(testingMethod.getId());
            schedule.setSampleId(inputSample.getId());
            schedule.setScheduleNodes(process.toString());
            schedule.setStartTime(new Date());
            schedule.setVerifyKey(qpcrVerifyRecord.getId());
            schedule.setVerifyTarget(relatedSchedule.getId());
            testingScheduleMapper.insertSelective(schedule);
            schduleIds.add(schedule.getId());
            orderIds.add(schedule.getOrderId());
            context.setContextForCreateVerifySchedule(verify, technicalRecord);
            
            //创建任务
            String testingNode = "";
            if (StringUtils.isEmpty(qpcrVerifyRecord.getDnaSampleId()))
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            TaskConfig config = context.getTaskConfig(testingNode);
            if (null == config)
            {
                config = bcmAdapter.getTaskConfigBySemantic(testingNode);
                context.setContextForTaskConfig(testingNode, config);
            }
            
            TestingTask task = context.getSampleTask(inputSample, config);
            
            if (null == task)
            {
                task = existDNATask(inputSample, config);
                if (null == task)
                {
                    task = new TestingTask();
                    task.setName(config.getName());
                    task.setSemantic(config.getSemantic());
                    task.setInputSampleId(inputSample.getId());
                    task.setStartTime(new Date());
                    task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                    task.setResubmit(false);
                    task.setResubmitCount(0);
                    
                    testingTaskMapper.insertSelective(task);
                    
                    TestingTaskRunVariable variable = new TestingTaskRunVariable();
                    variable.setTaskId(task.getId());
                    
                    testingTaskRunVariableMapper.insertSelective(variable);
                    context.setContextForCreateSampleTask(task);
                }
                
            }
            //激活任务最新节点
            
            TestingScheduleActive active = new TestingScheduleActive();
            active.setScheduleId(schedule.getId());
            active.setTaskId(task.getId());
            testingScheduleActiveMapper.insertSelective(active);
            
            //存储冗余字段
            if (isNotExist(taskList, task))
            {
                taskList.add(task);
            }
            
            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(task.getId());
            history.setTaskTimestamp(new Date());
            testingScheduleHistoryMapper.insertSelective(history);
            
            //设置加急
            Order order = orderMapper.selectByPrimaryKey(schedule.getOrderId());
            if (null != order.getIfUrgent())
            {
                if (1 == order.getIfUrgent())
                {
                    task.setIfUrgent(order.getIfUrgent());
                    task.setUrgentName(order.getUrgentName());
                    task.setUrgentUpdateTime(order.getUrgentUpdateTime());
                    testingTaskMapper.updateByPrimaryKey(task);
                }
            }
            
            schedule.setActiveTask(task.getName());
            testingScheduleMapper.updateByPrimaryKeySelective(schedule);
            
        }
        
        //MLPA验证
        else if (TestingMethod.MLPA.equals(testingMethod.getSemantic()))
        {
            MlpaVerifyRecord mlpaVerifyRecord = new MlpaVerifyRecord();
            mlpaVerifyRecord.setVerifyRecord(verify.getId());
            
            mlpaVerifyRecord.setCombineCode(getCombineCode(batchNo, verify));
            
            String inputSampleTypeId = testingMethod.getInputSampleType();
            TestingSample inputSample = testingSampleMapper.selectByPrimaryKey(verify.getInputSampleId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (StringUtils.isNotEmpty(inputSample.getParentSampleId()))
                {
                    mlpaVerifyRecord.setDnaSampleId(verify.getInputSampleId());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                mlpaVerifyRecord.setDnaSampleId(verify.getInputSampleId());
                                break;
                            }
                        }
                    }
                }
            }
            
            mlpaVerifyRecordMapper.insertSelective(mlpaVerifyRecord);
            
            // 创建流程
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (StringUtils.isEmpty(inputSample.getParentSampleId()))
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                TestingSample parentSample = testingSampleMapper.selectByPrimaryKey(inputSample.getParentSampleId());
                sampleId = parentSample.getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolve(sampleId, inputSampleTypeId);
            
            if (!CollectionUtils.isEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule = new TestingSchedule();
            //如果当前流程被暂停或者取消，相应验证流程也将暂停取消
            schedule.setProccessState(relatedSchedule.getProccessState());
            schedule.setOrderId(relatedSchedule.getOrderId());
            if (null != product)
            {
                schedule.setProductId(product.getId());
            }
            else
            {
                schedule.setProductId(relatedSchedule.getProductId());
            }
            schedule.setMethodId(testingMethod.getId());
            schedule.setSampleId(inputSample.getId());
            schedule.setScheduleNodes(process.toString());
            schedule.setStartTime(new Date());
            schedule.setVerifyKey(mlpaVerifyRecord.getId());
            schedule.setVerifyTarget(relatedSchedule.getId());
            testingScheduleMapper.insertSelective(schedule);
            schduleIds.add(schedule.getId());
            orderIds.add(schedule.getOrderId());
            context.setContextForCreateVerifySchedule(verify, technicalRecord);
            //创建任务
            String testingNode = "";
            if (StringUtils.isEmpty(mlpaVerifyRecord.getDnaSampleId()))
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            TaskConfig config = context.getTaskConfig(testingNode);
            if (null == config)
            {
                config = bcmAdapter.getTaskConfigBySemantic(testingNode);
                context.setContextForTaskConfig(testingNode, config);
            }
            
            TestingTask task = context.getSampleTask(inputSample, config);
            
            if (null == task)
            {
                task = existDNATask(inputSample, config);
                if (null == task)
                {
                    task = new TestingTask();
                    task.setName(config.getName());
                    task.setSemantic(config.getSemantic());
                    task.setInputSampleId(inputSample.getId());
                    task.setStartTime(new Date());
                    task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                    task.setResubmit(false);
                    task.setResubmitCount(0);
                    
                    testingTaskMapper.insertSelective(task);
                    
                    TestingTaskRunVariable variable = new TestingTaskRunVariable();
                    variable.setTaskId(task.getId());
                    
                    testingTaskRunVariableMapper.insertSelective(variable);
                    context.setContextForCreateSampleTask(task);
                    
                }
                
            }
            
            TestingScheduleActive active = new TestingScheduleActive();
            active.setScheduleId(schedule.getId());
            active.setTaskId(task.getId());
            testingScheduleActiveMapper.insertSelective(active);
            
            //存储冗余字段
            if (isNotExist(taskList, task))
            {
                taskList.add(task);
            }
            
            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(task.getId());
            history.setTaskTimestamp(new Date());
            testingScheduleHistoryMapper.insertSelective(history);
            
            //设置加急
            Order order = orderMapper.selectByPrimaryKey(schedule.getOrderId());
            if (null != order.getIfUrgent())
            {
                if (1 == order.getIfUrgent())
                {
                    task.setIfUrgent(order.getIfUrgent());
                    task.setUrgentName(order.getUrgentName());
                    task.setUrgentUpdateTime(order.getUrgentUpdateTime());
                    testingTaskMapper.updateByPrimaryKey(task);
                }
            }
            
            schedule.setActiveTask(task.getName());
            testingScheduleMapper.updateByPrimaryKeySelective(schedule);
            
        }
        
        //PCR-NGS验证
        else if (TestingMethod.PCR_NGS.equals(testingMethod.getSemantic()))
        {
            SangerVerifyRecord sangerVerifyRecord = new SangerVerifyRecord();
            sangerVerifyRecord.setVerifyRecord(verify.getId());
            sangerVerifyRecord.setCombineCode(getCombineCode(batchNo, verify));
            
            String inputSampleTypeId = testingMethod.getInputSampleType();
            TestingSample inputSample = testingSampleMapper.selectByPrimaryKey(verify.getInputSampleId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (StringUtils.isNotEmpty(inputSample.getParentSampleId()))
                {
                    sangerVerifyRecord.setDnaSampleId(verify.getInputSampleId());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                sangerVerifyRecord.setDnaSampleId(verify.getInputSampleId());
                                break;
                            }
                        }
                    }
                }
            }
            
            // 获取已设计引物
            Primer primer = getPcrNgsVerifyPrimer(verify);
            if (null != primer)
            {
                sangerVerifyRecord.setPrimerId(primer.getId());
            }
            
            sangerVerifyRecordMapper.insertSelective(sangerVerifyRecord);
            
            // 创建流程
            
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (StringUtils.isEmpty(inputSample.getParentSampleId()))
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                TestingSample parentSample = testingSampleMapper.selectByPrimaryKey(inputSample.getParentSampleId());
                sampleId = parentSample.getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolve(sampleId, inputSampleTypeId);
            
            if (!CollectionUtils.isEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule = new TestingSchedule();
            //如果当前流程被暂停或者取消，相应验证流程也将暂停取消
            schedule.setProccessState(relatedSchedule.getProccessState());
            schedule.setOrderId(relatedSchedule.getOrderId());
            if (null != product)
            {
                schedule.setProductId(product.getId());
            }
            else
            {
                schedule.setProductId(relatedSchedule.getProductId());
            }
            schedule.setMethodId(testingMethod.getId());
            schedule.setSampleId(inputSample.getId());
            schedule.setScheduleNodes(process.toString());
            schedule.setStartTime(new Date());
            schedule.setVerifyKey(sangerVerifyRecord.getId());
            schedule.setVerifyTarget(relatedSchedule.getId());
            testingScheduleMapper.insertSelective(schedule);
            schduleIds.add(schedule.getId());
            orderIds.add(schedule.getOrderId());
            context.setContextForCreateVerifySchedule(verify, technicalRecord);
            
            // 引物合成/设计任务
            if (StringUtils.isEmpty(sangerVerifyRecord.getPrimerId()))
            {
                TestingTask primerTask = context.getPrimerTask(technicalRecord);
                
                TestingTask existPrimerTask = getTestingTaskByChromAndLocation1(technicalRecord.getChromosome(), technicalRecord.getBeginLocus(), "PCR-NGS");
                
                if (null == primerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmAdapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PCR_NGS_PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    //引物库没匹配到引物的 还要判断下是否已经存在了相同的引物设计合成任务 用染色体 跟位置1去查询
                    if (null != existPrimerTask)
                    {
                        primerTask = existPrimerTask;
                    }
                    else
                    {
                        primerTask = new TestingTask();
                        primerTask.setName(sangerPrimerPrepareTaskConfig.getName());
                        primerTask.setSemantic(sangerPrimerPrepareTaskConfig.getSemantic());
                        primerTask.setInputSampleId(inputSample.getId());
                        primerTask.setStartTime(new Date());
                        primerTask.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                        primerTask.setResubmit(false);
                        primerTask.setResubmitCount(0);
                        
                        testingTaskMapper.insertSelective(primerTask);
                        
                        PrimerDesignTaskVariables pv = new PrimerDesignTaskVariables();
                        pv.setGene(technicalRecord.getGeneSymbol());
                        pv.setCodingExon(technicalRecord.getExon());
                        pv.setChromosomeLocation(technicalRecord.getChromosomeLocation());
                        pv.setChromosomeNumber(technicalRecord.getChromosome());
                        pv.setBeginLocus(technicalRecord.getBeginLocus());
                        pv.setEndLocus(technicalRecord.getEndLocus());
                        
                        TestingTaskRunVariable variable = new TestingTaskRunVariable();
                        variable.setTaskId(primerTask.getId());
                        variable.setText(JsonUtils.asJson(pv));
                        testingTaskRunVariableMapper.insertSelective(variable);
                        
                    }
                    context.setContextForCreateSangerPrimerPrepareTask(technicalRecord, primerTask);
                }
                
                TestingScheduleActive active = new TestingScheduleActive();
                active.setScheduleId(schedule.getId());
                active.setTaskId(primerTask.getId());
                testingScheduleActiveMapper.insertSelective(active);
                
                //存储冗余字段
                if (isNotExist(taskList, primerTask))
                {
                    taskList.add(primerTask);
                }
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(primerTask.getId());
                history.setTaskTimestamp(new Date());
                testingScheduleHistoryMapper.insertSelective(history);
                
                //设置加急
                Order order = orderMapper.selectByPrimaryKey(schedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        primerTask.setIfUrgent(order.getIfUrgent());
                        primerTask.setUrgentName(order.getUrgentName());
                        primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        testingTaskMapper.updateByPrimaryKey(primerTask);
                    }
                }
                
                schedule.setActiveTask(primerTask.getName());
                testingScheduleMapper.updateByPrimaryKeySelective(schedule);
                
            }
            else
            {
                //如果引物库有这个引物而且待下达或者待实验也有这个任务 此时优先选择这个任务
                TestingTask primerTask = context.getPrimerTask(technicalRecord);
                TestingTask existPrimerTask = null;
                if (null == primerTask)
                {
                    existPrimerTask = getTestingTaskByChromAndLocation1(technicalRecord.getChromosome(), technicalRecord.getBeginLocus(), "PCR-NGS");
                    primerTask = existPrimerTask;
                }
                else
                {
                    existPrimerTask = primerTask;
                }
                if (null != existPrimerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmAdapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setScheduleId(schedule.getId());
                    active.setTaskId(primerTask.getId());
                    testingScheduleActiveMapper.insertSelective(active);
                    
                    if (isNotExist(taskList, primerTask))
                    {
                        taskList.add(primerTask);
                    }
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(primerTask.getId());
                    history.setTaskTimestamp(new Date());
                    testingScheduleHistoryMapper.insertSelective(history);
                    
                    schedule.setActiveTask(primerTask.getName());
                    testingScheduleMapper.updateByPrimaryKeySelective(schedule);
                    
                    context.setContextForCreateSangerPrimerPrepareTask(technicalRecord, primerTask);
                    
                    //引物设置为空，因为要重新做这个任务
                    sangerVerifyRecord.setPrimerId(null);
                    sangerVerifyRecordMapper.updateByPrimaryKeySelective(sangerVerifyRecord);
                    
                    //设置加急
                    Order order = orderMapper.selectByPrimaryKey(schedule.getOrderId());
                    if (null != order.getIfUrgent())
                    {
                        if (1 == order.getIfUrgent())
                        {
                            primerTask.setIfUrgent(order.getIfUrgent());
                            primerTask.setUrgentName(order.getUrgentName());
                            primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                            testingTaskMapper.updateByPrimaryKey(primerTask);
                            
                        }
                    }
                }
            }
            
            //创建任务
            String testingNode = "";
            if (StringUtils.isEmpty(sangerVerifyRecord.getDnaSampleId()))
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            if (!TaskSemantic.PCR_NGS.equals(testingNode) || StringUtils.isNotEmpty(sangerVerifyRecord.getPrimerId()))
            {
                TaskConfig config = context.getTaskConfig(testingNode);
                
                if (null == config)
                {
                    config = bcmAdapter.getTaskConfigBySemantic(testingNode);
                    context.setContextForTaskConfig(testingNode, config);
                }
                
                TestingTask task = context.getSampleTask(inputSample, config);
                
                if (null == task)
                {
                    task = existDNATask(inputSample, config);
                    if (null == task)
                    {
                        task = new TestingTask();
                        task.setName(config.getName());
                        task.setSemantic(config.getSemantic());
                        task.setInputSampleId(inputSample.getId());
                        task.setStartTime(new Date());
                        task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                        task.setResubmit(false);
                        task.setResubmitCount(0);
                        
                        testingTaskMapper.insertSelective(task);
                        
                        TestingTaskRunVariable variable = new TestingTaskRunVariable();
                        variable.setTaskId(task.getId());
                        
                        testingTaskRunVariableMapper.insertSelective(variable);
                        context.setContextForCreateSampleTask(task);
                        
                    }
                    else
                    {
                        task.setReceivedSampleSamplingTime("");
                        task.setProductReportDeadline("");
                    }
                    
                }
                
                TestingScheduleActive active = new TestingScheduleActive();
                active.setScheduleId(schedule.getId());
                active.setTaskId(task.getId());
                testingScheduleActiveMapper.insertSelective(active);
                
                //存储冗余字段
                if (isNotExist(taskList, task))
                {
                    taskList.add(task);
                }
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(task.getId());
                history.setTaskTimestamp(new Date());
                testingScheduleHistoryMapper.insertSelective(history);
                
                //设置加急
                Order order = orderMapper.selectByPrimaryKey(schedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        task.setIfUrgent(order.getIfUrgent());
                        task.setUrgentName(order.getUrgentName());
                        task.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        testingTaskMapper.updateByPrimaryKey(task);
                    }
                }
                
                String activeTask = schedule.getActiveTask();
                
                if (StringUtils.isNotEmpty(activeTask))
                {
                    activeTask = activeTask + "|" + task.getName();
                }
                else
                {
                    activeTask = task.getName();
                }
                schedule.setActiveTask(activeTask);
                testingScheduleMapper.updateByPrimaryKeySelective(schedule);
            }
        }
        testingScheduleService.sendOrderVerifyTestingStartMessage(schduleIds, orderIds);
    } //assignVerifyProcess 方法
    
    private Primer getPcrNgsVerifyPrimer(TechnicalAnalyVerify verify)
    {
        TestingMethod testingMethod = getTestingMethodBySemantic(TestingMethod.PCR_NGS);
        CheckPrimerForDesignRequest request = new CheckPrimerForDesignRequest();
        TechnicalAnalyRecord technicalAnalyRecord = technicalAnalyRecordMapper.selectRecordByPrimaryKey(verify.getRecordId());
        request.setChromosomeNumber(technicalAnalyRecord.getChromosome());
        request.setPcrPoint(technicalAnalyRecord.getLocation1());
        request.setApplicationType(testingMethod.getId());//sanger验证id
        List<Primer> list = bsmAdapter.getPcrNgsList(request);
        if (Collections3.isEmpty(list))
        {
            return null;
        }
        Primer primer = new Primer();
        primer.setId(Collections3.getFirst(list).getId());
        return primer;
    }
    
    private TestingTask existDNATask(TestingSample inputSample, TaskConfig config)
    {
        TestingTask result = null;
        if (TaskSemantic.DNA_EXTRACT.equals(config.getSemantic()) || TaskSemantic.DNA_QC.equals(config.getSemantic()))
        {
            List<TestingTask> dnaTasks = getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_EXTRACT);
            List<TestingTask> qcTasks = getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
            if (Collections3.isNotEmpty(dnaTasks))
            {
                for (TestingTask dnaTask : dnaTasks)
                {
                    if (dnaTask.getStatus().equals(TestingTask.STATUS_ASSIGNABLE) || dnaTask.getStatus().equals(TestingTask.STATUS_ASSIGNING))
                    {
                        result = dnaTask;
                        return result;
                    }
                }
            }
            else if (Collections3.isNotEmpty(qcTasks))
            {
                for (TestingTask qcTask : qcTasks)
                {
                    if (qcTask.getStatus().equals(TestingTask.STATUS_ASSIGNABLE) || qcTask.getStatus().equals(TestingTask.STATUS_ASSIGNING))
                    {
                        result = qcTask;
                        return result;
                    }
                }
                
            }
            return result;
        }
        else
        {
            return result;
        }
    }
    
    private boolean isNotExist(List<TestingTask> taskList, TestingTask primerTask)
    {
        for (TestingTask temp : taskList)
        {
            if (temp.getId().equals(primerTask.getId()))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 此方法默认是取test_task表数据，暂不去取另外几个task表数据    找不到testingtask默认返回NULL
     * @param chromosome
     * @param beginLocus
     * @param method
     * @return
     */
    private TestingTask getTestingTaskByChromAndLocation1(String chromosome, String beginLocus, String method)
    {
        TestingTask testingTask;
        
        List<SangerVerifyRecord> sangerVerifyRecords = sangerVerifyRecordMapper.selectByRecord(chromosome, beginLocus, method);
        
        if (Collections3.isEmpty(sangerVerifyRecords))
        {
            return null;
        }
        
        String taskSemantic = "";
        if ("Sanger".equals(method))
        {
            taskSemantic = TaskSemantic.PRIMER_DESIGN;
        }
        else
        {
            taskSemantic = TaskSemantic.PCR_NGS_PRIMER_DESIGN;
        }
        for (SangerVerifyRecord temp : sangerVerifyRecords)
        {
            testingTask = getTestingTaskActiveByVerifyKey(temp.getId(), taskSemantic);
            
            return testingTask;
            
        }
        
        return null;
    }
    
    private TestingTask getTestingTaskActiveByVerifyKey(String id, String taskSemantic)
    {
        TestingSchedule searcher = new TestingSchedule();
        searcher.setVerifyKey(id);
        List<TestingSchedule> schedules = testingScheduleMapper.selectBySearcher(searcher);
        if (Collections3.isNotEmpty(schedules))
        {
            TestingSchedule schedule = Collections3.getFirst(schedules);
            
            TestingScheduleActive activeSearcher = new TestingScheduleActive();
            activeSearcher.setScheduleId(schedule.getId());
            List<TestingScheduleActive> actives = testingScheduleActiveMapper.selectBySearcher(activeSearcher);
            
            if (Collections3.isEmpty(actives))
            {
                return null;
            }
            for (TestingScheduleActive active : actives)
            {
                if (StringUtils.isEmpty(active.getTaskRefer()))
                {
                    TestingTask task = testingTaskMapper.selectByPrimaryKey(active.getTaskId());
                    if (taskSemantic.equals(task.getSemantic()))
                    {
                        return task;
                    }
                }
                
            }
            return null;
        }
        return null;
        
    }
    
    private List<TestingNode> resolve(String sampleId, String inputSampleTypeId)
    {
        return bcmAdapter.getTestingNodes(sampleId, inputSampleTypeId);
    }
    
    private Primer getSangerVerifyPrimer(TechnicalAnalyVerify verify)
    {
        TestingMethod testingMethod = getTestingMethodBySemantic(TestingMethod.SANGER);
        CheckPrimerForDesignRequest request = new CheckPrimerForDesignRequest();
        TechnicalAnalyRecord technicalAnalyRecord = technicalAnalyRecordMapper.selectRecordByPrimaryKey(verify.getRecordId());
        request.setChromosomeNumber(technicalAnalyRecord.getChromosome());
        request.setPcrPoint(technicalAnalyRecord.getLocation1());
        request.setApplicationType(testingMethod.getId());//sanger验证id
        List<Primer> list = bsmAdapter.getList(request);
        if (Collections3.isEmpty(list))
        {
            return null;
        }
        Primer primer = new Primer();
        primer.setId(Collections3.getFirst(list).getId());
        return primer;
    }
    
    private TestingMethod getTestingMethodBySemantic(String sanger)
    {
        TestingMethod searcher = new TestingMethod();
        searcher.setSemantic(sanger);
        List<TestingMethod> methods = testingMethodMapper.selectBySearcher(searcher);
        return Collections3.getFirst(methods);
    }
    
    private String getCombineCode(String batchNo, TechnicalAnalyVerify verify)
    {
        String combineCode = "";
        String sampleCode = "";
        
        TestingSample inputSample = testingSampleMapper.selectByPrimaryKey(verify.getInputSampleId());
        ReceivedSample receivedSample = receivedSampleMapper.selectByPrimaryKey(inputSample.getOriginalSampleId());
        if (null != receivedSample)
        {
            sampleCode = receivedSample.getSampleCode();
        }
        TechnicalAnalyRecord analyRecord = technicalAnalyRecordMapper.selectRecordByPrimaryKey(verify.getRecordId());
        if (null != verify && null != analyRecord)
        {
            combineCode = batchNo + "_" + sampleCode + "_" + analyRecord.getGeneSymbol();
            if (StringUtils.isNotEmpty(analyRecord.getChromosomeLocation()))
            {
                combineCode = combineCode + "-" + analyRecord.getChromosomeLocation();
            }
        }
        return combineCode;
    }
    
    private List<TestingTask> getRelatedTasks(String sampleId, String semantic)
    {
        TestingTask searcher = new TestingTask();
        searcher.setInputSampleId(sampleId);
        searcher.setSemantic(semantic);
        List<TestingTask> tasks = testingTaskMapper.selectBySearcher(searcher);
        return tasks;
    }
    
    private Product getProductByDataCode(String dataCode)
    {
        if (StringUtils.isEmpty(dataCode))
        {
            return null;
        }
        String arr[] = StringUtils.split(dataCode, "_");
        if (arr == null || arr.length <= 2 || arr.length != 3)
        {
            throw new IllegalStateException();
        }
        String productNo = arr[1];
        Product product = productMapper.selectByCode(productNo);
        return product;
    }
    
    @Override
    @Transactional
    public Integer cancelMutationCollection(MutationCollectionRequest request)
    {
        // technicalAnalyRecordMapper.delByMutationObjId(request.getMutationId());
        List<TechnicalAnalyRecord> result = technicalAnalyRecordMapper.selectByCondition(request);
        if (Collections3.isNotEmpty(result))
        {
            for (TechnicalAnalyRecord data : result)
            {
                technicalAnalyRecordMapper.deleteByPrimaryKey(data.getId());
                List<TechnicalAnalyVerify> verifys = technicalAnalyVerifyMapper.selectByRecord(data.getId());
                if (Collections3.isNotEmpty(verifys))
                {
                    for (TechnicalAnalyVerify technicalAnalyVerify : verifys)
                    {
                        technicalAnalyVerifyMapper.deleteByPrimaryKey(technicalAnalyVerify.getId());
                    }
                }
            }
            
        }
        else
        {
            logger.warn("根据条件查询不出收藏对象！");
        }
        return 1;
    }
    
    @Override
    @Transactional
    public void submitRecheckTechnicalTask(SubmitTechnicalTaskRequest request)
    {
        if (StringUtils.isEmpty(request.getFamilyGroupId()) || StringUtils.isEmpty(request.getStatus()))
        {
            System.err.println("提交复核缺少元素");
        }
        TechnicalAnalysisTask searcher = new TechnicalAnalysisTask();
        searcher.setFamilyGroupId(request.getFamilyGroupId());
        List<TechnicalAnalysisTask> tasks = technicalAnalysisTaskMapper.selectBySearcher(searcher);
        if (Collections3.isNotEmpty(tasks))
        {
            for (TechnicalAnalysisTask task : tasks)
            {
                task.setStatus(Integer.parseInt(request.getStatus()));
                task.setNote(request.getNote());
                if ("6".equals(request.getStatus()))
                {
                    task.setResubmit(1);
                    task.setResubmitCount(task.getResubmitCount() + 1);
                }
                technicalAnalysisTaskMapper.updateByPrimaryKeySelective(task);
            }
        }
        
    }
    
    @Override
    public Integer myCollectionCount(String analysisSampleId, String userId, String technicalTaskId)
    {
        Integer res = 0;
        // 突变收藏个数
        List<String> result = technicalAnalyRecordMapper.myCollectionCount(analysisSampleId, technicalTaskId);
        res = res + result.size();
        return res;
    }
    
    @Override
    public DataColAndMutationDataModel searchMutationListByAnalsysiSampleId(String analysisSampleId)
    {
        Boolean isFamily = dataAnalysisFacade.ifFamilyAnalysis(analysisSampleId);
        // 获取列头
        DataColAndMutationDataModel model = new DataColAndMutationDataModel();
        List<DataTemplateColumn> dataColList = new ArrayList<>();
        
        String templateId = "";
        List<ClaimTemplate> claimList = this.selectForPriFlag();
        if (null != claimList && claimList.size() > 0)
        {
            ClaimTemplate ct = claimList.get(0);
            dataColList = dataTemplateService.getColumn(ct.getTemplateId());
            templateId = ct.getTemplateId();
        }
        List<String> groupNameList = new ArrayList<String>();
        groupNameList.addAll(Arrays.asList("是否家系实验分析", "家系实验方法", "验证方案", "临床相关性判断"));
        List<String> removceGroup = Lists.newArrayList("HGMD", "之父测序深度", "之母测序深度", "之父基因型", "之母基因型");
        if (null != dataColList)
        {
            for (DataTemplateColumn col : dataColList)
            {
                if (!groupNameList.contains(col.getGroupName()))
                {
                    groupNameList.add(col.getGroupName());
                }
            }
            if (!isFamily)
            { //非家系
                groupNameList.removeAll(removceGroup);
            }
        }
        model.setColList(groupNameList);
        
        // 查询mongo
        
        List<TechnicalAnalyRecord> records = technicalAnalyRecordMapper.selectByFamilyId(analysisSampleId);
        List<DBObject> pager = new ArrayList<>();
        BasicDBObject queryCondition = new BasicDBObject();
        
        DBCollection mutationCollection = template.getCollection("analysis-mutation");
        for (TechnicalAnalyRecord record : records)
        {
            BasicDBList values = new BasicDBList();
            if (StringUtils.isEmpty(record.getMutationObjectId()))
            {
                continue;
            }
            values.add(new ObjectId(record.getMutationObjectId()));
            queryCondition.put("_id", new BasicDBObject("$in", values));
            
            BasicDBObject paSortObj = new BasicDBObject("pa_sort", 1);
            Iterator<DBObject> mutationIterator = mutationCollection.find(queryCondition).sort(paSortObj).iterator();
            while (mutationIterator.hasNext())
            {
                pager.add(mutationIterator.next());
            }
        }
        pager = sortMutationCollection(pager);
        MutationFilterToWarpDataModel dataModel = new MutationFilterToWarpDataModel();
        dataModel.setTemplateId(templateId);
        warpMutationData(null, model, dataModel, pager, pager.size());
        Set<String> recordIdSet = new HashSet<>();
        // 设置额外列头
        MutationCollectionRequest searcher = new MutationCollectionRequest();
        for (Map<String, String> map : model.getDataValue())
        {
            String objectId = map.get("_id").toString();
            searcher.setAnalysisSampleId(analysisSampleId);
            searcher.setMutationId(objectId);
            List<TechnicalAnalyRecord> analyRecords = technicalAnalyRecordMapper.selectByCondition(searcher);
            // List<TechnicalAnalyRecord> analyRecords = technicalAnalyRecordMapper.selectByMutationId(objectId); // bug  需要精确查询到任务下面
            if (Collections3.isNotEmpty(analyRecords))
            {
                for (TechnicalAnalyRecord record : analyRecords)
                {
                    if (!recordIdSet.contains(record.getId()))
                    {
                        map.put("recordId", record.getId());
                        recordIdSet.add(record.getId());
                        map.put("是否家系实验分析", record.getVerify());
                        map.put("家系实验方法", record.getVerifyMethod());
                        map.put("临床相关性判断", record.getClinicalJudgment());
                        List<TechnicalAnalyVerify> verifys = technicalAnalyVerifyMapper.selectByRecord(record.getId());
                        List<String> familyList = new ArrayList<>();
                        verifys.forEach((TechnicalAnalyVerify verify) -> {
                            familyList.add(verify.getSampleFamilyRelation());
                        });
                        map.put("验证方案", Joiner.on(",").join(familyList));
                        break;
                    }
                }
                
            }
        }
        return model;
    }
    
    private List<DBObject> sortMutationCollection(List<DBObject> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            for (int j = list.size() - 1; j > i; j--)
            {
                Integer no = (int)list.get(j).get("pa_sort");
                Integer no_1 = (int)list.get(j - 1).get("pa_sort");
                if (no.compareTo(no_1) < 0)
                {
                    //互换位置
                    DBObject stu = list.get(j);
                    list.set(j, list.get(j - 1));
                    list.set(j - 1, stu);
                }
            }
        }
        return list;
    }
    
    @Override
    public List<ReportExportMutationInfo> searchReportExportMutationInfoListByAnalsysiSampleId(ReportExportMutationInfoRequest request)
    {
        List<ReportExportMutationInfo> reportExportMutationInfos = new ArrayList<ReportExportMutationInfo>();
        // 查询mongo
        Map<String, Object> params = new HashMap<String, Object>();
        //params.put("technicalFamilyGroupId", request.getTechnicalFamilyGroupId());
        params.put("ids", request.getRecordIds());
        if (request.getRecordIds() != null && request.getRecordIds().size() > 0)
        {
            List<TechnicalAnalyRecord> records = technicalAnalyRecordMapper.selectByFamilyIdAndIds(params);
            List<DBObject> pager = new ArrayList<>();
            BasicDBObject queryCondition = new BasicDBObject();
            BasicDBList values = new BasicDBList();
            Map<String, TechnicalAnalyRecord> mutationObjectIdToTechnicalAnalyRecord = new HashMap<String, TechnicalAnalyRecord>();
            for (TechnicalAnalyRecord record : records)
            {
                if (record.getMutationObjectId() != null)
                {
                    values.add(new ObjectId(record.getMutationObjectId()));
                    mutationObjectIdToTechnicalAnalyRecord.put(record.getMutationObjectId(), record);
                }
            }
            queryCondition.put("_id", new BasicDBObject("$in", values));
            
            DBCollection mutationCollection = template.getCollection("analysis-mutation");
            BasicDBObject paSortObj = new BasicDBObject("pa_sort", 1);
            Iterator<DBObject> mutationIterator = mutationCollection.find(queryCondition).sort(paSortObj).iterator();
            while (mutationIterator.hasNext())
            {
                pager.add(mutationIterator.next());
            }
            warpMutationData(reportExportMutationInfos, pager, mutationObjectIdToTechnicalAnalyRecord); //mutationObjectIdToTechnicalAnalyRecord目的是设置临床相关性判断字段  
        }
        return reportExportMutationInfos;
    }
    
    private void warpMutationData(List<ReportExportMutationInfo> reportExportMutationInfos, List<DBObject> dbList, Map<String, TechnicalAnalyRecord> mutationObjectIdToTechnicalAnalyRecord)
    {
        for (DBObject obj2 : dbList)
        {
            ReportExportMutationInfo reportExportMutationInfo = new ReportExportMutationInfo();
            ReportExportMutationInfoExplain reportExportMutationInfoExplain = new ReportExportMutationInfoExplain();
            reportExportMutationInfo.setReportExportMutationInfoExplain(reportExportMutationInfoExplain);
            List<ReportExportDiseaseInfo> reportExportDiseaseInfos = new ArrayList<ReportExportDiseaseInfo>();
            reportExportMutationInfoExplain.setDiseaseInfos(reportExportDiseaseInfos);
            
            List<ReportExportEvidenceInfo> evidenceInfos = new ArrayList<ReportExportEvidenceInfo>();
            reportExportMutationInfoExplain.setEvidenceInfos(evidenceInfos);
            
            ObjectId objectId = (ObjectId)obj2.get("_id");
            reportExportMutationInfo.setObjectId(objectId.toString() == null ? "" : objectId.toString());
            if (null != objectId && mutationObjectIdToTechnicalAnalyRecord.get(objectId.toString()) != null)
            {
                reportExportMutationInfo.setClinicalJudgment(mutationObjectIdToTechnicalAnalyRecord.get(objectId.toString()).getClinicalJudgment());
            }
            reportExportMutationInfos.add(reportExportMutationInfo);
            reportExportMutationInfo.setGeneSymbol(obj2.get("Gene_Symbol") == null ? "" : obj2.get("Gene_Symbol").toString());
            reportExportMutationInfo.setChromosomalLocation(obj2.get("ID") == null ? "" : obj2.get("ID").toString());
            reportExportMutationInfo.setExon("");
            reportExportMutationInfo.setNucleotideChanges("");
            reportExportMutationInfo.setAminoAcidChanges("");
            if (null != obj2.get("AA_change"))
            {
                Map<String, Object> m = (Map<String, Object>)obj2.get("AA_change");
                if (null != m)
                {
                    reportExportMutationInfo.setExon(m.get("Exon") == null ? "" : m.get("Exon").toString());
                    reportExportMutationInfo.setNucleotideChanges(m.get("Nucleotide_Changes") == null ? "" : m.get("Nucleotide_Changes").toString());
                    reportExportMutationInfo.setAminoAcidChanges(m.get("Amino_Acid_Changes") == null ? "" : m.get("Amino_Acid_Changes").toString());
                }
            }
            reportExportMutationInfo.setGeneType(obj2.get("Gene_Type") == null ? "" : obj2.get("Gene_Type").toString());
            reportExportMutationInfo.setHighestMafName("");
            if (null != obj2.get("Highest-MAF"))
            {
                Map<String, Object> m = (Map<String, Object>)obj2.get("Highest-MAF");
                reportExportMutationInfo.setHighestMafName(m.get("name") == null ? "" : m.get("name").toString());
            }
            reportExportMutationInfo.setEffect(obj2.get("Effect") == null ? "" : obj2.get("Effect").toString());
            reportExportMutationInfo.setPathogenicAnalysis(obj2.get("Pathogenic_Analysis") == null ? "" : obj2.get("Pathogenic_Analysis").toString());
            
            if (null != obj2.get("DiseaseTotal"))
            {
                Map<String, Object> m = (Map<String, Object>)obj2.get("DiseaseTotal");
                reportExportMutationInfo.setInhert(m.get("Inhert") == null ? "" : m.get("Inhert").toString());
                reportExportMutationInfo.setDiseasePhenotype(m.get("Disease") == null ? "" : m.get("Disease").toString());
                
                if (m.get("Inhert") != null && m.get("DiseasePhenotype") != null && m.get("DiseaseInformation") != null && m.get("Disease") != null)
                {
                    String[] inhertArray = m.get("Inhert").toString().split("\\,");
                    String[] diseasePhenotypeArray = m.get("DiseasePhenotype").toString().split("\\,");
                    String[] diseaseInformationArray = m.get("DiseaseInformation").toString().split("\\,");
                    String[] diseaseArray = m.get("Disease").toString().split("\\,");
                    if (inhertArray.length == diseasePhenotypeArray.length && inhertArray.length == diseaseInformationArray.length
                        && inhertArray.length == diseaseArray.length)
                    {
                        for (int i = 0; i <= inhertArray.length - 1; i++)
                        {
                            ReportExportDiseaseInfo reportExportDiseaseInfo = new ReportExportDiseaseInfo();
                            reportExportDiseaseInfos.add(reportExportDiseaseInfo);
                            reportExportDiseaseInfo.setInhert(inhertArray[i]);
                            reportExportDiseaseInfo.setDiseasePhenotype(diseasePhenotypeArray[i]);
                            reportExportDiseaseInfo.setDiseaseInformation(diseaseInformationArray[i]);
                            reportExportDiseaseInfo.setDisease(diseaseArray[i]);
                        }
                    }
                    else
                    {
                        ReportExportDiseaseInfo reportExportDiseaseInfo = new ReportExportDiseaseInfo();
                        reportExportDiseaseInfo.setInhert(m.get("Inhert").toString());
                        reportExportDiseaseInfo.setDiseasePhenotype(m.get("DiseasePhenotype").toString());
                        reportExportDiseaseInfo.setDiseaseInformation(m.get("DiseaseInformation").toString());
                        reportExportDiseaseInfo.setDisease(m.get("Disease").toString());
                        reportExportDiseaseInfos.add(reportExportDiseaseInfo);
                    }
                }
                else
                {
                    ReportExportDiseaseInfo reportExportDiseaseInfo = new ReportExportDiseaseInfo();
                    reportExportDiseaseInfo.setInhert(m.get("Inhert") == null ? "" : m.get("Inhert").toString());
                    reportExportDiseaseInfo.setDiseasePhenotype(m.get("DiseasePhenotype") == null ? "" : m.get("DiseasePhenotype").toString());
                    reportExportDiseaseInfo.setDiseaseInformation(m.get("DiseaseInformation") == null ? "" : m.get("DiseaseInformation").toString());
                    reportExportDiseaseInfo.setDisease(m.get("Disease") == null ? "" : m.get("Disease").toString());
                    reportExportDiseaseInfos.add(reportExportDiseaseInfo);
                }
                
            }
            
            reportExportMutationInfo.setInhertSourceName("");
            if (null != obj2.get("遗传来源"))
            {
                Map<String, Object> m = (Map<String, Object>)obj2.get("遗传来源");
                reportExportMutationInfo.setInhertSourceName(m.get("name") == null ? "" : m.get("name").toString());
            }
            
            reportExportMutationInfoExplain.setObjectId(reportExportMutationInfo.getObjectId());
            reportExportMutationInfoExplain.setGeneSymbol(reportExportMutationInfo.getGeneSymbol());
            if (StringUtils.isNotBlank(reportExportMutationInfo.getGeneType()))
            {
                if (reportExportMutationInfo.getGeneType().equals("het"))
                {
                    reportExportMutationInfoExplain.setGeneType("杂合突变");
                }
                else if (reportExportMutationInfo.getGeneType().equals("hom"))
                {
                    reportExportMutationInfoExplain.setGeneType("纯合突变");
                }
                else if (reportExportMutationInfo.getGeneType().equals("hemi"))
                {
                    reportExportMutationInfoExplain.setGeneType("半合子突变");
                }
                else
                {
                    reportExportMutationInfoExplain.setGeneType(reportExportMutationInfo.getGeneType());
                }
            }
            if (obj2.get("Pathogenic_Analysis") != null)
            {
                String pathogenic = obj2.get("Pathogenic_Analysis") == null ? "" : obj2.get("Pathogenic_Analysis").toString();
                if (pathogenic.equals("Pathogenic"))
                {
                    reportExportMutationInfoExplain.setPathogenicAnalysis("致病性变异(Pathogenic)");
                }
                else if (pathogenic.equals("Likely pathogenic"))
                {
                    reportExportMutationInfoExplain.setPathogenicAnalysis("疑似致病性变异(Likely pathogenic)");
                }
                else if (pathogenic.equals("Uncertain"))
                {
                    reportExportMutationInfoExplain.setPathogenicAnalysis("临床意义未明变异(Uncertain)");
                }
                else
                {
                    reportExportMutationInfoExplain.setPathogenicAnalysis(pathogenic);
                }
            }
            if (null != obj2.get("Tag"))
            {
                Map<String, Object> m = (Map<String, Object>)obj2.get("Tag");
                reportExportMutationInfoExplain.setTagPublication(m.get("report_disease") == null ? "" : m.get("report_disease").toString());
                reportExportMutationInfoExplain.setTagReportDisease(m.get("Publication") == null ? "" : m.get("Publication").toString());
            }
            if (null != obj2.get("Mygeno_InterACMG"))
            {
                Map<String, Object> m = (Map<String, Object>)obj2.get("Mygeno_InterACMG");
                if (null != m.get("name"))
                {
                    String evidenceNames = m.get("name") == null ? "" : m.get("name").toString();
                    List<String> evidenceNameList = Arrays.asList(evidenceNames.split("\\;"));
                    for (String evidenceName : evidenceNameList)
                    {
                        ReportExportEvidenceInfo evidenceInfo = new ReportExportEvidenceInfo();
                        evidenceInfos.add(evidenceInfo);
                        evidenceInfo.setName(evidenceName);
                        Map<String, Object> evidenceNote = (Map<String, Object>)m.get(evidenceName);
                        if (evidenceNote != null)
                        {
                            evidenceInfo.setDecription(evidenceNote.get("note") == null ? "" : evidenceNote.get("note").toString());
                        }
                    }
                }
            }
        }
    }
    
    @Override
    @Transactional
    public void deleteVerifyDataById(String id)
    {
        addVerifyMapper.deleteByPrimaryKey(id);
    }
    
    @Override
    public List<ReportExportMutationInfo> searchReportExportMutationInfoListByObjectIds(List<String> objectIds)
    {
        List<ReportExportMutationInfo> reportExportMutationInfos = new ArrayList<ReportExportMutationInfo>();
        // 查询mongo
        List<DBObject> pager = new ArrayList<>();
        BasicDBObject queryCondition = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        Map<String, TechnicalAnalyRecord> mutationObjectIdToTechnicalAnalyRecord = new HashMap<String, TechnicalAnalyRecord>();
        for (String objectId : objectIds)
        {
            values.add(new ObjectId(objectId));
        }
        
        queryCondition.put("_id", new BasicDBObject("$in", values));
        
        DBCollection mutationCollection = template.getCollection("analysis-mutation");
        BasicDBObject paSortObj = new BasicDBObject("pa_sort", 1);
        Iterator<DBObject> mutationIterator = mutationCollection.find(queryCondition).sort(paSortObj).iterator();
        while (mutationIterator.hasNext())
        {
            pager.add(mutationIterator.next());
        }
        warpMutationData(reportExportMutationInfos, pager, mutationObjectIdToTechnicalAnalyRecord); //mutationObjectIdToTechnicalAnalyRecord目的是设置临床相关性判断字段
        return reportExportMutationInfos;
    }
    
}
