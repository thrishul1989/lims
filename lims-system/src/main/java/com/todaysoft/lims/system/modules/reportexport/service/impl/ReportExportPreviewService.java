package com.todaysoft.lims.system.modules.reportexport.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.report.model.ReportReviewModel;
import com.todaysoft.lims.system.modules.reportexport.entity.MutationFamilySangerResultInfo;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportCnvAnalysisPicResultList;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportDetectionResult;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportDetectionResultPicture;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportMethodColumnConfig;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportMutationInfo;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportSampleBaseInfo;
import com.todaysoft.lims.system.modules.reportexport.model.request.GetReportExportSampleBaseInfoRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportCnvAnalysisPicResultListRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportDetectionResultInfoRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportDetectionResultPictureRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportMutationInfoRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportUpdateRequest;
import com.todaysoft.lims.system.modules.reportexport.model.response.GenerateWordReportReponse;
import com.todaysoft.lims.system.modules.reportexport.service.IReportExportPreviewService;
import com.todaysoft.lims.system.service.impl.DefaultRequestInterceptor;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ReportExportPreviewService extends RestService implements IReportExportPreviewService
{
    private static final Logger logger = LoggerFactory.getLogger(ReportExportPreviewService.class);
    
    @Override
    public ReportExportSampleBaseInfo getReportExportSampleBaseInfo(GetReportExportSampleBaseInfoRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/reportexport/getReportExportSampleBaseInfo");
        ResponseEntity<ReportExportSampleBaseInfo> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<GetReportExportSampleBaseInfoRequest>(request),
                new ParameterizedTypeReference<ReportExportSampleBaseInfo>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public Map<String, List<ReportExportMutationInfo>> getReportExportMutationInfo(ReportExportMutationInfoRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/reportexport/getReportExportMutationInfo");
        ResponseEntity<Map<String, List<ReportExportMutationInfo>>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ReportExportMutationInfoRequest>(request),
                new ParameterizedTypeReference<Map<String, List<ReportExportMutationInfo>>>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public Map<String, List<ReportExportCnvAnalysisPicResultList>> getReportExportCnvAnalysisPicResultList(ReportExportCnvAnalysisPicResultListRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/reportexport/getReportExportCnvAnalysisPicResultList");
        ResponseEntity<Map<String, List<ReportExportCnvAnalysisPicResultList>>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ReportExportCnvAnalysisPicResultListRequest>(request),
                new ParameterizedTypeReference<Map<String, List<ReportExportCnvAnalysisPicResultList>>>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public void updateReportExport(ReportExportUpdateRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalexport/reportexport/updateReportExport");
        template.postForLocation(url, request);
    }

    @Override
    public GenerateWordReportReponse generateReport(String taskId)
    {
        try
        {
            String url = GatewayService.getServiceUrl("/technicalexport/reportexport/generateWordReport/{taskId}");
            return template.getForObject(url, GenerateWordReportReponse.class, Collections.singletonMap("taskId", taskId));
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public byte[] downLoadReport(String fileName)
    {
        try {
            String url = GatewayService.getServiceUrl("/technicalexport/reportexport/downloadReport/{fileName}");
            HttpHeaders headers = new HttpHeaders();
            //application/octet-stream ： 二进制流数据（最常见的文件下载）.
             headers.setContentType(new MediaType("application" ,"octet-stream",Charset.forName("ISO-8859-1")));  
            ResponseEntity<byte[]> response = template.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class, Collections.singletonMap("fileName", fileName));
            return response.getBody();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null; 
    }
    
    @Override
    public void downLoad1(String fileName)
    {
    String url = GatewayService.getServiceUrl("/technicalexport/reportexport/downloadReport");
    String filePath = "E:/hshy/lims/jacob/aaa.txt";
    InputStream inputStream = null;
    OutputStream outputStream = null;
            

    try {
        //      Object result = restTemplate.getForObject(url, Object.class);
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.valueOf("application/octet-stream"));
       // headers.getAccept().add(MediaType.valueOf("application/msword"));
       // headers.setAccept(;
        //headers.set
/*        List<HttpMessageConverter<?>> converters=new ArrayList<HttpMessageConverter<?>>();
        converters.add(new ByteArrayHttpMessageConverter());
        template.setMessageConverters(converters);*/
        ResponseEntity<byte[]> response = template.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
        System.out.println("contentType:"+response.getHeaders().getContentType());
        System.out.println("contentLength:"+response.getHeaders().getContentLength());
        

        byte[] result = response.getBody();
        inputStream = new ByteArrayInputStream(result);
        
        outputStream = new FileOutputStream(new File(filePath));
        
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf, 0, 1024)) != -1) {
            outputStream.write(buf, 0, len);
        }
        outputStream.flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if(inputStream != null) inputStream.close();
            if(outputStream != null) outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("数据流关闭异常！");
        }
    }
       // downloadImageByUrl();
    }
    
    
    
    


    public void downloadImageByUrl() {
        // TODO Auto-generated method stub
 
        ClientHttpRequestInterceptor acceptHeaderPdf = new AcceptHeaderHttpRequestInterceptor(
                "application/octet-stream");
        
      //  template.setInterceptors(Collections.singletonList((ClientHttpRequestInterceptor)));
        List<ClientHttpRequestInterceptor> clist = new ArrayList<ClientHttpRequestInterceptor>();
        clist.add(new DefaultRequestInterceptor());
        clist.add(acceptHeaderPdf);
        template.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        template.setInterceptors(clist);
        String url = GatewayService.getServiceUrl("/technicalexport/reportexport/downloadReport");
        byte[] response = template.getForObject(url, byte[].class);
        System.out.println(response.length);
        InputStream inputStream = new ByteArrayInputStream(response);
        OutputStream outputStream =null;
        try { 
            outputStream=new FileOutputStream(new File("E:/hshy/lims/jacob/aaa.txt")); 
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf, 0, 1024)) != -1) {
            outputStream.write(buf, 0, len);
        }
        outputStream.flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if(inputStream != null) inputStream.close();
            if(outputStream != null) outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("数据流关闭异常！");
        }
    }
    }
 
    class AcceptHeaderHttpRequestInterceptor implements
            ClientHttpRequestInterceptor {
        private final String headerValue;
 
        public AcceptHeaderHttpRequestInterceptor(String headerValue) {
            this.headerValue = headerValue;
        }
 
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                ClientHttpRequestExecution execution) throws IOException {
 
            HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
            List<MediaType> mytype = new ArrayList<MediaType>();
            mytype.add(MediaType.valueOf(headerValue));
            requestWrapper.getHeaders().setAccept(mytype);
 
            return execution.execute(requestWrapper, body);
        }
    }

    @Override
    public Map<String, List<ReportExportDetectionResult>> getReportExportDetectionResultInfo(ReportExportDetectionResultInfoRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/reportexport/getReportExportDetectionResultInfo");
        ResponseEntity<Map<String, List<ReportExportDetectionResult>>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ReportExportDetectionResultInfoRequest>(request),
                new ParameterizedTypeReference<Map<String, List<ReportExportDetectionResult>>>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public List<ReportExportMethodColumnConfig> getReportExportMethodColumnConfig(ReportExportDetectionResultInfoRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/reportexport/getReportExportMethodColumnConfig");
        ResponseEntity<List<ReportExportMethodColumnConfig>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ReportExportDetectionResultInfoRequest>(request),
                new ParameterizedTypeReference<List<ReportExportMethodColumnConfig>>()
                {
                });
        return exchange.getBody();
    }
    

    @Override
    public List<MutationFamilySangerResultInfo>  getMutationFamilySangerResultInfoByTaskId(String taskId)
    {
        try
        {
            String url = GatewayService.getServiceUrl("/technicalexport/reportexport/getSangerResultInfoByTaskId/{taskId}");
            ResponseEntity<List<MutationFamilySangerResultInfo>> exchange =
                template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MutationFamilySangerResultInfo>>()
                {
                }, taskId);
            return exchange.getBody();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    
    @Override
    public List<ReportExportDetectionResultPicture>  getReportExportDetectionResultPictureByTaskIdAndMethod(ReportExportDetectionResultPictureRequest request)
    {
        try
        {
            String url = GatewayService.getServiceUrl("/technicalexport/reportexport/getReportExportDetectionResultPictureByTaskIdAndMethod");
            ResponseEntity<List<ReportExportDetectionResultPicture>> exchange =
                template.exchange(url, HttpMethod.POST,  new HttpEntity<ReportExportDetectionResultPictureRequest>(request), new ParameterizedTypeReference<List<ReportExportDetectionResultPicture>>()
                {
                });
            return exchange.getBody();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
