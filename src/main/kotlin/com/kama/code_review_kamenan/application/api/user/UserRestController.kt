package com.kama.code_review_kamenan.application.api.user

import com.kama.code_review_kamenan.application.api.ApiConstant
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
    ): Map<String, Any> {
        val response: MutableMap<String, Any> = mutableMapOf()
        response[ApiConstant.ERROR] = true

        when {
            userFromMobile.password.isEmpty() -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyPassword", null, locale)
            }
            userDomain.isTakenUserByEmail(userFromMobile.email) -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emailAlreadyExists", null, locale)
            }
            userDomain.isTakenUserByUsername(userFromMobile.username) -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("usernameAlreadyExists", null, locale)
            }
            else -> {
                roleDomain.findByName(UserType.CUSTOMER).ifPresent {
                    val customer = Customer()

                    customer.firstname = userFromMobile.firstname
                    customer.lastname = userFromMobile.lastname
                    customer.contact.email = userFromMobile.email
                    customer.username = userFromMobile.username
                    customer.contact.phoneNumber = userFromMobile.phoneNumber
                    customer.password = userFromMobile.password
                    customer.roles = Collections.singleton(it)

                    val result = userDomain.saveUser(customer)

                    if (result.isSuccess) {
                        response[ApiConstant.ERROR] = false
                        response[ApiConstant.DATA] = result.data!!.toUserDto()
                        response[ApiConstant.MESSAGE] = "Compte créé avec success"
                    } else {
                        response[ApiConstant.MESSAGE] =
                            messageSource.getMessage(result.errors!!.values.first(), null, locale)
                    }

                }
            }
        }

        return response
    }

    @PostMapping(value = ["/authenticate"])
    fun authenticateUser(
        @RequestParam("username") username: String,
        @RequestParam("password") password: String,
        locale: Locale
    ): Map<String, Any> {
        val response: MutableMap<String, Any> = mutableMapOf()
        response[ApiConstant.ERROR] = true

        when {
            username.isEmpty() -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyUsername", null, locale)
            }
            password.isEmpty() -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyPassword", null, locale)
            }
            else -> {
                val result = userDomain.authenticateUserForMobile(username, password)

                if (result.isSuccess) {
                    response[ApiConstant.ERROR] = false
                    response[ApiConstant.DATA] = result.data!!.toUserDto()
                    response[ApiConstant.MESSAGE] = "Connexion réussi"
                } else {
                    response[ApiConstant.MESSAGE] =
                        messageSource.getMessage(result.errors!!.values.first(), null, locale)
                }
            }
        }
        return response
    }

}