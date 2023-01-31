plugins {
    application
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.splunk.example.WsAppMain")
    applicationDefaultJvmArgs = listOf(
        "-javaagent:splunk-otel-javaagent-1.20.0.jar",
        "-Dotel.javaagent.debug=true",
        "-Dotel.resource.attributes=deployment.environment=ws-stomp",
        "-Dotel.service.name=ws-stomp"
    )
}

dependencies {
    implementation("io.opentelemetry:opentelemetry-sdk:1.21.0")
    implementation("org.springframework.boot:spring-boot-starter-parent:2.7.8")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.8")
    implementation("org.springframework.boot:spring-boot-starter-websocket:2.7.8")
//    implementation("org.springframework:spring-websocket:5.3.25")
    implementation("org.springframework:spring-messaging:5.3.25")

    //implementation("org.testcontainers:testcontainers:1.17.6")
    //implementation("org.testcontainers:localstack:1.17.6")
}
