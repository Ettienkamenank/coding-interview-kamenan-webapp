package com.kama.code_review_kamenan.infrastructure.remote.dto


/**
 * @Author: Ettien Kamenan
 * @On: 05/12/2021
 * @At: 04:20
 */
class AnnouncementCommentDto(
    var id: Long = -1L,
    var comment: String = "",
    var user: UserDto? = null,
)

class AnnouncementCommentFromMobile(
    var comment: String = "",
    var announcementId: String = "",
)