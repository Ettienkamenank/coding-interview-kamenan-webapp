package com.kama.code_review_kamenan.application.api.activity_area

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
    ): ResponseEntity<ResponseBody<List<ActivityAreaDto>>> {

        var responseStatus: HttpStatus = HttpStatus.OK
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: List<ActivityAreaDto>? = listOf()

        if (token == null) {
            errors["session"] = messageSource.getMessage("emptyToken", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (errors.isEmpty()) {
            userDomain.findUserBySessionToken(token!!).ifPresent { user ->
                data = activityAreaDomain.findAll().map { it.toActivityAreaDto() }
            }
        }

        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), responseStatus)
    }

}