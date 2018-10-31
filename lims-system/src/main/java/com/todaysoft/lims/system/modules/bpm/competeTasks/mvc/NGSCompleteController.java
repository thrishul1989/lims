package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyDivisionSheetSample;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.division.BiologyAnnotationSheetViewModel;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.division.BiologyDivisionSheetViewModel;
import com.todaysoft.lims.system.modules.bpm.service.ITestingTaskSheetService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.DataColAndMutationDataModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CollectionCnvAnalysisPicResultList;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalAnalysisAddVerify;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.ITechnicalAnalyService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bcm.service.IConfigService;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.BiologyAnalySheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.CDNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.DNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.LibraryCaptureSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.LibraryQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.QtSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.RQTSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.SequencingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.TechnicalAnalySheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.INGSCompleteService;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateSheet;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bpm/completeTasks/ngs")
public class NGSCompleteController extends BaseController
{
    @Autowired
    private IUserService userService;
    
    @Autowired
    private INGSCompleteService ngsService;
    
    @Autowired
    private IConfigService configService;

    @Autowired
    private ITechnicalAnalyService service;

    
    @RequestMapping("/dna_extract.do")
    public String dnaExtractSheet(String id, ModelMap model)
    {
        DNAExtractSheet sheet = ngsService.getDNAExtractSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/dna_extract";
    }
    
    @RequestMapping("/dna_qc.do")
    public String dnaQcSheet(String id, ModelMap model)
    {
        DNAQcSheet sheet = ngsService.getDNAQcTestModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/dna_qc";
    }
    
    @RequestMapping("/cdna_extract.do")
    public String cdnaExtractSheet(String id, ModelMap model)
    {
        CDNAExtractSheet sheet = ngsService.getCDNAExtractSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/cdna_extract";
    }
    
    @RequestMapping("/cdna_qc.do")
    public String cdnaQcSheet(String id, ModelMap model)
    {
        CDNAQcSheet sheet = ngsService.getCDNAQcTestModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/cdna_qc";
    }
    
    @RequestMapping("/lib_create.do")
    public String libCreateSheet(String id, ModelMap model)
    {
        LibraryCreateSheet sheet = ngsService.getLibraryCreateSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/lib_create";
    }
    
    @RequestMapping("/lib_qc.do")
    public String libQcSheet(String id, ModelMap model)
    {
        LibraryQcSheet sheet = ngsService.getLibraryQcSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/lib_qc";
    }
    
    @RequestMapping("/lib_cap.do")
    public String libCapSheet(String id, ModelMap model)
    {
        LibraryCaptureSheet sheet = ngsService.getLibCapSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/lib_cap";
    }
    
    @RequestMapping("/rqt.do")
    public String RQTSheet(String id, ModelMap model)
    {
        RQTSheet sheet = ngsService.getRQTSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/rqt";
    }
    
    @RequestMapping("/pooling.do")
    public String PoolingSheet(String id, ModelMap model)
    {
        PoolingSubmitModel sheet = ngsService.getPoolingSubmitModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/pooling";
    }
    
    @RequestMapping("/qt.do")
    public String QtSheet(String id, ModelMap model)
    {
        QtSheet sheet = ngsService.getQtSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/qt";
    }
    
    @RequestMapping("/sequencing.do")
    public String SequencingSheet(String id, ModelMap model)
    {
        SequencingSheet sheet = ngsService.getSequencingSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/sequencing";
    }
    
    @RequestMapping("/ngsSequencing.do")
    public String ngsSequencingSheet(String id, ModelMap model)
    {
    	SequencingSubmitModel sheet = ngsService.getNgsSequencingSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/ngs_squecing";
    }
    
    
    @RequestMapping("/technical-analy.do")
    public String TechnicalAnalySheet(String id, ModelMap model)
    {
        TechnicalAnalySheet sheet = ngsService.getTechnicalAnalySheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/technical_analy";
    }
    
    @RequestMapping("/biology-analy.do")
    public String BiologyAnalySheet(String id, ModelMap model)
    {
        BiologyAnalySheet sheet = ngsService.getBiologyAnalySheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/ngs/biology_analy";
    }

    @RequestMapping("/biology-division.do")
    public String biologyDivision(String id, ModelMap model)
    {
        BiologyDivisionSheetViewModel data = ngsService.getBiologyDivisionSheet(id);

        if(null != data && Collections3.isNotEmpty(data.getSheetSampleList()))
        {
            // 原有的
            List<BiologyDivisionSheetSample> originalList = data.getSheetSampleList().stream().filter(x->!x.getIsAdd()).collect(Collectors.toList());
            // 后增加的
            List<BiologyDivisionSheetSample> laterList = data.getSheetSampleList().stream().filter(x->x.getIsAdd()).collect(Collectors.toList());

            model.addAttribute("originalList", originalList);
            model.addAttribute("laterList", laterList);
        }
        model.addAttribute("data", data);
        return "bpm/completeTasks/ngs/biology_division";
    }

    @RequestMapping("/biology-annotation.do")
    public String biologyAnnotation(String id, ModelMap model)
    {
        BiologyAnnotationSheetViewModel data = ngsService.getBiologyAnnotationSheet(id);
        model.addAttribute("data", data);
        return "bpm/completeTasks/ngs/biology_annotation";
    }

    @RequestMapping("/technical-analysis.do")
    public String technicalAnalysis(String id, ModelMap model) {

        String familyId = service.getFamilyId(id);
        List<TechnicalAnalysisTask> list = service.getTaskByFamilyId(familyId);
        model.addAttribute("familyTaskList", list);
        //补提验证数据
        List<TechnicalAnalysisAddVerify> addVerifyData = service.getAddVerifyDataByFamilyId(familyId);
        model.addAttribute("addVerifyData", warp(addVerifyData));
        // capcnv列表
        List<CollectionCnvAnalysisPicResultList> cnvModel = service.collectionCapcnvData(familyId);
        model.addAttribute("capcnv", cnvModel);
        // model.addAttribute("IsFamilyAnalysis", cnvModel.get(0).getCollectionCnvAnalysisResult().get(0).getIsFamilyAnalysis());
        // 突变列表

        DataColAndMutationDataModel data = service.searchMutationListByAnalsysiSampleId(familyId);
        model.addAttribute("ifCollectPage", 1);
        model.addAttribute("mutationData", data);
        model.addAttribute("pagination", data);
        model.addAttribute("analysisSampleId", id);
//        TechnicalAnalySheet technicalAnalySheet = service.getSheet(id);
        TestingSheet testingSheet = service.getTestingSheet(id);
        model.addAttribute("testingSheet",testingSheet);

        return "bpm/completeTasks/ngs/biology_analysis";
    }

    private List<TechnicalAnalysisAddVerify> warp(List<TechnicalAnalysisAddVerify> addVerifyData) {
        List<String> record;
        if (Collections3.isNotEmpty(addVerifyData)) {
            for (TechnicalAnalysisAddVerify d : addVerifyData) {
                record = new ArrayList<String>();
                String relation = d.getFamilyRelation();
                if (StringUtils.isEmpty(relation))
                    continue;
                String[] relationArr = relation.split("\\,");
                for (String r : relationArr) {
                    String[] arg = r.split("\\_");
                    record.add(StringUtils.isNotEmpty(arg) ? arg[0] : "");
                }
                d.setVerifySamplerCodeRelation(StringUtils.join(record, ","));
            }
        }
        return addVerifyData;
    }

    @RequestMapping("/downloadFile.do")
    public void download(HttpServletRequest req, HttpServletResponse resp,String dataUrl) {
        FileUtils.downloadAnnotationFile(req, resp, dataUrl);
    }
}
