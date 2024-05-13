package com.example.moneysystem.filter;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiCustomizer;

public class HeaderFilter implements OpenApiCustomizer {

    private final String headerName;

    private final String HeaderValue;

    public HeaderFilter(String headerName, String HeaderValue) {
        this.headerName = headerName;
        this.HeaderValue = HeaderValue;
    }

    @Override
    public void customise(OpenAPI openApi) {
        openApi.getPaths().entrySet().removeIf(
                path -> path.getValue().getParameters().stream()
                .anyMatch(
                        parameter -> ParameterIn.HEADER.toString().equals(parameter.getIn())
                                && parameter.getName().equals(headerName)
                                && parameter.getSchema().getEnum().contains(HeaderValue)
                ));
//        openApi.getPaths().entrySet().removeIf(
//                path -> path.getValue()..readOperations().stream()
//                        .anyMatch(
//                                operation -> operation.
//                        ));
    }
}
