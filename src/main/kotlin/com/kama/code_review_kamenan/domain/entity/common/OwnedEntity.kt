package com.kama.code_review_kamenan.domain.entity.common

import com.kama.code_review_kamenan.domain.entity.common.BaseEntity
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class OwnedEntity : BaseEntity() {

    var owner: Long = -1L
}