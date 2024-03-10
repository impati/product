package com.example.productclient.permission

import com.example.productclient.feign.RetryConfiguration
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "permission-api",
    url = "\${external.permission.prefix}",
    configuration = [BearerAuthorizationConfiguration::class, RetryConfiguration::class]
)
interface PermissionClient {

    @GetMapping("/{permissionId}")
    fun getHasPermission(@PathVariable permissionId: Long, @RequestParam memberNumber: String): PermissionResponse
}
