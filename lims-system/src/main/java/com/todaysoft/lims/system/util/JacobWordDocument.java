package com.todaysoft.lims.system.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobWordDocument
{
    private ActiveXComponent word;// word运行程序对象 
    
    private Dispatch documents;// word运行程序对象 
    
    private Dispatch document; // word文档 
    
    private Dispatch cursor;// 选定的范围或插入点
    
    private Dispatch markers;
    
    private Dispatch tables;
    
    public JacobWordDocument(String path)
    {
        ComThread.InitSTA();
        word = new ActiveXComponent("Word.Application");
        documents = word.getProperty("Documents").toDispatch();
        document = Dispatch.invoke(documents, "Open", Dispatch.Method, new Object[] {path, new Variant(false), new Variant(false)}, new int[1]).toDispatch();
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
        return Dispatch.invoke(rows, "item", Dispatch.Method, new Object[] {new Integer(index)}, new int[1]).toDispatch();
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
    
    public void insertPicture(String imagePath)
    {
        Dispatch.call(Dispatch.get(cursor, "InLineShapes").toDispatch(), "AddPicture", imagePath).toDispatch();
    }
}
