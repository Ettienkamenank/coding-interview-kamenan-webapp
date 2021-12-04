package com.kama.code_review_kamenan.domain.account.port

import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.utils.OperationResult

interface IRegisterUser {

    fun saveUser(model: User): OperationResult<User>
}