import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
	java
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm")
}

group = "kz.miniland"
version = "0.0.1-SNAPSHOT"

java {
	targetCompatibility = JavaVersion.VERSION_21
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.security:spring-security-oauth2-client")
	implementation("io.jsonwebtoken:jjwt:0.9.1")

	implementation("org.mapstruct:mapstruct:1.5.3.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

	implementation("io.micrometer:micrometer-tracing-bridge-brave")

	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	implementation(kotlin("stdlib-jdk8"))

	implementation("org.keycloak:keycloak-admin-client:21.0.1")

	implementation("org.apache.poi:poi:5.2.0")
	implementation("org.apache.poi:poi-ooxml:5.2.0")
	implementation("org.jxls:jxls-jexcel:1.0.9")
	implementation("org.dhatim:fastexcel-reader:0.15.3")
	implementation("org.dhatim:fastexcel:0.15.3")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

configure<DependencyManagementExtension> {
	imports {
		mavenBom("io.micrometer:micrometer-tracing-bom:1.1.3")
	}
}
kotlin {
	jvmToolchain(21)
}

rootProject.extra["spring-boot.build-image.imageName"] = "kz.miniland"