package org.kefirsf.palindrome

import java.io.File
import java.io.PrintWriter
import java.nio.charset.Charset

/**
 * Run application
 * @author kefir
 */
fun main(args: Array<String>) {
    val gen = Generator(File("e:/tmp/words.txt").readLines(Charset.forName("cp1251")))
    gen.run(4)

    PrintWriter("e:/tmp/palindromes", "utf8").use { writer ->
        gen.palindromes.forEach {
            writer.println(it.joinToString(" "))
        }
    }
}