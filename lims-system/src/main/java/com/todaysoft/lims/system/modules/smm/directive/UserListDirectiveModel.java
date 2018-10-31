package com.todaysoft.lims.system.modules.smm.directive;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.system.modules.smm.model.UserSearcher;
import com.todaysoft.lims.system.modules.smm.model.UserSimpleModel;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.freemarker.VariablesDirectiveModel;
import com.todaysoft.lims.utils.Collections3;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.Version;

@Component
public class UserListDirectiveModel extends VariablesDirectiveModel
{
    
    @Autowired
    private IUserService service;
    
    @Override
    protected Map<String, TemplateModel> getVariables(Map<String, TemplateModel> parameters, Environment env) throws TemplateException
    {
        Map<String, TemplateModel> variables = super.getVariables(parameters, env);
        Integer state = getIntegerParameter("state", parameters);
        UserSearcher searcher = new UserSearcher();
        searcher.setState(state);
        List<UserSimpleModel> users = service.selectList(searcher);
        
        if (Collections3.isEmpty(users))
        {
            users = Collections.emptyList();
        }
        
        variables.put("users", new DefaultObjectWrapperBuilder(new Version("2.3.23")).build().wrap(users));
        return variables;
    }
}
