package com.todaysoft.lims.kbm.es.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.kbm.es.model.Gene;
import com.todaysoft.lims.kbm.es.service.IGeneService;

@RestController
@RequestMapping("/es/gene")
public class GeneController
{
    @Autowired
    private IGeneService service;
    
    @RequestMapping("/index")
    public void index(@RequestBody Gene gene)
    {
        service.index(gene);
    }
    
    @RequestMapping("/remove/{id}")
    public boolean remove(@PathVariable String id)
    {
        return service.remove(id);
    }
    
    @RequestMapping("/search")
    public List<Gene> search(String keywords, Integer offset, Integer limit)
    {
        return service.search(keywords, null == offset ? 0 : offset.intValue(), null == limit ? 20 : limit.intValue());
    }
}
