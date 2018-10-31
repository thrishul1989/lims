package com.todaysoft.lims.system.util;

import java.util.Map;
import java.util.TreeMap;

public enum OrderSamplePurposeMenu
{
    
    ONE("1", "一代验证"), TWO("2", "检测"), THREE("3", "本人对照"), FOUR("4", "暂存");
    
    private String value;
    
    private String hint;
    
    OrderSamplePurposeMenu(String value, String hint)
    {
        this.value = value;
        this.hint = hint;
    }
    
    public static Map<String, OrderSamplePurposeMenu> list()
    {
        Map<String, OrderSamplePurposeMenu> map = new TreeMap<String, OrderSamplePurposeMenu>();
        for (OrderSamplePurposeMenu i : OrderSamplePurposeMenu.values())
        {
            map.put(i.value, i);
        }
        return map;
    }
    
    public static OrderSamplePurposeMenu valuesOf(String value)
    {
        for (OrderSamplePurposeMenu i : OrderSamplePurposeMenu.values())
        {
            if (i.value.equals(value))
            {
                return i;
            }
        }
        return null;
    }
    
    public static String hintOfValue(String value)
    {
        if (value == null)
        {
            return "检测";
        }
        for (OrderSamplePurposeMenu i : OrderSamplePurposeMenu.values())
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
