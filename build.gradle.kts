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
    annotationProcessor("com.google.auto.service:auto-service:1.0.1")
    compileOnly("com.google.auto.service:auto-service:1.0.1")

    compileOnly("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure:1.22.0-alpha")
    compileOnly("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure-spi:1.22.0")
    implementation("io.opentelemetry:opentelemetry-sdk:1.21.0")
    implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:1.22.1")

    implementation("org.springframework.boot:spring-boot-starter-parent:2.7.8")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.8")
    implementation("org.springframework.boot:spring-boot-starter-websocket:2.7.8")
    implementation("org.springframework:spring-messaging:5.3.25")
}
