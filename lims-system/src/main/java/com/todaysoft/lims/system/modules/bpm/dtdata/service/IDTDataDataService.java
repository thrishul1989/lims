package com.todaysoft.lims.system.modules.bpm.dtdata.service;

import java.io.File;

import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import org.springframework.web.multipart.MultipartFile;

public interface IDTDataDataService
{
    String zipFilesDTData(File zipfile, DTDataSheetModel sheet);

    DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile);
}
