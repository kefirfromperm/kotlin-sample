package org.kefirsf.palindrome

import java.util.*

/**
 * Palindrome generator
 * @author kefir
 */
class Generator {
    val direct: SortedSet<String>
    val reversed: SortedMap<String, String>
    val palindromes: MutableSet<List<String>> = HashSet()

    constructor(words: Collection<String>) {
        direct = TreeSet(words)
        reversed = TreeMap(words.associateBy { it.reversed() })
    }

    fun run() {
        val firstCandidates = direct.map { Candidate(it) }
        palindromes.addAll(firstCandidates.filter { it.palindrome }.map { it.result })
        var oldGen: Collection<Candidate> = firstCandidates.filterNot { it.palindrome }

        for (i in 1..20) {
            val newGen = HashSet<Candidate>()

            for (c in oldGen) {
                val children = generateChildren(c)

                palindromes.addAll(children.filter { it.palindrome }.map { it.result })
                newGen.addAll(children.filterNot { it.palindrome })
            }

            if (newGen.isEmpty()) {
                break
            }

            oldGen = newGen
        }
    }

    private fun generateChildren(c: Candidate): HashSet<Candidate> {
        if (c.leftSize > c.rightSize) {
            return generateRightChildren(c)
        } else if (c.leftSize < c.rightSize) {
            return generateLeftChildren(c)
        } else {
            throw ImpossibleWayException();
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
                break;
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

class ImpossibleWayException : Exception(
        "This way is impossible. If the exception was thrown then there is a bug in logic."
) {
}
