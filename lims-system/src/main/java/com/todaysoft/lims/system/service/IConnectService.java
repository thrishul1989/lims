package com.todaysoft.lims.system.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.model.searcher.ConnectSearcher;
import com.todaysoft.lims.system.model.vo.Connect;
import com.todaysoft.lims.system.model.vo.Pagination;

public interface IConnectService
{
    
    void create(Connect request);
    
    void modify(Connect request);
    
    void delete(String id);
    
    Pagination<Connect> paging(ConnectSearcher searcher, int pageNo, int i);
    
    Connect getConnectById(String id);
    
    boolean checkedconnectNum(ConnectSearcher connect);
    
    List<Connect> getConnectListById(String ids);
    
    List<Connect> getConnectList(ConnectSearcher searcher);
    
    Integer upload(MultipartFile contractFile);
    
    List<Connect> ConnectListForWkcreate(ConnectSearcher searcher);
    
    void downloadData(HttpServletResponse response, InputStream inputStream);
    
    void writer(String path, String fileName, String fileType, List<Connect> connects, String[] titles)
        throws Exception;
    
    void renderMergedOutputModel(HttpServletRequest request, HttpServletResponse response, String rootPath)
        throws Exception;
}
