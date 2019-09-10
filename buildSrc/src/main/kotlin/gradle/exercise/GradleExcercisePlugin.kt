package gradle.exercise

import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleExcercisePlugin : Plugin<Project> {
    private val manFile = "common/src/test/resources/README.md"

    private val curricullumFolder = ".curriculum"

    private val exerciseFilePattern = Regex("^exercise_[0-9][0-9][0-9]_\\w+\$")


    override fun apply(project: Project) {

        fun exerciseNames(): List<String> {
            val curriculumFiles = project.file(curricullumFolder).listFiles().toList()
            return curriculumFiles.mapNotNull { file ->
                if (file.nameWithoutExtension.matches(exerciseFilePattern) && (file.isDirectory || file.extension.toLowerCase() == "zip"))
                    file.nameWithoutExtension
                else
                    null
            }.sorted()
        }


        project.task("man") {
            doLast {
                val manText = project.file(manFile).readText()
                println(manText)
            }
        }

        project.task("listEx") {
            doLast {
                println("All available exercises:")
                for ((i, name) in exerciseNames().withIndex()) {
                    println("${i + 1}. $name")
                }
            }
        }
    }
}

