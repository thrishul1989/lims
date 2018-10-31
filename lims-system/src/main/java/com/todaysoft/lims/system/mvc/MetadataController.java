package com.todaysoft.lims.system.mvc;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.vo.TreeNode;
import com.todaysoft.lims.system.service.IMetadataService;

@Controller
@RequestMapping(value = "/metadata")
public class MetadataController extends BaseController
{
	
	private Logger logger = Logger.getLogger(MetadataController.class);

    @Autowired
    private IMetadataService service;
    
    @RequestMapping("/list.do")
    public String list(String key, ModelMap model)
    {
        model.clear();
        
        if (IMetadataService.METADATA_DEVICE_EXPERIMENT.equals(key))
        {
            return "redirect:/equipment/experEquipment_list.do";
        }
        else if (IMetadataService.METADATA_DEVICE_STORAGE.equals(key))
        {
            return "redirect:/equipment/storeContainer_list.do";
        }
        else if (IMetadataService.METADATA_REAGENT_MATERIAL.equals(key)) //原料
        {
            return "redirect:/reagent/list.do";
        }
        else if (IMetadataService.METADATA_REAGENT_PROBE.equals(key)) //探針
        {
            return "redirect:/probe/list.do";
        }
        else if (IMetadataService.METADATA_REAGENT_ANALYZE.equals(key)) //探針
        {
            return "redirect:/analyzeMethod/list.do";
        }
        else if (IMetadataService.METADATA_REAGENT_CONNECT.equals(key)) //接头
        {
        	return "redirect:/connect/list.do";
        }
        else if (IMetadataService.METADATA_REAGENT_PRIMER.equals(key))
        { 
            return "redirect:/primer/primerList.do";
        }
        else if (IMetadataService.METADATA_REAGENT_PRIMERREFERENCELIBRARY.equals(key))
        { 
        	return "redirect:/primerReferenceLibrary/list.do";
        }
        else if (IMetadataService.METADATA_ESAMPLE_OSAMPLE.equals(key)) //樣本
        {
            return "redirect:/sample/list.do";
        }
        else if (IMetadataService.METADATA_ESAMPLE_ISAMPLE.equals(key)) //实验产物
        {
            return "redirect:/equipment/equipmentProduct_list.do";
        }
        else if (IMetadataService.METADATA_REAGENT_KIT.equals(key)) //试剂盒
        {
            return "redirect:/reagentKit/list.do";
        }
        else if (IMetadataService.METADATA_SUPPLIER.equals(key)) //供应商
        {
            return "redirect:/supplier/list.do";
        }
        else if (IMetadataService.METADATA_PRODUCTER_MANAGE.equals(key)) //生产商管理
        {
        	return "redirect:/producterManage/list.do";
        }
        else if (IMetadataService.METADATA_FIRM.equals(key)) //客户单位管理
        {
        	return "redirect:/firm/list.do";
        }
        else if (IMetadataService.METADATA_CUSTOMER.equals(key)) //客户管理
        {
        	return "redirect:/customer/list.do";
        }
        
        else
        {
            return "redirect:/sample/list.do";
        }
    }
    
    @RequestMapping("/types.do")
    @ResponseBody
    public List<TreeNode> types()
    {
        return service.getHierarchyMetadataTypes();
    }
}
