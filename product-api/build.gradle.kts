import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.asciidoctor.jvm.convert") version "4.0.2"
}



dependencies {

    implementation(project(":product-domain"))
    implementation(project(":product-search"))
    implementation(project(":product-client"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    implementation("software.amazon.awssdk:s3")
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks {
    val snippetsDir by extra { file("build/generated-snippets") }

    test {
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        attributes(mapOf("snippets" to snippetsDir))
        doFirst {
            delete("src/main/resources/static/docs")
        }
        inputs.dir(snippetsDir)
        dependsOn(test)
        doLast {
            copy {
                from("build/docs/asciidoc")
                into("src/main/resources/static/docs")
            }
        }
    }

    build {
        dependsOn(asciidoctor)
    }
}
