package com.todaysoft.lims.testing.base.adapter;

import org.springframework.stereotype.Component;

public interface IDictAdapter
{
    String getDictTestByCategoryAndValue(String category,String value);
}
