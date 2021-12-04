package com.kama.code_review_kamenan.domain.account.worker

import com.kama.code_review_kamenan.domain.account.entity.ServiceProvider
import com.kama.code_review_kamenan.domain.account.entity.User
import com.kama.code_review_kamenan.domain.account.entity.UserType
import com.kama.code_review_kamenan.domain.account.port.UserDomain
import com.kama.code_review_kamenan.infrastructure.db.repository.UserRepository
import com.kama.code_review_kamenan.utils.EncryptionUtility
import com.kama.code_review_kamenan.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserWorker : UserDomain {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun authenticateUser(username: String, password: String): OperationResult<User> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: User? = null

        if (username.isEmpty() || password.isEmpty()) {
            errors["credentials"] = "Le nom d'utilisateur et le mot de passe sont obligatoires"
        }

        if (errors.isEmpty()) {
            val optionalUser = userRepository.findByUsername(username)
            val badCredentialsError = "Le nom d'utilisateur ou le mot de passe est incorrect"

            if (optionalUser.isPresent) {
                val user = optionalUser.get()
                if (BCryptPasswordEncoder().matches(password, user.password)) {
                    if (user.locked) {
                        errors["accountLocked"] = "Veuillez valider votre compte via l'email qui vous a été transmis"
                    } else {
                        data = user
                    }
                } else {
                    errors["credentials"] = badCredentialsError
                }
            } else {
                errors["credentials"] = badCredentialsError
            }
        }

        return OperationResult(data, errors)
    }

    override fun findTypeBy(id: Long): String {
        return userRepository.findTypeBy(id)
    }

    override fun isTakenUserByEmail(email: String): Boolean {
        return userRepository.countAllByContactEmail(email) > 0L
    }

    override fun isTakenUserByUsername(username: String): Boolean {
        return userRepository.countAllByUsername(username) > 0L
    }

    override fun lock(username: String): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data = false

        val optionalUser = userRepository.findByUsername(username).orElse(null)

        if (optionalUser == null) {
            errors["userNotFound"] = "userNotFound"
        } else {
            optionalUser.locked = true
            userRepository.save(optionalUser)

            data = true
        }

        return OperationResult(data, errors)
    }

    override fun unlock(username: String): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data = false

        val optionalUser = userRepository.findByUsername(username).orElse(null)

        if (optionalUser == null) {
            errors["userNotFound"] = "userNotFound"
        } else {
            optionalUser.locked = false
            userRepository.save(optionalUser)

            data = true
        }

        return OperationResult(data, errors)
    }

    override fun forgotPassword(user: User): OperationResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun updatePassword(user: User, newPassword: String): OperationResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun saveUser(model: User): OperationResult<User> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: User? = null

        if (model.contact.email.isEmpty()) {
            errors["email"] = "emptyEmail"
        }

        if (model.contact.phoneNumber.isEmpty()) {
            errors["phone"] = "empty_phone"
        }

        if (model.firstname.isNullOrEmpty()) {
            errors["firstname"] = "empty_firstName"
        }

        if (model.lastname.isNullOrEmpty()) {
            errors["lastname"] = "empty_lastName"
        }

        if (model.username.isEmpty()) {
            errors["username"] = "empty_username"
        }

        if (errors.isEmpty()) {
            data = userRepository.save(model)
        }

        return OperationResult(data, errors)
    }

    override fun findByUsername(username: String): Optional<User> = userRepository.findByUsername(username)

    override fun findByEmail(email: String): Optional<User> = userRepository.findByContactEmail(email)

    override fun findUserById(id: Long): Optional<User> = userRepository.findById(id)

    override fun count(): Long = userRepository.count()

    override fun makeServiceProviderVisible(id: Long): OperationResult<ServiceProvider> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: ServiceProvider? = null

        val optionalServiceProvider = userRepository.findById(id).orElse(null)

        if (optionalServiceProvider == null) {
            errors["userNotFound"] = "userNotFound"
        } else {
            when (findTypeBy(optionalServiceProvider.id)) {
                UserType.SERVICE_PROVIDER -> {
                    optionalServiceProvider as ServiceProvider
                    optionalServiceProvider.profileVisible = true

                    data = userRepository.save(optionalServiceProvider)
                }
            }
        }

        return OperationResult(data, errors)
    }

    override fun findAllServiceProviders(): MutableList<ServiceProvider> {
        val data: MutableList<ServiceProvider>

        val result = userRepository.findAllServiceProviders(UserType.SERVICE_PROVIDER)

        data = result.map {
            it as ServiceProvider
        }.toMutableList()

        return data
    }

    override fun authenticateUserForMobile(username: String, password: String): OperationResult<User> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: User? = null

        val badCredentialsError = "Identifier or Password is incorrect"
        val optionalUser = userRepository.findByUsername(username)

        if (!optionalUser.isPresent) {
            errors["credentials"] = badCredentialsError
        } else {
            val user = optionalUser.get()
            if (!BCryptPasswordEncoder().matches(password, user.password)) {
                errors["credentials"] = badCredentialsError
            } else {
                EncryptionUtility.sha256From("${user.credential.sessionToken}:${System.currentTimeMillis()}")?.get()
                    .let { encrypted ->
                        user.credential.sessionToken = encrypted!!
                    }

                data = userRepository.save(user)
            }
        }

        return OperationResult(data, errors)
    }

    override fun logoutUserForMobile(sessionToken: String): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Boolean? = false

        val optionalUser = userRepository.findByCredentialSessionToken(sessionToken)

        if (!optionalUser.isPresent) {
            errors["sessionToken"] = "Bad session token"
        }

        optionalUser.ifPresent {
            data = true
            it.credential.sessionToken = ""
            userRepository.save(it)
        }

        return OperationResult(data, errors)
    }

    override fun findUserBySessionToken(sessionToken: String): Optional<User> =
        userRepository.findByCredentialSessionToken(sessionToken)

}