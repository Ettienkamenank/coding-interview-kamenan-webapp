package com.kama.code_review_kamenan.application.bootstrap

import com.kama.code_review_kamenan.domain.account.entity.Customer
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.domain.activity_area.port.ActivityAreaDomain
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.announcement.port.AnnouncementDomain

object AnnouncementBootstrap {

    fun seed(userDomain: UserDomain, announcementDomain: AnnouncementDomain, activityAreaDomain: ActivityAreaDomain) {
        if (announcementDomain.count() == 0L) {
            activityAreaDomain.findById(1).ifPresent { area ->
                userDomain.findByUsername("client").ifPresent { user ->
                    val announcement = Announcement()
                    announcement.title = "Recherche de plombier"
                    announcement.description = "A la recherche d'un plombier pouvant reparer ma vanne d'arret"
                    announcement.customer = user as Customer
                    announcement.activityArea = area

                    announcementDomain.save(announcement)
                }
            }
        }
    }

}