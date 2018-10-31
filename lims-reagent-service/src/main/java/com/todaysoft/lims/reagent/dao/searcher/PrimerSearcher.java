package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Primer;
import com.todaysoft.lims.utils.StringUtils;

public class PrimerSearcher implements ISearcher<Primer>
{
    private String gene;
    
    private String applicationType;
    
    private String productCode;
    
    private String chromosomeNumber;
    
    private Long pcrPoint;
    
    private String testingMethodName;
    
    private String forwardPrimerName;
    
    private String reversePrimerName;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Primer s WHERE s.deleted = false");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addGeneFilter(hql, paramNames, parameters);
        addApplicationTypeFilter(hql, paramNames, parameters);
        addProductCodeFilter(hql, paramNames, parameters);
        addChromosomeNumberFilter(hql, paramNames, parameters);
        addPcrPointFilter(hql, paramNames, parameters);
        addForwardPrimerNameFilter(hql, paramNames, parameters);
        addReversePrimerNameFilter(hql, paramNames, parameters);
        if (StringUtils.isNotEmpty(gene))
        {
            hql.append(" ORDER BY length(s.gene) ,s.createTime desc ");
        }
        else
        {
            hql.append(" ORDER BY s.createTime desc ");
        }
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addGeneFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(gene))
        {
            hql.append(" AND s.gene LIKE :gene ");
            paramNames.add("gene");
            parameters.add("%" + gene + "%");
        }
    }
    
    private void addApplicationTypeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(applicationType))
        {
            hql.append(" AND s.applicationType =:applicationType ");
            paramNames.add("applicationType");
            parameters.add(applicationType);
        }
    }
    
    private void addProductCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(productCode))
        {
            hql.append(" AND s.productCode LIKE :productCode");
            paramNames.add("productCode");
            parameters.add("%" + productCode + "%");
        }
    }
    
    private void addChromosomeNumberFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(chromosomeNumber))
        {
            if (chromosomeNumber.contains("chr"))
            {
                chromosomeNumber = chromosomeNumber.replace("chr", "");
            }
            hql.append(" AND ( s.chromosomeNumber =:chromosomeNumber1 or s.chromosomeNumber = :chromosomeNumber2)");
            paramNames.add("chromosomeNumber1");
            paramNames.add("chromosomeNumber2");
            parameters.add(chromosomeNumber);
            parameters.add("chr" + chromosomeNumber);
        }
    }
    
    private void addPcrPointFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(pcrPoint))
        {
            hql.append(" AND ( (s.pcrStartPoint <= :pcrPoint1) AND (s.pcrEndPoint >= :pcrPoint2 )) ");
            paramNames.add("pcrPoint1");
            paramNames.add("pcrPoint2");
            parameters.add(pcrPoint - 50);
            parameters.add(pcrPoint + 50);
        }
    }
    
    private void addForwardPrimerNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(forwardPrimerName))
        {
            hql.append(" AND s.forwardPrimerName LIKE :forwardPrimerName ");
            paramNames.add("forwardPrimerName");
            parameters.add("%" + forwardPrimerName + "%");
        }
    }
    
    private void addReversePrimerNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(reversePrimerName))
        {
            hql.append(" AND s.reversePrimerName LIKE :reversePrimerName");
            paramNames.add("reversePrimerName");
            parameters.add("%" + reversePrimerName + "%");
        }
    }
    
    @Override
    public Class<Primer> getEntityClass()
    {
        return Primer.class;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public String getApplicationType()
    {
        return applicationType;
    }
    
    public void setApplicationType(String applicationType)
    {
        this.applicationType = applicationType;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getChromosomeNumber()
    {
        return chromosomeNumber;
    }
    
    public void setChromosomeNumber(String chromosomeNumber)
    {
        this.chromosomeNumber = chromosomeNumber;
    }
    
    public Long getPcrPoint()
    {
        return pcrPoint;
    }
    
    public void setPcrPoint(Long pcrPoint)
    {
        this.pcrPoint = pcrPoint;
    }
    
    public String getTestingMethodName()
    {
        return testingMethodName;
    }
    
    public void setTestingMethodName(String testingMethodName)
    {
        this.testingMethodName = testingMethodName;
    }
    
    public String getForwardPrimerName()
    {
        return forwardPrimerName;
    }
    
    public void setForwardPrimerName(String forwardPrimerName)
    {
        this.forwardPrimerName = forwardPrimerName;
    }
    
    public String getReversePrimerName()
    {
        return reversePrimerName;
    }
    
    public void setReversePrimerName(String reversePrimerName)
    {
        this.reversePrimerName = reversePrimerName;
    }
}
