package com.todaysoft.lims.product.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.model.request.DiseaseFormRequest;
import com.todaysoft.lims.product.model.request.DiseaseGeneFormRequest;
import com.todaysoft.lims.product.model.request.DiseaseGenePagingRequest;
import com.todaysoft.lims.product.model.request.DiseasePagingRequest;
import com.todaysoft.lims.product.model.response.DiseasePageModel;
import com.todaysoft.lims.product.model.response.GenePageModel;
import com.todaysoft.lims.product.service.IDiseaseService;

@RestController
@RequestMapping("/bcm/disease")
public class DiseaseController
{
    @Autowired
    private IDiseaseService service;
    
    /***********************疾病管理***************************************/
    @RequestMapping(value = "/pagingDisease")
    public Pagination<DiseasePageModel> pagingDisease(@RequestBody DiseasePagingRequest request)
    {
        Pagination<DiseasePageModel> p = service.pagingDisease(request);
        return p;
    }
    
    @RequestMapping(value = "/createDisease", method = RequestMethod.POST)
    public String createDisease(@RequestBody DiseaseFormRequest request)
    {
        String diseaseId = service.createDisease(request);
        if (StringUtils.isNotEmpty(diseaseId))
        {
            service.sendDiseaseProduce(diseaseId, "create");
        }
        
        return diseaseId;
    }
    
    @RequestMapping(value = "/deleteDisease/{id}", method = RequestMethod.DELETE)
    public void deleteDisease(@PathVariable String id)
    {
        String diseaseId = service.deleteDisease(id);
        if (StringUtils.isNotEmpty(diseaseId))
        {
            service.sendDiseaseProduce(diseaseId, "delete");
        }
        
    }
    
    @RequestMapping(value = "/updateDisease", method = RequestMethod.POST)
    public String updateDisease(@RequestBody DiseaseFormRequest request)
    {
        String diseaseId = service.updateDisease(request);
        if (StringUtils.isNotEmpty(diseaseId))
        {
            service.sendDiseaseProduce(diseaseId, "modify");
        }
        return diseaseId;
    }
    
    @RequestMapping(value = "/diseaseSelect")
    public List<DiseasePageModel> diseaseSelect(@RequestBody DiseasePagingRequest request)
    {
        return service.diseaseSelect(request);
    }
    
    /***********************基因管理***************************************/
    @RequestMapping(value = "/createGene", method = RequestMethod.POST)
    public String create(@RequestBody DiseaseGeneFormRequest request)
    {
        String geneId = service.create(request);
        if (StringUtils.isNotEmpty(geneId))
        {
            service.sendGeneProduce(geneId, "create");
        }
        return geneId;
    }
    
    @RequestMapping(value = "/updateGene", method = RequestMethod.POST)
    public String updateGene(@RequestBody DiseaseGeneFormRequest request)
    {
        String geneId = service.updateGene(request);
        if (StringUtils.isNotEmpty(geneId))
        {
            service.sendGeneProduce(geneId, "modify");
        }
        return geneId;
    }
    
    @RequestMapping(value = "/pagingGene")
    public Pagination<GenePageModel> paging(@RequestBody DiseaseGenePagingRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping(value = "/deleteGene/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        String geneId = service.delete(id);
        if (StringUtils.isNotEmpty(geneId))
        {
            service.sendGeneProduce(geneId, "delete");
        }
        
    }
    
    @RequestMapping(value = "/getGeneById/{id}")
    public GenePageModel getGeneById(@PathVariable String id)
    {
        return service.getGeneById(id);
    }
    
    @RequestMapping(value = "/getGeneByCode/{code}")
    public GenePageModel getGeneByCode(@PathVariable String code)
    {
        return service.getGeneByCode(code);
    }
    
    @RequestMapping(value = "/geneSelect")
    public List<GenePageModel> geneList(@RequestBody DiseaseGenePagingRequest searcher)
    {
        return service.geneList(searcher);
    }
    
    @RequestMapping(value = "/validateDiseaseName", method = RequestMethod.POST)
    public boolean validateDiseaseName(@RequestBody DiseaseFormRequest request)
    {
        return service.validateDiseaseName(request);
    }
    
    @RequestMapping(value = "/validateGeneName", method = RequestMethod.POST)
    public boolean validateGeneName(@RequestBody DiseaseGeneFormRequest request)
    {
        return service.validateGeneName(request);
    }
    
    @RequestMapping(value = "/getDiseaseById/{id}")
    public DiseasePageModel getDiseaseById(@PathVariable String id)
    {
        DiseasePageModel diseasePageModel = service.getDiseaseById(id);
        return diseasePageModel;
    }
    
    @RequestMapping(value = "/getDiseaseByCode/{code}")
    public DiseasePageModel getDiseaseByCode(@PathVariable String code)
    {
        DiseasePageModel disease = service.getDiseaseByCode(code);
        return disease;
    }
    
    @RequestMapping(value = "/validateDiseaseCode", method = RequestMethod.POST)
    public boolean validateDiseaseCode(@RequestBody DiseaseFormRequest request)
    {
        return service.validateDiseaseCode(request);
    }
    
    @RequestMapping(value = "/validateGeneCode", method = RequestMethod.POST)
    public boolean validateGeneCode(@RequestBody DiseaseFormRequest request)
    {
        return service.validateGeneCode(request);
    }
    
    @RequestMapping(value = "/getProductDiease/{diseaseId}", method = RequestMethod.GET)
    public Integer getProductDiease(@PathVariable String diseaseId)
    {
        return service.getProductDiease(diseaseId);
    }
    
    @RequestMapping(value = "/getProductIdDiease/{diseaseId}", method = RequestMethod.GET)
    public List<String> getProductIdDiease(@PathVariable String diseaseId)
    {
        return service.getProductIdDiease(diseaseId);
    }
    
    @RequestMapping(value = "/getProductGenes/{geneId}", method = RequestMethod.GET)
    public Integer getProductGenes(@PathVariable String geneId)
    {
        return service.getProductGenes(geneId);
    }
    
    @RequestMapping(value = "/getDiseaseByENName", method = RequestMethod.POST)
    public DiseasePageModel getDiseaseByENName(@RequestBody DiseaseGeneFormRequest request)
    {
        return service.getDiseaseByENName(request);
    }
    
    @RequestMapping(value = "/createRelationProduct", method = RequestMethod.POST)
    public boolean createRelationProduct(@RequestBody String id)
    {
        return service.createRelationProduct(id);
    }
    
    @RequestMapping(value = "/createRelationDisease", method = RequestMethod.POST)
    public boolean createRelationDisease(@RequestBody String id)
    {
        return service.createRelationDisease(id);
    }
    
    @RequestMapping(value = "/createRelationGene", method = RequestMethod.POST)
    public boolean createRelationGene(@RequestBody String id)
    {
        return service.createRelationGene(id);
    }
    
    @RequestMapping(value = "/createRelationPhenotype", method = RequestMethod.POST)
    public boolean createRelationPhenotype(@RequestBody String id)
    {
        return service.createRelationPhenotype(id);
    }
    
}
