package com.todaysoft.lims.technical.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CapCnvPictureGeneData {

	private String chrLocation;

	private String gene;

	private String area;

	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getChrLocation() {
		return chrLocation;
	}

	public void setChrLocation(String chrLocation) {
		this.chrLocation = chrLocation;
	}

}
