import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.2.40"

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
}

plugins {
    java
}

group = "de.forside"
version = "0.1"

apply {
    plugin("kotlin")
}

val kotlinVersion: String by extra

repositories {
//    mavenCentral()
	jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    implementation("no.tornado:tornadofx:1.7.15")
    implementation("com.1stleg:jnativehook:2.1.0")

    testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}