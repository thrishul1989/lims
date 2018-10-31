package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.searcher.DocumentSearcher;
import com.todaysoft.lims.system.model.vo.Document;
import com.todaysoft.lims.system.model.vo.DocumentKnowledge;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.disease.Disease;
import com.todaysoft.lims.system.model.vo.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.modules.smm.model.Dict;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.service.IDiseaseGeneService;
import com.todaysoft.lims.system.service.IDocumentService;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class DocumenetService extends RestService implements IDocumentService
{
    @Autowired
    private IDictService dictservice;
    
    @Autowired
    private IDiseaseGeneService diseaseservice;
    
    @Override
    public void create(Document request)
    {
        
        template.postForObject(GatewayService.getServiceUrl("/bcm/document/create.do"), request, Integer.class);
    }
    
    @Override
    public void modify(Document request)
    {
        
        template.postForObject(GatewayService.getServiceUrl("/bcm/document/modify.do"), request, Boolean.class);
    }
    
    @Override
    public void delete(String id)
    {
        
        template.delete(GatewayService.getServiceUrl("/bcm/document/delete/{id}"), Collections.singletonMap("id", id));
    }
    
    @Override
    public Pagination<Document> paging(DocumentSearcher searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bcm/document/list.do");
        ResponseEntity<Pagination<Document>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DocumentSearcher>(searcher), new ParameterizedTypeReference<Pagination<Document>>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public Document getDocumentById(String id)
    {
        
        return template.getForObject(GatewayService.getServiceUrl("/bcm/document/getDocument/{id}"), Document.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public boolean checkedName(DocumentSearcher connect)
    {
        
        return template.postForObject(GatewayService.getServiceUrl("/bcm/document/validate.do"), connect, boolean.class);
    }
    
    @Override
    public List<Document> getDocumentList(DocumentSearcher searcher)
    {
        
        String url = GatewayService.getServiceUrl("/bcm/document/getdocuments");
        ResponseEntity<List<Document>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DocumentSearcher>(searcher), new ParameterizedTypeReference<List<Document>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<String> getDDByDocumentId(DocumentSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bcm/document/getDDByDocumentId");
        ResponseEntity<List<String>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DocumentSearcher>(searcher), new ParameterizedTypeReference<List<String>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<String> getDGByDocumentId(DocumentSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bcm/document/getDGByDocumentId");
        ResponseEntity<List<String>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DocumentSearcher>(searcher), new ParameterizedTypeReference<List<String>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<String> getDocumentByDisease(String id)
    {
        DocumentSearcher searcher = new DocumentSearcher();
        searcher.setId(id);
        String url = GatewayService.getServiceUrl("/bcm/document/getDocumentByDisease");
        ResponseEntity<List<String>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DocumentSearcher>(searcher), new ParameterizedTypeReference<List<String>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<String> getDocumentByGene(String id)
    {
        DocumentSearcher searcher = new DocumentSearcher();
        searcher.setId(id);
        String url = GatewayService.getServiceUrl("/bcm/document/getDocumentByGene");
        ResponseEntity<List<String>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DocumentSearcher>(searcher), new ParameterizedTypeReference<List<String>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void upload(MultipartFile uploadData)
    {
        
        try
        {
            ImportExcel ei = new ImportExcel(uploadData, 0, 0);
            List<Document> documents = ei.getDataList(Document.class);
            for (Document document : documents)
            {
                
                DocumentKnowledge documentknowledge = new DocumentKnowledge();
                BeanUtils.copyProperties(document, documentknowledge);
                Dict d = dictservice.getDictByText("VFS", document.getVfs());
                if (null != d)
                {
                    documentknowledge.setVfs(d.getValue());
                }
                Gene gene = diseaseservice.getGeneByCode(document.getGeneOmim());
                if (null != gene)
                {
                    documentknowledge.setGeneId(gene.getId());
                }
                DiseaseGeneFormRequest request = new DiseaseGeneFormRequest();
                request.setName(document.getDiseaseOmim());
                Disease disease = diseaseservice.getDiseaseByENName(request);
                if (null != disease)
                {
                    documentknowledge.setDiseaseId(disease.getId());
                }
                List<DocumentKnowledge> dks = Lists.newArrayList();
                dks.add(documentknowledge);
                document.setDocumentKnowledge(dks);
                create(document);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void createDocument(Document request)
    {
        template.postForObject(GatewayService.getServiceUrl("/bcm/document/createDocument.do"), request, Integer.class);
    }
}
