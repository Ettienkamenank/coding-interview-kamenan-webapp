package com.kama.code_review_kamenan.domain.announcement.port

import com.kama.code_review_kamenan.domain.account.entity.Customer
import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 21:14
 */
interface IManageAnnouncement {

    fun save(announcement: Announcement): OperationResult<Announcement>

    fun lock(id: Long): OperationResult<Boolean>

    fun unlock(id: Long): OperationResult<Boolean>

    fun delete(id: Long): OperationResult<Boolean>

    fun findById(id: Long): Optional<Announcement>

    fun findByTitle(title: String): Optional<Announcement>

    fun findByCustomer(customer: Customer): Optional<Announcement>

    fun findByActivityArea(activityArea: ActivityArea): Optional<Announcement>

    fun findAll(): List<Announcement>

    fun findAllByActivityArea(activityArea: ActivityArea): MutableList<Announcement>

    fun count(): Long

}