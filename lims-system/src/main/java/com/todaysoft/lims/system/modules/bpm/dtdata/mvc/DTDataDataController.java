package com.todaysoft.lims.system.modules.bpm.dtdata.mvc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.dtdata.service.IDTDataDataService;
import com.todaysoft.lims.system.modules.bpm.dtdata.service.IDTDataService;
import com.todaysoft.lims.system.mvc.BaseController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/testing/data")
public class DTDataDataController extends BaseController
{
    @Autowired
    private IDTDataService service;
    
    @Autowired
    private IDTDataDataService dataService;

    @Autowired
    private IMlpaDataDataService mlpaDataService;

    @Autowired
    private ITestSheetService testSheetService;
    
    @ResponseBody
    @RequestMapping(value = "/downloadDTData", method = RequestMethod.POST)
    public String downloadDTData(String sheetId)
    {
        DTDataSheetModel sheet = service.getDataPcrSubmitModel(sheetId);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return dataService.zipFilesDTData(zipfile, sheet);
    }

    @RequestMapping(value = "/uploadDTResult", method = RequestMethod.POST)
    public String uploadDTResult(@RequestParam MultipartFile uploadData, String sheetId, String sampleCodes, HttpSession session, ModelMap model) throws IOException
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("DT_DATA_RESULT");
        if(null != parseResult)
        {
            //说明是重新上传的，清掉本地数据
            mlpaDataService.deleteLocalFileDir(parseResult);
        }
        DataAnalysisParseModel result = dataService.parse(sheetId,uploadData);
        List<DTDataSubmitTaskSuccessArgs> records = result.getModelMap().getRecords();
        List<String> dataCode = Lists.newArrayList();
        DTDataSheetModel sheet = service.getDataPcrSubmitModel(sheetId);
        if(sheet != null && Collections3.isNotEmpty(sheet.getTasks()))
        {
            sheet.getTasks().stream().forEach(x->dataCode.add(x.getCombineCode()));
        }

        if(Collections3.isNotEmpty(records) && StringUtils.isNotEmpty(sampleCodes))
        {
            List<String> sampleCodeList = Arrays.asList(sampleCodes.split(","));
            for(DTDataSubmitTaskSuccessArgs record:records)
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
        resultSession.setDtRecords(records);
        session.setAttribute("DT_DATA_RESULT",resultSession);
        model.addAttribute("result", result);
        return "bpm/test/dt_data_test_upload_result";
    }
}
