package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.PrimerDatum;

public class PrimerDatumSearcher implements ISearcher<PrimerDatum>
{
    private String chromosomeNumber;
    
    private String gene;
    
    private Long pcrPoint;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM PrimerDatum p WHERE p.deleted = false");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addChromosomeNumberFilter(hql, paramNames, parameters);
        addGeneFilter(hql, paramNames, parameters);
        addPcrPointFilter(hql, paramNames, parameters);
        hql.append(" ORDER BY p.createTime desc ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<PrimerDatum> getEntityClass()
    {
        return PrimerDatum.class;
    }
    
    private void addPcrPointFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(pcrPoint))
        {
            hql.append(" AND ( (p.pcrStartPoint <= :pcrPoint1) AND (p.pcrEndPoint >= :pcrPoint2 )) ");
            paramNames.add("pcrPoint1");
            paramNames.add("pcrPoint2");
            parameters.add(pcrPoint - 50);
            parameters.add(pcrPoint + 50);
        }
    }
    
    private void addGeneFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(gene))
        {
            hql.append(" AND p.gene like :gene");
            paramNames.add("gene");
            parameters.add("%" + gene + "%");
        }
    }
    
    private void addChromosomeNumberFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(chromosomeNumber))
        {
            if (chromosomeNumber.contains("chr"))
            {
                chromosomeNumber = chromosomeNumber.replace("chr", "");
            }
            hql.append(" AND ( p.chromosomeNumber =:chromosomeNumber1 or p.chromosomeNumber = :chromosomeNumber2)");
            paramNames.add("chromosomeNumber1");
            paramNames.add("chromosomeNumber2");
            parameters.add(chromosomeNumber);
            parameters.add("chr" + chromosomeNumber);
        }
    }
    
    public String getChromosomeNumber()
    {
        return chromosomeNumber;
    }
    
    public void setChromosomeNumber(String chromosomeNumber)
    {
        this.chromosomeNumber = chromosomeNumber;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public Long getPcrPoint()
    {
        return pcrPoint;
    }
    
    public void setPcrPoint(Long pcrPoint)
    {
        this.pcrPoint = pcrPoint;
    }
}
