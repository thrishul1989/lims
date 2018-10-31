package com.todaysoft.lims.system.modules.bpm.bioanalysis.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.AnnotateUploadHistoryModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.AnnotateUploadHistoryRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.ClaimTemplate;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.ClaimTemplateModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.ClaimTemplateRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.FilterItems;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.FilterItemsRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.UploadAnnotationModel;

public interface IClaimTemplateService
{
    Pagination<ClaimTemplate> searcher(ClaimTemplateRequest searcher, int pageNo, int pageSize);
    
    void saveItem(FilterItemsRequest request);
    
    List<FilterItems> getItemsList(FilterItemsRequest request);
    
    void create(ClaimTemplateRequest request);
    
    boolean validate(ClaimTemplateRequest request);
    
    ClaimTemplateModel get(String id);
    
    void modify(ClaimTemplateRequest request);
    
    ClaimTemplateModel getForView(String id);
    
    void delete(String id);
    
    void updatePriFlag(String id);
    
    Pagination<AnnotateUploadHistoryModel> getAnnotateHistory(AnnotateUploadHistoryRequest request);
    
    List<UploadAnnotationModel> uploadAnnotation(File file);
    
    List<Map<String, String>> saveAnnotation(List<UploadAnnotationModel> list);
    
    List<ClaimTemplate> claimTemplateList();//获取分析要求列表
    //    ClaimTemplate getById(String id);//通过id获取对象
    
}
