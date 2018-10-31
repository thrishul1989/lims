package com.todaysoft.lims.technical.service.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.todaysoft.lims.technical.config.ConfigManage;
import com.todaysoft.lims.technical.entity.CapCnvData;
import com.todaysoft.lims.technical.entity.SiteplotData;
import com.todaysoft.lims.technical.model.request.UploadAnnotationModel;
import com.todaysoft.lims.technical.model.request.UploadAnnotationRequest;
import com.todaysoft.lims.technical.mybatis.BioInfoAnnotateMapper;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotate;
import com.todaysoft.lims.technical.mybatis.entity.BiologyDivisionFastqData;
import com.todaysoft.lims.technical.mybatis.entity.Dict;
import com.todaysoft.lims.technical.service.IBioInfoAnnotateService;
import com.todaysoft.lims.technical.service.IBiologyDivisionDataService;
import com.todaysoft.lims.technical.service.IDictService;
import com.todaysoft.lims.technical.utils.Collections3;

@SuppressWarnings("unchecked")
@Service
public class BioInfoAnnotateService implements IBioInfoAnnotateService
{
    @Autowired
    private IBiologyDivisionDataService divisionDataService;
    
    @Autowired
    private IDictService service;
    
    @Autowired
    private MongoTemplate template;
    
    private ConfigManage configManger;
    
    @Autowired
    private BioInfoAnnotateMapper bioAnnotateMapper;
    
    private static final Logger logger = LoggerFactory.getLogger(BioInfoAnnotateService.class);
    
    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(8, 9, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
        new ThreadPoolExecutor.DiscardOldestPolicy());
    
    @Override
    public void saveAnnotation(UploadAnnotationRequest request)
    {
        for (UploadAnnotationModel model : request.getList())
        {
            threadPool.execute(new Runnable()
            {
                
                @Override
                public void run()
                {
                    logger.info("我是线程" + Thread.currentThread().getName() + "在处理" + model.getDataCode());
                    afterAnnotateForSave(model.getDataCode(),
                        model.getSnvJsonFilePath(),
                        model.getCapCnvFilePath(),
                        model.getSvJsonFilePath(),
                        model.getRgcFilePath(),
                        model.getQtControlFilePath());
                    
                }
            });
        }
    }
    
    /**
     * 注释保存突变、CapCNV、SV、Siteplot数据 dataCode：数据编号 filePath：路径
     * 1-突变，2-CapCNV，3-SV，4-Siteplot，5-质控
     */
    @Override
    @Transactional
    public Integer afterAnnotateForSave(String dataCode, String... filePath)
    {
        try
        {
            BiologyDivisionFastqData biologyDivisionData = divisionDataService.getByDataCode(dataCode); // 根据数据编号获取拆分数据
            // BioInfoAnnotate annotate =
            // getAnnotateBySampleCode(dataCode);//据数据编号获取注释数据
            StringBuffer uploadDesc = new StringBuffer();
            if (null != biologyDivisionData)
            {
                if (null != biologyDivisionData.getId() && "" != biologyDivisionData.getId())
                {
                    String id = biologyDivisionData.getId();
                    if (Collections3.isNotEmpty(filePath))
                    {
                        for (int i = 0; i < filePath.length; i++)
                        {
                            if (i == 0)
                            {
                                try
                                {
                                    if (StringUtils.isNotEmpty(filePath[i]))
                                    {
                                        Object jsonStr = uploadTextData(filePath[i]);
                                        DBCollection collection = template.getCollection("analysis-mutation");
                                        if (null != jsonStr)
                                        {
                                            List<Map<String, Object>> list = (List<Map<String, Object>>)jsonStr;
                                            if (null != list)
                                            {
                                                int k = 0;
                                                System.out.println(dataCode + "开始解析突变json-----");
                                                for (Map<String, Object> map : list)
                                                {
                                                    BasicDBObject object = new BasicDBObject();
                                                    replaceKey(map, object);
                                                    object.put("analysisSampleId", id);
                                                    collection.save(object);
                                                    if (k % 100 == 0)
                                                    {
                                                        
                                                    }
                                                    k++;
                                                }
                                                System.out.println(dataCode + "结束解析突变json-----");
                                                logger.info(dataCode + "结束解析突变json-----");
                                            }
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                    // annotate.setSnvJson("");
                                    // annotate.setAnnotateStatus(null);
                                    uploadDesc.append("snp_indel_Json文件");
                                }
                                
                            }
                            if (i == 1)
                            {
                                try
                                {
                                    if (StringUtils.isNotEmpty(filePath[i]))
                                    {
                                        System.out.println(dataCode + "开始解析cnv----");
                                        logger.info(dataCode + "开始解析cnv----");
                                        List<List<String>> list = uploadTxtLikeExcelData(filePath[i]);
                                        if (null != list && list.size() > 0)
                                        {
                                            for (List<String> sl : list)
                                            {
                                                CapCnvData capcnv = new CapCnvData();
                                                capcnv.setChrLocation(sl.get(0));
                                                capcnv.setGene(sl.get(1));
                                                capcnv.setArea(sl.get(2));
                                                capcnv.setCopyNumber(new BigDecimal(sl.get(3)).doubleValue());
                                                capcnv.setpValue(new BigDecimal(sl.get(4)).doubleValue());
                                                capcnv.setAnalysisSampleId(id);
                                                template.save(capcnv);
                                            }
                                        }
                                        
                                        System.out.println(dataCode + "cnv解析完成");
                                        logger.info(dataCode + "cnv解析完成");
                                    }
                                }
                                catch (Exception e)
                                {
                                    // annotate.setCnv("");
                                    // annotate.setAnnotateStatus(null);
                                    uploadDesc.append(" cnv文件");
                                }
                                
                            }
                            if (i == 2)
                            {
                                try
                                {
                                    if (StringUtils.isNotEmpty(filePath[i]))
                                    {
                                        System.out.println(dataCode + "开始解析sv-----");
                                        logger.info(dataCode + "开始解析sv-----");
                                        Object jsonStr = uploadTextData(filePath[i]);
                                        DBCollection collection = template.getCollection("analysis-sv");
                                        if (null != jsonStr)
                                        {
                                            List<Map<String, String>> list = (List<Map<String, String>>)jsonStr;
                                            if (null != list)
                                            {
                                                for (Map<String, String> map : list)
                                                {
                                                    BasicDBObject object = new BasicDBObject();
                                                    for (Map.Entry<String, String> entry : map.entrySet())
                                                    {
                                                        object.put(entry.getKey().toString(), entry.getValue());
                                                    }
                                                    object.put("analysisSampleId", id);
                                                    // object.put("analysisSampleId",
                                                    // "ccfdb546f5de11e78c880242ac110002");
                                                    collection.save(object);
                                                }
                                            }
                                        }
                                        System.out.println(dataCode + "sv解析完成-----");
                                        logger.info(dataCode + "sv解析完成-----");
                                    }
                                }
                                catch (Exception e)
                                {
                                    // annotate.setSvJson("");
                                    // annotate.setAnnotateStatus(null);
                                    uploadDesc.append(" sv文件");
                                }
                                
                            }
                            if (i == 3)
                            {
                                try
                                {
                                    if (StringUtils.isNotEmpty(filePath[i]))
                                    {
                                        System.out.println(dataCode + "开始解析site文件");
                                        logger.info(dataCode + "开始解析site文件");
                                        List<List<String>> list = uploadTxtLikeExcelData(filePath[i]);
                                        if (null != list && list.size() > 0)
                                        {
                                            for (List<String> sl : list)
                                            {
                                                SiteplotData siteplot = new SiteplotData();
                                                siteplot.setChr(sl.get(0));
                                                siteplot.setStart(sl.get(1));
                                                siteplot.setStop(sl.get(2));
                                                siteplot.setGeneSpan(sl.get(3));
                                                siteplot.setGene(sl.get(4));
                                                siteplot.setExon(sl.get(5));
                                                siteplot.setLen(sl.get(6));
                                                siteplot.setReadNum(sl.get(7));
                                                siteplot.setAnalysisSampleId(id);
                                                template.save(siteplot);
                                            }
                                        }
                                        System.out.println(dataCode + "site文件解析完成");
                                        logger.info(dataCode + "site文件解析完成");
                                    }
                                }
                                catch (Exception e)
                                {
                                    // annotate.setRegioncountDmdexon("");
                                    // annotate.setAnnotateStatus(null);
                                    uploadDesc.append(" Regioncount文件");
                                }
                                
                            }
                            if (i == 4)
                            {
                                try
                                {
                                    if (StringUtils.isNotEmpty(filePath[i]))
                                    {
                                        System.out.println(dataCode + "开始解析statistics文件");
                                        logger.info(dataCode + "开始解析statistics文件");
                                        List<List<String>> list = uploadStatisticsTxtLikeExcelData(filePath[i]);
                                        if (null != list && list.size() > 0)
                                        {
                                            DBCollection collection = template.getCollection("analysis-statistics");
                                            List<String> cols = list.get(0);
                                            for (int k = 1; k < list.size(); k++)
                                            {
                                                List<String> sl = list.get(k);
                                                BasicDBObject object = new BasicDBObject();
                                                object.put("sample", sl.get(0));
                                                object.put("rawDataBases", sl.get(1));
                                                object.put("cleanDataBases", sl.get(2));
                                                object.put("alignedBases", sl.get(3));
                                                object.put("aligned", sl.get(4));
                                                object.put("initialBases", sl.get(5));
                                                object.put("baseCovered", sl.get(6));
                                                object.put("coverageRegion", sl.get(7));
                                                object.put("effectiveBases", sl.get(8));
                                                object.put("fractionEffectiveBases", sl.get(9));
                                                object.put("averageSequencingDepth", sl.get(10));
                                                object.put("least4X", sl.get(11));
                                                object.put("least10X", sl.get(12));
                                                object.put("least20X", sl.get(13));
                                                checkColName(cols, object, sl);
                                                object.put("analysisSampleId", id);
                                                collection.save(object);
                                            }
                                            
                                        }
                                        
                                        System.out.println(dataCode + "statistics文件解析完成");
                                        System.out.println("线程" + Thread.currentThread().getName() + "处理数据编号" + dataCode + "完成！！！！！！！！！！！！！！");
                                        logger.info("线程" + Thread.currentThread().getName() + "处理数据编号" + dataCode + "完成！！！！！！！！！！！！！！");
                                    }
                                }
                                catch (Exception e)
                                {
                                    // annotate.setStatisticsDmdexon("");
                                    // annotate.setAnnotateStatus(null);
                                    uploadDesc.append(" Statistics文件");
                                }
                                
                            }
                        }
                        // annotate.setAnnotateStatus(null ==
                        // annotate.getAnnotateStatus() ? "6" : "5");
                        if (!StringUtils.isEmpty(uploadDesc))
                        {
                            // annotate.setUploadDesc(uploadDesc.append("解析出错，请重新上传对应文件!").toString());
                        }
                        else
                        {
                            // annotate.setUploadDesc("");
                        }
                        
                        // mapper.update(annotate);
                        
                    }
                }
            }
            
            return 1;
        }
        catch (Exception e)
        {
            
            return 0;
        }
        
    }
    
    /*
     * @Override public BioInfoAnnotate getAnnotateBySampleCode(String
     * sampleCode) { return mapper.getAnnotateBySampleCode(sampleCode); }
     */
    
    // 测试时 地址带文件夹
    public Object uploadTextData(String fileName) throws Exception
    {
        String ss = "";
        if (fileName.lastIndexOf("/") > 0)
        {
            ss = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
        }
        else
        {
            ss = fileName;
        }
        File file = new File(ss);
        String encoding = "UTF-8";
        String wholePath = "";
        String u = configManger.getDownloadUrl();
        System.out.println("----url:" + u);
        wholePath = u + fileName;
        if (fileName.contains("http"))
        {
            wholePath = fileName;
        }
        URL url = new URL(wholePath);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 得到输入流
        InputStream in = conn.getInputStream();
        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        // 以流的形式下载文件。
        byte[] buffer = readInputStream(in);
        os.write(buffer);
        os.flush();
        
        String r = file.getAbsolutePath();
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream fin = new FileInputStream(r);
        fin.read(filecontent);
        fin.close();
        in.close();
        os.close();
        file.delete();
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder charsetDecoder = charset.newDecoder();
        Object parse = JSON.parse(filecontent, 0, filelength.intValue(), charsetDecoder);
        return parse;
    }
    
    public static byte[] readInputStream(InputStream inputStream) throws IOException
    {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1)
        {
            bos.write(buffer, 0, len);
        }
        if (null != bos)
        {
            bos.close();
        }
        return bos.toByteArray();
    }
    
    // 解析质控的txt文本,不去第一行
    private List<List<String>> uploadStatisticsTxtLikeExcelData(String filePath) throws IOException
    {
        List<List<String>> list = new ArrayList<>();
        String encoding = "UTF-8";
        String wholePath = "";
        String u = configManger.getDownloadUrl();
        wholePath = u + filePath;
        if (filePath.contains("http://60.205.205.23"))
        {
            wholePath = filePath;
        }
        URL url = new URL(wholePath);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        InputStreamReader read = new InputStreamReader(inputStream, encoding);// 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null)
        {
            List<String> ls = new ArrayList<>();
            String[] arr = lineTxt.split("\\\t+");
            if (null != arr && arr.length > 0)
            {
                for (String s : arr)
                {
                    ls.add(s);
                }
            }
            list.add(ls);
        }
        read.close();
        return list;
        
    }
    
    private void checkColName(List<String> cols, BasicDBObject object, List<String> dataList)
    {
        if (Collections3.isNotEmpty(cols))
        {
            int a = 0;
            for (int i = 0; i < cols.size(); i++)
            {
                // duplication rate 去空 转小写
                if ("duplicationrate".equals(cols.get(i).replace(" ", "").toLowerCase()))
                {
                    a = i - 13 - 1;
                }
            }
            if (a > 0)
            {
                String name = cols.get(14);
                object.put("least" + name.substring(name.lastIndexOf("t") + 1).replace(" ", ""), dataList.get(14));
                if (a == 2)
                {
                    String name2 = cols.get(15);
                    object.put("least" + name2.substring(name2.lastIndexOf("t") + 1).replace(" ", ""), dataList.get(15));
                }
                if (a == 3)
                {
                    String name2 = cols.get(15);
                    String name3 = cols.get(16);
                    object.put("least" + name2.substring(name2.lastIndexOf("t") + 1).replace(" ", ""), dataList.get(15));
                    object.put("least" + name3.substring(name3.lastIndexOf("t") + 1).replace(" ", ""), dataList.get(16));
                }
            }
            object.put("duplicationRate", dataList.get(14 + a));
            object.put("chip", dataList.get(15 + a));
            if ((16 + a + 1) == dataList.size())
            {
                object.put("q30", dataList.get(16 + a));
            }
        }
    }
    
    // 解析数据类似excel的txt文本
    private List<List<String>> uploadTxtLikeExcelData(String filePath) throws Exception
    {
        List<List<String>> list = new ArrayList<>();
        String encoding = "UTF-8";
        String wholePath = "";
        String u = configManger.getDownloadUrl();
        System.out.println("----url:" + u);
        wholePath = u + filePath;
        if (filePath.contains("http://60.205.205.23"))
        {
            wholePath = filePath;
        }
        URL url = new URL(wholePath);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        InputStreamReader read = new InputStreamReader(inputStream, encoding);// 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        int i = 0;
        while ((lineTxt = bufferedReader.readLine()) != null)
        {
            if (i != 0)
            {
                List<String> ls = new ArrayList<>();
                String[] arr = lineTxt.split("\\s+");
                if (null != arr && arr.length > 0)
                {
                    for (String s : arr)
                    {
                        ls.add(s);
                    }
                }
                list.add(ls);
            }
            i++;
        }
        read.close();
        return list;
        
    }
    
    private BasicDBObject replaceKey(Map<String, Object> map, BasicDBObject object)
    {
        if (null != map)
        {
            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                Dict dict = service.getByText("JSON_KEY", entry.getKey());
                
                if (entry.getValue() instanceof String)
                {
                    object.put(null == dict ? entry.getKey() : dict.getDictValue(), entry.getValue());
                    if ("B1".equals(entry.getKey()))
                    {
                        if (null != entry.getValue())
                        {
                            String s = entry.getValue().toString();
                            if ("pathogenic".equals(s.toLowerCase().replace(" ", "")))
                            {
                                object.put("pa_sort", 1);
                            }
                            else if ("likelypathogenic".equals(s.toLowerCase().replace(" ", "")))
                            {
                                object.put("pa_sort", 2);
                            }
                            else if ("likelybenign".equals(s.toLowerCase().replace(" ", "")))
                            {
                                object.put("pa_sort", 3);
                            }
                            else if ("benign".equals(s.toLowerCase().replace(" ", "")))
                            {
                                object.put("pa_sort", 4);
                            }
                            else if ("uncertain".equals(s.toLowerCase().replace(" ", "")))
                            {
                                object.put("pa_sort", 5);
                            }
                            else
                            {
                                object.put("pa_sort", 6);
                            }
                        }
                    }
                }
                else
                {
                    BasicDBObject o = new BasicDBObject();
                    object.put(null == dict ? entry.getKey() : dict.getDictValue(), o);
                    replaceKey((Map<String, Object>)entry.getValue(), o);
                }
            }
        }
        return object;
    }
    
    @Override
    @Transactional
    public void update(BioInfoAnnotate bean)
    {
        bioAnnotateMapper.update(bean);
        
    }
    
}
