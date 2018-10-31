package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.disease.Disease;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseFormRequest;
import com.todaysoft.lims.gateway.model.request.disease.DiseasePagingRequest;
import com.todaysoft.lims.gateway.model.request.disease.Gene;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseGenePagingRequest;
import com.todaysoft.lims.gateway.service.IDiseaseService;

@RestController
@RequestMapping("/disease")
public class DiseaseController
{
    @Autowired
    private IDiseaseService service;
    
   
    
    
    /***********************疾病管理***************************************/
    @RequestMapping(value = "/pagingDisease")
  	public Pagination<Disease> pagingDisease(@RequestBody DiseasePagingRequest request){
  		Pagination<Disease> p =  service.pagingDisease(request);
  		return p;
  	}
    
    
    @RequestMapping(value = "/createDisease",method = RequestMethod.POST)
    public String createDisease(@RequestBody DiseaseFormRequest request)
    {
        return service.createDisease(request);
    }
    
    
    @RequestMapping(value = "/updateDisease",method = RequestMethod.POST)
    public String updateDisease(@RequestBody DiseaseFormRequest request){
        return service.updateDisease(request);
    }
    
    @RequestMapping(value = "/deleteDisease/{id}",method = RequestMethod.DELETE)
  	public void deleteDisease(@PathVariable String id){
  		service.deleteDisease(id);
  	}
    
    
    @RequestMapping(value = "/validateDiseaseName", method = RequestMethod.POST)
    public boolean validateDiseaseName(@RequestBody DiseaseFormRequest request){
    	return service.validateDiseaseName(request);
    }
	 
    
    @RequestMapping(value ="/diseaseSelect")
    public List<Disease> diseaseSelect(@RequestBody DiseasePagingRequest request){
		return service.diseaseSelect(request);
	}
    
    
    
    /*****************************************************************************/
    
    @RequestMapping(value = "/createGene",method = RequestMethod.POST)
    public String create(@RequestBody DiseaseGeneFormRequest request){
        return service.create(request);
    }
    
    @RequestMapping(value = "/updateGene",method = RequestMethod.POST)
    public String updateGene(@RequestBody DiseaseGeneFormRequest request){
        return service.updateGene(request);
    }
    
    
    @RequestMapping(value = "/pagingGene")
	public Pagination<Gene> paging(@RequestBody DiseaseGenePagingRequest request){
		return service.paging(request);
	}
    
    
    @RequestMapping(value = "/deleteGene/{id}",method = RequestMethod.DELETE)
	public void delete(@PathVariable String id){
		service.delete(id);
	}
    
    
    @RequestMapping(value = "/getGeneById/{id}",method = RequestMethod.GET)
   	public Gene getGeneById(@PathVariable String id){
   		return service.getGeneById(id);
   	}
    
    @RequestMapping(value = "/getGeneByCode/{code}",method = RequestMethod.GET)
   	public Gene getGeneByCode(@PathVariable String code){
   		return service.getGeneByCode(code);
   	}
    
    
       
    @RequestMapping(value = "/validateGeneName", method = RequestMethod.POST)
    public boolean validateGeneName(@RequestBody DiseaseGeneFormRequest request){
    	return service.validateGeneName(request);
    }
	 
    
    @RequestMapping(value="/geneSelect")
	public List<Gene> geneList(@RequestBody DiseaseGenePagingRequest searcher){
		return service.geneList(searcher);
	}
    
    
    @RequestMapping(value = "/getDiseaseById/{id}",method = RequestMethod.GET)
   	public Disease getDiseaseById(@PathVariable String id){
   		return service.getDiseaseById(id);
   	}
    
    @RequestMapping(value = "/getDiseaseByCode/{code}",method = RequestMethod.GET)
   	public Disease getDiseaseByCode(@PathVariable String code){
   		return service.getDiseaseByCode(code);
   	}
    
    
    
    
    @RequestMapping(value = "/validateDiseaseCode", method = RequestMethod.POST)
    public boolean validateDiseaseCode(@RequestBody DiseaseGeneFormRequest request){
    	return service.validateDiseaseCode(request);
    }
	 
    
    @RequestMapping(value = "/validateGeneCode", method = RequestMethod.POST)
    public boolean validateGeneCode(@RequestBody DiseaseGeneFormRequest request){
    	return service.validateGeneCode(request);
    }
	 
    
}
