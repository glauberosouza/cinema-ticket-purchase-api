package com.glauber.cinema.Ticket.configuration.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Springapiteste-public")
                .pathsToMatch("/purchases/**", "/tickets/**")
                .build();
    }
}
