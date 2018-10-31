package com.todaysoft.lims.technical.adapter.bpm;

import com.todaysoft.lims.technical.model.AbnormalSolveRecord;
import com.todaysoft.lims.technical.model.request.DataAnalysisReportModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TestingAdapter {
    private static final String SERVICE_NAME = "lims-gateway-service";

    @Autowired
    private RestTemplate template;

    public void taskFailReport(String taskId, String remark) {
        DataAnalysisReportModel requestModel = new DataAnalysisReportModel();
        requestModel.setTaskId(taskId);
        requestModel.setRemark(remark);
        requestModel.setTaskRefer("TECHNICAL-ANALYSIS");
        String url = "http://" + SERVICE_NAME + "/bpm/testing/biology-analy/taskFailReport";
        template.postForObject(url, requestModel, Integer.class);
    }

    public AbnormalSolveRecord getReSampleRecord(String taskId)
    {
        String url = "http://" + SERVICE_NAME + "/bpm/abnormal/getReSampleRecordByTask/{taskId}";
        return template.getForObject(url, AbnormalSolveRecord.class, taskId);
    }

}
