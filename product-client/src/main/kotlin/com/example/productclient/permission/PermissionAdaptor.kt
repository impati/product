package com.example.productclient.permission

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PermissionAdaptor(
    //private val permissionClient: PermissionClient, TODO : 권한 조회 API 개발 시 주석 해제
    private val log: Logger = LoggerFactory.getLogger("PermissionAdaptor")
) {

    fun hasPermission(permissionId: Long, memberNumber: String): Boolean {
        return try {
            log.info("getHasPermission API 요청 permissionId = {} , memberNumber = {}", permissionId, memberNumber)
//            val response = permissionClient.getHasPermission(permissionId, memberNumber)  TODO : 권한 조회 API 개발 시 주석 해제
            log.info("getHasPermission API 응답 permissionId = {} , memberNumber = {}", permissionId, memberNumber)

            memberNumber == "0000"
        } catch (e: Exception) {
            log.error(
                "getHasPermission API 에러 permissionId = {} , memberNumber = {}",
                permissionId,
                memberNumber,
                e
            )
            false
        }
    }
}
