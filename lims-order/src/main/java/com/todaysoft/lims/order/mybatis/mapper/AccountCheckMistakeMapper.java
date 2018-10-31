package com.todaysoft.lims.order.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.todaysoft.lims.order.mybatis.model.AccountCheckMistake;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeExample;

public interface AccountCheckMistakeMapper
{
    int countByExample(AccountCheckMistakeExample example);
    
    int deleteByExample(AccountCheckMistakeExample example);
    
    int deleteByPrimaryKey(String id);
    
    int insert(AccountCheckMistake record);
    
    int insertSelective(AccountCheckMistake record);
    
    List<AccountCheckMistake> selectByExample(AccountCheckMistakeExample example);
    
    AccountCheckMistake selectByPrimaryKey(String id);
    
    int updateByExampleSelective(@Param("record") AccountCheckMistake record, @Param("example") AccountCheckMistakeExample example);
    
    int updateByExample(@Param("record") AccountCheckMistake record, @Param("example") AccountCheckMistakeExample example);
    
    int updateByPrimaryKeySelective(AccountCheckMistake record);
    
    int updateByPrimaryKey(AccountCheckMistake record);
}