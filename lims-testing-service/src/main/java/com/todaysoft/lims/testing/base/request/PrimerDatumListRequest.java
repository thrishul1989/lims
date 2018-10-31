package com.todaysoft.lims.testing.base.request;

public class PrimerDatumListRequest
{
    private String chromosomeNumber;

    private String gene;

    private Long pcrPoint;

    public String getChromosomeNumber() {
        return chromosomeNumber;
    }

    public void setChromosomeNumber(String chromosomeNumber) {
        this.chromosomeNumber = chromosomeNumber;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public Long getPcrPoint() {
        return pcrPoint;
    }

    public void setPcrPoint(Long pcrPoint) {
        this.pcrPoint = pcrPoint;
    }

}
