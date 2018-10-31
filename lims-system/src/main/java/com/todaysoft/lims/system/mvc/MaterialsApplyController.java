package com.todaysoft.lims.system.mvc;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.searcher.MaterialsApplySearcher;
import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.DataArea;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.model.vo.enums.MaterialsStatus;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApply;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApplyDetail;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApplyTransport;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApplyTransportRelation;
import com.todaysoft.lims.system.model.vo.materialsApply.TemproaryData;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.model.Dict;
import com.todaysoft.lims.system.modules.smm.model.MaterialModel;
import com.todaysoft.lims.system.modules.smm.service.IAPPSalemanService;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.modules.smm.service.IMaterialService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.IAreaService;
import com.todaysoft.lims.system.service.IMaterialsApplyService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/materialsApply")
public class MaterialsApplyController extends BaseController
{
    @Autowired
    private IMaterialsApplyService service;
    
    @Autowired
    private IAPPSalemanService appsalemanservice;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private IDictService dictService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IMaterialService materialService; 
    
    @RequestMapping("/list.do")
    public String list(MaterialsApplySearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<MaterialsApply> pagination = service.paging(searcher, pageNo, DEFAULTPAGESIZE);
        for (MaterialsApply materialsApply : pagination.getRecords())
        {
            if (StringUtils.isNotEmpty(materialsApply.getCreatorId()))
            {
                BusinessInfo b = appsalemanservice.getBusinessInfo(materialsApply.getCreatorId());
                if (null != b)
                {
                    materialsApply.setTestingType(b.getTestingType());
                    materialsApply.setCounterman(b.getRealName());
                }
            }
            Set<String> set = new HashSet<String>();
            List<MaterialsApplyDetail> list = service.getEntityByMaterialsApplyId(materialsApply.getId());
            for (MaterialsApplyDetail materialsApplyDetail : list)
            {
                set.add(materialsApplyDetail.getMaterialsType());
            }
            List<String> typeList = Lists.newArrayList();
            typeList.addAll(set);
            List<String> values = Lists.newArrayList();
            for (String value : typeList)
            {
                //Dict dict = dictService.getDict("MATERIAL_CATEGORY", value);
                MaterialModel material = materialService.getById(value);
                values.add(material.getName());
            }
            materialsApply.setMaterialsType(values);
        }
        List<TestingType> testingTypes = testingTypeService.testingTypeList();
        model.addAttribute("testingTypes", testingTypes);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "materialsApply/materialsApply_list";
    }
    
    @RequestMapping("/view.do")
    public String view(TemproaryData data, ModelMap model)
    {
        MaterialsApply materialsApply = service.getEntityById(data.getId());
        BusinessInfo b = appsalemanservice.getBusinessInfo(materialsApply.getCreatorId());
        if (null != b)
        {
            materialsApply.setTestingType(b.getTestingType());
            materialsApply.setCounterman(b.getRealName());
        }
        if (StringUtils.isNotEmpty(materialsApply.getRecipientAddress()))
        {
            String result = exchangeArea(materialsApply.getRecipientAddress());
            DataArea target = areaService.get(Integer.parseInt(result));
            materialsApply.setFullName(target.getFullName());
        }
        List<MaterialsApplyDetail> list = service.getEntityByMaterialsApplyId(data.getId());
        List<MaterialsApplyDetail> details = Lists.newArrayList();
        List<MaterialsApplyDetail> madetails = Lists.newArrayList();
        if (Collections3.isNotEmpty(list))
        {
            for (MaterialsApplyDetail detail : list)
            {
                //Dict dName = dictService.getDict("MATERIAL_CATEGORY", detail.getMaterialsName());
                //Dict dType = dictService.getDict("MATERIAL_CATEGORY", detail.getMaterialsType());
                MaterialModel materialType = materialService.getById(detail.getMaterialsType());
                MaterialModel materialName = materialService.getById(detail.getMaterialsName());
                if (null != materialName)
                {
                    detail.setMaterialsName(materialName.getName());
                }
                if (null != materialType)
                {
                    detail.setMaterialsTypeName(materialType.getName());
                }
                details.add(detail);
                if (StringUtils.isNotEmpty(detail.getMaterialsCount()))
                {
                    detail.setCount(detail.getMaterialsCount() - detail.getMaterialsSendCount());
                    if (detail.getMaterialsCount() - detail.getMaterialsSendCount() <= 0)
                    {
                        continue;
                    }
                }
                madetails.add(detail);
                
            }
        }
        List<MaterialsApplyTransport> transports = service.getMATByMaterialsApplyId(data.getId());
        for (MaterialsApplyTransport mat : transports)
        {
            if (StringUtils.isNotEmpty(mat.getId()))
            {
                List<MaterialsApplyTransportRelation> relations = service.getMATRByTransportId(mat.getId());
                for (MaterialsApplyTransportRelation relation : relations)
                {
                    //Dict d = dictService.getDict("MATERIAL_CATEGORY", relation.getMaterialsName());
                    MaterialModel materialName = materialService.getById(relation.getMaterialsName());
                    relation.setMaterialsName(null == materialName ? null : materialName.getName());
                }
                mat.setRelations(relations);
            }
        }
        model.addAttribute("madetails", madetails);
        model.addAttribute("list", JSON.toJSONString(madetails));
        model.addAttribute("transports", transports);
        materialsApply.setMaterialsApplyDetail(details);
        model.addAttribute("materialsApply", materialsApply);
        model.addAttribute("flag", data.getFlag());
        return "materialsApply/materialsApply_view";
    }

    @RequestMapping("/deal.do")
    public String deal(TemproaryData data, ModelMap model)
    {
        MaterialsApply materialsApply = service.getEntityById(data.getId());
        BusinessInfo b = appsalemanservice.getBusinessInfo(materialsApply.getCreatorId());
        if (null != b)
        {
            materialsApply.setTestingType(b.getTestingType());
            materialsApply.setCounterman(b.getRealName());
        }
        if (StringUtils.isNotEmpty(materialsApply.getRecipientAddress()))
        {
            String result = exchangeArea(materialsApply.getRecipientAddress());
            DataArea target = areaService.get(Integer.parseInt(result));
            materialsApply.setFullName(target.getFullName());
        }
        List<MaterialsApplyDetail> list = service.getEntityByMaterialsApplyId(data.getId());
        List<MaterialsApplyDetail> details = Lists.newArrayList();
        List<MaterialsApplyDetail> madetails = Lists.newArrayList();
        if (Collections3.isNotEmpty(list))
        {
            for (MaterialsApplyDetail detail : list)
            {
                //Dict dName = dictService.getDict("MATERIAL_CATEGORY", detail.getMaterialsName());
                //Dict dType = dictService.getDict("MATERIAL_CATEGORY", detail.getMaterialsType());
                MaterialModel materialType = materialService.getById(detail.getMaterialsType());
                MaterialModel materialName = materialService.getById(detail.getMaterialsName());
                if (null != materialName)
                {
                    detail.setMaterialsName(materialName.getName());
                }
                if (null != materialType)
                {
                    detail.setMaterialsTypeName(materialType.getName());
                }
                details.add(detail);
                if (StringUtils.isNotEmpty(detail.getMaterialsCount()))
                {
                    detail.setCount(detail.getMaterialsCount() - detail.getMaterialsSendCount());
                    if (detail.getMaterialsCount() - detail.getMaterialsSendCount() <= 0)
                    {
                        continue;
                    }
                }
                madetails.add(detail);

            }
        }
        List<MaterialsApplyTransport> transports = service.getMATByMaterialsApplyId(data.getId());
        for (MaterialsApplyTransport mat : transports)
        {
            if (StringUtils.isNotEmpty(mat.getId()))
            {
                List<MaterialsApplyTransportRelation> relations = service.getMATRByTransportId(mat.getId());
                for (MaterialsApplyTransportRelation relation : relations)
                {
                    //Dict d = dictService.getDict("MATERIAL_CATEGORY", relation.getMaterialsName());
                    MaterialModel materialName = materialService.getById(relation.getMaterialsName());
                    relation.setMaterialsName(null == materialName ? null : materialName.getName());
                }
                mat.setRelations(relations);
            }
        }
        model.addAttribute("madetails", madetails);
        model.addAttribute("list", JSON.toJSONString(madetails));
        model.addAttribute("transports", transports);
        materialsApply.setMaterialsApplyDetail(details);
        model.addAttribute("materialsApply", materialsApply);
        model.addAttribute("flag", data.getFlag());
        return "materialsApply/materialsApply_view";
    }
    
    @RequestMapping("/handleMaterials.do")
    @ResponseBody
    @SystemServiceLog(description="业务配套物资管理-提交",type=5)
    public String handleMaterials(TemproaryData data)
    {
        MaterialsApply materialsApply = null;
        if (StringUtils.isNotEmpty(data.getId()))
        {
            materialsApply = service.getEntityById(data.getId());
            materialsApply.setStatus(MaterialsStatus.HANDLED);
            service.updateMaterialsApply(materialsApply);
        }
        List<MaterialsApplyTransport> transports = JSON.parseArray(data.getTransport() + "", MaterialsApplyTransport.class);
        for (MaterialsApplyTransport materialsApplyTransport : transports)
        {
            AuthorizedUser u = userService.getByToken();
            materialsApplyTransport.setCreatorId(u.getId());
            materialsApplyTransport.setCreatorName(u.getName());
            materialsApplyTransport.setApplyId(data.getId());
            materialsApplyTransport.setCreateTime(new Date());
            MaterialsApplyTransport mt = service.createTransport(materialsApplyTransport);
            if (Collections3.isNotEmpty(materialsApplyTransport.getRelations()))
            {
                List<MaterialsApplyTransportRelation> list = materialsApplyTransport.getRelations();
                
                for (MaterialsApplyTransportRelation relation : list)
                {
                    if (null != mt)
                    {
                        //Dict d = dictService.getDictByText("MATERIAL_CATEGORY", relation.getMaterialsName());
                        MaterialModel materialName = materialService.getByName(relation.getMaterialsName());
                        relation.setMaterialsName(materialName.getId());
                        relation.setTransportId(mt.getId());
                        service.createRelation(relation);
                    }
                    if (StringUtils.isNotEmpty(relation.getMaterialsApplyDetailId()))
                    {
                        MaterialsApplyDetail mad = service.getMaterialsApplyDetailById(relation.getMaterialsApplyDetailId());
                        if (null != mad)
                        {
                            if (relation.getMaterialSendCount() > 0)
                            {
                                mad.setMaterialsSendCount(mad.getMaterialsSendCount() + relation.getMaterialSendCount());
                                service.updateMaterialsApplyDetail(mad);
                            }
                        }
                    }
                }
                
            }
        }
        boolean flag = true;
        List<MaterialsApplyDetail> mads = service.getEntityByMaterialsApplyId(data.getId());
        for (MaterialsApplyDetail mad : mads)
        {
            if (mad.getMaterialsCount() - mad.getMaterialsSendCount() > 0)
            {
                flag = false;
                break;
            }
        }
        if (flag)
        {
            materialsApply.setStatus(MaterialsStatus.COMPLETED);
            materialsApply.setSendTime(new Date());
            service.updateMaterialsApply(materialsApply);
        }
        return "OK";
    }
    
    @RequestMapping("/getDetail.do")
    @ResponseBody
    public MaterialsApplyDetail getDetail(String id)
    {
        return service.getMaterialsApplyDetailById(id);
    }
    
    private static String exchangeArea(String i)
    {
        String result = "1";
        String[] change = i.split(",");
        if (StringUtils.isNotEmpty(change))
        {
            result = change[change.length - 1];
        }
        return result;
    }
}
