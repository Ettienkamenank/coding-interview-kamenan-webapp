package com.kama.code_review_kamenan.application.api.announcement

import com.kama.code_review_kamenan.application.api.ApiConstant
import com.kama.code_review_kamenan.application.api.RestControllerEndpoint
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
import com.kama.code_review_kamenan.infrastructure.remote.dto.AnnouncementFromMobile
import org.springframework.context.MessageSource
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
                    val data = announcementDomain.findAll().map { it.toAnnouncementDto() }

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

    @PostMapping(value = ["/create"])
    fun createAnnouncement(
        @SessionToken token: String?,
        @RequestBody(required = true) announcementToAdd: AnnouncementFromMobile,
        locale: Locale
    ): Map<String, Any> {

        val response: MutableMap<String, Any> = mutableMapOf()
        response[ApiConstant.ERROR] = true

        when {
            announcementToAdd.title.isEmpty() -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyTitle", null, locale)
            }
            announcementToAdd.description.isEmpty() -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyDescription", null, locale)
            }
            token == null -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyToken", null, locale)
            }
            else -> {
                val user = userDomain.findUserBySessionToken(token)

                if (user.isPresent) {
                    activityAreaDomain.findById(announcementToAdd.activityAreaId.toLong()).ifPresent { activityArea ->
                        val announcement = Announcement()
                        announcement.title = announcementToAdd.title
                        announcement.description = announcementToAdd.description
                        announcement.customer = user.get() as Customer
                        announcement.activityArea = activityArea

                        val result = announcementDomain.save(announcement)

                        if (result.isSuccess) {
                            response[ApiConstant.ERROR] = false
                            response[ApiConstant.DATA] = true
                            response[ApiConstant.MESSAGE] = "Annonce créé avec success"
                        } else {
                            response[ApiConstant.MESSAGE] =
                                messageSource.getMessage(result.errors!!.values.first(), null, locale)
                        }
                    }
                } else {
                    response[ApiConstant.MESSAGE] = "Session expiré"
                }
            }
        }

        return response
    }

    @PostMapping(value = ["/report"])
    fun reportAnnouncement(
        @SessionToken token: String?,
        @RequestBody(required = true) announcementActionToAdd: AnnouncementActionFromMobile,
        locale: Locale
    ): Map<String, Any> {

        val response: MutableMap<String, Any> = mutableMapOf()
        response[ApiConstant.ERROR] = true

        when {
            announcementActionToAdd.comment.isEmpty() -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyComment", null, locale)
            }
            announcementActionToAdd.announcementId.isEmpty() -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("announcementNotFound", null, locale)
            }
            token == null -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyToken", null, locale)
            }
            else -> {
                val user = userDomain.findUserBySessionToken(token)

                if (user.isPresent) {
                    announcementDomain.findById(announcementActionToAdd.announcementId.toLong())
                        .ifPresent { announcement ->

                            val announcementAction = AnnouncementAction()
                            announcementAction.comment = announcementActionToAdd.comment
                            announcementAction.type = AnnouncementActionType.REPORT
                            announcementAction.user = user.get()
                            announcementAction.announcement = announcement

                            val result = announcementActionDomain.save(announcementAction)

                            if (result.isSuccess) {
                                response[ApiConstant.ERROR] = false
                                response[ApiConstant.DATA] = true
                                response[ApiConstant.MESSAGE] = "Annonce signalée"
                            } else {
                                response[ApiConstant.MESSAGE] =
                                    messageSource.getMessage(result.errors!!.values.first(), null, locale)
                            }
                        }
                } else {
                    response[ApiConstant.MESSAGE] = "Session expiré"
                }
            }
        }

        return response
    }

    @PostMapping(value = ["/comment"])
    fun commentOnAnnouncement(
        @SessionToken token: String?,
        @RequestBody(required = true) announcementCommentToAdd: AnnouncementCommentFromMobile,
        locale: Locale
    ): Map<String, Any> {

        val response: MutableMap<String, Any> = mutableMapOf()
        response[ApiConstant.ERROR] = true

        when {
            announcementCommentToAdd.comment.isEmpty() -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyComment", null, locale)
            }
            announcementCommentToAdd.announcementId.isEmpty() -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("announcementNotFound", null, locale)
            }
            token == null -> {
                response[ApiConstant.MESSAGE] = messageSource.getMessage("emptyToken", null, locale)
            }
            else -> {
                val user = userDomain.findUserBySessionToken(token)

                if (user.isPresent) {
                    announcementDomain.findById(announcementCommentToAdd.announcementId.toLong())
                        .ifPresent { announcement ->

                            val announcementComment = AnnouncementComment()
                            announcementComment.comment = announcementCommentToAdd.comment
                            announcementComment.user = user.get()
                            announcementComment.announcement = announcement

                            val result = announcementCommentDomain.save(announcementComment)

                            if (result.isSuccess) {
                                response[ApiConstant.ERROR] = false
                                response[ApiConstant.DATA] = true
                                response[ApiConstant.MESSAGE] = "Annonce commentée"
                            } else {
                                response[ApiConstant.MESSAGE] =
                                    messageSource.getMessage(result.errors!!.values.first(), null, locale)
                            }
                        }
                } else {
                    response[ApiConstant.MESSAGE] = "Session expiré"
                }
            }
        }

        return response
    }

}