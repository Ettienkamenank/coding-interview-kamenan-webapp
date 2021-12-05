package com.kama.code_review_kamenan.infrastructure.db.repository

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.experience.entity.Experience
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:26
 */
interface ExperienceRepository: JpaRepository<Experience, Long> {

    fun findByTitle(title: String): Optional<Experience>

    fun findByServiceProvider(serviceProvider: ServiceProvider): Optional<Experience>

    fun findAllByServiceProvider(serviceProvider: ServiceProvider): MutableList<Experience>

}