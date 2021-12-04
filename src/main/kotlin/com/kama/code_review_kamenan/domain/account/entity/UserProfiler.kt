package com.kama.code_review_kamenan.domain.account.entity

object UserProfiler {

    fun profile(user: User) = UserProfile(user)

    class UserProfile(user: User) {

        val id = user.id

        val username = user.username

        val firstname = user.firstname

        val lastname = user.lastname

        val fullname = "$firstname $lastname"

        var photo = user.photo

        val isEnabled = user.isEnabled

        var actuator: Boolean = false

        var admin: Boolean = false

        var serviceProvider: Boolean = false

        var customer: Boolean = false

        init {
            val roles = user.roles
            actuator = roles!!.any { it.name == UserType.ACTUATOR }
            admin = roles.any { it.name == UserType.ADMIN }
            serviceProvider = roles.any { it.name == UserType.SERVICE_PROVIDER }
            customer = roles.any { it.name == UserType.CUSTOMER }
        }
    }
}