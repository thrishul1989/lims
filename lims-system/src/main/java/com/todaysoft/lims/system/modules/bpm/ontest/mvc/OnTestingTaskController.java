package com.todaysoft.lims.system.modules.bpm.ontest.mvc;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.persist.PageInfo;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingMonitorRequest;
import com.todaysoft.lims.system.modules.bpm.ontest.model.NgsSequecingTask;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingTask;
import com.todaysoft.lims.system.modules.bpm.ontest.service.ISequencingService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/process")
public class OnTestingTaskController extends BaseController {
	@Autowired
	private ISequencingService service;

	@RequestMapping("/onTestingList.do")
	public String testTasks(SequencingAssignableTaskSearcher searcher, ModelMap model) {
		List<SequencingTask> tasks = service.getAssignableList(searcher);
		model.addAttribute("tasks", tasks);
		model.addAttribute("searcher", searcher);
		return "bpm/process/onTesting_list";
	}

	@RequestMapping("/ngsSequecingList.do")
	public String ngsSequecingList(NgsSequecingTask searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
			ModelMap model) {
		PageInfo<NgsSequecingTask> tasks = service.getNgsSequecingList(searcher, pageNo, 10);
		Collections.sort(tasks.getList());
		model.addAttribute("tasks", tasks.getList()).addAttribute("pagination", tasks);
		model.addAttribute("searcher", searcher);
		return "bpm/process/ngs_sequecing_list";
	}

	@RequestMapping("/ngsSequecingMonitorList.do")
	public String ngsSequecingMonitorList(NgsSequecingMonitorRequest searcher,
			@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model) {
		PageInfo<NgsSequecingTask> page = service.ngsSequecingMonitorList(searcher, pageNo, 10);
		Collections.sort(page.getList(), new Comparator<NgsSequecingTask>() {

			@Override
			public int compare(NgsSequecingTask o1, NgsSequecingTask o2) {
				o1.setIfUrgent(null == o1.getIfUrgent() ? false : o1.getIfUrgent());
				o2.setIfUrgent(null == o2.getIfUrgent() ? false : o2.getIfUrgent());
				if ((o1.getIfUrgent() && o2.getIfUrgent()) || (!o1.getIfUrgent() && !o2.getIfUrgent())) {
					if (o1.getEndTime().after(o2.getEndTime())) {

						return 1;
					}
					else{
						return -1;
					}
				} else {

					if (o1.getIfUrgent()) {
						return -1;
					} else {
						return 1;
					}
				}
				
			}

		});
		model.addAttribute("tasks", page.getList()).addAttribute("pagination", page);
		model.addAttribute("searcher", searcher);
		return "bpm/process/ngs_monitor_list";
	}

}
