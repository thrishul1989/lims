package com.todaysoft.lims.system.modules.bpm.message.mvc;

import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.searcher.TaskSearcher;
import com.todaysoft.lims.system.model.vo.MessageNotice;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.TaskConfig;
import com.todaysoft.lims.system.modules.bcm.service.ITaskConfigService;
import com.todaysoft.lims.system.modules.bpm.message.model.MessageNoticeSearcher;
import com.todaysoft.lims.system.modules.bpm.message.service.IMessageNoticeService;
import com.todaysoft.lims.system.modules.bpm.message.service.request.MessageNoticeCreateRequest;
import com.todaysoft.lims.system.modules.bpm.message.service.request.MessageNoticeModifyRequest;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.UserSearcher;
import com.todaysoft.lims.system.modules.smm.model.UserSimpleModel;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/messageNotice")
public class MessageNoticeController extends BaseController {

    @Autowired
    private IMessageNoticeService messageNoticeService;

    @Autowired
    private ITaskConfigService taskConfigService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITaskService taskService;


    @RequestMapping(value = "/list.do")
    public String getMessageList(MessageNoticeSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<MessageNotice> pagination = messageNoticeService.paging(searcher, pageNo, 10);

        for (MessageNotice messageNotice: pagination.getRecords())
        {
            if (StringUtils.isNotEmpty(messageNotice.getHandleStrategy()))
            {
                //通过semantic获取处理策略名称
                TaskConfig taskConf = taskConfigService.getTaskBySemantic(messageNotice.getHandleStrategy());
                if (StringUtils.isNotEmpty(taskConf))
                {
                    messageNotice.setStrategyName(taskConf.getName());
                }
                else
                {
                    messageNotice.setStrategyName(messageNotice.getHandleStrategy());
                }
            }

            //获取用户名
            UserDetailsModel user = userService.getUserByID(messageNotice.getNotify());
            if(null!=user)
            {
                messageNotice.setUserName(user.getArchive().getName());
            }
        }

        List<Task> taskList = taskService.list(new TaskSearcher());
        model.addAttribute("taskList",taskList);

        //回显搜索用户
        if (!StringUtils.isEmpty(searcher.getNotify()))
        {
            UserDetailsModel userDModel = userService.getUserByID(searcher.getNotify());
            UserSimpleModel userModel = new UserSimpleModel();
            userModel.setName(userDModel.getArchive().getName());
            userModel.setId(userDModel.getId());
            model.addAttribute("userModel", JSONObject.toJSON(userModel).toString());
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "bpm/message/messageNotice_list";
    }

    @RequestMapping(value = "/toUser.do")
    public String getMessageToUserList(MessageNoticeSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        //获取当前用户
        AuthorizedUser user = userService.getByToken();
        searcher.setNotify(user.getId());
        Pagination<MessageNotice> pagination = messageNoticeService.paging(searcher, pageNo, 10);
        for (MessageNotice messageNotice: pagination.getRecords()) {
            if (StringUtils.isNotEmpty(messageNotice.getHandleStrategy()))
            {
                //通过semantic获取处理策略名称
                TaskConfig taskConf = taskConfigService.getTaskBySemantic(messageNotice.getHandleStrategy());
                if (StringUtils.isNotEmpty(taskConf))
                {
                    messageNotice.setStrategyName(taskConf.getName());
                }
                else
                {
                    messageNotice.setStrategyName(messageNotice.getHandleStrategy());
                }

            }
            //获取用户名
            messageNotice.setUserName(userService.getUserByID(messageNotice.getNotify()).getUsername());
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);

        List<Task> taskList = taskService.list(new TaskSearcher());
        model.addAttribute("taskList",taskList);
        return "bpm/message/messageNotice_User";
    }
    //新增
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        List<Task> taskList = taskService.list(new TaskSearcher());
        model.addAttribute("taskList",taskList);

        //获取用户列表
        UserSearcher searcher = new UserSearcher();
        List<UserSimpleModel>  userList = userService.list(searcher);
        model.addAttribute("userList",userList);

        return "bpm/message/messageNotice_form";

    }

    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(MessageNoticeCreateRequest request,BindingResult bindingResult)
    {

        messageNoticeService.create(request);
        return "redirect:/messageNotice/list.do";
    }

    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    @FormInputView
    public String modifyMessage(String id, ModelMap model)
    {
        List<Task> taskList = taskService.list(new TaskSearcher());
        model.addAttribute("taskList", taskList);

        //获取用户列表
        UserSearcher searcher = new UserSearcher();
        List<UserSimpleModel>  userList = userService.list(searcher);
        model.addAttribute("userList",userList);

        MessageNotice m = messageNoticeService.get(id);
        model.addAttribute("message", m);
        return "bpm/message/messageNotice_modify";
    }

    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(MessageNoticeModifyRequest request,BindingResult bindingResult)
    {
        messageNoticeService.modify(request);
        return "redirect:/messageNotice/list.do";
    }

    @RequestMapping(value = "/display.do")
    public String viewMaterial(String id, ModelMap model)
    {
        MessageNotice data = new MessageNotice();
        data.setId(id);
        MessageNotice m = messageNoticeService.get(id);
        m.setStrategyName(taskConfigService.getTaskBySemantic(m.getHandleStrategy()).getName());
        m.setUserName(userService.getUserByID(m.getNotify()).getUsername());
        model.addAttribute("message", m);
        return "bpm/message/messageNotice_view";
    }
    @RequestMapping(value = "/delete.do")
    public String delete(String id)
    {
        messageNoticeService.delete(id);
        return "redirect:/messageNotice/list.do";
    }


}
