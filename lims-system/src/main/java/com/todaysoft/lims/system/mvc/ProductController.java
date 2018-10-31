package com.todaysoft.lims.system.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.searcher.ProductSearcher;
import com.todaysoft.lims.system.model.vo.*;
import com.todaysoft.lims.system.model.vo.disease.Disease;
import com.todaysoft.lims.system.model.vo.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethodSearcher;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.bcm.service.ITestingMethodService;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.ClaimTemplate;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IClaimTemplateService;
import com.todaysoft.lims.system.modules.report.model.ReportTemplate;
import com.todaysoft.lims.system.modules.report.service.IReportTemplateService;
import com.todaysoft.lims.system.modules.smm.model.Dict;
import com.todaysoft.lims.system.modules.smm.model.UserArchive;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.*;
import com.todaysoft.lims.system.service.adapter.request.ProductAmountRule;
import com.todaysoft.lims.system.service.adapter.request.ProductCreateRequest;
import com.todaysoft.lims.system.service.adapter.request.ProductProbeRequest;
import com.todaysoft.lims.system.service.security.AccountDetails;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {
    @Autowired
    private IProductService service;

    @Autowired
    private IDictService dictService;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IMetadataSampleService sampleService;

    @Autowired
    private IProbeService iProbeService;

    @Autowired
    private ITestingMethodService testingMethodService;

    @Autowired
    private IDiseaseGeneService diseaseGeneService;

    @Autowired
    private ITestingTypeService testingTypeService;

    @Autowired
    private IReportTemplateService reportTemplateService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IClaimTemplateService claimTemplateService;


    @ResponseBody
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public String download(String sheetId) {

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File("product-disease" + ".zip");
        return service.download(zipfile);
    }

    @RequestMapping("/list.do")
    public String list(ProductSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session) {

        /**数据权限开始  。。。。。。。。。。。。。。。。。*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails) principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("PRODUCT_LIST")) {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("PRODUCT_LIST"));
        }

        /**数据权限结束  。。。。。。。。。。。。。。。。。*/

        Pagination<Product> pagination = service.paging(searcher, pageNo, 10);
        //查询所有营销中心
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        List<TestingType> subTestingTypeList = Lists.newArrayList();
        if (null != searcher.getTestingType()) {
            if (StringUtils.isNotEmpty(searcher.getTestingType().getId())) {
                TestingType request = new TestingType();
                request.setParentId(searcher.getTestingType().getId());
                subTestingTypeList = testingTypeService.testingSubtypeList(request);
            }
        }

        model.addAttribute("probeList", iProbeService.paging(new ProbeSeacher(), 1, 1000).getRecords());

        model.addAttribute("subTestingTypeList", subTestingTypeList);
        model.addAttribute("searcher", searcher).addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "product/product_list";
    }

    @RequestMapping(value = "/unique/{code}")
    public ResponseEntity<Boolean> unique(String code) {
        return new ResponseEntity<Boolean>(service.unique(code), HttpStatus.OK);
    }

    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    public String createPage(ModelMap model) {
        // 获取所有检测方法
        List<TestingMethod> checkManagementList = testingMethodService.list(new TestingMethodSearcher("", 1));

        //获取检测方法的标识semantic
        for (TestingMethod testingMethod: checkManagementList) {
            testingMethod.setSemantic(testingMethodService.get(testingMethod.getId()).getSemantic());
        }
        model.addAttribute("checkManagementList", checkManagementList);

        //查询所有营销中心
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        return "product/product_form";
    }

    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public String create(ProductCreateRequest data, ModelMap model, HttpSession session) {
        List<ProductProbeRequest> probes = JSON.parseArray(data.getProbes() + "", ProductProbeRequest.class);

        data.setProductAmountRuleList(JSON.parseArray(data.getProductAmountRule() + "", ProductAmountRule.class));
        data.setProductProbeList(probes);
        // 组装疾病和基因数据
        List<Disease> diseaseList = JSON.parseArray(data.getProductDisease() + "", Disease.class);
        List<Gene> geneList = JSON.parseArray(data.getProductGenes() + "", Gene.class);
        if (Collections3.isNotEmpty(diseaseList)) {

            List<ProductDisease> productDiseaseList = new ArrayList<ProductDisease>();
            for (Disease disease : diseaseList) {
                //检查疾病编号是否存在
                Disease d = diseaseGeneService.getDiseaseByCode(disease.getCode());
                if (null == d) {
                    //excel有误
                    return redirectList(model, session, "redirect:list.do");
                }
                ProductDisease productDisease = new ProductDisease();

                productDisease.setDisease(d);
                productDiseaseList.add(productDisease);
            }
            data.setProductDiseaseList(productDiseaseList);
        }
        if (Collections3.isNotEmpty(geneList)) {

            List<ProductGene> productGeneList = new ArrayList<ProductGene>();
            for (DiseaseGeneFormRequest gene : geneList) {
                //检查基因编号是否存在
                Gene d = diseaseGeneService.getGeneByCode(gene.getCode());
                if (null == d) {
                    //excel有误
                    return redirectList(model, session, "redirect:list.do");
                }
                ProductGene productGene = new ProductGene();

                productGene.setGene(d);
                productGeneList.add(productGene);
            }
            data.setProductGeneList(productGeneList);
        }
        service.insert(data);
        return redirectList(model, session, "redirect:list.do");
    }

    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    public String modifyPage(String id, ModelMap model) {
        Product data = service.getProduct(id);
        model.addAttribute("data", data);

        List<ReportTemplate> reportTemplateList = Lists.newArrayList();
        if (null != data.getReportTemplate() && StringUtils.isNotEmpty(data.getReportTemplate().getId())) {
            ReportTemplate temp = reportTemplateService.get(data.getReportTemplate().getId());
            reportTemplateList.add(temp);
        }
        model.addAttribute("reportTemplateList", JSONObject.toJSON(reportTemplateList).toString());

        List<Dict> classfys = dictService.getEntries("DETECTION_CLASSFY");
        model.addAttribute("classfys", classfys);

        // 获取所有检测方法
        List<TestingMethod> checkManagementList = testingMethodService.list(new TestingMethodSearcher("", 1));
        model.addAttribute("checkManagementList", checkManagementList);
        // 获取负责人
        List<UserArchive> principalList = new ArrayList<UserArchive>();
        for (ProductPrincipal prin : data.getProductPrincipalList()) {
            prin.getPrincipal().getArchive().setId(prin.getPrincipal().getId());
            principalList.add(prin.getPrincipal().getArchive());
        }

        List<UserArchive> productLeaderList = Lists.newArrayList();
        if (StringUtils.isNotEmpty(data.getProductLeader())) {
            UserDetailsModel user = userService.getUserByID(data.getProductLeader());
            if (null != user) {
                UserArchive ua = new UserArchive();
                ua.setId(user.getId());
                ua.setName(user.getArchive().getName());
                productLeaderList.add(ua);
            }
        }
        model.addAttribute("productLeaderList", JSONObject.toJSON(productLeaderList).toString());

        //查询所有营销中心
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        //获取小类TestingType
        TestingType tt = new TestingType();
        tt.setParentId(data.getTestingType().getId());
        List<TestingType> testingSubtypeList = testingTypeService.testingSubtypeList(tt);
        model.addAttribute("testingSubtypeList", testingSubtypeList);
        //获取所有可用探针
        List<Probe> probeList = iProbeService.list(new ProbeSeacher());
        model.addAttribute("probeList", JSONObject.toJSON(probeList).toString());
        if (null != data.getProductDuty()) {
            data.getProductDuty().getArchive().setId(data.getProductDuty().getId());
            model.addAttribute("productDuty", JSONObject.toJSON(data.getProductDuty().getArchive()).toString());
        }

        if (Collections3.isNotEmpty(data.getProductAmountRuleList())) {
            List<ProductAmountRule> ruleList = data.getProductAmountRuleList();
            for (ProductAmountRule rule : ruleList) {
                rule.setAmount(rule.getAmount().divide(new BigDecimal(100)));
            }

            model.addAttribute("geneList", JSONObject.toJSON(ruleList).toString());
        }

        model.addAttribute("principalList", JSONObject.toJSON(principalList).toString());
        model.addAttribute("testingMethodList", JSONObject.toJSON(data.getProductTestingMethodList()).toString());
        model.addAttribute("prductDiseaseList", JSONObject.toJSON(data.getProductDiseaseList()).toString());
        model.addAttribute("productGeneList", JSONObject.toJSON(data.getProductGeneList()).toString());
        data.setTestingGenes(data.getProductGeneList().size());
        return "product/update_product";
    }

    @RequestMapping("/getTestingList.do")
    @ResponseBody
    public List<TestingMethod> getCheckManagerList(String id) {
        Product data = service.getProduct(id);
        List<TestingMethod> result = new ArrayList<TestingMethod>();
        for (ProductTestingMethod testingMettod : data.getProductTestingMethodList()) {
            result.add(testingMettod.getTestingMethod());
        }
        return result;
    }

    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    public String modify(ProductCreateRequest data, ModelMap model, HttpSession session) {
        if (StringUtils.isNotEmpty(data.getProbes())) {
            List<ProductProbeRequest> probes = JSON.parseArray(data.getProbes() + "", ProductProbeRequest.class);
            data.setProductProbeList(probes);
        }

        data.setProductAmountRuleList(JSON.parseArray(data.getProductAmountRule() + "", ProductAmountRule.class));
        // 组装疾病和基因数据
        List<Disease> diseaseList = JSON.parseArray(data.getProductDisease() + "", Disease.class);
        List<Gene> geneList = JSON.parseArray(data.getProductGenes() + "", Gene.class);
        if (Collections3.isNotEmpty(diseaseList)) {

            List<ProductDisease> productDiseaseList = new ArrayList<ProductDisease>();
            for (Disease disease : diseaseList) {
                //检查疾病编号是否存在
                Disease d = diseaseGeneService.getDiseaseByCode(disease.getCode());
                if (null == d) {
                    //excel有误
                    return redirectList(model, session, "redirect:list.do");
                }
                ProductDisease productDisease = new ProductDisease();

                productDisease.setDisease(d);
                productDiseaseList.add(productDisease);
            }
            data.setProductDiseaseList(productDiseaseList);
        }
        if (Collections3.isNotEmpty(geneList)) {

            List<ProductGene> productGeneList = new ArrayList<ProductGene>();
            for (DiseaseGeneFormRequest gene : geneList) {
                //检查基因编号是否存在
                Gene d = diseaseGeneService.getGeneByCode(gene.getCode());
                if (null == d) {
                    //excel有误
                    return redirectList(model, session, "redirect:list.do");
                }
                ProductGene productGene = new ProductGene();

                productGene.setGene(d);
                productGeneList.add(productGene);
            }
            data.setProductGeneList(productGeneList);
        }
        service.modify(data);
        return redirectList(model, session, "redirect:list.do");
    }

    @RequestMapping(value = "/enable.do", method = RequestMethod.GET)
    public String enable(ProductCreateRequest data, ModelMap model, HttpSession session) {
        // data.setProbeList(JSON.parseArray(data.getProbeArray()+"", ProductProbe.class));
        service.enable(data);
        return redirectList(model, session, "redirect:list.do");
    }

    @RequestMapping("/delete.do")
    public String delete(String id, ModelMap model) {
        service.delete(id);
        model.clear();
        return "redirect:list.do";
    }

    @RequestMapping("/show.do")
    public String show(String id, ModelMap model, ProductCreateRequest request) {
        Product data = service.getProduct(id);
        model.addAttribute("data", data);

        List<Dict> classfys = dictService.getEntries("DETECTION_CLASSFY");
        model.addAttribute("classfys", classfys);

        //查询检测方法探针name
        for (ProductTestingMethod productMethod : data.getProductTestingMethodList()) {
            for (ProductProbe productProbe : productMethod.getProductProbe()) {

                Probe probe = iProbeService.getProbe(productProbe.getProbeId());
                productProbe.setProbeName(probe.getName());

            }

        }

        // 获取所有检测方法
        List<TestingMethod> checkManagementList = testingMethodService.list(new TestingMethodSearcher());
        //获取检测方法的标识
        for (TestingMethod testingMethod: checkManagementList) {
            testingMethod.setSemantic(testingMethodService.get(testingMethod.getId()).getSemantic());
        }
        model.addAttribute("checkManagementList", checkManagementList);

        model.addAttribute("checkManagementList", checkManagementList);
        // 获取负责人
        List<UserArchive> principalList = new ArrayList<UserArchive>();
        for (ProductPrincipal prin : data.getProductPrincipalList()) {
            prin.getPrincipal().getArchive().setId(prin.getPrincipal().getId());
            principalList.add(prin.getPrincipal().getArchive());
        }

        //查询所有营销中心
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        //获取小类TestingType
        TestingType tt = new TestingType();
        tt.setParentId(data.getTestingType().getId());
        List<TestingType> testingSubtypeList = testingTypeService.testingSubtypeList(tt);
        model.addAttribute("testingSubtypeList", testingSubtypeList);
        //获取所有可用探针
        List<Probe> probeList = iProbeService.list(new ProbeSeacher());
        data.setTestingGenes(data.getProductGeneList().size());
        model.addAttribute("probeList", probeList);

        if (StringUtils.isNotEmpty(data.getSamplePurpose())) {
            List<String> samplePurposeList = Arrays.asList(data.getSamplePurpose().split(","));
            model.addAttribute("samplePurposeList", samplePurposeList);
        }

        model.addAttribute("principalList", principalList);
        model.addAttribute("testingMethodList", data.getProductTestingMethodList());
        model.addAttribute("prductDiseaseList", data.getProductDiseaseList());
        model.addAttribute("productGeneList", data.getProductGeneList());
        return "product/product_show";
    }

    /**
     * 模糊匹配产品
     */
    @RequestMapping(value = "/productSelect.do", method = RequestMethod.GET)
    @ResponseBody
    public List containerSelect(ProductSearcher searcher) {
        Map map = new HashMap<>();
        map.put("message", "");
        searcher.setCode(searcher.getQuery());
        Pagination<Product> pagination = service.productSelect(searcher, 1, 100);
        return pagination.getRecords();
    }

    /**
     * 多选框赋值
     */
    @ResponseBody
    @RequestMapping(value = "/getProduct.do", method = RequestMethod.GET)
    public Product getProduct(String id, ModelMap model) {
        if (id != null) {
            Product data = service.getProduct(id);
            return data;
        } else {
            return null;
        }
    }

    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping("/validate.do")
    public boolean validate(ProductCreateRequest request) {
        return service.validate(request);
    }

    @RequestMapping("/getProbeList.do")
    @ResponseBody
    public List<ProductProbe> getProbeList(ProductCreateRequest request) {
        return service.getProbeList(request);
    }

    @RequestMapping("/geneUpload.do")
    @ResponseBody
    public Map geneUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        File file = service.upload(request, response, model);
        Map m = new HashMap<>();
        m.put("path", model.get("fileUrl"));
        Map listMap = service.analyticalGeneData(file);

        m.put("geneList", listMap.get("geneList"));
        //校验编号是否存在

        List<DiseaseGeneFormRequest> geneList = (List<DiseaseGeneFormRequest>) listMap.get("geneList");
        Map geneMap = new HashMap<>();

        if (Collections3.isNotEmpty(geneList)) {

            List<ProductGene> productGeneList = new ArrayList<ProductGene>();
            Iterator<DiseaseGeneFormRequest> geneIt = geneList.iterator();
            while (geneIt.hasNext()) {
                DiseaseGeneFormRequest gene = geneIt.next();
                if (StringUtils.isNotEmpty(gene.getCode())) {
                    if (geneMap.containsKey(gene.getCode())) {
                        m.put("result", 2);
                        return m;
                    }
                    //检查基因编号是否存在
                    if (null == diseaseGeneService.getGeneByCode(gene.getCode())) {
                        m.put("result", 0);
                        return m;

                    }
                    geneMap.put(gene.getCode(), "");
                } else {
                    geneIt.remove();
                }

            }

        }

        m.put("result", true);
        return m;
    }

    @RequestMapping("/diseaseUpload.do")
    @ResponseBody
    public Map diseaseUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        File file = service.upload(request, response, model);
        Map m = new HashMap<>();
        m.put("path", model.get("fileUrl"));
        Map listMap = service.analyticalDiseaseData(file);
        m.put("diseaseList", listMap.get("diseaseList"));

        //校验编号是否存在
        List<Disease> diseaseList = (List<Disease>) listMap.get("diseaseList");
        Map diseaseMap = new HashMap<>();
        if (Collections3.isNotEmpty(diseaseList)) {

            List<ProductDisease> productDiseaseList = new ArrayList<ProductDisease>();
            Iterator<Disease> diseaseIt = diseaseList.iterator();
            while (diseaseIt.hasNext()) {
                Disease disease = diseaseIt.next();
                if (StringUtils.isNotEmpty(disease.getCode())) {
                    if (diseaseMap.containsKey(disease.getCode())) {
                        m.put("result", 2);
                        return m;
                    }
                    //检查疾病编号是否存在
                    if (null == diseaseGeneService.getDiseaseByCode(disease.getCode())) {
                        m.put("result", 0);
                        return m;

                    }
                    diseaseMap.put(disease.getCode(), "");
                } else {
                    diseaseIt.remove();
                }

            }

        }

        m.put("result", true);
        return m;
    }

}
