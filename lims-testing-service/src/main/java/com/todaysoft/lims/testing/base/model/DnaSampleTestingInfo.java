package com.todaysoft.lims.testing.base.model;


import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DnaSampleTestingInfo
{
    private String dnaSheetCode;

    private String dnaAssignName;

    private Date dnaAssignDate;

    private String dnaTestorName;

    private Date dnaCompleteDate;

    private String dnaSampleCode;

    private BigDecimal dnaSampleConcentration;

    private BigDecimal dnaRatio2628;

    private BigDecimal dnaRatio2623;

    private BigDecimal dnaVolume;

    private String dnaQualityLevel;

    private String dnaReagentKitName;

    private String dnaIsQualified;

    private String dnaNotQualifiedRemark;

    private String dnaLocation;

    private List<LibSampleTestingInfo> libList = Lists.newArrayList();

    public String getDnaSheetCode() {
        return dnaSheetCode;
    }

    public void setDnaSheetCode(String dnaSheetCode) {
        this.dnaSheetCode = dnaSheetCode;
    }

    public String getDnaAssignName() {
        return dnaAssignName;
    }

    public void setDnaAssignName(String dnaAssignName) {
        this.dnaAssignName = dnaAssignName;
    }

    public Date getDnaAssignDate() {
        return dnaAssignDate;
    }

    public void setDnaAssignDate(Date dnaAssignDate) {
        this.dnaAssignDate = dnaAssignDate;
    }

    public String getDnaTestorName() {
        return dnaTestorName;
    }

    public void setDnaTestorName(String dnaTestorName) {
        this.dnaTestorName = dnaTestorName;
    }

    public Date getDnaCompleteDate() {
        return dnaCompleteDate;
    }

    public void setDnaCompleteDate(Date dnaCompleteDate) {
        this.dnaCompleteDate = dnaCompleteDate;
    }

    public String getDnaSampleCode() {
        return dnaSampleCode;
    }

    public void setDnaSampleCode(String dnaSampleCode) {
        this.dnaSampleCode = dnaSampleCode;
    }

    public BigDecimal getDnaSampleConcentration() {
        return dnaSampleConcentration;
    }

    public void setDnaSampleConcentration(BigDecimal dnaSampleConcentration) {
        this.dnaSampleConcentration = dnaSampleConcentration;
    }

    public BigDecimal getDnaRatio2628() {
        return dnaRatio2628;
    }

    public void setDnaRatio2628(BigDecimal dnaRatio2628) {
        this.dnaRatio2628 = dnaRatio2628;
    }

    public BigDecimal getDnaRatio2623() {
        return dnaRatio2623;
    }

    public void setDnaRatio2623(BigDecimal dnaRatio2623) {
        this.dnaRatio2623 = dnaRatio2623;
    }

    public BigDecimal getDnaVolume() {
        return dnaVolume;
    }

    public void setDnaVolume(BigDecimal dnaVolume) {
        this.dnaVolume = dnaVolume;
    }

    public String getDnaQualityLevel() {
        return dnaQualityLevel;
    }

    public void setDnaQualityLevel(String dnaQualityLevel) {
        this.dnaQualityLevel = dnaQualityLevel;
    }

    public String getDnaReagentKitName() {
        return dnaReagentKitName;
    }

    public void setDnaReagentKitName(String dnaReagentKitName) {
        this.dnaReagentKitName = dnaReagentKitName;
    }

    public String getDnaIsQualified() {
        return dnaIsQualified;
    }

    public void setDnaIsQualified(String dnaIsQualified) {
        this.dnaIsQualified = dnaIsQualified;
    }

    public String getDnaNotQualifiedRemark() {
        return dnaNotQualifiedRemark;
    }

    public void setDnaNotQualifiedRemark(String dnaNotQualifiedRemark) {
        this.dnaNotQualifiedRemark = dnaNotQualifiedRemark;
    }

    public String getDnaLocation() {
        return dnaLocation;
    }

    public void setDnaLocation(String dnaLocation) {
        this.dnaLocation = dnaLocation;
    }

    public List<LibSampleTestingInfo> getLibList() {
        return libList;
    }

    public void setLibList(List<LibSampleTestingInfo> libList) {
        this.libList = libList;
    }
}
