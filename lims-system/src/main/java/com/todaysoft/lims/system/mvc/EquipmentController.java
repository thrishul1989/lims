package com.todaysoft.lims.system.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.searcher.ExperEquipmentSearcher;
import com.todaysoft.lims.system.model.vo.ExperEquipment;
import com.todaysoft.lims.system.model.vo.ExperimentProduct;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.StorageLocation;
import com.todaysoft.lims.system.model.vo.StoreContainer;
import com.todaysoft.lims.system.model.vo.storecontainer.StoreContainerCreateRequest;
import com.todaysoft.lims.system.model.vo.storecontainer.StoreContainerModifyRequest;
import com.todaysoft.lims.system.model.vo.storecontainer.StoreContainerPagingRequest;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.smm.model.UserSearcher;
import com.todaysoft.lims.system.service.IExperEquipmentService;
import com.todaysoft.lims.system.service.IExperimentProductService;
import com.todaysoft.lims.system.service.IStoreContainerService;
import com.todaysoft.lims.system.service.adapter.request.ExperEquipmentRequest;
import com.todaysoft.lims.system.service.adapter.request.SamplePagingRequest;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/equipment")
public class EquipmentController extends BaseController
{
    
    @Autowired
    private IStoreContainerService storeService;
    
    @Autowired
    private IExperEquipmentService experService;
    
    @Autowired
    private IExperimentProductService experimentProductService;
    
    @Autowired
    private IStoreContainerService storeContainerService;
    
    //查看总数据 
    @RequestMapping(value = "/list.do")
    public String getDeviceList(UserSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        return "equipment/category_list";
        
    }
    
    /**
     * 匹配存储容器添加/修改form
     * @param model
     * @return
     */
    @Autowired
    private IMetadataSampleService sampleService;
    
    @RequestMapping("/storeContainerForm.do")
    public String addContainer(String id, ModelMap model)
    {
        
        /**
         * 返回数据字典等自带信息
         */
        StoreContainer data = new StoreContainer();
        Long usedCount = (long)0;
        if (id != null)
        {
            data = storeService.getStoreContainer(id);
            usedCount = storeService.countUserdLocation(id);
        }
        model.addAttribute("hasUsedLocation", usedCount > 0 ? true : false);
        model.addAttribute("container", data);
        
        List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
        model.addAttribute("sample", sampleList);
        
        return "equipment/storeContainer_form";
    }
    
    @RequestMapping("/form.do")
    /**
     * 匹配实验设备增加/修改form转向
     * @param id
     * @param model
     * @return
     */
    public String addform(String id, ModelMap model)
    {
        ExperEquipment data = new ExperEquipment();
        if (StringUtils.isNotEmpty(id))
        {
            data = experService.getExperEquipment(id);
        }
        
        model.addAttribute("experEquipment", data);
        
        return "equipment/equipment_update";
    }
    
    /**
     * 实验产物分页显示列表
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/equipmentProduct_list.do")
    public String list(ExperimentProduct searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<ExperimentProduct> pagination = experimentProductService.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "equipment/experiment_list";
    }
    
    @RequestMapping(value = "/createEquipmentProduct_list.do", method = RequestMethod.GET)
    public String createPage(ModelMap model)
    {
        ExperimentProduct data = new ExperimentProduct();
        model.addAttribute("experimentProduct", data);
        model.addAttribute("flag", "新增");
        return "equipment/experiment_form";
    }
    
    @ResponseBody
    @RequestMapping(value = "/validateName.do")
    public boolean checkedName(ExperimentProduct searcher)
    {
        
        return experimentProductService.checkedName(searcher);
    }
    
    /**************************************实验设备管理start*****************************************************/
    @RequestMapping("/experEquipment_list.do")
    public String experEquipmentList(ExperEquipmentSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<ExperEquipment> pagination = experService.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "equipment/experEquipment_paging";
    }
    
    @RequestMapping("/saveExperEquipment.do")
    public String saveExperEquipment(ExperEquipmentRequest request, ModelMap model, HttpSession session)
    {
        experService.createExperEquipment(request);
        return "redirect:/equipment/experEquipment_list.do";
        /* return redirectList(model, session, "redirect:/equipment/experEquipment_list.do");*/
    }
    
    /**
      * 查看实验设备详情
      * @param request
      * @param model
      * @param session
      * @return
      */
    @RequestMapping("/viewEquipment.do")
    public String viewEquipment(String id, ModelMap model)
    {
        ExperEquipment data = new ExperEquipment();
        if (StringUtils.isNotEmpty(id))
        {
            data = experService.getExperEquipment(id);
        }
        model.addAttribute("experEquipment", data);
        return "equipment/equipment_view";
    }
    
    @RequestMapping(value = "/createEquipmentProduct_list.do", method = RequestMethod.POST)
    public String createEquipmentProduct_list(ExperimentProduct experimentProduct, ModelMap model, HttpSession session)
    {
        if (experimentProduct.getId() == null)
        {
            experimentProductService.create(experimentProduct);
        }
        else
        {
            experimentProductService.modify(experimentProduct);
        }
        return redirectList(model, session);
    }
    
    /**
     * 修改实验设备
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    public String modify(ExperEquipmentRequest request, ModelMap model, HttpSession session)
    {
        experService.modify(request);
        return redirectList(model, session, "redirect:/equipment/experEquipment_list.do");
    }
    
    /**
    * 删除实验设备
    * @param id
    * @param model
    * @param session
    * @return
    */
    @RequestMapping("/delete.do")
    public String deleteExperEquipment(String id, ModelMap model, HttpSession session)
    {
        experService.deleteEquipment(id);
        return redirectList(model, session, "redirect:/equipment/experEquipment_list.do");
    }
    
    @RequestMapping("/checkEquipmentName.do")
    @ResponseBody
    public Boolean checkEquipmentName(ExperEquipmentRequest request)
    {
        return experService.checkEquipmentName(request);
    }
    
    @RequestMapping("/checkEquipmentNo.do")
    @ResponseBody
    public Boolean checkEquipmentNo(ExperEquipmentRequest request)
    {
        return experService.checkEquipmentNo(request);
    }
    
    /**************************************实验设备管理end*****************************************************/
    
    @RequestMapping(value = "/equipmentProductModify.do", method = RequestMethod.GET)
    public String modifyPage(Integer id, ModelMap model)
    {
        ExperimentProduct data = experimentProductService.getExperimentProduct(id);
        model.addAttribute("experimentProduct", data);
        model.addAttribute("flag", "编辑");
        return "equipment/experiment_form";
    }
    
    @RequestMapping("/equipmentProductDelete.do")
    public String delete(Integer id, ModelMap model, HttpSession session)
    {
        experimentProductService.delete(id);
        return redirectList(model, session);
    }
    
    /**************************************存储容器start*****************************************************/
    
    @RequestMapping(value = "/containerSelect.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> containerSelect(StoreContainerPagingRequest request)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "");
        
        Pagination<StoreContainer> containers = storeContainerService.paging(request, 1, 10);
        map.put("value", containers.getRecords());
        
        return map;
    }
    
    @RequestMapping("/storeContainer_list.do")
    public String containerList(StoreContainerPagingRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<StoreContainer> pagination = storeService.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
        model.addAttribute("sample", sampleList);
        
        return "equipment/storeContainer_list";
    }
    
    /**
     * 保存存储容器
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/saveStoreContainer.do")
    @ResponseBody
    public void saveStoreContainer(StoreContainerCreateRequest request, ModelMap model, HttpSession session)
    {
        storeService.create(request);
    }
    
    @RequestMapping(value = "/viewStoreContainer.do", method = RequestMethod.GET)
    public String viewStoreContainer(StoreContainerPagingRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        StoreContainer data = new StoreContainer();
        if (StringUtils.isNotEmpty(searcher.getId()))
        {
            data = storeService.getStoreContainer(searcher.getId());
        }
        
        model.addAttribute("container", data);
        
        Pagination<StorageLocation> pagination = storeService.getStorageLocationById(searcher, pageNo, 10);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("storeId", searcher.getId());
        return "equipment/storeContainer_view";
    }
    
    @RequestMapping(value = "/viewStoreContainer.do", method = RequestMethod.POST)
    public String view(StoreContainerPagingRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        StoreContainer data = new StoreContainer();
        if (StringUtils.isNotEmpty(searcher.getId()))
        {
            data = storeService.getStoreContainer(searcher.getId());
        }
        
        model.addAttribute("container", data);
        
        Pagination<StorageLocation> pagination = storeService.getStorageLocationById(searcher, pageNo, 10);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("storeId", searcher.getId());
        return "equipment/storeContainer_view";
    }
    
    /**
     * 修改存储容器
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifyStoreContainer.do", method = RequestMethod.POST)
    public String modifyStoreContainer(StoreContainerModifyRequest request, ModelMap model, HttpSession session)
    {
        storeService.modify(request);
        return redirectList(model, session, "redirect:/equipment/storeContainer_list.do");
    }
    
    @RequestMapping("/deleteStoreContainer.do")
    @ResponseBody
    public boolean deleteStoreContainer(String id, ModelMap model, HttpSession session)
    {
        
        boolean flag = false;
        if (storeService.countUserdLocation(id) == 0)
        {
            storeService.delete(id);
            flag = true;
        }
        return flag;
    }
    
    @RequestMapping("/cleanContainerLocation.do")
    @ResponseBody
    public Map<String, Object> cleanContainerLocation(String id, ModelMap model, HttpSession session)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int sum = storeService.cleanContainerLocation(id);
        result.put("result", "success");
        return result;
    }
    
    @RequestMapping("/synchronizeContainer.do")
    public String synchronizeContainer(String id, ModelMap model, HttpSession session)
    {
        storeService.synchronizeContainer(id);
        return redirectList(model, session, "redirect:/equipment/storeContainer_list.do");
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/equipment/equipmentProduct_list.do";
    }
    
    @RequestMapping("/cleanOneContainerLocation.do")
    @ResponseBody
    public Map<String, Object> cleanOneContainerLocation(String id, ModelMap model, HttpSession session)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int sum = storeService.cleanOneContainerLocation(id);
        result.put("result", "success");
        return result;
    }
    
    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping("/validate.do")
    public boolean validate(StoreContainerPagingRequest request)
    {
        return storeService.validate(request);
        
    }
    
    /**************************************存储容器end*****************************************************/
}
