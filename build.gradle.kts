import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
}

group = "com.travel"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	// dev tools
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// core
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// security
	implementation("com.auth0:java-jwt:4.4.0")
	implementation("org.springframework.boot:spring-boot-starter-security")
//	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
//	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
//	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
//	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
//	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
//	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

	// Mysql DB
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-mysql")
	runtimeOnly("com.mysql:mysql-connector-j")

	// logging
	implementation("io.github.microutils:kotlin-logging:3.0.5")

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
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
