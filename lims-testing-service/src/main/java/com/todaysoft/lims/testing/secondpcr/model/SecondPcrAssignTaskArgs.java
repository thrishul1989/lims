package com.todaysoft.lims.testing.secondpcr.model;

public class SecondPcrAssignTaskArgs
{
    private String id;

    private String spcrTestCode;

    private String pcrTaskCode;//一次pcr任务编号

    private String pcrTestCode;//一次pcr试验编号

    private String forwardPrimerLocationTemp;//引物临时位置

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpcrTestCode() {
        return spcrTestCode;
    }

    public void setSpcrTestCode(String spcrTestCode) {
        this.spcrTestCode = spcrTestCode;
    }

    public String getPcrTaskCode() {
        return pcrTaskCode;
    }

    public void setPcrTaskCode(String pcrTaskCode) {
        this.pcrTaskCode = pcrTaskCode;
    }

    public String getPcrTestCode() {
        return pcrTestCode;
    }

    public void setPcrTestCode(String pcrTestCode) {
        this.pcrTestCode = pcrTestCode;
    }

    public String getForwardPrimerLocationTemp() {
        return forwardPrimerLocationTemp;
    }

    public void setForwardPrimerLocationTemp(String forwardPrimerLocationTemp) {
        this.forwardPrimerLocationTemp = forwardPrimerLocationTemp;
    }
}
