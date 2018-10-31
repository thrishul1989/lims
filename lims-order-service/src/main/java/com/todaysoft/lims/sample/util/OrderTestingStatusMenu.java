package com.todaysoft.lims.sample.util;

import java.util.Map;
import java.util.TreeMap;

public enum OrderTestingStatusMenu
{
    
    DRAFT(0, "暂存"), DELEGATED(1, "待检测下放"), PROCESSING(2, "检测中"), PAUSED(3, "已暂停"), CANCELED(4, "已取消"), FINISHED(5, "已完成");
    
    private Integer value;
    
    private String hint;
    
    OrderTestingStatusMenu(int value, String hint)
    {
        this.value = value;
        this.hint = hint;
    }
    
    public static Map<Integer, OrderTestingStatusMenu> list()
    {
        Map<Integer, OrderTestingStatusMenu> map = new TreeMap<Integer, OrderTestingStatusMenu>();
        for (OrderTestingStatusMenu i : OrderTestingStatusMenu.values())
        {
            map.put(i.value, i);
        }
        return map;
    }
    
    public static OrderTestingStatusMenu valuesOf(Integer value)
    {
        for (OrderTestingStatusMenu i : OrderTestingStatusMenu.values())
        {
            if (i.value.equals(value))
            {
                return i;
            }
        }
        return null;
    }
    
    public static String hintOfValue(Integer value)
    {
        for (OrderTestingStatusMenu i : OrderTestingStatusMenu.values())
        {
            if (i.value.equals(value))
            {
                return i.hint;
            }
        }
        return null;
    }
    
    public String getHint()
    {
        return hint;
    }
    
    public void setHint(String hint)
    {
        this.hint = hint;
    }
    
}
