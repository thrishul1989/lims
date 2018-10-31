package com.todaysoft.lims.gateway.modules.report;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class ReportDistributeConfigProvider implements DistributeConfigProvider
{
    private static final String REPORT_SERVICE_NAME = "lims-report-service";
    
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getDataTemplateDistributeConfig());
        configs.add(getReportTemplateDistributeConfig());
        configs.add(getReportTemplateDefineDistributeConfig());
        configs.add(getBuiltinVariableDistributeConfig());
        configs.add(getDataSendingDistributeConfig());
        configs.add(getConcludingReportDistributeConfig());
        return configs;
    }
    
    private DistributeConfig getDataTemplateDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(REPORT_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/report/dataTemplate/**/*"));
        return config;
    }
    
    private DistributeConfig getReportTemplateDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(REPORT_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/report/reportTemplate/**/*"));
        return config;
    }
    
    private DistributeConfig getReportTemplateDefineDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(REPORT_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/report/reportTemplateDefine/**/*"));
        return config;
    }
    
    private DistributeConfig getBuiltinVariableDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(REPORT_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/report/builtinVariable/**/*"));
        return config;
    }
    
    private DistributeConfig getDataSendingDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(REPORT_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/report/testingDataSend/**/*"));
        return config;
    }
    
    private DistributeConfig getConcludingReportDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(REPORT_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/report/concludingReport/**/*"));
        return config;
    }
    
}
