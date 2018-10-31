package com.todaysoft.lims.testing.message.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.MessageNotice;
import com.todaysoft.lims.testing.message.model.request.MessageNoticeCreateRequest;
import com.todaysoft.lims.testing.message.model.request.MessageNoticeListRequest;
import com.todaysoft.lims.testing.message.model.request.MessageNoticeModifyRequest;

import java.util.List;

public interface IMessageNoticeService {

        Pagination<MessageNotice> paging(MessageNoticeListRequest request);

        List<MessageNotice> list(MessageNoticeListRequest request);

        MessageNotice get(String id);

        String create(MessageNoticeCreateRequest request);

        void modify(MessageNoticeModifyRequest request);

        void delete(String id);
}
