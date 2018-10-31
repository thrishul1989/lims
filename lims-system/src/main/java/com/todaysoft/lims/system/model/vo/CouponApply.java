package com.todaysoft.lims.system.model.vo;

import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;

import lombok.AllArgsConstructor;
import lombok.Builder;


@Builder(toBuilder = true)
@AllArgsConstructor
public class CouponApply 
{
    public List<CouponApplyDetail> getApplyDetailList()
    {
        return applyDetailList;
    }

    public void setApplyDetailList(List<CouponApplyDetail> applyDetailList)
    {
        this.applyDetailList = applyDetailList;
    }

    private String id;
    private String code;
    private String userId;
    private String applyTitle;
    private String applyReason;
    private String testingType;
    private String state;
    private String createTime;
    private String createTimeEnd;//额外字段，查询用
    private String reviewUserId;
    private String reviewTime;
    private String reviewReason;
    private Integer delFlag;
    
    private  List<CouponApplyDetail> applyDetailList;
    
    
    
    private Integer pageSize;
    private Integer pageNo;
    private UserDetailsModel user;//额外字段,展示字段
    private String totalAmount;//总金额，额外字段
    private String createName;//额外字段
    private String reviewName;//额外字段
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限
    
    public   CouponApply(){
        
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

    public UserDetailsModel getUser()
    {
        return user;
    }

    public void setUser(UserDetailsModel user)
    {
        this.user = user;
    }

    public String getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount)
    {
        this.totalAmount = totalAmount;
    }

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

    public String getReviewName()
    {
        return reviewName;
    }

    public void setReviewName(String reviewName)
    {
        this.reviewName = reviewName;
    }

    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }

    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
   
  
    
}
