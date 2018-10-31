package com.todaysoft.lims.smm.response;

public enum ResponseState
{
    
    SUCCESS("成功",1),FAIL("失败,自定义原因",3),ERRO("异常",0);
    private String name;
    private int index;
    
    
  
    
    
    
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    private ResponseState(String name, int index) {
        this.name = name;
        this.index = index;
      }
}
