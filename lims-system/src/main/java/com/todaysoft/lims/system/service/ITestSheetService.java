package com.todaysoft.lims.system.service;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model.FirstPCRSangerSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestSheetModel;
import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SecondPCRSangerSheetModel;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.TestingSheet;
import com.todaysoft.lims.system.model.vo.TestingSheetTaskAbsolute;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTSubmitModel;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSheetModel;
import com.todaysoft.lims.system.modules.bpm.libqc.model.LibraryQcSubmitModel;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestSubmitModel;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignSheetModel;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtSubmitModel;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTSheetModel;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitModel;

public interface ITestSheetService
{
    
    TestingSheet getTestSheet(Integer activitiTaskId);
    
    Pagination<TestingSheetTaskAbsolute> absolutePaging();
    
    void dnaSubmit(TestingSheet request);
    
    void dnaCheckSubmit(TestingSheet request);
    
    void wkCreateSubmit(TestingSheet request);
    
    void wkCheckSubmit(TestingSheet request);
    
    void wkCatchSubmit(TestingSheet request);
    
    void xddlSubmit(TestingSheet request);
    
    void jddlSubmit(TestingSheet request);
    
    void onTestSubmit(TestingSheet request);
    
    void bioInfoSubmit(TestingSheet request);
    
    void tecAnalysisSubmit(TestingSheet request);
    
    void reportCreateSubmit(TestingSheet request);
    
    void reportCheckSubmit(TestingSheet request);
    
    void reportMailSubmit(TestingSheet request);
    
    String downloadDNAData(String path, TestingSheet request);
    
    String downloadDNAQCData(InputStream is, DNAQcSheet request, BigDecimal[] concentration, BigDecimal[] volume, BigDecimal[] A260280, BigDecimal[] A260230, String[] qualityGrade);
    
    String downloadCDNAQCData(InputStream inputStream, CDNAQcSheet request, String[] concentration, String[] volume, String[] A260280, String[] A260230, String[] qualityGrade);
    
    String downloadLibQcData(InputStream is, LibraryQcSubmitModel request, String[] concentration, String[] volume, String[] A260280, String[] A260230, String[] qualityGrade);
    
    String downloadWKCreateData(String path, TestingSheet request);
    
    String downloadWKQCData(String path, TestingSheet request);
    
    String downloadLibrarycatchData(InputStream inputStream, LibraryCaptureSheetModel request, BigDecimal[] concentration, BigDecimal[] volume);
    
    String downloadRQTData(InputStream is, RQTSheetModel request, BigDecimal[] ctv);
    
    String downloadPoolingData(InputStream is, PoolingSubmitModel entity);
    
    String downloadJDDLData(InputStream inputStream, QtSubmitModel entity, BigDecimal concentration, BigDecimal libData);
    
    String downloadOnTestingData(InputStream inputStream, SequencingSubmitModel entity,File zipfile,String taskId) ;
    
    String downloadBioinformaticAnalysis(String path, TestingSheet request);
    
    String downloadAnalysisData(InputStream inputStream, TechnicalAnalySubmitModel request);
    
    String downloadPrimerDesignData(InputStream inputStream, PrimerDesignSheetModel request);

    String downloadPcrNgsPrimerDesignData(InputStream inputStream, PcrNgsPrimerDesignSheetModel request);

    String downloadReportCreate(String path, TestingSheet request);
    
    void downloadData(HttpServletResponse response, String path);
    
    List<String> uploadData(MultipartFile file, int stratRow, int column);
    
    String getValue(MultipartFile file, int stratRow, int column);
    
    Object getCellValue(Cell cell);
    
    String QCValue(double concentration, double one, String flag);
    
    List<List<String>> uploadDataByRow(MultipartFile file, int stratRow, int codeColumn);
    
    String getDownLoadPath(String tempFile);
    
    String zipFiles(File zipfile, FirstPCRSheetModel request);

    String zipPcrOneSangerFiles(File zipfile, FirstPCRSangerSheetModel request);
    
    String zipFilesPCR2(File zipfile, SecondPCRSheetModel request);

    String zipFilesPCRSanger2(File zipfile, SecondPCRSangerSheetModel request);
    
    String zipMlpaFiles(File zipfile, MlpaTestSubmitModel request);
    
    String zipDTFiles(File zipfile, DTSubmitModel request);
    
    String getFileName(String path, String nameCode, String downloadName);
    
    String getFormatTime(String formatDate, Date date);
    
    String zipFiles(File zipfile, List<File> srcfile);

    String zipPcrNgsFiles(File zipfile, PcrNgsTestSheetModel request);

	String downloadNgsSequecingData(InputStream is, SequencingSubmitModel sheet, File zipfile, String sampleCode);
}
