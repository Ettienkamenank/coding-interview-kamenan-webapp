package com.kama.code_review_kamenan.application.controller.activity_area

import com.kama.code_review_kamenan.application.controlForm.Color
import com.kama.code_review_kamenan.application.controlForm.ControlForm
import com.kama.code_review_kamenan.application.controller.BaseController
import com.kama.code_review_kamenan.application.controller.ControllerEndpoint
import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import com.kama.code_review_kamenan.domain.activity_area.port.ActivityAreaDomain
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
 * @At: 17:31
 */

@Controller
@RequestMapping(value = [ControllerEndpoint.DASHBOARD_ADMIN_ACTIVITY_AREA])
class ActivityAreaController(
    private val activityAreaDomain: ActivityAreaDomain,
    private val messageSource: MessageSource,
): BaseController("/backend/admin/activity_area/", ControllerEndpoint.DASHBOARD_ADMIN_ACTIVITY_AREA) {

    @GetMapping("/allAreas")
    fun listOfAreas(model: Model): String {
        val activityAreas = activityAreaDomain.findAll()
        model.addAttribute("areas", activityAreas)

        return forwardTo("activity_area")
    }

    @PostMapping("/add")
    fun addArea(
        redirectAttributes: RedirectAttributes,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        locale: Locale
    ): String{

        var url = "allAreas"

        val area = ActivityArea()
        area.title = title
        area.description = description

        val result: OperationResult<ActivityArea> = activityAreaDomain.save(area)

        val err: MutableMap<String, String> = mutableMapOf()
        if (result.errors!!.isNotEmpty()) {
            result.errors.forEach {
                    (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
            }
        }

        if (
            ControlForm.verifyHashMapRedirect(
                redirectAttributes,
                err,
                messageSource.getMessage("area_save_success", null, locale)
            )
        ) {
            url = "allAreas"
        }

        return redirectTo(url)
    }

    @PostMapping("/update")
    fun updateArea(
        redirectAttributes: RedirectAttributes,
        @RequestParam("id") id: String,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        locale: Locale
    ): String{

        var url = "allAreas"

        when {
            id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("empty_id", null, locale),
                    Color.Red
                )
            }
            else -> {
                val area = ActivityArea()
                area.id = id.toLong()
                area.title = title
                area.description = description

                val result: OperationResult<ActivityArea> = activityAreaDomain.save(area)

                val err: MutableMap<String, String> = mutableMapOf()
                if (result.errors!!.isNotEmpty()) {
                    result.errors.forEach {
                            (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
                    }
                }

                if (
                    ControlForm.verifyHashMapRedirect(
                        redirectAttributes,
                        err,
                        messageSource.getMessage("area_save_success", null, locale)
                    )
                ) {
                    url = "allAreas"
                }
            }
        }

        return redirectTo(url)
    }

}
