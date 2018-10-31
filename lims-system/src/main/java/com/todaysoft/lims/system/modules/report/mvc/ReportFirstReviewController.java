package com.todaysoft.lims.system.modules.report.mvc;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.config.ConfigManage;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportHandleModel;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportProcessModel;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportProcessModelArgs;
import com.todaysoft.lims.system.modules.bpm.report.service.IReportService;
import com.todaysoft.lims.system.modules.report.model.FirstReviewSearcher;
import com.todaysoft.lims.system.modules.report.model.ReportReviewModel;
import com.todaysoft.lims.system.modules.report.model.TestingReport;
import com.todaysoft.lims.system.modules.report.model.TestingReportReview;
import com.todaysoft.lims.system.modules.report.service.IReportFirstReviewService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/bpm/report/firstReview")
public class ReportFirstReviewController extends BaseController
{
    @Autowired
    private IReportFirstReviewService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private IReportService reportService;
    
    @RequestMapping(value = "/list.do")
    public String paging(FirstReviewSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<TestingReport> pagination = service.paging(searcher);
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "report/reportReview/reportFirstReview_list";
    }
    
    @RequestMapping(value = "/preview.do", method = RequestMethod.GET)
    public void view(HttpServletRequest req, HttpServletResponse response, String fileName)
    {
        
    }
    
    @RequestMapping(value = "/review.do", method = RequestMethod.GET)
    @FormInputView
    public String reviewForward(String id, ModelMap model)
    {
        TestingReport data = service.getReport(id);
        model.addAttribute("data", data);
        return "report/reportReview/reportFirstReview_form";
    }
    
    @RequestMapping(value = "/review.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String review(TestingReport request, ModelMap model, HttpSession session)
    {
        AuthorizedUser user = userService.getByToken();
        TestingReportReview review =
            new TestingReportReview(request, TestingReportReview.REVIEW_FIRST, request.getReviewResult(), request.getReviewOpinion(), user.getId(),
                user.getName(), new Date());
        service.firstReview(review);
        return redirectList(model, session, "redirect:/bpm/report/firstReview/list.do");
    }
    
    @RequestMapping("/isCandownload.do")
    @ResponseBody
    public Integer isCandownload(HttpServletRequest req, HttpServletResponse resp, String fileName)
    {
        return FileUtils.isCandownLoad(fileName);
    }
    
    @RequestMapping("/download.do")
    public void download(HttpServletRequest req, HttpServletResponse resp, String fileName)
    {
        FileUtils.downloadCompatibility(req, resp, fileName);
    }
    
    @RequestMapping("/isCandownloadZip.do")
    @ResponseBody
    public Integer isCandownloadZip(HttpServletRequest req, HttpServletResponse resp, String keys)
    {
        List<String> fileNames = Arrays.asList(keys.split(","));
        for (String fileName : fileNames)
        {
            if(null != FileUtils.isCandownLoad(fileName))
            {
                if(1 == FileUtils.isCandownLoad(fileName))
                {
                    return 1;
                }
            }
        }
        return null;
    }
    
    @RequestMapping("/batchDownload.do")
    public void batchDownload(String keys, HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File("download_report_" + time + ".zip");
        String zipName = zipfile.getName();
        
        List<String> fileNames = Arrays.asList(keys.split(","));
        List<File> fileList = Lists.newArrayList();
        
        for (String fileName : fileNames)
        {
            //把word先下载在本地
            String url = FileUtils.downloadForFile(resp, fileName);
            fileList.add(new File(url));
        }
        String file = testSheetService.zipFiles(zipfile, fileList);
        
        //把zip以流的形式传到页面
        InputStream inputStream = null;
        OutputStream os = null;
        try
        {
            inputStream = new FileInputStream(file);
            os = new BufferedOutputStream(resp.getOutputStream());
            byte[] buffer = FileUtils.readInputStream(inputStream);
            
            resp.addHeader("Content-Disposition", "inline; filename=\"" + zipName + "\"");
            resp.setContentType("application/octet-stream; charset=utf-8");
            resp.setContentType("application/octet-stream");
            os.write(buffer);
            os.flush();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (null != os)
            {
                os.close();
            }
            if (null != inputStream)
            {
                inputStream.close();
            }
            zipfile.delete();
        }
    }
    
    @RequestMapping(value = "/handleView.do", method = RequestMethod.GET)
    @FormInputView
    public String handle(String id, Integer viewStatus,ModelMap model)
    {
        ReportHandleModel data = reportService.getHandleModel(id);
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
    
    @RequestMapping(value = "/getProcess.do", method = RequestMethod.GET)
    public String getProcess(ReportProcessModelArgs args, ModelMap model)
    {
        ReportProcessModel processModel = reportService.getProcessModel(args);
        model.addAttribute("semantic", args.getSemantic());
        model.addAttribute("processModel", processModel);
        if(processModel.getCnvModel()!=null&&processModel.getCnvModel().size()>0) {
            if(processModel.getCnvModel().get(0).getCnvAnalysisPic()!=null) {
                model.addAttribute("analysisSampleId", processModel.getCnvModel().get(0).getCnvAnalysisPic().getSampleTestId());
            }
        }
        if(processModel.getTechnicalFamilyGroupId()!=null) {
            model.addAttribute("technicalFamilyGroupId",processModel.getTechnicalFamilyGroupId());
        }


        String url = "report/reportReview/processInfo/report_testing";
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
                url = "report/reportReview/processInfo/report_verify";
                break;
            case "PICTURE":
                model.addAttribute("pictureIds", processModel.getPictureIds());
                url = "report/reportReview/processInfo/report_pictures";
                break;
            default:
                break;
        }
        return url;
    }
    
    @RequestMapping(value = "/report_goBack.do", method = RequestMethod.GET)
    public String goBack(Integer status, ModelMap model, HttpSession session)
    {
        String redirectUrl = "redirect:/bpm/report/firstReview/list.do";
        if (status == 2)
        {
            redirectUrl = "redirect:/bpm/report/secondReview/list.do";
        }
        if (status == 3)
        {
            redirectUrl = "redirect:/bpm/report/completedReport/list.do";
        }
        if(status == 4)
        {
            redirectUrl = "redirect:/bpm/report/waitAssign/tasks.do";
        }
        if(status == 5)
        {
            redirectUrl = "redirect:/bpm/report/assigned/list.do";
        }
        return redirectList(model, session, redirectUrl);
    }
    
private static final String DEFAULT_ENCODING = "GBK";// 编码
    
    private static final int PROTECTED_LENGTH = 51200;// 输入流保护 50KB
    
    @RequestMapping(value = "/wordToHtml.do", method = RequestMethod.GET)
    @ResponseBody
    public String wordToHtml(String fileName, ModelMap model, HttpServletResponse response)
        throws TransformerException, IOException, ParserConfigurationException
    {
        // session.getServletContext().getRealPath("/");
        File fileu = new File(ConfigManage.getkey("uploadPath"));
        String path = fileu.getAbsolutePath().toString() + "\\";
        String outPutName = fileName.replace(".docx", ".html");
        String targetFileName = path + outPutName;
        try
        {
            docxToHtml(fileName, targetFileName);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        File file = new File(targetFileName);
        if(file.exists())
        {
         // 转字符串
            InputStream input = new FileInputStream(file);
            // 字节数组
            byte[] bcache = new byte[2048];
            int readSize = 0;// 每次读取的字节长度
            int totalSize = 0;// 总字节长度
            ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
            try
            {
                // 一次性读取2048字节
                while ((readSize = input.read(bcache)) > 0)
                {
                    totalSize += readSize;
                    /*
                     * if (totalSize > PROTECTED_LENGTH) {
                     * throw new Exception("输入流超出50K大小限制");
                     * }
                     */
                    // 将bcache中读取的input数据写入infoStream
                    infoStream.write(bcache, 0, readSize);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // 输入流关闭
                input.close();
            }
            String s = replaceHtmlTag(infoStream.toString(DEFAULT_ENCODING), "img", "src", "src=\"/bpm/report/firstReview/downloadPic.do?fileName=", "\"");
            return s;
        }
        return null;
        
    }
    
    /*
     * docx转换为html
     * sourceFilePath:源word文件路径
     * targetFileName:生成的html文件路径
     */
    private static void docxToHtml(String sourceFilePath, String targetFileName)
        throws Exception
    {
        String imagePathStr = Paths.get(targetFileName).getParent().resolve("../tmp/image").toString();
        OutputStreamWriter outputStreamWriter = null;
        try
        {
            URL url = new URL(FileUtils.getDownloadUrl(sourceFilePath));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            if (conn.getResponseCode() != 200)
                throw new RuntimeException("文件不存在或请求url失败");
            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            inputStream = conn.getInputStream();
            XWPFDocument document = new XWPFDocument(inputStream);
            XHTMLOptions options = XHTMLOptions.create();
            // 存放图片的文件夹
            options.setExtractor(new FileImageExtractor(new File(imagePathStr)));
            // html中图片的路径
            options.URIResolver(new BasicURIResolver("../tmp/image"));
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(targetFileName), "UTF-8");
            XHTMLConverter xhtmlConverter = (XHTMLConverter)XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, outputStreamWriter, options);
            
        }
        finally
        {
            if (outputStreamWriter != null)
            {
                outputStreamWriter.close();
            }
        }
    }
    
    /**
     * 替换指定标签的属性和值
     * 
     * @param str 需要处理的字符串
     * @param tag 标签名称
     * @param tagAttrib 要替换的标签属性值
     * @param startTag 新标签开始标记
     * @param endTag 新标签结束标记
     * @return
     */
    public static String replaceHtmlTag(String str, String tag, String tagAttrib, String startTag, String endTag)
    {
        String regxpForTag = "<\\s*" + tag + "\\s+([^>]*)\\s*";
        String regxpForTagAttrib = tagAttrib + "=\\s*\"([^\"]+)\"";
        Pattern patternForTag = Pattern.compile(regxpForTag, Pattern.CASE_INSENSITIVE);
        Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib, Pattern.CASE_INSENSITIVE);
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result)
        {
            StringBuffer sbreplace = new StringBuffer("<" + tag + " ");
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
            if (matcherForAttrib.find())
            {
                String attributeStr = matcherForAttrib.group(1);
                // 传入图片名称
                String imgName = "";
                if (StringUtils.isNotEmpty(attributeStr))
                {
                    String[] ss = attributeStr.split("/");
                    if (ss.length > 0)
                    {
                        imgName = ss[ss.length - 1];
                    }
                }
                matcherForAttrib.appendReplacement(sbreplace, startTag + imgName + endTag);
            }
            matcherForAttrib.appendTail(sbreplace);
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }
    
    @RequestMapping("/downloadPic.do")
    public void downloadPic(HttpServletRequest req, HttpServletResponse resp, String fileName)
    {
        File fileu = new File(ConfigManage.getkey("uploadPath"));
        String path = fileu.getAbsolutePath().toString();
        if (StringUtils.isNotEmpty(path))
        {
            String a = String.valueOf(path.charAt(0));
            String dpath = a + ":\\tmp\\image\\word\\media\\" + fileName;
            download(resp, dpath);
        }
    }
    
    @RequestMapping(value = "/getReportReviewModelByReportId", method = RequestMethod.GET)
    public String getReportReviewModelByReportId(String reportId,String reportNo, ModelMap model)
    {
        List<ReportReviewModel> list = service.getReportReviewModelByReportId(reportId);
        model.addAttribute("reportNo", reportNo);
        model.addAttribute("data", list);
        return "order/report_review_show";
        
    }
}
