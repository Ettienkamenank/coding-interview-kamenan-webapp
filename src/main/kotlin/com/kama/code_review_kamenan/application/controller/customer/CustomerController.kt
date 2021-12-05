package com.kama.code_review_kamenan.application.controller.customer

import com.kama.code_review_kamenan.application.controller.BaseController
import com.kama.code_review_kamenan.application.controller.ControllerEndpoint
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


/**
 * @Author: Ettien Kamenan
 * @On: 04/12/2021
 * @At: 22:38
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.DASHBOARD_ADMIN_CUSTOMER])
class CustomerController(
    private val userDomain: UserDomain,
    private val messageSource: MessageSource,
) : BaseController("/backend/admin/customer/", ControllerEndpoint.DASHBOARD_ADMIN_CUSTOMER) {

    @GetMapping("/allCustomers")
    fun listOfCustomers(model: Model): String {
        val customers = userDomain.findAllCustomers()
        model.addAttribute("customers", customers)

        return forwardTo("customer")
    }

}