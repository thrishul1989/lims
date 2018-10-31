package com.todaysoft.lims.system.modules.bpm.tecanalys.service;



public interface ITableModel<E>
{
    int getRowCount();

    int getColumnCount();

    String getColumnName(int columnIndex);

    E getRecord(int rowIndex);

    String getCellValue(int rowIndex, int columnIndex);
}
