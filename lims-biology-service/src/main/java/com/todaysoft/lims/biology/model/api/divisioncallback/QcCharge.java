package com.todaysoft.lims.biology.model.api.divisioncallback;


public class QcCharge {

    private String sample;//样本编号

    private String fraction;//Q30

    private String rawreads;//rawreads(M)

    private String yield;//rawdata(M)

    private ResultFile file;

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public String getRawreads() {
        return rawreads;
    }

    public void setRawreads(String rawreads) {
        this.rawreads = rawreads;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public ResultFile getFile() {
        return file;
    }

    public void setFile(ResultFile file) {
        this.file = file;
    }
}
