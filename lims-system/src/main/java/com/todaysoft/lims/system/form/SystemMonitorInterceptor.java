package com.todaysoft.lims.system.form;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class SystemMonitorInterceptor extends HandlerInterceptorAdapter
{
    private static Logger log = LoggerFactory.getLogger("SystemMonitor");
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
        try
        {
            String url = request.getRequestURL().toString();
            MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
            
            if (log.isDebugEnabled())
            {
                log.debug("Access url {} before, heap memory init {}M max {}M used {}M committed {}M, nonheap memory init {}M max {}M used {}M committed {}M.",
                    url,
                    asMillion(heapMemoryUsage.getInit()),
                    asMillion(heapMemoryUsage.getMax()),
                    asMillion(heapMemoryUsage.getUsed()),
                    asMillion(heapMemoryUsage.getCommitted()),
                    asMillion(nonHeapMemoryUsage.getInit()),
                    asMillion(nonHeapMemoryUsage.getMax()),
                    asMillion(nonHeapMemoryUsage.getUsed()),
                    asMillion(nonHeapMemoryUsage.getCommitted()));
            }
        }
        catch (Exception e)
        {
            log.error("Monitor error.", e);
        }
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception
    {
        try
        {
            String url = request.getRequestURL().toString();
            MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
            
            if (log.isDebugEnabled())
            {
                log.debug("Access url {} after, heap memory init {}M max {}M used {}M committed {}M, nonheap memory init {}M max {}M used {}M committed {}M.",
                    url,
                    asMillion(heapMemoryUsage.getInit()),
                    asMillion(heapMemoryUsage.getMax()),
                    asMillion(heapMemoryUsage.getUsed()),
                    asMillion(heapMemoryUsage.getCommitted()),
                    asMillion(nonHeapMemoryUsage.getInit()),
                    asMillion(nonHeapMemoryUsage.getMax()),
                    asMillion(nonHeapMemoryUsage.getUsed()),
                    asMillion(nonHeapMemoryUsage.getCommitted()));
            }
        }
        catch (Exception e)
        {
            log.error("Monitor error.", e);
        }
    }
    
    private long asMillion(long bytes)
    {
        return bytes / 1024 / 1024;
    }
}
