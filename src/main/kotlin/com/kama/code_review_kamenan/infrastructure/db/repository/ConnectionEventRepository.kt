package com.kama.code_review_kamenan.infrastructure.db.repository

import com.kama.code_review_kamenan.domain.entity.common.ConnectionEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConnectionEventRepository : JpaRepository<ConnectionEvent, Long> {

    fun countAllByUser_Id(id: Long): Long
}
