//package io.turntabl.config;
//import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/api/v1/hello");
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("erbynn").password("john").roles("ADMIN").and()
//                .withUser("eaph").password("erbynn").roles("USER");
//    }

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeRequests()
//                .anyRequest()
//                .fullyAuthenticated()
//                //.antMatchers("**/rest/*")
//                .and()
//                //.addFilterBefore(customFilter(), BasicAuthenticationFilter.class)
//                .httpBasic();
//        httpSecurity.csrf().disable();


//        httpSecurity.cors().and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/public").permitAll()
//                .anyRequest().authenticated();
//    }

//}
