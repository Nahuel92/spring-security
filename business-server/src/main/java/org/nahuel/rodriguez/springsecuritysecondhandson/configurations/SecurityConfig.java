package org.nahuel.rodriguez.springsecuritysecondhandson.configurations;

import org.nahuel.rodriguez.springsecuritysecondhandson.security.authenticationproviders.OtpAuthenticationProvider;
import org.nahuel.rodriguez.springsecuritysecondhandson.security.authenticationproviders.UsernamePasswordAuthenticationProvider;
import org.nahuel.rodriguez.springsecuritysecondhandson.security.filters.InitialAuthenticationFilter;
import org.nahuel.rodriguez.springsecuritysecondhandson.security.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final OtpAuthenticationProvider otpAuthenticationProvider;
    @Autowired
    private InitialAuthenticationFilter initialAuthenticationFilter;

    public SecurityConfig(final JwtAuthenticationFilter jwtAuthenticationFilter,
                          final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider,
                          final OtpAuthenticationProvider otpAuthenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
        this.otpAuthenticationProvider = otpAuthenticationProvider;
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
                .authenticationProvider(otpAuthenticationProvider);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
