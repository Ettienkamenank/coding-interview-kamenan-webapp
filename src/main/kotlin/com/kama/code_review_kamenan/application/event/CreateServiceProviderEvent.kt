package com.kama.code_review_kamenan.application.event

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import org.springframework.context.ApplicationEvent


/**
 * @Author: Ettien Kamenan
 * @On: 04/12/2021
 * @At: 15:45
 */
class CreateServiceProviderEvent(val serviceProvider: ServiceProvider): ApplicationEvent(serviceProvider)