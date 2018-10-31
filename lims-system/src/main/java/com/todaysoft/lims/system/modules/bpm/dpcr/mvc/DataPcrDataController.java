package com.todaysoft.lims.system.modules.bpm.dpcr.mvc;

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
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrTask;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataTask;
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

import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSheetModel;
import com.todaysoft.lims.system.modules.bpm.dpcr.service.IDataPcrDataService;
import com.todaysoft.lims.system.modules.bpm.dpcr.service.IDataPcrService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/testing/data")
public class DataPcrDataController extends BaseController
{
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IDataPcrService service;
    
    @Autowired
    private IDataPcrDataService dataService;

    @Autowired
    private IMlpaDataDataService mlpaDataDataService;
    
    @RequestMapping(value = "/uploadDataPcr", method = RequestMethod.POST)
    public String uploadDataPcr(@RequestParam MultipartFile uploadData, String sheetId, HttpSession session, ModelMap model) throws IOException
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("DPCR_DATA_RESULT");
        if(null != parseResult)
        {
            //说明是重新上传的，清掉本地数据
            mlpaDataDataService.deleteLocalFileDir(parseResult);
        }
        DataAnalysisParseModel result = dataService.parse(sheetId,uploadData);
        List<DataPcrSubmitTaskArgs> records = result.getModelMap().getRecords();
        //封装数据
        DataPcrSheetModel sheet = service.getDataPcrSubmitModel(sheetId);
        List<String> dataCode = Lists.newArrayList();
        if(Collections3.isNotEmpty(sheet.getTasks()))
        {
            sheet.getTasks().stream().forEach(x->dataCode.add(x.getCombineCode()));
        }
        Map<String,DataPcrTask> mapTask = Maps.newHashMap();
        if(null != sheet)
        {
            List<DataPcrTask> tasks = sheet.getTasks();
            for(DataPcrTask task:tasks)
            {
                if(!mapTask.containsKey(task.getCombineCode()))
                {
                    mapTask.put(task.getCombineCode(),task);
                }
            }
        }
        for(DataPcrSubmitTaskArgs dpcrRecord:records)
        {
            DataPcrTask task = mapTask.get(dpcrRecord.getCombineCode());
            if(null==task)
            {
                dpcrRecord.setValid(false);
                dpcrRecord.setMessage("数据编号不匹配");
                continue;
            }
            dpcrRecord.setId(task.getId());

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
            else if("失败".equals(dpcrRecord.getResultInfo()) && StringUtils.isEmpty(dpcrRecord.getRemark()))
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
        resultSession.setDpcrRecords(records);
        session.setAttribute("DPCR_DATA_RESULT",resultSession);
        model.addAttribute("result", result);
        model.addAttribute("sheet",sheet);
        return "bpm/sanger/test/sanger_upload_result";
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadDataPcrData", method = RequestMethod.POST)
    public String downloadDataPCRData(String sheetId)
    {
        DataPcrSheetModel sheet = service.getDataPcrSubmitModel(sheetId);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return dataService.zipFilesDataPcr(zipfile, sheet);
    }
}
