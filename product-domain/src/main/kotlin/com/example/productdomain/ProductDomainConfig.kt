package com.example.productdomain

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ComponentScan(basePackageClasses = [ProductDomainBase::class])
class ProductDomainConfig {
}
