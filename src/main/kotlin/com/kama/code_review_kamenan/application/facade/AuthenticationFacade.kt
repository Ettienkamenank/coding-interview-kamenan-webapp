package com.kama.code_review_kamenan.application.facade

import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthenticationFacade @Autowired
constructor(
    private val userDomain: UserDomain
) {

    fun getAuthenticatedUser(): Optional<User> {
        var optionalUser: Optional<User> = Optional.empty()

        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication !is AnonymousAuthenticationToken) {
            optionalUser = userDomain.findByUsername(authentication.name)
        }

        return optionalUser
    }

    fun getAuthenticatedUsername(): Optional<String> {
        var username: String? = null

        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication !is AnonymousAuthenticationToken) {
            username = authentication.name
        }
        return Optional.ofNullable(username)
    }


    fun getAuthenticatedUsers(): String? {
        var username: String? = null

        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication !is AnonymousAuthenticationToken) {
            username = authentication.name
        }
        return username
    }

}
