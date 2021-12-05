package com.kama.code_review_kamenan.application.api.activity_area

import com.kama.code_review_kamenan.application.api.ApiConstant
import com.kama.code_review_kamenan.application.api.RestControllerEndpoint
import com.kama.code_review_kamenan.application.common.ResponseBody
import com.kama.code_review_kamenan.application.common.ResponseSummary
import com.kama.code_review_kamenan.application.resolver.SessionToken
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import com.kama.code_review_kamenan.domain.activity_area.port.ActivityAreaDomain
import com.kama.code_review_kamenan.infrastructure.remote.dto.ActivityAreaDto
import com.kama.code_review_kamenan.infrastructure.remote.dto.AnnouncementDto
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 05/12/2021
 * @At: 03:10
 */

@RestController
@RequestMapping(value = [RestControllerEndpoint.API_SECURED_ACTIVITY_AREA_ENDPOINT])
class ActivityAreaRestController(
    private val activityAreaDomain: ActivityAreaDomain,
    private val userDomain: UserDomain,
    private val messageSource: MessageSource,
) {

    @GetMapping(value = ["/all-activity-areas"])
    fun getAllActivityAreas(
        @SessionToken token: String?,
        locale: Locale
    ): Map<String, Any> {

        val response: MutableMap<String, Any> = mutableMapOf()
        response[ApiConstant.ERROR] = true

        when (token) {
            null -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyToken", null, locale)
            }
            else -> {
                val user = userDomain.findUserBySessionToken(token)

                if (user.isPresent) {
                    val data = activityAreaDomain.findAll().map { it.toActivityAreaDto() }

                    response[ApiConstant.ERROR] = false
                    response[ApiConstant.DATA] = data
                    response[ApiConstant.MESSAGE] = "Liste"
                } else {
                    response[ApiConstant.MESSAGE] = "Session expiré"
                }
            }
        }

        return response
    }

}