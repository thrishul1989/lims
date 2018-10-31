package com.todaysoft.lims.testing.report.request;


import com.todaysoft.lims.testing.report.model.MutationSourceUpdateModel;

import java.util.List;

public class UpdateMutationSourceRequest
{
    private List<MutationSourceUpdateModel> mutationSourceUpdateModels;

    public List<MutationSourceUpdateModel> getMutationSourceUpdateModels() {
        return mutationSourceUpdateModels;
    }

    public void setMutationSourceUpdateModels(List<MutationSourceUpdateModel> mutationSourceUpdateModels) {
        this.mutationSourceUpdateModels = mutationSourceUpdateModels;
    }
}
