package org.kefirsf.palindrome

import java.util.*

/**
 * Palindrome generator
 * @author kefir
 */
class Generator {
    val direct : SortedSet<String>
    val reversed: SortedMap<String, String>

    constructor(words: Collection<String>){
        direct = TreeSet(words)
        reversed = TreeMap(words.associateBy { it.reversed() })
    }

    fun run(){
    }
}