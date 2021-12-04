package com.kama.code_review_kamenan.infrastructure.db.repository

import com.kama.code_review_kamenan.domain.account.entity.Customer
import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:17
 */
interface AnnouncementRepository: JpaRepository<Announcement, Long> {

    fun findByTitle(title: String): Optional<Announcement>

    fun findByCustomer(customer: Customer): Optional<Announcement>

    fun findByActivityArea(activityArea: ActivityArea): Optional<Announcement>

    fun findAllByActivityArea(activityArea: ActivityArea): MutableList<Announcement>

}