package com.todaysoft.lims.system.model.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.utils.StringUtils;

@Builder(toBuilder = true)
@AllArgsConstructor
public class Coupon
{
    private String id;
    
    private String code;//申请编号+客户id+流水号
    
    private CouponApplyDetail applyDetail;
    
    private String userId;
    
    private Integer amount;//面额分
    
    private String amount_toyuan;//分转元
    
    private String validStartDate;//开始时间
    
    private String validEndDate;//结束时间
    
    private Integer consumed;//消费状态
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private String createTime;
    
    private BigDecimal realAmount; //真是价格 元
    
    private boolean undueSearch;//判断是都要加过期判断
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限
    
    public Coupon()
    {
        
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
    
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    public String getValidStartDate()
    {
        return validStartDate;
    }
    
    public void setValidStartDate(String validStartDate)
    {
        this.validStartDate = validStartDate;
    }
    
    public String getValidEndDate()
    {
        return validEndDate;
    }
    
    public void setValidEndDate(String validEndDate)
    {
        this.validEndDate = validEndDate;
    }
    
    public Integer getConsumed()
    {
        return consumed;
    }
    
    public void setConsumed(Integer consumed)
    {
        this.consumed = consumed;
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
    
    public CouponApplyDetail getApplyDetail()
    {
        return applyDetail;
    }
    
    public void setApplyDetail(CouponApplyDetail applyDetail)
    {
        this.applyDetail = applyDetail;
    }
    
    public String getAmount_toyuan()
    {
        return amount_toyuan;
    }
    
    public void setAmount_toyuan(String amount_toyuan)
    {
        this.amount_toyuan = amount_toyuan;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    public BigDecimal getRealAmount()
    {
        if (StringUtils.isNotEmpty(amount))
        {
            return BigDecimal.valueOf(amount).divide(new BigDecimal(100));
        }
        else
        {
            return new BigDecimal(0);
        }
    }
    
    public void setRealAmount(BigDecimal realAmount)
    {
        this.realAmount = realAmount;
    }
    
    public boolean isUndueSearch()
    {
        return undueSearch;
    }
    
    public void setUndueSearch(boolean undueSearch)
    {
        this.undueSearch = undueSearch;
    }
    
}
