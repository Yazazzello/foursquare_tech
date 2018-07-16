package com.yazazzello.adyen.features.util

import java.io.BufferedReader
import java.io.InputStreamReader

object TestTools {

    fun getStringFromFile(name: String): String {
        val stream = TestTools::class.java.classLoader.getResourceAsStream(name)
        val sb = StringBuilder()
        with(BufferedReader(InputStreamReader(stream))) {
            lineSequence().forEach {
                sb.append(it).append("\n")
            }
            close()
        }
        return sb.toString()
    }
}
