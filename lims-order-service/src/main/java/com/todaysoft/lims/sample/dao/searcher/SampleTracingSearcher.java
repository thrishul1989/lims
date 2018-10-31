package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.sample.entity.SampleTracing;

public class SampleTracingSearcher implements ISearcher<SampleTracing> {

	private SampleTracing entity;
	
	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SampleTracing st WHERE 1=1"); 
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
	}

	private void addFilter(StringBuffer hql, List<String> paramNames,List<Object> parameters) {
		if (!StringUtils.isEmpty(entity.getSampleDetailId())){
            hql.append(" AND st.sampleDetailId = :sampleDetailId");
            paramNames.add("sampleDetailId");
            parameters.add(entity.getSampleDetailId());
        }
		
		if (!StringUtils.isEmpty(entity.getProductId())){
            hql.append(" AND st.productId = :productId");
            paramNames.add("productId");
            parameters.add(entity.getProductId());
        }
		if (!StringUtils.isEmpty(entity.getCategory())){
            hql.append(" AND st.category = :category");
            paramNames.add("category");
            parameters.add(entity.getCategory());
        }
		if (!StringUtils.isEmpty(entity.getTypeId())){
            hql.append(" AND st.typeId = :typeId");
            paramNames.add("typeId");
            parameters.add(entity.getTypeId());
        }
		if (!StringUtils.isEmpty(entity.getInstCode())){
            hql.append(" AND st.instCode = :instCode");
            paramNames.add("instCode");
            parameters.add(entity.getInstCode());
        }
		if (!StringUtils.isEmpty(entity.getStorageType())){
            hql.append(" AND st.storageType = :storageType");
            paramNames.add("storageType");
            parameters.add(entity.getStorageType());
        }
		if (!StringUtils.isEmpty(entity.getStorageLocation())){
            hql.append(" AND st.storageLocation = :storageLocation");
            paramNames.add("storageLocation");
            parameters.add(entity.getStorageLocation());
        }
	}
	
	@Override
	public Class<SampleTracing> getEntityClass() {
		return SampleTracing.class;
	}

	public SampleTracing getEntity() {
		return entity;
	}

	public void setEntity(SampleTracing entity) {
		this.entity = entity;
	}
}
