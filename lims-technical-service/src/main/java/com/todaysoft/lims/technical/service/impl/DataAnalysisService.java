package com.todaysoft.lims.technical.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.todaysoft.lims.technical.facade.DataAnalysisFacade;
import com.todaysoft.lims.technical.model.request.UploadEvidenceRequest;
import com.todaysoft.lims.technical.model.response.UploadEvidenceRespModel;
import com.todaysoft.lims.technical.service.IDataAnalysisService;
import com.todaysoft.lims.technical.utils.HttpRequestAPI;
import com.todaysoft.lims.technical.utils.JsonUtils;
import com.todaysoft.lims.technical.utils.StringUtils;

@Service
public class DataAnalysisService implements IDataAnalysisService
{
    @Autowired
    private MongoTemplate template;
    
    private static Logger log = LoggerFactory.getLogger(DataAnalysisFacade.class);
    
    @Override
    public String getEvidenceByMongoId(String mongoId)
    {
        String result = "";
        DBCollection collection = template.getCollection("analysis-mutation");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", new ObjectId(mongoId));
        DBObject dbObj = collection.findOne(queryCondition);
        if (StringUtils.isNotEmpty(dbObj.get("Mygeno_InterACMG")))
        {
            result = dbObj.get("Mygeno_InterACMG").toString();
        }
        return result;
    }
    
    @Override
    public UploadEvidenceRespModel uploadEvidence(UploadEvidenceRequest request)
    {
        Map<String, List<Map>> httpRequest = new HashMap<>();
        List<Map> list = new ArrayList<>();
        for (int i = 0; i <= request.getEvidence().size() - 1; i++)
        {
            Map<String, String> map = new HashMap<>();
            map.put("key", request.getEvidence().get(i));
            map.put("value", request.getEvidenceValue().get(i));
            list.add(map);
            
        }
        httpRequest.put("data", list);
        log.info("调用致病性分级接口参数:" + JsonUtils.toJson(httpRequest));
        UploadEvidenceRespModel resp = HttpRequestAPI.httpPost(HttpRequestAPI.DISEASE_LEVEL, JsonUtils.toJson(httpRequest), UploadEvidenceRespModel.class);
        log.info("调用致病性分级接口结果:" + resp);
        if ("0000".equals(resp.getStatusCode()))
        {
            if (!"5".equals(resp.getData().getStatus().getState()))
            {
                DBCollection mutationCollection = template.getCollection("analysis-mutation");
                // 更新mongo数据
                BasicDBObject dbQuery = new BasicDBObject("_id", new ObjectId(request.getMongoId()));
                log.info("调用致病性分级---突变数据ID:" + request.getMongoId());
                
                BasicDBObject updataOb = new BasicDBObject();
                
                Map<String, Object> map = new HashMap<>();
                String name = new String();
                for (int i = 0; i <= request.getEvidence().size() - 1; i++)
                {
                    if (i == 0)
                    {
                        name = request.getEvidence().get(i);
                    }
                    else
                    {
                        name = name + "," + request.getEvidence().get(i);
                    }
                    Map<String, String> mapScore = new HashMap<>();
                    mapScore.put("score", request.getEvidenceValue().get(i));
                    mapScore.put("note", request.getDesc().get(i));
                    map.put(request.getEvidence().get(i), mapScore);
                    
                }
                map.put("name", name);
                updataOb.append("Mygeno_InterACMG", map);
                switch (resp.getData().getStatus().getState())
                {
                
                    case "0":
                        updataOb.append("Pathogenic_Analysis", "Likely pathogenic");
                        updataOb.append("pa_sort", 2);
                        break;
                    case "1":
                        updataOb.append("Pathogenic_Analysis", "Pathogenic");
                        updataOb.append("pa_sort", 1);
                        break;
                    case "2":
                        updataOb.append("Pathogenic_Analysis", "Uncertain");
                        updataOb.append("pa_sort", 5);
                        break;
                    case "3":
                        updataOb.append("Pathogenic_Analysis", "Benign");
                        updataOb.append("pa_sort", 4);
                        break;
                    case "4":
                        updataOb.append("Pathogenic_Analysis", "Likely Benign");
                        updataOb.append("pa_sort", 3);
                        break;
                
                }
                
                BasicDBObject newDocument = new BasicDBObject("$set", updataOb);
                
                mutationCollection.update(dbQuery, newDocument, true, true);
            }
            
        }
        return resp;
    }
    
    @Override
    public String getMutationDetail(String objectId)
    {
        DBCollection collection = template.getCollection("analysis-mutation");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", new ObjectId(objectId));
        DBObject dbObj = collection.findOne(queryCondition);
        return dbObj.toString();
        
    }
}
