package com.todaysoft.lims.biology.adapter.impl;


import com.todaysoft.lims.biology.action.RequestHeaders;
import com.todaysoft.lims.biology.adapter.IUserAdapter;
import com.todaysoft.lims.biology.model.UserMinimalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@Service
public class UserAdapter implements IUserAdapter{

    @Autowired
    private RestTemplate template;

    @Override
    public UserMinimalModel getLoginUser(String token) {

        String url = GatewayService.getServiceUrl("/smm/authorize/user");
        template.setInterceptors(Collections.singletonList(new ClientHttpRequestInterceptor()
        {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException
            {
                request.getHeaders().set(RequestHeaders.MEMBER_TOKEN, token);
                return execution.execute(request, body);
            }
        }));
        return template.getForObject(url, UserMinimalModel.class);
    }
}
