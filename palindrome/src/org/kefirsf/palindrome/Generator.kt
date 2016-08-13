package org.kefirsf.palindrome

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.OutputStream
import java.io.PrintStream
import java.util.*

/**
 * Palindrome generator
 * @author kefir
 */
class Generator {
    val log: Logger = LoggerFactory.getLogger(Generator::class.java)
    val direct: SortedSet<String>
    val reversed: SortedMap<String, String>
    val palindromes: SortedSet<List<String>> = TreeSet<List<String>>(
            { o1, o2 ->
                var res = 0
                var i: Int = 0
                while (i < Math.min(o1.size, o2.size) && res == 0) {
                    res = o1[i].compareTo(o2[i])
                    i++
                }
                if(res==0) {
                    res = o1.size - o2.size
                }
                res
            }
    )

    constructor(words: Collection<String>) {
        direct = TreeSet(words)
        reversed = TreeMap(words.associateBy { it.reversed() })
    }

    fun run(max: Int) {
        val firstCandidates = direct.map { Candidate(it) }
        palindromes.addAll(firstCandidates.filter { it.palindrome }.map { it.result })
        var oldGen: Collection<Candidate> = firstCandidates.filterNot { it.palindrome }

        if (palindromes.isNotEmpty() && log.isDebugEnabled) {
            log.debug("One-word palindromes:\n${palindromes.joinToString("\n")}")
        }

        if (log.isDebugEnabled) {
            log.debug("First generation candidates:\n${oldGen.joinToString("\n")}")
        }

        for (i in 2..max) {
            log.info("Generation $i")

            val newGen = HashSet<Candidate>()

            for (c in oldGen) {
                val children = generateChildren(c)

                palindromes.addAll(children.filter { it.palindrome }.map { it.result })
                newGen.addAll(children.filterNot { it.palindrome || it.hasDuplication() })
            }

            log.info("Palindromes count - ${palindromes.size}")
            if (palindromes.isNotEmpty() && log.isDebugEnabled) {
                log.debug("Palindromes:\n${palindromes.joinToString("\n")}")
            }

            if (newGen.isEmpty()) {
                break
            }

            oldGen = newGen

            log.info("Candidates count - ${oldGen.size}")
            if (log.isDebugEnabled) {
                log.debug("Candidates:\n${oldGen.joinToString("\n")}")
            }
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
