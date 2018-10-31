package com.todaysoft.lims.technical.service;

import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;
import com.todaysoft.lims.technical.model.DataColAndMutationDataModel;
import com.todaysoft.lims.technical.model.TechnicalAnalysisAddVerify;
import com.todaysoft.lims.technical.model.TechnicalAnalysisSampleModel;
import com.todaysoft.lims.technical.model.VariableModel;
import com.todaysoft.lims.technical.model.request.ClaimTemplateRequest;
import com.todaysoft.lims.technical.model.request.DoctorIfColletionRequest;
import com.todaysoft.lims.technical.model.request.FilterItemsRequest;
import com.todaysoft.lims.technical.model.request.MutationCollectionRequest;
import com.todaysoft.lims.technical.model.request.MutationDataRequest;
import com.todaysoft.lims.technical.model.request.MutationFilterFormRequest;
import com.todaysoft.lims.technical.model.request.ReportExportMutationInfoRequest;
import com.todaysoft.lims.technical.model.request.SubmitTechnicalTaskRequest;
import com.todaysoft.lims.technical.model.request.TechnicalAddVerifyRequest;
import com.todaysoft.lims.technical.model.request.TechnicalCollectionRequest;
import com.todaysoft.lims.technical.model.response.ClaimTemplateColumnForDoctor;
import com.todaysoft.lims.technical.model.response.ClaimTemplateModel;
import com.todaysoft.lims.technical.mybatis.entity.ClaimTemplate;
import com.todaysoft.lims.technical.mybatis.entity.ClaimTemplateColumn;
import com.todaysoft.lims.technical.mybatis.entity.FilterItems;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportMutationInfo;
import com.todaysoft.lims.technical.service.impl.PagerQueryHandler;
import com.todaysoft.lims.technical.utils.Pagination;

public interface IClaimTemplateService extends PagerQueryHandler<DBObject>
{
    
    Pagination<ClaimTemplate> pager(ClaimTemplateRequest request);
    
    void create(ClaimTemplateRequest record);
    
    ClaimTemplate get(String id);
    
    List<ClaimTemplateColumn> getColumn(String templateId);
    
    List<FilterItems> getItemList(FilterItems record);
    
    boolean validate(ClaimTemplateRequest request);
    
    void saveItem(FilterItemsRequest request);
    
    ClaimTemplateModel getByIdForView(String id);
    
    void delete(ClaimTemplateRequest record);
    
    void setPriFlag(ClaimTemplateRequest request);
    
    ClaimTemplateModel getById(String id);
    
    void update(ClaimTemplateRequest request);
    
    List<ClaimTemplate> claimTemplateList();
    
    List<String> queryRecentFilterList(MutationFilterFormRequest request);
    
    List<ClaimTemplateColumnForDoctor> getClaimTempleColumnForDoctor();
    
    void saveMutationFilter(MutationFilterFormRequest request);
    
    DataColAndMutationDataModel queryMutationDataByFilter(MutationDataRequest request);
    
    List<ClaimTemplate> selectForPriFlag();
    
    boolean ifColletion(DoctorIfColletionRequest request, String token);
    
    List<TechnicalAnalysisSampleModel> getFamilySampleByFamilyId(String familyId);
    
    DataColAndMutationDataModel queryMutationDataByFilterForDownload(MutationDataRequest request);
    
    Map<String, String> getGroupDetails(String groupName, String id);
    
    Integer collection(TechnicalCollectionRequest request);
    
    void submitTechnicalTask(String familyGroupId);
    
    VariableModel startAnalsyisSchedule(String analysisFamilyId);
    
    Integer cancelMutationCollection(MutationCollectionRequest request);
    
    void submitRecheckTechnicalTask(SubmitTechnicalTaskRequest familyGroupId);
    
    Integer myCollectionCount(String analysisSampleId, String userId, String technicalTaskId);
    
    DataColAndMutationDataModel searchMutationListByAnalsysiSampleId(String analysisSampleId);
    
    List<ReportExportMutationInfo> searchReportExportMutationInfoListByAnalsysiSampleId(ReportExportMutationInfoRequest request);
    
    void saveAddVerify(TechnicalAddVerifyRequest request);
    
    List<TechnicalAnalysisAddVerify> getAddVerifyDataByFamilyId(String familyId);
    
    void deleteVerifyDataById(String id);
    
    List<ReportExportMutationInfo> searchReportExportMutationInfoListByObjectIds(List<String> objectIds);
    
    VariableModel getMod(String analysisFamilyId);
    
    VariableModel saveAddVerifyNew(TechnicalAddVerifyRequest request);
}
