package com.todaysoft.lims.testing.completeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.completeTasks.model.sanger.DataPcrSheet;
import com.todaysoft.lims.testing.completeTasks.model.sanger.FirstPcrSheet;
import com.todaysoft.lims.testing.completeTasks.model.sanger.PrimerDesignSheet;
import com.todaysoft.lims.testing.completeTasks.service.ISangerCompleteService;

@RestController
@RequestMapping("/bpm/completeTasks/sanger")
public class SangerCompleteAction
{
    @Autowired
    private ISangerCompleteService sangerService;
    
    @RequestMapping(value = "/primerdesign/sheet/{id}", method = RequestMethod.GET)
    public PrimerDesignSheet getPrimerDesignSheet(@PathVariable String id)
    {
        return sangerService.getPrimerDesignSheet(id);
    }
    
    @RequestMapping(value = "/firstpcr/sheet/{id}", method = RequestMethod.GET)
    public FirstPcrSheet getFirstPcrSheet(@PathVariable String id)
    {
        return sangerService.getFirstPcrSheet(id);
    }
    
    @RequestMapping(value = "/datapcr/sheet/{id}", method = RequestMethod.GET)
    public DataPcrSheet getDataPcrSheet(@PathVariable String id)
    {
        return sangerService.getDataPcrSheet(id);
    }
}
