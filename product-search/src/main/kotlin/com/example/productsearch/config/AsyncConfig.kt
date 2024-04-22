package com.example.productsearch.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableAsync

@Profile("prod | beta")
@EnableAsync
@Configuration
class AsyncConfig {
}
