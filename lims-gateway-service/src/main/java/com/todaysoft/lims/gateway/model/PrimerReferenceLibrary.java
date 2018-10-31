package com.todaysoft.lims.gateway.model;


public class PrimerReferenceLibrary {


	private String id;
	private String chromosomeNumber;//染色体编号
	private String pcrStartPoint; //PCR起始点
	private String pcrEndPoint; //PCR终止点
	private String reversePrimerSequence;//反向引物序列
	private String forwardPrimerSequence;//正向引物序列
	private String gene;//基因
	private String codingExon; //外显子
	private String codingExonArea; //外显子区域
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChromosomeNumber() {
		return chromosomeNumber;
	}
	public void setChromosomeNumber(String chromosomeNumber) {
		this.chromosomeNumber = chromosomeNumber;
	}
	public String getPcrStartPoint() {
		return pcrStartPoint;
	}
	public void setPcrStartPoint(String pcrStartPoint) {
		this.pcrStartPoint = pcrStartPoint;
	}
	public String getPcrEndPoint() {
		return pcrEndPoint;
	}
	public void setPcrEndPoint(String pcrEndPoint) {
		this.pcrEndPoint = pcrEndPoint;
	}
	public String getReversePrimerSequence() {
		return reversePrimerSequence;
	}
	public void setReversePrimerSequence(String reversePrimerSequence) {
		this.reversePrimerSequence = reversePrimerSequence;
	}
	public String getForwardPrimerSequence() {
		return forwardPrimerSequence;
	}
	public void setForwardPrimerSequence(String forwardPrimerSequence) {
		this.forwardPrimerSequence = forwardPrimerSequence;
	}
	public String getGene() {
		return gene;
	}
	public void setGene(String gene) {
		this.gene = gene;
	}
	public String getCodingExon() {
		return codingExon;
	}
	public void setCodingExon(String codingExon) {
		this.codingExon = codingExon;
	}
	public String getCodingExonArea() {
		return codingExonArea;
	}
	public void setCodingExonArea(String codingExonArea) {
		this.codingExonArea = codingExonArea;
	}
	
}
