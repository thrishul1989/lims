package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.model.TechnicalAnalysisSheetModel;

import java.util.List;

public interface TechnicalAnalysisSheetMapper {

    List<TechnicalAnalysisSheetModel> getSheetByFamilyGroupId(String taskId);

    TechnicalAnalysisSheetModel getSheet(String id);
}
