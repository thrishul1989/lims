package com.todaysoft.lims.sample.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.MaterialsApplySearcher;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApply;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApplyDetail;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApplyTransport;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApplyTransportRelation;
import com.todaysoft.lims.sample.service.IMaterialsApplyService;

@RestController
@RequestMapping("/bcm/materialsApply")
public class MaterialsApplyController
{
    
    @Autowired
    private IMaterialsApplyService service;
    
    @RequestMapping(value = "/paging")
    public Pagination<MaterialsApply> paging(@RequestBody MaterialsApplySearcher searcher)
    {
        return service.paging(searcher);
    }
    
    @RequestMapping(value = "/getMaterialsApplys")
    public List<MaterialsApply> getContracts(@RequestBody MaterialsApplySearcher searcher)
    {
        return service.getMaterialsApplys(searcher);
    }
    
    @RequestMapping(value = "/getMaterialsApply/{id}")
    public MaterialsApply getContractById(@PathVariable String id)
    {
        return service.getEntityById(id);
    }
    
    @RequestMapping(value = "/getEntityByMaterialsApplyId/{materialsApplyId}")
    public List<MaterialsApplyDetail> getEntityByMaterialsApplyId(@PathVariable String materialsApplyId)
    {
        return service.getEntityByMaterialsApplyId(materialsApplyId);
    }
    
    @RequestMapping(value = "/createTransport.do")
    public MaterialsApplyTransport createTransport(@RequestBody MaterialsApplyTransport data)
    {
        return service.createTransport(data);
    }
    
    @RequestMapping(value = "/createRelation.do")
    public void createRelation(@RequestBody MaterialsApplyTransportRelation data)
    {
        service.createRelation(data);
    }
    
    @RequestMapping(value = "/updateMaterialsApply")
    public void updateMaterialsApply(@RequestBody MaterialsApply data)
    {
        service.updateMaterialsApply(data);
    }
    
    @RequestMapping(value = "/updateMaterialsApplyDetail")
    public void updateMaterialsApplyDetail(@RequestBody MaterialsApplyDetail data)
    {
        service.updateMaterialsApplyDetail(data);
    }
    
    @RequestMapping(value = "getMaterialsApplyDetailById/{id}")
    public MaterialsApplyDetail getMaterialsApplyDetailById(@PathVariable String id)
    {
        return service.getMaterialsApplyDetailById(id);
    }
    
    @RequestMapping(value = "/getMATByMaterialsApplyId/{materialsApplyId}")
    public List<MaterialsApplyTransport> getMATByMaterialsApplyId(@PathVariable String materialsApplyId)
    {
        return service.getMATByMaterialsApplyId(materialsApplyId);
    }
    
    @RequestMapping(value = "/getMATRByTransportId/{transportId}")
    public List<MaterialsApplyTransportRelation> getMATRByTransportId(@PathVariable String transportId)
    {
        return service.getMATRByTransportId(transportId);
    }
}
