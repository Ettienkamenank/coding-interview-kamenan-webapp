package com.kama.code_review_kamenan.domain.entity.common

import com.kama.code_review_kamenan.domain.entity.common.BaseEntity
import com.kama.code_review_kamenan.domain.entity.common.Constant
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(indexes = [Index(name = "i_role_name", columnList = "name", unique = true)])
class Role : BaseEntity, Serializable {

    @Column(length = Constant.JPA_CONSTRAINTS_MEDIUM)
    var name: String = ""

    var libelle: String = ""

    constructor(name: String) {
        this.name = name
    }

    constructor(name: String, libelle: String) {
        this.name = name
        this.libelle = libelle
    }
}
