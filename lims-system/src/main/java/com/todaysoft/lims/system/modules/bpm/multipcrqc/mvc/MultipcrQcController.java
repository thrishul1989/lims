package com.todaysoft.lims.system.modules.bpm.multipcrqc.mvc;

import java.io.File;
import java.io.IOException;
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
import com.todaysoft.lims.system.modules.bpm.multipcrqc.model.MultipcrQcSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.multipcrqc.model.MultipcrQcSubmitTaskModelExcel;
import com.todaysoft.lims.system.modules.bpm.multipcrqc.model.MultipcrqcTestSheet;
import com.todaysoft.lims.system.modules.bpm.multipcrqc.service.IMultipcrQcService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ITestSheetService;

@Controller
@RequestMapping("/testing")
public class MultipcrQcController extends BaseController
{
    
    @Autowired
    private IMultipcrQcService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IProductService productService;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    
    
    @ResponseBody
    @RequestMapping(value = "/downloadMultipcrqc", method = RequestMethod.POST)
    public String downloadMultipcrqc(String sheetId)
    {
        MultipcrqcTestSheet sheet = service.getMultipcrqcSubmitModel(sheetId);
        
        AuthorizedUser user = userService.getByToken();
        sheet.setQcTester(user.getName());
        
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return service.zipFilesMultipcrqc(zipfile, sheet);
    }
    
    @ResponseBody
    @RequestMapping(value = "/uploadMultipcrqc", method = RequestMethod.POST)
    public List<MultipcrQcSubmitTaskModelExcel> uploadMultipcrpcr(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException
    {
        File file = productService.upload(request, response, model);
        List<MultipcrQcSubmitTaskModelExcel> list = service.uploadData(file);
        for (MultipcrQcSubmitTaskModelExcel excelModel : list)
        {
            excelModel.setQualityLevel(testSheetService.QCValue(Double.valueOf(excelModel.getConcn()), Double.valueOf(excelModel.getRatio2628()), "LibQc"));
        }
        return list;
    }
    
    
    
    
    @RequestMapping("/multipcrqc_test.do")
    @FormInputView
    public String multipcrQcTest(String id, ModelMap model)
    {
        MultipcrqcTestSheet sheet = service.getMultipcrqcSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        sheet.setQcTester(user.getName());
        
        model.addAttribute("sheet", sheet);
        return "bpm/test/multipcrqc_test_form";
    }
    
    
    @RequestMapping("/multipcrqc_test_submit.do")
    @FormSubmitHandler
    public String multipcrQcTestSubmit(MultipcrQcSubmitSheetModel request)
    {
        service.submitSheet(request);
        return "redirect:/testing/test_tasks.do ";
    }
    
    
    
}
