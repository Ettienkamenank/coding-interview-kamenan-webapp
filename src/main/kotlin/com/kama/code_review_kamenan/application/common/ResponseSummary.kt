package com.kama.code_review_kamenan.application.common

import java.util.*

class ResponseSummary(val errors: Map<String, String>? = null) {

    val isSuccess: Boolean = errors == null || errors.isEmpty()

    constructor(error: String) : this(object : HashMap<String, String>() {
        init {
            put("message", error)
        }
    })
}