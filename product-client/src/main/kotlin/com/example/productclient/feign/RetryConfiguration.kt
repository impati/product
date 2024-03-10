package com.example.productclient.feign

import feign.Retryer
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class RetryConfiguration {

    @Bean
    fun defaultRetry(): Retryer {
        return Retryer.Default(100, 1000, 3)
    }

    @Bean
    fun myErrorDecoder(): ErrorDecoder {
        return DefaultErrorDecoder()
    }
}
