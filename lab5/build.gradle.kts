plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21" // должно соответствовать версии Kotlin
}

group = "org.lab5"
version = "1.0-SNAPSHOT"
val ktorVersion = "2.0.1"


repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion") // Основной модуль Ktor
    implementation("io.ktor:ktor-server-netty:$ktorVersion") // Backend для Ktor (Netty)
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion") // Для работы с JSON
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1") // проверьте последнюю версию
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.3") // Логгирование
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // Стандартная библиотека Kotlin
    implementation("org.postgresql:postgresql:42.2.18") // Драйвер PostgreSQL

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}