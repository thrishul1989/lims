package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.CouponApply;

public class CouponApplySearcher extends IDataAuthoritySearcher implements ISearcher<CouponApply>
{
    
    private String id;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    private String code;
    
    private String userId;
    
    private String applyTitle;
    
    private String applyReason;
    
    private String testingType;
    
    private String state;
    
    private String createTime;
    
    private String reviewUserId;
    
    private String reviewTime;
    
    private String reviewReason;
    
    public List<CouponApplyDetailSearcher> getApplyDetailList()
    {
        return applyDetailList;
    }
    
    public void setApplyDetailList(List<CouponApplyDetailSearcher> applyDetailList)
    {
        this.applyDetailList = applyDetailList;
    }
    
    private Integer delFlag;
    
    private String createTimeEnd;//额外字段
    
    private String createName;//额外字段
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private List<CouponApplyDetailSearcher> applyDetailList;
    
    public String getCreateTimeEnd()
    {
        return createTimeEnd;
    }
    
    public void setCreateTimeEnd(String createTimeEnd)
    {
        this.createTimeEnd = createTimeEnd;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
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
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getApplyTitle()
    {
        return applyTitle;
    }
    
    public void setApplyTitle(String applyTitle)
    {
        this.applyTitle = applyTitle;
    }
    
    public String getApplyReason()
    {
        return applyReason;
    }
    
    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public String getReviewUserId()
    {
        return reviewUserId;
    }
    
    public void setReviewUserId(String reviewUserId)
    {
        this.reviewUserId = reviewUserId;
    }
    
    public String getReviewTime()
    {
        return reviewTime;
    }
    
    public void setReviewTime(String reviewTime)
    {
        this.reviewTime = reviewTime;
    }
    
    public String getReviewReason()
    {
        return reviewReason;
    }
    
    public void setReviewReason(String reviewReason)
    {
        this.reviewReason = reviewReason;
    }
    
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM CouponApply s where s.delFlag = 0");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addFilter(hql, names, values);
        
        hql.append(" ORDER BY s.createTime DESC");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(applyTitle))
        {
            hql.append(" AND s.applyTitle LIKE :applyTitle");
            paramNames.add("applyTitle");
            parameters.add("%" + applyTitle + "%");
        }
        
        /*  if (!StringUtils.isEmpty(createName))
          {
              hql.append(" AND s.userId in (select u.id from User u where u.archive.name LIKE :name)");
              paramNames.add("name");
              parameters.add("%" + createName + "%");
          }*/
        
        if (!StringUtils.isEmpty(createTime))
        {
            hql.append(" AND s.createTime > :createTime ");
            paramNames.add("createTime");
            parameters.add(createTime);
            
        }
        
        if (!StringUtils.isEmpty(createTimeEnd))
        {
            hql.append(" AND s.createTime < :createTimeEnd ");
            paramNames.add("createTimeEnd");
            parameters.add(createTimeEnd);
            
        }
        
        if (!StringUtils.isEmpty(state))
        {
            hql.append(" AND s.state = :state");
            paramNames.add("state");
            parameters.add(state);
        }
        
        if (!StringUtils.isEmpty(testingType))
        {
            hql.append(" AND s.testingType = :testingType");
            paramNames.add("testingType");
            parameters.add(testingType);
        }
        
    }
    
    @Override
    public Class<CouponApply> getEntityClass()
    {
        // TODO Auto-generated method stub
        return CouponApply.class;
    }
    
    @Override
    public NamedQueryer toAuthQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM CouponApply s where s.delFlag = 0");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addAuthFilter(hql, names, values, "userId");
        addFilter(hql, names, values);
        
        hql.append(" ORDER BY s.createTime DESC");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
}
