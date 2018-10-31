package com.todaysoft.lims.system.modules.bpm.tecanalys.model.request;

import java.util.List;

public class TechnicalAddVerifyRequest {
    private String technicalFamilyGroupId;

    private List<TechnicalAnalysisAddVerify> textsForList;

    public String getTechnicalFamilyGroupId() {
        return technicalFamilyGroupId;
    }

    public void setTechnicalFamilyGroupId(String technicalFamilyGroupId) {
        this.technicalFamilyGroupId = technicalFamilyGroupId;
    }

    public List<TechnicalAnalysisAddVerify> getTextsForList() {
        return textsForList;
    }

    public void setTextsForList(List<TechnicalAnalysisAddVerify> textsForList) {
        this.textsForList = textsForList;
    }
}
