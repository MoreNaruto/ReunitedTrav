package com.tmorris.reunitedtrav.config;

import com.tmorris.reunitedtrav.security.JWTAuthenticationEntryPoint;
import com.tmorris.reunitedtrav.security.JWTAuthenticationFilter;
import com.tmorris.reunitedtrav.security.JWTAuthorizationFilter;
import com.tmorris.reunitedtrav.security.util.JwtTokenUtil;
import com.tmorris.reunitedtrav.services.JpaAccountUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JpaAccountUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS,"/**")
                    .permitAll()
                .antMatchers(HttpMethod.POST, "/travelers/sign-up").permitAll()
                .antMatchers(HttpMethod.GET, "/token-validity").permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/travelers").hasAnyAuthority("ADMIN", "AGENT", "TRAVELER")
                .antMatchers("/agents").hasAnyAuthority("ADMIN", "AGENT")
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(jwtUserDetailsService, jwtTokenUtil, authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtTokenUtil))
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
