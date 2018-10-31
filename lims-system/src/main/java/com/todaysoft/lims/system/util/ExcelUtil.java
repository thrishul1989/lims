package com.todaysoft.lims.system.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.todaysoft.lims.utils.StringUtils;

/**
 * 
 * <p class="detail">
 * 描述：poi根据模板导出excel，根据excel坐标赋值，如（B1）
 * </p>
 * 
 * @ClassName: ExcelUtil
 */
public class ExcelUtil
{
    
    // 模板map
    private Map<String, Workbook> tempWorkbook = new HashMap<String, Workbook>();
    
    // 模板输入流map
    private Map<String, FileInputStream> tempStream = new HashMap<String, FileInputStream>();
    
    /**
     * 
     * <p class="detail">
     * 描述：临时单元格数据
     * </p>
     * 
     * @ClassName: Cell
     */
    class TempCell
    {
        private int row;
        
        private int column;
        
        private CellStyle cellStyle;
        
        private Object data;
        
        // 用于列表合并，表示几列合并
        private int columnSize = -1;
        
        public int getColumn()
        {
            return column;
        }
        
        public void setColumn(int column)
        {
            this.column = column;
        }
        
        public int getRow()
        {
            return row;
        }
        
        public void setRow(int row)
        {
            this.row = row;
        }
        
        public CellStyle getCellStyle()
        {
            return cellStyle;
        }
        
        public void setCellStyle(CellStyle cellStyle)
        {
            this.cellStyle = cellStyle;
        }
        
        public Object getData()
        {
            return data;
        }
        
        public void setData(Object data)
        {
            this.data = data;
        }
        
        public int getColumnSize()
        {
            return columnSize;
        }
        
        public void setColumnSize(int columnSize)
        {
            this.columnSize = columnSize;
        }
    }
    
    /**
     * 
     * <p class="detail">
     * 功能：按模板向Excel中相应地方填充数据
     * </p>
     * 
     * @param tempFilePath
     * @param dataMap
     * @param sheetNo
     * @throws IOException
     */
    public void writeData(String tempFilePath, Map<String, Object> dataMap, Sheet wsheet) throws IOException
    {
        // 获取模板填充格式位置等数据
        // HashMap tem = getTemp(tempFilePath,sheet);
        // 读取模板
        // Workbook wbModule = getTempWorkbook(tempFilePath);
        // 数据填充的sheet
        // Sheet wsheet = wbModule.getSheetAt(sheetNo);
        
        Iterator it = dataMap.entrySet().iterator();
        while (it.hasNext())
        {
            Entry<String, Object> entry = (Entry<String, Object>)it.next();
            String point = entry.getKey();
            Object data = entry.getValue();
            
            TempCell cell = getCell(point, data, wsheet);
            // 指定坐标赋值
            setCell(cell, wsheet);
        }
        // 设置生成excel中公式自动计算
        wsheet.setForceFormulaRecalculation(true);
    }
    
    /**
     * 
     * <p class="detail">
     * 功能：按模板向Excel中列表填充数据。 只支持列合并
     * </p>
     * 
     * @date 2015年9月27日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param tempFilePath
     * @param heads
     *            列表头部位置集合
     * @param datalist
     * @param sheetNo
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void writeDateList(String tempFilePath, String[] heads, List<Map<Integer, Object>> datalist, Sheet wsheet) throws FileNotFoundException, IOException
    {
        /*
         * //读取模板 Workbook wbModule = getTempWorkbook(tempFilePath);
         * //数据填充的sheet Sheet wsheet = wbModule.getSheetAt(sheetNo);
         * //列表数据模板cell
         */List<TempCell> tempCells = new ArrayList<TempCell>();
        for (int i = 0; i < heads.length; i++)
        {
            String point = heads[i];
            TempCell tempCell = getCell(point, null, wsheet);
            // 取得合并单元格位置 -1：表示不是合并单元格
            int pos = isMergedRegion(wsheet, tempCell.getRow(), tempCell.getColumn());
            if (pos > -1)
            {
                CellRangeAddress range = wsheet.getMergedRegion(pos);
                tempCell.setColumnSize(range.getLastColumn() - range.getFirstColumn());
            }
            tempCells.add(tempCell);
        }
        // 赋值
        for (int i = 0; i < datalist.size(); i++)
        {
            Map<Integer, Object> dataMap = datalist.get(i);
            for (int j = 0; j < tempCells.size(); j++)
            {
                TempCell tempCell = tempCells.get(j);
                tempCell.setRow(tempCell.getRow() + 1);
                tempCell.setData(dataMap.get(j + 1));
                setListCell(tempCell, wsheet);
            }
        }
    }
    
    /**
     * 
     * <p class="detail">
     * 功能：获取输入工作区
     * </p>
     * 
     * @date 2015年9月26日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param tempFilePath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Workbook getTempWorkbook(String tempFilePath) throws FileNotFoundException, IOException
    {
        if (!tempWorkbook.containsKey(tempFilePath))
        {
            if (tempFilePath.endsWith(".xlsx"))
            {
                tempWorkbook.put(tempFilePath, new XSSFWorkbook(getFileInputStream(tempFilePath)));
            }
            else if (tempFilePath.endsWith(".xls"))
            {
                tempWorkbook.put(tempFilePath, new HSSFWorkbook(getFileInputStream(tempFilePath)));
            }
        }
        return tempWorkbook.get(tempFilePath);
    }
    
    public Workbook getTempFinancialWorkbook(String tempFilePath) throws FileNotFoundException, IOException
    {
        if (!tempWorkbook.containsKey(tempFilePath))
        {
            if (tempFilePath.endsWith(".xlsx"))
            {
                XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(new File(tempFilePath)));
                SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
                tempWorkbook.put(tempFilePath, sxssfWorkbook);
            }
            else if (tempFilePath.endsWith(".xls"))
            {
                tempWorkbook.put(tempFilePath, new HSSFWorkbook(getFileInputStream(tempFilePath)));
            }
        }
        return tempWorkbook.get(tempFilePath);
        
    }
    
    /**
     * 
     * <p class="detail">
     * 功能：获得模板输入流
     * </p>
     * 
     * @date 2015年9月26日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param tempFilePath
     * @return
     * @throws FileNotFoundException
     */
    private FileInputStream getFileInputStream(String tempFilePath) throws FileNotFoundException
    {
        if (!tempStream.containsKey(tempFilePath))
        {
            tempStream.put(tempFilePath, new FileInputStream(tempFilePath));
        }
        return tempStream.get(tempFilePath);
    }
    
    /**
     * 
     * <p class="detail">
     * 功能：设置单元格数据,样式 (根据坐标：B3)
     * </p>
     * 
     * @date 2015年9月27日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param point
     * @param data
     * @param sheet
     * @return
     */
    private TempCell getCell(String point, Object data, Sheet sheet)
    {
        TempCell tempCell = new TempCell();
        
        // 得到列 字母
        String lineStr = "";
        String reg = "[A-Z]+|\\[|\\\\|\\]|\\^|\\_|\\`";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(point);
        while (m.find())
        {
            lineStr = m.group();
        }
        // 将列字母转成列号 根据ascii转换
        char[] ch = lineStr.toCharArray();
        int column = 0;
        for (int i = 0; i < ch.length; i++)
        {
            char c = ch[i];
            int post = ch.length - i - 1;
            int r = (int)Math.pow(10, post);
            column = column + r * (c - 65);
        }
        tempCell.setColumn(column);
        
        // 得到行号
        reg = "[1-9]+";
        p = Pattern.compile(reg);
        m = p.matcher(point);
        while (m.find())
        {
            tempCell.setRow((Integer.parseInt(m.group()) - 1));
        }
        
        // 获取模板指定单元格样式，设置到tempCell （写列表数据的时候用）
        Row rowIn = sheet.getRow(tempCell.getRow());
        if (rowIn == null)
        {
            rowIn = sheet.createRow(tempCell.getRow());
        }
        Cell cellIn = rowIn.getCell(tempCell.getColumn());
        if (cellIn == null)
        {
            cellIn = rowIn.createCell(tempCell.getColumn());
        }
        tempCell.setCellStyle(cellIn.getCellStyle());
        
        tempCell.setData(data);
        return tempCell;
    }
    
    /**
     * 
     * <p class="detail">
     * 功能：给指定坐标赋值
     * </p>
     * 
     * @date 2015年9月27日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param tempCell
     * @param sheet
     */
    private void setCell(TempCell tempCell, Sheet sheet)
    {
        if (tempCell.getColumnSize() > -1)
        {
            CellRangeAddress rangeAddress =
                mergeRegion(sheet, tempCell.getRow(), tempCell.getRow(), tempCell.getColumn(), tempCell.getColumn() + tempCell.getColumnSize());
            setRegionStyle(tempCell.getCellStyle(), rangeAddress, sheet);
        }
        
        Row rowIn = sheet.getRow(tempCell.getRow());
        if (rowIn == null)
        {
            rowIn = sheet.createRow(tempCell.getRow());
        }
        Cell cellIn = rowIn.getCell(tempCell.getColumn());
        if (cellIn == null)
        {
            cellIn = rowIn.createCell(tempCell.getColumn());
        }
        // 根据data类型给cell赋值
        if (tempCell.getData() instanceof String)
        {
            cellIn.setCellValue((String)tempCell.getData());
        }
        else if (tempCell.getData() instanceof Integer)
        {
            cellIn.setCellValue((int)tempCell.getData());
        }
        else if (tempCell.getData() instanceof Double)
        {
            cellIn.setCellValue((double)tempCell.getData());
        }
        else if (tempCell.getData() instanceof BigDecimal)
        {
            cellIn.setCellValue(tempCell.getData().toString());
        }
        else
        {
            cellIn.setCellValue((String)tempCell.getData());
        }
        // 样式
        if (tempCell.getCellStyle() != null && tempCell.getColumnSize() == -1)
        {
            cellIn.setCellStyle(tempCell.getCellStyle());
        }
        
    }
    
    private void setListCell(TempCell tempCell, Sheet sheet)
    {
        if (tempCell.getColumnSize() > -1)
        {
            CellRangeAddress rangeAddress =
                mergeRegion(sheet, tempCell.getRow(), tempCell.getRow(), tempCell.getColumn(), tempCell.getColumn() + tempCell.getColumnSize());
            setRegionStyle(tempCell.getCellStyle(), rangeAddress, sheet);
        }
        
        Row rowIn = sheet.getRow(tempCell.getRow());
        if (rowIn == null)
        {
            rowIn = sheet.createRow(tempCell.getRow());
        }
        Cell cellIn = rowIn.getCell(tempCell.getColumn());
        if (cellIn == null)
        {
            cellIn = rowIn.createCell(tempCell.getColumn());
        }
        // 根据data类型给cell赋值
        if (tempCell.getData() instanceof String)
        {
            cellIn.setCellValue((String)tempCell.getData());
        }
        else if (tempCell.getData() instanceof Integer)
        {
            cellIn.setCellValue((int)tempCell.getData());
        }
        else if (tempCell.getData() instanceof Double)
        {
            cellIn.setCellValue((double)tempCell.getData());
        }
        else if (tempCell.getData() instanceof BigDecimal)
        {
            cellIn.setCellValue(tempCell.getData().toString());
        }
        else
        {
            cellIn.setCellValue((String)tempCell.getData());
        }
        // 样式
        if (tempCell.getCellStyle() != null && tempCell.getColumnSize() == -1)
        {
            Font font = sheet.getWorkbook().createFont();
            font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            font.setFontName("宋体");
            font.setFontHeightInPoints((short)11);
            CellStyle style = sheet.getWorkbook().createCellStyle();
            style.setFont(font);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setBorderLeft(CellStyle.BORDER_THIN);
            cellIn.setCellStyle(style);
        }
        
    }
    
    /**
     * 
     * <p class="detail">
     * 功能：写到输出流并移除资源
     * </p>
     * 
     * @date 2015年9月27日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param tempFilePath
     * @param os
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void writeAndClose(String tempFilePath, OutputStream os) throws FileNotFoundException, IOException
    {
        if (getTempWorkbook(tempFilePath) != null)
        {
            getTempWorkbook(tempFilePath).write(os);
            tempWorkbook.remove(tempFilePath);
        }
        if (getFileInputStream(tempFilePath) != null)
        {
            getFileInputStream(tempFilePath).close();
            tempStream.remove(tempFilePath);
        }
    }
    
    public void write(String tempFilePath, OutputStream os) throws FileNotFoundException, IOException
    {
        if (getTempWorkbook(tempFilePath) != null)
        {
            getTempWorkbook(tempFilePath).write(os);
        }
        
    }
    
    public void close(String tempFilePath, OutputStream os) throws FileNotFoundException, IOException
    {
        if (getFileInputStream(tempFilePath) != null)
        {
            getFileInputStream(tempFilePath).close();
            tempStream.remove(tempFilePath);
        }
        
    }
    
    /**
     * 
     * <p class="detail">
     * 功能：判断指定的单元格是否是合并单元格
     * </p>
     * 
     * @date 2015年9月27日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param sheet
     * @param row
     * @param column
     * @return 0:不是合并单元格，i:合并单元格的位置
     */
    private Integer isMergedRegion(Sheet sheet, int row, int column)
    {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++)
        {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow)
            {
                if (column >= firstColumn && column <= lastColumn)
                {
                    return i;
                }
            }
        }
        return -1;
    }
    
    /**
     * 
     * <p class="detail">
     * 功能：合并单元格
     * </p>
     * 
     * @date 2015年9月27日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param sheet
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    private CellRangeAddress mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol)
    {
        CellRangeAddress rang = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(rang);
        return rang;
    }
    
    /**
     * 
     * <p class="detail">
     * 功能：设置合并单元格样式
     * </p>
     * 
     * @date 2015年9月27日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param cs
     * @param region
     * @param sheet
     */
    private static void setRegionStyle(CellStyle cs, CellRangeAddress region, Sheet sheet)
    {
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++)
        {
            Row row = sheet.getRow(i);
            if (row == null)
                row = sheet.createRow(i);
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++)
            {
                Cell cell = row.getCell(j);
                if (cell == null)
                {
                    cell = row.createCell(j);
                    cell.setCellValue("");
                }
                cell.setCellStyle(cs);
            }
        }
    }
    
    /**
     * 合并单元格
     * 
     * @param sheet
     *            要合并单元格的excel 的sheet
     * @param cellLine
     *            要合并的列
     * @param startRow
     *            要合并列的开始行
     * @param endRow
     *            要合并列的结束行
     */
    public void addMergedRegion(Sheet sheet, int cellLine, int startRow, int endRow, Workbook workBook)
    {
        
        CellStyle style = workBook.createCellStyle(); // 样式对象
        
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 获取第一行的数据,以便后面进行比较
        String s_will = getValue(sheet.getRow(startRow).getCell(cellLine));
        int count = 0;
        boolean flag = false;
        for (int i = 1; i <= endRow; i++)
        {
            String s_current = getValue(sheet.getRow(i).getCell(0));
            if (s_will.equals(s_current))
            {
                s_will = s_current;
                if (flag)
                {
                    sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
                    Row row = sheet.getRow(startRow - count);
                    String cellValueTemp = getValue(sheet.getRow(startRow - count).getCell(0));
                    Cell cell = row.createCell(0);
                    cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                    cell.setCellStyle(style); // 样式
                    count = 0;
                    flag = false;
                }
                startRow = i;
                count++;
            }
            else
            {
                flag = true;
                s_will = s_current;
            }
            if (i == endRow && count > 0)
            {
                sheet.addMergedRegion(new CellRangeAddress(endRow - count, endRow, cellLine, cellLine));
                String cellValueTemp = getValue(sheet.getRow(startRow - count).getCell(0));
                Row row = sheet.getRow(startRow - count);
                Cell cell = row.createCell(0);
                cell.setCellValue(cellValueTemp); // 跨单元格显示的数据    
                cell.setCellStyle(style); // 样式    
            }
            
        }
    }
    
    //NEW 
    public void addMergedRegion2(Sheet sheet, int cellLine, int startRow, int endRow, Workbook workBook)
    {
        
        CellStyle style = workBook.createCellStyle(); // 样式对象
        
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 获取第一行的数据,以便后面进行比较
        String s_will = getValue(sheet.getRow(startRow).getCell(cellLine));
        int count = 0;
        boolean flag = false;
        for (int i = 1; i <= endRow; i++)
        {
            String s_current = getValue(sheet.getRow(i).getCell(cellLine));
            if (s_will.equals(s_current))
            {
                s_will = s_current;
                if (flag)
                {
                    sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
                    Row row = sheet.getRow(startRow - count);
                    String cellValueTemp = getValue(sheet.getRow(startRow - count).getCell(cellLine));
                    Cell cell = row.createCell(cellLine);
                    cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                    cell.setCellStyle(style); // 样式
                    count = 0;
                    flag = false;
                }
                startRow = i;
                count++;
            }
            else
            {
                flag = true;
                s_will = s_current;
            }
            if (i == endRow && count > 0)
            {
                sheet.addMergedRegion(new CellRangeAddress(endRow - count, endRow, cellLine, cellLine));
                String cellValueTemp = getValue(sheet.getRow(startRow - count).getCell(cellLine));
                Row row = sheet.getRow(startRow - count);
                Cell cell = row.createCell(cellLine);
                cell.setCellValue(cellValueTemp); // 跨单元格显示的数据    
                cell.setCellStyle(style); // 样式    
            }
            
        }
    }
    
    public void mergedRegion(Sheet sheet, int cellLine, int startRow, int endRow, Workbook workBook)
    {
        
        CellStyle style = workBook.createCellStyle(); // 样式对象
        
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        // 获取第一行的数据,以便后面进行比较
        
        String s_will = getValue(sheet.getRow(startRow).getCell(cellLine));
        
        int count = 0;
        boolean flag = false;
        for (int i = startRow; i <= endRow; i++)
        {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;
            String s_current = getValue(row.getCell(0));
            if (s_will.equals(s_current) && StringUtils.isNotEmpty(s_current))
            {
                s_will = s_current;
                if (flag)
                {
                    sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
                    row = sheet.getRow(startRow - count);
                    String cellValueTemp = getValue(sheet.getRow(startRow - count).getCell(0));
                    Cell cell = row.createCell(0);
                    cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                    cell.setCellStyle(style); // 样式
                    count = 0;
                    flag = false;
                }
                startRow = i;
                count++;
            }
            else
            {
                flag = true;
                s_will = s_current;
            }
            // 由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
            if (i == endRow && count > 0)
            {
                sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
                String cellValueTemp = getValue(sheet.getRow(startRow - count).getCell(0));
                Row anoRow = sheet.getRow(startRow - count);
                Cell cell = anoRow.createCell(0);
                cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                cell.setCellStyle(style); // 样式
            }
        }
    }
    
    private String getValue(Cell hssfCell)
    {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN)
        {
            return String.valueOf(hssfCell.getBooleanCellValue());
        }
        else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC)
        {
            return String.valueOf((int)hssfCell.getNumericCellValue());
        }
        else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_STRING)
        {
            return String.valueOf(hssfCell.getStringCellValue());
        }
        else
        {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    
    public void mergedRegion2(Sheet sheet, int cellLine, int startRow, int endRow, Workbook workBook)
    {
        
        CellStyle style = workBook.createCellStyle(); // 样式对象
        
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        // 获取第一行的数据,以便后面进行比较
        
        String s_will = sheet.getRow(startRow).getCell(cellLine).getStringCellValue();
        
        int count = 0;
        boolean flag = false;
        for (int i = startRow; i <= endRow; i++)
        {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;
            row.getCell(cellLine).setCellType(Cell.CELL_TYPE_STRING);
            String s_current = row.getCell(cellLine).getStringCellValue();
            if (s_will.equals(s_current) && StringUtils.isNotEmpty(s_current))
            {
                s_will = s_current;
                if (flag)
                {
                    sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
                    row = sheet.getRow(startRow - count);
                    String cellValueTemp = sheet.getRow(startRow - count).getCell(cellLine).getStringCellValue();
                    Cell cell = row.createCell(cellLine);
                    cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                    cell.setCellStyle(style); // 样式
                    count = 0;
                    flag = false;
                }
                startRow = i;
                count++;
            }
            else
            {
                flag = true;
                s_will = s_current;
            }
            // 由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
            if (i == endRow && count > 0)
            {
                sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
                String cellValueTemp = sheet.getRow(startRow - count).getCell(cellLine).getStringCellValue();
                Row anoRow = sheet.getRow(startRow - count);
                Cell cell = anoRow.createCell(cellLine);
                cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                cell.setCellStyle(style); // 样式
            }
        }
    }
    
    public void mergedRegion3(Sheet sheet, int cellLine, int startRow, int endRow, Workbook workBook)
    {
        
        CellStyle style = workBook.createCellStyle(); // 样式对象
        
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        // 获取第一行的数据,以便后面进行比较
        
        String s_will = sheet.getRow(startRow).getCell(1).getStringCellValue();
        
        int count = 0;
        boolean flag = false;
        for (int i = startRow; i <= endRow; i++)
        {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;
            String s_current = row.getCell(1).getStringCellValue();
            if (s_will.equals(s_current) && StringUtils.isNotEmpty(s_current))
            {
                s_will = s_current;
                if (flag)
                {
                    sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
                    row = sheet.getRow(startRow - count);
                    String cellValueTemp = sheet.getRow(startRow - count).getCell(1).getStringCellValue();
                    Cell cell = row.createCell(1);
                    cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                    cell.setCellStyle(style); // 样式
                    count = 0;
                    flag = false;
                }
                startRow = i;
                count++;
            }
            else
            {
                flag = true;
                s_will = s_current;
            }
            // 由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
            if (i == endRow && count > 0)
            {
                sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
                String cellValueTemp = sheet.getRow(startRow - count).getCell(1).getStringCellValue();
                Row anoRow = sheet.getRow(startRow - count);
                Cell cell = anoRow.createCell(1);
                cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                cell.setCellStyle(style); // 样式
            }
        }
    }
    
    /*-----------------------------------------无模板无注解导出----------------------------------------------------*/
    /*  一. 操作流程 ：                                                                                           */
    /*      1) 写入表头内容(可选)                                                                                  */
    /*      2) 写入数据内容                                                                                       */
    /*  二. 参数说明                                                                                              */
    /*      *) data             =>      导出内容List集合                                                          */
    /*      *) header           =>      表头集合,有则写,无则不写                                                   */
    /*      *) sheetName        =>      Sheet索引名(默认0)                                                        */
    /*      *) isXSSF           =>      是否Excel2007以上                                                         */
    /*      *) targetPath       =>      导出文件路径                                                              */
    /*      *) os               =>      导出文件流                                                                */
    
    public void exportObjects2Excel(List<?> data, List<String> header, Workbook wbWork, String sheetName, boolean isXSSF, String targetPath) throws Exception
    {
        
        exportExcelNoModuleHandler(data, header, wbWork, sheetName, isXSSF);
    }
    
    public void exportObjects2Excel(List<?> data, List<String> header, String sheetName, boolean isXSSF, OutputStream os) throws Exception
    {
        
        exportExcelNoModuleHandler(data, header, null, sheetName, isXSSF).write(os);
    }
    
    public void exportObjects2Excel(List<?> data, List<String> header, String targetPath) throws Exception
    {
        
        exportExcelNoModuleHandler(data, header, null, null, true).write(new FileOutputStream(targetPath));
    }
    
    public void exportObjects2Excel(List<?> data, List<String> header, OutputStream os) throws Exception
    {
        
        exportExcelNoModuleHandler(data, header, null, null, true).write(os);
    }
    
    public void exportObjects2Excel(List<?> data, String targetPath) throws Exception
    {
        
        exportExcelNoModuleHandler(data, null, null, null, true).write(new FileOutputStream(targetPath));
    }
    
    public void exportObjects2Excel(List<?> data, OutputStream os) throws Exception
    {
        
        exportExcelNoModuleHandler(data, null, null, null, true).write(os);
    }
    
    private Workbook exportExcelNoModuleHandler(List<?> data, List<String> header, Workbook workbook, String sheetName, boolean isXSSF) throws Exception
    {
        
        Sheet sheet;
        if (null != sheetName && !"".equals(sheetName))
        {
            sheet = workbook.createSheet(sheetName);
        }
        else
        {
            sheet = workbook.createSheet();
        }
        
        int rowIndex = 0;
        if (null != header && header.size() > 0)
        {
            // 写标题
            Row row = sheet.createRow(rowIndex);
            for (int i = 0; i < header.size(); i++)
            {
                row.createCell(i, Cell.CELL_TYPE_STRING).setCellValue(header.get(i));
            }
            rowIndex++;
        }
        for (Object object : data)
        {
            if (null == object)
            {
                continue;
            }
            Row row = sheet.createRow(rowIndex);
            if (object.getClass().isArray())
            {
                for (int j = 0; j < Array.getLength(object); j++)
                {
                    row.createCell(j, Cell.CELL_TYPE_STRING).setCellValue(Array.get(object, j).toString());
                }
            }
            else if (object instanceof Collection)
            {
                Collection<?> items = (Collection<?>)object;
                int j = 0;
                for (Object item : items)
                {
                    String value = "";
                    if (null != item)
                    {
                        value = item.toString();
                    }
                    row.createCell(j, Cell.CELL_TYPE_STRING).setCellValue(value);
                    j++;
                }
            }
            else
            {
                row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(object.toString());
            }
            rowIndex++;
        }
        return workbook;
    }
    
    //模板 表头固定 导出
    public void exportObjects2ModuleExcel(List<?> data, int rowIndex, Workbook wbWork, String sheetName, boolean isXSSF, String targetPath) throws Exception
    {
        
        exportExcelModuleHandler(data, rowIndex, wbWork, sheetName, isXSSF);
    }
    
    private Workbook exportExcelModuleHandler(List<?> data, int rowIndex, Workbook workbook, String sheetName, boolean isXSSF) throws Exception
    {
        CellStyle style3 = workbook.createCellStyle(); // 样式对象
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        
        Sheet sheet;
        if (null != sheetName && !"".equals(sheetName))
        {
            sheet = workbook.getSheet(sheetName);
        }
        else
        {
            sheet = workbook.createSheet();
        }
        
        for (Object object : data)
        {
            if (null == object)
            {
                continue;
            }
            Row row = sheet.createRow(rowIndex);
            if (object.getClass().isArray())
            {
                for (int j = 0; j < Array.getLength(object); j++)
                {
                    Cell cell = row.createCell(j, Cell.CELL_TYPE_STRING);
                    if (null != Array.get(object, j))
                    {
                        cell.setCellValue(Array.get(object, j).toString());
                    }
                    else
                    {
                        cell.setCellValue("");
                    }
                    cell.setCellStyle(style3);
                }
            }
            else if (object instanceof Collection)
            {
                Collection<?> items = (Collection<?>)object;
                int j = 0;
                for (Object item : items)
                {
                    String value = "";
                    if (null != item)
                    {
                        value = item.toString();
                    }
                    row.createCell(j, Cell.CELL_TYPE_STRING).setCellValue(value);
                    j++;
                }
            }
            else
            {
                row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(object.toString());
            }
            rowIndex++;
        }
        return workbook;
    }
}