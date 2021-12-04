package com.kama.code_review_kamenan.domain.announcement.worker

import com.kama.code_review_kamenan.domain.account.entity.Customer
import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.announcement.port.AnnouncementDomain
import com.kama.code_review_kamenan.infrastructure.db.repository.AnnouncementRepository
import com.kama.code_review_kamenan.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 21:14
 */

@Service
class AnnouncementWorker : AnnouncementDomain {

    @Autowired
    lateinit var announcementRepository: AnnouncementRepository

    override fun save(announcement: Announcement): OperationResult<Announcement> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Announcement? = null

        if (announcement.title.isEmpty()) {
            errors["emptyTitle"] = "emptyTitle"
        }

        if (announcement.description.isEmpty()) {
            errors["emptyDescription"] = "emptyDescription"
        }

        if (errors.isEmpty()) {
            if (announcement.id == -1L) {
                data = announcementRepository.save(announcement)
            } else {
                val optionalAnnouncement = announcementRepository.findById(announcement.id).orElse(null)

                if (optionalAnnouncement == null) {
                    errors["announcementNotFound"] = "announcementNotFound"
                } else {
                    optionalAnnouncement.title = announcement.title
                    optionalAnnouncement.description = announcement.description

                    data = announcementRepository.save(optionalAnnouncement)
                }
            }
        }

        return OperationResult(data, errors)
    }

    override fun lock(id: Long): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data = false

        val optionalAnnouncement = announcementRepository.findById(id).orElse(null)

        if (optionalAnnouncement == null) {
            errors["announcementNotFound"] = "announcementNotFound"
        } else {
            optionalAnnouncement.locked = true
            announcementRepository.save(optionalAnnouncement)

            data = true
        }

        return OperationResult(data, errors)
    }

    override fun unlock(id: Long): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data = false

        val optionalAnnouncement = announcementRepository.findById(id).orElse(null)

        if (optionalAnnouncement == null) {
            errors["announcementNotFound"] = "announcementNotFound"
        } else {
            optionalAnnouncement.locked = false
            announcementRepository.save(optionalAnnouncement)

            data = true
        }

        return OperationResult(data, errors)
    }

    override fun delete(id: Long): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data = false

        val optionalAnnouncement = announcementRepository.findById(id).orElse(null)

        if (optionalAnnouncement == null) {
            errors["announcementNotFound"] = "announcementNotFound"
        } else {
            optionalAnnouncement.state = 1
            announcementRepository.save(optionalAnnouncement)

            data = true
        }

        return OperationResult(data, errors)
    }

    override fun findById(id: Long): Optional<Announcement> = announcementRepository.findById(id)

    override fun findByTitle(title: String): Optional<Announcement> = announcementRepository.findByTitle(title)

    override fun findByCustomer(customer: Customer): Optional<Announcement> =
        announcementRepository.findByCustomer(customer)

    override fun findByActivityArea(activityArea: ActivityArea): Optional<Announcement> =
        announcementRepository.findByActivityArea(activityArea)

    override fun findAll(): List<Announcement> = announcementRepository.findAll()

    override fun findAllByActivityArea(activityArea: ActivityArea): MutableList<Announcement> =
        announcementRepository.findAllByActivityArea(activityArea)

    override fun count(): Long = announcementRepository.count()
}