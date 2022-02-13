plugins {
    application
    kotlin("jvm") version "1.6.10"
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
    implementation("io.ktor:ktor-server-core:1.6.7")
    //engine
    implementation("io.ktor:ktor-server-netty:1.6.7")
    //logging - based upon Log4j
    implementation("ch.qos.logback:logback-classic:1.2.10")
}