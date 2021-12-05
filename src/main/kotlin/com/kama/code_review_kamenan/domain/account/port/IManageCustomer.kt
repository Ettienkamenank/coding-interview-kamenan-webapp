package com.kama.code_review_kamenan.domain.account.port

import com.kama.code_review_kamenan.domain.account.entity.Customer
import com.kama.code_review_kamenan.utils.OperationResult


/**
 * @Author: Ettien Kamenan
 * @On: 04/12/2021
 * @At: 00:49
 */
interface IManageCustomer {

//    fun lockCustomerAccount(id: Long): OperationResult<Boolean>
//
//    fun unlockCustomerAccount(id: Long): OperationResult<Boolean>

    fun findAllCustomers(): MutableList<Customer>

}