package com.todaysoft.lims.system.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.todaysoft.lims.exception.ServiceException;

@Service
public class ExceptionResolver extends AbstractHandlerExceptionResolver
{
    private Logger logger = Logger.getLogger(ExceptionResolver.class);
    
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    {
        ModelAndView mv = new ModelAndView("/error");
        String base = request.getContextPath();
        mv.getModelMap().addAttribute("exception", ex);
        mv.getModelMap().addAttribute("base", base);
        mv.getModelMap().addAttribute("system_js", base + "/static/system/js");
        mv.getModelMap().addAttribute("system_css", base + "/static/system/css");
        mv.getModelMap().addAttribute("system_images", base + "/static/system/images");
        mv.getModelMap().addAttribute("plugins", base + "/static/plugins");
        
        if (ex instanceof ServiceException)
        {
            ServiceException exception = (ServiceException)ex;
            mv.getModelMap().addAttribute("error_code", exception.getErrorCode());
            mv.getModelMap().addAttribute("error_message", exception.getErrorMessage());
        }
        else
        {
            logger.error(ex.getMessage(), ex);
        }
        
        return mv;
    }
}
