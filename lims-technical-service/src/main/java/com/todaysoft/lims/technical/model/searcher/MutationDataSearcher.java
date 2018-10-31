package com.todaysoft.lims.technical.model.searcher;

import com.mongodb.BasicDBObject;

public class MutationDataSearcher {
	private BasicDBObject searchCond;

	public BasicDBObject getSearchCond() {
		return searchCond;
	}

	public void setSearchCond(BasicDBObject searchCond) {
		this.searchCond = searchCond;
	}
}
