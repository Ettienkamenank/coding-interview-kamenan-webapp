package com.kama.code_review_kamenan.application.bootstrap

import com.kama.code_review_kamenan.domain.account.entity.UserType
import com.kama.code_review_kamenan.domain.account.port.RoleDomain
import com.kama.code_review_kamenan.domain.entity.common.Role

object RoleBootstrap {

    fun seed(domain: RoleDomain) {

        if (domain.count() == 0L) {
            domain.save(Role(UserType.ACTUATOR, UserType.ACTUATOR))
            domain.save(Role(UserType.ADMIN, UserType.ADMIN))
            domain.save(Role(UserType.SERVICE_PROVIDER, UserType.SERVICE_PROVIDER))
            domain.save(Role(UserType.CUSTOMER, UserType.CUSTOMER))
        }
    }
}