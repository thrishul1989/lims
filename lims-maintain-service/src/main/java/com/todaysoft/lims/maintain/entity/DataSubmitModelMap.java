package com.todaysoft.lims.maintain.entity;


import com.google.common.collect.Maps;

import java.util.Map;

public class DataSubmitModelMap
{
    public  Map<String,String> capNgsMap = Maps.newHashMap();

    public  Map<String,String> ngsMap = Maps.newHashMap();

    public  Map<String,String> qpcrMap = Maps.newHashMap();

    public  Map<String,String> sangerVerifyMap = Maps.newHashMap();

    public  Map<String,String> mlpaVerifyMap = Maps.newHashMap();

    public  Map<String,String> pcrNgsMap = Maps.newHashMap();

    public  Map<String,String> fluoMap = Maps.newHashMap();

    public  Map<String,String> sangerTestMap = Maps.newHashMap();

    public  Map<String,String> dtMap = Maps.newHashMap();

    public DataSubmitModelMap()
    {
        initCapNgsMap();
        initNgsMap();
        //数据分析提交的
        initQpcrMap();
        initSangerVerifyMap();
        initMlpaVerifyMap();
        initPcrNgsMap();
        initFluoMap();
        initSangerTestMap();
        initDtMap();
    }

    public void initCapNgsMap()
    {
        capNgsMap.put("下一步","verify");
        capNgsMap.put("分析位点注释","locusType");
        capNgsMap.put("验证方法","verifyMethod");
        capNgsMap.put("DataID","dataCode");
        capNgsMap.put("Sample","sample");
        capNgsMap.put("下一步","verify");
        capNgsMap.put("Gene_Symbol","geneSymbol");
        capNgsMap.put("ID","chrLocation");
        capNgsMap.put("Ref_Transcript","refTranscript");
        capNgsMap.put("Exon","exon");
        capNgsMap.put("Nucleotide_Changes","nucleotideChanges");
        capNgsMap.put("Amino_Acid_Changes","aminoAcidChanges");
        capNgsMap.put("Gene_Type","geneType");
        capNgsMap.put("Chr","chromosome");
        capNgsMap.put("Begin","beginLocus");
        capNgsMap.put("End","endLocus");
        capNgsMap.put("上游参考基因","upRefGene");
        capNgsMap.put("下游参考基因","downRefGene");
        capNgsMap.put("对照样本","refSample");
        capNgsMap.put("inhert","inhert");
        capNgsMap.put("Gene_Region","geneRegion");
        capNgsMap.put("CytoBand","cytoBand");
        capNgsMap.put("dbsnp147","dbsnp147");
    }

    public void initNgsMap()
    {
        ngsMap.put("下一步","verify");
        ngsMap.put("位点类型","locusType");
        ngsMap.put("验证方法","verifyMethod");
        ngsMap.put("DataID","dataCode");
        ngsMap.put("Sample","sample");
        ngsMap.put("Chr","chromosome");
        ngsMap.put("Start","beginLocus");
        ngsMap.put("End","endLocus");
        ngsMap.put("inhert","inhert");
    }

    public void initQpcrMap()
    {
        qpcrMap.put("合并编号","combineCode");
        qpcrMap.put("结果","result");
        qpcrMap.put("处理意见","dispose");
        qpcrMap.put("纯合/杂合","combineType");
        qpcrMap.put("突变来源","mutationSource");
        qpcrMap.put("备注","remark");
        qpcrMap.put("阴/阳性","negaOrPositive");
        qpcrMap.put("结论","conclusion");
    }

    public void initSangerVerifyMap()
    {
        sangerVerifyMap.put("合并编号","combineCode");
        sangerVerifyMap.put("结果","result");
        sangerVerifyMap.put("处理意见","dispose");
        sangerVerifyMap.put("纯合/杂合","combineType");
        sangerVerifyMap.put("突变来源","mutationSource");
        sangerVerifyMap.put("备注","remark");
    }


    public void initMlpaVerifyMap()
    {
        mlpaVerifyMap.put("样本编号","sampleCode");
        mlpaVerifyMap.put("突变基因","gene");
        mlpaVerifyMap.put("染色体位置","chromosomeLocation");
        mlpaVerifyMap.put("突变来源","mutationSource");
        mlpaVerifyMap.put("外显子","exon");
        mlpaVerifyMap.put("核苷酸变化","nucleotideChange");
        mlpaVerifyMap.put("纯合/杂合","pureHeteroNucleus");
        mlpaVerifyMap.put("备注","remark");
        mlpaVerifyMap.put("阴/阳性","negaOrPositive");
        mlpaVerifyMap.put("结论","conclusion");

    }


    public void initPcrNgsMap()
    {
        pcrNgsMap.put("合并编号","combineCode");
        pcrNgsMap.put("结果","result");
        pcrNgsMap.put("处理意见","dispose");
        pcrNgsMap.put("纯合/杂合","combineType");
        pcrNgsMap.put("突变来源","mutationSource");
        pcrNgsMap.put("备注","remark");
    }


    public void initFluoMap()
    {
        fluoMap.put("样本编号","sampleCode");
        fluoMap.put("突变基因","gene");
        fluoMap.put("染色体位置","chromosomeLocation");
        fluoMap.put("转录本号","transcriptCode");
        fluoMap.put("外显子","exon");
        fluoMap.put("核苷酸变化","nucleotideChange");
        fluoMap.put("氨基酸变化","aminoAcidChange");
        fluoMap.put("纯合/杂合","pureHeteroNucleus");
        fluoMap.put("备注","remark");
        fluoMap.put("阴/阳性","negaOrPositive");
        fluoMap.put("结论","conclusion");

    }


    public void initSangerTestMap()
    {
        sangerTestMap.put("合并编号","combineCode");
        sangerTestMap.put("结果","result");
        sangerTestMap.put("处理意见","dispose");
        sangerTestMap.put("纯合/杂合","combineType");
        sangerTestMap.put("突变来源","mutationSource");
        sangerTestMap.put("备注","remark");
    }


    public void initDtMap()
    {
        dtMap.put("合并名称","combineCode");
        dtMap.put("样本编号","sampleCode");
        dtMap.put("疾病类型","diseaseType");
        dtMap.put("检测基因","testGene");
        dtMap.put("重复数","repeatNumber");
        dtMap.put("正常参考值","normalValue");
        dtMap.put("备注","remark");
        dtMap.put("阴/阳性","negaOrPositive");
        dtMap.put("结论","conclusion");
    }


    public Map<String, String> getCapNgsMap() {
        return capNgsMap;
    }

    public void setCapNgsMap(Map<String, String> capNgsMap) {
        this.capNgsMap = capNgsMap;
    }

    public Map<String, String> getNgsMap() {
        return ngsMap;
    }

    public void setNgsMap(Map<String, String> ngsMap) {
        this.ngsMap = ngsMap;
    }

    public Map<String, String> getQpcrMap() {
        return qpcrMap;
    }

    public void setQpcrMap(Map<String, String> qpcrMap) {
        this.qpcrMap = qpcrMap;
    }

    public Map<String, String> getSangerVerifyMap() {
        return sangerVerifyMap;
    }

    public void setSangerVerifyMap(Map<String, String> sangerVerifyMap) {
        this.sangerVerifyMap = sangerVerifyMap;
    }

    public Map<String, String> getMlpaVerifyMap() {
        return mlpaVerifyMap;
    }

    public void setMlpaVerifyMap(Map<String, String> mlpaVerifyMap) {
        this.mlpaVerifyMap = mlpaVerifyMap;
    }

    public Map<String, String> getPcrNgsMap() {
        return pcrNgsMap;
    }

    public void setPcrNgsMap(Map<String, String> pcrNgsMap) {
        this.pcrNgsMap = pcrNgsMap;
    }

    public Map<String, String> getFluoMap() {
        return fluoMap;
    }

    public void setFluoMap(Map<String, String> fluoMap) {
        this.fluoMap = fluoMap;
    }

    public Map<String, String> getSangerTestMap() {
        return sangerTestMap;
    }

    public void setSangerTestMap(Map<String, String> sangerTestMap) {
        this.sangerTestMap = sangerTestMap;
    }

    public Map<String, String> getDtMap() {
        return dtMap;
    }

    public void setDtMap(Map<String, String> dtMap) {
        this.dtMap = dtMap;
    }
}
