package com.todaysoft.lims.system.modules.bmm.model;

import java.lang.reflect.Field;
import java.nio.channels.IllegalSelectorException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.todaysoft.lims.exception.ServiceException;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.ExcelColumnField;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.ExcelColumnValueType;

public class DefaultInvoiceImportModelResolver
{
    private MultipartFile data;
    
    public DefaultInvoiceImportModelResolver(MultipartFile data)
    {
        this.data = data;
    }
    
    public List<DefaultInvoiceImportModel> resolve()
    {
        try
        {
            String filename = data.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            
            Workbook workbook;
            
            if ("xlsx".equalsIgnoreCase(suffix))
            {
                workbook = new XSSFWorkbook(data.getInputStream());
            }
            else if ("xls".equalsIgnoreCase(suffix))
            {
                workbook = new HSSFWorkbook(data.getInputStream());
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
            
            Map<Integer, Field> fields = mapping();
            
            Row row;
            
            List<DefaultInvoiceImportModel> records = Lists.newArrayList();
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++)
            {
                row = sheet.getRow(i);
                
                if (null == row || ignore(row))
                {
                    continue;
                }
                
                records.add(getRecord(row, fields));
            }
            
            return records;
        }
        catch (Exception e)
        {
            throw ServiceException.innerError();
        }
    }
    
    private Map<Integer, Field> mapping()
    {
        Map<Integer, Field> mapping = new HashMap<Integer, Field>();
        
        Field[] fields = DefaultInvoiceImportModel.class.getDeclaredFields();
        
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
    
    private DefaultInvoiceImportModel getRecord(Row row, Map<Integer, Field> fields)
    {
        DefaultInvoiceImportModel record = new DefaultInvoiceImportModel();
        
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
                else if (DateUtil.isCellDateFormatted(cell))
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    String dateString = formatter.format(cell.getDateCellValue());
                    return dateString;
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
