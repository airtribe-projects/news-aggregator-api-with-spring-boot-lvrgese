package com.lvrgese.news_aggregator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean(name = "gNewsClient")
    public WebClient gNewsClient(WebClient.Builder builder) {
        return builder.baseUrl("https://gnews.io/api/v4")
                .build();
    }
}
