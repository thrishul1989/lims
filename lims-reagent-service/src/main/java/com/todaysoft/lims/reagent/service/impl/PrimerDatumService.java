package com.todaysoft.lims.reagent.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.PrimerDatumSearcher;
import com.todaysoft.lims.reagent.entity.PrimerDatum;
import com.todaysoft.lims.reagent.model.request.PrimerDatumListRequest;
import com.todaysoft.lims.reagent.model.request.PrimerDatumRequest;
import com.todaysoft.lims.reagent.service.IPrimerDatumService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class PrimerDatumService implements IPrimerDatumService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<PrimerDatum> paging(PrimerDatumListRequest request)
    {
        PrimerDatumSearcher searcher = new PrimerDatumSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), PrimerDatum.class);
    }
    
    @Override
    public List<PrimerDatum> list(PrimerDatumListRequest request)
    {
        PrimerDatumSearcher searcher = new PrimerDatumSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), PrimerDatum.class);
    }
    
    @Override
    public PrimerDatum get(String id)
    {
        return baseDaoSupport.get(PrimerDatum.class, id);
    }
    
    @Override
    @Transactional
    public String create(PrimerDatum request)
    {
        PrimerDatum entity = new PrimerDatum();
        BeanUtils.copyProperties(request, entity);
        entity.setDeleted(false);
        entity.setCreateTime(new Date());
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void modify(PrimerDatum request)
    {
        PrimerDatum entity = get(request.getId());
        BeanUtils.copyProperties(request, entity, "createTime", "deleted", "deleteTime");
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        PrimerDatum entity = get(id);
        entity.setDeleted(true);
        entity.setDeleteTime(new Date());
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void uploadData(PrimerDatumRequest request)
    {
        List<PrimerDatum> list = request.getList();
        if (Collections3.isNotEmpty(list))
        {
            /*for (int i = 0; i < 10; i++)
            {
                List<PrimerDatum> newList = list.subList(i * 5000, (i + 1) * 5000);
                Thread t = new Thread()
                {
                    @Override
                    @Transactional
                    public void run()
                    {
                        for (PrimerDatum p : newList)
                        {
                            create(p);
                        }
                    }
                };
                t.start();
            }*/
            for (int i = 0; i < list.size(); i++)
            {
                create(list.get(i));
            }
        }
        
    }
}
