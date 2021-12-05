package com.kama.code_review_kamenan.application.controller

import com.kama.code_review_kamenan.application.facade.AuthenticationFacade
import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.account.entity.UserType
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.domain.announcement.port.AnnouncementDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/12
 * @Time: 15:22
 */
@Controller
class MainController(
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val announcementDomain: AnnouncementDomain,
) : BaseController("", "") {


    @GetMapping(value = ["/dashboard", "/dashboard/"])
    fun dashboardPage(
        model: Model,
        redirectAttributes: RedirectAttributes
    ): String {

        val user = authenticationFacade.getAuthenticatedUser().get()
        val userRoleId = authenticationFacade.getAuthenticatedUser().get().roleId()

        return when (userDomain.findTypeBy(user.id)) {

            UserType.ACTUATOR -> {
                forwardTo("backend/admin/admin")
            }

            UserType.ADMIN -> {
                val announcements = announcementDomain.findAll()
                model.addAttribute("announcements", announcements)

                forwardTo("backend/admin/announcement/announcement")
            }

            UserType.SERVICE_PROVIDER -> {
                user as ServiceProvider
                val announcements = user.activityArea?.let { announcementDomain.findAllByActivityArea(it) }
                model.addAttribute("announcements", announcements)

                forwardTo("backend/service_provider/service_provider")
            }

            else -> forwardTo("account/login")
        }
    }

}