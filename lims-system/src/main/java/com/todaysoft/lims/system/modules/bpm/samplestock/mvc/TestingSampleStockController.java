package com.todaysoft.lims.system.modules.bpm.samplestock.mvc;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.*;
import com.todaysoft.lims.system.modules.bpm.samplestock.service.ISheetSampleStorageService;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestingTaskService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/testingSampleStorage")
public class TestingSampleStockController extends BaseController
{
    @Autowired
    private ISheetSampleStorageService sheetSampleStorageService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITestingTaskService testingTaskService;

    private static Logger log = LoggerFactory.getLogger(TestingSampleStockController.class);

    //实验样本出入库列表（1-出库 2-入库）
    public List<SampleStorageOutRecord> sample_list(TestingSheetSampleStorage searcher)
    {
        searcher.setStatus(0);//状态：0-未处理 1-已处理
        Pagination<SampleStorageOutRecord> pagination = sheetSampleStorageService.sample_list(searcher);
        return pagination.getRecords();
    }

    //样本出入库（完成样本出库及出库后生成的入库单）
    @Transactional
    @RequestMapping("/sampleOutOfStock.do")
    public void TestingSampleOutOfStock(TestingSheetSampleStorage searcher)
    {
        searcher.setStorageType("1");
        List<SampleStorageOutRecord> records = sample_list(searcher);
        if (Collections3.isNotEmpty(records))
        {
            for (SampleStorageOutRecord record: records )
            {
                log.info("testingSheetSample out storage id:" + record.getId());
                TestingSheetSampleStorage sheetSampleStorageOut = sheetSampleStorageService.getTestingSheetSampleStorage(record.getId());

                String userId ="9048b78e49ed4847b5c52fc0a004d1ad";
                UserDetailsModel userModel = userService.getUserByID(userId);
                SampleStockout stockout =
                        new SampleStockout().builder()
                                .operatorId(userId)
                                .operatorName(userModel.getArchive().getName())
                                .operateTime(new java.sql.Timestamp(System.currentTimeMillis()))
                                .build();

                stockout.setSheetId(sheetSampleStorageOut.getTestingSheet());
                String stockoutId = sheetSampleStorageService.createStockout(stockout);
                stockout.setId(stockoutId);
                Map fileterOut = new HashMap<>();
                for (TestingSheetTask sheeTask : sheetSampleStorageOut.getTestingSheet().getTestingSheetTaskList())
                {

                    if ("LIBRARY-CAP".equals(sheetSampleStorageOut.getTestingSheet().getSemantic()))
                    {// 文库捕获特殊处理

                        List<TestingCaptureSample> captureSampleList = sheetSampleStorageService.getTestingCaptureSampleBytaskId(sheeTask.getTestingTaskId());
                        for (TestingCaptureSample captureSample : captureSampleList)
                        {
                            // 去重
                            if (!fileterOut.containsKey(captureSample.getSample().getSampleCode()))
                            {
                                // 获取样本位置
                                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(captureSample.getSample().getSampleCode());

                                SampleStockoutDetails details =
                                        new SampleStockoutDetails().builder()
                                                .sampleLocation(storage.getLocationCode())
                                                .sampleId(new TestingSample().builder().id(captureSample.getSample().getId()).build())
                                                .record(stockout)
                                                .build();
                                sheetSampleStorageService.createStockoutDetail(details);
                                // 样本状态改为出库
                                storage.setStatus(2);
                                sheetSampleStorageService.updateTestingSampleStorage(storage);
                                fileterOut.put(captureSample.getSample().getSampleCode(), "");
                            }
                        }
                    }
                    else
                    {
                        TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                        // 获取样本位置
                        TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                        if (StringUtils.isNotEmpty(storage))
                        {
                            SampleStockoutDetails details =
                                    new SampleStockoutDetails().builder()
                                            .sampleLocation(storage.getLocationCode())
                                            .sampleId(new TestingSample().builder().id(testingTask.getInputSample().getId()).build())
                                            .record(stockout)
                                            .build();
                            sheetSampleStorageService.createStockoutDetail(details);

                            // 样本状态改为出库
                            storage.setStatus(2);
                            sheetSampleStorageService.updateTestingSampleStorage(storage);
                        }
                    }

                }
                sheetSampleStorageOut.setStatus(1);
                sheetSampleStorageOut.setStorageRecordId(stockoutId);
                sheetSampleStorageService.updateTestingSheetSampleStorage(sheetSampleStorageOut);
                log.info("testingSample out storage is success ");
                // 生成入库单,DNA质检不需要生成入库单，实验完成会自动生成入库单
                if (!"DNA-QC".equals(sheetSampleStorageOut.getTestingSheet().getSemantic()))
                {
                    TestingSheetSampleStorage sheetSampleStorageIn = sheetSampleStorageService.createAndReturnStorageIn(sheetSampleStorageOut.getTestingSheet());

                    if (null!= sheetSampleStorageIn)
                    {
                        log.info("testingSample in storage id:" + sheetSampleStorageIn.getId());
                        SampleStockin stockin =
                                new SampleStockin().builder()
                                        .operatorId(userId)
                                        .operatorName(userModel.getArchive().getName())
                                        .operateTime(new java.sql.Timestamp(System.currentTimeMillis()))
                                        .build();
                        String stockInId = sheetSampleStorageService.createStockin(stockin);
                        stockin.setId(stockInId);
                        Map fileterIN = new HashMap<>();
                        for (TestingSheetTask sheeTask : sheetSampleStorageIn.getTestingSheet().getTestingSheetTaskList())
                        {

                            if ("LIBRARY-CAP".equals(sheetSampleStorageIn.getTestingSheet().getSemantic()))
                            {// 文库捕获特殊处理

                                List<TestingCaptureSample> captureSampleList = sheetSampleStorageService.getTestingCaptureSampleBytaskId(sheeTask.getTestingTaskId());
                                for (TestingCaptureSample captureSample : captureSampleList)
                                {
                                    // 去重
                                    if (!fileterIN.containsKey(captureSample.getSample().getSampleCode()))
                                    {
                                        TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(captureSample.getSample().getSampleCode());
                                        if (null != storage)
                                        {
                                            SampleStockinDetails details =
                                                    new SampleStockinDetails().builder()
                                                            .sampleLocation(storage.getLocationCode())
                                                            .sampleId(new TestingSample().builder().id(captureSample.getSample().getId()).build())
                                                            .record(stockin)
                                                            .build();
                                            sheetSampleStorageService.createStockinDetail(details);
                                            // 样本状态改为入库
                                            storage.setStatus(1);
                                            sheetSampleStorageService.updateTestingSampleStorage(storage);
                                        }
                                        fileterIN.put(captureSample.getSample().getSampleCode(), "");
                                    }

                                }

                            }
                            else
                            {
                                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                                // 获取样本位置
                                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                                if (null != storage)
                                {
                                    SampleStockinDetails details =
                                            new SampleStockinDetails().builder()
                                                    .sampleLocation(storage.getLocationCode())
                                                    .sampleId(new TestingSample().builder().id(testingTask.getInputSample().getId()).build())
                                                    .record(stockin)
                                                    .build();
                                    sheetSampleStorageService.createStockinDetail(details);
                                    // 样本状态改为入库
                                    storage.setStatus(1);
                                    sheetSampleStorageService.updateTestingSampleStorage(storage);
                                }
                            }

                        }

                        sheetSampleStorageIn.setStatus(1);
                        sheetSampleStorageIn.setStorageRecordId(stockInId);
                        sheetSampleStorageService.updateTestingSheetSampleStorage(sheetSampleStorageIn);
                        log.info("testingSample in storage is success ");
                    }
                }
            }
        }

    }

    //样本入库
    @Transactional
    @RequestMapping("/sampleInStock.do")
    public void TestingSampleInStock(TestingSheetSampleStorage searcher)
    {
        searcher.setStorageType("2");
        List<SampleStorageOutRecord> records = sample_list(searcher);
        if (Collections3.isNotEmpty(records))
        {
            for (SampleStorageOutRecord record:records)
            {
                TestingSheetSampleStorage sheetSampleStorage = sheetSampleStorageService.getTestingSheetSampleStorage(record.getId());
                log.info("testingSample in storage id:" + record.getId());
                String userId ="9048b78e49ed4847b5c52fc0a004d1ad";
                UserDetailsModel userModel = userService.getUserByID(userId);
                SampleStockin stockin =
                        new SampleStockin().builder()
                                .operatorId(userId)
                                .operatorName(userModel.getArchive().getName())
                                .operateTime(new java.sql.Timestamp(System.currentTimeMillis()))
                                .build();
                String stockId = sheetSampleStorageService.createStockin(stockin);
                stockin.setId(stockId);
                Map fileter = new HashMap<>();
                for (TestingSheetTask sheeTask : sheetSampleStorage.getTestingSheet().getTestingSheetTaskList())
                {

                    if ("LIBRARY-CAP".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
                    {// 文库捕获特殊处理

                        List<TestingCaptureSample> captureSampleList = sheetSampleStorageService.getTestingCaptureSampleBytaskId(sheeTask.getTestingTaskId());
                        for (TestingCaptureSample captureSample : captureSampleList)
                        {
                            // 去重
                            if (!fileter.containsKey(captureSample.getSample().getSampleCode()))
                            {
                                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(captureSample.getSample().getSampleCode());
                                if (null != storage)
                                {
                                    SampleStockinDetails details =
                                            new SampleStockinDetails().builder()
                                                    .sampleLocation(storage.getLocationCode())
                                                    .sampleId(new TestingSample().builder().id(captureSample.getSample().getId()).build())
                                                    .record(stockin)
                                                    .build();
                                    sheetSampleStorageService.createStockinDetail(details);
                                    // 样本状态改为入库
                                    storage.setStatus(1);
                                    sheetSampleStorageService.updateTestingSampleStorage(storage);
                                }
                                fileter.put(captureSample.getSample().getSampleCode(), "");
                            }

                        }

                    }
                    else
                    {
                        TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                        // 获取样本位置
                        TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                        if (null != storage)
                        {
                            SampleStockinDetails details =
                                    new SampleStockinDetails().builder()
                                            .sampleLocation(storage.getLocationCode())
                                            .sampleId(new TestingSample().builder().id(testingTask.getInputSample().getId()).build())
                                            .record(stockin)
                                            .build();
                            sheetSampleStorageService.createStockinDetail(details);
                            // 样本状态改为入库
                            storage.setStatus(1);
                            sheetSampleStorageService.updateTestingSampleStorage(storage);
                        }
                    }

                }

                sheetSampleStorage.setStatus(1);
                sheetSampleStorage.setStorageRecordId(stockId);
                sheetSampleStorageService.updateTestingSheetSampleStorage(sheetSampleStorage);
                log.info("testingSample in storage is success");
            }
        }


    }
}
