package com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis;

import java.util.Date;

public class CnvAnalysisPic {
    private String id;

    private String sampleTestId;

    private String imageName;

    private String imageUrl;

    private Date createTime;

    private Date updateTime;
    
    private Integer isCollection;
    
    private String clinicalJudgment;//与临床表型高度相关，与临床表型相关

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleTestId() {
        return sampleTestId;
    }

    public void setSampleTestId(String sampleTestId) {
        this.sampleTestId = sampleTestId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsCollection()
    {
        return isCollection;
    }

    public void setIsCollection(Integer isCollection)
    {
        this.isCollection = isCollection;
    }

    public String getClinicalJudgment()
    {
        return clinicalJudgment;
    }

    public void setClinicalJudgment(String clinicalJudgment)
    {
        this.clinicalJudgment = clinicalJudgment;
    }
}