package com.kama.code_review_kamenan.infrastructure.db.repository

import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.announcement_action.entity.AnnouncementAction
import org.springframework.data.jpa.repository.JpaRepository


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:25
 */
interface AnnouncementActionRepository: JpaRepository<AnnouncementAction, Long> {

    fun findAllByAnnouncementAndType(announcement: Announcement, type: String): MutableList<AnnouncementAction>

    fun countAllByAnnouncementAndType(announcement: Announcement, type: String): Long

    fun countAllByUserAndType(user: User, type: String): Long

}