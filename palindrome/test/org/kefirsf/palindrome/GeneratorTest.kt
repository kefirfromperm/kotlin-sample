package org.kefirsf.palindrome

import org.junit.*
import org.junit.Assert.*

/**
 * Tests for palindrome generator.
 * @author kefir
 */
class GeneratorTest {
    @Test fun simple(){
        val words : Collection<String> = listOf("манит", "аргентина", "негра")
        val gen = Generator(words)
        gen.run()
        assertEquals(1, gen.count)
        assertEquals(listOf("аргентина", "манит", "негра"), gen.palindromes.first())
    }
}