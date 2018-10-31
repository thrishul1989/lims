package com.todaysoft.lims.system.modules.bpm.mlpadata.mvc;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSubmitTaskSuccessArgs;
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

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.system.mvc.BaseController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/testing/data")
public class MlpaDataDataController extends BaseController
{
    @Autowired
    private IMlpaDataService service;
    
    @Autowired
    private IMlpaDataDataService dataService;

    @Autowired
    private ITestSheetService testSheetService;
    
    @ResponseBody
    @RequestMapping(value = "/downloadMlpaData", method = RequestMethod.POST)
    public String downloadMlpaData(String sheetId)
    {
        MlpaDataSheetModel sheet = service.getMlpaDataSubmitModel(sheetId);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return dataService.zipFilesMlpaData(zipfile, sheet);
    }

    @RequestMapping(value = "/uploadMlpaResult", method = RequestMethod.POST)
    public String uploadFirstPCR(@RequestParam MultipartFile uploadData,String sheetId,String sampleCodes,HttpSession session,ModelMap model) throws IOException
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("MLPA_DATA_RESULT");
        if(null != parseResult)
        {
            //说明是重新上传的，清掉本地数据
            dataService.deleteLocalFileDir(parseResult);
        }
        DataAnalysisParseModel result = dataService.parse(sheetId,uploadData);
        List<MlpaDataSubmitTaskSuccessArgs> records = result.getModelMap().getRecords();
        List<String> dataCode = Lists.newArrayList();
        MlpaDataSheetModel sheet = service.getMlpaDataSubmitModel(sheetId);
        if(Collections3.isNotEmpty(sheet.getTasks()))
        {
            sheet.getTasks().stream().forEach(x->dataCode.add(x.getDataCode()));
        }

        if(Collections3.isNotEmpty(records) && StringUtils.isNotEmpty(sampleCodes))
        {
            List<String> sampleCodeList = Arrays.asList(sampleCodes.split(","));
            for(MlpaDataSubmitTaskSuccessArgs record:records)
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
        resultSession.setRecords(records);
        session.setAttribute("MLPA_DATA_RESULT",resultSession);
        model.addAttribute("result", result);
        return "bpm/test/mlpa_data_test_upload_result";
    }

    @RequestMapping("/reviewPic.do")
    public void reviewPic(HttpServletRequest req, HttpServletResponse resp, String fileName)
    {
        reviewPic(resp, fileName);
    }


    public void reviewPic(HttpServletResponse response, String path)
    {
        OutputStream os = null;
        InputStream is = null;
        try
        {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            is = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" +  new String(filename.getBytes("gbk"), "iso8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/jpeg");
            os.write(buffer);
            os.flush();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
                os.close();

            }
            catch (IOException e)
            {

                e.printStackTrace();
            }
        }
    }
}
