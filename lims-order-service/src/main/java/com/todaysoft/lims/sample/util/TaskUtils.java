package com.todaysoft.lims.sample.util;

import java.util.List;

import org.activiti.engine.TaskService;

import com.todaysoft.lims.sample.config.RootContext;

/**
 * Created with idea
 * User: lvdong
 * Date: 2016-08-29
 * Time: 8:59
 */
public class TaskUtils
{
    private static TaskService taskService = RootContext.getBean(TaskService.class);
    
    /**
     * 得到String类型的变量
     * @param taskId
     * @param key
     * @return
     */
    public static String getVarString(String taskId, String key)
    {
        
        Object var = taskService.getVariable(taskId, key);
        if (null == var)
        {
            return null;
        }
        return (String)taskService.getVariable(taskId, key);
    }
    
    /**
     * 得到Integer类型的变量
     * @param taskId
     * @param key
     * @return
     */
    public static Integer getVarInteger(String taskId, String key)
    {
        Object var = taskService.getVariable(taskId, key);
        if (null == var)
        {
            return null;
        }
        return (Integer)taskService.getVariable(taskId, key);
    }
    
    /**
     * 得到Boolean类型的变量
     * @param taskId
     * @param key
     * @return
     */
    public static Boolean getVarBoolean(String taskId, String key)
    {
        Object var = taskService.getVariable(taskId, key);
        if (null == var)
        {
            return null;
        }
        return (Boolean)taskService.getVariable(taskId, key);
    }
    
    /**
     * 得到Double类型的变量
     * @param taskId
     * @param key
     * @return
     */
    public static Double getVarDouble(String taskId, String key)
    {
        Object var = taskService.getVariable(taskId, key);
        if (null == var)
        {
            return null;
        }
        return (Double)taskService.getVariable(taskId, key);
    }
    
    /**
     * 得到List<String>类型的变量
     * @param taskId
     * @param key
     * @return
     */
    public static List<String> getVarListString(String taskId, String key)
    {
        Object var = taskService.getVariable(taskId, key);
        if (null == var)
        {
            return null;
        }
        return (List<String>)taskService.getVariable(taskId, key);
    }
    
}
