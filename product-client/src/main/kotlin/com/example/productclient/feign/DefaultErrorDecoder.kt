package com.example.productclient.feign

import feign.FeignException.FeignClientException
import feign.Response
import feign.RetryableException
import feign.codec.ErrorDecoder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.System.currentTimeMillis

class DefaultErrorDecoder : ErrorDecoder {

    private val log: Logger = LoggerFactory.getLogger("DefaultErrorDecoder")
    override fun decode(methodKey: String?, response: Response?): Exception {
        val request = response!!.request()
        val exception = FeignClientException.errorStatus(methodKey, response)

        if (exception.status() in 500..599) {
            log.error("외부 API 호출 실패 request = {} , response = {}", request, response)
            return RetryableException(
                exception.status(),
                "서버 오류로 실패했습니다",
                request.httpMethod(),
                currentTimeMillis() + RETRY_AFTER_MS,
                request
            )
        }

        return exception;
    }

    companion object {
        private const val RETRY_AFTER_MS = 100L
    }
}
