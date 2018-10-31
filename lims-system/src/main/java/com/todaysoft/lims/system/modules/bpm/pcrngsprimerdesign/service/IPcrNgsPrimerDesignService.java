package com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.*;

public interface IPcrNgsPrimerDesignService
{
    
    List<PcrNgsPrimerDesignTask> getPrimerDesignAssignableList(PcrNgsPrimerDesignAssignableTaskSearcher searcher);
    
    PcrNgsPrimerDesignAssignModel getPrimerDesignAssignModel(PcrNgsPrimerDesignAssignArgs args);
    
    void assignPrimerDesign(PcrNgsPrimerDesignAssignRequest data);
    
    PcrNgsPrimerDesignSheetModel getPrimerDesignSubmitModel(String id);
    
    void submitPrimerDesign(PcrNgsPrimerDesignSubmitRequest request);
}
