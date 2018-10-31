package com.todaysoft.lims.system.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.todaysoft.lims.system.model.searcher.ProductSearcher;
import com.todaysoft.lims.system.model.vo.InputOutputModel;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.ProductProbe;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.TaskConfig;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bpm.model.internal.ReagentKits;
import com.todaysoft.lims.system.service.adapter.request.ProductCreateRequest;
import com.todaysoft.lims.system.service.adapter.request.ProductListRequest;

public interface IProductService
{
    
    Pagination<Product> paging(ProductSearcher searcher, int pageNo, int pageSize);
    
    Pagination<Product> productSelect(ProductSearcher searcher, int pageNo, int pageSize);
    
    Product getProduct(String id);
    
    boolean unique(String code);
    
    void create(ProductCreateRequest data);
    
    void modify(ProductCreateRequest data);
    
    void enable(ProductCreateRequest data);
    
    void delete(String id);
    
    void insert(ProductCreateRequest data);
    
    List<MetadataSample> getList();
    
    List<Task> getTaskList();
    
    List<InputOutputModel> getDetectionClassfyList();
    
    List<InputOutputModel> getMethodList();
    
    List<MetadataSample> list();
    
    List<InputOutputModel> getAnalysisMethodList();
    
    boolean validate(ProductCreateRequest request);
    
    List<ProductProbe> getProbeList(ProductCreateRequest request);
    
    public File upload(HttpServletRequest request, HttpServletResponse response, ModelMap model);
    
    Map analyticalGeneData(File file);
    
    Map analyticalDiseaseData(File file);
    
    List<ReagentKits> getByTaskAndInput(TaskConfig taskConfig);
    
    List<Product> getProductByName(ProductSearcher searcher);
    
    String download(File zipfile);
    
    List<MetadataSample> getSampleByProductIds(ProductListRequest request);
}
