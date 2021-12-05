package com.kama.code_review_kamenan.application.api.announcement

import com.kama.code_review_kamenan.application.api.RestControllerEndpoint
import com.kama.code_review_kamenan.application.common.ResponseBody
import com.kama.code_review_kamenan.application.common.ResponseSummary
import com.kama.code_review_kamenan.application.resolver.SessionToken
import com.kama.code_review_kamenan.domain.account.entity.Customer
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.domain.activity_area.port.ActivityAreaDomain
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.announcement.port.AnnouncementDomain
import com.kama.code_review_kamenan.domain.announcement_action.entity.AnnouncementAction
import com.kama.code_review_kamenan.domain.announcement_action.entity.AnnouncementActionType
import com.kama.code_review_kamenan.domain.announcement_action.port.AnnouncementActionDomain
import com.kama.code_review_kamenan.domain.announcement_comment.entity.AnnouncementComment
import com.kama.code_review_kamenan.domain.announcement_comment.port.AnnouncementCommentDomain
import com.kama.code_review_kamenan.infrastructure.remote.dto.AnnouncementActionFromMobile
import com.kama.code_review_kamenan.infrastructure.remote.dto.AnnouncementCommentFromMobile
import com.kama.code_review_kamenan.infrastructure.remote.dto.AnnouncementDto
import com.kama.code_review_kamenan.infrastructure.remote.dto.AnnouncementFromMobile
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 05/12/2021
 * @At: 03:04
 */

@RestController
@RequestMapping(value = [RestControllerEndpoint.API_SECURED_ANNOUNCEMENT_ENDPOINT])
class AnnouncementRestController(
    private val announcementDomain: AnnouncementDomain,
    private val userDomain: UserDomain,
    private val activityAreaDomain: ActivityAreaDomain,
    private val announcementActionDomain: AnnouncementActionDomain,
    private val announcementCommentDomain: AnnouncementCommentDomain,
    private val messageSource: MessageSource,
) {

    @GetMapping(value = ["/all-announcements"])
    fun getAllAnnouncements(
        @SessionToken token: String?,
        locale: Locale
    ): ResponseEntity<ResponseBody<List<AnnouncementDto>>> {

        var responseStatus: HttpStatus = HttpStatus.OK
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: List<AnnouncementDto>? = listOf()

        if (token == null) {
            errors["session"] = messageSource.getMessage("emptyToken", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (errors.isEmpty()) {
            userDomain.findUserBySessionToken(token!!).ifPresent { user ->
                data = announcementDomain.findAll().map { it.toAnnouncementDto() }
            }
        }

        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), responseStatus)
    }

    @PostMapping(value = ["/create"])
    fun createAnnouncement(
        @SessionToken token: String?,
        @RequestBody(required = true) announcementToAdd: AnnouncementFromMobile,
        locale: Locale
    ): ResponseEntity<ResponseBody<Boolean>> {
        var responseStatus: HttpStatus = HttpStatus.OK
        var errors: MutableMap<String, String> = mutableMapOf()
        var data: Boolean? = false

        if (token == null) {
            errors["session"] = messageSource.getMessage("emptyToken", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (announcementToAdd.title.isEmpty()) {
            errors["emptyTitle"] = messageSource.getMessage("emptyTitle", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (announcementToAdd.description.isEmpty()) {
            errors["emptyDescription"] = messageSource.getMessage("emptyDescription", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (errors.isEmpty()) {
            userDomain.findUserBySessionToken(token!!).ifPresent { user ->
                activityAreaDomain.findById(announcementToAdd.activityAreaId.toLong()).ifPresent { activityArea ->

                    val announcement = Announcement()
                    announcement.title = announcementToAdd.title
                    announcement.description = announcementToAdd.description
                    announcement.customer = user as Customer
                    announcement.activityArea = activityArea

                    val result = announcementDomain.save(announcement)

                    if (result.isSuccess) {
                        errors = result.errors!!
                        data = true
                    } else {
                        data = false
                        result.errors!!.forEach { (key, value) ->
                            errors[key] = messageSource.getMessage(value, null, locale)
                        }
                    }

                }

            }
        }

        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), responseStatus)
    }

    @PostMapping(value = ["/report"])
    fun reportAnnouncement(
        @SessionToken token: String?,
        @RequestBody(required = true) announcementActionToAdd: AnnouncementActionFromMobile,
        locale: Locale
    ): ResponseEntity<ResponseBody<Boolean>> {
        var responseStatus: HttpStatus = HttpStatus.OK
        var errors: MutableMap<String, String> = mutableMapOf()
        var data: Boolean? = false

        if (token == null) {
            errors["session"] = messageSource.getMessage("emptyToken", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (announcementActionToAdd.comment.isEmpty()) {
            errors["emptyComment"] = messageSource.getMessage("emptyComment", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (announcementActionToAdd.announcementId.isEmpty()) {
            errors["announcementNotFound"] = messageSource.getMessage("announcementNotFound", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (errors.isEmpty()) {
            userDomain.findUserBySessionToken(token!!).ifPresent { user ->
                announcementDomain.findById(announcementActionToAdd.announcementId.toLong())
                    .ifPresent { announcement ->

                        val announcementAction = AnnouncementAction()
                        announcementAction.comment = announcementActionToAdd.comment
                        announcementAction.type = AnnouncementActionType.REPORT
                        announcementAction.user = user
                        announcementAction.announcement = announcement

                        val result = announcementActionDomain.save(announcementAction)

                        if (result.isSuccess) {
                            errors = result.errors!!
                            data = true
                        } else {
                            data = false
                            result.errors!!.forEach { (key, value) ->
                                errors[key] = messageSource.getMessage(value, null, locale)
                            }
                        }
                    }
            }
        }

        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), responseStatus)
    }

    @PostMapping(value = ["/comment"])
    fun commentOnAnnouncement(
        @SessionToken token: String?,
        @RequestBody(required = true) announcementCommentToAdd: AnnouncementCommentFromMobile,
        locale: Locale
    ): ResponseEntity<ResponseBody<Boolean>> {
        var responseStatus: HttpStatus = HttpStatus.OK
        var errors: MutableMap<String, String> = mutableMapOf()
        var data: Boolean? = false

        if (token == null) {
            errors["session"] = messageSource.getMessage("emptyToken", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (announcementCommentToAdd.comment.isEmpty()) {
            errors["emptyComment"] = messageSource.getMessage("emptyComment", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (announcementCommentToAdd.announcementId.isEmpty()) {
            errors["announcementNotFound"] = messageSource.getMessage("announcementNotFound", null, locale)
            responseStatus = HttpStatus.FORBIDDEN
        }

        if (errors.isEmpty()) {
            userDomain.findUserBySessionToken(token!!).ifPresent { user ->
                announcementDomain.findById(announcementCommentToAdd.announcementId.toLong())
                    .ifPresent { announcement ->

                        val announcementComment = AnnouncementComment()
                        announcementComment.comment = announcementCommentToAdd.comment
                        announcementComment.user = user
                        announcementComment.announcement = announcement

                        val result = announcementCommentDomain.save(announcementComment)

                        if (result.isSuccess) {
                            errors = result.errors!!
                            data = true
                        } else {
                            data = false
                            result.errors!!.forEach { (key, value) ->
                                errors[key] = messageSource.getMessage(value, null, locale)
                            }
                        }
                    }
            }
        }

        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), responseStatus)
    }

}