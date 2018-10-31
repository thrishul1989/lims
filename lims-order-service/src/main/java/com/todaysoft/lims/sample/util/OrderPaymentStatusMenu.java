package com.todaysoft.lims.sample.util;

import java.util.Map;
import java.util.TreeMap;

public enum OrderPaymentStatusMenu
{
    
    UNPAY(0, "未付款"), PAYUNSURE(1, "付款待确认"), PAYED(2, "已支付");
    
    private Integer value;
    
    private String hint;
    
    OrderPaymentStatusMenu(int value, String hint)
    {
        this.value = value;
        this.hint = hint;
    }
    
    public static Map<Integer, OrderPaymentStatusMenu> list()
    {
        Map<Integer, OrderPaymentStatusMenu> map = new TreeMap<Integer, OrderPaymentStatusMenu>();
        for (OrderPaymentStatusMenu i : OrderPaymentStatusMenu.values())
        {
            map.put(i.value, i);
        }
        return map;
    }
    
    public static OrderPaymentStatusMenu valuesOf(Integer value)
    {
        for (OrderPaymentStatusMenu i : OrderPaymentStatusMenu.values())
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
        for (OrderPaymentStatusMenu i : OrderPaymentStatusMenu.values())
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
