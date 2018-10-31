package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.ExperimentProduct;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.IExperimentProductService;
import com.todaysoft.lims.system.service.adapter.request.ExperimentProductListRequest;

@Service
public class ExperimentProductService extends RestService implements
		IExperimentProductService {

	@Override
	public Pagination<ExperimentProduct> paging(ExperimentProduct searcher,
			int pageNo, int pageSize) {
		ExperimentProduct request = new ExperimentProduct();
		BeanUtils.copyProperties(searcher, request);
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);

		String url = GatewayService.getServiceUrl("/bsm/experimentProduct/paging");
		ResponseEntity<Pagination<ExperimentProduct>> exchange = template
				.exchange(
						url,
						HttpMethod.POST,
						new HttpEntity<ExperimentProduct>(request),
						new ParameterizedTypeReference<Pagination<ExperimentProduct>>() {
						});
		return exchange.getBody();
	}

	@Override
	public void create(ExperimentProduct experimentProduct) {

		 String url = GatewayService.getServiceUrl("/bsm/experimentProduct/create");
	       String res= template.postForObject(url, experimentProduct, String.class);
	    
		

	}

	@Override
	public ExperimentProduct getExperimentProduct(Integer id) {
		String url = GatewayService.getServiceUrl("/bsm/experimentProduct/{id}");
		return template.getForObject(url, ExperimentProduct.class,
				Collections.singletonMap("id", id));
	}

	@Override
	public void delete(Integer id) {
		String url = GatewayService.getServiceUrl("/bsm/experimentProduct/{id}");
		template.delete(url, Collections.singletonMap("id", id));

	}


	@Override
	public void modify(ExperimentProduct experimentProduct) {
		String url = GatewayService.getServiceUrl("/bsm/experimentProduct/modify");
	       String res= template.postForObject(url, experimentProduct, String.class);
		
	}


	@Override
	public List<ExperimentProduct> list(ExperimentProductListRequest request) {
		// TODO Auto-generated method stub
		String url = GatewayService.getServiceUrl("/bsm/experimentProduct/list");
		ResponseEntity<List<ExperimentProduct>> exchange = template.exchange(
				url, HttpMethod.POST,
				new HttpEntity<ExperimentProductListRequest>(request),
				new ParameterizedTypeReference<List<ExperimentProduct>>() {
				});
		return exchange.getBody();

	}

	@Override
	public boolean checkedName(ExperimentProduct connect) {
		
		return template.postForObject(GatewayService.getServiceUrl("/bsm/experimentProduct/validateName.do"), connect, boolean.class);
	}


}
