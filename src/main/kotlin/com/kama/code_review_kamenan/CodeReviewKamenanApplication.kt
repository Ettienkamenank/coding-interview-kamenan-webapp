package com.kama.code_review_kamenan

import com.kama.code_review_kamenan.application.bootstrap.ActivityAreaBootstrap
import com.kama.code_review_kamenan.application.bootstrap.ActuatorBootstrap
import com.kama.code_review_kamenan.application.bootstrap.AnnouncementBootstrap
import com.kama.code_review_kamenan.application.bootstrap.RoleBootstrap
import com.kama.code_review_kamenan.domain.account.port.RoleDomain
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.domain.activity_area.port.ActivityAreaDomain
import com.kama.code_review_kamenan.domain.announcement.port.AnnouncementDomain
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class CodeReviewKamenanApplication {

    @Bean
    fun initDatabase(
        roleDomain: RoleDomain,
        userDomain: UserDomain,
        activityAreaDomain: ActivityAreaDomain,
        announcementDomain: AnnouncementDomain,
    ): CommandLineRunner {
        return CommandLineRunner {
            RoleBootstrap.seed(roleDomain)
            ActuatorBootstrap.seed(roleDomain, userDomain)
            ActivityAreaBootstrap.seed(activityAreaDomain)
            AnnouncementBootstrap.seed(userDomain, announcementDomain, activityAreaDomain)
        }
    }

}

fun main(args: Array<String>) {
    runApplication<CodeReviewKamenanApplication>(*args)
}
