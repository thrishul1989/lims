package com.todaysoft.lims.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.searcher.ProductSearcher;
import com.todaysoft.lims.system.model.vo.*;
import com.todaysoft.lims.system.model.vo.disease.Disease;
import com.todaysoft.lims.system.model.vo.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IClaimTemplateService;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.service.ICycleConfigService;
import com.todaysoft.lims.system.modules.bpm.longpcr.service.impl.LongPcrService;
import com.todaysoft.lims.system.modules.bpm.model.internal.ReagentKits;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.model.ScheduleTestingConfig;
import com.todaysoft.lims.system.modules.report.service.IDataTemplateService;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.adapter.request.ProductCreateRequest;
import com.todaysoft.lims.system.service.adapter.request.ProductListRequest;
import com.todaysoft.lims.system.service.adapter.request.ProductPagingRequest;
import com.todaysoft.lims.system.util.ConfigManage;
import com.todaysoft.lims.utils.StringUtils;
import com.todaysoft.lims.utils.excel.ImportExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductService implements IProductService
{
    private static Logger log = LoggerFactory.getLogger(ProductService.class);
    
    @Autowired
    private RestTemplate template;
    
    @Autowired
    IUserService userService;
    
    @Autowired
    private ITestSheetService testSheetService;

    @Autowired
    private IClaimTemplateService claimTemplateService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private IDataTemplateService dataTemplateService;

    @Autowired
    private ICycleConfigService cycleConfigService;
    @Override
    public Pagination<Product> paging(ProductSearcher searcher, int pageNo, int pageSize)
    {
        ProductPagingRequest request = new ProductPagingRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bcm/product/paging");
        ResponseEntity<Pagination<Product>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProductPagingRequest>(request), new ParameterizedTypeReference<Pagination<Product>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Product getProduct(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/product/{id}");
        Product p = template.getForObject(url, Product.class, id);
        for (ProductTestingMethod productTestingMethod:p.getProductTestingMethodList()) {

            //数据模板
            if (StringUtils.isNotEmpty(productTestingMethod.getDataTemplateId()))
            {
                DataTemplate template = dataTemplateService.get(productTestingMethod.getDataTemplateId());
                if (StringUtils.isNotEmpty(template.getName()))
                {
                    productTestingMethod.setDataTemplateName(template.getName());
                }
            }
           //周期配置
            if(productTestingMethod.getScheduleConfigId()!=null)
            {
              ScheduleTestingConfig scheduleConfigModel =  cycleConfigService.getTestingConfigById(productTestingMethod.getScheduleConfigId());
               if (StringUtils.isNotEmpty(scheduleConfigModel)&& StringUtils.isNotEmpty(scheduleConfigModel.getConfigName()))
               {
                   productTestingMethod.setScheduleName(scheduleConfigModel.getConfigName());
               }
            }

        }
        return p;
    }
    
    @Override
    public void create(ProductCreateRequest request)
    {
        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        List<ProductTestingMethod> productTestingMethodList = new ArrayList<ProductTestingMethod>();
        if (StringUtils.isNotEmpty(request.getTestMethodArray()))
        {
            for (String testMethod : request.getTestMethodArray().split("\\,"))
            {
                
                ProductTestingMethod testingMethod = new ProductTestingMethod();
                
                //塞入分析做标
                List<ProductTestingMethod> ll = JSON.parseArray(request.getCoordinates() + "", ProductTestingMethod.class);
                for (ProductTestingMethod coordiante : ll)
                {
                    if (testMethod.equals(coordiante.getTestingMethod().getId()))
                    {
                        testingMethod.setCoordinate(coordiante.getCoordinate());
                    }
                    
                }
                
                //塞入检测数据量
                List<ProductTestingMethod> testingDatasizeList = JSON.parseArray(request.getTestingDatasize() + "", ProductTestingMethod.class);
                for (ProductTestingMethod testingDatasize : testingDatasizeList)
                {
                    if (testMethod.equals(testingDatasize.getTestingMethod().getId()))
                    {
                        testingMethod.setTestingDatasize(testingDatasize.getTestingDatasize());
                        ;
                    }
                    
                }
                
                //检测配置
                List<ProductTestingMethod> scheduleTestingConfigList = JSON.parseArray(request.getScheduleTestingCongigs() + "", ProductTestingMethod.class);
                for (ProductTestingMethod scheduleTestingConfig : scheduleTestingConfigList)
                {
                    if (testMethod.equals(scheduleTestingConfig.getTestingMethod().getId()))
                    {
                        testingMethod.setScheduleConfigId(scheduleTestingConfig.getScheduleConfigId());
                    }
                }

                //检测配置
                List<ProductTestingMethod> dataTemplateConfigList = JSON.parseArray(request.getDataTemplateCongigs() + "", ProductTestingMethod.class);
                for (ProductTestingMethod dataTempalteConfig : dataTemplateConfigList)
                {
                    if (testMethod.equals(dataTempalteConfig.getTestingMethod().getId()))
                    {
                        testingMethod.setDataTemplateId(dataTempalteConfig.getDataTemplateId());
                    }
                }
                //分析内容
                List<ProductTestingMethod> claimTemplateConfigList = JSON.parseArray(request.getClaimTemplateCongigs() + "", ProductTestingMethod.class);

                for (ProductTestingMethod claimTempalteConfig : claimTemplateConfigList)
                {
                    if (testMethod.equals(claimTempalteConfig.getTestingMethod().getId()))
                    {
                        String claimTemplateID = "";
                        // claimTempalteConfig.getClaimTemplateId()-------["6fff47287499486fb4803f5c89ec6dab","856873dd2c0d4648bdedfe241b0882fd"]
                        //删除前两个字符
                        String str = claimTempalteConfig.getClaimTemplateId().substring(2,claimTempalteConfig.getClaimTemplateId().length());
                        //删除后两个字符
                        str = str.substring(0,str.length()-2);
                        //通过 "," 分隔把字符串转成数组
                        String[] arr = str.split("\",\"");
                        for (int i = 0; i< arr.length;i++)
                        {
                            claimTemplateID  = claimTemplateID +arr[i] +",";
                        }
                        claimTemplateID = claimTemplateID.substring(0,claimTemplateID.length() -1);
                        testingMethod.setClaimTemplateId( claimTemplateID);

                        //为分析内容( 冗余)赋值
                        String [] analyseContentArr = claimTemplateID.split(",");
                        String analyseContent = "";
                        for (int i = 0;i<analyseContentArr.length;i++)
                        {
                            analyseContent = analyseContent + claimTemplateService.get(analyseContentArr[i]).getName()+ ",";
                        }
                        analyseContent = analyseContent.substring(0,analyseContent.length() -1);
                        testingMethod.setAnalysisContent(analyseContent);

                    }
                }

                //分析要求
                List<ProductTestingMethod> coverageConfigList = JSON.parseArray(request.getCoverageCongigs() + "", ProductTestingMethod.class);

                for (ProductTestingMethod coverageConfig : coverageConfigList)
                {
                    if (testMethod.equals(coverageConfig.getTestingMethod().getId()))
                    {
                        String coverageID = "";
                        String str1 = coverageConfig.getCoverage().substring(2,coverageConfig.getCoverage().length());
                        str1 = str1.substring(0,str1.length() -2);
                        String[] arr1 = str1.split("\",\"");
                        for (int i =0 ;i<arr1.length;i++)
                        {
                            coverageID = coverageID +arr1[i] +",";
                        }
                        coverageID = coverageID.substring(0,coverageID.length() -1);
                        testingMethod.setCoverage(coverageID);
                        //为分析要求( 冗余)赋值
                        String [] analyseRequireArr = coverageID.split(",");
                        String analyseRequire = "";
                        for (int i = 0;i<analyseRequireArr.length;i++)
                        {
                            analyseRequire = analyseRequire + dictService.get(analyseRequireArr[i]).getText()+ ",";
                        }
                        analyseRequire = analyseRequire.substring(0,analyseRequire.length() -1);
                        testingMethod.setAnalysisRequire(analyseRequire);
                    }
                }

                TestingMethod checkManagement = new TestingMethod();
                checkManagement.setId(testMethod);
                testingMethod.setTestingMethod(checkManagement);
                productTestingMethodList.add(testingMethod);
            }
        }


        List<ProductSample> productSampleList = new ArrayList<ProductSample>();
        if (StringUtils.isNotEmpty(request.getEnSample()))
        {
            for (String sample : request.getEnSample().split("\\,"))
            {
                ProductSample productSample = new ProductSample();
                MetadataSample sam = new MetadataSample();
                sam.setId(sample);
                productSample.setSample(sam);
                productSampleList.add(productSample);
            }
        }
        List<ProductPrincipal> productPrincipalList = new ArrayList<ProductPrincipal>();
        if (StringUtils.isNotEmpty(request.getPrincipalArray()))
        {
            for (String principal : request.getPrincipalArray().split("\\,"))
            {
                ProductPrincipal prin = new ProductPrincipal();
                User user = new User();
                user.setId(principal);
                prin.setPrincipal(user);
                productPrincipalList.add(prin);
            }
        }
        product.setProductPrincipalList(productPrincipalList);
        product.setProductTestingMethodList(productTestingMethodList);
        product.setProductSampleList(productSampleList);


        
        product.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        
        User user = userService.getUserByToken();
        product.setCreater(user.getId());
        String url = GatewayService.getServiceUrl("/bcm/product/create");
        template.postForLocation(url, product);
    }
    
    @Override
    public void modify(ProductCreateRequest request)
    {
        Product product = this.getProduct(request.getId());
        BeanUtils.copyProperties(request, product, "creater", "createTime", "state");
        List<ProductTestingMethod> productTestingMethodList = new ArrayList<ProductTestingMethod>();
        if (StringUtils.isNotEmpty(request.getTestMethodArray()))
        {
            for (String testMethod : request.getTestMethodArray().split("\\,"))
            {
                ProductTestingMethod testingMethod = new ProductTestingMethod();
                
                //塞入分析做标
                List<ProductTestingMethod> ll = JSON.parseArray(request.getCoordinates() + "", ProductTestingMethod.class);
                for (ProductTestingMethod coordiante : ll)
                {
                    if (testMethod.equals(coordiante.getTestingMethod().getId()))
                    {
                        testingMethod.setCoordinate(coordiante.getCoordinate());
                    }
                    
                }
                
                //塞入检测数据量
                List<ProductTestingMethod> testingDatasizeList = JSON.parseArray(request.getTestingDatasize() + "", ProductTestingMethod.class);
                for (ProductTestingMethod testingDatasize : testingDatasizeList)
                {
                    if (testMethod.equals(testingDatasize.getTestingMethod().getId()))
                    {
                        testingMethod.setTestingDatasize(testingDatasize.getTestingDatasize());
                        ;
                    }
                    
                }
                //检测配置
                List<ProductTestingMethod> scheduleTestingConfigList = JSON.parseArray(request.getScheduleTestingCongigs() + "", ProductTestingMethod.class);
                for (ProductTestingMethod scheduleTestingConfig : scheduleTestingConfigList)
                {
                    if (testMethod.equals(scheduleTestingConfig.getTestingMethod().getId()))
                    {
                        testingMethod.setScheduleConfigId(scheduleTestingConfig.getScheduleConfigId());
                    }
                }

                //检测配置
                List<ProductTestingMethod> dataTemplateConfigList = JSON.parseArray(request.getDataTemplateCongigs() + "", ProductTestingMethod.class);
                for (ProductTestingMethod dataTempalteConfig : dataTemplateConfigList)
                {
                    if (testMethod.equals(dataTempalteConfig.getTestingMethod().getId()))
                    {
                        testingMethod.setDataTemplateId(dataTempalteConfig.getDataTemplateId());
                    }
                }
                //分析内容
                List<ProductTestingMethod> claimTemplateConfigList = JSON.parseArray(request.getClaimTemplateCongigs() + "", ProductTestingMethod.class);

                for (ProductTestingMethod claimTempalteConfig : claimTemplateConfigList)
                {
                    if (testMethod.equals(claimTempalteConfig.getTestingMethod().getId()))
                    {
                        String claimTemplateID = "";
                        // claimTempalteConfig.getClaimTemplateId()-------["6fff47287499486fb4803f5c89ec6dab","856873dd2c0d4648bdedfe241b0882fd"]
                        //删除前两个字符
                       String str = claimTempalteConfig.getClaimTemplateId().substring(2,claimTempalteConfig.getClaimTemplateId().length());
                       //删除后两个字符
                        str = str.substring(0,str.length()-2);
                        //通过 "," 分隔把字符串转成数组
                       String[] arr = str.split("\",\"");
                        for (int i = 0; i< arr.length;i++)
                        {
                            claimTemplateID  = claimTemplateID +arr[i] +",";
                        }
                        claimTemplateID = claimTemplateID.substring(0,claimTemplateID.length() -1);
                        testingMethod.setClaimTemplateId( claimTemplateID);
                        //为分析内容( 冗余)赋值
                        String [] analyseContentArr = claimTemplateID.split(",");
                        String analyseContent = "";
                        for (int i = 0;i<analyseContentArr.length;i++)
                        {
                           analyseContent = analyseContent + claimTemplateService.get(analyseContentArr[i]).getName()+ ",";
                        }
                        analyseContent = analyseContent.substring(0,analyseContent.length() -1);
                        testingMethod.setAnalysisContent(analyseContent);

                    }
                }

                //分析要求
                List<ProductTestingMethod> coverageConfigList = JSON.parseArray(request.getCoverageCongigs() + "", ProductTestingMethod.class);

                for (ProductTestingMethod coverageConfig : coverageConfigList)
                {
                    if (testMethod.equals(coverageConfig.getTestingMethod().getId()))
                    {
                        String coverageID = "";
                        String str1 = coverageConfig.getCoverage().substring(2,coverageConfig.getCoverage().length());
                        str1 = str1.substring(0,str1.length() -2);
                        String[] arr1 = str1.split("\",\"");
                        for (int i =0 ;i<arr1.length;i++)
                        {
                            coverageID = coverageID +arr1[i] +",";
                        }
                        coverageID = coverageID.substring(0,coverageID.length() -1);
                        testingMethod.setCoverage(coverageID);

                        //为分析要求( 冗余)赋值
                        String [] analyseRequireArr = coverageID.split(",");
                        String analyseRequire = "";
                        for (int i = 0;i<analyseRequireArr.length;i++)
                        {
                            analyseRequire = analyseRequire + dictService.get(analyseRequireArr[i]).getText()+ ",";
                        }
                        analyseRequire = analyseRequire.substring(0,analyseRequire.length() -1);
                        testingMethod.setAnalysisRequire(analyseRequire);
                    }
                }
                
                TestingMethod checkManagement = new TestingMethod();
                checkManagement.setId(testMethod);
                testingMethod.setTestingMethod(checkManagement);
                productTestingMethodList.add(testingMethod);
            }
        }
        List<ProductSample> productSampleList = new ArrayList<ProductSample>();
        if (StringUtils.isNotEmpty(request.getEnSample()))
        {
            for (String sample : request.getEnSample().split("\\,"))
            {
                ProductSample productSample = new ProductSample();
                MetadataSample sam = new MetadataSample();
                sam.setId(sample);
                productSample.setSample(sam);
                productSampleList.add(productSample);
            }
        }
        List<ProductPrincipal> productPrincipalList = new ArrayList<ProductPrincipal>();
        if (StringUtils.isNotEmpty(request.getPrincipalArray()))
        {
            for (String principal : request.getPrincipalArray().split("\\,"))
            {
                ProductPrincipal prin = new ProductPrincipal();
                User user = new User();
                user.setId(principal);
                prin.setPrincipal(user);
                productPrincipalList.add(prin);
            }
        }
        product.setProductPrincipalList(productPrincipalList);
        product.setProductTestingMethodList(productTestingMethodList);
        product.setProductSampleList(productSampleList);
        product.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String url = GatewayService.getServiceUrl("/bcm/product/modify");
        template.postForLocation(url, product);
    }
    
    @Override
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/product/{id}");
        template.delete(url, id);
    }
    
    @Override
    public boolean unique(String code)
    {
        String url = GatewayService.getServiceUrl("/bcm/product/unique/{code}");
        return template.getForObject(url, Boolean.class, code);
    }
    
    /**
     * @Description: 从product_form页面传到后台的ProductReceiveForm对象
     * @author: 沈从会
     * @Date: 2016年7月4日 下午4:27:22
     */
    @Override
    public void insert(ProductCreateRequest data)
    {
        log.info(data.toString());
        create(data);
    }
    
    /**
     * @Description: TODO
     * @author: 沈从会
     * @Date: 2016年7月6日 上午10:00:57
     */
    @Override
    public List<InputOutputModel> getAnalysisMethodList()
    {
        List<InputOutputModel> analysismethodlist = new ArrayList<>();
        analysismethodlist.add(new InputOutputModel(1, "分析方法-1"));
        analysismethodlist.add(new InputOutputModel(2, "分析方法-2"));
        analysismethodlist.add(new InputOutputModel(3, "分析方法-3"));
        analysismethodlist.add(new InputOutputModel(4, "分析方法-4"));
        return analysismethodlist;
    }
    
    @Override
    public Pagination<Product> productSelect(ProductSearcher searcher, int pageNo, int pageSize)
    {
        ProductPagingRequest request = new ProductPagingRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bcm/product/productSelect");
        ResponseEntity<Pagination<Product>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProductPagingRequest>(request), new ParameterizedTypeReference<Pagination<Product>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public boolean validate(ProductCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/product/validate");
        boolean res = template.postForObject(url, request, boolean.class);
        return res;
    }
    
    @Override
    public List<ProductProbe> getProbeList(ProductCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/product/getProbeList");
        ResponseEntity<List<ProductProbe>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProductCreateRequest>(request), new ParameterizedTypeReference<List<ProductProbe>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void enable(ProductCreateRequest data)
    {
        String url = GatewayService.getServiceUrl("/bcm/product/enable");
        template.postForLocation(url, data);
    }
    
    @Override
    public File upload(HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        
        File localFile = null;
        File file = new File(ConfigManage.getkey("uploadPath"));
        
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
                    SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
                    Date date = new Date();
                    String fileName = dateFormater.format(date) + mFile.getOriginalFilename();
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    String path = file.getAbsolutePath().toString() + "\\" + fileName;
                    localFile = new File(path);
                    try
                    {
                        mFile.transferTo(localFile);
                    }
                    catch (IllegalStateException | IOException e)
                    {
                        e.printStackTrace();
                    }
                    model.addAttribute("fileUrl", path);
                }
            }
        }
        return localFile;
    }
    
    @Override
    public Map analyticalDiseaseData(File file)
    {
        Map m = new HashMap<>();
        
        try
        {
            ImportExcel ei = new ImportExcel(file, 0, 0);
            List<Disease> diseaseList = ei.getDataList(Disease.class);
            
            m.put("diseaseList", diseaseList);
            
            return m;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return m;
    }
    
    @Override
    public Map analyticalGeneData(File file)
    {
        Map m = new HashMap<>();
        
        try
        {
            
            ImportExcel e = new ImportExcel(file, 0, 0);
            
            List<DiseaseGeneFormRequest> geneList = e.getDataList(DiseaseGeneFormRequest.class);
            m.put("geneList", geneList);
            
            return m;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return m;
    }
    
    @Override
    public List<ReagentKits> getByTaskAndInput(TaskConfig taskConfig)
    {
        String url = GatewayService.getServiceUrl("/bcm/task/getByTaskAndInput");
        ResponseEntity<List<ReagentKits>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TaskConfig>(taskConfig), new ParameterizedTypeReference<List<ReagentKits>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<MetadataSample> getList()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Task> getTaskList()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<InputOutputModel> getDetectionClassfyList()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<InputOutputModel> getMethodList()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<MetadataSample> list()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Product> getProductByName(ProductSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bcm/product/list");
        ResponseEntity<List<Product>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProductSearcher>(searcher), new ParameterizedTypeReference<List<Product>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String download(File zipfile)
    {
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadDisease()));
        srcfile.add(new File(downloadGene()));
        String zipFile = testSheetService.zipFiles(zipfile, srcfile);
        return zipFile;
    }
    
    private String downloadGene()
    {
        InputStream inputStream = LongPcrService.class.getResourceAsStream("/taskTemplate/producrGene.xlsx");
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File("基因模板" + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        System.out.println(file.getAbsolutePath());
        return file.getAbsolutePath();
    }
    
    private String downloadDisease()
    {
        InputStream inputStream = LongPcrService.class.getResourceAsStream("/taskTemplate/productDisease.xlsx");
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File("疾病模板" + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        return file.getAbsolutePath();
    }
    
    @Override
    public List<MetadataSample> getSampleByProductIds(ProductListRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/product/getSampleByProductIds");
        ResponseEntity<List<MetadataSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProductListRequest>(request), new ParameterizedTypeReference<List<MetadataSample>>()
            {
            });
        return exchange.getBody();
    }
    
}
