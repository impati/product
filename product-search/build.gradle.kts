import org.springframework.boot.gradle.tasks.bundling.BootJar


dependencies {
    implementation(project(":product-domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
