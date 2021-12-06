package com.kama.code_review_kamenan.application.controller.service_provider

import com.kama.code_review_kamenan.application.controlForm.Color
import com.kama.code_review_kamenan.application.controlForm.ControlForm
import com.kama.code_review_kamenan.application.controller.BaseController
import com.kama.code_review_kamenan.application.controller.ControllerEndpoint
import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 04/12/2021
 * @At: 19:36
 */

@Controller
@RequestMapping(value = [ControllerEndpoint.DASHBOARD_ADMIN_SERVICE_PROVIDER])
class ServiceProviderController(
    private val userDomain: UserDomain,
    private val messageSource: MessageSource,
) :
    BaseController("/backend/admin/service_provider/", ControllerEndpoint.DASHBOARD_ADMIN_SERVICE_PROVIDER) {

    @GetMapping("/allServiceProviders")
    fun listOfServiceProviders(model: Model): String {
        val serviceProviders = userDomain.findAllServiceProviders()
        model.addAttribute("serviceProviders", serviceProviders)

        return forwardTo("service_provider")
    }

    @GetMapping("/validateAccount/{id}")
    fun goToValidateAccount(
        model: Model, @PathVariable id: String,
        locale: Locale
    ): String {
        when {
            id.isEmpty() -> {
                ControlForm.model(
                    model,
                    messageSource.getMessage("userNotFound", null, locale),
                    Color.Red
                )
            }
            else -> {
                userDomain.findUserById(id.toLong()).ifPresent {
                    model.addAttribute("provider", it)
                }
            }
        }

        return forwardTo("validate_account")
    }

    @PostMapping("/makeProfileVisible")
    fun makeServiceProviderProfileVisible(
        redirectAttributes: RedirectAttributes,
        @RequestParam("id") id: String,
        locale: Locale
    ): String {

        var url = "allServiceProviders"

        when {
            id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("userNotFound", null, locale),
                    Color.Red
                )
            }
            else -> {

                val result: OperationResult<ServiceProvider> = userDomain.makeServiceProviderVisible(id.toLong())

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
                    url = "allServiceProviders"
                }
            }
        }

        return redirectTo(url)

    }

}