package com.kama.code_review_kamenan.utils.helper

object StringHelper {

    fun clean(data: String) = data.replace("\'", "\\\'")
}