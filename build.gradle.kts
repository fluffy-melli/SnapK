plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "8.3.0"
}

group = "dev.melli"
version = "0.0.2-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.spring.io/snapshot")
    }
    gradlePluginPortal()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.github.kwhat:jnativehook:2.2.2")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain(17)
}

tasks {
    shadowJar {
        mergeServiceFiles()
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")

        manifest {
            attributes["Main-Class"] = "dev.melli.MainKt"
        }

        from("src/main/resources") {
            include("**/*.properties")
        }
    }

    build {
        dependsOn(shadowJar)
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}