import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm") version "1.3.61"
    idea
    java
    application
    jacoco
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testImplementation(platform("org.junit:junit-bom:5.5.2"))
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine")

    testImplementation("com.github.kittinunf.fuel:fuel:2.2.1")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.25.1")
}

application {
    mainClassName = "AppKt"
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines = setOf("junit-jupiter")
    }
    testLogging {
        events = mutableSetOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }

    finalizedBy("jacocoTestReport")
}
