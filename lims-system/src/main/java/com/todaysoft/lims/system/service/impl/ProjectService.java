package com.todaysoft.lims.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Project;
import com.todaysoft.lims.system.service.IProjectService;
import com.todaysoft.lims.system.util.ConfigManage;

@Service
public class ProjectService extends RestService implements IProjectService{

	@Override
	public Pagination<Project> paging(Project searcher, int pageNo, int pageSize) {
		Project request = new Project();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/project/paging");
        ResponseEntity<Pagination<Project>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Project>(request), new ParameterizedTypeReference<Pagination<Project>>()
            {
            });
        return exchange.getBody();
	}

	@Override
	public void modify(Project request) {

        String url = GatewayService.getServiceUrl("/project/modify");
        template.postForObject(url, request, String.class);
		
	}
	
	

	public void modifyFileName(Project request) {
        String url = GatewayService.getServiceUrl("/project/modifyFileName");
        template.postForObject(url, request, String.class);
	}

	@Override
	public Project getProject(Integer id) {
		// TODO Auto-generated method stub
		   String url = GatewayService.getServiceUrl("/project/{id}");
		   return template.getForObject(url, Project.class, Collections.singletonMap("id", id));
	}

	@Override
	public void deleteProject(Integer id) {
		 String url = GatewayService.getServiceUrl("/project/{id}");
	        template.delete(url, Collections.singletonMap("id", id));
		
	}

	@Override
	public void createProject(Project request) {
		 String url = GatewayService.getServiceUrl("/project/create");
	   template.postForObject(url, request, String.class);
	
		
	}

	@Override
	public boolean validate(Project request) {
		String url = GatewayService.getServiceUrl("/project/validate");
	    return  template.postForObject(url, request, boolean.class);
	}

	@Override
	public File upload(HttpServletRequest request, HttpServletResponse response,Integer id) {
		File localFile = null;
		File file = new File(ConfigManage.getkey("uploadPath")+"\\"+id);
		 CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
		    if(cmr.isMultipart(request)){
		        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)(request);
		        Iterator<String> files = mRequest.getFileNames();
		        while(files.hasNext()){
		            MultipartFile mFile = mRequest.getFile(files.next());
		            if(mFile != null){
		            	/* SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
		        		 Date date=new Date();*/
		                String fileName = /*dateFormater.format(date) +*/ mFile.getOriginalFilename();
		                if(!file.exists()){
		                	file.mkdirs();
		                }
		                String path = file.getAbsolutePath().toString()+"\\"+fileName;
		                localFile = new File(path);
		                try {
							mFile.transferTo(localFile);
							Project project = new Project();
							project.setId(id);
							project.setAccessory(fileName);
							modifyFileName(project); //为项目增加附件名
							
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
						}
		                request.setAttribute("fileUrl", path);
		            }
		        }
		    }
		return localFile;
	}

	
	
	
	

}
