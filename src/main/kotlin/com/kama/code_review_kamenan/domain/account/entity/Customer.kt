package com.kama.code_review_kamenan.domain.account.entity

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


/**
 * @Author: Ettien Kamenan
 * @On: 03/12/2021
 * @At: 15:02
 */
@Entity
@DiscriminatorValue(UserType.CUSTOMER)
class Customer() : User() {}