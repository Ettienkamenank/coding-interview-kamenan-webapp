package com.kama.code_review_kamenan.utils

class OperationResult<T>(val data: T?,
                         val errors: MutableMap<String, String>? = null) {

    val isSuccess: Boolean = errors == null || errors.isEmpty()
}