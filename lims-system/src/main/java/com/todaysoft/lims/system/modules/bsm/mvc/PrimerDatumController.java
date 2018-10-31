package com.todaysoft.lims.system.modules.bsm.mvc;

import java.io.InputStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.PrimerDatum;
import com.todaysoft.lims.system.modules.bsm.model.PrimerDatumSearcher;
import com.todaysoft.lims.system.modules.bsm.service.IPrimerDatumService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/primerReferenceLibrary")
public class PrimerDatumController extends BaseController
{
    @Autowired
    private IPrimerDatumService service;
    
    @RequestMapping("/list.do")
    public String list(PrimerDatumSearcher searcher,
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model,
        HttpSession session)
    {
        Pagination<PrimerDatum> pagination = service.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "primerReferenceLibrary/primerReferenceLibrary_list";
    }
    
    @RequestMapping("/create.do")
    @FormSubmitHandler
    public String create(PrimerDatum entity)
    {
        service.create(entity);
        return "redirect:/primerReferenceLibrary/list.do";
    }
    
    @RequestMapping("/modify.do")
    @FormSubmitHandler
    public String modify(PrimerDatum entity)
    {
        service.modify(entity);
        return "redirect:/primerReferenceLibrary/list.do";
    }
    
    @RequestMapping("/delete.do")
    public String delete(String id)
    {
        service.delete(id);
        return "redirect:/primerReferenceLibrary/list.do";
    }
    
    @RequestMapping("/view.do")
    public String get(String id, ModelMap model)
    {
        PrimerDatum data = service.get(id);
        model.addAttribute("primerReferenceLibrary", data);
        return "/primerReferenceLibrary/primerReferenceLibrary_view";
    }
    
    @RequestMapping("/primerReferenceLibraryForm.do")
    @FormInputView
    public String form(String id, ModelMap model)
    {
        PrimerDatum data = new PrimerDatum();
        if (id != null)
        {
            data = service.get(id);
        }
        
        model.addAttribute("primerReferenceLibrary", data);
        return "primerReferenceLibrary/primerReferenceLibrary_form";
    }
    
    @RequestMapping("/upload.do")
    public String upload(@RequestParam MultipartFile uploadData)
    {
        service.upload(uploadData);
        return "redirect:/primerReferenceLibrary/list.do";
    }
    
    @ResponseBody
    @RequestMapping("/downloadPrimerDatum")
    public String downloadCode(String id)
    {
        
        InputStream is = PrimerDatum.class.getResourceAsStream("/taskTemplate/primerDatum.xlsx");
        return service.download(is);
    }
}
