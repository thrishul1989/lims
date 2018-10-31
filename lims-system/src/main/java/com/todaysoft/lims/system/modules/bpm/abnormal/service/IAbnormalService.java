package com.todaysoft.lims.system.modules.bpm.abnormal.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.AbnormalHistoryModel;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.AbnormalSolveRecord;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.AbnormalTask;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.AbnormalTaskDetails;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.AbnormalTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.AbnormalTaskSolveRequest;

public interface IAbnormalService
{
    Pagination<AbnormalTask> paging(AbnormalTaskSearcher searcher, int pageNo, int pageSize);
    
    AbnormalTaskDetails getDetails(String id,String semantic);
    
    void solve(AbnormalTaskSolveRequest request);

    Pagination<AbnormalSolveRecord> history(AbnormalSolveRecord searcher, Integer pageNo, Integer pageSize);

    AbnormalSolveRecord historyShow(String id);

    void generateRedundantFields(String semantics);

    void generateRedundantTask(String id);

    List<AbnormalHistoryModel> getAbnormalHistoryByTaskId(String taskId,String semantic);
    
    AbnormalHistoryModel getAbnormalHistorySingle(String taskId);
}
