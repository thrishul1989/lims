package com.todaysoft.lims.system.modules.bsm.mvc;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.system.model.form.PrimerFormRequest;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Primer;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethodSearcher;
import com.todaysoft.lims.system.modules.bcm.service.ITestingMethodService;
import com.todaysoft.lims.system.modules.bsm.model.PrimerExcelListRequest;
import com.todaysoft.lims.system.modules.bsm.service.IPrimerService;
import com.todaysoft.lims.system.modules.bsm.service.request.PrimerPagingRequest;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/primer")
public class PrimerController extends BaseController
{
    @Autowired
    private IPrimerService primerService;
    
    @Autowired
    private ITestingMethodService testingMethodservice;
    
    @RequestMapping("/list.do")
    public String tansToPrimer()
    {
        return "redirect:/primer/primerList.do";
    }
    
    @RequestMapping("/primerList.do")
    public String page(PrimerPagingRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<Primer> pagination = primerService.paging(searcher, pageNo, 10);
        TestingMethodSearcher searcherT = new TestingMethodSearcher();
        List<TestingMethod> tmList = testingMethodservice.list(searcherT);
        model.addAttribute("testingMethodList", tmList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "primer/primer_list";
        
    }
    
    //增加，修改判断
    @RequestMapping(value = "/primerForm.do")
    public String createPage(String id, ModelMap model, TestingMethodSearcher searcher)
    {
        Primer data = new Primer();
        
        if (id != null)
        {
            data = primerService.getPrimersById(id);
        }
        
        List<TestingMethod> tmList = testingMethodservice.list(searcher);
        model.addAttribute("primer", data).addAttribute("testingMethodList", tmList);
        return "primer/primer_form";
    }
    
    //修改
    @RequestMapping(value = "/modify.do")
    public String modify(PrimerFormRequest request, ModelMap model, HttpSession session)
    {
        primerService.modify(request);
        return redirectList(model, session, "redirect:/primer/primerList.do");
    }
    
    //增加
    @RequestMapping(value = "/create.do")
    public String create(PrimerFormRequest request, ModelMap model, HttpSession session)
    {
        primerService.create(request);
        return redirectList(model, session, "redirect:/primer/list.do");
    }
    
    //增加excel
    @RequestMapping(value = "/createByExcel.do")
    public String createByExcel(PrimerExcelListRequest request, ModelMap model, HttpSession session)
    {
        List<Primer> primerList = request.getPrimerList();
        if (!Collections3.isEmpty(primerList))
        {
            //去除excel中的逗号
            for (Primer p : primerList)
            {
                setNoCom(p);
            }
        }
        primerService.excelDataInsert(request);
        return redirectList(model, session, "redirect:/primer/list.do");
    }
    
    //属性去逗号，重新set一遍
    private void setNoCom(Primer p)
    {
        if (!StringUtils.isEmpty(p.getApplicationType()))
        {
            p.setApplicationType(deleteCom(p.getApplicationType()));
        }
        if (!StringUtils.isEmpty(p.getChromosomeLocation()))
        {
            p.setChromosomeLocation(deleteCom(p.getChromosomeLocation()));
        }
        if (!StringUtils.isEmpty(p.getChromosomeNumber()))
        {
            p.setChromosomeNumber(deleteCom(p.getChromosomeNumber()));
        }
        if (!StringUtils.isEmpty(p.getCodingExon()))
        {
            p.setCodingExon(deleteCom(p.getCodingExon()));
        }
        if (!StringUtils.isEmpty(p.getForwardPrimerName()))
        {
            p.setForwardPrimerName(deleteCom(p.getForwardPrimerName()));
        }
        if (!StringUtils.isEmpty(p.getForwardPrimerSequence()))
        {
            p.setForwardPrimerSequence(deleteCom(p.getForwardPrimerSequence()));
        }
        if (!StringUtils.isEmpty(p.getGene()))
        {
            p.setGene(deleteCom(p.getGene()));
        }
        if (!StringUtils.isEmpty(p.getReversePrimerName()))
        {
            p.setReversePrimerName(deleteCom(p.getReversePrimerName()));
        }
        if (!StringUtils.isEmpty(p.getReversePrimerSequence()))
        {
            p.setReversePrimerSequence(deleteCom(p.getReversePrimerSequence()));
        }
        if (!StringUtils.isEmpty(p.getProductCode()))
        {
            p.setProductCode(deleteCom(p.getProductCode()));
        }
    }
    
    //去除字符串中逗号
    private String deleteCom(String str)
    {
        String newStr = str.replace(",", "");
        return newStr;
    }
    
    //删除
    @RequestMapping(value = "/delete.do")
    public String delete(String id, ModelMap model, HttpSession session)
    {
        primerService.delete(id);
        return redirectList(model, session, "redirect:/primer/primerList.do");
    }
    
    //查看
    @RequestMapping(value = "/view.do")
    public String view(String id, ModelMap model, TestingMethodSearcher searcher)
    {
        
        Primer data = new Primer();
        data = primerService.getPrimersById(id);
        List<TestingMethod> tmList = testingMethodservice.list(searcher);
        model.addAttribute("testingMethodList", tmList);
        model.addAttribute("primer", data);
        return "primer/primer_view";
    }
    
    @ResponseBody
    @RequestMapping("/checkedPrimerNo")
    public boolean checkedPrimerNo(String primerNo)
    {
        return primerService.checkedPrimerNo(primerNo);
    }
    
    @RequestMapping(value = "/primer_design.do")
    public String primerDesign()
    {
        return "primer/primer_design";
    }
    
    @ResponseBody
    @RequestMapping(value = "/uploadPrimerDesign")
    public List<List<String>> uploadPrimerDesign(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        return primerService.analysisFile(uploadData);
    }
    
    @RequestMapping(value = "/validata")
    @ResponseBody
    public List<String> validate(String content, PrimerPagingRequest request)
    {
        List<Primer> pList = JSON.parseArray(content, Primer.class);
        List<String> sList = compareList(pList);
        return sList;
        
    }
    
    private List<String> compareList(List<Primer> pList)
    {
        //重复性验证
        List<String> flag = new ArrayList<String>();
        for (int i = 0; i < pList.size(); i++)
        {
            TestingMethod method = new TestingMethod();
            method.setName(pList.get(i).getApplicationType());
            TestingMethod t = testingMethodservice.getByName(method);
            pList.get(i).setApplicationType(t.getId());
            List<Primer> primers = primerService.selectByProperties(pList.get(i));
            if (!Collections3.isEmpty(primers))
            {
                flag.add(i + "");
            }
        }
        //正向引物和反向引物名是否存在        
        return flag;
        
    }
    
    @ResponseBody
    @RequestMapping("/downloadPrimer")
    public String downloadCode(String id)
    {
        String[] ids = id.split(",");
        List<Primer> primers = new ArrayList<Primer>();

        TestingMethodSearcher searcherT = new TestingMethodSearcher();
        List<TestingMethod> tmList = testingMethodservice.list(searcherT);

        for(String sheetId : ids)
        {
            Primer data = primerService.getPrimersById(sheetId);

            if (StringUtils.isNotEmpty(data.getApplicationType()))
            {
                for (TestingMethod tm : tmList)
                {
                    if (tm.getId().equals(data.getApplicationType()))
                    {
                        data.setApplicationType(tm.getName());
                    }
                }
            }
            primers.add(data);
        }
        
        InputStream is = Primer.class.getResourceAsStream("/taskTemplate/primer.xlsx");
        return primerService.download(is,primers);
    }
    
    @RequestMapping(value = "/batchDelete.do")
    public String batchDelete(PrimerPagingRequest request, ModelMap model, HttpSession session)
    {
        String[] ids = request.getIds().split(",");
        for(String id : ids)
        {
            primerService.delete(id);
        }
        return redirectList(model, session, "redirect:/primer/primerList.do");
    } 
}
