package com.todaysoft.lims.gateway.model.request.disease;

import java.util.List;

import com.todaysoft.lims.gateway.model.ProductGene;


public class Gene extends  DiseaseGeneFormRequest{
	public List<ProductGene> getProductGeneList() {
		return productGeneList;
	}

	public void setProductGeneList(List<ProductGene> productGeneList) {
		this.productGeneList = productGeneList;
	}

	private List<ProductGene> productGeneList;
}
