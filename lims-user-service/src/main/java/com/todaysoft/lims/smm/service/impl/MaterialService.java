package com.todaysoft.lims.smm.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.MaterialSearcher;
import com.todaysoft.lims.smm.entity.Material;
import com.todaysoft.lims.smm.request.MaterialCreateRequest;
import com.todaysoft.lims.smm.service.IMaterialService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class MaterialService implements IMaterialService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Material> paging(MaterialSearcher searcher)
    {
        return baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), Material.class);
    }
    
    @Override
    @Transactional
    public void create(MaterialCreateRequest request)
    {
        Material material = new Material();
        BeanUtils.copyProperties(request, material);
        material.setCreateTime(new Date());
        material.setState(false);
        material.setDelFlag(false);
        baseDaoSupport.insert(material);
    }
    
    @Override
    public boolean validate(MaterialSearcher searcher)
    {
        List<Material> list = baseDaoSupport.find(searcher.toQuery(), Material.class);
        if (Collections3.isNotEmpty(list))
        {
            Material material = Collections3.getFirst(list);
            if (material.getId().equals(searcher.getId()))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Material getById(String id)
    {
        return baseDaoSupport.get(Material.class, id);
    }
    
    @Override
    @Transactional
    public void modify(MaterialCreateRequest request)
    {
        Material material = baseDaoSupport.get(Material.class, request.getId());
        material.setName(request.getName());
        material.setDescription(request.getDescription());
        material.setMaterialSort(request.getMaterialSort());
        baseDaoSupport.update(material);
    }
    
    @Override
    @Transactional
    public Integer delete(String id)
    {
        Material material = baseDaoSupport.get(Material.class, id);
        if(1 == material.getType())
        {
            NamedQueryer queryer =
                NamedQueryer.builder()
                    .hql("FROM Material m where m.delFlag = 0 AND m.type = 2 AND m.materialSort.id = :sortId")
                    .names(Arrays.asList("sortId"))
                    .values(Arrays.asList(id))
                    .build();
            List<Material> list = baseDaoSupport.find(queryer,Material.class);
            if(Collections3.isNotEmpty(list))
            {
                return 1;
            }
        }
        material.setDelFlag(true);
        material.setDeleteTime(new Date());
        baseDaoSupport.update(material);
        return null;
    }

    @Override
    @Transactional
    public void disable(String id)
    {
        Material material = baseDaoSupport.get(Material.class, id);
        material.setState(true);
        baseDaoSupport.update(material);
    }

    @Override
    @Transactional
    public void enable(String id)
    {
        Material material = baseDaoSupport.get(Material.class, id);
        material.setState(false);
        baseDaoSupport.update(material);
        
    }

    @Override
    public List<Material> getSortList()
    {
        List<Material> list = baseDaoSupport.find(Material.class, "FROM Material m where m.delFlag = 0 AND m.type = 1 ORDER BY m.state,m.createTime DESC");
        return list;
    }

    @Override
    public Material getByName(MaterialSearcher searcher)
    {
        List<Material> list = baseDaoSupport.find(searcher.toQuery(), Material.class);
        if(Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
}
