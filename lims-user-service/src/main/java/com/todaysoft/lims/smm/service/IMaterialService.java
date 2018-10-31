package com.todaysoft.lims.smm.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.MaterialSearcher;
import com.todaysoft.lims.smm.entity.Material;
import com.todaysoft.lims.smm.request.MaterialCreateRequest;

public interface IMaterialService
{
    Pagination<Material> paging(MaterialSearcher searcher);
    void create(MaterialCreateRequest request);
    boolean validate(MaterialSearcher searcher);
    Material getById(String id);
    void modify(MaterialCreateRequest request);
    Integer delete(String id);
    void disable(String id);
    void enable(String id);
    List<Material> getSortList();
    Material getByName(MaterialSearcher searcher);
}
