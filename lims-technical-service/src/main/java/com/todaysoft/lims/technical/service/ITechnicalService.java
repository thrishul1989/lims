package com.todaysoft.lims.technical.service;

import com.todaysoft.lims.technical.service.core.event.TechnicalCreateEvent;

public interface ITechnicalService {

	String createTechnicalTask(TechnicalCreateEvent event);

}
