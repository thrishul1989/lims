package com.todaysoft.lims.system.modules.bpm.tecanalys.mvc;

import static java.util.stream.Collectors.toList;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.exception.ServiceException;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.request.PhenoTypePointsRequest;
import com.todaysoft.lims.system.model.transation.VariableModel;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.DataArea;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderExaminee;
import com.todaysoft.lims.system.model.vo.order.OrderPrimarySample;
import com.todaysoft.lims.system.model.vo.order.OrderResearchSample;
import com.todaysoft.lims.system.model.vo.order.OrderSampleGroup;
import com.todaysoft.lims.system.model.vo.order.OrderSubsidiarySample;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BioInfoAnnotate;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBiologyAnalyService;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.TechnicalAnalySheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.INGSCompleteService;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.TechnicalAnalysisAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ClaimTemplateColumnForDoctor;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.DataColAndMutationDataModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.DownLoadAnatationModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.MutationFilterForm;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.MutationStatisticsModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.MutationStatisticsSearcher;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.SiteplotData;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignArgs;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyListSearcher;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseResult;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitForm;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitVerifyRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitVerifyTaskArgs;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisSampleModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisTaskRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.UploadEvidenceRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.UploadEvidenceRespModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.BiologyDivisionFastqData;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.BiologyFamilyRelationAnnotate;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CancelCollectionCnvAnalysisResult;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CapCnvResultData;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CnvAnalysisDataModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CnvAnalysisFamilyDataModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CollectionCnvAnalysisPicResultList;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CapCnvRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CollectionCapcnvPicRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CompareSampleReanalysisRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CompareSampleRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.GroupNameDetailsRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.MutationCollectionRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.MutationDataRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.SubmitCnvVerifyRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.SubmitTechnicalTaskRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.SvRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalAddVerifyRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalAnalysisAddVerify;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalCollectionRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalReportRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.response.CompareSample;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.response.DrawPictureModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.ITechnicalAnalyService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IAreaService;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.util.OSSFileLoadUtil;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/testing")
public class TecAnalysisController extends BaseController
{
    @Autowired
    private ITechnicalAnalyService service;
    
    @Autowired
    private INGSCompleteService ngsService;
    
    @Autowired
    private IMlpaDataDataService dataService;
    
    @Autowired
    private IMetadataSampleService sampleService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IBiologyAnalyService biologyAnalyService;
    
    @RequestMapping("/technicalAnalysis_tasks.do")
    public String getAssignableList(TechnicalAnalyAssignableTaskSearcher searcher, ModelMap model, HttpSession session)
    {
        List<TechnicalAnalyTask> tasks = service.getAssignableList(searcher);
        session.setAttribute("s-searcher", searcher);
        model.addAttribute("searcher", searcher);
        // 一级界面，测序编号去重
        
        Set<TechnicalAnalyTask> set = new HashSet<>(tasks);
        
        List<TechnicalAnalyTask> sortList = Lists.newArrayList(set);
        // 按加急标识降序
        sortList.sort(Comparator.comparing(TechnicalAnalyTask::getIfUrgent)
            .reversed()
            .thenComparing(TechnicalAnalyTask::getPlannedFinishDate, Comparator.nullsLast(Comparator.naturalOrder())));
        model.addAttribute("tasks", sortList);
        
        return "bpm/process/tecanalys_list";
    }
    
    @RequestMapping("/technicalAnalysis_tasks_detail.do")
    public String getAssignableListDetail(TechnicalAnalyAssignableTaskSearcher searcher, ModelMap model, HttpSession session)
    {
        List<TechnicalAnalyTask> tasks = service.getAssignableListDetail(searcher);
        session.setAttribute("s-searcher", searcher);
        model.addAttribute("searcher", searcher);
        
        tasks.sort(Comparator.comparing(TechnicalAnalyTask::getResubmitCount)
            .reversed()
            .thenComparing(TechnicalAnalyTask::getPlannedFinishDate)
            .thenComparing(TechnicalAnalyTask::getOrderCode)
            .thenComparing(TechnicalAnalyTask::getStartTime));
        // 按加急标识降序
        tasks.sort(Comparator.comparing(TechnicalAnalyTask::getIfUrgent).reversed());
        model.addAttribute("tasks", tasks);
        
        return "bpm/process/tecanalys_list_detail";
    }
    
    /**
     * 样本信息
     * @param orderId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/technicalanaly_sampleinfo.do")
    public String technicalanalySampleinfo(TechnicalAnalysisTaskRequest searcher, ModelMap model, HttpSession session)
    {
        String orderId = "";
        List<TechnicalAnalysisTask> task = service.searchTechnicalAnalysisTask(searcher);
        if (Collections3.isNotEmpty(task))
        {
            orderId = Collections3.getFirst(task).getOrderId();
        }
        
        Order order = orderService.getById(orderId);
        
        if (StringUtils.isNotEmpty(order.getOwnerId()))
        {
            Customer cc = customerService.get(order.getOwnerId());
            order.setOwner(cc);
        }
        
        if (Collections3.isNotEmpty(order.getOrderExamineeList()))
        {
            OrderExaminee o = order.getOrderExamineeList().get(0);
            
            if (StringUtils.isNotEmpty(o.getOrigin()))
            {
                String result = exchangeArea(o.getOrigin());
                DataArea target = areaService.get(Integer.parseInt(result));
                if (StringUtils.isNotEmpty(target))
                {
                    o.setOriginFullName(target.getFullName());
                }
                
            }
            
            model.addAttribute("orderExaminee", o);
        }
        else
        {
            model.addAttribute("orderExaminee", new ArrayList<OrderExaminee>());
        }
        
        resolveSamples(order);
        
        model.addAttribute("record", order);
        model.addAttribute("orderJson", JSONObject.toJSONString(order));
        model.addAttribute("searcher", searcher);
        
        return "bpm/technicalanaly/sampleinfo";
    }
    
    private void resolveSamples(Order order)
    {
        if (StringUtils.isNotEmpty(order.getOrderPrimarySample()))
        {
            List<OrderPrimarySample> psample = order.getOrderPrimarySample();
            for (OrderPrimarySample o : psample)
            {
                MetadataSample metaSample = sampleService.get(o.getSampleTypeId());
                if (StringUtils.isNotEmpty(metaSample))
                {
                    o.setSamplereceivingUnit(metaSample.getReceivingUnit());
                    o.setSampleTypeName(metaSample.getName());
                }
            }
        }
        if (StringUtils.isNotEmpty(order.getOrderSubsidiarySample()))
        {
            
            List<OrderSubsidiarySample> ssample = order.getOrderSubsidiarySample();
            for (OrderSubsidiarySample o : ssample)
            {
                MetadataSample metaSample = sampleService.get(o.getSampleTypeId());
                if (StringUtils.isNotEmpty(metaSample))
                {
                    o.setSamplereceivingUnit(metaSample.getReceivingUnit());
                    o.setSampleTypeName(metaSample.getName());
                }
            }
        }
        if (StringUtils.isNotEmpty(order.getOrderSampleGroup()))
        {
            List<OrderSampleGroup> ssample = order.getOrderSampleGroup();
            for (OrderSampleGroup o : ssample)
            {
                if (StringUtils.isNotEmpty(o.getOrderResearchSample()))
                {
                    for (OrderResearchSample ors : o.getOrderResearchSample())
                    {
                        MetadataSample metaSample = sampleService.get(ors.getSampleTypeId());
                        if (StringUtils.isNotEmpty(metaSample))
                        {
                            ors.setSamplereceivingUnit(metaSample.getReceivingUnit());
                            ors.setSampleTypeName(metaSample.getName());
                        }
                    }
                    
                }
            }
        }
    }
    
    private static String exchangeArea(String i)
    {
        String result = "1";
        String[] change = i.split(",");
        if (StringUtils.isNotEmpty(change))
        {
            result = change[change.length - 1];
        }
        return result;
    }
    
    /**
     * 质量控制
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/technicalanaly_qualitycontrol.do")
    public String technicalanalyQualitycontrol(MutationStatisticsSearcher searcher, ModelMap model, HttpSession session)
    {
        List<MutationStatisticsModel> models = Lists.newArrayList();
        models = service.getQualitycontrolDataByaAnalysisId(searcher);
        model.addAttribute("models", models);
        model.addAttribute("searcher", searcher);
        return "bpm/technicalanaly/qualitycontrol";
    }
    
    @RequestMapping("/tecanalys_assign.do")
    @FormInputView
    public String getAssignModel(TechnicalAnalyAssignArgs args, ModelMap model)
    {
        TechnicalAnalyAssignModel data = service.getAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/assign/tecanalys_assign_form";
    }
    
    @RequestMapping("/tecanalys_assign_submit.do")
    @FormSubmitHandler
    public String assign(TechnicalAnalyAssignRequest request, ModelMap model, HttpSession session)
    {
        service.assign(request);
        return redirectList(model, session, "redirect:technicalAnalysis_tasks.do");
        
    }
    
    @RequestMapping("/tecanalys_test.do")
    @FormInputView
    public String getTecAnalysisSubmitModel(String id, ModelMap model)
    {
        TechnicalAnalySubmitModel sheet = service.getSubmitModel(id);
        
        if (sheet.isVerified())
        {
            if (!CollectionUtils.isEmpty(sheet.getTasks()))
            {
                sheet.getTasks().sort(Comparator.comparing(TechnicalAnalyTask::getQualifiedType).reversed());
            }
            
            model.addAttribute("sheet", sheet);
            return "bpm/test/tecanalys_submit_form";
        }
        else
        {
            model.addAttribute("sheet", sheet);
            return "bpm/test/tecanalys_test_form";
        }
    }
    
    @RequestMapping("/technical_analy_verify.do")
    @FormSubmitHandler
    public String submitVerify(TechnicalAnalySubmitVerifyRequest request, ModelMap model)
    {
        service.verify(request);
        
        boolean existsQualified = false;
        
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            for (TechnicalAnalySubmitVerifyTaskArgs task : request.getTasks())
            {
                if (task.isQualified())
                {
                    existsQualified = true;
                    break;
                }
            }
        }
        
        if (existsQualified)
        {
            model.clear();
            model.addAttribute("id", request.getId());
            return "redirect:/testing/tecanalys_test.do";
        }
        else
        {
            model.clear();
            return "redirect:/testing/test_tasks.do";
        }
    }
    
    @RequestMapping("/technical_analy_submit.do")
    @FormSubmitHandler
    public String submit(TechnicalAnalySubmitForm data, HttpSession session, ModelMap model)
    {
        TechnicalAnalyParseResult parseResult = (TechnicalAnalyParseResult)session.getAttribute("TECHNICAL_ANALY_RESULE");
        session.removeAttribute("TECHNICAL_ANALY_RESULE");
        service.submit(data, parseResult);
        model.clear();
        return "redirect:/testing/test_tasks.do";
    }
    
    @RequestMapping("/technical-analy/upload_result.do")
    @FormInputView
    public String uploadResult(String id, Integer isReTechnical, MultipartFile file, ModelMap model, HttpSession session)
    {
        TechnicalAnalyParseResult parseResult = (TechnicalAnalyParseResult)session.getAttribute("TECHNICAL_ANALY_RESULE");
        if (null != parseResult)
        {
            DataAnalysisParseModel parseModel = new DataAnalysisParseModel();
            parseModel.setUploadDir(parseResult.getUploadDir());
            // 说明是重新上传的，清掉本地数据
            dataService.deleteLocalFileDir(parseModel);
        }
        
        TechnicalAnalyParseResult result = service.parse(id, file, isReTechnical);
        session.setAttribute("TECHNICAL_ANALY_RESULE", result);
        model.addAttribute("id", id);
        model.addAttribute("result", result);
        return "bpm/test/technical_analy_result";
    }
    
    @RequestMapping("/reTecanalys_test.do")
    @FormInputView
    public String reTecAnalysisSubmitModel(String id, ModelMap model)
    {
        TechnicalAnalySubmitModel sheet = service.getSubmitModel(id);
        
        if (!CollectionUtils.isEmpty(sheet.getTasks()))
        {
            sheet.getTasks().sort(Comparator.comparing(TechnicalAnalyTask::getQualifiedType).reversed());
        }
        
        model.addAttribute("sheet", sheet);
        
        TechnicalAnalySheet completeSheet = ngsService.getTechnicalAnalySheet(id);
        model.addAttribute("completeSheet", completeSheet);
        
        return "bpm/test/re_tecanalys_submit_form";
        
    }
    
    @ResponseBody
    @RequestMapping("/technicalAnalysis/exportSummary.do")
    public String exportSummary(TechnicalAnalyAssignableTaskSearcher searcher, ModelMap model)
    {
        List<TechnicalAnalyTask> tasks = service.getListBySequencingCode(searcher);
        String result = "false";
        if (Collections3.isNotEmpty(tasks))
        {
            result = service.downloadBySequencingCode(searcher.getSequencingCode(), tasks);
        }
        return result;
    }
    
    @RequestMapping("/technicalAnalysis/generateData.do")
    void generateData()
    {
        service.generateData();
    }
    
    @RequestMapping("/viewPic.do")
    public void download(HttpServletRequest req, HttpServletResponse resp, String fileName)
    {
        FileUtils.download(resp, fileName);
    }
    
    @RequestMapping(value = "/technicalanaly_mutationlist.do")
    public String technicalanalyMutationlist(ModelMap model, HttpSession session)
    {
        
        return "bpm/technicalanaly/mutationlist";
    }
    
    @RequestMapping(value = "/technicalanaly_capcnvanalysis.do")
    public String technicalanalyCapcnvanalysis(CapCnvRequest request, String sampleId, ModelMap model, HttpSession session)
    {
        String analysisSampleId = request.getAnalysisSampleId();
        // 判断是否是家系分析
        boolean ifFamilyAnalysis = service.ifFamilyAnalysis(analysisSampleId);
        if (ifFamilyAnalysis)
        {
            CnvAnalysisFamilyDataModel data = service.getFamilyCapcnvRecords(request);
            model.addAttribute("data", data);
            model.addAttribute("pagination", data.getCapCnvDataPager());
            if (data.getBiologyDivisionFastqData() != null)
            {
                model.addAttribute("sampleCode", data.getBiologyDivisionFastqData().getSampleCode());
                model.addAttribute("sampleId", data.getBiologyDivisionFastqData().getSampleId());
            }
        }
        else
        {
            CnvAnalysisDataModel data = service.getRecords(request);
            model.addAttribute("data", data);
            model.addAttribute("pagination", data.getCapCnvDataPagination());
            model.addAttribute("sampleCode", data.getBiologyDivisionFastqData().getSampleCode());
            model.addAttribute("sampleId", data.getBiologyDivisionFastqData().getSampleId());
        }
        
        model.addAttribute("analysisSampleId", request.getAnalysisSampleId());
        
        model.addAttribute("firstUUID", UUID.randomUUID().toString().replaceAll("-", ""));
        //model.addAttribute("sampleCodeTemp", sampleCodeTemp);
        
        session.setAttribute("search", request);
        if (!ifFamilyAnalysis)
        {
            return "bpm/technicalanaly/capcnvanalysis";
        }
        else
        {
            return "bpm/technicalanaly/capcnvfamilyanalysis";
        }
    }
    
    @RequestMapping(value = "/technicalanaly_svdata.do")
    public String technicalanalySvdata(SvRequest request, ModelMap model, HttpSession session)
    {
        Pagination<String> pagination = service.technicalanalySvdata(request);
        List<List<String>> list = Lists.newArrayList();
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (String s : pagination.getRecords())
            {
                List<String> tdList = Lists.newArrayList();
                JSONObject temp = JSONObject.parseObject(s);
                tdList.add(null == temp.get("sv_type") ? "" : temp.get("sv_type").toString());
                tdList.add(null == temp.get("left_gene") ? "" : temp.get("left_gene").toString());
                tdList.add(null == temp.get("left_region") ? "" : temp.get("left_region").toString());
                tdList.add(null == temp.get("left_chr") ? "" : temp.get("left_chr").toString());
                tdList.add(null == temp.get("left_pos") ? "" : temp.get("left_pos").toString());
                tdList.add(null == temp.get("left_strand") ? "" : temp.get("left_strand").toString());
                tdList.add(null == temp.get("left_soft-clipped_reads") ? "" : temp.get("left_soft-clipped_reads").toString());
                tdList.add(null == temp.get("left_coverage") ? "" : temp.get("left_coverage").toString());
                tdList.add(null == temp.get("right_gene") ? "" : temp.get("right_gene").toString());
                tdList.add(null == temp.get("right_region") ? "" : temp.get("right_region").toString());
                tdList.add(null == temp.get("right_chr") ? "" : temp.get("right_chr").toString());
                tdList.add(null == temp.get("right_pos") ? "" : temp.get("right_pos").toString());
                tdList.add(null == temp.get("right_strand") ? "" : temp.get("right_strand").toString());
                tdList.add(null == temp.get("right_soft-clipped_reads") ? "" : temp.get("right_soft-clipped_reads").toString());
                tdList.add(null == temp.get("right_coverage") ? "" : temp.get("right_coverage").toString());
                tdList.add(null == temp.get("consensus_sequences") ? "" : temp.get("consensus_sequences").toString());
                list.add(tdList);
            }
        }
        model.addAttribute("pagination", pagination);
        model.addAttribute("data", list);
        
        return "bpm/technicalanaly/svdata";
    }
    
    @RequestMapping(value = "/analysisRegioncout.jsp")
    public String regioncout(SvRequest request, ModelMap model, HttpSession session)
    {
        Pagination<SiteplotData> pagination = service.SiteplotDataSearch(request);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", request);
        
        return "bpm/technicalanaly/analysis_regioncout";
        
    }
    
    @RequestMapping(value = "/selecttecanalys.do")
    public String selectTecanalys(TechnicalAnalyListSearcher searcher, ModelMap model, HttpSession session)
    {
        String returnUrl = "";
        try
        {
            switch (searcher.getFlag())
            {
            //样本信息
                case TechnicalAnalyListSearcher.SAMPLEINFO:
                    returnUrl = "bpm/technicalanaly/sampleinfo";
                    break;
                //质控
                case TechnicalAnalyListSearcher.QUALITYCONTROL:
                    returnUrl = "bpm/technicalanaly/qualitycontrol";
                    break;
                //突变列表
                case TechnicalAnalyListSearcher.MUTATIONLIST:
                    returnUrl = "bpm/technicalanaly/mutationlist";
                    break;
                //CapCNV分析
                case TechnicalAnalyListSearcher.CAPCNVANALYSIS:
                    returnUrl = "bpm/technicalanaly/capcnvanalysis";
                    break;
                //SV数据
                case TechnicalAnalyListSearcher.SVDATA:
                    returnUrl = "bpm/technicalanaly/svdata";
                    break;
                //突变列表
                case TechnicalAnalyListSearcher.REGIONCOUT:
                    returnUrl = "bpm/technicalanaly/regioncout";
                    break;
                default:
                    //样本信息
                    returnUrl = "bpm/technicalanaly/sampleinfo";
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return returnUrl;
    }
    
    @RequestMapping(value = "/technicalanaly_drawPicture.do")
    @ResponseBody
    public String drawPicture(String ids, String sampleCode, String sampleAnalysisId)
    {
        DrawPictureModel model = service.getCapCnvPicture(ids, sampleCode, sampleAnalysisId);
        String picUrl = "";
        if (null != model && null != model.getData())
        {
            picUrl = model.getData().getCap();
        }
        return picUrl;
    }
    
    // 下载筛选后数据
    @RequestMapping(value = "/technicalanaly_downloadFilterData.do")
    @ResponseBody
    public String downloadFilterData(@RequestBody CapCnvRequest request)
    {
        return service.downloadFilterData(request);
    }
    
    @ResponseBody
    @RequestMapping(value = "/technicalanaly_downloadFile", method = RequestMethod.POST)
    public void downloadFile(HttpServletResponse response, String formValue)
    {
        service.downloadData(response, formValue);
    }
    
    /************************** 下面是数据分析 *************************************************************/
    
    @RequestMapping(value = "/technicalanaly.do")
    public String getTechnicalAnalysisAssignableList(TechnicalAnalysisAssignableTaskSearcher searcher, HttpSession session, HttpServletRequest request, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        searcher.setStatusItems(Lists.newArrayList("1", "3", "6"));
        User user = userService.getUserByToken();
        searcher.setUserId(user.getId());
        Pagination<TechnicalAnalysisTask> pagination = service.getTechnicalAnalysisAssignableList(searcher, pageNo);
        // 按家系分组
        Map<String, List<TechnicalAnalysisTask>> res = new LinkedHashMap<String, List<TechnicalAnalysisTask>>();
        for (TechnicalAnalysisTask task : pagination.getRecords())
        {
            List<TechnicalAnalysisTask> et = service.searchErrorTechnicalTask(task.getFamilyGroupId());
            if (Collections3.isNotEmpty(et))
            {
                task.setResubmitCount(et.size());
                task.setOtestingSequencingCode(et.get(0).getTestingSequencingCode());
            }
            else
            {
                task.setOtestingSequencingCode(task.getTestingSequencingCode());
            }
            
            if (res.containsKey(task.getFamilyGroupId()))
            {
                res.get(task.getFamilyGroupId()).add(task);
            }
            else
            {
                List<TechnicalAnalysisTask> list = new ArrayList<TechnicalAnalysisTask>();
                list.add(task);
                res.put(task.getFamilyGroupId(), list);
            }
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        model.addAttribute("res", res);
        
        return "bpm/process/tecanalysis_task_list";
    }
    
    @RequestMapping(value = "/claimTechnicalAnalysis.do")
    @ResponseBody
    public Integer claimTechnicalAnalysis(String familyGroupId, ModelMap model)
    {
        return service.claimTechnicalAnalysis(familyGroupId);
        
    }
    
    @RequestMapping(value = "/filter.jsp")
    public String filter(String familyGroupId, ModelMap model, HttpServletRequest httpServletRequest, HttpSession session)
    {
        
        List<ClaimTemplateColumnForDoctor> list = service.getClaimTemplateColumnList();
        
        model.addAttribute("data", list);
        MutationFilterForm request = new MutationFilterForm();
        User user = userService.getUserByToken();
        request.setDoctorId(user.getId());
        List<String> slist = service.queryRecentFilterList(request);
        
        model.addAttribute("recentFilterList", slist);
        model.addAttribute("familyGroupId", familyGroupId);
        model.addAttribute("id", httpServletRequest.getParameter("id"));
        model.addAttribute("sampleCode", httpServletRequest.getParameter("sampleCode"));
        model.addAttribute("dataCode", httpServletRequest.getParameter("dataCode"));
        model.addAttribute("recheckTag", httpServletRequest.getParameter("recheckTag"));
        
        if (Collections3.isNotEmpty(slist))
        {
            return redirectList(model,
                session,
                "redirect:tab.do?dataCode=" + httpServletRequest.getParameter("dataCode") + "&sampleCode=" + httpServletRequest.getParameter("sampleCode")
                    + "&analysisFamilyId=" + familyGroupId + "&id=" + httpServletRequest.getParameter("id") + "&ifRecheck="
                    + httpServletRequest.getParameter("recheckTag"));
        }
        return "bpm/technicalanaly/mutation_filter_form";
    }
    
    @RequestMapping(value = "/saveFilterCreate.jsp", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveFilterCreate(MutationFilterForm request, ModelMap model, HttpSession session)
    {
        User user = userService.getUserByToken();
        request.setDoctorId(user.getId());
        service.saveFilterCreate(request);
        return true;
    }
    
    // 每次确认分析都先保存、参数去mongo最近一次
    @RequestMapping(value = "/analysisMutation.jsp")
    public String showMutationList(MutationDataRequest request, ModelMap model, HttpSession session)
    {
        // 带出订单下所有附属样本
        List<TechnicalAnalysisSampleModel> relationSamples = service.getFamilySampleByFamilyId(request.getAnalysisFamilyId());
        // 查出补提的记录
        List<TechnicalAnalysisAddVerify> addVerifyData = service.getAddVerifyDataByFamilyId(request.getAnalysisFamilyId());
        
        User user = userService.getUserByToken();
        request.setDoctorId(user.getId());
        DataColAndMutationDataModel data = service.queryMutationData(request);
        model.addAttribute("pagination", data);
        model.addAttribute("searcher", request);
        List<ClaimTemplateColumnForDoctor> list = service.getClaimTemplateColumnList();
        MutationFilterForm mrequest = new MutationFilterForm();
        mrequest.setDoctorId(user.getId());
        List<String> slist = service.queryRecentFilterList(mrequest);
        model.addAttribute("recentFilterList", slist);
        model.addAttribute("filterData", list);
        model.addAttribute("analysisSampleId", request.getAnalysisFamilyId());
        model.addAttribute("relationSamples", relationSamples);
        model.addAttribute("verifyDatas", addVerifyData);
        model.addAttribute("sampleCode", request.getSampleCode());
        model.addAttribute("dataCode", request.getDataCode());
        model.addAttribute("ifRecheck", request.getIfRecheck());
        model.addAttribute("id", request.getId());
        
        Boolean tag = service.checkTechnicalAnalysisHasHpoTask(request.getAnalysisFamilyId());
        model.addAttribute("annotateStatus", tag);
        return "bpm/technicalanaly/analysis_mutation";
    }
    
    @RequestMapping("/cancelCollection.do")
    @ResponseBody
    public Integer cancelMutationCollection(MutationCollectionRequest request)
    {
        service.cancelMutationCollection(request);
        return 1;
    }
    
    @RequestMapping(value = "/collection.jsp")
    @ResponseBody
    public Integer collection(@RequestBody TechnicalCollectionRequest request, ModelMap model, HttpSession session)
    {
        return service.collection(request);
        
    }
    
    //保存补提数据
    @RequestMapping(value = "/saveAddVerify.do", method = RequestMethod.POST)
    @ResponseBody
    public Integer saveAddVerify(TechnicalAddVerifyRequest request)
    {
        service.saveAddVerify(request);
        return 1;
    }
    
    //保存补提数据 起验证流程
    @RequestMapping(value = "/saveAddVer.do", method = RequestMethod.POST)
    @ResponseBody
    public Integer saveAddVer(TechnicalAddVerifyRequest request)
    {
        service.saveAddVerifyNew(request);//保存补提验证
        
        return 1;
    }
    
    // 下载筛选后数据
    @RequestMapping(value = "/downloadFilterData.jsp", method = RequestMethod.POST)
    @ResponseBody
    public String dowloadData(String analysisSampleId, ModelMap model, HttpServletRequest request, HttpSession session) throws Exception
    {
        User user = userService.getUserByToken();
        MutationDataRequest req = new MutationDataRequest();
        req.setAnalysisSampleId(analysisSampleId);
        req.setDoctorId(user.getId());
        return service.downloadFilterData(req, "mutation_filter", "xls");
    }
    
    /** 确认复核，启动验证流程*/
    /*@RequestMapping(value = "/startAnalsyisSchedule.jsp")
    public String startAnalsyisSchedule(String analysisFamilyId, ModelMap model, HttpSession session)
    {
        service.startAnalsyisSchedule(analysisFamilyId);
        return redirectList(model, session, "redirect:technicalanaly.do");
        // return "bpm/technicalanaly/analysis_mutation";
    }*/
    
    @RequestMapping(value = "/tab.do")
    public String tab(MutationDataRequest request, ModelMap model, HttpServletRequest httpServletRequest)
    {
        model.addAttribute("flag", TechnicalAnalyListSearcher.MUTATIONLIST);
        model.addAttribute("analysisSampleId", request.getAnalysisFamilyId());
        model.addAttribute("sampleCode", httpServletRequest.getParameter("sampleCode"));
        model.addAttribute("dataCode", request.getDataCode());
        /* model.addAttribute("dataCode", httpServletRequest.getParameter("dataCode"));*/
        model.addAttribute("request", request);
        // 根据家系id查询本次家系分析的样本list
        String familyAnalysisId = request.getAnalysisFamilyId();
        List<TechnicalAnalysisTask> list = service.getTaskByFamilyId(familyAnalysisId);
        
        list = list.stream().filter(x -> !x.getStatus().equals(7)).collect(toList());
        model.addAttribute("familyTaskList", list);
        return "bpm/technicalanaly/tecanalys_tab";
    }
    
    @RequestMapping(value = "/submitTechnicalTask.do")
    public String submitTechnicalTask(String familyGroupId, HttpSession session, ModelMap model, HttpServletRequest httpServletRequest)
    {
        service.submitTechnicalTask(familyGroupId);
        return redirectList(model, session, "redirect:technicalanaly.do");
    }
    
    @RequestMapping(value = "/submitTechnicalTaskAjax.do")
    @ResponseBody
    public void submitTechnicalTaskAjax(String familyGroupId, HttpSession session, ModelMap model, HttpServletRequest httpServletRequest)
    {
        service.submitTechnicalTask(familyGroupId);
        
    }
    
    @RequestMapping(value = "submitRecheckTechnicalTask.do")
    @ResponseBody
    public void submitRecheckTechnicalTask(SubmitTechnicalTaskRequest request, HttpSession session, ModelMap model, HttpServletRequest httpServletRequest)
    {
        VariableModel result = service.submitRecheckTechnicalTask(request);
        //放入后台维护,保证事务一致性
        if ("5".equals(request.getStatus()) && StringUtils.isNotEmpty(result.getScheduleIds()))
        {
            service.startAnalsyisSchedule(result);
            System.out.println("systme发送生成报告消息...");
        }
        //  return redirectList(model, session, "redirect:technical_recheck_list.do");
    }
    
    /**
     * 数据分析 复核
     * @param searcher
     * @param pageNo
     * @param model
     * @return
     */
    @RequestMapping(value = "/technical_recheck_list.do")
    public String getTechnicalAnalysisRecheckList(TechnicalAnalysisAssignableTaskSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        searcher.setStatusItems(Lists.newArrayList("4"));
        User user = userService.getUserByToken();
        searcher.setProductPrincipalId(user.getId()); //产品负责人
        Pagination<TechnicalAnalysisTask> pagination = service.getTechnicalAnalysisAssignableList(searcher, pageNo);
        // 按家系分组
        Map<String, List<TechnicalAnalysisTask>> res = new LinkedHashMap<String, List<TechnicalAnalysisTask>>();
        for (TechnicalAnalysisTask task : pagination.getRecords())
        {
            List<TechnicalAnalysisTask> et = service.searchErrorTechnicalTask(task.getFamilyGroupId());
            if (Collections3.isNotEmpty(et))
            {
                task.setResubmitCount(et.size());
                task.setOtestingSequencingCode(et.get(0).getTestingSequencingCode());
            }
            else
            {
                task.setOtestingSequencingCode(task.getTestingSequencingCode());
            }
            if (res.containsKey(task.getFamilyGroupId()))
            {
                res.get(task.getFamilyGroupId()).add(task);
            }
            else
            {
                List<TechnicalAnalysisTask> list = new ArrayList<TechnicalAnalysisTask>();
                list.add(task);
                res.put(task.getFamilyGroupId(), list);
            }
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        model.addAttribute("res", res);
        return "bpm/process/tecanalysis_task_recheck_list";
    }
    
    /**
     * 创建打分任务
     * @param model
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/phenoTypePoints", method = RequestMethod.POST)
    @ResponseBody
    public Integer phenoTypePoints(ModelMap model, HttpSession session, @RequestBody PhenoTypePointsRequest request)
    {
        Integer res = service.updatePhenoTypePoints(request);
        return res;
    }
    
    @RequestMapping("/getEvidenceByMongoId.do")
    @ResponseBody
    public String getEvidenceByMongoId(String mongoId, ModelMap model, HttpSession session)
    {
        String result = service.getEvidenceByMongoId(mongoId);
        System.out.println(result);
        return result;
    }
    
    /**
     * 致病性分级，更新分级
     * @param evidence
     * @param evidenceValue
     * @param desc
     * @param mongoId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/uploadEvidence.do")
    @ResponseBody
    public UploadEvidenceRespModel uploadEvidence(@RequestParam("evidence") String evidence, @RequestParam("evidenceValue") String evidenceValue, @RequestParam("desc") String desc, @RequestParam("mongoId") String mongoId, ModelMap model, HttpSession session)
    {
        UploadEvidenceRequest request = new UploadEvidenceRequest();
        request.setEvidence(Arrays.asList(evidence.split("\\,")));
        request.setEvidenceValue(Arrays.asList(evidenceValue.split("\\,")));
        request.setDesc(Arrays.asList(desc.split("\\,")));
        request.setMongoId(mongoId);
        UploadEvidenceRespModel resp = service.uploadEvidence(request);
        return resp;
        
    }
    
    /**
     * 突变数据详情
     * @param objectId
     * @return
     */
    @RequestMapping("/getMutationDetail.do")
    @ResponseBody
    public String getMutationDetail(String objectId)
    {
        String db = service.getMutationDetail(objectId);
        return db;
    }
    
    @RequestMapping(value = "/technicalanaly_saveAnalysisCapCnvResult.do")
    @ResponseBody
    public String saveAnalysisCapCnvResult(CapCnvResultData capCnvResultData)
    {
        service.saveAnalysisCapCnvResult(capCnvResultData);
        return "success";
    }
    
    @RequestMapping("/technicalanaly_compareSample.do")
    @FormInputView
    public String compareSample(CompareSampleRequest request, ModelMap model)
    {
        List<CompareSample> compareSamples = service.getCompareSample(request);
        model.addAttribute("data", compareSamples);
        model.addAttribute("searcher", request);
        return "bpm/technicalanaly/compare_sample";
    }
    
    @RequestMapping(value = "/technicalanaly_compareSampleReanalysis.do", method = RequestMethod.POST)
    @ResponseBody
    public String compareSampleReanalysis(@RequestBody CompareSampleReanalysisRequest request)
    {
        service.compareSampleReanalysis(request);
        return "success";
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public void downloadFilterFile(HttpServletResponse response, String formValue)
    {
        download(response, formValue);
    }
    
    // 查看分组列数据
    @RequestMapping(value = "/getGroupDetails.jsp")
    @ResponseBody
    public Map<String, String> getGroupDetails(GroupNameDetailsRequest request, HttpSession session)
    {
        Map<String, String> map = service.getGroupDetails(request);
        return map;
    }
    
    @RequestMapping("/technicalanaly_collectionCapcnv.do")
    @FormInputView
    public String collectionCapcnv(String sampleAnalysisId, String cnvAnalysisResultId, ModelMap model)
    {
        model.addAttribute("cnvAnalysisResultId", cnvAnalysisResultId);
        model.addAttribute("sampleAnalysisId", sampleAnalysisId);
        List<TechnicalAnalysisSampleModel> relationSamples = service.getFamilySampleByFamilyId(sampleAnalysisId);
        model.addAttribute("relationSamples", relationSamples);
        return "bpm/technicalanaly/collection_capcnv";
    }
    
    @RequestMapping("/technicalanaly_submitCnvVerify.do")
    @FormSubmitHandler
    @ResponseBody
    public String submitCnvVerify(SubmitCnvVerifyRequest submitCnvVerifyRequest)
    {
        service.submitCnvVerify(submitCnvVerifyRequest);
        return submitCnvVerifyRequest.getCnvAnalysisResultId();
    }
    
    @RequestMapping(value = "/technicalanaly_cancelSubmitCnvVerify.do", method = RequestMethod.POST)
    @ResponseBody
    public String technicalanaly_cancelSubmitCnvVerify(CancelCollectionCnvAnalysisResult request, HttpServletRequest httpServletRequest)
    {
        service.cancelSubmitCnvVerify(request);
        return "{\"message\":\"success\"}";
    }
    
    // 下载筛选后数据
    @RequestMapping(value = "/technicalanaly_downloadFamilyFilterData.do")
    @ResponseBody
    public String downloadFamilyFilterData(@RequestBody CapCnvRequest request)
    {
        return service.downloadFamilyFilterData(request);
    }
    
    @RequestMapping("/technicalanaly_collectionCapcnvPic.do")
    @ResponseBody
    public Integer collectionCapcnvPic(CollectionCapcnvPicRequest request)
    {
        service.collectionCapcnvPic(request);
        return 1;
    }
    
    @RequestMapping(value = "getFamilyAnnotate.jsp", method = RequestMethod.GET)
    @ResponseBody
    public List<BioInfoAnnotate> getFamilyAnnotate(String mongoId, ModelMap model, HttpSession session)
    {
        List<BioInfoAnnotate> list = service.getAnnotateByfamily(mongoId);
        return list;
        
    }
    
    @RequestMapping("/myCollection.do")
    public String myCollection(String analysisSampleId, String ifRecheck, ModelMap model)
    {
        List<TechnicalAnalysisTask> list = service.getTaskByFamilyId(analysisSampleId);
        model.addAttribute("familyTaskList", list);
        //补提验证数据
        List<TechnicalAnalysisAddVerify> addVerifyData = service.getAddVerifyDataByFamilyId(analysisSampleId);
        model.addAttribute("addVerifyData", warp(addVerifyData));
        // capcnv列表
        List<CollectionCnvAnalysisPicResultList> cnvModel = service.collectionCapcnvData(analysisSampleId);
        model.addAttribute("capcnv", cnvModel);
        // 突变列表
        
        DataColAndMutationDataModel data = service.searchMutationListByAnalsysiSampleId(analysisSampleId);
        model.addAttribute("pagination", data);
        model.addAttribute("analysisSampleId", analysisSampleId);
        model.addAttribute("ifRecheck", ifRecheck);
        return "bpm/technicalanaly/anaysis_collection";
    }
    
    @RequestMapping("/getTaskByFamilyId.do")
    @ResponseBody
    public TechnicalAnalysisTask getTaskByFamilyId(String analysisSampleId, ModelMap model)
    {
        List<TechnicalAnalysisTask> list = service.getTaskByFamilyId(analysisSampleId);
        return Collections3.getFirst(list);
    }
    
    private List<TechnicalAnalysisAddVerify> warp(List<TechnicalAnalysisAddVerify> addVerifyData)
    {
        List<String> record;
        if (Collections3.isNotEmpty(addVerifyData))
        {
            for (TechnicalAnalysisAddVerify d : addVerifyData)
            {
                record = new ArrayList<String>();
                String relation = d.getFamilyRelation();
                if (StringUtils.isEmpty(relation))
                    continue;
                String[] relationArr = relation.split("\\,");
                for (String r : relationArr)
                {
                    String[] arg = r.split("\\_");
                    record.add(StringUtils.isNotEmpty(arg) ? arg[0] : "");
                }
                d.setVerifySamplerCodeRelation(StringUtils.join(record, ","));
            }
        }
        return addVerifyData;
    }
    
    @RequestMapping("/deleteVerifyDataById.do")
    public String deleteVerifyDataById(String id, String analysisSampleId, ModelMap model, HttpSession session)
    {
        service.deleteVerifyDataById(id);
        return redirectList(model, session, "redirect:myCollection.do?analysisSampleId=" + analysisSampleId);
    }
    
    @RequestMapping(value = "/technicalReportOperate.do")
    @ResponseBody
    public void technicalReportOperate(TechnicalReportRequest request, ModelMap model)
    {
        service.technicalReportOperate(request);
    }
    
    @RequestMapping(value = "/checkTechnicalAnalysisIsFinished.do")
    @ResponseBody
    public Boolean checkTechnicalAnalysisIsFinished(String analysisSampleId, ModelMap model)
    {
        Boolean tag = service.checkTechnicalAnalysisIsFinished(analysisSampleId);
        return tag;
    }
    
    @RequestMapping(value = "/downloadAnatationFile.jsp")
    public String downloadAnatationFile(String analysisSampleId, ModelMap model, HttpSession session) throws Exception
    {
        List<DownLoadAnatationModel> data = new ArrayList<DownLoadAnatationModel>();
        BioInfoAnnotate res = biologyAnalyService.getByAnalaysisSampleId(analysisSampleId);
        BiologyDivisionFastqData divisionData = biologyAnalyService.getDataById(analysisSampleId);
        BiologyFamilyRelationAnnotate familyRelationAnnotate = biologyAnalyService.getFamilyRelationAnnotate(analysisSampleId);
        if (null != divisionData)
        {
            
            String fileOnePathName = divisionData.getFileQOne();// fastq1
            if (StringUtils.isNotEmpty(fileOnePathName))
            {
                DownLoadAnatationModel qOne = null;
                if (fileOnePathName.contains(",") || fileOnePathName.contains(";"))
                {
                    String fileArr[] = fileOnePathName.split(",");
                    if (null != fileArr && fileArr.length > 0)
                    {
                        for (int i = 0; i < fileArr.length; i++)
                        {
                            qOne = new DownLoadAnatationModel();
                            String fileTemp = fileArr[i];
                            String fileOne[] = fileTemp.split(";");
                            if (null != fileOne && fileOne.length == 2)
                            {
                                qOne.setFileName(fileOne[1]);
                                qOne.setFilePath(fileOne[0]);
                                qOne.setFileSize(getFileSize(fileOne[0]));
                                data.add(qOne);
                            }
                            else
                            {
                                System.err.println("user uploader file excepton.");
                                throw new IllegalStateException();
                            }
                        }
                    }
                }
                else
                {
                    qOne = new DownLoadAnatationModel();
                    qOne.setFileName(getRealFileName(fileOnePathName));
                    qOne.setFileSize(getFileSize(fileOnePathName));
                    qOne.setFilePath(fileOnePathName);
                    data.add(qOne);
                }
            }
            
            String fileTwoPathName = divisionData.getFileQTwo();// fastq2
            if (StringUtils.isNotEmpty(fileTwoPathName))
            {
                DownLoadAnatationModel qTwo = null;
                if (fileTwoPathName.contains(",") || fileTwoPathName.contains(";"))
                {
                    String fileArr[] = fileTwoPathName.split(",");
                    if (null != fileArr && fileArr.length > 0)
                    {
                        for (int i = 0; i < fileArr.length; i++)
                        {
                            qTwo = new DownLoadAnatationModel();
                            String fileOne[] = fileArr[i].split(";");
                            if (null != fileOne && fileOne.length == 2)
                            {
                                qTwo.setFileName(fileOne[1]);
                                qTwo.setFilePath(fileOne[0]);
                                qTwo.setFileSize(getFileSize(fileOne[0]));
                                data.add(qTwo);
                            }
                            else
                            {
                                System.err.println("user uploader file excepton.");
                                throw new IllegalStateException();
                            }
                        }
                    }
                }
                else
                {
                    qTwo = new DownLoadAnatationModel();
                    qTwo.setFileName(getRealFileName(fileTwoPathName));
                    qTwo.setFileSize(getFileSize(fileTwoPathName));
                    qTwo.setFilePath(fileTwoPathName);
                    data.add(qTwo);
                }
            }
            
            DownLoadAnatationModel fiveTwo = new DownLoadAnatationModel();//md5
            fiveTwo.setFileName(getRealFileName(divisionData.getMdFiveTwo()));
            fiveTwo.setFileSize(getFileSize(divisionData.getMdFiveTwo()));
            fiveTwo.setFilePath(divisionData.getMdFiveTwo());
            data.add(fiveTwo);
            
            DownLoadAnatationModel fiveOne = new DownLoadAnatationModel();
            fiveOne.setFileName(getRealFileName(divisionData.getMdFiveOne()));
            fiveOne.setFileSize(getFileSize(divisionData.getMdFiveOne()));
            fiveOne.setFilePath(divisionData.getMdFiveOne());
            data.add(fiveOne);
            
            //总的质控文件
            DownLoadAnatationModel totalFile = new DownLoadAnatationModel();
            totalFile.setFileName(getRealFileName(divisionData.getTotalFile()));
            totalFile.setFileSize(getFileSize(divisionData.getTotalFile()));
            totalFile.setFilePath(divisionData.getTotalFile());
            data.add(totalFile);
            
        }
        
        if (null != res)
        {
            // 查询文件大小
            DownLoadAnatationModel cnv = new DownLoadAnatationModel();
            cnv.setFileName(getRealFileName(res.getCnv()));
            cnv.setFileSize(getFileSize(res.getCnv()));
            cnv.setFilePath(res.getCnv());
            DownLoadAnatationModel sv = new DownLoadAnatationModel();
            sv.setFileName(getRealFileName(res.getSv()));
            sv.setFileSize(getFileSize(res.getSv()));
            sv.setFilePath(res.getSv());
            
            DownLoadAnatationModel make = new DownLoadAnatationModel();
            make.setFileName(getRealFileName(res.getMakeCptyNumberVariation()));
            make.setFileSize(getFileSize(res.getMakeCptyNumberVariation()));
            make.setFilePath(res.getMakeCptyNumberVariation());
            
            DownLoadAnatationModel region = new DownLoadAnatationModel();
            region.setFileName(getRealFileName(res.getRegioncountDmdexon()));
            region.setFileSize(getFileSize(res.getRegioncountDmdexon()));
            region.setFilePath(res.getRegioncountDmdexon());
            DownLoadAnatationModel staticDemaon = new DownLoadAnatationModel();
            staticDemaon.setFileName(getRealFileName(res.getStatisticsDmdexon()));
            staticDemaon.setFileSize(getFileSize(res.getStatisticsDmdexon()));
            staticDemaon.setFilePath(res.getStatisticsDmdexon());
            DownLoadAnatationModel vcf = new DownLoadAnatationModel();
            vcf.setFileName(getRealFileName(res.getVcfDmdexon()));
            vcf.setFileSize(getFileSize(res.getVcfDmdexon()));
            vcf.setFilePath(res.getVcfDmdexon());
            DownLoadAnatationModel bam = new DownLoadAnatationModel();
            bam.setFileName(getRealFileName(res.getBamBam()));
            bam.setFileSize(getFileSize(res.getBamBam()));
            bam.setFilePath(res.getBamBam());
            DownLoadAnatationModel bai = new DownLoadAnatationModel();
            bai.setFileName(getRealFileName(res.getBamBai()));
            bai.setFileSize(getFileSize(res.getBamBai()));
            bai.setFilePath(res.getBamBai());
            
            DownLoadAnatationModel svJson = new DownLoadAnatationModel();
            svJson.setFileName(getRealFileName(res.getSvJson()));
            svJson.setFileSize(getFileSize(res.getSvJson()));
            svJson.setFilePath(res.getSvJson());
            
            DownLoadAnatationModel snvJson = new DownLoadAnatationModel();
            snvJson.setFileName(getRealFileName(res.getSnvJson()));
            snvJson.setFileSize(getFileSize(res.getSnvJson()));
            snvJson.setFilePath(res.getSnvJson());
            data.add(svJson);
            data.add(snvJson);
            data.add(cnv);
            data.add(sv);
            data.add(make);
            data.add(region);
            data.add(staticDemaon);
            data.add(vcf);
            data.add(bam);
            data.add(bai);
        }
        
        if (null != familyRelationAnnotate)
        {
            // 查询文件大小
            DownLoadAnatationModel cnv = new DownLoadAnatationModel();
            cnv.setFileName(getRealFileName(familyRelationAnnotate.getCnv()));
            cnv.setFileSize(getFileSize(familyRelationAnnotate.getCnv()));
            cnv.setFilePath(familyRelationAnnotate.getCnv());
            
            DownLoadAnatationModel make = new DownLoadAnatationModel();
            make.setFileName(getRealFileName(familyRelationAnnotate.getMakeCptyNumberVariation()));
            make.setFileSize(getFileSize(familyRelationAnnotate.getMakeCptyNumberVariation()));
            make.setFilePath(familyRelationAnnotate.getMakeCptyNumberVariation());
            
            DownLoadAnatationModel snvJson = new DownLoadAnatationModel();
            snvJson.setFileName(getRealFileName(familyRelationAnnotate.getSnvJson()));
            snvJson.setFileSize(getFileSize(familyRelationAnnotate.getSnvJson()));
            snvJson.setFilePath(familyRelationAnnotate.getSnvJson());
            
            DownLoadAnatationModel sv = new DownLoadAnatationModel();
            sv.setFileName(getRealFileName(familyRelationAnnotate.getSv()));
            sv.setFileSize(getFileSize(familyRelationAnnotate.getSv()));
            sv.setFilePath(familyRelationAnnotate.getSv());
            
            DownLoadAnatationModel svJson = new DownLoadAnatationModel();
            svJson.setFileName(getRealFileName(familyRelationAnnotate.getSvJson()));
            svJson.setFileSize(getFileSize(familyRelationAnnotate.getSvJson()));
            svJson.setFilePath(familyRelationAnnotate.getSvJson());
            
            DownLoadAnatationModel region = new DownLoadAnatationModel();
            region.setFileName(getRealFileName(familyRelationAnnotate.getRegioncountDmdexon()));
            region.setFileSize(getFileSize(familyRelationAnnotate.getRegioncountDmdexon()));
            region.setFilePath(familyRelationAnnotate.getRegioncountDmdexon());
            
            DownLoadAnatationModel staticDemaon = new DownLoadAnatationModel();
            staticDemaon.setFileName(getRealFileName(familyRelationAnnotate.getStatisticsDmdexon()));
            staticDemaon.setFileSize(getFileSize(familyRelationAnnotate.getStatisticsDmdexon()));
            staticDemaon.setFilePath(familyRelationAnnotate.getStatisticsDmdexon());
            data.add(svJson);
            data.add(cnv);
            data.add(sv);
            data.add(make);
            data.add(region);
            data.add(staticDemaon);
            data.add(snvJson);
        }
        model.addAttribute("data", data);
        return "bpm/technicalanaly/anatationFile";
        
    }
    
    @RequestMapping("/downloadPic.jsp")
    public void downloadPic(HttpServletRequest req, HttpServletResponse resp, String filePath) throws Exception
    {
        try
        {
            
            download(resp, filePath, getRealFileName(filePath));
        }
        catch (Exception e)
        {
            throw new ServiceException("101011", "找不到文件");
            
        }
        
    }
    
    public void download(HttpServletResponse response, String path, String fileName) throws Exception
    {
        // 构造URL
        OutputStream os = null;
        InputStream is = null;
        InputStream inputStream = null;
        try
        {
            
            inputStream = OSSFileLoadUtil.getInputStreanmFromALiyun(path);
            System.out.println("获取流成功");
            
            // 以流的形式下载文件
            System.out.println("1");
            // 清空response
            response.reset();
            
            fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            response.addHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
            response.setContentType("application/octet-stream; charset=utf-8");
            System.out.println("2");
            os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            
            BufferedInputStream bins = new BufferedInputStream(inputStream);// 放到缓冲流里面
            BufferedOutputStream bouts = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            int bytesRead = 0;
            
            // 开始向网络传输文件流
            while ((bytesRead = bins.read(buffer, 0, 8192)) != -1)
            {
                bouts.write(buffer, 0, bytesRead);
            }
            bouts.flush();
            inputStream.close();
            bins.close();
            bouts.close();
            
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != is)
                {
                    is.close();
                }
                os.close();
                inputStream.close();
                
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
        }
        
    }
    
    public String getFileSize(String fileName) throws Exception
    {
        try
        {
            BigDecimal bigd = new BigDecimal(0);
            
            if (StringUtils.isNotEmpty(fileName))
            {
                bigd = new BigDecimal(OSSFileLoadUtil.getFileLengthFromALiyun(fileName));
            }
            
            return bigd.divide(new BigDecimal(1024), 2, BigDecimal.ROUND_HALF_UP).toString() + "kb";
        }
        catch (Exception e)
        {
            return null;
        }
        
    }
    
    public String getRealFileName(String filePath)
    {
        if (StringUtils.isNotEmpty(filePath))
        {
            String[] arrays = filePath.split("\\/");
            return arrays[arrays.length - 1];
        }
        return "";
        
    }
    
}
