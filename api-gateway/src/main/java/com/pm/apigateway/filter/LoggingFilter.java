package com.pm.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingFilter implements GatewayFilterFactory<LoggingFilter.Config> {
    public static class Config {
        // can add config props if needed
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("Request path: " + exchange.getRequest().getPath());
            return chain.filter(exchange);
        };
    }
}
