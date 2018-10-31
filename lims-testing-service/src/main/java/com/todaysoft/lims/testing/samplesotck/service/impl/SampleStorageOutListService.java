package com.todaysoft.lims.testing.samplesotck.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.dao.searcher.TestingSheetSampleStorageSearcher;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStorageOutRecord;
import com.todaysoft.lims.testing.samplesotck.service.ISampleStorageOutListService;

@Service
public class SampleStorageOutListService implements ISampleStorageOutListService
{

    @Autowired
    private BaseDaoSupport baseDaoSupport;

    @Override
    public Pagination<SampleStorageOutRecord> sampleStorageOut_list(TestingSheetSampleStorageSearcher searcher)
    {

        Pagination<SampleStorageOutRecord> pagerDto = null;
        try
        {
            String record_testing_sheet =
                "select * from LIMS_TESTING_SHEET FORCE INDEX(IDX_CREATE_TIME) where SEMANTIC<>'FLUO-ANALYSE' order by CREATE_TIME desc";
            String count_testing_sheet = "select * from LIMS_TESTING_SHEET where SEMANTIC<>'FLUO-ANALYSE'";
            StringBuffer recordSql = new StringBuffer();
            recordSql.append(buildSql(false, record_testing_sheet, searcher));
            StringBuffer countSql = new StringBuffer();
            countSql.append(buildSql(true, count_testing_sheet, searcher));
            String record_sql = addFilter(recordSql, searcher);
            String count_sql = addFilter(countSql, searcher);
            //分页
            SQLQuery sqlQuery = baseDaoSupport.hibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(record_sql);

            List<Object[]> lists =
                sqlQuery.setFirstResult((searcher.getPageNo() == null ? 1 : searcher.getPageNo() - 1)
                    * (searcher.getPageSize() == null ? 10 : searcher.getPageSize()))
                    .setMaxResults(searcher.getPageSize() == null ? 10 : searcher.getPageSize())
                    .list();
            //查询总数
            Object totals_obj = baseDaoSupport.hibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(count_sql).uniqueResult();
            Long totals = Long.valueOf(totals_obj.toString());

            //封装页面数据参数
            List<SampleStorageOutRecord> rows = object2SampleStorageOutRecord(lists);
            pagerDto = new Pagination<SampleStorageOutRecord>();
            pagerDto.setPageNo(searcher.getPageNo());
            pagerDto.setPageSize(searcher.getPageSize());
            pagerDto.setTotalCount(totals.intValue());
            pagerDto.setRecords(rows);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return pagerDto;
    }

    @Override
    public Pagination<SampleStorageOutRecord> sampleStorage_list(TestingSheetSampleStorageSearcher searcher) {
        Pagination<SampleStorageOutRecord> pagerDto = null;
        try {
            String record_testing_sheet="select * from LIMS_TESTING_SHEET FORCE INDEX(IDX_CREATE_TIME) where SEMANTIC<>'FLUO-ANALYSE' order by CREATE_TIME desc";
            String count_testing_sheet="select * from LIMS_TESTING_SHEET where SEMANTIC<>'FLUO-ANALYSE'";
            StringBuffer recordSql = new StringBuffer();
            recordSql.append(buildSql(false,record_testing_sheet,searcher));
            StringBuffer countSql = new StringBuffer();
            countSql.append(buildSql(true,count_testing_sheet,searcher));
            String record_sql=addFilter(recordSql,searcher);
            String count_sql=addFilter(countSql,searcher);
            //分页
            SQLQuery sqlQuery= baseDaoSupport.hibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(record_sql);

            List<Object[]> lists = (List<Object[]>)sqlQuery
                    .list();
            //查询总数
            Object totals_obj = baseDaoSupport.hibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(count_sql)
                    .uniqueResult();
            Long totals = Long.valueOf(totals_obj.toString());

            //封装页面数据参数
            List<SampleStorageOutRecord> rows = object2SampleStorageOutRecord(lists);
            pagerDto = new Pagination<SampleStorageOutRecord>();
            pagerDto.setTotalCount(totals.intValue());
            pagerDto.setRecords(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagerDto;
    }

    private String buildSql(boolean isCount, String testing_sheet_sql, TestingSheetSampleStorageSearcher searcher)
    {
        String columnSql =
            " DISTINCT" + "	testingshe0_.id," + "	testingshe0_.STATUS," + "	testingshe.code," + "	testingshe.task_Name," + "	testingshe.assign_Time,"
                + "	testingshe.CREATE_TIME ";
        if (isCount)
        {
            columnSql = " count( " + columnSql + " ) ";
        }
        return "select " + columnSql + "    from" + "   (" + testing_sheet_sql + ")" + "  testingshe " + " left  join"
            + "	 LIMS_TESTING_SHEET_SAMPLE_STORAGE testingshe0_" + " on  testingshe.ID =testingshe0_.SHEET_ID" + " inner  join"
            + "   LIMS_TESTING_SHEET_TASK testingshe1_" + "  on   testingshe0_.SHEET_ID=testingshe1_.SHEET_ID   where 1=1 ";
    }

    private List<SampleStorageOutRecord> object2SampleStorageOutRecord(List<Object[]> lists)
    {
        List<SampleStorageOutRecord> records = new ArrayList<SampleStorageOutRecord>();
        if (lists != null && lists.size() > 0)
        {
            for (Object[] objects : lists)
            {
                SampleStorageOutRecord record = new SampleStorageOutRecord();
                records.add(record);
                record.setId(objects[0] == null ? "" : objects[0].toString());
                if (objects[1] == null)
                {
                    record.setStatus(null);
                }
                else
                {
                    record.setStatus((Boolean)objects[1] ? 1 : 0);
                }
                record.setCode(objects[2] == null ? "" : objects[2].toString());
                record.setTaskName(objects[3] == null ? "" : objects[3].toString());
                record.setAssignTime(objects[4] == null ? null : (Date)objects[4]);
                record.setCreateTime(objects[5] == null ? null : (Date)objects[5]);
            }
        }
        return records;
    }

    private String addFilter(StringBuffer sql, TestingSheetSampleStorageSearcher searcher)
    {
        if (!StringUtils.isEmpty(searcher.getTestingSheetCode()))
        {
            sql.append(" AND testingshe.code LIKE '%" + searcher.getTestingSheetCode() + "%'");
        }
        if (null != searcher.getStatus())
        {
            sql.append(" AND testingshe0_.STATUS = " + searcher.getStatus());
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(searcher.getStorageType()))
        {
            sql.append(" AND testingshe0_.STORAGE_TYPE = '" + searcher.getStorageType() + "' ");
        }
        /*if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(searcher.getSampleCode()))
        {
            sql.append(" AND testingsam3_.SAMPLE_CODE = '" + searcher.getSampleCode() + "' ");
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(searcher.getSampleName()))
        {
            sql.append(" AND receivedsam4_.SAMPLE_NAME Like '" + searcher.getSampleName() + "%' ");
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(searcher.getSemantic()))
        {
            sql.append(" AND testingtas2_.SEMANTIC = '" + searcher.getSemantic() + "' ");
        }*/
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(searcher.getSampleCode()))
        {
            sql.append(" AND testingshe1_.TESTING_TASK_ID in (select testingtas2_.ID from LIMS_TESTING_TASK testingtas2_ join LIMS_TESTING_SAMPLE testingsam3_ "
                + " on testingtas2_.INPUT_SAMPLE_ID = testingsam3_.ID where testingsam3_.SAMPLE_CODE = '" + searcher.getSampleCode() + "' )");
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(searcher.getSampleName()))
        {

            sql.append(" AND testingshe1_.TESTING_TASK_ID in (select testingtas2_.ID from LIMS_TESTING_TASK testingtas2_ join LIMS_TESTING_SAMPLE testingsam3_ "
                + " on testingtas2_.INPUT_SAMPLE_ID = testingsam3_.ID LEFT JOIN LIMS_RECEIVED_SAMPLE receivedsam4_ ON testingsam3_.ORIGINAL_SAMPLE_ID = receivedsam4_.sample_Id");
            sql.append(" where receivedsam4_.SAMPLE_NAME Like '" + searcher.getSampleName() + "%' )");
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(searcher.getSemantic()))
        {
            sql.append(" AND  testingshe1_.TESTING_TASK_ID in (select testingtas2_.ID from LIMS_TESTING_TASK testingtas2_ where testingtas2_.SEMANTIC = '"
                + searcher.getSemantic() + "') ");
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(searcher.getStartTime()))
        {
            sql.append(" AND testingshe.assign_Time   >=  STR_TO_DATE('" + searcher.getStartTime() + "','%Y-%m-%d %H:%i:%s') ");
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(searcher.getEndTime()))
        {
            sql.append(" AND testingshe.assign_Time <= STR_TO_DATE('" + searcher.getEndTime() + "','%Y-%m-%d %H:%i:%s')");
        }
        return sql.toString();
    }
}
