package com.todaysoft.lims.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

@Configuration
@EnableWebSecurity
public class SpringSecurityContext extends WebSecurityConfigurerAdapter
{
    @Autowired
    private AuthenticationProvider authenticationProvider;
    
    @Autowired
    private AccessDecisionManager accessDecisionManager;
    
    @Value("${maximumSessions}")
    private Integer maximumSessions;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(authenticationProvider);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/login");
        web.ignoring().antMatchers("/bpm/report/generate/callback");//报告生成回调
        /*web.ignoring().antMatchers("/testing/biologyDivision/callBack");//报告生成回调
        web.ignoring().antMatchers("/testing/biologyAnnotation/callBack");//报告生成回调
        */web.ignoring().antMatchers("/testing/ngsSequecingCallBack");//报告生成回调
        web.ignoring().antMatchers("/order/resolveAccountStatement");//对账生成回调
        web.ignoring().antMatchers("/order/sync/*");//订单同部数据
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().antMatchers("/").authenticated();
        http.authorizeRequests().antMatchers("/**/*.do").authenticated().accessDecisionManager(accessDecisionManager);
        http.csrf().disable();
        
        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN));
        
        http.formLogin().loginPage("/login");
        http.formLogin().loginProcessingUrl("/login_process");
        http.formLogin().defaultSuccessUrl("/", true);
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/login");
        http.sessionManagement().maximumSessions(maximumSessions);
        
    }
    
}
