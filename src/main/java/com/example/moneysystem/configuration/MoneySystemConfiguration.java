package com.example.moneysystem.configuration;

import com.example.moneysystem.constants.Constants;
import com.example.moneysystem.filter.HeaderFilter;
import com.example.moneysystem.interceptor.Slf4jMDCInterceptor;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "config.moneysystem")
public class MoneySystemConfiguration implements WebMvcConfigurer {
    private String slf4MDCHeader;

    // NOT USED register filter
//    @Bean
//    public FilterRegistrationBean<Slf4jMDCFilter> servletRegistrationBean() {
//        final FilterRegistrationBean<Slf4jMDCFilter> registrationBean = new FilterRegistrationBean<>();
//        final Slf4jMDCFilter log4jMDCFilterFilter = new Slf4jMDCFilter(header);
//        registrationBean.setFilter(log4jMDCFilterFilter);
//        registrationBean.addUrlPatterns("/api/*");
//        registrationBean.setOrder(2);
//        return registrationBean;
//    }

    @Bean
    public OpenAPI openAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8090");
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("paolo.piccinini88@gmail.com");
        contact.setName("Paolo Piccinini");

        Info info = new Info()
                .title("UK money system before 1970 calculation API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to do calculations with UK money system before 1970.");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }

    // Tryied to override Accept header NOT WORKING
//    @Bean
//    public OperationCustomizer addCustomGlobalHeader() {
//        return (Operation operation, HandlerMethod handlerMethod) -> {
//            Parameter headerParameter = new Parameter().in(ParameterIn.HEADER.toString()).required(true).
//                    schema(new StringSchema().
//                            _default(Constants.VERSION_1_HEADER)).name("accept");
//            operation.addParametersItem(headerParameter);
//            return operation;
//        };
//    }


    //https://github.com/springdoc/springdoc-openapi/issues/449
    //NOT working there is no way to get the headers in HeaderFilter
//    @Bean
//    public GroupedOpenApi groupOpenApi() {
//        return GroupedOpenApi.builder()
//                .group("v1").packagesToScan("com.example.moneysystem.controller")
//                .addOpenApiCustomizer(new HeaderFilter("ciao", Constants.VERSION_1_HEADER))
//                .build();
//    }

    // to expose /actuator/httpExchange
    @Bean
    public HttpExchangeRepository httpExchangeRepository() {
        return new InMemoryHttpExchangeRepository();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Slf4jMDCInterceptor(slf4MDCHeader));
    }

    public void setSlf4MDCHeader(String slf4MDCHeader) {
        this.slf4MDCHeader = slf4MDCHeader;
    }
}
