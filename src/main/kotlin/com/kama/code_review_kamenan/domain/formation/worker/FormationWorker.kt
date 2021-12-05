package com.kama.code_review_kamenan.domain.formation.worker

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.formation.entity.Formation
import com.kama.code_review_kamenan.domain.formation.port.FormationDomain
import com.kama.code_review_kamenan.infrastructure.db.repository.FormationRepository
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
class FormationWorker : FormationDomain {

    @Autowired
    lateinit var formationRepository: FormationRepository

    override fun save(formation: Formation): OperationResult<Formation> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Formation? = null

        if (formation.title.isEmpty()) {
            errors["emptyTitle"] = "emptyTitle"
        }

        if (errors.isEmpty()) {
            if (formation.id == -1L) {
                val optionalFormationTitle = formationRepository.findByTitle(formation.title)

                when {
                    optionalFormationTitle.isPresent -> {
                        errors["formationExists"] = "formationExists"
                    }
                    else -> {
                        data = formationRepository.save(formation)
                    }
                }
            } else {
                val optionalFormation = formationRepository.findById(formation.id)

                if (optionalFormation.isPresent) {
                    val formationToUpdate = optionalFormation.get()
                    formationToUpdate.title = formation.title
                    formationToUpdate.description = formation.description

                    data = formationRepository.save(formationToUpdate)
                } else {
                    errors["formationNotFound"] = "formationNotFound"
                }
            }
        }


        return OperationResult(data, errors)
    }

    override fun findByServiceProvider(serviceProvider: ServiceProvider): Optional<Formation> =
        formationRepository.findByServiceProvider(serviceProvider)

    override fun findById(id: Long): Optional<Formation> = formationRepository.findById(id)

    override fun findAll(): List<Formation> = formationRepository.findAll()

    override fun findAllByServiceProvider(serviceProvider: ServiceProvider): MutableList<Formation> =
        formationRepository.findAllByServiceProvider(serviceProvider)

    override fun count(): Long = formationRepository.count()

}