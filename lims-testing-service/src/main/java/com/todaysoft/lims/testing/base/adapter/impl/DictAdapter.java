package com.todaysoft.lims.testing.base.adapter.impl;

import com.todaysoft.lims.testing.base.adapter.IDictAdapter;
import com.todaysoft.lims.testing.base.model.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DictAdapter extends AbstractAdapter implements IDictAdapter {

    private static final String SERVICE_NAME = "lims-user-service";

    @Autowired
    private RestTemplate template;

    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }

    @Override
    public String getDictTestByCategoryAndValue(String category, String value) {
        String text = "";
        if (value.indexOf("/") != -1)
        {
            value = value.replace("/", "_");
        }

        String url = getServiceUrl("/smm/dict/{category}/entries/{value}");
        Dict dict =  template.getForObject(url, Dict.class, category, value);
        if(null != dict)
        {
            text = dict.getText();
        }
        return text;
    }
}
