package com.kama.code_review_kamenan.infrastructure.remote.dto


/**
 * @Author: Ettien Kamenan
 * @On: 05/12/2021
 * @At: 04:04
 */
class AnnouncementActionDto(
    var id: Long = -1L,
    var comment: String = "",
    var type: String = "",
    var user: UserDto? = null,
)

class AnnouncementActionFromMobile(
    var comment: String = "",
    var announcementId: String = "",
)