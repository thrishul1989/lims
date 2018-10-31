package com.todaysoft.lims.testing.primerdesign.service;

import com.todaysoft.lims.testing.primerdesign.dao.PrimerDesignAssignableTaskSearcher;
import com.todaysoft.lims.testing.primerdesign.model.*;

import java.util.List;


public interface IPrimerDesignService
{
    List<PrimerDesignTask> getAssignableList(PrimerDesignAssignableTaskSearcher searcher,int flag);

    PrimerDesignAssignModel getAssignableModel(PrimerDesignAssignArgs args);

    void assign(PrimerDesignAssignRequest request, String token);

    PrimerDesignSheetModel getTestingSheet(String id);

    void submit(PrimerDesignSubmitRequest request, String token);
}
