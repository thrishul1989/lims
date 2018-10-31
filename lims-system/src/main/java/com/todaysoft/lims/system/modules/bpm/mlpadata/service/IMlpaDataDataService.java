package com.todaysoft.lims.system.modules.bpm.mlpadata.service;

import java.io.File;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ZipFileUploadModel;
import org.springframework.web.multipart.MultipartFile;

public interface IMlpaDataDataService
{
    String zipFilesMlpaData(File zipfile, MlpaDataSheetModel sheet);

    DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile);

    ZipFileUploadModel unZipFiles(MultipartFile zipFile,String semantic);

    void deleteLocalFileDir(DataAnalysisParseModel model);

    boolean deleteDir(File dir);

    void uploadFileQNAndDeleteLocal(List<String> fileListm,String dir);

    List<TestingDataPic> uploadFileAndWrapData(DataAnalysisParseModel parseModel,String sheetId);
}
