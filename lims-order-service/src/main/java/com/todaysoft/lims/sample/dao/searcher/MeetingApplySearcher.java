package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.meeting.MeetingApply;
import com.todaysoft.lims.utils.StringUtils;

@Builder
@AllArgsConstructor
public class MeetingApplySearcher implements ISearcher<MeetingApply>
{
    
    private Date updateTime;// '更新时间',
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private Integer status;
    
    private String supportDept;
    
    private String createName;
    
    private Date meetingTimeStart;
    
    private Date meetingTimeEnd;
    
    private Date applyTime;//'申请时间',
    
    private Date applyEndTime;//'额外字段',
    
    public MeetingApplySearcher()
    {
        
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select m FROM MeetingApply m WHERE  1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        if (StringUtils.isNotEmpty(status) && status == 1 && StringUtils.isNotEmpty(meetingTimeStart))
        {
            hql.append(" order by  m.meetingTime");
        }
        else
        {
            hql.append(" order by  m.applyTime desc");
        }
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<MeetingApply> getEntityClass()
    {
        // TODO Auto-generated method stub
        return MeetingApply.class;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        if (status == null && !StringUtils.isNotEmpty(supportDept) && !StringUtils.isNotEmpty(createName)
            && meetingTimeStart == null && meetingTimeEnd == null && applyTime == null && applyEndTime == null)
        {
            hql.append(" ");
            
        }
        else
        {
            if (StringUtils.isNotEmpty(status))
            {
                hql.append(" AND m.status=:status");
                paramNames.add("status");
                parameters.add(status);
            }
            
        }
        
        if (StringUtils.isNotEmpty(createName))
        {
            hql.append(" AND m.createName=:createName");
            paramNames.add("createName");
            parameters.add(createName);
        }
        if (StringUtils.isNotEmpty(applyTime))
        {
            
            hql.append(" AND m.applyTime >=:applyTime");
            paramNames.add("applyTime");
            parameters.add(applyTime);
        }
        
        if (StringUtils.isNotEmpty(applyEndTime))
        {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(applyEndTime);
            calendar.add(calendar.DATE, 1);
            hql.append(" AND m.applyTime < :applyEndTime");
            paramNames.add("applyEndTime");
            parameters.add(calendar.getTime());
        }
        if (StringUtils.isNotEmpty(meetingTimeStart))
        {
            hql.append(" AND m.meetingTime >=:meetingTimeStart");
            paramNames.add("meetingTimeStart");
            parameters.add(meetingTimeStart);
        }
        if (StringUtils.isNotEmpty(meetingTimeEnd))
        {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(meetingTimeEnd);
            calendar.add(calendar.DATE, 1);
            hql.append(" AND m.meetingTime < :meetingTimeEnd");
            paramNames.add("meetingTimeEnd");
            parameters.add(calendar.getTime());
        }
        if (StringUtils.isNotEmpty(supportDept))
        {
            hql.append(" AND m.createId in (select a.userId from APPSaleman a where a.testingType = :supportDept)");
            paramNames.add("supportDept");
            parameters.add(supportDept);
        }
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getSupportDept()
    {
        return supportDept;
    }
    
    public void setSupportDept(String supportDept)
    {
        this.supportDept = supportDept;
    }
    
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    public Date getApplyEndTime()
    {
        return applyEndTime;
    }
    
    public void setApplyEndTime(Date applyEndTime)
    {
        this.applyEndTime = applyEndTime;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public Date getMeetingTimeStart()
    {
        return meetingTimeStart;
    }
    
    public void setMeetingTimeStart(Date meetingTimeStart)
    {
        this.meetingTimeStart = meetingTimeStart;
    }
    
    public Date getMeetingTimeEnd()
    {
        return meetingTimeEnd;
    }
    
    public void setMeetingTimeEnd(Date meetingTimeEnd)
    {
        this.meetingTimeEnd = meetingTimeEnd;
    }
    
}
