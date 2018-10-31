package com.todaysoft.lims.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.product.entity.TestingType;
import com.todaysoft.lims.product.service.ITestingTypeService;

@Service
public class TestingTypeService implements ITestingTypeService{
	@Autowired
    private BaseDaoSupport baseDaoSupport;
	
	@Override
	public List<TestingType> testingTypeList() {
		// TODO Auto-generated method stub
		
		return baseDaoSupport.find(TestingType.class, "from TestingType t where t.parentId is null");
	}

	@Override
	public List<TestingType> testingSubtypeList(String parentId) {
		// TODO Auto-generated method stub
		return baseDaoSupport.find(TestingType.class, "from TestingType t where t.parentId = "+parentId+" ");
	}

    @Override
    public TestingType get(String id)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.get(TestingType.class, id);
    }
}
