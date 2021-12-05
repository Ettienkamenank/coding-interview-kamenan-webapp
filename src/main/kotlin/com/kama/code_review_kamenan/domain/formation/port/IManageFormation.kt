package com.kama.code_review_kamenan.domain.formation.port

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.formation.entity.Formation
import com.kama.code_review_kamenan.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 19:14
 */
interface IManageFormation {

    fun save(formation: Formation): OperationResult<Formation>

    fun findByServiceProvider(serviceProvider: ServiceProvider): Optional<Formation>

    fun findById(id: Long): Optional<Formation>

    fun findAll(): List<Formation>

    fun findAllByServiceProvider(serviceProvider: ServiceProvider): MutableList<Formation>

    fun count(): Long

}