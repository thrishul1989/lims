package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyDivisionTask;
import com.todaysoft.lims.biology.model.request.BioDivisionListRequest;
import com.todaysoft.lims.persist.Pagination;

import java.util.List;

public interface BiologyDivisionTaskMapper {

    int insertTask(BiologyDivisionTask record);

    List<BiologyDivisionTask> getTaskSearchList(BioDivisionListRequest request);

    BiologyDivisionTask getTaskById(String id);

    int updateById(BiologyDivisionTask record);

    BiologyDivisionTask getDivisionTaskBySquencingCode(String squencingCode);
}