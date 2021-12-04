package com.kama.code_review_kamenan.application.listener

import com.kama.code_review_kamenan.application.event.SendEmailEvent
import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.utils.helper.EmailHelper
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class SendEmailListener(private val emailHelper: EmailHelper):ApplicationListener<SendEmailEvent> {
    override fun onApplicationEvent(event: SendEmailEvent) {
        if (event!=null){
            val user : User = event.user

            if (!user.contact.email.equals("") && user.contact.email != "null"){

                emailHelper.sendEmail(user.contact.email,user.username)
            }
        }
    }


}