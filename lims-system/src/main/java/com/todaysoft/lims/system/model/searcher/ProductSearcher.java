package com.todaysoft.lims.system.model.searcher;

import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.model.vo.TestingType;

public class ProductSearcher
{
    private String id;
    
    private String code; // 产品编号
    
    private String name; // 产品名称
    
    private String query;//多选匹配
    
    private TestingType testingType = new TestingType();// 检测类型以及分类
    
    private Integer enabled;// 状态
    
    private String testInstitution;//检测机构：0 检验所 1麦诺基公司
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限
    
    private String tType;
    
    private boolean likeSearch;
    
    private String productDutyName;
    
    private String testingSubtype;//二级分类
    
    private String principalName;//技术负责人
    
    private String productProbe;
    
    public String getProductProbe()
    {
        return productProbe;
    }
    
    public void setProductProbe(String productProbe)
    {
        this.productProbe = productProbe;
    }
    
    public String getTestingSubtype()
    {
        return testingSubtype;
    }
    
    public void setTestingSubtype(String testingSubtype)
    {
        this.testingSubtype = testingSubtype;
    }
    
    public String getPrincipalName()
    {
        return principalName;
    }
    
    public void setPrincipalName(String principalName)
    {
        this.principalName = principalName;
    }
    
    public String getProductDutyName()
    {
        return productDutyName;
    }
    
    public void setProductDutyName(String productDutyName)
    {
        this.productDutyName = productDutyName;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public boolean isLikeSearch()
    {
        return likeSearch;
    }
    
    public void setLikeSearch(boolean likeSearch)
    {
        this.likeSearch = likeSearch;
    }
    
    public String gettType()
    {
        return tType;
    }
    
    public void settType(String tType)
    {
        this.tType = tType;
    }
    
    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    public TestingType getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(TestingType testingType)
    {
        this.testingType = testingType;
    }
    
    public Integer getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    
    public String getTestInstitution()
    {
        return testInstitution;
    }
    
    public void setTestInstitution(String testInstitution)
    {
        this.testInstitution = testInstitution;
    }
    
    public String getQuery()
    {
        return query;
    }
    
    public void setQuery(String query)
    {
        this.query = query;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
}
