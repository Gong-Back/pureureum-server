import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
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

	// db
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	testImplementation("com.h2database:h2:1.4.200")

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

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
