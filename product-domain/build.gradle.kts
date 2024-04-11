import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.jpa") version "1.9.22"
    id("org.flywaydb.flyway") version "10.8.1"
}


buildscript {

    dependencies {
        classpath("org.flywaydb:flyway-mysql:10.8.1")
    }
}


dependencies {
    implementation(project(":product-common"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
}

flyway {
    cleanDisabled = false
    url = "jdbc:mysql://localhost:3306/product"
    user = "impati"
    password = "impati"
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
