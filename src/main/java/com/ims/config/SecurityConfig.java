package com.ims.config;
import com.ims.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    // User Creation
    @Autowired
    public UserDetailsService userDetailsService;

    // Configuring HttpSecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().cors().and()
                .authorizeHttpRequests()
                .requestMatchers("api/v1/auth/signin","/api/v1/auth/**", "api/v1/noauth/**","api/v1/noauth/inventory-details/**", "api/v1/auth/signup/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/v1/auth/merchant/**","/api/v1/auth/current-user","/api/test/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/v1/auth/categories/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/v1/auth/brands/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/v1/auth/locations/**","/api/v1/auth/loanoffer/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/v1/auth/products/**","/api/v1/auth/contract","/api/v1/auth/products/update","/api/v1/auth/loanapplications/**","/api/v1/auth/loanapplications/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/test/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/auth/user/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/auth/admin/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
