package com.todaysoft.lims.testing.samplesotck.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.dao.searcher.TestingSheetSampleStorageSearcher;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStorageOutRecord;

public interface ISampleStorageOutListService {

	public Pagination<SampleStorageOutRecord> sampleStorageOut_list(TestingSheetSampleStorageSearcher searcher);

	public Pagination<SampleStorageOutRecord> sampleStorage_list(TestingSheetSampleStorageSearcher searcher);
}
