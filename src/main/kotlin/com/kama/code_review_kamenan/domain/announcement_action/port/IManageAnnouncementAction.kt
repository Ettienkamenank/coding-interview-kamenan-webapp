package com.kama.code_review_kamenan.domain.announcement_action.port

import com.kama.code_review_kamenan.domain.account.entity.Customer
import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.announcement_action.entity.AnnouncementAction
import com.kama.code_review_kamenan.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 22:58
 */
interface IManageAnnouncementAction {

    fun save(announcementAction: AnnouncementAction): OperationResult<AnnouncementAction>

    fun findById(id: Long): Optional<AnnouncementAction>

    fun findAll(): List<AnnouncementAction>

    fun findAllByAnnouncementAndType(announcement: Announcement, type: String): MutableList<AnnouncementAction>

    fun count(): Long

    fun countAllByAnnouncementAndType(announcement: Announcement, type: String): Long

    fun countAllByUserAndType(user: User, type: String): Long

}