package com.todaysoft.lims.sample.util;

import java.util.Map;
import java.util.TreeMap;

public enum OrderSamplePurposeMenu
{
    
    ONE(1, "一代验证"), TWO(2, "检测"), THREE(3, "本人对照"), FOUR(4, "暂存");
    
    private Integer value;
    
    private String hint;
    
    OrderSamplePurposeMenu(int value, String hint)
    {
        this.value = value;
        this.hint = hint;
    }
    
    public static Map<Integer, OrderSamplePurposeMenu> list()
    {
        Map<Integer, OrderSamplePurposeMenu> map = new TreeMap<Integer, OrderSamplePurposeMenu>();
        for (OrderSamplePurposeMenu i : OrderSamplePurposeMenu.values())
        {
            map.put(i.value, i);
        }
        return map;
    }
    
    public static OrderSamplePurposeMenu valuesOf(Integer value)
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
    
    public static String hintOfValue(Integer value)
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
