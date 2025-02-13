val logbackVersion: String by project
val exposedVersion: String by project
val mysqlVersion: String by project
val hikariCpVersion: String by project


plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // WebFlux (코루틴 지원 포함)
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // HikariCP (데이터소스)
    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    // MySQL Connector
    implementation("mysql:mysql-connector-java:$mysqlVersion")

    // Exposed DSL 관련 라이브러리
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    // 코루틴 관련
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    // 검증 (Optional)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
