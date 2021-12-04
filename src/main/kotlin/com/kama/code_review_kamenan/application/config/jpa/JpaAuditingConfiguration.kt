package com.kama.code_review_kamenan.application.config.jpa

import com.kama.code_review_kamenan.application.config.jpa.AuditorAwareImplementation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class JpaAuditingConfiguration {

    @Bean
    fun auditorAware(): AuditorAware<String> = AuditorAwareImplementation()
}