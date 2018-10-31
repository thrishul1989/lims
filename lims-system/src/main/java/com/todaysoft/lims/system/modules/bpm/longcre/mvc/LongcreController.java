package com.todaysoft.lims.system.modules.bpm.longcre.mvc;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.dnaext.mvc.DNAExtractController;
import com.todaysoft.lims.system.modules.bpm.longcre.model.LongcreSubmitSheet;
import com.todaysoft.lims.system.modules.bpm.longcre.servicve.ILongcreService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class LongcreController extends BaseController
{
    
    @Autowired
    private ILongcreService service;
    
    @Autowired
    private IUserService userService;
    
    @ResponseBody
    @RequestMapping(value = "/downloadLongcre", method = RequestMethod.POST)
    public String downloadLongcre(String sheetId)
    {
        LongcreSubmitSheet sheet = service.getLongcreSubmitModel(sheetId);
        
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return service.zipFilesLongcre(zipfile, sheet);
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadOutput", method = RequestMethod.POST)
    public String downloadOutput(String sheetId)
    {
        LongcreSubmitSheet sheet = service.getLongcreSubmitModel(sheetId);
        
        InputStream is = DNAExtractController.class.getResourceAsStream("/taskTemplate/longpcr/LONGCRE_PRINT.xlsx");
        
        return service.downloadOutput(is, sheet);
    }
    
    @RequestMapping("/longcre_test.do")
    @FormInputView
    public String getLongcreSubmitModel(String id, ModelMap model)
    {
        LongcreSubmitSheet sheet = service.getLongcreSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        sheet.setCreTester(user.getName());
        
        model.addAttribute("sheet", sheet);
        return "bpm/test/longcre_test_form";
    }
    
    @RequestMapping("/longcre_test_submit.do")
    @FormSubmitHandler
    public String submitLongcre(LongcreSubmitSheet request)
    {
        
        service.submitLongcre(request);
        return "redirect:/testing/test_tasks.do ";
    }
    
}
