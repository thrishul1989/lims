package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.ProductPrincipal;

public interface ProductPrincipalMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProductPrincipal record);

    int insertSelective(ProductPrincipal record);

    ProductPrincipal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductPrincipal record);

    int updateByPrimaryKey(ProductPrincipal record);
    
    List<ProductPrincipal> getByProduct(String productId);
}