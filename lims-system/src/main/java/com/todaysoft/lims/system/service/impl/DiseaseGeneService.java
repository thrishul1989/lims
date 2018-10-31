package com.todaysoft.lims.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Phenotype;
import com.todaysoft.lims.system.model.vo.disease.Disease;
import com.todaysoft.lims.system.model.vo.disease.DiseaseFormRequest;
import com.todaysoft.lims.system.model.vo.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.system.model.vo.disease.DiseaseGenePagingRequest;
import com.todaysoft.lims.system.model.vo.disease.DiseasePagingRequest;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.service.IDiseaseGeneService;
import com.todaysoft.lims.system.util.ConfigManage;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class DiseaseGeneService extends RestService implements IDiseaseGeneService
{
    
    @Override
    public String createGene(DiseaseGeneFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/createGene");
        return template.postForObject(url, request, String.class);
    }
    
    @Override
    public Pagination<Gene> paging(DiseaseGenePagingRequest request, int pageNo, int pagesize)
    {
        
        request.setPageNo(pageNo);
        request.setPageSize(pagesize);
        
        String url = GatewayService.getServiceUrl("/bcm/disease/pagingGene");
        ResponseEntity<Pagination<Gene>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DiseaseGenePagingRequest>(request), new ParameterizedTypeReference<Pagination<Gene>>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public void deleteGene(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/deleteGene/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @Override
    public Pagination<Disease> pagingDisease(DiseasePagingRequest request, int pageNo, int pagesize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(pagesize);
        request.setDiseaseType(request.getDiseaseType());
        String url = GatewayService.getServiceUrl("/bcm/disease/pagingDisease");
        ResponseEntity<Pagination<Disease>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DiseasePagingRequest>(request), new ParameterizedTypeReference<Pagination<Disease>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String createDisease(DiseaseFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/createDisease");
        return template.postForObject(url, request, String.class);
    }
    
    @Override
    public void deleteDisease(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/deleteDisease/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @Override
    public List<Gene> geneList(DiseaseGenePagingRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/geneSelect");
        ResponseEntity<List<Gene>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DiseaseGenePagingRequest>(request), new ParameterizedTypeReference<List<Gene>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Gene getGeneById(String id)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/disease/getGeneById/{id}"), Gene.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public boolean validateDiseaseName(DiseaseFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/validateDiseaseName");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public boolean validateGeneName(DiseaseGeneFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/validateGeneName");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public String updateGene(DiseaseGeneFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/updateGene");
        return template.postForObject(url, request, String.class);
    }
    
    @Override
    public Disease getDiseaseById(String id)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/disease/getDiseaseById/{id}"), Disease.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public String updateDisease(DiseaseFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/updateDisease");
        return template.postForObject(url, request, String.class);
    }
    
    @Override
    public File upload(HttpServletRequest request, HttpServletResponse response)
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
                    request.setAttribute("fileUrl", path);
                }
            }
        }
        return localFile;
    }
    
    @Override
    public void analyticalDisease(HttpServletRequest request, HttpServletResponse response)
    {
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
                    try
                    {
                        ImportExcel ei = new ImportExcel(mFile, 0, 0);
                        List<DiseaseFormRequest> diseaseList = ei.getDataList(DiseaseFormRequest.class);
                        for (DiseaseFormRequest d : diseaseList)
                        {
                            createDisease(d);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    @Override
    public List<DiseaseGeneFormRequest> analyticalGene(HttpServletRequest request, HttpServletResponse response)
    {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        List<DiseaseGeneFormRequest> geneList = new ArrayList<DiseaseGeneFormRequest>();
        if (cmr.isMultipart(request))
        {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)(request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext())
            {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null)
                {
                    try
                    {
                        ImportExcel ei = new ImportExcel(mFile, 0, 0);
                        geneList = ei.getDataList(DiseaseGeneFormRequest.class);
                        
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            
            return geneList;
            
        }
        return geneList;
    }
    
    public void createPhenotype(Phenotype request)
    {
        template.postForObject(GatewayService.getServiceUrl("/phenotype/create.do"), request, Integer.class);
    }
    
    @Override
    public void quickUploadDisease(HttpServletRequest request, HttpServletResponse response)
    {
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
                    try
                    {
                        ImportExcel ei = new ImportExcel(mFile, 0, 0);
                        List<DiseaseFormRequest> diseaseList = ei.getDataList(DiseaseFormRequest.class);
                        for (DiseaseFormRequest d : diseaseList)
                        {
                            createDisease(d);
                        }
                        
                        ImportExcel geneei = new ImportExcel(mFile, 0, 1);
                        List<DiseaseGeneFormRequest> geneList = geneei.getDataList(DiseaseGeneFormRequest.class);
                        for (DiseaseGeneFormRequest g : geneList)
                        {
                            createGene(g);
                        }
                        
                        ImportExcel phenotypeEi = new ImportExcel(mFile, 0, 2);
                        List<Phenotype> phenotypes = phenotypeEi.getDataList(Phenotype.class);
                        for (Phenotype phenotype : phenotypes)
                        {
                            createPhenotype(phenotype);
                        }
                        
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    @Override
    public boolean validateGeneCode(DiseaseGeneFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/validateGeneCode");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public boolean validateDiseaseCode(DiseaseFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/validateDiseaseCode");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public Disease getDiseaseByCode(String code)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/disease/getDiseaseByCode/{code}"),
            Disease.class,
            Collections.singletonMap("code", code));
    }
    
    @Override
    public Gene getGeneByCode(String code)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/disease/getGeneByCode/{code}"), Gene.class, Collections.singletonMap("code", code));
    }
    
    @Override
    public List<Disease> diseaseSelect(DiseasePagingRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/diseaseSelect");
        ResponseEntity<List<Disease>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DiseasePagingRequest>(request), new ParameterizedTypeReference<List<Disease>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Integer getProductDiease(String diseaseId)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/disease/getProductDiease/{diseaseId}"),
            Integer.class,
            Collections.singletonMap("diseaseId", diseaseId));
    }
    
    @Override
    public Integer getProductGenes(String geneId)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/disease/getProductGenes/{geneId}"),
            Integer.class,
            Collections.singletonMap("geneId", geneId));
    }
    
    @Override
    public Disease getDiseaseByENName(DiseaseGeneFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/disease/getDiseaseByENName");
        return template.postForObject(url, request, Disease.class);
    }
    
    @Override
    public void indexForProducts()
    {
        String url = GatewayService.getServiceUrl("/maintain/indexes/products");
        template.postForLocation(url, Collections.emptyMap());
    }
    
    @Override
    public void indexForDiseases()
    {
        String url = GatewayService.getServiceUrl("/maintain/indexes/diseases");
        template.postForLocation(url, Collections.emptyMap());
    }
    
    @Override
    public void indexForGenes()
    {
        String url = GatewayService.getServiceUrl("/maintain/indexes/genes");
        template.postForLocation(url, Collections.emptyMap());
    }
    
    @Override
    public void indexForPhenotypes()
    {
        String url = GatewayService.getServiceUrl("/maintain/indexes/phenotypes");
        template.postForLocation(url, Collections.emptyMap());
    }
    
    @Override
    public IndexESMonitor getIndexESMonitor()
    {
        String url = GatewayService.getServiceUrl("/maintain/indexes/products/monitor");
        Monitor products = template.getForObject(url, Monitor.class);
        
        url = GatewayService.getServiceUrl("/maintain/indexes/genes/monitor");
        Monitor genes = template.getForObject(url, Monitor.class);
        
        url = GatewayService.getServiceUrl("/maintain/indexes/diseases/monitor");
        Monitor diseases = template.getForObject(url, Monitor.class);
        
        url = GatewayService.getServiceUrl("/maintain/indexes/phenotypes/monitor");
        Monitor phenotypes = template.getForObject(url, Monitor.class);
        
        IndexESMonitor monitor = new IndexESMonitor();
        monitor.setProducts(products);
        monitor.setGenes(genes);
        monitor.setDiseases(diseases);
        monitor.setPhenotypes(phenotypes);
        return monitor;
    }
}
