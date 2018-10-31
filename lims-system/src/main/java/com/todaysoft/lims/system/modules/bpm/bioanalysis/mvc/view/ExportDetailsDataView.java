package com.todaysoft.lims.system.modules.bpm.bioanalysis.mvc.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnalyDetailsRecord;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBiologyAnalyService;
import com.todaysoft.lims.system.util.AbstractXlsxView;
import com.todaysoft.lims.utils.Collections3;

@Component("exportDetailsDataView")
public class ExportDetailsDataView extends AbstractXlsxView
{
    @Autowired
    private IBiologyAnalyService service;
    
    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request)
    {
        try
        {
            InputStream input = ExportDetailsDataView.class.getResourceAsStream("/taskTemplate/biology-analy_details_data_template.xlsx");
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
        List<BiologyAnalyDetailsRecord> records = service.getDetailsRecords(id);
        String poolingCode = "";
        if (!CollectionUtils.isEmpty(records))
        {
            poolingCode = Collections3.getFirst(records).getPoolingCode();
            Sheet template = workbook.getSheetAt(0);
            Sheet sheet = workbook.createSheet("分析数据任务表");
            int index = 0;
            Row row = sheet.createRow(index++);
            copyRow(row, template.getRow(0));
            
            int columns = row.getLastCellNum();
            
            for (int i = 0; i < columns; i++)
            {
                sheet.setColumnWidth(i, template.getColumnWidth(i));
            }
            
            Row templateRow = template.getRow(1);
            
            Font font = sheet.getWorkbook().createFont();
            
            CellStyle style = sheet.getWorkbook().createCellStyle();
            
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            for (BiologyAnalyDetailsRecord record : records)
            {
                if ("sub".equals(record.getFlag()))
                {
                    font.setColor(HSSFColor.RED.index);
                    style.setFont(font);
                    templateRow.getCell(4).setCellStyle(style);
                    
                }
                else if ("main".equals(record.getFlag()))
                {
                    font.setColor(HSSFColor.RED.index);
                    style.setFont(font);
                    templateRow.getCell(3).setCellStyle(style);
                    
                }
                else
                {
                    font.setColor(HSSFColor.BLACK.index);
                    style.setFont(font);
                    //templateRow.getCell(3).setCellStyle(style);
                    //templateRow.getCell(4).setCellStyle(style);
                }
                
                setText(cloneCell(sheet, index, 0, templateRow), StringUtils.isEmpty(record.getPoolingCode()) ? "" : record.getPoolingCode());
                setText(cloneCell(sheet, index, 1, templateRow), StringUtils.isEmpty(record.getOrderCode()) ? "" : record.getOrderCode());
                setText(cloneCell(sheet, index, 2, templateRow), StringUtils.isEmpty(record.getRecordCode()) ? "" : record.getRecordCode());
                setText(cloneCell(sheet, index, 3, templateRow), StringUtils.isEmpty(record.getReceivedSampleCode()) ? "" : record.getReceivedSampleCode());
                setText(cloneCell(sheet, index, 4, templateRow), StringUtils.isEmpty(record.getReferenceSampleCode()) ? "" : record.getReferenceSampleCode());
                setText(cloneCell(sheet, index, 5, templateRow), StringUtils.isEmpty(record.getReferenceSampleType()) ? "" : record.getReferenceSampleType());
                setText(cloneCell(sheet, index, 6, templateRow), StringUtils.isEmpty(record.getReferencePoolingCode()) ? "" : record.getReferencePoolingCode());
                setText(cloneCell(sheet, index, 7, templateRow), StringUtils.isEmpty(record.getLibrarySampleCode()) ? "" : record.getLibrarySampleCode());
                setText(cloneCell(sheet, index, 8, templateRow), StringUtils.isEmpty(record.getLibraryIndex()) ? "" : record.getLibraryIndex());
                setText(cloneCell(sheet, index, 9, templateRow), StringUtils.isEmpty(record.getCoordinate()) ? "" : record.getCoordinate());
                setText(cloneCell(sheet, index, 10, templateRow), StringUtils.isEmpty(record.getExamineeSex()) ? "" : record.getExamineeSex());
                setText(cloneCell(sheet, index, 11, templateRow), StringUtils.isEmpty(record.getSampleName()) ? "" : record.getSampleName());
                setText(cloneCell(sheet, index, 12, templateRow), StringUtils.isEmpty(record.getSampleType()) ? "" : record.getSampleType());
                setText(cloneCell(sheet, index, 13, templateRow), StringUtils.isEmpty(record.getTestingType()) ? "" : record.getTestingType());
                setText(cloneCell(sheet, index, 14, templateRow), StringUtils.isEmpty(record.getExamineeName()) ? "" : record.getExamineeName());
                setText(cloneCell(sheet, index, 15, templateRow), StringUtils.isEmpty(record.getContractCode()) ? "" : record.getContractCode());
                setText(cloneCell(sheet, index, 16, templateRow), StringUtils.isEmpty(record.getRemark()) ? "" : record.getRemark());
                setText(cloneCell(sheet, index, 17, templateRow), StringUtils.isEmpty(record.getProductCode()) ? "" : record.getProductCode());
                setText(cloneCell(sheet, index, 18, templateRow), StringUtils.isEmpty(record.getProductName()) ? "" : record.getProductName());
                setText(cloneCell(sheet, index, 19, templateRow), StringUtils.isEmpty(record.getFamilyRelation()) ? "本人" : record.getFamilyRelation());
                setText(cloneCell(sheet, index, 20, templateRow), StringUtils.isEmpty(record.getExamineeDiagnosis()) ? "" : record.getExamineeDiagnosis());
                setText(cloneCell(sheet, index, 21, templateRow), StringUtils.isEmpty(record.getExamineeGenes()) ? "" : record.getExamineeGenes());
                setText(cloneCell(sheet, index, 22, templateRow), StringUtils.isEmpty(record.getExamineePhenotypes()) ? "" : record.getExamineePhenotypes());
                setText(cloneCell(sheet, index, 23, templateRow), StringUtils.isEmpty(record.getMedicalHistory()) ? "" : record.getMedicalHistory());
                setText(cloneCell(sheet, index, 24, templateRow), StringUtils.isEmpty(record.getFamilyMedicalHistory()) ? "" : record.getFamilyMedicalHistory());
                setText(cloneCell(sheet, index, 25, templateRow), StringUtils.isEmpty(record.getClinicalRecommend()) ? "" : record.getClinicalRecommend());
                index++;
            }
            
            workbook.removeSheetAt(0);
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
        String time = dateFormater.format(new Date());
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("生信分析-分析数据任务表-" + poolingCode + "-" + time + ".xlsx", "UTF-8"));
    }
}
