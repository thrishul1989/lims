package com.todaysoft.lims.technical.export.mybatis.model;

import java.util.List;

public class MutationFamilySangerResultInfo
{
    private String geneSymbol;
    
    private String chromosomalLocation;
    
    private String nucleotideChanges;

    private String aminoAcidChanges;
    
    private String objectId;
    
    private List<MutationFamilySangerReportSample> mutationFamilySangerReportSamples;

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

    public String getObjectId()
    {
        return objectId;
    }

    public void setObjectId(String objectId)
    {
        this.objectId = objectId;
    }
    

    public List<MutationFamilySangerReportSample> getMutationFamilySangerReportSamples()
    {
        return mutationFamilySangerReportSamples;
    }

    public void setMutationFamilySangerReportSamples(List<MutationFamilySangerReportSample> mutationFamilySangerReportSamples)
    {
        this.mutationFamilySangerReportSamples = mutationFamilySangerReportSamples;
    }

    
}
