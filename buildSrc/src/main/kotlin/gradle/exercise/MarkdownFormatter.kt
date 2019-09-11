package gradle.exercise

import com.github.ajalt.mordant.TermColors


object MarkdownFormatter {
    private val tc = TermColors(TermColors.Level.ANSI16)

    private val codeRegex = Regex("`([^`]+)`")
    private val italicRegex = Regex("\\*([^*]+)\\*")
    private val boldRegex = Regex("\\*\\*([^*]+)\\*\\*")

    private val codeBlockStartRegex = Regex("^\\s*```(bash|scala|java|kotlin|groovy)?\\s*$")
    private val codeBlockEndRegex = Regex("^\\s*```\\s*$")

    private fun transformMarkdownLine(markdownLine: String): String {
        return markdownLine.let { str ->
            codeRegex.replace(str, { match -> tc.green(match.groups.get(1)!!.value) })
        }.let { str ->
            boldRegex.replace(str, { match -> tc.bold(match.groups.get(1)!!.value) })
        }.let { str ->
            italicRegex.replace(str, { match -> tc.italic(match.groups.get(1)!!.value) })
        }
    }

    fun markdownToAnsiText(markdownText: String): String {
        val builder = StringBuilder()
        var inCodeBlock = false
        for (line in markdownText.lines()) {

            if (line.isNotEmpty() && line.first() == '#')
                builder.append(tc.bold(line.drop(1)))
            else if (!inCodeBlock && codeBlockStartRegex.matches(line))
                inCodeBlock = true
            else if (inCodeBlock && codeBlockEndRegex.matches(line))
                inCodeBlock = false
            else if (inCodeBlock) {
                builder.append("  ")
                builder.append(tc.green(line))
            }
            else
                builder.append(transformMarkdownLine(line))

            builder.append("\n")
        }
        return builder.toString()
    }

}