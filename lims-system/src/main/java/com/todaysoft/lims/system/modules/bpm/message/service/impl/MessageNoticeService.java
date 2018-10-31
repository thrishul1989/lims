package com.todaysoft.lims.system.modules.bpm.message.service.impl;

import com.todaysoft.lims.system.model.vo.MessageNotice;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.message.model.MessageNoticeSearcher;
import com.todaysoft.lims.system.modules.bpm.message.service.IMessageNoticeService;
import com.todaysoft.lims.system.modules.bpm.message.service.request.MessageNoticeCreateRequest;
import com.todaysoft.lims.system.modules.bpm.message.service.request.MessageNoticeListRequest;
import com.todaysoft.lims.system.modules.bpm.message.service.request.MessageNoticeModifyRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageNoticeService extends RestService implements IMessageNoticeService{

    @Override
    public Pagination<MessageNotice> paging(MessageNoticeSearcher seacher, int pageNo, int pageSize) {
        MessageNoticeListRequest request = new MessageNoticeListRequest();
        BeanUtils.copyProperties(seacher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bpm/messageNotice/paging");
        ResponseEntity<Pagination<MessageNotice>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<MessageNoticeListRequest>(request), new ParameterizedTypeReference<Pagination<MessageNotice>>()
                {
                });
        return exchange.getBody();


    }

    @Override
    public List<MessageNotice> list(MessageNoticeSearcher searcher) {
        MessageNoticeListRequest request = new MessageNoticeListRequest();
        BeanUtils.copyProperties(searcher, request);
        String url = GatewayService.getServiceUrl("/bpm/messageNotice/list");
        ResponseEntity<List<MessageNotice>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<MessageNoticeListRequest>(request), new ParameterizedTypeReference<List<MessageNotice>>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public MessageNotice get(String id) {
        String url = GatewayService.getServiceUrl("/bpm/messageNotice/{id}");
        return template.getForObject(url, MessageNotice.class, id);
    }

    @Override
    public String create(MessageNoticeCreateRequest request) {
        String url = GatewayService.getServiceUrl("/bpm/messageNotice/create");
        return template.postForObject(url, request, String.class);
    }

    @Override
    public void modify(MessageNoticeModifyRequest request) {
        String url = GatewayService.getServiceUrl("/bpm/messageNotice/modify");
        template.postForLocation(url, request);
    }

    @Override
    public void delete(String id) {
        String url = GatewayService.getServiceUrl("/bpm/messageNotice/{id}");
        template.delete(url, id);
    }

}
