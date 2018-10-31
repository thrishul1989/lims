package com.todaysoft.lims.technical.model.request;

import java.util.List;

public class ReportMutationDataRequest {
	private List<String> mutationObjectIds;

    public List<String> getMutationObjectIds()
    {
        return mutationObjectIds;
    }

    public void setMutationObjectIds(List<String> mutationObjectIds)
    {
        this.mutationObjectIds = mutationObjectIds;
    }

}
