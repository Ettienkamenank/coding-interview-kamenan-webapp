package com.kama.code_review_kamenan.application.controller

import com.kama.code_review_kamenan.application.facade.AuthenticationFacade
import com.kama.code_review_kamenan.domain.account.entity.UserType
import com.kama.code_review_kamenan.domain.account.port.UserDomain
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
                forwardTo("backend/admin/admin")
            }

            UserType.CUSTOMER -> {
                forwardTo("backend/service_provider/service_provider")
//                val lostObjectTypes = lostObjectTypeDomain.findAll()
//                model.addAttribute("lostObjectTypes", lostObjectTypes)
//
//                forwardTo("frontend/dashboard")
            }

            else -> forwardTo("account/login")
        }
    }

}