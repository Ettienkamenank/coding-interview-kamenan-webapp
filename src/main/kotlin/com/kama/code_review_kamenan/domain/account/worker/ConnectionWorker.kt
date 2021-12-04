package com.kama.code_review_kamenan.domain.account.worker

import com.kama.code_review_kamenan.domain.account.port.ConnectionDomain
import com.kama.code_review_kamenan.domain.entity.common.ConnectionEvent
import com.kama.code_review_kamenan.infrastructure.db.repository.ConnectionEventRepository
import com.kama.code_review_kamenan.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConnectionWorker : ConnectionDomain {

    @Autowired
    private lateinit var connectionEventRepository: ConnectionEventRepository

    override fun save(model: ConnectionEvent): OperationResult<ConnectionEvent> {
        return OperationResult(connectionEventRepository.save(model))
    }

    override fun isFirstConnection(id: Long): Boolean = connectionEventRepository.countAllByUser_Id(id) == 0L

}