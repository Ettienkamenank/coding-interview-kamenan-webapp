package com.kama.code_review_kamenan.application.api

object RestControllerEndpoint {

    const val API_BASE_URL = "api/v1"

    const val API_BASE_SECURED_URL = "api/secured/v1"

    /**
     * Mobile app API
     */

    /**
     * Insecure endpoints
     */
    const val API_USER_ENDPOINT = "$API_BASE_URL/user"

    const val API_CITY_ENDPOINT = "$API_BASE_URL/city"

    const val API_DISTRICT_ENDPOINT = "$API_BASE_URL/district"

    const val API_LOST_OBJECT_TYPE_ENDPOINT = "$API_BASE_URL/lostObjectType"

    const val API_TAG_ENDPOINT = "$API_BASE_URL/tag"


    /**
     * Insecure endpoints
     */
    const val API_SECURED_USER_ENDPOINT = "$API_BASE_SECURED_URL/user"

    const val API_LOST_OBJECT_ENDPOINT = "$API_BASE_SECURED_URL/lostObject"

    const val API_DECLARATION_ENDPOINT = "$API_BASE_SECURED_URL/declaration"

}