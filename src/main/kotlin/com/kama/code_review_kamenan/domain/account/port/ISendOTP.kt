package com.kama.code_review_kamenan.domain.account.port

import com.kama.code_review_kamenan.utils.OperationResult

interface ISendOTP {

    fun sendOTP(receiver: String, otp : String): OperationResult<Boolean>
}