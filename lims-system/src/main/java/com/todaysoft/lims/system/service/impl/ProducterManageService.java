package com.todaysoft.lims.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.searcher.ProducterManageSearcher;
import com.todaysoft.lims.system.model.vo.DataArea;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.ProducterManage;
import com.todaysoft.lims.system.service.IProducterManageService;

@Service
public class ProducterManageService extends RestService implements IProducterManageService{

	@Override
	public Pagination<ProducterManage> paging(ProducterManageSearcher searcher, int pageNo, int pageSize) {
		
		searcher.setPageNo(pageNo);
		searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/producterManage/paging");
        ResponseEntity<Pagination<ProducterManage>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProducterManageSearcher>(searcher), new ParameterizedTypeReference<Pagination<ProducterManage>>()
            {
            });
        return exchange.getBody();
	}

	@Override
	public void modify(ProducterManage request) {

        String url = GatewayService.getServiceUrl("/producterManage/modify");
        template.postForObject(url, request, String.class);
		
	}

	@Override
	public ProducterManage getProducterManage(String id) {
		
	    String url = GatewayService.getServiceUrl("/producterManage/get/{id}");
          return template.getForObject(url, ProducterManage.class, Collections.singletonMap("id", id));
	}

	@Override
	public void delete(String id) {
		 String url = GatewayService.getServiceUrl("/producterManage/{id}");
	        template.delete(url, Collections.singletonMap("id", id));
		
	}

	@Override
	public void create(ProducterManage request) {
		 String url = GatewayService.getServiceUrl("/producterManage/create");
	        template.postForObject(url, request, String.class);
		
	}

	@Override
	public boolean validate(ProducterManage request) {
		String url = GatewayService.getServiceUrl("/producterManage/validate");
       return  template.postForObject(url, request, boolean.class);
	}
	
	@Override
	public void doSome(ProducterManage producterManage,String flag){
		
		List<String> emails = null;
		if(producterManage.getContactEmails().indexOf(",")>0){
			String[] strs = producterManage.getContactEmails().split(",");
			if("view".equals(flag)){
				emails = new ArrayList<String>(Arrays.asList(strs));
				producterManage.setEmails(emails);
			}else{
				
				producterManage.setContactEmails(strs[0]+"...");
			}
		}
	}
	
	@Override
	public DataArea getDataAreaById(String parentId) {

	    String url = GatewayService.getServiceUrl("/producterManage/getByParentId/{parentId}");
          return template.getForObject(url, DataArea.class, Collections.singletonMap("parentId", parentId));
	}

	@Override
	public List<DataArea> findRoots() {
		 String url = GatewayService.getServiceUrl("/producterManage/findRoots");
	        ResponseEntity<List<DataArea>> exchange =
	            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DataArea>>()
	            {
	            });
	        return exchange.getBody();
	}

	@Override
	public boolean deleteEmail(ProducterManage data) {
		
		String url = GatewayService.getServiceUrl("/producterManage/deleteEmail");
	    return template.postForObject(url, data,boolean.class);
	}

}
