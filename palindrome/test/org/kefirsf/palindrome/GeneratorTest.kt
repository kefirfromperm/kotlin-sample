package org.kefirsf.palindrome

import org.junit.*;

/**
 * Tests for palindrome generator.
 * @author kefir
 */
class GeneratorTest {
    @Test fun simple(){
        val words : Collection<String> = listOf("манит", "аргентина", "негра")
        val gen = Generator(words)
        gen.run()
        Assert.assertEquals(1, gen.count)
        Assert.assertEquals(listOf("аргентина", "манит", "негра"), gen.palindromes[0])
    }
}