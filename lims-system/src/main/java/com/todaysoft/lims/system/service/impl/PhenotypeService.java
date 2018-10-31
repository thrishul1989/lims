package com.todaysoft.lims.system.service.impl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.searcher.PhenotypeSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Phenotype;
import com.todaysoft.lims.system.model.vo.disease.DiseasePhenotype;
import com.todaysoft.lims.system.service.IPhenotypeService;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class PhenotypeService extends RestService implements IPhenotypeService
{
    
    @Override
    public void create(Phenotype request)
    {
        
        template.postForObject(GatewayService.getServiceUrl("/bcm/phenotype/create.do"), request, Integer.class);
    }
    
    @Override
    public void modify(Phenotype request)
    {
        
        template.postForObject(GatewayService.getServiceUrl("/bcm/phenotype/modify.do"), request, Boolean.class);
    }
    
    @Override
    public void delete(PhenotypeSearcher searcher)
    {
        
        template.postForObject(GatewayService.getServiceUrl("/bcm/phenotype/delete"), searcher, boolean.class);
    }
    
    @Override
    public Pagination<Phenotype> paging(PhenotypeSearcher searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bcm/phenotype/list.do");
        ResponseEntity<Pagination<Phenotype>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PhenotypeSearcher>(searcher), new ParameterizedTypeReference<Pagination<Phenotype>>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public Phenotype getPhenotypeById(String id)
    {
        
        return template.getForObject(GatewayService.getServiceUrl("/bcm/phenotype/getPhenotype/{id}"), Phenotype.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public boolean checkedName(PhenotypeSearcher connect)
    {
        
        return template.postForObject(GatewayService.getServiceUrl("/bcm/phenotype/validate.do"), connect, boolean.class);
    }
    
    @Override
    public List<Phenotype> getPhenotypeList(PhenotypeSearcher searcher)
    {
        
        String url = GatewayService.getServiceUrl("/bcm/phenotype/getPhenotypes");
        ResponseEntity<List<Phenotype>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PhenotypeSearcher>(searcher), new ParameterizedTypeReference<List<Phenotype>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<Phenotype> upload(MultipartFile uploadData)
    {
        List<Phenotype> phenotypes = Lists.newArrayList();
        if (uploadData != null)
        {
            try
            {
                ImportExcel ei = new ImportExcel(uploadData, 0, 0);
                phenotypes = ei.getDataList(Phenotype.class);
                /* for (Phenotype phenotype : phenotypes)
                 {
                     create(phenotype);
                 }*/
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return phenotypes;
    }
    
    @Override
    public List<DiseasePhenotype> getDiseasePhenotype(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/phenotype/getDiseasePhenotype");
        PhenotypeSearcher searcher = new PhenotypeSearcher();
        searcher.setId(id);
        ResponseEntity<List<DiseasePhenotype>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PhenotypeSearcher>(searcher), new ParameterizedTypeReference<List<DiseasePhenotype>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void downloadData(HttpServletResponse response, InputStream inputStream)
    {
        
        OutputStream outputStream = null;
        try
        {
            // 以流的形式下载文件。
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            // 清空response
            response.reset();
            outputStream = new BufferedOutputStream(response.getOutputStream());
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("phenotype.xlsx"));
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
            
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
                outputStream.close();
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
        }
        
    }
    
    @Override
    public Phenotype getPhenotypeByCode(String code)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/phenotype/getPhenotypeByCode/{code}"),
            Phenotype.class,
            Collections.singletonMap("code", code));
    }
}
