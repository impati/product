import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {

    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("kapt") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("org.sonarqube") version "4.2.1.3168"
    jacoco
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
}

subprojects {

    apply {
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("kotlin-allopen")
        plugin("jacoco")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.0")
        }
    }

    dependencies {

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        compileOnly("io.github.oshai:kotlin-logging-jvm:4.0.0")

        implementation("org.springframework.boot:spring-boot-starter")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    jacoco {
        toolVersion = "0.8.8" // 사용할 JaCoCo 버전 지정
    }

    tasks.test {
        finalizedBy(tasks.jacocoTestReport) // 테스트 태스크가 성공적으로 완료되면 JaCoCo 리포트 생성 태스크를 실행
    }

    tasks.jacocoTestReport {

        dependsOn("test") // 리포트 생성 전 테스트 실행

        reports {
            xml.required = true
            html.required = true
            csv.required = false
        }
    }

    sonarqube {
        properties {
            property("sonar.projectKey", "impati_product")
            property("sonar.projectName", "product")
            property("sonar.organization", "impati")
            property("sonar.host.url", "https://sonarcloud.io")
            property(
                "sonar.coverage.jacoco.xmlReportPaths",
                "${projectDir}/build/reports/jacoco/test/jacocoTestReport.xml"
            )
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }
}

tasks.register("jacocoRootReport", JacocoReport::class) {
    // 모든 서브 프로젝트의 'test' 태스크에 의존성 추가
    dependsOn(subprojects.map { "${it.path}:test" })

    // 추가 소스 디렉터리 설정
    additionalSourceDirs.setFrom(files(subprojects.map { "${it.projectDir}/src/main/kotlin" }))

    // 소스 디렉터리 설정
    sourceDirectories.setFrom(files(subprojects.map { "${it.projectDir}/src/main/kotlin" }))

    // 클래스 디렉터리 설정
    classDirectories.setFrom(files(subprojects.flatMap {
        it.fileTree("${it.projectDir}/build/classes/kotlin/main").files
    }))

    // 실행 데이터(executionData) 설정
    executionData.setFrom(files(subprojects.map {
        it.fileTree("${it.projectDir}/build").include("/jacoco/test.exec")
    }))

    // 리포트 설정
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "impati_product")
        property("sonar.projectName", "product")
        property("sonar.organization", "impati")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}


tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}


tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}


