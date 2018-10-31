package com.todaysoft.lims.testing.message.service.impl;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.MessageNotice;
import com.todaysoft.lims.testing.message.dao.MessageNoticeSearcher;
import com.todaysoft.lims.testing.message.model.request.MessageNoticeCreateRequest;
import com.todaysoft.lims.testing.message.model.request.MessageNoticeListRequest;
import com.todaysoft.lims.testing.message.model.request.MessageNoticeModifyRequest;
import com.todaysoft.lims.testing.message.service.IMessageNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageNoticeService implements IMessageNoticeService{

    @Autowired
    private BaseDaoSupport baseDaoSupport;

    @Override
    public Pagination<MessageNotice> paging(MessageNoticeListRequest request) {
        MessageNoticeSearcher searcher = new MessageNoticeSearcher();
        searcher.setNotify(request.getNotify());
        searcher.setSampleCode(request.getSampleCode());
        searcher.setHandleStrategy(request.getHandleStrategy());
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), MessageNotice.class);
    }

    @Override
    public List<MessageNotice> list(MessageNoticeListRequest request) {
        MessageNoticeSearcher searcher = new MessageNoticeSearcher();
        searcher.setSampleCode(request.getSampleCode());
        searcher.setHandleStrategy(request.getHandleStrategy());
        searcher.setNotify(request.getNotify());
        return baseDaoSupport.find(searcher);
    }

    @Override
    public MessageNotice get(String id) {
        return baseDaoSupport.get(MessageNotice.class, id);
    }


    @Override
    @Transactional
    public String create(MessageNoticeCreateRequest request) {
        MessageNotice entity = new MessageNotice();
        entity.setSampleCode(request.getSampleCode());
        entity.setHandleStrategy(request.getHandleStrategy());
        entity.setNotify(request.getNotify());
        entity.setNoticeTime(request.getNoticeTime());
        entity.setContent(request.getContent());
        baseDaoSupport.insert(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public void modify(MessageNoticeModifyRequest request) {
        MessageNotice entity = new MessageNotice();
        entity.setId(request.getId());
        entity.setSampleCode(request.getSampleCode());
        entity.setHandleStrategy(request.getHandleStrategy());
        entity.setNotify(request.getNotify());
        entity.setNoticeTime(request.getNoticeTime());
        entity.setContent(request.getContent());
        baseDaoSupport.update(entity);
    }

    @Override
    @Transactional
    public void delete(String id) {
        MessageNotice entity = get(id);
        entity.setDeleted(true);
        baseDaoSupport.update(entity);
    }
}
