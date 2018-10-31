package com.todaysoft.lims.testing.pcrngsprimerdesign.service;

import com.todaysoft.lims.testing.pcrngsprimerdesign.dao.PcrNgsPrimerDesignAssignableTaskSearcher;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.*;

import java.util.List;


public interface IPcrNgsPrimerDesignService
{
    List<PcrNgsPrimerDesignTask> getAssignableList(PcrNgsPrimerDesignAssignableTaskSearcher searcher);

    PcrNgsPrimerDesignAssignModel getAssignableModel(PcrNgsPrimerDesignAssignArgs args);

    void assign(PcrNgsPrimerDesignAssignRequest request, String token);

    PcrNgsPrimerDesignSheetModel getTestingSheet(String id);

    void submit(PcrNgsPrimerDesignSubmitRequest request, String token);
}
