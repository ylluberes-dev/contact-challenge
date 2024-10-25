package com.ylluberes.kenectlabs.contactchallenge.security;

import io.netty.util.internal.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
public class AuthenticationFilter extends GenericFilterBean {

    public static final String API_KEY_HEADER = "API-Key";
    public static final String SECRET_KEY_HEADER = "API-Secret";

    private String validApiKey;
    private String validSecretKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            String apiKey = extractApiKey((HttpServletRequest) request);
            String secretKey = extractSecretKey((HttpServletRequest) request);
            if(StringUtil.isNullOrEmpty(apiKey) || StringUtil.isNullOrEmpty(secretKey)) {
                throw new BadCredentialsException("Missing API key or SECRET key");
            }
            if(validApiKey.equals(apiKey) && validSecretKey.equals(secretKey)) {
                ApiKeyAuth authentication = new ApiKeyAuth(apiKey,secretKey);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new BadCredentialsException("Invalid API key or SECRET key");
            }

        } catch (Exception exp) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            writer.print(exp.getMessage());
            writer.flush();
            writer.close();
        }

        filterChain.doFilter(request, response);
    }

    private String extractSecretKey (HttpServletRequest request) {
        return request.getHeader(SECRET_KEY_HEADER);
    }

    private String extractApiKey (HttpServletRequest request) {
        return request.getHeader(API_KEY_HEADER);
    }
}