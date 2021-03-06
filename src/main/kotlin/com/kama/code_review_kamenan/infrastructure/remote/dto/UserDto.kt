package com.kama.code_review_kamenan.infrastructure.remote.dto


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/07/08
 * @Time: 17:34
 */
class UserDto(
    var id: Long = -1L,
    var firstname: String = "",
    var lastname: String = "",
    var username: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var sessionToken: String = ""
)

class CustomerDto(
    var id: Long = -1L,
    var firstname: String = "",
    var lastname: String = "",
    var username: String = "",
    var email: String = "",
    var phoneNumber: String = ""
)

class UserFromMobile(
    var firstname: String = "",
    var lastname: String = "",
    var email: String = "",
    var username: String = "",
    var phoneNumber: String = "",
    var password: String = ""
)