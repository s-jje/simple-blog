package com.project.simpleblog.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final List<RequestMatcher> requestMatcherList = new ArrayList<>();

    private final JwtTokenProvider jwtTokenProvider;

    @PostConstruct
    public void init() {
        requestMatcherList.add(new AntPathRequestMatcher("/api/**/sign-up", HttpMethod.POST.name()));
        requestMatcherList.add(new AntPathRequestMatcher("/api/**/sign-in", HttpMethod.POST.name()));
        requestMatcherList.add(new AntPathRequestMatcher("/api/boards", HttpMethod.GET.name()));
        requestMatcherList.add(new AntPathRequestMatcher("/api/boards/{id}", HttpMethod.GET.name()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("requestUrl: {} {}", request.getMethod(), request.getRequestURL());

        String token = jwtTokenProvider.resolveToken(request);

        if (token != null) {
            if (!jwtTokenProvider.isValidToken(token)) {
                return;
            }
            String username = jwtTokenProvider.getUsername(token);
            Authentication authentication = jwtTokenProvider.createAuthentication(username);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return requestMatcherList.stream().anyMatch(requestMatcher -> requestMatcher.matches(request));
    }

}
