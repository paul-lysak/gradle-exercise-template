import org.gradle.api.tasks.testing.logging.TestExceptionFormat


plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.50")
}

val kotlinTestVersion by extra("3.2.1")
val junit5Version by extra("5.4.0")

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

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
        testImplementation("io.kotlintest:kotlintest-runner-junit5:$kotlinTestVersion")
        testImplementation("org.junit.jupiter:junit-jupiter:$junit5Version")

        // Use the Kotlin JUnit integration.
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    }


    tasks.test {
        useJUnitPlatform()
        testLogging {
            outputs.upToDateWhen { false } //always run tests
            events("passed", "skipped", "failed")
            showStandardStreams = true
            exceptionFormat = TestExceptionFormat.FULL
        }
    }


}

project(":common") {
    configurations {
        create("test")
    }

    tasks.register<Jar>("testJar") {
        getArchiveClassifier().set("test")
        from(project.the<SourceSetContainer>()["test"].output)
    }

    artifacts {
        add("test", tasks["testJar"])
    }
}

project(":exercises") {
    dependencies {
        implementation(project(":common"))
        testImplementation(project(":common", configuration = "test"))
    }
}


apply<gradle.exercise.GradleExcercisePlugin>()


