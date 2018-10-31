package com.todaysoft.lims.system.modules.bpm.bioanalysis.mvc.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnalySampleRecord;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBiologyAnalyService;
import com.todaysoft.lims.system.util.AbstractXlsxView;
import com.todaysoft.lims.utils.Collections3;

@Component("exportSampleDataView")
public class ExportSampleDataView extends AbstractXlsxView
{
    @Autowired
    private IBiologyAnalyService service;
    
    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request)
    {
        try
        {
            InputStream input = ExportDetailsDataView.class.getResourceAsStream("/taskTemplate/biology-analy_sample_data_template.xlsx");
            return new XSSFWorkbook(input);
        }
        catch (IOException e)
        {
            return super.createWorkbook(model, request);
        }
    }
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String id = (String)model.get("id");
        List<BiologyAnalySampleRecord> records = service.getSampleRecords(id);
        Set<BiologyAnalySampleRecord> sets = new HashSet<BiologyAnalySampleRecord>(records);
        String poolingCode = "";
        if (!CollectionUtils.isEmpty(records))
        {
            poolingCode = Collections3.getFirst(records).getPoolingCode();
            Sheet template = workbook.getSheetAt(0);
            Sheet sheet = workbook.createSheet("拆分数据任务表");
            int index = 0;
            Row row = sheet.createRow(index++);
            copyRow(row, template.getRow(0));
            
            int columns = row.getLastCellNum();
            
            for (int i = 0; i < columns; i++)
            {
                sheet.setColumnWidth(i, template.getColumnWidth(i));
            }
            
            Row templateRow = template.getRow(1);
            
            for (BiologyAnalySampleRecord record : sets)
            {
                setText(cloneCell(sheet, index, 0, templateRow), StringUtils.isEmpty(record.getPoolingCode()) ? "" : record.getPoolingCode());
                setText(cloneCell(sheet, index, 1, templateRow), StringUtils.isEmpty(record.getRecordCode()) ? "" : record.getRecordCode());
                setText(cloneCell(sheet, index, 2, templateRow), StringUtils.isEmpty(record.getReceivedSampleCode()) ? "" : record.getReceivedSampleCode());
                setText(cloneCell(sheet, index, 3, templateRow), StringUtils.isEmpty(record.getLibraryIndex()) ? "" : record.getLibraryIndex());
                index++;
            }
            
            workbook.removeSheetAt(0);
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
        String time = dateFormater.format(new Date());
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("生信分析-拆分数据任务表-" + poolingCode + "-" + time + ".xlsx", "UTF-8"));
    }
}