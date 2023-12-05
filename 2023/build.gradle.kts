plugins {
    kotlin("jvm") version "1.7.21"
}

repositories {
    mavenCentral()
}
dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    // Other dependencies.
    testImplementation(kotlin("test"))
    implementation(kotlin("test-junit"))
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.5.1"
    }
}
