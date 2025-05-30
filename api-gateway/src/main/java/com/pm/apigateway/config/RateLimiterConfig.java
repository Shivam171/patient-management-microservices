package com.pm.apigateway.config;

import com.pm.apigateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class RateLimiterConfig {
    // Rate limiting key resolver based on client IP address
    @Bean
    @Primary
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                Objects.requireNonNull(exchange.getRequest().getRemoteAddress())
                        .getAddress()
                        .getHostAddress()
        );
    }

    // Rate limiting key resolver based on client IP address
    @Bean
    public KeyResolver userKeyResolver(JwtUtil jwtUtil) {
        return exchange -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                try {
                    String token = authHeader.substring(7);
                    String subject = jwtUtil.extractSubject(token);
                    return Mono.just(subject);
                } catch (Exception e) {
                    return Mono.just("anonymous-user"); // fallback
                }
            }

            return Mono.just("anonymous-user");
        };
    }
}
