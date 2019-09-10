package gradle.exercise

import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleExcercisePlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.task("man") {
            doLast {
                println("TODO: print the manual")
            }
        }
    }
}

