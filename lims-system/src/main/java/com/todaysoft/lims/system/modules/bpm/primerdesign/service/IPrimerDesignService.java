package com.todaysoft.lims.system.modules.bpm.primerdesign.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.primerdesign.model.*;

public interface IPrimerDesignService
{
    
    List<PrimerDesignTask> getPrimerDesignAssignableList(PrimerDesignAssignableTaskSearcher searcher);
    
    PrimerDesignAssignModel getPrimerDesignAssignModel(PrimerDesignAssignArgs args);
    
    void assignPrimerDesign(PrimerDesignAssignRequest data);
    
    PrimerDesignSheetModel getPrimerDesignSubmitModel(String id);
    
    void submitPrimerDesign(PrimerDesignSubmitRequest request);
}
