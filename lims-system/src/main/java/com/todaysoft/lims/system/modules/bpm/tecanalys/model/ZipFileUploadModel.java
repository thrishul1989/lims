package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import org.apache.tools.zip.ZipFile;

import java.io.File;
import java.util.List;


public class ZipFileUploadModel
{
    private File file;//解压出来的 xls

    private ZipFile zipFile;// 压缩文件本身

    private String uploadDir;// 上传文件的最外层路径

    private File localZipFile;//本地的压缩文件

    private List<String> localFilePath;//解压出来的图片本地路径 确认之后上传到七牛

    private List<TestingDataPic> picList;//图片list,根据数据编号的list，url逗号隔开

    private String picParentPath;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ZipFile getZipFile() {
        return zipFile;
    }

    public void setZipFile(ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public File getLocalZipFile() {
        return localZipFile;
    }

    public void setLocalZipFile(File localZipFile) {
        this.localZipFile = localZipFile;
    }

    public List<String> getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(List<String> localFilePath) {
        this.localFilePath = localFilePath;
    }

    public List<TestingDataPic> getPicList() {
        return picList;
    }

    public void setPicList(List<TestingDataPic> picList) {
        this.picList = picList;
    }

    public String getPicParentPath() {
        return picParentPath;
    }

    public void setPicParentPath(String picParentPath) {
        this.picParentPath = picParentPath;
    }
}
