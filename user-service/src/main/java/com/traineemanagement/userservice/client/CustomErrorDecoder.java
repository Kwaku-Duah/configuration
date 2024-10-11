package com.traineemanagement.userservice.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traineemanagement.userservice.exception.GenericErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper mapper = new ObjectMapper();

    
    /** 
     * @param methodKey
     * @param response
     * @return Exception
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        // Check if the response body is null
        if (response.body() == null) {
            return new RuntimeException("Received null response body for method: " + methodKey);
        }

        try (InputStream body = response.body().asInputStream()) {
            // Convert the response body to a Map
            Map<String, String> errors = mapper.readValue(
                IOUtils.toString(body, StandardCharsets.UTF_8), 
                new TypeReference<Map<String, String>>() {}
            );
            
            // Handle specific HTTP status codes
            if (response.status() == 400) {
                return new RuntimeException("Validation error: " + errors);
            } else {
                return GenericErrorResponse
                        .builder()
                        .httpStatus(HttpStatus.valueOf(response.status()))
                        .message(errors.getOrDefault("error", "Unknown error occurred"))
                        .build();
            }
        } catch (IOException exception) {
            // Handle JSON parsing or other IO issues
            return GenericErrorResponse.builder()
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .message("Error decoding response: " + exception.getMessage())
                    .build();
        }
    }
}
