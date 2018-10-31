package com.todaysoft.lims.technical.facade;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.todaysoft.lims.technical.adapter.bpm.TestingAdapter;
import com.todaysoft.lims.technical.adapter.smm.SMMAdapter;
import com.todaysoft.lims.technical.entity.CapCnvData;
import com.todaysoft.lims.technical.model.BiologyAnalysisSearchSheet;
import com.todaysoft.lims.technical.model.TechnicalAnalysisAssignableTaskSearcher;
import com.todaysoft.lims.technical.model.TechnicalAnalysisSheetModel;
import com.todaysoft.lims.technical.model.TechnicalAnalysisTaskFailOperate;
import com.todaysoft.lims.technical.model.TestingSheetRequest;
import com.todaysoft.lims.technical.model.TestingTaskModel;
import com.todaysoft.lims.technical.model.UserMinimalModel;
import com.todaysoft.lims.technical.model.request.CancelCollectionCnvAnalysisResult;
import com.todaysoft.lims.technical.model.request.CapCnvAnalysisSampleBamRequest;
import com.todaysoft.lims.technical.model.request.CapCnvDrawPicRequest;
import com.todaysoft.lims.technical.model.request.CapCnvPictureGeneData;
import com.todaysoft.lims.technical.model.request.CapCnvPictureRequest;
import com.todaysoft.lims.technical.model.request.CapCnvRequest;
import com.todaysoft.lims.technical.model.request.CapCnvResultData;
import com.todaysoft.lims.technical.model.request.CollectionCapcnvPicRequest;
import com.todaysoft.lims.technical.model.request.CompareSampleReanalysisRequest;
import com.todaysoft.lims.technical.model.request.CompareSampleRequest;
import com.todaysoft.lims.technical.model.request.ReportCollectionCapcnvDataRequest;
import com.todaysoft.lims.technical.model.request.SubmitCnvVerifyRequest;
import com.todaysoft.lims.technical.model.request.TechnicalReportRequest;
import com.todaysoft.lims.technical.model.request.UploadEvidenceRequest;
import com.todaysoft.lims.technical.model.response.CapCnvDataResponse;
import com.todaysoft.lims.technical.model.response.CnvAnalysisDataModel;
import com.todaysoft.lims.technical.model.response.CnvAnalysisFamilyDataModel;
import com.todaysoft.lims.technical.model.response.CnvAnalysisPicResultList;
import com.todaysoft.lims.technical.model.response.CollectionCnvAnalysisPicResultList;
import com.todaysoft.lims.technical.model.response.DrawPictureModel;
import com.todaysoft.lims.technical.model.response.ListResponse;
import com.todaysoft.lims.technical.model.response.MutationStatisticsModel;
import com.todaysoft.lims.technical.model.response.UploadEvidenceRespModel;
import com.todaysoft.lims.technical.model.searcher.MutationStatisticsSearcher;
import com.todaysoft.lims.technical.mybatis.BioInfoAnnotateHpoMonitorMapper;
import com.todaysoft.lims.technical.mybatis.BioInfoAnnotateMapper;
import com.todaysoft.lims.technical.mybatis.BiologyDivisionFastqDataMapper;
import com.todaysoft.lims.technical.mybatis.BiologyFamilyAnalysisMapper;
import com.todaysoft.lims.technical.mybatis.BiologyFamilyAnalysisSampleRelationMapper;
import com.todaysoft.lims.technical.mybatis.CnvAnalysisMonitorMapper;
import com.todaysoft.lims.technical.mybatis.CnvAnalysisPicMapper;
import com.todaysoft.lims.technical.mybatis.CnvAnalysisResultMapper;
import com.todaysoft.lims.technical.mybatis.OrderSampleDetailMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalyRecordMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalyVerifyMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalysisSheetMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalysisTaskFailOperateMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalysisTaskMapper;
import com.todaysoft.lims.technical.mybatis.TestingScheduleActiveMapper;
import com.todaysoft.lims.technical.mybatis.TestingScheduleHistoryMapper;
import com.todaysoft.lims.technical.mybatis.TestingTaskMapper;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotate;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotateHpoMonitor;
import com.todaysoft.lims.technical.mybatis.entity.BiologyDivisionFastqData;
import com.todaysoft.lims.technical.mybatis.entity.BiologyFamilyAnalysis;
import com.todaysoft.lims.technical.mybatis.entity.BiologyFamilyAnalysisSampleRelation;
import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisMonitor;
import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisPic;
import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisResult;
import com.todaysoft.lims.technical.mybatis.entity.CollectionCnvAnalysisResult;
import com.todaysoft.lims.technical.mybatis.entity.CompareSample;
import com.todaysoft.lims.technical.mybatis.entity.SampleSex;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyRecord;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyRecordWithBLOBs;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyVerify;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisSheet;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.technical.mybatis.parameter.CompareSampleSearch;
import com.todaysoft.lims.technical.mybatis.parameter.FamilyAnalysisSearcher;
import com.todaysoft.lims.technical.service.IBioInfoAnnotateService;
import com.todaysoft.lims.technical.service.IDataAnalysisService;
import com.todaysoft.lims.technical.util.BeanUtil;
import com.todaysoft.lims.technical.utils.Collections3;
import com.todaysoft.lims.technical.utils.HttpRequestAPI;
import com.todaysoft.lims.technical.utils.IdGen;
import com.todaysoft.lims.technical.utils.JsonUtils;
import com.todaysoft.lims.technical.utils.MongoObjectUtil;
import com.todaysoft.lims.technical.utils.Pager;
import com.todaysoft.lims.technical.utils.Pagination;

@Component
public class DataAnalysisFacade
{
    static final String TECHNICAL_ANALYSIS_SEMANTIC = "TECHNICAL_ANALYSIS";
    
    static final String TECHNICAL_ANALYSIS_NAME = "新技术分析";
    
    @Autowired
    private MongoTemplate template;
    
    @Autowired
    private CnvAnalysisMonitorMapper cnvAnalysisMonitorMapper;
    
    @Autowired
    private CnvAnalysisPicMapper cnvAnalysisPicMapper;
    
    @Autowired
    private CnvAnalysisResultMapper cnvAnalysisResultMapper;
    
    @Autowired
    private TechnicalAnalysisTaskMapper technicalAnalysisTaskMapper;
    
    @Autowired
    private BioInfoAnnotateHpoMonitorMapper bioInfoAnnotateHpoMonitorMapper;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private IDataAnalysisService service;
    
    @Autowired
    private BioInfoAnnotateMapper bioInfoAnnotateMapper;
    
    @Autowired
    private IBioInfoAnnotateService bioInfoAnnotateService;
    
    @Autowired
    private BiologyDivisionFastqDataMapper biologyDivisionFastqDataMapper;
    
    @Autowired
    private TechnicalAnalyRecordMapper technicalAnalyRecordMapper;
    
    @Autowired
    private TechnicalAnalyVerifyMapper technicalAnalyVerifyMapper;
    
    @Autowired
    private BiologyFamilyAnalysisMapper biologyFamilyAnalysisMapper;
    
    @Autowired
    private OrderSampleDetailMapper orderSampleDetailMapper;
    
    @Autowired
    private BiologyFamilyAnalysisSampleRelationMapper biologyFamilyAnalysisSampleRelationMapper;
    
    @Autowired
    private TestingScheduleActiveMapper testingScheduleActiveMapper;
    
    @Autowired
    private TestingScheduleHistoryMapper testingScheduleHistoryMapper;
    
    @Autowired
    private TestingTaskMapper testingTaskMapper;
    
    @Autowired
    private TechnicalAnalysisTaskFailOperateMapper failOperateMapper;
    
    @Autowired
    private TechnicalAnalysisSheetMapper analysisSheetMapper;
    
    @Autowired
    private TestingAdapter testingAdapter;
    
    private static Logger log = LoggerFactory.getLogger(DataAnalysisFacade.class);
    
    public List<CapCnvData> getCapCnvList(CapCnvRequest request)
    {
        Pageable pageable = new PageRequest(request.getPageNo(), request.getPageSize());
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(request.getGene()))
        {
            // 模糊匹配
            Pattern pattern = Pattern.compile("^.*" + request.getGene() + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("gene").regex(pattern);
        }
        if (!StringUtils.isEmpty(request.getStartCopyNumber()) && !StringUtils.isEmpty(request.getEndCopyNumber()))
        {
            criteria.and("copyNumber").gte(request.getStartCopyNumber()).lte(request.getEndCopyNumber());
        }
        else if (!StringUtils.isEmpty(request.getStartCopyNumber()))
        {
            criteria.and("copyNumber").gte(request.getStartCopyNumber());
        }
        else if (!StringUtils.isEmpty(request.getEndCopyNumber()))
        {
            criteria.and("copyNumber").lte(request.getEndCopyNumber());
        }
        
        if (!StringUtils.isEmpty(request.getStartPValue()) && !StringUtils.isEmpty(request.getEndPValue()))
        {
            criteria.and("pValue").gte(request.getStartPValue()).lte(request.getEndPValue());
        }
        else if (!StringUtils.isEmpty(request.getStartPValue()))
        {
            criteria.and("pValue").gte(request.getStartPValue());
        }
        else if (!StringUtils.isEmpty(request.getEndPValue()))
        {
            criteria.and("pValue").lte(request.getEndPValue());
        }
        criteria.and("analysisSampleId").is(request.getAnalysisSampleId());
        query.addCriteria(criteria);
        List<CapCnvData> records = template.find(query.with(pageable), CapCnvData.class);
        return records;
    }
    
    public Map<String, Object> getPager(CapCnvRequest request)
    {
        Map<String, Object> map = Maps.newHashMap();
        Pager<CapCnvData> pager = new Pager<CapCnvData>();
        Integer page = null == request.getPageNo() ? 1 : request.getPageNo();
        Integer pageSize = null == request.getPageSize() ? 200 : request.getPageSize();
        DBCollection collection = template.getCollection("analysis-capcnv");
        List<BasicDBObject> qr = new ArrayList<BasicDBObject>();
        BasicDBObject qury = new BasicDBObject();
        qury.put("analysisSampleId", request.getAnalysisSampleId());
        qr.add(qury);
        if (!StringUtils.isEmpty(request.getGene()))
        {
            Pattern pattern = Pattern.compile("^.*" + request.getGene() + ".*$", Pattern.CASE_INSENSITIVE);
            BasicDBObject qury1 = new BasicDBObject();
            qury1.put("gene", pattern);
            qr.add(qury1);
        }
        if (!StringUtils.isEmpty(request.getStartCopyNumber()))
        {
            BasicDBObject minObj = new BasicDBObject("copyNumber", new BasicDBObject("$gte", request.getStartCopyNumber()));
            qr.add(minObj);
        }
        if (!StringUtils.isEmpty(request.getEndCopyNumber()))
        {
            BasicDBObject maxObj = new BasicDBObject("copyNumber", new BasicDBObject("$lte", request.getEndCopyNumber()));
            qr.add(maxObj);
        }
        
        if (!StringUtils.isEmpty(request.getStartPValue()))
        {
            BasicDBObject minObj = new BasicDBObject("pValue", new BasicDBObject("$gte", request.getStartPValue()));
            qr.add(minObj);
        }
        if (!StringUtils.isEmpty(request.getEndPValue()))
        {
            BasicDBObject maxObj = new BasicDBObject("pValue", new BasicDBObject("$lte", request.getEndPValue()));
            qr.add(maxObj);
        }
        
        BasicDBObject quryList = new BasicDBObject("$and", qr);
        long totalCount = 0;
        
        map.put("genes", new Long(collection.distinct("gene", quryList).size()));
        map.put("areas", new Long(collection.distinct("area", quryList).size()));
        
        Iterator<DBObject> iterator = null;
        
        if (Collections3.isNotEmpty(qr))
        {
            totalCount = collection.find(quryList).count();
            iterator = collection.find(quryList).skip((page - 1) * pageSize).limit(pageSize).iterator();
        }
        else
        {
            iterator = collection.find().skip((page - 1) * pageSize).limit(pageSize).iterator();
        }
        
        List<CapCnvData> records = new ArrayList<CapCnvData>();
        while (iterator.hasNext())
        {
            
            DBObject obj = iterator.next();
            CapCnvData capCnvData = new CapCnvData();
            try
            {
                BeanUtil.dbObject2Bean(obj, capCnvData);
                capCnvData.setId(capCnvData.get_id());
                records.add(capCnvData);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        pager.setPageNo(page);
        pager.setPageSize(pageSize);
        pager.setRecords(records);
        pager.setTotalCount((int)totalCount);
        map.put("pager", pager);
        return map;
    }
    
    public CnvAnalysisDataModel getCapCnvDataModel(CapCnvRequest request)
    {
        String analysisSampleId = request.getAnalysisSampleId();
        CnvAnalysisDataModel data = new CnvAnalysisDataModel();
        // 突变筛选列表  如果正在重新分析 不返回数据并提示正在重新分析
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", analysisSampleId);
        CnvAnalysisMonitor monitor = cnvAnalysisMonitorMapper.getBySampleAnalysisId(analysisSampleId);
        // 1.如果没查到说明没做重新分析 2.查到了且状态不是进行中的 查询出结果
        if (null == monitor || (null != monitor && CnvAnalysisMonitor.ANALYSIS_GOING_STAUS.intValue() != monitor.getStatus().intValue()))
        {
            Map<String, Object> map = getPager(request);
            Pager<CapCnvData> capCnvDataPager = (Pager<CapCnvData>)map.get("pager");
            Long geneSize = (Long)map.get("genes");
            Long areaSize = (Long)map.get("areas");
            data.setCapCnvDataPager(capCnvDataPager);
            data.setGeneSize(geneSize);
            data.setAreaSize(areaSize);
        }
        else
        {
            data.setStatus(CnvAnalysisMonitor.ANALYSIS_GOING_STAUS);
        }
        //2.查询图片数据
        List<CnvAnalysisPic> cnvAnalysisPics = cnvAnalysisPicMapper.getBySampleTestId(analysisSampleId);
        if (cnvAnalysisPics != null && cnvAnalysisPics.size() > 0)
        {
            List<CnvAnalysisPicResultList> cnvAnalysisPicResultLists = new ArrayList<CnvAnalysisPicResultList>();
            data.setCnvAnalysisPicResultList(cnvAnalysisPicResultLists);
            for (CnvAnalysisPic cnvAnalysisPic : cnvAnalysisPics)
            {
                CnvAnalysisPicResultList cnvAnalysisPicResultList = new CnvAnalysisPicResultList();
                cnvAnalysisPicResultLists.add(cnvAnalysisPicResultList);
                cnvAnalysisPicResultList.setCnvAnalysisPic(cnvAnalysisPic);
                //3.查询手动添加的突变结果
                List<CnvAnalysisResult> cnvResultList =
                    cnvAnalysisResultMapper.getListBySampleTestIdAndCnvAnalysisPicId(analysisSampleId, cnvAnalysisPic.getId());
                cnvAnalysisPicResultList.setCnvAnalysisResultList(cnvResultList);
            }
        }
        BiologyDivisionFastqData biologyDivisionFastqData = biologyDivisionFastqDataMapper.selectByPrimaryKey(analysisSampleId);
        data.setBiologyDivisionFastqData(biologyDivisionFastqData);
        return data;
    }
    
    private boolean validBlank(CnvAnalysisResult result)
    {
        if (StringUtils.isEmpty(result.getGene()) && StringUtils.isEmpty(result.getExon()) && StringUtils.isEmpty(result.getMissingArea())
            && StringUtils.isEmpty(result.getInheritSource()) && StringUtils.isEmpty(result.getRelatedDisease()))
        {
            return true;
        }
        return false;
    }
    
    // 更新致病性分级中name中包含BA;->BA1;
    public void updateMygenoACMG()
    {
        
        DBCollection mutationCollection = template.getCollection("analysis-mutation");
        // 更新mongo数据
        Pattern pattern = Pattern.compile("^.*BA;.*$", Pattern.CASE_INSENSITIVE);
        BasicDBObject dbQuery = new BasicDBObject();
        dbQuery.put("Mygeno_InterACMG.name", pattern);
        
        DBCursor ret = mutationCollection.find(dbQuery);
        int size = ret.size();
        int i = 1;
        while (ret.hasNext())
        {
            BasicDBObject bdbObj = (BasicDBObject)ret.next();
            BasicDBObject dbUpdate = new BasicDBObject();
            if (bdbObj != null)
            {
                String obj_id = bdbObj.getString("_id");
                System.out.println("total size is:" + size + " now updating:" + i + "条");
                i++;
                Map map = (Map)bdbObj.get("Mygeno_InterACMG");
                String name = "";
                if (null != map && map.containsKey("name"))
                {
                    name = map.get("name").toString();
                }
                // 更新他的name
                if (!StringUtils.isEmpty(name))
                {
                    name = name.replace("BA;", "BA1;");
                }
                else
                {
                    continue;
                }
                BasicDBObject dbQueryObj = new BasicDBObject("_id", new ObjectId(obj_id));
                BasicDBObject dbUpdateDoc = new BasicDBObject();
                dbUpdate.append("Mygeno_InterACMG.name", name);
                dbUpdateDoc.put("$set", dbUpdate);
                mutationCollection.update(dbQueryObj, dbUpdateDoc);
            }
        }
        
    }
    
    public Pagination<TechnicalAnalysisTask> getAssginableTasks(TechnicalAnalysisAssignableTaskSearcher request, String token)
    {
        
        //UserMinimalModel loginer = smmadapter.getLoginUser(token);
        
        TechnicalAnalysisTask searcher = new TechnicalAnalysisTask();
        org.springframework.beans.BeanUtils.copyProperties(request, searcher);
        // searcher.setUserId(loginer.getId());
        List<String> groupIds = technicalAnalysisTaskMapper.selectDistinctGroupId(searcher);
        PageInfo<String> pagination = new PageInfo<String>(groupIds);
        searcher.setGroupIds(pagination.getList());
        List<TechnicalAnalysisTask> tasks = technicalAnalysisTaskMapper.selectCurrentTasks(searcher);
        Pagination<TechnicalAnalysisTask> pagin = new Pagination<TechnicalAnalysisTask>();
        // 查询过往的测序编号
        
        pagin.setPageNo(pagination.getPageNum());
        pagin.setPageSize(pagination.getPageSize());
        pagin.setTotalCount((int)pagination.getTotal());
        pagin.setRecords(tasks);
        return pagin;
    }
    
    /*public Pagination<TechnicalAnalysisTask> getRecheckTechnicalAnalysisList(TechnicalAnalysisAssignableTaskSearcher request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        
        TechnicalAnalysisTask searcher = new TechnicalAnalysisTask();
        org.springframework.beans.BeanUtils.copyProperties(request, searcher);
        searcher.setProductPrincipalId(loginer.getId()); //复核---自动分配产品负责人
        searcher.setStatusItems(Lists.newArrayList("4"));
        List<String> groupIds = technicalAnalysisTaskMapper.selectDistinctGroupId(searcher);
        PageInfo<String> pagination = new PageInfo<String>(groupIds);
        searcher.setGroupIds(pagination.getList());
        List<TechnicalAnalysisTask> tasks = technicalAnalysisTaskMapper.selectCurrentTasks(searcher);
        Pagination<TechnicalAnalysisTask> pagin = new Pagination<TechnicalAnalysisTask>();
        pagin.setPageNo(pagination.getPageNum());
        pagin.setPageSize(pagination.getPageSize());
        pagin.setTotalCount((int)pagination.getTotal());
        pagin.setRecords(tasks);
        return pagin;
    }*/
    
    public Integer claimTechnicalAnalysis(String familyGroupId, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        // 判断是否应被认领
        TechnicalAnalysisTask searcher = new TechnicalAnalysisTask();
        searcher.setFamilyGroupId(familyGroupId);
        List<TechnicalAnalysisTask> tasks = technicalAnalysisTaskMapper.selectBySearcher(searcher);
        
        tasks = tasks.stream().filter(x -> !x.getStatus().equals(7)).collect(toList());
        
        if (Collections3.isNotEmpty(tasks))
        {
            TechnicalAnalysisTask task = Collections3.getFirst(tasks);
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(task.getUserId()))
            {
                return 2;
            }
            else
            {
                
                for (TechnicalAnalysisTask tt : tasks)
                {
                    
                    tt.setUserId(loginer.getId());
                    tt.setStatus(3);
                    technicalAnalysisTaskMapper.updateByPrimaryKeySelective(tt);
                    
                }
                TechnicalAnalysisSheet technicalAnalysisSheet = new TechnicalAnalysisSheet();
                technicalAnalysisSheet.setId(IdGen.uuid());
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                String formateDate = format.format(new Date());
                List<String> liushuiId = technicalAnalysisTaskMapper.getNextVal("XJS");
                technicalAnalysisSheet.setCode("XJS" + formateDate + String.format("%02d", Long.parseLong(liushuiId.get(0))));
                technicalAnalysisSheet.setTaskId(tasks.get(0).getFamilyGroupId());
                technicalAnalysisSheet.setTesterId(tasks.get(0).getUserId());
                technicalAnalysisSheet.setTesterName(technicalAnalysisTaskMapper.getTesterName(tasks.get(0).getUserId()));
                technicalAnalysisSheet.setCreateTime(new Date());
                technicalAnalysisTaskMapper.insertSheet(technicalAnalysisSheet);
                
                return 1;
            }
            
        }
        else
        {
            return 0;
        }
    }
    
    public List<MutationStatisticsModel> getQualitycontrolDataByaAnalysisId(MutationStatisticsSearcher searcher)
    {
        List<MutationStatisticsModel> result = new ArrayList<MutationStatisticsModel>();
        DBCollection collection = template.getCollection("analysis-statistics");
        BasicDBObject object = new BasicDBObject("analysisSampleId", searcher.getAnalysisSampleId());
        Iterator<DBObject> iterator = collection.find(object).iterator();
        while (iterator.hasNext())
        {
            MutationStatisticsModel model = new MutationStatisticsModel();
            DBObject obj = iterator.next();
            try
            {
                MongoObjectUtil.dbObject2Bean(obj, model);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
            result.add(model);
            
        }
        return result;
        
    }
    
    public List<TechnicalAnalysisTask> searchTechnicalAnalysisTask(TechnicalAnalysisTask searcher)
    {
        return technicalAnalysisTaskMapper.selectBySearcher(searcher);
    }
    
    public String getEvidenceByMongoId(String mongoId)
    {
        return service.getEvidenceByMongoId(mongoId);
    }
    
    public boolean ifFamilyAnalysis(String analysisSampleId)
    {
        BiologyDivisionFastqData division = biologyDivisionFastqDataMapper.selectByPrimaryKey(analysisSampleId);
        if (null == division)
        {
            return true;
        }
        return false;
    }
    
    public DrawPictureModel getCapCnvPicture(CapCnvDrawPicRequest request) throws IllegalAccessException, InvocationTargetException
    {
        
        String ids = request.getIds();
        List<String> idList = new ArrayList<String>();
        if (!StringUtils.isEmpty(ids))
        {
            String arr[] = ids.split(",");
            idList = Arrays.asList(arr);
        }
        // 查询出来capcnv mongo数据
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("_id").in(idList);
        query.addCriteria(criteria);
        List<Map> records = template.find(query, Map.class, "analysis-capcnv");
        if (CollectionUtils.isEmpty(records))
        {
            return null;
        }
        List<CapCnvPictureGeneData> data = new ArrayList<CapCnvPictureGeneData>();
        CapCnvPictureGeneData capCnvPictureGeneData;
        for (Map<String, String> capData : records)
        {
            capCnvPictureGeneData = new CapCnvPictureGeneData();
            String location = "";
            if (null == capData.get("Location"))
            {
                if (null != capData.get("chrLocation"))
                {
                    location = capData.get("chrLocation").toString();
                }
            }
            else
            {
                location = capData.get("Location").toString();
            }
            capCnvPictureGeneData.setChrLocation(location);
            String area = "";
            if (null == capData.get("Region"))
            {
                if (null != capData.get("area"))
                {
                    area = capData.get("area").toString();
                }
            }
            else
            {
                area = capData.get("Region").toString();
            }
            capCnvPictureGeneData.setArea(area);
            String gene = "";
            if (null == capData.get("Gene"))
            {
                if (null != capData.get("gene"))
                {
                    gene = capData.get("gene").toString();
                }
            }
            else
            {
                gene = capData.get("Gene").toString();
            }
            capCnvPictureGeneData.setGene(gene);
            // 判断是家系还是单例
            BiologyDivisionFastqData division = biologyDivisionFastqDataMapper.selectByPrimaryKey(capData.get("analysisSampleId").toString());
            if (null != division)
            {
                Map<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("copyNumber", String.valueOf(capData.get("copyNumber")));
                dataMap.put("pValue", String.valueOf(capData.get("pValue")));
                capCnvPictureGeneData.getData().add(dataMap);
            }
            else
            {// 家系
                Set<String> dataCodeSet = new HashSet<>();
                for (String key : capData.keySet())
                {
                    if (key.contains("("))
                    {
                        dataCodeSet.add(key.substring(key.indexOf("(") + 1, key.indexOf(")")));
                    }
                    
                }
                for (String dataCode : dataCodeSet)
                {
                    Map<String, String> dataMap = new HashMap<String, String>();
                    dataMap.put("copyNumber", capData.get("CopyRatio(" + dataCode + ")").toString());
                    dataMap.put("pValue", capData.get("P-value(" + dataCode + ")").toString());
                    capCnvPictureGeneData.getData().add(dataMap);
                }
            }
            data.add(capCnvPictureGeneData);
        }
        CapCnvPictureRequest picRequest = new CapCnvPictureRequest();
        picRequest.setData(data);
        picRequest.setSampleCode(request.getSampleCode());
        log.info("调用capcnv作图接口参数:" + JsonUtils.toJson(picRequest));
        DrawPictureModel model = HttpRequestAPI.httpPost(HttpRequestAPI.DRAW_PICTURE_URL, JsonUtils.toJson(picRequest), DrawPictureModel.class);
        log.info("调用capcnv作图接口返回:" + model);
        // 把查询出来结果存到对应的图片数据表
        if (null != model && null != model.getData())
        {
            String url = model.getData().getCapCnv();
            if (url.contains("10.10.1.67"))
            {
                url = url.replace("10.10.1.67", HttpRequestAPI.CNV_HOST);
            }
            if (StringUtils.isEmpty(url))
            {
                log.warn(" return url is null ,analysisId is:" + request.getSampleAnalysisId());
            }
            // 1.第一次生成直接保存进去，后面更新图片即可
            CnvAnalysisPic cnvAnalysisPic = new CnvAnalysisPic();
            cnvAnalysisPic.setSampleTestId(request.getSampleAnalysisId());
            cnvAnalysisPic.setImageUrl(url);
            cnvAnalysisPic.setCreateTime(new Date());
            cnvAnalysisPic.setUpdateTime(new Date());
            cnvAnalysisPic.setIsCollection(0);
            cnvAnalysisPicMapper.insert(cnvAnalysisPic);
        }
        
        return model;
        
    }
    
    public ListResponse<CnvAnalysisResult> cnvAnalysisResultList(CapCnvRequest request)
    {
        List<CnvAnalysisResult> cnvResultList = cnvAnalysisResultMapper.getListBySampleTestId(request.getAnalysisSampleId());
        return new ListResponse<CnvAnalysisResult>(cnvResultList);
    }
    
    public void saveAnalysisCapCnvResult(CapCnvResultData request)
    {
        
        String sampleAnalysisId = request.getSampleAnalysisId();
        List<CnvAnalysisResult> cnvResultList =
            cnvAnalysisResultMapper.getListBySampleTestIdAndCnvAnalysisPicId(sampleAnalysisId, request.getCnvAnalysisPicId());
        
        List<CnvAnalysisResult> cnvAnalysisResult = request.getCnvAnalysisResult();
        
        if (Collections3.isNotEmpty(cnvAnalysisResult))
        {
            //拆分染色体位置为染色体编号，染色体开始位置，染色体结束位置
            
            List<String> idsAll = Lists.newArrayList();
            List<String> idsReq = Lists.newArrayList();
            
            // 多余的删除掉 包含已收藏的
            if (Collections3.isNotEmpty(cnvResultList))
            {
                
                cnvResultList.forEach(x -> idsAll.add(x.getId()));
                cnvAnalysisResult.forEach(x -> idsReq.add(x.getId()));
                idsAll.removeAll(idsReq);// 过滤出来需要删除的id
                for (String id : idsAll)
                {
                    cnvAnalysisResultMapper.deleteByPrimaryKey(id);
                }
            }
            
            for (CnvAnalysisResult result : cnvAnalysisResult)
            {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(result.getMissingArea()))
                {
                    String[] chromosomeLocus = result.getMissingArea().split("-");
                    if (chromosomeLocus.length == 2)
                    {
                        if (org.apache.commons.lang3.StringUtils.isNotBlank(chromosomeLocus[0]))
                        {
                            result.setChromosome(chromosomeLocus[0]);
                        }
                        String locus = chromosomeLocus[1];
                        if (org.apache.commons.lang3.StringUtils.isNotBlank(locus))
                        {
                            locus = locus.trim();
                            if (locus.contains(" "))
                            {
                                String[] beginLocusEndLocus = locus.split(" ");
                                result.setBeginLocus(beginLocusEndLocus[0] == null ? "" : beginLocusEndLocus[0]);
                                result.setEndLocus(beginLocusEndLocus[1] == null ? "" : beginLocusEndLocus[1]);
                            }
                            else
                            {
                                result.setBeginLocus(locus == null ? "" : locus);
                                result.setEndLocus(locus == null ? "" : locus);
                            }
                        }
                    }
                }
                
                String id = result.getId();
                CnvAnalysisResult temp = cnvAnalysisResultMapper.selectByPrimaryKey(id);
                
                if (null != temp)
                {
                    temp.setSampleTestId(sampleAnalysisId);
                    temp.setUpdateTime(new Date());
                    temp.setExon(result.getExon());
                    temp.setGene(result.getGene());
                    temp.setInheritSource(result.getInheritSource());
                    temp.setMissingArea(result.getMissingArea());
                    temp.setRelatedDisease(result.getRelatedDisease());
                    temp.setCnvAnalysisPicId(request.getCnvAnalysisPicId());
                    temp.setClinicalJudgment(result.getClinicalJudgment());
                    temp.setChromosome(result.getChromosome());
                    temp.setBeginLocus(result.getBeginLocus());
                    temp.setEndLocus(result.getEndLocus());
                    temp.setInhert(result.getInhert());
                    temp.setPathogenicLevel(result.getPathogenicLevel());
                    cnvAnalysisResultMapper.updateByPrimaryKey(temp);
                    
                    List<TechnicalAnalyRecordWithBLOBs> technicalAnalyRecordWithBLOBList = technicalAnalyRecordMapper.getByCnvResultId(id);
                    for (TechnicalAnalyRecordWithBLOBs technicalAnalyRecordWithBLOBs : technicalAnalyRecordWithBLOBList)
                    {
                        technicalAnalyRecordWithBLOBs.setClinicalJudgment(result.getClinicalJudgment());
                        technicalAnalyRecordWithBLOBs.setGeneSymbol(result.getGene());
                        technicalAnalyRecordWithBLOBs.setChromosomeLocation(result.getMissingArea());
                        technicalAnalyRecordWithBLOBs.setChromosome(result.getChromosome());
                        technicalAnalyRecordWithBLOBs.setBeginLocus(result.getBeginLocus());
                        technicalAnalyRecordWithBLOBs.setEndLocus(result.getEndLocus());
                        technicalAnalyRecordWithBLOBs.setExon(result.getExon());
                        technicalAnalyRecordWithBLOBs.setMutationSource(result.getInheritSource());
                        technicalAnalyRecordWithBLOBs.setDisease(result.getRelatedDisease());
                        technicalAnalyRecordWithBLOBs.setInhert(result.getInhert());
                        technicalAnalyRecordMapper.updateByPrimaryKey(technicalAnalyRecordWithBLOBs);
                    }
                }
                else
                {
                    if (!validBlank(result))// 都是空白 就不保存
                    {
                        temp = new CnvAnalysisResult();
                        temp.setSampleTestId(sampleAnalysisId);
                        temp.setId(result.getId());
                        temp.setCreateTime(new Date());
                        temp.setUpdateTime(new Date());
                        temp.setExon(result.getExon());
                        temp.setGene(result.getGene());
                        temp.setInheritSource(result.getInheritSource());
                        temp.setMissingArea(result.getMissingArea());
                        temp.setRelatedDisease(result.getRelatedDisease());
                        temp.setCnvAnalysisPicId(request.getCnvAnalysisPicId());
                        temp.setClinicalJudgment(result.getClinicalJudgment());
                        temp.setChromosome(result.getChromosome());
                        temp.setBeginLocus(result.getBeginLocus());
                        temp.setEndLocus(result.getEndLocus());
                        temp.setInhert(result.getInhert());
                        temp.setPathogenicLevel(result.getPathogenicLevel());
                        cnvAnalysisResultMapper.insert(temp);
                    }
                }
            }
        }
        
    }
    
    public List<CompareSample> getCompareSampleList(CompareSampleRequest request) throws IllegalAccessException, InvocationTargetException
    {
        CompareSampleSearch compareSampleSearch = new CompareSampleSearch();
        BeanUtils.copyProperties(request, compareSampleSearch);
        List<CompareSample> compareSamples = biologyDivisionFastqDataMapper.selectCompareSample(compareSampleSearch);
        return compareSamples;
    }
    
    public void compareSampleReanalysis(CompareSampleReanalysisRequest request)
    {
        
        // 1.通过这个ID查询出来cnv分析接口需要的数据
        // 1.bed文件（lims传产品编号）
        // 2.对照样本列表及对应的bam文件地址 同一个产品同一检测方法
        // 3.分析样本：bam文件地址
        // 传递的数据格式待定
        if (request.getCapCnvAnalysisRequest() == null || request.getCapCnvAnalysisRequest().getAnalysisSample() == null
            || request.getCapCnvAnalysisRequest().getCompareList() == null)
        {
            throw new IllegalStateException("参数不能为空");
        }
        for (CapCnvAnalysisSampleBamRequest capCnvAnalysisSampleBamRequest : request.getCapCnvAnalysisRequest().getCompareList())
        {
            capCnvAnalysisSampleBamRequest.setSex(Integer.valueOf(getSex(capCnvAnalysisSampleBamRequest)));
        }
        BioInfoAnnotate bioInfoAnnotate = bioInfoAnnotateMapper.getAnnotateBySampleCode(request.getCapCnvAnalysisRequest().getBedCode());
        if (bioInfoAnnotate != null && org.apache.commons.lang3.StringUtils.isNotBlank(bioInfoAnnotate.getBamBam()))
        {
            request.getCapCnvAnalysisRequest().getAnalysisSample().setBamFilePath(bioInfoAnnotate.getBamBam());
        }
        request.getCapCnvAnalysisRequest().getAnalysisSample().setSex(Integer.valueOf(getSex(request.getCapCnvAnalysisRequest().getAnalysisSample())));
        String requestJson = JsonUtils.toJson(request.getCapCnvAnalysisRequest());
        log.info("redo cnv analysis json is:" + requestJson);
        DrawPictureModel model = HttpRequestAPI.httpPost(HttpRequestAPI.RE_ANALYSIS_CNV_TASKID_URL, requestJson, DrawPictureModel.class);
        String taskId = "";
        if (null != model && null != model.getData())
        {
            taskId = model.getData().getTaskId();
        }
        else
        {
            throw new IllegalStateException();
        }
        CnvAnalysisMonitor monitor = new CnvAnalysisMonitor();
        monitor.setTaskId(taskId);
        monitor.setStatus(CnvAnalysisMonitor.ANALYSIS_GOING_STAUS);
        monitor.setSampleTestId(request.getSampleAnalysisId());
        monitor.setCreateTime(new Date());
        cnvAnalysisMonitorMapper.insert(monitor);
        
        // 清掉之前保存的数据
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("analysisSampleId").is(request.getSampleAnalysisId());
        query.addCriteria(criteria);
        template.remove(query, CapCnvData.class);
    }
    
    private String getSex(CapCnvAnalysisSampleBamRequest capCnvAnalysisSampleBamRequest)
    {
        String sex = "2";
        SampleSex sampleSex = biologyDivisionFastqDataMapper.getPrimarySampleSexByCode(capCnvAnalysisSampleBamRequest.getSampleCode());
        if (null != sampleSex)
        {
            if (StringUtils.isEmpty(sampleSex.getSex()))
            {
                sex = "2";//未知
            }
            else
            {
                sex = sampleSex.getSex();
            }
        }
        else
        {
            sampleSex = biologyDivisionFastqDataMapper.getSubSampleSexByCode(capCnvAnalysisSampleBamRequest.getSampleCode());
            if (StringUtils.isEmpty(sampleSex.getSex()))
            {
                sex = "2";//未知
            }
            else
            {
                sex = sampleSex.getSex();
                FamilyRelationSex frs = new FamilyRelationSex();
                String val = frs.map.get(sampleSex.getSex());
                if (!StringUtils.isEmpty(val))
                {
                    if ("本人".equals(val))
                    {
                        sex = "2";
                    }
                    else if ("男".equals(val))
                    {
                        sex = "0";
                    }
                    else if ("女".equals(val))
                    {
                        sex = "1";
                    }
                    else
                    {
                        sex = "2";
                    }
                }
                else
                {
                    sex = "2";
                }
            }
        }
        return sex;
    }
    
    public void getReCnvAnalysisResult()
    {
        // 查询正在 处理的 重新分析的cnv数据
        List<CnvAnalysisMonitor> monitorList = cnvAnalysisMonitorMapper.getGoingCnvAnalysisList();
        if (!CollectionUtils.isEmpty(monitorList))
        {
            log.info(" waiting reanalysis cnv size is:" + monitorList.size());
            for (CnvAnalysisMonitor monitor : monitorList)
            {
                todoUpdateMonitor(monitor);
            }
        }
    }
    
    public void todoUpdateMonitor(CnvAnalysisMonitor monitor)
    {
        String taskId = monitor.getTaskId();
        updateCnvAnalysis(monitor, taskId);
    }
    
    public void updateCnvAnalysis(CnvAnalysisMonitor monitor, String taskId)
    {
        CapCnvDataResponse model = HttpRequestAPI.httpGetByTaskId(HttpRequestAPI.RE_ANALYSIS_CNV_SEARCH_URL, taskId, CapCnvDataResponse.class);
        
        if (null != model && null != model.getData())
        {
            log.info("调用capcvn回调返回:" + model.getData());
            BiologyDivisionFastqData divisionData = biologyDivisionFastqDataMapper.selectByPrimaryKey(monitor.getSampleTestId());
            if (null == divisionData)
            {
                throw new IllegalStateException();
            }
            String dataXls = model.getData().getCapCnv();
            if (!StringUtils.isEmpty(dataXls))
            {
                // 调用解析接口 存入数据
                bioInfoAnnotateService.afterAnnotateForSave(divisionData.getDataCode(), "", dataXls, "", "");
                
                // 更新监控状态
                monitor.setStatus(CnvAnalysisMonitor.ANALYSIS_SUCCESS_STAUS);
                monitor.setUpdateTime(new Date());
                cnvAnalysisMonitorMapper.updateByPrimaryKey(monitor);
                
                // 更新最新的数据地址？
                if (null != divisionData)
                {
                    String dataCode = divisionData.getDataCode();
                    BioInfoAnnotate bioInfoAnnotate = bioInfoAnnotateMapper.getAnnotateBySampleCode(dataCode);
                    if (null != bioInfoAnnotate)
                    {
                        bioInfoAnnotate.setCnv(dataXls);
                        bioInfoAnnotateMapper.update(bioInfoAnnotate);
                    }
                }
            }
        }
    }
    
    public UploadEvidenceRespModel uploadEvidence(UploadEvidenceRequest request)
    {
        return service.uploadEvidence(request);
    }
    
    public String getMutationDetail(String objectId)
    {
        return service.getMutationDetail(objectId);
    }
    
    @Transactional
    public void submitCnvVerify(SubmitCnvVerifyRequest request)
    {
        CancelCollectionCnvAnalysisResult searcher = new CancelCollectionCnvAnalysisResult();
        searcher.setCnvAnalysisResultId(request.getCnvAnalysisResultId());
        cancelSubmitCnvVerify(searcher);
        TechnicalAnalyRecordWithBLOBs technicalAnalyRecordWithBLOBs = new TechnicalAnalyRecordWithBLOBs();
        technicalAnalyRecordWithBLOBs.setSourceRef(TechnicalAnalyRecord.SOURCE_REF_CAPCNV);
        technicalAnalyRecordWithBLOBs.setCnvResultId(request.getCnvAnalysisResultId());
        technicalAnalyRecordWithBLOBs.setVerify(request.getIsFamilyTestAnalysis());
        
        CnvAnalysisResult cnvAnalysisResult = cnvAnalysisResultMapper.selectByPrimaryKey(request.getCnvAnalysisResultId());
        technicalAnalyRecordWithBLOBs.setClinicalJudgment(cnvAnalysisResult.getClinicalJudgment());
        technicalAnalyRecordWithBLOBs.setGeneSymbol(cnvAnalysisResult.getGene());
        technicalAnalyRecordWithBLOBs.setChromosomeLocation(cnvAnalysisResult.getMissingArea());
        technicalAnalyRecordWithBLOBs.setChromosome(cnvAnalysisResult.getChromosome());
        technicalAnalyRecordWithBLOBs.setBeginLocus(cnvAnalysisResult.getBeginLocus());
        technicalAnalyRecordWithBLOBs.setEndLocus(cnvAnalysisResult.getEndLocus());
        technicalAnalyRecordWithBLOBs.setExon(cnvAnalysisResult.getExon());
        technicalAnalyRecordWithBLOBs.setMutationSource(cnvAnalysisResult.getInheritSource());
        technicalAnalyRecordWithBLOBs.setDisease(cnvAnalysisResult.getRelatedDisease());
        technicalAnalyRecordWithBLOBs.setInhert(cnvAnalysisResult.getInhert());
        
        BiologyDivisionFastqData fastqData = biologyDivisionFastqDataMapper.selectByPrimaryKey(request.getTechnicalFamilyGroupId());
        if (fastqData != null)
        {
            technicalAnalyRecordWithBLOBs.setTechnicalFamilyGroupId(fastqData.getId());
            technicalAnalyRecordWithBLOBs.setDataCode(fastqData.getDataCode());
            technicalAnalyRecordWithBLOBs.setSample(fastqData.getSampleCode());
        }
        boolean isInsertTechnicalAnalyRecordWithBLOBs = false;
        if (request.getIsFamilyTestAnalysis().equals("验证"))
        {
            technicalAnalyRecordWithBLOBs.setLocusType("验证位点");
            if (request.getmLPAverifySampleRelation() != null && request.getmLPAverifySampleRelation().size() > 0)
            {
                technicalAnalyRecordWithBLOBs.setVerifyMethod(TechnicalAnalyRecord.FAMILYTESTMETHOD_MLPA);
                technicalAnalyRecordMapper.insert(technicalAnalyRecordWithBLOBs);
                isInsertTechnicalAnalyRecordWithBLOBs = true;
                for (String mlpaVerifySampleRelation : request.getmLPAverifySampleRelation())
                {
                    String[] sampleCodeRelations = mlpaVerifySampleRelation.split(";");
                    TechnicalAnalyVerify technicalAnalyVerify = new TechnicalAnalyVerify();
                    technicalAnalyVerify.setRecordId(technicalAnalyRecordWithBLOBs.getId());
                    technicalAnalyVerify.setInputSampleId(sampleCodeRelations[0]);
                    technicalAnalyVerify.setSampleFamilyRelation(sampleCodeRelations[1]);
                    technicalAnalyVerifyMapper.insert(technicalAnalyVerify);
                }
            }
            if (request.getqPCRVerifySampleRelation() != null && request.getqPCRVerifySampleRelation().size() > 0)
            {
                technicalAnalyRecordWithBLOBs.setVerifyMethod(TechnicalAnalyRecord.FAMILYTESTMETHOD_QPCR);
                technicalAnalyRecordMapper.insert(technicalAnalyRecordWithBLOBs);
                isInsertTechnicalAnalyRecordWithBLOBs = true;
                for (String qpcrVerifySampleRelation : request.getqPCRVerifySampleRelation())
                {
                    String[] sampleCodeRelations = qpcrVerifySampleRelation.split(";");
                    TechnicalAnalyVerify technicalAnalyVerify = new TechnicalAnalyVerify();
                    technicalAnalyVerify.setRecordId(technicalAnalyRecordWithBLOBs.getId());
                    technicalAnalyVerify.setInputSampleId(sampleCodeRelations[0]);
                    technicalAnalyVerify.setSampleFamilyRelation(sampleCodeRelations[1]);
                    technicalAnalyVerifyMapper.insert(technicalAnalyVerify);
                }
            }
        }
        else
        {
            technicalAnalyRecordWithBLOBs.setLocusType(request.getLocusType());
        }
        if (!isInsertTechnicalAnalyRecordWithBLOBs)
        {
            technicalAnalyRecordMapper.insert(technicalAnalyRecordWithBLOBs);
        }
        
    }
    
    @Transactional
    public void cancelSubmitCnvVerify(CancelCollectionCnvAnalysisResult request)
    {
        if (!StringUtils.isEmpty(request.getCnvAnalysisResultId()))
        {
            List<String> technicalAnalyRecordWithBLOBsIds = technicalAnalyRecordMapper.getIDsByCnvResultId(request.getCnvAnalysisResultId());
            if (technicalAnalyRecordWithBLOBsIds != null && technicalAnalyRecordWithBLOBsIds.size() > 0)
            {
                technicalAnalyRecordWithBLOBsIds.forEach(technicalAnalyRecordWithBLOBsId -> {
                    List<String> technicalAnalyVerifyIds = technicalAnalyVerifyMapper.selectIdsByRecordId(technicalAnalyRecordWithBLOBsId);
                    technicalAnalyVerifyIds.forEach(technicalAnalyVerifyId -> technicalAnalyVerifyMapper.deleteByPrimaryKey(technicalAnalyVerifyId));
                    technicalAnalyRecordMapper.deleteByPrimaryKey(technicalAnalyRecordWithBLOBsId);
                });
            }
            else
            {
                log.info("根据cnvAnalysisResultId:" + request + ",找不到对应的CnvAnalysisVerify记录");
            }
        }
        if (!StringUtils.isEmpty(request.getTechnicalAnalyRecordId()))
        {
            TechnicalAnalyRecord technicalAnalyRecordWithBLOBsIds = technicalAnalyRecordMapper.selectRecordByPrimaryKey(request.getTechnicalAnalyRecordId());
            if (technicalAnalyRecordWithBLOBsIds != null)
            {
                List<String> technicalAnalyVerifyIds = technicalAnalyVerifyMapper.selectIdsByRecordId(technicalAnalyRecordWithBLOBsIds.getId());
                technicalAnalyVerifyIds.forEach(technicalAnalyVerifyId -> technicalAnalyVerifyMapper.deleteByPrimaryKey(technicalAnalyVerifyId));
                technicalAnalyRecordMapper.deleteByPrimaryKey(technicalAnalyRecordWithBLOBsIds.getId());
            }
            else
            {
                log.info("根据technicalAnalyRecordId:" + request + ",找不到对应的record记录");
            }
        }
        
    }
    
    public List<Map> getFamilyCapCnvList(CapCnvRequest request)
    {
        
        BiologyDivisionFastqData mainDivisionData = new BiologyDivisionFastqData();// 主样本
        FamilyAnalysisSearcher searcher = new FamilyAnalysisSearcher();
        searcher.setId(request.getAnalysisSampleId());
        
        List<BiologyFamilyAnalysis> familyAnalysisList = biologyFamilyAnalysisMapper.search(searcher);
        if (Collections3.isNotEmpty(familyAnalysisList) && familyAnalysisList.size() == 1)
        {
            
            BiologyFamilyAnalysis familyAnalysis = Collections3.getFirst(familyAnalysisList);
            
            familyAnalysis.setSampleList(orderSampleDetailMapper.selectByFamilyAnalysisId(familyAnalysis.getId())); // 根据家系分析id获取样本list
            mainDivisionData = biologyDivisionFastqDataMapper.selectByDataCode(request.getDataCode());
            /*            if (familyAnalysis.getSampleList() != null && familyAnalysis.getSampleList().size() > 0)
                        {
                            for (OrderSampleDetail sample : familyAnalysis.getSampleList())
                            {
                                if (OrderSampleDetail.SAMPLEBTYPE_PRIMARYSAMPLE == sample.getSampleBtype())
                                {// 主样本
                                    mainDivisionData = biologyDivisionFastqDataMapper.selectByDataCode(sample.getSampleId());
                                    break;
                                }
                            }
                        }*/
        }
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(request.getGene()))
        {
            // 模糊匹配
            Pattern pattern = Pattern.compile("^.*" + request.getGene() + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("Gene").regex(pattern);
        }
        if (!StringUtils.isEmpty(request.getStartCopyNumber()) && !StringUtils.isEmpty(request.getEndCopyNumber()))
        {
            criteria.and("CopyRatio(" + mainDivisionData.getDataCode() + ")").gte(request.getStartCopyNumber()).lte(request.getEndCopyNumber());
        }
        else if (!StringUtils.isEmpty(request.getStartCopyNumber()))
        {
            criteria.and("CopyRatio(" + mainDivisionData.getDataCode() + ")").gte(request.getStartCopyNumber());
        }
        else if (!StringUtils.isEmpty(request.getEndCopyNumber()))
        {
            criteria.and("CopyRatio(" + mainDivisionData.getDataCode() + ")").lte(request.getEndCopyNumber());
        }
        
        if (!StringUtils.isEmpty(request.getStartPValue()) && !StringUtils.isEmpty(request.getEndPValue()))
        {
            criteria.and("P-value(" + mainDivisionData.getDataCode() + ")").gte(request.getStartPValue()).lte(request.getEndPValue());
        }
        else if (!StringUtils.isEmpty(request.getStartPValue()))
        {
            criteria.and("P-value(" + mainDivisionData.getDataCode() + ")").gte(request.getStartPValue());
        }
        else if (!StringUtils.isEmpty(request.getEndPValue()))
        {
            criteria.and("P-value(" + mainDivisionData.getDataCode() + ")").lte(request.getEndPValue());
        }
        criteria.and("analysisSampleId").is(request.getAnalysisSampleId());
        query.addCriteria(criteria);
        List<Map> records = template.find(query, Map.class, "analysis-capcnv");
        for (Map m : records)
        {
            m.put("_id", m.get("_id").toString());
        }
        return records;
    }
    
    public CnvAnalysisFamilyDataModel getFamilyCapCnvDataModel(CapCnvRequest request)
    {
        
        String analysisSampleId = request.getAnalysisSampleId();
        CnvAnalysisFamilyDataModel data = new CnvAnalysisFamilyDataModel();
        // 突变筛选列表 如果正在重新分析 不返回数据并提示正在重新分析
        CnvAnalysisMonitor monitor = cnvAnalysisMonitorMapper.getBySampleAnalysisId(analysisSampleId);
        // 1.如果没查到说明没做重新分析 2.查到了且状态不是进行中的 查询出结果
        if (null == monitor || (null != monitor && CnvAnalysisMonitor.ANALYSIS_GOING_STAUS.intValue() != monitor.getStatus().intValue()))
        {
            Map<String, Object> map = getFamilyPager(request);
            Pager<Map> capCnvDataPager = (Pager<Map>)map.get("pager");
            Long geneSize = (Long)map.get("genes");
            Long areaSize = (Long)map.get("areas");
            data.setCapCnvDataPager(capCnvDataPager);
            data.setGeneSize(geneSize);
            data.setAreaSize(areaSize);
            data.setBiologyDivisionFastqData((BiologyDivisionFastqData)map.get("biologyDivisionFastqData"));
        }
        else
        {
            data.setStatus(CnvAnalysisMonitor.ANALYSIS_GOING_STAUS);
        }
        // 2.查询图片数据
        List<CnvAnalysisPic> cnvAnalysisPics = cnvAnalysisPicMapper.getBySampleTestId(analysisSampleId);
        if (cnvAnalysisPics != null && cnvAnalysisPics.size() > 0)
        {
            List<CnvAnalysisPicResultList> cnvAnalysisPicResultLists = new ArrayList<CnvAnalysisPicResultList>();
            data.setCnvAnalysisPicResultList(cnvAnalysisPicResultLists);
            for (CnvAnalysisPic cnvAnalysisPic : cnvAnalysisPics)
            {
                CnvAnalysisPicResultList cnvAnalysisPicResultList = new CnvAnalysisPicResultList();
                cnvAnalysisPicResultLists.add(cnvAnalysisPicResultList);
                cnvAnalysisPicResultList.setCnvAnalysisPic(cnvAnalysisPic);
                //3.查询手动添加的突变结果
                List<CnvAnalysisResult> cnvResultList =
                    cnvAnalysisResultMapper.getListBySampleTestIdAndCnvAnalysisPicId(analysisSampleId, cnvAnalysisPic.getId());
                cnvAnalysisPicResultList.setCnvAnalysisResultList(cnvResultList);
            }
        }
        //BiologyDivisionFastqData biologyDivisionFastqData = biologyDivisionFastqDataMapper.selectByPrimaryKey(analysisSampleId);
        if (data.getCapCnvDataPager() != null && data.getCapCnvDataPager().getRecords() != null)
        {
            for (Map m : data.getCapCnvDataPager().getRecords())
            {
                m.put("_id", m.get("_id").toString());
            }
        }
        return data;
    }
    
    private Map<String, Object> getFamilyPager(CapCnvRequest request)
    {
        
        // 查询主样本，根据主样本c值P值筛选
        BiologyDivisionFastqData mainDivisionData = null;// 主样本
        FamilyAnalysisSearcher searcher = new FamilyAnalysisSearcher();
        searcher.setId(request.getAnalysisSampleId());
        Map<String, Object> map = Maps.newHashMap();
        List<BiologyFamilyAnalysis> familyAnalysisList = biologyFamilyAnalysisMapper.search(searcher);
        
        if (Collections3.isNotEmpty(familyAnalysisList) && familyAnalysisList.size() == 1)
        {
            
            BiologyFamilyAnalysis familyAnalysis = Collections3.getFirst(familyAnalysisList);
            
            familyAnalysis.setSampleList(orderSampleDetailMapper.selectByFamilyAnalysisId(familyAnalysis.getId())); // 根据家系分析id获取样本list
            mainDivisionData = biologyDivisionFastqDataMapper.selectByDataCode(request.getDataCode());
            map.put("biologyDivisionFastqData", mainDivisionData);
            /*            if (familyAnalysis.getSampleList() != null && familyAnalysis.getSampleList().size() > 0)
                        {
                            for (OrderSampleDetail sample : familyAnalysis.getSampleList())
                            {
                                if (OrderSampleDetail.SAMPLEBTYPE_PRIMARYSAMPLE == sample.getSampleBtype())
                                {// 主样本
                                    mainDivisionData = biologyDivisionFastqDataMapper.getBySampleId(sample.getSampleId());
                                    map.put("biologyDivisionFastqData", mainDivisionData);
                                    break;
                                }
                            }
                        }*/
        }
        if (mainDivisionData == null)
            return map;
        
        Pager<Map> pager = new Pager<Map>();
        Integer page = null == request.getPageNo() ? 1 : request.getPageNo();
        Integer pageSize = null == request.getPageSize() ? 200 : request.getPageSize();
        
        // Criteria criteria = new Criteria();
        DBCollection collection = template.getCollection("analysis-capcnv");
        List<BasicDBObject> qr = new ArrayList<BasicDBObject>();
        BasicDBObject qury = new BasicDBObject();
        qury.put("analysisSampleId", request.getAnalysisSampleId());
        qr.add(qury);
        if (!StringUtils.isEmpty(request.getGene()))
        {
            Pattern pattern = Pattern.compile("^.*" + request.getGene() + ".*$", Pattern.CASE_INSENSITIVE);
            BasicDBObject qury1 = new BasicDBObject();
            qury1.put("Gene", pattern);
            qr.add(qury1);
        }
        if (!StringUtils.isEmpty(request.getStartCopyNumber()))
        {
            BasicDBObject minObj =
                new BasicDBObject("CopyRatio(" + mainDivisionData.getDataCode() + ")", new BasicDBObject("$gte", request.getStartCopyNumber()));
            qr.add(minObj);
        }
        if (!StringUtils.isEmpty(request.getEndCopyNumber()))
        {
            BasicDBObject maxObj =
                new BasicDBObject("CopyRatio(" + mainDivisionData.getDataCode() + ")", new BasicDBObject("$lte", request.getEndCopyNumber()));
            qr.add(maxObj);
        }
        
        if (!StringUtils.isEmpty(request.getStartPValue()))
        {
            BasicDBObject minObj = new BasicDBObject("P-value(" + mainDivisionData.getDataCode() + ")", new BasicDBObject("$gte", request.getStartPValue()));
            qr.add(minObj);
        }
        if (!StringUtils.isEmpty(request.getEndPValue()))
        {
            BasicDBObject maxObj = new BasicDBObject("P-value(" + mainDivisionData.getDataCode() + ")", new BasicDBObject("$lte", request.getEndPValue()));
            qr.add(maxObj);
        }
        
        BasicDBObject quryList = new BasicDBObject("$and", qr);
        long totalCount = 0;
        map.put("genes", new Long(collection.distinct("Gene", quryList).size()));
        map.put("areas", new Long(collection.distinct("Region", quryList).size()));
        Iterator<DBObject> iterator = null;
        if (Collections3.isNotEmpty(qr))
        {
            totalCount = collection.find(quryList).count();
            iterator = collection.find(quryList).skip((page - 1) * pageSize).limit(pageSize).iterator();
        }
        else
        {
            iterator = collection.find().skip((page - 1) * pageSize).limit(pageSize).iterator();
        }
        List<Map> records = new ArrayList<Map>();
        while (iterator.hasNext())
        {
            DBObject obj = iterator.next();
            try
            {
                records.add(obj.toMap());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        pager.setPageNo(page);
        pager.setPageSize(pageSize);
        pager.setRecords(records);
        pager.setTotalCount((int)totalCount);
        map.put("pager", pager);
        return map;
    }
    
    public List<BioInfoAnnotate> getAnnotateByfamily(String mongoId)
    {
        List<BioInfoAnnotate> list = new ArrayList<BioInfoAnnotate>();
        List<BiologyFamilyAnalysisSampleRelation> relations = biologyFamilyAnalysisSampleRelationMapper.getRelationByFamilyId(mongoId);
        if (Collections3.isNotEmpty(relations))
        {
            for (BiologyFamilyAnalysisSampleRelation familyRelation : relations)
            {
                BioInfoAnnotate annotate = bioInfoAnnotateMapper.getByAnalaysisSampleId(familyRelation.getSampleId());
                list.add(annotate);
            }
        }
        else
        {
            BiologyDivisionFastqData divison = biologyDivisionFastqDataMapper.selectByPrimaryKey(mongoId);
            BioInfoAnnotate annotate = bioInfoAnnotateMapper.getAnnotateBySampleCode(divison.getDataCode());
            if (!StringUtils.isEmpty(annotate))
            {
                list.add(annotate);
            }
            
        }
        
        return list;
    }
    
    public void collectionCapcnvPic(CollectionCapcnvPicRequest request)
    {
        CnvAnalysisPic cnvAnalysisPic = cnvAnalysisPicMapper.selectByPrimaryKey(request.getCnvAnalysisPicId());
        if (cnvAnalysisPic != null)
        {
            cnvAnalysisPic.setIsCollection(request.getIfCollection());
            cnvAnalysisPic.setUpdateTime(new Date());
            cnvAnalysisPic.setClinicalJudgment(request.getClinicalJudgment());
            cnvAnalysisPicMapper.updateByPrimaryKey(cnvAnalysisPic);
        }
        else
        {
            log.warn("根据：cnvAnalysisPicId" + request.getCnvAnalysisPicId() + ",查询记录不存在！");
        }
    }
    
    //遍历cnvAnalysisPics
    //如果图片已收藏，设置CnvAnalysisPicResultList.cnvAnalysisPic的属性；
    //根据CnvAnalysisPic.id查找CnvAnalysisResult记录，遍历CnvAnalysisResult记录
    //根据CnvAnalysisResult.id查找technicalAnalyRecord记录，遍历technicalAnalyRecord，
    // technicalAnalyRecord根据technicalAnalyRecord.id查找TechnicalAnalyVerify记录,遍历TechnicalAnalyVerify记录
    //统计TechnicalAnalyVerify设置CollectionCnvAnalysisResult的属性
    public List<CollectionCnvAnalysisPicResultList> collectionCapcnvData(String analysisSampleId)
    {
        List<CnvAnalysisPic> cnvAnalysisPics = cnvAnalysisPicMapper.getBySampleTestId(analysisSampleId);
        return collectionCapcnvData(cnvAnalysisPics);
    }
    
    public List<CollectionCnvAnalysisPicResultList> collectionCapcnvData(List<CnvAnalysisPic> cnvAnalysisPics)
    {
        List<CollectionCnvAnalysisPicResultList> collectionCnvAnalysisPicResultLists = new ArrayList<CollectionCnvAnalysisPicResultList>();
        if (cnvAnalysisPics != null && cnvAnalysisPics.size() > 0)
        {
            for (CnvAnalysisPic cnvAnalysisPic : cnvAnalysisPics)
            {
                CollectionCnvAnalysisPicResultList collectionCnvAnalysisPicResultList = new CollectionCnvAnalysisPicResultList();
                List<CollectionCnvAnalysisResult> collectionCnvAnalysisResults = new ArrayList<CollectionCnvAnalysisResult>();
                collectionCnvAnalysisPicResultList.setCollectionCnvAnalysisResult(collectionCnvAnalysisResults);
                if (cnvAnalysisPic.getIsCollection() == 1)
                { //图片已收藏
                    collectionCnvAnalysisPicResultList.setCnvAnalysisPic(cnvAnalysisPic);
                }
                List<CnvAnalysisResult> cnvResultLists = cnvAnalysisResultMapper.getListByCnvAnalysisPicId(cnvAnalysisPic.getId());
                for (CnvAnalysisResult cnvAnalysisResult : cnvResultLists)
                {
                    List<TechnicalAnalyRecordWithBLOBs> resultList = technicalAnalyRecordMapper.getByCnvResultId(cnvAnalysisResult.getId());
                    CollectionCnvAnalysisResult collectionCnvAnalysisResult = null;
                    if (resultList != null && resultList.size() > 0)
                    {
                        for (TechnicalAnalyRecordWithBLOBs technicalAnalyRecordWithBLOBs : resultList)
                        {
                            collectionCnvAnalysisResult = new CollectionCnvAnalysisResult();
                            BeanUtils.copyProperties(cnvAnalysisResult, collectionCnvAnalysisResult);
                            String isFamilyAnalysis = "否";
                            String familyMethod = "";
                            String verifySample = "";
                            if (technicalAnalyRecordWithBLOBs.getVerify().equals("验证"))
                            {
                                isFamilyAnalysis = "是";
                                familyMethod = technicalAnalyRecordWithBLOBs.getVerifyMethod();
                                List<TechnicalAnalyVerify> technicalAnalyVerifys =
                                    technicalAnalyVerifyMapper.selectByRecord(technicalAnalyRecordWithBLOBs.getId());
                                for (TechnicalAnalyVerify technicalAnalyVerify : technicalAnalyVerifys)
                                {
                                    verifySample += technicalAnalyVerify.getSampleFamilyRelation() + ",";
                                }
                                if (verifySample.contains(","))
                                {
                                    verifySample = verifySample.substring(0, verifySample.lastIndexOf(","));
                                }
                            }
                            else if (technicalAnalyRecordWithBLOBs.getVerify().equals("不验证"))
                            {
                                isFamilyAnalysis = "否";
                            }
                            // collectionCnvAnalysisResult.setId(technicalAnalyRecordWithBLOBs.getId());
                            collectionCnvAnalysisResult.setIsFamilyAnalysis(isFamilyAnalysis);
                            collectionCnvAnalysisResult.setFamilyMethod(familyMethod);
                            collectionCnvAnalysisResult.setVerifySample(verifySample);
                            collectionCnvAnalysisResult.setTechnicalAnalyRecordId(technicalAnalyRecordWithBLOBs.getId());
                            collectionCnvAnalysisResults.add(collectionCnvAnalysisResult);
                        }
                        
                    }
                }
                if (collectionCnvAnalysisPicResultList.getCnvAnalysisPic() != null
                    || (collectionCnvAnalysisPicResultList.getCollectionCnvAnalysisResult() != null && !collectionCnvAnalysisPicResultList.getCollectionCnvAnalysisResult()
                        .isEmpty()))
                {
                    collectionCnvAnalysisPicResultLists.add(collectionCnvAnalysisPicResultList);
                }
            }
        }
        return collectionCnvAnalysisPicResultLists;
    }
    
    public List<CollectionCnvAnalysisPicResultList> collectionReportCapcnvData(ReportCollectionCapcnvDataRequest reportCollectionCapcnvDataRequest)
    {
        List<CollectionCnvAnalysisPicResultList> collectionCnvAnalysisPicResultLists = new ArrayList<CollectionCnvAnalysisPicResultList>();
        if (reportCollectionCapcnvDataRequest.getCnvAnalysisPicIds() != null && reportCollectionCapcnvDataRequest.getCnvAnalysisPicIds().size() > 0)
        {
            List<CnvAnalysisPic> cnvAnalysisPics = cnvAnalysisPicMapper.getByIds(reportCollectionCapcnvDataRequest.getCnvAnalysisPicIds());
            collectionCnvAnalysisPicResultLists = collectionCapcnvData(cnvAnalysisPics);
        }
        return collectionCnvAnalysisPicResultLists;
    }
    
    public List<TechnicalAnalysisTask> getTaskByFamilyId(String familyAnalysisId)
    {
        List<TechnicalAnalysisTask> result = technicalAnalysisTaskMapper.getTaskByFamilyId(familyAnalysisId);
        return result;
    }
    
    public void technicalReportOperate(TechnicalReportRequest request, String token)
    {
        //异常上报
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        
        //1.处理技术分析任务状态 2.异常上报
        if (!StringUtils.isEmpty(request.getReportState()))
        {
            String taskIds[] = request.getReportState().split(",");
            for (String taskId : taskIds)
            {
                TechnicalAnalysisTask analysisTask = technicalAnalysisTaskMapper.selectByPrimaryKey(taskId);
                analysisTask.setStatus(7);//暂时用7异常上报 等搞清楚技术分析的各个状态值含义
                analysisTask.setEndTime(new Date());
                analysisTask.setEndType(2);// 任务结束类型：1-正常结束 2-异常结束
                //                analysisTask.setResubmit(1);
                //                analysisTask.setResubmitCount(null==analysisTask.getResubmitCount()?1:analysisTask.getResubmitCount().intValue()+1);
                technicalAnalysisTaskMapper.updateByPrimaryKey(analysisTask);
                
                //保存操作记录表
                TechnicalAnalysisTaskFailOperate operate = new TechnicalAnalysisTaskFailOperate();
                operate.setId(IdGen.uuid());
                operate.setTaskId(taskId);
                operate.setOperatorId(loginer.getId());
                operate.setOperatorName(loginer.getName());
                operate.setOperatorType(1);//异常上报
                operate.setRemark(request.getRemark());
                operate.setCreateTime(new Date());
                failOperateMapper.insert(operate);
                
                testingAdapter.taskFailReport(taskId, request.getRemark());
            }
        }
    }
    
    public void reTodoTechnicalAnalysis(String id, String token)
    {
        //1.
        
    }
    
    public List<BiologyAnalysisSearchSheet> completeSheetPaging(TestingSheetRequest request)
    {
        if (!StringUtils.isEmpty(request.getAssignTimeStart()))
        {
            request.setAssignTimeStart(request.getAssignTimeStart().replace("/", "-") + " 00:00:00");
        }
        if (!StringUtils.isEmpty(request.getAssignTimeEnd()))
        {
            request.setAssignTimeEnd(request.getAssignTimeEnd().replace("/", "-") + " 59:59:59");
        }
        return technicalAnalysisTaskMapper.completeSheetPage(request);
    }
    
    public TestingTaskModel getTaskById(String testingTaskId)
    {
        
        try
        {
            TestingTaskModel model = new TestingTaskModel();
            TechnicalAnalysisTask task = technicalAnalysisTaskMapper.selectByPrimaryKey(testingTaskId);
            model.setId(task.getId());
            model.setStartTime(task.getStartTime());
            model.setSemantic(TECHNICAL_ANALYSIS_SEMANTIC);
            model.setName(TECHNICAL_ANALYSIS_NAME);
            model.setEndTime(task.getEndTime());
            model.setEndType(task.getEndType());
            model.setReceivedSampleCode(task.getReceivedSampleCode());
            if (task.getResubmit() == 0)
            {
                model.setResubmit(false);
            }
            else if (task.getResubmit() == 1)
            {
                model.setResubmit(true);
            }
            model.setResubmitCount(task.getResubmitCount());
            model.setStatus(task.getStatus());
            return model;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        
    }
    
    public List<TechnicalAnalysisSheetModel> getSheetListByTaskId(String id)
    {
        TechnicalAnalysisTask task = technicalAnalysisTaskMapper.selectByPrimaryKey(id);
        if (!StringUtils.isEmpty(task) && !StringUtils.isEmpty(task.getFamilyGroupId()))
        {
            List<TechnicalAnalysisSheetModel> sheet = analysisSheetMapper.getSheetByFamilyGroupId(task.getFamilyGroupId());
            if (sheet != null)
            {
                for (TechnicalAnalysisSheetModel sm : sheet)
                {
                    sm.setSubmitTime(task.getEndTime());
                }
                return sheet;
            }
        }
        
        return null;
    }
    
    public TechnicalAnalysisSheetModel getTestingSheet(String id)
    {
        TechnicalAnalysisSheetModel sheetModel = analysisSheetMapper.getSheet(id);
        if (!StringUtils.isEmpty(sheetModel))
        {
            List<TechnicalAnalysisTask> tasks = technicalAnalysisTaskMapper.getTaskByFamilyIdIncludeError(sheetModel.getTaskId());
            if (!StringUtils.isEmpty(tasks.get(0).getEndTime()))
            {
                sheetModel.setSubmitTime(tasks.get(0).getEndTime());
            }
        }
        return sheetModel;
    }
    
    public Boolean checkTechnicalAnalysisIsFinished(String analysisSampleId)
    {
        CnvAnalysisMonitor monitor = cnvAnalysisMonitorMapper.getCurrentTaskBySampleAnalysisId(analysisSampleId);
        BioInfoAnnotateHpoMonitor hpo = bioInfoAnnotateHpoMonitorMapper.getBySampleAnalysisId(analysisSampleId);
        
        return StringUtils.isEmpty(monitor) && StringUtils.isEmpty(hpo); //都为空为true
    }
    
    public Boolean checkTechnicalAnalysisHasHpoTask(String analysisSampleId)
    {
        BioInfoAnnotateHpoMonitor hpo = bioInfoAnnotateHpoMonitorMapper.getBySampleAnalysisId(analysisSampleId);
        return StringUtils.isEmpty(hpo); //都为空为true
    }
    
    public String getFamily(String id)
    {
        return technicalAnalysisTaskMapper.getFamily(id);
    }
    
    public TechnicalAnalysisTask getTask(String familyGroupId)
    {
        List<TechnicalAnalysisTask> technicalAnalysisTasks = technicalAnalysisTaskMapper.getTaskByFamilyId(familyGroupId);
        if (technicalAnalysisTasks.size() > 0)
            return technicalAnalysisTasks.get(0);
        return null;
    }
    
    public List<TechnicalAnalysisTask> searchErrorTechnicalTask(String familyAnalysisId)
    {
        List<TechnicalAnalysisTask> technicalAnalysisTasks = technicalAnalysisTaskMapper.searchErrorTechnicalTask(familyAnalysisId);
        return technicalAnalysisTasks;
    }
}
