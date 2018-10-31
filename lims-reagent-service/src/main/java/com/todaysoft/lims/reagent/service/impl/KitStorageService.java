package com.todaysoft.lims.reagent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.KitStorageSearcher;
import com.todaysoft.lims.reagent.entity.KitStorage;
import com.todaysoft.lims.reagent.entity.ReagentKit;
import com.todaysoft.lims.reagent.service.IKitStorageService;

@Service
public class KitStorageService implements IKitStorageService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<KitStorage> paging(KitStorageSearcher request)
    {
        
        return baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), KitStorage.class);
    }
    
    @Override
    @Transactional
    public Integer create(KitStorageSearcher request)
    {
        //递增seq
        KitStorage st;
        ReagentKit kit = baseDaoSupport.get(ReagentKit.class, request.getReagentKit().getId());
        List<KitStorage> max = baseDaoSupport.find(KitStorage.class,
            "from KitStorage P where P.seq=(select max(t.seq) from KitStorage t where t.reagentKit.id=" + request.getReagentKit().getId() + ")");
        if (!max.isEmpty())
        {
            st = max.get(0);
            for (int i = 1; i <= request.getCreateNum(); i++)
            {
                KitStorage storage = new KitStorage();
                storage.setCode(kit.getCode() + "-" + (st.getSeq() + i));
                storage.setReactionNum(request.getReactionNum());
                storage.setReagentKit(kit);
                storage.setSeq(st.getSeq() + i);
                baseDaoSupport.insert(storage);
            }
        }
        else
        {
            for (int i = 1; i <= request.getCreateNum(); i++)
            {
                KitStorage storage = new KitStorage();
                storage.setCode(kit.getCode() + "-" + (i));
                storage.setReactionNum(request.getReactionNum());
                storage.setReagentKit(kit);
                storage.setSeq(+i);
                baseDaoSupport.insert(storage);
            }
        }
        
        return 0;
    }
    
    @Override
    @Transactional
    public void modifyReaction(KitStorageSearcher request)
    {
        KitStorage entity = get(request.getId());
        entity.setReactionNum(request.getReactionNum());
        baseDaoSupport.update(entity);
        
    }
    
    @Override
    public KitStorage get(Integer id)
    {
        return baseDaoSupport.get(KitStorage.class, id);
    }
    
    @Override
    @Transactional
    public void delete(Integer id)
    {
        KitStorage entity = get(id);
        baseDaoSupport.delete(entity);
        
    }
    
    @Override
    public List<KitStorage> list(KitStorageSearcher request)
    {
        
        return baseDaoSupport.find(request);
    }
    
    @Override
    @Transactional
    public Integer countByKitId(Integer kitId)
    {
        String hql = "select sum(k.reactionNum) from KitStorage k where k.reagentKit.id = ?";
        long c = baseDaoSupport.find(Number.class, hql, kitId).get(0).longValue();
        return new Integer((int)c);
    }
    
}
