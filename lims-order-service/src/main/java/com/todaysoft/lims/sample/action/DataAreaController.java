package com.todaysoft.lims.sample.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.sample.entity.DataArea;
import com.todaysoft.lims.sample.service.IAreaService;

@RestController
@RequestMapping("/bmm/dataarea")
public class DataAreaController
{
    @Autowired
    private IAreaService areaService;
    
    /**
     * 根据id获取订单
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}")
    public DataArea get(@PathVariable Integer id)
    {
        return areaService.get(id);
    }
    
    @RequestMapping(value = "/findRoots")
    private List<DataArea> findRoots()
    {
        return areaService.findRoots();
    }
    
    @RequestMapping(value = "/findByParentId")
    private List<DataArea> findByParentId(@RequestBody DataArea request)
    {
        return areaService.findByParentId(request);
    }
    
}
