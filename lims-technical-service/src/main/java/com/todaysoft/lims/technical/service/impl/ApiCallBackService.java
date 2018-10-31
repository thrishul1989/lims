package com.todaysoft.lims.technical.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.todaysoft.lims.technical.config.ConfigManage;
import com.todaysoft.lims.technical.facade.DataAnalysisFacade;
import com.todaysoft.lims.technical.model.request.MutationCollectionRequest;
import com.todaysoft.lims.technical.model.response.PhenoTypePointCallbackRequest;
import com.todaysoft.lims.technical.model.searcher.SampleSearcher;
import com.todaysoft.lims.technical.mybatis.BioInfoAnnotateHpoMonitorMapper;
import com.todaysoft.lims.technical.mybatis.BioInfoAnnotateMapper;
import com.todaysoft.lims.technical.mybatis.BiologyDivisionFastqDataMapper;
import com.todaysoft.lims.technical.mybatis.BiologyFamilyRelationAnnotateMapper;
import com.todaysoft.lims.technical.mybatis.CnvAnalysisMonitorMapper;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotate;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotateHpoMonitor;
import com.todaysoft.lims.technical.mybatis.entity.BiologyDivisionFastqData;
import com.todaysoft.lims.technical.mybatis.entity.BiologyFamilyRelationAnnotate;
import com.todaysoft.lims.technical.service.IApiCallBackService;
import com.todaysoft.lims.technical.service.IBioInfoAnnotateService;
import com.todaysoft.lims.technical.utils.Collections3;
import com.todaysoft.lims.technical.utils.JsonUtils;
import com.todaysoft.lims.technical.utils.OSSFileLoadUtil;

@Service
public class ApiCallBackService implements IApiCallBackService
{
    
    @Autowired
    private BioInfoAnnotateHpoMonitorMapper bioInfoAnnotateMonitorMapper;
    
    @Autowired
    private IBioInfoAnnotateService bioInfoAnnotateService;
    
    @Autowired
    private CnvAnalysisMonitorMapper cnvAnalysisMonitorMapper;
    
    private static Logger log = LoggerFactory.getLogger(ApiCallBackService.class);
    
    @Autowired
    private BioInfoAnnotateMapper bioInfoAnnotateMapper;
    
    @Autowired
    private BiologyDivisionFastqDataMapper biologyDivisionFastqDataMapper;
    
    @Autowired
    private MongoTemplate template;
    
    @Autowired
    private ConfigManage configManage;
    
    @Autowired
    private DataAnalysisFacade dataAnalysisFacade;
    
    @Autowired
    private BiologyFamilyRelationAnnotateMapper biologyFamilyRelationAnnotateMapper;
    
    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(8, 9, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
        new ThreadPoolExecutor.DiscardOldestPolicy());
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void phenoTypePointCallback(PhenoTypePointCallbackRequest request) throws IOException
    {
        BioInfoAnnotateHpoMonitor monitor = bioInfoAnnotateMonitorMapper.getByMonitorId(request.getTaskId());
        BioInfoAnnotate bioInfoAnnotate = bioInfoAnnotateMapper.selectByPrimaryKey(monitor.getAnnotateId()); //非家系
        BiologyFamilyRelationAnnotate ganalysisFamilyAnnotate = biologyFamilyRelationAnnotateMapper.selectByFamilyId(monitor.getAnnotateId()); //家系
        if (null != monitor && (null != bioInfoAnnotate || null != ganalysisFamilyAnnotate))
        {
            // 正在解析文件的不去操作
            if ((null != bioInfoAnnotate && !"8".equals(bioInfoAnnotate.getAnnotateStatus()))
                || (null != ganalysisFamilyAnnotate && 8 != ganalysisFamilyAnnotate.getStatus()))
            {
                try
                {
                    if (null != bioInfoAnnotate)
                    {
                        // 修改状态为正在解析打分中...防止再次解析引起冲突
                        bioInfoAnnotate.setAnnotateStatus("7");
                        bioInfoAnnotateService.update(bioInfoAnnotate);
                    }
                    if (null != ganalysisFamilyAnnotate)
                    {
                        ganalysisFamilyAnnotate.setStatus(7);
                        biologyFamilyRelationAnnotateMapper.updateBySelective(ganalysisFamilyAnnotate);
                    }
                    // 读取文件
                    InputStream inputStream = OSSFileLoadUtil.getInputStreanmFromALiyun(translate(request.getHpoJson()));
                    
                    // 以流的形式下载文件。
                    byte[] buffer = readInputStream(inputStream);
                    log.info("以流的形式下载文件");
                    List<Map> jsonList = JsonUtils.fromJson(new String(buffer, "UTF-8"), List.class);
                    
                    // 修改mongo数据
                    String analysisSampleId = "";
                    
                    if (null != bioInfoAnnotate)
                    {
                        SampleSearcher searcher = new SampleSearcher();
                        searcher.setDataCode(bioInfoAnnotate.getDataCode());
                        List<BiologyDivisionFastqData> list = biologyDivisionFastqDataMapper.search(searcher);
                        if (Collections3.isNotEmpty(list))
                        {
                            analysisSampleId = Collections3.getFirst(list).getId();
                        }
                    }
                    else if (null != ganalysisFamilyAnnotate)
                    {// 家系
                        analysisSampleId = ganalysisFamilyAnnotate.getGanalysisFamilyId();
                    }
                    DBCollection collection = template.getCollection("analysis-mutation");
                    
                    inputStream.close();
                    log.info("更新打分结束,关闭流文件....");
                    
                    monitor.setEndTime(new Date());
                    monitor.setStatus(true);
                    bioInfoAnnotateMonitorMapper.updateByPrimaryKey(monitor);
                    
                    if (null != bioInfoAnnotate)
                    {
                        bioInfoAnnotate.setAnnotateStatus("5");
                        bioInfoAnnotateMapper.update(bioInfoAnnotate);
                        
                    }
                    else if (null != ganalysisFamilyAnnotate)
                    {
                        ganalysisFamilyAnnotate.setStatus(5);
                        biologyFamilyRelationAnnotateMapper.updateBySelective(ganalysisFamilyAnnotate);
                    }
                    MutationCollectionRequest req = new MutationCollectionRequest();
                    req.setAnalysisSampleId(analysisSampleId);
                    threadPool.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            updateTotalScore(jsonList, req, collection);
                            
                        }
                    });
                    
                }
                catch (Exception e)
                {
                    // 报错还将状态 修改为打分中
                    log.error("解析hpo路径" + request.getHpoJson() + "出错");
                    e.printStackTrace();
                    monitor.setEndTime(null);
                    monitor.setStatus(false);
                    bioInfoAnnotateMonitorMapper.updateByPrimaryKey(monitor);
                    
                    if (null != bioInfoAnnotate)
                    {
                        bioInfoAnnotate.setAnnotateStatus("7");
                        bioInfoAnnotateMapper.update(bioInfoAnnotate);
                    }
                }
            }
        }
        
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateTotalScore(List<Map> jsonList, MutationCollectionRequest request, DBCollection collection)
    {
        BasicDBObject dbQuery = new BasicDBObject();
        for (Map<String, String> json : jsonList)
        {
            dbQuery.put("Begin", json.get("Begin"));
            dbQuery.put("End", json.get("End"));
            dbQuery.put("Chr", json.get("Chr"));
            dbQuery.put("Alt", json.get("Alt"));
            dbQuery.put("Ref", json.get("Ref"));
            dbQuery.put("analysisSampleId", request.getAnalysisSampleId());
            
            BasicDBObject newDocument =
                new BasicDBObject(
                    "$set",
                    new BasicDBObject("Total_Score.EXOMISER_GENE_COMBINED_SCORE", json.get("EXOMISER_GENE_COMBINED_SCORE")).append("Total_Score.Phenolyzer_Score",
                        json.get("Phenolyzer_Score"))
                        .append("Total_Score.Total_Score", json.get("Total_Score")));
            
            collection.update(dbQuery, newDocument, false, true);
            
        }
        log.info("SUCCESS,更新数据结束....");
    }
    
    //"hpoJson": "http://10.10.1.67/cloud_data/hpo/201807/hpo_2368.json"
    private static String translate(String fileName)
    {
        String tname = fileName.substring(fileName.indexOf("cloud_data"), fileName.length());
        tname = "http://60.205.205.23/" + tname;
        return tname;
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
    
}
