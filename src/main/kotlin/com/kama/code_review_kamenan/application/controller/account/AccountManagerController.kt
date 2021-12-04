package com.kama.code_review_kamenan.application.controller.account

import com.kama.code_review_kamenan.application.controlForm.Color
import com.kama.code_review_kamenan.application.controlForm.ControlForm
import com.kama.code_review_kamenan.application.controller.BaseController
import com.kama.code_review_kamenan.application.controller.ControllerEndpoint
import com.kama.code_review_kamenan.application.event.CreateServiceProviderEvent
import com.kama.code_review_kamenan.domain.account.entity.FolderSrc
import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.account.entity.UserType
import com.kama.code_review_kamenan.domain.account.port.RoleDomain
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.domain.activity_area.port.ActivityAreaDomain
import com.kama.code_review_kamenan.infrastructure.local.storage.StorageService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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
    private val activityAreaDomain: ActivityAreaDomain,
    private val storageService: StorageService,
    private val eventPublisher: ApplicationEventPublisher,
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
        val activityAreas = activityAreaDomain.findAll()

        model.addAttribute("activityAreas", activityAreas)

        return forwardTo("register")
    }

    @PostMapping("/register")
    fun registerServiceProvider(
        redirectAttributes: RedirectAttributes,
        @RequestParam("firstname") firstname: String,
        @RequestParam("lastname") lastname: String,
        @RequestParam("email") email: String,
        @RequestParam("username") username: String,
        @RequestParam("phoneNumber") phoneNumber: String,
        @RequestParam("phoneNumber2") phoneNumber2: String,
        @RequestParam("biography") biography: String,
        @RequestParam("activityArea") activityAreaId: String,
        @RequestParam("idCardFront") idCardFront: MultipartFile,
        @RequestParam("idCardBack") idCardBack: MultipartFile,
        @RequestParam("password") password: String,
        @RequestParam("password-confirm") passwordConfirm: String,
        locale: Locale,
    ): String {

        var page = "register"

        when {
            password != passwordConfirm -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("passwordDontMatch", null, locale),
                    Color.Red
                )
            }
            userDomain.isTakenUserByEmail(email) -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("emailAlreadyExists", null, locale),
                    Color.Red
                )
            }
            userDomain.isTakenUserByUsername(username) -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("usernameAlreadyExists", null, locale),
                    Color.Red
                )
            }
            else -> {
                activityAreaDomain.findById(activityAreaId.toLong()).ifPresent { activityArea ->

                    roleDomain.findByName(UserType.SERVICE_PROVIDER).ifPresent { role ->
                        val serviceProvider = ServiceProvider()

                        serviceProvider.firstname = firstname
                        serviceProvider.lastname = lastname
                        serviceProvider.contact.email = email
                        serviceProvider.username = username
                        serviceProvider.password = password
                        serviceProvider.roles = Collections.singleton(role)
                        serviceProvider.locked = true
                        serviceProvider.contact.phoneNumber = phoneNumber
                        serviceProvider.contact.phoneNumber2 = phoneNumber2
                        serviceProvider.biography = biography
                        serviceProvider.activityArea = activityArea

                        /**
                         * Uploading files
                         */
                        if (idCardFront.originalFilename != "") {
                            if (!storageService.storeMix(idCardFront, "", FolderSrc.SRC_ID_CARDS).first) {
                                serviceProvider.idCardFront =
                                    idCardFront.originalFilename.toString().lowercase(Locale.FRENCH)
                            }
                        }

                        if (idCardBack.originalFilename != "") {
                            if (!storageService.storeMix(idCardBack, "", FolderSrc.SRC_ID_CARDS).first) {
                                serviceProvider.idCardBack =
                                    idCardBack.originalFilename.toString().lowercase(Locale.FRENCH)
                            }
                        }

                        val result = userDomain.saveUser(serviceProvider)

                        if (result.isSuccess) {
                            eventPublisher.publishEvent(CreateServiceProviderEvent(result.data as ServiceProvider))
                        }

                        val err: MutableMap<String, String> = mutableMapOf()
                        if (result.errors!!.isNotEmpty()) {
                            result.errors.forEach { (key, value) ->
                                err[key] = messageSource.getMessage(value, null, locale)
                            }
                        }

                        if (
                            ControlForm.verifyHashMapRedirect(
                                redirectAttributes, err,
                                messageSource.getMessage("success_register", null, locale)
                            )
                        ) {
                            page = "login"
                        }

                    }

                }
            }
        }

        return redirectTo(page)
    }

    @GetMapping("/validate-account/{username}")
    fun validateAccount(model: Model, @PathVariable username: String): String {
        val user: User? = userDomain.findByUsername(username).orElse(null)

        return if (user == null) {
            forwardTo("failure-validate-account")
        } else {
            userDomain.unlock(user.username)

            forwardTo("login")
        }

    }

}