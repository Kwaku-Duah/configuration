package com.traineemanagement.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.traineemanagement.gateway.Util.JwtUtil;
import com.traineemanagement.gateway.exceptions.InvalidHeaderException;
import com.traineemanagement.gateway.exceptions.NoAuthorizationHeaderException;
import com.traineemanagement.gateway.exceptions.UnauthorizedAccessException;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private RouteValidator validator;

    @Autowired
    private RestTemplate template;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    
    /** 
     * @param config
     * @return GatewayFilter
     */
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new NoAuthorizationHeaderException("Missing Authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7); 
                } else {
                    throw new InvalidHeaderException("Invalid Authorization header format");
                }

                try {
                    logger.info("Validating token with USER-SERVICE...");
                    template.getForObject("http://USER-SERVICE/v1/auth/validate?token=" + authHeader, String.class);
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    throw new UnauthorizedAccessException("UNAUTHORIZED ACCESS TO THE APPLICATION");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
