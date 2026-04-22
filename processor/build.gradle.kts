plugins {
    kotlin("jvm")
    kotlin("kapt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("com.google.auto.service:auto-service:1.1.1")
    kapt("com.google.auto.service:auto-service:1.1.1")

    implementation("com.squareup:kotlinpoet:1.14.2")

    implementation(project(":annotations"))
}

kapt {
    correctErrorTypes = true
}

kotlin {
    jvmToolchain(17)
}
