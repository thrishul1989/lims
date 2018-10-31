package com.todaysoft.lims.system.modules.bpm.fluoanalyse.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.fluoanalyse.model.*;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.fluoanalyse.service.IFluoAnalyseService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.TestingSheetController;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ITestSheetService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/testing")
public class FluoAnalyseController extends BaseController
{
    @Autowired
    private IFluoAnalyseService service;
    
    @Autowired
    private IUserService userService;

    @Autowired
    private IMlpaDataDataService mlpaDataService;
    
    @RequestMapping("/fluo_analyse_task.do")
    public String fluoAnalyseTasks(FluoAnalyseAssignableTaskSearcher searcher, ModelMap model)
    {
        List<FluoAnalyseAssignTask> tasks = service.getfluoAnalyseAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/fluoanalyse_list";
    }
    
    @RequestMapping("/fluo_analyse_assign.do")
    @FormInputView
    public String getFluoAnalyseAssignModel(FluoAnalyseAssignArgs args, ModelMap model)
    {
        FluoAnalyseAssignModel data = service.getFluoAnalyseAssignModel(args);
        for (FluoAnalyseTask assignModel : data.getTasks())
        {
            setAttr(assignModel);
        }
        model.addAttribute("data", data);
        return "bpm/assign/fluoanalyse_assign_form";
    }
    
    @RequestMapping("/fluo_analyse_assign_submit.do")
    @FormSubmitHandler
    public String assignFluoAnalyse(FluoAnalyseAssignRequest request)
    {
        service.assignFluoAnalyse(request);
        return "redirect:/testing/fluo_analyse_task.do";
    }
    
    @RequestMapping("/fluo_analyse_test.do")
    @FormInputView
    public String getFluoAnalyseSubmitModel(String id, ModelMap model)
    {
        FluoAnalyseSubmitModel sheet = service.getFluoAnalyseSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        sheet.setTester(user.getName());
        
        for (FluoAnalyseTask assignModel : sheet.getTasks())
        {
            setAttr(assignModel);
        }
        model.addAttribute("sheet", sheet);
        
        return "bpm/test/fluoanalyse_test_form";
    }
    
    @RequestMapping("/fluo_analyse_test_submit.do")
    @FormSubmitHandler
    public String fluoAnalyseSubmit(FluoAnalyseSubmitSheetModel request,HttpSession session)
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("FLUO_DATA_RESULT");
        session.removeAttribute("FLUO_DATA_RESULT");
        if(null != parseResult)
        {
            request.setParseModel(parseResult);
        }
        service.submitSheet(request);
        return "redirect:/testing/test_tasks.do ";
    }

    @RequestMapping(value = "/fluo/uploadFluoResult", method = RequestMethod.POST)
    public String uploadFluoResult(@RequestParam MultipartFile uploadData, String sheetId, String sampleCodes, HttpSession session,ModelMap model) throws IOException
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("FLUO_DATA_RESULT");
        if(null != parseResult)
        {
            //说明是重新上传的，清掉本地数据
            mlpaDataService.deleteLocalFileDir(parseResult);
        }
        DataAnalysisParseModel result = service.parse(sheetId,uploadData);
        FluoAnalyseSubmitModel sheet = service.getFluoAnalyseSubmitModel(sheetId);
        List<String> dataCode = Lists.newArrayList();
        if(Collections3.isNotEmpty(sheet.getTasks()))
        {
            sheet.getTasks().stream().forEach(x->dataCode.add(x.getDataCode()));
        }

        List<FluoDataSubmitTaskSuccessArgs> records = result.getModelMap().getRecords();
        if(Collections3.isNotEmpty(records) && StringUtils.isNotEmpty(sampleCodes))
        {
            List<String> sampleCodeList = Arrays.asList(sampleCodes.split(","));
            for(FluoDataSubmitTaskSuccessArgs record:records)
            {
                record.setValid(true);
                if(StringUtils.isEmpty(record.getSampleCode()))
                {
                    record.setValid(false);
                    record.setMessage("样本编号为空");
                    continue;
                }
                if(!sampleCodeList.contains(record.getSampleCode()))
                {
                    record.setValid(false);
                    record.setMessage("样本编号有误");
                    continue;
                }
            }
        }

        //验证图片
        for(TestingDataPic pic:result.getPicList())
        {
            boolean valid = true;
            String message = "有效";
            String picNameDataCode = pic.getDataCode();
            if(!dataCode.contains(picNameDataCode))
            {
                valid = false;
                message = "无效-图片名称合并编号有误";
            }
            pic.setValid(valid);
            pic.setMessage(message);
        }

        DataAnalysisParseModel resultSession = new DataAnalysisParseModel();
        resultSession.setUploadDir(result.getUploadDir());
        resultSession.setLocalFilePath(result.getLocalFilePath());
        resultSession.setPicList(result.getPicList());
        resultSession.setFluoRecords(records);
        session.setAttribute("FLUO_DATA_RESULT",resultSession);
        model.addAttribute("result", result);
        return "bpm/test/fluo_data_test_upload_result";
    }
    
    @ResponseBody
    @RequestMapping(value = "/fluo/downloadFluotestData", method = RequestMethod.POST)
    public String downloadFluotestData(String sheetId)
    {
        FluoAnalyseSubmitModel sheet = service.getFluoAnalyseSubmitModel(sheetId);
        for (FluoAnalyseTask assignModel : sheet.getTasks())
        {
            setAttr(assignModel);
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return service.zipFilesFluoAnalyseData(zipfile, sheet);
    }

    private void setAttr(FluoAnalyseTask assignModel)
    {
        assignModel.setDnaConcn((BigDecimal)JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("concn"));
        assignModel.setQualityLevel(JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
    }
}
