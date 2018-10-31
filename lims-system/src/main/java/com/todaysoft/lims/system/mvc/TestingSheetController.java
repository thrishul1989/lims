package com.todaysoft.lims.system.mvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.config.ExceptionResolver;
import com.todaysoft.lims.system.model.vo.TestingSheet;
import com.todaysoft.lims.system.model.vo.TestingSheetTask;
import com.todaysoft.lims.system.model.vo.TestingSheetTaskAbsolute;
import com.todaysoft.lims.system.model.vo.TestingSheetTaskResult;
import com.todaysoft.lims.system.model.vo.testingtask.TaskSemantic;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.service.ICDNAQcService;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.INGSCompleteService;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.dnaqc.service.IDNAQcService;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTSubmitModel;
import com.todaysoft.lims.system.modules.bpm.dt.service.IDTService;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.firstpcr.service.IFirstPCRService;
import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model.FirstPCRSangerSheetModel;
import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.service.IFirstPCRSangerService;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSheetModel;
import com.todaysoft.lims.system.modules.bpm.libcap.service.ILibraryCaptureService;
import com.todaysoft.lims.system.modules.bpm.libqc.model.LibraryQcSubmitModel;
import com.todaysoft.lims.system.modules.bpm.libqc.service.ILibraryQcService;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestSubmitModel;
import com.todaysoft.lims.system.modules.bpm.mlpa.service.IMlpaService;
import com.todaysoft.lims.system.modules.bpm.model.test.args.TestArgs;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.ontest.service.ISequencingService;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.service.IPcrNgsPrimerDesignService;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.service.IPcrNgsTestService;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.pooling.service.IPoolingService;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.CheckPrimerForDesignRequest;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignSheetModel;
import com.todaysoft.lims.system.modules.bpm.primerdesign.service.IPrimerDesignService;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtSubmitModel;
import com.todaysoft.lims.system.modules.bpm.qt.service.IQtService;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTSheetModel;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.rqt.service.IRQTService;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.secondpcr.service.ISecondPCRService;
import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SecondPCRSangerSheetModel;
import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.service.ISecondPCRSangerService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.ITechnicalAnalyService;
import com.todaysoft.lims.system.modules.bsm.service.IPrimerService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.ITestingTaskService;
import com.todaysoft.lims.system.util.ConfigManage;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/testSheet")
public class TestingSheetController extends BaseController {

	@Autowired
	private ITestSheetService testSheetService;

	@Autowired
	private IDNAQcService dnaQcservice;

	@Autowired
	private ICDNAQcService cdnaQcservice;

	@Autowired
	private ILibraryQcService libQcService;

	@Autowired
	private IRQTService rqtService;

	@Autowired
	private ILibraryCaptureService libCapService;

	@Autowired
	private IPoolingService poolingService;

	@Autowired
	private IQtService qtService;
	@Autowired
	private ISequencingService sequencingService;

	@Autowired
	private ITechnicalAnalyService tecAnalysisService;

	@Autowired
	private IPrimerDesignService primerDesignService;

	@Autowired
	private IPcrNgsPrimerDesignService pcrNgsPrimerDesignService;

	@Autowired
	private IFirstPCRService firstPCRService;

	@Autowired
	private IPcrNgsTestService pcrNgsTestService;

	@Autowired
	private IPrimerService primerService;

	@Autowired
	private ISecondPCRService secondPCRService;

	@Autowired
	private ISecondPCRSangerService secondPCRSangerService;

	@Autowired
	private ITestingTaskService testingTaskService;

	@Autowired
	private IMlpaService mlpaService;

	@Autowired
	private IDTService dtService;

	@Autowired
	private IFirstPCRSangerService firstPCRSangerService;

	@Autowired
	private INGSCompleteService ngsCompleteService;

	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ExceptionResolver.class);

	@RequestMapping(value = "/absolute/paging", method = RequestMethod.POST)
	private Pagination<TestingSheetTaskAbsolute> absolutePaging() {
		return testSheetService.absolutePaging();
	}

	public List<String> getMethods(String method) {
		List<String> methodList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(method)) {
			String[] methods = method.split(",");
			for (int i = 0; i < methods.length; i++) {
				methodList.add(methods[i]);
			}
		}
		return methodList;

	}

	@RequestMapping(value = "/xddlData.do", method = RequestMethod.GET)
	public String xddl(String sheetsContent, Integer taskActivitiId, ModelMap model) {
		// TestingSheet sheet = testSheetService.getTestSheet(taskActivitiId);
		// List<TestingSheetTask> sheetTaskList = sheet.getSheetTaskList();
		// if (!StringUtils.isEmpty(sheetsContent))
		// {
		// JSONArray jsonArr = JSONObject.parseArray(sheetsContent);
		// for (int i = 0; i < jsonArr.size(); i++)
		// {
		// if (null != jsonArr.getJSONObject(i))
		// {
		// String ct = jsonArr.getJSONObject(i).get("CT").toString();
		// String volumeRatioToMix =
		// jsonArr.getJSONObject(i).get("volumeRatioToMix").toString();
		// String dataSize =
		// jsonArr.getJSONObject(i).get("dataSize").toString();
		//
		// TestingSheetTask tsTask = sheetTaskList.get(i);
		// tsTask.setDataSize(Double.parseDouble(dataSize));
		// TestingSheetTaskResult tstResult = new TestingSheetTaskResult();
		// tstResult.setCT(Double.parseDouble(ct));
		// tstResult.setVolumeRatioToMix(Double.parseDouble(volumeRatioToMix));
		// tsTask.setTaskResult(tstResult);
		// }
		// }
		// }
		// model.addAttribute("sheet", sheet);
		return "testschedule/xddl";

	}

	@ResponseBody
	@RequestMapping(value = "/xddlSubCT.do")
	public RQTSubmitRequest xddlSubCt(Integer sheetId, ModelMap model, HttpSession session, RQTSubmitRequest request, String sheetTaskList) {
		if (!StringUtils.isEmpty(sheetTaskList)) {
			JSONArray json = JSONObject.parseArray(sheetTaskList);
			for (int i = 0; i < json.size(); i++) {
				String ct = json.getJSONObject(i).get("ct").toString();
			}
			// 通过CT值给产物相对量和体积值赋值。
		}
		// 区分实验类型
		return request;
	}

	/**
	 * 生信分析
	 * */
	@ResponseBody
	@RequestMapping(value = "/bioInfoSubmit.do", method = RequestMethod.GET)
	public Map BioInfoSubmit(TestingSheet request, ModelMap model) {
		JSONObject jsStr = JSONObject.parseObject(request.getReq());
		request.setCode(jsStr.getString("code").toString());
		request.setId(jsStr.get("id").toString());
		request.setSemantic(jsStr.get("semantic").toString());
		Object oo = jsStr.get("sheetTaskList");
		List<TestingSheetTaskResult> list = JSON.parseArray(oo + "", TestingSheetTaskResult.class);
		List<TestingSheetTask> tlist = Lists.newArrayList();
		list.stream().forEach(tsResult -> {
			// tsResult.setActivitiTaskId(jsStr.getString("activitiTaskId").toString());
			// TestingSheetTask testingSheetTask = new TestingSheetTask();
			// testingSheetTask.setId(Integer.parseInt(jsStr.get("testingSheetTaskId").toString()));
			// testingSheetTask.setTaskResult(tsResult);
			// tlist.add(testingSheetTask);
			});
		request.setTestingSheetTaskList(tlist);
		testSheetService.bioInfoSubmit(request);
		Map m = new HashMap<>();
		m.put("result", "success");
		return m;

	}

	/**
	 * 技术分析
	 * */
	@ResponseBody
	@RequestMapping(value = "/tecAnalysisSubmit.do", method = RequestMethod.GET)
	public Map tecAnalysisSubmit(TestingSheet request, ModelMap model) {
		JSONObject jsStr = JSONObject.parseObject(request.getReq());
		request.setCode(jsStr.getString("code").toString());
		request.setId(jsStr.get("id").toString());
		request.setSemantic(jsStr.get("semantic").toString());
		Object oo = jsStr.get("sheetTaskList");
		List<TestingSheetTaskResult> list = JSON.parseArray(oo + "", TestingSheetTaskResult.class);
		List<TestingSheetTask> tlist = Lists.newArrayList();
		list.stream().forEach(tsResult -> {
			tsResult.setActivitiTaskId(jsStr.getString("activitiTaskId").toString());
			TestingSheetTask testingSheetTask = new TestingSheetTask();
			// testingSheetTask.setId(Integer.parseInt(jsStr.get("testingSheetTaskId").toString()));
			// testingSheetTask.setTaskResult(tsResult);
				tlist.add(testingSheetTask);
			});
		request.setTestingSheetTaskList(tlist);
		testSheetService.tecAnalysisSubmit(request);
		Map m = new HashMap<>();
		m.put("result", "success");
		return m;
	}

	/**
	 * 生成报告
	 * */
	@ResponseBody
	@RequestMapping(value = "/reportCreateSubmit.do", method = RequestMethod.GET)
	public Map reportCreateSubmit(TestingSheet request, ModelMap model) {
		JSONObject jsStr = JSONObject.parseObject(request.getReq());
		request.setCode(jsStr.getString("code").toString());
		request.setId(jsStr.get("id").toString());
		request.setSemantic(jsStr.get("semantic").toString());
		Object oo = jsStr.get("sheetTaskList");
		List<TestingSheetTaskResult> list = JSON.parseArray(oo + "", TestingSheetTaskResult.class);
		List<TestingSheetTask> tlist = Lists.newArrayList();
		list.stream().forEach(tsResult -> {
			tsResult.setActivitiTaskId(jsStr.getString("activitiTaskId").toString());
			TestingSheetTask testingSheetTask = new TestingSheetTask();
			// testingSheetTask.setId(Integer.parseInt(jsStr.get("testingSheetTaskId").toString()));
			// testingSheetTask.setTaskResult(tsResult);
				tlist.add(testingSheetTask);
			});
		request.setTestingSheetTaskList(tlist);
		testSheetService.reportCreateSubmit(request);
		Map m = new HashMap<>();
		m.put("result", "success");
		return m;
	}

	/**
	 * 报告审核
	 * */
	@ResponseBody
	@RequestMapping(value = "/reportCheckSubmit.do", method = RequestMethod.GET)
	public Map reportCheckSubmit(TestingSheet request, ModelMap model) {
		JSONObject jsStr = JSONObject.parseObject(request.getReq());
		request.setCode(jsStr.getString("code").toString());
		request.setId(jsStr.get("id").toString());
		request.setSemantic(jsStr.get("semantic").toString());
		Object oo = jsStr.get("sheetTaskList");
		List<TestingSheetTaskResult> list = JSON.parseArray(oo + "", TestingSheetTaskResult.class);
		List<TestingSheetTask> tlist = Lists.newArrayList();
		list.stream().forEach(tsResult -> {
			tsResult.setActivitiTaskId(jsStr.getString("activitiTaskId").toString());
			TestingSheetTask testingSheetTask = new TestingSheetTask();
			// testingSheetTask.setId(Integer.parseInt(jsStr.get("testingSheetTaskId").toString()));
			// testingSheetTask.setTaskResult(tsResult);
				tlist.add(testingSheetTask);
			});
		request.setTestingSheetTaskList(tlist);
		testSheetService.reportCheckSubmit(request);
		Map m = new HashMap<>();
		m.put("result", "success");
		return m;
	}

	/**
	 * 报告邮寄
	 * */
	@ResponseBody
	@RequestMapping(value = "/reportMailSubmit.do", method = RequestMethod.GET)
	public Map reportMailSubmit(TestingSheet request, ModelMap model) {
		JSONObject jsStr = JSONObject.parseObject(request.getReq());
		request.setCode(jsStr.getString("code").toString());
		request.setId(jsStr.get("id").toString());
		request.setSemantic(jsStr.get("semantic").toString());
		Object oo = jsStr.get("sheetTaskList");
		List<TestingSheetTaskResult> list = JSON.parseArray(oo + "", TestingSheetTaskResult.class);
		List<TestingSheetTask> tlist = Lists.newArrayList();
		list.stream().forEach(tsResult -> {
			tsResult.setActivitiTaskId(jsStr.getString("activitiTaskId").toString());
			TestingSheetTask testingSheetTask = new TestingSheetTask();
			// testingSheetTask.setId(Integer.parseInt(jsStr.get("testingSheetTaskId").toString()));
			// testingSheetTask.setTaskResult(tsResult);
				tlist.add(testingSheetTask);
			});
		request.setTestingSheetTaskList(tlist);
		testSheetService.reportMailSubmit(request);
		Map m = new HashMap<>();
		m.put("result", "success");
		return m;
	}

	@ResponseBody
	@RequestMapping(value = "/downloadDNAData", method = RequestMethod.POST)
	public String downloadDNAData(Integer taskActivitiId) {
		ClassLoader classLoader = ConfigManage.class.getClassLoader();
		String resource = null;
		TestingSheet entity = testSheetService.getTestSheet(taskActivitiId);
		if (TaskSemantic.DNA_EXTRACT.equals(entity.getSemantic())) {

			resource = classLoader.getResource("taskTemplate/DNATemplate.xlsx").getPath();
		} else {
			resource = classLoader.getResource("taskTemplate/RNATemplate.xlsx").getPath();
		}
		TestingSheetController.class.getResourceAsStream("/taskTemplate/RNATemplate.xlsx");

		String path = null;
		if (resource.indexOf("/") == 0) {
			path = resource.substring(1);
		}
		return testSheetService.downloadDNAData(path, entity);

	}

	@ResponseBody
	@RequestMapping(value = "/uploadDNAAmount")
	public List<List<String>> uploadDNAAmount(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(testSheetService.uploadData(uploadData, 8, 7));
		list.add(testSheetService.uploadData(uploadData, 8, 8));
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/downloadDNAQCData", method = RequestMethod.POST)
	public String downloadDNAQCData(String sheetId, BigDecimal[] concentration, BigDecimal[] volume, BigDecimal[] A260280, BigDecimal[] A260230,
			String[] qualityGrade) {
		TestArgs args = new TestArgs();
		args.setId(sheetId);
		DNAQcSheet entity = dnaQcservice.getDNAQcTestModel(args);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/DNA_QC.xlsx");
		logger.info("~~~~DNA_QC_path~~~" + is);
		return testSheetService.downloadDNAQCData(is, entity, concentration, volume, A260280, A260230, qualityGrade);

	}

	@ResponseBody
	@RequestMapping(value = "/downloadCDNAQCData", method = RequestMethod.POST)
	public String downloadCDNAQCData(String sheetId, String[] concentration, String[] volume, String[] A260280, String[] A260230,
			String[] qualityGrade) {
		TestArgs args = new TestArgs();
		args.setId(sheetId);
		CDNAQcSheet entity = cdnaQcservice.getCDNAQcTestModel(args);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/CDNA_QC.xlsx");
		logger.info("~~~~CDNA_QC_PATH~~~" + is);
		return testSheetService.downloadCDNAQCData(is, entity, concentration, volume, A260280, A260230, qualityGrade);

	}

	@ResponseBody
	@RequestMapping(value = "/downloadWKCreateData", method = RequestMethod.POST)
	public String downloadWKCreateData(Integer taskActivitiId) {
		TestingSheet entity = testSheetService.getTestSheet(taskActivitiId);
		ClassLoader classLoader = ConfigManage.class.getClassLoader();
		String resource = classLoader.getResource("taskTemplate/WK_CREATE.xlsx").getPath();
		String path = null;
		if (resource.indexOf("/") == 0) {
			path = resource.substring(1);
		}
		return testSheetService.downloadWKCreateData(path, entity);

	}

	@ResponseBody
	@RequestMapping(value = "/downloadLibQcData", method = RequestMethod.POST)
	public String downloadLibQcData(String sheetId, String[] concentration, String[] volume, String[] A260280, String[] A260230, String[] qualityGrade) {
		LibraryQcSubmitModel entity = libQcService.getSubmitModel(sheetId);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/LIB_QC.xlsx");
		return testSheetService.downloadLibQcData(is, entity, concentration, volume, A260280, A260230, qualityGrade);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadLibCapData", method = RequestMethod.POST)
	public String downloadLibrarycatchData(String sheetId, BigDecimal[] concentration, BigDecimal[] volume) {
		TestArgs args = new TestArgs();
		args.setId(sheetId);
		LibraryCaptureSheetModel entity = libCapService.getSubmitModel(sheetId);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/LIBRARY_CATCH.xlsx");
		logger.info("~~~~LIBRARY_CATCH_PATH~~~" + is);
		return testSheetService.downloadLibrarycatchData(is, entity, concentration, volume);

	}

	@ResponseBody
	@RequestMapping(value = "/downloadRQTData", method = RequestMethod.POST)
	public String downloadRQTData(String sheetId, BigDecimal[] ctv) {
		TestArgs args = new TestArgs();
		args.setId(sheetId);
		RQTSheetModel entity = rqtService.getRQTSubmitModel(sheetId);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/RQT.xlsx");
		logger.info("~~~~LIBRARY_CATCH_PATH~~~" + is);

		return testSheetService.downloadRQTData(is, entity, ctv);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadPoolingData", method = RequestMethod.POST)
	public String downloadRQTData(String sheetId) {
		TestArgs args = new TestArgs();
		args.setId(sheetId);
		PoolingSubmitModel entity = poolingService.getPoolingSubmitModel(sheetId);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/Pooling.xlsx");
		logger.info("~~~~POOLING_PATH~~~" + is);

		return testSheetService.downloadPoolingData(is, entity);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadQtData", method = RequestMethod.POST)
	public String downloadJDDLData(String sheetId, BigDecimal concn, BigDecimal fragment_length) {
		TestArgs args = new TestArgs();
		args.setId(sheetId);
		QtSubmitModel entity = qtService.getSubmitModel(sheetId);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/QT.xlsx");
		logger.info("~~~~QT_PATH~~~" + is);
		return testSheetService.downloadJDDLData(is, entity, concn, fragment_length);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadOnTestingData", method = RequestMethod.POST)
	public String downloadOnTestData(String sheetId) {
		TestArgs args = new TestArgs();
		args.setId(sheetId);
		SequencingSubmitModel entity = sequencingService.getSubmitModel(sheetId);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/OnTesting.xlsx");
		logger.info("~~~~QT_PATH~~~" + is);

		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormater.format(new Date());
		File zipfile = new File(time + "_" + entity.getCode() + ".zip");
		return testSheetService.downloadOnTestingData(is, entity, zipfile, entity.getTestingSheetTaskList().get(0).getTestingTaskId());

	}

	@ResponseBody
	@RequestMapping(value = "/downloadNgsSequecingData", method = RequestMethod.POST)
	public String downloadNgsSequecingData(String sheetId) {
		TestArgs args = new TestArgs();
		args.setId(sheetId);
		SequencingSubmitModel sheet = ngsCompleteService.getNgsSequencingSheet(sheetId);
		InputStream is = null;
		if (1 == sheet.getSequecingType()) {
			// 自测
			is = TestingSheetController.class.getResourceAsStream("/taskTemplate/ngs_self.xlsx");
		} else {
			// 外送
			is = TestingSheetController.class.getResourceAsStream("/taskTemplate/ngs_other.xlsx");
		}

		logger.info("~~~~QT_PATH~~~" + is);

		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormater.format(new Date());
		File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
		return testSheetService.downloadNgsSequecingData(is, sheet, zipfile, sheet.getSampleCode());

	}

	@ResponseBody
	@RequestMapping(value = "/downloadBioinformaticAnalysis", method = RequestMethod.POST)
	public String downloadBioinformaticAnalysis(Integer taskActivitiId) {
		TestingSheet entity = testSheetService.getTestSheet(taskActivitiId);
		ClassLoader classLoader = ConfigManage.class.getClassLoader();
		String resource = classLoader.getResource("taskTemplate/BIOINFORMATICS_ANALYZE.xlsx").getPath();
		String path = null;
		if (resource.indexOf("/") == 0) {
			path = resource.substring(1);
		}
		return testSheetService.downloadBioinformaticAnalysis(path, entity);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadTecAnalysisData", method = RequestMethod.POST)
	public String downloadanalysisData(String sheetId) {
		TechnicalAnalySubmitModel sheet = tecAnalysisService.getSheetExportModel(sheetId);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/TEC_ANALYSIS.xlsx");
		String str = testSheetService.downloadAnalysisData(is, sheet);
		return str;
	}

	@ResponseBody
	@RequestMapping(value = "/downloadPrimerDesignData", method = RequestMethod.POST)
	public String downloadPrimerDesignData(String sheetId) {
		PrimerDesignSheetModel sheet = primerDesignService.getPrimerDesignSubmitModel(sheetId);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/sanger/PRIMER_DESIGN.xlsx");
		return testSheetService.downloadPrimerDesignData(is, sheet);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadPcrNgsPrimerDesignData", method = RequestMethod.POST)
	public String downloadPcrNgsPrimerDesignData(String sheetId) {
		PcrNgsPrimerDesignSheetModel sheet = pcrNgsPrimerDesignService.getPrimerDesignSubmitModel(sheetId);
		InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/pcrngs/PRIMER_DESIGN.xlsx");
		return testSheetService.downloadPcrNgsPrimerDesignData(is, sheet);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadFirstPCRData", method = RequestMethod.POST)
	public String downloadFirstPCRData(String sheetId) {
		FirstPCRSheetModel sheet = firstPCRService.getFirstPCRSubmitModel(sheetId);
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormater.format(new Date());
		File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
		return testSheetService.zipFiles(zipfile, sheet);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadFirstPCRSangerData", method = RequestMethod.POST)
	public String downloadFirstPCRSangerData(String sheetId) {
		FirstPCRSangerSheetModel sheet = firstPCRSangerService.getFirstPCRSubmitModel(sheetId);
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormater.format(new Date());
		File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
		return testSheetService.zipPcrOneSangerFiles(zipfile, sheet);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadSecondPCRData", method = RequestMethod.POST)
	public String downloadSecondPCRData(String sheetId) {
		SecondPCRSheetModel sheet = secondPCRService.getSecondPCRSubmitModel(sheetId);
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormater.format(new Date());
		File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
		return testSheetService.zipFilesPCR2(zipfile, sheet);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadSecondPCRSangerData", method = RequestMethod.POST)
	public String downloadSecondPCRSangerData(String sheetId) {
		SecondPCRSangerSheetModel sheet = secondPCRSangerService.getSecondPCRSubmitModel(sheetId);
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormater.format(new Date());
		File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
		return testSheetService.zipFilesPCRSanger2(zipfile, sheet);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadReportCreate", method = RequestMethod.POST)
	public String downloadReportCreate(Integer taskActivitiId) {
		TestingSheet entity = testSheetService.getTestSheet(taskActivitiId);
		ClassLoader classLoader = ConfigManage.class.getClassLoader();
		String resource = classLoader.getResource("taskTemplate/ANALYSIS_DATA.xlsx").getPath();
		String path = null;
		if (resource.indexOf("/") == 0) {
			path = resource.substring(1);
		}
		String str = testSheetService.downloadReportCreate(path, entity);
		return str;
	}

	// mlpa导出任务单
	@ResponseBody
	@RequestMapping(value = "/downloadMlpaData", method = RequestMethod.POST)
	public String downloadMlpaData(String sheetId) {
		MlpaTestSubmitModel sheet = mlpaService.getMlpaSubmitModel(sheetId);
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormater.format(new Date());
		File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
		return testSheetService.zipMlpaFiles(zipfile, sheet);
	}

	// 动态突变导出任务单
	@ResponseBody
	@RequestMapping(value = "/downloadDTData", method = RequestMethod.POST)
	public String downloadDTData(String sheetId) {
		DTSubmitModel sheet = dtService.getDTSubmitModel(sheetId);
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormater.format(new Date());
		File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
		return testSheetService.zipDTFiles(zipfile, sheet);
	}

	// PCR-NGS导出任务单
	@ResponseBody
	@RequestMapping(value = "/downloadPcrNgsData", method = RequestMethod.POST)
	public String downloadPcrNgsData(String sheetId) {
		PcrNgsTestSheetModel sheet = pcrNgsTestService.getPcrNgsTestSubmitModel(sheetId);
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormater.format(new Date());
		File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
		return testSheetService.zipPcrNgsFiles(zipfile, sheet);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	public void downloadFile(HttpServletResponse response, String formValue) {
		testSheetService.downloadData(response, formValue);
	}

	@ResponseBody
	@RequestMapping(value = "/uploadDNAQCAmount")
	public List<List<String>> uploadDNAQCAmount(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> str0 = new ArrayList<String>();
		List<String> str1 = testSheetService.uploadData(uploadData, 9, 5);
		List<String> str2 = testSheetService.uploadData(uploadData, 9, 6);
		List<String> str3 = testSheetService.uploadData(uploadData, 9, 7);
		List<String> str4 = testSheetService.uploadData(uploadData, 9, 8);
		List<String> str5 = testSheetService.uploadData(uploadData, 9, 9);
		List<Double> listD0 = new ArrayList<Double>();
		List<Double> listD1 = new ArrayList<Double>();
		List<String> str6 = new ArrayList<String>();
		for (String string : str1) {
			if (StringUtils.isNotEmpty(string)) {
				if (string.indexOf(".") > 0) {
					str0.add(string.substring(0, string.indexOf(".")));
					continue;
				}
				str0.add(string);
			}
		}
		for (String string : str2) {
			if (StringUtils.isNotEmpty(string)) {
				listD0.add(Double.valueOf(string));
			}
		}
		for (String string : str4) {
			if (StringUtils.isNotEmpty(string)) {
				listD1.add(Double.valueOf(string));
			}
		}
		for (int i = 0; i < str1.size(); i++) {
			if (StringUtils.isAnyNotEmpty(str1.get(i)) && Collections3.isNotEmpty(listD0) && Collections3.isNotEmpty(listD1)) {
				str6.add(testSheetService.QCValue(listD0.get(i), listD1.get(i), "DNAQC"));
			}
		}
		list.add(str0);
		list.add(str2);
		list.add(str3);
		list.add(str4);
		list.add(str5);
		list.add(str6);
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadCDNAQCAmount")
	public List<List<String>> uploadCDNAQCAmount(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> str0 = new ArrayList<String>();
		List<String> str1 = testSheetService.uploadData(uploadData, 9, 5);
		List<String> str2 = testSheetService.uploadData(uploadData, 9, 6);
		List<String> str3 = testSheetService.uploadData(uploadData, 9, 7);
		List<String> str4 = testSheetService.uploadData(uploadData, 9, 8);
		List<String> str5 = testSheetService.uploadData(uploadData, 9, 9);
		List<Double> listD0 = new ArrayList<Double>();
		List<Double> listD1 = new ArrayList<Double>();
		List<String> str6 = new ArrayList<String>();
		for (String string : str1) {
			if (StringUtils.isNotEmpty(string)) {
				if (string.indexOf(".") > 0) {
					str0.add(string.substring(0, string.indexOf(".")));
					continue;
				}
				str0.add(string);
			}
		}
		for (String string : str2) {
			if (StringUtils.isNotEmpty(string)) {
				listD0.add(Double.valueOf(string));
			}
		}
		for (String string : str4) {
			if (StringUtils.isNotEmpty(string)) {
				listD1.add(Double.valueOf(string));
			}
		}
		for (int i = 0; i < str1.size(); i++) {
			if (StringUtils.isAnyNotEmpty(str1.get(i)) && Collections3.isNotEmpty(listD0) && Collections3.isNotEmpty(listD1)) {
				str6.add(testSheetService.QCValue(listD0.get(i), listD1.get(i), "CDNAQC"));
			}
		}
		list.add(str0);
		list.add(str2);
		list.add(str3);
		list.add(str4);
		list.add(str5);
		list.add(str6);
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadWKCreateAmount", method = RequestMethod.POST)
	public List<List<String>> uploadWKCreateAmount(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(testSheetService.uploadData(uploadData, 8, 7));
		list.add(testSheetService.uploadData(uploadData, 8, 8));
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadLibQcAmount", method = RequestMethod.POST)
	public List<List<String>> uploadLibQcAmount(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> str0 = new ArrayList<String>();
		List<String> str1 = testSheetService.uploadData(uploadData, 9, 5);
		List<String> str2 = testSheetService.uploadData(uploadData, 9, 6);
		List<String> str3 = testSheetService.uploadData(uploadData, 9, 7);
		List<String> str4 = testSheetService.uploadData(uploadData, 9, 8);
		List<String> str5 = testSheetService.uploadData(uploadData, 9, 9);
		List<String> strFragmentLengthStart = testSheetService.uploadData(uploadData, 9, 10);
		List<String> strFragmentLengthEnd = testSheetService.uploadData(uploadData, 9, 11);
		List<Double> listD0 = new ArrayList<Double>();
		List<Double> listD1 = new ArrayList<Double>();
		List<String> str6 = new ArrayList<String>();
		for (String string : str1) {
			if (StringUtils.isNotEmpty(string)) {
				if (string.indexOf(".") > 0) {
					str0.add(string.substring(0, string.indexOf(".")));
					continue;
				}
				str0.add(string);
			}
		}
		for (String string : str2) {
			if (StringUtils.isNotEmpty(string)) {
				listD0.add(Double.valueOf(string));
			}
		}
		for (String string : str4) {
			if (StringUtils.isNotEmpty(string)) {
				listD1.add(Double.valueOf(string));
			}
		}
		for (int i = 0; i < str1.size(); i++) {
			if (StringUtils.isAnyNotEmpty(str1.get(i)) && Collections3.isNotEmpty(listD0) && Collections3.isNotEmpty(listD1)) {
				str6.add(testSheetService.QCValue(listD0.get(i), listD1.get(i), "LibQc"));
			}
		}
		list.add(str0);
		list.add(str2);
		list.add(str3);
		list.add(str4);
		list.add(str5);
		list.add(strFragmentLengthStart);
		list.add(strFragmentLengthEnd);
		list.add(str6);
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadLibCapAmount", method = RequestMethod.POST)
	public List<List<String>> uploadWKCatchAmount(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> code = testSheetService.uploadData(uploadData, 9, 0);
		List<String> conn = testSheetService.uploadData(uploadData, 9, 9);
		List<String> codeFilter = Lists.newArrayList();
		List<String> connFilter = Lists.newArrayList();
		for (String c : code) {
			if (StringUtils.isNotEmpty(c) && !codeFilter.contains(c)) {
				codeFilter.add(c);
			}
		}
		for (String c : conn) {
			if (StringUtils.isNotEmpty(c)) {
				connFilter.add(c);
			}
		}

		list.add(codeFilter);
		list.add(connFilter);
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadRQTctv", method = RequestMethod.POST)
	public List<List<String>> uploadPoolingCT(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> list = Lists.newArrayList();
		List<String> codeList = testSheetService.uploadData(uploadData, 2, 2);
		List<String> ctvList = testSheetService.uploadData(uploadData, 2, 4);
		list.add(codeList);
		list.add(ctvList);
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadJDDLAmount", method = RequestMethod.POST)
	public List<List<String>> uploadJDDLAmount(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(testSheetService.uploadData(uploadData, 8, 9));
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadOnTestAmount", method = RequestMethod.POST)
	public List<List<String>> uploadOnTestAmount(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(testSheetService.uploadData(uploadData, 7, 8));
		list.add(testSheetService.uploadData(uploadData, 7, 9));
		list.add(testSheetService.uploadData(uploadData, 7, 10));
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadTecAnalys", method = RequestMethod.POST)
	public List<List<String>> uploadTecAnalys(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> dataList = testSheetService.uploadDataByRow(uploadData, 1, 9);

		// 导入结果校验
		// 重复记录校验
		List<String> codeList = Lists.newArrayList();
		List<String> mutationGeneList = Lists.newArrayList();
		List<String> chromosomalLocationList = Lists.newArrayList();
		for (List<String> list : dataList) {
			// 必填字段验证(code默认不为空)
			if ("QPCR".equals(list.get(2)) && "验证".equals(list.get(0))) {
				if (StringUtils.isEmpty(list.get(61)) || StringUtils.isEmpty(list.get(62)) || StringUtils.isEmpty(list.get(63))) {
					list.add("QPCR必填项缺失");
				}
			} else {
				if (StringUtils.isEmpty(list.get(10)) || StringUtils.isEmpty(list.get(11))) {
					list.add("必填项缺失");
				}
			}

			if (Collections3.isNotEmpty(codeList)) {
				for (int codeIndex = 0; codeIndex < codeList.size(); codeIndex++) {
					if (codeList.get(codeIndex).equals(list.get(9))) {
						// MLPA或动态突变，单记录验证
						if ("MLPA".equals(list.get(2)) || "动态突变".equals(list.get(2))) {
							list.add("单条验证多条记录错误");
						}

						for (int i = 0; i < mutationGeneList.size(); i++) {
							if (mutationGeneList.get(codeIndex).equals(list.get(10))) {
								for (int j = 0; j < chromosomalLocationList.size(); j++) {
									if (chromosomalLocationList.get(codeIndex).equals(list.get(11))) {
										list.add("重复");
									}
								}
							}
						}
					}
				}
				codeList.add(list.get(9));
				mutationGeneList.add(list.get(10));
				chromosomalLocationList.add(list.get(11));
			} else {
				codeList.add(list.get(9));
				mutationGeneList.add(list.get(10));
				chromosomalLocationList.add(list.get(11));
			}
		}

		return dataList;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadPrimerDesign", method = RequestMethod.POST)
	public List<List<String>> uploadPrimerDesign(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> dataList = testSheetService.uploadDataByRow(uploadData, 7, 0);
		List<List<String>> checkList = Lists.newArrayList();
		for (List<String> list : dataList) {
			if (Collections3.isNotEmpty(checkList)) {
				if (!checkList.contains(list)) {
					checkList.add(list);
				}
			} else {
				checkList.add(list);
			}
		}
		TestingMethod testingMethod = testingTaskService.getTestingMethodBySemantic(TestingMethod.SANGER);
		for (List<String> list : checkList) {
			if (StringUtils.isEmpty(list.get(11))) {
				list.add(13, "必填项缺失");
			} else {
				if ("成功".equals(list.get(11))) {
					if (StringUtils.isEmpty(list.get(3)) || StringUtils.isEmpty(list.get(4)) || StringUtils.isEmpty(list.get(5))
							|| StringUtils.isEmpty(list.get(6)) || StringUtils.isEmpty(list.get(7)) || StringUtils.isEmpty(list.get(8))
							|| StringUtils.isEmpty(list.get(9)) || StringUtils.isEmpty(list.get(10))) {
						list.add(13, "必填项缺失");
					} else {
						CheckPrimerForDesignRequest checkRequest = new CheckPrimerForDesignRequest();
						checkRequest.setGene(list.get(1));
						checkRequest.setCodingExon(list.get(2));
						checkRequest.setChromosomeNumber(list.get(3));
						checkRequest.setForwardPrimerName(list.get(7));
						checkRequest.setReversePrimerName(list.get(9));
						checkRequest.setApplicationType(testingMethod.getId());
						if (isNum(list.get(5)) && isNum(list.get(6))) {
							checkRequest.setPcrStartPoint(Long.parseLong(list.get(5)));
							checkRequest.setPcrEndPoint(Long.parseLong(list.get(6)));
							String checkResult = primerService.checkPrimerForDesign(checkRequest);
							if (CheckPrimerForDesignRequest.PRIMER_DESIGN_CHECK_NO_F.equals(checkResult)) {
								list.add(13, "正向引物名已存在");
							}
							if (CheckPrimerForDesignRequest.PRIMER_DESIGN_CHECK_NO_R.equals(checkResult)) {
								list.add(13, "反向引物名已存在");
							}
							if (CheckPrimerForDesignRequest.PRIMER_DESIGN_CHECK_SAVE.equals(checkResult)) {
								list.add(13, "重复-数据库已存");
							}
						} else {
							list.add(13, "PCR起点或终点不是整数(不能含',')");
						}
					}
				} else if ("失败".equals(list.get(11))) {
					if (StringUtils.isEmpty(list.get(3)) || StringUtils.isEmpty(list.get(4))) {
						list.add(13, "必填项缺失");
					}
				}
			}
		}
		return dataList;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadFirstPCR", method = RequestMethod.POST)
	public List<List<String>> uploadFirstPCR(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> dataList = testSheetService.uploadDataByRow(uploadData, 1, 2);
		for (List<String> list : dataList) {
			if (StringUtils.isEmpty(list.get(3))) {
				list.add(6, "结果不能为空");
			} else {
				if (("成功".equals(list.get(3)) && !"通过".equals(list.get(4))) || ("失败".equals(list.get(3)) && "通过".equals(list.get(4)))) {
					list.add(6, "处理意见异常");
				}
			}
			if ("失败".equals(list.get(3)) && StringUtils.isEmpty(list.get(5))) {
				list.add(6, "备注为空");
			}
		}
		return dataList;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadPcrNgsTest", method = RequestMethod.POST)
	public List<List<String>> uploadPcrNgsTest(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<List<String>> dataList = testSheetService.uploadDataByRow(uploadData, 1, 2);
		for (List<String> list : dataList) {
			if (StringUtils.isEmpty(list.get(1))) {
				list.add(4, "结果不能为空");
			} else {
				if (("成功".equals(list.get(1)) && !"通过".equals(list.get(2))) || ("失败".equals(list.get(1)) && "通过".equals(list.get(2)))) {
					list.add(4, "处理意见异常");
				}
			}
			if ("失败".equals(list.get(1)) && StringUtils.isEmpty(list.get(3))) {
				list.add(4, "备注为空");
			}
		}
		return dataList;
	}

	public static String dateToStrLong(Date senddate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(senddate);
		return dateString;
	}

	public static boolean isNum(String str) {
		try {
			new BigDecimal(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
