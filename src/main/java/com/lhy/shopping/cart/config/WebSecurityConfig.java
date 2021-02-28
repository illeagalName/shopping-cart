package com.lhy.shopping.cart.config;


import com.lhy.shopping.cart.component.CustomAuthenticationSuccessHandler;
import com.lhy.shopping.cart.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
        super.configure(web);
    }

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/orderList", "/admin/order", "/admin/accountInfo")
                .hasAnyRole("ROLE_EMPLOYEE", "ROLE_MANAGER")
                .antMatchers("/admin/product").access("hasRole('ROLE_MANAGER')")
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .defaultSuccessUrl("/accountInfo")
                .failureUrl("/login?error=true")
                .usernameParameter("userName")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler)
                .and()
                .logout().logoutSuccessUrl("/index")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .authorizeRequests()
                .antMatchers("/login","/index","/style.css").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();

    }
}
