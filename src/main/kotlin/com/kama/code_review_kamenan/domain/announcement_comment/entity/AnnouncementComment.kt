package com.kama.code_review_kamenan.domain.announcement_comment.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.entity.common.BaseEntity
import com.kama.code_review_kamenan.infrastructure.remote.dto.AnnouncementCommentDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 15:46
 */
@Entity
class AnnouncementComment() : BaseEntity() {

    @Column(columnDefinition = "text")
    var comment: String = ""

    var state: Int = 0

    @ManyToOne(targetEntity = User::class, optional = false)
    var user: User? = null

    @JsonIgnore
    @ManyToOne(targetEntity = Announcement::class, optional = false)
    var announcement: Announcement? = null

    fun toAnnouncementCommentDto() = AnnouncementCommentDto(
        id = id, comment = comment, user = user!!.toUserDto()
    )

}