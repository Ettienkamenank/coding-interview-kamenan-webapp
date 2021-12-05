package com.kama.code_review_kamenan.application.api.user

import com.kama.code_review_kamenan.application.api.ApiConstant
import com.kama.code_review_kamenan.application.api.RestControllerEndpoint
import com.kama.code_review_kamenan.application.common.ResponseBody
import com.kama.code_review_kamenan.application.common.ResponseSummary
import com.kama.code_review_kamenan.application.resolver.SessionToken
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.infrastructure.remote.dto.UserDto
import org.springframework.context.MessageSource
import org.springframework.web.bind.annotation.*
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 05/12/2021
 * @At: 02:30
 */

@RestController
@RequestMapping(value = [RestControllerEndpoint.API_SECURED_USER_ENDPOINT])
class UserSecuredRestController(
    private val userDomain: UserDomain,
    private val messageSource: MessageSource,
) {

    @PostMapping(value = ["/logout"])
    fun logout(@SessionToken token: String?, locale: Locale): Map<String, Any> {

        val response: MutableMap<String, Any> = mutableMapOf()
        response[ApiConstant.ERROR] = true

        when (token) {
            null -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyToken", null, locale)
            }
            else -> {
                val result = userDomain.logoutUserForMobile(token)

                if (result.isSuccess) {
                    response[ApiConstant.ERROR] = false
                    response[ApiConstant.DATA] = result.data!!
                    response[ApiConstant.MESSAGE] = "Deconnexion reussi"
                } else {
                    response[ApiConstant.MESSAGE] =
                        messageSource.getMessage(result.errors!!.values.first(), null, locale)
                }
            }
        }
        return response
    }

    @GetMapping(value = ["/findUser"])
    fun getUserById(
        @RequestParam("userId", required = false) user_id: String?,
        locale: Locale
    ): Map<String, Any> {

        val response: MutableMap<String, Any> = mutableMapOf()
        response[ApiConstant.ERROR] = true

        when (user_id) {
            null -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("userNotFound", null, locale)
            }
            else -> {
                val result = userDomain.findUserById(user_id.toLong())

                if (result.isPresent) {
                    response[ApiConstant.ERROR] = false
                    response[ApiConstant.DATA] = result.get().toUserDto()
                    response[ApiConstant.MESSAGE] = "User trouvé"
                } else {
                    response[ApiConstant.MESSAGE] = "Aucun user trouvé"
                }
            }
        }
        return response
    }

}