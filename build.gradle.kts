val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

project.setProperty("mainClassName", "com.pandey.shubham.ApplicationKt")

plugins {
    application
    kotlin("jvm") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("com.google.cloud.tools.appengine") version "2.4.2"
}

group = "com.pandey.shubham"
version = "1.0-SNAPSHOT"
application {
    mainClass.set("com.pandey.shubham.ApplicationKt")
}

repositories {
    mavenCentral()
    jcenter()
}

appengine {
    stage {
        setArtifact("build/libs/${project.name}-${project.version}-all.jar")
    }
    deploy {
        version = "GCLOUD_CONFIG"
        projectId = "GCLOUD_CONFIG"
    }
}


dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
}