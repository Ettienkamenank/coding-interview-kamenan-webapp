package com.kama.code_review_kamenan.application.api.user
//
//import com.kama.code_review_kamenan.application.api.RestControllerEndpoint
//import com.kama.code_review_kamenan.application.common.ResponseBody
//import com.kama.code_review_kamenan.application.common.ResponseSummary
//import com.kama.code_review_kamenan.application.resolver.SessionToken
//import com.b2i.lost_and_found_webapp.domain.account.port.UserDomain
//import com.b2i.lost_and_found_webapp.infrastructure.remote.dto.UserDto
//import com.kama.code_review_kamenan.domain.account.port.UserDomain
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
//@RequestMapping(value = [RestControllerEndpoint.API_SECURED_USER_ENDPOINT])
//class UserSecuredRestController(
//    private val userDomain: UserDomain,
//    private val messageSource: MessageSource,
//) {
//
//    @PostMapping(value = ["/logout"])
//    fun logout(@SessionToken token: String?, locale: Locale): ResponseEntity<ResponseBody<Boolean>> {
//
//        var responseStatus: HttpStatus = HttpStatus.OK
//
//        var errors: MutableMap<String, String> = mutableMapOf()
//        var data: Boolean? = false
//
//        if (token == null) {
//            errors["session"] = messageSource.getMessage("empty_token", null, locale)
//            responseStatus = HttpStatus.FORBIDDEN
//        }
//
//        if (errors.isEmpty()) {
//            val result = userDomain.logoutUserForMobile(token!!)
//            errors = result.errors!!
//            data = result.data
//        }
//
//        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), responseStatus)
//    }
//
//    @GetMapping(value = ["/find_user"])
//    fun getUserById(
//        @RequestParam("userId", required = false) user_id: String?,
//        locale: Locale
//    ): ResponseEntity<ResponseBody<UserDto>> {
//
//        var responseStatus: HttpStatus = HttpStatus.OK
//
//        val errors: MutableMap<String, String> = mutableMapOf()
//        var data = UserDto()
//
//        if (user_id == null) {
//            errors["empty_id"] = messageSource.getMessage("empty_id", null, locale)
//            responseStatus = HttpStatus.FORBIDDEN
//        }
//
//        if (errors.isEmpty()) {
//            userDomain.findUserById(user_id!!.toLong()).ifPresent {
//                data = it.toUserDto()
//            }
//        }
//
//        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), responseStatus)
//    }
//
//}