package com.kama.code_review_kamenan.domain.entity.embeddable

import javax.persistence.Embeddable

@Embeddable
class Contact(
    var phoneNumber: String = "",
    var phoneNumber2: String = "",
    var email: String = ""
)