package com.todaysoft.lims.product.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.document.Document;
import com.todaysoft.lims.product.model.request.DocumentRequest;
import com.todaysoft.lims.product.service.IDocumentService;

@RestController
@RequestMapping("/bcm/document")
public class DocumentController
{
    
    @Autowired
    private IDocumentService service;
    
    @RequestMapping(value = "/list.do")
    public Pagination<Document> paging(@RequestBody DocumentRequest searcher)
    {
        return service.paging(searcher);
    }
    
    @RequestMapping(value = "/create.do")
    public void create(@RequestBody Document request)
    {
        service.create(request);
    }
    
    @RequestMapping(value = "/createDocument.do")
    public void createDocument(@RequestBody Document request)
    {
        service.createDocument(request);
    }
    
    @RequestMapping(value = "/modify.do")
    public void modify(@RequestBody Document request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/delete/{id}")
    public void delete(@PathVariable String id)
    {
        service.delete(id);
    }
    
    @RequestMapping(value = "/getDocument/{id}")
    public Document getDocumentById(@PathVariable String id)
    {
        
        return service.getDocumentById(id);
    }
    
    @RequestMapping(value = "/validate.do")
    public boolean checkedName(@RequestBody DocumentRequest searcher)
    {
        return service.checkedName(searcher);
    }
    
    @RequestMapping(value = "/getDocuments")
    public List<Document> getDocuments(@RequestBody DocumentRequest searcher)
    {
        return service.getDocumentList(searcher);
    }
    
    @RequestMapping(value = "/getDDByDocumentId")
    public List<String> getDDByDocumentId(@RequestBody DocumentRequest searcher)
    {
        return service.getDDByDocumentId(searcher);
    }
    
    @RequestMapping(value = "/getDGByDocumentId")
    public List<String> getDGByDocumentId(@RequestBody DocumentRequest searcher)
    {
        return service.getDGByDocumentId(searcher);
    }
    
    @RequestMapping(value = "/getDocumentByDisease")
    public List<String> getDocumentByDisease(@RequestBody DocumentRequest request)
    {
        return service.getDocumentByDisease(request);
    }
    
    @RequestMapping(value = "/getDocumentByGene")
    public List<String> getDocumentByGene(@RequestBody DocumentRequest request)
    {
        return service.getDocumentByGene(request);
    }
}
