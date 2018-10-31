package com.todaysoft.lims.schedule.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_TECHNICAL_ANALY_RECORD")
public class TestingTechnicalAnalyRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = 5494199963282327440L;
    
    private TestingSheet sheet;

    private String verify;          //下一步-验证/不验证

    private String locusType;       //位点类型-验证位点/纯阴性报告/参考位点

    private String verifyMethod;    // 验证方法

    private String dataCode;        //数据编号

    private String sample;      // 样本编号

    private String geneSymbol;  //突变基因

    private String chrLocation;  //染色体位置

    private String refTranscript;   //转录本号

    private String exon;        //外显子

    private String nucleotideChanges; //核苷酸变化

    private String aminoAcidChanges; //氨基酸变化

    private String geneType;    //纯合/杂合

    private String inhert;      //遗传方式

    private String chromosome;  //染色体

    private String beginLocus;  //位置1

    private String endLocus;    //位置2

    private String geneRegion;  //变异区域

    private String cytoBand;    //染色体区段

    private String dbsnp147;   //SNP编号（dbsnp147）

    private String upRefGene;   //上游参考基因

    private String downRefGene;   //下游参考基因

    private String refSample;   //对照样本
    
    private String aminoAcidChangesWithDocument;
    
    private String hgvs;
    
    private String diseaseWithDocument;
    
    private String tag;
    
    private String publication;
    
    private String effect;
    
    private String aug2015;
    
    private String pathogenicAnalysis;
    
    private String disease;
    
    private String diseasePhenotype;
    
    private String diseaseInformation;
    
    private String clinvar2016;
    
    private String refAltFre;
    
    private String refBase;
    
    private String altBase;
    
    private String mutRatio;
    
    private String refCount;
    
    private String mutCount;
    
    private String depth;
    
    private String mutationType;
    
    private String pathSNP;
    
    private String mutInNormal;
    
    private String genome1000;
    
    private String mutInDatabase;
    
    private String aug1000g2015;
    
    private String esp6500si;
    
    private String inhouse;
    
    private String exACALL;
    
    private String exACEAS;
    
    private String sift;
    
    private String siftPredict;
    
    private String polyPhen2;
    
    private String polyPhen2Predict;
    
    private String mutationTaster;
    
    private String mutationTasterPredict;
    
    private String gerp;
    
    private String gerpPredict;
    
    private String spidex;
    
    private String changeAA;
    
    private String dbNsfpInterPro;
    
    private String revelScore;
    
    private String mcapScore;
    
    private String mcapPred;
    
    private List<TestingVerifyRecord> verifyRecords;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHEET_ID")
    public TestingSheet getSheet()
    {
        return sheet;
    }
    
    public void setSheet(TestingSheet sheet)
    {
        this.sheet = sheet;
    }
    
    @Transient
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
    
    @Transient
    public String getMutationGene()
    {
        return geneSymbol;
    }
    
    @Transient
    public String getChromosomalLocation()
    {
        return chrLocation;
    }
    
    @Transient
    public String getVerifyScheme()
    {
        return "";
    }
    
    @Column(name = "EXON")
    public String getExon()
    {
        return exon;
    }
    
    public void setExon(String exon)
    {
        this.exon = exon;
    }
    
    @Column(name = "CHROMOSOME")
    public String getChromosome()
    {
        return chromosome;
    }
    
    public void setChromosome(String chromosome)
    {
        this.chromosome = chromosome;
    }
    
    @Column(name = "VERIFY")
    public String getVerify()
    {
        return verify;
    }
    
    public void setVerify(String verify)
    {
        this.verify = verify;
    }
    
    @Column(name = "LOCUS_TYPE")
    public String getLocusType()
    {
        return locusType;
    }
    
    public void setLocusType(String locusType)
    {
        this.locusType = locusType;
    }
    
    @Column(name = "VERIFY_METHOD")
    public String getVerifyMethod()
    {
        return verifyMethod;
    }
    
    public void setVerifyMethod(String verifyMethod)
    {
        this.verifyMethod = verifyMethod;
    }
    
    @Column(name = "UP_REFER_GENE")
    public String getUpRefGene()
    {
        return upRefGene;
    }
    
    public void setUpRefGene(String upRefGene)
    {
        this.upRefGene = upRefGene;
    }
    
    @Column(name = "DOWN_REFER_GENE")
    public String getDownRefGene()
    {
        return downRefGene;
    }
    
    public void setDownRefGene(String downRefGene)
    {
        this.downRefGene = downRefGene;
    }
    
    @Column(name = "REFER_SAMPLE")
    public String getRefSample()
    {
        return refSample;
    }
    
    public void setRefSample(String refSample)
    {
        this.refSample = refSample;
    }
    
    @Column(name = "AAC_DOCUMENT")
    public String getAminoAcidChangesWithDocument()
    {
        return aminoAcidChangesWithDocument;
    }
    
    public void setAminoAcidChangesWithDocument(String aminoAcidChangesWithDocument)
    {
        this.aminoAcidChangesWithDocument = aminoAcidChangesWithDocument;
    }
    
    @Column(name = "HGVS")
    public String getHgvs()
    {
        return hgvs;
    }
    
    public void setHgvs(String hgvs)
    {
        this.hgvs = hgvs;
    }
    
    @Column(name = "DISEASE_DOCUMENT")
    public String getDiseaseWithDocument()
    {
        return diseaseWithDocument;
    }
    
    public void setDiseaseWithDocument(String diseaseWithDocument)
    {
        this.diseaseWithDocument = diseaseWithDocument;
    }
    
    @Column(name = "TAG")
    public String getTag()
    {
        return tag;
    }
    
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    
    @Column(name = "PUBLICATION")
    public String getPublication()
    {
        return publication;
    }
    
    public void setPublication(String publication)
    {
        this.publication = publication;
    }
    
    @Column(name = "EFFECT")
    public String getEffect()
    {
        return effect;
    }
    
    public void setEffect(String effect)
    {
        this.effect = effect;
    }
    
    @Column(name = "SAMPLE")
    public String getSample()
    {
        return sample;
    }
    
    public void setSample(String sample)
    {
        this.sample = sample;
    }
    
    @Column(name = "GENE_SYMBOL")
    public String getGeneSymbol()
    {
        return geneSymbol;
    }
    
    public void setGeneSymbol(String geneSymbol)
    {
        this.geneSymbol = geneSymbol;
    }
    
    @Column(name = "CHROMOSOME_LOCATION")
    public String getChrLocation()
    {
        return chrLocation;
    }
    
    public void setChrLocation(String chrLocation)
    {
        this.chrLocation = chrLocation;
    }
    
    @Column(name = "REF_TRANSCRIPT")
    public String getRefTranscript()
    {
        return refTranscript;
    }
    
    public void setRefTranscript(String refTranscript)
    {
        this.refTranscript = refTranscript;
    }
    
    @Column(name = "NUCLEOTIDE_CHANGES")
    public String getNucleotideChanges()
    {
        return nucleotideChanges;
    }
    
    public void setNucleotideChanges(String nucleotideChanges)
    {
        this.nucleotideChanges = nucleotideChanges;
    }
    
    @Column(name = "AMINOACID_CHANGES")
    public String getAminoAcidChanges()
    {
        return aminoAcidChanges;
    }
    
    public void setAminoAcidChanges(String aminoAcidChanges)
    {
        this.aminoAcidChanges = aminoAcidChanges;
    }
    
    @Column(name = "GENE_TYPE")
    public String getGeneType()
    {
        return geneType;
    }
    
    public void setGeneType(String geneType)
    {
        this.geneType = geneType;
    }
    
    @Column(name = "AUG_2015")
    public String getAug2015()
    {
        return aug2015;
    }
    
    public void setAug2015(String aug2015)
    {
        this.aug2015 = aug2015;
    }
    
    @Column(name = "PATHOGENIC_ANALYSIS")
    public String getPathogenicAnalysis()
    {
        return pathogenicAnalysis;
    }
    
    public void setPathogenicAnalysis(String pathogenicAnalysis)
    {
        this.pathogenicAnalysis = pathogenicAnalysis;
    }
    
    @Column(name = "INHERT")
    public String getInhert()
    {
        return inhert;
    }
    
    public void setInhert(String inhert)
    {
        this.inhert = inhert;
    }
    
    @Column(name = "DISEASE")
    public String getDisease()
    {
        return disease;
    }
    
    public void setDisease(String disease)
    {
        this.disease = disease;
    }
    
    @Column(name = "DISEASE_PHENOTYPE")
    public String getDiseasePhenotype()
    {
        return diseasePhenotype;
    }
    
    public void setDiseasePhenotype(String diseasePhenotype)
    {
        this.diseasePhenotype = diseasePhenotype;
    }
    
    @Column(name = "DISEASE_INFORMATION")
    public String getDiseaseInformation()
    {
        return diseaseInformation;
    }
    
    public void setDiseaseInformation(String diseaseInformation)
    {
        this.diseaseInformation = diseaseInformation;
    }
    
    @Column(name = "CLINVAR_2016")
    public String getClinvar2016()
    {
        return clinvar2016;
    }
    
    public void setClinvar2016(String clinvar2016)
    {
        this.clinvar2016 = clinvar2016;
    }
    
    @Column(name = "REFER_ALT_FREQUENCY")
    public String getRefAltFre()
    {
        return refAltFre;
    }
    
    public void setRefAltFre(String refAltFre)
    {
        this.refAltFre = refAltFre;
    }
    
    @Column(name = "BEGIN_LOCUS")
    public String getBeginLocus()
    {
        return beginLocus;
    }
    
    public void setBeginLocus(String beginLocus)
    {
        this.beginLocus = beginLocus;
    }
    
    @Column(name = "END_LOCUS")
    public String getEndLocus()
    {
        return endLocus;
    }
    
    public void setEndLocus(String endLocus)
    {
        this.endLocus = endLocus;
    }
    
    @Column(name = "REFER_BASE")
    public String getRefBase()
    {
        return refBase;
    }
    
    public void setRefBase(String refBase)
    {
        this.refBase = refBase;
    }
    
    @Column(name = "ALT_BASE")
    public String getAltBase()
    {
        return altBase;
    }
    
    public void setAltBase(String altBase)
    {
        this.altBase = altBase;
    }
    
    @Column(name = "MUTATION_RATIO")
    public String getMutRatio()
    {
        return mutRatio;
    }
    
    public void setMutRatio(String mutRatio)
    {
        this.mutRatio = mutRatio;
    }
    
    @Column(name = "REFER_COUNT")
    public String getRefCount()
    {
        return refCount;
    }
    
    public void setRefCount(String refCount)
    {
        this.refCount = refCount;
    }
    
    @Column(name = "MUTATION_COUNT")
    public String getMutCount()
    {
        return mutCount;
    }
    
    public void setMutCount(String mutCount)
    {
        this.mutCount = mutCount;
    }
    
    @Column(name = "DEPTH")
    public String getDepth()
    {
        return depth;
    }
    
    public void setDepth(String depth)
    {
        this.depth = depth;
    }
    
    @Column(name = "MUTATION_TYPE")
    public String getMutationType()
    {
        return mutationType;
    }
    
    public void setMutationType(String mutationType)
    {
        this.mutationType = mutationType;
    }
    
    @Column(name = "GENE_REGION")
    public String getGeneRegion()
    {
        return geneRegion;
    }
    
    public void setGeneRegion(String geneRegion)
    {
        this.geneRegion = geneRegion;
    }
    
    @Column(name = "CYTO_BAND")
    public String getCytoBand()
    {
        return cytoBand;
    }
    
    public void setCytoBand(String cytoBand)
    {
        this.cytoBand = cytoBand;
    }
    
    @Column(name = "DBSNP147")
    public String getDbsnp147()
    {
        return dbsnp147;
    }
    
    public void setDbsnp147(String dbsnp147)
    {
        this.dbsnp147 = dbsnp147;
    }
    
    @Column(name = "PATHSNP")
    public String getPathSNP()
    {
        return pathSNP;
    }
    
    public void setPathSNP(String pathSNP)
    {
        this.pathSNP = pathSNP;
    }
    
    @Column(name = "MUTATION_NORMAL")
    public String getMutInNormal()
    {
        return mutInNormal;
    }
    
    public void setMutInNormal(String mutInNormal)
    {
        this.mutInNormal = mutInNormal;
    }
    
    @Column(name = "GENOME_1000")
    public String getGenome1000()
    {
        return genome1000;
    }
    
    public void setGenome1000(String genome1000)
    {
        this.genome1000 = genome1000;
    }
    
    @Column(name = "MUTATION_DATABASE")
    public String getMutInDatabase()
    {
        return mutInDatabase;
    }
    
    public void setMutInDatabase(String mutInDatabase)
    {
        this.mutInDatabase = mutInDatabase;
    }
    
    @Column(name = "AUG_1000G_2015")
    public String getAug1000g2015()
    {
        return aug1000g2015;
    }
    
    public void setAug1000g2015(String aug1000g2015)
    {
        this.aug1000g2015 = aug1000g2015;
    }
    
    @Column(name = "ESP6500SI")
    public String getEsp6500si()
    {
        return esp6500si;
    }
    
    public void setEsp6500si(String esp6500si)
    {
        this.esp6500si = esp6500si;
    }
    
    @Column(name = "INHOUSE")
    public String getInhouse()
    {
        return inhouse;
    }
    
    public void setInhouse(String inhouse)
    {
        this.inhouse = inhouse;
    }
    
    @Column(name = "EXAC_ALL")
    public String getExACALL()
    {
        return exACALL;
    }
    
    public void setExACALL(String exACALL)
    {
        this.exACALL = exACALL;
    }
    
    @Column(name = "EXAC_EAS")
    public String getExACEAS()
    {
        return exACEAS;
    }
    
    public void setExACEAS(String exACEAS)
    {
        this.exACEAS = exACEAS;
    }
    
    @Column(name = "SIFT")
    public String getSift()
    {
        return sift;
    }
    
    public void setSift(String sift)
    {
        this.sift = sift;
    }
    
    @Column(name = "SIFT_PREDICT")
    public String getSiftPredict()
    {
        return siftPredict;
    }
    
    public void setSiftPredict(String siftPredict)
    {
        this.siftPredict = siftPredict;
    }
    
    @Column(name = "POLYPHEN2")
    public String getPolyPhen2()
    {
        return polyPhen2;
    }
    
    public void setPolyPhen2(String polyPhen2)
    {
        this.polyPhen2 = polyPhen2;
    }
    
    @Column(name = "POLYPHEN2_PREDICT")
    public String getPolyPhen2Predict()
    {
        return polyPhen2Predict;
    }
    
    public void setPolyPhen2Predict(String polyPhen2Predict)
    {
        this.polyPhen2Predict = polyPhen2Predict;
    }
    
    @Column(name = "MUTATION_TASTER")
    public String getMutationTaster()
    {
        return mutationTaster;
    }
    
    public void setMutationTaster(String mutationTaster)
    {
        this.mutationTaster = mutationTaster;
    }
    
    @Column(name = "MUTATION_TASTER_PREDICT")
    public String getMutationTasterPredict()
    {
        return mutationTasterPredict;
    }
    
    public void setMutationTasterPredict(String mutationTasterPredict)
    {
        this.mutationTasterPredict = mutationTasterPredict;
    }
    
    @Column(name = "GERP")
    public String getGerp()
    {
        return gerp;
    }
    
    public void setGerp(String gerp)
    {
        this.gerp = gerp;
    }
    
    @Column(name = "GERP_PREDICT")
    public String getGerpPredict()
    {
        return gerpPredict;
    }
    
    public void setGerpPredict(String gerpPredict)
    {
        this.gerpPredict = gerpPredict;
    }
    
    @Column(name = "SPIDEX")
    public String getSpidex()
    {
        return spidex;
    }
    
    public void setSpidex(String spidex)
    {
        this.spidex = spidex;
    }
    
    @Column(name = "CHANGE_AA")
    public String getChangeAA()
    {
        return changeAA;
    }
    
    public void setChangeAA(String changeAA)
    {
        this.changeAA = changeAA;
    }
    
    @Column(name = "DBNSFPINTERPRO")
    public String getDbNsfpInterPro()
    {
        return dbNsfpInterPro;
    }
    
    public void setDbNsfpInterPro(String dbNsfpInterPro)
    {
        this.dbNsfpInterPro = dbNsfpInterPro;
    }
    
    @Column(name = "REVEL_SCORE")
    public String getRevelScore()
    {
        return revelScore;
    }
    
    public void setRevelScore(String revelScore)
    {
        this.revelScore = revelScore;
    }
    
    @Column(name = "MCAP_SCORE")
    public String getMcapScore()
    {
        return mcapScore;
    }
    
    public void setMcapScore(String mcapScore)
    {
        this.mcapScore = mcapScore;
    }
    
    @Column(name = "MCAP_PREDICT")
    public String getMcapPred()
    {
        return mcapPred;
    }
    
    public void setMcapPred(String mcapPred)
    {
        this.mcapPred = mcapPred;
    }

    @Column(name = "DATA_CODE")
    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "analyRecord")
    public List<TestingVerifyRecord> getVerifyRecords()
    {
        return verifyRecords;
    }
    
    public void setVerifyRecords(List<TestingVerifyRecord> verifyRecords)
    {
        this.verifyRecords = verifyRecords;
    }
}
