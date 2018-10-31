package com.todaysoft.lims.system.modules.bpm.dpcrsanger.mvc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerSheetModel;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerTask;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.service.IDataPcrSangerDataService;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.service.IDataPcrSangerService;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/testing/data")
public class DataPcrSangerDataController extends BaseController
{
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IDataPcrSangerService service;
    
    @Autowired
    private IDataPcrSangerDataService dataService;

    @Autowired
    private IMlpaDataDataService mlpaDataDataService;
    
    @RequestMapping(value = "/uploadDataPcrSanger", method = RequestMethod.POST)
    public String uploadDataPcr(@RequestParam MultipartFile uploadData, String sheetId, HttpSession session, ModelMap model) throws IOException
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("DPCR_SANGER_DATA_RESULT");
        if(null != parseResult)
        {
            //说明是重新上传的，清掉本地数据
            mlpaDataDataService.deleteLocalFileDir(parseResult);
        }
        DataAnalysisParseModel result = dataService.parse(sheetId,uploadData);
        List<DataPcrSangerSubmitTaskArgs> records = result.getModelMap().getRecords();
        //封装数据
        DataPcrSangerSheetModel sheet = service.getDataPcrSubmitModel(sheetId);

        List<String> dataCode = Lists.newArrayList();

        Map<String,DataPcrSangerTask> mapTaskId = Maps.newHashMap();
        if(null != sheet)
        {
            List<DataPcrSangerTask> tasks = sheet.getTasks();
            for(DataPcrSangerTask task:tasks)
            {
                if(!dataCode.contains(task.getCombineCode()))
                {
                    dataCode.add(task.getCombineCode());
                }
                if(!mapTaskId.containsKey(task.getCombineCode()))
                {
                    mapTaskId.put(task.getCombineCode(),task);
                }
            }
        }
        for(DataPcrSangerSubmitTaskArgs dpcrRecord:records)
        {
            DataPcrSangerTask task = mapTaskId.get(dpcrRecord.getCombineCode());
            dpcrRecord.setSheetCode(sheet.getCode());
            dpcrRecord.setPcrTestCode(task.getPcrTestCode());
            if(null==task)
            {
                dpcrRecord.setValid(false);
                dpcrRecord.setMessage("数据编号不匹配");
                continue;
            }
            dpcrRecord.setId(task.getId());
            dpcrRecord.setSampleName(task.getSampleName());
            dpcrRecord.setPrimerId(task.getPrimerId());
            dpcrRecord.setRunningStatus(task.getRunningStatus());
            dpcrRecord.setValid(true);
            if(null==dpcrRecord.getResult() || StringUtils.isEmpty(dpcrRecord.getDispose()))
            {
                dpcrRecord.setValid(false);
                dpcrRecord.setMessage("未上传结果");
            }else if(("成功".equals(dpcrRecord.getResultInfo()) && !"出数据".equals(dpcrRecord.getDispose())) ||
                    ("失败".equals(dpcrRecord.getResultInfo()) && "出数据".equals(dpcrRecord.getDispose()))){
                dpcrRecord.setValid(false);
                dpcrRecord.setMessage("处理意见异常");
            }
            if("失败".equals(dpcrRecord.getResultInfo()) && StringUtils.isEmpty(dpcrRecord.getRemark())){
                dpcrRecord.setValid(false);
                dpcrRecord.setMessage("备注不可为空");
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
        resultSession.setDpcrSangerRecords(records);
        session.setAttribute("DPCR_SANGER_DATA_RESULT",resultSession);
        model.addAttribute("result", result);
        model.addAttribute("sheet",sheet);
        return "bpm/sangerTest/test/sanger_test_upload_result";
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadDataPcrSangerData", method = RequestMethod.POST)
    public String downloadDataPcrSangerData(String sheetId)
    {
        DataPcrSangerSheetModel sheet = service.getDataPcrSubmitModel(sheetId);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return dataService.zipFilesDataPcr(zipfile, sheet);
    }
}
