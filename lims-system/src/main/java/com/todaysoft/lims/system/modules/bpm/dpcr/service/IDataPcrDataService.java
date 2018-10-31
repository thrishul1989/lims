package com.todaysoft.lims.system.modules.bpm.dpcr.service;

import java.io.File;

import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSheetModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import org.springframework.web.multipart.MultipartFile;

public interface IDataPcrDataService
{
    String zipFilesDataPcr(File zipfile, DataPcrSheetModel sheet);

    DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile);
}
