package com.todaysoft.lims.system.modules.bpm.mlpadata.model;


import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.fluoanalyse.model.FluoDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.DefaultTableModel;

import java.util.List;

public class DataAnalysisParseModel
{
    private DefaultTableModel modelMap;

    private List<TestingDataPic> picList;

    private List<String> localFilePath;

    private String uploadDir;// 上传文件的路径

    private String picParentPath;//图片的父路径

    private List<MlpaDataSubmitTaskSuccessArgs> records;

    private List<DTDataSubmitTaskSuccessArgs> dtRecords;

    private List<FluoDataSubmitTaskSuccessArgs> fluoRecords;

    private List<PcrNgsDataSubmitTaskArgs> pcrNgsRecords;

    private List<DataPcrSubmitTaskArgs> dpcrRecords;

    private List<DataPcrSangerSubmitTaskArgs> dpcrSangerRecords;

    private List<QpcrSubmitTask> qpcrRecords;

    public DefaultTableModel getModelMap() {
        return modelMap;
    }

    public void setModelMap(DefaultTableModel modelMap) {
        this.modelMap = modelMap;
    }

    public List<TestingDataPic> getPicList() {
        return picList;
    }

    public void setPicList(List<TestingDataPic> picList) {
        this.picList = picList;
    }

    public List<String> getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(List<String> localFilePath) {
        this.localFilePath = localFilePath;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getPicParentPath() {
        return picParentPath;
    }

    public void setPicParentPath(String picParentPath) {
        this.picParentPath = picParentPath;
    }

    public List<MlpaDataSubmitTaskSuccessArgs> getRecords() {
        return records;
    }

    public void setRecords(List<MlpaDataSubmitTaskSuccessArgs> records) {
        this.records = records;
    }

    public List<DTDataSubmitTaskSuccessArgs> getDtRecords() {
        return dtRecords;
    }

    public void setDtRecords(List<DTDataSubmitTaskSuccessArgs> dtRecords) {
        this.dtRecords = dtRecords;
    }

    public List<FluoDataSubmitTaskSuccessArgs> getFluoRecords() {
        return fluoRecords;
    }

    public void setFluoRecords(List<FluoDataSubmitTaskSuccessArgs> fluoRecords) {
        this.fluoRecords = fluoRecords;
    }

    public List<PcrNgsDataSubmitTaskArgs> getPcrNgsRecords() {
        return pcrNgsRecords;
    }

    public void setPcrNgsRecords(List<PcrNgsDataSubmitTaskArgs> pcrNgsRecords) {
        this.pcrNgsRecords = pcrNgsRecords;
    }

    public List<DataPcrSubmitTaskArgs> getDpcrRecords() {
        return dpcrRecords;
    }

    public void setDpcrRecords(List<DataPcrSubmitTaskArgs> dpcrRecords) {
        this.dpcrRecords = dpcrRecords;
    }

    public List<DataPcrSangerSubmitTaskArgs> getDpcrSangerRecords() {
        return dpcrSangerRecords;
    }

    public void setDpcrSangerRecords(List<DataPcrSangerSubmitTaskArgs> dpcrSangerRecords) {
        this.dpcrSangerRecords = dpcrSangerRecords;
    }

    public List<QpcrSubmitTask> getQpcrRecords() {
        return qpcrRecords;
    }

    public void setQpcrRecords(List<QpcrSubmitTask> qpcrRecords) {
        this.qpcrRecords = qpcrRecords;
    }
}
