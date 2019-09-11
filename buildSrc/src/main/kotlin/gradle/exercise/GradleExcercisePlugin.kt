package gradle.exercise

import com.github.ajalt.mordant.TermColors
import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleExcercisePlugin : Plugin<Project> {

    private val curricullumFolder = ".curriculum"

    private val exerciseFilePattern = Regex("^exercise_[0-9][0-9][0-9]_\\w+\$")

    private val currentExerciseFile = ".current_exercise"

    private val exercisesWorkDir = "exercises"
    private val replacementFolder = "src/test"

    private val manFile = "common/src/test/resources/README.md"
    private val manExFile = exercisesWorkDir.sep() + "src/test/resources/README.md"


    private fun String.sep() = if (this.isEmpty() || this.endsWith("/")) this else this + "/"

    private val tc = TermColors(TermColors.Level.ANSI16)


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
            println("Switching to $targetExercise")

            //TODO support .zip files
            val targetDir = project.file(exercisesWorkDir.sep() + replacementFolder)
            targetDir.deleteRecursively()
            project.file(curricullumFolder.sep() + targetExercise.sep() + replacementFolder).copyRecursively(targetDir, overwrite = false)

            project.file(currentExerciseFile).writeText(targetExercise)
        }

        project.task("man") {
            doLast {
                val manText = project.file(manFile).readText()
                println(MarkdownFormatter.markdownToAnsiText(manText))
            }
        }

        project.task("manEx") {
            doLast {
                val manText = project.file(manExFile).readText()
                println(MarkdownFormatter.markdownToAnsiText(manText))
            }
        }


        project.task("listEx") {
            doLast {
                val current = currentExName()
                println("All available exercises:")
                for ((i, name) in exerciseNames.withIndex()) {
                    val msg = "${i + 1}. $name"
                    if (current == name)
                        println(tc.bold(msg))
                    else
                        println(msg)
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
            project.task("gotoEx${i + 1}") {
                doLast {
                    switchExercise(name)
                }
            }
        }
    }
}

