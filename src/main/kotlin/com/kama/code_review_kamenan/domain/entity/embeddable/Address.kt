package com.kama.code_review_kamenan.domain.entity.embeddable

import javax.persistence.Embeddable

@Embeddable
class Address(
    var primaryAddress: String = "",
    var secondaryAddress: String = "",
    var postalCode: String = "",
    var city: String = "",
    var country: String = "",
    var area: String = "",
    var department: String = "",
    var district: String = "",
    var borough: String = ""
)