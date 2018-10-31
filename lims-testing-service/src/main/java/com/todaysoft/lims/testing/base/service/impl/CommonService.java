package com.todaysoft.lims.testing.base.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.Sequence;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonService implements ICommonService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public String getDNAExtractCode(int i)
    {
        String code = "";
        String arr[] = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int val = i / 8;
        int mod = i % 8;
        if (mod == 0)
        {
            code = arr[7] + val;
        }
        else
        {
            code = arr[mod - 1] + (val + 1);
        }
        return code;
    }

    @Override
    public String getFirstPcrSampleTempLocation(int i) {
        String code = "";
        String arr[] = {"A", "B", "C", "D", "E", "F"};
        int val = i / 6;
        int mod = i % 6;
        if (mod == 0)
        {
            code = arr[5] + val;
        }
        else
        {
            code = arr[mod - 1] + (val + 1);
        }
        return "Sample1-"+code;
    }

    @Override
    public String getPrimerTempLocation(int i) {
        int temp=0;
        String f = "F1";
        if(24 < i){
            temp = i-24;
            f="F2";
        }else{
            temp = i;
        }
        String code = "";
        String arr[] = {"A", "B", "C", "D"};
        int val = temp / 4;
        int mod = temp % 4;
        if (mod == 0)
        {
            code = arr[3] + val;
        }
        else
        {
            code = arr[mod - 1] + (val + 1);
        }
        return "Primer-"+f+"-"+code;
    }

    @Override
    public String getSecondPrimerLocation(int i) {
        int temp=0;
        String f = "F1";
        if(24 < i){
            temp = i-24;
            f="F2";
        }else{
            temp = i;
        }
        String code = "";
        String arr[] = {"A", "B", "C", "D"};
        int val = temp / 4;
        int mod = temp % 4;
        if (mod == 0)
        {
            code = arr[3] + val;
        }
        else
        {
            code = arr[mod - 1] + (val + 1);
        }
        return "Primer1-"+code;
    }

    @Override
    public String getMlpaCode(int start,int i)
    {
        String code = "";int num=0;
        String arr[] = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int val = i / 8;
        int mod = i % 8;
        if(val<1)
        {
            num = start+1;
        }else if(1<=val && mod!=0){
            num=start+val+1;
        }else if(1<=val && mod==0){
            num=start+val;
        }
        if (mod == 0)
        {
            code = arr[7] + num;
        }
        else
        {
            code = arr[mod - 1] + num;
        }
        return code;
    }

    @Override
    public String getTestSampleCode(String sampleCode, String semantic)
    {
        String code = "";
        switch (semantic)
        {
            case TaskSemantic.DNA_EXTRACT:
                code = "D" + new SimpleDateFormat("yy", Locale.CHINESE).format(Calendar.getInstance().getTime()) + sampleCode + "01";
                break;
            case TaskSemantic.DNA_QC:
                code = "D" + new SimpleDateFormat("yy", Locale.CHINESE).format(Calendar.getInstance().getTime()) + sampleCode + "01";
                break;
            case TaskSemantic.CDNA_EXTRACT:
                code = "C" + new SimpleDateFormat("yy", Locale.CHINESE).format(Calendar.getInstance().getTime()) + sampleCode + "01";
                break;
            case TaskSemantic.CDNA_QC:
                code = "C" + new SimpleDateFormat("yy", Locale.CHINESE).format(Calendar.getInstance().getTime()) + sampleCode + "01";
                break;
            default:
                break;
        }
        return code;
    }
    
    @Override
    @Transactional
    public String getTaskCodeBySemantic(String semantic)
    {
        String indexStr="";
        switch(semantic){
            case TaskSemantic.DNA_EXTRACT:
                indexStr="DNA";
                break;
            case TaskSemantic.DNA_QC:
                indexStr="DNAZJ";
                break;
            case TaskSemantic.CDNA_EXTRACT:
                indexStr="CDNA";
                break;
            case TaskSemantic.CDNA_QC:
                indexStr="CDNAZJ";
                break;
            case TaskSemantic.LIBRARY_CRE:
                indexStr="LIB";
                break;
            case TaskSemantic.LIBRARY_QC:
                indexStr="LIBZJ";
                break;
            case TaskSemantic.LIBRARY_CAP:
                indexStr="CAP";
                break;
            case TaskSemantic.RQT:
                indexStr="DL";
                break;
            case TaskSemantic.POOLING:
                indexStr="HY";
                break;
            case TaskSemantic.QT:
                indexStr="JD";
                break;
            case TaskSemantic.NGS_SEQ:
                indexStr="SJ";
                break;
            case TaskSemantic.PRIMER_DESIGN:
                indexStr="P";
                break;
            case TaskSemantic.PCR_ONE:
                indexStr="O";
                break;
            case TaskSemantic.PCR_TWO:
                indexStr="T";
                break;
            case TaskSemantic.DATA_ANALYSIS:
                indexStr="SVSG";
                break;
            case TaskSemantic.PCR_NGS_DATA_ANALYSIS:
                indexStr="PNSG";
                break;
            case TaskSemantic.DT_DATA_ANALYSIS:
                indexStr="DTSG";
                break;
            case TaskSemantic.MLPA_DATA_ANALYSIS:
                indexStr="MLSG";
                break;
            case TaskSemantic.SANGER_DATA_ANALYSIS:
                indexStr="STSG";
                break;
            case TaskSemantic.BIOLOGY_ANALY:
                indexStr="SX";
                break;
            case TaskSemantic.TECHNICAL_ANALY:
                indexStr="JS";
                break;
            case TaskSemantic.MLPA:
                indexStr="MLPA";
                break;
            case TaskSemantic.QPCR:
                indexStr="QPCR";
                break;
            case TaskSemantic.DT:
                indexStr="DT";
                break;
            case TaskSemantic.PCR_NGS:
                indexStr="PN";
                break;
            case TaskSemantic.LONG_PCR:
                indexStr="LPCR";
                break;
            case TaskSemantic.LONG_CRE:
                indexStr="LBPCR";
                break;
            case TaskSemantic.LONG_QC:
                indexStr="LBPCRQC";
                break;
            case TaskSemantic.MULTI_PCR:
                indexStr="MPCR";
                break;
            case TaskSemantic.MULTIPCR_QC:
                indexStr="MPCRQC";
                break;
            case TaskSemantic.FLUO_TEST:
                indexStr="RT";
                break;
            case TaskSemantic.FLUO_ANALYSE:
                indexStr="RTSG";
                break;
            case TaskSemantic.DATA_DIVISION:
                indexStr="SXC";
                break;
            case TaskSemantic.BIOLOGY_ANNOTATION:
                indexStr="SXZ";
                break;
            case TaskSemantic.SANGER_DATA_REPORT:
                indexStr="JSD";
                break;
            default:
                indexStr="QT";
                break;
        }

        String code = "";
        int index = 1;
        Sequence sequence = getSequenceListByNameAndDate(semantic);
        if(null != sequence){
            index = sequence.getCurrentValue().intValue();
        }
        code = indexStr + DateUtils.getDate("yyMMdd")+ String.format("%02d", index);
        return code;
    }

    @Override
    @Transactional
    public String getTechnicalAnalBatchNo() {
        String todayStr = DateUtils.getDate("MMdd");
        String combineCode="";
        int index = 1;
        Sequence sequence = getSequenceListByNameAndDate("technicalCombineBatch");
        if(null != sequence){
            index = sequence.getCurrentValue().intValue();
        }
        combineCode = "S" + todayStr + String.format("%02d", index);
        return combineCode;
    }

    @Transactional
    public Sequence getSequenceListByNameAndDate(String name)
    {
        Sequence sequence;
        String todayStr = DateUtils.getDate("yyyyMMdd");
        NamedQueryer queryer = NamedQueryer.builder()
                .hql("FROM Sequence s WHERE s.name = :name")
                .names(Lists.newArrayList("name"))
                .values(Lists.newArrayList(name))
                .build();
        sequence = baseDaoSupport.find(queryer, Sequence.class).get(0);
        if(null == sequence){
            return null;
        }else{
            if(null==sequence.getBatchDate()){
                sequence.setBatchDate(new Date());
                sequence.setCurrentValue(1L);
            }else{
                if(!todayStr.equals(DateUtils.formatDate(sequence.getBatchDate(),"yyyyMMdd")))
                {
                    sequence.setBatchDate(new Date());
                    sequence.setCurrentValue(1L);
                }else{
                    sequence.setCurrentValue(sequence.getCurrentValue()+sequence.getIncrement());
                }
            }
        }
        baseDaoSupport.update(sequence);
        return sequence;
    }

    @Override
    @Transactional
    public String getReportNoByName(String name) {
        String todayStr = DateUtils.getDate("yyyy");
        String hql = "FROM Sequence s WHERE s.name=:name ";
        List<Sequence> list = baseDaoSupport.findByNamedParam(Sequence.class,hql,new String[]{"name"},new String[]{name});
        Sequence sequence = Collections3.getFirst(list);
        Long current = sequence.getCurrentValue();
        String result="";
        if(!todayStr.equals(DateUtils.formatDate(sequence.getBatchDate(),"yyyy")))//年份变化 重置
        {
            sequence.setCurrentValue(1L);
            sequence.setBatchDate(new Date());
            result = todayStr.substring(2,4)+"RT"+String.format("%07d", 1);

        }else{
            sequence.setCurrentValue(sequence.getCurrentValue()+sequence.getIncrement());
            result = todayStr.substring(2,4)+"RT"+String.format("%07d", current);
        }
        baseDaoSupport.update(sequence);
        return result;


    }
}
