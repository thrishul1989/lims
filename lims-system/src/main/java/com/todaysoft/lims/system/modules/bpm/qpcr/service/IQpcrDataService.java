package com.todaysoft.lims.system.modules.bpm.qpcr.service;

import java.io.File;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitModel;
import org.springframework.web.multipart.MultipartFile;

public interface IQpcrDataService
{
    String zipFilesQpcr(File zipfile, QpcrSubmitModel sheet);

    DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile);
}
