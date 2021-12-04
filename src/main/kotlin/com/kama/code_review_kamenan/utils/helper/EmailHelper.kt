package com.kama.code_review_kamenan.utils.helper

import com.kama.code_review_kamenan.application.controlForm.Url
import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.account.entity.User
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

/**
 * @Author: Ettien Kamenan
 * @Date: 2021/06/01
 * @Time: 13:35
 */
@Service
class EmailHelper(
    private val javaMailSender: JavaMailSender,
    private val templateEngine: TemplateEngine, ) {

    fun sendEmail(to: String, reference: String) {
        val msg = SimpleMailMessage()

        msg.setTo(to)
        msg.setSubject("REFERENCE DE VOTRE RENDEZ-VOUS")
        msg.setText("La reference est : $reference")
        javaMailSender.send(msg)
    }

    fun registerEmail(serviceProvider: ServiceProvider) {

        val link: String = "http://localhost:8080/" + "account/validate-account/" + serviceProvider.username

        val msg = SimpleMailMessage()

        msg.setTo(serviceProvider.contact.email)
        msg.setSubject("VALIDATION DE VOTRE COMPTE")
        msg.setText("Veuillez valider votre compte via le lien suivant : $link")
        javaMailSender.send(msg)

    }
}