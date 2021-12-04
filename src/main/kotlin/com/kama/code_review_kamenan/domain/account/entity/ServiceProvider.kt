package com.kama.code_review_kamenan.domain.account.entity

import com.kama.code_review_kamenan.domain.activity_area.entity.ActivityArea
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 15:05
 */

@Entity
@DiscriminatorValue(UserType.SERVICE_PROVIDER)
class ServiceProvider() : User() {

    @Column(columnDefinition = "text")
    var biography: String? = ""

    var idCardFront: String? = ""

    var idCardBack: String? = ""

    var profileVisible: Boolean = false

    @ManyToOne(targetEntity = ActivityArea::class, optional = true)
    var activityArea: ActivityArea? = null

}