package com.example.productsearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@SpringBootApplication(
    scanBasePackages = ["com.example.productsearch", "com.example.productdomain"]
)
@EnableElasticsearchRepositories
class ProductSearchApplication

fun main(args: Array<String>) {
    runApplication<ProductSearchApplication>(*args)
}
