package com.kama.code_review_kamenan.application.listener

import com.kama.code_review_kamenan.application.event.RegisterDoneEvent
import com.kama.code_review_kamenan.utils.helper.EmailHelper
import org.springframework.context.ApplicationListener

class RegisterDoneListener(private val emailHelper: EmailHelper):ApplicationListener<RegisterDoneEvent> {

    override fun onApplicationEvent(event: RegisterDoneEvent) {

//        if(event!=null){
//            if(!event.user.contact.email.equals("") && !event.user.contact.email.equals(null) ){
//                emailHelper.sendEmail(event.user.contact.email,event.user.username,event.user.password)
//            }
//        }

    }


}