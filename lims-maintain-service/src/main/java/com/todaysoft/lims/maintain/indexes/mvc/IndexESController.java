package com.todaysoft.lims.maintain.indexes.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.maintain.commons.Monitor;
import com.todaysoft.lims.maintain.indexes.service.ICoreService;

@RestController
@RequestMapping("/maintain/indexes")
public class IndexESController
{
    @Autowired
    private ICoreService service;
    
    @RequestMapping("/products")
    public void indexForProducts()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                service.indexForProducts();
            }
        }).start();
    }
    
    @RequestMapping("/products/monitor")
    public Monitor monitorForProducts()
    {
        return service.monitorForProducts();
    }
    
    @RequestMapping("/genes")
    public void indexForGenes()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                service.indexForGenes();
            }
        }).start();
    }
    
    @RequestMapping("/genes/monitor")
    public Monitor monitorForGenes()
    {
        return service.monitorForGenes();
    }
    
    @RequestMapping("/diseases")
    public void indexForDiseases()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                service.indexForDiseases();
            }
        }).start();
    }
    
    @RequestMapping("/diseases/monitor")
    public Monitor monitorForDiseases()
    {
        return service.monitorForDiseases();
    }
    
    @RequestMapping("/phenotypes")
    public void indexForPhenotypes()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                service.indexForPhenotypes();
            }
        }).start();
    }
    
    @RequestMapping("/phenotypes/monitor")
    public Monitor monitorForPhenotypes()
    {
        return service.monitorForPhenotypes();
    }
}
