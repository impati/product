package com.example.productdomain

import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties
@EnableJpaRepositories(
    basePackageClasses = [ProductDomainBase::class]
)
@EntityScan(basePackageClasses = [ProductDomainBase::class])
class ProductJpaConfig {


    @Bean("entityManagerFactory")
    fun entityManagerFactory(
        jpaProperties: JpaProperties,
        hibernateProperties: HibernateProperties
    ): LocalContainerEntityManagerFactoryBean {
        return EntityManagerFactoryBuilder(HibernateJpaVendorAdapter(), jpaProperties.properties, null)
            .dataSource(dataSource())
            .properties(hibernateProperties.determineHibernateProperties(jpaProperties.properties, HibernateSettings()))
            .persistenceUnit("productDomain")
            .packages(ProductDomainBase::class.java)
            .build()
    }

    @Bean("transactionManager")
    fun platformTransactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa")
    fun jpaProperties(): JpaProperties = JpaProperties()

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa.hibernate")
    fun hibernateProperties(): HibernateProperties = HibernateProperties()

    @Bean("datasource")
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build();
}
