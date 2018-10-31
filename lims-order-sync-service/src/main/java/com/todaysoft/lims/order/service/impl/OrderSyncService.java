package com.todaysoft.lims.order.service.impl;

import com.google.common.collect.Lists;
import com.todaysoft.lims.order.model.*;
import com.todaysoft.lims.order.mybatis.mapper.BaseOrderModelMapper;
import com.todaysoft.lims.order.mybatis.mapper.entity.*;
import com.todaysoft.lims.order.service.IOrderSyncService;
import com.todaysoft.lims.order.util.JsonUtils;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Service
public class OrderSyncService implements IOrderSyncService {
    private static Logger log = LoggerFactory.getLogger(OrderSyncService.class);

    @Autowired
    private BaseOrderModelMapper orderModelMapper;

    @Override
    public List<OrderDetail> patchSearch(OrderSyncSearchModel request) {
        List<OrderDetail> orderDetailList = Lists.newArrayList();
        List<BaseOrderModel> orderByModelList = orderModelMapper.getOrderByModel(request);
        if (Collections3.isNotEmpty(orderByModelList)) {
            long startTime = System.currentTimeMillis(); //获取开始时间
            OrderDetail orderDetail = null;
            for (BaseOrderModel orderModel : orderByModelList) {
                orderDetail = new OrderDetail();
                wrapOrderDetailByOderList(orderModel, orderDetail);
                orderDetailList.add(orderDetail);
            }
            long endTime = System.currentTimeMillis(); //获取结束时间
            log.info("Search result size is: " + orderByModelList.size() + ".----wrap completed---程序运行时间：" + (endTime - startTime) / 1000 + " s.");
        } else {
            log.warn("Search Result Is Null.");
        }
        return orderDetailList;
    }

    @Override
    public OrderDetail searchByOrderCode(OrderSyncSearchModel request) {
        OrderDetail orderDetail = null;
        List<BaseOrderModel> orderByModelList = orderModelMapper.getOrderByModel(request);
        if (Collections3.isNotEmpty(orderByModelList)) {
            orderDetail = new OrderDetail();
            BaseOrderModel orderModel = Collections3.getFirst(orderByModelList);
            wrapOrderDetailByOderList(orderModel, orderDetail);
        } else {
            log.warn("The order not find.order code is:" + request.getOrderFormCode());
        }

        return orderDetail;
    }

    private void wrapOrderDetailByOderList(BaseOrderModel orderModel, OrderDetail orderDetail) {

        BeanUtils.copyProperties(orderModel, orderDetail);
        String orderId = orderModel.getOrderFormKey();
        //查询并封装疾病基因表型 封装病历图 家族图
        searchGeneAndWrap(orderModel, orderDetail);
        //查询封装主样本
        List<OrderSampleModel> primarySamples = orderModelMapper.getOrderPrimarySampleModelByOrderId(orderId);
        wrapSampleList(primarySamples, orderDetail);
        //查询封装附属样本
        List<OrderSampleModel> subSamples = orderModelMapper.getOrderSubSampleModelByOrderId(orderId);
        wrapSampleList(subSamples, orderDetail);
        //封装订单产品
        List<OrderProductModel> productList = orderModelMapper.getOrderProductModelByOrderId(orderId);
        wrapProductList(productList, orderDetail);
        //封装检测数据
        List<TestingDataModel> dataList = orderModelMapper.getTestingDataModelByOrderId(orderId);
        wrapTestingData(dataList, orderDetail);
    }

    private void wrapTestingData(List<TestingDataModel> dataList, OrderDetail orderDetail) {

        List<ProductSample> productSamples = Lists.newArrayList();

        List<ValidateSample> validateSamples = Lists.newArrayList();

        OrderDatas orderDatas = null;
        if (Collections3.isNotEmpty(dataList)) {
            ProductSample productSample = null;
            ValidateSample validateSample = null;
            orderDetail.setStartTime(Collections3.getFirst(dataList).getStartTime());//订单信息 启动时间
            orderDatas = new OrderDatas();
            orderDatas.setProductSamples(productSamples);
            orderDatas.setValidateSamples(validateSamples);
            for (TestingDataModel data : dataList) {
                String verifyKey = data.getVerifyKey();
                String scheduleId = data.getDataKey();
                List<ScheduleTaskModel> taskList = orderModelMapper.getTaskListBySchedule(scheduleId);//TASK表的任务
                if (StringUtils.isEmpty(verifyKey))//检测流程
                {
                    productSample = new ProductSample();
                    wrapTaskInfo(taskList, data);
                    // 新的上机实验查询 sequencerType laneCode runCode
                    List<ScheduleTaskModel> ngsList = orderModelMapper.getNgsTaskListBySchedule(scheduleId);
                    ScheduleTaskModel model = Collections3.getFirst(ngsList);
                    if (null != model) {
                        data.setSequencerType(model.getSequencerType());
                        data.setRunCode(model.getRunCode());
                        data.setLaneCode(model.getLaneCode());
                    }
                    BeanUtils.copyProperties(data, productSample);
                    if ("2".equals(data.getProccessState())) {
                        productSample.setDetectStatus("已取消");
                    }
                    productSamples.add(productSample);
                } else {//验证流程
                    validateSample = new ValidateSample();
                    BeanUtils.copyProperties(data, validateSample);
                    ScheduleTaskModel taskModel = Collections3.getFirst(taskList);
                    if ("2".equals(data.getProccessState())) {
                        validateSample.setValidateStatus("已取消");
                    } else {
                        validateSample.setValidateStatus(data.getDetectStatus());
                    }
                    validateSample.setUpdateTime(Collections3.getFirst(taskList).getUpdateTime());
                    // 根据流程查询验证数据
                    List<ScheduleTaskModel> recordList = Lists.newArrayList();
                    if (TaskSemantic.SANGER_METHOD_NAME.equals(data.getMethodName())) {
                        recordList = orderModelMapper.getSiteAndGeneByScheduleAndSangerOrPcrNgs(scheduleId);

                    } else if (TaskSemantic.PCRNGS_METHOD_NAME.equals(data.getMethodName())) {
                        recordList = orderModelMapper.getSiteAndGeneByScheduleAndSangerOrPcrNgs(scheduleId);
                        //PCRNGS有个测序编号 通过新的技术分析任务查询
                        ScheduleTaskModel model = Collections3.getFirst(orderModelMapper.getNgsTaskListBySchedule(scheduleId));
                        if (null != model) {
                            validateSample.setSequencingCode(model.getSequencingCode());
                        }

                    } else if (TaskSemantic.MLPA_METHOD_NAME.equals(data.getMethodName())) {
                        recordList = orderModelMapper.getSiteAndGeneByScheduleAndMlpa(scheduleId);
                    } else if (TaskSemantic.QPCR_METHOD_NAME.equals(data.getMethodName())) {
                        recordList = orderModelMapper.getSiteAndGeneByScheduleAndQpcr(scheduleId);
                    }
                    if (Collections3.isNotEmpty(recordList)) {
                        ScheduleTaskModel analyRecord = Collections3.getFirst(recordList);
                        validateSample.setDataCode(analyRecord.getDataCode());
                        validateSample.setSite(analyRecord.getSite());
                        validateSample.setSymbol(analyRecord.getSymbol());
                    }
                    validateSamples.add(validateSample);
                }
            }
        }
        orderDetail.setOrderDatas(orderDatas);
    }

    private void wrapTaskInfo(List<ScheduleTaskModel> taskList, TestingDataModel data) {
        if (Collections3.isNotEmpty(taskList)) {
            List<String> probeList = Lists.newArrayList();
            data.setUpdateTime(Collections3.getFirst(taskList).getUpdateTime());
            data.setCoordinates(Collections3.getFirst(taskList).getCoordinate());
            for (ScheduleTaskModel taskModel : taskList) {
                String attribute = taskModel.getAttributes();
                if (TaskSemantic.DNA_QC.equals(taskModel.getSemantic())) {
                    data.setDataCode(taskModel.getDataCode());
                }
                if (TaskSemantic.LIBRARY_QC.equals(taskModel.getSemantic())) {
                    data.setLibraryCode(taskModel.getSampleCode());
                    //设置接头
                    if (StringUtils.isNotEmpty(attribute)) {
                        Map map = JsonUtils.fromJson(attribute, Map.class);
                        if (null != map) {
                            data.setJointNumber(null == map.get("index") ? "" : map.get("index").toString());
                        }
                    }
                }
                if (TaskSemantic.TECHNICAL_ANALY.equals(taskModel.getSemantic())) {
                    data.setSequencingCode(taskModel.getLaneCode());
                    data.setPoolingCode(taskModel.getLaneCode());//测序编号 上机号 混样好都是同一个了
                }
                if (TaskSemantic.RQT.equals(taskModel.getSemantic())) {
                    //取探针 可能多个
                    Map map = JsonUtils.fromJson(attribute, Map.class);
                    if (null != map) {
                        Object probeName = map.get("probeName");
                        if (null != probeName && !probeList.contains(probeName.toString())) {
                            probeList.add(probeName.toString());
                        }
                    }
                }
                if (TaskSemantic.QT.equals(taskModel.getSemantic()) || TaskSemantic.POOLING.equals(taskModel.getSemantic())) {
                    if (StringUtils.isNotEmpty(taskModel.getLaneCode()))//混样编号
                    {
                        data.setSequencingCode(taskModel.getLaneCode());
                        data.setPoolingCode(taskModel.getLaneCode());
                    }
                }
            }
            data.setProbe(Collections3.convertToString(probeList, ","));
        }
    }

    private void wrapProductList(List<OrderProductModel> productList, OrderDetail orderDetail) {
        List<OrderProductDetail> orderProducts = Lists.newArrayList();
        if (Collections3.isNotEmpty(productList)) {
            OrderProductDetail orderProductDetail = null;
            for (OrderProductModel product : productList) {
                orderProductDetail = new OrderProductDetail();
                BeanUtils.copyProperties(product, orderProductDetail);
                if (StringUtils.isNotEmpty(orderProductDetail.getProductStatus())) {
                    String orderStatus = "";
                    switch (orderProductDetail.getProductStatus()) {
                        case "0":
                            orderStatus = "待检测";
                            break;
                        case "1":
                            orderStatus = "待数据分析";
                            break;
                        case "2":
                            orderStatus = "待验证";
                            break;
                        case "3":
                            orderStatus = "待出报告";
                            break;
                        case "4":
                            orderStatus = "待审核报告";
                            break;
                        case "5":
                            orderStatus = "待寄送报告";
                            break;
                        case "6":
                            orderStatus = "已完成";
                            break;
                        case "7":
                            orderStatus = "不寄送邮件";
                            break;
                        case "8":
                            orderStatus = "检测取消";
                            break;
                        default:
                            orderStatus = "未知";
                            break;
                    }
                    orderProductDetail.setProductStatus(orderStatus);
                }
                String productId = product.getProductKey();
                List<ProductMethodModel> productMethodList = orderModelMapper.getProductMethodModelByProductId(productId);
                List<MethodDetail> methods = Lists.newArrayList();
                if (Collections3.isNotEmpty(productMethodList)) {
                    MethodDetail methodDetail = null;
                    for (ProductMethodModel method : productMethodList) {
                        methodDetail = new MethodDetail();
                        BeanUtils.copyProperties(method, methodDetail);
                        methods.add(methodDetail);
                    }
                }
                orderProductDetail.setMethods(methods);
            }
            orderProducts.add(orderProductDetail);
        }
        orderDetail.setOrderProducts(orderProducts);
    }

    private void wrapSampleList(List<OrderSampleModel> primarySamples, OrderDetail orderDetail) {
        List<OrderSampleDetail> samples = orderDetail.getSamples();
        if (Collections3.isNotEmpty(primarySamples)) {
            OrderSampleDetail sampleDetail = null;
            for (OrderSampleModel osm : primarySamples) {
                sampleDetail = new OrderSampleDetail();
                BeanUtils.copyProperties(osm, sampleDetail);
                //处理样本状态值
                String sampleStatus = sampleDetail.getSampleStatus();
                if (StringUtils.isNotEmpty(sampleStatus)) {
                    String status = "";
                    switch (sampleStatus) {
                        case "0":
                            status = "待送样";
                            break;
                        case "1":
                            status = "送样中";
                            break;
                        case "2":
                            status = "已接收样本";
                            break;
                        case "3":
                            status = "异常样本";
                            break;
                        case "4":
                            status = "已返样样本";
                            break;
                        default:
                            status = "未知";
                            break;
                    }
                    sampleDetail.setSampleStatus(status);
                    samples.add(sampleDetail);
                }
                String qcStatus = sampleDetail.getQualityStatus();
                if (StringUtils.isNotEmpty(qcStatus)) {
                    String status = "";
                    if ("0".equals(qcStatus)) {
                        status = "不合格";
                    } else if ("1".equals(qcStatus)) {
                        status = "合格";
                    }
                    sampleDetail.setQualityStatus(status);
                }
            }
            orderDetail.setSamples(samples);
        }
    }

    private void searchGeneAndWrap(BaseOrderModel orderModel, OrderDetail orderDetail) {
        String orderId = orderDetail.getOrderFormKey();
        // 订单状态 支付状态
        if (StringUtils.isNotEmpty(orderModel.getOrderFormStatus())) {
            String orderStatus = "";
            switch (orderModel.getOrderFormStatus()) {
                case "-1":
                    orderStatus = "待完善";
                    break;
                case "1":
                    orderStatus = "待检测下放";
                    break;
                case "2":
                    orderStatus = "检测中";
                    break;
                case "3":
                    orderStatus = "已暂停";
                    break;
                case "4":
                    orderStatus = "已取消";
                    break;
                case "5":
                    orderStatus = "已完成";
                    break;
                default:
                    orderStatus = "未知";
                    break;
            }
            orderDetail.setOrderFormStatus(orderStatus);
        }
        if (StringUtils.isNotEmpty(orderModel.getPayStatus())) {
            String payStatus = "";
            if ("0".equals(orderModel.getPayStatus())) {
                payStatus = "待付款";
            } else if ("1".equals(orderModel.getPayStatus())) {
                payStatus = "待付款确认";
            } else if ("2".equals(orderModel.getPayStatus())) {
                payStatus = "已付款";
            } else {
                payStatus = "未知";
            }
            orderDetail.setPayStatus(payStatus);
        }

        if (null != orderModel.getUrgent()) {
            String urgent = "";
            if (0 == orderModel.getUrgent().intValue()) {
                urgent = "不加急";
            } else if (1 == orderModel.getUrgent().intValue()) {
                urgent = "加急";
            }
            orderDetail.setUrgent(urgent);
        }

        //查询疾病
        List<OrderExamineeModel> diseaseList = orderModelMapper.getOrderExamineeDiseaseByOrderId(orderId);
        if (Collections3.isNotEmpty(diseaseList)) {
            orderDetail.setDiseaseOmimIds(Collections3.extractToString(diseaseList, "engName", ","));
            orderDetail.setDiseaseNames(Collections3.extractToString(diseaseList, "chineseName", "|"));
        }

        List<OrderExamineeModel> geneList = orderModelMapper.getOrderExamineeGeneByOrderId(orderId);
        if (Collections3.isNotEmpty(geneList)) {
            orderDetail.setGeneOmimIds(Collections3.extractToString(geneList, "engName", ","));
            orderDetail.setGeneSymbols(Collections3.extractToString(geneList, "chineseName", "|"));
        }
        List<OrderExamineeModel> phenotypeList = orderModelMapper.getOrderExamineePhenotypeByOrderId(orderId);
        if (Collections3.isNotEmpty(phenotypeList)) {
            orderDetail.setPhenotypeHpos(Collections3.extractToString(phenotypeList, "engName", ","));
            orderDetail.setPhenotypeNames(Collections3.extractToString(phenotypeList, "chineseName", "|"));
        }
        orderDetail.setCasePics(getOrderPicUrlListByString(orderModel.getRecordFigures()));
        orderDetail.setFamilyPic(getOrderPicUrlListByString(orderModel.getFamilyFigures()));
    }

    private List<OrderPicUrl> getOrderPicUrlListByString(String figure) {
        List<OrderPicUrl> result = Lists.newArrayList();
        if (StringUtils.isNotEmpty(figure)) {
            String casePicArr[] = figure.split(",");
            OrderPicUrl temp = null;
            if (null != casePicArr && casePicArr.length > 0) {
                for (String url : casePicArr) {
                    temp = new OrderPicUrl();
                    temp.setHttp(url);
                    result.add(temp);
                }
            }
        }
        return result;
    }

    private void test() {
        final int count = 10; // 计数次数
        final CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // do anything
                        System.out.println("线程" + Thread.currentThread().getId());
                    } catch (Throwable e) {
                        // whatever
                    } finally {
                        // 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
                        latch.countDown();
                    }
                }
            }).start();
        }
        try {
            // 10个线程countDown()都执行之后才会释放当前线程,程序才能继续往后执行
            latch.await(3, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            // whatever
        }
        System.out.println("Finish");
    }
}
