package com.todaysoft.lims.reagent.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.Equipment;
import com.todaysoft.lims.reagent.model.request.EquipmentCreateRequest;
import com.todaysoft.lims.reagent.model.request.EquipmentModifyRequest;
import com.todaysoft.lims.reagent.model.request.EquipmentPagingRequest;


public interface IEquipmentService {
	Pagination<Equipment> paging(EquipmentPagingRequest request);

	Equipment getEquipment(String id);

	String create(EquipmentCreateRequest request);

	void modify(EquipmentModifyRequest request);

	void delete(String id);

	Boolean checkName(EquipmentCreateRequest request);

	Boolean checkEquipmentNo(EquipmentCreateRequest request);

}
