package com.example.productapi.product.api.controller

import com.example.productapi.product.application.S3Service
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class FileUploadController(
    val s3Service: S3Service
) {

    @PostMapping("/files/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<FileUploadResponse> {

        return ResponseEntity.ok(FileUploadResponse(s3Service.uploadImage(file)))
    }
}

class FileUploadResponse(val imagePath: String)
