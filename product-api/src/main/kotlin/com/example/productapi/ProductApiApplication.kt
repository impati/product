package com.example.productapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["com.example.productapi", "com.example.productdomain"]
)
class ProductApiApplication

fun main(args: Array<String>) {
    runApplication<ProductApiApplication>(*args)
}
