package com.todaysoft.lims.biology.action;

import com.todaysoft.lims.biology.model.Pagination;
import com.todaysoft.lims.biology.model.entity.ClaimTemplate;
import com.todaysoft.lims.biology.model.entity.FilterItems;
import com.todaysoft.lims.biology.model.request.ClaimTemplateRequest;
import com.todaysoft.lims.biology.model.request.FilterItemsRequest;
import com.todaysoft.lims.biology.model.response.ClaimTemplateModel;
import com.todaysoft.lims.biology.service.IClaimTemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/biology/claimTemplate")
public class ClaimTemplateController
{
    @Autowired
    private IClaimTemplateService service;
    
    @RequestMapping("/paging")
    public Pagination<ClaimTemplate> pager(@RequestBody ClaimTemplateRequest request)
    {
        return service.pager(request);
        
    }
    
    @RequestMapping("/getItemList")
    public List<FilterItems> getItemList(@RequestBody FilterItemsRequest request)
    {
        FilterItems record = new FilterItems();
        BeanUtils.copyProperties(request, record);
        return service.getItemList(record);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody ClaimTemplateRequest request)
    {
        return service.validate(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody ClaimTemplateRequest request)
    {
        service.create(request);
    }
    
    @RequestMapping("/saveItem")
    public void saveItem(@RequestBody FilterItemsRequest request)
    {
        
        service.saveItem(request);
    }
    
    @RequestMapping("/getByIdForView")
    public ClaimTemplateModel getByIdForView(@RequestBody ClaimTemplateRequest request)
    {
        return service.getByIdForView(request.getId());
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody ClaimTemplateRequest request)
    {
        service.delete(request);
    }
    
    @RequestMapping("/setPriFlag")
    public void setPriFlag(@RequestBody ClaimTemplateRequest request)
    {
        service.setPriFlag(request);
    }
    
    @RequestMapping("/getById")
    public ClaimTemplateModel getById(@RequestBody ClaimTemplateRequest request)
    {
        return service.getById(request.getId());
    }
    
    @RequestMapping("/update")
    public void update(@RequestBody ClaimTemplateRequest request)
    {
        service.update(request);
    }

   //获取分析要求列表
    @RequestMapping(value = "/claimTemplateList", method = RequestMethod.GET)
    public List<ClaimTemplate> claimTemplateList()
    {
        return service.claimTemplateList();
    }


}
