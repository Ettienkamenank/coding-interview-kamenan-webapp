package com.kama.code_review_kamenan.infrastructure.db.repository

import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:18
 */
interface ActivityAreaRepository: JpaRepository<ActivityArea, Long> {

    fun findByTitle(title: String): Optional<ActivityArea>

}