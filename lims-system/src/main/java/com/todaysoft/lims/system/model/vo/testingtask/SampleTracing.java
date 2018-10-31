package com.todaysoft.lims.system.model.vo.testingtask;

import java.util.Date;

public class SampleTracing {

	private Integer id;
	private Integer sampleDetailId; // 样本接收Id
	private Integer productId; // 产品id
	private String original; // 是否是原始样本 1-是 0-否
	private String category; // 样本类别 1-样本 2-实验产物
	private Integer typeId; // 样本id（和样本类别有关）
	private String typeName; // 样本名称
	private String instCode; // 当前样本编号
	private String storageType; // 存储类别 1-临存 2-长存
	private String storageLocation; // 存储位置

	// 质检结果
	private Double volume; // 体积
	private Double concn; // 浓度
	private Double index260280; // 指标
	private Double index260230; // 指标

	private String indexCode; // 接头编号
	private Integer probeId; // 探针
	private String probeName; //
	private Double inputAmount; // 实验投入量
	private String inputUnit;
	private Double outputAmount; // 产出量
	private Double remainAmount; // 剩余量
	private String unit; // 单位
	private String QTStatus; // 质检状态 1-合格 2-不合格

	private Integer scheduleId; // 任务流程id
	private Date createTime; //
	private String semantic; // 任务类型
	private String taskName; // 任务名称
	private Integer executerId; // 实验员
	private String executerName;
	
	private String sheetCode; //混样产物编号
	
	private Double CT;
	private Double dataSize;
	private Double mixedVolume;
	
	private Integer fragmentLength; // 片段长度
    private Double concentrationPC; // 上机浓度
    private Double cluster;
    private Double clusterEffectiveRate;
    private Double efficientData; // 有效数据量
    private Double Q30;

    private String coordinateList; //分析坐标
    private String checkManagement;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getConcn() {
		return concn;
	}

	public void setConcn(Double concn) {
		this.concn = concn;
	}

	public Double getIndex260280() {
		return index260280;
	}

	public void setIndex260280(Double index260280) {
		this.index260280 = index260280;
	}

	public Double getIndex260230() {
		return index260230;
	}

	public void setIndex260230(Double index260230) {
		this.index260230 = index260230;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public Integer getProbeId() {
		return probeId;
	}

	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}

	public Double getInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(Double inputAmount) {
		this.inputAmount = inputAmount;
	}

	public String getInputUnit() {
		return inputUnit;
	}

	public void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	public Double getOutputAmount() {
		return outputAmount;
	}

	public void setOutputAmount(Double outputAmount) {
		this.outputAmount = outputAmount;
	}

	public Double getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getQTStatus() {
		return QTStatus;
	}

	public void setQTStatus(String qTStatus) {
		QTStatus = qTStatus;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getExecuterId() {
		return executerId;
	}

	public void setExecuterId(Integer executerId) {
		this.executerId = executerId;
	}

	public String getExecuterName() {
		return executerName;
	}

	public void setExecuterName(String executerName) {
		this.executerName = executerName;
	}

	public Double getCT() {
		return CT;
	}

	public void setCT(Double cT) {
		CT = cT;
	}

	public Double getDataSize() {
		return dataSize;
	}

	public void setDataSize(Double dataSize) {
		this.dataSize = dataSize;
	}

	public Double getMixedVolume() {
		return mixedVolume;
	}

	public void setMixedVolume(Double mixedVolume) {
		this.mixedVolume = mixedVolume;
	}

	public Integer getFragmentLength() {
		return fragmentLength;
	}

	public void setFragmentLength(Integer fragmentLength) {
		this.fragmentLength = fragmentLength;
	}

	public Double getConcentrationPC() {
		return concentrationPC;
	}

	public void setConcentrationPC(Double concentrationPC) {
		this.concentrationPC = concentrationPC;
	}

	public Double getCluster() {
		return cluster;
	}

	public void setCluster(Double cluster) {
		this.cluster = cluster;
	}

	public Double getClusterEffectiveRate() {
		return clusterEffectiveRate;
	}

	public void setClusterEffectiveRate(Double clusterEffectiveRate) {
		this.clusterEffectiveRate = clusterEffectiveRate;
	}

	public Double getEfficientData() {
		return efficientData;
	}

	public void setEfficientData(Double efficientData) {
		this.efficientData = efficientData;
	}

	public Double getQ30() {
		return Q30;
	}

	public void setQ30(Double q30) {
		Q30 = q30;
	}

	public String getSheetCode() {
		return sheetCode;
	}

	public void setSheetCode(String sheetCode) {
		this.sheetCode = sheetCode;
	}

	public String getCoordinateList() {
		return coordinateList;
	}

	public void setCoordinateList(String coordinateList) {
		this.coordinateList = coordinateList;
	}

	public String getCheckManagement() {
		return checkManagement;
	}

	public void setCheckManagement(String checkManagement) {
		this.checkManagement = checkManagement;
	}
}
