import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

group = "gongback"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // db
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    testImplementation("com.h2database:h2:2.2.222")

    // flyway
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")

    // spring cloud aws + aws parameter store
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.2"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-parameter-store")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:4.0.4")

    // sentry
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:6.32.0")

    // redis
    implementation("org.redisson:redisson-spring-boot-starter:3.23.4")

    // kotlin-jdsl
    implementation("com.linecorp.kotlin-jdsl:hibernate-kotlin-jdsl-jakarta:2.2.1.RELEASE")

    // opensearch
    implementation("org.opensearch.client:spring-data-opensearch-starter:1.2.0") {
        exclude("org.opensearch.client", "opensearch-rest-client-sniffer")
    }
    implementation("org.opensearch.client:spring-data-opensearch-test-autoconfigure:1.2.0") {
        exclude("org.opensearch.client", "opensearch-rest-client-sniffer")
    }

    // springdoc
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // http interface
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // aws s3
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // jwt
    compileOnly("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    ktlint {
        verbose.set(true)
    }

    withType<Test> {
        useJUnitPlatform()
    }
}
