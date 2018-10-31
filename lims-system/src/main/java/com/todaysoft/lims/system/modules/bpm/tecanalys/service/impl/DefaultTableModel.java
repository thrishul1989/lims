package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl;


import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.ITableModel;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTableModel<E> implements ITableModel
{
    private List<String> columns;

    private List<E> records;

    @Override
    public int getRowCount()
    {
        return Collections3.isEmpty(records) ? 0 : records.size();
    }

    @Override
    public int getColumnCount()
    {
        return Collections3.isEmpty(columns) ? 0 : columns.size();
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        return columns.get(columnIndex);
    }

    @Override
    public E getRecord(int rowIndex)
    {
        return records.get(rowIndex);
    }

    @Override
    public String getCellValue(int rowIndex, int columnIndex)
    {
        E record = getRecord(rowIndex);
        String columnName = getColumnName(columnIndex);
        Map<String,String> data = (Map<String,String>)Reflections.getFieldValue(record,"map");
        if(null != data)
        {
            return data.get(columnName);
        }
        return null;

    }

    public DefaultTableModel(List<String> columns,List<E> records)
    {
        this.columns = columns;
        this.records = records;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<E> getRecords() {
        return records;
    }

    public void setRecords(List<E> records) {
        this.records = records;
    }
}
