package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheetSearcher;
import com.todaysoft.lims.system.modules.bpm.service.TestingTaskSheetServiceFactory;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.security.AccountDetails;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/bpm/completeTasks")
public class CompleteTasksController extends BaseController {
	@Autowired
	private IUserService userService;

	@Autowired
	private TestingTaskSheetServiceFactory factory;

	// 实验列表
	@RequestMapping("/tasks.do")
	public String paging(TestingSheetSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session) {

		/** 数据权限开始 。。。。。。。。。。。。。。。。。 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		AccountDetails account = (AccountDetails) principal;
		Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
		if (dataAuthorityMap.containsKey("COMPLETE_TASK_LIST")) {
			searcher.setDataAuthoritySearcher(dataAuthorityMap.get("COMPLETE_TASK_LIST"));
		}

		/** 数据权限结束 。。。。。。。。。。。。。。。。。 */

		searcher.setPageNo(pageNo);
		searcher.setPageSize(pageSize);

		Pagination<TestingSheet> pagination = null;

		pagination = factory.getSerivceByTaskRefer(searcher.getTaskName()).completeSheetpaging(searcher);

		model.addAttribute("pagination", pagination).addAttribute("searcher", searcher);
		session.setAttribute("s-pageNo", pageNo);
		session.setAttribute("s-searcher", searcher);
		return "bpm/completeTasks/tasks_list";
	}
}
