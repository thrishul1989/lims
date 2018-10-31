package com.todaysoft.lims.sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.AutoKeyEntity;
import com.todaysoft.lims.persist.QueryField;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "LIMS_SAMPLE_TRACING")
@Builder(toBuilder = true)
@AllArgsConstructor
public class SampleTracing extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@QueryField(type = QueryField.QueryType.eq)
	private Integer sampleDetailId; // 样本接收Id
	@QueryField(type = QueryField.QueryType.eq)
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
	private Double inputAmount; // 实验样本样本投入量
	private String inputUnit;
	private Double outputAmount; // 产出量
	private Double remainAmount; // 剩余量
	private String unit; // 单位
	private String QTStatus; // 质检状态 1-合格 2-不合格

	private Integer scheduleId; // 任务流程id
	private Date createTime; //
	@QueryField(type = QueryField.QueryType.eq)
	private String semantic; // 任务类型
	private String taskName; // 任务名称
	private Integer executerId; // 实验员
	private String executerName;
	
	@QueryField(type = QueryField.QueryType.eq)
	private String sheetCode; //混样产物编号
	private String wkbhCode;
	
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
	
	public SampleTracing() {

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "SAMPLE_DETAIL_ID")
	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
	}

	@Column(name = "PRODUCT_ID")
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "ORIGINAL")
	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	@Column(name = "CATEGORY")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "TYPE_ID")
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Column(name = "TYPE_NAME")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "INST_CODE")
	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	@Column(name = "STORAGE_TYPE")
	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	@Column(name = "STORAGE_LOCATION")
	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	@Column(name = "VOLUME")
	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	@Column(name = "CONCN")
	public Double getConcn() {
		return concn;
	}

	public void setConcn(Double concn) {
		this.concn = concn;
	}

	@Column(name = "INDEX_260_280")
	public Double getIndex260280() {
		return index260280;
	}

	public void setIndex260280(Double index260280) {
		this.index260280 = index260280;
	}

	@Column(name = "INDEX_260_230")
	public Double getIndex260230() {
		return index260230;
	}

	public void setIndex260230(Double index260230) {
		this.index260230 = index260230;
	}

	@Column(name = "INDEX_CODE")
	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	@Column(name = "PROBE_ID")
	public Integer getProbeId() {
		return probeId;
	}

	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}

	@Column(name = "PROBE_NAME")
	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}

	@Column(name = "INPUT_AMOUNT")
	public Double getInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(Double inputAmount) {
		this.inputAmount = inputAmount;
	}
	
	@Column(name = "INPUT_UNIT")
	public String getInputUnit() {
		return inputUnit;
	}

	public void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	@Column(name = "OUTPUT_AMOUNT")
	public Double getOutputAmount() {
		return outputAmount;
	}

	public void setOutputAmount(Double outputAmount) {
		this.outputAmount = outputAmount;
	}

	@Column(name = "REMAIN_AMOUNT")
	public Double getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}

	@Column(name = "UNIT")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "QTSTATUS")
	public String getQTStatus() {
		return QTStatus;
	}

	public void setQTStatus(String qTStatus) {
		QTStatus = qTStatus;
	}

	@Column(name = "EXECUTER_ID")
	public Integer getExecuterId() {
		return executerId;
	}

	public void setExecuterId(Integer executerId) {
		this.executerId = executerId;
	}

	@Column(name = "EXECUTER_NAME")
	public String getExecuterName() {
		return executerName;
	}

	public void setExecuterName(String executerName) {
		this.executerName = executerName;
	}

	@Column(name = "SCHEDULE_ID")
	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "SEMANTIC")
	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	@Column(name = "TASK_NAME")
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name = "CT")
	public Double getCT() {
		return CT;
	}

	public void setCT(Double cT) {
		CT = cT;
	}

	@Column(name = "DATA_SIZE")
	public Double getDataSize() {
		return dataSize;
	}

	public void setDataSize(Double dataSize) {
		this.dataSize = dataSize;
	}

	@Column(name = "MIXED_VOLUME")
	public Double getMixedVolume() {
		return mixedVolume;
	}

	public void setMixedVolume(Double mixedVolume) {
		this.mixedVolume = mixedVolume;
	}

	@Column(name = "SHEET_CODE")
	public String getSheetCode() {
		return sheetCode;
	}

	public void setSheetCode(String sheetCode) {
		this.sheetCode = sheetCode;
	}

	@Column(name = "FRAGMENT_LENGTH")
	public Integer getFragmentLength() {
		return fragmentLength;
	}

	public void setFragmentLength(Integer fragmentLength) {
		this.fragmentLength = fragmentLength;
	}

	@Column(name = "CONCENTRATION_PC")
	public Double getConcentrationPC() {
		return concentrationPC;
	}

	public void setConcentrationPC(Double concentrationPC) {
		this.concentrationPC = concentrationPC;
	}

	@Column(name = "CLUSTER")
	public Double getCluster() {
		return cluster;
	}

	public void setCluster(Double cluster) {
		this.cluster = cluster;
	}

	@Column(name = "CLUSTER_EFFECTIVE_RATE")
	public Double getClusterEffectiveRate() {
		return clusterEffectiveRate;
	}

	public void setClusterEffectiveRate(Double clusterEffectiveRate) {
		this.clusterEffectiveRate = clusterEffectiveRate;
	}

	@Column(name = "EFFICIENT_DATA")
	public Double getEfficientData() {
		return efficientData;
	}

	public void setEfficientData(Double efficientData) {
		this.efficientData = efficientData;
	}

	@Column(name = "Q30")
	public Double getQ30() {
		return Q30;
	}

	public void setQ30(Double q30) {
		Q30 = q30;
	}

	@Column(name = "WKBH_CODE")
	public String getWkbhCode() {
		return wkbhCode;
	}

	public void setWkbhCode(String wkbhCode) {
		this.wkbhCode = wkbhCode;
	}

	@Column(name = "COORDINATE_LIST")
	public String getCoordinateList() {
		return coordinateList;
	}

	public void setCoordinateList(String coordinateList) {
		this.coordinateList = coordinateList;
	}

	@Column(name = "CHECK_MANAGEMENT")
	public String getCheckManagement() {
		return checkManagement;
	}

	public void setCheckManagement(String checkManagement) {
		this.checkManagement = checkManagement;
	}
}
