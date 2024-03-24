package com.example.productclient.permission

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PermissionAdaptorTest {

    val permissionAdaptor: PermissionAdaptor = PermissionAdaptor()


    @Test
    @DisplayName("멤버가 특정 permission 을 가지고 있으면 true 를 응답한다.")
    fun hasPermission() {
        val permissionId = 999L
        val memberNumber = "0000"

        val permissionResponse = permissionAdaptor.hasPermission(permissionId, memberNumber)

        assertThat(permissionResponse).isTrue()
    }

    @Test
    @DisplayName("멤버가 특정 permission 을 가지고 있지 않으면 false 를 응답한다.")
    fun hasPermissionFail() {
        // TODO 권한 조회 API 개발 시 테스트 작성
    }

    @Test
    @DisplayName("권한 확인 API 가 실패하면 false 를 응답한다.")
    fun hasPermissionError() {
        // TODO 권한 조회 API 개발 시 테스트 작성
    }
}
