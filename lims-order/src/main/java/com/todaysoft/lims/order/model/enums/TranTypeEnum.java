package com.todaysoft.lims.order.model.enums;

import java.util.Map;
import java.util.TreeMap;

public enum TranTypeEnum
{
    
    TRAN_TYPE_CONSUMER("01", "消费"), TRAN_TYPE_CANCEL("02", "撤销");
    
    public final String hint;
    
    public final String value;
    
    TranTypeEnum(String hint, String value)
    {
        this.hint = hint;
        this.value = value;
    }
    
    public static Map<String, String> list()
    {
        Map<String, String> map = new TreeMap<String, String>();
        for (TranTypeEnum i : TranTypeEnum.values())
        {
            map.put(i.value, i.hint);
        }
        return map;
    }
    
    public static TranTypeEnum objectOf(String value)
    {
        for (TranTypeEnum i : TranTypeEnum.values())
        {
            if (i.value.equals(value))
            {
                return i;
            }
        }
        return null;
    }
    
    public static String getHIntFromValue(String value)
    {
        for (TranTypeEnum i : TranTypeEnum.values())
        {
            if (i.value.equals(value))
            {
                return i.getHint();
            }
        }
        return null;
    }
    
    public static String getValueFromHInt(String hint)
    {
        for (TranTypeEnum i : TranTypeEnum.values())
        {
            if (i.hint.equals(hint))
            {
                return i.getValue();
            }
        }
        return null;
    }
    
    public String getValue()
    {
        return value;
    }
    
    public String getHint()
    {
        return hint;
    }
    
}
