package com.kama.code_review_kamenan.domain.entity.common

import com.kama.code_review_kamenan.domain.entity.common.AuditableEntity
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class StateEntity : AuditableEntity<String>() {

    var active: Boolean = false
}