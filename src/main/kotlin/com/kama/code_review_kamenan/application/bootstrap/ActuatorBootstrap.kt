package com.kama.code_review_kamenan.application.bootstrap

import com.kama.code_review_kamenan.domain.account.entity.*
import com.kama.code_review_kamenan.domain.account.port.RoleDomain
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import java.util.*

object ActuatorBootstrap {

    fun seed(roleDomain: RoleDomain, userDomain: UserDomain) {
        if (userDomain.count() == 0L) {
            roleDomain.findByName(UserType.ACTUATOR).ifPresent { role ->

                val actuator = Actuator(
                        username = "actuator",
                        password = "grant",
                        roles = Collections.singleton(role)
                )
                actuator.firstname = "Actuator"
                actuator.lastname = "Test"
                actuator.contact.email ="actuator@gmail.com"
                actuator.contact.phoneNumber ="+225 0700000000"
                actuator.enabled = true
                userDomain.saveUser(actuator)
            }

            roleDomain.findByName(UserType.ADMIN).ifPresent { role ->
                val admin = Admin(
                    username = "admin",
                    password = "grant",
                    roles = Collections.singleton(role)
                )
                admin.firstname = "Admin"
                admin.lastname = "Tester"
                admin.contact.email ="admin@gmail.com"
                admin.contact.phoneNumber ="+225 0700000000"
                admin.enabled = true
                userDomain.saveUser(admin)
            }

            roleDomain.findByName(UserType.CUSTOMER).ifPresent { role ->
                val client = Customer()

                client.firstname = "Client"
                client.lastname = "Mobile"
                client.username = "client"
                client.password = "grant"
                client.roles = Collections.singleton(role)
                client.contact.email ="client@gmail.com"
                client.contact.phoneNumber ="+225 0756838980"
                client.enabled = true
                userDomain.saveUser(client)
            }
        }
    }
}