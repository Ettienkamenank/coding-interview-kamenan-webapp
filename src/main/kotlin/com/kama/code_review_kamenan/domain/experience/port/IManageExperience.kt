package com.kama.code_review_kamenan.domain.experience.port

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.experience.entity.Experience
import com.kama.code_review_kamenan.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 19:59
 */
interface IManageExperience {

    fun save(experience: Experience): OperationResult<Experience>

    fun findByServiceProvider(serviceProvider: ServiceProvider): Optional<Experience>

    fun findById(id: Long): Optional<Experience>

    fun findAll(): List<Experience>

    fun count(): Long

}