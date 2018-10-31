package com.todaysoft.lims.system.modules.bcm.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.modules.bcm.service.IGeneService;
import com.todaysoft.lims.system.modules.bcm.service.request.GenePagingRequest;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/gene")
public class GeneController extends BaseController
{
    @Autowired
    private IGeneService service;
    
    @RequestMapping("/list.do")
    @ResponseBody
    public List<Gene> list(GenePagingRequest searcher,
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Pagination<Gene> pagination = service.paging(searcher, pageNo, 10);
        List<Gene> geneList = pagination.getRecords();
        for (Gene g : geneList)
        {
            g.setName(g.getSymbol());
        }
        return geneList;
    }
}
