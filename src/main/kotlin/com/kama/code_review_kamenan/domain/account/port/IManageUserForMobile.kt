package com.kama.code_review_kamenan.domain.account.port

import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/07/06
 * @Time: 12:41
 */
interface IManageUserForMobile {

    fun authenticateUserForMobile(username: String, password: String): OperationResult<User>

    fun logoutUserForMobile(sessionToken: String): OperationResult<Boolean>

    fun findUserBySessionToken(sessionToken: String): Optional<User>

}