package org.kefirsf.palindrome

import java.io.OutputStream
import java.io.PrintStream
import java.util.*

/**
 * Palindrome generator
 * @author kefir
 */
class Generator {
    val direct: SortedSet<String>
    val reversed: SortedMap<String, String>
    val palindromes: MutableSet<List<String>> = HashSet()
    private var output: PrintStream = PrintStream(NullOutputStream())
    fun setOutput(stream: OutputStream) {
        if (stream is PrintStream) {
            output = stream
        } else {
            output = PrintStream(stream)
        }
    }

    constructor(words: Collection<String>) {
        direct = TreeSet(words)
        reversed = TreeMap(words.associateBy { it.reversed() })
    }

    fun run(max: Int) {
        val firstCandidates = direct.map { Candidate(it) }
        palindromes.addAll(firstCandidates.filter { it.palindrome }.map { it.result })
        var oldGen: Collection<Candidate> = firstCandidates.filterNot { it.palindrome }

        if(palindromes.isNotEmpty()) {
            output.println("Palindromes: $palindromes")
        }

        output.println("Candidates: $oldGen")

        for (i in 1..max) {
            output.println("Generation $i")

            val newGen = HashSet<Candidate>()

            for (c in oldGen) {
                val children = generateChildren(c)

                palindromes.addAll(children.filter { it.palindrome }.map { it.result })
                newGen.addAll(children.filterNot { it.palindrome || it.hasDuplication()})
            }

            if(palindromes.isNotEmpty()) {
                output.println("Palindromes: $palindromes")
            }

            if (newGen.isEmpty()) {
                break
            }

            oldGen = newGen

            output.println("Candidates: $oldGen")
        }
    }

    private fun generateChildren(c: Candidate): HashSet<Candidate> {
        if (c.leftSize > c.rightSize) {
            return generateRightChildren(c)
        } else if (c.leftSize < c.rightSize) {
            return generateLeftChildren(c)
        } else {
            throw ImpossibleWayException()
        }
    }

    private fun generateLeftChildren(c: Candidate): HashSet<Candidate> {
        val newCandidates = HashSet<Candidate>()
        for (sp in (1..(c.part.length - 1)).map { c.part.takeLast(it) }) {
            val word = sp.reversed()
            if (direct.contains(word)) {
                newCandidates.add(Candidate(c, word))
            }
        }

        val rp = c.part.reversed()
        for (word in direct.tailSet(rp)) {
            if (word.startsWith(rp)) {
                newCandidates.add(Candidate(c, word))
            } else {
                break
            }
        }
        return newCandidates
    }

    private fun generateRightChildren(c: Candidate): HashSet<Candidate> {
        val newCandidates = HashSet<Candidate>()
        for (sp in (1..(c.part.length - 1)).map { c.part.take(it) }) {
            val word = reversed[sp]
            if (word != null) {
                newCandidates.add(Candidate(c, word))
            }
        }

        for ((key, value) in reversed.tailMap(c.part)) {
            if (key.startsWith(c.part)) {
                newCandidates.add(Candidate(c, value))
            } else {
                break
            }
        }
        return newCandidates
    }

    val count: Int
        get() {
            return palindromes.size
        }
}

class NullOutputStream : OutputStream() {
    override fun write(b: Int) {
        // Nothing
    }
}

class ImpossibleWayException : Exception(
        "This way is impossible. If the exception was thrown then there is a bug in logic."
) {
}
