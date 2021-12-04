package com.kama.code_review_kamenan.domain.reference.worker

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.reference.entity.Reference
import com.kama.code_review_kamenan.domain.reference.port.ReferenceDomain
import com.kama.code_review_kamenan.infrastructure.db.repository.ReferenceRepository
import com.kama.code_review_kamenan.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:21
 */

@Service
class ReferenceWorker: ReferenceDomain {

    @Autowired
    lateinit var referenceRepository: ReferenceRepository

    override fun save(reference: Reference): OperationResult<Reference> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Reference? = null

        if (reference.title.isEmpty()) {
            errors["emptyTitle"] = "emptyTitle"
        }

        if (errors.isEmpty()) {
            if (reference.id == -1L) {
                val optionalReferenceTitle = referenceRepository.findByTitle(reference.title)

                when {
                    optionalReferenceTitle.isPresent -> {
                        errors["referenceExists"] = "referenceExists"
                    }
                    else -> {
                        data = referenceRepository.save(reference)
                    }
                }
            } else {
                val optionalReference = referenceRepository.findById(reference.id)

                if (optionalReference.isPresent) {
                    val referenceToUpdate = optionalReference.get()
                    referenceToUpdate.title = reference.title
                    referenceToUpdate.description = reference.description
                    referenceToUpdate.link = reference.link

                    data = referenceRepository.save(referenceToUpdate)
                } else {
                    errors["referenceNotFound"] = "referenceNotFound"
                }
            }
        }


        return OperationResult(data, errors)
    }

    override fun findByServiceProvider(serviceProvider: ServiceProvider): Optional<Reference> = referenceRepository.findByServiceProvider(serviceProvider)

    override fun findById(id: Long): Optional<Reference> = referenceRepository.findById(id)

    override fun findAll(): List<Reference> = referenceRepository.findAll()

    override fun count(): Long = referenceRepository.count()
}