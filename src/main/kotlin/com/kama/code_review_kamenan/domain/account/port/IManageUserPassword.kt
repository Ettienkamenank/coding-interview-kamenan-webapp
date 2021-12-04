package com.kama.code_review_kamenan.domain.account.port

import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.utils.OperationResult

interface IManageUserPassword {

    fun forgotPassword(user: User): OperationResult<Boolean>

    fun updatePassword(user: User, newPassword: String): OperationResult<Boolean>
}