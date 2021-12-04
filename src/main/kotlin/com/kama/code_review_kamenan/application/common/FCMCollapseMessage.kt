package com.kama.code_review_kamenan.application.common

import com.fasterxml.jackson.annotation.JsonProperty

class FCMCollapseMessage(name: String, to: String, data: Map<String, String>,
                         @field:JsonProperty(value = "collapse_key") val collapseKey: String) : FCMMessage(name, to, data)