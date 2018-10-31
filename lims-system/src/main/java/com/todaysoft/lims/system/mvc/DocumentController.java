package com.todaysoft.lims.system.mvc;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.searcher.DocumentSearcher;
import com.todaysoft.lims.system.model.vo.Document;
import com.todaysoft.lims.system.model.vo.DocumentKnowledge;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.disease.Disease;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.service.IDiseaseGeneService;
import com.todaysoft.lims.system.service.IDocumentService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/document")
public class DocumentController extends BaseController
{
    
    @Autowired
    private IDocumentService service;
    
    @Autowired
    private IDiseaseGeneService diseaseService;
    
    @RequestMapping(value = "/paging.do")
    public String paging(DocumentSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<Document> pagination = service.paging(searcher, pageNo, 10);
        /* Pagination<Document> pags = new Pagination<Document>();
         BeanUtils.copyProperties(pagination, pags, "records");
         List<Document> list = Lists.newArrayList();
         for (Document document : pagination.getRecords())
         {
             list.add(addFields(document));
         }
         pags.setRecords(list);*/
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "document/document_list";
    }
    
    @RequestMapping(value = "/form.do")
    public String create(String id, ModelMap model)
    {
        String flag = "新增文献报道";
        
        Document document = new Document();
        if (StringUtils.isNotEmpty(id))
        {
            document = service.getDocumentById(id);
            flag = "修改文献报道";
            List<DocumentKnowledge> knowledge = document.getDocumentKnowledge();
            if (Collections3.isNotEmpty(knowledge))
            {
                for (DocumentKnowledge d : knowledge)
                {
                    if (StringUtils.isEmpty(d.getDiseaseId()))
                    {
                        if (StringUtils.isEmpty(d.getDiseaseOmim()))
                        {
                            d.setDiseaseEnName("");
                            
                        }
                        else
                        {
                            d.setDiseaseEnName(d.getDiseaseOmim());
                        }
                        d.setDiseaseOmim("");
                        
                    }
                    else
                    {
                        d.setDiseaseEnName("");
                    }
                }
            }
            
            model.addAttribute("knowledgeList", JSONObject.toJSONString(knowledge));
            
        }
        model.addAttribute("document", document);
        model.addAttribute("flag", flag);
        return "document/document_form";
    }
    
    @RequestMapping(value = "/create.do")
    public String create(Document request)
    {
        service.create(request);
        
        return "redirect:/document/paging.do";
    }
    
    /**
     * 修改文献库
     * @param request
     * @return
     */
    @RequestMapping(value = "/modify.do")
    public String modify(Document request)
    {
        
        request.setDocumentKnowledge(JSON.parseArray(request.getKnowledge() + "", DocumentKnowledge.class));
        if (StringUtils.isNotEmpty(request.getId()))
        {
            service.modify(request);
        }
        else
        {
            service.createDocument(request);
        }
        return "redirect:/document/paging.do";
    }
    
    @RequestMapping(value = "/delete.do")
    public String delete(String id)
    {
        service.delete(id);
        return "redirect:/document/paging.do";
    }
    
    @RequestMapping(value = "/getDocument")
    @ResponseBody
    public Document getDocumentById(String id)
    {
        return service.getDocumentById(id);
    }
    
    @ResponseBody
    @RequestMapping(value = "/validate.do")
    public boolean checkedName(DocumentSearcher searcher)
    {
        return service.checkedName(searcher);
    }
    
    @RequestMapping(value = "/getDocuments.do")
    public List<Document> getDocuments(DocumentSearcher searcher)
    {
        return service.getDocumentList(searcher);
    }
    
    @RequestMapping(value = "/getDocumentSelected.do")
    @ResponseBody
    public List<Document> getDocumentSelected(DocumentSearcher searcher)
    {
        
        return service.getDocumentList(searcher);
    }
    
    @RequestMapping(value = "/view.do")
    public String view(String id, ModelMap model)
    {
        Document document = service.getDocumentById(id);
        model.addAttribute("document", addFields(document));
        return "document/document_view";
    }
    
    public Document addFields(Document document)
    {
        DocumentSearcher s = new DocumentSearcher();
        s.setId(document.getId());
        List<String> diseaseids = service.getDDByDocumentId(s);
        List<String> geneIds = service.getDGByDocumentId(s);
        List<Disease> diseases = Lists.newArrayList();
        if (Collections3.isNotEmpty(diseaseids))
        {
            for (String diseaseId : diseaseids)
            {
                if (StringUtils.isNotEmpty(diseaseId) && StringUtils.isNotEmpty(diseaseService.getDiseaseById(diseaseId)))
                {
                    diseases.add(diseaseService.getDiseaseById(diseaseId));
                }
                
            }
        }
        List<Gene> genes = Lists.newArrayList();
        if (Collections3.isNotEmpty(geneIds))
        {
            for (String geneId : geneIds)
            {
                if (StringUtils.isNotEmpty(geneId) && StringUtils.isNotEmpty(diseaseService.getGeneById(geneId)))
                {
                    genes.add(diseaseService.getGeneById(geneId));
                }
            }
        }
        // document.setDiseaseList(diseases);
        //document.setGeneList(genes);
        return document;
    }
    
    @RequestMapping("/upload.do")
    public String upload(@RequestParam MultipartFile uploadData)
    {
        service.upload(uploadData);
        return "redirect:/document/paging.do";
    }
}
