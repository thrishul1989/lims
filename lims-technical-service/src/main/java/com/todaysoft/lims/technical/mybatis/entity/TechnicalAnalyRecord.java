package com.todaysoft.lims.technical.mybatis.entity;

public class TechnicalAnalyRecord
{
    public static final String FAMILYTESTMETHOD_MLPA = "MLPA";
    
    public static final String FAMILYTESTMETHOD_QPCR = "QPCR";
    
    public static final String SOURCE_REF_CAPCNV = "2";
    
    private String id;
    
    private String locusType;
    
    private String verify;
    
    private String verifyMethod;
    
    private String upReferGene;
    
    private String downReferGene;
    
    private String referSample;
    
    private String aacDocument;
    
    private String hgvs;
    
    private String diseaseDocument;
    
    private String tag;
    
    private String publication;
    
    private String effect;
    
    private String sample;
    
    private String geneSymbol;
    
    private String chromosomeLocation;
    
    private String refTranscript;
    
    private String exon;
    
    private String nucleotideChanges;
    
    private String aminoacidChanges;
    
    private String geneType;
    
    private String aug2015;
    
    private String pathogenicAnalysis;
    
    private String inhert;
    
    private String clinvar2016;
    
    private String referAltFrequency;
    
    private String chromosome;
    
    private String beginLocus;
    
    private String endLocus;
    
    private String referBase;
    
    private String altBase;
    
    private String mutationRatio;
    
    private String referCount;
    
    private String mutationCount;
    
    private String depth;
    
    private String mutationType;
    
    private String geneRegion;
    
    private String cytoBand;
    
    private String dbsnp147;
    
    private String pathsnp;
    
    private String mutationNormal;
    
    private String genome1000;
    
    private String mutationDatabase;
    
    private String aug1000g2015;
    
    private String esp6500si;
    
    private String inhouse;
    
    private String exacAll;
    
    private String exacEas;
    
    private String sift;
    
    private String siftPredict;
    
    private String polyphen2;
    
    private String polyphen2Predict;
    
    private String mutationTaster;
    
    private String mutationTasterPredict;
    
    private String gerp;
    
    private String gerpPredict;
    
    private String spidex;
    
    private String dbnsfpinterpro;
    
    private String revelScore;
    
    private String mcapScore;
    
    private String mcapPredict;
    
    private String sheetId;
    
    private String dataCode;
    
    private String clinicalJudgment;
    
    private String mutationSource;
    
    private String technicalFamilyGroupId;
    
    private String technicalTaskId; //新增技术分析任务ID 解决异常处理后 groupID重复问题
    
    private String sourceRef;
    
    private String mutationObjectId;
    
    private String cnvResultId;
    
    public Long getLocation1()
    {
        if (null == beginLocus)
        {
            return null;
        }
        
        try
        {
            return Long.valueOf(beginLocus);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getLocusType()
    {
        return locusType;
    }
    
    public void setLocusType(String locusType)
    {
        this.locusType = locusType;
    }
    
    public String getVerify()
    {
        return verify;
    }
    
    public void setVerify(String verify)
    {
        this.verify = verify;
    }
    
    public String getVerifyMethod()
    {
        return verifyMethod;
    }
    
    public void setVerifyMethod(String verifyMethod)
    {
        this.verifyMethod = verifyMethod;
    }
    
    public String getUpReferGene()
    {
        return upReferGene;
    }
    
    public void setUpReferGene(String upReferGene)
    {
        this.upReferGene = upReferGene;
    }
    
    public String getDownReferGene()
    {
        return downReferGene;
    }
    
    public void setDownReferGene(String downReferGene)
    {
        this.downReferGene = downReferGene;
    }
    
    public String getReferSample()
    {
        return referSample;
    }
    
    public void setReferSample(String referSample)
    {
        this.referSample = referSample;
    }
    
    public String getAacDocument()
    {
        return aacDocument;
    }
    
    public void setAacDocument(String aacDocument)
    {
        this.aacDocument = aacDocument;
    }
    
    public String getHgvs()
    {
        return hgvs;
    }
    
    public void setHgvs(String hgvs)
    {
        this.hgvs = hgvs;
    }
    
    public String getDiseaseDocument()
    {
        return diseaseDocument;
    }
    
    public void setDiseaseDocument(String diseaseDocument)
    {
        this.diseaseDocument = diseaseDocument;
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
    
    public String getSample()
    {
        return sample;
    }
    
    public void setSample(String sample)
    {
        this.sample = sample;
    }
    
    public String getGeneSymbol()
    {
        return geneSymbol;
    }
    
    public void setGeneSymbol(String geneSymbol)
    {
        this.geneSymbol = geneSymbol;
    }
    
    public String getChromosomeLocation()
    {
        return chromosomeLocation;
    }
    
    public void setChromosomeLocation(String chromosomeLocation)
    {
        this.chromosomeLocation = chromosomeLocation;
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
    
    public String getAminoacidChanges()
    {
        return aminoacidChanges;
    }
    
    public void setAminoacidChanges(String aminoacidChanges)
    {
        this.aminoacidChanges = aminoacidChanges;
    }
    
    public String getGeneType()
    {
        return geneType;
    }
    
    public void setGeneType(String geneType)
    {
        this.geneType = geneType;
    }
    
    public String getAug2015()
    {
        return aug2015;
    }
    
    public void setAug2015(String aug2015)
    {
        this.aug2015 = aug2015;
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
    
    public String getClinvar2016()
    {
        return clinvar2016;
    }
    
    public void setClinvar2016(String clinvar2016)
    {
        this.clinvar2016 = clinvar2016;
    }
    
    public String getReferAltFrequency()
    {
        return referAltFrequency;
    }
    
    public void setReferAltFrequency(String referAltFrequency)
    {
        this.referAltFrequency = referAltFrequency;
    }
    
    public String getChromosome()
    {
        return chromosome;
    }
    
    public void setChromosome(String chromosome)
    {
        this.chromosome = chromosome;
    }
    
    public String getBeginLocus()
    {
        return beginLocus;
    }
    
    public void setBeginLocus(String beginLocus)
    {
        this.beginLocus = beginLocus;
    }
    
    public String getEndLocus()
    {
        return endLocus;
    }
    
    public void setEndLocus(String endLocus)
    {
        this.endLocus = endLocus;
    }
    
    public String getReferBase()
    {
        return referBase;
    }
    
    public void setReferBase(String referBase)
    {
        this.referBase = referBase;
    }
    
    public String getAltBase()
    {
        return altBase;
    }
    
    public void setAltBase(String altBase)
    {
        this.altBase = altBase;
    }
    
    public String getMutationRatio()
    {
        return mutationRatio;
    }
    
    public void setMutationRatio(String mutationRatio)
    {
        this.mutationRatio = mutationRatio;
    }
    
    public String getReferCount()
    {
        return referCount;
    }
    
    public void setReferCount(String referCount)
    {
        this.referCount = referCount;
    }
    
    public String getMutationCount()
    {
        return mutationCount;
    }
    
    public void setMutationCount(String mutationCount)
    {
        this.mutationCount = mutationCount;
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
    
    public String getPathsnp()
    {
        return pathsnp;
    }
    
    public void setPathsnp(String pathsnp)
    {
        this.pathsnp = pathsnp;
    }
    
    public String getMutationNormal()
    {
        return mutationNormal;
    }
    
    public void setMutationNormal(String mutationNormal)
    {
        this.mutationNormal = mutationNormal;
    }
    
    public String getGenome1000()
    {
        return genome1000;
    }
    
    public void setGenome1000(String genome1000)
    {
        this.genome1000 = genome1000;
    }
    
    public String getMutationDatabase()
    {
        return mutationDatabase;
    }
    
    public void setMutationDatabase(String mutationDatabase)
    {
        this.mutationDatabase = mutationDatabase;
    }
    
    public String getAug1000g2015()
    {
        return aug1000g2015;
    }
    
    public void setAug1000g2015(String aug1000g2015)
    {
        this.aug1000g2015 = aug1000g2015;
    }
    
    public String getEsp6500si()
    {
        return esp6500si;
    }
    
    public void setEsp6500si(String esp6500si)
    {
        this.esp6500si = esp6500si;
    }
    
    public String getInhouse()
    {
        return inhouse;
    }
    
    public void setInhouse(String inhouse)
    {
        this.inhouse = inhouse;
    }
    
    public String getExacAll()
    {
        return exacAll;
    }
    
    public void setExacAll(String exacAll)
    {
        this.exacAll = exacAll;
    }
    
    public String getExacEas()
    {
        return exacEas;
    }
    
    public void setExacEas(String exacEas)
    {
        this.exacEas = exacEas;
    }
    
    public String getSift()
    {
        return sift;
    }
    
    public void setSift(String sift)
    {
        this.sift = sift;
    }
    
    public String getSiftPredict()
    {
        return siftPredict;
    }
    
    public void setSiftPredict(String siftPredict)
    {
        this.siftPredict = siftPredict;
    }
    
    public String getPolyphen2()
    {
        return polyphen2;
    }
    
    public void setPolyphen2(String polyphen2)
    {
        this.polyphen2 = polyphen2;
    }
    
    public String getPolyphen2Predict()
    {
        return polyphen2Predict;
    }
    
    public void setPolyphen2Predict(String polyphen2Predict)
    {
        this.polyphen2Predict = polyphen2Predict;
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
    
    public String getGerp()
    {
        return gerp;
    }
    
    public void setGerp(String gerp)
    {
        this.gerp = gerp;
    }
    
    public String getGerpPredict()
    {
        return gerpPredict;
    }
    
    public void setGerpPredict(String gerpPredict)
    {
        this.gerpPredict = gerpPredict;
    }
    
    public String getSpidex()
    {
        return spidex;
    }
    
    public void setSpidex(String spidex)
    {
        this.spidex = spidex;
    }
    
    public String getDbnsfpinterpro()
    {
        return dbnsfpinterpro;
    }
    
    public void setDbnsfpinterpro(String dbnsfpinterpro)
    {
        this.dbnsfpinterpro = dbnsfpinterpro;
    }
    
    public String getRevelScore()
    {
        return revelScore;
    }
    
    public void setRevelScore(String revelScore)
    {
        this.revelScore = revelScore;
    }
    
    public String getMcapScore()
    {
        return mcapScore;
    }
    
    public void setMcapScore(String mcapScore)
    {
        this.mcapScore = mcapScore;
    }
    
    public String getMcapPredict()
    {
        return mcapPredict;
    }
    
    public void setMcapPredict(String mcapPredict)
    {
        this.mcapPredict = mcapPredict;
    }
    
    public String getSheetId()
    {
        return sheetId;
    }
    
    public void setSheetId(String sheetId)
    {
        this.sheetId = sheetId;
    }
    
    public String getDataCode()
    {
        return dataCode;
    }
    
    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }
    
    public String getClinicalJudgment()
    {
        return clinicalJudgment;
    }
    
    public void setClinicalJudgment(String clinicalJudgment)
    {
        this.clinicalJudgment = clinicalJudgment;
    }
    
    public String getMutationSource()
    {
        return mutationSource;
    }
    
    public void setMutationSource(String mutationSource)
    {
        this.mutationSource = mutationSource;
    }
    
    public String getTechnicalFamilyGroupId()
    {
        return technicalFamilyGroupId;
    }
    
    public void setTechnicalFamilyGroupId(String technicalFamilyGroupId)
    {
        this.technicalFamilyGroupId = technicalFamilyGroupId;
    }
    
    public String getSourceRef()
    {
        return sourceRef;
    }
    
    public void setSourceRef(String sourceRef)
    {
        this.sourceRef = sourceRef;
    }
    
    public String getMutationObjectId()
    {
        return mutationObjectId;
    }
    
    public void setMutationObjectId(String mutationObjectId)
    {
        this.mutationObjectId = mutationObjectId;
    }
    
    public String getCnvResultId()
    {
        return cnvResultId;
    }
    
    public void setCnvResultId(String cnvResultId)
    {
        this.cnvResultId = cnvResultId;
    }
    
    public String getTechnicalTaskId()
    {
        return technicalTaskId;
    }
    
    public void setTechnicalTaskId(String technicalTaskId)
    {
        this.technicalTaskId = technicalTaskId;
    }
    
}