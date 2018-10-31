package com.todaysoft.lims.system.modules.bpm.pcrngsdata.mvc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
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

import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.service.IPcrNgsDataDataService;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.service.IPcrNgsDataService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/testing/data")
public class PcrNgsDataDataController extends BaseController
{
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IPcrNgsDataService service;
    
    @Autowired
    private IPcrNgsDataDataService dataService;

    @Autowired
    private IMlpaDataDataService mlpaDataDataService;
    
    @RequestMapping(value = "/uploadPcrNgsData", method = RequestMethod.POST)
    public String uploadDataPcr(@RequestParam MultipartFile uploadData, String sheetId,HttpSession session,ModelMap model) throws IOException
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("PCRNGS_DATA_RESULT");
        if(null != parseResult)
        {
            //说明是重新上传的，清掉本地数据
            mlpaDataDataService.deleteLocalFileDir(parseResult);
        }
        DataAnalysisParseModel result = dataService.parse(sheetId,uploadData);
        List<PcrNgsDataSubmitTaskArgs> records = result.getModelMap().getRecords();
        //封装数据
        List<String> dataCode = Lists.newArrayList();
        PcrNgsDataSheetModel sheet = service.getDataPcrSubmitModel(sheetId);
        Map<String,PcrNgsDataTask> mapBioTestCode = Maps.newHashMap();
        if(null != sheet)
        {
            List<PcrNgsDataTask> tasks = sheet.getTasks();
            for(PcrNgsDataTask task:tasks)
            {
                if(!dataCode.contains(task.getCombineCode()))
                {
                    dataCode.add(task.getCombineCode());
                }
                if(!mapBioTestCode.containsKey(task.getCombineCode()))
                {
                    mapBioTestCode.put(task.getCombineCode(),task);
                }
            }
        }
        for(PcrNgsDataSubmitTaskArgs pcrNgsRecord:records)
        {
            PcrNgsDataTask task = mapBioTestCode.get(pcrNgsRecord.getCombineCode());
            pcrNgsRecord.setSheetCode(sheet.getCode());
            pcrNgsRecord.setBioTestCode(pcrNgsRecord.getCombineCode());
            if(null==task)
            {
                pcrNgsRecord.setValid(false);
                pcrNgsRecord.setMessage("数据编号不匹配");
                continue;
            }
            pcrNgsRecord.setId(task.getId());
            pcrNgsRecord.setValid(true);
            if(null==pcrNgsRecord.getResult() || StringUtils.isEmpty(pcrNgsRecord.getDispose()))
            {
                pcrNgsRecord.setValid(false);
                pcrNgsRecord.setMessage("未上传结果");
            }else if(("成功".equals(pcrNgsRecord.getResultInfo()) && !"出数据".equals(pcrNgsRecord.getDispose())) ||
                    ("失败".equals(pcrNgsRecord.getResultInfo()) && "出数据".equals(pcrNgsRecord.getDispose()))){
                pcrNgsRecord.setValid(false);
                pcrNgsRecord.setMessage("处理意见异常");
            }
            else if("失败".equals(pcrNgsRecord.getResultInfo()) && StringUtils.isEmpty(pcrNgsRecord.getRemark()))
            {
                pcrNgsRecord.setValid(false);
                pcrNgsRecord.setMessage("备注为空");
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
        resultSession.setPcrNgsRecords(records);
        session.setAttribute("PCRNGS_DATA_RESULT",resultSession);
        model.addAttribute("result", result);
        model.addAttribute("sheet",sheet);
        return "bpm/pcr_ngs/test/pcr_ngs_test_upload_result";
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadPcrNgsDataData", method = RequestMethod.POST)
    public String downloadDataPCRData(String sheetId)
    {
        PcrNgsDataSheetModel sheet = service.getDataPcrSubmitModel(sheetId);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return dataService.zipFilesDataPcr(zipfile, sheet);
    }
}
