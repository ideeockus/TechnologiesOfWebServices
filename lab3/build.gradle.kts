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
    implementation("javax.xml.ws:jaxws-api:2.3.1")
    implementation("com.sun.xml.ws:jaxws-ri:2.3.2") // JAX-WS RI
    implementation("javax.jws:javax.jws-api:1.1")   // Для javax.jws
    implementation("org.postgresql:postgresql:42.2.18") // Зависимость для PostgreSQL
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}