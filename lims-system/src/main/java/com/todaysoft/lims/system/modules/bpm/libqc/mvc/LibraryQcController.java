package com.todaysoft.lims.system.modules.bpm.libqc.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.libqc.model.LibraryQcSubmitModel;
import com.todaysoft.lims.system.modules.bpm.libqc.model.LibraryQcSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.libqc.service.ILibraryQcService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class LibraryQcController extends BaseController
{
    @Autowired
    private ILibraryQcService service;
    
    //实验页面
    @RequestMapping("/lib_qc_test.do")
    @FormInputView
    public String libQcTest(String id, ModelMap model)
    {
        LibraryQcSubmitModel sheet = service.getSubmitModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/test/lib_qc_test_form";
    }
    
    //实验提交
    @RequestMapping("/lib_qc_test_submit.do")
    @FormSubmitHandler
    public String libQcTestSubmit(LibraryQcSubmitRequest request)
    {
        service.submit(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
