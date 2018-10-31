package com.todaysoft.lims.testing.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderProduct;
import com.todaysoft.lims.testing.base.entity.OrderResearchProduct;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.service.IOrderStatusService;
import com.todaysoft.lims.testing.base.utils.OrderConstants;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class OrderStatusService implements IOrderStatusService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public String cancelOrderStatus(String orderId, String productId, String sampleId, String tag)
    {
        Order order = baseDaoSupport.get(Order.class, orderId);
        System.out.println(null == order.getType() ? "订单类型为空:" + order.getId() : order.getType().getId());
        //都被取消，设置产品状态
        if (!"4".equals(order.getType().getId()))
        {//非科研订单
            List<OrderProduct> orderProducts =
                baseDaoSupport.findByNamedParam(OrderProduct.class,
                    "from OrderProduct op where op.order.id=:orderId " + "and op.product.id=:productId",
                    new String[] {"orderId", "productId"},
                    new Object[] {orderId, productId});
            if (Collections3.isNotEmpty(orderProducts))
            {
                for (OrderProduct op : orderProducts)
                {
                    if (OrderConstants.SHEDULE_CANCEL.equals(tag))
                    {
                        op.setProductStatus(OrderConstants.ORDER_PRODUCT_CANCEL);
                    }
                    else if (OrderConstants.SHEDULE_FINISH.equals(tag))
                    {
                        op.setProductStatus(OrderConstants.ORDER_PRODUCT_FINISH);
                    }
                    baseDaoSupport.update(op);
                }
            }
        }
        else
        {//科研订单
        
            StringBuffer researchHql = new StringBuffer();
            researchHql.append("  select p FROM OrderResearchSample s join s.orderResearchProduct p where s.order.id=:orderId and  p.product.id =:productId ");
            List<String> param = new ArrayList<String>();
            List<Object> paramvalue = new ArrayList<Object>();
            NamedQueryer query = new NamedQueryer();
            param.add("orderId");
            param.add("productId");
            paramvalue.add(orderId);
            paramvalue.add(productId);
            if (StringUtils.isNotEmpty(sampleId))
            {
                TestingSample sample = baseDaoSupport.get(TestingSample.class, sampleId);
                if (StringUtils.isNotEmpty(sample))
                {
                    String originSampleId = sample.getReceivedSample().getSampleId();
                    researchHql.append(" and p.orderResearchSample.id=:sampleId  ");
                    param.add("sampleId");
                    paramvalue.add(originSampleId);
                    
                }
                
            }
            //当产品完成的时候 ,只完成 正在检测的样本
            if (OrderConstants.SHEDULE_FINISH.equals(tag))
            {
                researchHql.append(" and p.productStatus =:status ");
                param.add("status");
                paramvalue.add(0);
            }
            query.setHql(researchHql.toString());
            query.setNames(param);
            query.setValues(paramvalue);
            List<OrderResearchProduct> researchResult = baseDaoSupport.find(query, OrderResearchProduct.class);
            
            if (Collections3.isNotEmpty(researchResult))
            {
                for (OrderResearchProduct op : researchResult)
                {
                    if (OrderConstants.SHEDULE_CANCEL.equals(tag))
                    {
                        op.setProductStatus(OrderConstants.ORDER_RESEARCH_PRODUCT_CANCEL);
                    }
                    else if (OrderConstants.SHEDULE_FINISH.equals(tag))
                    {
                        op.setProductStatus(OrderConstants.ORDER_RESEARCH_PRODUCT_FINISH);
                    }
                    baseDaoSupport.update(op);
                }
            }
        }
        return order.getId();
    }
    
    @Override
    @Transactional
    public void pauseOrderStatus(String sheduleId, String type)
    {
        TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, sheduleId);
        if (StringUtils.isNotEmpty(schedule))
        {
            String orderId = schedule.getOrderId();
            String hql = " select s.id from TestingSchedule s where s.orderId =:orderId and s.proccessState !=:cancel ";
            List<String> result = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"orderId", "cancel"}, new Object[] {orderId, 1});
            if (Collections3.isEmpty(result))
            {
                if (OrderConstants.SHEDULE_PAUSE.equals(type))
                {
                    Order order = baseDaoSupport.get(Order.class, orderId);
                    order.setTestingStatus(OrderConstants.ORDER_TESTING_PAUSED);
                    order.setUpdateTime(new Date());
                    baseDaoSupport.update(order);
                }
                
            }
            else
            {
                if (OrderConstants.SHEDULE_RESTART.equals(type))
                {
                    Order order = baseDaoSupport.get(Order.class, orderId);
                    order.setTestingStatus(OrderConstants.ORDER_TESTING_PROCESSING);
                    order.setUpdateTime(new Date());
                    baseDaoSupport.update(order);
                }
            }
        }
        
    }
}
