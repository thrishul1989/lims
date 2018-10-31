package com.todaysoft.lims.system.modules.bpm.message.service;

import com.todaysoft.lims.system.model.vo.MessageNotice;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.message.model.MessageNoticeSearcher;
import com.todaysoft.lims.system.modules.bpm.message.service.request.MessageNoticeCreateRequest;
import com.todaysoft.lims.system.modules.bpm.message.service.request.MessageNoticeModifyRequest;

import java.util.List;

public interface IMessageNoticeService {

    Pagination<MessageNotice> paging(MessageNoticeSearcher seacher, int pageNo, int pageSize);

    List<MessageNotice> list(MessageNoticeSearcher searcher);

    MessageNotice get(String id);

    String create(MessageNoticeCreateRequest request);

    void modify(MessageNoticeModifyRequest request);

    void delete(String id);

}
