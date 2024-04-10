package com.example.productsearch.config

import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import java.time.Duration

@Configuration
class SearchConfig(
    val properties: ElasticsearchProperties
) : ElasticsearchConfiguration() {

    override fun clientConfiguration(): ClientConfiguration {

        return ClientConfiguration.builder()
            .connectedTo(*properties.uris.toTypedArray())
            .withConnectTimeout(Duration.ofSeconds(5))
            .withSocketTimeout(Duration.ofSeconds(3))
            .build()
    }
}
