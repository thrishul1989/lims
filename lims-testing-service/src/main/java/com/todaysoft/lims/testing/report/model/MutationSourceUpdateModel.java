package com.todaysoft.lims.testing.report.model;

public class MutationSourceUpdateModel
{
    private String id;// MongoDB 突变-主键

    private String mutationSource; //遗传来源

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMutationSource() {
        return mutationSource;
    }

    public void setMutationSource(String mutationSource) {
        this.mutationSource = mutationSource;
    }
}
