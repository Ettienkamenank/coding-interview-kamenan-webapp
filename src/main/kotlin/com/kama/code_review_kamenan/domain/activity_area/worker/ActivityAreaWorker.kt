package com.kama.code_review_kamenan.domain.activity_area.worker

import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import com.kama.code_review_kamenan.domain.activity_area.port.ActivityAreaDomain
import com.kama.code_review_kamenan.infrastructure.db.repository.ActivityAreaRepository
import com.kama.code_review_kamenan.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:16
 */
@Service
class ActivityAreaWorker : ActivityAreaDomain {

    @Autowired
    lateinit var activityAreaRepository: ActivityAreaRepository

    override fun save(activityArea: ActivityArea): OperationResult<ActivityArea> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: ActivityArea? = null

        if (activityArea.title.isEmpty()) {
            errors["emptyTitle"] = "emptyTitle"
        }

        if (errors.isEmpty()) {
            if (activityArea.id == -1L) {
                val optionalActivityAreaTitle = activityAreaRepository.findByTitle(activityArea.title)

                when {
                    optionalActivityAreaTitle.isPresent -> {
                        errors["activityAreaExist"] = "activityAreaExists"
                    }
                    else -> {
                        data = activityAreaRepository.save(activityArea)
                    }
                }

            } else {
                val optionalActivityArea = activityAreaRepository.findById(activityArea.id)

                if (optionalActivityArea.isPresent) {
                    val activityAreaToUpdate = optionalActivityArea.get()
                    activityAreaToUpdate.title = activityArea.title
                    activityAreaToUpdate.description = activityArea.description

                    data = activityAreaRepository.save(activityAreaToUpdate)
                } else {
                    errors["activityAreaNotFound"] = "activityAreaNotFound"
                }
            }
        }

        return OperationResult(data, errors)
    }

    override fun findByTitle(title: String): Optional<ActivityArea> = activityAreaRepository.findByTitle(title)

    override fun findById(id: Long): Optional<ActivityArea> = activityAreaRepository.findById(id)

    override fun findAll(): List<ActivityArea> = activityAreaRepository.findAll()

    override fun count(): Long = activityAreaRepository.count()

}