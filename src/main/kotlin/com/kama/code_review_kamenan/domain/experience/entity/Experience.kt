package com.kama.code_review_kamenan.domain.experience.entity

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 15:43
 */
@Entity
class Experience : BaseEntity() {

    var title: String = ""

    var description: String = ""

    var state: Int = 0

    @ManyToOne(targetEntity = ServiceProvider::class, optional = false)
    var serviceProvider: ServiceProvider? = null

}