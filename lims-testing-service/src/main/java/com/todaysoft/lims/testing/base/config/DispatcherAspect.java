package com.todaysoft.lims.testing.base.config;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

@Aspect
@Configuration
public class DispatcherAspect implements EnvironmentAware, ApplicationContextAware {

    private static ApplicationContext context;
    private Environment env;
    private static Logger log = LoggerFactory.getLogger("TestingMonitor");

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;

    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    private void anyMethod() {

    }

    @Before("anyMethod()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        System.out.println("Logging before "+request.getServletPath());
        try
        {
            String url = request.getServletPath().toString();
            MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();

//            if (log.isDebugEnabled())
//            {
//                log.debug("Access url {} before, heap memory init {}M max {}M used {}M committed {}M, nonheap memory init {}M max {}M used {}M committed {}M.",
//                        url,
//                        asMillion(heapMemoryUsage.getInit()),
//                        asMillion(heapMemoryUsage.getMax()),
//                        asMillion(heapMemoryUsage.getUsed()),
//                        asMillion(heapMemoryUsage.getCommitted()),
//                        asMillion(nonHeapMemoryUsage.getInit()),
//                        asMillion(nonHeapMemoryUsage.getMax()),
//                        asMillion(nonHeapMemoryUsage.getUsed()),
//                        asMillion(nonHeapMemoryUsage.getCommitted()));
//            }
        }
        catch (Exception e)
        {
//            log.error("Monitor error.", e);
        }
    }

    @After("anyMethod()")
    public void after(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try
        {
            String url = request.getServletPath().toString();
            MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();

//            if (log.isDebugEnabled())
//            {
//                log.debug("Access url {} after, heap memory init {}M max {}M used {}M committed {}M, nonheap memory init {}M max {}M used {}M committed {}M.",
//                        url,
//                        asMillion(heapMemoryUsage.getInit()),
//                        asMillion(heapMemoryUsage.getMax()),
//                        asMillion(heapMemoryUsage.getUsed()),
//                        asMillion(heapMemoryUsage.getCommitted()),
//                        asMillion(nonHeapMemoryUsage.getInit()),
//                        asMillion(nonHeapMemoryUsage.getMax()),
//                        asMillion(nonHeapMemoryUsage.getUsed()),
//                        asMillion(nonHeapMemoryUsage.getCommitted()));
//            }
        }
        catch (Exception e)
        {
//            log.error("Monitor error.", e);
        }
    }


    @Override
    public void setEnvironment(Environment environment) {

    }

    private long asMillion(long bytes)
    {
        return bytes / 1024 / 1024;
    }
}
