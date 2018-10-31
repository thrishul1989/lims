package com.todaysoft.lims.order.model.reconciliation;

import java.util.ArrayList;
import java.util.List;

/**
 * 对账接口实体
 *
 * 
 */
public class ReconciliationInterface
{
    
    /** 接口名称 */
    private String interfaceName;
    
    /** 接口代码 */
    private int interfaceCode;
    
    /** 接口描述 */
    private String interfaceDesc;
    
    /** 是否有效 PublicStatusEnum */
    private String status;
    
    /** 对账单周期 **/
    private int billDay;
    
    public String getInterfaceName()
    {
        return interfaceName;
    }
    
    public void setInterfaceName(String interfaceName)
    {
        this.interfaceName = interfaceName;
    }
    
    public int getInterfaceCode()
    {
        return interfaceCode;
    }
    
    public void setInterfaceCode(int interfaceCode)
    {
        this.interfaceCode = interfaceCode;
    }
    
    public String getInterfaceDesc()
    {
        return interfaceDesc;
    }
    
    public void setInterfaceDesc(String interfaceDesc)
    {
        this.interfaceDesc = interfaceDesc;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public int getBillDay()
    {
        return billDay;
    }
    
    public void setBillDay(int billDay)
    {
        this.billDay = billDay;
    }
    
    public static List<ReconciliationInterface> getInterface()
    {
        List<ReconciliationInterface> list = new ArrayList<ReconciliationInterface>();
        
        // 王府井支付
        ReconciliationInterface wangfujing = new ReconciliationInterface();
        wangfujing.setInterfaceCode(7);
        wangfujing.setBillDay(1);
        list.add(wangfujing);
        
        return list;
        
    }
}
