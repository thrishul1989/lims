package com.todaysoft.lims.system.modules.bpm.report.mvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.modules.bpm.report.model.*;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisSampleModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalAnalysisAddVerify;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.ITechnicalAnalyService;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.qiniu.util.Json;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.report.service.IReportService;
import com.todaysoft.lims.system.modules.bpm.report.service.request.GenerateReportRequest;
import com.todaysoft.lims.system.modules.bpm.report.service.request.ReportSendSearcher;
import com.todaysoft.lims.system.modules.bpm.report.service.request.SendDataRequest;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;
import com.todaysoft.lims.system.util.ConfigManage;
import com.todaysoft.lims.system.util.Constant;
import com.todaysoft.lims.system.util.JsonUtils;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/bpm/report")
public class ReportTaskController extends BaseController
{
    @Autowired
    private ITechnicalAnalyService technicalAnalyService;

    @Autowired
    private IReportService service;
    
    @RequestMapping("/tasks.do")
    public String tasks(ReportTaskSearch searcher, Integer pageNo, Integer pageSize, ModelMap model, HttpSession session)
    {
        if (null == pageNo)
        {
            pageNo = 1;
        }
        
        if (null == pageSize)
        {
            pageSize = 50;
        }
        searcher.setStatus(1);
        Pagination<TestingReport> pagination = service.paging(searcher, pageNo, pageSize);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-pageSize", pageSize);
        session.setAttribute("s-searcher", searcher);
        return "bpm/report/report_task_list";
    }
    
    @RequestMapping("/integration/tasks.do")
    public String integrationTasks(ReportTaskSearch searcher, Integer pageNo, Integer pageSize, ModelMap model, HttpSession session)
    {
        if (null == pageNo)
        {
            pageNo = 1;
        }
        
        if (null == pageSize)
        {
            pageSize = 50;
        }
        searcher.setStatus(0);
        Pagination<TestingReport> pagination = service.paging(searcher, pageNo, pageSize);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-pageSize", pageSize);
        session.setAttribute("s-searcher", searcher);
        return "bpm/report/report_integration_task_list";
    }

/*    @RequestMapping("/geneticCan.do")
    public String geneticCan(ReportTaskSearch searcher, Integer pageNo, Integer pageSize, ModelMap model, HttpSession session)
    {
        if (null == pageNo)
        {
            pageNo = 1;
        }

        if (null == pageSize)
        {
            pageSize = 50;
        }
        searcher.setStatus(1);
        Pagination<TestingReport> pagination = service.paging(searcher, pageNo, pageSize);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-pageSize", pageSize);
        session.setAttribute("s-searcher", searcher);
        return "bpm/report/report_genetic_task_list";
    }


    @RequestMapping("/geneticWait.do")
    public String geneticWait(ReportTaskSearch searcher, Integer pageNo, Integer pageSize, ModelMap model, HttpSession session)
    {
        if (null == pageNo)
        {
            pageNo = 1;
        }

        if (null == pageSize)
        {
            pageSize = 50;
        }
        searcher.setStatus(0);
        Pagination<TestingReport> pagination = service.paging(searcher, pageNo, pageSize);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-pageSize", pageSize);
        session.setAttribute("s-searcher", searcher);
        return "bpm/report/report_genetic_wait_list";
    }*/


    @RequestMapping("/waitAssign/tasks.do")
    public String waitTodoTasks(ReportTaskSearch searcher, Integer pageNo, Integer pageSize, ModelMap model, HttpSession session)
    {
        if (null == pageNo)
        {
            pageNo = 1;
        }

        if (null == pageSize)
        {
            pageSize = 50;
        }
        Pagination<TestingReport> pagination = service.assignPaging(searcher, pageNo, pageSize);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-pageSize", pageSize);
        session.setAttribute("s-searcher", searcher);
        return "bpm/report/report_waitAssign_list";
    }

    @RequestMapping("/assigned/list.do")
    public String assignedList(ReportTaskSearch searcher, Integer pageNo, Integer pageSize, ModelMap model, HttpSession session)
    {
        if (null == pageNo)
        {
            pageNo = 1;
        }

        if (null == pageSize)
        {
            pageSize = 50;
        }
        Pagination<TestingReport> pagination = service.assignEdPaging(searcher, pageNo, pageSize);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-pageSize", pageSize);
        session.setAttribute("s-searcher", searcher);
        return "bpm/report/report_assigned_list";
    }

    @RequestMapping(value = "/assign.do", method = RequestMethod.POST)
    public String assign(TestingReportAssignModel assignModel,ModelMap model,HttpSession session)
    {
        service.assign(assignModel);
        return redirectList(model, session, "redirect:/bpm/report/waitAssign/tasks.do");
    }
    
    @RequestMapping(value = "/handle.do", method = RequestMethod.GET)
    @FormInputView
    public String handle(String id, ModelMap model,Integer redirectStatus)
    {
        ReportHandleModel data = service.getHandleModel(id);
        String semantic = "";
        if (Collections3.isNotEmpty(data.getTestingMethods()))
        {
            semantic = Collections3.getFirst(data.getTestingMethods()).getSemantic();
        }
        model.addAttribute("redirectStatus", redirectStatus);
        model.addAttribute("semantic", semantic);
        model.addAttribute("testingMethods", JSONObject.toJSON(data.getTestingMethods()).toString());
        model.addAttribute("data", data);
        model.addAttribute("taskId", id);
        return "bpm/report/report_solve";
    }
    
    @RequestMapping(value = "/getProcess.do", method = RequestMethod.GET)
    public String getProcess(ReportProcessModelArgs args, ModelMap model)
    {
        ReportProcessModel processModel = service.getProcessModel(args);
        model.addAttribute("semantic", args.getSemantic());
        model.addAttribute("processModel", processModel);
        model.addAttribute("taskId",args.getId());
        if(StringUtils.isNotBlank(processModel.getTechnicalFamilyGroupId())) {
            TechnicalAnalyTask technicalAnalyTask= technicalAnalyService.getTask(processModel.getTechnicalFamilyGroupId());
            if(technicalAnalyTask!=null) {
                model.addAttribute("sampleCode",technicalAnalyTask.getReceivedSampleCode());
                model.addAttribute("dataCode",technicalAnalyTask.getTestingAnalyDataCode());
                model.addAttribute("status",technicalAnalyTask.getStatus());
            }
        }

        model.addAttribute("analysisSampleId", processModel.getTechnicalFamilyGroupId());

        if(processModel.getTechnicalFamilyGroupId()!=null) {
            List<TechnicalAnalysisSampleModel> relationSamples = technicalAnalyService.getFamilySampleByFamilyId(processModel.getTechnicalFamilyGroupId());
            model.addAttribute("relationSamples", relationSamples);
            // 查出补提的记录
            List<TechnicalAnalysisAddVerify> addVerifyData = technicalAnalyService.getAddVerifyDataByFamilyId(processModel.getTechnicalFamilyGroupId());

            model.addAttribute("verifyDatas", addVerifyData);
            model.addAttribute("technicalFamilyGroupId",processModel.getTechnicalFamilyGroupId());
        }
        
        String url = "bpm/report/processInfo/report_testing";
        switch (args.getSemantic())
        {
            case "NGS":
            case "CAP-NGS":
            case "MULTI-PCR":
            case "Long-PCR":
            case "MLPA-TEST":
            case "DT-PCR":
            case "FLUO-TEST":
            case "SANGER-TEST":
                break;
            case "SANGER":
            case "PCR-NGS":
            case "QPCR":
            case "MLPA":
                url = "bpm/report/processInfo/report_verify";
                break;
            case "PICTURE":
                model.addAttribute("pictureIds", processModel.getPictureIds());
                url = "bpm/report/processInfo/report_pictures";
                break;
            default:
                break;
        }
        return url;
    }
    
    //
    //    @RequestMapping(value = "/handle.do", method = RequestMethod.POST)
    //    @FormSubmitHandler
    //    public String solve(ExtraSampleTaskHandleRequest request, ModelMap model, HttpSession session)
    //    {
    //        service.handle(request);
    //        return redirectList(model, session);
    //    }
    //
    //    private String redirectList(ModelMap model, HttpSession session)
    //    {
    //        model.clear();
    //        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
    //        model.addAttribute("pageSize", session.getAttribute("s-pageSize"));
    //        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
    //        return "redirect:/bpm/extraSample/tasks.do";
    //    }
    //
    @ResponseBody
    @RequestMapping(value = "/uploadZipReport", method = RequestMethod.POST)
    public Map<String, Object> historyShow(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Map<String, Object> result = new HashMap<>();
        //解压文件 订单编号_样本编号_样本名称_产品编号
        ReturnModel rm = service.unZip(uploadData);
        result.put("msg", rm);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/uploadZipReport0", method = RequestMethod.POST)
    public Map<String, Object> historyShow0(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Map<String, Object> result = new HashMap<>();
        //解压文件 订单编号_样本编号_样本名称_产品编号
        ReturnModel rm = service.unZip0(uploadData);
        result.put("msg", rm);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadData", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8;", "application/json;"})
    public String downloadDataPCRData(String reportId, HttpServletResponse response)
    {
        ReportDataModel sheet = service.getReportDataByIds(reportId);
        InputStream is = ReportTaskController.class.getResourceAsStream("/taskTemplate/REPORT_DATA.xlsx");
        return service.downloadData(is, sheet, reportId);
    }
    
    @ResponseBody
    @RequestMapping(value = "/dealReport", method = RequestMethod.POST)
    public Map<String, Object> dealReport(String path, String wordPath, String id) throws IOException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String s = service.dealReport(path, wordPath, id);
        map.put("msg", s);
        map.put("path", path);
        map.put("result", true);
        return map;
    }
    
    @RequestMapping(value = "/submitRedirectList.do", method = RequestMethod.POST)
    public String submitRedirectList(TestingReport request, ModelMap model, HttpSession session)
    {
        service.updateTestingReport(request);
        if (1 == request.getStatus())
        {
            return redirectTaskList(model, session);
        }
        else
        {
            return redirectIntegrationTaskList(model, session);
        }
    }
    
    private String redirectTaskList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/bpm/report/tasks.do";
    }
    
    private String redirectIntegrationTaskList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/bpm/report/integration/tasks.do";
    }
    
    @RequestMapping("/getSrcByPath.do")
    public void getSrcByPath(HttpServletRequest req, HttpServletResponse resp, String fileName, String wordName)
    {
        
        FileUtils.download(resp, fileName, wordName);
        
    }
    
    @RequestMapping("/upload.do")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        List<String> fileList = upload1(request, response);
        Map<String, Object> m = new HashMap<String, Object>();
        List<String> initView = new ArrayList<>();
        List initViewConfig = new ArrayList<>();
        String path = fileList.get(1);
        String wordPath = fileList.get(0);
        /*for (String file : fileList)
        {*/
        initView.add("<script>$('.file-zoom-content >').css('height','700px');$('.file-zoom-content').css('height','auto');</script>"
            + "<a style='display:flex' href='" + request.getContextPath() + "/bpm/report/getSrcByPath.do?fileName=" + path + "&wordName=" + wordPath
            + "'>下载</a>");
        
        Map<String, Object> conf = new HashMap<String, Object>();
        conf.put("caption", wordPath);
        conf.put("width", "120px");
        conf.put("key", path);
        conf.put("size", path.length());
        initViewConfig.add(conf);
        //}
        m.put("fileList", fileList);
        
        m.put("initialPreview", initView);
        m.put("initialPreviewConfig", initViewConfig);
        return m;
    }
    
    public List<String> upload1(HttpServletRequest request, HttpServletResponse response)
    {
        File localFile = null;
        File file = new File(ConfigManage.getkey("uploadPath"));
        List<String> fileList = new ArrayList<String>();
        
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (cmr.isMultipart(request))
        {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)(request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext())
            {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null)
                {
                    
                    SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
                    Date date = new Date();
                    int fileIndex = mFile.getOriginalFilename().lastIndexOf(".");
                    String sufffixname = "";
                    if (fileIndex != -1)
                    {
                        sufffixname = mFile.getOriginalFilename().substring(fileIndex, mFile.getOriginalFilename().length());
                    }
                    String fileName = Constant.REPORT_NAME_PREFIX + UUID.randomUUID().toString().replace("-", "") + sufffixname;
                    String wordName = dateFormater2.format(date) + "_" + mFile.getOriginalFilename();
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    String path = file.getAbsolutePath().toString() + "\\" + fileName;
                    localFile = new File(path);
                    try
                    {
                        mFile.transferTo(localFile);
                        FileUtils.uploadQiniu(localFile, fileName);
                        localFile.delete();
                    }
                    catch (IllegalStateException | IOException e)
                    {
                        e.printStackTrace();
                    }
                    fileList.add(wordName);
                    fileList.add(fileName);
                }
            }
        }
        return fileList;
    }
    
    @ResponseBody
    @RequestMapping("/deletePic.do")
    public boolean deletePic()
    {
        return true;
    }
    
    @RequestMapping(value = "/validateData")
    @ResponseBody
    public Integer validateData()
    {
        return service.validateData();
    }
    
    @RequestMapping(value = "/handleSangerVerify")
    @ResponseBody
    public String handleSangerVerify()
    {
        service.handleSangerVerify();
        return "success";
    }
    
    @RequestMapping(value = "/handleReportName")
    @ResponseBody
    public String handleReportName()
    {
        service.handleReportName();
        return "success";
    }
    
    @RequestMapping(value = "/findTestingReports")
    @ResponseBody
    public String findTestingReports()
    {
        return service.findTestingReports();
    }
    
    @RequestMapping(value = "/report_edit.do")
    @ResponseBody
    public String reportEdit(TestingReport report)
    {
        return service.reportEdit(report);
    }
    
    @RequestMapping(value = "/handle/save_testing_sample.do")
    @ResponseBody
    public String saveTestingSample(ReportProcessModel request)
    {
        service.saveTestingSample(request);
        String s = "";
        List<TestSampleModel> list = request.getTestSamples();
        if(Collections3.isNotEmpty(list))
        {
            for(TestSampleModel testSampleModel : list)
            {
                if(!testSampleModel.isQualified())
                {
                    s += testSampleModel.getId()+",";
                }
            }
        }
        if(StringUtils.isNotEmpty(s))
        {
            s = s.substring(0, s.length()-1);
        }
        return s;
    }
    
    @RequestMapping(value = "/handle/save_testing_result.do")
    @ResponseBody
    public String saveTestingResult(ReportProcessModel request)
    {
        List<ReportProcessResult> processResults = JSON.parseArray(request.getTestingResult() + "", ReportProcessResult.class);
        request.setProcessResults(processResults);
        service.saveTestingResult(request);
        return "";
    }
    
    @RequestMapping(value = "/report_upload_single.do", produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody
    public String uploadSingleFile(@RequestParam MultipartFile uploadData)
    {
        return service.uploadSingleFile(uploadData);
    }
    
    @RequestMapping(value = "/handle/save_pictures.do")
    @ResponseBody
    public String savePictures(TestingReport request)
    {
        return service.savePictures(request);
    }
    
    @RequestMapping(value = "/handle/save_verify_sample.do")
    @ResponseBody
    public List<Map<String,String>> saveVerifySample(ReportProcessModel request)
    {
        service.saveVerifySample(request);
        List<Map<String,String>> maplist = Lists.newArrayList();
        List<VerifySampleModel> list = request.getVerifySamples();
        if(Collections3.isNotEmpty(list))
        {
            for(VerifySampleModel verifySampleModel : list)
            {
                System.out.println("---qualified："+verifySampleModel.getId()+","+verifySampleModel.getChrLocation()+","+verifySampleModel.isQualified());
                if(!verifySampleModel.isQualified())
                {
                    Map<String,String> map = new HashMap<>();
                    map.put(verifySampleModel.getCombineCode() , verifySampleModel.getChrLocation());
                    maplist.add(map);
                }
            }
        }
        return maplist;
    }
    
    @RequestMapping(value = "/report_handle.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String solve(TestingReport request, ModelMap model, HttpSession session)
    {
        service.solve(request);
        String redirectUrl = "redirect:/bpm/report/integration/tasks.do";
        if (request.getStatus() == 1)
        {
            redirectUrl = "redirect:/bpm/report/tasks.do";
        }
        return redirectList(model, session, redirectUrl);
    }
    
    @RequestMapping(value = "/updateTestingReportRedundant")
    @ResponseBody
    public String updateTestingReportRedundant()
    {
        service.updateTestingReportRedundant();
        return "success";
    }

    @RequestMapping(value = "/updateTestingReportTechnicalMan")
    @ResponseBody
    public String updateTestingReportTechnicalMan()
    {
        service.updateTestingReportTechnicalMan();
        return "success";
    }

    @RequestMapping(value = "/updateTestingReportProductDutyMan")
    @ResponseBody
    public String updateTestingReportProductDutyMan()
    {
        service.updateTestingReportProductDutyMan();
        return "success";
    }

    @RequestMapping(value = "/updateTechnicalRecordToMap")
    @ResponseBody
    public String updateTechnicalRecordToMap()
    {
        service.updateTechnicalRecordToMap();
        return "success";
    }

    @RequestMapping(value = "/getTechnicalManByReport")
    @ResponseBody
    public List<UserDetailsModel> getTechnicalManByReport(String reportIds)
    {
        List<UserDetailsModel> result = service.getTechnicalManByReport(reportIds);
        return result;
    }

    @RequestMapping(value = "/updateDataAnalysisRecordToMap")
    @ResponseBody
    public String updateDataAnalysisRecordToMap()
    {
        service.updateDataAnalysisRecordToMap();
        return "success";
    }
    
    @RequestMapping(value = "/report_goBack.do", method = RequestMethod.GET)
    public String goBack(TestingReport request, ModelMap model, HttpSession session)
    {
        String redirectUrl = "redirect:/bpm/report/integration/tasks.do";
        if (request.getStatus() == 1)
        {
            redirectUrl = "redirect:/bpm/report/tasks.do";
        }
        return redirectList(model, session, redirectUrl);
    }

    @RequestMapping("/download.do")
    public void download(HttpServletRequest req, HttpServletResponse resp, String fileName)
    {
        FileUtils.downloadReportPic(req, resp, fileName);
    }

    //报告生成回调接口
    @RequestMapping("/generate/callback")
    @ResponseBody
    public String callback(ReportCallbackModel model)
    {
        ResponseModel responseModel =  service.reportCallbackModel(model);
        return JsonUtils.asJson(responseModel);
    }

    @RequestMapping(value = "/send.do",method = RequestMethod.POST)
    public String send(SendDataRequest request,ModelMap model, HttpSession session)
    {
        service.send(request);
        String redirectUrl = "redirect:/bpm/report/integration/tasks.do";
        if (request.getStatus() == 1)
        {
            redirectUrl = "redirect:/bpm/report/tasks.do";
        }
        return redirectList(model, session, redirectUrl);
    }

    //remark.do
    @RequestMapping(value = "/remark.do",method = RequestMethod.POST)
    public String remark(TestingReport request,ModelMap model, HttpSession session)
    {
        service.remark(request);
        String redirectUrl = "redirect:/bpm/report/tasks.do";
        if (request.getStatus() == 0)
        {
            redirectUrl = "redirect:/bpm/report/integration/tasks.do";
        }
        return redirectList(model, session, redirectUrl);
        
    }
    
    @RequestMapping(value = "/reportSendList.do")
    public String reportSendList(ReportSendSearcher searcher,@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,ModelMap model, HttpSession session)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<ReportSendCallBackModel> pagination = service.reportSendPaging(searcher);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "bpm/report/report_send_list";
        
    }
    
    @RequestMapping(value = "/reportSendView.do")
    public String reportSendView(String id, ModelMap model)
    {
        ReportSendDataModel dataModel = service.getSendData(id);
        String ss = JSONObject.toJSON(dataModel).toString();
        model.addAttribute("record", dataModel);
        List<TestingDataModel> testingDataList = dataModel.getTestingDatas();
        List<VerifyDataModel> verifyDataList = dataModel.getVerifyDatas();
        List<String> methods = Lists.newArrayList();
        Map<String,List<Map<String,String>>> methodDetails = new HashMap<>();
        if(Collections3.isNotEmpty(testingDataList))
        {
            for(TestingDataModel testingDataModel : testingDataList)
            {
                if(Collections3.isNotEmpty(methods))
                {
                    if(isHaving(methods, testingDataModel.getTestingMethod()))
                    {
                        methods.add(testingDataModel.getTestingMethod());
                    }
                    /*for(int i = 0;i<methods.size();i++)
                    {
                        String s = methods.get(i);
                        if(!s.equals(testingDataModel.getTestingMethod()))
                        {
                            methods.add(testingDataModel.getTestingMethod());
                        }
                    }*/
                }
                else
                {
                    methods.add(testingDataModel.getTestingMethod());
                }
                
                if(null != methodDetails)
                {
                    List<Map<String, String>> a = methodDetails.get(testingDataModel.getTestingMethod());
                    if(null == a)
                    {
                        a = testingDataModel.getDetails();
                    }
                    else
                    {
                        a.addAll(testingDataModel.getDetails());
                    }
                    methodDetails.put(testingDataModel.getTestingMethod(), a);
                }
                
            }
        }
        if(Collections3.isNotEmpty(verifyDataList))
        {
            for(VerifyDataModel verifyDataModel : verifyDataList)
            {
                if(Collections3.isNotEmpty(methods))
                {
                    if(isHaving(methods, verifyDataModel.getVerifyMethod()))
                    {
                        methods.add(verifyDataModel.getVerifyMethod());
                    }
                    /*for(int i = 0;i<methods.size();i++)
                    {
                        String s = methods.get(i);
                        if(!s.equals(verifyDataModel.getVerifyMethod()))
                        {
                            methods.add(verifyDataModel.getVerifyMethod());
                        }
                    }*/
                }
                else
                {
                    methods.add(verifyDataModel.getVerifyMethod());
                }
            }
        }
        model.addAttribute("methods", methods);
        model.addAttribute("methodDetails", methodDetails);
        model.addAttribute("verifyDataList",verifyDataList);
        return "bpm/report/report_send_view";
        
    }
    
    private boolean isHaving(List<String> methods,String s)
    {
        if(Collections3.isNotEmpty(methods))
        {
            for(String str : methods)
            {
                if(str.equals(s))
                {
                    return false;
                }
            }
        }
        return true;
        
    }
    
    //待分配数据查看
    @RequestMapping(value = "/waitAssignHandleView.do", method = RequestMethod.GET)
    @FormInputView
    public String waitAssignHandle(String id, Integer viewStatus,ModelMap model)
    {
        ReportHandleModel data = service.getHandleModel(id);
        data.setViewStatus(viewStatus);
        String semantic = "";
        if (Collections3.isNotEmpty(data.getTestingMethods()))
        {
            semantic = Collections3.getFirst(data.getTestingMethods()).getSemantic();
        }
        model.addAttribute("semantic", semantic);
        model.addAttribute("testingMethods", JSONObject.toJSON(data.getTestingMethods()).toString());
        model.addAttribute("data", data);
        return "report/reportReview/report_solve";
    }
    
    //已分配数据查看
    @RequestMapping(value = "/assignedHandleView.do", method = RequestMethod.GET)
    @FormInputView
    public String assignedHandle(String id, Integer viewStatus,ModelMap model)
    {
        ReportHandleModel data = service.getHandleModel(id);
        data.setViewStatus(viewStatus);
        String semantic = "";
        if (Collections3.isNotEmpty(data.getTestingMethods()))
        {
            semantic = Collections3.getFirst(data.getTestingMethods()).getSemantic();
        }
        model.addAttribute("semantic", semantic);
        model.addAttribute("testingMethods", JSONObject.toJSON(data.getTestingMethods()).toString());
        model.addAttribute("data", data);
        return "report/reportReview/report_solve";
    }
    
    @RequestMapping(value = "/generateReport.do",method = RequestMethod.POST)
    public String generateReport(GenerateReportRequest request,ModelMap model, HttpSession session)
    {
        service.generateReport(request);
        String redirectUrl = "redirect:/bpm/report/integration/tasks.do";
        return redirectList(model, session, redirectUrl);
    }
}
