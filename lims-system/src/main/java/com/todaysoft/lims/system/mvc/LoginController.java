package com.todaysoft.lims.system.mvc;

import com.todaysoft.lims.system.model.vo.Menu;
import com.todaysoft.lims.system.model.vo.MessageNotice;
import com.todaysoft.lims.system.modules.bpm.message.model.MessageNoticeSearcher;
import com.todaysoft.lims.system.modules.bpm.message.service.IMessageNoticeService;
import com.todaysoft.lims.system.modules.bpm.model.TestTask;
import com.todaysoft.lims.system.modules.bpm.model.TestTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.service.ITestingTestTasksService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.IResourceService;
import com.todaysoft.lims.system.service.security.AccountDetails;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class LoginController extends BaseController
{
    @Autowired
    private IResourceService menuService;
    
    @Autowired
    private ITestingTestTasksService testTasksService;
    
    @Autowired
    private IUserService userService;

    @Autowired

    private IMessageNoticeService messageNoticeService;
    
    @RequestMapping("/login")
    public String login(ModelMap model)
    {
        return "login";
    }
    
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model)
    {
        List<Menu> menus = menuService.getAuthorizedHierarchyMenus();
        AccountDetails account = getAccountDetails();
        model.addAttribute("menus", menus);
        if (Collections3.isNotEmpty(menus) && Collections3.isNotEmpty(menus.get(0).getSubmenus()))
        {
            model.addAttribute("innerUri", menus.get(0).getSubmenus().get(0).getUri());
        }
        else
        {
            model.addAttribute("innerUri", "/index.do");
        }
        TestTaskSearcher searcher = new TestTaskSearcher();
        AuthorizedUser user = userService.getByToken();
        searcher.setTesterId(user.getId());
        List<TestTask> tasks = testTasksService.testTasks(searcher);
        model.addAttribute("size", tasks.size());

        MessageNoticeSearcher messageNoticeSearcher = new MessageNoticeSearcher();
        messageNoticeSearcher.setNotify(user.getId());
        List<MessageNotice> notices = messageNoticeService.list(messageNoticeSearcher);
        if (notices.size() == 0)
        {
            model.addAttribute("total","");
        }
        else
        {
            model.addAttribute("total", notices.size());
        }
        model.addAttribute("account", account);
        return "layout";
    }
    
    @RequestMapping("/index.do")
    public String defaultPage(ModelMap model)
    {
        return "product/index";
    }
}
