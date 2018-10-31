package com.todaysoft.lims.order.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeScratchPool;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeScratchPoolExample;

public interface AccountCheckMistakeScratchPoolMapper
{
    int countByExample(AccountCheckMistakeScratchPoolExample example);
    
    int deleteByExample(AccountCheckMistakeScratchPoolExample example);
    
    int deleteByPrimaryKey(String id);
    
    int insert(AccountCheckMistakeScratchPool record);
    
    int insertSelective(AccountCheckMistakeScratchPool record);
    
    List<AccountCheckMistakeScratchPool> selectByExample(AccountCheckMistakeScratchPoolExample example);
    
    AccountCheckMistakeScratchPool selectByPrimaryKey(String id);
    
    int updateByExampleSelective(@Param("record") AccountCheckMistakeScratchPool record, @Param("example") AccountCheckMistakeScratchPoolExample example);
    
    int updateByExample(@Param("record") AccountCheckMistakeScratchPool record, @Param("example") AccountCheckMistakeScratchPoolExample example);
    
    int updateByPrimaryKeySelective(AccountCheckMistakeScratchPool record);
    
    int updateByPrimaryKey(AccountCheckMistakeScratchPool record);
}