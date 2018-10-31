package com.todaysoft.lims.product.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.document.Document;
import com.todaysoft.lims.product.model.request.DocumentRequest;

public interface IDocumentService
{
    void create(Document request);
    
    void modify(Document request);
    
    void delete(String id);
    
    Pagination<Document> paging(DocumentRequest searcher);
    
    Document getDocumentById(String id);
    
    boolean checkedName(DocumentRequest connect);
    
    List<Document> getDocumentList(DocumentRequest searcher);
    
    List<String> getDDByDocumentId(DocumentRequest searcher);
    
    List<String> getDGByDocumentId(DocumentRequest searcher);
    
    List<String> getDocumentByDisease(DocumentRequest id);
    
    List<String> getDocumentByGene(DocumentRequest request);
    
    void createDocument(Document request);
    
}
