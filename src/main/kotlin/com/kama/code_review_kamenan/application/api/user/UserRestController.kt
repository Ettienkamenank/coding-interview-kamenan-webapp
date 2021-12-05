package com.kama.code_review_kamenan.application.api.user

import com.kama.code_review_kamenan.application.api.RestControllerEndpoint
import com.kama.code_review_kamenan.application.common.ResponseBody
import com.kama.code_review_kamenan.application.common.ResponseSummary
import com.kama.code_review_kamenan.domain.account.entity.Customer
import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.account.entity.UserType
import com.kama.code_review_kamenan.domain.account.port.RoleDomain
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.infrastructure.remote.dto.UserDto
import com.kama.code_review_kamenan.infrastructure.remote.dto.UserFromMobile
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 05/12/2021
 * @At: 02:29
 */
@RestController
@RequestMapping(value = [RestControllerEndpoint.API_USER_ENDPOINT])
class UserRestController(
    private val userDomain: UserDomain,
    private val roleDomain: RoleDomain,
    private val messageSource: MessageSource
) {

    @PostMapping(value = ["/create"])
    fun createCustomer(
        @RequestBody(required = true) userFromMobile: UserFromMobile,
        locale: Locale
    ): ResponseEntity<ResponseBody<Boolean>> {
        var errors: MutableMap<String, String> = mutableMapOf()
        var data: User? = null

        if (userFromMobile.password.isEmpty()) {
            errors["emptyPassword"] = messageSource.getMessage("emptyPassword", null, locale)
        }

        if (userDomain.isTakenUserByEmail(userFromMobile.email)) {
            errors["emailAlreadyExists"] = messageSource.getMessage("emailAlreadyExists", null, locale)
        }

        if (userDomain.isTakenUserByUsername(userFromMobile.username)) {
            errors["usernameAlreadyExists"] = messageSource.getMessage("usernameAlreadyExists", null, locale)
        }

        if (errors.isEmpty()) {
            roleDomain.findByName(UserType.CUSTOMER).ifPresent {
                val customer = Customer()

                customer.firstname = userFromMobile.firstname
                customer.lastname = userFromMobile.lastname
                customer.contact.email = userFromMobile.email
                customer.username = userFromMobile.username
                customer.contact.phoneNumber = userFromMobile.phoneNumber
                customer.password = userFromMobile.password

                val result = userDomain.saveUser(customer)

                errors = result.errors!!
                data = result.data
            }
        }

        return ResponseEntity(ResponseBody(data != null, ResponseSummary(errors)), HttpStatus.OK)
    }


    @PostMapping(value = ["/authenticate"])
    fun authenticateUser(
        @RequestParam("username") username: String,
        @RequestParam("password") password: String,
        locale: Locale
    ): ResponseEntity<ResponseBody<UserDto>> {

        var errors: MutableMap<String, String> = mutableMapOf()
        var data = UserDto()

        if (username.isEmpty()) {
            errors["username"] = messageSource.getMessage("emptyUsername", null, locale)
        }

        if (password.isEmpty()) {
            errors["password"] = messageSource.getMessage("emptyPassword", null, locale)
        }

        if (errors.isEmpty()) {
            val result = userDomain.authenticateUserForMobile(username, password)
            errors = result.errors!!

            if (result.data != null) {
                data = result.data.toUserDto()
            }
        }

        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), HttpStatus.OK)
    }

}