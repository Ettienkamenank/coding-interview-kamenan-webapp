package com.kama.code_review_kamenan.domain.experience.worker

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.experience.entity.Experience
import com.kama.code_review_kamenan.domain.experience.port.ExperienceDomain
import com.kama.code_review_kamenan.infrastructure.db.repository.ExperienceRepository
import com.kama.code_review_kamenan.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:20
 */
@Service
class ExperienceWorker : ExperienceDomain {

    @Autowired
    lateinit var experienceRepository: ExperienceRepository

    override fun save(experience: Experience): OperationResult<Experience> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Experience? = null

        if (experience.title.isEmpty()) {
            errors["emptyTitle"] = "emptyTitle"
        }

        if (errors.isEmpty()) {
            if (experience.id == -1L) {
                val optionalExperienceTitle = experienceRepository.findByTitle(experience.title)

                when {
                    optionalExperienceTitle.isPresent -> {
                        errors["experienceExists"] = "experienceExists"
                    }
                    else -> {
                        data = experienceRepository.save(experience)
                    }
                }
            } else {
                val optionalExperience = experienceRepository.findById(experience.id)

                if (optionalExperience.isPresent) {
                    val experienceToUpdate = optionalExperience.get()
                    experienceToUpdate.title = experience.title
                    experienceToUpdate.description = experience.description

                    data = experienceRepository.save(experienceToUpdate)
                } else {
                    errors["experienceNotFound"] = "experienceNotFound"
                }
            }
        }

        return OperationResult(data, errors)
    }

    override fun findByServiceProvider(serviceProvider: ServiceProvider): Optional<Experience> =
        experienceRepository.findByServiceProvider(serviceProvider)

    override fun findById(id: Long): Optional<Experience> = experienceRepository.findById(id)

    override fun findAll(): List<Experience> = experienceRepository.findAll()

    override fun count(): Long = experienceRepository.count()
}