package com.todaysoft.lims.technical.service.impl;

import java.util.List;

public interface PagerQueryHandler<T> {
	int count(Object searcher);

	List<T> query(Object searcher, int offset, int limit);
}
