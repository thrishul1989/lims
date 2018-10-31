package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.DataTempalteSemantic;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ZipFileUploadModel;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.todaysoft.lims.exception.ServiceException;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyRecord;

public class TechnicalAnalyRecordResolver
{
    private ZipFileUploadModel model;

    private DataTemplate dataTemplate;

    public TechnicalAnalyRecordResolver(ZipFileUploadModel model, DataTemplate dataTemplate)
    {
        this.model = model;
        this.dataTemplate = dataTemplate;
    }
    
    public List<TechnicalAnalyRecord> resolve()
    {
        int titleIndex = dataTemplate.getTitleIndex();
        int dataIndex = dataTemplate.getStartRowIndex();
        try
        {
            File xlxFile = model.getFile();
            String fileName = xlxFile.getName();
            String suffix="";
            int fileIndex = fileName.lastIndexOf(".");
            if (fileIndex != -1)
            {
                suffix =fileName.substring(fileName.lastIndexOf(".") + 1);
            }

            Workbook workbook;
            
            if ("xlsx".equalsIgnoreCase(suffix))
            {
                workbook = new XSSFWorkbook(new FileInputStream(xlxFile));
            }
            else if ("xls".equalsIgnoreCase(suffix))
            {
                workbook = new HSSFWorkbook(new FileInputStream(xlxFile));
            }
            else
            {
                throw new IllegalStateException();
            }
            
            Sheet sheet = workbook.getSheetAt(0);
            
            if (null == sheet)
            {
                throw new IllegalStateException();
            }


            List<TechnicalAnalyRecord> records = new ArrayList<TechnicalAnalyRecord>();

            //取标题行
            if( sheet.getLastRowNum() < titleIndex)
            {
                throw new IllegalStateException();
            }
            Row titleRow = sheet.getRow(titleIndex-1);
            Map<Integer,String> columnTitleMap = Maps.newHashMap();//列对应的标题名称

            for (int j = titleRow.getFirstCellNum(); j < titleRow.getLastCellNum(); j++)
            {
                Cell cell = titleRow.getCell(j, Row.RETURN_BLANK_AS_NULL);

                if (null == cell)
                {
                    continue;
                }
                String value = getCellString(titleRow, j, ExcelColumnValueType.STRING);//每一列标题
                if(StringUtils.isEmpty(value))
                {
                    continue;
                }
                columnTitleMap.put(j,value);
            }

            TechnicalAnalyRecord record;
            //取数据行
            Row row;
            for (int i = dataIndex-1; i <= sheet.getLastRowNum(); i++)
            {
                row = sheet.getRow(i);
                
                if (null == row || ignore(row))
                {
                    continue;
                }
                record = new TechnicalAnalyRecord();
                for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++)
                {
                    String title = columnTitleMap.get(j);

                    Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);

                    if (null == cell)
                    {
                        continue;
                    }
                    String value = getCellString(row, j, ExcelColumnValueType.STRING);
                    wrap(title,record,value);
                }
                records.add(record);
            }
            return records;
        }
        catch (Exception e)
        {
            throw ServiceException.innerError();
        }
    }

    public void wrap(String key,TechnicalAnalyRecord record,String value)
    {
        record.getMap().put(key,value);
        String semantic = dataTemplate.getMap().get(key);
        if(StringUtils.isEmpty(semantic))
        {
            return;
        }
        switch (semantic) {
            case DataTempalteSemantic.sampleCode:
                record.setSample(value);
                break;
            case DataTempalteSemantic.verify:
                record.setVerify(value);
                break;
            case DataTempalteSemantic.locusType:
                record.setLocusType(value);
                break;
            case DataTempalteSemantic.verifyMethod:
                record.setVerifyMethod(value);
                break;
            case DataTempalteSemantic.geneSymbol:
                record.setGeneSymbol(value);
                break;
            case DataTempalteSemantic.dataCode:
                record.setDataCode(value);
                break;
            case DataTempalteSemantic.chrLocation:
                record.setChrLocation(value);
                break;
            case DataTempalteSemantic.refTranscript:
                record.setRefTranscript(value);
                break;
            case DataTempalteSemantic.exon:
                record.setExon(value);
                break;
            case DataTempalteSemantic.nucleotideChanges:
                record.setNucleotideChanges(value);
                break;
            case DataTempalteSemantic.aminoAcidChanges:
                record.setAminoAcidChanges(value);
                break;
            case DataTempalteSemantic.geneType:
                record.setGeneType(value);
                break;
            case DataTempalteSemantic.chromosome:
                record.setChromosome(value);
                break;
            case DataTempalteSemantic.beginLocus:
                record.setBeginLocus(value);
                break;
            case DataTempalteSemantic.endLocus:
                record.setEndLocus(value);
                break;
            case DataTempalteSemantic.upRefGene:
                record.setUpRefGene(value);
                break;
            case DataTempalteSemantic.downRefGene:
                record.setDownRefGene(value);
                break;
            case DataTempalteSemantic.refSample:
                record.setRefSample(value);
                break;
            case DataTempalteSemantic.clinicalJudgment:
                record.setClinicalJudgment(value);
                break;
            case DataTempalteSemantic.mutationSource:
                record.setMutationSource(value);
                break;
            default:
                break;
        }
    }
    
    private TechnicalAnalyRecord getRecord(Row row, Map<Integer, Field> fields)
    {
        TechnicalAnalyRecord record = new TechnicalAnalyRecord();
        
        Field field;
        ExcelColumnField annotation;
        
        for (Map.Entry<Integer, Field> entry : fields.entrySet())
        {
            field = entry.getValue();
            field.setAccessible(true);
            
            try
            {
                annotation = field.getAnnotation(ExcelColumnField.class);
                field.set(record, getCellString(row, entry.getKey(), annotation.type()));
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                //
            }
        }
        
        return record;
    }
    
    private Map<Integer, Field> mapping()
    {
        Map<Integer, Field> mapping = new HashMap<Integer, Field>();
        
        Field[] fields = TechnicalAnalyRecord.class.getDeclaredFields();
        
        int index;
        ExcelColumnField annotation;
        
        for (Field field : fields)
        {
            annotation = field.getAnnotation(ExcelColumnField.class);
            
            if (null == annotation)
            {
                continue;
            }
            
            index = annotation.index();
            
            if (mapping.containsKey(index))
            {
                throw new IllegalSelectorException();
            }
            
            mapping.put(index, field);
        }
        
        return mapping;
    }
    
    private boolean ignore(Row row)
    {
        Cell cell;
        
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
        {
            cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
            
            if (null == cell)
            {
                continue;
            }
            
            if (!"".equals(getCellString(row, i, ExcelColumnValueType.STRING)))
            {
                return false;
            }
        }
        
        return true;
    }
    
    private static String getCellString(Row row, int col, ExcelColumnValueType type)
    {
        Cell cell = row.getCell(col);
        
        if (null != cell)
        {
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            {
                if (ExcelColumnValueType.FLOAT.equals(type))
                {
                    return String.valueOf(cell.getNumericCellValue()).trim();
                }
                else
                {
                    return String.valueOf((long)cell.getNumericCellValue()).trim();
                }
            }
            else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA)
            {
                try
                {
                    if (ExcelColumnValueType.FLOAT.equals(type))
                    {
                        return String.valueOf(cell.getNumericCellValue()).trim();
                    }
                    else
                    {
                        return String.valueOf((long)cell.getNumericCellValue()).trim();
                    }
                }
                catch (IllegalStateException e)
                {
                    try
                    {
                        return String.valueOf(cell.getRichStringCellValue());
                    }
                    catch (Exception ex)
                    {
                        return "";
                    }
                }
            }
            else
            {
                try
                {
                    return cell.getStringCellValue().trim();
                }
                catch (Exception ex)
                {
                    return "";
                }
            }
        }
        
        return "";
    }
}
