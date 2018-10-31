package com.todaysoft.lims.system.modules.bpm.longqc.mvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.dnaext.mvc.DNAExtractController;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongQcSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongQcSubmitTaskModelExcel;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongqcTestSheet;
import com.todaysoft.lims.system.modules.bpm.longqc.service.ILongQcService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ITestSheetService;

@Controller
@RequestMapping("/testing")
public class LongQcController extends BaseController
{
    
    @Autowired
    private ILongQcService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IProductService productService;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @ResponseBody
    @RequestMapping(value = "/downloadLongqc", method = RequestMethod.POST)
    public String downloadLongqc(String sheetId)
    {
        LongqcTestSheet sheet = service.getLongqcSubmitModel(sheetId);
        
        AuthorizedUser user = userService.getByToken();
        sheet.setQcTester(user.getName());
        
     
        
        InputStream is = DNAExtractController.class.getResourceAsStream("/taskTemplate/longpcr/LONGQC_TASK.xlsx");
        
        
        return service.download(is, sheet);
    }
    
    @ResponseBody
    @RequestMapping(value = "/uploadLongqc", method = RequestMethod.POST)
    public List<LongQcSubmitTaskModelExcel> uploadLongpcr(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException
    {
        File file = productService.upload(request, response, model);
        List<LongQcSubmitTaskModelExcel> list = service.uploadData(file);
        for (LongQcSubmitTaskModelExcel excelModel : list)
        {
            excelModel.setQualityLevel(testSheetService.QCValue(Double.valueOf(excelModel.getConcn()), Double.valueOf(excelModel.getRatio2628()), "LibQc"));
        }
        return list;
    }
    
    @RequestMapping("/longqc_test.do")
    @FormInputView
    public String longQcTest(String id, ModelMap model)
    {
        LongqcTestSheet sheet = service.getLongqcSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        sheet.setQcTester(user.getName());
        
        model.addAttribute("sheet", sheet);
        return "bpm/test/longqc_test_form";
    }
    
    @RequestMapping("/longqc_test_submit.do")
    @FormSubmitHandler
    public String longQcTestSubmit(LongQcSubmitSheetModel request)
    {
        service.submitSheet(request);
        return "redirect:/testing/test_tasks.do ";
    }
    
}
