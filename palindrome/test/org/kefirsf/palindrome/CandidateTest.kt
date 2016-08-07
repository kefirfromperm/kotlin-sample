package org.kefirsf.palindrome

import org.junit.*
import org.junit.Assert.*

/**
 * Tests for the Candidate class
 * @author kefir
 */
class CandidateTest {
    @Test fun simple(){
        val word = "test"
        val c = Candidate(word)

        assertEquals(listOf(word), c.left)
        assertEquals(emptyList<String>(), c.right)

        assertEquals(word.length, c.leftSize)
        assertEquals(0, c.rightSize)

        assertEquals(word, c.part)
        assertFalse(c.palindrome)
    }

    @Test fun size(){
        val c = Candidate(Candidate(Candidate("12"), "1"), "32")
        assertTrue(c.palindrome)
        assertEquals(2, c.leftSize)
        assertEquals(3, c.rightSize)
    }
}