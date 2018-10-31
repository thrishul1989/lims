package com.todaysoft.lims.system.mvc;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.system.model.searcher.PhenotypeSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Phenotype;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.service.IPhenotypeService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/phenotype")
public class PhenotypeController extends BaseController
{
    
    @Autowired
    private IPhenotypeService service;
    
    @RequestMapping(value = "/list.do")
    public String paging(PhenotypeSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<Phenotype> pagination = service.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "phenotype/phenotype_list";
    }
    
    @RequestMapping(value = "/create.do")
    public String create(Phenotype request)
    {
        
        service.create(request);
        return "redirect:/phenotype/list.do";
    }
    
    @RequestMapping(value = "/modify.do")
    public String modify(Phenotype request)
    {
        
        service.modify(request);
        return "redirect:/phenotype/list.do";
    }
    
    @RequestMapping(value = "/delete.do")
    public String delete(String id)
    {
        PhenotypeSearcher searcher = new PhenotypeSearcher();
        searcher.setId(id);
        service.delete(searcher);
        return "redirect:/phenotype/list.do";
    }
    
    @RequestMapping(value = "/getPhenotype")
    @ResponseBody
    public Phenotype getPhenotypeById(String id)
    {
        
        return service.getPhenotypeById(id);
    }
    
    @RequestMapping(value = "/getPhenotypeByIds")
    @ResponseBody
    public List<Phenotype> getPhenotypeByIds(String ids)
    {
        
        List<Phenotype> list = new ArrayList<>();
        String[] idArray = ids.split("\\,");
        for (String id : idArray)
        {
            Phenotype g = service.getPhenotypeById(id);
            list.add(g);
        }
        
        return list;
        
    }
    
    @ResponseBody
    @RequestMapping(value = "/validate.do")
    public boolean checkedName(PhenotypeSearcher searcher)
    {
        return service.checkedName(searcher);
    }
    
    @RequestMapping(value = "/getPhenotypes.do")
    public List<Phenotype> getPhenotypes(PhenotypeSearcher searcher)
    {
        
        return service.getPhenotypeList(searcher);
    }
    
    @RequestMapping(value = "/getPhenotypeSelected.do")
    @ResponseBody
    public List<Phenotype> getPhenotypeSelected(PhenotypeSearcher searcher)
    {
        Pagination<Phenotype> pagination = service.paging(searcher, 1, 10);
        return pagination.getRecords();
        // return service.getPhenotypeList(searcher);
    }
    
    @RequestMapping("/upload.do")
    public String upload(@RequestParam MultipartFile uploadData, ModelMap model)
    {
        List<Phenotype> uploadList = (List<Phenotype>)removeNullValue(service.upload(uploadData));
        
        //查看是否有重复编号，标记不同颜色提示
        for (Phenotype p : uploadList)
        {
            Phenotype phenotype = service.getPhenotypeByCode("" == p.getCode() ? "0" : p.getCode());
            if (null == phenotype)
            {
                p.setIsOverwrite(0); //正确数据
            }
            else
            {
                p.setIsOverwrite(1); //重复数据
            }
            
        }
        
        model.addAttribute("list", uploadList);
        
        return "phenotype/phenotype_show";
        
    }
    
    /** 
     * 解决excel格式未删干净，留有空数据。【会耗时间】删除list中所有为空的元素 
     * 
     * @param list 
     * @return 
     */
    public static Collection<?> removeNullValue(Collection<?> collection)
    {
        if (collection == null)
        {
            return null;
        }
        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext())
        {
            Object o = iterator.next();
            Field[] field = o.getClass().getDeclaredFields();
            boolean result = true;
            for (int j = 0; j < field.length; j++) // 遍历所有属性
            {
                String name = field[j].getName(); // 获取属性的名字
                if (!StringUtils.isNotEmpty(getFieldValue(o, name)))
                {
                    continue;
                }
                else
                {
                    result = false;
                    break;
                }
                
            }
            if (result)
            {
                iterator.remove();
            }
            
        }
        return collection;
    }
    
    private static Object getFieldValue(Object thisClass, String fieldName)
    {
        Object value = new Object();
        Method method = null;
        try
        {
            String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            method = thisClass.getClass().getMethod("get" + methodName);
            value = method.invoke(thisClass);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }
    
    @RequestMapping("/download.do")
    public void dowloadData(HttpServletRequest request, HttpServletResponse response)
    {
        InputStream is = ConnectController.class.getResourceAsStream("/taskTemplate/phenotype.xlsx");
        service.downloadData(response, is);
    }
    
    @RequestMapping(value = "/view.do")
    public String view(String id, ModelMap model)
    {
        Phenotype ptenotype = service.getPhenotypeById(id);
        model.addAttribute("phenotype", ptenotype);
        
        return "phenotype/phenotype_view";
    }
    
    @RequestMapping("/insertData.do")
    @ResponseBody
    public boolean insertData(String value, HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        /*if (!StringUtils.isEmpty(value))
        {
            List<Phenotype> list = JSON.parseArray(value + "", Phenotype.class);
            List<Phenotype> localList = getPhenotypes(new PhenotypeSearcher());
            for (Phenotype phenotype : list)
            {
                if (localList.contains(phenotype))
                {
                    PhenotypeSearcher searcher = new PhenotypeSearcher();
                    searcher.setCode(phenotype.getCode());
                    
                    List<Phenotype> p = service.getPhenotypeList(searcher);
                    if (Collections3.isNotEmpty(p))
                    {
                        Phenotype source = p.get(0);
                        phenotype.setId(source.getId());
                        service.modify(phenotype);
                    }
                }
                service.create(phenotype);
            }
        }
        return true;*/
        
        List<Phenotype> list = JSON.parseArray(value + "", Phenotype.class);
        for (Phenotype gene : list)
        {
            if (StringUtils.isNotEmpty(gene.getCode()))
            {
                Phenotype g = service.getPhenotypeByCode(gene.getCode());
                if (null != g)
                {
                    //覆盖
                    gene.setId(g.getId());
                    service.modify(gene);
                }
                else
                {
                    service.create(gene);
                }
            }
            
        }
        
        return true;
        
    }
    
    @RequestMapping(value = "/getPhenotypeAll.do", method = RequestMethod.GET)
    @ResponseBody
    public Map getPhenotypeAll(PhenotypeSearcher searcher)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        Pagination<Phenotype> pagination = service.paging(searcher, 1, 10);
        List<Phenotype> list = pagination.getRecords();
        map.put("value", list);
        return map;
    }
}
