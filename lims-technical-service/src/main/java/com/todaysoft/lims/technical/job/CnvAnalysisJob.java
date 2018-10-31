package com.todaysoft.lims.technical.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.technical.facade.DataAnalysisFacade;

@Component
public class CnvAnalysisJob
{
    @Autowired
    private DataAnalysisFacade dataAnalysisFacade;

    private static Logger log = LoggerFactory.getLogger(CnvAnalysisJob.class);

    @Scheduled(cron = "0 0/2 * * * ? ")
    public void execute()
    {
        log.info(" start search cnvReanalysis ");
        try {
            dataAnalysisFacade.getReCnvAnalysisResult();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
