package com.todaysoft.lims.testing.completeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.completeTasks.model.ngs.BiologyAnalySheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.CDNAQcSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.DNAQcSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.LibraryCaptureSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.LibraryQcSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.QtSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.RQTSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.SequencingSheet;
import com.todaysoft.lims.testing.completeTasks.model.ngs.TechnicalAnalySheet;
import com.todaysoft.lims.testing.completeTasks.service.INGSCompleteService;

@RestController
@RequestMapping("/bpm/completeTasks/ngs")
public class NGSCompleteAction
{
    @Autowired
    private INGSCompleteService ngsService;
    
    @RequestMapping(value = "/dnaqc/sheet/{id}", method = RequestMethod.GET)
    public DNAQcSheet getDNAQcSheet(@PathVariable String id)
    {
        return ngsService.getDNAQcSheet(id);
    }
    
    @RequestMapping(value = "/cdnaqc/sheet/{id}", method = RequestMethod.GET)
    public CDNAQcSheet getCDNAQcSheet(@PathVariable String id)
    {
        return ngsService.getCDNAQcSheet(id);
    }
    
    @RequestMapping(value = "/libqc/sheet/{id}", method = RequestMethod.GET)
    public LibraryQcSheet getLibraryQcSheet(@PathVariable String id)
    {
        return ngsService.getLibraryQcSheet(id);
    }
    
    @RequestMapping(value = "/libcap/sheet/{id}", method = RequestMethod.GET)
    public LibraryCaptureSheet getLibraryCaptureSheet(@PathVariable String id)
    {
        return ngsService.getLibCapSheet(id);
    }
    
    @RequestMapping(value = "/rqt/sheet/{id}", method = RequestMethod.GET)
    public RQTSheet getRQTSheet(@PathVariable String id)
    {
        return ngsService.getRQTSheet(id);
    }
    
    @RequestMapping(value = "/qt/sheet/{id}", method = RequestMethod.GET)
    public QtSheet getQtSheet(@PathVariable String id)
    {
        return ngsService.getQtSheet(id);
    }
    
    @RequestMapping(value = "/sequencing/sheet/{id}", method = RequestMethod.GET)
    public SequencingSheet getSequencingSheet(@PathVariable String id)
    {
        return ngsService.getSequencingSheet(id);
    }
    
    
    @RequestMapping(value = "/technical-analy/sheet/{id}", method = RequestMethod.GET)
    public TechnicalAnalySheet getTechnicalAnalySheet(@PathVariable String id)
    {
        return ngsService.getTechnicalAnalySheet(id);
    }
    
    
    
    @RequestMapping(value = "/biology-analy/sheet/{id}", method = RequestMethod.GET)
    public BiologyAnalySheet getBiologyAnalySheet(@PathVariable String id)
    {
        return ngsService.getBiologyAnalySheet(id);
    }
}
