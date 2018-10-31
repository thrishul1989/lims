package com.todaysoft.lims.technical.service;

import com.todaysoft.lims.technical.entity.SiteplotData;
import com.todaysoft.lims.technical.model.request.SvRequest;
import com.todaysoft.lims.technical.model.response.PagerResponse;
import com.todaysoft.lims.technical.service.impl.PagerQueryHandler;

public interface ISvService extends PagerQueryHandler<String>
{

    PagerResponse<String> svPager(SvRequest request);

    PagerResponse<SiteplotData> regioncoutPager(SvRequest request);
    
}
