package com.todaysoft.lims.technical.export.mybatis.mapper;

import java.util.List;


import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMethodColumnConfig;
import org.apache.ibatis.annotations.Param;
public interface ReportExportMethodColumnConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReportExportMethodColumnConfig record);

    int insertSelective(ReportExportMethodColumnConfig record);

    ReportExportMethodColumnConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReportExportMethodColumnConfig record);

    int updateByPrimaryKey(ReportExportMethodColumnConfig record);
    
    List<ReportExportMethodColumnConfig> selectByMethod(@Param("method")String method);
}