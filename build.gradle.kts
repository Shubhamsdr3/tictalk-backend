val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version("1.4.10")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("com.google.cloud.tools.appengine") version "2.4.2"
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")
}

repositories {
    mavenCentral()
    jcenter()
}


//appengine {
//    stage {
//        artifact = "build/libs/${project.name}-${project.version}-all.jar"
//    }
//    deploy {
//        version = "GCLOUD_CONFIG"
//        projectId = "GCLOUD_CONFIG"
//    }
//}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    implementation( "io.ktor:ktor-serialization:$ktor_version")

    implementation( "org.jetbrains.exposed:exposed:0.17.6")
    implementation("com.zaxxer:HikariCP:3.4.2")
    implementation("mysql", "mysql-connector-java",  "8.0.11")
    implementation("org.ktorm", "ktorm-core","3.2.0")
    implementation("org.ktorm", "ktorm-support-mysql","3.2.0")
    implementation( "org.slf4j", "slf4j-simple", "1.7.25")
    implementation("com.google.code.gson", "gson", "2.8.1")
    implementation(kotlin("stdlib"))

    //auth
    implementation("io.ktor", "ktor-auth", ktor_version)
    implementation("io.ktor", "ktor-auth-jwt", ktor_version)
    implementation ("com.auth0:java-jwt:3.18.1")
}