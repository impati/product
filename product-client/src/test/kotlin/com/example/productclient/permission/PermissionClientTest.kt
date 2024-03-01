package com.example.productclient.permission

import com.example.productclient.FeignConfig
import com.example.productclient.FeignConfigTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@Disabled // TODO : 권한 조회 API 개발 시 테스트
@SpringBootTest(classes = [FeignConfigTest::class, FeignConfig::class])
class PermissionClientTest @Autowired constructor(
    val permissionClient: PermissionClient
) {

    @Test
    @DisplayName("멤버가 특정 permission 을 가지고 있는지 확인하는 API")
    fun getHasPermission() {
        val permissionId = 229L
        val memberNumber = "root-member"

        val hasPermissionResponse = permissionClient.getHasPermission(permissionId, memberNumber)

        assertThat(hasPermissionResponse.hasPermission).isTrue()
    }
}
