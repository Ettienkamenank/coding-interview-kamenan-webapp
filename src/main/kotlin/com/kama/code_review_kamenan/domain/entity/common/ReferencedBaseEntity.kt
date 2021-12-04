package com.kama.code_review_kamenan.domain.entity.common

import com.kama.code_review_kamenan.domain.entity.common.BaseEntity
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class ReferencedBaseEntity : BaseEntity() {

    var reference: String = ""
}
