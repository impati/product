package com.example.productapi.global

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class S3Config(
    @Value("\${aws.s3.region}")
    val region: String,
    @Value("\${aws.s3.bucket-name}")
    val bucketName: String,
    @Value("\${aws.s3.directory}")
    val directory: String,
    @Value("\${aws.s3.accessKey}")
    val accessKey: String,
    @Value("\${aws.s3.secretKey}")
    val secretKey: String,
) {

    @Bean
    fun s3Client(): S3Client {
        val credentials = AwsBasicCredentials.create(accessKey, secretKey)
        return S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(Region.of(region))
            .build()
    }
}
