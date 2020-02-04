//
//package io.turntabl.config;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("v1/api/hello");
//    }
//
////    @Override
////    protected void configure(HttpSecurity httpSecurity) throws Exception {
////              httpSecurity.authorizeRequests().anyRequest().permitAll().and().httpBasic();
////              httpSecurity.csrf().disable();
////    }
//
//}
