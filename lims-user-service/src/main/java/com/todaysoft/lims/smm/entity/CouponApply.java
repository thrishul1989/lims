package com.todaysoft.lims.smm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_COUPON_APPLY")
@Builder(toBuilder = true)
@AllArgsConstructor
public class CouponApply extends UuidKeyEntity
{
    
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
    private Integer delFlag;
    
   private  List<CouponApplyDetail> applyDetailList;
    
    public CouponApply(){
        
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    @Column(name = "APPLY_TITLE")
    public String getApplyTitle()
    {
        return applyTitle;
    }
    public void setApplyTitle(String applyTitle)
    {
        this.applyTitle = applyTitle;
    }
    @Column(name = "APPLY_REASON")
    public String getApplyReason()
    {
        return applyReason;
    }
    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }
    @Column(name = "TESTING_TYPE")
    public String getTestingType()
    {
        return testingType;
    }
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    @Column(name = "STATE")
    public String getState()
    {
        return state;
    }
    public void setState(String state)
    {
        this.state = state;
    }
    @Column(name = "CREATE_TIME")
    public String getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    @Column(name = "REVIEW_USER_ID")
    public String getReviewUserId()
    {
        return reviewUserId;
    }
    public void setReviewUserId(String reviewUserId)
    {
        this.reviewUserId = reviewUserId;
    }
    @Column(name = "REVIEW_TIME")
    public String getReviewTime()
    {
        return reviewTime;
    }
    public void setReviewTime(String reviewTime)
    {
        this.reviewTime = reviewTime;
    }
    @Column(name = "REVIEW_REASON")
    public String getReviewReason()
    {
        return reviewReason;
    }
    public void setReviewReason(String reviewReason)
    {
        this.reviewReason = reviewReason;
    }
    @Column(name = "DEL_FLAG")
    public Integer getDelFlag()
    {
        return delFlag;
    }
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }

    @OneToMany(mappedBy = "couponApply", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    public List<CouponApplyDetail> getApplyDetailList()
    {
        return applyDetailList;
    }

    public void setApplyDetailList(List<CouponApplyDetail> applyDetailList)
    {
        this.applyDetailList = applyDetailList;
    }
  
    
}
