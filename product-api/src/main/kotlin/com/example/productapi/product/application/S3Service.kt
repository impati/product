package com.example.productapi.product.application

import com.example.productapi.global.S3Config
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Component
class S3Service(
    val s3Config: S3Config,
    val s3Client: S3Client
) {

    fun uploadImage(imageFile: MultipartFile): String {
        val savepoint = "${s3Config.directory}/${imageFile.originalFilename}"
        val request = PutObjectRequest.builder()
            .bucket(s3Config.bucketName)
            .key(savepoint)
            .build()

        s3Client.putObject(request, RequestBody.fromInputStream(imageFile.inputStream, imageFile.size))

        return "https://${s3Config.bucketName}.s3.${s3Config.region}.amazonaws.com/${savepoint}"
    }
}
