package com.todaysoft.lims.reagent.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.ProbeSearcher;
import com.todaysoft.lims.reagent.entity.Gene;
import com.todaysoft.lims.reagent.entity.Probe;
import com.todaysoft.lims.reagent.entity.ProbeGene;
import com.todaysoft.lims.reagent.exception.ServiceException;
import com.todaysoft.lims.reagent.model.request.ProbeCreateRequest;
import com.todaysoft.lims.reagent.model.request.ProbeListRequest;
import com.todaysoft.lims.reagent.model.request.ProbeModifyRequest;
import com.todaysoft.lims.reagent.model.request.ProbePagingRequest;
import com.todaysoft.lims.reagent.service.IProbeService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ProbeService implements IProbeService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Probe> paging(ProbePagingRequest request)
    {
        ProbeSearcher searcher = new ProbeSearcher();
        BeanUtils.copyProperties(request, searcher);
        searcher.setDelFlag(0);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), Probe.class);
    }
    
    @Override
    public List<Probe> list(ProbeListRequest request)
    {
        ProbeSearcher searcher = new ProbeSearcher();
        BeanUtils.copyProperties(request, searcher);
        searcher.setDelFlag(0);
        List<Probe> pList = baseDaoSupport.find(searcher);
        return pList;
    }
    
    @Override
    public Probe get(String id)
    {
        return baseDaoSupport.get(Probe.class, id);
    }
    
    @Override
    public Probe getByName(String name)
    {
        if (StringUtils.isEmpty(name))
        {
            return null;
        }
        String hql = "FROM Probe p WHERE p.name = :name";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Arrays.asList("name")).values(Arrays.asList((Object)name)).build();
        List<Probe> probes = baseDaoSupport.find(queryer, Probe.class);
        return probes.isEmpty() ? null : Collections3.getFirst(probes);
    }
    
    @Override
    @Transactional
    public String create(ProbeCreateRequest request)
    {
        if (StringUtils.isEmpty(request.getName()))
        {
            // TODO: 错误码设置
            throw new ServiceException("探针名称不能为空");
        }
        Probe entity = new Probe();
        
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        entity.setDelFlag(0);
        List l = baseDaoSupport.findBySql("select nextval('probeSeq')");
        if (!l.isEmpty())
        {
            entity.setCode(String.format("P%03d", l.get(0)));
            
        }
        
        baseDaoSupport.insert(entity);
        if (StringUtils.isNotEmpty(request.getGeneId()))
        {
            
            String[] geneId = request.getGeneId().split(",");
            for (int i = 0; i < geneId.length; i++)
            {
                ProbeGene pg = new ProbeGene();
                Gene g = new Gene();
                g.setId(geneId[i]);
                pg.setGene(g);
                pg.setProbe(entity);
                baseDaoSupport.insert(pg);
            }
        }
        baseDaoSupport.update(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void modify(ProbeModifyRequest request)
    {
        Probe entity = get(request.getId());
        baseDaoSupport.execute("delete from ProbeGene p where p.probe.id='" + entity.getId() + "'");
        if (StringUtils.isNotEmpty(request.getGeneId()))
        {
            String[] geneId = request.getGeneId().split(",");
            for (int i = 0; i < geneId.length; i++)
            {
                ProbeGene pg = new ProbeGene();
                Gene g = new Gene();
                g.setId(geneId[i]);
                pg.setGene(g);
                pg.setProbe(entity);
                baseDaoSupport.insert(pg);
            }
        }
        
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        entity.setDelFlag(0);
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        Probe entity = get(id);
        entity.setDelFlag(1);
        entity.setDeleteTime(new Date());
        baseDaoSupport.update(entity);
    }
    
    @Override
    public boolean validate(Probe request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Probe.class, request, "code", "name")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public List<Probe> getContactProducts(List<Integer> ids)
    {
        // TODO Auto-generated method stub
        if (null == ids || ids.isEmpty())
        {
            return null;
        }
        String hql = "FROM Probe p WHERE p.id IN :ids";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Arrays.asList("ids")).values(Arrays.asList((Object)ids)).build();
        List<Probe> products = baseDaoSupport.find(queryer, Probe.class);
        return products;
    }
    
    @Override
    @Transactional
    public String getProbeNext()
    {
        // TODO Auto-generated method stub
        List l = baseDaoSupport.findBySql("select nextval('probeSeq')");
        if (!l.isEmpty())
        {
            return String.format("P%03d", l.get(0));
            
        }
        else
        {
            return "P00001";
        }
    }
}
