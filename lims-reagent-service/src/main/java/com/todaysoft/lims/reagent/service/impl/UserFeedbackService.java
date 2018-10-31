package com.todaysoft.lims.reagent.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.UserFeedbackSearcher;
import com.todaysoft.lims.reagent.entity.Area;
import com.todaysoft.lims.reagent.entity.BusinessInfo;
import com.todaysoft.lims.reagent.entity.Company;
import com.todaysoft.lims.reagent.entity.Customer;
import com.todaysoft.lims.reagent.entity.UserArchive;
import com.todaysoft.lims.reagent.entity.UserDetailsModel;
import com.todaysoft.lims.reagent.entity.UserFeedback;
import com.todaysoft.lims.reagent.service.IUserFeedbackService;
import com.todaysoft.lims.reagent.service.adapter.UserAdapter;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class UserFeedbackService implements IUserFeedbackService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private UserAdapter userAdapter;
    
    private static final String SERVICE_NAME = "lims-user-service";
    
    @Override
    public Pagination<UserFeedback> paging(UserFeedbackSearcher request)
    {
        //1、根据name判定是客户还是业务员。2、获取userId。3、在UserFeedbackSearcher拼接userId查询条件
        List<String> userId = new ArrayList<String>();
        String Hql = "FROM BusinessInfo b WHERE b.realName like :name";
        List<BusinessInfo> businessInfo =
            baseDaoSupport.findByNamedParam(BusinessInfo.class,
                Hql,
                new String[] {"name"},
                new Object[] {"%" + request.getName() + "%"});
        if (businessInfo.size() != 0)
        {
            for (BusinessInfo b : businessInfo)
            {
                userId.add(b.getId().trim());
            }
        }
        String Hql2 = "FROM  Customer as  c WHERE c.realName  like :name";
        List<Customer> customer =
            baseDaoSupport.findByNamedParam(Customer.class,
                Hql2,
                new String[] {"name"},
                new Object[] {"%" + request.getName() + "%"});//必须加单引号
        if (customer.size() != 0)
        {
            for (Customer c : customer)
            {
                userId.add(c.getId().trim());
            }
        }
        request.setUserIds(userId);
        Pagination<UserFeedback> paging =
            baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), UserFeedback.class);
        List<UserFeedback> userFeedback = paging.getRecords();
        //1、判断用户是客户还是业务员。2、如果是用户根据用户所在单位获取省市信息。3、如果是业务员获取业务员职称和所在省市信息。
        for (UserFeedback u : userFeedback)
        {
            if (StringUtils.isNotEmpty(u.getUserId()))
            {
                Customer c = baseDaoSupport.get(Customer.class, u.getUserId());
                if (c != null)
                {
                    u.setName(c.getRealName());
                    u.setPosition("客户");
                    Company company = c.getCompany();
                    u.setProvince(company.getProvince().getName());
                    u.setCity(company.getCity().getName());
                    Area province = baseDaoSupport.get(Area.class, company.getProvince().getId());
                    u.setProvince(province.getName());
                    Area city = baseDaoSupport.get(Area.class, company.getCity().getId());
                    u.setCity(city.getName());
                    
                }
                else
                {
                    BusinessInfo b = baseDaoSupport.get(BusinessInfo.class, u.getUserId());
                    u.setPosition(b.getRoleType());
                    u.setName(b.getRealName());
                    UserDetailsModel userDetailModel = userAdapter.getUserDetailsModel(u.getUserId());
                    UserArchive userArchive = userDetailModel.getArchive();
                    if (userArchive != null)
                    {
                        u.setProvince(userArchive.getProvince());
                        u.setCity(userArchive.getCity());
                    }
                }
            }
        }
        //删除反馈人模糊查询，没查找到的记录。
        if (userId.size() > 0)
        {
            Iterator<UserFeedback> u = userFeedback.iterator();
            while (u.hasNext())
            {
                UserFeedback u1 = u.next();
                if (!userId.contains(u1.getUserId()))
                {
                    u.remove();
                }
            }
        }
        return paging;
    }
}
