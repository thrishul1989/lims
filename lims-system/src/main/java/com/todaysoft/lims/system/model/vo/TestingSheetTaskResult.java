package com.todaysoft.lims.system.model.vo;

import java.util.List;

public class TestingSheetTaskResult{

	private Integer id;
	
	private String activitiTaskId;
    
    private Integer result; //1.成功 2.失败可重做 3.失败不可重做
    
    private String reason;
    
    private Double inputAmount; //输入样本量
    
    private String inputUnit;
    
    private Double outputAmount; //输出样本量
    
    private String outputUnit;
    
    private Double concentration; //浓度(ng/ul)

	private String qtLevel;
    
    private Double volume; //体积(ul)
    
    private Double A260280; //A260/280
    
    private Double A260230; //A260/230
    
    private String dispose; //处理方式
    
    private String QTResult; //质检结果（系统生成）
    
    private String remark; //备注
    
    private Double CT;
    
    private Double productRelativeAmount; //产物相对量

    private Double mixedVolume; //混合体积
    
    private Double volumeRatioToMix; //微调后的混合体积
    
    private Integer fragmentLength; // 片段长度
    
    private Double concentrationPC; // 上机浓度
    
    private Double cluster;
    
    private Double clusterEffectiveRate;
    
    private Double efficientData; // 有效数据量
    
    private Double Q30;
    
    private List<TestingSheetTaskAbsoluteData> absoluteDataList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getOutputUnit() {
		return outputUnit;
	}

	public void setOutputUnit(String outputUnit) {
		this.outputUnit = outputUnit;
	}

	public Double getConcentration() {
		return concentration;
	}

	public void setConcentration(Double concentration) {
		this.concentration = concentration;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getA260280() {
		return A260280;
	}

	public void setA260280(Double a260280) {
		A260280 = a260280;
	}

	public Double getA260230() {
		return A260230;
	}

	public void setA260230(Double a260230) {
		A260230 = a260230;
	}

	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}

	public String getQTResult() {
		return QTResult;
	}

	public void setQTResult(String qTResult) {
		QTResult = qTResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getCT() {
		return CT;
	}

	public void setCT(Double cT) {
		CT = cT;
	}

	public Double getProductRelativeAmount() {
		return productRelativeAmount;
	}

	public void setProductRelativeAmount(Double productRelativeAmount) {
		this.productRelativeAmount = productRelativeAmount;
	}

	public Double getMixedVolume() {
		return mixedVolume;
	}

	public void setMixedVolume(Double mixedVolume) {
		this.mixedVolume = mixedVolume;
	}

	public Double getVolumeRatioToMix() {
		return volumeRatioToMix;
	}

	public void setVolumeRatioToMix(Double volumeRatioToMix) {
		this.volumeRatioToMix = volumeRatioToMix;
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

	public List<TestingSheetTaskAbsoluteData> getAbsoluteDataList() {
		return absoluteDataList;
	}

	public void setAbsoluteDataList(List<TestingSheetTaskAbsoluteData> absoluteDataList) {
		this.absoluteDataList = absoluteDataList;
	}

	public String getQtLevel() {
		return qtLevel;
	}

	public void setQtLevel(String qtLevel) {
		this.qtLevel = qtLevel;
	}
}
