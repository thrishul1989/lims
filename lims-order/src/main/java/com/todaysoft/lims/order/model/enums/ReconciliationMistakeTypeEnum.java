package com.todaysoft.lims.order.model.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账差错类型枚举 .

 */
public enum ReconciliationMistakeTypeEnum
{
    
    BANK_MISS("银行漏单"), // 银行不存在该订单
    PLATFORM_MISS("平台漏单"), // 平台不存在该订单
    PLATFORM_SHORT_CASH_MISMATCH("平台短款，金额不符"), // 平台需支付金额比银行实际支付金额少（基本不会出现）
    PLATFORM_OVER_CASH_MISMATCH("平台长款,金额不符"); // 银行实际支付金额比平台需支付金额少
    
    /** 描述 */
    private String desc;
    
    private ReconciliationMistakeTypeEnum(String desc)
    {
        this.desc = desc;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public static Map<String, Map<String, Object>> toMap()
    {
        ReconciliationMistakeTypeEnum[] ary = ReconciliationMistakeTypeEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < ary.length; num++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = ary[num].name();
            map.put("desc", ary[num].getDesc());
            map.put("name", ary[num].name());
            enumMap.put(key, map);
        }
        return enumMap;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List toList()
    {
        ReconciliationMistakeTypeEnum[] ary = ReconciliationMistakeTypeEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++)
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            map.put("name", ary[i].name());
            list.add(map);
        }
        return list;
    }
    
    public static ReconciliationMistakeTypeEnum getEnum(String name)
    {
        ReconciliationMistakeTypeEnum[] arry = ReconciliationMistakeTypeEnum.values();
        for (int i = 0; i < arry.length; i++)
        {
            if (arry[i].name().equalsIgnoreCase(name))
            {
                return arry[i];
            }
        }
        return null;
    }
    
    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr()
    {
        ReconciliationMistakeTypeEnum[] enums = ReconciliationMistakeTypeEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (ReconciliationMistakeTypeEnum senum : enums)
        {
            if (!"[".equals(jsonStr.toString()))
            {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }
}
