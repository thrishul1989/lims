package com.todaysoft.lims.testing.completeTasks.service;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.completeTasks.model.ngs.BiologyAnalySheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.CDNAQcSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.DNAQcSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.LibraryCaptureSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.LibraryQcSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.QtSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.RQTSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.SequencingSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.TechnicalAnalySheet;

public interface INGSCompleteService
{
    DNAQcSheet getDNAQcSheet(String id);
    
    CDNAQcSheet getCDNAQcSheet(String id);
    
    LibraryQcSheet getLibraryQcSheet(String id);
    
    LibraryCaptureSheet getLibCapSheet(String id);
    
    RQTSheet getRQTSheet(String id);
    
    QtSheet getQtSheet(String id);
    
    SequencingSheet getSequencingSheet(String id);
    
    TechnicalAnalySheet getTechnicalAnalySheet(String id);
    
    BiologyAnalySheet getBiologyAnalySheet(String id);

  
}
