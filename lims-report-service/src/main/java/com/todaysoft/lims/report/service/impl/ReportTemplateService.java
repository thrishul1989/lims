package com.todaysoft.lims.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.ReportTemplateSearcher;
import com.todaysoft.lims.report.entity.ContentBookmark;
import com.todaysoft.lims.report.entity.ContentTableColumn;
import com.todaysoft.lims.report.entity.ReportTemplate;
import com.todaysoft.lims.report.service.IReportTemplateService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ReportTemplateService implements IReportTemplateService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<ReportTemplate> paging(ReportTemplateSearcher searcher)
    {
        return baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), ReportTemplate.class);
    }
    
    @Override
    public ReportTemplate get(String id)
    {
        return baseDaoSupport.get(ReportTemplate.class, id);
    }
    
    @Override
    @Transactional
    public void create(ReportTemplate template)
    {
        baseDaoSupport.insert(template);
        for (ContentBookmark bookmark : template.getBookmarkList())
        {
            bookmark.setReportTemplate(template);
            baseDaoSupport.insert(bookmark);
            if (Collections3.isNotEmpty(bookmark.getTableColumnList()) && "2".equals(bookmark.getContentType()))
            {
                for (ContentTableColumn column : bookmark.getTableColumnList())
                {
                    column.setBookmark(bookmark);
                    baseDaoSupport.insert(column);
                }
            }
        }
    }
    
    @Override
    @Transactional
    public void modify(ReportTemplate template)
    {
        ReportTemplate rt = get(template.getId());
        for (ContentBookmark bk : rt.getBookmarkList())
        {
            for (ContentTableColumn column : bk.getTableColumnList())
            {
                baseDaoSupport.delete(column);
            }
            baseDaoSupport.delete(bk);
        }
        for (ContentBookmark bookmark : template.getBookmarkList())
        {
            bookmark.setReportTemplate(template);
            baseDaoSupport.insert(bookmark);
            if (Collections3.isNotEmpty(bookmark.getTableColumnList()) && "2".equals(bookmark.getContentType()))
            {
                for (ContentTableColumn column : bookmark.getTableColumnList())
                {
                    column.setBookmark(bookmark);
                    baseDaoSupport.insert(column);
                }
            }
        }
        baseDaoSupport.merge(template);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        ReportTemplate entity = get(id);
        entity.setDelFlag(true);
        baseDaoSupport.update(entity);
    }
    
    @Override
    public boolean validate(ReportTemplate request)
    {
        String hql = "FROM ReportTemplate rt WHERE rt.delFlag = false AND rt.name = :name";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("name")).values(Lists.newArrayList(request.getName())).build();
        List<ReportTemplate> list = baseDaoSupport.find(queryer, ReportTemplate.class);
        if (Collections3.isNotEmpty(list))
        {
            if (StringUtils.isEmpty(request.getId()))
            {
                return false;
            }
            else
            {
                ReportTemplate dt = Collections3.getFirst(list);
                if (!dt.getId().equals(request.getId()))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public List<ReportTemplate> list(ReportTemplateSearcher searcher)
    {
        Pagination<ReportTemplate> pagination = paging(searcher);
        if (null != pagination)
        {
            if (Collections3.isNotEmpty(pagination.getRecords()))
            {
                return pagination.getRecords();
            }
        }
        return null;
    }
}
