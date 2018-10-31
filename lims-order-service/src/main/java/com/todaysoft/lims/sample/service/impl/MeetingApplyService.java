package com.todaysoft.lims.sample.service.impl;

import java.util.List;

import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.MeetingApplySearcher;
import com.todaysoft.lims.sample.entity.APPSaleman;
import com.todaysoft.lims.sample.entity.TestingType;
import com.todaysoft.lims.sample.entity.meeting.MeetingApply;
import com.todaysoft.lims.sample.entity.meeting.MeetingApplyCheck;
import com.todaysoft.lims.sample.entity.meeting.MeetingDispatchUser;
import com.todaysoft.lims.sample.model.meeting.MeetingApplyResponseRequest;
import com.todaysoft.lims.sample.service.IMeetingApplyService;
import com.todaysoft.lims.sample.service.adapter.ProductAdapter;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class MeetingApplyService implements IMeetingApplyService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ProductAdapter productService;
    
    @Override
    public Pagination<MeetingApply> paging(MeetingApplySearcher request)
    {
        Pagination<MeetingApply> paging =
            baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), MeetingApply.class);
        List<MeetingApply> meetingApplies = paging.getRecords();
        List<TestingType> testingTypeList = productService.testingTypeList();
        for (MeetingApply m : meetingApplies)
        {
            //根据状态赋值中文
            
            switch (m.getStatus())
            {
                case 0:
                    m.setStatusName("审核中");
                    break;
                case 1:
                    m.setStatusName("已通过");
                    break;
                case 2:
                    m.setStatusName("未通过");
                    break;
                case 3:
                    m.setStatusName("已响应");
                    break;
                case 4:
                    m.setStatusName("已完成");
                    break;
            }
            //通过申请人id 在APP_BUSINESS_CONFIG获取营销中心id,在LIMS_TESTING_TYPE获取营销中心名称
            APPSaleman saleman = baseDaoSupport.get(APPSaleman.class, m.getCreateId());
            for (TestingType t : testingTypeList)
            {
                if (t.getId().equals(saleman.getTestingType()))
                {
                    m.setBusinessName(t.getName());
                }
            }
            
            List<MeetingApplyCheck> meetingApplyChecks = m.getMeetingApplyCheck();
            if (meetingApplyChecks.size() > 0)
            {
                for (MeetingApplyCheck mac : meetingApplyChecks)
                {
                    
                    //审批中，选待审核节点
                    if (m.getStatus() == 0)
                    {
                        if (mac.getStatus() == 0)
                        {
                            m.setCheckName(mac.getCheckName());
                        }
                    }
                    //未通过，选择审核不通过节点
                    else if (m.getStatus() == 2)
                    {
                        if (mac.getStatus() == 2)
                        {
                            m.setCheckName(mac.getCheckName());
                        }
                    }
                    else
                    //已响应 已通过 已完成   选择审核通过
                    {
                        if (mac.getStatus() == 1)
                        {
                            m.setCheckName(mac.getCheckName());
                        }
                    }
                }
            }
            else
            {//审批人为经理 
                m.setCheckName(m.getCreateName());
            }
            
        }
        paging.setRecords(meetingApplies);
        return paging;
    }
    
    @Override
    public MeetingApply getMeetingApply(String id)
    {
        MeetingApply meetingApply = baseDaoSupport.get(MeetingApply.class, id);
        APPSaleman appSaleman = baseDaoSupport.get(APPSaleman.class, meetingApply.getCreateId());
        if ("2".equals(appSaleman.getRoleType()) /*|| "3".equals(appSaleman.getRoleType())*/)
        {
            meetingApply.setRoleType("1");
        }
        else
        {
            meetingApply.setRoleType("0");
        }
        switch (meetingApply.getStatus())
        {
            case 0:
                meetingApply.setStatusName("审核中");
                break;
            case 1:
                meetingApply.setStatusName("已通过");
                break;
            case 2:
                meetingApply.setStatusName("未通过");
                break;
            case 3:
                meetingApply.setStatusName("已响应");
                break;
            case 4:
                meetingApply.setStatusName("已完成");
                break;
        }
        List<MeetingApplyCheck> meetingApplyChecks = meetingApply.getMeetingApplyCheck();
        for (MeetingApplyCheck m : meetingApplyChecks)
        {
            switch (m.getStatus())
            {
                case 0:
                    m.setStatusName("审核中");
                    break;
                case 1:
                    m.setStatusName("已通过");
                    break;
                case 2:
                    m.setStatusName("未通过");
                    break;
                case 3:
                    m.setStatusName("已响应");
                    break;
                case 4:
                    m.setStatusName("已完成");
                    break;
            }
        }
        //添加营销中心
        APPSaleman saleman = baseDaoSupport.get(APPSaleman.class, meetingApply.getCreateId());
        meetingApply.setTestingType(saleman.getTestingType());
        return meetingApply;
    }
    
    @Override
    @Transactional
    public void updateResponse(MeetingApplyResponseRequest request)
    {
        MeetingApply ma = baseDaoSupport.get(MeetingApply.class, request.getId());
        String[] userIds = null;
        String[] names = null;
        if (StringUtils.isNotEmpty(request.getUserIds()))
        {
            userIds = request.getUserIds().split(",");
            for (int i = 0; i < userIds.length; i++)
            {
                deleteDispatch(request.getId());
            }
        }
        if (StringUtils.isNotEmpty(request.getUserNames()))
        {
            names = request.getUserNames().split(",");
            for (int i = 0; i < userIds.length; i++)
            {
                MeetingDispatchUser m = new MeetingDispatchUser();
                m.setMeetingApply(ma);
                m.setName(names[i]);
                m.setUserId(userIds[i]);
                
                baseDaoSupport.insert(m);
            }
        }
        
        String hql = "select m from MeetingDispatchUser as m where m.meetingApply.id= :id ";
        List<MeetingDispatchUser> meetingDispatchUsers =
            baseDaoSupport.findByNamedParam(MeetingDispatchUser.class,
                hql,
                new String[] {"id"},
                new Object[] {request.getId()});
        ma.setPreachContent(request.getPreachContent());
        ma.setMeetingDispatchUsers(meetingDispatchUsers);
        ma.setStatus(3);
        
        baseDaoSupport.update(ma);
    }
    
    @Transient
    public void deleteDispatch(String id)
    {
        String hql = "delete from MeetingDispatchUser as m where m.meetingApply.id='" + id + "'";
        baseDaoSupport.execute(hql);
    }
}
