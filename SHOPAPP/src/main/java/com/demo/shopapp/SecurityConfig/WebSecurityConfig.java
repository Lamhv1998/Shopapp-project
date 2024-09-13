package com.demo.shopapp.SecurityConfig;

import com.demo.shopapp.components.JwtTokenUtil;
import com.demo.shopapp.filters.JwtTokenFilter;
import com.demo.shopapp.model.Role;
import com.demo.shopapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(
                                    apiPrefix + "/users/register",
                                    apiPrefix + "/users/login")
                            .permitAll()
                            .requestMatchers(HttpMethod.POST, apiPrefix + "/orders/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, apiPrefix+"/orders/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, apiPrefix+"/orders/**").hasAnyRole(Role.USER,Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, apiPrefix+"/orders/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.POST, apiPrefix + "/categories/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, apiPrefix+"/categories/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, apiPrefix+"/categories?**").hasAnyRole(Role.USER,Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, apiPrefix+"/categories/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.POST, apiPrefix + "/products/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, apiPrefix+"/products/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, apiPrefix+"/products**").hasAnyRole(Role.USER,Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, apiPrefix+"/products/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.POST, apiPrefix + "/order_details/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, apiPrefix+"/order_details/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, apiPrefix+"/order_details/**").hasAnyRole(Role.USER,Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, apiPrefix+"/order_details/**").hasRole(Role.ADMIN)

                            .anyRequest().authenticated();
                });
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Auth-Token"));
                configuration.setExposedHeaders(List.of("X-Auth-Token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
                return http.build();
    }
}
