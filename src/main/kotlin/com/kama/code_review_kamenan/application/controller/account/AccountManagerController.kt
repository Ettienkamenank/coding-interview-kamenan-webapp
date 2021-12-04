package com.kama.code_review_kamenan.application.controller.account

import com.kama.code_review_kamenan.application.controlForm.Color
import com.kama.code_review_kamenan.application.controlForm.ControlForm
import com.kama.code_review_kamenan.application.controller.BaseController
import com.kama.code_review_kamenan.application.controller.ControllerEndpoint
import com.kama.code_review_kamenan.domain.account.entity.UserType
import com.kama.code_review_kamenan.domain.account.port.RoleDomain
import com.kama.code_review_kamenan.domain.account.port.UserDomain
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
 * @Date: 2021/06/09
 * @Time: 19:16
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.ACCOUNT])
class AccountManagerController(
    private val userDomain: UserDomain,
    private val roleDomain: RoleDomain,
    private val messageSource: MessageSource
) : BaseController("/account/", ControllerEndpoint.ACCOUNT) {

    @RequestMapping("/login")
    fun loginPage(
        model: Model
    ): String {
        return forwardTo("login")
    }

    @GetMapping("/register")
    fun registerPage(
        model: Model
    ): String {
        return forwardTo("register")
    }

//    @PostMapping("/register-collector")
//    fun registerCollector(
//        redirectAttributes: RedirectAttributes,
//        @RequestParam("firstName") firstName: String,
//        @RequestParam("lastName") lastName: String,
//        @RequestParam("phoneNumber") phoneNumber: String,
//        @RequestParam("email") email: String,
//        @RequestParam("birthday") birthday: String,
//        @RequestParam("username") username: String,
//        @RequestParam("city") city: String,
//        @RequestParam("localAddress") localAddress: String,
//        @RequestParam("password") password: String,
//        @RequestParam("passwordConfirm") passwordConfirm: String,
//        locale: Locale,
//    ): String {
//
//        var page = "register"
//        val collector = Collector()
//
//        when {
//            birthday.isEmpty() -> {
//                ControlForm.redirectAttribute(
//                    redirectAttributes,
//                    messageSource.getMessage("empty_birthday", null, locale),
//                    Color.red
//                )
//            }
//            city.isEmpty() -> {
//                ControlForm.redirectAttribute(
//                    redirectAttributes,
//                    messageSource.getMessage("empty_field", null, locale),
//                    Color.red
//                )
//            }
//            localAddress.isEmpty() -> {
//                ControlForm.redirectAttribute(
//                    redirectAttributes,
//                    messageSource.getMessage("empty_field", null, locale),
//                    Color.red
//                )
//            }
//            userDomain.isTakenUserByEmail(email) -> {
//                ControlForm.redirectAttribute(
//                    redirectAttributes,
//                    messageSource.getMessage("email_already_exists", null, locale),
//                    Color.red
//                )
//            }
//            userDomain.isTakenUserByUsername(username) -> {
//                ControlForm.redirectAttribute(
//                    redirectAttributes,
//                    messageSource.getMessage("username_already_exists", null, locale),
//                    Color.red
//                )
//            }
//            else -> {
//                roleDomain.findByName(UserType.CUSTOMER).ifPresent {
//                    collector.firstname = firstName
//                    collector.lastname = lastName
//                    collector.contact.phone = phoneNumber
//                    collector.contact.email = email
//                    collector.username = username
//                    collector.birthday = ControlForm.formatDate(birthday)
//                    collector.address.city = city
//                    collector.localAddress = localAddress
//                    collector.roles = Collections.singleton(it)
//                    collector.enabled = true
//
//                    val result = userDomain.saveUser(collector)
//
//                    val err: MutableMap<String, String> = mutableMapOf()
//                    if (result.errors!!.isNotEmpty()) {
//                        result.errors.forEach { (key, value) ->
//                            err[key] = messageSource.getMessage(value, null, locale)
//                        }
//                    }
//
//                    if (
//                        ControlForm.verifyHashMapRedirect(
//                            redirectAttributes, err,
//                            messageSource.getMessage("success_register", null, locale)
//                        )
//                    ) {
//                        page = "login"
//                    }
//
//                }
//
//            }
//        }
//
//        return redirectTo(page)
//    }

//    @PostMapping("/register-customer")
//    fun registerCustomer(
//        redirectAttributes: RedirectAttributes,
//        @RequestParam("firstName") firstName: String,
//        @RequestParam("lastName") lastName: String,
//        @RequestParam("phoneNumber") phoneNumber: String,
//        @RequestParam("email") email: String,
//        @RequestParam("birthday") birthday: String,
//        @RequestParam("username") username: String,
//        @RequestParam("password") password: String,
//        @RequestParam("passwordConfirm") passwordConfirm: String,
//        locale: Locale,
//    ): String {
//
//        var page = "register"
//        val customer = Customer()
//
//        when {
//            birthday.isEmpty() -> {
//                ControlForm.redirectAttribute(
//                    redirectAttributes,
//                    messageSource.getMessage("empty_birthday", null, locale),
//                    Color.red
//                )
//            }
//            password.isEmpty() -> {
//                ControlForm.redirectAttribute(
//                    redirectAttributes,
//                    messageSource.getMessage("empty_password", null, locale),
//                    Color.red
//                )
//            }
//            password != passwordConfirm -> {
//                ControlForm.redirectAttribute(
//                    redirectAttributes,
//                    messageSource.getMessage("password_different", null, locale),
//                    Color.red
//                )
//            }
//            userDomain.isTakenUserByEmail(email) -> {
//                ControlForm.redirectAttribute(
//                    redirectAttributes,
//                    messageSource.getMessage("email_already_exists", null, locale),
//                    Color.red
//                )
//            }
//            userDomain.isTakenUserByUsername(username) -> {
//                ControlForm.redirectAttribute(
//                    redirectAttributes,
//                    messageSource.getMessage("username_already_exists", null, locale),
//                    Color.red
//                )
//            }
//            else -> {
//                roleDomain.findByName(UserType.CUSTOMER).ifPresent {
//                    customer.firstname = firstName
//                    customer.lastname = lastName
//                    customer.contact.phone = phoneNumber
//                    customer.contact.email = email
//                    customer.username = username
//                    customer.birthday = ControlForm.formatDateV2(birthday)
//                    customer.password = password
//                    customer.roles = Collections.singleton(it)
//                    customer.enabled = true
//
//                    val result = userDomain.saveUser(customer)
//
//                    val err: MutableMap<String, String> = mutableMapOf()
//                    if (result.errors!!.isNotEmpty()) {
//                        result.errors.forEach { (key, value) ->
//                            err[key] = messageSource.getMessage(value, null, locale)
//                        }
//                    }
//
//                    if (
//                        ControlForm.verifyHashMapRedirect(
//                            redirectAttributes, err,
//                            messageSource.getMessage("success_register", null, locale)
//                        )
//                    ) {
//                        page = "login"
//                    }
//                }
//            }
//        }
//
//        return redirectTo(page)
//    }

    @GetMapping("/forgot-password")
    fun forgotPassPage(
        model: Model
    ): String {
        return forwardTo("forgot-password")
    }

}