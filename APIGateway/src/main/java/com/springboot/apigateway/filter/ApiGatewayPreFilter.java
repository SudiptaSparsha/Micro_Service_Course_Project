package com.springboot.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@Slf4j
public class ApiGatewayPreFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("pre-filter is executed!");
        String requestPath = exchange.getRequest().getPath().toString();
        log.info("Request path = " + requestPath);
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Set<String> headerNameSet = headers.keySet();
        headerNameSet.forEach((headerName) -> {
            String headerValue = headers.getFirst(headerName);
            log.info(headerName + " " + headerValue);
        });
        return chain.filter(exchange);
    }
}
