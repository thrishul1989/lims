package com.todaysoft.lims.system.modules.bpm.bioanalysis.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplate;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplateColumn;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplateModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplateRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBioDataTemplateService;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/biAnalysis/dataTemplate")
public class BiologyDataTemplateController extends BaseController
{
    @Autowired
    private IDictService dictService;
    
    @Autowired
    private IBioDataTemplateService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/list.do")
    public String paging(DataTemplateRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        Pagination<DataTemplate> pagination = service.searcher(searcher, pageNo, pageSize);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "bpm/biology/dataTemplate/dataTemplate_list";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    public String createForward(ModelMap model)
    {
        
        return "bpm/biology/dataTemplate/dataTemplate_form";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public String create(DataTemplateRequest searcher, ModelMap model, HttpSession session)
    {
        User user = userService.getUserByToken();
        if (user != null)
        {
            searcher.setCreateId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            searcher.setCreateName(userachive.getArchive().getName());
        }
        searcher.setDelFlag(false);
        searcher.setPriFlag(true);
        service.create(searcher);
        return redirectList(model, session, "redirect:/biAnalysis/dataTemplate/list.do");
    }
    
    @ResponseBody
    @RequestMapping(value = "/validate.do", method = RequestMethod.POST)
    public boolean validate(DataTemplateRequest request)
    {
        return service.validate(request);
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    public String modifyForward(String id, ModelMap model, HttpSession session)
    {
        DataTemplateModel data = service.get(id);
        model.addAttribute("data", data);
        session.setAttribute("dataTemplateColList", data.getColumnList());
        return "bpm/biology/dataTemplate/dataTemplate_form";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    public String modify(DataTemplateRequest request, ModelMap model, HttpSession session)
    {
        @SuppressWarnings("unchecked")
        List<DataTemplateColumn> colList = (List<DataTemplateColumn>)session.getAttribute("dataTemplateColList");
        List<String> deleteIds = getDiffrent(colList, request.getColumnList());
        request.setDeleteIds(deleteIds);
        service.modify(request);
        return redirectList(model, session, "redirect:/biAnalysis/dataTemplate/list.do");
    }
    
    private static List<String> getDiffrent(List<DataTemplateColumn> list1, List<DataTemplateColumn> list2)
    {
        List<String> diff = new ArrayList<String>();
        List<DataTemplateColumn> maxList = list1;
        List<DataTemplateColumn> minList = list2;
        if (list2.size() > list1.size())
        {
            maxList = list2;
            minList = list1;
        }
        Map<String, Integer> map = new HashMap<String, Integer>(maxList.size());
        for (DataTemplateColumn col : maxList)
        {
            if (StringUtils.isNotEmpty(col.getId()))
            {
                map.put(col.getId(), 1);
            }
        }
        for (DataTemplateColumn col : minList)
        {
            if (StringUtils.isNotEmpty(col.getId()))
            {
                if (map.get(col.getId()) != null)
                {
                    map.put(col.getId(), 2);
                    continue;
                }
                diff.add(col.getId());
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            if (entry.getValue() == 1)
            {
                diff.add(entry.getKey());
            }
        }
        return diff;
        
    }
    
    @RequestMapping(value = "/view.do")
    public String view(String id, ModelMap model)
    {
        DataTemplateModel data = service.get(id);
        model.addAttribute("data", data);
        return "/bpm/biology/dataTemplate/dataTemplate_view";
    }
    
    @RequestMapping(value = "/getColumnList.do")
    @ResponseBody
    public List<DataTemplateColumn> getColumnList(String templateId)
    {
        List<DataTemplateColumn> list = Lists.newArrayList();
        DataTemplateModel data = service.get(templateId);
        if (null != data)
        {
            list = data.getColumnList();
        }
        return list;
    }
    
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public void delete(String id, ModelMap model)
    {
        service.delete(id);
    }
}
