package org.kefirsf.palindrome

import java.io.File
import java.nio.charset.Charset

/**
 * @author kefir
 */
fun main(args:Array<String>){
    val gen = Generator(File("e:/tmp/words.txt").readLines(Charset.forName("cp1251")))
    //gen.setOutput(System.out)
    gen.run(2)
    gen.palindromes.forEach {
        System.out.println(it)
    }
}