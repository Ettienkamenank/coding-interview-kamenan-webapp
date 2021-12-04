package com.kama.code_review_kamenan.domain.activity_area.port

import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import com.kama.code_review_kamenan.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 17:36
 */
interface IManageActivityArea {

    fun save(activityArea: ActivityArea): OperationResult<ActivityArea>

    fun findByTitle(title: String): Optional<ActivityArea>

    fun findById(id: Long): Optional<ActivityArea>

    fun findAll(): List<ActivityArea>

    fun count(): Long

}