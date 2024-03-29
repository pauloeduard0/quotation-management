package com.inatel.quotationmanagement.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
@WebFilter(urlPatterns = "/quote")
@Order(-999)
@Slf4j
public class RequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        String requestBody = this.getContentAsString(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        if (requestBody.length() > 0) {
            log.info("Request body:\n{}", requestBody);
        }

        String responseBody = this.getContentAsString(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
        boolean includeResponsePayload = true;
        if (responseBody.length() > 0 && includeResponsePayload) {
            log.info("Response body:\n{}", responseBody);
        }
        responseWrapper.copyBodyToResponse();
    }

    private String getContentAsString(byte[] contentAsByteArray, String characterEncoding) throws UnsupportedEncodingException {
        if (contentAsByteArray == null || contentAsByteArray.length == 0) {
            return "";
        }
        return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
    }

}