package com.todaysoft.lims.system.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.searcher.ContractSystemRequest;
import com.todaysoft.lims.system.model.searcher.CustomerSystemRequest;
import com.todaysoft.lims.system.model.searcher.CycleSystemRequest;
import com.todaysoft.lims.system.model.searcher.OrderSystemRequest;
import com.todaysoft.lims.system.model.searcher.OtherReportFormSearcher;
import com.todaysoft.lims.system.model.vo.OtherReportFormModel;
import com.todaysoft.lims.system.model.vo.contract.reportForm.ContractReportFormOneHeadModel;
import com.todaysoft.lims.system.model.vo.contract.reportForm.ReportSystemContractInfo;
import com.todaysoft.lims.system.model.vo.order.customerOrderReportForm.ReportSystemCustomerInfo;
import com.todaysoft.lims.system.model.vo.order.orderReportForm.OrderReportFormOneHeadModel;
import com.todaysoft.lims.system.model.vo.order.orderReportForm.ReportSystemOrderInfo;
import com.todaysoft.lims.system.modules.bpm.service.impl.TestingTestTasksService;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.ISystemReportFormService;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SystemReportFormService extends RestService implements ISystemReportFormService
{
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ICustomerService customerService;
    
    private static Logger log = LoggerFactory.getLogger(SystemReportFormService.class);
    
    @Override
    public List<ReportSystemOrderInfo> orderReportFormListForWkcreate(OrderSystemRequest searcher)
    {
        List<ReportSystemOrderInfo> records = Lists.newArrayList();
        //orderService.getOrderForBaseInfoList(searcher);
        
        int pageNo = 1;
        int pageSize = 50;
        Pagination<ReportSystemOrderInfo> pagination=null;
        do
        {
            searcher.setPageNo(pageNo++);
            searcher.setPageSize(pageSize);
            Pagination<ReportSystemOrderInfo> model = orderSystemInfoPaging(searcher);
            if(null != model)
            {
                pagination = new Pagination<>();
                pagination.setPageNo(model.getPageNo());
                pagination.setPageSize(model.getPageSize());
                pagination.setTotalCount(model.getTotalCount());
                if (Collections3.isNotEmpty(model.getRecords()))
                {
                    records.addAll(model.getRecords());
                }
            }
            if (log.isDebugEnabled())
            {
                log.debug("Search records for page {} successful, total count {}, total page {}.",
                        pageNo,
                        pagination.getTotalCount(),
                        pagination.getTotalPage());
            }

        } while (!pagination.isLastPage());

        if (log.isDebugEnabled())
        {
            log.debug("Search records successful, total count {}.", records.size());
        }
        return records;
    }
    
    private Pagination<ReportSystemOrderInfo> orderSystemInfoPaging(OrderSystemRequest searcher) {

        String url = GatewayService.getServiceUrl("/export/system/orderSystemInfoList");
        ResponseEntity<Pagination<ReportSystemOrderInfo>> exchange =
                template.exchange(url,
                        HttpMethod.POST,
                        new HttpEntity<OrderSystemRequest>(searcher),
                        new ParameterizedTypeReference<Pagination<ReportSystemOrderInfo>>()
                        {
                        });
        return exchange.getBody();
    }
    
    @Override
    public String writer(String fileName, String fileType, List<ReportSystemOrderInfo> models, Map<String, List<String>> mapTitle)
        throws Exception
    {
        Workbook wb = null;
        String time = DateUtils.formatDate(new Date(), "yyyyMMdd");
        File file = new File(fileName +"_"+ time + "." +fileType);
        Sheet sheet = null;
        // 创建工作文档对象
        if (!file.exists())
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();
                
            }
            else if (fileType.equals("xlsx"))
            {
                
                wb = new XSSFWorkbook();
            }
            // 创建sheet对象
            sheet = wb.createSheet("sheet1");
            OutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            
        }
        else
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();
                
            }
            else if (fileType.equals("xlsx"))
            {
                wb = new XSSFWorkbook();
                
            }
        }
        // 创建sheet对象
        if (sheet == null)
        {
            sheet = wb.createSheet("sheet1");
        }
        
        Row row = null;
        Cell cell = null;
        // --------一级列头设置----------
        CellStyle style = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short)260);
        style.setFont(font);
        // ----------二级列头设置---------
        CellStyle style2 = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style2.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style2.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style2.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeight((short)240);
        // font2.setColor(HSSFColor.RED.index);
        style2.setFont(font2);
        // -----------内容设置----------------
        CellStyle style3 = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style3.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style3.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style3.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font3 = wb.createFont();
        font3.setFontName("宋体");
        // font3.setFontHeight((short)240);
        style3.setFont(font3);
        
        List<OrderReportFormOneHeadModel> oneHeadList = Lists.newArrayList();// 一级列头(不重复)
        List<String> oneHeadForTwoList = Lists.newArrayList();// 一级列头(根据二级标题重复生成一级标题)
        List<String> baseInfoList = Lists.newArrayList();
        List<String> productInfoList = Lists.newArrayList();
        List<String> examineeInfoList = Lists.newArrayList();
        List<String> diseaseInfoList = Lists.newArrayList();
        List<String> primarySampleInfoList = Lists.newArrayList();
        List<String> subsidiarySampleInfoList = Lists.newArrayList();
        List<String> recipientInfoList = Lists.newArrayList();
        List<String> costInfoList = Lists.newArrayList();
        List<String> refundInfoList = Lists.newArrayList();
        List<String> billingInfoList = Lists.newArrayList();
        List<String> reportInfoList = Lists.newArrayList();
        List<String> twoHeadList = Lists.newArrayList();// 二级列头
        for (Map.Entry<String, List<String>> entry : mapTitle.entrySet())
        {
            OrderReportFormOneHeadModel oneHeadModel = new OrderReportFormOneHeadModel();
            if ("baseInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("订单信息");
                oneHeadModel.setName("订单信息");
                oneHeadModel.setIndex(1);
                baseInfoList = entry.getValue();
            }
            else if ("productInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("检测产品");
                oneHeadModel.setName("检测产品");
                oneHeadModel.setIndex(2);
                productInfoList = entry.getValue();
            }
            else if ("examineeInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("受检人信息");
                oneHeadModel.setName("受检人信息");
                oneHeadModel.setIndex(3);
                examineeInfoList = entry.getValue();
            }
            else if ("diseaseInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("疾病信息");
                oneHeadModel.setName("疾病信息");
                oneHeadModel.setIndex(4);
                diseaseInfoList = entry.getValue();
            }
            else if ("primarySampleInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("主样本信息");
                oneHeadModel.setName("主样本信息");
                oneHeadModel.setIndex(5);
                primarySampleInfoList = entry.getValue();
            }
            else if ("subsidiarySampleInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("家属样本信息");
                oneHeadModel.setName("样本信息");
                oneHeadModel.setIndex(6);
                subsidiarySampleInfoList = entry.getValue();
            }
            else if ("recipientInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("收件人信息");
                oneHeadModel.setName("收件人信息");
                oneHeadModel.setIndex(7);
                recipientInfoList = entry.getValue();
            }
            else if ("costInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("费用信息");
                oneHeadModel.setName("费用信息");
                oneHeadModel.setIndex(8);
                costInfoList = entry.getValue();
            }
            else if ("refundInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("退款信息");
                oneHeadModel.setName("退款信息");
                oneHeadModel.setIndex(9);
                refundInfoList = entry.getValue();
            }
            else if ("billingInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("开票信息");
                oneHeadModel.setName("开票信息");
                oneHeadModel.setIndex(10);
                billingInfoList = entry.getValue();
            }
            else if ("reportInfo".equals(entry.getKey()))
            {
                // oneHeadList.add("报告信息");
                oneHeadModel.setName("报告信息");
                oneHeadModel.setIndex(11);
                reportInfoList = entry.getValue();
            }
            oneHeadList.add(oneHeadModel);
        }
        // 排序
        oneHeadList.sort(Comparator.comparing(OrderReportFormOneHeadModel::getIndex));
        String[] headnum0 = new String[oneHeadList.size()];// 对应excel中的行和列，下标从0开始{"开始行,结束行,开始列,结束列"}
        int startIndex = 0;
        int endIndex = 0;
        int i = 0;
        if (Collections3.isNotEmpty(baseInfoList))
        {
            endIndex += baseInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += baseInfoList.size();
            for (int j = 0; j < baseInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(baseInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(productInfoList))
        {
            endIndex += productInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += productInfoList.size();
            for (int j = 0; j < productInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(productInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(examineeInfoList))
        {
            endIndex += examineeInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += examineeInfoList.size();
            for (int j = 0; j < examineeInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(examineeInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(diseaseInfoList))
        {
            endIndex += diseaseInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += diseaseInfoList.size();
            for (int j = 0; j < diseaseInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(diseaseInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(primarySampleInfoList))
        {
            endIndex += primarySampleInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += primarySampleInfoList.size();
            for (int j = 0; j < primarySampleInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(primarySampleInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(subsidiarySampleInfoList))
        {
            endIndex += subsidiarySampleInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += subsidiarySampleInfoList.size();
            for (int j = 0; j < subsidiarySampleInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(subsidiarySampleInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(recipientInfoList))
        {
            endIndex += recipientInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += recipientInfoList.size();
            for (int j = 0; j < recipientInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(recipientInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(costInfoList))
        {
            endIndex += costInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += costInfoList.size();
            for (int j = 0; j < costInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(costInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(refundInfoList))
        {
            endIndex += refundInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += refundInfoList.size();
            for (int j = 0; j < refundInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(refundInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(billingInfoList))
        {
            endIndex += billingInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += billingInfoList.size();
            for (int j = 0; j < billingInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(billingInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(reportInfoList))
        {
            endIndex += reportInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += reportInfoList.size();
            for (int j = 0; j < reportInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(reportInfoList.get(j));
            }
            i++;
        }
        for (int j = 0; j < headnum0.length; j++)
        {
            String str = headnum0[j];
            String[] indexArr = str.split(",");
            headnum0[j] = indexArr[0] + "," + indexArr[1] + "," + indexArr[2] + "," + String.valueOf(Integer.parseInt(indexArr[3]) - 1);
        }
        
        row = sheet.createRow(0); // 创建第1行
        for (int j = 0; j < endIndex; j++)
        {
            cell = row.createCell(j);
            cell.setCellValue(oneHeadForTwoList.get(j));
            cell.setCellStyle(style); // 样式，居中
            sheet.setColumnWidth(j, 20 * 256);
        }
        row.setHeight((short)450);
        // 动态合并单元格
        for (int j = 0; j < headnum0.length; j++)
        {
            String[] temp = headnum0[j].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        row = sheet.createRow(1); // 创建第2行
        for (int j = 0; j < twoHeadList.size(); j++)
        {
            cell = row.createCell(j);
            cell.setCellValue(twoHeadList.get(j));
            cell.setCellStyle(style2); // 样式，居中
        }
        List<String[]> datalist = Lists.newArrayList();
        for (ReportSystemOrderInfo model : models)
        {
            
            datalist.addAll(wrapObjectToListString(model,
                endIndex,
                baseInfoList,
                productInfoList,
                examineeInfoList,
                diseaseInfoList,
                subsidiarySampleInfoList,
                recipientInfoList,
                costInfoList,
                refundInfoList,
                billingInfoList,
                reportInfoList));
        }
        ExcelUtil excel = new ExcelUtil();
        String path2 = file.getPath();
        excel.exportObjects2ModuleExcel(datalist, 2, wb, "sheet1", true, path2);
        wb.write(new FileOutputStream(path2));
        return file.getAbsolutePath();
        // 循环写入行数据
        /*
         * for (int j = 0; j < models.size(); j++)
         * {
         * row = sheet.createRow(rowIndex+2);
         * 
         * 
         * 
         * List<ProductInfoModel> productInfos = models.get(j).getProductInfoList();
         * 
         * if(Collections3.isNotEmpty(productInfos))
         * {
         * rowIndex +=productInfos.size();
         * for(int l = 0;l<productInfos.size();l++)
         * {
         * if(l>0)
         * {
         * row = sheet.createRow(j + 2+l);
         * }
         * if(Collections3.isNotEmpty(productInfoList))
         * {
         * for(int k = 0;k<productInfoList.size();k++)
         * {
         * cell = row.createCell(k+ baseInfoList.size());
         * if ("产品名称".equals(productInfoList.get(k)))
         * {
         * cell.setCellValue((productInfos.get(l)).getProductName());
         * }
         * if ("产品退款状态".equals(productInfoList.get(k)))
         * {
         * cell.setCellValue((productInfos.get(l)).getProductRefundStatus());
         * }
         * if ("报告状态".equals(productInfoList.get(k)))
         * {
         * cell.setCellValue((productInfos.get(l)).getProductReportStatus());
         * }
         * if ("产品状态".equals(productInfoList.get(k)))
         * {
         * cell.setCellValue((productInfos.get(l)).getProductStatus());
         * }
         * if ("出报告日期".equals(productInfoList.get(k)))
         * {
         * cell.setCellValue((productInfos.get(l)).getProductReportDate());
         * }
         * if ("寄送日期".equals(productInfoList.get(k)))
         * {
         * cell.setCellValue((productInfos.get(l)).getSendDate());
         * }
         * cell.setCellStyle(style3);
         * }
         * }
         * }
         * }
         * else
         * {
         * rowIndex +=1;
         * }
         * }
         */
        
        // 创建文件流
        // OutputStream stream = new FileOutputStream(excelPath);
        // 写入数据
        // wb.write(stream);
        // 关闭文件流
        // stream.close();
        
    }
    
    private List<String[]> wrapObjectToListString(ReportSystemOrderInfo model, int endIndex, List<String> baseInfoList, List<String> productInfoList, List<String> examineeInfoList,
        List<String> diseaseInfoList, List<String> subsidiarySampleInfoList, List<String> recipientInfoList, List<String> costInfoList, List<String> refundInfoList, List<String> billingInfoList,
        List<String> reportInfoList)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String[]> results = Lists.newArrayList();
        if (null != model)
        {
            int maxSize = maxSize(model,productInfoList,examineeInfoList,diseaseInfoList,subsidiarySampleInfoList,recipientInfoList,
                costInfoList,refundInfoList,billingInfoList,reportInfoList);
            for (int i = 0; i < maxSize; i++)
            {
                int z = 0;
                String[] modelArr = new String[endIndex];
                //2017.12.19 订单信息和收件信息补空行显示
                //if (i < 1)
                //{
                    for (int k = 0; k < baseInfoList.size(); k++)
                    {
                        if ("订单编号".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getOrderCode()) ? model.getOrderCode() : "";
                        }
                        if ("订单类型".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getOrderType()) ? model.getOrderType() : "";
                        }
                        if ("客户".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getCustomer()) ? model.getCustomer() : "";
                        }
                        if ("送检单位".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getCompanyName()) ? model.getCompanyName() : "";
                        }
                        if ("业务员".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getCreateName()) ? model.getCreateName() : "";
                        }
                        if ("订单主状态".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getTestingStatus()) ? model.getTestingStatus() : "";
                        }
                        if ("订单支付状态".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getPaymentStatus()) ? model.getPaymentStatus() : "";
                        }
                        if ("订单延迟状态".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getDelayStatus()) ? model.getDelayStatus() : "";
                        }
                        if ("订单金额".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getOrderAmount()) ? String.valueOf(model.getOrderAmount()) : "";
                        }
                        if ("客户参与".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getDoctorAssist()) ? model.getDoctorAssist() : "";
                        }
                        if ("所属合同".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getContractName()) ? model.getContractName() : "";
                        }
                        if ("采样费用".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getSamplingAmount()) ? String.valueOf(model.getSamplingAmount()) : "";
                        }
                        if ("创建时间".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getCreateTime()) ?  sdfss.format(model.getCreateTime()) : "";
                        }
                        if ("启动时间".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getStartTime()) ? sdfss.format(model.getStartTime()) : "";
                        }
                    }
                //}
                z = baseInfoList.size();
                if (Collections3.isNotEmpty(productInfoList))// 列头
                {
                    if (Collections3.isNotEmpty(model.getProductList()))
                    {
                        if (i < model.getProductList().size())
                        {
                            int z_p = z;
                            // for (ProductInfoModel pim : model.getProductInfoList())
                            // {
                            for (int k = 0; k < productInfoList.size(); k++)
                            {
                                if ("产品名称".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_p + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getProductName()) ? model.getProductList().get(i).getProductName() : "";
                                }
                                if("lane号".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_p + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getLanes()) ? model.getProductList().get(i).getLanes() : "";
                                }
                                if ("产品退款状态".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_p + k] =
                                        StringUtils.isNotEmpty(model.getProductList().get(i).getRefundStatus()) ? model.getProductList().get(i).getRefundStatus() : "";
                                }
                                /*if ("报告状态".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_p + k] =
                                        StringUtils.isNotEmpty(model.getProductInfoList().get(i).getProductReportStatus()) ? model.getProductInfoList().get(i).getProductReportStatus() : "";
                                }*/
                                if ("产品状态".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_p + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getProductStatus()) ? model.getProductList().get(i).getProductStatus() : "";
                                }
                                /*if ("出报告日期".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_p + k] =
                                        StringUtils.isNotEmpty(model.getProductInfoList().get(i).getProductReportDate()) ? model.getProductInfoList().get(i).getProductReportDate() : "";
                                }
                                if ("寄送日期".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_p + k] = StringUtils.isNotEmpty(model.getProductInfoList().get(i).getSendDate()) ? model.getProductInfoList().get(i).getSendDate() : "";
                                }*/
                            }
                            // }
                        }
                    }
                    z += productInfoList.size();
                }
                if (Collections3.isNotEmpty(examineeInfoList))// 列头
                {
                        if (i < 1)
                        {
                            int z_e = z;
                            for (int k = 0; k < examineeInfoList.size(); k++)
                            {
                                if ("名称".equals(examineeInfoList.get(k)))
                                {
                                    modelArr[z_e + k] = StringUtils.isNotEmpty(model.getExamineeName()) ? model.getExamineeName() : "";
                                }
                                if ("民族".equals(examineeInfoList.get(k)))
                                {
                                    modelArr[z_e + k] = StringUtils.isNotEmpty(model.getNation()) ? model.getNation() : "";
                                }
                                if ("性别".equals(examineeInfoList.get(k)))
                                {
                                    modelArr[z_e + k] = StringUtils.isNotEmpty(model.getSex()) ? model.getSex() : "";
                                }
                                if ("出生日期".equals(examineeInfoList.get(k)))
                                {
                                    modelArr[z_e + k] = StringUtils.isNotEmpty(model.getBirthday()) ? model.getBirthday() : "";
                                }
                                if ("申请检测时年龄".equals(examineeInfoList.get(k)))
                                {
                                    modelArr[z_e + k] = StringUtils.isNotEmpty(model.getAgeSnapshot()) ? model.getAgeSnapshot() : "";
                                }
                                if ("籍贯".equals(examineeInfoList.get(k)))
                                {
                                    modelArr[z_e + k] = StringUtils.isNotEmpty(model.getOrigin()) ? model.getOrigin() : "";
                                }
                                if ("联系人".equals(examineeInfoList.get(k)))
                                {
                                    modelArr[z_e + k] = StringUtils.isNotEmpty(model.getContactName()) ? model.getContactName() : "";
                                }
                                if ("联系电话".equals(examineeInfoList.get(k)))
                                {
                                    modelArr[z_e + k] = StringUtils.isNotEmpty(model.getContactPhome()) ? model.getContactPhome() : "";
                                }
                                if ("联系邮箱".equals(examineeInfoList.get(k)))
                                {
                                    modelArr[z_e + k] = StringUtils.isNotEmpty(model.getContactEmail()) ? model.getContactEmail() : "";
                                }
                                if ("病例号".equals(examineeInfoList.get(k)))
                                {
                                    modelArr[z_e + k] = StringUtils.isNotEmpty(model.getRecordNo()) ? model.getRecordNo() : "";
                                }
                                // z = z_e + k+1;
                            }
                            // }
                        }
                    z += examineeInfoList.size();
                }
                if (Collections3.isNotEmpty(diseaseInfoList))// 列头
                {
                    //if (Collections3.isNotEmpty(model.getDiseaseInfoList()))
                    //{
                        if (i < 1)
                        {
                            int z_d = z;
                            // for(DiseaseInfoModel dim : model.getDiseaseInfoList())
                            // {
                            for (int k = 0; k < diseaseInfoList.size(); k++)
                            {
                                if ("临床诊断".equals(diseaseInfoList.get(k)))
                                {
                                    modelArr[z_d + k] = StringUtils.isNotEmpty(model.getDisease()) ? model.getDisease() : "";
                                }
                                if ("重点关注基因".equals(diseaseInfoList.get(k)))
                                {
                                    modelArr[z_d + k] = StringUtils.isNotEmpty(model.getGene()) ? model.getGene() : "";
                                }
                                if ("临床表型".equals(diseaseInfoList.get(k)))
                                {
                                    modelArr[z_d + k] = StringUtils.isNotEmpty(model.getPhenotype()) ? model.getPhenotype() : "";
                                }
                                if ("病史摘要".equals(diseaseInfoList.get(k)))
                                {
                                    modelArr[z_d + k] = StringUtils.isNotEmpty(model.getMedicalHistory()) ? model.getMedicalHistory() : "";
                                }
                                if ("家族史摘要".equals(diseaseInfoList.get(k)))
                                {
                                    modelArr[z_d + k] =
                                        StringUtils.isNotEmpty(model.getFamilyMedicalHistory()) ? model.getFamilyMedicalHistory() : "";
                                }
                                if ("临床推荐理由".equals(diseaseInfoList.get(k)))
                                {
                                    modelArr[z_d + k] =
                                        StringUtils.isNotEmpty(model.getClinicalRecommend()) ? model.getClinicalRecommend() : "";
                                }
                                // z = z_d+k+1;
                            }
                            // }
                        }
                    //}
                    z += diseaseInfoList.size();
                }
                if (Collections3.isNotEmpty(subsidiarySampleInfoList))
                {
                    if (Collections3.isNotEmpty(model.getSampleList()))
                    {
                        if (i < model.getSampleList().size())
                        {
                            int z_s = z;
                            // for(SubsidiarySampleInfoModel ssim : model.getSampleInfoList())
                            // {
                            for (int k = 0; k < subsidiarySampleInfoList.size(); k++)
                            {
                                if ("样本编号".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getSampleCode()) ? model.getSampleList().get(i).getSampleCode() : "";
                                }
                                if ("样本类型".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getSampleType()) ? model.getSampleList().get(i).getSampleType() : "";
                                }
                                if ("样本量".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getSampleSize()) ? model.getSampleList().get(i).getSampleSize() : "";
                                }
                                if ("采样时间".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getSamplingDate()) ? sdf.format(model.getSampleList().get(i).getSamplingDate())  : "";
                                }
                                if ("样本状态".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getSamplePackageStatus()) ? model.getSampleList().get(i).getSamplePackageStatus() : "";
                                }
                                if ("打包时间".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getPackDate()) ? sdfss.format(model.getSampleList().get(i).getPackDate())  : "";
                                }
                                if ("接收日期".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getAcceptSampleTime()) ? sdfss.format(model.getSampleList().get(i).getAcceptSampleTime()) : "";
                                }
                                if ("样本提供者姓名".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getOwnerName()) ? model.getSampleList().get(i).getOwnerName() : "";
                                }
                                if ("样本提供者年龄".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getOwnerAge()) ? model.getSampleList().get(i).getOwnerAge() : "";
                                }
                                if ("家属关系".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getFamilyRelation()) ? model.getSampleList().get(i).getFamilyRelation() : "";
                                }
                                if ("样本用途".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getPuprose()) ? model.getSampleList().get(i).getPuprose() : "";
                                }
                                if ("家属症状".equals(subsidiarySampleInfoList.get(k)))
                                {
                                    modelArr[z_s + k] = StringUtils.isNotEmpty(model.getSampleList().get(i).getOwnerPhenotype()) ? model.getSampleList().get(i).getOwnerPhenotype() : "";
                                }
                                // z= z_s+k+1;
                            }
                            // }
                        }
                    }
                    z += subsidiarySampleInfoList.size();
                }
                if (Collections3.isNotEmpty(recipientInfoList))
                {
                    //if (Collections3.isNotEmpty(model.getRecipientInfoList()))
                    //{
                        //if (i < 1)
                        //{
                            int z_r = z;
                            for (int k = 0; k < recipientInfoList.size(); k++)
                            {
                                if ("收件人姓名".equals(recipientInfoList.get(k)))
                                {
                                    modelArr[z_r + k] = StringUtils.isNotEmpty(model.getRecipientName()) ? model.getRecipientName() : "";
                                }
                                if ("收件人电话".equals(recipientInfoList.get(k)))
                                {
                                    modelArr[z_r + k] = StringUtils.isNotEmpty(model.getRecipientPhone()) ? model.getRecipientPhone() : "";
                                }
                                if ("收件人地址".equals(recipientInfoList.get(k)))
                                {
                                    modelArr[z_r + k] =
                                        StringUtils.isNotEmpty(model.getRecipientAddress()) ? model.getRecipientAddress() : "";
                                }
                            }
                        //}
                    //}
                    z += recipientInfoList.size();
                }
                if (Collections3.isNotEmpty(costInfoList))
                {
                    //if (Collections3.isNotEmpty(model.getCostInfoList()))
                    //{
                        if (i < 1)
                        {
                            int z_c = z;
                            for (int k = 0; k < costInfoList.size(); k++)
                            {
                                if ("发票抬头".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] = StringUtils.isNotEmpty(model.getInvoiceTitle()) ? model.getInvoiceTitle() : "";
                                }
                                if ("付款方式".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] = StringUtils.isNotEmpty(model.getPayType()) ? model.getPayType() : "";
                                }
                                if ("付款状态".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] = StringUtils.isNotEmpty(model.getPayStatus()) ? model.getPayStatus() : "";
                                }
                                if ("减免金额".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getReduceAmount()) ? model.getReduceAmount() : "";
                                }
                                if ("抵用券".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getDiscountAmount()) ? model.getDiscountAmount() : "";
                                }
                                if ("减免原因".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] = StringUtils.isNotEmpty(model.getReduceReason()) ? model.getReduceReason() : "";
                                }
                                if ("减免状态".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] = StringUtils.isNotEmpty(model.getReduceStatus()) ? model.getReduceStatus() : "";
                                }
                                if ("减免审核通过时间".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] = StringUtils.isNotEmpty(model.getReduceCheckTime()) ? sdfss.format(model.getReduceCheckTime())  : "";
                                }
                                if ("应付款".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] = StringUtils.isNotEmpty(model.getAmount()) ? model.getAmount() : "";
                                }
                                if ("实付款".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getIncomingAmount()) ? model.getIncomingAmount() : "";
                                }
                                if ("付款时间".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] = StringUtils.isNotEmpty(model.getPaymentTime()) ? sdf.format(model.getPaymentTime())  : "";
                                }
                                if ("付款确认时间".equals(costInfoList.get(k)))
                                {
                                    modelArr[z_c + k] = StringUtils.isNotEmpty(model.getPaymentCheckTime()) ? sdfss.format(model.getPaymentCheckTime())  : "";
                                }
                            }
                        }
                    //}
                    z += costInfoList.size();
                }
                if (Collections3.isNotEmpty(refundInfoList))
                {
                    if (Collections3.isNotEmpty(model.getProductList()))
                    {
                        if (i < model.getProductList().size())
                        {
                            int z_ref = z;
                            for (int k = 0; k < refundInfoList.size(); k++)
                            {
                                if ("产品编号".equals(refundInfoList.get(k)))
                                {
                                    modelArr[z_ref + k] =
                                        StringUtils.isNotEmpty(model.getProductList().get(i).getProductCode()) ? model.getProductList().get(i).getProductCode() : "";
                                }
                                if ("产品名称".equals(refundInfoList.get(k)))
                                {
                                    modelArr[z_ref + k] =
                                        StringUtils.isNotEmpty(model.getProductList().get(i).getProductName()) ? model.getProductList().get(i).getProductName() : "";
                                }
                                if ("产品价格".equals(refundInfoList.get(k)))
                                {
                                    modelArr[z_ref + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getProductPrice())
                                        ? String.valueOf(model.getProductList().get(i).getProductPrice()) : "";
                                }
                                if ("退款金额".equals(refundInfoList.get(k)))
                                {
                                    modelArr[z_ref + k] =
                                        StringUtils.isNotEmpty(model.getProductList().get(i).getReplyAmount()) ? String.valueOf(model.getProductList().get(i).getReplyAmount()) : "";
                                }
                                if ("理由".equals(refundInfoList.get(k)))
                                {
                                    modelArr[z_ref + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getReplyReason()) ? model.getProductList().get(i).getReplyReason() : "";
                                }
                                if ("申请时间".equals(refundInfoList.get(k)))
                                {
                                    modelArr[z_ref + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getApplyTime()) ? sdfss.format(model.getProductList().get(i).getApplyTime())  : "";
                                }
                                if ("退款时间".equals(refundInfoList.get(k)))
                                {
                                    modelArr[z_ref + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getReplyCheckTime()) ? sdfss.format(model.getProductList().get(i).getReplyCheckTime())  : "";
                                }
                                if ("状态".equals(refundInfoList.get(k)))
                                {
                                    modelArr[z_ref + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getRefundStatus()) ? model.getProductList().get(i).getRefundStatus() : "";
                                }
                            }
                        }
                    }
                    z += refundInfoList.size();
                }
                if (Collections3.isNotEmpty(billingInfoList))
                {
                    if (Collections3.isNotEmpty(model.getInvoiceList()))
                    {
                        if (i < model.getInvoiceList().size())
                        {
                            int z_b = z;
                            for (int k = 0; k < billingInfoList.size(); k++)
                            {
                                if ("开票单号".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] = StringUtils.isNotEmpty(model.getInvoiceList().get(i).getDrawerNo()) ? model.getInvoiceList().get(i).getDrawerNo() : "";
                                }
                                if ("开票单位".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] = StringUtils.isNotEmpty(model.getInvoiceList().get(i).getInstitution()) ? model.getInvoiceList().get(i).getInstitution() : "";
                                }
                                if ("开票金额".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] =
                                        StringUtils.isNotEmpty(model.getInvoiceList().get(i).getAmount()) ? String.valueOf(model.getInvoiceList().get(i).getAmount()) : "";
                                }
                                if ("开票人".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] = StringUtils.isNotEmpty(model.getInvoiceList().get(i).getDrawerName()) ? model.getInvoiceList().get(i).getDrawerName() : "";
                                }
                                if ("开票时间".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] = StringUtils.isNotEmpty(model.getInvoiceList().get(i).getInvoiceTime()) ? model.getInvoiceList().get(i).getInvoiceTime() : "";
                                }
                                if ("领取人".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] = StringUtils.isNotEmpty(model.getInvoiceList().get(i).getReceiverName()) ? model.getInvoiceList().get(i).getReceiverName() : "";
                                }
                                if ("寄送状态".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] = StringUtils.isNotEmpty(model.getInvoiceList().get(i).getSendStatus()) ? model.getInvoiceList().get(i).getSendStatus() : "";
                                }
                                if ("快递类别".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] = StringUtils.isNotEmpty(model.getInvoiceList().get(i).getTransportType()) ? model.getInvoiceList().get(i).getTransportType() : "";
                                }
                                if ("快递单号".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] = StringUtils.isNotEmpty(model.getInvoiceList().get(i).getTrackNumber()) ? model.getInvoiceList().get(i).getTrackNumber() : "";
                                }
                                if ("寄送人".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] = StringUtils.isNotEmpty(model.getInvoiceList().get(i).getOperateName()) ? model.getInvoiceList().get(i).getOperateName() : "";
                                }
                                if ("寄送时间".equals(billingInfoList.get(k)))
                                {
                                    modelArr[z_b + k] = StringUtils.isNotEmpty(model.getInvoiceList().get(i).getSendTime()) ? model.getInvoiceList().get(i).getSendTime() : "";
                                }
                            }
                        }
                    }
                    z += billingInfoList.size();
                }
                if (Collections3.isNotEmpty(reportInfoList))
                {
                    if (Collections3.isNotEmpty(model.getProductList()))
                    {
                        if (i < model.getProductList().size())
                        {
                            int z_rep = z;
                            for (int k = 0; k < reportInfoList.size(); k++)
                            {
                                if ("报告编号".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getReportNo()) ? model.getProductList().get(i).getReportNo() : "";
                                }
                                if ("报告名称".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getReportName()) ? model.getProductList().get(i).getReportName() : "";
                                }
                                if ("出报告时间".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getReportDate()) ? sdfss.format(model.getProductList().get(i).getReportDate())  : "";
                                }
                                if ("出报告人".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] =
                                        StringUtils.isNotEmpty(model.getProductList().get(i).getReportReceiverName()) ? model.getProductList().get(i).getReportReceiverName() : "";
                                }
                                if ("一审完成时间".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getFirstCheckTime()) ? sdfss.format(model.getProductList().get(i).getFirstCheckTime())  : "";
                                }
                                if ("一审审核人".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getFirstCheckName()) ? model.getProductList().get(i).getFirstCheckName() : "";
                                }
                                if ("二审完成时间".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getSecondCheckTime()) ? sdfss.format(model.getProductList().get(i).getSecondCheckTime())  : "";
                                }
                                if ("二审审核人".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getSecondCheckName()) ? model.getProductList().get(i).getSecondCheckName() : "";
                                }
                                if ("状态".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getReportStatus()) ? model.getProductList().get(i).getReportStatus() : "";
                                }
                                if ("寄送状态".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getTransportStatus()) ? model.getProductList().get(i).getTransportStatus() : "";
                                }
                                if ("寄送内容".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getTransportContent()) ? model.getProductList().get(i).getTransportContent() : "";
                                }
                                if ("快递类别".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getTrackType()) ? model.getProductList().get(i).getTrackType() : "";
                                }
                                if ("快递单号".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getTrackNumber()) ? model.getProductList().get(i).getTrackNumber() : "";
                                }
                                if ("寄送人".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getTransportName()) ? model.getProductList().get(i).getTransportName() : "";
                                }
                                if ("寄送时间".equals(reportInfoList.get(k)))
                                {
                                    modelArr[z_rep + k] = StringUtils.isNotEmpty(model.getProductList().get(i).getTransportTime()) ? sdfss.format(model.getProductList().get(i).getTransportTime())  : "";
                                }
                            }
                        }
                    }
                    z += reportInfoList.size();
                }
                
                results.add(modelArr);
            }
        }
        return results;
        
    }
    
    private int maxSize(ReportSystemOrderInfo model,List<String> productInfoList, List<String> examineeInfoList,
        List<String> diseaseInfoList, List<String> subsidiarySampleInfoList, List<String> recipientInfoList, List<String> costInfoList, List<String> refundInfoList, List<String> billingInfoList,
        List<String> reportInfoList)
    {
        int a = 1;
        if(Collections3.isNotEmpty(productInfoList))
        {
            if (Collections3.isNotEmpty(model.getProductList()))
            {
                a = Math.max(a, model.getProductList().size());
            }
        }
        /*if(Collections3.isNotEmpty(examineeInfoList))
        {
            if (Collections3.isNotEmpty(model.getExamineeInfoList()))
            {
                a = Math.max(a, model.getExamineeInfoList().size());
            }
        }*/
        /*if(Collections3.isNotEmpty(diseaseInfoList))
        {
            if (Collections3.isNotEmpty(model.getDiseaseInfoList()))
            {
                a = Math.max(a, model.getDiseaseInfoList().size());
            }
        }*/
        if(Collections3.isNotEmpty(subsidiarySampleInfoList))
        {
            if (Collections3.isNotEmpty(model.getSampleList()))
            {
                a = Math.max(a, model.getSampleList().size());
            }
        }
        /*if(Collections3.isNotEmpty(recipientInfoList))
        {
            if (Collections3.isNotEmpty(model.getRecipientInfoList()))
            {
                a = Math.max(a, model.getRecipientInfoList().size());
            }
        }*/
        /*if(Collections3.isNotEmpty(costInfoList))
        {
            if (Collections3.isNotEmpty(model.getCostInfoList()))
            {
                a = Math.max(a, model.getCostInfoList().size());
            }
        }*/
        if(Collections3.isNotEmpty(refundInfoList))
        {
            if (Collections3.isNotEmpty(model.getProductList()))
            {
                a = Math.max(a, model.getProductList().size());
            }
        }
        if(Collections3.isNotEmpty(billingInfoList))
        {
            if (Collections3.isNotEmpty(model.getInvoiceList()))
            {
                a = Math.max(a, model.getInvoiceList().size());
            }
        }
        if(Collections3.isNotEmpty(reportInfoList))
        {
            if (Collections3.isNotEmpty(model.getProductList()))
            {
                a = Math.max(a, model.getProductList().size());
            }
        }
        return a;
        
    }
    
    @Override
    public void renderMergedOutputModel(HttpServletRequest request, HttpServletResponse response, String rootPath)
        throws Exception
    {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        File file = new File(rootPath + "orderReportForm.xls");
        if (null == file)
        {
            return;
        }
        SimpleDateFormat formate = new SimpleDateFormat("yyyyMMddhhmmss");
        String datFormate = formate.format(new Date());
        String txtName = "order_report_form" + "_" + datFormate + ".xls";
        inputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        response.reset();// 清空response的buffer
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/vnd.ms-excel; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + txtName);
        response.setCharacterEncoding("utf-8");
        outputStream = new BufferedOutputStream(response.getOutputStream());
        try
        {
            outputStream.write(buffer);
            outputStream.flush();
        }
        finally
        {
            if (null != inputStream)
            {
                inputStream.close();
            }
            
            if (null != outputStream)
            {
                outputStream.close();
            }
        }
        if (null != file)
        {
            file.delete();
        }
    }
  //-------------------------------------------合同---------------------------------------------------------------------------
    @Override
    public List<ReportSystemContractInfo> contractReportFormListForWkcreate(ContractSystemRequest searcher)
    {
        List<ReportSystemContractInfo> records = Lists.newArrayList();
        
        int pageNo = 1;
        int pageSize = 50;
        Pagination<ReportSystemContractInfo> pagination=null;
        do
        {
            searcher.setPageNo(pageNo++);
            searcher.setPageSize(pageSize);
            Pagination<ReportSystemContractInfo> model = contractSystemInfoPaging(searcher);
            if(null != model)
            {
                pagination = new Pagination<>();
                pagination.setPageNo(model.getPageNo());
                pagination.setPageSize(model.getPageSize());
                pagination.setTotalCount(model.getTotalCount());
                if (Collections3.isNotEmpty(model.getRecords()))
                {
                    records.addAll(model.getRecords());
                }
            }
            if (log.isDebugEnabled())
            {
                log.debug("Search records for page {} successful, total count {}, total page {}.",
                        pageNo,
                        pagination.getTotalCount(),
                        pagination.getTotalPage());
            }

        } while (!pagination.isLastPage());

        if (log.isDebugEnabled())
        {
            log.debug("Search records successful, total count {}.", records.size());
        }
        return records;
    }

    private Pagination<ReportSystemContractInfo> contractSystemInfoPaging(ContractSystemRequest searcher) {

        String url = GatewayService.getServiceUrl("/export/system/contractSystemInfoList");
        ResponseEntity<Pagination<ReportSystemContractInfo>> exchange =
                template.exchange(url,
                        HttpMethod.POST,
                        new HttpEntity<ContractSystemRequest>(searcher),
                        new ParameterizedTypeReference<Pagination<ReportSystemContractInfo>>()
                        {
                        });
        return exchange.getBody();
    }
    
    @Override
    public String writerContract(String fileName, String fileType, List<ReportSystemContractInfo> contracts, Map<String, List<String>> mapTitle) throws Exception
    {
        Workbook wb = null;
        String time = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        File file = new File(fileName +"_"+ time + "." +fileType);
        Sheet sheet = null;
        // 创建工作文档对象
        if (!file.exists())
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();
                
            }
            else if (fileType.equals("xlsx"))
            {
                
                wb = new XSSFWorkbook();
            }
            // 创建sheet对象
            sheet = wb.createSheet("sheet1");
            OutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            
        }
        else
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();
                
            }
            else if (fileType.equals("xlsx"))
            {
                wb = new XSSFWorkbook();
                
            }
        }
        // 创建sheet对象
        if (sheet == null)
        {
            sheet = wb.createSheet("sheet1");
        }
        
        Row row = null;
        Cell cell = null;
        // --------一级列头设置----------
        CellStyle style = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short)260);
        style.setFont(font);
        // ----------二级列头设置---------
        CellStyle style2 = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style2.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style2.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style2.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeight((short)240);
        // font2.setColor(HSSFColor.RED.index);
        style2.setFont(font2);
        // -----------内容设置----------------
        CellStyle style3 = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style3.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style3.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style3.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font3 = wb.createFont();
        font3.setFontName("宋体");
        // font3.setFontHeight((short)240);
        style3.setFont(font3);
        
        List<ContractReportFormOneHeadModel> oneHeadList = Lists.newArrayList();// 一级列头(不重复)
        List<String> oneHeadForTwoList = Lists.newArrayList();// 一级列头(根据二级标题重复生成一级标题)
        List<String> baseInfoList = Lists.newArrayList();
        List<String> partyAInfoList = Lists.newArrayList();
        List<String> partyBInfoList = Lists.newArrayList();
        List<String> successDeliveryList = Lists.newArrayList();
        List<String> settlementInfoList = Lists.newArrayList();
        List<String> productInfoList = Lists.newArrayList();
        List<String> testInfoList = Lists.newArrayList();
        List<String> contractOriginalList = Lists.newArrayList();
        List<String> contractUserInfoList = Lists.newArrayList();
        List<String> changeUserInfoList = Lists.newArrayList();
        List<String> twoHeadList = Lists.newArrayList();// 二级列头
        for (Map.Entry<String, List<String>> entry : mapTitle.entrySet())
        {
            ContractReportFormOneHeadModel oneHeadModel = new ContractReportFormOneHeadModel();
            if ("baseInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("合同信息");
                oneHeadModel.setIndex(1);
                baseInfoList = entry.getValue();
            }
            else if ("partyAInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("甲方信息");
                oneHeadModel.setIndex(2);
                partyAInfoList = entry.getValue();
            }
            else if ("partyBInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("乙方信息");
                oneHeadModel.setIndex(3);
                partyBInfoList = entry.getValue();
            }
            else if ("successDelivery".equals(entry.getKey()))
            {
                oneHeadModel.setName("成果交付");
                oneHeadModel.setIndex(4);
                successDeliveryList = entry.getValue();
            }
            else if ("settlementInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("结算方式");
                oneHeadModel.setIndex(5);
                settlementInfoList = entry.getValue();
            }
            else if ("productInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("产品列表");
                oneHeadModel.setIndex(6);
                productInfoList = entry.getValue();
            }
            else if ("testInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("实验信息");
                oneHeadModel.setIndex(7);
                testInfoList = entry.getValue();
            }
            else if ("contractOriginal".equals(entry.getKey()))
            {
                oneHeadModel.setName("合同原件");
                oneHeadModel.setIndex(8);
                contractOriginalList = entry.getValue();
            }
            else if ("contractUserInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("合同对象信息");
                oneHeadModel.setIndex(9);
                contractUserInfoList = entry.getValue();
            }
            else if ("changeUserInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("变更业务员信息");
                oneHeadModel.setIndex(10);
                changeUserInfoList = entry.getValue();
            }
            oneHeadList.add(oneHeadModel);
        }
        // 排序
        oneHeadList.sort(Comparator.comparing(ContractReportFormOneHeadModel::getIndex));
        String[] headnum0 = new String[oneHeadList.size()];// 对应excel中的行和列，下标从0开始{"开始行,结束行,开始列,结束列"}
        int startIndex = 0;
        int endIndex = 0;
        int i = 0;
        if (Collections3.isNotEmpty(baseInfoList))
        {
            endIndex += baseInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += baseInfoList.size();
            for (int j = 0; j < baseInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(baseInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(partyAInfoList))
        {
            endIndex += partyAInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += partyAInfoList.size();
            for (int j = 0; j < partyAInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(partyAInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(partyBInfoList))
        {
            endIndex += partyBInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += partyBInfoList.size();
            for (int j = 0; j < partyBInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(partyBInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(successDeliveryList))
        {
            endIndex += successDeliveryList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += successDeliveryList.size();
            for (int j = 0; j < successDeliveryList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(successDeliveryList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(settlementInfoList))
        {
            endIndex += settlementInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += settlementInfoList.size();
            for (int j = 0; j < settlementInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(settlementInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(productInfoList))
        {
            endIndex += productInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += productInfoList.size();
            for (int j = 0; j < productInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(productInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(testInfoList))
        {
            endIndex += testInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += testInfoList.size();
            for (int j = 0; j < testInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(testInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(contractOriginalList))
        {
            endIndex += contractOriginalList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += contractOriginalList.size();
            for (int j = 0; j < contractOriginalList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(contractOriginalList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(contractUserInfoList))
        {
            endIndex += contractUserInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += contractUserInfoList.size();
            for (int j = 0; j < contractUserInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(contractUserInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(changeUserInfoList))
        {
            endIndex += changeUserInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += changeUserInfoList.size();
            for (int j = 0; j < changeUserInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(changeUserInfoList.get(j));
            }
            i++;
        }
        for (int j = 0; j < headnum0.length; j++)
        {
            String str = headnum0[j];
            String[] indexArr = str.split(",");
            headnum0[j] = indexArr[0] + "," + indexArr[1] + "," + indexArr[2] + "," + String.valueOf(Integer.parseInt(indexArr[3]) - 1);
        }
        
        row = sheet.createRow(0); // 创建第1行
        for (int j = 0; j < endIndex; j++)
        {
            cell = row.createCell(j);
            cell.setCellValue(oneHeadForTwoList.get(j));
            cell.setCellStyle(style); // 样式，居中
            sheet.setColumnWidth(j, 20 * 256);
        }
        row.setHeight((short)450);
        // 动态合并单元格
        for (int j = 0; j < headnum0.length; j++)
        {
            String[] temp = headnum0[j].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        row = sheet.createRow(1); // 创建第2行
        for (int j = 0; j < twoHeadList.size(); j++)
        {
            cell = row.createCell(j);
            cell.setCellValue(twoHeadList.get(j));
            cell.setCellStyle(style2); // 样式，居中
        }
        List<String[]> datalist = Lists.newArrayList();
        for (ReportSystemContractInfo model : contracts)
        {
            datalist.addAll(wrapObjectToListString(model,
                endIndex,
                baseInfoList,
                partyAInfoList,
                partyBInfoList,
                successDeliveryList,
                settlementInfoList,
                productInfoList,
                testInfoList,
                contractOriginalList,
                contractUserInfoList,
                changeUserInfoList));
        }
        ExcelUtil excel = new ExcelUtil();
        String path2 = file.getPath();
        excel.exportObjects2ModuleExcel(datalist, 2, wb, "sheet1", true, path2);
        wb.write(new FileOutputStream(path2));
        return file.getAbsolutePath();
    }
    
    private List<String[]> wrapObjectToListString(ReportSystemContractInfo model, int endIndex, List<String> baseInfoList, List<String> partyAInfoList, List<String> partyBInfoList, List<String> successDeliveryList, List<String> settlementInfoList, List<String> productInfoList, List<String> testInfoList, List<String> contractOriginalList, List<String> contractUserInfoList, List<String> changeUserInfoList)
    {
        List<String[]> results = Lists.newArrayList();
        if (null != model)
        {
            int maxSize =
                maxSizeContract(model,
                    partyAInfoList,
                    partyBInfoList,
                    successDeliveryList,
                    settlementInfoList,
                    productInfoList,
                    testInfoList,
                    contractOriginalList,
                    contractUserInfoList,
                    changeUserInfoList);
            for (int i = 0; i < maxSize; i++)
            {
                int z = 0;
                String[] modelArr = new String[endIndex];
                if (i < 1)
                {
                    for (int k = 0; k < baseInfoList.size(); k++)
                    {
                        if ("合同编号".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getCode()) ? model.getCode() : "";
                        }
                        if ("合同名称".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getName()) ? model.getName() : "";
                        }
                        if ("合同状态".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getStatus()) ? model.getStatus() : "";
                        }
                        if ("合同有效期".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getStartTime()) ? model.getStartTime() : "";
                        }
                        if ("合同截止日期".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getEndTime()) ? model.getEndTime() : "";
                        }
                        if ("营销中心".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getMarketingCenter()) ? model.getMarketingCenter() : "";
                        }
                        if ("业务员".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getSignUser()) ? model.getSignUser() : "";
                        }
                        if ("签订日期".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getSignDate()) ? model.getSignDate() : "";
                        }
                        if ("是否入院".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getHospitalAdmited()) ? model.getHospitalAdmited() : "";
                        }
                        if ("开票形式".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getInvoiceMethod()) ? model.getInvoiceMethod() : "";
                        }
                        if ("启动方式".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getStartMode()) ? model.getStartMode() : "";
                        }
                        if ("备注说明".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getRemark()) ? model.getRemark() : "";
                        }
                    }
                }
                z = baseInfoList.size();
                if (Collections3.isNotEmpty(partyAInfoList))
                {
                    //if (Collections3.isNotEmpty(model.getPartyAInfoList()))
                    //{
                        if (i < 1)
                        {
                            int z_pa = z;
                            for (int k = 0; k < partyAInfoList.size(); k++)
                            {
                                if ("甲方".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getAcompanyName()) ? model.getAcompanyName() : "";
                                }
                                if ("联系人".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getAcontactName()) ? model.getAcontactName() : "";
                                }
                                if ("电话".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getAcontactPhone()) ? model.getAcontactPhone() : "";
                                }
                                if ("邮箱".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getAcontactEmail()) ? model.getAcontactEmail() : "";
                                }
                                if ("邮编".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getZipCode()) ? model.getZipCode() : "";
                                }
                                if ("发票抬头".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getInvoiceTitle()) ? model.getInvoiceTitle() : "";
                                }
                                if ("地址".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getAddress()) ? model.getAddress(): "";
                                }
                            }
                        }
                    //}
                    z += partyAInfoList.size();
                }
                if (Collections3.isNotEmpty(partyBInfoList))
                {
                    //if (Collections3.isNotEmpty(model.getPartyBInfoList()))
                    //{
                        if (i < 1)
                        {
                            int z_pb = z;
                            for (int k = 0; k < partyBInfoList.size(); k++)
                            {
                                if ("乙方".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getBcompanyName()) ? model.getBcompanyName() : "";
                                }
                                if ("联系人".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getBcontactName()) ? model.getBcontactName() : "";
                                }
                                if ("电话".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getBcontactPhone()) ? model.getBcontactPhone() : "";
                                }
                                if ("开户银行".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getDepositBank()) ? model.getDepositBank() : "";
                                }
                                if ("账号".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getAccountNo()) ? model.getAccountNo() : "";
                                }
                                if ("开户名称".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getAccountName()) ? model.getAccountName() : "";
                                }
                            }
                        }
                    //}
                    z += partyBInfoList.size();
                }
                if (Collections3.isNotEmpty(successDeliveryList))
                {
                    //if (Collections3.isNotEmpty(model.getSuccessDeliveryList()))
                    //{
                        if (i < 1)
                        {
                            int z_s = z;
                            for (int k = 0; k < successDeliveryList.size(); k++)
                            {
                                if ("交付形式".equals(successDeliveryList.get(k)))
                                {
                                    modelArr[z_s + k] =
                                        StringUtils.isNotEmpty(model.getDeliveryMode()) ? model.getDeliveryMode() : "";
                                }
                                if ("交付方式".equals(successDeliveryList.get(k)))
                                {
                                    modelArr[z_s + k] =
                                        StringUtils.isNotEmpty(model.getDeliveryResult()) ? model.getDeliveryResult() : "";
                                }
                            }
                        }
                    //}
                    z += successDeliveryList.size();
                }
                if (Collections3.isNotEmpty(settlementInfoList))
                {
                    //if (Collections3.isNotEmpty(model.getSettlementInfoList()))
                    //{
                        if (i < 1)
                        {
                            int z_se = z;
                            for (int k = 0; k < settlementInfoList.size(); k++)
                            {
                                if ("结算方式".equals(settlementInfoList.get(k)))
                                {
                                    modelArr[z_se + k] =
                                        StringUtils.isNotEmpty(model.getSettlementMode()) ? model.getSettlementMode() : "";
                                }
                                if ("付款明细".equals(settlementInfoList.get(k)))
                                {
                                    modelArr[z_se + k] =
                                        StringUtils.isNotEmpty(model.getRemark()) ? model.getRemark() : "";
                                }
                                if ("合同总额".equals(settlementInfoList.get(k)))
                                {
                                    modelArr[z_se + k] =
                                        StringUtils.isNotEmpty(model.getAmount()) ? model.getAmount() : "";
                                }
                            }
                        }
                    //}
                    z += settlementInfoList.size();
                }
                if (Collections3.isNotEmpty(productInfoList))
                {
                    if (Collections3.isNotEmpty(model.getProductList()))
                    {
                        if (i < model.getProductList().size())
                        {
                            int z_pr = z;
                            for (int k = 0; k < productInfoList.size(); k++)
                            {
                                if ("产品名称".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_pr + k] =
                                        StringUtils.isNotEmpty(model.getProductList().get(i).getProductName()) ? model.getProductList()
                                            .get(i)
                                            .getProductName() : "";
                                }
                                if ("单价".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_pr + k] =
                                        StringUtils.isNotEmpty(model.getProductList().get(i).getPrice()) ? model.getProductList().get(i).getPrice() : "";
                                }
                                if ("数量".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_pr + k] =
                                        StringUtils.isNotEmpty(model.getProductList().get(i).getCount()) ? model.getProductList().get(i).getCount() : "";
                                }
                                if ("价格".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_pr + k] =
                                        StringUtils.isNotEmpty(model.getProductList().get(i).getAmount()) ? model.getProductList().get(i).getAmount() : "";
                                }
                                if ("服务要求".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_pr + k] =
                                        StringUtils.isNotEmpty(model.getProductList().get(i).getRequirement()) ? model.getProductList()
                                            .get(i)
                                            .getRequirement() : "";
                                }
                            }
                        }
                    }
                    z += productInfoList.size();
                }
                if (Collections3.isNotEmpty(testInfoList))
                {
                    if (Collections3.isNotEmpty(model.getSampleList()))
                    {
                        if (i < model.getSampleList().size())
                        {
                            int z_t = z;
                            for (int k = 0; k < testInfoList.size(); k++)
                            {
                                if ("样本种属".equals(testInfoList.get(k)))
                                {
                                    modelArr[z_t + k] =
                                        StringUtils.isNotEmpty(model.getSampleList().get(i).getSampleKind()) ? model.getSampleList().get(i).getSampleKind()
                                            : "";
                                }
                                if ("样本类型".equals(testInfoList.get(k)))
                                {
                                    modelArr[z_t + k] =
                                        StringUtils.isNotEmpty(model.getSampleList().get(i).getSampleType()) ? model.getSampleList().get(i).getSampleType()
                                            : "";
                                }
                            }
                        }
                    }
                    z += testInfoList.size();
                }
                if (Collections3.isNotEmpty(contractOriginalList))
                {
                    //if (Collections3.isNotEmpty(model.getContractOriginalList()))
                    //{
                        if (i < 1)
                        {
                            int z_co = z;
                            for (int k = 0; k < contractOriginalList.size(); k++)
                            {
                                if ("合同文件名".equals(contractOriginalList.get(k)))
                                {
                                    modelArr[z_co + k] =
                                        StringUtils.isNotEmpty(model.getOriginalName()) ? model.getOriginalName() : "";
                                }
                                if ("更新时间".equals(contractOriginalList.get(k)))
                                {
                                    modelArr[z_co + k] =
                                        StringUtils.isNotEmpty(model.getUpdateTime()) ? model.getUpdateTime() : "";
                                }
                            }
                        }
                    //}
                    z += contractOriginalList.size();
                }
                if (Collections3.isNotEmpty(contractUserInfoList))
                {
                    if (Collections3.isNotEmpty(model.getCustomerList()))
                    {
                        if (i < model.getCustomerList().size())
                        {
                            int z_cu = z;
                            for (int k = 0; k < contractUserInfoList.size(); k++)
                            {
                                if ("姓名".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getCustomerList().get(i).getName()) ? model.getCustomerList()
                                            .get(i)
                                            .getName() : "";
                                }
                                if ("手机号".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getCustomerList().get(i).getPhone()) ? model.getCustomerList()
                                            .get(i)
                                            .getPhone() : "";
                                }
                                if ("性别".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getCustomerList().get(i).getSex()) ? model.getCustomerList()
                                            .get(i)
                                            .getSex() : "";
                                }
                                if ("科室".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getCustomerList().get(i).getDept()) ? model.getCustomerList()
                                            .get(i)
                                            .getDept() : "";
                                }
                                if ("单位".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getCustomerList().get(i).getInstitutionName()) ? model.getCustomerList()
                                            .get(i)
                                            .getInstitutionName() : "";
                                }
                                if ("职称".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getCustomerList().get(i).getPosition()) ? model.getCustomerList()
                                            .get(i)
                                            .getPosition() : "";
                                }
                            }
                        }
                    }
                    z += contractUserInfoList.size();
                }
                if (Collections3.isNotEmpty(changeUserInfoList))
                {
                    if (Collections3.isNotEmpty(model.getChangeList()))
                    {
                        if (i < model.getChangeList().size())
                        {
                            int z_c = z;
                            for (int k = 0; k < changeUserInfoList.size(); k++)
                            {
                                if ("变更前业务员".equals(changeUserInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getChangeList().get(i).getBeforeSignUser()) ? model.getChangeList()
                                            .get(i)
                                            .getBeforeSignUser() : "";
                                }
                                if ("变更后业务员".equals(changeUserInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getChangeList().get(i).getAfterSignUser()) ? model.getChangeList()
                                            .get(i)
                                            .getAfterSignUser() : "";
                                }
                                if ("操作人".equals(changeUserInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getChangeList().get(i).getOperateName()) ? model.getChangeList()
                                            .get(i)
                                            .getOperateName() : "";
                                }
                                if ("操作时间".equals(changeUserInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getChangeList().get(i).getOperateTime()) ? model.getChangeList()
                                            .get(i)
                                            .getOperateTime() : "";
                                }
                            }
                        }
                    }
                    z += changeUserInfoList.size();
                }
                results.add(modelArr);
            }
        }
        return results;
        
    }
    
    private int maxSizeContract(ReportSystemContractInfo model, List<String> partyAInfoList, List<String> partyBInfoList, List<String> successDeliveryList, List<String> settlementInfoList, List<String> productInfoList, List<String> testInfoList, List<String> contractOriginalList, List<String> contractUserInfoList, List<String> changeUserInfoList)
    {
        int a = 1;
        /*if (Collections3.isNotEmpty(partyAInfoList))
        {
            if (Collections3.isNotEmpty(model.getPartyAInfoList()))
            {
                a = Math.max(a, model.getPartyAInfoList().size());
            }
        }*/
        /*if (Collections3.isNotEmpty(partyBInfoList))
        {
            if (Collections3.isNotEmpty(model.getPartyBInfoList()))
            {
                a = Math.max(a, model.getPartyBInfoList().size());
            }
        }*/
        /*if (Collections3.isNotEmpty(successDeliveryList))
        {
            if (Collections3.isNotEmpty(model.getSuccessDeliveryList()))
            {
                a = Math.max(a, model.getSuccessDeliveryList().size());
            }
        }*/
        /*if (Collections3.isNotEmpty(settlementInfoList))
        {
            if (Collections3.isNotEmpty(model.getSettlementInfoList()))
            {
                a = Math.max(a, model.getSettlementInfoList().size());
            }
        }*/
        if (Collections3.isNotEmpty(productInfoList))
        {
            if (Collections3.isNotEmpty(model.getProductList()))
            {
                a = Math.max(a, model.getProductList().size());
            }
        }
        if (Collections3.isNotEmpty(testInfoList))
        {
            if (Collections3.isNotEmpty(model.getSampleList()))
            {
                a = Math.max(a, model.getSampleList().size());
            }
        }
        /*if (Collections3.isNotEmpty(contractOriginalList))
        {
            if (Collections3.isNotEmpty(model.getContractOriginalList()))
            {
                a = Math.max(a, model.getContractOriginalList().size());
            }
        }*/
        if (Collections3.isNotEmpty(contractUserInfoList))
        {
            if (Collections3.isNotEmpty(model.getCustomerList()))
            {
                a = Math.max(a, model.getCustomerList().size());
            }
        }
        if (Collections3.isNotEmpty(changeUserInfoList))
        {
            if (Collections3.isNotEmpty(model.getChangeList()))
            {
                a = Math.max(a, model.getChangeList().size());
            }
        }
        return a;
    }
//-------------------------------------------客户---------------------------------------------------------------------------
    @Override
    public List<ReportSystemCustomerInfo> customerOrderReportFormListForWkcreate(CustomerSystemRequest searcher)
    {
        List<ReportSystemCustomerInfo> records = Lists.newArrayList();
//        List<ReportSystemCustomerInfo> customerInfoList = customerService.getCustomerForCustomerInfoList(searcher);
        int pageNo = 1;
        int pageSize = 50;
        Pagination<ReportSystemCustomerInfo> pagination=null;
        do
        {
            searcher.setPageNo(pageNo++);
            searcher.setPageSize(pageSize);
            Pagination<ReportSystemCustomerInfo> model = customerSystemInfoPaging(searcher);
            if(null != model)
            {
                pagination = new Pagination<>();
                pagination.setPageNo(model.getPageNo());
                pagination.setPageSize(model.getPageSize());
                pagination.setTotalCount(model.getTotalCount());
                if (Collections3.isNotEmpty(model.getRecords()))
                {
                    records.addAll(model.getRecords());
                }
            }
            if (log.isDebugEnabled())
            {
                log.debug("Search records for page {} successful, total count {}, total page {}.",
                        pageNo,
                        pagination.getTotalCount(),
                        pagination.getTotalPage());
            }

        } while (!pagination.isLastPage());

        if (log.isDebugEnabled())
        {
            log.debug("Search records successful, total count {}.", records.size());
        }
        return records;
    }

    private Pagination<ReportSystemCustomerInfo> customerSystemInfoPaging(CustomerSystemRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/export/system/customerSystemInfoList");
        ResponseEntity<Pagination<ReportSystemCustomerInfo>> exchange =
                template.exchange(url,
                        HttpMethod.POST,
                        new HttpEntity<CustomerSystemRequest>(searcher),
                        new ParameterizedTypeReference<Pagination<ReportSystemCustomerInfo>>()
                        {
                        });
        return exchange.getBody();
    }

    @Override
    public String writerCustomer(String fileName, String fileType, List<ReportSystemCustomerInfo> models, Map<String, List<String>> mapTitle) throws Exception
    {
        Workbook wb = null;
        String time = DateUtils.formatDate(new Date(), "yyyyMMdd");
        File file = new File(fileName +"_"+ time + "." +fileType);
        Sheet sheet = null;
        // 创建工作文档对象
        if (!file.exists())
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();

            }
            else if (fileType.equals("xlsx"))
            {

                wb = new XSSFWorkbook();
            }
            // 创建sheet对象
            sheet = wb.createSheet("sheet1");
            OutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();

        }
        else
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();

            }
            else if (fileType.equals("xlsx"))
            {
                wb = new XSSFWorkbook();

            }
        }
        // 创建sheet对象
        if (sheet == null)
        {
            sheet = wb.createSheet("sheet1");
        }

        Row row = null;
        Cell cell = null;
        // --------列头设置----------
        CellStyle style = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short)260);
        style.setFont(font);
        // -----------内容设置----------------
        CellStyle style2 = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style2.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style2.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style2.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font2 = wb.createFont();
        font2.setFontName("宋体");
        // font2.setFontHeight((short)240);
        style2.setFont(font2);

        List<String> customerInfoList = Lists.newArrayList();
        for (Map.Entry<String, List<String>> entry : mapTitle.entrySet())
        {
            customerInfoList = entry.getValue();
        }

        row = sheet.createRow(0); // 创建第1行
        if (Collections3.isNotEmpty(customerInfoList)) {
            for (int j = 0; j < customerInfoList.size(); j++)
            {
                cell = row.createCell(j);
                cell.setCellValue(customerInfoList.get(j));
                cell.setCellStyle(style); // 样式，居中
            }
        }

        List<String[]> datalist = Lists.newArrayList();
        for (ReportSystemCustomerInfo model : models)
        {
            datalist.addAll(wrapObjectToListString(model,customerInfoList.size(),customerInfoList));
        }


        ExcelUtil excel = new ExcelUtil();
        String path = file.getPath();
        excel.exportObjects2ModuleExcel(datalist, 1, wb, "sheet1", true, path);
        wb.write(new FileOutputStream(path));
        return file.getAbsolutePath();

    }

    private Collection<? extends String[]> wrapObjectToListString(ReportSystemCustomerInfo model, int endIndex, List<String> customerInfoList)
    {
        List<String[]> results = Lists.newArrayList();
        if (null != model)
        {
            String[] modelArr = new String[endIndex];
            for (int k = 0; k < customerInfoList.size(); k++) {
                if ("姓名".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getCustomerName()) ? model.getCustomerName() : "";
                }
                if ("性别".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getSex()) ? model.getSex() : "";
                }
                if ("状态".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getActivateStatus()) ? model.getActivateStatus() : "";
                }
                if ("所属单位".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getCompanyName()) ? model.getCompanyName() : "";
                }
                if ("科室".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getDept()) ? model.getDept() : "";
                }
                if ("邮箱".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getEmail()) ? model.getEmail() : "";
                }
                if ("手机号".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getPhoneNum()) ? model.getPhoneNum() : "";
                }
                if ("办公楼/房间号".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getRoomNum()) ? model.getRoomNum() : "";
                }
                if ("座机".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getLandLine()) ? model.getLandLine() : "";
                }
                if ("出生年月".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getBirthday()) ? model.getBirthday() : "";
                }
                if ("职位/职称".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getPosition()) ? model.getPosition() : "";
                }
                if ("合作方向".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getCooperate()) ? model.getCooperate() : "";
                }
                if ("是否参与分析".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getAnalysisType()) ? model.getAnalysisType() : "";
                }
                if ("单位地址".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getAddress()) ? model.getAddress() : "";
                }
                if ("就诊特点".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getCharacteristic()) ? model.getCharacteristic() : "";
                }
                if ("擅长领域".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getResearchFiled()) ? model.getResearchFiled() : "";
                }
                if ("简介".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getIntroduction()) ? model.getIntroduction() : "";
                }
                if ("附属账号数".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getCountSubCustomer()) ? String.valueOf(model.getCountSubCustomer()) : "";
                }
                if ("附属账号信息".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getSubCustomer()) ? model.getSubCustomer() : "";
                }
                if ("初始业务员".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getCreatorName()) ? model.getCreatorName() : "";
                }
                if ("当前业务员".equals(customerInfoList.get(k))) {
                    modelArr[k] = StringUtils.isNotEmpty(model.getCurrentName()) ? model.getCurrentName() : "";
                }

            }
            results.add(modelArr);
        }
        return results;
    }

    @Override
    public String exportOtherReportForm(CycleSystemRequest searcher)
    {
        InputStream in = null;
        String time = DateUtils.formatDate(new Date(), "yyyyMMdd");
        File file = new File("cycle_report_form_" + time + ".xlsx");
        Workbook wbModule;
        List<OtherReportFormModel> modelList = getCycleReportFormList(searcher);
        try
        {
            in = TestingTestTasksService.class.getResourceAsStream("/taskTemplate/otherReportForm/CYCLE_REPORT_FORM.xlsx");
            TestSheetService.inputstreamToFile(in, file);
            String path = file.getPath();
            
            ExcelUtil excel = new ExcelUtil();
            wbModule = excel.getTempWorkbook(path);
            List<String[]> datalist = wrapTaskToNgsCapNGS(modelList);
            List<String[]> dataList2 = wrapTaskOutNgsCapNGS(modelList);
            excel.exportObjects2ModuleExcel(datalist, 1, wbModule, "NGS和CapNGS检测周期报表", true, path);
            excel.exportObjects2ModuleExcel(dataList2, 1, wbModule, "其他检测周期报表", true, path);
            wbModule.write(new FileOutputStream(path));
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return file.getAbsolutePath();
    }
    
    private List<String[]> wrapTaskToNgsCapNGS(List<OtherReportFormModel> list)
    {
        List<String[]> results = Lists.newArrayList();
        if (Collections3.isNotEmpty(list))
        {
            for (OtherReportFormModel model : list)
            {
                if ("NGS".equals(model.getTestingMethods()) || "CapNGS".equals(model.getTestingMethods()))
                {
                    String result[] = new String[35];
                    result[0] = model.getOrderCode();
                    result[1] = model.getMarketingCenter();
                    result[2] = model.getTestingStatus();
                    result[3] = model.getProductName();
                    result[4] = model.getTestingMethods();
                    result[5] = model.getContractName();
                    result[6] = model.getSampleCode();
                    result[7] = model.getExamineeName();
                    result[8] = model.getCustomerName();
                    result[9] = model.getCompanyName();
                    result[10] = model.getCreateName();
                    result[11] = model.getCycleValue();
                    result[12] = model.getSubmitTime();
                    result[13] = model.getPlanFinishTime();
                    result[14] = model.getActualFinishTime();
                    result[15] = model.getIsOnTime();
                    result[16] = model.getPostponeDay();
                    result[17] = model.getActualFinishDay();
                    result[18] = model.getResampleCount();
                    result[19] = model.getNgsseqCount();
                    result[20] = model.getLane();
                    result[21] = model.getSampleReceivedDay();
                    result[22] = model.getOrderPaymentDay();
                    result[23] = model.getTestingStartDay();
                    result[24] = model.getDnaDay();
                    result[25] = model.getLibCreateDay();
                    result[26] = model.getLibCatchDay();
                    result[27] = model.getRqtDay();
                    result[28] = model.getNgsseqDay();
                    result[29] = model.getBiologyDay();
                    result[30] = model.getTechnicalDay();
                    result[31] = model.getVerifyDay();
                    result[32] = model.getReportGenerateDay();
                    result[33] = model.getReportCheckDay();
                    result[34] = model.getReportSendDay();
                    results.add(result);
                }
            }
        }
        return results;
    }
    
    private List<String[]> wrapTaskOutNgsCapNGS(List<OtherReportFormModel> list)
    {
        List<String[]> results = Lists.newArrayList();
        if (Collections3.isNotEmpty(list))
        {
            for (OtherReportFormModel model : list)
            {
                if (!"NGS".equals(model.getTestingMethods()) && !"CapNGS".equals(model.getTestingMethods()))
                {
                    String result[] = new String[28];
                    result[0] = model.getOrderCode();
                    result[1] = model.getMarketingCenter();
                    result[2] = model.getTestingStatus();
                    result[3] = model.getProductName();
                    result[4] = model.getTestingMethods();
                    result[5] = model.getContractName();
                    result[6] = model.getSampleCode();
                    result[7] = model.getExamineeName();
                    result[8] = model.getCustomerName();
                    result[9] = model.getCompanyName();
                    result[10] = model.getCreateName();
                    result[11] = model.getCycleValue();
                    result[12] = model.getSubmitTime();
                    result[13] = model.getPlanFinishTime();
                    result[14] = model.getActualFinishTime();
                    result[15] = model.getIsOnTime();
                    result[16] = model.getPostponeDay();
                    result[17] = model.getActualFinishDay();
                    result[18] = model.getResampleCount();
                    result[19] = model.getSampleReceivedDay();
                    result[20] = model.getOrderPaymentDay();
                    result[21] = model.getTestingStartDay();
                    result[22] = model.getDnaDay();
                    result[23] = model.getTestDay();
                    result[24] = model.getTechnicalDay();
                    result[25] = model.getReportGenerateDay();
                    result[26] = model.getReportCheckDay();
                    result[27] = model.getReportSendDay();
                    results.add(result);
                }
            }
        }
        return results;
    }
    
    private List<OtherReportFormModel> getCycleReportFormList(CycleSystemRequest searcher)
    {
        List<OtherReportFormModel> records = Lists.newArrayList();
        
        int pageNo = 1;
        int pageSize = 50;
        Pagination<OtherReportFormModel> pagination=null;
        do
        {
            searcher.setPageNo(pageNo++);
            searcher.setPageSize(pageSize);
            Pagination<OtherReportFormModel> model = getCycleReportFormPagin(searcher);
            if(null != model)
            {
                pagination = new Pagination<>();
                pagination.setPageNo(model.getPageNo());
                pagination.setPageSize(model.getPageSize());
                pagination.setTotalCount(model.getTotalCount());
                if (Collections3.isNotEmpty(model.getRecords()))
                {
                    records.addAll(model.getRecords());
                }
            }
            if (log.isDebugEnabled())
            {
                log.debug("Search records for page {} successful, total count {}, total page {}.",
                        pageNo,
                        pagination.getTotalCount(),
                        pagination.getTotalPage());
            }

        } while (!pagination.isLastPage());

        if (log.isDebugEnabled())
        {
            log.debug("Search records successful, total count {}.", records.size());
        }
        return records;
    }
    
    private Pagination<OtherReportFormModel> getCycleReportFormPagin(CycleSystemRequest request)
    {
        String url = GatewayService.getServiceUrl("/export/system/cycleSystemInfoList");
        ResponseEntity<Pagination<OtherReportFormModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CycleSystemRequest>(request), new ParameterizedTypeReference<Pagination<OtherReportFormModel>>()
            {
            });
        return exchange.getBody();
        
    }
}
