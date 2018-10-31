/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.todaysoft.lims.system.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Convenient superclass for Excel document views in the Office 2007 XLSX format
 * (as supported by POI-OOXML). Compatible with Apache POI 3.5 and higher.
 *
 * <p>For working with the workbook in subclasses, see
 * <a href="http://poi.apache.org">Apache's POI site</a>.
 *
 * @author Juergen Hoeller
 * @since 4.2
 */
public abstract class AbstractXlsxView extends AbstractXlsView
{
    
    /**
     * Default Constructor.
     * <p>Sets the content type of the view to
     * {@code "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}.
     */
    public AbstractXlsxView()
    {
        setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
    
    /**
     * This implementation creates an {@link XSSFWorkbook} for the XLSX format.
     */
    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request)
    {
        return new XSSFWorkbook();
    }
    
    protected void copyRow(Row row, Row target)
    {
        int cells = target.getLastCellNum();
        
        Cell cell;
        Cell targetCell;
        
        for (int i = 0; i < cells; i++)
        {
            cell = row.createCell(i);
            targetCell = target.getCell(i);
            cell.setCellType(targetCell.getCellType());
            cell.setCellValue(targetCell.getStringCellValue());
            cell.setCellStyle(targetCell.getCellStyle());
        }
        
        row.setHeight(target.getHeight());
    }
    
    protected Cell cloneCell(Sheet sheet, int row, int col, Row templateRow)
    {
        Row sheetRow = sheet.getRow(row);
        
        if (sheetRow == null)
        {
            sheetRow = sheet.createRow(row);
            
            if (null != templateRow)
            {
                sheetRow.setHeight(templateRow.getHeight());
            }
        }
        
        Cell cell = sheetRow.getCell(col);
        
        if (cell == null)
        {
            cell = sheetRow.createCell(col);
        }
        
        if (null != templateRow)
        {
            cell.setCellStyle(templateRow.getCell(col).getCellStyle());
        }
        
        return cell;
    }
    
    protected void setText(Cell cell, Integer value)
    {
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(value);
    }
    
    protected void setText(Cell cell, String value)
    {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(value);
    }
}
