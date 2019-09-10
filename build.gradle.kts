import org.gradle.api.tasks.testing.logging.TestExceptionFormat


plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
//    id("org.jetbrains.kotlin.jvm").version("1.3.20")
    id("org.jetbrains.kotlin.jvm").version("1.3.50")

    // Apply the application plugin to add support for building a CLI application.
//    application
}

allprojects {
    //                id("org.jetbrains.kotlin.jvm").version("1.3.20")
    apply(plugin = "org.jetbrains.kotlin.jvm")
//    version = "1.3.50"
//    kotlin("jvm") version "1.3.50"

    repositories {
        // Use jcenter for resolving your dependencies.
        // You can declare any Maven/Ivy/file repository here.
        jcenter()

        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }

    dependencies {
        // Use the Kotlin JDK 8 standard library.
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // Use the Kotlin test library.
        testImplementation("org.jetbrains.kotlin:kotlin-test")

        // Use the Kotlin JUnit integration.
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    }


    tasks.test {
//        useJUnitPlatform()
        testLogging {
            outputs.upToDateWhen { false } //always run tests
            events("passed", "skipped", "failed")
            showStandardStreams = true
            exceptionFormat = TestExceptionFormat.FULL
        }
    }


}


apply<gradle.exercise.GradleExcercisePlugin>()

//dependencies {
//    // Use the Kotlin JDK 8 standard library.
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//
//    // Use the Kotlin test library.
//    testImplementation("org.jetbrains.kotlin:kotlin-test")
//
//    // Use the Kotlin JUnit integration.
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
//}

//application {
//    // Define the main class for the application.
//    mainClassName = "gradle.exercise.AppKt"
//}



