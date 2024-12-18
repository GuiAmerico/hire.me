package com.bemobi.encurtadorurl.config.cors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsConfig extends OncePerRequestFilter {

  @Value("${app.cors.allow-origin}")
  private String ALLOW_ORIGIN_HEADER;
  private static final String ALLOW_CREDENTIALS_VALUE = "true";
  private static final String ALLOW_METHODS_VALUE = "POST, GET, PUT, OPTIONS, DELETE, PATCH";
  private static final String MAX_AGE_VALUE = "3600";
  private static final String ALLOW_HEADERS_VALUE = "Origin, X-Requested-With, Content-Type, Accept, Authorization, Accept-Language";
  private static final String EXPOSE_HEADERS_VALUE = "Location, Content-Disposition, Content-Type, Authorization, Set-Cookie";
  private static final String VARY_VALUE = "Origin";

  @Override
  public void doFilterInternal(
    HttpServletRequest httpReq,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ALLOW_ORIGIN_HEADER);
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOW_CREDENTIALS_VALUE);
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOW_METHODS_VALUE);
    response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE_VALUE);
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOW_HEADERS_VALUE);
    response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, EXPOSE_HEADERS_VALUE);
    response.setHeader(HttpHeaders.VARY, VARY_VALUE);
    filterChain.doFilter(httpReq, response);
  }
}