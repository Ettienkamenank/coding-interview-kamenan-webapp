package com.kama.code_review_kamenan.domain.announcement_comment.port

import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.announcement_comment.entity.AnnouncementComment
import com.kama.code_review_kamenan.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 23:53
 */
interface IManageAnnouncementComment {

    fun save(announcementComment: AnnouncementComment): OperationResult<AnnouncementComment>

    fun findById(id: Long): Optional<AnnouncementComment>

    fun findAllByAnnouncement(announcement: Announcement): MutableList<AnnouncementComment>

    fun findAll(): List<AnnouncementComment>

    fun count(): Long

    fun countAllByAnnouncement(announcement: Announcement): Long


}