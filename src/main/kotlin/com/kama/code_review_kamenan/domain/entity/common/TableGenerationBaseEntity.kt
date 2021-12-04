package com.kama.code_review_kamenan.domain.entity.common

import com.kama.code_review_kamenan.domain.entity.common.AuditableEntity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class TableGenerationBaseEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        var id: Long? = null
) : AuditableEntity<String>()
