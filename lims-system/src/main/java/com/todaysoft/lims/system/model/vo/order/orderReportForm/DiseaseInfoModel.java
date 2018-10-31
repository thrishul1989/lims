package com.todaysoft.lims.system.model.vo.order.orderReportForm;

public class DiseaseInfoModel
{
  //--------疾病信息---------
    private String disease;//临床诊断
    private String gene;//重点关注基因
    private String phenotype;//临床表型
    private String medicalHistory;//病史摘要
    private String familyMedicalHistory;//家族史摘要
    private String clinicalRecommend;//临床推荐理由
    public String getDisease()
    {
        return disease;
    }
    public void setDisease(String disease)
    {
        this.disease = disease;
    }
    public String getGene()
    {
        return gene;
    }
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    public String getPhenotype()
    {
        return phenotype;
    }
    public void setPhenotype(String phenotype)
    {
        this.phenotype = phenotype;
    }
    public String getMedicalHistory()
    {
        return medicalHistory;
    }
    public void setMedicalHistory(String medicalHistory)
    {
        this.medicalHistory = medicalHistory;
    }
    public String getFamilyMedicalHistory()
    {
        return familyMedicalHistory;
    }
    public void setFamilyMedicalHistory(String familyMedicalHistory)
    {
        this.familyMedicalHistory = familyMedicalHistory;
    }
    public String getClinicalRecommend()
    {
        return clinicalRecommend;
    }
    public void setClinicalRecommend(String clinicalRecommend)
    {
        this.clinicalRecommend = clinicalRecommend;
    }
    
}
