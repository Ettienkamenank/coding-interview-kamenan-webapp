package com.kama.code_review_kamenan.application.controller.announcement

import com.kama.code_review_kamenan.application.controller.BaseController
import com.kama.code_review_kamenan.application.controller.ControllerEndpoint
import com.kama.code_review_kamenan.domain.announcement.port.AnnouncementDomain
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


/**
 * @Author: Ettien Kamenan
 * @On: 04/12/2021
 * @At: 22:48
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.DASHBOARD_ADMIN_ANNOUNCEMENT])
class AnnouncementController(
    private val announcementDomain: AnnouncementDomain,
    private val messageSource: MessageSource,
) : BaseController("/backend/admin/announcement/", ControllerEndpoint.DASHBOARD_ADMIN_ANNOUNCEMENT) {

    @GetMapping("/allAnnouncements")
    fun listOfAnnouncements(model: Model): String {
        val announcements = announcementDomain.findAll()
        model.addAttribute("announcements", announcements)

        return forwardTo("announcement")
    }
}