package com.example.moneysystem.service;

import com.example.moneysystem.dto.ContentTrace;
import com.example.moneysystem.dto.ContentTraceDescriptor;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.web.exchanges.HttpExchangesEndpoint;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EndpointWebExtension(endpoint = HttpExchangesEndpoint.class)
public class HttpTraceEndpointExtension {

    private final CustomHttpTraceRepository repository;

    public HttpTraceEndpointExtension(CustomHttpTraceRepository repository) {
        super();
        this.repository = repository;
    }

    @ReadOperation
    public ContentTraceDescriptor contents() {
        List<ContentTrace> traces = repository.findAllWithContent();
        return new ContentTraceDescriptor(traces);
    }
}
