package com.kama.code_review_kamenan.domain.account.port

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.utils.OperationResult


/**
 * @Author: Ettien Kamenan
 * @On: 04/12/2021
 * @At: 00:30
 */
interface IManageServiceProvider {

    fun makeServiceProviderVisible(id: Long): OperationResult<ServiceProvider>

    fun findAllServiceProviders(): MutableList<ServiceProvider>

}