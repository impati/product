package com.example.productapi.product.application

import com.example.productapi.global.S3Config
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectResponse

class S3ServiceTest {

    @Test
    fun uploadImage() {
        val s3Config = S3Config("region", "bucket", "product", "a", "s")
        val s3Client: S3Client = mockk(relaxed = true)
        val mockFile = MockMultipartFile("imageFile", "originalFileName", "image/jpeg", "mockFileContent".toByteArray())
        val s3Service = S3Service(
            s3Config,
            s3Client
        )
        every {
            s3Client.putObject(
                any(PutObjectRequest::class),
                any(RequestBody::class)
            )
        } returns PutObjectResponse.builder().build()

        val uploadImagePath = s3Service.uploadImage(mockFile)

        assertThat(uploadImagePath).contains("https://bucket.s3.region.amazonaws.com/product/originalFileName")
    }
}
