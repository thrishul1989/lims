package com.todaysoft.lims.system.modules.bpm.bioanalysis.mvc;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.AnnotateUploadHistoryRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.ClaimTemplate;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.ClaimTemplateModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.ClaimTemplateRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.FilterItems;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.FilterItemsRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.UploadAnnotationModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplate;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplateColumn;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplateModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplateRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBioDataTemplateService;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IClaimTemplateService;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.utils.Collections3;

@Controller
@RequestMapping(value = "/claimTemplate")
public class ClaimTemplateController extends BaseController
{
    @Autowired
    private IClaimTemplateService service;
    
    @Autowired
    private IBioDataTemplateService dataTemplateservice;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/list.do")
    public String paging(ClaimTemplateRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        Pagination<ClaimTemplate> pagination = service.searcher(searcher, pageNo, pageSize);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "bpm/biology/claimTemplate/claimTemplate_list";
    }
    
    @RequestMapping("/saveItem.do")
    @ResponseBody
    public void saveItem(FilterItemsRequest request)
    {
        service.saveItem(request);
    }
    
    @RequestMapping("/getItemList.do")
    @ResponseBody
    public List<FilterItems> getItemList(FilterItemsRequest request)
    {
        return service.getItemsList(request);
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    public String createForward(ModelMap model)
    {
        DataTemplateRequest searcher = new DataTemplateRequest();
        List<DataTemplate> dataTemplateList = dataTemplateservice.getDataTemplateList(searcher);
        model.addAttribute("dataTemplateList", dataTemplateList);
        return "bpm/biology/claimTemplate/claimTemplate_form";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public String create(ClaimTemplateRequest searcher, ModelMap model, HttpSession session)
    {
        User user = userService.getUserByToken();
        if (user != null)
        {
            searcher.setCreateId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            searcher.setCreateName(userachive.getArchive().getName());
        }
        searcher.setDelFlag(false);
        service.create(searcher);
        return redirectList(model, session, "redirect:/claimTemplate/list.do");
    }
    
    @ResponseBody
    @RequestMapping(value = "/validate.do", method = RequestMethod.POST)
    public boolean validate(ClaimTemplateRequest request)
    {
        return service.validate(request);
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    public String modifyForward(String id, ModelMap model)
    {
        ClaimTemplateModel data = service.get(id);
        model.addAttribute("data", data);
        DataTemplateRequest searcher = new DataTemplateRequest();
        List<DataTemplate> dataTemplateList = dataTemplateservice.getDataTemplateList(searcher);
        model.addAttribute("dataTemplateList", dataTemplateList);
        DataTemplateModel dataTemplateModel = dataTemplateservice.get(data.getTemplateId());
        List<DataTemplateColumn> dataTemplateColumnList = Lists.newArrayList();
        if (null != dataTemplateModel)
        {
            dataTemplateColumnList = dataTemplateModel.getColumnList();
        }
        model.addAttribute("dataTemplateColumnList", dataTemplateColumnList);
        FilterItemsRequest request = new FilterItemsRequest();
        service.getItemsList(request);
        return "bpm/biology/claimTemplate/claimTemplate_form";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    public String modify(ClaimTemplateRequest request, ModelMap model, HttpSession session)
    {
        service.modify(request);
        return redirectList(model, session, "redirect:/claimTemplate/list.do");
    }
    
    @RequestMapping(value = "/view.do")
    public String view(String id, ModelMap model)
    {
        ClaimTemplateModel data = service.getForView(id);
        model.addAttribute("data", data);
        return "bpm/biology/claimTemplate/claimTemplate_view";
    }
    
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public void delete(String id, ModelMap model)
    {
        service.delete(id);
    }
    
    @RequestMapping(value = "/updatePriFlag.do")
    public String updatePriFlag(String id, ModelMap model, HttpSession session)
    {
        service.updatePriFlag(id);
        return redirectList(model, session, "redirect:/claimTemplate/list.do");
    }
    
    @RequestMapping(value = "/annotationUpload.do")
    public String annotationUpload(ModelMap model, HttpSession session, AnnotateUploadHistoryRequest request)
    {
        model.addAttribute("searcher", request);
        return "bpm/biology/claimTemplate/annotation_upload";
    }
    
    @RequestMapping(value = "/fileUpload.do")
    @ResponseBody
    public List<Map<String, String>> fileUpload(HttpServletRequest request, ModelMap model, HttpSession session)
    {
        
        File file = upload(request, model);
        List<UploadAnnotationModel> list = service.uploadAnnotation(file);
        if (Collections3.isNotEmpty(list))
        {
            List<Map<String, String>> res = service.saveAnnotation(list);
            return res;
        }
        return null;
    }
    
    //分析要求列表
    @RequestMapping("/claimTemplateList")
    @ResponseBody
    public List<ClaimTemplate> claimTemplateLists()
    {
        return service.claimTemplateList();
    }
    
}
