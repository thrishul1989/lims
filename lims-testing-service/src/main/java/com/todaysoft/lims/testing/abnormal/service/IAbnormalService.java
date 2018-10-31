package com.todaysoft.lims.testing.abnormal.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.abnormal.dao.AbnormalTaskSearcher;
import com.todaysoft.lims.testing.abnormal.model.AbnormalHistoryModel;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTask;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskDetails;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskSolveRequest;
import com.todaysoft.lims.testing.abnormal.model.request.AbnormalSolveRecordSearcher;
import com.todaysoft.lims.testing.base.entity.AbnormalSolveRecord;
import com.todaysoft.lims.testing.base.model.VariableModel;

public interface IAbnormalService
{
    Pagination<AbnormalTask> paging(AbnormalTaskSearcher searcher, int pageNo, int pageSize);
    
    AbnormalTaskDetails getDetails(String id,String semantic);
    
    String solve(AbnormalTaskSolveRequest request, String token, VariableModel model);
    
    Pagination<AbnormalSolveRecordSearcher> history(AbnormalSolveRecordSearcher searcher);
    
    AbnormalSolveRecordSearcher history(String id);
    
    AbnormalSolveRecord getByTaskId(String taskId);
    
    List<AbnormalHistoryModel> getAbnormalHistoryByTaskId(String taskId,String semantic);

    AbnormalHistoryModel getAbnormalHistorySingle(String taskId);

    AbnormalSolveRecord getReSampleRecord(String scheduleId);

    AbnormalSolveRecord getReSampleRecordByTask(String taskId);

}
