package com.kama.code_review_kamenan.application.event

import com.kama.code_review_kamenan.domain.account.entity.User
import org.springframework.context.ApplicationEvent

class SendEmailEvent(val user: User): ApplicationEvent(user) {
}