package com.todaysoft.lims.report.service.impl;


import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.report.entity.ReportTaskRecord;
import com.todaysoft.lims.report.service.IReportTaskRecordService;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportTaskRecordService implements IReportTaskRecordService
{

    @Autowired
    private BaseDaoSupport baseDaoSupport;

    @Override
    public ReportTaskRecord get(String id) {
        String hql = " FROM ReportTaskRecord r WHERE r.exportTaskId='"+id+"'";
        List<ReportTaskRecord> results = baseDaoSupport.find(ReportTaskRecord.class,hql);
        return Collections3.getFirst(results);
    }

    @Override
    @Transactional
    public void create(ReportTaskRecord reportTaskRecord) {
        baseDaoSupport.insert(reportTaskRecord);
    }
}
