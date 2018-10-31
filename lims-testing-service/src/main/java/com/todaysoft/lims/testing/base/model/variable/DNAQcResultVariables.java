package com.todaysoft.lims.testing.base.model.variable;


public class DNAQcResultVariables {

    private String concentration;//浓度

    private String volume;//体积

    private String A260280;//

    private String A260230;

    private String qcResult;//质检结果

    private String qcLevel;//质检等级

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getA260280() {
        return A260280;
    }

    public void setA260280(String a260280) {
        A260280 = a260280;
    }

    public String getA260230() {
        return A260230;
    }

    public void setA260230(String a260230) {
        A260230 = a260230;
    }

    public String getQcResult() {
        return qcResult;
    }

    public void setQcResult(String qcResult) {
        this.qcResult = qcResult;
    }

    public String getQcLevel() {
        return qcLevel;
    }

    public void setQcLevel(String qcLevel) {
        this.qcLevel = qcLevel;
    }
}
