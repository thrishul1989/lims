package com.todaysoft.lims.system.modules.bpm.pcrngsdata.model;

import com.google.common.collect.Maps;
import com.todaysoft.lims.exception.ServiceException;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.DataTempalteSemantic;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ZipFileUploadModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.ExcelColumnValueType;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PcrNgsDataAnalRecordResolver
{
    private ZipFileUploadModel model;

    private DataTemplate dataTemplate;

    public PcrNgsDataAnalRecordResolver(ZipFileUploadModel model, DataTemplate dataTemplate)
    {
        this.model = model;
        this.dataTemplate = dataTemplate;
    }
    
    public List<PcrNgsDataSubmitTaskArgs> resolve()
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


            List<PcrNgsDataSubmitTaskArgs> records = new ArrayList<PcrNgsDataSubmitTaskArgs>();

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

            PcrNgsDataSubmitTaskArgs record;
            //取数据行
            Row row;
            for (int i = dataIndex-1; i <= sheet.getLastRowNum(); i++)
            {
                row = sheet.getRow(i);
                
                if (null == row || ignore(row))
                {
                    continue;
                }
                record = new PcrNgsDataSubmitTaskArgs();
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

    public void wrap(String key,PcrNgsDataSubmitTaskArgs record,String value)
    {
        record.getMap().put(key,value);
        String semantic = dataTemplate.getMap().get(key);
        if(StringUtils.isEmpty(semantic))
        {
            return;
        }
        switch (semantic) {
            case DataTempalteSemantic.result:
                record.setResultInfo(value);
                if("成功".equals(value))
                {
                    record.setResult(0);
                }else{
                    record.setResult(1);
                }
                break;
            case DataTempalteSemantic.dispose:
                record.setDispose(value);
                break;
            case DataTempalteSemantic.combineCode:
                record.setCombineCode(value);
                break;
            case DataTempalteSemantic.remark:
                record.setRemark(value);
                break;
            default:
                break;
        }
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
