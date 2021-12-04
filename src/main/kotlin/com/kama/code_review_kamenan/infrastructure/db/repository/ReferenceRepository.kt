package com.kama.code_review_kamenan.infrastructure.db.repository

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.reference.entity.Reference
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:21
 */
interface ReferenceRepository: JpaRepository<Reference, Long> {

    fun findByTitle(title: String): Optional<Reference>

    fun findByServiceProvider(serviceProvider: ServiceProvider): Optional<Reference>

}