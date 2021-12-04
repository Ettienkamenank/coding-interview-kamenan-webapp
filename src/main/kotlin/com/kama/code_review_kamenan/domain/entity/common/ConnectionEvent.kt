package com.kama.code_review_kamenan.domain.entity.common

import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.entity.embeddable.Device
import javax.persistence.*

@Entity
data class ConnectionEvent(
        @field:Column(nullable = false, updatable = false) val username: String,
        @field:Column(name = "user_agent", nullable = false, updatable = false) val userAgent: String,
        @field:ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(name = "owner", referencedColumnName = "id")
        val user: User,
        @field:Embedded
        val device: Device
) : BaseEntity()
