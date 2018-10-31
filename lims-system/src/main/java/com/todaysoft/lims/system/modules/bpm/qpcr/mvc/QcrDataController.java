package com.todaysoft.lims.system.modules.bpm.qpcr.mvc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSheetModel;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrTask;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitTask;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitModel;
import com.todaysoft.lims.system.modules.bpm.qpcr.service.IQpcrDataService;
import com.todaysoft.lims.system.modules.bpm.qpcr.service.IQpcrService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/testing/data")
public class QcrDataController extends BaseController
{
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IQpcrService service;
    
    @Autowired
    private IQpcrDataService dataService;

    @Autowired
    private IMlpaDataDataService mlpaDataDataService;
    
    @RequestMapping(value = "/uploadQpcr", method = RequestMethod.POST)
    public String uploadDataPcr(@RequestParam MultipartFile uploadData, String sheetId, HttpSession session, ModelMap model) throws IOException
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("QPCR_DATA_RESULT");
        if(null != parseResult)
        {
            //说明是重新上传的，清掉本地数据
            mlpaDataDataService.deleteLocalFileDir(parseResult);
        }
        DataAnalysisParseModel result = dataService.parse(sheetId,uploadData);
        List<QpcrSubmitTask> records = result.getModelMap().getRecords();
        QpcrSubmitModel sheet = service.getQpcrSubmitModel(sheetId);
        List<String> dataCode = Lists.newArrayList();
        if(Collections3.isNotEmpty(sheet.getTasks()))
        {
            sheet.getTasks().stream().forEach(x->dataCode.add(x.getCombineCode()));
        }
        //封装数据
        Map<String,String> mapTaskId = Maps.newHashMap();
        if(null != sheet)
        {
            List<QpcrTask> tasks = sheet.getTasks();
            for(QpcrTask task:tasks)
            {
                if(!mapTaskId.containsKey(task.getCombineCode()))
                {
                    mapTaskId.put(task.getCombineCode(),task.getId());
                }
            }
        }
        for(QpcrSubmitTask dpcrRecord:records)
        {
            String taskId = mapTaskId.get(dpcrRecord.getCombineCode());
            if(StringUtils.isEmpty(taskId))
            {
                dpcrRecord.setValid(false);
                dpcrRecord.setMessage("数据编号不匹配");
                continue;
            }
            dpcrRecord.setId(mapTaskId.get(dpcrRecord.getCombineCode()));
            dpcrRecord.setValid(true);
            if(null==dpcrRecord.getResult() || StringUtils.isEmpty(dpcrRecord.getDispose()))
            {
                dpcrRecord.setValid(false);
                dpcrRecord.setMessage("未上传结果");
            }else if(("成功".equals(dpcrRecord.getResultInfo()) && !"出数据".equals(dpcrRecord.getDispose())) ||
                    ("失败".equals(dpcrRecord.getResultInfo()) && "出数据".equals(dpcrRecord.getDispose()))){
                dpcrRecord.setValid(false);
                dpcrRecord.setMessage("处理意见异常");
            }else if("失败".equals(dpcrRecord.getResultInfo()) && StringUtils.isEmpty(dpcrRecord.getRemark()))
            {
                dpcrRecord.setValid(false);
                dpcrRecord.setMessage("备注为空");
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
        resultSession.setQpcrRecords(records);
        session.setAttribute("QPCR_DATA_RESULT",resultSession);
        model.addAttribute("result", result);
        model.addAttribute("sheet",sheet);
        return "bpm/test/qpcr_upload_result";
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadQpcr", method = RequestMethod.POST)
    public String downloadQpcr(String sheetId)
    {
        QpcrSubmitModel sheet = service.getQpcrSubmitModel(sheetId);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return dataService.zipFilesQpcr(zipfile, sheet);
    }
}
