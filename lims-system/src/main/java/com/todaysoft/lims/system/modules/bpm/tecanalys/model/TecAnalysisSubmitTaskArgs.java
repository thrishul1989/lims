package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

public class TecAnalysisSubmitTaskArgs
{
    private String id;
    
    private String nextStep; //下一步
    
    private String locusType; //位点类型
    
    private String validationMethod; //验证方法
    
    private String descr; //文献报道的氨基酸变化
    
    private String hgvs; //文献报道的碱基变化
    
    private String disease; //文献报道的疾病
    
    private String tag; //致病因子
    
    private String publication; //报道文献
    
    private String effect; //突变影响
    
    private String sampleCode; //
    
    private String geneSymbol; //突变基因
    
    private String chromosomalLocation; //染色体位置
    
    private String refTranscript; //转录本编号
    
    private String exon; //外显子
    
    private String nucleotideChanges; //核苷酸变化
    
    private String aminoAcidChanges; //氨基酸变化
    
    private String geneType; //纯合/杂合
    
    private String normalFrequency; //正常人频率
    
    private String pathogenicAnalysis; //致病性预测
    
    private String inhert;//遗传方式
    
    private String diseasePhenotype; //疾病/表型
    
    private String clinicalPhenotype; //临床表现型
    
    private String diseaseInformation; //疾病简介
    
    private String clinvarDatabase; //clinvar数据库
    
    private String refAltFre; //参考碱基/变异碱基(突变频率)
    
    private String chr; //染色体
    
    private String beginLocation; //位置1
    
    private String endLocation; //位置2
    
    private String refBase; //参考碱基
    
    private String altBase; //突变碱基
    
    private String mutRatio; //突变频率
    
    private String refCount; //参考碱基深度
    
    private String mutCount; //
    
    private String depth; //测序深度
    
    private String mutationType; //变异类别
    
    private String geneRegion; //变异区域
    
    private String cytoBand; //染色体区段
    
    private String dbsnp147; //SNP编号
    
    private String pathSNP; //致病性SNP
    
    private String mutInNormal; //正常人中的突变
    
    private String genome1000; //千人基因组
    
    private String mutInDatabase; //迈基诺致病数据库
    
    private String all1000g2015aug; //千人基因组频率
    
    private String ESP6500si; //ESP6500频率
    
    private String inhouse; //迈基诺正常人频率
    
    private String exACDatabase; //EXAC数据库频率
    
    private String exACEAS; //ExAC亚洲人频率
    
    private String SIFT; //SIFT
    
    private String SIFTPredict; //SIFT_Predict
    
    private String polyPhen2; //PolyPhen_2
    
    private String polyPhen2Predict; //PolyPhen_2_Predict
    
    private String mutationTaster; //MutationTaster
    
    private String mutationTasterPredict; //MutationTaster_Predict
    
    private String GERP; //GERP++
    
    private String GERPPredict; //GERP++_Predict
    
    private String SPIDEX; //SPIDEX
    
    private String AAChange; //变异注释
    
    private String dbNsfpInterPro; //dbNsfpInterPro
    
    private String REVELScore; //REVEL_score
    
    private String MCAPScore; //MCAP_score
    
    private String MCAPPred; //MCAP_pred
    
    private String upstreamGene; //上游内参基因
    
    private String downstreamGene; //下游内参基因
    
    private String referenceSample; //对照样本
    
    private String familySituation; //家系情况
    
    private String validationProtocols; //验证方案
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getNextStep()
    {
        return nextStep;
    }
    
    public void setNextStep(String nextStep)
    {
        this.nextStep = nextStep;
    }
    
    public String getLocusType()
    {
        return locusType;
    }
    
    public void setLocusType(String locusType)
    {
        this.locusType = locusType;
    }
    
    public String getValidationMethod()
    {
        return validationMethod;
    }
    
    public void setValidationMethod(String validationMethod)
    {
        this.validationMethod = validationMethod;
    }
    
    public String getDescr()
    {
        return descr;
    }
    
    public void setDescr(String descr)
    {
        this.descr = descr;
    }
    
    public String getHgvs()
    {
        return hgvs;
    }
    
    public void setHgvs(String hgvs)
    {
        this.hgvs = hgvs;
    }
    
    public String getDisease()
    {
        return disease;
    }
    
    public void setDisease(String disease)
    {
        this.disease = disease;
    }
    
    public String getTag()
    {
        return tag;
    }
    
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    
    public String getPublication()
    {
        return publication;
    }
    
    public void setPublication(String publication)
    {
        this.publication = publication;
    }
    
    public String getEffect()
    {
        return effect;
    }
    
    public void setEffect(String effect)
    {
        this.effect = effect;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getGeneSymbol()
    {
        return geneSymbol;
    }
    
    public void setGeneSymbol(String geneSymbol)
    {
        this.geneSymbol = geneSymbol;
    }
    
    public String getChromosomalLocation()
    {
        return chromosomalLocation;
    }
    
    public void setChromosomalLocation(String chromosomalLocation)
    {
        this.chromosomalLocation = chromosomalLocation;
    }
    
    public String getRefTranscript()
    {
        return refTranscript;
    }
    
    public void setRefTranscript(String refTranscript)
    {
        this.refTranscript = refTranscript;
    }
    
    public String getExon()
    {
        return exon;
    }
    
    public void setExon(String exon)
    {
        this.exon = exon;
    }
    
    public String getNucleotideChanges()
    {
        return nucleotideChanges;
    }
    
    public void setNucleotideChanges(String nucleotideChanges)
    {
        this.nucleotideChanges = nucleotideChanges;
    }
    
    public String getAminoAcidChanges()
    {
        return aminoAcidChanges;
    }
    
    public void setAminoAcidChanges(String aminoAcidChanges)
    {
        this.aminoAcidChanges = aminoAcidChanges;
    }
    
    public String getGeneType()
    {
        return geneType;
    }
    
    public void setGeneType(String geneType)
    {
        this.geneType = geneType;
    }
    
    public String getNormalFrequency()
    {
        return normalFrequency;
    }
    
    public void setNormalFrequency(String normalFrequency)
    {
        this.normalFrequency = normalFrequency;
    }
    
    public String getPathogenicAnalysis()
    {
        return pathogenicAnalysis;
    }
    
    public void setPathogenicAnalysis(String pathogenicAnalysis)
    {
        this.pathogenicAnalysis = pathogenicAnalysis;
    }
    
    public String getInhert()
    {
        return inhert;
    }
    
    public void setInhert(String inhert)
    {
        this.inhert = inhert;
    }
    
    public String getDiseasePhenotype()
    {
        return diseasePhenotype;
    }
    
    public void setDiseasePhenotype(String diseasePhenotype)
    {
        this.diseasePhenotype = diseasePhenotype;
    }
    
    public String getClinicalPhenotype()
    {
        return clinicalPhenotype;
    }
    
    public void setClinicalPhenotype(String clinicalPhenotype)
    {
        this.clinicalPhenotype = clinicalPhenotype;
    }
    
    public String getDiseaseInformation()
    {
        return diseaseInformation;
    }
    
    public void setDiseaseInformation(String diseaseInformation)
    {
        this.diseaseInformation = diseaseInformation;
    }
    
    public String getClinvarDatabase()
    {
        return clinvarDatabase;
    }
    
    public void setClinvarDatabase(String clinvarDatabase)
    {
        this.clinvarDatabase = clinvarDatabase;
    }
    
    public String getRefAltFre()
    {
        return refAltFre;
    }
    
    public void setRefAltFre(String refAltFre)
    {
        this.refAltFre = refAltFre;
    }
    
    public String getChr()
    {
        return chr;
    }
    
    public void setChr(String chr)
    {
        this.chr = chr;
    }
    
    public String getBeginLocation()
    {
        return beginLocation;
    }
    
    public void setBeginLocation(String beginLocation)
    {
        this.beginLocation = beginLocation;
    }
    
    public String getEndLocation()
    {
        return endLocation;
    }
    
    public void setEndLocation(String endLocation)
    {
        this.endLocation = endLocation;
    }
    
    public String getRefBase()
    {
        return refBase;
    }
    
    public void setRefBase(String refBase)
    {
        this.refBase = refBase;
    }
    
    public String getAltBase()
    {
        return altBase;
    }
    
    public void setAltBase(String altBase)
    {
        this.altBase = altBase;
    }
    
    public String getMutRatio()
    {
        return mutRatio;
    }
    
    public void setMutRatio(String mutRatio)
    {
        this.mutRatio = mutRatio;
    }
    
    public String getRefCount()
    {
        return refCount;
    }
    
    public void setRefCount(String refCount)
    {
        this.refCount = refCount;
    }
    
    public String getMutCount()
    {
        return mutCount;
    }
    
    public void setMutCount(String mutCount)
    {
        this.mutCount = mutCount;
    }
    
    public String getDepth()
    {
        return depth;
    }
    
    public void setDepth(String depth)
    {
        this.depth = depth;
    }
    
    public String getMutationType()
    {
        return mutationType;
    }
    
    public void setMutationType(String mutationType)
    {
        this.mutationType = mutationType;
    }
    
    public String getGeneRegion()
    {
        return geneRegion;
    }
    
    public void setGeneRegion(String geneRegion)
    {
        this.geneRegion = geneRegion;
    }
    
    public String getCytoBand()
    {
        return cytoBand;
    }
    
    public void setCytoBand(String cytoBand)
    {
        this.cytoBand = cytoBand;
    }
    
    public String getDbsnp147()
    {
        return dbsnp147;
    }
    
    public void setDbsnp147(String dbsnp147)
    {
        this.dbsnp147 = dbsnp147;
    }
    
    public String getPathSNP()
    {
        return pathSNP;
    }
    
    public void setPathSNP(String pathSNP)
    {
        this.pathSNP = pathSNP;
    }
    
    public String getMutInNormal()
    {
        return mutInNormal;
    }
    
    public void setMutInNormal(String mutInNormal)
    {
        this.mutInNormal = mutInNormal;
    }
    
    public String getGenome1000()
    {
        return genome1000;
    }
    
    public void setGenome1000(String genome1000)
    {
        this.genome1000 = genome1000;
    }
    
    public String getMutInDatabase()
    {
        return mutInDatabase;
    }
    
    public void setMutInDatabase(String mutInDatabase)
    {
        this.mutInDatabase = mutInDatabase;
    }
    
    public String getAll1000g2015aug()
    {
        return all1000g2015aug;
    }
    
    public void setAll1000g2015aug(String all1000g2015aug)
    {
        this.all1000g2015aug = all1000g2015aug;
    }
    
    public String getESP6500si()
    {
        return ESP6500si;
    }
    
    public void setESP6500si(String eSP6500si)
    {
        ESP6500si = eSP6500si;
    }
    
    public String getInhouse()
    {
        return inhouse;
    }
    
    public void setInhouse(String inhouse)
    {
        this.inhouse = inhouse;
    }
    
    public String getExACDatabase()
    {
        return exACDatabase;
    }
    
    public void setExACDatabase(String exACDatabase)
    {
        this.exACDatabase = exACDatabase;
    }
    
    public String getExACEAS()
    {
        return exACEAS;
    }
    
    public void setExACEAS(String exACEAS)
    {
        this.exACEAS = exACEAS;
    }
    
    public String getSIFT()
    {
        return SIFT;
    }
    
    public void setSIFT(String sIFT)
    {
        SIFT = sIFT;
    }
    
    public String getSIFTPredict()
    {
        return SIFTPredict;
    }
    
    public void setSIFTPredict(String sIFTPredict)
    {
        SIFTPredict = sIFTPredict;
    }
    
    public String getPolyPhen2()
    {
        return polyPhen2;
    }
    
    public void setPolyPhen2(String polyPhen2)
    {
        this.polyPhen2 = polyPhen2;
    }
    
    public String getPolyPhen2Predict()
    {
        return polyPhen2Predict;
    }
    
    public void setPolyPhen2Predict(String polyPhen2Predict)
    {
        this.polyPhen2Predict = polyPhen2Predict;
    }
    
    public String getMutationTaster()
    {
        return mutationTaster;
    }
    
    public void setMutationTaster(String mutationTaster)
    {
        this.mutationTaster = mutationTaster;
    }
    
    public String getMutationTasterPredict()
    {
        return mutationTasterPredict;
    }
    
    public void setMutationTasterPredict(String mutationTasterPredict)
    {
        this.mutationTasterPredict = mutationTasterPredict;
    }
    
    public String getGERP()
    {
        return GERP;
    }
    
    public void setGERP(String gERP)
    {
        GERP = gERP;
    }
    
    public String getGERPPredict()
    {
        return GERPPredict;
    }
    
    public void setGERPPredict(String gERPPredict)
    {
        GERPPredict = gERPPredict;
    }
    
    public String getSPIDEX()
    {
        return SPIDEX;
    }
    
    public void setSPIDEX(String sPIDEX)
    {
        SPIDEX = sPIDEX;
    }
    
    public String getAAChange()
    {
        return AAChange;
    }
    
    public void setAAChange(String aAChange)
    {
        AAChange = aAChange;
    }
    
    public String getDbNsfpInterPro()
    {
        return dbNsfpInterPro;
    }
    
    public void setDbNsfpInterPro(String dbNsfpInterPro)
    {
        this.dbNsfpInterPro = dbNsfpInterPro;
    }
    
    public String getREVELScore()
    {
        return REVELScore;
    }
    
    public void setREVELScore(String rEVELScore)
    {
        REVELScore = rEVELScore;
    }
    
    public String getMCAPScore()
    {
        return MCAPScore;
    }
    
    public void setMCAPScore(String mCAPScore)
    {
        MCAPScore = mCAPScore;
    }
    
    public String getMCAPPred()
    {
        return MCAPPred;
    }
    
    public void setMCAPPred(String mCAPPred)
    {
        MCAPPred = mCAPPred;
    }
    
    public String getUpstreamGene()
    {
        return upstreamGene;
    }
    
    public void setUpstreamGene(String upstreamGene)
    {
        this.upstreamGene = upstreamGene;
    }
    
    public String getDownstreamGene()
    {
        return downstreamGene;
    }
    
    public void setDownstreamGene(String downstreamGene)
    {
        this.downstreamGene = downstreamGene;
    }
    
    public String getReferenceSample()
    {
        return referenceSample;
    }
    
    public void setReferenceSample(String referenceSample)
    {
        this.referenceSample = referenceSample;
    }
    
    public String getFamilySituation()
    {
        return familySituation;
    }
    
    public void setFamilySituation(String familySituation)
    {
        this.familySituation = familySituation;
    }
    
    public String getValidationProtocols()
    {
        return validationProtocols;
    }
    
    public void setValidationProtocols(String validationProtocols)
    {
        this.validationProtocols = validationProtocols;
    }
}
