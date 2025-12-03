plugins {
    kotlin("jvm") version "2.2.21"
    application
}

repositories {
    mavenCentral()
}
dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("org.testng:testng:6.9.6")
    // Other dependencies.
    testImplementation(kotlin("test"))
}

tasks {
    sourceSets {
        main {
            kotlin.srcDir("src")
        }
    }

    wrapper {
        gradleVersion = "9.2.1"
    }

}

tasks.test {
    useJUnitPlatform()
}