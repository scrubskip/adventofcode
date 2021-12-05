plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    // Other dependencies.
    testImplementation(kotlin("test"))
    implementation(kotlin("test-junit"))
}

tasks.test {
    useJUnitPlatform()
}