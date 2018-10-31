package com.todaysoft.lims.system.modules.bpm.pcrngsdata.service;

import java.io.File;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSheetModel;
import org.springframework.web.multipart.MultipartFile;

public interface IPcrNgsDataDataService
{
    String zipFilesDataPcr(File zipfile, PcrNgsDataSheetModel sheet);

    DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile);
}
