package com.todaysoft.lims.biology.service.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.*;

import com.todaysoft.lims.biology.adapter.impl.GatewayService;
import com.todaysoft.lims.biology.model.*;
import com.todaysoft.lims.biology.model.entity.ReceivedSample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.todaysoft.lims.biology.adapter.impl.DictAdapter;
import com.todaysoft.lims.biology.model.BiologyAnnotationFamilyTask;
import com.todaysoft.lims.biology.model.BiologyAnnotationTask;
import com.todaysoft.lims.biology.model.BiologyDivisionFastqData;
import com.todaysoft.lims.biology.model.BiologyFamilyRelationAnnotate;
import com.todaysoft.lims.biology.model.entity.CapCnvData;
import com.todaysoft.lims.biology.model.entity.SiteplotData;
import com.todaysoft.lims.biology.model.response.DictModel;
import com.todaysoft.lims.biology.mybatis.mapper.BioInfoAnnotateMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyAnnotationFamilyTaskMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyAnnotationTaskMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionFastqDataMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyFamilyRelationAnnotateMapper;
import com.todaysoft.lims.biology.mybatis.mapper.entity.BioInfoAnnotate;
import com.todaysoft.lims.biology.service.IBioInfoAnnotateService;
import com.todaysoft.lims.biology.util.OSSFileLoadUtil;
/*import com.todaysoft.lims.config.ConfigManage;*/
import com.todaysoft.lims.utils.Collections3;
import org.springframework.web.client.RestTemplate;

@Service
public class BioInfoAnnotateService implements IBioInfoAnnotateService
{
    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel
    
    @Autowired
    private MongoTemplate template;
    
    @Autowired
    private BiologyDivisionFastqDataMapper divisionFastQdataMapper;
    
    @Autowired
    private BioInfoAnnotateMapper bioInfoAnnotateMapper;
    
    @Autowired
    private BiologyAnnotationTaskMapper biologyAnnotationTaskMapper;
    
    @Autowired
    private BiologyFamilyRelationAnnotateMapper familyRelationAnnotateMapper;
    
    @Autowired
    private BiologyAnnotationFamilyTaskMapper biologyAnnotationFamilyTaskMapper;

    @Autowired
    private RestTemplate restTemplate;

    
    @Autowired
    private DictAdapter dictAdapter;
    
    private static final Logger logger = LoggerFactory.getLogger(BioInfoAnnotateService.class);
    
    @Override
    @Transactional
    public Integer afterAnnotateForSave(BiologyAnnotationTask annotationTask, String... filePath)
    {
        try
        {
            String dataCode = annotationTask.getDataCode();
            BiologyDivisionFastqData biologyDivisionData = divisionFastQdataMapper.selectByDataCode(dataCode);//拆分数据
            BioInfoAnnotate annotate = bioInfoAnnotateMapper.getAnnotateBySampleCode(dataCode); // 注释数据
            StringBuffer uploadDesc = new StringBuffer();
            if (null != biologyDivisionData)
            {
                if (null != biologyDivisionData.getId() && "" != biologyDivisionData.getId())
                {
                    String id = biologyDivisionData.getId();
                    if (Collections3.isNotEmpty(filePath))
                    {
                        for (int i = 0; i < filePath.length; i++)
                        {
                            if (i == 0)
                            {
                                saveMutation(id, filePath[i], dataCode, annotate, uploadDesc);
                            }
                            if (i == 1)
                            {
                                saveCnv(id, filePath[i], dataCode, annotate, uploadDesc);
                            }
                            if (i == 2)
                            {
                                saveSv(id, filePath[i], dataCode, annotate, uploadDesc);
                            }
                            if (i == 3)
                            {
                                saveRegionCount(id, filePath[i], dataCode, annotate, uploadDesc);
                            }
                            if (i == 4)
                            {
                                saveStatistics(id, filePath[i], dataCode, annotate, uploadDesc);
                            }
                        }
                        Integer state = 5;
                        if (null == annotate.getAnnotateStatus())
                        {
                            state = 6;
                        }
                        annotate.setAnnotateStatus(state.toString());
                        annotationTask.setAnnotationState(state);
                        if (state == 6)
                        {
                            annotationTask.setStatus(0);//这里把状态改成未处理0是为了在注释状态出现错误6，但是质控状态合格的情况下能够在列表展示这条数据
                        }
                        if (state == 5)
                        {
                            if(annotationTask.getStatisticsState()!= null && 0 == annotationTask.getStatisticsState())
                            {
                                annotationTask.setStatus(1); //如果质控状态合格，但是出现错误重新解析文件后，把原先的未处理改成已处理，解析完成后从列表中移除
                            }
                        }
                        if (!StringUtils.isEmpty(uploadDesc))
                        {
                            annotate.setUploadDesc(uploadDesc.append("解析出错!").toString());
                            annotationTask.setRemark(uploadDesc.toString());
                        }
                        else
                        {
                            annotate.setUploadDesc("");
                            annotationTask.setRemark("");
                        }
                        bioInfoAnnotateMapper.update(annotate);
                        biologyAnnotationTaskMapper.updateByPrimaryKey(annotationTask);
                    }
                }
            }
            
            return 1;
        }
        catch (Exception e)
        {
            return 0;
        }
        
    }
    
    @Override
    @Transactional
    public void familyAnnotateForSave(BiologyAnnotationFamilyTask familyTask, BiologyFamilyRelationAnnotate familyRelationAnnotate)
    {
        
        //1.家系解析indel文件
        familySaveAnnotaion(familyRelationAnnotate);
        
        //2.家系解析cnv文件
        familySaveCnv(familyRelationAnnotate);
        
        //3.家系解析sv文件
        familySaveSv(familyRelationAnnotate);
        
        //4.家系解析RegionCount文件
        familySaveRegionCount(familyRelationAnnotate);
        
        //5.家系解析Statistics文件
        familySaveStatistics(familyRelationAnnotate);
        
        //6.更新家系状态 如果没出错就是成功
        if (StringUtils.isNotEmpty(familyRelationAnnotate.getAnalysisDesc()))
        {
            familyRelationAnnotate.setDescrption(familyRelationAnnotate.getAnalysisDesc() + " 解析出错");
            familyRelationAnnotate.setStatus(4);
            familyTask.setDescrption(familyRelationAnnotate.getAnalysisDesc() + " 解析出错");
            familyTask.setStatus(4);
        }
        else
        {
            familyRelationAnnotate.setStatus(5);
            familyRelationAnnotate.setDescrption("");
            familyTask.setDescrption("");
            familyTask.setStatus(5);
        }
        familyTask.setEndTime(new Date());
        familyRelationAnnotateMapper.updateByPrimaryKey(familyRelationAnnotate);
        biologyAnnotationFamilyTaskMapper.updateByPrimaryKey(familyTask);
    }
    
    private void familySaveStatistics(BiologyFamilyRelationAnnotate familyRelationAnnotate)
    {
        try
        {
            String familyId = familyRelationAnnotate.getGanalysisFamilyId();
            String path = familyRelationAnnotate.getStatisticsDmdexon();
            if (StringUtils.isNotEmpty(path))
            {
                // 清掉之前保存的数据
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("analysisSampleId").is(familyId);
                query.addCriteria(criteria);
                template.remove(query, "analysis-statistics");
                logger.info("开始解析家系突变Statistics-----familyId：" + familyId);
                List<List<String>> list = uploadStatisticsTxtLikeExcelData(path);
                if (null != list && list.size() > 0)
                {
                    DBCollection collection = template.getCollection("analysis-statistics");
                    List<String> cols = list.get(0);
                    for (int k = 1; k < list.size(); k++)
                    {
                        List<String> sl = list.get(k);
                        BasicDBObject object = new BasicDBObject();
                        object.put("sample", sl.get(0));
                        object.put("rawDataBases", sl.get(1));
                        object.put("cleanDataBases", sl.get(2));
                        object.put("alignedBases", sl.get(3));
                        object.put("aligned", sl.get(4));
                        object.put("initialBases", sl.get(5));
                        object.put("baseCovered", sl.get(6));
                        object.put("coverageRegion", sl.get(7));
                        object.put("effectiveBases", sl.get(8));
                        object.put("fractionEffectiveBases", sl.get(9));
                        object.put("averageSequencingDepth", sl.get(10));
                        object.put("least4X", sl.get(11));
                        object.put("least10X", sl.get(12));
                        object.put("least20X", sl.get(13));
                        checkColName(cols, object, sl);
                        object.put("analysisSampleId", familyId);
                        collection.save(object);
                    }
                    logger.info("结束解析家系Statistics-----familyId：" + familyId);
                }
                logger.info("线程" + Thread.currentThread().getName() + "处理家系id：" + familyId + "完成！！！！！！！！！！！！！！");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            familyRelationAnnotate.setStatisticsDmdexon("");
            familyRelationAnnotate.setStatus(4);
            familyRelationAnnotate.setAnalysisDesc(familyRelationAnnotate.getAnalysisDesc() + " Statistics文件");
        }
    }
    
    private void familySaveRegionCount(BiologyFamilyRelationAnnotate familyRelationAnnotate)
    {
        try
        {
            String familyId = familyRelationAnnotate.getGanalysisFamilyId();
            String path = familyRelationAnnotate.getRegioncountDmdexon();
            if (StringUtils.isNotEmpty(path))
            {
                // 清掉之前保存的数据
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("analysisSampleId").is(familyId);
                query.addCriteria(criteria);
                template.findAllAndRemove(query, SiteplotData.class);
                logger.info("开始解析家系突变Regioncountjson-----familyId：" + familyId);
                List<List<String>> list = uploadTxtLikeExcelData(path);
                if (null != list && list.size() > 0)
                {
                    for (List<String> sl : list)
                    {
                        SiteplotData siteplot = new SiteplotData();
                        siteplot.setChr(sl.get(0));
                        siteplot.setStart(sl.get(1));
                        siteplot.setStop(sl.get(2));
                        siteplot.setGeneSpan(sl.get(3));
                        siteplot.setGene(sl.get(4));
                        siteplot.setExon(sl.get(5));
                        siteplot.setLen(sl.get(6));
                        siteplot.setReadNum(sl.get(7));
                        siteplot.setAnalysisSampleId(familyId);
                        template.save(siteplot);
                    }
                    logger.info("结束解析家系Regioncountjson-----familyId：" + familyId);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            familyRelationAnnotate.setRegioncountDmdexon("");
            familyRelationAnnotate.setStatus(4);
            familyRelationAnnotate.setAnalysisDesc(familyRelationAnnotate.getAnalysisDesc() + " Regioncount文件");
        }
        
    }
    
    private void familySaveSv(BiologyFamilyRelationAnnotate familyRelationAnnotate)
    {
        try
        {
            String familyId = familyRelationAnnotate.getGanalysisFamilyId();
            String path = familyRelationAnnotate.getSvJson();
            if (StringUtils.isNotEmpty(path))
            {
                // 清掉之前保存的数据
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("analysisSampleId").is(familyId);
                query.addCriteria(criteria);
                template.remove(query, "analysis-sv");
                logger.info("开始解析家系svjson-----familyId：" + familyId);
                Object jsonStr = uploadTextData(path);
                DBCollection collection = template.getCollection("analysis-sv");
                if (null != jsonStr)
                {
                    List<Map<String, String>> list = (List<Map<String, String>>)jsonStr;
                    if (null != list)
                    {
                        for (Map<String, String> map : list)
                        {
                            BasicDBObject object = new BasicDBObject();
                            for (Map.Entry<String, String> entry : map.entrySet())
                            {
                                object.put(entry.getKey().toString(), entry.getValue());
                            }
                            object.put("analysisSampleId", familyId);
                            collection.save(object);
                        }
                    }
                    logger.info("结束解析家系svjson-----familyId：" + familyId);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            familyRelationAnnotate.setSvJson("");
            familyRelationAnnotate.setStatus(4);
            familyRelationAnnotate.setAnalysisDesc(familyRelationAnnotate.getAnalysisDesc() + " sv文件");
        }
    }
    
    private void familySaveCnv(BiologyFamilyRelationAnnotate familyRelationAnnotate)
    {
        try
        {
            String familyId = familyRelationAnnotate.getGanalysisFamilyId();
            String path = familyRelationAnnotate.getCnv();
            if (StringUtils.isNotEmpty(path))
            {
                // 清掉之前保存的数据
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("analysisSampleId").is(familyId);
                query.addCriteria(criteria);
                template.remove(query, "analysis-capcnv");
                logger.info("开始解析家系cnvjson-----familyId：" + familyId);
                List<List<String>> list = uploadTxtLikeExcelData(path);
                if (null != list && list.size() > 0)
                {
                    for (List<String> sl : list)
                    {
                        CapCnvData capcnv = new CapCnvData();
                        capcnv.setChrLocation(sl.get(0));
                        capcnv.setGene(sl.get(1));
                        capcnv.setArea(sl.get(2));
                        capcnv.setCopyNumber(new BigDecimal(sl.get(3)).doubleValue());
                        capcnv.setpValue(new BigDecimal(sl.get(4)).doubleValue());
                        capcnv.setAnalysisSampleId(familyId);
                        template.save(capcnv);
                    }
                }
                logger.info("结束解析家系cnvjson-----familyId：" + familyId);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            familyRelationAnnotate.setCnv("");
            familyRelationAnnotate.setStatus(4);
            familyRelationAnnotate.setAnalysisDesc(familyRelationAnnotate.getAnalysisDesc() + " cnv文件");
        }
        
    }
    
    private void familySaveAnnotaion(BiologyFamilyRelationAnnotate familyRelationAnnotate)
    {
        try
        {
            String familyId = familyRelationAnnotate.getGanalysisFamilyId();
            String path = familyRelationAnnotate.getSnvJson();
            if (StringUtils.isNotEmpty(path))
            {
                // 清掉之前保存的数据
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("analysisSampleId").is(familyId);
                query.addCriteria(criteria);
                template.remove(query, "analysis-mutation");
                logger.info("开始解析家系突变json-----familyId：" + familyId);
                Object jsonStr = uploadTextData(path);
                if (null == jsonStr)
                {
                    logger.error("家系突变jsonStr is null");
                }
                DBCollection collection = template.getCollection("analysis-mutation");
                if (null != jsonStr)
                {
                    List<Map<String, Object>> list = (List<Map<String, Object>>)jsonStr;
                    if (null == list)
                    {
                        logger.error("家系突变list is null");
                    }
                    if (null != list)
                    {
                        logger.info("goto list.list size is:" + list.size());
                        Map<String, String> jsonMap = getDictMapByCategory("JSON_KEY");
                        if (null == jsonMap)
                        {
                            logger.error("家系突变jsonMap is null");
                        }
                        for (Map<String, Object> map : list)
                        {
                            BasicDBObject object = new BasicDBObject();
                            replaceKey(map, object, jsonMap);
                            object.put("analysisSampleId", familyId);
                            collection.save(object);
                        }
                    }
                    logger.info("结束解析家系突变json-----familyId：" + familyId);
                }
            }
        }
        catch (Exception e)
        {
            logger.error(" 解析突变json error ");
            e.printStackTrace();
            familyRelationAnnotate.setSnvJson("");
            familyRelationAnnotate.setStatus(4);
            familyRelationAnnotate.setAnalysisDesc(familyRelationAnnotate.getAnalysisDesc() + "snp_indel_Json文件");
        }
    }
    
    private void saveStatistics(String id, String path, String dataCode, BioInfoAnnotate annotate, StringBuffer uploadDesc)
    {
        try
        {
            if (StringUtils.isNotEmpty(path))
            {
                // 清掉之前保存的数据
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("analysisSampleId").is(id);
                query.addCriteria(criteria);
                template.remove(query, "analysis-statistics");
                
                logger.info(dataCode + "开始解析statistics文件");
                List<List<String>> list = uploadStatisticsTxtLikeExcelData(path);
                if (null != list && list.size() > 0)
                {
                    DBCollection collection = template.getCollection("analysis-statistics");
                    List<String> cols = list.get(0);
                    for (int k = 1; k < list.size(); k++)
                    {
                        List<String> sl = list.get(k);
                        BasicDBObject object = new BasicDBObject();
                        object.put("sample", sl.get(0));
                        object.put("rawDataBases", sl.get(1));
                        object.put("cleanDataBases", sl.get(2));
                        object.put("alignedBases", sl.get(3));
                        object.put("aligned", sl.get(4));
                        object.put("initialBases", sl.get(5));
                        object.put("baseCovered", sl.get(6));
                        object.put("coverageRegion", sl.get(7));
                        object.put("effectiveBases", sl.get(8));
                        object.put("fractionEffectiveBases", sl.get(9));
                        object.put("averageSequencingDepth", sl.get(10));
                        object.put("least4X", sl.get(11));
                        object.put("least10X", sl.get(12));
                        object.put("least20X", sl.get(13));
                        checkColName(cols, object, sl);
                        object.put("analysisSampleId", id);
                        collection.save(object);
                    }
                    
                }
                
                logger.info(dataCode + "statistics文件解析完成");
                logger.info("线程" + Thread.currentThread().getName() + "处理数据编号" + dataCode + "完成！！！！！！！！！！！！！！");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            annotate.setStatisticsDmdexon("");
            annotate.setAnnotateStatus(null);
            uploadDesc.append(" Statistics文件");
        }
    }
    
    private void saveRegionCount(String id, String path, String dataCode, BioInfoAnnotate annotate, StringBuffer uploadDesc)
    {
        try
        {
            if (StringUtils.isNotEmpty(path))
            {
                // 清掉之前保存的数据
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("analysisSampleId").is(id);
                query.addCriteria(criteria);
                template.findAllAndRemove(query, SiteplotData.class);
                //
                logger.info(dataCode + "开始解析site文件");
                List<List<String>> list = uploadTxtLikeExcelData(path);
                if (null != list && list.size() > 0)
                {
                    for (List<String> sl : list)
                    {
                        SiteplotData siteplot = new SiteplotData();
                        siteplot.setChr(sl.get(0));
                        siteplot.setStart(sl.get(1));
                        siteplot.setStop(sl.get(2));
                        siteplot.setGeneSpan(sl.get(3));
                        siteplot.setGene(sl.get(4));
                        siteplot.setExon(sl.get(5));
                        siteplot.setLen(sl.get(6));
                        siteplot.setReadNum(sl.get(7));
                        siteplot.setAnalysisSampleId(id);
                        template.save(siteplot);
                    }
                }
                logger.info(dataCode + "site文件解析完成");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            annotate.setRegioncountDmdexon("");
            annotate.setAnnotateStatus(null);
            uploadDesc.append(" Regioncount文件");
        }
    }
    
    private void saveSv(String id, String path, String dataCode, BioInfoAnnotate annotate, StringBuffer uploadDesc)
    {
        try
        {
            if (StringUtils.isNotEmpty(path))
            {
                // 清掉之前保存的数据
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("analysisSampleId").is(id);
                query.addCriteria(criteria);
                template.remove(query, "analysis-sv");
                //
                logger.info(dataCode + "开始解析sv-----");
                Object jsonStr = uploadTextData(path);
                DBCollection collection = template.getCollection("analysis-sv");
                if (null != jsonStr)
                {
                    List<Map<String, String>> list = (List<Map<String, String>>)jsonStr;
                    if (null != list)
                    {
                        for (Map<String, String> map : list)
                        {
                            BasicDBObject object = new BasicDBObject();
                            for (Map.Entry<String, String> entry : map.entrySet())
                            {
                                object.put(entry.getKey().toString(), entry.getValue());
                            }
                            object.put("analysisSampleId", id);
                            collection.save(object);
                        }
                    }
                }
                logger.info(dataCode + "sv解析完成-----");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            annotate.setSvJson("");
            annotate.setAnnotateStatus(null);
            uploadDesc.append(" sv文件");
        }
    }
    
    private void saveCnv(String id, String path, String dataCode, BioInfoAnnotate annotate, StringBuffer uploadDesc)
    {
        try
        {
            if (StringUtils.isNotEmpty(path))
            {
                // 清掉之前保存的数据
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("analysisSampleId").is(id);
                query.addCriteria(criteria);
                template.remove(query, "analysis-capcnv");
                logger.info(dataCode + "开始解析cnv----");
                List<List<String>> list = uploadTxtLikeExcelData(path);
                if (null != list && list.size() > 0)
                {
                    for (List<String> sl : list)
                    {
                        CapCnvData capcnv = new CapCnvData();
                        capcnv.setChrLocation(sl.get(0));
                        capcnv.setGene(sl.get(1));
                        capcnv.setArea(sl.get(2));
                        capcnv.setCopyNumber(new BigDecimal(sl.get(3)).doubleValue());
                        capcnv.setpValue(new BigDecimal(sl.get(4)).doubleValue());
                        capcnv.setAnalysisSampleId(id);
                        template.save(capcnv);
                    }
                }

                logger.info(dataCode + "cnv解析完成");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            annotate.setCnv("");
            annotate.setAnnotateStatus(null);
            uploadDesc.append(" cnv文件");
        }
    }
    
    private void saveMutation(String id, String path, String dataCode, BioInfoAnnotate annotate, StringBuffer uploadDesc)
    {
        try
        {
            if (StringUtils.isNotEmpty(path))
            {
                // 清掉之前保存的数据
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("analysisSampleId").is(id);
                query.addCriteria(criteria);
                template.remove(query, "analysis-mutation");
                //
                Object jsonStr = uploadTextData(path);
                DBCollection collection = template.getCollection("analysis-mutation");
                if (null != jsonStr)
                {
                    List<Map<String, Object>> list = (List<Map<String, Object>>)jsonStr;
                    if (null != list)
                    {
                        logger.info(dataCode + "开始解析突变json-----");
                        Map<String, String> jsonMap = getDictMapByCategory("JSON_KEY");
                        for (Map<String, Object> map : list)
                        {
                            BasicDBObject object = new BasicDBObject();
                            replaceKey(map, object, jsonMap);
                            object.put("analysisSampleId", id);
                            collection.save(object);
                        }
                        logger.info(dataCode + "结束解析突变json-----");
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            annotate.setSnvJson("");
            annotate.setAnnotateStatus(null);
            uploadDesc.append("snp_indel_Json文件");
        }
    }

    // 测试时 地址带文件夹
    public Object uploadTextData(String fileName) throws Exception
    {
        String ss = "";
        if (fileName.lastIndexOf("/") > 0)
        {
            ss = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
        }
        else
        {
            ss = fileName;
        }
        File file = new File(ss);
        String encoding = "UTF-8";
        InputStream in = OSSFileLoadUtil.getInputStreanmFromALiyun(fileName);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        // 以流的形式下载文件。
        byte[] buffer = readInputStream(in);
        os.write(buffer);
        os.flush();

        String r = file.getAbsolutePath();
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream fin = new FileInputStream(r);
        fin.read(filecontent);
        fin.close();
        in.close();
        os.close();
        file.delete();
        Charset charset = Charset.forName(encoding);
        CharsetDecoder charsetDecoder = charset.newDecoder();
        Object parse = JSON.parse(filecontent, 0, filelength.intValue(), charsetDecoder);
        return parse;
    }
    
    /**
     * 从输入流中获取字节数组
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException
    {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1)
        {
            bos.write(buffer, 0, len);
        }
        if (null != bos)
        {
            bos.close();
        }
        return bos.toByteArray();
    }
    
    /* private Map<String, DictModel> replaceMap = new HashMap<String, DictModel>();
     
     private static Map<String, String> dictMap = new ConcurrentHashMap<>();
     */
    public Map<String, String> getDictMapByCategory(String category)
    {
        Map<String, String> dictMap = new HashMap<>();
        if (null != dictMap && dictMap.size() > 0)
        {
            return dictMap;
        }
        else
        {
            List<DictModel> entries = dictAdapter.getDictByCategory(category);
            for (DictModel dict : entries)
            {
                dictMap.put(dict.getText(), dict.getValue());
            }
        }
        return dictMap;
    }
    
    private BasicDBObject replaceKey(Map<String, Object> map, BasicDBObject object, Map<String, String> dictMap)
    {
        
        if (null != map)
        {
            
            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                String value = dictMap.get(entry.getKey());
                /*  dict = replaceMap.get(entry.getKey());
                  if (null == dict)
                  {
                      dict = dictAdapter.getDictByText("JSON_KEY", entry.getKey());
                      if (null != dict)
                      {
                          replaceMap.put(entry.getKey(), dict);
                      }
                      else
                      {
                          dict = new DictModel();
                          dict.setValue(entry.getKey());
                          replaceMap.put(entry.getKey(), dict);
                      }
                      
                  }*/
                
                if (entry.getValue() instanceof String)
                {
                    object.put(null == value ? entry.getKey() : value, entry.getValue());
                    if ("B1".equals(entry.getKey()))
                    {
                        if (null != entry.getValue())
                        {
                            String s = entry.getValue().toString();
                            if ("pathogenic".equals(s.toLowerCase().replace(" ", "")))
                            {
                                object.put("pa_sort", 1);
                            }
                            else if ("likelypathogenic".equals(s.toLowerCase().replace(" ", "")))
                            {
                                object.put("pa_sort", 2);
                            }
                            else if ("likelybenign".equals(s.toLowerCase().replace(" ", "")))
                            {
                                object.put("pa_sort", 3);
                            }
                            else if ("benign".equals(s.toLowerCase().replace(" ", "")))
                            {
                                object.put("pa_sort", 4);
                            }
                            else if ("uncertain".equals(s.toLowerCase().replace(" ", "")))
                            {
                                object.put("pa_sort", 5);
                            }
                            else
                            {
                                object.put("pa_sort", 6);
                            }
                        }
                    }
                }
                else
                {
                    if (entry.getKey().equals("B6"))
                    {
                        // 特殊处理数组
                        Map<String, List<String>> B6Json = new HashMap<String, List<String>>();
                        List<Map<String, String>> list = (List<Map<String, String>>)entry.getValue();
                        for (Map<String, String> m : list)
                        {
                            for (Map.Entry<String, String> en : m.entrySet())
                            {
                                if (null == B6Json.get(en.getKey()))
                                {
                                    List<String> ll = new ArrayList<>();
                                    ll.add(en.getValue());
                                    B6Json.put(en.getKey(), ll);
                                }
                                else
                                {
                                    B6Json.get(en.getKey()).add(en.getValue());
                                }
                            }
                        }
                        
                        Map<String, Object> jsonMap = new HashMap<>();
                        B6Json.forEach((k, v) -> {
                            jsonMap.put(k, Joiner.on(",").join(v));
                        });
                        BasicDBObject o = new BasicDBObject();
                        object.put(value, o);
                        replaceKey(jsonMap, o, dictMap);
                        
                    }
                    else
                    {
                        BasicDBObject o = new BasicDBObject();
                        object.put(null == value ? entry.getKey() : value, o);
                        replaceKey((Map<String, Object>)entry.getValue(), o, dictMap);
                    }
                }
            }
        }
        return object;
    }
    
    // 解析数据类似excel的txt文本
    private List<List<String>> uploadTxtLikeExcelData(String filePath) throws Exception
    {
        List<List<String>> list = new ArrayList<>();
        String encoding = "UTF-8";
        String wholePath = "";
        InputStream in = OSSFileLoadUtil.getInputStreanmFromALiyun(filePath);
        InputStreamReader read = new InputStreamReader(in, encoding);// 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        int i = 0;
        while ((lineTxt = bufferedReader.readLine()) != null)
        {
            if (i != 0)
            {
                List<String> ls = new ArrayList<>();
                String[] arr = lineTxt.split("\\s+");
                if (null != arr && arr.length > 0)
                {
                    for (String s : arr)
                    {
                        ls.add(s);
                    }
                }
                list.add(ls);
            }
            i++;
        }
        read.close();
        return list;
        
    }
    
    // 解析质控的txt文本,不去第一行
    private List<List<String>> uploadStatisticsTxtLikeExcelData(String filePath) throws Exception
    {
        List<List<String>> list = new ArrayList<>();
        String encoding = "UTF-8";
        InputStream in = OSSFileLoadUtil.getInputStreanmFromALiyun(filePath);
        InputStreamReader read = new InputStreamReader(in, encoding);// 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null)
        {
            List<String> ls = new ArrayList<>();
            String[] arr = lineTxt.split("\\\t+");
            if (null != arr && arr.length > 0)
            {
                for (String s : arr)
                {
                    ls.add(s);
                }
            }
            list.add(ls);
        }
        read.close();
        return list;
        
    }
    
    private void checkColName(List<String> cols, BasicDBObject object, List<String> dataList)
    {
        if (Collections3.isNotEmpty(cols))
        {
            int a = 0;
            for (int i = 0; i < cols.size(); i++)
            {
                // duplication rate 去空 转小写
                if ("duplicationrate".equals(cols.get(i).replace(" ", "").toLowerCase()))
                {
                    a = i - 13 - 1;
                }
            }
            if (a > 0)
            {
                String name = cols.get(14);
                object.put("least" + name.substring(name.lastIndexOf("t") + 1).replace(" ", ""), dataList.get(14));
                if (a == 2)
                {
                    String name2 = cols.get(15);
                    object.put("least" + name2.substring(name2.lastIndexOf("t") + 1).replace(" ", ""), dataList.get(15));
                }
                if (a == 3)
                {
                    String name2 = cols.get(15);
                    String name3 = cols.get(16);
                    object.put("least" + name2.substring(name2.lastIndexOf("t") + 1).replace(" ", ""), dataList.get(15));
                    object.put("least" + name3.substring(name3.lastIndexOf("t") + 1).replace(" ", ""), dataList.get(16));
                }
            }
            object.put("duplicationRate", dataList.get(14 + a));
            object.put("chip", dataList.get(15 + a));
            if ((16 + a + 1) == dataList.size())
            {
                object.put("q30", dataList.get(16 + a));
            }
        }
    }

    @Override
    public OrderSampleView getOrderSampleViewBySampleId(String sampleld) {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderSampleViewBySampleId/{sampleld}");
        return restTemplate.getForObject(url, OrderSampleView.class, Collections.singletonMap("sampleld", sampleld));
    }

    @Override
    public ReceivedSample getReceivedSampleBySampleCode(String sampleCode) {
        String url = GatewayService.getServiceUrl("/bpm/testing/getReceivedSampleBySampleCode/{sampleCode}");
        return restTemplate.getForObject(url, ReceivedSample.class, Collections.singletonMap("sampleCode", sampleCode));
    }

    @Override
    public AbnormalSolveRecord getReSampleRecord(String scheduleId) {
        String url = GatewayService.getServiceUrl("/bpm/abnormal/getReSampleRecord/{scheduleId}");
        return restTemplate.getForObject(url, AbnormalSolveRecord.class, Collections.singletonMap("scheduleId", scheduleId));
    }

}
