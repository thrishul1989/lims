package com.todaysoft.lims.system.modules.report.mvc;

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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.report.model.BuiltinVariable;
import com.todaysoft.lims.system.modules.report.model.ContentBookmark;
import com.todaysoft.lims.system.modules.report.model.ContentTableColumn;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.model.DataTemplateColumn;
import com.todaysoft.lims.system.modules.report.model.ReportTemplate;
import com.todaysoft.lims.system.modules.report.service.IBuiltinVariableService;
import com.todaysoft.lims.system.modules.report.service.IDataTemplateService;
import com.todaysoft.lims.system.modules.report.service.IReportTemplateService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/report/reportTemplate")
public class ReportTemplateController extends BaseController
{
    @Autowired
    private IReportTemplateService service;
    
    @Autowired
    private IBuiltinVariableService builtinService;
    
    @Autowired
    private IDataTemplateService dataTemplateService;
    
    @RequestMapping(value = "/list.do")
    public String paging(ReportTemplate searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<ReportTemplate> pagination = service.paging(searcher);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "report/reportTemplate/reportTemplate_list";
    }
    
    @RequestMapping(value = "/view.do")
    public String view(String id, ModelMap model)
    {
        ReportTemplate data = service.get(id);
        model.addAttribute("data", data);
        return "report/reportTemplate/reportTemplate_view";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    @FormInputView
    public String createForward(ModelMap model)
    {
        return "report/reportTemplate/reportTemplate_form";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(ReportTemplate reportTemplate, ModelMap model, HttpSession session)
    {
        List<ContentBookmark> bookmarkList = JSON.parseArray(reportTemplate.getContent(), ContentBookmark.class);
        for (ContentBookmark bm : bookmarkList)
        {
            if ("1".equals(bm.getContentType()) && StringUtils.isNotEmpty(bm.getBuiltinId()))
            {
                BuiltinVariable builtin = builtinService.get(bm.getBuiltinId());
                bm.setBuiltinVariable(builtin);
                bm.setTableColumnList(null);
            }
            else if ("2".equals(bm.getContentType()) && Collections3.isNotEmpty(bm.getTableColumnList()))
            {
                for (ContentTableColumn clolumn : bm.getTableColumnList())
                {
                    if ("1".equals(clolumn.getVariableType()) && StringUtils.isNotEmpty(clolumn.getBuiltinId()))
                    {
                        BuiltinVariable builtin = builtinService.get(clolumn.getBuiltinId());
                        clolumn.setBuiltinVariable(builtin);
                    }
                    else
                    {
                        if ("1".equals(clolumn.getDataType()) && StringUtils.isNotEmpty(clolumn.getDataTemplateColumnId()))
                        {
                            DataTemplateColumn dtc = dataTemplateService.getDataTemplateColumn(clolumn.getDataTemplateColumnId());
                            clolumn.setDataTemplateColumn(dtc);
                        }
                    }
                }
            }
        }
        reportTemplate.setBookmarkList(bookmarkList);
        service.create(reportTemplate);
        return redirectList(model, session, "redirect:/report/reportTemplate/list.do");
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    @FormInputView
    public String modifyForward(String id, ModelMap model)
    {
        ReportTemplate data = service.get(id);
        List<DataTemplate> dataTemplate = Lists.newArrayList();
        dataTemplate.add(data.getTemplate());
        
        Map<String, List<BuiltinVariable>> builtinVariableMap = new HashMap<String, List<BuiltinVariable>>();
        Map<String, List<BuiltinVariable>> tableColumnBuiltinVariableMap = new HashMap<String, List<BuiltinVariable>>();
        for (ContentBookmark cb : data.getBookmarkList())
        {
            if ("1".equals(cb.getContentType()) && cb.getBuiltinVariable() != null)
            {
                List<BuiltinVariable> bvList = Lists.newArrayList();
                bvList.add(cb.getBuiltinVariable());
                builtinVariableMap.put(cb.getId() + cb.getBuiltinVariable().getId(), bvList);
            }
            else if ("2".equals(cb.getContentType()) && Collections3.isNotEmpty(cb.getTableColumnList()))
            {
                for (ContentTableColumn ctc : cb.getTableColumnList())
                {
                    if ("1".equals(ctc.getVariableType()) && ctc.getBuiltinVariable() != null)
                    {
                        List<BuiltinVariable> bvList = Lists.newArrayList();
                        bvList.add(ctc.getBuiltinVariable());
                        tableColumnBuiltinVariableMap.put(cb.getId() + ctc.getId() + ctc.getBuiltinVariable().getId(), bvList);
                    }
                }
            }
        }
        
        DataTemplateColumn searcher = new DataTemplateColumn();
        searcher.setTemplate(data.getTemplate());
        List<DataTemplateColumn> dataTemplateColumnList = dataTemplateService.dataTemplateColumnList(searcher);
        
        model.addAttribute("data", data);
        model.addAttribute("dataTemplate", JSONObject.toJSON(dataTemplate).toString());
        model.addAttribute("builtinVariableMap", JSONObject.toJSON(builtinVariableMap).toString());
        model.addAttribute("tableColumnBuiltinVariableMap", JSONObject.toJSON(tableColumnBuiltinVariableMap).toString());
        model.addAttribute("dataTemplateColumnList", dataTemplateColumnList);
        return "report/reportTemplate/reportTemplate_form";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(ReportTemplate reportTemplate, ModelMap model, HttpSession session)
    {
        List<ContentBookmark> bookmarkList = JSON.parseArray(reportTemplate.getContent(), ContentBookmark.class);
        for (ContentBookmark bm : bookmarkList)
        {
            if ("1".equals(bm.getContentType()) && StringUtils.isNotEmpty(bm.getBuiltinId()))
            {
                BuiltinVariable builtin = builtinService.get(bm.getBuiltinId());
                bm.setBuiltinVariable(builtin);
                bm.setTableColumnList(null);
            }
            else if ("2".equals(bm.getContentType()) && Collections3.isNotEmpty(bm.getTableColumnList()))
            {
                for (ContentTableColumn clolumn : bm.getTableColumnList())
                {
                    if ("1".equals(clolumn.getVariableType()) && StringUtils.isNotEmpty(clolumn.getBuiltinId()))
                    {
                        BuiltinVariable builtin = builtinService.get(clolumn.getBuiltinId());
                        clolumn.setBuiltinVariable(builtin);
                    }
                    else
                    {
                        if ("1".equals(clolumn.getDataType()) && StringUtils.isNotEmpty(clolumn.getDataTemplateColumnId()))
                        {
                            DataTemplateColumn dtc = dataTemplateService.getDataTemplateColumn(clolumn.getDataTemplateColumnId());
                            clolumn.setDataTemplateColumn(dtc);
                        }
                    }
                }
            }
        }
        reportTemplate.setBookmarkList(bookmarkList);
        service.modify(reportTemplate);
        return redirectList(model, session, "redirect:/report/reportTemplate/list.do");
    }
    
    @RequestMapping(value = "/delete.do")
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session, "redirect:/report/reportTemplate/list.do");
    }
    
    @ResponseBody
    @RequestMapping(value = "/validate.do", method = RequestMethod.POST)
    public boolean validate(ReportTemplate request)
    {
        return service.validate(request);
    }
    
    @ResponseBody
    @RequestMapping(value = "/uploadTemplate.do", produces = "text/plain;charset=UTF-8")
    public String uploadTemplate(@RequestParam MultipartFile uploadData)
    {
        String url = FileUtils.uploadSingleFile(uploadData);
        return url;
    }
    
    @ResponseBody
    @RequestMapping(value = "/uploadImage.do", produces = "text/plain;charset=UTF-8")
    public String uploadImage(@RequestParam MultipartFile uploadImg)
    {
        String url = FileUtils.uploadSingleFile(uploadImg);
        return url;
    }
    
    @RequestMapping("/selectReportTemplate.do")
    @ResponseBody
    public List<ReportTemplate> reportTemplateList(String key)
    {
        ReportTemplate searcher = new ReportTemplate();
        searcher.setName(key);
        searcher.setPageNo(1);
        searcher.setPageSize(10);
        return service.reportTemplateList(searcher);
    }
    
    @RequestMapping(value = "/getReportTemplate.do")
    @ResponseBody
    public ReportTemplate getReportTemplate(String id)
    {
        return service.get(id);
    }
}
