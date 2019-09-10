package gradle.exercise

import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleExcercisePlugin : Plugin<Project> {
    private val manFile = "common/src/test/resources/README.md"

    private val curricullumFolder = ".curriculum"

    private val exerciseFilePattern = Regex("^exercise_[0-9][0-9][0-9]_\\w+\$")

    private val currentExerciseFile = ".current_exercise"


    override fun apply(project: Project) {

        val exerciseNames: List<String> by lazy {
            val curriculumFiles = project.file(curricullumFolder).listFiles().toList()
            curriculumFiles.mapNotNull { file ->
                if (file.nameWithoutExtension.matches(exerciseFilePattern) && (file.isDirectory || file.extension.toLowerCase() == "zip"))
                    file.nameWithoutExtension
                else
                    null
            }.sorted()
        }

        val exerciseNamesIndexed by lazy { exerciseNames.withIndex() }

        fun currentExName(): String? {
            return project.file(currentExerciseFile).readText()
        }

        fun switchExercise(targetExercise: String) {
            println("TODO: swtich to $targetExercise")
            //TODO swap test dirs content

            project.file(currentExerciseFile).writeText(targetExercise)
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
                for ((i, name) in exerciseNames.withIndex()) {
                    println("${i + 1}. $name")
                }
            }
        }

        project.task("nextEx") {
            doLast {
                val allExcercises = exerciseNames
                val currentEx = currentExName()
                val currentIndex: Int = currentEx?.let { allExcercises.indexOf(it) } ?: -1
                val firstEx = allExcercises.first()
                if (currentEx == null || currentIndex < 0) {
                    println("Current exercise not known, navigating to the first ${firstEx}")
                    switchExercise(firstEx)
                } else if (currentIndex == allExcercises.size - 1) {
                    println("Already at the last exercise $currentEx, not switching")
                } else {
                    switchExercise(allExcercises.get(currentIndex + 1))
                }
            }
        }

        project.task("prevEx") {
            doLast {
                val allExcercises = exerciseNames
                val currentEx = currentExName()
                val currentIndex: Int = currentEx?.let { allExcercises.indexOf(it) } ?: -1
                val firstEx = allExcercises.first()
                if (currentEx == null || currentIndex < 0) {
                    println("Current exercise not known, navigating to the first ${firstEx}")
                    switchExercise(firstEx)
                } else if (currentIndex == 0) {
                    println("Already at the first exercise $currentEx, not switching")
                } else {
                    switchExercise(allExcercises.get(currentIndex - 1))
                }
            }
        }

        for ((i, name) in exerciseNamesIndexed) {
            project.task("gotoEx$i") {
                doLast {
                    switchExercise(name)
                }
            }
        }
    }
}

