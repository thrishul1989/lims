package com.todaysoft.lims.system.service.adapter.request;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Probe;

public class ProductProbeRequest {
	



private 	String methodId;
	public List<Probe> getProbes() {
	return probes;
}
public void setProbes(List<Probe> probes) {
	this.probes = probes;
}
	private List<Probe> probes;
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	
	
	
}




