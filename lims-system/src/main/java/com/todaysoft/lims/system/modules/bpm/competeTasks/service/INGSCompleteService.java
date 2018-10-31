package com.todaysoft.lims.system.modules.bpm.competeTasks.service;

import com.todaysoft.lims.system.model.vo.order.TestingSchedule;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.division.BiologyAnnotationSheetViewModel;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.division.BiologyDivisionSheetViewModel;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.BiologyAnalySheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.CDNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.DNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.LibraryCaptureSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.LibraryQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.QtSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.RQTSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.SequencingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.TechnicalAnalySheet;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateSheet;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitModel;

public interface INGSCompleteService
{
    DNAExtractSheet getDNAExtractSheet(String id);
    
    DNAQcSheet getDNAQcTestModel(String id);
    
    CDNAExtractSheet getCDNAExtractSheet(String id);
    
    CDNAQcSheet getCDNAQcTestModel(String id);
    
    LibraryCreateSheet getLibraryCreateSheet(String id);
    
    LibraryQcSheet getLibraryQcSheet(String id);
    
    LibraryCaptureSheet getLibCapSheet(String id);
    
    RQTSheet getRQTSheet(String id);
    
    PoolingSubmitModel getPoolingSubmitModel(String id);
    
    QtSheet getQtSheet(String id);
    
    SequencingSheet getSequencingSheet(String id);
    
    TechnicalAnalySheet getTechnicalAnalySheet(String id);
    
    BiologyAnalySheet getBiologyAnalySheet(String id);

	SequencingSubmitModel getNgsSequencingSheet(String id);

    BiologyDivisionSheetViewModel getBiologyDivisionSheet(String id);

    BiologyAnnotationSheetViewModel getBiologyAnnotationSheet(String id);
}
