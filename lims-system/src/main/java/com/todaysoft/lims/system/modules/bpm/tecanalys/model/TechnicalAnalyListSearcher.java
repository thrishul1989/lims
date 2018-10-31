package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

public class TechnicalAnalyListSearcher
{
    public static final String SAMPLEINFO = "0";//样本信息
    public static final String QUALITYCONTROL  = "1";//质控
    public static final String MUTATIONLIST  = "2";//突变列表
    public static final String CAPCNVANALYSIS  = "3";//CapCNV分析
    public static final String SVDATA  = "4";//SV数据
    public static final String REGIONCOUT  = "5";//regioncout
    
    private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
   
}
