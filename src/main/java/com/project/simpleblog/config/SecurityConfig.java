package com.project.simpleblog.config;

import com.project.simpleblog.domain.UserRoleEnum;
import com.project.simpleblog.jwt.JwtAuthenticationFilter;
import com.project.simpleblog.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final List<String> permitAllUrls = new ArrayList<>();
    private final List<String> permitAllWithGetMethodUrls = new ArrayList<>();
    private final List<String> permitAdminUrls = new ArrayList<>();

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @PostConstruct
    public void init() {
        permitAllUrls.add("/api/**/sign-up");
        permitAllUrls.add("/api/**/sign-in");

        permitAllWithGetMethodUrls.add("/api/boards");
        permitAllWithGetMethodUrls.add("/api/boards/{id}");
        permitAllWithGetMethodUrls.add("/{username}/categories/{categoryName}/boards");
        permitAllWithGetMethodUrls.add("/api/users/{username}/categories");

        permitAdminUrls.add("/api/admin/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                    .httpBasic().disable()
                    .formLogin().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers(permitAllUrls.toArray(String[]::new)).permitAll()
                    .antMatchers(HttpMethod.GET, permitAllWithGetMethodUrls.toArray(String[]::new)).permitAll()
                    .antMatchers(permitAdminUrls.toArray(String[]::new)).hasRole(UserRoleEnum.ADMIN.name())
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
