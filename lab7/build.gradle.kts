plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.apache.juddi:juddi-client:3.3.10")
    implementation("org.apache.cxf:cxf-rt-frontend-jaxws:3.4.3")
    implementation("org.apache.cxf:cxf-rt-transports-http:3.4.3")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}