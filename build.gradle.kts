import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {

    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("kapt") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allprojects {
    repositories {
        mavenCentral()
    }

    group = "com.example"
    version = "0.0.1-SNAPSHOT"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {

    apply {
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("kotlin-allopen")
    }

    dependencies {

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        implementation("org.springframework.boot:spring-boot-starter")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.junit.jupiter:junit-jupiter")
    }
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

