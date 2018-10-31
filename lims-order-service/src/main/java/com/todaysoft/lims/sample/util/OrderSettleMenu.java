package com.todaysoft.lims.sample.util;

import java.util.Map;
import java.util.TreeMap;

public enum OrderSettleMenu
{
    
    ONE(1, "一次性付款"), TWO(2, "分批付款"), THREE(3, "月结"), FOUR(4, "一单一结");
    
    private Integer value;
    
    private String hint;
    
    OrderSettleMenu(int value, String hint)
    {
        this.value = value;
        this.hint = hint;
    }
    
    public static Map<Integer, OrderSettleMenu> list()
    {
        Map<Integer, OrderSettleMenu> map = new TreeMap<Integer, OrderSettleMenu>();
        for (OrderSettleMenu i : OrderSettleMenu.values())
        {
            map.put(i.value, i);
        }
        return map;
    }
    
    public static OrderSettleMenu valuesOf(Integer value)
    {
        for (OrderSettleMenu i : OrderSettleMenu.values())
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
        for (OrderSettleMenu i : OrderSettleMenu.values())
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
