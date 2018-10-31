package com.todaysoft.lims.product.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.dao.searcher.DocumentSearcher;
import com.todaysoft.lims.product.entity.document.Document;
import com.todaysoft.lims.product.entity.document.DocumentKnowledge;
import com.todaysoft.lims.product.model.request.DocumentRequest;
import com.todaysoft.lims.product.service.IDocumentService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DocumentService implements IDocumentService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    @Transactional
    public void create(Document request)
    {
        request.setCreateTime(new Date());
        request.setDeleted(false);
        baseDaoSupport.insert(request);
        DocumentKnowledge dk = Collections3.isNotEmpty(request.getDocumentKnowledge()) ? request.getDocumentKnowledge().get(0) : null;
        request.getDocumentKnowledge().clear();
        dk.setDocument(request);
        baseDaoSupport.insert(dk);
        // baseDaoSupport.insert(request);
        //往桥表DocumentDisease 插数据
        //  String diseaseIds = request.getDiseases();
        //insertDocumentDisease(diseaseIds, request.getId());
        
        //往桥表DocumentGENE 插数据
        // String geneIds = request.getGenes();
        //insertDocumentGene(geneIds, request.getId());
    }
    
    @Override
    @Transactional
    public void createDocument(Document request)
    {
        request.setCreateTime(new Date());
        request.setDeleted(false);
        baseDaoSupport.insert(request);
        try
        {
            for (DocumentKnowledge k : request.getDocumentKnowledge())
            {
                DocumentKnowledge p = new DocumentKnowledge();
                BeanUtils.copyProperties(k, p, new String[] {"document"});
                if (StringUtils.isEmpty(k.getDiseaseId()))
                {
                    p.setDiseaseOmim(p.getDiseaseEnName());
                }
                p.setDocument(request);
                baseDaoSupport.insert(p);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    @Transactional
    public void modify(Document request)
    {
        
        request.setCreateTime(new Date());
        request.setDeleted(false);
        baseDaoSupport.update(request);
        try
        {
            baseDaoSupport.executeHql("delete DocumentKnowledge s where s.document.id = ?", new Object[] {request.getId()});
            for (DocumentKnowledge k : request.getDocumentKnowledge())
            {
                DocumentKnowledge p = new DocumentKnowledge();
                BeanUtils.copyProperties(k, p, new String[] {"document"});
                p.setDocument(request);
                
                if (StringUtils.isEmpty(k.getDiseaseId()))
                {
                    p.setDiseaseOmim(p.getDiseaseEnName());
                }
                baseDaoSupport.insert(p);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        Document d = getDocumentById(id);
        if (StringUtils.isNotEmpty(d))
        {
            d.setDeleted(true);
            d.setDeleteTime(new Date());
            baseDaoSupport.update(d);
            
            /*baseDaoSupport.executeHql("delete DocumentGene d where d.documentId = ?", new Object[] {d.getId()});
            baseDaoSupport.executeHql("delete DocumentDisease d where d.documentId = ?", new Object[] {d.getId()});*/
        }
    }
    
    @Override
    public Pagination<Document> paging(DocumentRequest request)
    {
        DocumentSearcher searcher = new DocumentSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<Document> p = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), Document.class);
        return p;
    }
    
    @Override
    public Document getDocumentById(String id)
    {
        return baseDaoSupport.get(Document.class, id);
    }
    
    @Override
    public boolean checkedName(DocumentRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Document.class, request, "code")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public List<Document> getDocumentList(DocumentRequest request)
    {
        
        DocumentSearcher searcher = new DocumentSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher);
    }
    
    /*public void insertDocumentGene(String ids, String entityId)
    {
        for (String geneId : ids.split(","))
        {
            DocumentGene dg = new DocumentGene();
            dg.setDocumentId(entityId);
            dg.setGeneId(geneId);
            baseDaoSupport.insert(dg);
        }
    }
    
    public void insertDocumentDisease(String ids, String entityId)
    {
        for (String diseaseId : ids.split(","))
        {
            DocumentDisease dg = new DocumentDisease();
            dg.setDocumentId(entityId);
            dg.setDiseaseId(diseaseId);
            baseDaoSupport.insert(dg);
        }
    }*/
    
    @Override
    public List<String> getDDByDocumentId(DocumentRequest searcher)
    {
        String hql = "SELECT dd.diseaseId FROM DocumentDisease dd WHERE dd.documentId = :documentId";
        return baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"documentId"}, new Object[] {searcher.getId()});
    }
    
    @Override
    public List<String> getDGByDocumentId(DocumentRequest searcher)
    {
        String hql = "SELECT dg.geneId FROM DocumentGene dg WHERE dg.documentId = :documentId";
        return baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"documentId"}, new Object[] {searcher.getId()});
    }
    
    @Override
    public List<String> getDocumentByDisease(DocumentRequest request)
    {
        String hql = "SELECT distinct(d.title) FROM Document d left join d.documentKnowledge dd WHERE  dd.diseaseId =:diseaseId";
        List<String> result = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"diseaseId"}, new Object[] {request.getId()});
        return result;
    }
    
    @Override
    public List<String> getDocumentByGene(DocumentRequest request)
    {
        String hql = "SELECT distinct(d.title) FROM Document d left join d.documentKnowledge dd WHERE  dd.geneId =:geneId";
        List<String> result = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"geneId"}, new Object[] {request.getId()});
        return result;
    }
    
}
