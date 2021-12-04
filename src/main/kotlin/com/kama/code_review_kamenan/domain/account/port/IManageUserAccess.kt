package com.kama.code_review_kamenan.domain.account.port

import com.kama.code_review_kamenan.utils.OperationResult

interface IManageUserAccess : IManageUserPassword {

    fun lock(username: String): OperationResult<Boolean>

    fun unlock(username: String): OperationResult<Boolean>
}