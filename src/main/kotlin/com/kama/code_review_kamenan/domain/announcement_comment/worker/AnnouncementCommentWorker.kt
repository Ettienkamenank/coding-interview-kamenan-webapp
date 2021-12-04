package com.kama.code_review_kamenan.domain.announcement_comment.worker

import com.kama.code_review_kamenan.domain.announcement.entity.Announcement
import com.kama.code_review_kamenan.domain.announcement_comment.entity.AnnouncementComment
import com.kama.code_review_kamenan.domain.announcement_comment.port.AnnouncementCommentDomain
import com.kama.code_review_kamenan.infrastructure.db.repository.AnnouncementCommentRepository
import com.kama.code_review_kamenan.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 16:19
 */

@Service
class AnnouncementCommentWorker : AnnouncementCommentDomain {

    @Autowired
    lateinit var announcementCommentRepository: AnnouncementCommentRepository

    override fun save(announcementComment: AnnouncementComment): OperationResult<AnnouncementComment> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: AnnouncementComment? = null

        if (announcementComment.comment.isEmpty()) {
            errors["emptyComment"] = "emptyComment"
        }

        if (errors.isEmpty()) {
            if (announcementComment.id == -1L) {
                data = announcementCommentRepository.save(announcementComment)
            } else {
                val optionalAnnouncementComment =
                    announcementCommentRepository.findById(announcementComment.id).orElse(null)

                if (optionalAnnouncementComment == null) {
                    errors["announcementCommentNotFound"] = "announcementCommentNotFound"
                } else {
                    optionalAnnouncementComment.comment = announcementComment.comment

                    data = announcementCommentRepository.save(optionalAnnouncementComment)
                }
            }
        }

        return OperationResult(data, errors)
    }

    override fun findById(id: Long): Optional<AnnouncementComment> = announcementCommentRepository.findById(id)

    override fun findAllByAnnouncement(announcement: Announcement): MutableList<AnnouncementComment> =
        announcementCommentRepository.findAllByAnnouncement(announcement)

    override fun findAll(): List<AnnouncementComment> = announcementCommentRepository.findAll()

    override fun count(): Long = announcementCommentRepository.count()

    override fun countAllByAnnouncement(announcement: Announcement): Long =
        announcementCommentRepository.countAllByAnnouncement(announcement)
}