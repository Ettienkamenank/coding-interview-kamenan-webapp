package com.kama.code_review_kamenan.domain.account.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.Embeddable


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/07/06
 * @Time: 12:22
 */
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
class Credential {

    @JsonIgnore
    var apiKey: String = ""

    var sessionToken: String = ""

}