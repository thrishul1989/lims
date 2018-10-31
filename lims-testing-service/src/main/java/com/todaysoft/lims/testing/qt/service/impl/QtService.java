package com.todaysoft.lims.testing.qt.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Sets;
import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.biologyanaly.model.BiologyAnalySampleRecord;
import com.todaysoft.lims.testing.biologyanaly.service.IBiologyAnalyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.model.ReagentKitSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.pooling.model.PoolingTaskVariables;
import com.todaysoft.lims.testing.qt.context.QtSubmitContext;
import com.todaysoft.lims.testing.qt.context.QtSubmitScheduleContext;
import com.todaysoft.lims.testing.qt.context.QtSubmitTaskContext;
import com.todaysoft.lims.testing.qt.dao.QtAssignableTaskSearcher;
import com.todaysoft.lims.testing.qt.model.QtAssignArgs;
import com.todaysoft.lims.testing.qt.model.QtAssignModel;
import com.todaysoft.lims.testing.qt.model.QtAssignReference;
import com.todaysoft.lims.testing.qt.model.QtAssignReferenceArgs;
import com.todaysoft.lims.testing.qt.model.QtAssignRequest;
import com.todaysoft.lims.testing.qt.model.QtSampleAttributes;
import com.todaysoft.lims.testing.qt.model.QtSheetVariables;
import com.todaysoft.lims.testing.qt.model.QtSubmitModel;
import com.todaysoft.lims.testing.qt.model.QtSubmitRequest;
import com.todaysoft.lims.testing.qt.model.QtSubmitSampleArgs;
import com.todaysoft.lims.testing.qt.model.QtTask;
import com.todaysoft.lims.testing.qt.service.IQtService;
import com.todaysoft.lims.testing.rqt.model.NgsCreateEvent;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class QtService implements IQtService {
	@Autowired
	private SMMAdapter smmadapter;

	@Autowired
	private BCMAdapter bcmadapter;

	@Autowired
	private BSMAdapter bsmadapter;

	@Autowired
	private BaseDaoSupport baseDaoSupport;

	@Autowired
	private ITestingTaskService testingTaskService;

	@Autowired
	private ITestingSheetService testingSheetService;

	@Autowired
	private IActivitiService activitiService;

	@Autowired
	private ITestingScheduleService testingScheduleService;

	@Autowired
	private ICommonService commonService;

	@Autowired
	private IBiologyAnalyService biologyAnalyService;

	@Override
	public List<QtTask> getAssignableList(QtAssignableTaskSearcher searcher) {
		List<TestingTask> records = baseDaoSupport.find(searcher);

		if (CollectionUtils.isEmpty(records)) {
			return Collections.emptyList();
		}

		List<QtTask> tasks = new ArrayList<QtTask>();

		for (TestingTask record : records) {
			tasks.add(wrap(record));
		}
		/*
		 * tasks.sort(Comparator.comparing(QtTask::getIfUrgent).reversed()
		 * .thenComparing(QtTask::getPlannedFinishDate)
		 * .thenComparing(QtTask::getCreateTime));
		 */
		return tasks;
	}

	@Override
	public QtAssignModel getAssignModel(QtAssignArgs args) {
		QtAssignModel model = new QtAssignModel();

		if (null == args || StringUtils.isEmpty(args.getId())) {
			return model;
		}

		TestingTask task = baseDaoSupport.get(TestingTask.class, args.getId());

		if (null == task) {
			return model;
		}

		model.setId(task.getId());
		model.setSampleCode(task.getInputSample().getSampleCode());
		model.setCreateTime(task.getStartTime());
		model.setReferences(getAssignReferences());
		return model;
	}

	@Override
	@Transactional
	public void assign(QtAssignRequest request, String token) {
		TestingTask testingTask = testingTaskService.get(request.getId());
		testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
		testingTaskService.modify(testingTask);

		// 创建任务单
		TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
		TestingSheet sheet = testingSheetService.create(model);

		if (!CollectionUtils.isEmpty(request.getReferences())) {
			SequencingRecord record;
			SequencingRecord referenceRecord;

			for (QtAssignReferenceArgs reference : request.getReferences()) {
				record = baseDaoSupport.get(SequencingRecord.class, reference.getId());

				if (null == record) {
					throw new IllegalStateException();
				}

				referenceRecord = new SequencingRecord();
				referenceRecord.setSample(record.getSample());
				referenceRecord.setReference(true);
				referenceRecord.setQtSheetId(sheet.getId());
				referenceRecord.setQtFragmentLength(record.getQtFragmentLength());
				referenceRecord.setQtSequencingConcn(record.getQtSequencingConcn());
				referenceRecord.setSequencingCluster(record.getSequencingCluster());
				referenceRecord.setSequencingEffectiveRate(record.getSequencingEffectiveRate());
				referenceRecord.setSequencingEffectiveSize(record.getSequencingEffectiveSize());
				referenceRecord.setSequencingQ30(record.getSequencingQ30());
				referenceRecord.setSequencingTime(record.getSequencingTime());
				baseDaoSupport.insert(referenceRecord);
			}
		}

		// 启动流程
		activitiService.releaseTestingSheet(sheet);
	}

	private TestingSheetCreateModel buildTestingSheetCreateModel(QtAssignRequest request, String token) {
		UserMinimalModel loginer = smmadapter.getLoginUser(token);
		UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
		TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.QT);

		TestingSheetCreateModel model = new TestingSheetCreateModel();
		model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.QT));
		model.setTaskSemantic(TaskSemantic.QT);
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
		model.setTasks(Collections.singletonList(request.getId()));

		QtSheetVariables variables = new QtSheetVariables();
		variables.setReagentKitId(request.getReagentKitId());
		model.setVariables(variables);
		return model;
	}

	@Override
	public QtSubmitModel getSubmitModel(String id) {
		TestingSheet entity = testingSheetService.getSheet(id);

		if (null == entity) {
			return null;
		}

		QtSubmitModel sheet = new QtSubmitModel();
		sheet.setId(entity.getId());
		sheet.setCode(entity.getCode());
		sheet.setAssignerName(entity.getAssignerName());
		sheet.setAssignTime(entity.getAssignTime());
		sheet.setDescription(entity.getDescription());

		QtSheetVariables variables = JsonUtils.asObject(entity.getVariables(), QtSheetVariables.class);

		if (null != variables) {
			String reagentKitId = variables.getReagentKitId();
			ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);

			if (null != reagentKit) {
				sheet.setReagentKitName(reagentKit.getName());
			}
		}

		String taskId = entity.getTestingSheetTaskList().get(0).getTestingTaskId();
		TestingTask task = baseDaoSupport.get(TestingTask.class, taskId);
		sheet.setSampleCode(task.getInputSample().getSampleCode());
		sheet.setReferences(getSheetReferences(entity.getId()));
		return sheet;
	}

	@Override
	@Transactional
	public void submit(QtSubmitRequest request, String token) {

		// 异步保存拆分样本表
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

		// 1、设置提交上下文数据
		QtSubmitContext context = generateSubmitContext(request, token);

		// 2、更新实验任务状态
		doUpdateTestingTasks(context);

		// 3、发送创建下一节点任务消息
		doCreateNextNodeTasks(context);

		doCreateDivisionSample(context);

		// 5、更新任务单
		doUpdateSheet(context);

		// 6、更新定量结果
		doUpdateQTResult(context);

		// 7、完成工作流
		doCompleteProcess(context);
	}

	private void doUpdateTestingTasks(QtSubmitContext context) {
		QtSubmitTaskContext taskContext = context.getTask();

		TestingTask task = taskContext.getTestingTaskEntity();
		task.setEndTime(new Date());
		task.setStatus(TestingTask.STATUS_END);
		task.setEndType(TestingTask.END_SUCCESS);
		baseDaoSupport.update(task);

		TestingTaskResult result = new TestingTaskResult();
		result.setTaskId(task.getId());
		result.setResult(TestingTaskResult.RESULT_SUCCESS);
		baseDaoSupport.insert(result);

		TestingSample inputSample = task.getInputSample();
		QtSampleAttributes attributes = JsonUtils.asObject(inputSample.getAttributes(), QtSampleAttributes.class);

		if (null == attributes) {
			attributes = new QtSampleAttributes();
		}

		attributes.setConcn(taskContext.getConcn());
		attributes.setFragmentLength(taskContext.getFragmentLength());
		inputSample.setAttributes(JsonUtils.asJson(attributes));
		baseDaoSupport.update(inputSample);
	}

	private void doCreateNextNodeTasks(QtSubmitContext context) {
		NgsCreateEvent ngsCreateEvent = new NgsCreateEvent();
		BigDecimal testingDatasizes = new BigDecimal(0);

		Set<QtSubmitScheduleContext> schedules = context.getRelatedSchedules();

		List<String> noRepeatTask = new ArrayList<String>();
		for (QtSubmitScheduleContext schedule : schedules) {

			// 查找相对定量的数据量
			if (null != schedule) {
				List<TestingScheduleHistory> historys = testingScheduleService.getTestingScheduleHistoryByScheduleID(schedule
						.getTestingScheduleEntity().getId());
				if (Collections3.isNotEmpty(historys)) {
					for (TestingScheduleHistory history : historys) {
						TestingTask testingtask = testingTaskService.get(history.getTaskId());
						if(null==testingtask)
						{
							continue;
						}
						if (null != testingtask) {
							if ("POOLING".equals(testingtask.getSemantic()) && isRepeatTask(noRepeatTask, testingtask)) {
								PoolingTaskVariables variables = testingTaskService.obtainVariables(testingtask.getId(), PoolingTaskVariables.class);
								BigDecimal testingDatasize = variables == null ? null : variables.getTestingDatasize();
								testingDatasizes = testingDatasizes.add(testingDatasize);

							}
						}

					}
				}
			}

		}

		TestingSample testingSample = context.getNextTask().getInputSample();

		QtSampleAttributes attributes = JsonUtils.asObject(testingSample.getAttributes(), QtSampleAttributes.class);
		ngsCreateEvent.setSequecingCode(testingSample.getSampleCode());
		ngsCreateEvent.setSequecingCon(attributes.getConcn());

		ngsCreateEvent.setQtTaskId(context.getTask().getTestingTaskEntity().getId());
		ngsCreateEvent.setDataSize(testingDatasizes);

		testingScheduleService.sendNgsCreateMessage(ngsCreateEvent);

	}

	private void doCreateDivisionSample(QtSubmitContext context) {

		PoolingDivisionSample divisionSample;

		List<BiologyAnalySampleRecord> records = biologyAnalyService.getSampleRecords(context.getTask().getTestingTaskEntity().getId());
		List<String> dataCodeList = Lists.newArrayList();
		if (Collections3.isNotEmpty(records)) {
			for (BiologyAnalySampleRecord record : records) {
				if(dataCodeList.contains(record.getRecordCode()))
				{
					continue;
				}
				divisionSample = new PoolingDivisionSample();
				divisionSample.setSampleCode(record.getReceivedSampleCode());
				divisionSample.setDataCode(record.getRecordCode());
				divisionSample.setSequencingCode(record.getPoolingCode());
				divisionSample.setIndexCode(record.getLibraryIndex());
				TestingSample sample = getTestingSampleBySampleCode(record.getReceivedSampleCode());
				if (null != sample) {
					divisionSample.setSampleId(sample.getId());
				}
				baseDaoSupport.insert(divisionSample);
				dataCodeList.add(record.getRecordCode());
			}
		}
	}

	private void doUpdateSheet(QtSubmitContext context) {
		TestingSheet sheet = context.getSheetEntity();
		UserMinimalModel submiter = context.getSubmiter();
		sheet.setSubmiterId(submiter.getId());
		sheet.setSubmiterName(submiter.getName());
		sheet.setSubmitTime(new Date());
		baseDaoSupport.update(sheet);
	}

	private void doUpdateQTResult(QtSubmitContext context) {
		QtSubmitRequest result = context.getSubmitResult();
		QtSubmitSampleArgs primaryResult = result.getPrimarySample();

		// 更新主样本定量结果
		SequencingRecord primaryRecord = new SequencingRecord();
		primaryRecord.setSample(context.getTask().getTestingTaskEntity().getInputSample());
		primaryRecord.setReference(false);
		primaryRecord.setQtSheetId(context.getSheetEntity().getId());
		primaryRecord.setQtTime(context.getSheetEntity().getSubmitTime());
		primaryRecord.setQtQbitValue(primaryResult.getLibrarySizeOfQbit());
		primaryRecord.setQtQPCRValue(primaryResult.getLibrarySizeOfQPCR());
		primaryRecord.setQt2100Value(primaryResult.getLibrarySizeOf2100());
		primaryRecord.setQtFragmentLength(primaryResult.getFragmentLength());
		primaryRecord.setQtSequencingConcn(primaryResult.getConcn());
		baseDaoSupport.insert(primaryRecord);

		// 更新参考样本定量结果
		List<QtSubmitSampleArgs> referenceResults = result.getReferenceSamples();

		if (CollectionUtils.isEmpty(referenceResults)) {
			return;
		}

		SequencingRecord referenceRecord;

		for (QtSubmitSampleArgs referenceResult : referenceResults) {
			referenceRecord = baseDaoSupport.get(SequencingRecord.class, referenceResult.getId());

			if (null == referenceRecord) {
				throw new IllegalStateException();
			}

			referenceRecord.setQtQbitValue(referenceResult.getLibrarySizeOfQbit());
			referenceRecord.setQtQPCRValue(referenceResult.getLibrarySizeOfQPCR());
			referenceRecord.setQt2100Value(referenceResult.getLibrarySizeOf2100());
			baseDaoSupport.update(referenceRecord);
		}
	}

	private void doCompleteProcess(QtSubmitContext context) {
		TestingSheet sheet = context.getSheetEntity();
		activitiService.submitTestingSheet(sheet.getId());
	}

	private QtSubmitContext generateSubmitContext(QtSubmitRequest request, String token) {
		TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, request.getId());

		if (null == sheet) {
			throw new IllegalStateException();
		}

		QtSubmitContext context = new QtSubmitContext();
		context.setContextForSubmitResult(request);
		context.setContextForSubmiter(smmadapter.getLoginUser(token));
		context.setContextForTestingSheet(sheet);

		String testingTaskId = sheet.getTestingSheetTaskList().get(0).getTestingTaskId();
		TestingTask task = baseDaoSupport.get(TestingTask.class, testingTaskId);
		context.setContextForTestingTask(task);
		context.setContextForPrimarySampleQTResult(request.getPrimarySample());

		List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(testingTaskId);

		TaskConfig scheduleNextNodeConfig;

		for (TestingSchedule schedule : schedules) {
			scheduleNextNodeConfig = testingScheduleService.getScheduleNextNodeConfig(schedule, task.getSemantic());

			if (null == scheduleNextNodeConfig) {
				throw new IllegalStateException();
			}

			context.setContextForTestingSchedule(schedule, scheduleNextNodeConfig);
		}

		context.setContextForNextNodeTaskConfig();

		return context;
	}

	private List<QtAssignReference> getAssignReferences() {
		String hql = "FROM SequencingRecord r WHERE r.reference = false AND r.sequencingSheetId IS NOT NULL ORDER BY r.sequencingTime DESC";
		NamedQueryer queryer = new NamedQueryer();
		queryer.setHql(hql);
		queryer.setNames(new ArrayList<String>());
		queryer.setValues(new ArrayList<Object>());
		Pagination<SequencingRecord> pagination = baseDaoSupport.find(queryer, 1, 4, SequencingRecord.class);
		List<SequencingRecord> records = pagination.getRecords();
		return wrap(records);
	}

	private List<QtAssignReference> getSheetReferences(String sheetId) {
		String hql = "FROM SequencingRecord r WHERE r.qtSheetId = :sheetId ORDER BY r.sequencingTime DESC";
		List<SequencingRecord> records = baseDaoSupport.findByNamedParam(SequencingRecord.class, hql, new String[] { "sheetId" },
				new Object[] { sheetId });
		return wrap(records);
	}

	private List<QtAssignReference> wrap(List<SequencingRecord> records) {
		if (CollectionUtils.isEmpty(records)) {
			return Collections.emptyList();
		}

		QtAssignReference reference;
		List<QtAssignReference> references = new ArrayList<QtAssignReference>();

		for (SequencingRecord record : records) {
			reference = new QtAssignReference();
			reference.setId(record.getId());
			reference.setSampleCode(record.getSample().getSampleCode());
			reference.setFragmentLength(record.getQtFragmentLength());
			reference.setConcn(record.getQtSequencingConcn());
			reference.setCluster(record.getSequencingCluster());
			reference.setTestingTime(record.getSequencingTime());
			reference.setLibrarySizeOf2100(record.getQt2100Value());
			reference.setLibrarySizeOfQbit(record.getQtQbitValue());
			reference.setLibrarySizeOfQPCR(record.getQtQPCRValue());
			if (null != record.getQtQPCRValue()) {
				reference.setLibraryValueOfQPCR(record.getQtQPCRValue().multiply(new BigDecimal(452))
						.divide(new BigDecimal(record.getQtFragmentLength()), 2, RoundingMode.CEILING));
			}

			references.add(reference);
		}

		return references;
	}

	private QtTask wrap(TestingTask entity) {
		QtTask task = new QtTask();
		task.setId(entity.getId());
		task.setSampleCode(entity.getInputSample().getSampleCode());
		task.setCreateTime(entity.getStartTime());
		// 设置加急
		if (null == entity.getIfUrgent()) {
			task.setIfUrgent(0);
		} else {
			task.setIfUrgent(entity.getIfUrgent());
		}

		task.setUrgentName(entity.getUrgentName());
		task.setUrgentUpdateTime(entity.getUrgentUpdateTime());
		// setPlannedFinishDate(entity, task);
		return task;
	}

	private void setPlannedFinishDate(TestingTask entity, QtTask task) {
		NamedQueryer queryer = NamedQueryer.builder().hql("FROM TestingScheduleHistory tsh WHERE tsh.taskId = :taskId")
				.names(Lists.newArrayList("taskId")).values(Lists.newArrayList(entity.getId())).build();
		List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
		Date date = null;
		if (Collections3.isNotEmpty(historys)) {
			TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, historys.get(0).getScheduleId());
			List<OrderPlanTask> plans = Lists.newArrayList();
			String hql = "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId "
					+ " AND opt.sampleId = :sampleId AND opt.testingMethodId = :testingMethodId AND opt.taskSemantic = :taskSemantic";
			if (StringUtils.isNotEmpty(schedule.getVerifyKey())) {
				hql += " AND opt.verifyId = :verifyId";
				plans = baseDaoSupport.findByNamedParam(
						OrderPlanTask.class,
						hql,
						new String[] { "orderId", "productId", "sampleId", "testingMethodId", "taskSemantic", "verifyId" },
						new Object[] { schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(), schedule.getMethodId(),
								entity.getSemantic(), schedule.getVerifyKey() });
			} else {
				plans = baseDaoSupport.findByNamedParam(OrderPlanTask.class, hql, new String[] { "orderId", "productId", "sampleId",
						"testingMethodId", "taskSemantic" }, new Object[] { schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(),
						schedule.getMethodId(), entity.getSemantic() });
			}
			if (Collections3.isNotEmpty(plans)) {
				date = plans.get(0).getPlannedFinishDate();
			}
		}
		if (null != date) {
			task.setPlannedFinishDate(date);
		} else {
			try {
				task.setPlannedFinishDate(new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	// 多重PCR和longpcr和PCR-NGS到相对定量合并任务，所以数据量只计算一次
	private boolean isRepeatTask(List<String> noRepeatTask, TestingTask task) {
		for (String taskId : noRepeatTask) {
			if (taskId.equals(task.getId())) {
				return false;
			}

		}
		noRepeatTask.add(task.getId());

		return true;
	}

	private TestingSample getTestingSampleBySampleCode(String sampleCode) {
		String hql = " FROM TestingSample t WHERE t.sampleCode = '" + sampleCode + "'";
		List<TestingSample> records = baseDaoSupport.find(TestingSample.class, hql);
		return Collections3.isEmpty(records) ? null : Collections3.getFirst(records);
	}

}
