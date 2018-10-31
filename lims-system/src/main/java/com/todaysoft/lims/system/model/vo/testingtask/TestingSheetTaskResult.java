package com.todaysoft.lims.system.model.vo.testingtask;

public class TestingSheetTaskResult  {
	
	private Integer id;
	
	private String activitiTaskId;
    
    private Integer result; //1.成功 2.失败可重做 3.失败不可重做
    
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
}
