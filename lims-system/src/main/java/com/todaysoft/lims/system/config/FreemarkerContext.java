package com.todaysoft.lims.system.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.todaysoft.lims.system.modules.bcm.directive.SampleListDirectiveModel;
import com.todaysoft.lims.system.modules.bcm.directive.SampleListTextDirectiveModel;
import com.todaysoft.lims.system.modules.bcm.directive.TestingNodeReagentKitConfigsDirectiveModel;
import com.todaysoft.lims.system.modules.bcm.directive.TestingNodeUserGroupConfigsDirectiveModel;
import com.todaysoft.lims.system.modules.bpm.directive.FreeMarkCollectionCountModel;
import com.todaysoft.lims.system.modules.bpm.directive.FreeMarkIfColletionModel;
import com.todaysoft.lims.system.modules.smm.directive.AmountToYuanDirectiveModal;
import com.todaysoft.lims.system.modules.smm.directive.DictEntriesDirectiveModel;
import com.todaysoft.lims.system.modules.smm.directive.DictEntryTextDirectiveModel;
import com.todaysoft.lims.system.modules.smm.directive.FileDownloadDirectiveModel;
import com.todaysoft.lims.system.modules.smm.directive.FormatAmountDirectiveModal;
import com.todaysoft.lims.system.modules.smm.directive.SecurityResourceDirectiveModel;
import com.todaysoft.lims.system.modules.smm.directive.TestingTypeDirectiveModel;
import com.todaysoft.lims.system.modules.smm.directive.UserListDirectiveModel;
import com.todaysoft.lims.system.service.freemarker.DisplayTextDirectiveModel;

@Configuration
public class FreemarkerContext
{
    @Autowired
    private DictEntryTextDirectiveModel dictEntryTextDirectiveModel;
    
    @Autowired
    private DictEntriesDirectiveModel dictEntriesDirectiveModel;
    
    @Autowired
    private SampleListTextDirectiveModel sampleListTextDirectiveModel;
    
    @Autowired
    private SampleListDirectiveModel sampleListDirectiveModel;
    
    @Autowired
    private UserListDirectiveModel userListDirectiveModel;
    
    @Autowired
    private FreeMarkIfColletionModel freeMarkIfColletionModel;
    
    @Autowired
    private TestingNodeReagentKitConfigsDirectiveModel testingNodeReagentKitConfigsDirectiveModel;
    
    @Autowired
    private SecurityResourceDirectiveModel securityResourceDirectiveModel;
    
    @Autowired
    private FileDownloadDirectiveModel fileDownloadDirectiveModel;
    
    @Autowired
    private TestingTypeDirectiveModel testingTypeDirectiveModel;
    
    @Autowired
    private AmountToYuanDirectiveModal amountToYuanDirectiveModal;
    
    @Autowired
    private FormatAmountDirectiveModal formatAmountDirectiveModal;
    
    @Autowired
    private DisplayTextDirectiveModel displayTextDirectiveModel;
    
    @Autowired
    private TestingNodeUserGroupConfigsDirectiveModel testingNodeUserGroupConfigsDirectiveModel;
    
    @Autowired
    private FreeMarkCollectionCountModel freeMarkCollectionCountModel;
    
    @Bean(name = "freeMarkerConfigurer")
    protected FreeMarkerConfigurer getFreeMarkerConfigurer()
    {
        Properties properties = new Properties();
        properties.setProperty("template_update_delay", "0");
        properties.setProperty("defaultEncoding", "UTF-8");
        properties.setProperty("url_escaping_charset", "UTF-8");
        properties.setProperty("locale", "zh_CN");
        properties.setProperty("boolean_format", "true,false");
        properties.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
        properties.setProperty("date_format", "yyyy-MM-dd");
        properties.setProperty("time_format", "HH:mm:ss");
        properties.setProperty("number_format", "0.##");
        properties.setProperty("whitespace_stripping", "true");
        
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("dict_entry_text", dictEntryTextDirectiveModel);
        variables.put("dict_entries", dictEntriesDirectiveModel);
        variables.put("bcm_samples", sampleListDirectiveModel);
        variables.put("bcm_samples_text", sampleListTextDirectiveModel);
        variables.put("smm_users", userListDirectiveModel);
        variables.put("bcm_testing_rkc", testingNodeReagentKitConfigsDirectiveModel);
        variables.put("bcm_members", testingNodeUserGroupConfigsDirectiveModel);
        variables.put("smm_security_resource", securityResourceDirectiveModel);
        variables.put("download_url", fileDownloadDirectiveModel);
        variables.put("testing_type", testingTypeDirectiveModel);
        variables.put("amount_toyuan", amountToYuanDirectiveModal);
        variables.put("bcm_format_amount", formatAmountDirectiveModal);
        variables.put("base_text", displayTextDirectiveModel);
        variables.put("if_collection", freeMarkIfColletionModel);
        variables.put("doctor_collection_count", freeMarkCollectionCountModel);
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/templates/WEB-INF/views/");
        configurer.setFreemarkerSettings(properties);
        configurer.setFreemarkerVariables(variables);
        return configurer;
    }
}
