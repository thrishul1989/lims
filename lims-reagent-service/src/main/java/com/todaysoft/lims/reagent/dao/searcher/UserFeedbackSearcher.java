package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.UserFeedback;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

public class UserFeedbackSearcher implements ISearcher<UserFeedback>
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    private String id;
    
    private Date feedStartbackDate;
    
    private Date feedEndbackDate;
    
    private String feedbackOption;
    
    private String name;
    
    private String position;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String userId;
    
    private List<String> userIds;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("SELECT u FROM UserFeedback as u where 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" ORDER BY u.feedbackDate DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<UserFeedback> getEntityClass()
    {
        return UserFeedback.class;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        if (StringUtils.isNotEmpty(userId))
        {
            
            hql.append(" AND u.userId=:userId");
            paramNames.add("userId");
            parameters.add(userId);
        }
        
        if (userIds.size() > 0)
        {
            
            hql.append(" AND u.userId IN(");
            for (String userId : userIds)
            {
                if (!Collections3.getLast(userIds).equals(userId))
                {
                    hql.append(" '" + userId + "',");
                }
                else
                {
                    hql.append(" '" + userId + "'");
                }
                
            }
            hql.append(")");
            
        }
        
        if (StringUtils.isNotEmpty(position))
        {
            if ("0".equals(position))
            {
                hql.append(" AND u.userType=0");
            }
            else
            {
                hql.append(" AND u.userId in(SELECT b.id FROM  BusinessInfo as  b WHERE b.roleType = :position)");
                paramNames.add("position");
                parameters.add(position);
            }
        }
        
        if (StringUtils.isNotEmpty(feedbackOption))
        {
            hql.append(" AND u.feedbackOption like :feedbackOption");
            paramNames.add("feedbackOption");
            parameters.add("%" + feedbackOption + "%");
        }
        if (StringUtils.isNotEmpty(feedStartbackDate))
        {
            
            hql.append(" AND u.feedbackDate >=:feedStartbackDate");
            paramNames.add("feedStartbackDate");
            parameters.add(feedStartbackDate);
            
        }
        
        if (StringUtils.isNotEmpty(feedEndbackDate))
        {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(feedEndbackDate);
            calendar.add(calendar.DATE, 1);
            hql.append(" AND u.feedbackDate <=:feedEndbackDate");
            paramNames.add("feedEndbackDate");
            parameters.add(calendar.getTime());
        }
        
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Date getFeedStartbackDate()
    {
        return feedStartbackDate;
    }
    
    public void setFeedStartbackDate(Date feedStartbackDate)
    {
        this.feedStartbackDate = feedStartbackDate;
    }
    
    public Date getFeedEndbackDate()
    {
        return feedEndbackDate;
    }
    
    public void setFeedEndbackDate(Date feedEndbackDate)
    {
        this.feedEndbackDate = feedEndbackDate;
    }
    
    public String getFeedbackOption()
    {
        return feedbackOption;
    }
    
    public void setFeedbackOption(String feedbackOption)
    {
        this.feedbackOption = feedbackOption;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPosition()
    {
        return position;
    }
    
    public void setPosition(String position)
    {
        this.position = position;
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
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public List<String> getUserIds()
    {
        return userIds;
    }
    
    public void setUserIds(List<String> userIds)
    {
        this.userIds = userIds;
    }
    
}
