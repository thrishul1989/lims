package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleTransferring;
import com.todaysoft.lims.utils.StringUtils;

public class SampleTransferringSearcher implements ISearcher<SampleTransferring>{
	private String code ;//'接收编号'
	private String remark;// '接收备注',
	private String  operatorId  ;  // '操作人ID',
	private String  operatorName;//      '操作人姓名',
	private Date  operateTime;//  '操作时间',
	private Integer pageNo;
	private Integer pageSize;
	public Integer getPageNo() {
		return pageNo;
	}


	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}


	public Integer getPageSize() {
		return pageSize;
	}


	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SampleTransferring s WHERE 1=1"); 
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
	}
	
	
	private void addFilter(StringBuffer hql, List<String> paramNames,List<Object> parameters) {
		if (StringUtils.isNotEmpty(code)){
            hql.append(" AND s.code = :code");
            paramNames.add("code");
            parameters.add(code);
        }
     
        hql.append(" order by s.operateTime desc");
	}

	@Override
	public Class<SampleTransferring> getEntityClass() {
		return SampleTransferring.class;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}
