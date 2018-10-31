package com.todaysoft.lims.biology.service;

import com.todaysoft.lims.biology.model.Pagination;
import com.todaysoft.lims.biology.model.entity.ClaimTemplate;
import com.todaysoft.lims.biology.model.entity.ClaimTemplateColumn;
import com.todaysoft.lims.biology.model.entity.FilterItems;
import com.todaysoft.lims.biology.model.request.ClaimTemplateRequest;
import com.todaysoft.lims.biology.model.request.FilterItemsRequest;
import com.todaysoft.lims.biology.model.response.ClaimTemplateModel;

import java.util.List;

public interface IClaimTemplateService
{
    
    Pagination<ClaimTemplate> pager(ClaimTemplateRequest request);
    
    void create(ClaimTemplateRequest record);
    
    ClaimTemplate get(String id);
    
    List<ClaimTemplateColumn> getColumn(String templateId);
    
    /* void createColumn(ClaimTemplateColumn record);
    
    void setPriFlag(String id);
    
    List<ClaimTemplate> selectForPriFlag();*/
    
    List<FilterItems> getItemList(FilterItems record);
    
    boolean validate(ClaimTemplateRequest request);
    
    void saveItem(FilterItemsRequest request);
    
    ClaimTemplateModel getByIdForView(String id);
    
    void delete(ClaimTemplateRequest record);
    
    void setPriFlag(ClaimTemplateRequest request);
    
    ClaimTemplateModel getById(String id);
    
    void update(ClaimTemplateRequest request);

    List<ClaimTemplate> claimTemplateList();
}
