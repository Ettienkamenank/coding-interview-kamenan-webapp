package com.kama.code_review_kamenan.domain.account.port

import com.kama.code_review_kamenan.domain.entity.common.ConnectionEvent
import com.kama.code_review_kamenan.utils.OperationResult

interface IManageConnectionEvent {

    fun save(model: ConnectionEvent): OperationResult<ConnectionEvent>

    fun isFirstConnection(id: Long): Boolean
}