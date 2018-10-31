package com.todaysoft.lims.system.modules.bpm.competeTasks.service.impl;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.division.BiologyAnnotationSheetViewModel;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.division.BiologyDivisionSheetViewModel;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.order.TestingSchedule;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.BiologyAnalySheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.CDNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.DNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.LibraryCaptureSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.LibraryQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.QtSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.RQTSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.SequencingSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs.TechnicalAnalySheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.INGSCompleteService;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateSheet;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class NGSCompleteService extends RestService implements INGSCompleteService {

	@Override
	public DNAExtractSheet getDNAExtractSheet(String id) {
		String url = GatewayService.getServiceUrl("/bpm/testing/dnaext/sheets/{id}");
		return template.getForObject(url, DNAExtractSheet.class, id);
	}

	@Override
	public DNAQcSheet getDNAQcTestModel(String id) {
		String url = GatewayService.getServiceUrl("/bpm/completeTasks/ngs/dnaqc/sheet/{id}");
		return template.getForObject(url, DNAQcSheet.class, id);
	}

	@Override
	public CDNAExtractSheet getCDNAExtractSheet(String id) {
		String url = GatewayService.getServiceUrl("/bpm/testing/cdnaext/sheets/{id}");
		return template.getForObject(url, CDNAExtractSheet.class, id);
	}

	@Override
	public CDNAQcSheet getCDNAQcTestModel(String id) {
		String url = GatewayService.getServiceUrl("/bpm/completeTasks/ngs/cdnaqc/sheet/{id}");
		return template.getForObject(url, CDNAQcSheet.class, id);
	}

	@Override
	public LibraryCreateSheet getLibraryCreateSheet(String id) {
		String url = GatewayService.getServiceUrl("/bpm/testing/libcre/sheets/{id}");
		return template.getForObject(url, LibraryCreateSheet.class, id);
	}

	@Override
	public LibraryQcSheet getLibraryQcSheet(String id) {
		String url = GatewayService.getServiceUrl("/bpm/completeTasks/ngs/libqc/sheet/{id}");
		return template.getForObject(url, LibraryQcSheet.class, id);
	}

	@Override
	public LibraryCaptureSheet getLibCapSheet(String id) {
		String url = GatewayService.getServiceUrl("/bpm/completeTasks/ngs/libcap/sheet/{id}");
		return template.getForObject(url, LibraryCaptureSheet.class, id);
	}

	@Override
	public RQTSheet getRQTSheet(String id) {
		String url = GatewayService.getServiceUrl("/bpm/completeTasks/ngs/rqt/sheet/{id}");
		return template.getForObject(url, RQTSheet.class, id);
	}

	@Override
	public PoolingSubmitModel getPoolingSubmitModel(String id) {
		String url = GatewayService.getServiceUrl("/bpm/testing/pooling/sheets/{id}");
		return template.getForObject(url, PoolingSubmitModel.class, id);
	}

	@Override
	public QtSheet getQtSheet(String id) {
		String url = GatewayService.getServiceUrl("/bpm/completeTasks/ngs/qt/sheet/{id}");
		return template.getForObject(url, QtSheet.class, id);
	}

	@Override
	public SequencingSheet getSequencingSheet(String id) {
		String url = GatewayService.getServiceUrl("/bpm/completeTasks/ngs/sequencing/sheet/{id}");
		return template.getForObject(url, SequencingSheet.class, id);
	}

	@Override
	public TechnicalAnalySheet getTechnicalAnalySheet(String id) {
		String url = GatewayService.getServiceUrl("/bpm/completeTasks/ngs/technical-analy/sheet/{id}");
		return template.getForObject(url, TechnicalAnalySheet.class, id);
	}

	@Override
	public BiologyAnalySheet getBiologyAnalySheet(String id) {
		String url = GatewayService.getServiceUrl("/bpm/completeTasks/ngs/biology-analy/sheet/{id}");
		return template.getForObject(url, BiologyAnalySheet.class, id);
	}

	@Override
	public SequencingSubmitModel getNgsSequencingSheet(String id) {
		// TODO Auto-generated method stub
		String url = GatewayService.getServiceUrl("/bpm/ngs/testingTask/getNgsSequencingSheet/{sheetId}");
		return template.getForObject(url, SequencingSubmitModel.class, id);
	}

	@Override
	public BiologyDivisionSheetViewModel getBiologyDivisionSheet(String id) {
		String url = GatewayService.getServiceUrl("/biology/division/getBiologyDivisionSheet/{sheetId}");
		return template.getForObject(url, BiologyDivisionSheetViewModel.class, id);
	}

	@Override
	public BiologyAnnotationSheetViewModel getBiologyAnnotationSheet(String id) {
		String url = GatewayService.getServiceUrl("/biology/annotation/getBiologyAnnotationSheet/{sheetId}");
		return template.getForObject(url, BiologyAnnotationSheetViewModel.class, id);
	}
}
