plugins {
    kotlin("jvm")
    kotlin("kapt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":annotations"))
    kapt(project(":processor"))
}

kotlin {
    jvmToolchain(17)
}
