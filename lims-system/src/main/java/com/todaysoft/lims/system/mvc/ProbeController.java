package com.todaysoft.lims.system.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Probe;
import com.todaysoft.lims.system.model.vo.ProbeGene;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.service.ITestingMethodService;
import com.todaysoft.lims.system.service.IProbeService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/probe")
public class ProbeController extends BaseController
{
    
    @Autowired
    private IProbeService iProbeService;
    
    @Autowired
    private ITestingMethodService testingMethodService;
    
    /**
     * 探针数据
     * */
    @RequestMapping(value = "/list.do")
    public String getDeviceList(ProbeSeacher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Pagination<Probe> pagination = iProbeService.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "probe/probe_list";
        
    }
    
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Probe> getList(ProbeSeacher searcher, ModelMap model)
    {
        List<Probe> list = iProbeService.list(searcher);
        return list;
    }
    
    @RequestMapping(value = "/getProbe")
    @ResponseBody
    public Probe getProbe(String id, ModelMap model)
    {
        
        return iProbeService.getProbe(id);
    }
    
    /**
     * 新增探针
     * */
    @RequestMapping(value = "/createProbe.do", method = RequestMethod.GET)
    @FormInputView
    public String createPage(ModelMap model)
    {
        Probe data = new Probe();
        model.addAttribute("probe", data);
        model.addAttribute("flag", "新增");
        
        return "probe/probe_form";
    }
    
    /**
     * 修改探针
     * */
    @RequestMapping(value = "/probeModify.do", method = RequestMethod.GET)
    @FormInputView
    public String modifyPage(String id, ModelMap model)
    {
        Probe data = iProbeService.getProbe(id);
        List<Gene> geneList = new ArrayList<Gene>();
        if (data.getProbeGeneList().size() > 0)
        {
            for (ProbeGene probeGene : data.getProbeGeneList())
            {
                Gene gene = probeGene.getGene();
                gene.setName(gene.getSymbol());
                geneList.add(gene);
            }
        }
        //获取检测平台集合        
        List<TestingMethod> testingMethodList = new ArrayList<TestingMethod>();
        String ids = data.getTestingPlatForm();
        String[] testingMethodIds = ids.split(",");
        for (int i = 0; i < testingMethodIds.length; i++)
        {
            TestingMethod t = testingMethodService.get(testingMethodIds[i]);
            testingMethodList.add(t);
        }
        model.addAttribute("testingMethodList", JSONObject.toJSON(testingMethodList).toString()).addAttribute("geneList",
            JSONObject.toJSON(geneList).toString());
        model.addAttribute("probe", data);
        model.addAttribute("flag", "修改");
        return "probe/probe_form";
    }
    
    /**
     * 修改探针
     * 
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/probeModify.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modifyProbe(ProbeSeacher request, ModelMap model, HttpSession session)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            //获取探针编号
            String code = iProbeService.getProbeNext();
            request.setCode(code);
            iProbeService.createProbe(request);
        }
        else
        {
            iProbeService.modify(request);
        }
        
        return redirectList(model, session, "redirect:/probe/list.do");
    }
    
    /**
     * 删除探针
     * 
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/delete.do")
    public String deleteProbe(String id, ModelMap model, HttpSession session)
    {
        iProbeService.deleteProbe(id);
        return redirectList(model, session, "redirect:/probe/list.do");
    }
    
    /**
     * 唯一性教研
     */
    @ResponseBody
    @RequestMapping("/validate.do")
    public boolean validate(ProbeSeacher search)
    {
        return iProbeService.validate(search);
        
    }
    
    /**
     * 获取探针多选框
     */
    @RequestMapping(value = "/getProbeList.do")
    @ResponseBody
    public List<Probe> containerSelect(ProbeSeacher searcher, ModelMap model)
    {
        Pagination<Probe> pagination = iProbeService.paging(searcher, 1, 1000);
        /*model.addAttribute("probeList", pagination.getRecords());*/
        return pagination.getRecords();
    }
    
}
