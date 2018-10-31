package com.todaysoft.lims.sample.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.MaterialsApplySearcher;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApply;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApplyDetail;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApplyTransport;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApplyTransportRelation;
import com.todaysoft.lims.sample.service.IMaterialsApplyService;

@Service
public class MaterialsApplyService implements IMaterialsApplyService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<MaterialsApply> paging(MaterialsApplySearcher searcher)
    {
        Pagination<MaterialsApply> paging = baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), MaterialsApply.class);
        return paging;
    }
    
    @Override
    public List<MaterialsApply> getMaterialsApplys(MaterialsApplySearcher searcher)
    {
        
        return baseDaoSupport.find(searcher.toQuery(), MaterialsApply.class);
    }
    
    @Override
    public MaterialsApply getEntityById(String id)
    {
        
        return baseDaoSupport.get(MaterialsApply.class, id);
    }
    
    @Override
    public List<MaterialsApplyDetail> getEntityByMaterialsApplyId(String materialsApplyId)
    {
        String hql = "FROM MaterialsApplyDetail m WHERE m.applyId= :materialsApplyId";
        List<MaterialsApplyDetail> records =
            baseDaoSupport.findByNamedParam(MaterialsApplyDetail.class, hql, new String[] {"materialsApplyId"}, new Object[] {materialsApplyId});
        return records;
    }
    
    @Override
    @Transactional
    public MaterialsApplyTransport createTransport(MaterialsApplyTransport data)
    {
        baseDaoSupport.insert(data);
        return data;
    }
    
    @Override
    @Transactional
    public void createRelation(MaterialsApplyTransportRelation data)
    {
        baseDaoSupport.insert(data);
    }
    
    @Override
    @Transactional
    public void updateMaterialsApply(MaterialsApply data)
    {
        baseDaoSupport.update(data);
    }
    
    @Override
    @Transactional
    public void updateMaterialsApplyDetail(MaterialsApplyDetail data)
    {
        baseDaoSupport.update(data);
    }
    
    @Override
    public MaterialsApplyDetail getMaterialsApplyDetailById(String id)
    {
        return baseDaoSupport.get(MaterialsApplyDetail.class, id);
    }
    
    @Override
    public List<MaterialsApplyTransport> getMATByMaterialsApplyId(String materialsApplyId)
    {
        String hql = "FROM MaterialsApplyTransport m WHERE m.applyId= :materialsApplyId";
        List<MaterialsApplyTransport> records =
            baseDaoSupport.findByNamedParam(MaterialsApplyTransport.class, hql, new String[] {"materialsApplyId"}, new Object[] {materialsApplyId});
        return records;
    }
    
    @Override
    public List<MaterialsApplyTransportRelation> getMATRByTransportId(String transportId)
    {
        String hql = "FROM MaterialsApplyTransportRelation m WHERE m.transportId= :transportId";
        List<MaterialsApplyTransportRelation> records =
            baseDaoSupport.findByNamedParam(MaterialsApplyTransportRelation.class, hql, new String[] {"transportId"}, new Object[] {transportId});
        return records;
    }
    
}
