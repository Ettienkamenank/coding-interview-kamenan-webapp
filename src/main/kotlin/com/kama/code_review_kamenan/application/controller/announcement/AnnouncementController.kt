package com.kama.code_review_kamenan.application.controller.announcement

import com.kama.code_review_kamenan.application.controlForm.Color
import com.kama.code_review_kamenan.application.controlForm.ControlForm
import com.kama.code_review_kamenan.application.controller.BaseController
import com.kama.code_review_kamenan.application.controller.ControllerEndpoint
import com.kama.code_review_kamenan.application.facade.AuthenticationFacade
import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.account.entity.UserType
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.domain.announcement.port.AnnouncementDomain
import com.kama.code_review_kamenan.domain.announcement_action.entity.AnnouncementAction
import com.kama.code_review_kamenan.domain.announcement_action.entity.AnnouncementActionType
import com.kama.code_review_kamenan.domain.announcement_action.port.AnnouncementActionDomain
import com.kama.code_review_kamenan.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 04/12/2021
 * @At: 22:48
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.DASHBOARD_ANNOUNCEMENT])
class AnnouncementController(
    private val authenticationFacade: AuthenticationFacade,
    private val announcementDomain: AnnouncementDomain,
    private val announcementActionDomain: AnnouncementActionDomain,
    private val userDomain: UserDomain,
    private val messageSource: MessageSource,
) : BaseController("", ControllerEndpoint.DASHBOARD_ANNOUNCEMENT) {

    @GetMapping("/allAnnouncements")
    fun listOfAnnouncements(model: Model): String {
        val user = authenticationFacade.getAuthenticatedUser().get()

        when (userDomain.findTypeBy(user.id)) {
            UserType.ADMIN -> {
                val announcements = announcementDomain.findAll()
                model.addAttribute("announcements", announcements)

                return forwardTo("backend/admin/announcement/announcement")
            }

            UserType.SERVICE_PROVIDER -> {
                user as ServiceProvider
                val announcements = user.activityArea?.let { announcementDomain.findAllByActivityArea(it) }
                model.addAttribute("announcements", announcements)
                model.addAttribute("profileVisible", user.profileVisible)

                return forwardTo("backend/service_provider/service_provider")
            }
            else ->  {
                return  forwardTo("account/login")
            }
        }

    }

    @PostMapping("/suggestAvailability")
    fun suggestAvailability(
        redirectAttributes: RedirectAttributes,
        @RequestParam("id") annonceId: String,
        @RequestParam("comment") comment: String,
        locale: Locale
    ): String {

        var url = "allAnnouncements"

        when {
            annonceId.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("announcementNotFound", null, locale),
                    Color.Red
                )
            }
            else -> {
                val user = authenticationFacade.getAuthenticatedUser().get()

                when (userDomain.findTypeBy(user.id)) {

                    UserType.SERVICE_PROVIDER -> {
                        user as ServiceProvider

                        announcementDomain.findById(annonceId.toLong()).ifPresent { annonce ->
                            val announcementA = AnnouncementAction()
                            announcementA.comment = comment
                            announcementA.user = user
                            announcementA.announcement = annonce
                            announcementA.type = AnnouncementActionType.AVAILABILITY

                            val result = announcementActionDomain.save(announcementA)

                            val err: MutableMap<String, String> = mutableMapOf()
                            if (result.errors!!.isNotEmpty()) {
                                result.errors.forEach { (key, value) ->
                                    err[key] = messageSource.getMessage(value, null, locale)
                                }
                            }

                            if (
                                ControlForm.verifyHashMapRedirect(
                                    redirectAttributes,
                                    err,
                                    messageSource.getMessage("userVisible", null, locale)
                                )
                            ) {
                                url = "allAnnouncements"
                            }
                        }

                    }

                    else -> url = "allAnnouncements"
                }

            }
        }

        return redirectTo(url)

    }

}