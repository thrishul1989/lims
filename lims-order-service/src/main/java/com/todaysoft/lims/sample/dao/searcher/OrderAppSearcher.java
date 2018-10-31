package com.todaysoft.lims.sample.dao.searcher;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderAppSearcher implements ISearcher<Order>
{
    private String bussinessId;//业务员
    private Integer testingStatus; //订单主状态---用来查询 1-待检测下放，2-检测中 ，3-已暂停 状态下的订单
    @Override
    public NamedQueryer toQuery() {
        StringBuffer hql = new StringBuffer(512);
        hql.append("SELECT o FROM Order o  WHERE o.deleted = false and o.testingStatus in(1,2,3) and o.projectManager != null "); /*状态变更 ：加上已取消*/
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addBusinessFilter(hql, paramNames, parameters);
        hql.append(" order by o.createTime desc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    private void addBusinessFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {

        if (StringUtils.isNotEmpty(bussinessId))
        {
            hql.append(" AND o.creatorId =:bussinessId");
            paramNames.add("bussinessId");
            parameters.add(bussinessId);
        }
    }
    @Override
    public Class<Order> getEntityClass()
    {
        return Order.class;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public Integer getTestingStatus() {
        return testingStatus;
    }

    public void setTestingStatus(Integer testingStatus) {
        this.testingStatus = testingStatus;
    }
}
