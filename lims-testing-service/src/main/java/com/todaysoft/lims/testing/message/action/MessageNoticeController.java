package com.todaysoft.lims.testing.message.action;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.MessageNotice;
import com.todaysoft.lims.testing.message.model.request.MessageNoticeCreateRequest;
import com.todaysoft.lims.testing.message.model.request.MessageNoticeListRequest;
import com.todaysoft.lims.testing.message.model.request.MessageNoticeModifyRequest;
import com.todaysoft.lims.testing.message.service.IMessageNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bpm/messageNotice")
public class MessageNoticeController {

    @Autowired
    private IMessageNoticeService service;

    @RequestMapping(value = "/paging")
    public Pagination<MessageNotice> paging(@RequestBody MessageNoticeListRequest request) {
        return service.paging(request);
    }

    @RequestMapping(value = "/list")
    public List<MessageNotice> list(@RequestBody MessageNoticeListRequest request) {
        return service.list(request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MessageNotice get(@PathVariable String id) {
        return service.get(id);
    }

    @RequestMapping(value = "/create")
    public void create(@RequestBody MessageNoticeCreateRequest request) {
        service.create(request);
    }

    @RequestMapping(value = "/modify")
    public void modify(@RequestBody MessageNoticeModifyRequest request) {
        service.modify(request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
