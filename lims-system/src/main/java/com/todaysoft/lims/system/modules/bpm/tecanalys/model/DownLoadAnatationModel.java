package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

public class DownLoadAnatationModel {

	private String fileName;     //文件名称
	private String fileSize;     //文件大小
	private String filePath;     //下载路径

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

}
