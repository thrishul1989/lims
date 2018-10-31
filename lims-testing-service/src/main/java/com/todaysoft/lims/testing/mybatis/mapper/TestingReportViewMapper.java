package com.todaysoft.lims.testing.mybatis.mapper;


import com.todaysoft.lims.testing.mybatis.model.TestingSheetModel;

import java.util.List;

public interface TestingReportViewMapper {

    List<TestingSheetModel> getSheetByOrderIdAndProjectId(String orderId, String projectId);
}