package com.todaysoft.lims.system.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Phenotype;
import com.todaysoft.lims.system.model.vo.disease.Disease;
import com.todaysoft.lims.system.model.vo.disease.DiseaseAlias;
import com.todaysoft.lims.system.model.vo.disease.DiseaseFormRequest;
import com.todaysoft.lims.system.model.vo.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.system.model.vo.disease.DiseaseGenePagingRequest;
import com.todaysoft.lims.system.model.vo.disease.DiseasePagingRequest;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.model.vo.disease.GeneAlias;
import com.todaysoft.lims.system.model.vo.disease.SimpleGene;
import com.todaysoft.lims.system.service.IDiseaseGeneService;
import com.todaysoft.lims.system.service.IDocumentService;
import com.todaysoft.lims.system.service.IPhenotypeService;
import com.todaysoft.lims.system.service.impl.IndexESMonitor;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/disease")
public class DiseaseController extends BaseController
{
    @Autowired
    private IDiseaseGeneService service;
    
    @Autowired
    private IPhenotypeService phenotypeservice;
    
    @Autowired
    private IDocumentService documentService;
    
    @RequestMapping("/list.do")
    public String getReceiveOrderList()
    {
        return "disease/main";
    }
    
    /***********************疾病管理start***************************************/
    @RequestMapping(value = "/pagingDisease.do")
    public String pagingDisease(DiseasePagingRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<Disease> pagination = service.pagingDisease(searcher, pageNo, DEFAULTPAGESIZE);
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "disease/disease_main";
    }
    
    /**
     * 疾病添加、修改页面
     * */
    @RequestMapping(value = "/diseaseForm.do", method = RequestMethod.GET)
    public String forwardDiseasePage(String id, ModelMap model, HttpSession session)
    {
        Disease data = new Disease();
        String flag = "新增";
        if (StringUtils.isNotEmpty(id))
        {
            data = getDiseaseById(id);
            flag = "修改";
        }
        model.addAttribute("entity", data);
        if (Collections3.isNotEmpty(data.getAlias()))
        {
            model.addAttribute("aliasList", JSONObject.toJSON(data.getAlias()).toString());
        }
        if (Collections3.isNotEmpty(data.getGeneList()))
        {
            List<SimpleGene> geneList = data.getGeneList();
            model.addAttribute("geneList", JSONObject.toJSON(geneList).toString());
        }
        if (Collections3.isNotEmpty(data.getPhenotypeList()))
        {
            
            List<Phenotype> list = data.getPhenotypeList();
            model.addAttribute("phenotypeList", JSONObject.toJSON(list).toString());
            
        }
        model.addAttribute("flag", flag);
        
        return "disease/disease_form";
    }
    
    @RequestMapping(value = "/getDiseaseById")
    private Disease getDiseaseById(String id)
    {
        Disease d = service.getDiseaseById(id);
        if (StringUtils.isNotEmpty(d) && StringUtils.isNotEmpty(d.getId()))
        {
            List<String> documents = documentService.getDocumentByDisease(d.getId());
            
            d.setDocuments(documents);
        }
        
        return d;
    }
    
    @RequestMapping(value = "/getDiseaseByIds")
    @ResponseBody
    private List<Disease> getDiseaseByIds(String ids)
    {
        List<Disease> list = new ArrayList<>();
        String[] idArray = ids.split("\\,");
        for (String id : idArray)
        {
            Disease d = service.getDiseaseById(id);
            list.add(d);
        }
        
        return list;
    }
    
    @RequestMapping(value = "/createDisease.do", method = RequestMethod.POST)
    public String createDisease(DiseaseFormRequest request, ModelMap model, HttpSession session)
    {
        /**
         * 别名
         */
        request.setAlias(JSON.parseArray(request.getAliasJson() + "", DiseaseAlias.class));
        request.setGeneList(JSON.parseArray(request.getGenes() + "", Gene.class));
        request.setPhenotypeList(JSON.parseArray(request.getPhenotypes() + "", Phenotype.class));
        if (StringUtils.isNotEmpty(request.getId()))
        {
            service.updateDisease(request);
        }
        else
        {
            service.createDisease(request);
        }
        
        return "redirect:/disease/pagingDisease.do";
    }
    
    @RequestMapping(value = "/deleteDisease.do")
    @ResponseBody
    public boolean deleteDisease(String id, ModelMap model, HttpSession session)
    {
        if (service.getProductDiease(id) > 0)
        {
            return false;
        }
        else
        {
            service.deleteDisease(id);
        }
        return true;
    }
    
    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping("/validateDiseaseName.do")
    public boolean validateDiseaseName(DiseaseFormRequest request)
    {
        return service.validateDiseaseName(request);
    }
    
    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping("/validateDiseaseCode.do")
    public boolean validateDiseaseCode(DiseaseFormRequest request)
    {
        return service.validateDiseaseCode(request);
    }
    
    /**
     * 查看基因
     */
    @RequestMapping("/viewDisease.do")
    public String viewDisease(String id, ModelMap model, HttpSession session)
    {
        Disease data = new Disease();
        if (StringUtils.isNotEmpty(id))
        {
            data = getDiseaseById(id);
        }
        model.addAttribute("entity", data);
        return "disease/disease_view";
    }
    
    @RequestMapping(value = "/diseaseSelect.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Disease> diseaseSelect(DiseasePagingRequest request)
    {
        
        Pagination<Disease> pagination = service.pagingDisease(request, 1, DEFAULTPAGESIZE);
        return pagination.getRecords();
    }
    
    @RequestMapping(value = "/getDiseaseAll.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDiseaseAll(DiseasePagingRequest searcher)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "");
        List<Disease> list = service.pagingDisease(searcher, 1, DEFAULTPAGESIZE).getRecords();
        map.put("value", list);
        return map;
    }
    
    /***********************疾病管理end***************************************/
    
    /***********************基因管理start***************************************/
    
    @RequestMapping("/genelist.do")
    public String genelist(DiseaseGenePagingRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<Gene> pagination = service.paging(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "disease/disease_gene";
    }
    
    //增加
    @RequestMapping(value = "/addDiseaseGene.do")
    public String createGene(DiseaseGeneFormRequest request, ModelMap model, HttpSession session)
    {
        request.setAlias(JSON.parseArray(request.getAliasJson() + "", GeneAlias.class));
        
        if (StringUtils.isNotEmpty(request.getId()))
        {
            service.updateGene(request);
        }
        else
        {
            service.createGene(request);
        }
        return "redirect:/disease/genelist.do";
    }
    
    @RequestMapping(value = "/deleteGene")
    @ResponseBody
    public boolean deleteGene(String id, ModelMap model, HttpSession session)
    {
        
        if (service.getProductGenes(id) > 0)
        {
            return false;
        }
        else
        {
            service.deleteGene(id);
        }
        return true;
    }
    
    @RequestMapping(value = "/getGeneById")
    public Gene getGeneById(String id)
    {
        Gene g = service.getGeneById(id);
        if (StringUtils.isNotEmpty(g) && StringUtils.isNotEmpty(g.getId()))
        {
            List<String> documents = documentService.getDocumentByGene(g.getId());
            g.setDocuments(documents);
        }
        return g;
    }
    
    @RequestMapping(value = "/getGeneByIds")
    @ResponseBody
    public List<Gene> getGeneByIds(String ids)
    {
        
        List<Gene> list = new ArrayList<>();
        String[] idArray = ids.split("\\,");
        for (String id : idArray)
        {
            Gene g = service.getGeneById(id);
            list.add(g);
        }
        
        return list;
        
    }
    
    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping("/validateGeneName.do")
    public boolean validateGeneName(DiseaseGeneFormRequest request)
    {
        return service.validateGeneName(request);
    }
    
    /**
    * 唯一性校验
    */
    @ResponseBody
    @RequestMapping("/validateGeneCode.do")
    public boolean validateGeneCode(DiseaseGeneFormRequest request)
    {
        return service.validateGeneCode(request);
    }
    
    /**
     * 基因页面
     * */
    @RequestMapping(value = "/geneForm.do", method = RequestMethod.GET)
    public String createPage(String id, ModelMap model, HttpSession session)
    {
        Gene data = new Gene();
        String flag = "新增";
        if (StringUtils.isNotEmpty(id))
        {
            data = getGeneById(id);
            flag = "修改";
        }
        model.addAttribute("entity", data);
        if (Collections3.isNotEmpty(data.getAlias()))
        {
            model.addAttribute("aliasList", JSONObject.toJSON(data.getAlias()).toString());
        }
        model.addAttribute("flag", flag);
        return "disease/gene_form";
    }
    
    @RequestMapping(value = "/viewGene.do", method = RequestMethod.GET)
    public String viewGene(String id, ModelMap model, HttpSession session)
    {
        
        Gene data = new Gene();
        if (StringUtils.isNotEmpty(id))
        {
            data = getGeneById(id);
        }
        model.addAttribute("entity", data);
        if (Collections3.isNotEmpty(data.getAlias()))
        {
            model.addAttribute("aliasList", JSONObject.toJSON(data.getAlias()).toString());
        }
        return "disease/gene_view";
    }
    
    /***********************基因管理end***************************************/
    
    @RequestMapping(value = "/geneSelect.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Gene> geneSelect(DiseaseGenePagingRequest request)
    {
        Pagination<Gene> pagination = service.paging(request, 1, DEFAULTPAGESIZE);
        return pagination.getRecords();
        //return service.geneList(request);
    }
    
    /***********************上传疾病数据***************************************/
    @RequestMapping("/uploadDisease.do")
    public String uploadDisease(HttpServletRequest request, HttpServletResponse response)
    {
        service.analyticalDisease(request, response);
        return "redirect:/disease/pagingDisease.do";
    }
    
    /***********************上传基因数据***************************************/
    @RequestMapping("/uploadGene.do")
    public String uploadGene(HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        List<DiseaseGeneFormRequest> list = service.analyticalGene(request, response);
        //查看是否有重复编号，标记不同颜色提示
        for (DiseaseGeneFormRequest gene : list)
        {
            
            Gene d = service.getGeneByCode("" == gene.getCode() ? "0" : gene.getCode());
            if (null == d)
            {
                gene.setIsOverwrite(0);
            }
            else
            {
                gene.setIsOverwrite(1);
            }
            
        }
        
        model.addAttribute("geneList", list);
        return "/disease/gene_upload";
    }
    
    @RequestMapping(value = "/uploadGeneData.do", method = RequestMethod.POST)
    @ResponseBody
    public String uploadGeneData(String geneList, HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        List<DiseaseGeneFormRequest> list = JSON.parseArray(geneList + "", DiseaseGeneFormRequest.class);
        for (DiseaseGeneFormRequest gene : list)
        {
            if (StringUtils.isNotEmpty(gene.getCode()))
            {
                Gene g = service.getGeneByCode(gene.getCode());
                if (null != g)
                {
                    //覆盖
                    gene.setId(g.getId());
                    String[] alias = gene.getImportAlias().split("\\;");
                    List<GeneAlias> geneAliasList = new ArrayList<GeneAlias>();
                    for (String a : alias)
                    {
                        GeneAlias genealia = new GeneAlias();
                        genealia.setId(gene.getId());
                        genealia.setName(a);
                        genealia.setSymbol(a);
                        geneAliasList.add(genealia);
                        
                    }
                    gene.setAlias(geneAliasList);
                    service.updateGene(gene);
                }
                else
                {
                    String[] alias = gene.getImportAlias().split("\\;");
                    List<GeneAlias> geneAliasList = new ArrayList<GeneAlias>();
                    for (String a : alias)
                    {
                        GeneAlias genealia = new GeneAlias();
                        genealia.setName(a);
                        genealia.setSymbol(a);
                        geneAliasList.add(genealia);
                        
                    }
                    gene.setAlias(geneAliasList);
                    service.createGene(gene);
                }
                
            }
            
        }
        
        return "success";
        
    }
    
    @RequestMapping("/quickUploadDisease.do")
    public String quickUploadDisease(HttpServletRequest request, HttpServletResponse response)
    {
        service.quickUploadDisease(request, response);
        return "redirect:/disease/pagingDisease.do";
    }
    
    @RequestMapping(value = "/getGenesAll.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getGenes(DiseaseGenePagingRequest searcher)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "");
        List<Gene> list = service.paging(searcher, 1, DEFAULTPAGESIZE).getRecords();
        map.put("value", list);
        return map;
    }
    
    @RequestMapping("/esSearchMapper.do")
    public String esSearchMapper(ModelMap model, HttpSession session)
    {
        return "disease/es_search_mapper";
    }
    
    @RequestMapping("/esMapper.do")
    @ResponseBody
    public boolean esMapper(int type)
    {
        switch (type)
        {
            case 1:
                service.indexForProducts();
                break;
            case 2:
                service.indexForDiseases();
                break;
            case 3:
                service.indexForGenes();
                break;
            case 4:
                service.indexForPhenotypes();
                break;
        }
        
        return true;
    }
    
    @RequestMapping("/indexes/monitor.do")
    @ResponseBody
    public IndexESMonitor getMonitor()
    {
        return service.getIndexESMonitor();
    }
}
