package org.kefirsf.palindrome

import org.junit.*
import org.junit.Assert.*

/**
 * Tests for palindrome generator.
 * @author kefir
 */
class GeneratorTest {
    @Test
    fun simple(){
        val words : Collection<String> = listOf("манит", "аргентина", "негра")
        val gen = Generator(words)
        gen.run()
        assertEquals(1, gen.count)
        assertEquals(listOf("аргентина", "манит", "негра"), gen.palindromes.first())
    }

    @Test
    fun bigLeft(){
        val gen = Generator(listOf("21", "43", "65", "1234567"))
        gen.run()
        assertEquals(1, gen.count)
        assertEquals(listOf("1234567", "65", "43", "21"), gen.palindromes.first())
    }

    @Test
    fun bigRight(){
        val gen = Generator(listOf("12", "34", "56", "7654321"))
        gen.run()
        assertEquals(1, gen.count)
        assertEquals(listOf("12", "34", "56", "7654321"), gen.palindromes.first())
    }

    @Test
    fun mirror(){
        val gen = Generator(listOf("123", "321"))
        gen.run()
        assertEquals(2, gen.count)
        assertTrue(gen.palindromes.contains(listOf("123", "321")))
        assertTrue(gen.palindromes.contains(listOf("321", "123")))
    }

    @Test
    fun chain(){
        val gen = Generator(listOf("123", "0987", "654321", "456789", "82", "010987"))
        gen.run()
        assertEquals(2, gen.count)
        assertTrue(gen.palindromes.contains(listOf("123", "456789", "0987", "654321")))
        assertTrue(gen.palindromes.contains(listOf("123", "456789", "010987", "654321")))
    }
}