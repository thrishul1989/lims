package com.todaysoft.lims.testing.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingVerifyRecord;
import com.todaysoft.lims.testing.base.request.CheckPrimerForDesignRequest;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.ITestingVerifyService;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.utils.Collections3;

@Service
public class TestingVerifyService implements ITestingVerifyService
{
    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private BSMAdapter bsmAdapter;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Override
    public String getSangerVerifyCode()
    {
        return commonService.getTechnicalAnalBatchNo();
    }
    
    @Override
    public Primer getSangerVerifyPrimer(TestingVerifyRecord record)
    {
        TestingMethod testingMethod = testingTaskService.getTestingMethodBySemantic(TestingMethod.SANGER);
        CheckPrimerForDesignRequest request = new CheckPrimerForDesignRequest();
        request.setChromosomeNumber(record.getAnalyRecord().getChromosome());
        request.setPcrPoint(record.getAnalyRecord().getLocation1());
        request.setApplicationType(testingMethod.getId());//sanger验证id
        List<com.todaysoft.lims.testing.base.model.Primer> list = bsmAdapter.getList(request);
        if (Collections3.isEmpty(list))
        {
            return null;
        }
        Primer primer = new Primer();
        primer.setId(Collections3.getFirst(list).getId());
        return primer;
    }

    @Override
    public Primer getPcrNgsVerifyPrimer(TestingVerifyRecord record)
    {
        TestingMethod testingMethod = testingTaskService.getTestingMethodBySemantic(TestingMethod.PCR_NGS);
        CheckPrimerForDesignRequest request = new CheckPrimerForDesignRequest();
        request.setChromosomeNumber(record.getAnalyRecord().getChromosome());
        request.setPcrPoint(record.getAnalyRecord().getLocation1());
        request.setApplicationType(testingMethod.getId());//sanger验证id
        List<com.todaysoft.lims.testing.base.model.Primer> list = bsmAdapter.getPcrNgsList(request);
        if (Collections3.isEmpty(list))
        {
            return null;
        }
        Primer primer = new Primer();
        primer.setId(Collections3.getFirst(list).getId());
        return primer;
    }
}
