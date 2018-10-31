package com.todaysoft.lims.biology.adapter;

import com.todaysoft.lims.biology.model.response.DictModel;

import java.util.List;

public interface IDictAdapter
{
    DictModel getDictByText(String category, String text);
    
    List<DictModel> getDictByCategory(String category);
}
