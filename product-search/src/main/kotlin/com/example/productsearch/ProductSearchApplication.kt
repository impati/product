package com.example.productsearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["com.example.productsearch", "com.example.productdomain"]
)
class ProductSearchApplication

fun main(args: Array<String>) {
    runApplication<ProductSearchApplication>(*args)
}
