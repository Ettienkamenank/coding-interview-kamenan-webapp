package com.kama.code_review_kamenan.infrastructure.remote.dto

import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 05/12/2021
 * @At: 03:19
 */
class AnnouncementDto(
    var id: Long = -1L,
    var title: String = "",
    var description: String = "",
    var customer: UserDto? = null,
    var activityArea: ActivityAreaDto? = null
)

class AnnouncementFromMobile(
    var title: String = "",
    var description: String = "",
    var activityAreaId: String = "",
)