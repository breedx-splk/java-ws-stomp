plugins {
    application
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.splunk.example.MeasureExternalsMain")
    applicationDefaultJvmArgs = listOf(
        "-javaagent:splunk-otel-javaagent-1.20.0-SNAPSHOT.jar",
        "-Dotel.javaagent.debug=true",
        "-Dotel.resource.attributes=deployment.environment=ws-stomp",
        "-Dotel.service.name=ws-stomp"
    )
}

dependencies {
    implementation("io.opentelemetry:opentelemetry-sdk:1.21.0")

    //implementation("org.testcontainers:testcontainers:1.17.6")
    //implementation("org.testcontainers:localstack:1.17.6")
}
