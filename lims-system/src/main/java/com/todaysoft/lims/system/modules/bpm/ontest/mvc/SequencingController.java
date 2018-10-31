package com.todaysoft.lims.system.modules.bpm.ontest.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.persist.PageInfo;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsCallBackRequest;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingMachine;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingMachineMdReq;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingAssignModel;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingAssignRequest;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.ontest.service.ISequencingService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class SequencingController extends BaseController
{
    @Autowired
    private ISequencingService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/ontesting_assign.do")
    @FormInputView
    public String getOnTestingAssignModel(String id, ModelMap model)
    {
        SequencingAssignModel data = service.getAssignModel(id);
        model.addAttribute("data", data);
        return "bpm/assign/onTesting_assign_form";
    }
    
    @RequestMapping("/ngsSequecing_assign.do")
    @FormInputView
    public String ngsSequecingAssinging(String id, ModelMap model)
    {
        SequencingAssignModel data = service.getNgsSequecingAssignModel(id);
        model.addAttribute("data", data);
        return "bpm/assign/ngsSequecing_assign_form";
    }
    
    @RequestMapping("/ontesting_assign_submit.do")
    @FormSubmitHandler
    public String assignOnTesting(SequencingAssignRequest request)
    {
        service.assign(request);
        return "redirect:/process/onTestingList.do";
    }
    
    @RequestMapping("/ngsSequecing_assign_submit.do")
    @FormSubmitHandler
    public String assignNgsSequecing(SequencingAssignRequest request)
    {
        service.assignNgsSequecing(request);
        return "redirect:/process/ngsSequecingList.do";
    }
    
    @RequestMapping("/ontesting_test.do")
    @FormInputView
    public String getOnTestingSubmitModel(String id, ModelMap model)
    {
        SequencingSubmitModel sheet = service.getSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/onTest_test_form";
    }
    
    @RequestMapping("/ngsSequecing_test.do")
    @FormInputView
    public String getNgsSequecingSubmitModel(String sheetId, ModelMap model)
    {
        SequencingSubmitModel sheet = service.getNgsSequecingSubmitModel(sheetId);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/ngsSequecing_test_form";
    }
    
    @RequestMapping("/ontesting_submit.do")
    @FormSubmitHandler
    public String submitOnTesting(SequencingSubmitRequest request)
    {
        service.submit(request);
        return "redirect:/testing/test_tasks.do ";
    }
    
    @RequestMapping("/ngsSequecing_submit.do")
    @FormSubmitHandler
    public String submitNgsSequecing(SequencingSubmitRequest request)
    {
        service.submitNgsSequecing(request);
        return "redirect:/testing/test_tasks.do ";
    }
    
    @RequestMapping("/erroTaskReSequecing.do")
    public String erroTaskReSequecing(String id)
    {
        service.erroTaskReSequecing(id);
        return "redirect:/process/ngsSequecingMonitorList.do ";
    }
    
    /**
     * 测序机型管理
     * */
    
    @RequestMapping(value = "/sequecingMachine/list.do")
    public String getSequecingMachine(NgsSequecingMachine searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        PageInfo<NgsSequecingMachine> pagination = service.machineList(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "sequecingmachine/machine_list";
        
    }
    
    /**
     * 修改测序机型
     * 
     *
     */
    @RequestMapping(value = "/sequecingMachine/modify.do", method = RequestMethod.GET)
    public String modifyPage(NgsSequecingMachine searcher, ModelMap model, HttpSession session)
    {
        NgsSequecingMachine sequecingMachine = service.getSequecingMachine(searcher.getId());
        model.addAttribute("sequecingMachine", sequecingMachine);
        return "sequecingmachine/machine_modify";
    }
    
    @RequestMapping(value = "/sequecingMachine/modify.do", method = RequestMethod.POST)
    public String modify(NgsSequecingMachineMdReq request, ModelMap model, HttpSession session)
    {
        
        service.modifySequecingMachine(request);
        return redirectList(model, session, "redirect:/testing/sequecingMachine/list.do");
    }
    
    /**
     * 下机监控回调接口
     * 
     * */
    @RequestMapping(value = "/ngsSequecingCallBack", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> ngsSequecingCallBack(@RequestBody NgsCallBackRequest request)
    {
        System.out.println("ngsSequecingCallBack doing......");
        Map<String, String> resp = new HashMap<>();
        resp.put("statusCode", "0000");
        resp.put("message", "请求成功！");
        
        try
        {
            service.ngsSequecingCallBack(request.getTaskId(), Integer.parseInt(request.getFileIntegrity()));
        }
        catch (Exception e)
        {
            return resp;
        }
        
        return resp;
    }
    
}
