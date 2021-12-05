package com.kama.code_review_kamenan.domain.announcement_action.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.entity.common.BaseEntity
import com.kama.code_review_kamenan.infrastructure.remote.dto.AnnouncementActionDto
import javax.persistence.Entity
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 15:47
 */
@Entity
class AnnouncementAction() : BaseEntity() {

    var comment: String = ""

    var type: String = AnnouncementActionType.AVAILABILITY

    var state: Int = 0

    @ManyToOne(targetEntity = User::class, optional = false)
    var user: User? = null

    @JsonIgnore
    @ManyToOne(targetEntity = Announcement::class, optional = false)
    var announcement: Announcement? = null

    fun toAnnouncementActionDto() = AnnouncementActionDto(
        id = id,
        comment = comment,
        type = type,
        user = user!!.toUserDto()
    )

}