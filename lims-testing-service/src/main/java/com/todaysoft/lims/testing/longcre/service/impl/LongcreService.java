package com.todaysoft.lims.testing.longcre.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ReagentKit;
import com.todaysoft.lims.testing.base.entity.Sequence;
import com.todaysoft.lims.testing.base.entity.TestingLongpcrTask;
import com.todaysoft.lims.testing.base.entity.TestingMultipcrTask;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSampleTemporary;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.SampleTypeSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.TestingCodeComparator;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.longcre.model.LongcreSubmitModel;
import com.todaysoft.lims.testing.longcre.model.LongcreSubmitSheet;
import com.todaysoft.lims.testing.longcre.service.ILongcreService;
import com.todaysoft.lims.testing.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.testing.longqc.model.LongQcAssignSheet;
import com.todaysoft.lims.testing.longqc.model.LongQcSheetVariables;
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrqcTestModel;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class LongcreService implements ILongcreService {

	@Autowired
	private BaseDaoSupport baseDaoSupport;

	@Autowired
	private ITestingScheduleService testingSheduleService;

	@Autowired
	private ICommonService commonService;

	@Autowired
	private SMMAdapter smmadapter;

	@Autowired
	private BCMAdapter bcmadapter;

	@Autowired
	private ITestingSheetService testingSheetService;

	@Autowired
	private ITestingTaskService testingTaskService;

	@Autowired
	private IActivitiService activitiService;

	@Autowired
	private BCMAdapter bcmAdapter;

	@Override
	@Transactional
	public LongcreSubmitSheet getTestingSheet(String id) {
		Map<String, Integer> codeContext = new HashMap<String, Integer>();
		LongcreSubmitSheet longcreSubmitSheet = new LongcreSubmitSheet();
		TestingSheet sheet = testingSheetService.getSheet(id);
		longcreSubmitSheet.setAssignerName(sheet.getAssignerName());
		longcreSubmitSheet.setAssignTime(sheet.getAssignTime());
		longcreSubmitSheet.setCode(sheet.getCode());
		longcreSubmitSheet.setDescription(sheet.getDescription());
		longcreSubmitSheet.setId(sheet.getId());
		longcreSubmitSheet.setCreTester(sheet.getTesterName());
		longcreSubmitSheet.setSubmitTime(sheet.getSubmitTime());
		String creReagentKitId = JSON.parseObject(sheet.getVariables()).getString("creReagentKitId").toString();
		ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class, creReagentKitId);
		longcreSubmitSheet.setCreReagentKitName(reagentKit.getName());

		List<TestingSheetTask> testingSheetTaskList = sheet.getTestingSheetTaskList();
		List<LongcreSubmitModel> tasks = new ArrayList<LongcreSubmitModel>();
		Product product = null;
		int i = 1;
		int testingCode = 1;

		for (TestingSheetTask sheetTask : testingSheetTaskList) {
			TestingTask testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
			TestingTaskRunVariable runvarible = baseDaoSupport.get(TestingTaskRunVariable.class, testingTask.getId());
			List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(testingTask.getId());
			if (Collections3.isNotEmpty(shedules)) {
				product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
			} else {
				shedules = testingSheduleService.getRelatedSchedulesByTestingTask(testingTask.getId());
				if (Collections3.isNotEmpty(shedules)) {
					product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
				}
			}
			/*------------------------------------------**/
			LongcreSubmitModel longcreSubmitModel = new LongcreSubmitModel();
			longcreSubmitModel.setId(testingTask.getId());
			longcreSubmitModel.setConnectNum(JSON.parseObject(runvarible.getText()).get("connectNum").toString());
			longcreSubmitModel.setInputVolumn(new BigDecimal(JSON.parseObject(runvarible.getText()).get("inputVolumn").toString()));
			if (new BigDecimal(JSON.parseObject(runvarible.getText()).get("waterVolumn").toString()).signum() == -1) {
				longcreSubmitModel.setWaterVolumn(new BigDecimal(0));
			} else {
				longcreSubmitModel.setWaterVolumn(new BigDecimal(JSON.parseObject(runvarible.getText()).get("waterVolumn").toString()));
			}

			longcreSubmitModel.setOutputSampleConcn(new BigDecimal(JSON.parseObject(runvarible.getText()).get("concn").toString()));
			longcreSubmitModel.setProduct(product);
			longcreSubmitModel.setTestingTask(testingTask);
			longcreSubmitModel.setQualityLevel(JSON.parseObject(testingTask.getInputSample().getAttributes()).get("qualityLevel").toString());
			if (null == codeContext.get(testingTask.getInputSample().getSampleCode())) {
				i = 1;
				codeContext.put(testingTask.getInputSample().getSampleCode(), 1);
			} else {
				Integer count = codeContext.get(testingTask.getInputSample().getSampleCode());
				i = 1 + count;
				codeContext.put(testingTask.getInputSample().getSampleCode(), i);
			}
			String outpuSampleCode = buildLongcreSampleCode(testingTask, i);
			longcreSubmitModel.setOutputSampleCode(outpuSampleCode);
			// ww location
			String scheduleId = getScheduleIdByTask(testingTask.getId());
			String upTaskId = getTaskIdBySemantic("Long-PCR", scheduleId);
			TestingLongpcrTask longpcrTask = getTestingLongpcrTask(upTaskId);
			// TestingTask upTestingTask = baseDaoSupport.get(TestingTask.class,
			// upTaskId);
			if (null != longpcrTask) {
				longcreSubmitModel.setTestingCode(longpcrTask.getTestingCode());
			}
			tasks.add(longcreSubmitModel);

		}

		Collections.sort(tasks, new Comparator<LongcreSubmitModel>() {
			@Override
			public int compare(LongcreSubmitModel o1, LongcreSubmitModel o2) {
				return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
			}
		});

		longcreSubmitSheet.setTasks(tasks);

		return longcreSubmitSheet;

	}

	private String getScheduleIdByTask(String taskId) {
		String hql = "FROM TestingScheduleHistory tsh WHERE tsh.taskId = :taskId";
		NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("taskId")).values(Lists.newArrayList(taskId)).build();
		List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
		if (Collections3.isNotEmpty(historys)) {
			return historys.get(0).getScheduleId();
		}
		return null;

	}

	private String getTaskIdBySemantic(String semantic, String scheduleId) {
		String hql = "FROM TestingScheduleHistory tsh WHERE EXISTS(SELECT tt.id FROM TestingTask tt WHERE tt.id = tsh.taskId AND tt.semantic = :semantic) AND tsh.scheduleId = :scheduleId ORDER BY tsh.timestamp DESC";
		NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("semantic", "scheduleId"))
				.values(Lists.newArrayList(semantic, scheduleId)).build();
		List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
		if (Collections3.isNotEmpty(historys)) {
			return historys.get(0).getTaskId();
		}
		return null;

	}

	private TestingLongpcrTask getTestingLongpcrTask(String taskId) {
		String hql = "FROM TestingLongpcrTask mt WHERE mt.testingTask.id = :taskId order by mt.testingCode ";
		NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("taskId")).values(Lists.newArrayList(taskId)).build();
		List<TestingLongpcrTask> mtask = baseDaoSupport.find(queryer, TestingLongpcrTask.class);
		if (Collections3.isNotEmpty(mtask)) {
			return mtask.get(0);
		}
		return null;

	}

	private void sortLocationMethod(List<LongcreSubmitModel> list) {
		Collections.sort(list, new Comparator<LongcreSubmitModel>() {

			@Override
			public int compare(LongcreSubmitModel o1, LongcreSubmitModel o2) {
				if (StringUtils.isNotEmpty(o1.getLocation()) && StringUtils.isNotEmpty(o2.getLocation())) {
					return o1.getLocation().compareTo(o2.getLocation());
				}
				return 0;
			}
		});
	}

	private String buildLongcreSampleCode(TestingTask task, int i) {
		boolean ifrepeat = false;

		while (!ifrepeat) {
			List<TestingSample> samples = baseDaoSupport.find(TestingSample.class, "from TestingSample t where t.sampleCode ='P"
					+ task.getInputSample().getSampleCode() + new DecimalFormat("00").format(i) + "'");
			if (Collections3.isNotEmpty(samples)) {
				i = i + 1;
			} else {
				List<TestingSampleTemporary> list = baseDaoSupport.find(
						TestingSampleTemporary.class,
						"from TestingSampleTemporary t where t.sampleCode='P" + task.getInputSample().getSampleCode()
								+ new DecimalFormat("00").format(i) + "'");
				if (Collections3.isEmpty(list)) {
					ifrepeat = true;
					// 保存产物编号到临时产物表
					TestingSampleTemporary temporatySample = new TestingSampleTemporary();
					temporatySample.setParentSample(task.getInputSample());
					temporatySample.setSampleCode("P" + task.getInputSample().getSampleCode() + new DecimalFormat("00").format(i));
					temporatySample.setTestingTask(task);
					baseDaoSupport.insert(temporatySample);
					return "P" + task.getInputSample().getSampleCode() + new DecimalFormat("00").format(i);
				} else {
					if (Collections3.getFirst(list).getTestingTask().getId().equals(task.getId()))// 已经有临时记录，用这个临时记录
					{
						return Collections3.getFirst(list).getSampleCode();
					} else {
						i = i + 1;
					}
				}
			}
		}
		return null;
	}

	@Override
	@Transactional
	public void submit(LongcreSubmitSheet sheet, String token) {

		TestingSheet submitSheet = new TestingSheet();
		List<TestingTask> nextTasks = new ArrayList<>();

		for (LongcreSubmitModel submitTask : sheet.getTasks()) {
			/********** 更新longcre任务结果 *****************************/

			TestingTask task = baseDaoSupport.get(TestingTask.class, submitTask.getId());
			task.setEndType(TestingTask.END_SUCCESS);
			task.setEndTime(new Date());
			task.setStatus(TestingTask.STATUS_END);
			baseDaoSupport.update(task);
			// 生成新的产物
			TestingSample testingSample = new TestingSample();
			testingSample.setSampleCode(submitTask.getOutputSampleCode());
			testingSample.setParentSample(task.getInputSample());
			testingSample.setReceivedSample(task.getInputSample().getReceivedSample());
			TaskConfig config = bcmAdapter.getTaskConfigBySemantic(task.getSemantic());
			testingSample.setSampleTypeId(config.getOutputSampleId());
			SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(config.getOutputSampleId());
			testingSample.setSampleTypeName(outputSampleType.getName());
			// 设置文库接头
			Map map = new HashMap<>();
			TestingTaskRunVariable run = baseDaoSupport.get(TestingTaskRunVariable.class, task.getId());
			map.put("index", JSON.parseObject(run.getText()).get("connectNum").toString());
			testingSample.setAttributes(JsonUtils.asJson(map));
			baseDaoSupport.insert(testingSample);
			// 删除临时产物变，保持编号的时效性
			baseDaoSupport.execute("delete from TestingSampleTemporary s where s.sampleCode='" + submitTask.getOutputSampleCode() + "'");

			/********* 创建新节点任务 *************************************/
			List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(task.getId());
			TaskConfig nextTask = testingSheduleService.getScheduleNextNodeConfig(shedules.get(0), task.getSemantic());
			TestingTask newTask = new TestingTask();
			newTask.setName(nextTask.getName());
			newTask.setSemantic(nextTask.getSemantic());
			newTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
			newTask.setResubmit(false);
			newTask.setResubmitCount(0);
			newTask.setStartTime(new Date());
			newTask.setInputSample(testingSample);
			baseDaoSupport.insert(newTask);
			nextTasks.add(newTask);

			/****** 设置检测流程激活节点状态 *************************************/
			testingSheduleService.gotoNextNode(shedules.get(0), TaskSemantic.LONG_CRE, newTask, new Date());

			/*************** 设置任务单提交结果 ***********************************/
			submitSheet = baseDaoSupport.get(TestingSheet.class, sheet.getId());
			UserMinimalModel submiter = smmadapter.getLoginUser(token);
			submitSheet.setSubmiterId(submiter.getId());
			submitSheet.setSubmiterName(submiter.getName());
			submitSheet.setSubmitTime(new Date());
			baseDaoSupport.update(submitSheet);

			/************************ 完成longcre任务单待办事项 ********************/

			activitiService.submitTestingSheet(sheet.getId());

		}

		/*********************** 生成longqc任务单 ***************************/
		// 创建任务单
		LongQcAssignSheet longqcSheet = new LongQcAssignSheet();
		longqcSheet.setTasks(nextTasks);
		// 分配实验人员,试剂盒()
		TestingSheet testingSheet = testingSheetService.getSheet(sheet.getId());
		longqcSheet.setQcReagentKit(JSON.parseObject(testingSheet.getVariables()).get("qcReagentKitId").toString());
		longqcSheet.setQcTesterId(JSON.parseObject(testingSheet.getVariables()).get("qcTestId").toString());
		longqcSheet.setCreReagentKit(JSON.parseObject(testingSheet.getVariables()).get("creReagentKitId").toString());
		longqcSheet.setCreTesterId(JSON.parseObject(testingSheet.getVariables()).get("creTestId").toString());
		longqcSheet.setPcrReagentKit(JSON.parseObject(testingSheet.getVariables()).get("pcrReagentKitId").toString());
		longqcSheet.setPcrTesterId(JSON.parseObject(testingSheet.getVariables()).get("pcrTestId").toString());
		TestingSheetCreateModel model = buildLongQcTestingSheetCreateModel(longqcSheet, token, submitSheet.getCode().substring(5, 11));
		TestingSheet startSheet = testingSheetService.create(model);
		for (TestingTask task : nextTasks) {
			// 更新task状态

			task.setStatus(TestingTask.STATUS_ASSIGNING);
			testingTaskService.modify(task);

		}

		// 存储冗余信息
		testingTaskService.updateTaskRedundantColumn(nextTasks, 0);

		// 启动流程
		activitiService.releaseTestingSheet(startSheet);

	}

	public TestingSheetCreateModel buildLongQcTestingSheetCreateModel(LongQcAssignSheet request, String token, String time) {
		UserMinimalModel loginer = smmadapter.getLoginUser(token);
		UserMinimalModel tester = smmadapter.getUserByID(request.getQcTesterId());
		TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.LONG_QC);

		TestingSheetCreateModel model = new TestingSheetCreateModel();

		String code = "";
		int index = 1;
		Sequence sequence = commonService.getSequenceListByNameAndDate(TaskSemantic.LONG_QC);
		if (null != sequence) {
			index = sequence.getCurrentValue().intValue();
		}
		code = "LBPCRQC" + time + String.format("%02d", index);

		model.setCode(code);

		model.setTaskSemantic(TaskSemantic.LONG_QC);
		model.setTaskName(taskConfig.getName());

		if (null != tester) {
			model.setTesterId(tester.getId());
			model.setTesterName(tester.getName());
		}

		if (null != loginer) {
			model.setAssignerId(loginer.getId());
			model.setAssignerName(loginer.getName());
		}

		Date timestamp = new Date();
		model.setAssignTime(timestamp);
		model.setCreateTime(timestamp);
		model.setDescription(request.getDescription());

		if (!CollectionUtils.isEmpty(request.getTasks())) {
			List<String> list1 = new ArrayList<String>();

			for (TestingTask task : request.getTasks()) {
				list1.add(task.getId());
			}

			model.setTasks(list1);
		}

		// 设置longcre任务单自定义参数对象
		LongQcSheetVariables variables = new LongQcSheetVariables();

		variables.setQcTestId(request.getQcTesterId());
		variables.setQcReagentKitId(request.getQcReagentKit());

		variables.setCreTestId(request.getCreTesterId());
		variables.setCreReagentKitId(request.getCreReagentKit());

		variables.setPcrTestId(request.getPcrTesterId());
		variables.setPcrReagentKitId(request.getPcrReagentKit());

		model.setVariables(variables);

		return model;
	}

}
