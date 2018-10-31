package com.todaysoft.lims.technical.job;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.technical.model.response.PhenoTypePointCallbackRequest;
import com.todaysoft.lims.technical.model.response.PhenoTypePointRespModel;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotateHpoMonitor;
import com.todaysoft.lims.technical.service.IApiCallBackService;
import com.todaysoft.lims.technical.service.IPhenotypeService;
import com.todaysoft.lims.technical.utils.HttpRequestAPI;

@Component
public class PhenotypePointJob
{
    
    @Autowired
    private IPhenotypeService phenotypeService;
    
    @Autowired
    private IApiCallBackService apiCallbackService;
    
    private static Logger log = LoggerFactory.getLogger(PhenotypePointJob.class);
    
    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(8, 9, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
        new ThreadPoolExecutor.DiscardOldestPolicy());
    
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void execute()
    {
        log.info(" start execute PhenotypePoint ");
        List<BioInfoAnnotateHpoMonitor> list = phenotypeService.getMonitoringList();
        for (BioInfoAnnotateHpoMonitor monitor : list)
        {
            threadPool.execute(new Runnable()
            {
                @SuppressWarnings("unchecked")
                @Override
                public void run()
                {
                    try
                    {
                        PhenoTypePointRespModel resp =
                            HttpRequestAPI.httpGetByTaskId(HttpRequestAPI.PHENOTYPE_POINT_STATE, monitor.getMonitorTaskId(), PhenoTypePointRespModel.class);
                        if (null != resp && "0000".equals(resp.getStatusCode())
                            && "1".equals(((Map<String, Object>)resp.getData().get("status")).get("state").toString()))
                        {
                            
                            PhenoTypePointCallbackRequest request = new PhenoTypePointCallbackRequest();
                            request.setTaskId(resp.getData().get("taskId").toString());
                            request.setHpoJson(resp.getData().get("hpoJson").toString());
                            apiCallbackService.phenoTypePointCallback(request);
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    
                }
            });
            
        }
    }
}
