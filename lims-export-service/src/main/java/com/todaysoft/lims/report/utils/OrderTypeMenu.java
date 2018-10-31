package com.todaysoft.lims.report.utils;

import java.util.Map;
import java.util.TreeMap;

public enum OrderTypeMenu
{
    
    ONE(1, "临床遗传"), TWO(2, "临床肿瘤"), THREE(3, "健康筛查"), FOUR(4, "科技服务");
    
    private Integer value;
    
    private String hint;
    
    OrderTypeMenu(int value, String hint)
    {
        this.value = value;
        this.hint = hint;
    }
    
    public static Map<Integer, OrderTypeMenu> list()
    {
        Map<Integer, OrderTypeMenu> map = new TreeMap<Integer, OrderTypeMenu>();
        for (OrderTypeMenu i : OrderTypeMenu.values())
        {
            map.put(i.value, i);
        }
        return map;
    }
    
    public static OrderTypeMenu valuesOf(Integer value)
    {
        for (OrderTypeMenu i : OrderTypeMenu.values())
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
        for (OrderTypeMenu i : OrderTypeMenu.values())
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
