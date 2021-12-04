package com.kama.code_review_kamenan.domain.account.port

import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.utils.OperationResult
import java.util.*

interface IAuthenticateUser {

    fun findByUsername(username: String): Optional<User>

    fun findByEmail(email: String): Optional<User>

    fun findUserById(id: Long): Optional<User>

    fun authenticateUser(username: String, password: String): OperationResult<User>

    fun findTypeBy(id: Long): String

    fun isTakenUserByEmail(email: String):Boolean

    fun isTakenUserByUsername(username:String):Boolean

}