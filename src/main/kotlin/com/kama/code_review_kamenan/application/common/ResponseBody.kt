package com.kama.code_review_kamenan.application.common

class ResponseBody<T>(val data: T? = null, val summary: ResponseSummary?) {

    constructor(summary: ResponseSummary?) : this(null, summary)
}