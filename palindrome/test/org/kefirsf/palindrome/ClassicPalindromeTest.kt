package org.kefirsf.palindrome

import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.*
import org.junit.Assert.*


/**
 * Test for classic palindromes
 * @author kefir
 */
@RunWith(Parameterized::class)
class ClassicPalindromeTest(val words:Collection<String>, val palindrome:List<String>) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() : Collection<Array<Collection<String>>> {
            return listOf(
                    arrayOf(setOf("redivider"), listOf("redivider")),
                    arrayOf(setOf("noon"), listOf("noon")),
                    arrayOf(setOf("civic"), listOf("civic")),
                    arrayOf(setOf("radar"), listOf("radar")),
                    arrayOf(setOf("rotor"), listOf("rotor")),

                    arrayOf(setOf("level"), listOf("level")),
                    arrayOf(setOf("kayak"), listOf("kayak")),
                    arrayOf(setOf("reviver"), listOf("reviver")),
                    arrayOf(setOf("racecar"), listOf("racecar")),
                    arrayOf(setOf("redder"), listOf("redder")),

                    arrayOf(setOf("madam"), listOf("madam")),
                    arrayOf(setOf("refer"), listOf("refer")),
                    arrayOf(setOf("ehcache"), listOf("ehcache")),

                    arrayOf(setOf("лезу", "санузел", "на"), listOf("лезу", "на", "санузел")),
                    arrayOf(setOf("веер", "для", "евреев", "веял"), listOf("веер", "веял", "для", "евреев")),
                    arrayOf(setOf("оголи", "пожилого", "жопу"), listOf("оголи", "жопу", "пожилого")),
                    arrayOf(setOf("боре", "обрезал", "хер", "лазер"), listOf("лазер", "боре", "хер", "обрезал"))
            )
        }
    }

    @Test
    fun testPalindrome(){
        val gen = Generator(words)
        gen.setOutput(System.out)
        gen.run(20)
        assertEquals(1, gen.count)
        assertEquals(palindrome, gen.palindromes.first())
    }
}