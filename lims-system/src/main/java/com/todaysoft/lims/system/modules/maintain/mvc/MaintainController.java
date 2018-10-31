package com.todaysoft.lims.system.modules.maintain.mvc;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todaysoft.lims.system.modules.maintain.service.IMaintainService;

@Controller
@RequestMapping(value = "/maintain")
public class MaintainController {

	@Autowired
	private IMaintainService service;

	/**
	 * 修改产品的检测方法模板
	 * */
	@RequestMapping("/update_testingMethod_template")
	public void updateMethodTemplate() {
		service.updateMethodTemplate();

	}

	/**
	 * 删除开票流程中为0的数据
	 * */
	@RequestMapping("/deleteZeroDatas")
	public void deleteZeroDatas() {
		service.deleteZeroDatas();

	}

	/**
	 * 修改老订单走到生信就技术没有继续走到技术分析
	 * */
	@RequestMapping(value = "/update_schedule_notgoin_tech", method = RequestMethod.GET)
	public void updateScheduleNotgoinTech(String orderCodes) {
		service.updateScheduleNotgoinTech(orderCodes);
		System.out.println("订单重新生成生信分析完成");

	}

	/**
	 * 导出所有订单上机时间报告等信息
	 * */
	@RequestMapping(value = "/exportAllOrderInfomation", method = RequestMethod.GET)
	public void exportAllOrderInfomation() throws IOException {
		service.exportAllOrderInfomation();

	}

	/**
	 * 修改订单产品状态
	 * */
	@RequestMapping(value = "/updateOrderProduct", method = RequestMethod.GET)
	public void updateOrderProduct() throws IOException {
		service.updateOrderProduct();

	}

	/**
	 * 处理任务冗余应完成时间
	 */
	@RequestMapping("/updateTestingTaskPlanFinishDate")
	public void updateTestingTaskPlanFinishDate() {
		service.updateTestingTaskPlanFinishDate();

	}

	/**
	 * 处理MLPA验证图片遗漏存orderId等些信息
	 */
	@RequestMapping("/updateDataPicForMlpaVerify")
	public void updateDataPicForMlpaVerify() {
		service.updateDataPicForMlpaVerify();

	}

	/**
	 * 处理报告寄送已完成，产品状态未完成，订单状态未完成的单子
	 */
	@RequestMapping("/updateOrderStatusForSchedulePlanTask")
	public void updateOrderStatusForSchedulePlanTask() {
		service.updateOrderStatusForSchedulePlanTask();

	}

	/**
	 * ngs测序任务旧任务刷成新任务
	 * */

	@RequestMapping("/updateOldNgsSequecingTask")
	public void updateOldNgsSequecingTask() {
		service.updateOldNgsSequecingTask();

	}
	
	@RequestMapping("/updateNgsAndBioTask")
    public void updateNgsAndBioTask(String sequecingCode,Integer  tag) {
        service.updateNgsAndBioTask(sequecingCode,tag);

    }
}
