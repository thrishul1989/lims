package com.todaysoft.lims.testing.message.dao;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.MessageNotice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageNoticeSearcher  implements ISearcher<MessageNotice>{

    private String sampleCode; //样本编号

    private String handleStrategy ; //处理策略

    private String notify ; //通知用户

    @Override
    public NamedQueryer toQuery() {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM MessageNotice m where 1 = 1");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addSampleCodeFilter(hql, names, values);
        addHandleStrategyFilter(hql, names, values);
        addNotifyFilter(hql, names, values);
        hql.append(" ORDER BY m.noticeTime DESC");

        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }

    @Override
    public Class<MessageNotice> getEntityClass() {
        return MessageNotice.class;
    }
  //筛选样本编号
    private void addSampleCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != sampleCode && !"".equals(sampleCode))
        {
            hql.append(" AND m.sampleCode LIKE :sampleCode");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
    }
  //筛选处理策略
    private void addHandleStrategyFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != handleStrategy && !"".equals(handleStrategy))
        {
            hql.append(" AND m.handleStrategy LIKE :handleStrategy");
            paramNames.add("handleStrategy");
            parameters.add("%" + handleStrategy + "%");
        }
    }
  //（用户通知消息列表）筛选当前用户
    private void addNotifyFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != notify && !"".equals(notify))
        {
            hql.append(" AND m.notify = :notify");
            paramNames.add("notify");
            parameters.add(notify);
        }
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getHandleStrategy() {
        return handleStrategy;
    }

    public void setHandleStrategy(String handleStrategy) {
        this.handleStrategy = handleStrategy;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }
}
