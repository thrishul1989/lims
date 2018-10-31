package com.todaysoft.lims.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.InvoiceApplySearcher;
import com.todaysoft.lims.sample.entity.APPSaleman;
import com.todaysoft.lims.sample.entity.DataArea;
import com.todaysoft.lims.sample.entity.FinanceInvoiceTask;
import com.todaysoft.lims.sample.entity.InvoiceApply;
import com.todaysoft.lims.sample.entity.TestingType;
import com.todaysoft.lims.sample.entity.order.Customer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.order.OrderProduct;
import com.todaysoft.lims.sample.entity.sampleBack.Department;
import com.todaysoft.lims.sample.model.InvoiceApplyEvent;
import com.todaysoft.lims.sample.model.UserDetailsModel;
import com.todaysoft.lims.sample.service.IAreaService;
import com.todaysoft.lims.sample.service.IInvoiceApplyService;
import com.todaysoft.lims.sample.service.IOrderService;
import com.todaysoft.lims.sample.service.adapter.ProductAdapter;
import com.todaysoft.lims.sample.service.adapter.UserAdapter;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class InvoiceApplyService implements IInvoiceApplyService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private UserAdapter userService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ProductAdapter productService;
    
    @Autowired
    private IAreaService areaService;
    
    @Override
    public Pagination<InvoiceApply> paging(InvoiceApplySearcher searcher)
    {
        Pagination<InvoiceApply> paging = baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), InvoiceApply.class);
        List<InvoiceApply> list = Lists.newArrayList();
        List<TestingType> testingTypeList = productService.testingTypeList();
        
        if (Collections3.isNotEmpty(paging.getRecords()))
        {
            for (InvoiceApply ia : paging.getRecords())
            {
                getInvoiceApply(ia, testingTypeList);
                list.add(ia);
            }
        }
        List<InvoiceApply> first = Lists.newArrayList();
        List<InvoiceApply> second = Lists.newArrayList();
        List<InvoiceApply> third = Lists.newArrayList();
        List<InvoiceApply> fourth = Lists.newArrayList();
        for (InvoiceApply i : list)
        {
            if (1 == i.getStatus())
            {
                first.add(i);
            }
            if (3 == i.getStatus())
            {
                second.add(i);
            }
            if (2 == i.getStatus())
            {
                third.add(i);
            }
            if (4 == i.getStatus())
            {
                fourth.add(i);
            }
        }
        list.clear();
        list.addAll(first);
        list.addAll(second);
        list.addAll(third);
        list.addAll(fourth);
        paging.setRecords(list);
        return paging;
    }
    
    @Override
    public InvoiceApply get(String id)
    {
        InvoiceApply data = baseDaoSupport.get(InvoiceApply.class, id);
        List<TestingType> testingTypeList = productService.testingTypeList();
        
        //获得订单list，获取费用信息
        List<Order> orderList = Lists.newArrayList();
        String orderIds = data.getOrderIds();
        if (StringUtils.isNotEmpty(orderIds))
        {
            String[] orderIdArray = orderIds.split(",");
            for (String orderId : orderIdArray)
            {
                Order order = orderService.getOrderById(orderId);
                orderList.add(order);
            }
        }
        data.setOrderList(orderList);
        
        //省市区
        String companyAddressDetail = addressDetail(data.getCompanyAddress(), data.getCompanyAddressDetail());
        String recipientAddressDetail = addressDetail(data.getRecipientAddress(), data.getRecipientAddressDetail());
        data.setCompanyAddressDetail(companyAddressDetail);
        data.setRecipientAddressDetail(recipientAddressDetail);
        
        getInvoiceApply(data, testingTypeList);
        return data;
    }
    
    @Override
    @Transactional
    public void doApply(InvoiceApply data)
    {
        InvoiceApply entity = baseDaoSupport.get(InvoiceApply.class, data.getId());
        entity.setAuthIdea(data.getAuthIdea());
        entity.setDrawerId(data.getDrawerId());
        entity.setReceiverName(data.getReceiverName());
        entity.setInvoiceTime(data.getInvoiceTime());
        entity.setInvoicerNo(data.getInvoicerNo());
        if ("1".equals(data.getApplyResultValue()))
        {
            UserDetailsModel user = userService.getUser(data.getDrawerId());
            entity.setDrawerName(user.getArchive().getName());
            entity.setStatus(3);
        }
        if ("2".equals(data.getApplyResultValue()))
        {
            entity.setStatus(2);
        }
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void send(InvoiceApply data)
    {
        InvoiceApply entity = baseDaoSupport.get(InvoiceApply.class, data.getId());
        entity.setSendDate(data.getSendDate());
        entity.setSendContent(data.getSendContent());
        entity.setTransportType(data.getTransportType());
        entity.setTransportName(data.getTransportName());
        entity.setTransportPhone(data.getTransportPhone());
        entity.setCourierNumber(data.getCourierNumber());
        entity.setStatus(4);
        baseDaoSupport.update(entity);
    }
    
    private InvoiceApply getInvoiceApply(InvoiceApply ia, List<TestingType> testingTypeList)
    {
        APPSaleman saleman = baseDaoSupport.get(APPSaleman.class, ia.getCreatorId());
        String testingType = "";
        for (TestingType type : testingTypeList)
        {
            if (null != saleman && type.getId().equals(saleman.getTestingType()))
            {
                testingType = type.getName();
            }
        }
        ia.setTestingType(testingType);
        
        UserDetailsModel user = userService.getUser(ia.getCreatorId());
        if (StringUtils.isNotEmpty(user))
        {
            ia.setCreatorId(user.getArchive().getName());
            Department department = baseDaoSupport.get(Department.class, user.getArchive().getDeptId());
            if (null != department)
            {
                ia.setDeptName(department.getText());
            }
        }
        
        if (StringUtils.isNotEmpty(ia.getCustomerId()))
        {
            Customer customer = baseDaoSupport.get(Customer.class, ia.getCustomerId());
            if (StringUtils.isNotEmpty(customer))
            {
                ia.setCustomerId(customer.getRealName());
            }
            
        }
        
        String orderCode = "";
        List<String> orderCodeList = Lists.newArrayList();
        if (StringUtils.isNotEmpty(ia.getOrderIds()))
        {
            String[] ids = ia.getOrderIds().split(",");
            for (int i = 0; i < ids.length; i++)
            {
                Order order = orderService.getOrderById(ids[i]);
                if (null != order)
                {
                    orderCodeList.add(order.getCode());
                }
            }
        }
        for (int i = 0; i < orderCodeList.size(); i++)
        {
            if (i == 0)
            {
                orderCode = orderCodeList.get(0);
            }
            else
            {
                orderCode = orderCode + "," + orderCodeList.get(i);
            }
        }
        ia.setOrderIds(orderCode);
        return ia;
    }
    
    private String addressDetail(String address, String addressDetail)
    {
        if (StringUtils.isNotEmpty(address))
        {
            String[] arr = address.split(",");
            String areaId = arr[arr.length - 1];
            DataArea area = areaService.get(Integer.parseInt(areaId));
            address = area.getFullName();
            addressDetail = address + addressDetail;
        }
        return addressDetail;
    }
    
    @Override
    public List<String> institutionList(InvoiceApplyEvent event)
    {
        List<String> institutionList = Lists.newArrayList();
        if (InvoiceApplyEvent.APPLY_AUTO.equals(event.getApplyType()))
        {
            Order order = orderService.getOrderById(event.getApplyKey());
            if (null != order)
            {
                checkInstitution(order, institutionList);
            }
        }
        else if (InvoiceApplyEvent.APPLY_MANUAL.equals(event.getApplyType()))
        {
            InvoiceApply invoiceApply = get(event.getApplyKey());
            if (null != invoiceApply && Collections3.isNotEmpty(invoiceApply.getOrderList()))
            {
                for (Order order : invoiceApply.getOrderList())
                {
                    if (null != order)
                    {
                        checkInstitution(order, institutionList);
                    }
                }
            }
        }
        return institutionList;
    }
    
    private void checkInstitution(Order order, List<String> institutionList)
    {
        if (Collections3.isNotEmpty(order.getOrderProductList()))
        {
            for (OrderProduct op : order.getOrderProductList())
            {
                if (null != op.getProduct())
                {
                    String institution = op.getProduct().getTestInstitution();
                    if (StringUtils.isNotEmpty(institution) && institution.contains("1"))
                    {
                        if (!institutionList.contains(FinanceInvoiceTask.INSTITUTION_COMPANY))
                        {
                            institutionList.add(FinanceInvoiceTask.INSTITUTION_COMPANY);
                        }
                    }
                    else
                    {
                        if (!institutionList.contains(FinanceInvoiceTask.INSTITUTION_INSPECTION))
                        {
                            institutionList.add(FinanceInvoiceTask.INSTITUTION_INSPECTION);
                        }
                    }
                }
            }
        }
    }
}
