package com.example.moneysystem.dto;

import org.springframework.boot.actuate.web.exchanges.HttpExchange;

public class ContentTrace {

    private HttpExchange httpExchange;

    private String requestBody;

    private String responseBody;

    public ContentTrace() {
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public HttpExchange getHttpExchange() {
        return httpExchange;
    }

    public void setHttpExchange(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }
}
