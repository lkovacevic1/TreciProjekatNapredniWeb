package com.example.TreciProjekatNapredniWeb.configuration;

import com.example.TreciProjekatNapredniWeb.filter.JwtFilter;
import com.example.TreciProjekatNapredniWeb.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailService userService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SpringSecurityConfig(UserDetailService userService, JwtFilter jwtFilter){
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService);
    }

    @Override
    protected  void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/user/login/**").permitAll()
                .antMatchers("/api/user/roles/**").permitAll()
                .antMatchers("/api/user/all/**").hasAuthority("can_read_users")
                .antMatchers("/api/user/create/**").hasAuthority("can_create_users")
                .antMatchers("/api/user/update/**").hasAuthority("can_update_users")
                .antMatchers("/api/user/delete/**").hasAuthority("can_delete_users")
                .antMatchers("/api/machine/all/**").hasAuthority("can_read_machines")
                .antMatchers("/api/machine/search/**").hasAuthority("can_search_machines")
                .antMatchers("/api/machine/start/**").hasAuthority("can_start_machines")
                .antMatchers("/api/machine/stop/**").hasAuthority("can_stop_machines")
                .antMatchers("/api/machine/restart/**").hasAuthority("can_restart_machines")
                .antMatchers("/api/machine/create/**").hasAuthority("can_create_machines")
                .antMatchers("/api/machine/destroy/**").hasAuthority("can_destroy_machines")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return  super.authenticationManager();
    }
}
