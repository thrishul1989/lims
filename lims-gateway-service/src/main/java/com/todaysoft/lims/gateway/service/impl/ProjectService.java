package com.todaysoft.lims.gateway.service.impl;

import com.todaysoft.lims.gateway.model.*;
import com.todaysoft.lims.gateway.model.request.ProjectCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProjectListRequest;
import com.todaysoft.lims.gateway.model.request.ProjectModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProjectPagingRequest;
import com.todaysoft.lims.gateway.service.IProjectService;
import com.todaysoft.lims.gateway.service.adapter.ProjectAdapter;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.stream.Collectors;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {

	@Autowired
	private ProjectAdapter projectAdapter;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductService productService;
	
	@Override
	public Pagination<Project> paging(ProjectPagingRequest request) {
		Pagination<Project> projectPage = projectAdapter.paging(request);
		List<Project> paging = new ArrayList<Project>();
		List<Integer> userIds = new ArrayList<Integer>();
		List<Integer> customerIds = new ArrayList<Integer>();
		
		if(projectPage.getRecords().size() > 0){
			for(Project entity : projectPage.getRecords()){
				getUserId(userIds, entity);
				getCustomerId(customerIds, entity);
			}
		}
		
		if(projectPage.getRecords().size() > 0){
			List<UserDetailsModel> allUsers = userService.getContactUsers(userIds);
			List<Customer> allCustomers = customerService.getCustomers(customerIds);
			for(Project entity : projectPage.getRecords()){
				if(null != allUsers){
					for(UserDetailsModel user : allUsers){
						setLeaders(entity, user);
					}
				}
				if(null != allCustomers){
					for(Customer customer : allCustomers){
						setCustomers(entity,customer);
					}
				}
				paging.add(entity);
			}
		}
		projectPage.setRecords(paging);
		return projectPage;
	}

	private void setCustomers(Project entity, Customer customer) {
		if(entity.getCustomerId() == customer.getId()){
			entity.setCustomer(customer);
		}	
	}

	@Override
	public List<Project> list(ProjectListRequest request) {
		List<Project> projectList = projectAdapter.list(request);
		List<Project> paging = new ArrayList<Project>();
		List<Integer> userIds = new ArrayList<Integer>();
		List<Integer> customerIds = new ArrayList<Integer>();
		
		if(projectList.size() > 0){
			for(Project entity : projectList){
				getUserId(userIds, entity);
				getCustomerId(customerIds, entity);
			}
		}
		
		if(projectList.size() > 0){
			List<UserDetailsModel> allUsers = userService.getContactUsers(userIds);
			List<Customer> allCustomers = customerService.getCustomers(customerIds);
			for(Project entity : projectList){
				if(null != allUsers){
					for(UserDetailsModel user : allUsers){
						setLeaders(entity, user);
					}
				}
				if(null != allCustomers){
					for(Customer customer : allCustomers){
						setCustomers(entity,customer);
					}
				}
				/*List<Product> productList = productService.getContactProducts(getProductList(entity.getProductArray()));
				entity.setProductList(productList);*/
				paging.add(entity);
			}
		}
		projectList.clear();
		projectList.addAll(paging);
		return projectList;
	}

	@Override
	public Project get(Integer id) {
		Project entity = projectAdapter.get(id);
		List<Integer> userIds = new ArrayList<Integer>();
		List<Integer> customerIds = new ArrayList<Integer>();
		getUserId(userIds, entity);
		getCustomerId(customerIds, entity);
		List<UserDetailsModel> allUsers = userService.getContactUsers(userIds);
		List<Customer> allCustomers = customerService.getCustomers(customerIds);
		if(null != allUsers){
			for(UserDetailsModel user : allUsers){
				setLeaders(entity, user);
			}
		}
		if(null != allCustomers){
			for(Customer customer : allCustomers){
				setCustomers(entity,customer);
			}
		}
		List<Product> productList = productService.getContactProducts(getProductList(entity.getProductArray()));
		entity.setProductList(productList);
		return entity;
	}

	@Override
	public Integer create(ProjectCreateRequest request) {
		if(null != request.getProjectLeader()){
			request.setProjectLeaderId(request.getProjectLeader().getId());
		}
		if(null != request.getTechnicalLeader()){
			request.setTechnicalLeaderId(request.getTechnicalLeader().getId());
		}
		if(null != request.getExperimentLeader()){
			request.setExperimentLeaderId(request.getExperimentLeader().getId());
		}
		if(null != request.getCustomer()){
			request.setCustomerId(request.getCustomer().getId());
		}
		//request.setProductArray(getProductArray(request.getProductList()));
		return projectAdapter.create(request);
	}

	@Override
	public void modify(ProjectModifyRequest request) {
		if(null != request.getProjectLeader()){
			request.setProjectLeaderId(request.getProjectLeader().getId());
		}
		if(null != request.getTechnicalLeader()){
			request.setTechnicalLeaderId(request.getTechnicalLeader().getId());
		}
		if(null != request.getExperimentLeader()){
			request.setExperimentLeaderId(request.getExperimentLeader().getId());
		}
		if(null != request.getCustomer()){
			request.setCustomerId(request.getCustomer().getId());
		}
		//request.setProductArray(getProductArray(request.getProductList()));
		projectAdapter.modify(request);
	}
	
	@Override
	public void modifyFileName(ProjectModifyRequest request) {
		projectAdapter.modifyFileName(request);
	}
	

	@Override
	public void delete(Integer id) {
		projectAdapter.delete(id);
	}
	
	@Override
	public boolean validate(Project request) {
		return projectAdapter.validate(request);
	}

	//获取用户的id集合
	public void getUserId(List<Integer> userIds , Project entity){
		if(null != entity.getProjectLeaderId() && !userIds.contains(entity.getProjectLeaderId())){
			userIds.add(entity.getProjectLeaderId());
		}
		if(null != entity.getTechnicalLeaderId() && !userIds.contains(entity.getTechnicalLeaderId())){
			userIds.add(entity.getTechnicalLeaderId());
		}
		if(null != entity.getExperimentLeaderId() && !userIds.contains(entity.getExperimentLeaderId())){
			userIds.add(entity.getExperimentLeaderId());
		}
	}
	
	//project服务传过来的id转换成对应的User对象
	public void setLeaders(Project entity , UserDetailsModel user){
		if(entity.getProjectLeaderId() == user.getId()){
			entity.setProjectLeader(user);
		}
		if(entity.getTechnicalLeaderId() == user.getId()){
			entity.setTechnicalLeader(user);
		}
		if(entity.getExperimentLeaderId() == user.getId()){
			entity.setExperimentLeader(user);
		}
	}
	
	//获取客户的id集合
	public void getCustomerId(List<Integer> customerIds , Project entity){
		if(null != entity.getCustomerId() && !customerIds.contains(entity.getCustomerId())){
			customerIds.add(entity.getCustomerId());
		}
	}
	
	//project传来的产品id数组，保存成list
	public List<Integer> getProductList(String array){
		if(StringUtils.isNotEmpty(array)){
			return Arrays.stream(array.split(",")).map(Integer::parseInt).collect(Collectors.toList());
		}
		return null;
	}
	
	//system传来的产品list，保存成String
	public String getProductArray(List<Product> productList){
		if(Collections3.isNotEmpty(productList)){
			return productList.stream().map(Product::getId).map(String::valueOf).collect(Collectors.joining(","));
		}
		return null;
	}

}
