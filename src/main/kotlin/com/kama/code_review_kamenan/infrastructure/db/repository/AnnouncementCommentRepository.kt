package com.kama.code_review_kamenan.infrastructure.db.repository

import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.announcement_comment.entity.AnnouncementComment
import org.springframework.data.jpa.repository.JpaRepository


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:25
 */
interface AnnouncementCommentRepository: JpaRepository<AnnouncementComment, Long> {

    fun findAllByAnnouncement(announcement: Announcement): MutableList<AnnouncementComment>

    fun countAllByAnnouncement(announcement: Announcement): Long

}