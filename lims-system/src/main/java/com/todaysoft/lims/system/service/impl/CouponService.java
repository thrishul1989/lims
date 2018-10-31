package com.todaysoft.lims.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.todaysoft.lims.system.model.vo.Coupon;
import com.todaysoft.lims.system.model.vo.CouponApply;
import com.todaysoft.lims.system.model.vo.CouponApplyDetail;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.TaskConfig;
import com.todaysoft.lims.system.model.vo.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.system.modules.bpm.model.internal.ReagentKits;
import com.todaysoft.lims.system.service.ICouponService;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.system.service.adapter.request.ProductPagingRequest;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class CouponService implements ICouponService
{
    @Autowired
    private RestTemplate template;
    
    @Override
    public Pagination<CouponApply> couponApplyPaging(CouponApply searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/smm/coupon/couponApplyPaging");
        ResponseEntity<Pagination<CouponApply>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CouponApply>(searcher), new ParameterizedTypeReference<Pagination<CouponApply>>()
            {
            });
        return exchange.getBody();
    }

    @Override
    public Integer getTotalAmountByApply(CouponApply couponApply)
    {
        String url = GatewayService.getServiceUrl("/smm/coupon/getTotalAmountByApply");
        ResponseEntity<Integer> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<CouponApply>(couponApply), new ParameterizedTypeReference<Integer>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public CouponApply getApply(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/coupon/getApply/{id}");
        CouponApply apply = template.getForObject(url, CouponApply.class, id);
        return apply;
    }

    @Override
    public Integer reviewApply(CouponApply request)
    {
        String url = GatewayService.getServiceUrl("/smm/coupon/reviewApply");
        ResponseEntity<Integer> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<CouponApply>(request), new ParameterizedTypeReference<Integer>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public List<CouponApplyDetail> analyticalDetail(HttpServletRequest request, HttpServletResponse response)
    {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        List<CouponApplyDetail> detailList=new ArrayList<CouponApplyDetail>();
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
                        detailList = ei.getDataList(CouponApplyDetail.class);
                       
                      
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            
            return detailList;
            
        }
        return detailList;
    }

    @Override
    public Integer applyAdd(CouponApply request)
    {
        String url = GatewayService.getServiceUrl("/smm/coupon/applyAdd");
        ResponseEntity<Integer> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<CouponApply>(request), new ParameterizedTypeReference<Integer>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public Pagination<Coupon> couponList(Coupon searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/smm/coupon/couponList");
        ResponseEntity<Pagination<Coupon>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Coupon>(searcher), new ParameterizedTypeReference<Pagination<Coupon>>()
            {
            });
        return exchange.getBody();
    }

  

    @Override
    @SystemServiceLog(description="抵用券申请-删除",type=7)
    public void deleteApply(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/coupon/deleteApply/{id}");
        template.delete(url, id);
        
    }

    @Override
    public List<CouponApplyDetail> couponDetialByApply(CouponApply request)
    {
        String url = GatewayService.getServiceUrl("/smm/coupon/couponDetialByApply");
        ResponseEntity<List<CouponApplyDetail>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CouponApply>(request), new ParameterizedTypeReference<List<CouponApplyDetail>>()
            {
            });
        return exchange.getBody();
    }

   
}
