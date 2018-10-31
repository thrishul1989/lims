package com.todaysoft.lims.schedule.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.schedule.entity.AbnormalSolveRecord;
import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.entity.OrderPlanTaskDependency;
import com.todaysoft.lims.schedule.entity.TestingSchedule;
import com.todaysoft.lims.schedule.entity.TestingScheduleHistory;
import com.todaysoft.lims.schedule.entity.TestingSheet;
import com.todaysoft.lims.schedule.entity.TestingSheetTask;
import com.todaysoft.lims.schedule.entity.TestingTask;
import com.todaysoft.lims.schedule.entity.TestingTaskResult;
import com.todaysoft.lims.schedule.model.PlanSearchModel;
import com.todaysoft.lims.schedule.model.TaskSemantic;
import com.todaysoft.lims.schedule.service.IScheduleConfigService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.ITestingScheduleService;
import com.todaysoft.lims.schedule.service.core.event.ScheduleTaskActiveEvent;
import com.todaysoft.lims.schedule.util.JsonUtils;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingScheduleService implements ITestingScheduleService {
	private static Logger log = LoggerFactory.getLogger(SchedulePlanService.class);

	@Autowired
	private BaseDaoSupport baseDaoSupport;

	@Autowired
	private IScheduleConfigService configService;

	@Autowired
	private ISchedulePlanService schedulePlanService;

	@Override
	@Transactional
	public void updatePlanWithSheetSubmit(String sheetId) {
		// 1.传过来任务单id 查询任务单相关的所有任务 在查出来任务相关的所有流程 取到
		// 每个orderid,productid,sampleid,methodid,tasksemantic
		/*
		 * List<OrderPlanTask> records = getBySheetId(sheetId);
		 * log.info("orderPlan length is:"+records.size()); for(OrderPlanTask
		 * orderPlanTask:records) {
		 * schedulePlanService.updatePlanForTaskFinished(orderPlanTask.getId());
		 * }
		 */
		// 2017.10.25 根据策略
		TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, sheetId);
		PlanSearchModel model;
		if (null == sheet) {
			// 查不到应该是生信分析 生信分析没有任务单 传的是任务id
			TestingTask bioTask = baseDaoSupport.get(TestingTask.class, sheetId);
			if (null == bioTask) {
				log.error("bioTask? task si null,taskId is:" + sheetId);
				throw new IllegalStateException();
			}
			// TestingTaskResult taskResult =
			// baseDaoSupport.get(TestingTaskResult.class, bioTask.getId());
			List<TestingSchedule> schedules = getSchedulesByTaskId(bioTask.getId());
			for (TestingSchedule schedule : schedules) {
				model = new PlanSearchModel();
				model.setSemantic(bioTask.getSemantic());
				model.setOrderId(schedule.getOrderId());
				model.setProductId(schedule.getProductId());
				model.setMethodId(schedule.getMethodId());
				model.setSampleId(schedule.getSampleId());
				model.setVerifyKey(schedule.getVerifyKey());
				OrderPlanTask planTask = getOrderPlanTaskByTaskId(model);
				schedulePlanService.updatePlanForTaskFinished(planTask.getId());
			}
		} else {
			List<TestingSheetTask> testingSheetTaskList = sheet.getTestingSheetTaskList();
			if (Collections3.isNotEmpty(testingSheetTaskList)) {
				for (TestingSheetTask sheetTask : testingSheetTaskList) {
					TestingTask testingTask = baseDaoSupport.get(TestingTask.class, sheetTask.getTestingTaskId());
					TestingTaskResult taskResult = baseDaoSupport.get(TestingTaskResult.class, sheetTask.getTestingTaskId());
					List<TestingSchedule> schedules = getSchedulesByTaskId(sheetTask.getTestingTaskId());
					for (TestingSchedule schedule : schedules) {
						model = new PlanSearchModel();
						model.setSemantic(testingTask.getSemantic());
						model.setOrderId(schedule.getOrderId());
						model.setProductId(schedule.getProductId());
						model.setMethodId(schedule.getMethodId());
						model.setSampleId(schedule.getSampleId());
						model.setVerifyKey(schedule.getVerifyKey());
						OrderPlanTask planTask = getOrderPlanTaskByTaskId(model);
						if (null != taskResult) {
							if (null != planTask) {
								if ("0".equals(taskResult.getResult()))// 成功
								{
									schedulePlanService.updatePlanForTaskFinished(planTask.getId());
								} else if ("2".equals(taskResult.getResult()))// 直接处理
								{
									dealByTaskSemantic(testingTask, taskResult, planTask, schedule);
								} else if (StringUtils.isEmpty(taskResult.getResult()))// 有taskresult，但其他值都是空
																						// 按成功发送
								{
									schedulePlanService.updatePlanForTaskFinished(planTask.getId());
								}
							} else {
								// 验证流程重新提取dna,做完dna质检后，激活验证的流程
								if (StringUtils.isNotEmpty(schedule.getVerifyKey())) {
									String semantic = "";
									if (schedule.getScheduleNodes().contains("DNA-EXT")) {
										semantic = schedule.getScheduleNodes().split("/")[2];
									} else if (!schedule.getScheduleNodes().contains("DNA-EXT") && schedule.getScheduleNodes().contains("DNA-QC")) {
										semantic = schedule.getScheduleNodes().split("/")[1];
									}
									PlanSearchModel modelyc = new PlanSearchModel();
									model.setSemantic(semantic);
									model.setOrderId(schedule.getOrderId());
									model.setProductId(schedule.getProductId());
									model.setMethodId(schedule.getMethodId());
									model.setSampleId(schedule.getSampleId());
									model.setVerifyKey(schedule.getVerifyKey());
									OrderPlanTask planTaskYc = getOrderPlanTaskByTaskId(modelyc);
									if (null != planTaskYc) {
										planTaskYc.setActived(true);
										planTaskYc.setActualStartDate(new Date());
										planTaskYc.setActualFinishDate(null);
										baseDaoSupport.update(planTaskYc);
									}
								}
							}

						}// 为空直接当成功 (longpcr构建没存taskResult表)
						else {
							schedulePlanService.updatePlanForTaskFinished(planTask.getId());
						}
					}
				}
			}
		}

	}

	// 根据策略处理
	public void dealByTaskSemantic(TestingTask task, TestingTaskResult taskResult, OrderPlanTask planTask, TestingSchedule schedule) {
		String semantic = task.getSemantic();
		String details = taskResult.getDetails();
		String result = "";
		switch (semantic) {
		case TaskSemantic.DNA_QC:
			result = "重新提取";
			finishActualStartUp(planTask, TaskSemantic.DNA_EXTRACT);
			break;
		case TaskSemantic.CDNA_QC:
			result = "重新提取";
			finishActualStartUp(planTask, TaskSemantic.CDNA_EXTRACT);
			break;
		case TaskSemantic.LIBRARY_QC:
			result = "重新建库";
			finishActualStartUp(planTask, TaskSemantic.LIBRARY_CRE);
			break;
		case TaskSemantic.LIBRARY_CAP:
			result = "重新捕获";
			break;
		case TaskSemantic.DT_DATA_ANALYSIS:
			result = "重新实验";
			finishActualStartUp(planTask, TaskSemantic.DT);
			break;
		case TaskSemantic.MLPA_DATA_ANALYSIS:
			if ("2".equals(taskResult.getStrategy())) {
				result = "重新提取DNA/CDNA(原始样本为DNA/CDNA则重做质检)";
				String scheduleNode = schedule.getScheduleNodes().split("/")[0];
				finishActualStartUp(planTask, scheduleNode);
			} else if ("3".equals(taskResult.getStrategy())) {
				result = "重新实验";
				finishActualStartUp(planTask, TaskSemantic.MLPA);
			}
			break;
		case TaskSemantic.LONG_QC:
			result = "重新实验";
			finishActualStartUp(planTask, TaskSemantic.LONG_PCR);
			break;
		case TaskSemantic.MULTIPCR_QC:
			result = "重新实验";
			finishActualStartUp(planTask, TaskSemantic.MULTI_PCR);
			break;
		case TaskSemantic.PRIMER_DESIGN:
			result = details;
			break;
		case TaskSemantic.PCR_ONE:
			result = details;
			break;
		case TaskSemantic.DATA_ANALYSIS:
			if (StringUtils.isNotEmpty(details)) {
				Map map = JsonUtils.asObject(details, Map.class);
				if (null != map && map.containsKey("dispose")) {
					result = StringUtils.isNotEmpty(map.get("dispose")) ? map.get("dispose").toString() : "";
					if ("重新测序".equals(result) || "反向测序".equals(result)) {
						finishActualStartUp(planTask, TaskSemantic.PCR_TWO);
					} else if ("一次PCR".equals(result)) {
						finishActualStartUp(planTask, TaskSemantic.PCR_ONE);
					} else if ("重新设计引物".equals(result)) {
						// 完成当前
						planTask.setActived(false);
						planTask.setFinished(true);
						planTask.setActualFinishDate(new Date());
						baseDaoSupport.update(planTask);
					}
				}
			}
			break;
		case TaskSemantic.SANGER_PCR_ONE:
			result = details;
			break;
		case TaskSemantic.SANGER_DATA_ANALYSIS:
			if (StringUtils.isNotEmpty(details)) {
				Map map = JsonUtils.asObject(details, Map.class);
				if (null != map && map.containsKey("dispose")) {
					result = StringUtils.isNotEmpty(map.get("dispose")) ? map.get("dispose").toString() : "";
					if ("重新测序".equals(result) || "反向测序".equals(result)) {
						finishActualStartUp(planTask, TaskSemantic.SANGER_PCR_TWO);
					} else if ("一次PCR".equals(result)) {
						finishActualStartUp(planTask, TaskSemantic.SANGER_PCR_ONE);
					}
				}
			}
			break;
		case TaskSemantic.FLUO_ANALYSE:
			result = "重新实验";
			finishActualStartUp(planTask, TaskSemantic.FLUO_TEST);
			break;
		case TaskSemantic.QPCR:
			result = "重新实验";
			break;
		case TaskSemantic.PCR_NGS:
			result = details;
			break;
		case TaskSemantic.PCR_NGS_DATA_ANALYSIS:
			if (StringUtils.isNotEmpty(details)) {
				Map map = JsonUtils.asObject(details, Map.class);
				if (null != map && map.containsKey("dispose")) {
					result = StringUtils.isNotEmpty(map.get("dispose")) ? map.get("dispose").toString() : "";
					if ("重新PCR-NGS实验".equals(result)) {
						finishActualStartUp(planTask, TaskSemantic.PCR_NGS);
					} else if ("重新设计引物".equals(result)) {
						// 完成当前
						planTask.setActived(false);
						planTask.setFinished(true);
						planTask.setActualFinishDate(new Date());
						baseDaoSupport.update(planTask);
					}
				}
			}
			break;
		default:
			break;
		}
	}

	// 完成当前节点，激活策略的节点(进行中)
	private void finishActualStartUp(OrderPlanTask planTask, String semantic) {
		if (null != planTask) {
			// 完成当前
			planTask.setActived(false);
			planTask.setFinished(true);
			planTask.setActualFinishDate(new Date());
			baseDaoSupport.update(planTask);
			// 激活上个任务
			PlanSearchModel model = new PlanSearchModel();
			model.setSemantic(semantic);
			model.setOrderId(planTask.getOrderId());
			model.setProductId(planTask.getProductId());
			model.setMethodId(planTask.getTestingMethodId());
			model.setSampleId(planTask.getSampleId());
			model.setVerifyKey(planTask.getVerifyId());
			OrderPlanTask plan2 = getOrderPlanTaskByTaskId(model);
			if(plan2!=null) {
			    plan2.setActived(true);
	            plan2.setActualStartDate(new Date());
	            plan2.setActualFinishDate(null);
	            baseDaoSupport.update(plan2);
	            // 如果是报告那边置为不合格，实验都完成了，还要向上打开父节点(进行中) 2017.11.1
	            openParentNodeActiving(plan2.getParentId());
			}
		}
	}

	private void openParentNodeActiving(String parentId) {
		OrderPlanTask planTask = baseDaoSupport.get(OrderPlanTask.class, parentId);
		planTask.setActived(true);
		planTask.setActualFinishDate(null);
		baseDaoSupport.update(planTask);
		if (StringUtils.isNotEmpty(planTask.getParentId())) {
			openParentNodeActiving(planTask.getParentId());
		}
	}

	// 根据task获取schedules(可能任务合起来做)
	private List<TestingSchedule> getSchedulesByTaskId(String taskId) {
		List<TestingSchedule> list = Lists.newArrayList();
		String hql = "FROM TestingScheduleHistory t WHERE 1=1  AND t.taskId =:taskId ";
		List<TestingScheduleHistory> hisLists = baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[] { "taskId" },
				new Object[] { taskId });
		if (Collections3.isNotEmpty(hisLists)) {
			for (TestingScheduleHistory history : hisLists) {
				TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, history.getScheduleId());
				list.add(schedule);
			}
		}
		return list;
	}

	public OrderPlanTask getOrderPlanTaskByTaskId(PlanSearchModel model) {
		String hql = " FROM OrderPlanTask op WHERE op.orderId =:orderId AND op.productId = :productId AND op.testingMethodId = :methodId AND op.sampleId = :sampleId AND op.taskSemantic = :semantic ";
		List<String> names = Lists.newArrayList();
		List<Object> values = Lists.newArrayList();
		names.addAll(Arrays.asList("orderId", "productId", "methodId", "sampleId", "semantic"));
		values.addAll(Arrays.asList(model.getOrderId(), model.getProductId(), model.getMethodId(), model.getSampleId(), model.getSemantic()));
		if (StringUtils.isNotEmpty(model.getVerifyKey())) {
			hql = hql + " AND op.verifyId = :verifyKey ";
			names.add("verifyKey");
			values.add(model.getVerifyKey());
		}
		NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(names).values(values).build();
		List<OrderPlanTask> plans = baseDaoSupport.find(queryer, OrderPlanTask.class);
		if (Collections3.isNotEmpty(plans)) {
			return plans.get(0);
		}

		return null;
	}

	public List<OrderPlanTask> getBySheetId(String id) {
		// 1.传过来任务单id 查询任务单相关的所有任务 在查出来任务相关的所有流程 取到
		// 每个orderid,productid,sampleid,methodid,tasksemantic
		List<String> taskIds = Lists.newArrayList();
		String taskSemantic = "";
		TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, id);
		PlanSearchModel model;
		List<PlanSearchModel> planSearchModels = Lists.newArrayList();
		List<TestingSchedule> scheduleList = Lists.newArrayList();
		if (null == sheet) {
			// 查不到应该是生信分析 生信分析没有任务单 传的是任务id
			TestingTask bioTask = baseDaoSupport.get(TestingTask.class, id);
			if (null == bioTask) {
				log.error("bioTask? task si null,taskId is:" + id);
				throw new IllegalStateException();
			}
			taskSemantic = bioTask.getSemantic();
			taskIds.add(id);
			scheduleList = getTestingScheduleByTaskId(taskIds);
		} else {
			List<TestingSheetTask> testingSheetTaskList = sheet.getTestingSheetTaskList();
			if (Collections3.isNotEmpty(testingSheetTaskList)) {
				testingSheetTaskList.stream().forEach(x -> taskIds.add(x.getTestingTaskId()));
				TestingTask testingTask = baseDaoSupport.get(TestingTask.class, taskIds.get(0));
				if (null == testingTask) {
					throw new IllegalStateException();
				}
				taskSemantic = testingTask.getSemantic();
				scheduleList = getTestingScheduleByTaskId(taskIds);
			}
		}
		for (TestingSchedule schedule : scheduleList) {
			model = new PlanSearchModel();
			model.setSemantic(taskSemantic);
			model.setOrderId(schedule.getOrderId());
			model.setProductId(schedule.getProductId());
			model.setMethodId(schedule.getMethodId());
			model.setSampleId(schedule.getSampleId());
			model.setVerifyKey(schedule.getVerifyKey());
			planSearchModels.add(model);
		}
		List<OrderPlanTask> records = getOrderPlansTaskByTaskId(planSearchModels);
		return records;
	}

	public List<TestingSchedule> getTestingScheduleByTaskId(List<String> taskIds) {
		String hql = "FROM TestingScheduleHistory t WHERE 1=1 AND EXISTS ( SELECT task.id FROM TestingTask task WHERE task.id = t.taskId AND task.status = 3 ) AND t.taskId IN (:taskIds) ";
		NamedQueryer queryer = new NamedQueryer();
		queryer.setHql(hql.toString());
		queryer.setNames(Arrays.asList("taskIds"));
		queryer.setValues(Arrays.asList(taskIds));
		List<TestingScheduleHistory> hisLists = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
		List<String> scheduleIds = Lists.newArrayList();
		if (Collections3.isNotEmpty(hisLists)) {
			hisLists.stream().forEach(x -> scheduleIds.add(x.getScheduleId()));
			Set<String> records = new HashSet<>(scheduleIds);
			String hql2 = "FROM TestingSchedule t WHERE t.id IN (:scheduleIds)";
			NamedQueryer queryer2 = new NamedQueryer();
			queryer2.setHql(hql2.toString());
			queryer2.setNames(Arrays.asList("scheduleIds"));
			queryer2.setValues(Arrays.asList(records));
			List<TestingSchedule> scheduleList = baseDaoSupport.find(queryer2, TestingSchedule.class);
			return scheduleList;
		}
		return Lists.newArrayList();
	}

	public List<OrderPlanTask> getOrderPlansTaskByTaskId(List<PlanSearchModel> planSearchModels) {
		List<OrderPlanTask> records = Lists.newArrayList();
		if (Collections3.isNotEmpty(planSearchModels)) {
			String hql = " FROM OrderPlanTask op WHERE op.orderId =:orderId AND op.productId = :productId AND op.testingMethodId = :methodId AND op.sampleId = :sampleId AND op.taskSemantic = :semantic ";
			for (PlanSearchModel model : planSearchModels) {
				List<String> names = Lists.newArrayList();
				List<Object> values = Lists.newArrayList();
				names.addAll(Arrays.asList("orderId", "productId", "methodId", "sampleId", "semantic"));
				values.addAll(Arrays.asList(model.getOrderId(), model.getProductId(), model.getMethodId(), model.getSampleId(), model.getSemantic()));
				if (StringUtils.isNotEmpty(model.getVerifyKey())) {
					hql = hql + " AND op.verifyId = :verifyKey ";
					names.add("verifyKey");
					values.add(model.getVerifyKey());
				}
				NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(names).values(values).build();
				List<OrderPlanTask> plans = baseDaoSupport.find(queryer, OrderPlanTask.class);
				if (Collections3.isEmpty(plans)) {
					continue;
				}
				records.addAll(plans);
			}
		}
		return records;
	}

	private boolean isFinshed(List<OrderPlanTaskDependency> depListTwo) {
		for (OrderPlanTaskDependency optd2 : depListTwo) {
			OrderPlanTask planTask = configService.getPlanTaskById(optd2.getDependencyTaskId());
			if (!planTask.isFinished()) {
				return false;
			}

		}
		return true;
	}

	@Override
	@Transactional
	public void updateAbnormalSolve(String taskId) {
		TestingTask task = baseDaoSupport.get(TestingTask.class, taskId);
		List<TestingSchedule> schedules = getSchedulesByTaskId(taskId);
		String semantic=task.getSemantic();
		updatePlanForAbnormalSovle(taskId, schedules, semantic);
	}
	
    @Override
    @Transactional
    public void updateAbnormalSolveForNewBiology(String taskId, String semantic)
    {
        List<TestingSchedule> testingSchedules = getSchedulesByTaskId(taskId);
        log.info("testingSchedules的size："+testingSchedules.size());
        updatePlanForAbnormalSovle(taskId, testingSchedules, semantic);
    }

    private void updatePlanForAbnormalSovle(String taskId, List<TestingSchedule> schedules, String semantic)
    {
        for (TestingSchedule schedule : schedules) {
			PlanSearchModel model = new PlanSearchModel();
			model.setSemantic(semantic);
			model.setOrderId(schedule.getOrderId());
			model.setProductId(schedule.getProductId());
			model.setMethodId(schedule.getMethodId());
			model.setSampleId(schedule.getSampleId());
			model.setVerifyKey(schedule.getVerifyKey());
			OrderPlanTask planTask = getOrderPlanTaskByTaskId(model);// 当前节点
			// 策略节点
			List<AbnormalSolveRecord> abList = baseDaoSupport.find(AbnormalSolveRecord.class, "FROM AbnormalSolveRecord a WHERE a.taskId = '"
					+ taskId + "'");

			if (Collections3.isNotEmpty(abList)) {
				AbnormalSolveRecord abnormalSolve = abList.get(0);
				// 只完成当前节点
				if (TaskSemantic.RESAMPLING.equals(abnormalSolve.getStrategy())) {
					if (null != planTask) {
						planTask.setActived(false);
						planTask.setFinished(true);
						planTask.setActualFinishDate(new Date());
						baseDaoSupport.update(planTask);
					}
				} else if (TaskSemantic.EXPERIMENT_CANCER.equals(abnormalSolve.getStrategy())) {
					log.error(" go to EXPERIMENT-CANCER ");
					if (null != planTask) {
						updatePlanForTaskCancel(planTask.getId(), true);
					}
				} else// 完成当前节点，激活策略节点
				{
					String strategy = "";
					String scheduleNode = schedule.getScheduleNodes().split("/")[0];
					if (TaskSemantic.DNA_EXTRACT.equals(abnormalSolve.getStrategy())) {
						if (TaskSemantic.DNA_QC.equals(scheduleNode)) {
							strategy = TaskSemantic.DNA_QC;
						} else {
							strategy = abnormalSolve.getStrategy();
						}
					} else if (TaskSemantic.CDNA_EXTRACT.equals(abnormalSolve.getStrategy())) {
						if (TaskSemantic.CDNA_QC.equals(scheduleNode)) {
							strategy = TaskSemantic.CDNA_QC;
						} else {
							strategy = abnormalSolve.getStrategy();
						}
					}else if(TaskSemantic.BIOLOGY_ANNOTATION.equals(abnormalSolve.getStrategy())) { 
					    strategy = TaskSemantic.BIOLOGY_ANALY;
					}else {
						strategy = abnormalSolve.getStrategy();
					}
					finishActualStartUp(planTask, strategy);
				}
			}
		}
    }

	// 取消操作
	private void updatePlanForTaskCancel(String planId, boolean flag) {
		OrderPlanTask planTask = baseDaoSupport.get(OrderPlanTask.class, planId);
		planTask.setActived(false);
		if (flag) {
			planTask.setCanceled(true);// 已取消
			planTask.setFinished(false);// 已完成置为未完成
		} else {
			planTask.setCanceled(false);
			planTask.setFinished(true);
		}
		planTask.setActualFinishDate(new Date());
		baseDaoSupport.update(planTask);
		if (StringUtils.isNotEmpty(planTask.getParentId())) {
			if ("GROUP_PRODUCT_SAMPLE_METHOD_VERIFY".equals(planTask.getTaskSemantic())) {
				if (!isGroupCompleteScheduleCancelForVerify(planTask.getParentId())) {
					updatePlanForTaskCancel(planTask.getParentId(), false);
				} else {
					if (!isGroupCompleteScheduleCancelAndFinishedForVerify(planTask.getParentId())) {
						if (isHavingOneFinishedForVerify(planTask.getParentId())) {
							updatePlanForTaskCancel(planTask.getParentId(), false);
						}
					}
				}
			} else if (planTask.getTaskSemantic().contains("GROUP_PRODUCT")
					&& !"GROUP_PRODUCT_SAMPLE_METHOD_VERIFY".equals(planTask.getTaskSemantic())) {
				if (!isGroupCompleteScheduleCancel(planTask.getParentId())) {
					updatePlanForTaskCancel(planTask.getParentId(), true);
				} else {
					if (!isGroupCompleteScheduleCancelAndFinished(planTask.getParentId())) {
						if (isHavingOneFinished(planTask.getParentId())) {
							updatePlanForTaskCancel(planTask.getParentId(), false);
						}
					}
				}
			} else// 查询parentid 是这个组的任务 是否有已取消,各个实验节点(有一个已取消，即向上更新)
			{
				if (isGroupDownCompleteScheduleCancel(planTask.getParentId())) {
					updatePlanForTaskCancel(planTask.getParentId(), true);
				}
			}
		}
	}

	// 检测验证最下面实验节点，有一个已取消，即已取消
	private boolean isGroupDownCompleteScheduleCancel(String parentId) {
		NamedQueryer queryer = NamedQueryer.builder()
				.hql("SELECT COUNT(*) FROM OrderPlanTask opt WHERE opt.parentId=:parentId AND opt.canceled=true ").names(Arrays.asList("parentId"))
				.values(Arrays.asList(parentId)).build();
		return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	// 检测：上面组节点，有一个不是已取消，即不取消。
	private boolean isGroupCompleteScheduleCancel(String parentId) {
		NamedQueryer queryer = NamedQueryer.builder()
				.hql("SELECT COUNT(*) FROM OrderPlanTask opt WHERE opt.parentId=:parentId AND opt.canceled=false ").names(Arrays.asList("parentId"))
				.values(Arrays.asList(parentId)).build();
		return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
	}

	// 检测：有已取消、已完成的，这时取消，父节点应该变成已完成
	private boolean isGroupCompleteScheduleCancelAndFinished(String parentId) {
		NamedQueryer queryer = NamedQueryer.builder()
				.hql("SELECT COUNT(*) FROM OrderPlanTask opt WHERE opt.parentId=:parentId AND opt.canceled=false AND opt.finished=false")
				.names(Arrays.asList("parentId")).values(Arrays.asList(parentId)).build();
		return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
	}

	// 判断有一个已完成，其他是已取消，上面节点已完成
	private boolean isHavingOneFinished(String parentId) {
		NamedQueryer queryer = NamedQueryer.builder()
				.hql("SELECT COUNT(*) FROM OrderPlanTask opt WHERE opt.parentId=:parentId AND opt.finished=true").names(Arrays.asList("parentId"))
				.values(Arrays.asList(parentId)).build();
		return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
	}

	/**
     ******************************************************************************************************************  
     */
	// 验证：判断有几个验证流程，取消 ，向上完成
	private boolean isGroupCompleteScheduleCancelForVerify(String parentId) {
		NamedQueryer queryer = NamedQueryer.builder()
				.hql("SELECT COUNT(*) FROM OrderPlanTask opt WHERE opt.parentId=:parentId AND opt.canceled=false AND opt.verifyId is not null")
				.names(Arrays.asList("parentId")).values(Arrays.asList(parentId)).build();
		return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
	}

	// 验证：有已取消、已完成的，这时取消，父节点应该变成已完成
	private boolean isGroupCompleteScheduleCancelAndFinishedForVerify(String parentId) {
		NamedQueryer queryer = NamedQueryer
				.builder()
				.hql("SELECT COUNT(*) FROM OrderPlanTask opt WHERE opt.parentId=:parentId AND opt.canceled=false AND opt.finished=false AND opt.verifyId is not null")
				.names(Arrays.asList("parentId")).values(Arrays.asList(parentId)).build();
		return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
	}

	// 判断有一个已完成，其他是已取消，上面节点已完成
	private boolean isHavingOneFinishedForVerify(String parentId) {
		NamedQueryer queryer = NamedQueryer.builder()
				.hql("SELECT COUNT(*) FROM OrderPlanTask opt WHERE opt.parentId=:parentId AND opt.finished=true AND opt.verifyId is not null")
				.names(Arrays.asList("parentId")).values(Arrays.asList(parentId)).build();
		return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
	}

	// 状态查询取消流程
	@Override
	@Transactional
	public void updateStatusSearchCancel(String scheduleId) {
		TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, scheduleId);
		PlanSearchModel model = new PlanSearchModel();
		model.setOrderId(schedule.getOrderId());
		model.setProductId(schedule.getProductId());
		model.setMethodId(schedule.getMethodId());
		model.setSampleId(schedule.getSampleId());
		model.setVerifyKey(schedule.getVerifyKey());
		OrderPlanTask planTask = getOrderPlanTaskByScheduleId(model);
		if (null != planTask) {
			updatePlanForTaskCancel(planTask.getId(), true);
		}
	}

	public OrderPlanTask getOrderPlanTaskByScheduleId(PlanSearchModel model) {
		// 不是组节点的当前监控节点
		String hql = " FROM OrderPlanTask op WHERE op.orderId =:orderId AND op.productId = :productId AND op.testingMethodId = :methodId AND op.sampleId = :sampleId AND op.grouped = false AND op.actived = true ";
		List<String> names = Lists.newArrayList();
		List<Object> values = Lists.newArrayList();
		names.addAll(Arrays.asList("orderId", "productId", "methodId", "sampleId"));
		values.addAll(Arrays.asList(model.getOrderId(), model.getProductId(), model.getMethodId(), model.getSampleId()));
		if (StringUtils.isNotEmpty(model.getVerifyKey())) {
			hql = hql + " AND op.verifyId = :verifyKey ";
			names.add("verifyKey");
			values.add(model.getVerifyKey());
		}
		NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(names).values(values).build();
		List<OrderPlanTask> plans = baseDaoSupport.find(queryer, OrderPlanTask.class);
		if (Collections3.isNotEmpty(plans)) {
			return plans.get(0);
		}
		return null;
	}

	@Override
	public void newUpdatePlanWithSheetSubmit(ScheduleTaskActiveEvent event) {
//
//		PlanSearchModel model;
//		TestingTaskResult taskResult = baseDaoSupport.get(TestingTaskResult.class, event.getPreTaskId());
//		List<TestingSchedule> schedules = getSchedulesByTaskId(event.getPreTaskId());
//		for (TestingSchedule schedule : schedules) {
//			model = new PlanSearchModel();
//			model.setSemantic(ScheduleActiveTaskService.getSemanticByTaskRefer(event.get));
//			model.setOrderId(schedule.getOrderId());
//			model.setProductId(schedule.getProductId());
//			model.setMethodId(schedule.getMethodId());
//			model.setSampleId(schedule.getSampleId());
//			model.setVerifyKey(schedule.getVerifyKey());
//			OrderPlanTask planTask = getOrderPlanTaskByTaskId(model);
//			if (null != taskResult) {
//				if (null != planTask) {
//					if ("0".equals(taskResult.getResult()))// 成功
//					{
//						schedulePlanService.updatePlanForTaskFinished(planTask.getId());
//					} else if ("2".equals(taskResult.getResult()))// 直接处理
//					{
//						dealByTaskSemantic(testingTask, taskResult, planTask, schedule);
//					} else if (StringUtils.isEmpty(taskResult.getResult()))// 有taskresult，但其他值都是空
//																			// 按成功发送
//					{
//						schedulePlanService.updatePlanForTaskFinished(planTask.getId());
//					}
//				} else {
//					// 验证流程重新提取dna,做完dna质检后，激活验证的流程
//					if (StringUtils.isNotEmpty(schedule.getVerifyKey())) {
//						String semantic = "";
//						if (schedule.getScheduleNodes().contains("DNA-EXT")) {
//							semantic = schedule.getScheduleNodes().split("/")[2];
//						} else if (!schedule.getScheduleNodes().contains("DNA-EXT") && schedule.getScheduleNodes().contains("DNA-QC")) {
//							semantic = schedule.getScheduleNodes().split("/")[1];
//						}
//						PlanSearchModel modelyc = new PlanSearchModel();
//						model.setSemantic(semantic);
//						model.setOrderId(schedule.getOrderId());
//						model.setProductId(schedule.getProductId());
//						model.setMethodId(schedule.getMethodId());
//						model.setSampleId(schedule.getSampleId());
//						model.setVerifyKey(schedule.getVerifyKey());
//						OrderPlanTask planTaskYc = getOrderPlanTaskByTaskId(modelyc);
//						if (null != planTaskYc) {
//							planTaskYc.setActived(true);
//							planTaskYc.setActualStartDate(new Date());
//							planTaskYc.setActualFinishDate(null);
//							baseDaoSupport.update(planTaskYc);
//						}
//					}
//				}
//
//			}// 为空直接当成功 (longpcr构建没存taskResult表)
//			else {
//				schedulePlanService.updatePlanForTaskFinished(planTask.getId());
//			}
//		}

	}
}
