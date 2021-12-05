package com.kama.code_review_kamenan.application.controller

import com.kama.code_review_kamenan.application.controlForm.ControlForm
import com.kama.code_review_kamenan.application.facade.AuthenticationFacade
import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.account.entity.UserType
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.domain.announcement.port.AnnouncementDomain
import com.kama.code_review_kamenan.domain.experience.entity.Experience
import com.kama.code_review_kamenan.domain.experience.port.ExperienceDomain
import com.kama.code_review_kamenan.domain.formation.entity.Formation
import com.kama.code_review_kamenan.domain.formation.port.FormationDomain
import com.kama.code_review_kamenan.domain.reference.entity.Reference
import com.kama.code_review_kamenan.domain.reference.port.ReferenceDomain
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
 * @On: 05/12/2021
 * @At: 00:45
 */

@Controller
@RequestMapping(value = [ControllerEndpoint.DASHBOARD_SERVICE_PROVIDER])
class UserProfileController(
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val announcementDomain: AnnouncementDomain,
    private val formationDomain: FormationDomain,
    private val experienceDomain: ExperienceDomain,
    private val referenceDomain: ReferenceDomain,
    private val messageSource: MessageSource,
) :
    BaseController("/backend/service_provider/", ControllerEndpoint.DASHBOARD_SERVICE_PROVIDER) {

    @GetMapping("/home")
    fun goToHome(model: Model): String {
        val user = authenticationFacade.getAuthenticatedUser().get()

        return when (userDomain.findTypeBy(user.id)) {

            UserType.SERVICE_PROVIDER -> {
                user as ServiceProvider
                val announcements = user.activityArea?.let { announcementDomain.findAllByActivityArea(it) }
                model.addAttribute("announcements", announcements)

                forwardTo("service_provider")
            }

            else -> forwardTo("account/login")
        }
    }

    @GetMapping("/profile")
    fun goToProfile(
        model: Model,
        locale: Locale
    ): String {
        val user = authenticationFacade.getAuthenticatedUser().get()

        return when (userDomain.findTypeBy(user.id)) {
            UserType.SERVICE_PROVIDER -> {
                user as ServiceProvider

                val formations = formationDomain.findAllByServiceProvider(user)
                val experiences = experienceDomain.findAllByServiceProvider(user)
                val references = referenceDomain.findAllByServiceProvider(user)

                model.addAttribute("provider", user)
                model.addAttribute("formations", formations)
                model.addAttribute("experiences", experiences)
                model.addAttribute("references", references)

                forwardTo("profile")
            }
            else -> {
                forwardTo("account/login")
            }
        }
    }

    @PostMapping("/addFormation")
    fun addFormation(
        redirectAttributes: RedirectAttributes,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        locale: Locale
    ): String {

        var url = "profile"

        val user = authenticationFacade.getAuthenticatedUser().get()

        when (userDomain.findTypeBy(user.id)) {
            UserType.SERVICE_PROVIDER -> {
                user as ServiceProvider

                val formation = Formation()
                formation.title = title
                formation.description = description
                formation.serviceProvider = user

                val result: OperationResult<Formation> = formationDomain.save(formation)

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
                        messageSource.getMessage("formationSave", null, locale)
                    )
                ) {
                    url = "profile"
                }

                return redirectTo(url)
            }
            else -> {
                url = "home"
                return redirectTo(url)
            }
        }

    }

    @PostMapping("/addExperience")
    fun addExperience(
        redirectAttributes: RedirectAttributes,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        locale: Locale
    ): String {

        var url = "profile"

        val user = authenticationFacade.getAuthenticatedUser().get()

        when (userDomain.findTypeBy(user.id)) {
            UserType.SERVICE_PROVIDER -> {
                user as ServiceProvider

                val experience = Experience()
                experience.title = title
                experience.description = description
                experience.serviceProvider = user

                val result: OperationResult<Experience> = experienceDomain.save(experience)

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
                        messageSource.getMessage("formationSave", null, locale)
                    )
                ) {
                    url = "profile"
                }

                return redirectTo(url)
            }
            else -> {
                url = "home"
                return redirectTo(url)
            }
        }

    }

    @PostMapping("/addReference")
    fun addReference(
        redirectAttributes: RedirectAttributes,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("link") link: String,
        locale: Locale
    ): String {

        var url = "profile"

        val user = authenticationFacade.getAuthenticatedUser().get()

        when (userDomain.findTypeBy(user.id)) {
            UserType.SERVICE_PROVIDER -> {
                user as ServiceProvider

                val reference = Reference()
                reference.title = title
                reference.description = description
                reference.link = link
                reference.serviceProvider = user

                val result: OperationResult<Reference> = referenceDomain.save(reference)

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
                        messageSource.getMessage("formationSave", null, locale)
                    )
                ) {
                    url = "profile"
                }

                return redirectTo(url)
            }
            else -> {
                url = "home"
                return redirectTo(url)
            }
        }

    }
}