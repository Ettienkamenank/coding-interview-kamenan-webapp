package com.kama.code_review_kamenan.application.api

object RestControllerEndpoint {

    private const val API_BASE_URL = "api/v1"

    private const val API_BASE_SECURED_URL = "api/secured/v1"

    /**
     * Mobile app API
     */

    /**
     * Authentication APIs
     */
    const val API_USER_ENDPOINT = "$API_BASE_URL/user"


    /**
     * User Authenticated endpoints
     */
    const val API_SECURED_USER_ENDPOINT = "$API_BASE_SECURED_URL/user"

    const val API_SECURED_ACTIVITY_AREA_ENDPOINT = "$API_BASE_SECURED_URL/activity-area"

    const val API_SECURED_ANNOUNCEMENT_ENDPOINT = "$API_BASE_SECURED_URL/announcement"

}