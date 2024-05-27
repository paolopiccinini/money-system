package com.example.moneysystem.service;

import com.example.moneysystem.dto.ContentTrace;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class CustomHttpTraceRepository implements HttpExchangeRepository {

    private final List<ContentTrace> contents = new LinkedList<>();

    private final ContentTraceManager traceManager;

    public CustomHttpTraceRepository(ContentTraceManager traceManager) {
        super();
        this.traceManager = traceManager;
    }

    @Override
    public void add(HttpExchange trace) {
        synchronized (this.contents) {
            ContentTrace contentTrace = traceManager.getTrace();
            contentTrace.setHttpExchange(trace);
            this.contents.add(0, contentTrace);
        }
    }

    @Override
    public List<HttpExchange> findAll() {
        synchronized (this.contents) {
            return contents.stream().map(ContentTrace::getHttpExchange)
                    .toList();
        }
    }

    public List<ContentTrace> findAllWithContent() {
        synchronized (this.contents) {
            return Collections.unmodifiableList(new ArrayList<>(this.contents));
        }
    }

}
