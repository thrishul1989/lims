package com.todaysoft.lims.system.modules.bpm.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.searcher.SampleExperimentRequest;
import com.todaysoft.lims.system.model.searcher.SampleTestingExportSearch;
import com.todaysoft.lims.system.model.vo.DnaSampleTestingInfo;
import com.todaysoft.lims.system.model.vo.LibSampleTestingInfo;
import com.todaysoft.lims.system.model.vo.OrderSampleExportModel;
import com.todaysoft.lims.system.model.vo.OrderSamplePageModel;
import com.todaysoft.lims.system.model.vo.ReportOrderSampleInfo;
import com.todaysoft.lims.system.model.vo.SampleTestingExportModel;
import com.todaysoft.lims.system.model.vo.TaskFailExportModel;
import com.todaysoft.lims.system.model.vo.TaskSheetExportModel;
import com.todaysoft.lims.system.model.vo.TestingTaskRequest;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheetSearcher;
import com.todaysoft.lims.system.modules.bpm.model.TestTask;
import com.todaysoft.lims.system.modules.bpm.model.TestTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.ontest.service.impl.SequencingService;
import com.todaysoft.lims.system.modules.bpm.service.ITestingTaskSheetService;
import com.todaysoft.lims.system.modules.bpm.service.ITestingTestTasksService;
import com.todaysoft.lims.system.modules.bpm.service.TestingTaskSheetServiceFactory;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingTestTasksService implements ITestingTestTasksService, ITestingTaskSheetService {
	@Autowired
	private RestTemplate template;

	@Autowired
	private TestingTaskSheetServiceFactory testingTaskSheetServiceFactory;

	private static Logger log = LoggerFactory.getLogger(TestingTestTasksService.class);

	@Override
	public List<TestTask> testTasks(TestTaskSearcher searcher) {
		String url = GatewayService.getServiceUrl("/bpm/testing/testList");
		ResponseEntity<List<TestTask>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestTaskSearcher>(searcher),
				new ParameterizedTypeReference<List<TestTask>>() {
				});

		List<TestTask> allList = exchange.getBody();
		// 对不同的实验节点待办列表进行组装
		// 上机实验
		List<TestTask> ngsSequecingSheetList = testingTaskSheetServiceFactory.getSerivceByTaskRefer("NGS_SEQUECING_TASK").getTestingTaskSheet();

		allList.addAll(ngsSequecingSheetList);
		Collections.sort(allList);

		return allList;
	}

	@Override
	public String exportSampleTesting(SampleExperimentRequest searcher) {
		String time = DateUtils.formatDate(new Date(), "yyyyMMdd");
		File file = new File("SAMPLE_TESTING_REPORT_FORM_" + time + ".xlsx");
		Workbook wbModule;
		List<String[]> dataList = getSampleTestingExportRecords(searcher);
		try {
			InputStream in = TestingTestTasksService.class.getResourceAsStream("/taskTemplate/testingReportForm/SAMPLE_TESTING_REPORT_FORM.xlsx");
			TestSheetService.inputstreamToFile(in, file);
			String path = file.getPath();

			ExcelUtil excel = new ExcelUtil();
			wbModule = excel.getTempWorkbook(path);
			excel.exportObjects2ModuleExcel(dataList, 2, wbModule, "样本实验统计表", true, path);
			wbModule.write(new FileOutputStream(path));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return file.getAbsolutePath();
	}

	private List<String[]> wrapObjectToListString(SampleTestingExportModel model) {
		List<String[]> results = Lists.newArrayList();
		if (null != model) {
			String[] modelArr = new String[41];
			modelArr[0] = StringUtils.isNotEmpty(model.getOrderCode()) ? model.getOrderCode() : "";
			modelArr[2] = StringUtils.isNotEmpty(model.getProductName()) ? model.getProductName() : "";
			modelArr[3] = StringUtils.isNotEmpty(model.getContractName()) ? model.getContractName() : "";
			modelArr[4] = StringUtils.isNotEmpty(model.getMarketCenter()) ? model.getMarketCenter() : "";
			modelArr[6] = StringUtils.isNotEmpty(model.getCustomerName()) ? model.getCustomerName() : "";
			modelArr[7] = StringUtils.isNotEmpty(model.getCustomerCompany()) ? model.getCustomerCompany() : "";
			modelArr[8] = StringUtils.isNotEmpty(model.getSalesManName()) ? model.getSalesManName() : "";
			if (Collections3.isNotEmpty(model.getSamples())) {
				for (OrderSampleExportModel sample : model.getSamples()) {
					String[] sampleArr = new String[41];
					sampleArr = modelArr.clone();
					sampleArr[1] = StringUtils.isNotEmpty(sample.getSampleCode()) ? sample.getSampleCode() : "";
					sampleArr[5] = StringUtils.isNotEmpty(sample.getSampleName()) ? sample.getSampleName() : "";
					sampleArr[9] = StringUtils.isNotEmpty(sample.getReceivedSampleLongLocation()) ? sample.getReceivedSampleLongLocation() : "";
					sampleArr[10] = StringUtils.isNotEmpty(sample.getReceivedSampleTermLocation()) ? sample.getReceivedSampleTermLocation() : "";
					if (Collections3.isNotEmpty(sample.getDnaList())) {
						for (DnaSampleTestingInfo dnaSample : sample.getDnaList()) {
							String[] dnaSampleArr = new String[41];
							dnaSampleArr = sampleArr.clone();
							dnaSampleArr[11] = StringUtils.isNotEmpty(dnaSample.getDnaSheetCode()) ? dnaSample.getDnaSheetCode() : "";
							dnaSampleArr[12] = StringUtils.isNotEmpty(dnaSample.getDnaAssignName()) ? dnaSample.getDnaAssignName() : "";
							dnaSampleArr[13] = StringUtils.isNotEmpty(dnaSample.getDnaAssignDate()) ? DateUtils.formatDate(
									dnaSample.getDnaAssignDate(), "yyyy-MM-dd HH:mm:ss") : "";
							dnaSampleArr[14] = StringUtils.isNotEmpty(dnaSample.getDnaTestorName()) ? dnaSample.getDnaTestorName() : "";
							dnaSampleArr[15] = StringUtils.isNotEmpty(dnaSample.getDnaCompleteDate()) ? DateUtils.formatDate(
									dnaSample.getDnaCompleteDate(), "yyyy-MM-dd HH:mm:ss") : "";
							dnaSampleArr[16] = StringUtils.isNotEmpty(dnaSample.getDnaSampleCode()) ? dnaSample.getDnaSampleCode() : "";
							dnaSampleArr[17] = StringUtils.isNotEmpty(dnaSample.getDnaSampleConcentration()) ? dnaSample.getDnaSampleConcentration()
									.toString() : "";
							dnaSampleArr[18] = StringUtils.isNotEmpty(dnaSample.getDnaRatio2628()) ? dnaSample.getDnaRatio2628().toString() : "";
							dnaSampleArr[19] = StringUtils.isNotEmpty(dnaSample.getDnaRatio2623()) ? dnaSample.getDnaRatio2623().toString() : "";
							dnaSampleArr[20] = StringUtils.isNotEmpty(dnaSample.getDnaVolume()) ? dnaSample.getDnaVolume().toString() : "";
							dnaSampleArr[21] = StringUtils.isNotEmpty(dnaSample.getDnaQualityLevel()) ? dnaSample.getDnaQualityLevel() : "";
							dnaSampleArr[22] = StringUtils.isNotEmpty(dnaSample.getDnaReagentKitName()) ? dnaSample.getDnaReagentKitName() : "";
							dnaSampleArr[23] = StringUtils.isNotEmpty(dnaSample.getDnaIsQualified()) ? dnaSample.getDnaIsQualified() : "";
							dnaSampleArr[24] = StringUtils.isNotEmpty(dnaSample.getDnaNotQualifiedRemark()) ? dnaSample.getDnaNotQualifiedRemark()
									: "";
							dnaSampleArr[25] = StringUtils.isNotEmpty(dnaSample.getDnaLocation()) ? dnaSample.getDnaLocation() : "";
							if (Collections3.isNotEmpty(dnaSample.getLibList())) {
								for (LibSampleTestingInfo libSample : dnaSample.getLibList()) {
									String[] libSampleArr = new String[41];
									libSampleArr = dnaSampleArr.clone();
									libSampleArr[26] = StringUtils.isNotEmpty(libSample.getLibSheetCode()) ? libSample.getLibSheetCode() : "";
									libSampleArr[27] = StringUtils.isNotEmpty(libSample.getLibAssignName()) ? libSample.getLibAssignName() : "";
									libSampleArr[28] = StringUtils.isNotEmpty(libSample.getLibAssignDate()) ? DateUtils.formatDate(
											libSample.getLibAssignDate(), "yyyy-MM-dd HH:mm:ss") : "";
									libSampleArr[29] = StringUtils.isNotEmpty(libSample.getLibTestorName()) ? libSample.getLibTestorName() : "";
									libSampleArr[30] = StringUtils.isNotEmpty(libSample.getLibCompleteDate()) ? DateUtils.formatDate(
											libSample.getLibCompleteDate(), "yyyy-MM-dd HH:mm:ss") : "";
									libSampleArr[31] = StringUtils.isNotEmpty(libSample.getLibCode()) ? libSample.getLibCode() : "";
									libSampleArr[32] = StringUtils.isNotEmpty(libSample.getLibConcn()) ? libSample.getLibConcn().toString() : "";
									libSampleArr[33] = StringUtils.isNotEmpty(libSample.getLib2628()) ? libSample.getLib2628().toString() : "";
									libSampleArr[34] = StringUtils.isNotEmpty(libSample.getLib2623()) ? libSample.getLib2623().toString() : "";
									libSampleArr[35] = StringUtils.isNotEmpty(libSample.getLibVulume()) ? libSample.getLibVulume().toString() : "";
									libSampleArr[36] = StringUtils.isNotEmpty(libSample.getLibQualityLevel()) ? libSample.getLibQualityLevel() : "";
									libSampleArr[37] = StringUtils.isNotEmpty(libSample.getLibReagentKitName()) ? libSample.getLibReagentKitName()
											: "";
									libSampleArr[38] = StringUtils.isNotEmpty(libSample.getLibIsQualified()) ? libSample.getLibIsQualified() : "";
									libSampleArr[39] = StringUtils.isNotEmpty(libSample.getLibNotQualifiedRemark()) ? libSample
											.getLibNotQualifiedRemark() : "";
									libSampleArr[40] = StringUtils.isNotEmpty(libSample.getLibLocation()) ? libSample.getLibLocation() : "";
									results.add(libSampleArr);
								}
							} else {
								results.add(dnaSampleArr);
							}
						}
					} else {
						results.add(sampleArr);
					}
				}
			} else {
				results.add(modelArr);
			}
		}
		return results;
	}

	@Override
	public String exportTaskSheet(SampleExperimentRequest searcher) {
		String time = DateUtils.formatDate(new Date(), "yyyyMMdd");
		File file = new File("TASK_SHEET_" + time + ".xlsx");
		Workbook wbModule;
		List<String[]> dataList = getSheetSampleExportRecords(searcher);
		try {
			InputStream in = TestingTestTasksService.class.getResourceAsStream("/taskTemplate/testingReportForm/WORK_TASK.xlsx");
			TestSheetService.inputstreamToFile(in, file);
			String path = file.getPath();

			ExcelUtil excel = new ExcelUtil();
			wbModule = excel.getTempWorkbook(path);

			excel.exportObjects2ModuleExcel(dataList, 1, wbModule, "工作任务统计表", true, path);
			wbModule.write(new FileOutputStream(path));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return file.getAbsolutePath();

	}

	private List<String[]> getSampleTestingExportRecords(SampleExperimentRequest searcher) {
		int pageNo = 1;
		int pageSize = 50;
		List<String[]> records = Lists.newArrayList();
		Pagination<ReportOrderSampleInfo> pagination = null;
		do {
			searcher.setPageNo(pageNo++);
			searcher.setPageSize(pageSize);
			OrderSamplePageModel model = getSampleTestingExportRecordPaging(searcher);
			if (null != model) {
				pagination = new Pagination<>();
				pagination.setPageNo(model.getPageNo());
				pagination.setPageSize(model.getPageSize());
				pagination.setTotalCount(model.getTotalCount());
				if (Collections3.isNotEmpty(model.getRecords())) {
					records.addAll(model.getRecords());
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("Search records for page {} successful, total count {}, total page {}.", pageNo, pagination.getTotalCount(),
						pagination.getTotalPage());
			}

		} while (!pagination.isLastPage());

		if (log.isDebugEnabled()) {
			log.debug("Search records successful, total count {}.", records.size());
		}
		return records;
	}

	private List<String[]> wrapTaskSheetToData(List<TaskSheetExportModel> list) {
		List<String[]> results = Lists.newArrayList();
		if (Collections3.isNotEmpty(list)) {
			for (TaskSheetExportModel model : list) {
				String result[] = new String[9];
				result[0] = model.getTaskName();
				result[1] = model.getTaskSheetCode();
				result[2] = model.getAssignName();
				result[3] = StringUtils.isNotEmpty(model.getAssignDate()) ? DateUtils.formatDate(model.getAssignDate(), "yyyy-MM-dd HH:mm:ss") : "";
				result[4] = model.getTestorName();
				result[5] = StringUtils.isNotEmpty(model.getCompleteDate()) ? DateUtils.formatDate(model.getCompleteDate(), "yyyy-MM-dd HH:mm:ss")
						: "";
				result[6] = StringUtils.toStr(model.getTaskSampleNum());
				result[7] = model.getReagentKitName();
				result[8] = StringUtils.isNotEmpty(model.getTaskDay()) ? model.getTaskDay().toString() + "" : "";
				results.add(result);
			}
		}
		return results;
	}

	@Override
	public String exportTaskSucessRate(SampleExperimentRequest searcher) {
		String time = DateUtils.formatDate(new Date(), "yyyyMMdd");
		File file = new File("TASK_SUCESS_RATE_" + time + ".xlsx");
		Workbook wbModule;
		List<String[]> dataList = getTaskSucessRateExportRecords(searcher);
		try {
			InputStream in = TestingTestTasksService.class.getResourceAsStream("/taskTemplate/testingReportForm/TEST_SUCCESS_RATE.xlsx");
			TestSheetService.inputstreamToFile(in, file);
			String path = file.getPath();

			ExcelUtil excel = new ExcelUtil();
			wbModule = excel.getTempWorkbook(path);

			excel.exportObjects2ModuleExcel(dataList, 1, wbModule, "样本成功率统计", true, path);
			wbModule.write(new FileOutputStream(path));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return file.getAbsolutePath();
	}

	private List<String[]> wrapTaskSucessRateToData(List<TaskSheetExportModel> list) {
		List<String[]> results = Lists.newArrayList();
		if (Collections3.isNotEmpty(list)) {
			for (TaskSheetExportModel model : list) {
				String result[] = new String[9];
				result[0] = model.getTaskName();
				result[1] = model.getTaskSheetCode();
				result[2] = model.getAssignName();
				result[3] = StringUtils.isNotEmpty(model.getAssignDate()) ? DateUtils.formatDate(model.getAssignDate(), "yyyy-MM-dd HH:mm:ss") : "";
				result[4] = model.getTestorName();
				result[5] = StringUtils.isNotEmpty(model.getCompleteDate()) ? DateUtils.formatDate(model.getCompleteDate(), "yyyy-MM-dd HH:mm:ss")
						: "";
				result[6] = StringUtils.toStr(model.getTaskSampleNum());
				result[7] = StringUtils.isNotEmpty(model.getTaskDay()) ? model.getTaskDay().toString() + "" : "";
				result[8] = StringUtils.isNotEmpty(model.getSuccessRate()) ? model.getSuccessRate().toString() : "";
				results.add(result);
			}
		}
		return results;
	}

	@Override
	public String exportFailTasks(SampleExperimentRequest searcher) {
		String time = DateUtils.formatDate(new Date(), "yyyyMMdd");
		File file = new File("TASK_FAIL_" + time + ".xlsx");
		Workbook wbModule;
		List<String[]> dataList = getTaskFailSolveExportRecords(searcher);
		try {
			InputStream in = TestingTestTasksService.class.getResourceAsStream("/taskTemplate/testingReportForm/TASK_FAIL.xlsx");
			TestSheetService.inputstreamToFile(in, file);
			String path = file.getPath();

			ExcelUtil excel = new ExcelUtil();
			wbModule = excel.getTempWorkbook(path);

			excel.exportObjects2ModuleExcel(dataList, 1, wbModule, "异常任务处理", true, path);
			wbModule.write(new FileOutputStream(path));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return file.getAbsolutePath();
	}

	private List<String[]> getTaskFailSolveExportRecords(SampleExperimentRequest searcher) {

		int pageNo = 1;
		int pageSize = 100;
		List<String[]> records = Lists.newArrayList();
		Pagination<ReportOrderSampleInfo> pagination = null;
		do {
			searcher.setPageNo(pageNo++);
			searcher.setPageSize(pageSize);
			OrderSamplePageModel model = getTaskFailSolvePaging(searcher);
			if (null != model) {
				pagination = new Pagination<>();
				pagination.setPageNo(model.getPageNo());
				pagination.setPageSize(model.getPageSize());
				pagination.setTotalCount(model.getTotalCount());
				if (Collections3.isNotEmpty(model.getRecords())) {
					records.addAll(model.getRecords());
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("Search records for page {} successful, total count {}, total page {}.", pageNo, pagination.getTotalCount(),
						pagination.getTotalPage());
			}

		} while (!pagination.isLastPage());

		if (log.isDebugEnabled()) {
			log.debug("Search records successful, total count {}.", records.size());
		}
		return records;
	}

	private OrderSamplePageModel getTaskFailSolvePaging(SampleExperimentRequest searcher) {
		String url = GatewayService.getServiceUrl("/export/experiment/taskFailSolvePaging");
		ResponseEntity<OrderSamplePageModel> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SampleExperimentRequest>(searcher),
				new ParameterizedTypeReference<OrderSamplePageModel>() {
				});
		return exchange.getBody();
	}

	private List<String[]> wrapTaskFailToData(List<TaskFailExportModel> list) {
		List<String[]> results = Lists.newArrayList();
		if (Collections3.isNotEmpty(list)) {
			for (TaskFailExportModel model : list) {
				String result[] = new String[12];
				result[0] = model.getTaskName();
				result[1] = model.getOrderCode();
				result[2] = model.getProductName();
				result[3] = model.getMethods();
				result[4] = model.getReceivedSampleName();
				result[5] = model.getReceivedSampleCode();
				result[6] = model.getFailRemark();
				result[7] = StringUtils.isNotEmpty(model.getSubmitDate()) ? DateUtils.formatDate(model.getSubmitDate(), "yyyy-MM-dd HH:mm:ss") : "";
				result[8] = model.getOperatorName();
				result[9] = StringUtils.isNotEmpty(model.getOperatorDate()) ? DateUtils.formatDate(model.getOperatorDate(), "yyyy-MM-dd HH:mm:ss")
						: "";
				result[10] = model.getOperateStrategy();
				result[11] = model.getOperateRemark();
				results.add(result);
			}
		}
		return results;
	}

	public List<String[]> getSheetSampleExportRecords(SampleExperimentRequest searcher) {
		int pageNo = 1;
		int pageSize = 100;
		List<String[]> records = Lists.newArrayList();
		Pagination<ReportOrderSampleInfo> pagination = null;
		do {
			searcher.setPageNo(pageNo++);
			searcher.setPageSize(pageSize);
			OrderSamplePageModel model = getSheetSampleRecordPaging(searcher);
			if (null != model) {
				pagination = new Pagination<>();
				pagination.setPageNo(model.getPageNo());
				pagination.setPageSize(model.getPageSize());
				pagination.setTotalCount(model.getTotalCount());
				if (Collections3.isNotEmpty(model.getRecords())) {
					records.addAll(model.getRecords());
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("Search records for page {} successful, total count {}, total page {}.", pageNo, pagination.getTotalCount(),
						pagination.getTotalPage());
			}

		} while (!pagination.isLastPage());

		if (log.isDebugEnabled()) {
			log.debug("Search records successful, total count {}.", records.size());
		}
		return records;
	}

	private OrderSamplePageModel getSampleTestingExportRecordPaging(SampleExperimentRequest searcher) {

		String url = GatewayService.getServiceUrl("/export/experiment/sampleExperimentPaging");
		ResponseEntity<OrderSamplePageModel> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SampleExperimentRequest>(searcher),
				new ParameterizedTypeReference<OrderSamplePageModel>() {
				});
		return exchange.getBody();
	}

	public OrderSamplePageModel getSheetSampleRecordPaging(SampleExperimentRequest searcher) {
		String url = GatewayService.getServiceUrl("/export/experiment/sheetSamplePaging");
		ResponseEntity<OrderSamplePageModel> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SampleExperimentRequest>(searcher),
				new ParameterizedTypeReference<OrderSamplePageModel>() {
				});
		return exchange.getBody();
	}

	private List<String[]> getTaskSucessRateExportRecords(SampleExperimentRequest searcher) {
		int pageNo = 1;
		int pageSize = 100;
		List<String[]> records = Lists.newArrayList();
		Pagination<ReportOrderSampleInfo> pagination = null;
		do {
			searcher.setPageNo(pageNo++);
			searcher.setPageSize(pageSize);
			OrderSamplePageModel model = getTaskSucessRateExportPaging(searcher);
			if (null != model) {
				pagination = new Pagination<>();
				pagination.setPageNo(model.getPageNo());
				pagination.setPageSize(model.getPageSize());
				pagination.setTotalCount(model.getTotalCount());
				if (Collections3.isNotEmpty(model.getRecords())) {
					records.addAll(model.getRecords());
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("Search records for page {} successful, total count {}, total page {}.", pageNo, pagination.getTotalCount(),
						pagination.getTotalPage());
			}

		} while (!pagination.isLastPage());

		if (log.isDebugEnabled()) {
			log.debug("Search records successful, total count {}.", records.size());
		}
		return records;
	}

	public OrderSamplePageModel getTaskSucessRateExportPaging(SampleExperimentRequest searcher) {
		String url = GatewayService.getServiceUrl("/export/experiment/sheetSampleSuccessRatePaging");
		ResponseEntity<OrderSamplePageModel> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SampleExperimentRequest>(searcher),
				new ParameterizedTypeReference<OrderSamplePageModel>() {
				});
		return exchange.getBody();
	}

	private List<TaskFailExportModel> getExportFailTasksExportRecords(SampleTestingExportSearch searcher) {
		String url = GatewayService.getServiceUrl("/bpm/testing/getTaskFailModel");
		ResponseEntity<List<TaskFailExportModel>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SampleTestingExportSearch>(
				searcher), new ParameterizedTypeReference<List<TaskFailExportModel>>() {
		});
		return exchange.getBody();
	}

	@Override
	public List<TestTask> getTestingTaskSheet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestingSheet getTestingSheetByTaskId(String taskId) {
		String url = GatewayService.getServiceUrl("/bpm/testing/order/getTestingSheetByTaskId/{taskId}");
		return template.getForObject(url, TestingSheet.class, Collections.singletonMap("taskId", taskId));
	}

	@Override
	public TestingTaskRequest getTTRById(String testingTaskId) {
		String url = GatewayService.getServiceUrl("/bpm/testing/order/getTTRById/{testingTaskId}");
		return template.getForObject(url, TestingTaskRequest.class, Collections.singletonMap("testingTaskId", testingTaskId));
	}

	@Override
	public Pagination<TestingSheet> completeSheetpaging(TestingSheetSearcher searcher) {
		String url = GatewayService.getServiceUrl("/bpm/testingSheet/paging");
		ResponseEntity<Pagination<TestingSheet>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<TestingSheetSearcher>(searcher),
				new ParameterizedTypeReference<Pagination<TestingSheet>>() {
				});
		return exchange.getBody();
	}

}