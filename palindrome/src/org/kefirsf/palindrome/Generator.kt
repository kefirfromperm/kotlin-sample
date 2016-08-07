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
                if (c.leftSize > c.rightSize) {
                    for (sp in (1..(c.part.length - 1)).map { c.part.take(it) }) {
                        val word = reversed[sp]
                        if (word != null) {
                            newGen.add(Candidate(c, word))
                        }
                    }

                    for ((key, value) in reversed.tailMap(c.part)) {
                        if (key.startsWith(c.part)) {
                            newGen.add(Candidate(c, value))
                        } else {
                            break
                        }
                    }
                } else if (c.leftSize < c.rightSize) {

                }
            }

            // TODO remove
            System.out.println(newGen)

            palindromes.addAll(newGen.filter { it.palindrome }.map { it.result })
            oldGen = newGen.filterNot { it.palindrome }

            if(oldGen.isEmpty()){
                break
            }
        }
    }

    val count:Int
    get(){
        return palindromes.size
    }
}