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


dependencies {
    // WebFlux (코루틴 지원 포함)
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // 코루틴 관련
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // spring-security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // WebFlux Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.4")


    // HikariCP (데이터소스)
    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    // MySQL Connector
    implementation("mysql:mysql-connector-java:$mysqlVersion")

    // Exposed DSL 관련 라이브러리
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    // 검증 (Optional)
    implementation("org.springframework.boot:spring-boot-starter-validation")
    
    //트랜젝션
    implementation("org.springframework:spring-tx")

    // JWT 기반 Resource Server (JWT 검증 등, 필요에 따라 사용)
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // JWT 생성/검증을 위한 JJWT 라이브러리 (버전은 최신 버전으로 조정)
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5") // Jackson 을 통한 JSON 파싱


    // AOP
    implementation("org.springframework.boot:spring-boot-starter-aop")

    testImplementation("org.springframework.boot:spring-boot-starter-test")



}

repositories {
    mavenCentral()
}


kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
