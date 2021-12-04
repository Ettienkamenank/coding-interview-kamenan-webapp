package com.kama.code_review_kamenan.domain.announcement.entity

import com.kama.code_review_kamenan.domain.account.entity.Customer
import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import com.kama.code_review_kamenan.domain.entity.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 15:47
 */
@Entity
class Announcement() : BaseEntity() {

    var title: String = ""

    @Column(columnDefinition = "text")
    var description: String = ""

    var locked: Boolean = false

    var state: Int = 0

    @ManyToOne(targetEntity = Customer::class, optional = false)
    var customer: Customer? = null

    @ManyToOne(targetEntity = ActivityArea::class, optional = false)
    var activityArea: ActivityArea? = null

}