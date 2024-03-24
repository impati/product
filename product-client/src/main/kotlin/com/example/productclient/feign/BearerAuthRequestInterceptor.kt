package com.example.productclient.feign

import feign.RequestInterceptor
import feign.RequestTemplate

class BearerAuthRequestInterceptor(
    private val bearerToken: String
) : RequestInterceptor {

    override fun apply(template: RequestTemplate?) {
        template!!.header("Authorization", bearerToken)
    }
}
