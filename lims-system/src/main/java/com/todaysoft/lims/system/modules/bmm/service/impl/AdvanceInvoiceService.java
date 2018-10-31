package com.todaysoft.lims.system.modules.bmm.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.modules.bmm.model.AdvanceInvoiceImportListModel;
import com.todaysoft.lims.system.modules.bmm.model.AdvanceInvoiceImportModel;
import com.todaysoft.lims.system.modules.bmm.model.AdvanceInvoiceImportModelResolver;
import com.todaysoft.lims.system.modules.bmm.model.AdvanceInvoiceImportParseModel;
import com.todaysoft.lims.system.modules.bmm.model.DefaultInvoiceModel;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceApplyModel;
import com.todaysoft.lims.system.modules.bmm.model.request.AdvanceInvoiceOrderProductRequest;
import com.todaysoft.lims.system.modules.bmm.model.request.AdvanceInvoiceSearcher;
import com.todaysoft.lims.system.modules.bmm.service.IAdvanceInvoiceService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUser;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUserModel;
import com.todaysoft.lims.system.modules.smm.service.IInvoiceUserService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class AdvanceInvoiceService extends RestService implements IAdvanceInvoiceService
{
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IInvoiceUserService invoiceUserService;
    
    @Autowired
    private IUserService userService;
    
    @Override
    public Pagination<InvoiceApplyModel> paging(AdvanceInvoiceSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/advanceInvoice/paging");
        ResponseEntity<Pagination<InvoiceApplyModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<AdvanceInvoiceSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<InvoiceApplyModel>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public InvoiceApplyModel get(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/advanceInvoice/{id}");
        return template.getForObject(url, InvoiceApplyModel.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public void solve(InvoiceApplyModel request)
    {
        String url = GatewayService.getServiceUrl("/bmm/advanceInvoice/solve");
        template.postForObject(url, request, Integer.class);
    }

    @Override
    public void delayAdvanceInvoice(String id)
    {
        InvoiceApplyModel request = new InvoiceApplyModel();
        request.setId(id);
        String url = GatewayService.getServiceUrl("/bmm/advanceInvoice/delayAdvanceInvoice");
        template.postForLocation(url, request);
    }
    
    @Override
    public List<InvoiceApplyModel> list(AdvanceInvoiceSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/advanceInvoice/list");
        ResponseEntity<List<InvoiceApplyModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<AdvanceInvoiceSearcher>(searcher), new ParameterizedTypeReference<List<InvoiceApplyModel>>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public List<InvoiceApplyModel> simpleList(AdvanceInvoiceSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/advanceInvoice/simpleList");
        ResponseEntity<List<InvoiceApplyModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<AdvanceInvoiceSearcher>(searcher), new ParameterizedTypeReference<List<InvoiceApplyModel>>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public String download(InputStream is, List<InvoiceApplyModel> list)
    {
        List<InvoiceApplyModel> splitList = Lists.newArrayList();
        for (InvoiceApplyModel model : list)
        {
            if (Collections3.isNotEmpty(model.getOrderList()))
            {
                for (Order order : model.getOrderList())
                {
                    InvoiceApplyModel iam = new InvoiceApplyModel();
                    BeanUtils.copyProperties(model, iam);
                    if (null != order)
                    {
                        iam.setOrderCode(order.getCode());
                        iam.setUpdateTime(order.getUpdateTime());
                        //iam.setInvoiceTitle(order.getInvoiceTitle());
                        if (null != order.getContract())
                        {
                            iam.setContractCode(order.getContract().getCode());
                        }
                        if (Collections3.isNotEmpty(order.getOrderExamineeList()))
                        {
                            iam.setExaminee(Collections3.getFirst(order.getOrderExamineeList()).getName());
                        }
                        
                        for (DefaultInvoiceModel dim : iam.getOrderCostList())
                        {
                            if (dim.getCode().equals(order.getCode()))
                            {
                                iam.setInvoiceAmount(dim.getCurrentActualPay());
                            }
                        }
                    }
                    splitList.add(iam);
                }
            }
        }
        
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File("advance_" + time + ".xlsx");
        TestSheetService.inputstreamToFile(is, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            
            String invoiceTypeContent = "";
            for (InvoiceApplyModel model : splitList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, model.getCode());
                data.put(2, model.getOrderCode());
                data.put(3, model.getContractCode());
                data.put(4, model.getTestingType());
                data.put(5, model.getExaminee());
                data.put(6, model.getCustomerId());
                data.put(7, model.getCreatorId());
                String institution = "";
                if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(model.getInstitution()))
                {
                     institution ="北京迈基诺";
                }
                else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(model.getInstitution()))
                {
                    institution ="北京检验所";
                }
                else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(model.getInstitution()))
                {
                    institution ="重庆迈基诺";
                }
                else
                {
                    institution ="重庆检验所";
                }
                data.put(8,institution);
                data.put(9, model.getInvoiceAmount().setScale(2, RoundingMode.HALF_UP));
                data.put(10, model.getUpdateTime() == null ? "" : testSheetService.getFormatTime("yyyy/MM/dd", model.getUpdateTime()));
                data.put(11, model.getInvoiceTitle());
                data.put(12, model.getInvoiceContent());
                if ("1".equals(model.getInvoiceType()))
                {
                    invoiceTypeContent = "普票";
                }
                else if ("2".equals(model.getInvoiceType()))
                {
                    invoiceTypeContent =
                        "专用；单位名称：" + model.getCompanyName() + "；税号：" + model.getTaxNo() + "；开户银行：" + model.getOpeningBank() + "；账号：" + model.getAccountNumber()
                            + "；联系人：" + model.getContactName() + "；电话：" + model.getContactPhone() + "；单位地址：" + model.getCompanyAddressDetail();
                }
                else
                {
                    invoiceTypeContent = "电票";
                }
                data.put(13, invoiceTypeContent);
                data.put(14, "");
                data.put(15, "");
                data.put(16, "");
                data.put(17, "");
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1", "O1", "P1", "Q1"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
            excel.writeDateList(path, heads, datalist, wsheet);
            
            // 写到输出流并移除资源
            excel.writeAndClose(path, os);
            os.flush();
            
        }
        catch (FileNotFoundException e)
        {
            
            e.printStackTrace();
        }
        catch (IOException e)
        {
            
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
    
    @Override
    public List<AdvanceInvoiceImportParseModel> parse(MultipartFile file)
    {
        AdvanceInvoiceImportModelResolver resolver = new AdvanceInvoiceImportModelResolver(file);
        List<AdvanceInvoiceImportModel> records = resolver.resolve();
        List<AdvanceInvoiceImportParseModel> result = Lists.newArrayList();
        List<String> conditions = Lists.newArrayList();
        for (AdvanceInvoiceImportModel model : records)
        {
            String condition = model.getApplyCode() + "-" + model.getInstitution();
            if (!conditions.contains(condition))
            {
                conditions.add(condition);
            }
            
            AdvanceInvoiceImportParseModel parseModel = new AdvanceInvoiceImportParseModel();
            parseModel.setValid(true);
            
            if (StringUtils.isEmpty(model.getDrawerName()) || StringUtils.isEmpty(model.getInvoiceTime()) || StringUtils.isEmpty(model.getInvoicerNo()))
            {
                parseModel.setValid(false);
                parseModel.setMessage("开票信息必填项缺失");
            }
            else
            {
                InvoiceUserModel userModel = new InvoiceUserModel();
                userModel.setName(model.getDrawerName());
                List<InvoiceUserModel> userList = invoiceUserService.findUserByName(userModel);
                if (Collections3.isEmpty(userList) || userList.size() > 1)
                {
                    parseModel.setValid(false);
                    parseModel.setMessage("请填写正确的开票人姓名");
                }
            }
            if (StringUtils.isEmpty(parseModel.getMessage()))
            {
                if (StringUtils.isEmpty(model.getApplyCode()) || StringUtils.isEmpty(model.getOrderCode()) || StringUtils.isEmpty(model.getInstitution())
                    || StringUtils.isEmpty(model.getInvoiceAmount()))
                {
                    parseModel.setValid(false);
                    parseModel.setMessage("基本信息缺失");
                }
                else
                {
                    AdvanceInvoiceSearcher searcher = new AdvanceInvoiceSearcher();
                    searcher.setCode(model.getApplyCode());
                    searcher.setOrderCode(model.getOrderCode());
                    if(StringUtils.isNotEmpty(model.getInstitution()))
                    {
                        if (model.getInstitution().equals("北京检验所"))
                        {
                          searcher.setInstitution("0");
                        }
                        else if (model.getInstitution().equals("北京迈基诺"))
                        {
                            searcher.setInstitution("1");
                        }
                        else if (model.getInstitution().equals("重庆迈基诺") )
                        {
                            searcher.setInstitution("2");
                        }
                        else
                        {
                            searcher.setInstitution("3");
                        }
                    }

                    searcher.setSolveStatus(2);
                    List<InvoiceApplyModel> list = list(searcher);
                    if (Collections3.isEmpty(list))
                    {
                        parseModel.setValid(false);
                        parseModel.setMessage("此开票数据不存在或已被处理");
                    }
                }
            }
            parseModel.setModel(model);
            result.add(parseModel);
        }
        
        List<List<AdvanceInvoiceImportParseModel>> reList = Lists.newArrayList();
        for (String condition : conditions)
        {
            List<AdvanceInvoiceImportParseModel> filterList = Lists.newArrayList();
            for (AdvanceInvoiceImportParseModel parseModel : result)
            {
                AdvanceInvoiceImportModel model = parseModel.getModel();
                String str = model.getApplyCode() + "-" + model.getInstitution();
                if (str.equals(condition))
                {
                    filterList.add(parseModel);
                }
            }
            if (Collections3.isNotEmpty(filterList))
            {
                reList.add(filterList);
            }
        }
        for (List<AdvanceInvoiceImportParseModel> modelList : reList)
        {
            BigDecimal invoiceAmount = new BigDecimal(0);
            AdvanceInvoiceImportModel diim = Collections3.getFirst(modelList).getModel();
            AdvanceInvoiceSearcher searcher = new AdvanceInvoiceSearcher();
            searcher.setCode(diim.getApplyCode());
            if(StringUtils.isNotEmpty(diim.getInstitution()))
            {
                if (diim.getInstitution().equals("北京迈基诺"))
                {
                    searcher.setInstitution("1");
                }
                else if (diim.getInstitution().equals("北京检验所"))
                {
                    searcher.setInstitution("0");
                }
                else if (diim.getInstitution().equals("重庆迈基诺"))
                {
                    searcher.setInstitution("2");
                }
                else
                {
                    searcher.setInstitution("3");
                }

            }
            searcher.setSolveStatus(2);
            List<InvoiceApplyModel> list = list(searcher);
            if (Collections3.isNotEmpty(list))
            {
                InvoiceApplyModel iam = Collections3.getFirst(list);
                invoiceAmount = iam.getCurrentActualPay().setScale(2, RoundingMode.HALF_UP);
            }
            
            BigDecimal eachAmount = new BigDecimal(0);
            for (AdvanceInvoiceImportParseModel model : modelList)
            {
                eachAmount = eachAmount.add(new BigDecimal(model.getModel().getInvoiceAmount()));
            }
            for (AdvanceInvoiceImportParseModel model : modelList)
            {
                if (StringUtils.isEmpty(model.getMessage()) && invoiceAmount.compareTo(eachAmount) != 0)
                {
                    model.setValid(false);
                    model.setMessage("开票金额总和实收款不符");
                }
            }
        }
        return result;
    }
    
    @Override
    public void importSolve(AdvanceInvoiceImportListModel request)
    {
        String url = GatewayService.getServiceUrl("/bmm/advanceInvoice/importSolve");
        template.postForObject(url, request, Integer.class);
    }
    
    private String getInstitution()
    {
        AuthorizedUser user = userService.getByToken();
        List<String> institutions = Lists.newArrayList("0", "1");
        for (String institution : institutions)
        {
            InvoiceUser iu = invoiceUserService.getByInstitution(institution);
            for (InvoiceUserModel um : iu.getUserList())
            {
                if (um.getUser().getId().equals(user.getId()))
                {
                    return institution;
                }
            }
        }
        return null;
    }
    
    @Override
    public void updateOrderById(AdvanceInvoiceOrderProductRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/advanceInvoice/updateOrdersById");
        template.postForObject(url, request, boolean.class);
    }
}
