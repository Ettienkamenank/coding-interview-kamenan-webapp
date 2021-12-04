package com.kama.code_review_kamenan.utils.helper

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

    fun registerEmail(user: User) {

        val baseUrl: String = "/email/"

        val link: String = baseUrl + "confirm-account/" + user.username
        val unsubscribeLink: String = baseUrl + "unsubscribe/" + user.username

        val context = Context()
        context.setVariable("user", user)
        context.setVariable("link", link)
        context.setVariable("unsubscribe_link", unsubscribeLink)

        val process = templateEngine.process("service/email/register-template", context)
        val mimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage)
        helper.setSubject("Bienvenue " + user.firstname + " " + user.lastname)
        helper.setText(process, true)
        helper.setTo(user.contact.email)
        javaMailSender.send(mimeMessage)
    }
}