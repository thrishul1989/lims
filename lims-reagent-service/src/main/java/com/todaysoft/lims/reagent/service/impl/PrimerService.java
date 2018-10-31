package com.todaysoft.lims.reagent.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.PrimerSearcher;
import com.todaysoft.lims.reagent.entity.Primer;
import com.todaysoft.lims.reagent.entity.TestingMethod;
import com.todaysoft.lims.reagent.model.request.CheckPrimerForDesignRequest;
import com.todaysoft.lims.reagent.model.request.PrimerCreateRequest;
import com.todaysoft.lims.reagent.model.request.PrimerExcelListRequest;
import com.todaysoft.lims.reagent.model.request.PrimerModifyRequest;
import com.todaysoft.lims.reagent.model.request.PrimerPagingRequest;
import com.todaysoft.lims.reagent.service.IPrimerService;
import com.todaysoft.lims.reagent.service.ITestingMethodService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class PrimerService implements IPrimerService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingMethodService testingMethodService;
    
    @Override
    public Pagination<Primer> paging(PrimerPagingRequest request)
    {
        PrimerSearcher primerSearcher = new PrimerSearcher();
        BeanUtils.copyProperties(request, primerSearcher);
        if (StringUtils.isNotEmpty(request.getApplicationType()))
        {
            TestingMethod t = testingMethodService.getById(request.getApplicationType());
            primerSearcher.setTestingMethodName(t.getName());
        }
        return baseDaoSupport.find(primerSearcher.toQuery(), request.getPageNo(), request.getPageSize(), Primer.class);
    }
    
    @Override
    public List<Primer> list(PrimerPagingRequest request)
    {
        PrimerSearcher primerSearcher = new PrimerSearcher();
        BeanUtils.copyProperties(request, primerSearcher);
        return baseDaoSupport.find(primerSearcher.toQuery(), Primer.class);
    }
    
    @Override
    public List<Primer> pcrNgsList(PrimerPagingRequest request)
    {
        String chromosomeNumber = request.getChromosomeNumber();
        if (StringUtils.isNotEmpty(chromosomeNumber))
        {
            if (chromosomeNumber.contains("chr"))
            {
                chromosomeNumber = chromosomeNumber.replace("chr", "");
            }
            
        }
        String hql =
            "FROM Primer p where (p.chromosomeNumber = :chromosomeNumber or p.chromosomeNumber = :chromosomeNumber2 ) AND ( (p.pcrStartPoint <= :pcrPoint) AND (p.pcrEndPoint >= :pcrPoint ) ) AND p.applicationType=:applicationType AND p.deleted = false order by p.createTime desc ";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Lists.newArrayList("chromosomeNumber", "chromosomeNumber2", "pcrPoint", "applicationType"))
                .values(Lists.newArrayList(chromosomeNumber,
                    "chr" + chromosomeNumber,
                    (Object)request.getPcrPoint(),
                    (Object)request.getApplicationType()))
                .build();
        List<Primer> records = baseDaoSupport.find(queryer, Primer.class);
        return records;
    }
    
    @Override
    public Primer getPrimer(String id)
    {
        Primer p = baseDaoSupport.get(Primer.class, id);
        return p;
    }
    
    @Override
    @Transactional
    public String create(PrimerCreateRequest request)
    {
        Primer entity = new Primer();
        BeanUtils.copyProperties(request, entity);
        entity.setDeleted(false);
        entity.setCreateTime(new Date());
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void modify(PrimerModifyRequest request)
    {
        Primer entity = getPrimer(request.getId());
        BeanUtils.copyProperties(request, entity, "createTime", "deleted", "deleteTime");
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        Primer entity = getPrimer(id);
        entity.setDeleted(true);
        entity.setDeleteTime(new Date());
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public boolean checkedPrimerNo(String primerNo)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM Primer p WHERE p.primerNo = :primerNo AND p.deleted = false")
                .names(Lists.newArrayList("primerNo"))
                .values(Lists.newArrayList((Object)primerNo))
                .build();
        List<Primer> records = baseDaoSupport.find(queryer, Primer.class);
        
        return Collections3.isEmpty(records);
    }
    
    @Override
    public String checkPrimerForDesign(CheckPrimerForDesignRequest request)
    {
        String chromosomeNumber = request.getChromosomeNumber();
        if (StringUtils.isNotEmpty(chromosomeNumber))
        {
            if (chromosomeNumber.contains("chr"))
            {
                chromosomeNumber = chromosomeNumber.replace("chr", "");
            }
            
        }
        String hql =
            "FROM Primer p where ( p.chromosomeNumber = :chromosomeNumber or p.chromosomeNumber = :chromosomeNumber2 ) AND p.pcrStartPoint = :pcrStartPoint AND p.pcrEndPoint = :pcrEndPoint AND p.gene = :gene AND p.codingExon = :codingExon AND p.reversePrimerName = :reversePrimerName AND p.forwardPrimerName = :forwardPrimerName AND p.applicationType = :applicationType AND p.deleted = false";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Lists.newArrayList("chromosomeNumber",
                    "chromosomeNumber2",
                    "pcrStartPoint",
                    "pcrEndPoint",
                    "gene",
                    "codingExon",
                    "reversePrimerName",
                    "forwardPrimerName",
                    "applicationType"))
                .values(Lists.newArrayList(chromosomeNumber,
                    "chr" + chromosomeNumber,
                    (Object)request.getPcrStartPoint(),
                    (Object)request.getPcrEndPoint(),
                    (Object)request.getGene(),
                    (Object)request.getCodingExon(),
                    (Object)request.getReversePrimerName(),
                    (Object)request.getForwardPrimerName(),
                    (Object)request.getApplicationType()))
                .build();
        List<Primer> records = baseDaoSupport.find(queryer, Primer.class);
        if (Collections3.isEmpty(records))
        {
            NamedQueryer nameQueryerF =
                NamedQueryer.builder()
                    .hql("FROM Primer p WHERE p.forwardPrimerName = :forwardPrimerName AND p.deleted = false")
                    .names(Lists.newArrayList("forwardPrimerName"))
                    .values(Lists.newArrayList((Object)request.getForwardPrimerName()))
                    .build();
            List<Primer> nameRecordsF = baseDaoSupport.find(nameQueryerF, Primer.class);
            
            NamedQueryer nameQueryerR =
                NamedQueryer.builder()
                    .hql("FROM Primer p WHERE p.reversePrimerName = :reversePrimerName AND p.deleted = false")
                    .names(Lists.newArrayList("reversePrimerName"))
                    .values(Lists.newArrayList((Object)request.getReversePrimerName()))
                    .build();
            List<Primer> nameRecordsR = baseDaoSupport.find(nameQueryerR, Primer.class);
            
            if (Collections3.isNotEmpty(nameRecordsF))
            {
                return CheckPrimerForDesignRequest.PRIMER_DESIGN_CHECK_NO_F;
            }
            if (Collections3.isNotEmpty(nameRecordsR))
            {
                return CheckPrimerForDesignRequest.PRIMER_DESIGN_CHECK_NO_R;
            }
            return CheckPrimerForDesignRequest.PRIMER_DESIGN_CHECK_YES;
        }
        return CheckPrimerForDesignRequest.PRIMER_DESIGN_CHECK_SAVE;
        //0数据库已存，1数据库未保存，校验通过，2数据库未存，正向引物名存在，3数据库未存，反向引物名存在
    }
    
    @Override
    @Transactional
    public void excelDataInsert(PrimerExcelListRequest request)
    {
        
        List<Primer> primerList = request.getPrimerList();
        if (primerList.size() > 0)
        {
            for (Primer p : primerList)
            {
                p.setDeleted(false);
                p.setCreateTime(new Date());
                baseDaoSupport.insert(p);
            }
        }
    }
    
    @Override
    public List<Primer> getListByChromAndPcr(CheckPrimerForDesignRequest request)
    {
        String chromosomeNumber = request.getChromosomeNumber();
        if (StringUtils.isNotEmpty(chromosomeNumber))
        {
            if (chromosomeNumber.contains("chr"))
            {
                chromosomeNumber = chromosomeNumber.replace("chr", "");
            }
            
        }
        String hql =
            "FROM Primer p where ( p.chromosomeNumber = :chromosomeNumber or p.chromosomeNumber = :chromosomeNumber2 ) AND p.pcrStartPoint = :pcrStartPoint AND p.pcrEndPoint = :pcrEndPoint AND p.forwardPrimerName=:forwardPrimerName AND p.reversePrimerName=:reversePrimerName AND p.applicationType=:applicationType AND p.deleted = false order by p.createTime desc ";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Lists.newArrayList("chromosomeNumber",
                    "chromosomeNumber2",
                    "pcrStartPoint",
                    "pcrEndPoint",
                    "forwardPrimerName",
                    "reversePrimerName",
                    "applicationType"))
                .values(Lists.newArrayList(chromosomeNumber,
                    "chr" + chromosomeNumber,
                    request.getPcrStartPoint(),
                    request.getPcrEndPoint(),
                    request.getForwardPrimerName(),
                    request.getReversePrimerName(),
                    (Object)request.getApplicationType()))
                .build();
        List<Primer> records = baseDaoSupport.find(queryer, Primer.class);
        return records;
    }
    
    @Override
    public List<Primer> getListByProductCodeAndType(CheckPrimerForDesignRequest request)
    {
        String hql =
            "FROM Primer p where p.productCode=:productCode AND p.applicationType=:applicationType AND p.deleted = false order by p.createTime desc ";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Lists.newArrayList("productCode", "applicationType"))
                .values(Lists.newArrayList((Object)request.getProductCode(), request.getApplicationType()))
                .build();
        List<Primer> records = baseDaoSupport.find(queryer, Primer.class);
        return records;
    }
    
    @Override
    public Primer getByName(String name)
    {
        
        if (StringUtils.isEmpty(name))
        {
            return null;
        }
        String hql = "FROM Primer p WHERE p.name = :name";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Arrays.asList("name")).values(Arrays.asList((Object)name)).build();
        List<Primer> primers = baseDaoSupport.find(queryer, Primer.class);
        return primers.isEmpty() ? null : Collections3.getFirst(primers);
        
    }
    
    @Override
    public List<Primer> selectByProperties(Primer p)
    {
        String hql =
            "FROM Primer p WHERE p.gene = :gene AND p.chromosomeNumber=:chromosomeNumber AND p.pcrStartPoint=:pcrStartPoint AND p.pcrEndPoint=:pcrEndPoint"
                + " AND p.forwardPrimerName=:forwardPrimerName AND p.reversePrimerName=:reversePrimerName AND p.productCode=:productCode AND p.applicationType=:applicationType";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Lists.newArrayList("gene",
                    "chromosomeNumber",
                    "pcrStartPoint",
                    "pcrEndPoint",
                    "forwardPrimerName",
                    "reversePrimerName",
                    "productCode",
                    "applicationType"))
                .values(Lists.newArrayList((Object)p.getGene(),
                    (Object) p.getChromosomeNumber(),
                    (Object)p.getPcrStartPoint(),
                    (Object)p.getPcrEndPoint(),
                    (Object)p.getForwardPrimerName(),
                    (Object)p.getReversePrimerName(),
                    (Object)p.getProductCode(),
                    (Object)p.getApplicationType()))
                .build();
        return baseDaoSupport.find(queryer, Primer.class);
    }
}
