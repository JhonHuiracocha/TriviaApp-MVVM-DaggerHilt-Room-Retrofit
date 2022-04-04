package com.multi.trivia.utils

import java.nio.charset.StandardCharsets

object Decoder {

    fun String.fromBase64(): String {
        return String(
            android.util.Base64.decode(this, android.util.Base64.DEFAULT),
            StandardCharsets.UTF_8
        )
    }

}