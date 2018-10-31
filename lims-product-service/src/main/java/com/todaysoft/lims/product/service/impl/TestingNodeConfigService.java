package com.todaysoft.lims.product.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.product.dao.searcher.TestingNodeReagentKitConfigSearcher;
import com.todaysoft.lims.product.entity.APPSaleman;
import com.todaysoft.lims.product.entity.ReagentKit;
import com.todaysoft.lims.product.entity.TaskConfig;
import com.todaysoft.lims.product.entity.TaskInputConfig;
import com.todaysoft.lims.product.entity.TestingNodeUserGroupConfig;
import com.todaysoft.lims.product.entity.User;
import com.todaysoft.lims.product.entity.UserGroup;
import com.todaysoft.lims.product.entity.UserGroupMember;
import com.todaysoft.lims.product.entity.UserState;
import com.todaysoft.lims.product.model.TestingNodeReagentKitConfig;
import com.todaysoft.lims.product.model.request.TestingNodeReagentKitConfigRequest;
import com.todaysoft.lims.product.model.request.TestingNodeUserGroupConfigRequest;
import com.todaysoft.lims.product.service.ITaskConfigService;
import com.todaysoft.lims.product.service.ITestingNodeConfigService;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingNodeConfigService implements ITestingNodeConfigService
{
    private static Logger log = LoggerFactory.getLogger(TestingNodeConfigService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITaskConfigService taskConfigService;
    
    @Override
    public List<TestingNodeReagentKitConfig> search(TestingNodeReagentKitConfigRequest request)
    {
        // 根据前置节点获取样本类型
        if (StringUtils.isEmpty(request.getTestingSample()) && !StringUtils.isEmpty(request.getTestingPrenode()))
        {
            TaskConfig config = taskConfigService.getBySemantic(request.getTestingPrenode());
            
            if (null != config)
            {
                request.setTestingSample(config.getOutputSampleId());
            }
        }
        
        TestingNodeReagentKitConfigSearcher searcher = new TestingNodeReagentKitConfigSearcher();
        searcher.setTestingNode(request.getTestingNode());
        searcher.setTestingSample(request.getTestingSample());
        List<TaskInputConfig> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingNodeReagentKitConfig config;
        List<TestingNodeReagentKitConfig> configs = new ArrayList<TestingNodeReagentKitConfig>();
        
        for (TaskInputConfig record : records)
        {
            config = new TestingNodeReagentKitConfig();
            config.setId(record.getReagentKitId());
            config.setName(getReagentKitName(record.getReagentKitId()));
            config.setSampleInputQuantity(record.getInputSize());
            config.setSampleInputVolume(record.getInputVolume());
            configs.add(config);
        }
        
        return configs;
    }
    
    private String getReagentKitName(String id)
    {
        ReagentKit kit = baseDaoSupport.get(ReagentKit.class, id);
        return null == kit ? null : kit.getName();
    }
    
    @Override
    public List<TestingNodeUserGroupConfig> getUserBySemantic(TestingNodeUserGroupConfigRequest request)
    {
        if (null == request)
        {
            throw new IllegalArgumentException();
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to search testing node users for semantic, {}", request.getSemantic());
        }
        
        List<TestingNodeUserGroupConfig> users = new ArrayList<TestingNodeUserGroupConfig>();
        TaskConfig task = taskConfigService.getTaskBySemantic(request.getSemantic());
        UserGroup userGroup = task.getUserGroup();
        
        if (userGroup != null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("User group is exists, return users in the group, group id {}", userGroup.getId());
            }
            
            if (userGroup.getGroupLeader().getState().equals(UserState.ENABLED))
            {
                TestingNodeUserGroupConfig leader = new TestingNodeUserGroupConfig();
                leader.setId(userGroup.getGroupLeader().getId());
                leader.setName(userGroup.getGroupLeaderName());
                users.add(leader);
            }
            
            List<UserGroupMember> members = userGroup.getUserGroupMembers();
            
            if (log.isDebugEnabled())
            {
                log.debug("User group contains {} members", CollectionUtils.isEmpty(members) ? 0 : members.size());
            }
            
            for (UserGroupMember u : members)
            {
                if (u.getUser().getState().equals(UserState.ENABLED))
                {
                    TestingNodeUserGroupConfig member = new TestingNodeUserGroupConfig();
                    member.setId(u.getUser().getId());
                    member.setName(u.getUserName());
                    users.add(member);
                }
            }
            
            if (log.isDebugEnabled())
            {
                List<String> names = new ArrayList<String>();
                
                for (TestingNodeUserGroupConfig user : users)
                {
                    names.add(user.getName());
                }
                
                log.debug("Return users in group {}, names {}", userGroup.getId(), StringUtils.join(names, ","));
            }
        }
        else
        {
            List<User> users2 = baseDaoSupport.find(User.class);
            Iterator<User> it = users2.iterator();
            
            while (it.hasNext())
            {
                User u = it.next();
                APPSaleman entity = baseDaoSupport.get(APPSaleman.class, u.getId());
                if (entity != null)
                {
                    it.remove();
                }
            }
            for (User u : users2)
            {
                if (u.getState().equals(UserState.ENABLED))
                {
                    TestingNodeUserGroupConfig member = new TestingNodeUserGroupConfig();
                    member.setId(u.getId());
                    member.setName(u.getArchive().getName());
                    users.add(member);
                }
            }
            
            if (log.isDebugEnabled())
            {
                List<String> names = new ArrayList<String>();
                
                for (TestingNodeUserGroupConfig user : users)
                {
                    names.add(user.getName());
                }
                
                log.debug("Return users without salesman, names {}", StringUtils.join(names, ","));
            }
        }
        
        return users;
    }
}
