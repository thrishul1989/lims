package com.todaysoft.lims.system.modules.bpm.dpcrsanger.service;

import java.io.File;

import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerSheetModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import org.springframework.web.multipart.MultipartFile;

public interface IDataPcrSangerDataService
{
    String zipFilesDataPcr(File zipfile, DataPcrSangerSheetModel sheet);

    DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile);
}
