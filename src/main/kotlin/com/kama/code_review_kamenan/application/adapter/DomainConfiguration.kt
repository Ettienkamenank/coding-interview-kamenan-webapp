package com.kama.code_review_kamenan.application.adapter

import com.kama.code_review_kamenan.domain.account.port.ConnectionDomain
import com.kama.code_review_kamenan.domain.account.port.IAuthenticateUser
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.domain.account.worker.ConnectionWorker
import com.kama.code_review_kamenan.domain.account.worker.UserWorker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfiguration {

    @Bean
    fun userDomain(): UserDomain = UserWorker()

    @Bean
    fun connectionDomain(): ConnectionDomain = ConnectionWorker()

    @Bean
    fun authenticationManager(): IAuthenticateUser = UserWorker()

}