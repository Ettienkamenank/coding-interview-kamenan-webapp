package com.kama.code_review_kamenan.domain.announcement_action.worker

import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.announcement_action.entity.AnnouncementAction
import com.kama.code_review_kamenan.domain.announcement_action.port.AnnouncementActionDomain
import com.kama.code_review_kamenan.infrastructure.db.repository.AnnouncementActionRepository
import com.kama.code_review_kamenan.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:18
 */

@Service
class AnnouncementActionWorker : AnnouncementActionDomain {

    @Autowired
    lateinit var announcementActionRepository: AnnouncementActionRepository

    override fun save(announcementAction: AnnouncementAction): OperationResult<AnnouncementAction> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: AnnouncementAction? = null

        if (announcementAction.comment.isEmpty()) {
            errors["emptyComment"] = "emptyComment"
        }

        if (errors.isEmpty()) {
            if (announcementAction.id == -1L) {
                val actionNumberByUser =
                    announcementActionRepository.countAllByUserAndType(
                        announcementAction.user!!,
                        announcementAction.type
                    )

                when {
                    actionNumberByUser > 1L -> {
                        errors["announcementActionExists"] = "announcementActionExists"
                    }
                    else -> {
                        data = announcementActionRepository.save(announcementAction)
                    }
                }
            } else {
                val optionalAnnouncementAction =
                    announcementActionRepository.findById(announcementAction.id).orElse(null)

                if (optionalAnnouncementAction == null) {
                    errors["announcementActionNotFound"] = "announcementActionNotFound"
                } else {
                    optionalAnnouncementAction.comment = announcementAction.comment

                    data = announcementActionRepository.save(optionalAnnouncementAction)
                }
            }
        }

        return OperationResult(data, errors)
    }

    override fun findById(id: Long): Optional<AnnouncementAction> = announcementActionRepository.findById(id)

    override fun findAll(): List<AnnouncementAction> = announcementActionRepository.findAll()

    override fun findAllByAnnouncementAndType(
        announcement: Announcement,
        type: String
    ): MutableList<AnnouncementAction> = announcementActionRepository.findAllByAnnouncementAndType(announcement, type)

    override fun count(): Long = announcementActionRepository.count()

    override fun countAllByAnnouncementAndType(announcement: Announcement, type: String): Long =
        announcementActionRepository.countAllByAnnouncementAndType(announcement, type)

    override fun countAllByUserAndType(user: User, type: String): Long =
        announcementActionRepository.countAllByUserAndType(user, type)
}