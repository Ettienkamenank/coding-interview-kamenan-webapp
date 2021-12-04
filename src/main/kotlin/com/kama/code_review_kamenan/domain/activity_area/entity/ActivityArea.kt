package com.kama.code_review_kamenan.domain.activity_area.entity

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.entity.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 15:45
 */

@Entity
class ActivityArea() : BaseEntity() {

    var title: String = ""

    @Column(columnDefinition = "text")
    var description: String = ""

    var state: Int = 0

    constructor( title: String ) : this() {
        this.title = title
    }

}