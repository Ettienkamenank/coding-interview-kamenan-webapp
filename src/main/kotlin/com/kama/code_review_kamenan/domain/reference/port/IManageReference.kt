package com.kama.code_review_kamenan.domain.reference.port

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.reference.entity.Reference
import com.kama.code_review_kamenan.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 18:23
 */
interface IManageReference {

    fun save(reference: Reference): OperationResult<Reference>

    fun findByServiceProvider(serviceProvider: ServiceProvider): Optional<Reference>

    fun findById(id: Long): Optional<Reference>

    fun findAll(): List<Reference>

    fun findAllByServiceProvider(serviceProvider: ServiceProvider): MutableList<Reference>

    fun count(): Long

}