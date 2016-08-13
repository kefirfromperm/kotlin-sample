package org.kefirsf.palindrome

import java.util.*

/**
 * Palindrome candidate
 * @author kefir
 */
class Candidate {
    val left: List<String>
    val right: List<String>

    constructor(word: String) {
        left = listOf(word)
        right = emptyList()
    }

    constructor(candidate: Candidate, word: String) {
        if (candidate.leftSize <= candidate.rightSize) {
            left = candidate.left + word
            right = candidate.right
        } else {
            left = candidate.left
            right = listOf(word) + candidate.right
        }
    }

    val leftSize: Int
        get() {
            return left.fold(0, { a, s -> a + s.length })
        }

    val rightSize: Int
        get() {
            return right.fold(0, { a, s -> a + s.length })
        }

    val part: String
        get() {
            if (leftSize < rightSize) {
                return right.first().take(rightSize - leftSize)
            } else if (leftSize > rightSize) {
                return left.last().takeLast(leftSize - rightSize)
            } else {
                return ""
            }
        }

    val palindrome: Boolean
        get() {
            return part.isEmpty() || isItPalindrome(part)
        }

    private fun isItPalindrome(word: String): Boolean {
        return word.equals(word.reversed())
    }

    /**
     * Return result palindrome
     */
    val result: List<String>
        get() {
            return left + right
        }

    override fun toString(): String {
        return "Candidate(left=$left, right=$right, palindrome=$palindrome, part=$part)"
    }

    fun hasDuplication():Boolean{
        val set = HashSet<String>()
        for(word in left){
            if(set.contains(word)){
                return true
            }
        }
        for(word in right){
            if(set.contains(word)){
                return true
            }
        }
        return false
    }
}