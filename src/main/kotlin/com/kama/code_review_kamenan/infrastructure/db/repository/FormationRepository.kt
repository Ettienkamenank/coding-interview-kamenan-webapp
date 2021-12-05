package com.kama.code_review_kamenan.infrastructure.db.repository

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.formation.entity.Formation
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:27
 */
interface FormationRepository: JpaRepository<Formation, Long> {

    fun findByTitle(title: String): Optional<Formation>

    fun findByServiceProvider(serviceProvider: ServiceProvider): Optional<Formation>

    fun findAllByServiceProvider(serviceProvider: ServiceProvider): MutableList<Formation>

}