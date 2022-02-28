val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project
val gsonVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
}
//similar to specifying the manifest's location
application {
    mainClass.set("com.learning.ApplicationKt")
}

group = "com.learning"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    //importing Kotlin
    implementation(kotlin("stdlib"))

    //core ktor
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    //engine
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    //parse serialized format (e.g., JSON) into and from Kotlin objects
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    //logging - based upon Log4j
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    //unit testing
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    //provides utility functions for assertions
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    //GSON
    testImplementation("com.google.code.gson:gson:$gsonVersion")
}