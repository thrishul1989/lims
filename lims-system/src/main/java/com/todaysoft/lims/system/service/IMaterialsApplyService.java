package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.searcher.MaterialsApplySearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApply;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApplyDetail;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApplyTransport;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApplyTransportRelation;

public interface IMaterialsApplyService
{
    
    Pagination<MaterialsApply> paging(MaterialsApplySearcher searcher, int pageNo, int pageSize);
    
    List<MaterialsApply> getMaterialsApplys(MaterialsApplySearcher searcher);
    
    MaterialsApply getEntityById(String id);
    
    List<MaterialsApplyDetail> getEntityByMaterialsApplyId(String materialsApplyId);
    
    MaterialsApplyTransport createTransport(MaterialsApplyTransport data);
    
    void createRelation(MaterialsApplyTransportRelation data);
    
    void updateMaterialsApply(MaterialsApply data);
    
    void updateMaterialsApplyDetail(MaterialsApplyDetail data);
    
    MaterialsApplyDetail getMaterialsApplyDetailById(String id);
    
    List<MaterialsApplyTransport> getMATByMaterialsApplyId(String materialsApplyId);
    
    List<MaterialsApplyTransportRelation> getMATRByTransportId(String transportId);
    
}
