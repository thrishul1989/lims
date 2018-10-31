package com.todaysoft.lims.system.modules.bmm.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.Department;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.modules.bmm.model.MeetingApply;
import com.todaysoft.lims.system.modules.bmm.model.MeetingApplyJoin;
import com.todaysoft.lims.system.modules.bmm.model.request.MeetingApplyResponseRequest;
import com.todaysoft.lims.system.modules.bmm.model.request.MeetingApplySearcher;
import com.todaysoft.lims.system.modules.bmm.service.IMeetingApplyService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IDepartmentService;
import com.todaysoft.lims.system.service.ITestingTypeService;

@Controller
@RequestMapping(value = "/meetingApply")
public class MeetingApplyController extends BaseController
{
    @Autowired
    private IMeetingApplyService meetingApplyService;
    
    @Autowired
    private IDepartmentService departmentService;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @RequestMapping("/meetingList.do")
    public String paging(MeetingApplySearcher searcher,
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model,
        HttpSession session)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        //如果是初始化，给状态和会议时间赋值
        if (null == searcher.getStatus() && null == searcher.getApplyEndTime() && null == searcher.getApplyTime()
            && null == searcher.getMeetingTimeStart() && null == searcher.getMeetingTimeEnd()
            && null == searcher.getCreateName() && null == searcher.getSupportDept())
        {
            searcher.setStatus(1);
            searcher.setMeetingTimeStart(new Date());
        }
        Pagination<MeetingApply> pagination = meetingApplyService.paging(searcher);
        List<TestingType> testingTypes = testingTypeService.testingTypeList();
        model.addAttribute("searcher", searcher)//保留查询结果
            .addAttribute("pagination", pagination)
            .addAttribute("testingTypes", testingTypes);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "/bmm/meetingApply/meetingApply_list";
    }
    
    @RequestMapping("/view.do")
    public String view(String id, ModelMap model, HttpSession session, String status)
    {
        MeetingApply meetingApply = meetingApplyService.getMeetingApply(id);
        //获取参加总人数
        List<MeetingApplyJoin> meetingApplyJoin = meetingApply.getMeetingApplyJoin();
        int amount = 0;
        for (MeetingApplyJoin m : meetingApplyJoin)
        {
            amount += m.getPersonAmount();
        }
        //获取支持部门name集合
        String deptIds = meetingApply.getSupportDept();
        String[] strs = deptIds.split(",");
        List<String> str = new ArrayList<String>();
        for (int i = 0; i < strs.length; i++)
        {
            Department d = departmentService.get(strs[i]);
            if (i != str.size() - 1)
            {
                str.add(d.getText());
            }
            else
            {
                str.add(d.getText());
            }
        }
        String carryingMaterial = meetingApply.getCarryingMaterial();
        String[] carryingMaterials = carryingMaterial.split(",");
        List<String> str1 = new ArrayList<String>();
        for (int i = 0; i < carryingMaterials.length; i++)
        {
            str1.add(carryingMaterials[i]);
        }
        model.addAttribute("dept", str)
            .addAttribute("carryingMaterials", str1)
            .addAttribute("amount", amount)
            .addAttribute("meetingDispatchUsers", JSONObject.toJSON(meetingApply.getMeetingDispatchUsers()).toString());
        session.setAttribute("meetingApply", meetingApply);
        
        String html = "/bmm/meetingApply/meetingApply_show";
        if ("3".equals(status))
        {
            html = "/bmm/meetingApply/meetingApply_response";
        }
        if (3 == meetingApply.getStatus())
        {
            html = "/bmm/meetingApply/meetingApply_response_show";
        }
        if (4 == meetingApply.getStatus())
        {
            html = "/bmm/meetingApply/meetingApply_finish_show";
        }
        return html;
    }
    
    @RequestMapping("/responseUpdate.do")
    public String responseUpdate(MeetingApplyResponseRequest data)
    {
        meetingApplyService.updateResponse(data);
        return "redirect:/meetingApply/meetingList.do";
    }
}
