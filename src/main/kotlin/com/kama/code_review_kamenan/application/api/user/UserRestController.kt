package com.kama.code_review_kamenan.application.api.user

//import com.kama.code_review_kamenan.application.api.RestControllerEndpoint
//import com.kama.code_review_kamenan.application.common.ResponseBody
//import com.kama.code_review_kamenan.application.common.ResponseSummary
//import com.b2i.lost_and_found_webapp.domain.account.entity.Customer
//import com.b2i.lost_and_found_webapp.domain.account.entity.User
//import com.b2i.lost_and_found_webapp.domain.account.entity.UserType
//import com.b2i.lost_and_found_webapp.domain.account.port.RoleDomain
//import com.b2i.lost_and_found_webapp.domain.account.port.UserDomain
//import com.b2i.lost_and_found_webapp.infrastructure.remote.dto.UserDto
//import com.b2i.lost_and_found_webapp.infrastructure.remote.dto.UserFromMobile
//import org.springframework.context.MessageSource
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.*
//import java.util.*
//
//
///**
// * @Author: Ettien Kamenan
// * @Date: 2021/07/06
// * @Time: 10:40
// */
//@RestController
//@RequestMapping(value = [RestControllerEndpoint.API_USER_ENDPOINT])
//class UserRestController(
//    private val userDomain: UserDomain,
//    private val roleDomain: RoleDomain,
//    private val messageSource: MessageSource
//) {
//
//    @PostMapping(value = ["/create"])
//    fun createUser(
//        @RequestBody(required = true) userFromMobile: UserFromMobile,
//        locale: Locale
//    ): ResponseEntity<ResponseBody<Boolean>> {
//
//        var errors: MutableMap<String, String> = mutableMapOf()
//        var data: User? = null
//
//        if (userFromMobile.password.isEmpty()) {
//            errors["empty_password"] = messageSource.getMessage("empty_password", null, locale)
//        }
//
//        if (userDomain.isTakenUserByEmail(userFromMobile.email)) {
//            errors["email_already_exists"] = messageSource.getMessage("email_already_exists", null, locale)
//        }
//
//        if (userDomain.isTakenUserByUsername(userFromMobile.username)) {
//            errors["username_already_exists"] = messageSource.getMessage("username_already_exists", null, locale)
//        }
//
//        if (errors.isEmpty()) {
//            roleDomain.findByName(UserType.CUSTOMER).ifPresent {
//                val customer = Customer()
//
//                customer.firstname = userFromMobile.firstname
//                customer.lastname = userFromMobile.lastname
//                customer.contact.email = userFromMobile.email
//                customer.username = userFromMobile.username
//                customer.contact.phone = userFromMobile.phone
//                customer.password = userFromMobile.password
//
//                val result = userDomain.saveUser(customer)
//
//                errors = result.errors!!
//                data = result.data
//            }
//
//        }
//
//        return ResponseEntity(ResponseBody(data != null, ResponseSummary(errors)), HttpStatus.OK)
//    }
//
//    @PostMapping(value = ["/authenticate"])
//    fun authenticateUser(
//        @RequestParam("username") username: String,
//        @RequestParam("password") password: String,
//        locale: Locale
//    ): ResponseEntity<ResponseBody<UserDto>> {
//
//        var errors: MutableMap<String, String> = mutableMapOf()
//        var data = UserDto()
//
//        if (username.isEmpty()) {
//            errors["username"] = messageSource.getMessage("empty_username", null, locale)
//        }
//
//        if (password.isEmpty()) {
//            errors["password"] = messageSource.getMessage("empty_password", null, locale)
//        }
//
//        if (errors.isEmpty()) {
//            val result = userDomain.authenticateUserForMobile(username, password)
//            errors = result.errors!!
//
//            if (result.data != null) {
//                data = result.data.toUserDto()
//            }
//        }
//
//        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), HttpStatus.OK)
//    }
//
//}