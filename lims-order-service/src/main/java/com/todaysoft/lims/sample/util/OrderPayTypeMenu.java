package com.todaysoft.lims.sample.util;

import java.util.Map;
import java.util.TreeMap;

public enum OrderPayTypeMenu
{
    
    ONE("1", "支付宝"), TWO("2", "支付宝扫码"), THREE("3", "POS机"), FOUR("4", "转账");
    
    private String value;
    
    private String hint;
    
    OrderPayTypeMenu(String value, String hint)
    {
        this.value = value;
        this.hint = hint;
    }
    
    public static Map<String, OrderPayTypeMenu> list()
    {
        Map<String, OrderPayTypeMenu> map = new TreeMap<String, OrderPayTypeMenu>();
        for (OrderPayTypeMenu i : OrderPayTypeMenu.values())
        {
            map.put(i.value, i);
        }
        return map;
    }
    
    public static OrderPayTypeMenu valuesOf(String value)
    {
        for (OrderPayTypeMenu i : OrderPayTypeMenu.values())
        {
            if (i.value == value)
            {
                return i;
            }
        }
        return null;
    }
    
    public static String hintOfValue(String value)
    {
        for (OrderPayTypeMenu i : OrderPayTypeMenu.values())
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
