package com.kama.code_review_kamenan.domain.account.entity

import com.kama.code_review_kamenan.domain.entity.common.Role
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 14:51
 */
@Entity
@DiscriminatorValue(UserType.ADMIN)
class Admin() : User() {
    constructor( username: String, password : String, roles: Set<Role> = setOf() ) : this() {
        this.username = username
        this.password = password
        this.roles = roles
    }
}