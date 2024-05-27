package com.example.moneysystem.service;

import com.example.moneysystem.dto.ContentTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.UnsupportedEncodingException;

@Component
@RequestScope
public class ContentTraceManager {

    private ContentTrace trace;

    public ContentTraceManager() {
    }

    protected static Logger logger = LoggerFactory.getLogger(ContentTraceManager.class);

    public void updateBody(ContentCachingRequestWrapper wrappedRequest,
                           ContentCachingResponseWrapper wrappedResponse) {

        String requestBody = getRequestBody(wrappedRequest);
        getTrace().setRequestBody(requestBody);

        String responseBody = getResponseBody(wrappedResponse);
        getTrace().setResponseBody(responseBody);
    }

    protected String getRequestBody(
            ContentCachingRequestWrapper wrappedRequest) {
        try {
            if (wrappedRequest.getContentLength() <= 0) {
                return null;
            }
            return new String(wrappedRequest.getContentAsByteArray(), 0,
                    wrappedRequest.getContentLength(),
                    wrappedRequest.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            logger.error(
                    "Could not read cached request body: " + e.getMessage());
            return null;
        }

    }

    protected String getResponseBody(
            ContentCachingResponseWrapper wrappedResponse) {

        try {
            if (wrappedResponse.getContentSize() <= 0) {
                return null;
            }
            return new String(wrappedResponse.getContentAsByteArray(), 0,
                    wrappedResponse.getContentSize(),
                    wrappedResponse.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            logger.error(
                    "Could not read cached response body: " + e.getMessage());
            return null;
        }

    }

    public ContentTrace getTrace() {
        if (trace == null) {
            trace = new ContentTrace();
        }
        return trace;
    }
}
