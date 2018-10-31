package com.todaysoft.lims.sample.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.AutoKeyEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "LIMS_TESTING_SHEET_TASK_RESULT")
@Builder(toBuilder = true)
@AllArgsConstructor
public class TestingSheetTaskResult extends AutoKeyEntity {

	private static final long serialVersionUID = 1843628974948804402L;

	private String activitiTaskId;
    
    private Integer result; //1.成功 2.失败可重做 3.失败不可重做，提取结果
    
    private String reason;
    
    private Double inputAmount; //输入样本量
    
    private String inputUnit;
    
    private Double outputAmount; //输出样本量
    
    private String outputUnit;
    
    private Double concentration; //浓度(ng/ul)
    
    private Double volume; //体积(ul)
    
    private Double A260280; //A260/280
    
    private Double A260230; //A260/230
    
    private String dispose; //处理方式
    
    private String QTResult; //质检结果（系统生成）

	private String qtLevel;//质量等级
    
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
    
    public TestingSheetTaskResult(){
    	
    }

	@Column(name = "ACTIVITI_TASK_ID")
	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	@Column(name = "RESULT")
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	@Column(name = "REASON")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	@Column(name = "OUTPUT_UNIT")
	public String getOutputUnit() {
		return outputUnit;
	}

	public void setOutputUnit(String outputUnit) {
		this.outputUnit = outputUnit;
	}

	@Column(name = "CONCENTRATION")
	public Double getConcentration() {
		return concentration;
	}

	public void setConcentration(Double concentration) {
		this.concentration = concentration;
	}

	@Column(name = "VOLUME")
	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	@Column(name = "A260280")
	public Double getA260280() {
		return A260280;
	}

	public void setA260280(Double a260280) {
		A260280 = a260280;
	}

	@Column(name = "A260230")
	public Double getA260230() {
		return A260230;
	}

	public void setA260230(Double a260230) {
		A260230 = a260230;
	}

	@Column(name = "DISPOSE")
	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}

	@Column(name = "QTRESULT")
	public String getQTResult() {
		return QTResult;
	}

	public void setQTResult(String qTResult) {
		QTResult = qTResult;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CT")
	public Double getCT() {
		return CT;
	}

	public void setCT(Double cT) {
		CT = cT;
	}

	@Column(name = "PRODUCT_RELATIVE_AMOUNT")
	public Double getProductRelativeAmount() {
		return productRelativeAmount;
	}

	public void setProductRelativeAmount(Double productRelativeAmount) {
		this.productRelativeAmount = productRelativeAmount;
	}

	@Column(name = "MIXED_VOLUME")
	public Double getMixedVolume() {
		return mixedVolume;
	}

	public void setMixedVolume(Double mixedVolume) {
		this.mixedVolume = mixedVolume;
	}

	@Column(name = "VOLUME_RATIO_TO_MIX")
	public Double getVolumeRatioToMix() {
		return volumeRatioToMix;
	}
	
	public void setVolumeRatioToMix(Double volumeRatioToMix) {
		this.volumeRatioToMix = volumeRatioToMix;
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
	
	@OneToMany(mappedBy = "sheetTaskResult",cascade= {CascadeType.ALL},fetch=FetchType.EAGER )
	@NotFound(action = NotFoundAction.IGNORE)
	public List<TestingSheetTaskAbsoluteData> getAbsoluteDataList() {
		return absoluteDataList;
	}

	public void setAbsoluteDataList(
			List<TestingSheetTaskAbsoluteData> absoluteDataList) {
		this.absoluteDataList = absoluteDataList;
	}
	@Column(name = "qt_level")
	public String getQtLevel() {
		return qtLevel;
	}

	public void setQtLevel(String qtLevel) {
		this.qtLevel = qtLevel;
	}
}
