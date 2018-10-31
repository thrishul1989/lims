package com.todaysoft.lims.technical.export.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobWordDocument
{
    private ActiveXComponent word;
    
    private Dispatch documents;
    
    private Dispatch document;
    
    private Dispatch cursor;
    
    private Dispatch markers;
    
    private Dispatch tables;
    
    public JacobWordDocument(String path)
    {
        ComThread.InitSTA();
        word = new ActiveXComponent("Word.Application");
        documents = word.getProperty("Documents").toDispatch();
        document =
            Dispatch.invoke(documents,
                "Open",
                Dispatch.Method,
                new Object[] {path, new Variant(false), new Variant(false)},
                new int[1]).toDispatch();
        cursor = Dispatch.get(word, "Selection").toDispatch();
        markers = Dispatch.call(document, "Bookmarks").toDispatch();
        tables = Dispatch.get(document, "Tables").toDispatch();
    }
    
    public void destory()
    {
        if (null != word)
        {
            Dispatch.call(word, "Quit");
            word = null;
            ComThread.Release();
        }
    }
    
    public Dispatch getMarker(String name)
    {
        boolean exists = Dispatch.call(markers, "Exists", name).getBoolean();
        
        if (!exists)
        {
            return null;
        }
        
        return Dispatch.call(markers, "Item", name).toDispatch();
    }
    
    public Dispatch getTable(int index)
    {
        return Dispatch.call(tables, "Item", new Variant(index)).toDispatch();
    }
    
    public void insertRow(Dispatch table)
    {
        Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
        Dispatch.call(rows, "Add").toDispatch();
    }
    
    public Dispatch getRow(Dispatch table, int index)
    {
        Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
        return Dispatch.invoke(rows, "item", Dispatch.Method, new Object[] {new Integer(index)}, new int[1])
            .toDispatch();
    }
    
    public void setFontColor(String color)
    {
        Dispatch font = Dispatch.get(cursor, "Font").toDispatch();
        Dispatch.put(font, "Color", color);
    }
    
    public void typeText(String text)
    {
        Dispatch.call(cursor, "TypeText", new Variant(text));
    }
    
    public Dispatch getCell(Dispatch table, int row, int column)
    {
        return Dispatch.call(table, "Cell", new Variant(row), new Variant(column)).toDispatch();
    }
    
    public void selectCell(Dispatch table, int row, int column)
    {
        Dispatch.call(getCell(table, row, column), "Select");
    }
    
    public void insertCellText(Dispatch cell, String text)
    {
        Dispatch.call(cell, "Select");
        Dispatch.put(cursor, "Text", text);
    }
    
    public void insertCellText(Dispatch table, int row, int column, String text)
    {
        insertCellText(getCell(table, row, column), text);
    }
    
    public void insertEndCellText(Dispatch table, int row, int column, String text)
    {
        Dispatch range = Dispatch.call(getCell(table, row, column), "Range").toDispatch();
        Dispatch.put(cursor, "Start", Dispatch.get(range, "Start").getInt());
        Dispatch.put(cursor, "Text", text);
    }
    
    public void insertCellPicture(Dispatch table, int row, int column,String imagePath)
    {
        Dispatch range = Dispatch.call(getCell(table, row, column), "Range").toDispatch();
        Dispatch.put(cursor, "End", Dispatch.get(range, "End").getInt());
        Dispatch.call(cursor, "MoveLeft"); //在左边一个单元格插入了
        Dispatch.call(Dispatch.get(cursor, "InLineShapes").toDispatch(),"AddPicture", imagePath).toDispatch(); 
    }
    
    public void setRowBackgroundColor(Dispatch row, Long color)
    {
        Dispatch shading = Dispatch.get(row, "Shading").toDispatch();
        Dispatch.put(shading, "BackgroundPatternColor", new Variant(color));
    }
    
    public void setCursor(Dispatch marker)
    {
        Dispatch range = Dispatch.call(marker, "Range").toDispatch();
        Dispatch.put(cursor, "Start", Dispatch.get(range, "Start").getInt());
    }
    
    public void insertFile(String filepath)
    {
        Dispatch.call(cursor, "InsertFile", filepath);
    }
    
    public void breakInsertFile(String filepath)
    {
        Dispatch.call(cursor, "InsertBreak", new Variant(7));
        Dispatch.call(cursor, "InsertFile", filepath);
    }
    
    public void insertMarkerText(String name, String text)
    {
        Dispatch marker = getMarker(name);
        
        if (null == marker)
        {
            return;
        }
        
        setCursor(marker);
        insertMarkerText(marker, text);
    }
    
    public void insertMarkerEndText(String name, String text)
    {
        Dispatch marker = getMarker(name);
        
        if (null == marker)
        {
            return;
        }
        
        setCursor(marker);
        insertMarkerText(marker, text);
        Dispatch.call(cursor, "MoveDown");
    }
    
    public void insertStartMarkerText(String name, String text)
    {
        Dispatch marker = getMarker(name);
        
        if (null == marker)
        {
            return;
        }
        
        setCursor(marker);
        Dispatch.put(cursor, "Text", new Variant(text));
    }
    
    public void insertMarkerText(Dispatch marker, String text)
    {
        Dispatch range = Dispatch.call(marker, "Range").toDispatch();
        Dispatch.put(range, "Text", new Variant(text));
    }
    
    public void updateFirstTableOfContents()
    {
        Dispatch tablesOfContents = Dispatch.call(document, "TablesOfContents").toDispatch();
        Dispatch first = Dispatch.call(tablesOfContents, "Item", new Variant(1)).toDispatch();
        Dispatch.call(first, "Update");
    }
    
    public void UpdateFirstTableOfPageNumbers()
    {
        Dispatch tablesOfContents = Dispatch.call(document, "TablesOfContents").toDispatch();
        Dispatch first = Dispatch.call(tablesOfContents, "Item", new Variant(1)).toDispatch();
        Dispatch.call(first, "UpdatePageNumbers");
    }

    public void updatePagesFirstTableOfContents()
    {
        Dispatch tablesOfContents = Dispatch.call(document, "TablesOfContents").toDispatch();
        Dispatch first = Dispatch.call(tablesOfContents, "Item", new Variant(1)).toDispatch();
        Dispatch.call(first, "UpdatePageNumbers");
    }
    
    public void saveAs(String filepath)
    {
        Dispatch.call(document, "SaveAs", filepath);
    }

    public void insertMarkerPicture(String name, String imagePath)
    {
        Dispatch marker = getMarker(name);
        
        if (null == marker)
        {
            return;
        }
        
        setRangeCursorEnd(marker);
        insertPicture(imagePath);
    }
    
    public void insertPicture(String imagePath){
    	Dispatch.call(Dispatch.get(cursor,"InLineShapes").toDispatch(),"AddPicture", imagePath).toDispatch();
    }
    
    public void setRangeCursorEnd(Dispatch marker)
    {
        Dispatch range = Dispatch.call(marker, "Range").toDispatch();
        Dispatch.put(cursor, "End", Dispatch.get(range, "End").getInt());
    }
    
    public Dispatch insertMarkerTable(String name,int row, int column) {
        Dispatch tables = Dispatch.get(document, "Tables").toDispatch();  
        Dispatch marker = getMarker(name);
        if (null == marker)
        {
            return null;
        }
        Dispatch range = Dispatch.call(marker, "Range").toDispatch();
        Dispatch newTable = Dispatch.call(tables, "Add",  range,
                new Variant(row), new Variant(column))  //, new Variant(1)
                .toDispatch();// 设置row,column,表格外框宽度  
        return newTable ;   
    }
    
    public Dispatch insertMarkerEndTable(String name,int row, int column) {
        Dispatch tables = Dispatch.get(document, "Tables").toDispatch();  
        Dispatch marker = getMarker(name);
        if (null == marker)
        {
            return null;
        }
        
        Dispatch range = Dispatch.call(marker, "Range").toDispatch();
        Dispatch.put(cursor, "End", Dispatch.get(range, "End").getInt());
        Dispatch range2 = Dispatch.call(marker, "Range").toDispatch();
        Dispatch newTable = Dispatch.call(tables, "Add",  range2,
                new Variant(row), new Variant(column), new Variant(1))  
                .toDispatch();// 设置row,column,表格外框宽度  
        return newTable ;   
    }
    
    public void mergeCell(Dispatch table, int fstCellRowIdx, int fstCellColIdx, int secCellRowIdx, int secCellColIdx){      
        Dispatch fstCell = Dispatch.call(table, "Cell",new Variant(fstCellRowIdx), new Variant(fstCellColIdx)).toDispatch();      
        Dispatch secCell = Dispatch.call(table, "Cell",new Variant(secCellRowIdx), new Variant(secCellColIdx)).toDispatch();      
        Dispatch.call(fstCell, "Merge", secCell);      
    }
    
    public void setTableStyle(Dispatch table) {
        Dispatch oBorders = Dispatch.call(table, "Borders", 0-3).toDispatch();
        Dispatch.put(oBorders, "LineStyle", new Variant(1));
        
        oBorders = Dispatch.call(table, "Borders", 0-1).toDispatch();
        Dispatch.put(oBorders, "LineStyle", new Variant(1));
        
        oBorders = Dispatch.call(table, "Borders", 0-5).toDispatch();
        Dispatch.put(oBorders, "LineStyle", new Variant(1));
        
        Dispatch rows = Dispatch.get(table, "Rows").toDispatch();  
        int rowCount= Integer  
            .valueOf(Dispatch.get(rows, "Count").toString());
        Dispatch cols = Dispatch.get(table,  "Columns" ).toDispatch();
        int celCount= Integer  
            .valueOf(Dispatch.get(cols, "Count").toString());
        for(int j=1;j<=rowCount;j++) {
            for(int k=1;k<=celCount;k++) {
                Dispatch.put(getCell(table, j, k), "VerticalAlignment", new Variant(1));
                selectCell(table, j, k);
                Dispatch alignment = Dispatch.get(cursor, "ParagraphFormat")  
                        .toDispatch();  
                Dispatch.put(alignment, "Alignment", "1");
                Dispatch font = Dispatch.get(cursor,  "Font" ).toDispatch();
                Dispatch.put(font,  "Name" ,  new  Variant("宋体"));
                Dispatch.put(font,  "Size" , 10);
                if(j==1) {
                    Dispatch.put(font,  "Bold" ,  new  Variant(true));
                }else {
                    Dispatch.put(font,  "Bold" ,  new  Variant(false));
                }
            }
        }
    }
    
    public void closeDocument()
    {
    	Dispatch.call(document,"Close",new Variant(0)); 
    }
    
    public void closeDocumentNotSave()
    {
        if(document!=null) {
            Dispatch.call(document,"Close",false); 
        }
    }
    
    public void saveAsPDF(String filepath)
    { 
    	 Dispatch.call(document, "SaveAs", filepath, 17);  
    	 Dispatch.call(document, "Close", false);
    }

    public void setWaterMark(String waterMarkPath)
    {
        Dispatch activeWindow = Dispatch.get(word, "ActiveWindow").toDispatch();
        // 取得活动窗格对象
        Dispatch activePan = Dispatch.get(activeWindow, "ActivePane").toDispatch();
        // 取得视窗对象
        Dispatch view = Dispatch.get(activePan, "View").toDispatch();
        // 打开页眉，值为9，页脚为10
        Dispatch.put(view, "SeekView", new Variant(9));
        Dispatch docSelection = Dispatch.get(activeWindow, "Selection").toDispatch();
        //获取页眉和页脚
        Dispatch headfooter = Dispatch.get(docSelection, "HeaderFooter").toDispatch();
        // 获取水印图形对象
        Dispatch shapes = Dispatch.get(headfooter, "Shapes").toDispatch();
        // 给文档全部加上水印,设置了水印效果，内容，字体，大小，是否加粗，是否斜体，左边距，上边距。
        //调用shapes对象的AddPicture方法将全路径为picname的图片插入当前文档
        Dispatch picture = Dispatch.call(shapes, "AddPicture", waterMarkPath).toDispatch();

        //选择当前word文档的水印
        Dispatch.call(picture, "Select");
        Dispatch.put(picture, "Left", new Variant(382));
        Dispatch.put(picture, "Top", new Variant(4));
        Dispatch.put(picture, "Width", new Variant(142));
        Dispatch.put(picture, "Height", new Variant(37));

        //关闭页眉
        Dispatch.put(view, "SeekView", new Variant(0));

    }
}
