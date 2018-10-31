package com.todaysoft.lims.testing.secondpcr.model;


public class SecondPcrTaskVariables
{
    private String fpcrTaskCode;//pcr任务编号

    private String fpcrTestCode;//pcr试验编号

    private String spcrTestCode;//二次PCR实验编号

    private String primerLocationTemp;//引物临时位置（反向测序才会有）

    private Integer flag;//标志  1表示一次pCR直接到二次PCR  2就是经过二次PCR下达的

    private String remark;

    private Integer forwardFlag;//1正向 2反向

    public String getFpcrTaskCode() {
        return fpcrTaskCode;
    }

    public void setFpcrTaskCode(String fpcrTaskCode) {
        this.fpcrTaskCode = fpcrTaskCode;
    }

    public String getFpcrTestCode() {
        return fpcrTestCode;
    }

    public void setFpcrTestCode(String fpcrTestCode) {
        this.fpcrTestCode = fpcrTestCode;
    }

    public String getSpcrTestCode() {
        return spcrTestCode;
    }

    public void setSpcrTestCode(String spcrTestCode) {
        this.spcrTestCode = spcrTestCode;
    }

    public String getPrimerLocationTemp() {
        return primerLocationTemp;
    }

    public void setPrimerLocationTemp(String primerLocationTemp) {
        this.primerLocationTemp = primerLocationTemp;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getForwardFlag() {
        return forwardFlag;
    }

    public void setForwardFlag(Integer forwardFlag) {
        this.forwardFlag = forwardFlag;
    }
}
