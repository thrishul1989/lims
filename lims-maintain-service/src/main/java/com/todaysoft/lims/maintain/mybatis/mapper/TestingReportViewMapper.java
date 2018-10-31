package com.todaysoft.lims.maintain.mybatis.mapper;

import com.todaysoft.lims.maintain.mybatis.model.TestingReportView;
import com.todaysoft.lims.maintain.mybatis.model.TestingSheetModel;

import java.util.List;

public interface TestingReportViewMapper {
    int insert(TestingReportView record);

    int insertSelective(TestingReportView record);

    List<TestingReportView> getNotExistReportList();

    List<TestingSheetModel> getSheetByOrderIdAndProjectId(String orderId,String projectId);
}