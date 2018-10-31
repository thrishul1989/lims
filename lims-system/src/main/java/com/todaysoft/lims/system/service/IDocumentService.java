package com.todaysoft.lims.system.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.model.searcher.DocumentSearcher;
import com.todaysoft.lims.system.model.vo.Document;
import com.todaysoft.lims.system.model.vo.Pagination;

public interface IDocumentService
{
    
    void create(Document request);
    
    void modify(Document request);
    
    void delete(String id);
    
    Pagination<Document> paging(DocumentSearcher searcher, int pageNo, int pageSize);
    
    Document getDocumentById(String id);
    
    boolean checkedName(DocumentSearcher connect);
    
    List<Document> getDocumentList(DocumentSearcher searcher);
    
    List<String> getDDByDocumentId(DocumentSearcher searcher);
    
    List<String> getDGByDocumentId(DocumentSearcher searcher);
    
    List<String> getDocumentByDisease(String id);
    
    List<String> getDocumentByGene(String id);
    
    void upload(MultipartFile contractFile);
    
    void createDocument(Document request);
    
}
