package com.todaysoft.lims.system.model.vo.enums;

public enum WangPayTypeEnum
{
    
    CARD("1", "刷卡"), WEIXIN("4", "微信"), ZHIFUBAO("5", "支付宝"), YINLIAN("6", "银联二维码"), FENQI("8", "分期消费"), JIFENKA("9", "积分刷卡"), WAIKA("A", "外卡");
    
    private String hint;
    
    private String desc;
    
    private WangPayTypeEnum(String hint, String desc)
    {
        this.hint = hint;
        this.desc = desc;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public static WangPayTypeEnum getEnum(String name)
    {
        WangPayTypeEnum[] arry = WangPayTypeEnum.values();
        for (int i = 0; i < arry.length; i++)
        {
            if (arry[i].name().equals(name))
            {
                return arry[i];
            }
        }
        return null;
    }
    
    public static String getValueFromHInt(String hint)
    {
        for (WangPayTypeEnum i : WangPayTypeEnum.values())
        {
            if (i.hint.equals(hint))
            {
                return i.getDesc();
            }
        }
        return "";
    }
    
    public String getHint()
    {
        return hint;
    }
    
    public void setHint(String hint)
    {
        this.hint = hint;
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
}
