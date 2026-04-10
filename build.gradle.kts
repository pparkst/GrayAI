plugins {
	java
	id("org.springframework.boot") version "3.5.13"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.ppakst"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springAiVersion"] = "1.1.4"

dependencies {
    // Spring Starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.ai:spring-ai-starter-model-google-genai")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    // 1. Lombok Configuration Processor
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    // Runtime & Test
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}



dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<JavaExec> {
    jvmArgs("-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8", "-Dsun.stderr.encoding=UTF-8")
}