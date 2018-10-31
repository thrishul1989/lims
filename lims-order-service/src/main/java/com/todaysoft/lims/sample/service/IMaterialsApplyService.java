package com.todaysoft.lims.sample.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.MaterialsApplySearcher;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApply;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApplyDetail;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApplyTransport;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApplyTransportRelation;

public interface IMaterialsApplyService
{
    
    Pagination<MaterialsApply> paging(MaterialsApplySearcher searcher);
    
    List<MaterialsApply> getMaterialsApplys(MaterialsApplySearcher searcher);
    
    MaterialsApply getEntityById(String id);
    
    List<MaterialsApplyDetail> getEntityByMaterialsApplyId(String materialsApplyId);
    
    MaterialsApplyTransport createTransport(MaterialsApplyTransport data);
    
    void createRelation(MaterialsApplyTransportRelation data);
    
    void updateMaterialsApply(MaterialsApply data);
    
    void updateMaterialsApplyDetail(MaterialsApplyDetail data);
    
    MaterialsApplyDetail getMaterialsApplyDetailById(@PathVariable String id);
    
    List<MaterialsApplyTransport> getMATByMaterialsApplyId(String materialsApplyId);
    
    List<MaterialsApplyTransportRelation> getMATRByTransportId(String transportId);
}
