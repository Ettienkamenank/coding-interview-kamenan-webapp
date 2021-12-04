package com.kama.code_review_kamenan.application.listener

import com.kama.code_review_kamenan.application.event.CreateServiceProviderEvent
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.utils.helper.EmailHelper
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component


/**
 * @Author: Ettien Kamenan
 * @On: 04/12/2021
 * @At: 15:45
 */

@Component
class CreateServiceProviderListener(private val emailHelper: EmailHelper): ApplicationListener<CreateServiceProviderEvent> {
    override fun onApplicationEvent(event: CreateServiceProviderEvent) {
        val serviceProvider = event.serviceProvider

        if(serviceProvider.contact.email != "" && !serviceProvider.contact.email.equals(null)) {
            emailHelper.registerEmail(serviceProvider)
        }
    }
}