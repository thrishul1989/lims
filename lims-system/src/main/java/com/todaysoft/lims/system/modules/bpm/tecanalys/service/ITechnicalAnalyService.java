package com.todaysoft.lims.system.modules.bpm.tecanalys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.model.request.PhenoTypePointsRequest;
import com.todaysoft.lims.system.model.transation.VariableModel;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BioInfoAnnotate;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.tecanalys.TechnicalAnalysisAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ClaimTemplateColumnForDoctor;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.DataColAndMutationDataModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.MutationFilterForm;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.MutationStatisticsModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.MutationStatisticsSearcher;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.SiteplotData;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignArgs;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseResult;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitForm;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitVerifyRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisSampleModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalysisTaskRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.UploadEvidenceRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.UploadEvidenceRespModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CancelCollectionCnvAnalysisResult;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CapCnvResultData;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CnvAnalysisDataModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CnvAnalysisFamilyDataModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CollectionCnvAnalysisPicResultList;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CapCnvRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CollectionCapcnvPicRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CompareSampleReanalysisRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.CompareSampleRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.DoctorIfColletionRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.GroupNameDetailsRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.MutationCollectionRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.MutationDataRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.SubmitCnvVerifyRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.SubmitTechnicalTaskRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.SvRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalAddVerifyRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalAnalysisAddVerify;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalCollectionRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.TechnicalReportRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.response.CompareSample;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.response.DrawPictureModel;

public interface ITechnicalAnalyService
{
    List<TechnicalAnalyTask> getAssignableList(TechnicalAnalyAssignableTaskSearcher searcher);
    
    List<TechnicalAnalyTask> getAssignableListDetail(TechnicalAnalyAssignableTaskSearcher searcher);
    
    TechnicalAnalyAssignModel getAssignModel(TechnicalAnalyAssignArgs args);
    
    void assign(TechnicalAnalyAssignRequest request);
    
    TechnicalAnalySubmitModel getSubmitModel(String id);
    
    TechnicalAnalySubmitModel getSheetExportModel(String id);
    
    void verify(TechnicalAnalySubmitVerifyRequest request);
    
    TechnicalAnalyParseResult parse(String id, MultipartFile file, Integer isReTechnical);
    
    void submit(TechnicalAnalySubmitForm data, TechnicalAnalyParseResult parseResult);
    
    List<TechnicalAnalyTask> getListBySequencingCode(TechnicalAnalyAssignableTaskSearcher searcher);
    
    String downloadBySequencingCode(String sequencingCode, List<TechnicalAnalyTask> tasks);
    
    void generateData();
    
    CnvAnalysisDataModel getRecords(CapCnvRequest request);
    
    boolean ifFamilyAnalysis(String analysisSampleId);
    
    DrawPictureModel getCapCnvPicture(String ids, String sampleCode, String sampleAnalysisId);
    
    String downloadFilterData(CapCnvRequest request);
    
    void downloadData(HttpServletResponse response, String formValue);
    
    void saveAnalysisCapCnvResult(CapCnvResultData capCnvResultData);
    
    List<CompareSample> getCompareSample(CompareSampleRequest request);
    
    void compareSampleReanalysis(CompareSampleReanalysisRequest request);
    
    Pagination<TechnicalAnalysisTask> getTechnicalAnalysisAssignableList(TechnicalAnalysisAssignableTaskSearcher searcher, int pageNo);
    
    Integer claimTechnicalAnalysis(String familyGroupId);
    
    List<String> queryRecentFilterList(MutationFilterForm request);
    
    List<ClaimTemplateColumnForDoctor> getClaimTemplateColumnList();
    
    void saveFilterCreate(MutationFilterForm request);
    
    DataColAndMutationDataModel queryMutationData(MutationDataRequest request);
    
    Integer updatePhenoTypePoints(PhenoTypePointsRequest request);
    
    List<MutationStatisticsModel> getQualitycontrolDataByaAnalysisId(MutationStatisticsSearcher searcher);
    
    boolean ifColletion(DoctorIfColletionRequest req);
    
    List<TechnicalAnalysisSampleModel> getFamilySampleByFamilyId(String familyId);
    
    List<TechnicalAnalysisTask> searchTechnicalAnalysisTask(TechnicalAnalysisTaskRequest searcher);
    
    String downloadFilterData(MutationDataRequest req, String string, String string2) throws Exception;
    
    String getEvidenceByMongoId(String mongoId);
    
    UploadEvidenceRespModel uploadEvidence(UploadEvidenceRequest request);
    
    Map<String, String> getGroupDetails(GroupNameDetailsRequest request);
    
    String getMutationDetail(String objectId);
    
    void submitCnvVerify(SubmitCnvVerifyRequest submitCnvVerifyRequest);
    
    void cancelSubmitCnvVerify(CancelCollectionCnvAnalysisResult request);
    
    Integer collection(TechnicalCollectionRequest request);
    
    void submitTechnicalTask(String familyGroupId);
    
    CnvAnalysisFamilyDataModel getFamilyCapcnvRecords(CapCnvRequest request);
    
    void startAnalsyisSchedule(VariableModel model);
    
    Integer cancelMutationCollection(MutationCollectionRequest request);
    
    VariableModel submitRecheckTechnicalTask(SubmitTechnicalTaskRequest request);
    
    Integer myCollectionCount(String analysisSampleId, String technicalTaskId);
    
    String downloadFamilyFilterData(CapCnvRequest request);
    
    Pagination<String> technicalanalySvdata(SvRequest request);
    
    void collectionCapcnvPic(CollectionCapcnvPicRequest request);
    
    List<BioInfoAnnotate> getAnnotateByfamily(String mongoId);
    
    Pagination<SiteplotData> SiteplotDataSearch(SvRequest request);
    
    DataColAndMutationDataModel searchMutationListByAnalsysiSampleId(String analysisSampleId);
    
    List<TechnicalAnalysisTask> getTaskByFamilyId(String familyAnalysisId);
    
    void technicalReportOperate(TechnicalReportRequest request);
    
    List<CollectionCnvAnalysisPicResultList> collectionCapcnvData(String analysisSampleId);
    
    void saveAddVerify(TechnicalAddVerifyRequest request);
    
    List<TechnicalAnalysisAddVerify> getAddVerifyDataByFamilyId(String analysisFamilyId);
    
    void deleteVerifyDataById(String id);
    
    Boolean checkTechnicalAnalysisIsFinished(String analysisSampleId);
    
    Boolean checkTechnicalAnalysisHasHpoTask(String analysisFamilyId);
    
    String getFamilyId(String id);
    
    VariableModel getMod(String familyGroupId);
    
    TestingSheet getTestingSheet(String taskId);
    
    TechnicalAnalyTask getTask(String familyGroupId);
    
    void saveAddVerifyNew(TechnicalAddVerifyRequest request);
    
    List<TechnicalAnalysisTask> searchErrorTechnicalTask(String familyGroupId);
    
}
