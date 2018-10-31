package com.todaysoft.lims.system.modules.bsm.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.PrimerDatum;
import com.todaysoft.lims.system.model.vo.PrimerDatumRequest;
import com.todaysoft.lims.system.modules.bsm.model.PrimerDatumSearcher;
import com.todaysoft.lims.system.modules.bsm.service.IPrimerDatumService;
import com.todaysoft.lims.system.modules.bsm.service.request.PrimerDatumListRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class PrimerDatumService extends RestService implements IPrimerDatumService
{
    @Override
    public Pagination<PrimerDatum> paging(PrimerDatumSearcher searcher, int pageNo, int pageSize)
    {
        PrimerDatumListRequest request = new PrimerDatumListRequest();
        request.setChromosomeNumber(searcher.getChromosomeNumber());
        request.setGene(searcher.getGene());
        request.setPcrPoint(searcher.getPcrPoint());
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/primer_datum/paging");
        ResponseEntity<Pagination<PrimerDatum>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PrimerDatumListRequest>(request),
                new ParameterizedTypeReference<Pagination<PrimerDatum>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public PrimerDatum get(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer_datum/{id}");
        return template.getForObject(url, PrimerDatum.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public String create(PrimerDatum request)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer_datum/create");
        return template.postForObject(url, request, String.class);
    }
    
    @Override
    public void modify(PrimerDatum request)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer_datum/modify");
        template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer_datum/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @Override
    public void upload(MultipartFile uploadData)
    {
        List<PrimerDatum> list = Lists.newArrayList();
        try
        {
            ImportExcel ei = new ImportExcel(uploadData, 0, 0);
            list = ei.getDataList(PrimerDatum.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        PrimerDatumRequest request = new PrimerDatumRequest();
        request.setList(list);
        String url = GatewayService.getServiceUrl("/bsm/primer_datum/uploadData");
        template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public String download(InputStream is)
    {
        
        File tempFile = new File("primerDatum" + ".xlsx");
        TestSheetService.inputstreamToFile(is, tempFile);
        String path = tempFile.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1"}; // 必须为列表头部所有位置集合， 输出
            
            excel.writeDateList(path, heads, datalist, wsheet);
            
            // 写到输出流并移除资源
            excel.writeAndClose(path, os);
            os.flush();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String abPath = tempFile.getAbsolutePath();
        return abPath;
        
    }
}
